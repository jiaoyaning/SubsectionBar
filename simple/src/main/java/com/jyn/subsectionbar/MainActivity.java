package com.jyn.subsectionbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jyn.subsectionprogressbar.ProgressBarBean;
import com.jyn.subsectionprogressbar.SubsectionProgressBar;
import com.jyn.subsectionseekbar.SectionBean;
import com.jyn.subsectionseekbar.SubsectionSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    private SubsectionProgressBar subsectionProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initProgressBar();
        initSeekBar();
    }

    private void initProgressBar() {
        subsectionProgressBar = findViewById(R.id.subsectionpb);
        subsectionProgressBar.setSeekBarBeans(getProgressBarBean());
    }

    private void initSeekBar() {
        SubsectionSeekBar bmpSeekBar = findViewById(R.id.seek_bar_bmp);
        final TextView seekbarTx = findViewById(R.id.seekbar_tx);
        bmpSeekBar.setProgress(50);
        bmpSeekBar.setOnSubsectionSeekBarChangeListener(new SubsectionSeekBar.onSubsectionSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(View view, int progress, boolean fromUser) {
                seekbarTx.setText("progress: " + progress);
                subsectionProgressBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(View view) {

            }

            @Override
            public void onStopTrackingTouch(View view) {

            }
        });
        bmpSeekBar.setSectionBeans(getBmpSeekBars());
        bmpSeekBar.setSecondaryProgress(500);
        SubsectionSeekBar seekBar = findViewById(R.id.seek_bar);
        final TextView seekbarTx2 = findViewById(R.id.seekbar_tx2);
        seekBar.setSectionBeans(getSeekBars());
        seekBar.setOnSubsectionSeekBarChangeListener(new SubsectionSeekBar.onSubsectionSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(View view, int progress, boolean fromUser) {
                seekbarTx2.setText("progress: " + progress);
            }

            @Override
            public void onStartTrackingTouch(View view) {

            }

            @Override
            public void onStopTrackingTouch(View view) {

            }
        });

        SeekBar seekBar1 = findViewById(R.id.native_seek_bar);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("main", "progress:" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public List<ProgressBarBean> getProgressBarBean() {
        List<ProgressBarBean> progressBarBeans = new ArrayList<>();
        progressBarBeans.add(new ProgressBarBean(100, 300, Color.parseColor("#00B6D0")));
        progressBarBeans.add(new ProgressBarBean(400, 700, Color.parseColor("#007196")));
        progressBarBeans.add(new ProgressBarBean(800, 1000, Color.parseColor("#005672")));
        return progressBarBeans;
    }

    public List<SectionBean> getBmpSeekBars() {
        List<SectionBean> sectionBeans = new ArrayList<>();
        sectionBeans.add(new SectionBean(ContextCompat.getColor(this, R.color.red1),
                200, 300, false));
        sectionBeans.add(new SectionBean(ContextCompat.getColor(this, R.color.blue2),
                600, 1000, false));
        return sectionBeans;
    }


    public List<Integer> getKeyBar() {
        List<Integer> keybar = new ArrayList<>();
        keybar.add(400);
        keybar.add(800);
        return keybar;
    }

    public List<SectionBean> getSeekBars() {
        List<SectionBean> sectionBeans = new ArrayList<>();
        sectionBeans.add(new SectionBean(ContextCompat.getColor(this, R.color.blue1),
                300, 400, true));
        sectionBeans.add(new SectionBean(ContextCompat.getColor(this, R.color.red1),
                500, 800, false));
        return sectionBeans;
    }
}
