package com.dqr.www.roundcircleimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private Button mBtnXf;
    private Button mBtnShader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnShader = (Button) findViewById(R.id.btn_shader);
        mBtnXf = (Button) findViewById(R.id.btn_xf);

        mBtnShader.setOnClickListener(this);
        mBtnXf.setOnClickListener(this);

    }


    private Bitmap getBitmap(){
       Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.one);

        Matrix matrix = new Matrix();
        float sx=300f/bitmap.getWidth();
        float sy=300f/bitmap.getHeight();
        matrix.setScale(sx,sy);
        Log.d(TAG, "getBitmap: sx:"+sx+"    xy:"+sy+"   img:width:"+bitmap.getWidth()+" height:"+bitmap.getHeight());
        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        Log.d(TAG, "new: width:"+result.getWidth()+" height:"+result.getHeight());
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_shader:
                startActivity(new Intent(MainActivity.this,ShaderActivity.class));
                break;
            case R.id.btn_xf:
                startActivity(new Intent(MainActivity.this,XFerModeActivity.class));
                break;
        }
    }
}
