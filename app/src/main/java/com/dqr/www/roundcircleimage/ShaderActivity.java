package com.dqr.www.roundcircleimage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Description：
 * Author：LiuYM
 * Date： 2017-03-02 20:18
 */

public class ShaderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image_view_layout);
        setContentView(R.layout.activity_round_image_view_layout);
        mRecyclerView= (RecyclerView) findViewById(R.id.rl_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RoundAdapter());
    }
}
