package smartBot.data.repository.types;

public class Range<T> {
    private boolean minRangeInclusive;
    private boolean maxRangeInclusive;
    private T minValue;
    private T maxValue;

    public Range () {
    }

    public Range (String dbData) {
        this.minRangeInclusive = (dbData != null && dbData.startsWith("["));
        this.maxRangeInclusive = (dbData != null && dbData.endsWith("]"));
    }

    public boolean isMinRangeInclusive() {
        return minRangeInclusive;
    }

    public void setMinRangeInclusive(boolean minRangeInclusive) {
        this.minRangeInclusive = minRangeInclusive;
    }

    public boolean isMaxRangeInclusive() {
        return maxRangeInclusive;
    }

    public void setMaxRangeInclusive(boolean maxRangeInclusive) {
        this.maxRangeInclusive = maxRangeInclusive;
    }

    public T getMinValue() {
        return minValue;
    }

    public void setMinValue(T minValue) {
        this.minValue = minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(T maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        if (minValue == null && maxValue == null) return sb.toString();

        if (this.minRangeInclusive) sb.append("["); else sb.append("(");
        if (minValue != null) sb.append(minValue); else sb.append("");

        sb.append(",");

        if (maxValue != null) sb.append(maxValue); else sb.append("");
        if (this.maxRangeInclusive) sb.append("]"); else sb.append(")");

        return sb.toString();
    }
}
