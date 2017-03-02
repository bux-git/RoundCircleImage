package com.dqr.www.roundcircleimage.custview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.dqr.www.roundcircleimage.R;

/**
 * Description：使用XFerMode 实现
 * Author：LiuYM
 * Date： 2017-02-28 17:56
 */

public class RoundCircleImage extends View {
    private static final String TAG = "RoundCircleImage";
    private Bitmap mSrc;
    private int mBorderRadius;
    private int mType;//circle 0 round 1


    private static final int CIRCLE = 0;//圆
    private static final int ROUND = 1;//圆角

    public RoundCircleImage(Context context) {
        this(context, null);
    }

    public RoundCircleImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.RoundCircleImage_theme);
    }

    public RoundCircleImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, R.style.round_circle_default);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundCircleImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCircleImage, defStyleAttr, defStyleRes);
        mType = array.getInt(R.styleable.RoundCircleImage_type, 0);
        mBorderRadius = array.getDimensionPixelOffset(R.styleable.RoundCircleImage_borderRadius, 0);
        mSrc = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.RoundCircleImage_src, 0));
        array.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = 0;
        int height = 0;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int imgWidth = mSrc.getWidth() + getPaddingLeft() + getPaddingRight();
            width = Math.min(widthSize, imgWidth);
        }

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int imgHeight = mSrc.getHeight() + getPaddingTop() + getPaddingBottom();
            height = Math.min(heightSize, imgHeight);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.RED);
        switch (mType) {
            case ROUND:
                canvas.drawBitmap(roundBitmap(), 0, 0, null);
                break;
            case CIRCLE:
                int min = Math.min(getWidth(), getHeight());
                canvas.drawBitmap(circleBitmap(min), 0, 0, null);
                break;
        }

    }

    private Bitmap circleBitmap(int min) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap circle = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circle);
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


       canvas.drawBitmap(getScaleBitmap(min,min),0,0,paint);

        return circle;
    }


    private Bitmap roundBitmap() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawRoundRect(rectF,mBorderRadius,mBorderRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(getScaleBitmap(getWidth(),getHeight()), 0, 0, paint);

        return bitmap;
    }


    private Bitmap getScaleBitmap(int srcWidth,int srcHeight){
        Matrix matrix = new Matrix();
        matrix.setScale(srcWidth*1f/mSrc.getWidth(),srcHeight*1f/mSrc.getHeight());
        Bitmap src = Bitmap.createBitmap(mSrc, 0, 0, mSrc.getWidth(), mSrc.getHeight(), matrix, true);
        return src;
    }
}
