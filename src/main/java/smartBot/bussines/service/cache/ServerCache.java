package smartBot.bussines.service.cache;

import org.springframework.stereotype.Component;
import smartBot.bean.Currency;
import smartBot.bean.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ServerCache {

    private static Map<Integer, Map<Integer, CurrencyRates>> currencyRateLastMinMaxCache = new ConcurrentHashMap<>();
    private static Map<Integer, Currency> currencyCache = new ConcurrentHashMap<>();
    private static Map<Integer, List<MarginRates>> marginRateCache = new ConcurrentHashMap<>();
    private static Map<Integer, List<Scope>> scopeCache = new ConcurrentHashMap<>();
    private static List<ZoneLevel> zoneLevelCache = new ArrayList<>();

    // Map<CurrencyId, Map<Type, Rate>>
    public Map<Integer, Map<Integer, CurrencyRates>> getCurrencyRatesCache() {
        return currencyRateLastMinMaxCache;
    }

    public Currency getCurrencyFromCache(Integer currencyId) {
        return currencyCache.get(currencyId);
    }

    public synchronized void setCurrencyToCache(Currency currency) {
        if (currency == null) return;
        currencyCache.put(currency.getId(), currency);
    }

    public Currency getCurrencyFromCache(String s) {
        for (Map.Entry<Integer, Currency> entry : currencyCache.entrySet()) {
            if (entry.getValue().getShortName().equalsIgnoreCase(s) ||
                    entry.getValue().getClearingCode().equalsIgnoreCase(s)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public synchronized void setCurrencyToCache(String shortName, Currency currency) {
        if (currency == null) return;

        for (Map.Entry<Integer, Currency> entry : currencyCache.entrySet()) {
            if (entry.getValue().getShortName().equalsIgnoreCase(shortName)) {
                currencyCache.put(entry.getValue().getId(), currency);
                break;
            }
        }

    }

    public synchronized boolean isNewCalculationNeededOrSkip(CurrencyRates currencyRate, Integer type) {

        Map<Integer, CurrencyRates> tmpCurrencyRateMap = currencyRateLastMinMaxCache.get(currencyRate.getCurrency().getId());

        // init
        if (tmpCurrencyRateMap == null) {
            tmpCurrencyRateMap = new HashMap<>();
            currencyRateLastMinMaxCache.put(currencyRate.getCurrency().getId(), tmpCurrencyRateMap);
        }

        // check
        CurrencyRates tmpCurrencyRates = tmpCurrencyRateMap.get(type);

        if (tmpCurrencyRates == null || type.intValue() == Scope.BUILD_FROM_HIGH && tmpCurrencyRates.getHigh() < currencyRate.getHigh()) {
            tmpCurrencyRateMap.remove(tmpCurrencyRates);
            tmpCurrencyRateMap.put(type, currencyRate);

            return true;
        }
        if (tmpCurrencyRates == null || type.intValue() == Scope.BUILD_FROM_LOW && tmpCurrencyRates.getLow() > currencyRate.getLow()) {
            tmpCurrencyRateMap.remove(tmpCurrencyRates);
            tmpCurrencyRateMap.put(type, currencyRate);

            return true;
        }
        return false;
    }


    public MarginRates getMarginRateFromCache(Integer currencyId, Date onDate) {
        List<MarginRates> marginRateList = marginRateCache.get(currencyId);

        if (marginRateList == null) {
            marginRateList = new ArrayList<>();
        }

        Collections.sort(marginRateList);
        for (MarginRates marginRate : marginRateList) {
            if (marginRate.getStartDate().before(onDate)) {
                return marginRate;
            }
        }
        return null;
    }

    public synchronized void setMarginRateToCache(Integer currencyId, MarginRates marginRate) {

        List<MarginRates> marginRateList = marginRateCache.get(currencyId);
        if (marginRateList == null) {
            marginRateList = new ArrayList<>();
        }

        marginRateList.add(marginRate);
        marginRateCache.put(currencyId, marginRateList);
    }

    public synchronized Scope getScopeFromCache(Integer currencyId, Integer type, Date onDate) {
        List<Scope> scopeList = scopeCache.get(currencyId);
        if (scopeList == null) {
            scopeList = new ArrayList<>();
        }

        List<Scope> scopeListTmp = null;
        scopeListTmp = scopeList
                .stream()
                .filter(scope ->
                            scope.getTimestampFrom().before(onDate)
                                    && scope.getCurrency().getId() == currencyId
                                    && scope.getType() == type)
                .collect(Collectors.toList());

        Collections.sort(scopeListTmp);
        Scope scopeTmp = null;
        if (!scopeListTmp.isEmpty()) {
            scopeTmp = scopeListTmp.get(0);
        }

        return scopeTmp;
    }

    public synchronized void setScopeCache(Scope scope) {
        List<Scope> scopeList = scopeCache.get(scope.getCurrency().getId());
        if (scopeList == null) {
            scopeList = new ArrayList<>();
        }

        boolean exists = false;
        for (Scope s : scopeList) {
            if (s.getCurrency().getId().intValue() == scope.getCurrency().getId().intValue()
                    && s.getType().intValue() == scope.getType().intValue()
                    && s.getTimestampFrom() == scope.getTimestampFrom()) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            scopeList.add(scope);
            scopeCache.put(scope.getCurrency().getId(), scopeList);
        }
    }

    public synchronized void removeScopeFromCache(Scope scope) {
        if (scopeCache != null && scopeCache.containsValue(scope)) {
            scopeCache.remove(scope.getCurrency().getId());
        }
    }

    public synchronized ZoneLevel getZoneLevelFromCache(Integer id) {
        for (ZoneLevel zoneLevel : zoneLevelCache) {
            if (zoneLevel.getId().intValue() == id) {
                return zoneLevel;
            }
        }

        return null;
    }

    public synchronized List<ZoneLevel> getZoneLevelFromCache() {
        return zoneLevelCache;
    }

    public synchronized static void setZoneLevelToCache(List<ZoneLevel> zoneLevels) {
        zoneLevelCache.clear();
        zoneLevelCache.addAll(zoneLevels);
    }

    public synchronized static void setZoneLevelToCache(ZoneLevel zoneLevel) {
        if (!zoneLevelCache.contains(zoneLevel)) {
            zoneLevelCache.add(zoneLevel);
        }
    }
}
