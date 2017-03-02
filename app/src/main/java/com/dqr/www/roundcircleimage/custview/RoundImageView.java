package com.dqr.www.roundcircleimage.custview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.dqr.www.roundcircleimage.R;

/**
 * Description：自定义 圆角 圆形ImageView 使用Shader
 * <p>
 * Author：LiuYM
 * Date： 2017-03-02 14:02
 */

public class RoundImageView extends ImageView {
    private static final String TAG = "RoundImageView";

    private static final int CIRCLE = 0;//圆
    private static final int ROUND = 1;//圆角

    private int mBorderRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics());//圆角半径 默认10dp
    private int mType = 0;//图片类型
    private int mWidth;//宽度
    private int mRadius;

    public RoundImageView(Context context) {
        this(context,null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCircleImage);
        mRadius = array.getDimensionPixelOffset(R.styleable.RoundCircleImage_borderRadius, 10);
        mType = array.getInt(R.styleable.RoundCircleImage_type, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mType == CIRCLE) {//圆形 强制设置宽高一致 以最小的为准
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(getDrawable()==null)return;
        Paint paint = setShader();
        if (mType == CIRCLE) {
            canvas.drawCircle(mRadius, mRadius, mRadius, paint);
        } else {
            RectF rectF = new RectF(0, 0, getWidth(), getHeight());
            canvas.drawRoundRect(rectF, mBorderRadius, mBorderRadius, paint);
        }
    }

    /**
     * 设置着色器返回Paint
     * @return
     */
    private Paint setShader() {
       Bitmap bitmap=getBitmap();

        Matrix mMatrix = new Matrix();
        mMatrix.setScale(getWidth()*1f  / bitmap.getWidth(), getHeight()*1f  / bitmap.getHeight());//等比缩放成控件宽高
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        shader.setLocalMatrix(mMatrix);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setShader(shader);
        return paint;
    }

    /**
     * 获取Bitmap
     * @return
     */
    private Bitmap getBitmap(){
        Bitmap  bitmap;
        Drawable drawable = getDrawable();
        if (drawable == null) return null;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int imgWidth = drawable.getIntrinsicWidth();
            int imgHeight = drawable.getIntrinsicHeight();

            bitmap = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, imgWidth, imgHeight);
            drawable.draw(canvas);
        }
        return bitmap;
    }


    public void setBorderRadius(int borderRadius) {
        int pxBr = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, borderRadius, getResources().getDisplayMetrics());
        if(mBorderRadius!=pxBr){
            mBorderRadius = pxBr;
            invalidate();
        }
    }

    public void setType(int type) {
       if(mType!=type){
           mType=type;
           if(mType!=CIRCLE&&mType!=ROUND)mType=ROUND;
           requestLayout();
       }
    }
}
