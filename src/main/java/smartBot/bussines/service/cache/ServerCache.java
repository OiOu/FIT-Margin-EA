package smartBot.bussines.service.cache;

import org.springframework.stereotype.Component;
import smartBot.bean.Currency;
import smartBot.bean.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ServerCache {

    private static Map<Integer, Map<Integer, CurrencyRates>> currencyRateLastMinMaxCache = Collections.synchronizedMap(new ConcurrentHashMap<>());
    private static Map<Integer, Currency> currencyCache = Collections.synchronizedMap(new ConcurrentHashMap<>());
    private static Map<Integer, List<MarginRates>> marginRateCache = Collections.synchronizedMap(new ConcurrentHashMap<>());
    private static Map<Integer, List<Scope>> scopeCache = Collections.synchronizedMap(new ConcurrentHashMap<>());
    private static List<ZoneLevel> zoneLevelCache = Collections.synchronizedList(new ArrayList<>());
    private static Map<Integer, Priority> priorityCache = Collections.synchronizedMap(new ConcurrentHashMap<>());

    // Map<CurrencyId, Map<Type, Rate>>
    public Map<Integer, Map<Integer, CurrencyRates>> getCurrencyRatesCache() {
        return currencyRateLastMinMaxCache;
    }

    public Currency getCurrencyFromCache(Integer currencyId) {
        return currencyCache.get(currencyId);
    }

    public void setCurrencyToCache(Currency currency) {
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

    public void setCurrencyToCache(String shortName, Currency currency) {
        if (currency == null) return;

        for (Map.Entry<Integer, Currency> entry : currencyCache.entrySet()) {
            if (entry.getValue().getShortName().equalsIgnoreCase(shortName)) {
                currencyCache.put(entry.getValue().getId(), currency);
                break;
            }
        }

    }

    public boolean isNewCalculationNeededOrSkip(CurrencyRates currencyRate, Integer type) {

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

    public void setScopeCache(Scope scope) {
        List<Scope> scopeList = scopeCache.get(scope.getCurrency().getId());
        if (scopeList == null) {
            scopeList = new ArrayList<>();
        }

        for (Scope s : scopeList) {
            if (s.getId().intValue() == scope.getId().intValue()
                    && s.getType().intValue() == scope.getType().intValue()
                    && s.getTimestampFrom() == scope.getTimestampFrom()) {
                scopeList.remove(s);
                break;
            }
        }

        scopeList.add(scope);
        scopeCache.put(scope.getCurrency().getId(), scopeList);
    }

    public void removeScopeFromCache(Scope scope) {
        if (scopeCache != null && scopeCache.containsValue(scope)) {
            scopeCache.remove(scope.getCurrency().getId());
        }
    }

    public ZoneLevel getZoneLevelFromCache(Integer id) {
        for (ZoneLevel zoneLevel : zoneLevelCache) {
            if (zoneLevel.getId().intValue() == id) {
                return zoneLevel;
            }
        }

        return null;
    }

    public List<ZoneLevel> getZoneLevelFromCache() {
        return zoneLevelCache;
    }

    public static void setZoneLevelToCache(List<ZoneLevel> zoneLevels) {
        zoneLevelCache.clear();
        zoneLevelCache.addAll(zoneLevels);
    }

    public static void setZoneLevelToCache(ZoneLevel zoneLevel) {
        if (!zoneLevelCache.contains(zoneLevel)) {
            zoneLevelCache.add(zoneLevel);
        }
    }

    public Priority getPriorityFromCache(Integer currencyId) {
        return priorityCache.get(currencyId);
    }

    public void setPriorityFromCache(Integer currencyId, Priority priority) {
        Priority priorityFromCache = priorityCache.get(currencyId);
        if (priorityFromCache == null) {
            priorityFromCache = priority;
        } else {
            if (priorityFromCache.getType() != priority.getType()) {
                priorityFromCache = priority;
            }
        }
        if (priorityFromCache != null) {
            priorityCache.put(currencyId, priorityFromCache);
        }
    }

}
