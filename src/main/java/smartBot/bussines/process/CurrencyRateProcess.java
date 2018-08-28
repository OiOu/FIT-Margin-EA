package smartBot.bussines.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.*;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.defines.OrderConstants;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    public void checkAndProcessOrders(CurrencyRates currentCurrencyRate, HostPort hostPort) {
        List<Order> orderList = serverCache.getOrderFromCache(currentCurrencyRate.getCurrency().getId());
        List<Order> orderToRemove = new ArrayList<>();
        List<Order> orderToSave = new ArrayList<>();

        BarCandle barCandle = new BarCandle(currentCurrencyRate);
        Integer heightK = (currentCurrencyRate.getCurrency().getK() != null? currentCurrencyRate.getCurrency().getK() : 1);

        if (orderList != null) {
            for (Order order : orderList) {
                // Activate order (shows if order was really open)
                if (!order.getActivated()
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPrice() >= barCandle.getLow())
                        || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPrice() <= barCandle.getHigh()))) {

                    order.setActivated(true);
                    order.setOpenTimestamp(barCandle.getOpenTimestamp());
                    orderProcess.activate(order, hostPort);
                    orderToSave.add(order);
                }

                // Close order by SL
                if (order.getActivated() && order.getCloseReason() == null
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPriceStopLoss() >= barCandle.getLow())
                        || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPriceStopLoss() <= barCandle.getHigh()))) {

                    order.setCloseReason(OrderConstants.CLOSE_STOP_LOSS);
                    if (order.getBreakEvenActivated()) {
                        order.setCloseReason(OrderConstants.CLOSE_BREAK_EVEN);
                    }

                    order.setCloseTimestamp(barCandle.getOpenTimestamp());
                    Double points = order.getPriceStopLoss() - order.getPrice();
                    if (order.getSubtype() == OrderSubType.OP_SELL_LIMIT ) {
                        points = order.getPrice() - order.getPriceStopLoss();
                    }

                    order.setPoints(((Double) (points / heightK / currentCurrencyRate.getPointPips() / currentCurrencyRate.getPointPrice())).intValue());
                    orderProcess.closeOrder(order, hostPort);
                    orderToRemove.add(order);

                    continue;
                }

                // Modify order - break even when zone price will be triggered
                if (order.getActivated() && !order.getBreakEvenActivated() && order.getPriceBreakEvenProfit() != null
                    && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT
                        && ((barCandle.getType() == null && order.getPriceBreakEvenProfit() <= barCandle.getHigh())
                            || (barCandle.getType() != null && (barCandle.getType() == BarCandle.UP && order.getPriceBreakEvenProfit() <= barCandle.getHigh()))))

                    || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT
                        && ((barCandle.getType() == null && order.getPriceBreakEvenProfit() >= barCandle.getLow() )
                            || (barCandle.getType() != null && (barCandle.getType() == BarCandle.DOWN && order.getPriceBreakEvenProfit() >= barCandle.getLow()))))
                    )
                ) {

                    // TODO 11 - get from DB
                    order.setPriceStopLoss(order.getPrice() + 11 * heightK * currentCurrencyRate.getPointPips() * currentCurrencyRate.getPointPrice());
                    if (order.getSubtype() == OrderSubType.OP_SELL_LIMIT ) {
                        order.setPriceStopLoss(order.getPrice() - 11 * heightK * currentCurrencyRate.getPointPips() * currentCurrencyRate.getPointPrice());
                    }

                    order.setBreakEvenActivated(true);
                    orderProcess.modifyOrder(order, hostPort);
                    orderToSave.add(order);

                }

                // Modify order - Trail stop
                if (order.getActivated() && order.getPriceTrailProfit() != null && !order.getTrailStopActivated()
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT
                        && ((barCandle.getType() == null && order.getPriceTrailProfit() <= barCandle.getHigh() )
                            || (barCandle.getType() != null && (barCandle.getType() == BarCandle.UP && order.getPriceTrailProfit() <= barCandle.getHigh()))))

                        || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT
                        && ((barCandle.getType() == null && order.getPriceTrailProfit() >= barCandle.getLow())
                            || (barCandle.getType() != null && (barCandle.getType() == BarCandle.DOWN && order.getPriceTrailProfit() >= barCandle.getLow()))))
                )) {
                    if (order.getPriceBreakEvenProfit() == null) {
                        order.setPriceStopLoss(order.getPriceTrailProfit());
                    } else {
                        order.setPriceStopLoss(order.getPriceBreakEvenProfit());
                    }

                    order.setTrailStopActivated(true);
                    orderProcess.modifyOrder(order, hostPort);
                    orderToSave.add(order);

                }

                // Close order by TP
                if (order.getActivated() && order.getCloseReason() == null
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT
                            && ((barCandle.getType() == null && order.getPriceTakeProfit() <= barCandle.getHigh())
                                || (barCandle.getType() != null && (barCandle.getType() == BarCandle.UP && order.getPriceTakeProfit() <= barCandle.getHigh()))))

                        || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT
                            && ((barCandle.getType() == null && order.getPriceTakeProfit() >= barCandle.getLow())
                                || (barCandle.getType() != null && (barCandle.getType() == BarCandle.DOWN && order.getPriceTakeProfit() >= barCandle.getLow()))))
                )) {

                    order.setCloseReason(OrderConstants.CLOSE_TAKE_PROFIT);
                    order.setCloseTimestamp(currentCurrencyRate.getTimestamp());
                    order.setPoints(new Double(Math.ceil(Math.abs(order.getPrice() - order.getPriceTakeProfit()) / heightK / currentCurrencyRate.getPointPips() / currentCurrencyRate.getPointPrice())).intValue());
                    orderProcess.closeOrder(order, hostPort);
                    orderToRemove.add(order);

                    continue;
                }
            }


            if (orderToRemove.size() > 0) {
                orderToRemove.forEach(order -> serverCache.removeOrderFromCache(order));
            }

            if (orderToSave.size() > 0) {
                orderToSave.forEach(order -> serverCache.setOrderToCache(order));
            }
        }
    }
}
