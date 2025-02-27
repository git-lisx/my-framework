package cn.xian.tomcat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;
import java.util.Iterator;

public class SimpleSelectorExample {
    public static void main(String[] args) throws IOException {
        // 创建Selector
        Selector selector = Selector.open();
        
        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);
        
        // 注册到Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        System.out.println("Server started on port 8080");

        while (true) {
            // 等待就绪的通道
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isAcceptable()) {
                    // 接受连接
                    SocketChannel clientChannel = serverSocketChannel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("接受连接，Accepted connection from " + clientChannel.getRemoteAddress());
                } else if (key.isReadable()) {
                    // 读取数据
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    int bytesRead = clientChannel.read(buffer);

                    if (bytesRead == -1) {
                        clientChannel.close();
                        System.out.println("客户端关闭连接，Connection closed by client");
                    } else {
                        String request = new String(buffer.array()).trim();
                        System.out.println("接收数据，-开始 ");
                        System.out.println( request);
                        System.out.println("接收数据，-结束" );

                        // 回应客户端
                        ByteBuffer responseBuffer = ByteBuffer.wrap("Hello, Client!".getBytes());
                        clientChannel.write(responseBuffer);
                    }
                }
            }
        }
    }
}
