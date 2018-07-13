package smartBot.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Json {

    protected static final Log logger = LogFactory.getLog(Json.class);

    public static final String DATE_FORMAT = "yyyy-MM-dd kk:mm:ss";

    private static ObjectMapper objectMapper;

    public static <T> T readObjectFromString(String strContent, Class<T> clazz) {
        return readObjectFromString(strContent, null, clazz);
    }

    /**
     * Sample1: readObjectFromString(String strContent, TypeReference<T> type)
     * Sample2: Json.readObjectFromString(strBody, new TypeReference<List<Project>>() {});
     *
     * @param strContent
     * @param type
     * @return
     */
    public static <T> T readObjectFromString(String strContent, TypeReference<T> type) {
        return readObjectFromString(strContent, type, null);
    }

    private static <T> T readObjectFromString(String strContent, TypeReference<T> type, Class<T> clazz) {
        ObjectMapper objectMapper = getObjectMapper();
        T object = null;

        if (!isContentParseble(strContent)) {
            return null;
        }

        try {
            if (type != null) {
                object = objectMapper.readValue(strContent, type);
            } else if (clazz != null) {
                object = objectMapper.readValue(strContent, clazz);
            }
        } catch (IOException e) {
            logger.debug("Cannot parse json.", e);
        }
        return object;
    }


        /**
         * Sample1: readObjectFromFile(File file, TypeReference<T> type)
         * Sample2: Json.readObjectFromFile(file, new TypeReference<List<Project>>() {});
         *
         * @param file
         * @param type
         * @return
         */
    public static <T> T readObjectFromFile(File file, TypeReference<T> type) {
        ObjectMapper objectMapper = getObjectMapper();
        T object = null;

        try {
            object = objectMapper.readValue(file, type);
        } catch (IOException e) {
            logger.warn("Cannot parse file.", e);
        }

        return object;
    }


    public static boolean isContentParseble(String strContent) {

        boolean bGood = true;

        if (strContent == null)
            bGood = false;

        if (bGood)
            if (strContent.startsWith("<html>") || strContent.equals(""))
                bGood = false;

        if (bGood)
            if (strContent.equals("FALSE\n"))
                bGood = false;

        return bGood;
    }

    private static ObjectMapper getObjectMapper() {
        if(objectMapper==null) {
            objectMapper = new ObjectMapper();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            objectMapper.setDateFormat(sdf);
            objectMapper.registerModule(new JodaModule());
        }

        return objectMapper;
    }

    public static String writeObjectToString(Object objectForJson) {
        String strJson = "";
        ObjectMapper objectMapper = getObjectMapper();

        try {
            strJson = objectMapper.writeValueAsString(objectForJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return strJson;
    }

    public static void writeObjectToFile(Object objectForJson, File file) {
        ObjectMapper objectMapper = getObjectMapper();

        try {
            objectMapper.writeValue(file, objectForJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String writeObjectToStringIncludeNonNull(Object objectForJson) {
        String strJson = "";
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            strJson = objectMapper.writeValueAsString(objectForJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return strJson;
    }


    public static <T> T cloneThroughJson(T t) {
        String json = writeObjectToString(t);
        return (T) readObjectFromString(json, t.getClass());
    }

    public static String removeSymbolsForParsingJson(String strJson) {
        strJson = strJson.replace("\\", "");

        //remove this  ->" text "<-
        if (strJson.startsWith("\"")) {
            strJson = strJson.replaceFirst("\"", "");
            strJson = strJson.substring(0, strJson.length() - 1);
        }

        return strJson;
    }

}
