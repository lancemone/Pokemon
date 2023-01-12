package com.timothy.common.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.timothy.common.R;
import com.timothy.common.utils.BitmapUtils;

public class CircleImageView extends AppCompatImageView {

    private int mRadius;
    private int mBorderWidth;
    private int mBorderColour;

    private int mBorderStartColour;
    private int mBorderEndColour;

    private Paint mPaint;

    public CircleImageView(@NonNull Context context) {
        this(context, null);
    }

    public CircleImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attributeSet, int defStyleAttr){
        final TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImageView);
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.CircleImageView_border_width, 0);
        mBorderColour = typedArray.getColor(R.styleable.CircleImageView_border_color, Color.WHITE);
        mBorderStartColour = typedArray.getColor(R.styleable.CircleImageView_border_start_color, 0);
        mBorderEndColour = typedArray.getColor(R.styleable.CircleImageView_border_end_color, 0);
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(w, h) / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int diameter = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = diameter / 2;
        // 重新设置宽高
        setMeasuredDimension(diameter, diameter);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null){
            return;
        }
        mPaint = new Paint();
        Bitmap bitmap = BitmapUtils.drawableToBitmap(getDrawable());
        if (bitmap == null){
            return;
        }
        int srcRadius = mRadius - mBorderWidth;
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        RectF bitmapRectF = new RectF(mBorderWidth, mBorderWidth, viewWidth - mBorderWidth, viewHeight - mBorderWidth);
        //构建BitmapShader对象，设置为CLAMP模式，当所画图形的尺寸小于bitmap尺寸的时候，会对bitmap进行裁剪
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 对bitmap进行缩放
        float mScale = (srcRadius * 2.0f) / Math.min(bitmap.getWidth(), bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(mScale, mScale);
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        // 画圆形，并对paint设置的BitmapShader中的bitmap进行裁剪处理
        canvas.drawCircle(bitmapRectF.centerX(), bitmapRectF.centerY(), srcRadius, mPaint);
        drawBorder(canvas);
//        super.onDraw(canvas);
    }

    private void drawBorder(Canvas canvas){
        if (canvas != null && mBorderWidth > 0){
//            mPaint.reset();
            Paint paint = new Paint();
            RectF mCircleRect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
            paint.setColor(mBorderColour);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(mBorderWidth);
            paint.setAntiAlias(true);
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            if (mBorderStartColour != 0 && mBorderEndColour != 0){
                Log.d("motao", "mBorderStartColour=" + mBorderStartColour + "mBorderEndColour=" + mBorderEndColour);
                SweepGradient sweepGradient = new SweepGradient(mCircleRect.centerX(), mCircleRect.centerY(), mBorderStartColour, mBorderEndColour);
                paint.setShader(sweepGradient);
            }
            canvas.drawArc(mCircleRect, 0, 360, false, paint);
        }
    }
}
