package com.jyn.subsectionseekbar;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class RangeSeekBar extends View {
    private Context mContext;
    private SeekBar seekBar = new SeekBar();

    /**
     * 默认总进度
     */
    private int max = 1000;



    /**
     * 背景色画笔
     */
    private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * SeekBar按钮的位置
     */
    private int lineTop, lineBottom, lineLeft, lineRight;
    private int lineCorners;
    private int lineWidth;
    private RectF line = new RectF();

    private android.widget.SeekBar.OnSeekBarChangeListener onSeekBarChangeListener;

    public RangeSeekBar(Context context) {
        super(context);
    }

    public RangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public void setOnSeekBarChangeListener(android.widget.SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.onSeekBarChangeListener = onSeekBarChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightSize * 2 > widthSize) {
            setMeasuredDimension(widthSize, widthSize / 2);
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
        /**
         * 属性 left right top bottom 描述了SeekBar按钮的位置
         * 蓝后根据它们预先设置确定出 RectF line 背景的三维
         * lineCorners 圆滑的边缘似乎会比直角更好看
         */
        lineLeft = seekBarRadius;
        lineRight = w - seekBarRadius;
        lineTop = seekBarRadius - seekBarRadius / 4;
        lineBottom = seekBarRadius + seekBarRadius / 4;
        lineWidth = lineRight - lineLeft;

        //左上右下
        line.set(lineLeft, lineTop, lineRight, lineBottom);
        lineCorners = (int) ((lineBottom - lineTop) * 0.45f);

        // 在RangeSeekBar确定尺寸时确定SeekBar按钮尺寸
        seekBar.onSizeChanged(seekBarRadius, seekBarRadius, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setAntiAlias(true);
//        paint.setColor(0xFFD7D7D7);
        mBackgroundPaint.setColor(Color.parseColor("#d9d9d9"));
        /*
         * rect：RectF对象。
         * rx：x方向上的圆角半径。
         * ry：y方向上的圆角半径。
         * paint：绘制时所使用的画笔。
         * 绘制背景图
         */
        canvas.drawRoundRect(line, lineCorners, lineCorners, mBackgroundPaint);
        // 绘制按钮图
        seekBar.draw(canvas);
    }

    /**
     * 点击监听
     *
     * @param event
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                boolean touchResult = false;
                // 进行检测，手指手指是否落在当前SeekBar上。即声明SeekBar时使用left、top、right、bottom属性所描述区域的内部
//                if (seekBar.collide(event)) {
//                    touchResult = true;
//                    Toast.makeText(mContext, "SeekBar被点击", Toast.LENGTH_SHORT).show();
//                }
            case MotionEvent.ACTION_MOVE:
                float percent;
                if (x <= lineLeft) {
                    percent = 0;
                } else if (x >= lineRight) {
                    percent = 1;
                } else {
                    percent = (x - lineLeft) * 1f / (lineWidth);
                }
                Log.i("main", "percent:" + percent);
                // SeekBar按钮根据当前手指在拖动条上的滑动而滑动
                seekBar.slide(percent);
                this.invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    public class SeekBar {
        //        int lineWidth; // 拖动条宽度 可在onSizeChanged时刻获得
        float currPercent;

        /**
         * widthSize：导航点宽度
         * heightSize：导航点高度
         */
        int widthSize;
        int heightSize;

        /**
         * 右左中下，点位点
         */
        int left, right, top, bottom;
        Bitmap bmp;

        /**
         * 导航圆圈的bar
         */
        private Paint defaultPaint;

        /**
         * 径向渐变，由内而外渐变
         */
        private RadialGradient shadowGradient;

        float material = 0;
        ValueAnimator anim;
        final TypeEvaluator<Integer> te = new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                int alpha = (int) (Color.alpha(startValue) + fraction * (Color.alpha(endValue) - Color.alpha(startValue)));
                int red = (int) (Color.red(startValue) + fraction * (Color.red(endValue) - Color.red(startValue)));
                int green = (int) (Color.green(startValue) + fraction * (Color.green(endValue) - Color.green(startValue)));
                int blue = (int) (Color.blue(startValue) + fraction * (Color.blue(endValue) - Color.blue(startValue)));
                return Color.argb(alpha, red, green, blue);
            }
        };

        /**
         * 当RangeSeekBar尺寸发生变化时，SeekBar按钮尺寸随之变化
         *
         * @param centerX    SeekBar按钮的X中心在RangeSeekBar中的相对位置
         * @param centerY    SeekBar按钮的Y中心在RangeSeekBar中的相对位置
         * @param heightSize RangeSeekBar期望SeekBar所拥有的高度
         */
        void onSizeChanged(int centerX, int centerY, int heightSize) {
            /**
             * 属性 left right top bottom 描述了SeekBar按钮的位置<br>
             */
            this.heightSize = heightSize;
            widthSize = (int) (heightSize * 0.8f); //为阴影留出位置
            left = centerX - widthSize / 2;
            right = centerX + widthSize / 2;
            top = centerY - heightSize / 2;
            bottom = centerY + heightSize / 2;

            bmp = Bitmap.createBitmap(widthSize, heightSize, Bitmap.Config.ARGB_8888);
            int bmpCenterX = bmp.getWidth() / 2;
            int bmpCenterY = bmp.getHeight() / 2;

            //背景条半径
            int bmpRadius = (int) (widthSize * 0.5f);

            defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // 绘制Shadow
            defaultPaint.setStyle(Paint.Style.FILL);
            Canvas defaultCanvas = new Canvas(bmp);
            int barShadowRadius = (int) (bmpRadius * 0.95f);
            defaultCanvas.save();
            defaultCanvas.translate(0, bmpRadius * 0.25f);
            shadowGradient = new RadialGradient(bmpCenterX, bmpCenterY, barShadowRadius,
                    Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
            defaultPaint.setShader(shadowGradient);
            defaultCanvas.drawCircle(bmpCenterX, bmpCenterY, barShadowRadius, defaultPaint);
            defaultPaint.setShader(null);
            defaultCanvas.restore();
            // 绘制Body
            defaultPaint.setStyle(Paint.Style.FILL);
            defaultPaint.setColor(0xFFFFFFFF);
            defaultCanvas.drawCircle(bmpCenterX, bmpCenterY, bmpRadius, defaultPaint);
            // 绘制Border
            defaultPaint.setStyle(Paint.Style.STROKE);
            defaultPaint.setColor(0xFFD7D7D7);
            defaultCanvas.drawCircle(bmpCenterX, bmpCenterY, bmpRadius, defaultPaint);
        }

        void draw(Canvas canvas) {
            int offset = (int) (lineWidth * currPercent);
            canvas.save();

            /*
             * 画布向（X，Y）方向平移
             *
             * 参数1: 向X轴方向移动x距离
             * 参数2: 向Y轴方向移动y距离
             */
            canvas.translate(left, 0);
            canvas.translate(offset, 0);
            drawDefault(canvas);
            canvas.restore();
        }

        private void drawDefault(Canvas canvas) {
            int centerX = widthSize / 2;
            int centerY = heightSize / 2;
            int radius = (int) (widthSize * 0.5f);
            // draw shadow
            defaultPaint.setStyle(Paint.Style.FILL);
            canvas.save();
            canvas.translate(0, radius * 0.25f);
            canvas.scale(1 + (0.1f * material), 1 + (0.1f * material), centerX, centerY);
            defaultPaint.setShader(shadowGradient);
            canvas.drawCircle(centerX, centerY, radius, defaultPaint);
            defaultPaint.setShader(null);
            canvas.restore();
            // draw body
            defaultPaint.setStyle(Paint.Style.FILL);
            defaultPaint.setColor(te.evaluate(material, 0xFFFFFFFF, 0xFFE7E7E7));
            canvas.drawCircle(centerX, centerY, radius, defaultPaint);
            // draw border
            defaultPaint.setStyle(Paint.Style.STROKE);
            defaultPaint.setColor(0xFFD7D7D7);
            canvas.drawCircle(centerX, centerY, radius, defaultPaint);
        }

        /**
         * 判断是否被点击
         *
         * @param event
         * @return
         */
        boolean collide(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            int offset = (int) (lineWidth * currPercent);
            return x > left + offset && x < right + offset && y > top && y < bottom;
        }

        /**
         * 滑动
         *
         * @param percent
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
}
