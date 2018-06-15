package smartBot.connection.netty.server.exceptions;

public class MessageException extends Exception {
    private static final long serialVersionUID = -5077635019985663697L;

    private String stompMessage;

    /**
     * Instantiates a new unparseable exception.
     *
     * @param message
     *          the message
     * @param stompMessage
     *          the stomp message
     * @param cause
     *          the cause
     */
    public MessageException(String message, String stompMessage, Throwable cause) {
        super(message, cause);
        this.stompMessage = stompMessage;
    }

    /**
     * Instantiates a new unparseable exception.
     *
     * @param message
     *          the message
     */
    public MessageException(String message) {
        super(message);
    }

    /**
     * Gets the stomp message.
     *
     * @return the stomp message
     */
    public String getStompMessage() {
        return stompMessage;
    }
}
