package com.gt.demo.groupchat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 群聊系统客户端
 */
public class GroupChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6666;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            selector = Selector.open();

            socketChannel = socketChannel.open(new InetSocketAddress(HOST, PORT));

            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_READ);

            username = socketChannel.getLocalAddress().toString().substring(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param info
     */
    public void sendInfo(String info) {
        info = username + ": " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取消息
     */
    public void readInfo() {
        try {
            int select = selector.select();
            if (select > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isReadable()) {
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        System.out.println(new String(buffer.array()));
                    }
                    iterator.remove();
                }


            } else {
                System.out.println("没有可用通道 。。。");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

//        GroupChatClient groupChatClient = new GroupChatClient();
        final GroupChatClient groupChatClient = new GroupChatClient();
        //启动一个线程，接收数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    groupChatClient.readInfo();
                    try{
                        Thread.currentThread().sleep(3000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            groupChatClient.sendInfo(s);
        }

    }
}
