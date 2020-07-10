package com.gt.demo.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {

    public static void main(String[] args) throws Exception {

        //创建文件输入流
        File file = new File("D:\\ideaworkspace\\netty\\netty\\javaIO\\src\\main\\java\\com\\gt\\demo\\nio\\channel\\file02.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        //通过 fileInputStream 获取对应的 FileChannel
        FileChannel channel01 = fileInputStream.getChannel();

        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\ideaworkspace\\netty\\netty\\javaIO\\src\\main\\java\\com\\gt\\demo\\nio\\channel\\file03.txt");

        //通过 fileOutputStream  获取对应的 FileChannel
        FileChannel channel02 = fileOutputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while(true){

            //重要操作！！！ 清空Buffer
            byteBuffer.clear();

            int read = channel01.read(byteBuffer);
            if(read == -1){
                break;
            }

            byteBuffer.flip();
            channel02.write(byteBuffer);
        }
        fileInputStream.close();
        fileInputStream.close();

    }
}
