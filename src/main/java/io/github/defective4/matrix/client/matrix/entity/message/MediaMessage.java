package io.github.defective4.matrix.client.matrix.entity.message;

import com.google.gson.annotations.SerializedName;

public class MediaMessage extends Message {

    public static class MediaInfo {
        @SerializedName("h")
        private final int height;
        private final String mimetype;
        private final long size;
        @SerializedName("thumbnail_url")
        private final String thumbnailURL;
        @SerializedName("w")
        private final int width;

        public MediaInfo(int height, int width, String mimetype, long size, String thumbnailURL) {
            this.height = height;
            this.width = width;
            this.mimetype = mimetype;
            this.size = size;
            this.thumbnailURL = thumbnailURL;
        }

        public int getHeight() {
            return height;
        }

        public String getMimetype() {
            return mimetype;
        }

        public long getSize() {
            return size;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }

        public int getWidth() {
            return width;
        }

    }

    public static final String TYPE_IMAGE = "m.image";

    public static final String TYPE_VIDEO = "m.video";

    private final String filename;
    private final MediaInfo info;
    private final String url;

    public MediaMessage(String msgtype, String url, MediaInfo info, String filename) {
        super(msgtype);
        this.filename = filename;
        this.url = url;
        this.info = info;
    }

    public String getFilename() {
        return filename;
    }

    public MediaInfo getInfo() {
        return info;
    }

    public String getUrl() {
        return url;
    }

}
