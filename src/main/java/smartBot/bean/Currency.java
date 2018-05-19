package smartBot.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id;

    @Size( min = 1, max = 255 )
    private String name;

    @NotNull
    @Size( max = 255 )
    private String shortName; // currency code: EUR, USD, CAD


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
