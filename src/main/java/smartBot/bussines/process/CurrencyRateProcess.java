package smartBot.bussines.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Order;
import smartBot.bean.OrderSubType;
import smartBot.bean.Scope;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.defines.OrderConstants;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Transactional
@Component
public class CurrencyRateProcess {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyRateProcess.class);

    @Resource
    private ServerCache serverCache;

    @Resource
    private SimpleScopeProcess simpleScopeProcess;

    @Resource
    private OrderProcess orderProcess;


    public void calculateZones(Scope scope) {
        // Calculate zones for each scope
        simpleScopeProcess.calculate(scope, scope.getCurrencyRate());
    }

    public void checkAndProcess(Scope scope, CurrencyRates currentCurrencyRate, HostPort hostPort) {
        // Determine zones that was touched by price
        simpleScopeProcess.scopeCheckAndProcess(scope, currentCurrencyRate, hostPort);
    }

    public void checkPriority(Scope scope, CurrencyRates currentCurrencyRate, HostPort hostPort) {
        // TODO implement logic as for orders
    }

    public void checkAndProcessOrders(Scope scope, CurrencyRates currentCurrencyRate, HostPort hostPort) {
        List<Order> orderList = serverCache.getOrderFromCache(scope.getCurrency().getId());
        if (orderList != null) {
            for (Iterator<Order> it = orderList.iterator(); it.hasNext();){
                Order order = it.next();

                // Activate order (shows if order was really open)
                if (!order.getActivated()
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPrice() >= currentCurrencyRate.getLow())
                        || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPrice() <= currentCurrencyRate.getHigh()))) {

                    order.setActivated(true);
                    order.setOpenTimestamp(currentCurrencyRate.getTimestamp());
                    orderProcess.activate(order, hostPort);
                }

                // Close order by Break even
                if (order.getActivated() && order.getBreakEvenActivated() && order.getCloseReason() == null
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPriceStopLoss() >= currentCurrencyRate.getLow())
                            || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPriceStopLoss() <= currentCurrencyRate.getHigh()))) {

                    order.setCloseReason(OrderConstants.CLOSE_BREAK_EVEN);
                    order.setCloseTimestamp(currentCurrencyRate.getTimestamp());
                    order.setPoints(new Double(Math.abs(order.getPriceBreakEvenProfit() - order.getPrice() ) / currentCurrencyRate.getPointPips() / currentCurrencyRate.getPointPrice()).intValue());
                    orderProcess.closeOrder(order, hostPort);
                    it.remove();
                }

                // Close order by SL
                if (order.getActivated() && order.getCloseReason() == null
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPriceStopLoss() >= currentCurrencyRate.getLow())
                            || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPriceStopLoss() <= currentCurrencyRate.getHigh()))) {

                    order.setCloseReason(OrderConstants.CLOSE_STOP_LOSS);
                    order.setCloseTimestamp(currentCurrencyRate.getTimestamp());
                    order.setPoints((-1) * new Double(Math.ceil(Math.abs(order.getPrice() - order.getPriceStopLoss()) / currentCurrencyRate.getPointPips() / currentCurrencyRate.getPointPrice())).intValue());
                    orderProcess.closeOrder(order, hostPort);
                    it.remove();
                }

                // Close order by TP
                if (order.getActivated() && order.getCloseReason() == null
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPriceTakeProfit() <= currentCurrencyRate.getHigh())
                            || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPriceTakeProfit() >= currentCurrencyRate.getLow()))) {

                    order.setCloseReason(OrderConstants.CLOSE_TAKE_PROFIT);
                    order.setCloseTimestamp(currentCurrencyRate.getTimestamp());
                    order.setPoints(new Double(Math.ceil(Math.abs(order.getPrice() - order.getPriceTakeProfit()) / currentCurrencyRate.getPointPips() / currentCurrencyRate.getPointPrice())).intValue());
                    orderProcess.closeOrder(order, hostPort);
                    it.remove();
                }

                // Modify order - break even
                if (order.getActivated() && !order.getBreakEvenActivated() && order.getPriceBreakEvenProfit() != null
                    && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPriceBreakEvenProfit() <= currentCurrencyRate.getHigh())
                        || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPriceBreakEvenProfit() >= currentCurrencyRate.getLow()))) {

                    order.setPriceStopLoss(order.getPrice() + 10 * currentCurrencyRate.getPointPips() * currentCurrencyRate.getPointPrice() * scope.getType());
                    order.setBreakEvenActivated(true);
                    //order.setCloseTimestamp(currentCurrencyRate.getTimestamp());
                    orderProcess.modifyOrder(order, hostPort);
                }
            }
        }
    }
}
