package com.gt.demo.nio.channel;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 输出流，将文件中的数据读取出来
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws Exception {
        //创建文件输入流
        File file = new File("D:\\ideaworkspace\\netty\\netty\\javaIO\\src\\main\\java\\com\\gt\\demo\\nio\\channel\\file01.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        //通过 fileInputStream 获取对应的 FileChannel
        FileChannel channel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将通道的数据读取到缓冲区
        channel.read(byteBuffer);

        //将字节转换为字符串
        System.out.println(new String(byteBuffer.array()));

        fileInputStream.close();
    }
}
