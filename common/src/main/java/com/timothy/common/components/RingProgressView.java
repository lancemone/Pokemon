package com.timothy.common.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.timothy.common.utils.UIUtils;

/**
 * 环形进度条，支持渐变
 */
public class RingProgressView extends View {

    private Paint mPaint;
    private int mRingWidth;
    private int mRingStartColor;
    private int mRingEndColor;
    private int mRingColor;

    private int mRingRadius;

    public RingProgressView(Context context) {
        this(context, null);
    }

    public RingProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    public RingProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attributeSet){
        mRingWidth = UIUtils.dp2px(context, 2);
        mRingRadius = UIUtils.dp2px(context, 100);
        mRingColor = Color.WHITE;
        mRingStartColor = context.getColor(com.timothy.resource.R.color.color_primary);
        mRingEndColor = context.getColor(com.timothy.resource.R.color.color_primary_variant);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int ringSize = MeasureSpec.makeMeasureSpec(mRingRadius * 2, MeasureSpec.EXACTLY);
        if (widthMeasureSpec < ringSize){
            widthMeasureSpec = ringSize;
        }
        if (heightMeasureSpec < ringSize){
            heightMeasureSpec = ringSize;
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        RectF rectFRing = new RectF(0, 0, mRingRadius * 2, mRingRadius * 2);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mRingColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRingWidth);
        SweepGradient sweepGradient =  new SweepGradient(rectFRing.centerX(), rectFRing.centerY(), mRingStartColor, mRingEndColor);
        mPaint.setShader(sweepGradient);
        canvas.drawArc(rectFRing, 0, 360, false, mPaint);
    }
}
