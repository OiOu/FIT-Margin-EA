package smartBot.connection.netty.nio_v1.common;

import smartBot.connection.netty.nio_v1.exceptions.MessageException;

import java.io.IOException;
import java.io.Serializable;

public interface NettyMessage<HDR extends NettyMessageHeader> extends Serializable {

    /**
     * Gets the header.
     *
     * @return the header
     */
    HDR getHeader();

    /**
     * Returns a STOMP-string representation of a {@link NettyMessage}.
     *
     * @param validate
     *          if true message validation is executed
     * @return the string
     */
    String toStompMessage(boolean validate) throws MessageException;

//    /**
//     * Gets the message type.
//     *
//     * @return the message type
//     */
//    StompMessageType getMessageType();

    /**
     * Performs validation on the message.
     */
    void validate() throws IOException, MessageException;

}
