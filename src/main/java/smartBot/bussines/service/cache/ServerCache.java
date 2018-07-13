package smartBot.bussines.service.cache;

import org.springframework.stereotype.Component;
import smartBot.bean.Currency;
import smartBot.bean.*;

import java.util.*;

@Component
public class ServerCache {

    private static Map<Integer, Map<Integer, CurrencyRates>> currencyRateLastMinMaxCache = new HashMap<>();
    private static Map<Integer, Currency> currencyCache = new HashMap<>();
    private static Map<Integer, List<MarginRates>> marginRateCache = new HashMap<>();
    private static Map<Integer, List<Scope>> scopeCache = new HashMap<>();
    private static List<ZoneLevel> zoneLevelCache = new ArrayList<>();

    // Map<CurrencyId, Map<Type, Rate>>
    public Map<Integer, Map<Integer, CurrencyRates>> getCurrencyRatesCache() {
        return currencyRateLastMinMaxCache;
    }

    public Currency getCurrencyFromCache(Integer currencyId) {
        return currencyCache.get(currencyId);
    }

    public void setCurrencyToCache(Currency currency) {
        if (currency == null ) return;
        currencyCache.put(currency.getId(), currency);
    }

    public Currency getCurrencyFromCache(String s) {
        for (Map.Entry<Integer, Currency> entry :currencyCache.entrySet()) {
            if (entry.getValue().getShortName().equalsIgnoreCase(s) ||
                    entry.getValue().getClearingCode().equalsIgnoreCase(s)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void setCurrencyToCache(String shortName, Currency currency) {
        if (currency == null) return;

        for (Map.Entry<Integer, Currency> entry :currencyCache.entrySet()) {
            if (entry.getValue().getShortName().equalsIgnoreCase(shortName)) {
                currencyCache.put(entry.getValue().getId(), currency);
                break;
            }
        }

    }

    public boolean isNewCalculationNeededOrSkip(CurrencyRates currencyRates, Integer type) {

        Map<Integer, CurrencyRates> tmpCurrencyRateMap = currencyRateLastMinMaxCache.get(currencyRates.getCurrencyId());

        // init
        if (tmpCurrencyRateMap == null) {
            tmpCurrencyRateMap = new HashMap<>();
            tmpCurrencyRateMap.put(Scope.BUILD_FROM_HIGH, currencyRates);
            tmpCurrencyRateMap.put(Scope.BUILD_FROM_LOW, currencyRates);
            currencyRateLastMinMaxCache.put(currencyRates.getCurrencyId(), tmpCurrencyRateMap);

            return true;
        } else {
            // check
            CurrencyRates tmpCurrencyRates = tmpCurrencyRateMap.get(type);

            if (type.intValue() == Scope.BUILD_FROM_HIGH && tmpCurrencyRates.getHigh() < currencyRates.getHigh()) {
                tmpCurrencyRateMap.remove(tmpCurrencyRates);
                tmpCurrencyRateMap.put(type, currencyRates);

                return true;
            }
            if (type.intValue() == Scope.BUILD_FROM_LOW && tmpCurrencyRates.getLow() > currencyRates.getLow()) {
                tmpCurrencyRateMap.remove(tmpCurrencyRates);
                tmpCurrencyRateMap.put(type, currencyRates);

                return true;
            }
        }
        return false;
    }


    public MarginRates getMarginRateFromCache(Integer currencyId, Date onDate) {
        List<MarginRates> marginRateList = marginRateCache.get(currencyId);

        if (marginRateList == null) {
            marginRateList = new ArrayList<>();
        }

        Collections.sort(marginRateList);
        for (MarginRates marginRate: marginRateList) {
           if (marginRate.getStartDate().before(onDate)) {
                return marginRate;
           }
        }
        return null;
    }

    public void setMarginRateToCache(Integer currencyId, MarginRates marginRate) {

        List<MarginRates> marginRateList = marginRateCache.get(currencyId);
        if (marginRateList == null) {
            marginRateList = new ArrayList<>();
        }

        marginRateList.add(marginRate);
        marginRateCache.put(currencyId, marginRateList);
    }

    public Scope getScopeFromCache(Integer currencyId, Integer type, Date onDate) {
        List<Scope> scopeList = scopeCache.get(currencyId);
        if (scopeList == null) {
            scopeList = new ArrayList<>();
        }

        Scope scopeTmp = null;
        for (Scope scope: scopeList) {
            if (scope.getType() == type
                    && scope.getTimestampFrom().before(onDate)
                    && scope.getTimestampTo() == null
                    || scope.getTimestampTo() != null && scope.getTimestampTo().after(onDate)) {
                return scope;
            }
        }

        return scopeTmp;
    }

    public void setScopeCache(Scope scope) {
        List<Scope> scopeList = scopeCache.get(scope.getCurrencyId());
        if (scopeList == null) {
            scopeList = new ArrayList<>();
        }

        boolean exists = false;
        for (Scope s : scopeList) {
            if (s.getCurrencyId().intValue() == scope.getCurrencyId().intValue()
                    && s.getType().intValue() == scope.getType().intValue()
                    && s.getTimestampFrom() == scope.getTimestampFrom()) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            scopeList.add(scope);
            scopeCache.put(scope.getCurrencyId(), scopeList);
        }
    }

    public ZoneLevel getZoneLevelFromCache(Integer id) {
        for (ZoneLevel zoneLevel: zoneLevelCache) {
            if (zoneLevel.getId().intValue() == id) {
                return zoneLevel;
            }
        }

        return null;
    }

    public static void setZoneLevelToCache(ZoneLevel zoneLevel) {
        zoneLevelCache.add(zoneLevel);
    }
}
