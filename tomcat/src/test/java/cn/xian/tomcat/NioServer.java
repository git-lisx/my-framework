package cn.xian.tomcat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {
        // 创建 ServerSocketChannel 并绑定端口
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(8080));
        serverChannel.configureBlocking(false); // 设置为非阻塞模式

        // 创建 Selector
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT); // 注册接收连接事件

        System.out.println("服务器已启动，监听端口 8080...");

        while (true) {
            // 阻塞，等待事件发生
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();

            // 使用增强的 for 循环遍历已选择的键
            for (SelectionKey key : selectedKeys) {
                if (key.isAcceptable()) {
                    // 处理新的连接
                    SocketChannel clientChannel = serverChannel.accept();
                    clientChannel.configureBlocking(false); // 设置为非阻塞模式
                    clientChannel.register(selector, SelectionKey.OP_READ); // 注册读事件
                    System.out.println("已接受连接来自: " + clientChannel.getRemoteAddress());
                } else if (key.isReadable()) {
                    // 处理读事件
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    int bytesRead = clientChannel.read(buffer);

                    System.out.println("读取到的bytesRead= " + bytesRead);
                    if (bytesRead == -1) {
                        System.out.println("读取到的bytesRead为-1");
                        // 客户端关闭连接
//                        System.out.println("客户端关闭连接: " + clientChannel.getRemoteAddress());
//                        clientChannel.close();
                    } else if (bytesRead > 0) {
                        // 输出接收到的数据
                        buffer.flip(); // 切换到读模式
                        byte[] data = new byte[buffer.remaining()];
                        buffer.get(data);
                        String request = new String(data).trim();
                        System.out.println("接收到: " + request);

                        // 处理 HTTP 请求并发送响应
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "Content-Length: 13\r\n" +
                                "\r\n" +
                                "Hello, world!";
                        ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
                        clientChannel.write(responseBuffer);
                    }
                    System.out.println("客户端关闭连接: " + clientChannel.getRemoteAddress());
                    clientChannel.close();
                    System.out.println();
                }
            }

            // 清空已处理的键
            selectedKeys.clear();
        }
    }
}
