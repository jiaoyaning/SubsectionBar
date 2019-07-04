package com.jyn.subsectionseekbar;

/**
 * 颜色分段bean类
 */
public class SeekBarBean {
    /**
     * 颜色 RGB值
     */
    private int color;
    /**
     * 起点
     */
    private int origin;
    /**
     * 终点
     */
    private int terminus;

    public SeekBarBean(int color, int origin, int terminus) {
        this.color = color;
        this.origin = origin;
        this.terminus = terminus;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getTerminus() {
        return terminus;
    }

    public void setTerminus(int terminus) {
        this.terminus = terminus;
    }
}
