package com.timothy.rotatingtext;

public class AnimationInterface {
    // 当前动画状态
    private boolean animationRunning;
    private AnimationListener animationListener;

    public AnimationInterface(){}

    public AnimationInterface(boolean animationRunning) {
        this.animationRunning = animationRunning;
    }

    public AnimationInterface(boolean animationRunning, AnimationListener animationListener) {
        this.animationRunning = animationRunning;
        this.animationListener = animationListener;
    }

    public boolean isAnimationRunning() {
        return animationRunning;
    }

    public void setAnimationRunning(boolean animationRunning) {
        this.animationRunning = animationRunning;
        if (animationListener != null){
            animationListener.onAnimationStatusChanged(animationRunning);
        }
    }

    public AnimationListener getAnimationListener() {
        return animationListener;
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.animationListener = animationListener;
    }

    public interface AnimationListener{
        void onAnimationStatusChanged(boolean status);
    }
}
