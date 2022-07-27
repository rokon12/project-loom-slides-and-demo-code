package ca.bazlur.loom.lab1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ThreadedNioServer {

    static Map<SocketChannel, Queue<ByteBuffer>> pendingData = new HashMap<>();

    public static void main(String[] args) throws IOException {
        var port = 8080;

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress(port));
        socketChannel.configureBlocking(false);

        var selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            var select = selector.select();
            if (select == 0) continue;
            var selectionKeys = selector.selectedKeys();
            var iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                }
                iterator.remove();
            }
        }
    }

    private static void accept(SelectionKey selectionKey) throws IOException {
        var channel = (ServerSocketChannel) selectionKey.channel();
        var sc = channel.accept(); //never null
        System.out.println("Connected: " + sc);
        pendingData.put(sc, new ConcurrentLinkedDeque<>());
        sc.configureBlocking(false);
        sc.register(selectionKey.selector(), SelectionKey.OP_READ);
    }

    private static void read(SelectionKey selectionKey) throws IOException {
        var channel = (SocketChannel) selectionKey.channel();
        var byteBuffer = ByteBuffer.allocateDirect(80);
        var read = channel.read(byteBuffer);
        if (read == -1) {
            channel.close();
            pendingData.remove(channel);
            return;
        }

        if (read > 0) {
            processBuffer(byteBuffer);
            pendingData.get(channel).add(byteBuffer);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private static void write(SelectionKey selectionKey) throws IOException {
        var channel = (SocketChannel) selectionKey.channel();
        var queue = pendingData.getOrDefault(channel, new ArrayDeque<>());
        while (!queue.isEmpty()) {
            var buff = queue.peek();
            if (buff.position() != buff.limit()) {
                buff.flip();
            }
            var written = channel.write(buff);
            if (written == -1) {
                channel.close();
                pendingData.remove(channel);
                return;
            }
            if (buff.hasRemaining()) return;
            queue.remove();
        }
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

    private static void processBuffer(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        StringBuilder line = new StringBuilder();
        line.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())))
                .append("<<server>>: ");

        for (int b = 0; b < byteBuffer.limit(); b++) {
            var b1 = byteBuffer.get(b);
            line.append(Character.toUpperCase((char) b1));
        }
        var s = line.toString();
        var bytes = s.getBytes();
        byteBuffer.clear();
        byteBuffer.put(bytes);
        System.out.println("Executing from: " + Thread.currentThread());
    }
}
