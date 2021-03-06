package com.gt.demo.nio.buffer;

import java.nio.IntBuffer;

public class BasicBuffer {

    public static void main(String[] args) {

        IntBuffer intBuffer = IntBuffer.allocate(5);

        intBuffer.put(1);
        intBuffer.put(2);
        intBuffer.put(3);
        intBuffer.put(4);
        intBuffer.put(5);



        intBuffer.flip();

        intBuffer.put(11);
        intBuffer.put(21);
        intBuffer.put(31);
        intBuffer.put(41);
        intBuffer.put(51);

        intBuffer.flip();

        for (int i = 0; i < intBuffer.capacity(); i++){
            System.out.println(intBuffer.get());
        }
    }
}
