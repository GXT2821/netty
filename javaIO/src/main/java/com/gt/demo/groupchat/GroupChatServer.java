package com.gt.demo.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 群聊系统
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6666;

    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            //serverSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置为非阻塞模式
            listenChannel.configureBlocking(false);
            //将 listChannel 注册到 selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 监听
     */
    public void listen() {

        try {
            //循环处理
            while (true) {
                int select = selector.select(2000);
                if (select > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel accept = listenChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_READ);
                            System.out.println(accept.getRemoteAddress() + "上线");
                        }

                        if (key.isReadable()) {
                            readData(key);
                        }
                        iterator.remove();
                    }


                } else {
                    System.out.println("等待中。。。");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取消息
     *
     * @param selectionKey
     */
    private void readData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {

            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int read = socketChannel.read(buffer);

            if (read > 0) {
                String str = new String(buffer.array());
                System.out.println(str);

                //向其他客户端转发消息
                sendInfoToOtherClients(str, socketChannel);
            }

        } catch (Exception e) {
//            e.printStackTrace();
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线了");
                //取消注册
                selectionKey.cancel();
                //关闭通道
                socketChannel.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    /**
     * 转发消息给其他客户端
     *
     * @param msg
     * @param channel
     */
    private void sendInfoToOtherClients(String msg, SocketChannel channel) throws IOException {

        System.out.println("服务器开始转发消息。。。");

        //遍历所有注册到 selector 的 SocketChannel
        for (SelectionKey selectedKey : selector.keys()) {

            Channel targetChannel = selectedKey.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && channel != targetChannel) {
                SocketChannel socketChannel = (SocketChannel) targetChannel;

                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

                socketChannel.write(buffer);
            }

        }
    }


    public static void main(String[] args) {

        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
