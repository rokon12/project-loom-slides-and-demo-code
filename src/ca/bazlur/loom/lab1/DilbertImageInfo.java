package ca.bazlur.loom.lab1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DilbertImageInfo extends ImageInfo {
    @Override
    public ImageInfo findImage(String body) {
        this.imagePath = findProperty(body, "image");
        return this;
    }

    @Override
    public String getUrlForDate(LocalDate date) {
        return "https://dilbert.com/strip/" + DateTimeFormatter.ISO_DATE.format(date);
    }

    private static String findProperty(String body, String filter) {
        String search = "meta name=\"twitter:" + filter + "\" content=\"";
        return body.lines().filter(line -> line.contains(search))
                .findFirst()
                .map(line -> line.replaceAll(".*" + search, ""))
                .map(line -> line.replaceAll("\".*", ""))
                .orElseThrow(() -> new IllegalStateException("Could not find \"" + filter + "\""));
    }
}
