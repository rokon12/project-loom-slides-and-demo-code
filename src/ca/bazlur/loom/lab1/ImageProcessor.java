package ca.bazlur.loom.lab1;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

class NamedThreadFactory implements ThreadFactory {
    private String name;
    private int count;

    public NamedThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        count++;
        ThreadFactory factory = Executors.defaultThreadFactory();
        Thread t = factory.newThread(r);
        t.setName(name + "-" + count);
        System.out.println("New thread " + t);
        return t;
    }
}

public class ImageProcessor {
    public static final int NUMBER_TO_SHOW = 1000;
    public static final int DELAY = 0; // ms between requests
    private final CountDownLatch latch = new CountDownLatch(NUMBER_TO_SHOW);
    private Executor executor1 = Executors.newCachedThreadPool(new NamedThreadFactory("executor1"));
    private ExecutorService executor2 = Executors.newFixedThreadPool(100, new NamedThreadFactory("executor2"));
    public boolean printMessage = true;
    public boolean saveFile = true;
    public boolean showInFrame = true;
    public Path imageDir = Paths.get("/tmp/images");

    private final HttpClient client = HttpClient.newBuilder()
            .executor(executor1)
            .followRedirects(HttpClient.Redirect.NEVER)
            .build();

    public <T> CompletableFuture<T> getAsync(String url, HttpResponse.BodyHandler<T> responseBodyHandler) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .build();
        return client.sendAsync(request, responseBodyHandler)
                .thenApply(HttpResponse::body);
    }

    public CompletableFuture<ImageInfo> findImageInfo(LocalDate date, ImageInfo info) {
        return getAsync(info.getUrlForDate(date), HttpResponse.BodyHandlers.ofString())
                .thenApply(info::findImage);
    }

    public CompletableFuture<ImageInfo> findImageData(ImageInfo info) {
        return getAsync(info.getImagePath(), HttpResponse.BodyHandlers.ofByteArray())
                .thenApply(info::setImageData);
    }

    public void load(LocalDate date, ImageInfo info) {
        findImageInfo(date, info)
                .thenCompose(this::findImageData)
                .thenAccept(this::process);
    }

    public void loadAll() {
        long time = System.nanoTime();
        try {
            LocalDate date = LocalDate.now();
            for (int i = 0; i < NUMBER_TO_SHOW; i++) {
                ImageInfo info = new DilbertImageInfo();
                // ImageInfo info = new WikimediaImageInfo();
                info.setDate(date.toString());
                System.out.println("Loading " + date);
                load(date, info);
                if (DELAY > 0) Thread.sleep(DELAY);
                date = date.minusDays(1);
            }
            latch.await();
            executor2.shutdown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted");
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }

    public void process(ImageInfo info) {
        latch.countDown();
        if (printMessage) {
            System.out.println("process called by " + Thread.currentThread() + ", date: " + info.getDate());
        }
        if (showInFrame) {
            EventQueue.invokeLater(() -> {
                JFrame frame = new JFrame(info.getDate());
                frame.add(new JLabel(new ImageIcon(info.getImageData())));
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            });
        }
        if (saveFile)
            try {
                Files.createDirectories(imageDir);
                Files.write(imageDir.resolve(info.getDate() + ".jpg"), info.getImageData());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

    public static void main(String... args) {
        ImageProcessor processor = new ImageProcessor();
        processor.loadAll();
    }
}