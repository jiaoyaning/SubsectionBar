package com.jyn.subsectionprogressbar;

public class ProgressBarBean {
    /**
     * 起点
     */
    private int origin;
    /**
     * 终点
     */
    private int terminus;
    /**
     * 颜色 RGB值
     */
    private int color;


    public ProgressBarBean(int origin, int terminus, int color) {
        this.origin = origin;
        this.terminus = terminus;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
