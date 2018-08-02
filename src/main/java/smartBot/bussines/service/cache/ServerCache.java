package smartBot.bussines.service.cache;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import smartBot.bean.Currency;
import smartBot.bean.*;
import smartBot.utils.DoubleUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ServerCache {

    private static Map<Integer, Map<Integer, CurrencyRates>> currencyRateCache = Collections.synchronizedMap(new ConcurrentHashMap<>());
    private static Map<Integer, Currency> currencyCache = Collections.synchronizedMap(new ConcurrentHashMap<>());
    private static Map<Integer, List<MarginRates>> marginRateCache = Collections.synchronizedMap(new ConcurrentHashMap<>());
    private static Map<Integer, List<Scope>> scopeCache = Collections.synchronizedMap(new ConcurrentHashMap<>());
    private static List<ZoneLevel> zoneLevelCache = Collections.synchronizedList(new ArrayList<>());
    private static List<PriorityType> priorityTypeCache = Collections.synchronizedList(new ArrayList<>());
    private static List<PrioritySubType> prioritySubTypeCache = Collections.synchronizedList(new ArrayList<>());

    // Map<CurrencyId, Map<Type, Rate>>
    public CurrencyRates getCurrencyRatesFromCache(Integer currencyId, Integer scopeType) {
        Map<Integer, CurrencyRates> currencyRatesMap = currencyRateCache.get(currencyId);

        if (currencyRatesMap != null) {
            return currencyRatesMap.get(scopeType);
        }

        return null;
    }

    public void setCurrencyRatesToCache(CurrencyRates currencyRate) {
        Map<Integer, CurrencyRates> currencyRatesMap = currencyRateCache.get(currencyRate.getCurrency().getId());

        if (currencyRatesMap != null) {
            currencyRatesMap.remove(currencyRate.getScope().getType());
            currencyRatesMap.put(currencyRate.getScope().getType(), currencyRate);
        }
        return;
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

    public boolean isNewCalculationNeededOrSkip(CurrencyRates currencyRateFromDB, CurrencyRates currentCurrencyRate, Integer type) {

        if (type.intValue() == Scope.BUILD_FROM_HIGH &&
                (currencyRateFromDB == null
                        || DoubleUtils.round(currencyRateFromDB.getHigh() , 5) < DoubleUtils.round(currentCurrencyRate.getHigh(), 5))) {
            return true;
        }
        if (type.intValue() == Scope.BUILD_FROM_LOW &&
                (currencyRateFromDB == null
                        || DoubleUtils.round(currencyRateFromDB.getLow(), 5) > DoubleUtils.round(currentCurrencyRate.getLow(), 5))) {
            return true;
        }
        return false;
    }


    public MarginRates getMarginRateFromCache(Integer currencyId, DateTime onDate) {
        List<MarginRates> marginRateList = marginRateCache.get(currencyId);

        if (marginRateList == null) {
            marginRateList = new ArrayList<>();
        }

        Collections.sort(marginRateList);
        for (MarginRates marginRate : marginRateList) {
            if (marginRate.getStartDate().isBefore(onDate)) {
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

    public Scope getScopeFromCache(Integer currencyId, Integer type) {
        List<Scope> scopeList = scopeCache.get(currencyId);
        if (scopeList == null) {
            scopeList = new ArrayList<>();
        }

        List<Scope> scopeListTmp = scopeList
                .stream()
                .filter(scope -> scope.getTimestampTo() == null
                                && scope.getCurrency().getId() == currencyId
                                && scope.getType() == type)
                .collect(Collectors.toList());

        Collections.sort(scopeListTmp);
        Optional<Scope> scopeTmp = null;
        if (!scopeListTmp.isEmpty()) {
            scopeTmp = scopeListTmp.stream().findFirst();
            return scopeTmp.get();
        }
        return null;
    }

    public void setScopeCache(Scope scope) {
        List<Scope> scopeList = scopeCache.get(scope.getCurrency().getId());
        if (scopeList == null) {
            scopeList = new ArrayList<>();
        }

        for (Scope s : scopeList) {
            if (s.getId().intValue() == scope.getId().intValue()
                    && s.getType().intValue() == scope.getType().intValue()) {
                scopeList.remove(s);
                break;
            }
        }

        scopeList.add(scope);
        scopeCache.put(scope.getCurrency().getId(), scopeList);
    }

    public void removeScopeFromCache(Scope scope) {
        if (scopeCache != null) {
            List<Scope> scopeList = scopeCache.get(scope.getCurrency().getId());
            if (scopeList != null) {
                scopeList.remove(scope);
            }
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

    public void removeCurrencyRateFromCache(CurrencyRates currencyRate, Integer type) {
        if (currencyRateCache != null) {
            Map<Integer, CurrencyRates> tmpCurrencyRateMap = currencyRateCache.get(currencyRate.getCurrency().getId());
            if (tmpCurrencyRateMap != null) {
                tmpCurrencyRateMap.remove(type);
            }
        }
    }

    public void setPriorityTypeToCache(PriorityType priorityType) {
        if (priorityTypeCache != null && !priorityTypeCache.contains(priorityType)) {
            priorityTypeCache.add(priorityType);
        }
    }

    public void setPrioritySubType(PrioritySubType prioritySubType) {
        if (prioritySubTypeCache != null && !prioritySubTypeCache.contains(prioritySubType)) {
            prioritySubTypeCache.add(prioritySubType);
        }
    }

    public PriorityType getPriorityTypeFromCache(Integer type) {
        if (priorityTypeCache != null) {
            for (PriorityType pt : priorityTypeCache) {
                if (pt.getType() == type) {
                    return pt;
                }
            }
        }
        return null;
    }

    public PrioritySubType getPrioritySubTypeFromCache(Integer subtype) {
        if (prioritySubTypeCache != null) {
            for (PrioritySubType pst : prioritySubTypeCache) {
                if (pst.getSubtype() == subtype) {
                    return pst;
                }
            }
        }
        return null;
    }
}
