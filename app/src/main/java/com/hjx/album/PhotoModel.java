package com.hjx.album;

public class PhotoModel {
    private int mId;

    private String mPath;

    private int mWidth;

    private int mHeight;

    private PhotoModel(Builder builder) {
        mId = builder.id;
        mPath = builder.path;
        mWidth = builder.width;
        mHeight = builder.height;
    }

    public static class Builder {
        private int id;

        private String path;

        private int width;

        private int height;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public PhotoModel build() {
            return new PhotoModel(this);
        }
    }
}
