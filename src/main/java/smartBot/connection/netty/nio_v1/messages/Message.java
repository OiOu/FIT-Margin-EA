package smartBot.connection.netty.nio_v1.messages;

import org.apache.commons.lang.StringUtils;
import smartBot.connection.netty.nio_v1.exceptions.MessageException;

public class Message extends AbstractMessage<MessageHeader> {

    private static final long serialVersionUID = 5351072786156865214L;

    public static final String PING = "PING";
    public static final String PONG = "PONG";

    /**
     * Instantiates a new message message.
     *
     * @param destination
     *          the destination
     */
    public Message(String destination) {
        getHeader().setDestination(destination);
   }

    public Message() {}

    /*
     * (non-Javadoc)
     *
     * @see {@link AbstractMessage.createNewHeader()}
     */
    @Override
    protected MessageHeader createNewHeader() {
        return new MessageHeader();
    }

    /*
     * (non-Javadoc)
     *
     * @see {@link AbstractMessage.validate()}
     */
    @Override
    public void validate() throws MessageException {
        if (StringUtils.isEmpty(getHeader().getDestination())) {
            throw new MessageException(MessageHeader.DESTINATION + " is required");
        }
    }
}
