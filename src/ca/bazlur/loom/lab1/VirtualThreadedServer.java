package ca.bazlur.loom.lab1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualThreadedServer {
    private static AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) throws IOException {
        var serverSocket = new ServerSocket(8080);

        try (var executors = Executors.newVirtualThreadPerTaskExecutor()) {
            while (true) {
                var socket = serverSocket.accept();
                executors.submit(() -> {
                    handle(socket);
                });
            }
        }
    }

    private static void handle(Socket socket) {
        int count = counter.incrementAndGet();
        System.out.printf("Connected %d, %s \n:", count, socket.toString());
        try (socket;
             var printWriter = new PrintWriter(socket.getOutputStream(), true);
             var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            printWriter.println(processLine("Connected, start typing..."));
            String inputLine, outputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {
                outputLine = processLine(inputLine);
                printWriter.println(outputLine);

                if (inputLine.startsWith("quit") || inputLine.startsWith("bye")) {
                    break;
                }

                if (inputLine.contains("exception")) throw new RuntimeException("simulated exception");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String processLine(String inputLine) {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())) + "<<server>>: " + inputLine.toUpperCase();
    }
}
