package com.gt.demo.nio.channel;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 1、MappedByteBuffer 可以让文件直接在内存（堆外内存）中修改，操作系统不需要进行拷贝
 *
 */
public class MappedByteBufferDemo {

    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\ideaworkspace\\netty\\netty\\javaIO\\src\\main\\java\\com\\gt\\demo\\nio\\channel\\file01.txt", "rw");

        //获取通道
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 参数1：使用读写的模式
         * 参数2：可以直接修改的起始位置
         * 参数3：映射到内存的大小
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0, (byte)'H');

        randomAccessFile.close();

    }
}
