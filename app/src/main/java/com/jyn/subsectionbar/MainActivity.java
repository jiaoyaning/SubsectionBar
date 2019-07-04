package com.jyn.subsectionbar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jyn.subsectionprogressbar.SubsectionProgressBar;
import com.jyn.subsectionseekbar.SubsectionSeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        SubsectionProgressBar subsectionpb = findViewById(R.id.subsectionpb);
        subsectionpb.setMaxProgress(100);
        SubsectionSeekBar rangeSeekBar = findViewById(R.id.range_seek_bar);
        final TextView seekbarTx = findViewById(R.id.seekbar_tx);
        rangeSeekBar.setProgress(50);
//        rangeSeekBar.setSecondaryProgress(700);
        rangeSeekBar.setOnSubsectionSeekBarChangeListener(new SubsectionSeekBar.onSubsectionSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(View view, int progress, boolean fromUser) {
                Log.i("main", "progress:" + progress);
                seekbarTx.setText("progress:" + progress);
            }

            @Override
            public void onStartTrackingTouch(View view) {

            }

            @Override
            public void onStopTrackingTouch(View view) {

            }
        });
    }
}
