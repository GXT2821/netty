package com.gt.demo.BIO;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

    public static void main(String[] args) {
        //1、创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            //2、创建服务端
            ServerSocket socket = new ServerSocket(6666);
            System.out.println("服务启动成功");

            while(true){
                //监听，等待客户端连接

                final Socket accept = socket.accept();
                System.out.println("连接到一个客户端");
                //创建一个线程，与之通信
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        //获取客户端输入流
                        try {
                            byte[] bytes = new byte[1024];
                            InputStream inputStream = accept.getInputStream();
                            while(true){
                                int read = inputStream.read(bytes);
                                if(read != -1){
                                    System.out.println(new String(bytes, 0, read));
                                }else{
                                    break;
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                accept.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
