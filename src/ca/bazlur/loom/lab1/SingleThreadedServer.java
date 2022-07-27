package ca.bazlur.loom.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SingleThreadedServer {
    public static void main(String[] args) throws IOException {
        var serverSocket = new ServerSocket(8080);
        while (true) {
            var socket = serverSocket.accept();
            handle(socket);
        }
    }

    private static void handle(Socket socket) throws IOException {
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
        }
    }

    private static String processLine(String inputLine) {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())) + "<<server>>: " + inputLine.toUpperCase();
    }
}
