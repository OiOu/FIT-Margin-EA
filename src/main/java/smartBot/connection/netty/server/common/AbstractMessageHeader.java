package smartBot.connection.netty.server.common;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

public class AbstractMessageHeader implements NettyMessageHeader {

    private static final long serialVersionUID = 4570408820942642173L;

    /** The Constant CONTENT_LENGTH. */
    public static final String CONTENT_LENGTH = "content-length";

    /** The Constant CONTENT_TYPE. */
    public static final String CONTENT_TYPE = "content-type";

    private Map<String, String> headers = new HashMap<String, String>();

    /**
     * Sets the content length.
     *
     * @param length
     *          the new content length
     */
    public void setContentLength(int length) {
        addHeader(CONTENT_LENGTH, Integer.toString(length));
    }

    /**
     * Gets the content length.
     *
     * @return the content length
     */
    public int getContentLength() {
        String length = getHeaderValue(CONTENT_LENGTH);
        if (length == null) return -1;

        return Integer.parseInt(length);
    }

    /**
     * <i>If a client or a server receives repeated frame header entries, only the
     * first header entry SHOULD be used as the value of header entry. Subsequent
     * values are only used to maintain a history of state changes of the header
     * and MAY be ignored.</i><br>
     * <br>
     * If addHeader is invoked when the key specified is already in use the value
     * is NOT updated. To replace an existing key-value, first use the
     *
     * @param key
     *          the key
     * @param value
     *          the value {@link AbstractMessageHeader#removeHeader(String)}
     *          method.
     */
    @Override
    public void addHeader(String key, String value) {
        if (!hasHeader(key)) headers.put(key, value);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * smartcore.connectors.netty.client.message.common.SmartmapMessageHeader#removeHeader(java.lang.String
     * )
     */
    @Override
    public void removeHeader(String key) {
        headers.remove(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * smartcore.connectors.netty.client.message.common.SmartmapMessageHeader#getHeaderValue(java.lang
     * .String)
     */
    @Override
    public String getHeaderValue(String key) {
        return headers.get(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * smartcore.connectors.netty.client.message.common.SmartmapMessageHeader#hasHeader(java.lang.String)
     */
    @Override
    public boolean hasHeader(String key) {
        return headers.containsKey(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see smartcore.connectors.netty.client.message.common.SmartmapMessageHeader#toMessageHeader()
     */
    @Override
    public final String toMessageHeader() {
        boolean first = true;

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if (!first) builder.append("\n");

            builder.append(entry.getKey());
            builder.append(":");
            builder.append(entry.getValue());

            first = false;
        }

        return builder.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see smartcore.connectors.netty.client.message.common.SmartmapMessageHeader#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() {
        return new HashMap<String, String>(headers);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return toMessageHeader();
    }

    /**
     * Sets the content type.
     *
     * @param mimeType
     *          the new content type
     */
    public void setContentType(String mimeType) {
        addHeader(CONTENT_TYPE, mimeType);
    }

    /**
     * Gets the content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return getHeaderValue(CONTENT_TYPE);
    }


}
