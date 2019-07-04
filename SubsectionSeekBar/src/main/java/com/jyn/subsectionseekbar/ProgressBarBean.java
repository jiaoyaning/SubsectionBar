package com.jyn.subsectionseekbar;

/**
 * 颜色分段bean类
 */
public class ProgressBarBean {
    /**
     * 颜色 RGB值
     */
    private String color;
    /**
     * 起点
     */
    private int origin;
    /**
     * 持续
     */
    private int extent;

    public ProgressBarBean(String color, int origin, int extent) {
        this.color = color;
        this.origin = origin;
        this.extent = extent;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getExtent() {
        return extent;
    }

    public void setExtent(int extent) {
        this.extent = extent;
    }
}
