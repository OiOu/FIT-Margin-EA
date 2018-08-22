package smartBot.bussines.service;

import smartBot.bean.OrderSettings;

public interface OrderSettingsService extends Service<OrderSettings>{

    OrderSettings getByCurrencyId(Integer currencyId);
}
