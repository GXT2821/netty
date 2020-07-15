package com.gt.demo.nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) throws Exception {

        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();

        //设置非阻塞模式
        socketChannel.configureBlocking(false);

        //提供服务器端的ip port
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        //连接服务器
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
            }
        }

        //连接成功，就发送数据
        String str = "Hello NIO";
        ByteBuffer wrap = ByteBuffer.wrap(str.getBytes());
        //将数据写入 channel
        socketChannel.write(wrap);

        System.in.read();

    }
}
