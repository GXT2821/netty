package com.gt.demo.handler;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @Desciption 对于每个传入的消息都要调用此方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        //将消息记录到控制台
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        //将接受到的消息写给发送者，而不冲刷出站消息
        ctx.write(in);
    }

    /**
     * @Description 通知ChannelInboundHandler最后一次对ChannelRead()的调用是当前批量读取中的最后一条消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将未决消息冲刷到远程节点，并且关闭改Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * @Description 在读取操作期间，有异常抛出时会调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常栈
        cause.printStackTrace();
        //关闭改Channel
        ctx.close();
    }
}
