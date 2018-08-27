package smartBot.bussines.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.*;
import smartBot.bussines.listeners.OrderListener;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.defines.PriorityConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class OrderProcess {
    private static final Log logger = LogFactory.getLog(OrderProcess.class);

    private List<OrderListener> listeners = Collections.synchronizedList(new ArrayList<>());

    public List<OrderListener> getListeners() {
        return this.listeners;
    }

    public void registerListener(OrderListener listener) {
        // Add the listener to the list of registered listeners
        this.listeners.add(listener);
    }

    public void unRegisterListener(OrderListener listener) {
        // Remove the listener from the list of the registered listeners
        this.listeners.remove(listener);
    }

    public void openOrder(CurrencyRates currencyRate, Priority priority, Zone zone, HostPort hostPort) {

        String orderName = priority.getType().getName() + "_" + currencyRate.getTimestamp();

        Order order = new Order();
        order.setName(orderName);
        order.setCurrency(priority.getCurrency());
        order.setLevel(zone.getLevel());
        order.setOpenTimestamp(currencyRate.getTimestamp());
        order.setType(priority.getType().getType());

        if (priority.getType().getType() == PriorityConstants.SELL) {
            order.setSubtype(OrderSubType.OP_SELL_LIMIT);
        } else {
            order.setSubtype(OrderSubType.OP_BUY_LIMIT);
        }

        Integer heightK = (currencyRate.getCurrency().getK() != null ? currencyRate.getCurrency().getK() : 1);
        order.setPrice(zone.getPriceCalc() + zone.getLevel().getOrderAssignmentShift() * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * zone.getScope().getType());

        order.setPriceStopLoss(zone.getPriceStopLoss());
        order.setPriceTakeProfit(zone.getPriceTakeProfit());

        // Optional values
        if (zone.getPriceBreakEvenProfit() != null) {
            order.setPriceBreakEvenProfit(zone.getPriceBreakEvenProfit());
        }
        if (zone.getPriceTrailProfit() != null) {
            order.setPriceTrailProfit(zone.getPriceTrailProfit());
        }

        order.setBreakEvenActivated(false);
        order.setTrailStopActivated(false);
        order.setActivated(false);

        // Notify the list of registered listeners
        this.notifyOpenOrderListeners(order, hostPort);
    }

    public void modifyOrder(Order order,HostPort hostPort) {
        // Notify the list of registered listeners
        this.notifyModifyOrderListeners(order, hostPort);
    }

    public void closeOrder(Order order, HostPort hostPort) {
        // Notify the list of registered listeners
        this.notifyCloseOrderListeners(order, hostPort);
    }

    protected void notifyOpenOrderListeners(Order order, HostPort hostPort) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onOrderOpen(order, hostPort));
    }

    protected void notifyCloseOrderListeners(Order order, HostPort hostPort) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onOrderClose(order, hostPort));
    }

    protected void notifyCloseAllBuyOrderListeners(Integer currencyId, Integer subType, HostPort hostPort) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onOrderCloseAll(currencyId, subType, hostPort));
    }

    protected void notifyModifyOrderListeners(Order order, HostPort hostPort) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onOrderModify(order, hostPort));
    }

    public void activate(Order order, HostPort hostPort) {
        this.listeners.forEach(listener -> listener.onActivate(order, hostPort));
    }

    public void closeAllNotActivated(Integer currencyId, Integer subType, HostPort hostPort) {
        notifyCloseAllBuyOrderListeners(currencyId, subType, hostPort);
    }

}
