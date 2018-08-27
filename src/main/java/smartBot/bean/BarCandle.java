package smartBot.bean;

import org.joda.time.DateTime;

public class BarCandle {

    public static final Integer DOWN = -1;
    public static final Integer UP = 1;

    private Double low;
    private Double high;
    private Double open;
    private Double close;
    private Integer volume;
    private DateTime openTimestamp;
    private Integer type;

    public BarCandle(CurrencyRates currentCurrencyRate) {
        if (currentCurrencyRate == null) {
            return;
        }

        if (currentCurrencyRate.isHistory() && currentCurrencyRate.getOpen() < currentCurrencyRate.getClose()) {
            type = UP;
        }
        if (currentCurrencyRate.isHistory() && currentCurrencyRate.getOpen() > currentCurrencyRate.getClose()) {
            type = DOWN;
        }

        low = currentCurrencyRate.getLow();
        high = currentCurrencyRate.getHigh();
        open = currentCurrencyRate.getOpen();
        close = currentCurrencyRate.getClose();
        volume = currentCurrencyRate.getVolume();
        openTimestamp = currentCurrencyRate.getTimestamp();
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public DateTime getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(DateTime openTimestamp) {
        this.openTimestamp = openTimestamp;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
