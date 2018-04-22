package dffuture.selfstudy.netty.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf)msg;
        try {
            while(in.isReadable()) {
                System.out.println(in.readByte());
                System.out.flush();
            }
        } finally {
            // 丢弃收到的数据
            ReferenceCountUtil.release(msg);
            // in.release();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 发生异常时，关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
