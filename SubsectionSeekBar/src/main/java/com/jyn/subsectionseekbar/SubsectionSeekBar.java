package com.jyn.subsectionseekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("AppCompatCustomView")
public class SubsectionSeekBar extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int lineTop, lineBottom, lineLeft, lineRight;
    private int lineCorners;
    private int lineWidth;
    private RectF line = new RectF();

    public SubsectionSeekBar(Context context) {
        super(context);
    }

    public SubsectionSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightSize * 2 > widthSize) {
            setMeasuredDimension(widthSize, (widthSize / 2));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

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
        line.set(lineLeft, lineTop, lineRight, lineBottom);
        lineCorners = (int) ((lineBottom - lineTop) * 0.45f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFFD7D7D7);
        canvas.drawRoundRect(line, lineCorners, lineCorners, paint);
    }
}
