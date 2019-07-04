package com.jyn.subsectionbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jyn.subsectionprogressbar.SubsectionProgressBar;

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
    }
}
