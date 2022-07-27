package ca.bazlur.loom.lab1;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;


public class ImageDownloader {
    private static String IMAGE_DESTINATION_FOLDER = "images";

    public static void main(String[] args) throws IOException {
        var ulrs = IntStream.range(0, 1_000)
                .mapToObj(index -> "https://picsum.photos/500/300?random=" + index)
                .toList();


        downloadWithThreadPool(ulrs);
        downloadWithVirtualThread(ulrs);
    }

    //Total time taken = 4378 ms 1000
    // Total time taken = 3959 ms with 100 images
    public static void downloadWithVirtualThread(List<String> urls) {
        System.out.println("starting downloading " + urls.size() + " with VirtualThread");

        cleanDestination();
        Instant start;


        try (ExecutorService threadPool = Executors.newVirtualThreadPerTaskExecutor()) {
            start = Instant.now();
            var futures = new ArrayList<Future<?>>();
            for (String url : urls) {
                Future<?> future = threadPool.submit(() -> saveImage(url));
                futures.add(future);
            }

            for (var future : futures) {
                future.get();
            }

            long total = Duration.between(start, Instant.now()).toMillis();
            System.out.println("Total time taken = " + total + " ms");

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    //Total time taken = 6037 ms for 100 images 10 threads
    //Total time taken = 21554 ms  10 threads
    //Total time taken = 27062 ms 100 Threads
    public static void downloadWithThreadPool(List<String> urls) {
        System.out.println("starting downloading " + urls.size() + " with FixedThreadPool");
        cleanDestination();
        Instant start;

        try (ExecutorService threadPool = Executors.newFixedThreadPool(100)) {
            start = Instant.now();
            var futures = new ArrayList<Future<?>>();
            for (String url : urls) {
                Future<?> future = threadPool.submit(() -> saveImage(url));
                futures.add(future);
            }

            for (var future : futures) {
                future.get();
            }

            long total = Duration.between(start, Instant.now()).toMillis();
            System.out.println("Total time taken = " + total + " ms");

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static void cleanDestination() {
        if (Files.exists(Path.of(IMAGE_DESTINATION_FOLDER))) {
            try {
                Files.walk(Path.of(IMAGE_DESTINATION_FOLDER))
                        .filter(p -> !p.endsWith(IMAGE_DESTINATION_FOLDER))
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (Throwable e) {
                                throw new RuntimeException(e);
                            }
                        });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void saveImage(String url) {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(IMAGE_DESTINATION_FOLDER + "/" + UUID.randomUUID() + ".jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
