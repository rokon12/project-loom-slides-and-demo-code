package ca.bazlur.loom.lab1;

import java.time.LocalDate;

public abstract class ImageInfo {
    private String date;
    protected String imagePath;
    private byte[] imageData;

    public abstract ImageInfo findImage(String body);

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ImageInfo setImageData(byte[] imageData) {
        this.imageData = imageData;
        return this;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public abstract String getUrlForDate(LocalDate date);
}
