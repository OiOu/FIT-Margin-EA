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

                // Close order by Break even
                if (order.getBreakEvenActivated() && order.getCloseReason() == null && order.getPriceBreakEvenProfit() != null
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPriceBreakEvenProfit() >= currentCurrencyRate.getLow())
                            || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPriceBreakEvenProfit() <= currentCurrencyRate.getHigh()))) {

                    order.setCloseReason(OrderConstants.CLOSE_BREAK_EVEN);
                    orderProcess.closeOrder(order, hostPort);
                    it.remove();
                }

                // Close order by SL
                if (order.getCloseReason() == null
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPriceStopLoss() >= currentCurrencyRate.getLow())
                            || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPriceStopLoss() <= currentCurrencyRate.getHigh()))) {

                    order.setCloseReason(OrderConstants.CLOSE_STOP_LOSS);
                    orderProcess.closeOrder(order, hostPort);
                    it.remove();
                }

                // Close order by TP
                if (order.getCloseReason() == null
                        && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPriceTakeProfit() <= currentCurrencyRate.getHigh())
                            || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPriceTakeProfit() >= currentCurrencyRate.getLow()))) {

                    order.setCloseReason(OrderConstants.CLOSE_TAKE_PROFIT);
                    orderProcess.closeOrder(order, hostPort);
                    it.remove();
                }

                // Modify order - break even
                if (!order.getBreakEvenActivated() && order.getPriceTrailProfit() != null
                    && ((order.getSubtype() == OrderSubType.OP_BUY_LIMIT && order.getPriceTrailProfit() <= currentCurrencyRate.getHigh())
                        || (order.getSubtype() == OrderSubType.OP_SELL_LIMIT && order.getPriceTrailProfit() >= currentCurrencyRate.getLow()))) {

                    order.setPriceStopLoss(order.getPriceBreakEvenProfit());
                    order.setBreakEvenActivated(true);

                    orderProcess.modifyOrder(order, hostPort);
                }
            }
        }
    }
}
