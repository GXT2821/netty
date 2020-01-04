package com.gt.demo.server;

import com.gt.demo.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        new EchoServer(port).start();

    }

    public void start() throws InterruptedException {
        final EchoServerHandler echoServerHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //指定所使用的 NIO 传输 Channel
            bootstrap.group(group).channel(NioServerSocketChannel.class)
                    //使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(echoServerHandler);
                        }
                    });
            //异步地绑定服务器，调用 sync() 方法阻塞等待直到绑定完成
            ChannelFuture future = bootstrap.bind().sync();
            //获取 Channel 的 CloseFuture，并且阻塞当前线程直到它完成
            future.channel().closeFuture().sync();
        } finally {
            //关闭 EventLoopGroup,释放所有的资源
            group.shutdownGracefully().sync();
        }

    }
}
