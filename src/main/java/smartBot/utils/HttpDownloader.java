package smartBot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import smartBot.bean.json.MarginRatesJSON;

import java.io.IOException;
import java.net.URL;

@Component
@Configuration
public class HttpDownloader {

    @Value("${CME.margin.fx.url}")
    private String urlFXString;

    @Value("${CME.margin.metal.url}")
    private String urlMetalString;

    public MarginRatesJSON getMarginRatesFXJSONFile() {
        MarginRatesJSON marginRatesJSON = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            marginRatesJSON = mapper.readValue(new URL(this.urlFXString), MarginRatesJSON.class);
            marginRatesJSON.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return marginRatesJSON;
    }

    public MarginRatesJSON getMarginRatesMetalJSONFile() {
        MarginRatesJSON marginRatesJSON = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            marginRatesJSON = mapper.readValue(new URL(this.urlMetalString), MarginRatesJSON.class);
            marginRatesJSON.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return marginRatesJSON;
    }

    public String getUrlFXString() {
        return urlFXString;
    }

    public void setUrlFXString(String urlFXString) {
        this.urlFXString = urlFXString;
    }

    public String getUrlMetalString() {
        return urlMetalString;
    }

    public void setUrlMetalString(String urlMetalString) {
        this.urlMetalString = urlMetalString;
    }
}
