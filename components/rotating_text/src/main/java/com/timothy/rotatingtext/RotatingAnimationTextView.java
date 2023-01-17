package com.timothy.rotatingtext;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 文字轮播控件
 * TODO: 控件宽度计算，没有按照最长的文本计算宽度以及没有设置最大宽度
 */
public class RotatingAnimationTextView extends TextView {

    private Rotatable rotatable;
    private Paint paint;
    private float density;
    private boolean isRotatableSet = false;
    private Path pathIn, pathOut;
    private Timer updateWordTimer;
    private Handler handler;

    private String currentText = "";
    private String oldText = "";
    private AnimationInterface animationInterface = new AnimationInterface(false);
    private boolean isPaused = false;

    public RotatingAnimationTextView(Context context) {
        super(context);
    }

    public RotatingAnimationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotatingAnimationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RotatingAnimationTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setRotatable(Rotatable rotatable) {
        if (rotatable == null){
            return;
        }
        this.rotatable = rotatable;
        isRotatableSet = true;
        init();
    }

    private void init(){
        paint = new Paint();
        density = getContext().getResources().getDisplayMetrics().density;
        paint.setAntiAlias(true);
        paint.setTextSize(rotatable.getSize());
        paint.setColor(rotatable.getColor());
        if (rotatable.isCenter()){
            paint.setTextAlign(Paint.Align.CENTER);
        }
        if (rotatable.getTypeface() != null){
            paint.setTypeface(rotatable.getTypeface());
        }
        setText(rotatable.getLargestWordWithSpace());
        currentText = rotatable.getNextWord();
        oldText = currentText;
        post(new Runnable() {
            @Override
            public void run() {
                pathIn = new Path();
                pathIn.moveTo(0.0f, getHeight() - paint.getFontMetrics().bottom);
                pathIn.lineTo(getWidth(), getHeight() - paint.getFontMetrics().bottom);
                rotatable.setPathIn(pathIn);

                pathOut = new Path();
                pathOut.moveTo(0.0f, (2 * getHeight()) - paint.getFontMetrics().bottom);
                pathOut.lineTo(getWidth(), (2 * getHeight()) - paint.getFontMetrics().bottom);
                rotatable.setPathOut(pathOut);
            }
        });
        if (handler == null){
            handler = new Handler(Looper.getMainLooper());
        }
        handler.postDelayed(mHandlerRun, 1000 / rotatable.getFPS());
        invalidate();
        updateWordTimer = new Timer();
        updateWordTimer.scheduleAtFixedRate(initTimerTask, rotatable.getUpdateDuration(), rotatable.getUpdateDuration());
    }

    private final TimerTask initTimerTask = new TimerTask() {
        @Override
        public void run() {
            WeakReference<Context> context = new WeakReference<>(getContext());
            if (context.get() == null){
                return;
            }
            if (context.get() instanceof Activity){
                ((Activity) context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isPaused){
                            pauseRender();
                        }else {
                            animationInterface.setAnimationRunning(true);
                            resumeRender();
                            animateInHorizontal();
                            animateOutHorizontal();
                            oldText = currentText;
                            currentText = rotatable.getNextWord();
                        }
                    }
                });
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (isRotatableSet){
            if (rotatable.isUpdated()){
                updatePaint();
                rotatable.setUpdated(false);
            }

            String text = currentText;
            int number = rotatable.getCurrentWordNumber();
            int arrayLength = rotatable.colorArraySize();
            if (rotatable.getPathIn() != null) {
                canvas.drawTextOnPath(text, rotatable.getPathIn(), 0.0f, 0.0f, paint);

                if(rotatable.useArray()) {
                    if (number < arrayLength && number > 0) {
                        paint.setColor(rotatable.getColorFromArray(number-1));
                    } else {
                        paint.setColor(rotatable.getColorFromArray(arrayLength-1));
                    }
                }

                if (rotatable.getPathOut() != null) {
                    canvas.drawTextOnPath(oldText, rotatable.getPathOut(), 0.0f, 0.0f, paint);
                    if(number < arrayLength && rotatable.useArray()) {
                        paint.setColor(rotatable.getColorFromArray(number));
                    }
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (updateWordTimer != null){
            updateWordTimer.cancel();
        }
    }

    private void animateInHorizontal(){
        ValueAnimator animator;
        if(!rotatable.isApplyHorizontal()) {
            animator = ValueAnimator.ofFloat(0.0f, getHeight());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    pathIn = new Path();
                    pathIn.moveTo(0.0f, (Float) valueAnimator.getAnimatedValue() - paint.getFontMetrics().bottom);
                    pathIn.lineTo(getWidth(), (Float) valueAnimator.getAnimatedValue() - paint.getFontMetrics().bottom);
                    rotatable.setPathIn(pathIn);
                }
            });
        }
        else {
            animator = ValueAnimator.ofFloat(-getWidth(),0.0f );
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    pathIn = new Path();
                    pathIn.moveTo((Float) valueAnimator.getAnimatedValue(), 2*getHeight()/3.0f);
                    pathIn.lineTo((Float) valueAnimator.getAnimatedValue() + getWidth(), 2*getHeight()/3.0f);
                    rotatable.setPathIn(pathIn);
                }
            });
        }
        animator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                animationInterface.setAnimationRunning(false);
            }
        });
        animator.setInterpolator(rotatable.getInterpolator());
        animator.setDuration(rotatable.getAnimationDuration());
        animator.start();
    }

    private void animateOutHorizontal(){
        ValueAnimator animator;
        if(!rotatable.isApplyHorizontal()) {
            animator = ValueAnimator.ofFloat(getHeight(), getHeight() * 2.0f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    pathOut = new Path();
                    pathOut.moveTo(0.0f, (Float) valueAnimator.getAnimatedValue() - paint.getFontMetrics().bottom);
                    pathOut.lineTo(getWidth(), (Float) valueAnimator.getAnimatedValue() - paint.getFontMetrics().bottom);
                    rotatable.setPathOut(pathOut);
                }
            });
        }
        else {
            animator = ValueAnimator.ofFloat(0.0f,getWidth()+10.0f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    pathOut = new Path();
                    pathOut.moveTo((Float) valueAnimator.getAnimatedValue(), 2*getHeight()/3.0f);
                    pathOut.lineTo((Float) valueAnimator.getAnimatedValue() + getWidth(), 2*getHeight()/3.0f);
                    rotatable.setPathOut(pathOut);
                }
            });
        }
        animator.setInterpolator(rotatable.getInterpolator());
        animator.setDuration(rotatable.getAnimationDuration());
        animator.start();
    }

    private void animateInCurve(){
        final int stringLength = rotatable.peekNextWord().length();
        final float[] yValues = new float[stringLength];
        for (int i = 0; i < stringLength; i++) {
            yValues[i] = 0.0f;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, getHeight());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                yValues[0] = (float) valueAnimator.getAnimatedValue() - paint.getFontMetrics().bottom;
                for (int i = 1; i < stringLength; i++) {
                    if (valueAnimator.getAnimatedFraction() > (float) i / (float) (stringLength)) {
                        yValues[i] = (valueAnimator.getAnimatedFraction() - (float) i / (float) (stringLength)) * yValues[0];
                    }
                }

                pathIn = new Path();
                pathIn.moveTo(0.0f, yValues[0]);
                for (int i = 1; i < stringLength; i++) {
                    pathIn.lineTo((getWidth() * ((float) i / (float) stringLength)), yValues[i]);
                    pathIn.moveTo((getWidth() * ((float) i / (float) stringLength)), yValues[i]);
                }
                rotatable.setPathIn(pathIn);
            }
        });
        animator.setInterpolator(rotatable.getInterpolator());
        animator.setDuration(rotatable.getAnimationDuration());
        animator.start();
    }

    private void animateOutCurve(){
        final int stringLength = getText().length();
        final float[] yValues = new float[stringLength];
        for (int i = 0; i < stringLength; i++) {
            yValues[i] = getHeight();
        }
        ValueAnimator animator = ValueAnimator.ofFloat(getHeight(), 2 * getHeight());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                yValues[0] = (float) animator.getAnimatedValue() - paint.getFontMetrics().bottom;
                for (int i = 1; i < stringLength; i++) {
                    if (animator.getAnimatedFraction() > (float) i / (float) (stringLength)) {
                        yValues[i] = (animator.getAnimatedFraction() - (float) i / (float) (stringLength)) * yValues[0];
                    }
                }
                pathIn = new Path();
                pathIn.moveTo(0.0f, yValues[0]);
                for (int i = 1; i < stringLength; i++) {
                    pathIn.lineTo((getWidth() * ((float) i / (float) stringLength)), yValues[i]);
                    pathIn.moveTo((getWidth() * ((float) i / (float) stringLength)), yValues[i]);
                }
                rotatable.setPathIn(pathIn);
            }
        });
        animator.setInterpolator(rotatable.getInterpolator());
        animator.setDuration(rotatable.getAnimationDuration());
        animator.start();
    }

    private void pauseRender(){
        if (handler != null){
            handler.removeCallbacks(mHandlerRun);
            handler = null;
        }
    }

    private void resumeRender(){
        if (handler == null){
            handler = new Handler(Looper.getMainLooper());
        }
        handler.postDelayed(mHandlerRun, 1000 / rotatable.getFPS());
    }

    private final Runnable mHandlerRun = new Runnable() {
        @Override
        public void run() {
            handlerInvalidate();
        }
    };

    private void handlerInvalidate(){
        if (handler != null){
            invalidate();
            handler.postDelayed(mHandlerRun, 1000 / rotatable.getFPS());
        }
    }

    private void updatePaint(){
        paint.setTextSize(rotatable.getSize());
        paint.setColor(rotatable.getColor());
        if (rotatable.isCenter()) {
            paint.setTextAlign(Paint.Align.CENTER);
        }

        if (rotatable.getTypeface() != null) {
            paint.setTypeface(rotatable.getTypeface());
        }

        if (updateWordTimer != null) {
            updateWordTimer.cancel();
        }
        updateWordTimer = new Timer();
        updateWordTimer.scheduleAtFixedRate(updateTimerTask, rotatable.getUpdateDuration(), rotatable.getUpdateDuration());
    }

    private final TimerTask updateTimerTask = new TimerTask() {
        @Override
        public void run() {
            WeakReference<Context> context = new WeakReference<>(getContext());
            if (context.get() == null){
                return;
            }
            if (context.get() instanceof Activity){
                ((Activity) context.get()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isPaused){
                            pauseRender();
                        }else {
                            if (currentText.equals(rotatable.getInitialWord()) && rotatable.getCycles() != 0) {
                                rotatable.countCycles(true);
                            }

                            if (rotatable.countCycles(false) >= rotatable.getCycles()+1 && rotatable.getCycles() != 0) {
                                rotatable.setCycles(0);
                                isPaused = true;
                                pauseRender();
                            }else {
                                animationInterface.setAnimationRunning(true);
                                resumeRender();
                                animateInHorizontal();
                                animateOutHorizontal();
                                oldText = currentText;
                                currentText = rotatable.getNextWord();
                            }
                        }
                    }
                });
            }
        }
    };

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public AnimationInterface getAnimationInterface() {
        return animationInterface;
    }

    public void setAnimationInterface(AnimationInterface animationInterface) {
        this.animationInterface = animationInterface;
    }
}
