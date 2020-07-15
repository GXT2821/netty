package com.gt.demo.nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class NIOServer {

    public static void main(String[] args) throws Exception {

        //创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个 selector 对象
        Selector selector = Selector.open();

        //绑定一个端口，在服务端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把 serverSocketChannel 注册到 selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true) {

            if (selector.select(1000) == 0) { //没有事件发生
                System.out.println("服务器等待1s，无连接");
                continue;
            }
            //如果有事件发生，获取相关的 selectionKey 集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //遍历 selectionKeys
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                //获取到 selectionKey
                SelectionKey selectionKey = iterator.next();
                //根据不同的key,对应不同的事件做相应的处理
                if (selectionKey.isAcceptable()) { //如果是 OP_ACCEPT，有新的连接
                    //该客户端生成一个 SocketChannel
                    SocketChannel accept = serverSocketChannel.accept();
                    //设置为非阻塞
                    accept.configureBlocking(false);
                    //将socketChannel 注册到 selector
                    System.out.println("接收到连接请求");
                    accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));


                }
                if(selectionKey.isReadable()){
                    //通过key 反向获取对应 channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    
                    //获取key关联的buffer
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();

                    channel.read(buffer);
                    System.out.println("客户端发送的数据为" + new String(buffer.array()));
                }
                //手动从集合中移除当前的 key, 防止重复操作
                iterator.remove();
            }
        }
    }
}
