package smartBot.connection.netty.nio_v1.parser;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartBot.connection.netty.nio_v1.common.AbstractMessageHeader;
import smartBot.connection.netty.nio_v1.common.NettyMessage;
import smartBot.connection.netty.nio_v1.exceptions.MessageException;
import smartBot.connection.netty.nio_v1.messages.AbstractMessage;
import smartBot.connection.netty.nio_v1.messages.Message;
import smartBot.connection.netty.nio_v1.messages.PingMessage;
import smartBot.connection.netty.nio_v1.messages.PongMessage;
import smartBot.utils.SerializationUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MessageParser {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /** The Constant EOM. */
    public static final String EOM = "\000";

    /**
     * Parses the message.
     *
     * @param <MSG>
     *          the generic type
     * @param stompMessage
     *          the stomp message
     * @return the msg
     * @throws MessageException
     *           the unparseable exception
     */
    public <MSG extends NettyMessage<?>> MSG parseMessage(String stompMessage) throws MessageException {
        //check for heartbeat message
        if (Message.PONG.equals(stompMessage)) {
            return (MSG) new PongMessage();
        }
        if (Message.PING.equals(stompMessage)) {
            return (MSG) new PingMessage();
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new StringReader(stompMessage));

            List<String> headers = new ArrayList<>();
            String hdr = reader.readLine();

            while (StringUtils.isNotEmpty(hdr)) {
                headers.add(hdr);
                hdr = reader.readLine();
            }

            String body = reader.readLine();
            body = body == null || body.equals(EOM) ? null : fillBody(body, reader);

            MSG msg = createSmartmapMessage(/*type,*/ headers);

            if (!StringUtils.isEmpty(body) && msg instanceof AbstractMessage<?>) {
                AbstractMessage<?> abm = (AbstractMessage<?>) msg;
                abm.setBody(isText(headers) ? body : convertToObject(body, abm.getHeader().getContentType()));
            }
            return msg;
        } catch (Exception e) {
            throw new MessageException("The message supplied cannot be parsed as a STOMP message", stompMessage, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.warn("Could not close reader", e);
                }
            }
        }
    }

    /**
     * Converts the specified string to an object based upon the specified content
     * type. Only base64 encoding is supported for Java objects.
     *
     * @param body
     *          the body
     * @param contentType
     *          the content type
     * @return the object
     * @throws ClassNotFoundException
     *           the class not found exception
     * @throws IOException
     *           Signals that an I/O exception has occurred.
     */
    protected Object convertToObject(String body, String contentType) throws /*IllegalObjectException,*/
            ClassNotFoundException, IOException {
        if (!AbstractMessage.JAVA_BASE64_MIME_TYPE.equals(contentType)) {
            throw new NotImplementedException(
                    "Subclass this class and override convertToObject to enable conversion using mime type " + contentType);
        }

        Object o = SerializationUtils.deserializeBase64(body);

        return o;
    }

    /**
     * Checks if is text.
     *
     * @param headers
     *          the headers
     * @return true, if is text
     */
    protected boolean isText(List<String> headers) {
        boolean text = false;
        boolean content = false;
        for (String hdr : headers) {
            if (hdr.contains(AbstractMessageHeader.CONTENT_TYPE)) {
                content = true;
                text = hdr.contains("text/");
            }
        }

        return !content || (content && text);
    }

    /**
     * Creates the stampy message.
     *
     * @param <MSG>
     *          the generic type
//     * @param type
//     *          the type
     * @param headers
     *          the headers
     * @return the msg
     * @throws MessageException
     *           the unparseable exception
     */
    @SuppressWarnings("unchecked")
    protected <MSG extends NettyMessage<?>> MSG createSmartmapMessage(/*StompMessageType type, */List<String> headers)
            throws MessageException {

        MSG message = null;

        message = (MSG) new Message();

        message.getHeader();

        addHeaders(message, headers);

        return message;
    }

    private <MSG extends NettyMessage<?>> void addHeaders(MSG message, List<String> headers) throws MessageException {
        for (String header : headers) {
            StringTokenizer st = new StringTokenizer(header, ":");

            if (st.countTokens() < 2) {
                log.error("Cannot parse STOMP header {}", header);
                throw new MessageException("Cannot parse STOMP header " + header);
            }

            String key = st.nextToken();
            String value = header.substring(key.length() + 1);

            message.getHeader().addHeader(key, value);
        }
    }

    /**
     * Fills the body of the STOMP message.
     *
     * @param body
     *          the body
     * @param reader
     *          the reader
     * @return the string
     * @throws IOException
     *           Signals that an I/O exception has occurred.
     */
    protected String fillBody(String body, BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder(trimEOM(body));

        String s = reader.readLine();

        while (s != null) {
            builder.append(trimEOM(s));
            s = reader.readLine();
        }

        return builder.toString();
    }

    /**
     * Trims the terminating byte.
     *
     * @param s
     *          the s
     * @return the string
     */
    protected String trimEOM(String s) {
        String trimmed = s;
        if (s.contains(EOM)) {
            int idx = s.indexOf(EOM);
            trimmed = s.substring(0, idx);
        }

        return trimmed;
    }
}
