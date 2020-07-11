## BIO 编程的简单流程
 * 1、服务器端启动一个serverSocket 
 * 2、客户端启动 Socket对服务器进行通信，默认情况下服务器端需要对每个客户端建立一个线程与之通信  
 * 3、客户端发出请求后，先咨询服务器是否有线程响应，如果没有则会等待，或被拒绝；如果有响应客户端线程会等待请求结束后，在继续执行  
 
 
## NIO
* NIO 有三大核心部分：Channel（通道）、Buffer（缓冲区）、Selector（选择器）

## Buffer
* Buffer类定义了所有缓冲区都具有的四个属性来提供关于其所包含的数据元素的信息  
* 1、Capacity 容量，即可以容纳的最大数据量；在缓冲区创建的时候设定且不能改变  
* 2、Limit 表示缓冲区的当前终点，不能对缓冲区超过极限的位置进行读写操作。且极限位置可以修改  
* 3、Position 位置，下一个要被读或写的元素的索引，每次读写缓冲区数据时都会改变该值，为下一次读写准备  
* 4、Mark 标记  

**关于Buffer 和 Channel 的注意事项和细节**  
* 1、ByteBuffer支持类型化的 put 和 get， put 放入的是什么数据类型，get就应该使用相应的数据类型取出，否则有可能有 BufferUnderflowException 异常
* 2、可以将一个普通的 Buffer 转成只读 Buffer  
* 3、NIO 提供了 MappedByteBuffer，可以让文件直接在内存（堆外内存）中进行修改，而如何同步到文件中由 NIO 来完成  
* 4、NIO 支持通过多个 Buffer 完成读写操作，即 Scattering 和 Gatering  


