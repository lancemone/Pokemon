package com.timothy.common.components.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 摩天轮效果的view
 */
public class FerrisWheelLayout extends ViewGroup {

    /**
     * 该容器内child item的默认尺寸
     */
    private static final float RATIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private static final float RATIO_PADDING_LAYOUT = 1 / 12f;
    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private static final int FLINGABLE_VALUE = 300;
    /**
     * 如果移动角度达到该值，则屏蔽点击
     */
    private static final int NOCLICK_VALUE = 3;
    private int mRadius;
    /**
     * 菜单的中心child的默认尺寸
     */
    private final float RATIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;
    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private final int mFlingableValue = FLINGABLE_VALUE;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private float mPadding;
    /**
     * 布局时的开始角度
     */
    private final double mStartAngle = 0;
    /**
     * 菜单项的文本
     */
    private String[] mItemTexts;

    /**
     * 菜单项的图标
     */
    private int[] mItemImages;
    /**
     * 外部菜单的个数
     */
    private int mMenuItemCount;
    /**
     * 检测按下到抬起时旋转的角度
     */
    private float mTmpAngle;
    /**
     * 检测按下到抬起时使用的时间
     */
    private long mDownTime;
    /**
     * 判断是否正在自动滚动
     */
    private boolean isFling;

    private float ratio_child_dimension = RATIO_DEFAULT_CHILD_DIMENSION;

    private float ratio_center_dimension = RATIO_DEFAULT_CENTERITEM_DIMENSION;

    public FerrisWheelLayout(Context context) {
        this(context, null);
    }

    public FerrisWheelLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FerrisWheelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeData(attrs);
        init();
    }

    private void initAttributeData(AttributeSet attrs) {
    }

    private void init() {
        setPadding(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int resWidth = 0;
        int resHeight = 0;

        if (mRadius <= 0) {
            //  根据传入的参数，分别获取测量模式和测量值
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);

            // 如果宽或者高的测量模式非精确值
            if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
                mRadius = getDefaultRadius();
            } else {
                mRadius = Math.min(width, height);
            }
        }
        resWidth = resHeight = mRadius;
        setMeasuredDimension(resWidth, resHeight);
        // menu item数量
        final int count = getChildCount();
        // menu item尺寸
        int childSize = (int) (mRadius * ratio_child_dimension);
        // menu item测量模式
        int childMode = MeasureSpec.EXACTLY;
        // 测量子View尺寸
        for (int i=0; i< count; i++){
            final View childView = getChildAt(i);
            if (childView.getVisibility() == GONE){
                continue;
            }
            // 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
            int makeMeasureSpec = -1;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private int getDefaultRadius() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public void setMenuItemIconsAndTexts(int[] resIds, String[] texts){
        mItemImages = resIds;
        mItemTexts = texts;
        if (resIds != null && texts != null){
            mMenuItemCount = Math.max(resIds.length, texts.length);
        }else {
            mMenuItemCount = resIds == null ? texts == null ? 0 : texts.length : resIds.length;
        }
        addMenuItems();
    }

    private void addMenuItems(){

    }

    public interface OnMenuItemClickListener {
        void itemClick(View view, int pos);

        void itemCenterClick(View view);
    }
}
