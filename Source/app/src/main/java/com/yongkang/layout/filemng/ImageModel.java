package com.yongkang.layout.filemng;

/**
 * Created by Yong Kang on 06-May-17.
 */

public class ImageModel {

    String path;
    String size;
    Boolean check;

    public ImageModel(String path, String size, Boolean check) {
        this.path = path;
        this.size = size;
        this.check = check;
    }

    public ImageModel(String path, String size) {
        this.path = path;
        this.size = size;
        this.check = false;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean
    getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
