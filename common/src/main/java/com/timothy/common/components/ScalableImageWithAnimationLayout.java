package com.timothy.common.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ScalableImageWithAnimationLayout extends HorizontalScrollView {

    private int childRadius = 0;

    public ScalableImageWithAnimationLayout(Context context) {
        this(context, null);
    }

    public ScalableImageWithAnimationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalableImageWithAnimationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ScalableImageWithAnimationLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setChildRadiusDP(int radiusDP){
        this.childRadius = dp2px(getContext(), radiusDP);
    }

    private void init(){
        if (childRadius == 0){
            childRadius = dp2px(getContext(), 12);
        }
    }

    private int dp2px(Context context, int dp){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
