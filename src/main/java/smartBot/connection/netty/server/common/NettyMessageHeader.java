package smartBot.connection.netty.server.common;

import java.io.Serializable;
import java.util.Map;

public interface NettyMessageHeader extends Serializable {

    /**
     * To message header.
     *
     * @return the string
     */
    String toMessageHeader();

    /**
     * Adds the header.
     *
     * @param key
     *          the key
     * @param value
     *          the value
     */
    void addHeader(String key, String value);

    /**
     * Checks for header.
     *
     * @param key
     *          the key
     * @return true, if successful
     */
    boolean hasHeader(String key);

    /**
     * Removes the header.
     *
     * @param key
     *          the key
     */
    void removeHeader(String key);

    /**
     * Gets the header value.
     *
     * @param key
     *          the key
     * @return the header value
     */
    String getHeaderValue(String key);

    /**
     * Gets the headers.
     *
     * @return the headers
     */
    Map<String, String> getHeaders();
}
