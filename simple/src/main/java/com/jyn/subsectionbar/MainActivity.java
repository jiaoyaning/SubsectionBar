package com.jyn.subsectionbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.jyn.subsectionseekbar.SectionBean;
import com.jyn.subsectionseekbar.SubsectionSeekBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSeekBar();
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
            }

            @Override
            public void onStartTrackingTouch(View view) {

            }

            @Override
            public void onStopTrackingTouch(View view) {

            }

            @Override
            public void onKeyTouch(int person, float x) {
            }
        });

        bmpSeekBar.setSectionBeans(getBmpSeekBars());
        bmpSeekBar.setSecondaryProgress(500);
        SubsectionSeekBar seekBar = findViewById(R.id.seek_bar);
        final TextView seekbarTx2 = findViewById(R.id.seekbar_tx2);

        seekBar.setSectionBeans(getSeekBars());
        seekBar.setKayBars(getKeyBar());

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

            @Override
            public void onKeyTouch(int person, float x) {
            }
        });

        SeekBar seekBar1 = findViewById(R.id.native_seek_bar);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
                300, 400, false));
        sectionBeans.add(new SectionBean(ContextCompat.getColor(this, R.color.red1),
                500, 800, false));
        return sectionBeans;
    }
}
