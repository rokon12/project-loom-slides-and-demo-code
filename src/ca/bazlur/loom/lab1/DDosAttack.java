package ca.bazlur.loom.lab1;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class DDosAttack {
    public static void main(String[] args)
            throws IOException, InterruptedException {
        var sockets = new Socket[20_000];

        int count = 0;
        for (int i = 0; i < sockets.length; i++) {
            sockets[i] = new Socket("localhost", 8080);
            System.out.printf("Connected: %d %s\n", ++count, sockets[i]);
        }
        TimeUnit.HOURS.sleep(1);
    }
}
