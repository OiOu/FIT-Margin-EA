package smartBot.bean.json;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import smartBot.bean.MarginRates;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarginRatesJSON implements Serializable {

    static class Props {
        Integer pageTotal;
        Integer pageSize;
        Integer pageNumber;

        public Integer getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(Integer pageTotal) {
            this.pageTotal = pageTotal;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(Integer pageNumber) {
            this.pageNumber = pageNumber;
        }
    }

    private static final long serialVersionUID = 1L;

    private Integer total;
    private Props props;
    private List<MarginRates> marginRates;
    private boolean empty;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Props getProps() {
        return props;
    }

    public void setProps(Props props) {
        this.props = props;
    }

    public List<MarginRates> getMarginRates() {
        return marginRates;
    }

    public void setMarginRates(List<MarginRates> marginRates) {
        this.marginRates = marginRates;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
