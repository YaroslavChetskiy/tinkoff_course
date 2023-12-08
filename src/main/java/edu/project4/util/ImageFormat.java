package edu.project4.util;

public enum ImageFormat {
    JPEG(".jpeg"),
    BMP(".bmp"),
    PNG(".png");

    final String extension;

    ImageFormat(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
