package com.jyn.subsectionbar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jyn.subsectionseekbar.SeekBarBean;
import com.jyn.subsectionseekbar.SubsectionSeekBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
//        SubsectionProgressBar subsectionpb = findViewById(R.id.subsectionpb);
//        subsectionpb.setMaxProgress(100);
        SubsectionSeekBar rangeSeekBar = findViewById(R.id.range_seek_bar);
        final TextView seekbarTx = findViewById(R.id.seekbar_tx);
        rangeSeekBar.setProgress(50);
//        rangeSeekBar.setSecondaryProgress(700);
//        rangeSeekBar.setSeekBarBeans(getSeekBars());
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

        List<VideoBean> videoList = getVideoList();
        long durationSum = 0;
        //设置播放源的时候直接获取总时长
        for (int i = 0; i < videoList.size(); i++) {
            //获取总时长
            VideoBean videoBean = videoList.get(i);
            durationSum = durationSum + videoBean.getDuration();
        }
        List<SeekBarBean> seekBarBeans = new ArrayList<>();
        long sum = 0;
        for (int i = 0; i < videoList.size(); i++) {
            VideoBean videoBean = videoList.get(i);
            long duration = videoBean.getDuration(); //本时长
            if (videoBean.isMark()) {
                long origin = sum * 1000L / durationSum;
                long terminus = origin + duration * 1000L / durationSum;
                SeekBarBean seekBarBean = new SeekBarBean(
                        this.getResources().getColor(R.color.red1), (int) origin, (int) terminus, true);
                seekBarBeans.add(seekBarBean);
            }
            sum = sum + duration;
        }
        rangeSeekBar.setSeekBarBeans(seekBarBeans);
    }

    public List<SeekBarBean> getSeekBars() {
        List<SeekBarBean> seekBarBeans = new ArrayList<>();
        seekBarBeans.add(new SeekBarBean(getResources().getColor(R.color.red1),
                200, 300, false));
        seekBarBeans.add(new SeekBarBean(getResources().getColor(R.color.blue2),
                500, 1000, false));
        return seekBarBeans;
    }

    public List<VideoBean> getVideoList() {
        List<VideoBean> videoLists = new ArrayList<>();
        videoLists.add(new VideoBean("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4", 188731, false));

//        videoLists.add(new VideoBean("http://classclient.neibu.koolearn.com/77.flv", 382411));
        videoLists.add(new VideoBean("http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4", 60093, true));
        videoLists.add(new VideoBean("http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super", 117284, false));
        return videoLists;
    }
}
