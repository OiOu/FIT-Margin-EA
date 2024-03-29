package smartBot.bussines.listeners;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;

public interface ZoneListener {

   void onZoneAdd(Scope scope, Zone zone);

   void onZoneRemove(Scope scope, Zone zone);

   void onZoneTouch(Scope scope, Zone zone);

   void calculate(Scope scope, CurrencyRates currencyRate);
}
