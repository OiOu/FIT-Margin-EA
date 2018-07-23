package smartBot.bussines.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;

@Component
@Transactional
public class PriorityProcess {
    private static final Log logger = LogFactory.getLog(PriorityProcess.class);

    public void determine(Scope scope, CurrencyRates currencyRate) {
        if (scope != null) {
            // Notify the list of registered listeners
            //this.notifyZoneTouchListeners(scope, currencyRate);
        }
        return;
    }
}
