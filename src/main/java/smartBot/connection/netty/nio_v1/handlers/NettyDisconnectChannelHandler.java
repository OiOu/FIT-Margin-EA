package smartBot.connection.netty.nio_v1.handlers;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartBot.bussines.service.CurrencyService;

@ChannelHandler.Sharable
public class NettyDisconnectChannelHandler extends ChannelInboundHandlerAdapter {

    private static Log logger = LogFactory.getLog(NettyDisconnectChannelHandler.class);

    private CurrencyService currencyService;

    public NettyDisconnectChannelHandler(
            CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        String strLocalAddress = ctx.channel().remoteAddress().toString();
        strLocalAddress = strLocalAddress.replace("/", "" );

        logger.info("Channel closed: id=" + strLocalAddress + " for currency: " /* asset.getFactoryId()*/);


        super.channelInactive(ctx);
    }
}
