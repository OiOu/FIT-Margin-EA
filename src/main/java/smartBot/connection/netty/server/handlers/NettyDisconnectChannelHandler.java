package smartBot.connection.netty.server.handlers;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class NettyDisconnectChannelHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(NettyDisconnectChannelHandler.class);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        String strLocalAddress = ctx.channel().remoteAddress().toString();
        strLocalAddress = strLocalAddress.replace("/", "" );

        logger.info("Channel closed: id=" + strLocalAddress + " for currency: " /* asset.getFactoryId()*/);

        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        // cause.printStackTrace(); THIS WAS SHOWING THE EXCEPTION
        ctx.close();
    }
}
