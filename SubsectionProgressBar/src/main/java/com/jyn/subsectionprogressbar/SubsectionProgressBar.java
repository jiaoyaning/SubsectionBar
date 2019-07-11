package com.jyn.subsectionprogressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SubsectionProgressBar extends View {
    private Context mContext;

    /**
     * 默认总进度
     */
    private int mMax = 1000;
    /**
     * 当前进度 相对max值而言
     */
    private int mProgress = 0;

    /**
     * bar 背景色
     */
    private int backgroundColor;

    /**
     * bar 进度条颜色
     */
    private int progressColor;

    /**
     * 按钮图片ID
     */
    private int seekBarResId;

    /**
     * 分段背景色值
     */
    private List<ProgressBarBean> progressBarBeans = new ArrayList<>();

    /**
     * 进度条高度比例
     * 进度条高度 = 控件高度/ratio
     */
    private int ratio;

    /**
     * 当前进度百分比
     */
    private float percent;

    /**
     * 背景色画笔
     */
    private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * SeekBar按钮的位置
     */
    private int lineTop, lineBottom, lineLeft, lineRight;
    /**
     * 圆角
     */
    private int lineCorners;

    /**
     * Bar 的宽度
     */
    private int lineWidth;

    /**
     * 总体背景色
     */
    private RectF line = new RectF();

    /**
     * 已走完进度背景色
     */
    private RectF progressLine = new RectF();

    public SubsectionProgressBar(Context context) {
        super(context);
    }

    @SuppressLint("Recycle")
    public SubsectionProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.SubsectionProgressBar);
        mMax = t.getInt(R.styleable.SubsectionProgressBar_max, 1000);
        seekBarResId = t.getResourceId(R.styleable.SubsectionProgressBar_seekBarResId, 0);
        backgroundColor = t.getColor(R.styleable.SubsectionProgressBar_backgroundColor, Color.parseColor("#d9d9d9"));
        progressColor = t.getColor(R.styleable.SubsectionProgressBar_progressColor, Color.parseColor("#00B6D0"));
        ratio = t.getInteger(R.styleable.SubsectionProgressBar_ratio, 4);
    }

    public SubsectionProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
