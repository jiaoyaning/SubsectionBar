package com.jyn.subsectionseekbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.SeekBar;

@SuppressLint("AppCompatCustomView")
public class TextSeekBar extends SeekBar {
    public TextSeekBar(Context context) {
        super(context);
    }

    public TextSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(Color.RED);
        ClipDrawable clipDrawable = new ClipDrawable(colorDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);

        Drawable drawable = getResources().getDrawable(R.color.blue1);
        Drawable[] layers = new Drawable[]{drawable, clipDrawable};
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setDrawableByLayerId(0, drawable);
        layerDrawable.setDrawableByLayerId(1, clipDrawable);
        this.setProgressDrawable(clipDrawable);
        setProgress(50);
    }
}
