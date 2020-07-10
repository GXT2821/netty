package com.gt.demo.nio.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 输入流， 将字符数据写入文件中
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws Exception {

        String str = "Hello FileChannel";

        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\ideaworkspace\\netty\\netty\\javaIO\\src\\main\\java\\com\\gt\\demo\\nio\\channel\\file01.txt");

        //通过 fileOutputStream  获取对应的 FileChannel
        FileChannel channel = fileOutputStream.getChannel();

        //创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将 str 值 放入 byteBuffer
        byteBuffer.put(str.getBytes());

        byteBuffer.flip();

        //将 buffer 数据写入到 channel
        channel.write(byteBuffer);

        //关闭IO流
        fileOutputStream.close();
    }
}
