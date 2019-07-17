package com.jyn.subsectionprogressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SubsectionProgressBar extends View {
    private Context mContext;

    // 默认总进度
    private int mMax = 1000;

    // 当前进度 相对max值而言
    private int mProgress = 0;

    // 第二条进度条值
    private int mSecondaryProgress = 0;

    // bar 默认背景色
    private int backgroundColor;

    // bar 默认进度条颜色
    private int progressColor;

    // 第二进度条颜色
    private int secondaryProgressColor;

    // 按钮图片ID
    private int barResId;

    // 按钮颜色
    private int barColor;

    // 分段背景色值
    private List<ProgressBarBean> progressBarBeans = new ArrayList<>();

    /**
     * 进度条高度比例
     * 进度条高度 = 控件高度/ratio
     */
    private int ratio;

    // 当前进度百分比
    private float percent;

    // 背景色画笔
    private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // SeekBar按钮的位置
    private int lineTop, lineBottom, lineLeft, lineRight;

    // 圆角
    private int lineCorners;

    // Bar 的宽度
    private int lineWidth;

    // 总体背景色
    private RectF line = new RectF();

    // 已走完进度背景色
    private RectF progressLine = new RectF();

    // 第二进度颜色
    private RectF secondaryProgressLine = new RectF();

    // 导航按钮
    private SeekBar seekBar = new SeekBar();

    // SubsectionSeekBar 监听
    private onSubsectionSeekBarChangeListener onSubsectionSeekBarChangeListener;

    //===============参数方法分割线=============================================================

    public SubsectionProgressBar(Context context) {
        super(context);
    }

    @SuppressLint("Recycle")
    public SubsectionProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.SubsectionProgressBar);
        mMax = t.getInt(R.styleable.SubsectionProgressBar_max, 1000);
        barResId = t.getResourceId(R.styleable.SubsectionProgressBar_barResId, 0);
        backgroundColor = t.getColor(R.styleable.SubsectionProgressBar_backgroundColor, Color.parseColor("#d9d9d9"));
        progressColor = t.getColor(R.styleable.SubsectionProgressBar_progressColor, Color.parseColor("#00B6D0"));
        secondaryProgressColor = t.getColor(R.styleable.SubsectionProgressBar_secondaryProgressColor, Color.parseColor("#98F5FF"));
        ratio = t.getInteger(R.styleable.SubsectionProgressBar_ratio, 4);

        barColor = t.getColor(R.styleable.SubsectionProgressBar_barColor, Color.parseColor("#FF7F50"));
    }

    /**
     * 设置最大值
     *
     * @param max 最大值
     */
    public void setMax(int max) {
        this.mMax = max;
    }

    /**
     * 设置进度
     *
     * @param progress 进度值
     */
    public void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException(" progress 不可小于 0");
        } else if (progress > mMax) {
            progress = mMax;
        }
        this.mProgress = progress;
        updateSeekBar(progress);
    }

    public int getProgress() {
        return mProgress;
    }

    /**
     * 设置第二进度
     *
     * @param secondaryProgress 第二进度
     */
    public void setSecondaryProgress(int secondaryProgress) {
        this.mSecondaryProgress = secondaryProgress;
        invalidate();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setSecondaryProgressColor(int secondaryProgressColor) {
        this.secondaryProgressColor = secondaryProgressColor;
    }

    public void setSeekBarBeans(List<ProgressBarBean> progressBarBeans) {
        this.progressBarBeans.clear();
        this.progressBarBeans.addAll(progressBarBeans);
        this.invalidate();
    }

    /**
     * 设置监听
     *
     * @param onSubsectionSeekBarChangeListener 监听
     */
    public void setOnSubsectionSeekBarChangeListener(onSubsectionSeekBarChangeListener onSubsectionSeekBarChangeListener) {
        this.onSubsectionSeekBarChangeListener = onSubsectionSeekBarChangeListener;
    }

    //================测量绘制Touch时间分割线==============================================
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightSize > widthSize) {
            setMeasuredDimension(widthSize, widthSize);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * @param w    当前view的宽
     * @param h    当前view的高
     * @param oldw 改变之后的宽
     * @param oldh 改变之后的高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int seekBarRadius = h / 2;
        /*
         * 属性 left right top bottom 描述了SeekBar按钮的位置
         * 蓝后根据它们预先设置确定出 RectF line 背景的三维
         * lineCorners 圆滑的边缘似乎会比直角更好看
         */
        lineLeft = seekBarRadius;
        lineRight = w - seekBarRadius;
        lineTop = seekBarRadius - seekBarRadius / ratio;
        lineBottom = seekBarRadius + seekBarRadius / ratio;

        //progress宽度
        lineWidth = lineRight - lineLeft;
        //左上右下
        line.set(lineLeft, lineTop, lineRight, lineBottom);
        //圆角
        lineCorners = (int) ((lineBottom - lineTop) * 0.45f);
        // 在RangeSeekBar确定尺寸时确定SeekBar按钮尺寸
        seekBar.onSizeChanged(seekBarRadius, seekBarRadius, h, seekBarRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setAntiAlias(true);
        drawBackground(canvas);
        if (progressBarBeans != null && progressBarBeans.size() > 0) {
            for (int i = 0; i < progressBarBeans.size(); i++) {
                drawSubsectionBean(canvas, progressBarBeans.get(i));
            }
        }
        drawSecondaryProgress(canvas);
        drawProgress(canvas);
        // 绘制按钮
        seekBar.draw(canvas);
    }

    /**
     * 绘制背景
     *
     * @param canvas 画布
     */
    private void drawBackground(Canvas canvas) {
        mBackgroundPaint.setColor(Color.parseColor("#d9d9d9"));
        /*
         * rect：RectF对象。
         * rx：x方向上的圆角半径。
         * ry：y方向上的圆角半径。
         * paint：绘制时所使用的画笔。
         */
        canvas.drawRoundRect(line, lineCorners, lineCorners, mBackgroundPaint);
    }

    /**
     * 绘制分段背景
     *
     * @param canvas           画布
     * @param progressBarBeans 分段背景bean类
     */
    private void drawSubsectionBean(Canvas canvas, ProgressBarBean progressBarBeans) {
        //起点
        int origin = progressBarBeans.getOrigin();
        //终点
        int terminus = progressBarBeans.getTerminus();
        if (0 <= origin && origin < terminus & terminus <= mMax) {
            //起点位置 绘制
            int originLeft = (int) (origin * 1f / mMax * lineWidth);
            //终点位置 绘制
            int terminusRight = (int) (terminus * 1f / mMax * lineWidth);
            mBackgroundPaint.setColor(progressBarBeans.getColor());
            RectF subsectionLine = new RectF();
            subsectionLine.set(lineLeft + originLeft, lineTop, lineLeft + terminusRight, lineBottom);
            canvas.drawRoundRect(subsectionLine, lineCorners, lineCorners, mBackgroundPaint);
        } else {
            //直接手动抛出异常
            throw new RuntimeException("坐标位置错误，\n" +
                    "1：起点小于0；\n" +
                    "2：起点大于终点；\n" +
                    "3：终点小于总长");
        }
    }

    /**
     * 绘制第二条进度条的颜色
     *
     * @param canvas 画布
     */
    private void drawSecondaryProgress(Canvas canvas) {
        mBackgroundPaint.setColor(secondaryProgressColor);
        int secondaryprogressLineWidth = (int) (mSecondaryProgress * 1f / mMax * lineWidth);
        secondaryProgressLine.set(lineLeft, lineTop, secondaryprogressLineWidth + lineLeft, lineBottom);
        canvas.drawRoundRect(secondaryProgressLine, lineCorners, lineCorners, mBackgroundPaint);
    }

    /**
     * 绘制已走过进度的颜色
     *
     * @param canvas 画布
     */
    private void drawProgress(Canvas canvas) {
        int progressLineWidth = (int) (lineWidth * percent);
        progressLine.set(lineLeft, lineTop, progressLineWidth + lineLeft, lineBottom);
        mBackgroundPaint.setColor(Color.parseColor("#00B6D0"));
        canvas.drawRoundRect(progressLine, lineCorners, lineCorners, mBackgroundPaint);
    }

//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //点击位置坐标 x
//        float x = event.getX();
//        // bar的位置
//        if (x <= lineLeft) {
//            percent = 0;
//            mProgress = 0;
//        } else if (x >= lineRight) {
//            percent = 1;
//            mProgress = mMax;
//        } else {
//            percent = (x - lineLeft) * 1f / (lineWidth);
//            mProgress = (int) ((x - lineLeft) * 1f / (lineWidth) * mMax);
//        }
//
//        int checkProgress = checkProgress(this.mProgress);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                isTouch = true;
//                if (onSubsectionSeekBarChangeListener != null) {
//                    onSubsectionSeekBarChangeListener.onStartTrackingTouch(this);
//                }
//            case MotionEvent.ACTION_MOVE:
//                this.mProgress = checkProgress;
//                updateSeekBar(this.mProgress);
//                if (onSubsectionSeekBarChangeListener != null) {
//                    onSubsectionSeekBarChangeListener.onProgressChanged(this, this.mProgress, true);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                isTouch = false;
//                this.mProgress = checkProgress;
//                updateSeekBar(this.mProgress);
//                if (onSubsectionSeekBarChangeListener != null && checkProgress != mProgress) {
//                    onSubsectionSeekBarChangeListener.onStopTrackingTouch(this);
//                    onSubsectionSeekBarChangeListener.onProgressChanged(this, this.mProgress, true);
//                }
//                break;
//        }
//        return true;
//    }

    /**
     * 根据进度调整bar位置
     *
     * @param percent 0-1之间
     */
    public void updateSeekBar(float percent) {
        // SeekBar按钮根据当前手指在拖动条上的滑动而滑动
        if (percent < 0) {
            throw new IllegalArgumentException(" percent 不可小于 0");
        } else if (percent > 1) {
            percent = 1;
        }
        seekBar.slide(percent);
        this.invalidate();
    }

    /**
     * 根据进度调转bar位置
     *
     * @param progress 0-max 之间
     */
    public void updateSeekBar(int progress) {
        // SeekBar按钮根据当前手指在拖动条上的滑动而滑动
        percent = progress * 1f / mMax;
        updateSeekBar(percent);
    }

    public class SeekBar {

        /**
         * 按钮位置，百分比
         */
        float currPercent;

        /**
         * 移动后的左边界
         */
        int left;

        /**
         * 导航点中心坐标
         */
        int centerX;
        int centerY;

        /**
         * 导航圈半径
         */
        int radius;
        /**
         * 导航圆圈的bar
         */
        private Paint barPaint;

        /**
         * 如果按钮是张图片
         */
        private Bitmap barBmp;

        /**
         * 当RangeSeekBar尺寸发生变化时，SeekBar按钮尺寸随之变化
         *
         * @param centerX SeekBar按钮的X中心在RangeSeekBar中的相对位置
         * @param centerY SeekBar按钮的Y中心在RangeSeekBar中的相对位置
         */
        void onSizeChanged(int centerX, int centerY, int hSize, int radius) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
            if (barResId > 0) {
                barBmp = creatBmp(hSize, barResId);
            } else {
                initPaint();
            }
        }

        /**
         * 把ID转换成bmp
         *
         * @param size 尺寸
         * @param id   图片ID
         * @return 转换成的Bitmap
         */
        private Bitmap creatBmp(int size, int id) {
            Bitmap originalBmp = BitmapFactory.decodeResource(mContext.getResources(), id);
            if (originalBmp != null) {
                Matrix matrix = new Matrix();
                float scaleHeight = ((float) size) / originalBmp.getHeight();
                //等比按高度缩放
                matrix.postScale(scaleHeight, scaleHeight);
                return Bitmap.createBitmap(originalBmp, 0, 0, originalBmp.getWidth(), originalBmp.getHeight(), matrix, true);
            }
            return null;
        }

        //初始化画笔
        private void initPaint() {
            barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            barPaint.setColor(barColor);
        }

        void draw(Canvas canvas) {
            int offset = (int) (lineWidth * currPercent);
            canvas.save();
            canvas.translate(offset, 0);
            if (barBmp != null) {
                canvas.drawBitmap(barBmp, left, left, null);
            } else {
                canvas.translate(left, 0);
                canvas.drawCircle(centerX, centerY, radius, barPaint);
            }
            canvas.restore();
        }

//        boolean collide(MotionEvent event) {
//            //判断是否被点击
//            float x = event.getX();
//            float y = event.getY();
//            int offset = (int) (lineWidth * currPercent);
//            return x > left + offset && x < right + offset && y > top && y < bottom;
//        }

        /**
         * 滑动
         *
         * @param percent 滑动百分比
         */
        void slide(float percent) {
            if (percent < 0) {
                percent = 0;
            } else if (percent > 1) {
                percent = 1;
            }
            currPercent = percent;
        }
    }

    /**
     * SubsectionSeekBar的监听
     */
    public interface onSubsectionSeekBarChangeListener {
        /**
         * 进度条改变后的监听
         *
         * @param view     本view
         * @param progress 进度
         * @param fromUser 是否是用户改变
         */
        void onProgressChanged(View view, int progress, boolean fromUser);

        /**
         * 触发开始的点击事件
         *
         * @param view 本view
         */
        void onStartTrackingTouch(View view);

        /**
         * 触发结束后的点击事件
         *
         * @param view 本view
         */
        void onStopTrackingTouch(View view);
    }
}
