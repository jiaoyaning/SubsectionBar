package com.jyn.subsectionbar;

public class VideoBean {

    /**
     * 视频播放地址
     */
    String url;

    /**
     * 持续时间
     */
    long duration;

    /**
     * 是否是标记时间
     */
    boolean isMark;

    public VideoBean(String url, long duration, boolean isMark) {
        this.url = url;
        this.duration = duration;
        this.isMark = isMark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isMark() {
        return isMark;
    }

    public void setMark(boolean mark) {
        isMark = mark;
    }
}
