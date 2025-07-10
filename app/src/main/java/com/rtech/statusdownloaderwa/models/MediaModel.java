package com.rtech.statusdownloaderwa.models;
public class MediaModel {
    public String path;
    public boolean isVideo;
   public String type;
    public MediaModel(String path,Boolean isVideo,String type) {
        this.path=path;
        this.isVideo=isVideo;
        this.type=type;
    }
}
