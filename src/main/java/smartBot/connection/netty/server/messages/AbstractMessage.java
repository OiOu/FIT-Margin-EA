package smartBot.connection.netty.server.messages;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartBot.connection.netty.server.common.AbstractMessageHeader;
import smartBot.connection.netty.server.common.NettyMessage;
import smartBot.connection.netty.server.exceptions.MessageException;
import smartBot.defines.Constants;
import smartBot.utils.SerializationUtils;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public abstract class AbstractMessage<HDR extends AbstractMessageHeader> implements NettyMessage<HDR> {

    private static final long serialVersionUID = -577180637937320507L;

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private HDR header;

    /** The Constant JAVA_BASE64_MIME_TYPE. */
    public static final String JAVA_BASE64_MIME_TYPE = "java/base64";
    public static final String JAVA_JSON_TYPE = "application/json";

    private String bodyEncoding = JAVA_BASE64_MIME_TYPE;

    private Object body;

    @Override
    public HDR getHeader() {
        if (header == null) header = createNewHeader();
        return header;
    }

    protected abstract HDR createNewHeader();

    @Override
    public final String toStompMessage(boolean validate) throws MessageException {
        if (validate) validate();

        StringBuilder builder = new StringBuilder();

        String body = postHeader();

        String header = getHeader().toMessageHeader();
        if (StringUtils.isNotEmpty(header)) {
            builder.append(header);
        }
        builder.append("\n" + Constants.BOM + "\n");
        builder.append(body);

        //builder.append(Constants.EOM);

        return builder.toString();
    }

    @Override
    public abstract void validate() throws MessageException;

    @SuppressWarnings("unchecked")
    public <O extends Object> O getBody() {
        return (O) body;
    }

    public <O extends Object> void setBody(O body) {
        this.body = body;
    }

    public void setMimeType(String mimeType) {
        getHeader().setContentType(mimeType);
    }

    public void setMimeType(String mimeType, String encoding) {
        mimeType += ";charset=" + encoding;
        setMimeType(mimeType);
    }

    public boolean isText() {
        String value = getHeader().getContentType();
        if (value == null) return true;

        return value.contains("text/");
    }

    protected String postHeader() {
        if (getBody() == null) return null;

        if (isText()) {
            return getBody();
        } else {
            try {
                String encoded = getBodyEncoding().equals(JAVA_BASE64_MIME_TYPE) ? getObjectArrayAsBase64(getBody())
                        : getObjectArrayAsString(getBody());
                getHeader().removeHeader(AbstractMessageHeader.CONTENT_TYPE);
                getHeader().removeHeader(AbstractMessageHeader.CONTENT_LENGTH);
                getHeader().setContentLength(encoded.length());
                getHeader().setContentType(getBodyEncoding());
                return encoded;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Should the {@link AbstractMessage#getBodyEncoding()} return a value
     * other than {@link AbstractMessage#JAVA_BASE64_MIME_TYPE}, this method
     * will be invoked. The default implementation throws a
     * NotImplementedException. Override as necessary.
     *
     * @param body
     *          the body
     * @return the object array as string
     */
    protected String getObjectArrayAsString(Object body) {
        throw new NotImplementedException("Subclass the abstract body message and override getObjectArrayAsString for "
                + getBodyEncoding() + " encoding");
    }

    /**
     * Gets the object array as base64.
     *
     * @param o
     *          the o
     * @return the object array as base64
     * @throws IOException
     *           Signals that an I/O exception has occurred.
     */
    public String getObjectArrayAsBase64(Object o) throws IOException {
        log.debug("Serializing object to a string using Base64 encoding", o);
        return SerializationUtils.serializeBase64(o);
    }

    /**
     * Gets the body encoding. Defaults to
     * {@link AbstractMessage#JAVA_BASE64_MIME_TYPE}.
     *
     * @return the body encoding
     */
    public String getBodyEncoding() {
        return bodyEncoding;
    }

    /**
     * Sets the body encoding.
     *
     * @param bodyEncoding
     *          the new body encoding
     */
    public void setBodyEncoding(String bodyEncoding) {
        this.bodyEncoding = bodyEncoding;
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
        return ToStringBuilder.reflectionToString(this);
    }

}
