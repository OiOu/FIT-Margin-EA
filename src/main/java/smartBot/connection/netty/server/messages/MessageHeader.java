package smartBot.connection.netty.server.messages;

import smartBot.connection.netty.server.common.AbstractMessageHeader;

public class MessageHeader extends AbstractMessageHeader {

    private static final long serialVersionUID = -1715758376656092863L;

    /** The Constant COMMAND. */
    public static final String COMMAND = "command";

    /** The Constant DESTINATION. */
    public static final String DESTINATION = "destination";

    /** The Constant TIMESTAMP. */
    public static final String TIMESTAMP = "timestamp";

    /**
     * Sets the command.
     *
     * @param command
     *          the new command
     */
    public void setCommand(String command) {
        addHeader(COMMAND, command);
    }

    public String getCommand() {
        return getHeaderValue(COMMAND);
    }

    /**
     * Sets the destination.
     *
     * @param destination
     *          the new destination
     */
    public void setDestination(String destination) {
        addHeader(DESTINATION, destination);
    }

    /**
     * Gets the destination.
     *
     * @return the destination
     */
    public String getDestination() {
        return getHeaderValue(DESTINATION);
    }

    /**
     * Sets the timestamp.
     *
     * @param timestamp
     *          the new destination
     */
    public void setTimestamp(String timestamp) {
        addHeader(TIMESTAMP, timestamp);
    }

    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    public String getTimestamp() {
        return getHeaderValue(TIMESTAMP);
    }

}
