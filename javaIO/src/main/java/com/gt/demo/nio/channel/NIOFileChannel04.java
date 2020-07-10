package com.gt.demo.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 实现文件的拷贝
 */
public class NIOFileChannel04 {

    public static void main(String[] args) throws Exception{
        //创建文件输入流
        File file = new File("D:\\ideaworkspace\\netty\\netty\\javaIO\\src\\main\\java\\com\\gt\\demo\\nio\\channel\\file02.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        //通过 fileInputStream 获取对应的 FileChannel
        FileChannel channel01 = fileInputStream.getChannel();

        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\ideaworkspace\\netty\\netty\\javaIO\\src\\main\\java\\com\\gt\\demo\\nio\\channel\\file04.txt");

        //通过 fileOutputStream  获取对应的 FileChannel
        FileChannel channel02 = fileOutputStream.getChannel();

        //使用 transferfrom 完成拷贝
        channel02.transferFrom(channel01, 0, channel01.size());

        channel01.close();
        channel02.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
