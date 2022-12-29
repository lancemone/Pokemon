package com.timothy.common.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.timothy.common.R;
import com.timothy.common.utils.UIUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <a href="https://github.com/shehuan/NiceImageView">...</a>
 */

public class CircleOrFilletImageView extends AppCompatImageView {

    public static final int CIRCLE = 0;
    public static final int FILLET = 1;

    private final Context context;

    private boolean isCircle = false;   // 是否显示为圆形，如果为圆形则设置的corner无效
    private boolean isCoverImage = false;   // border、inner_border是否覆盖图片
    private int borderColor; // 边框颜色

    private int borderWidth;
    private int innerBorderWidth; // 内层边框宽度
    private int innerBorderColor; // 内层边框充色
    private int cornerRadius; // 统一设置圆角半径，优先级高于单独设置每个角的半径
    private int cornerTopLeftRadius; // 左上角圆角半径
    private int cornerTopRightRadius; // 右上角圆角半径
    private int cornerBottomLeftRadius; // 左下角圆角半径
    private int cornerBottomRightRadius; // 右下角圆角半径
    private int maskColor; // 遮罩颜色
    private final Xfermode xfermode;
    private int width;
    private int height;
    private float radius;

    private final float[] borderRadii;
    private final float[] srcRadii;

    private RectF srcRectF; // 图片占的矩形区域
    private final RectF borderRectF; // 边框的矩形区域

    private final Paint paint;
    private final Path path; // 用来裁剪图片的ptah
    private final Path srcPath; // 图片区域大小的path

    public CircleOrFilletImageView(@NonNull Context context) {
        this(context, null);
    }

    public CircleOrFilletImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleOrFilletImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        try (TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleOrFilletImageView, 0, 0)) {
            for (int i = 0; i < array.getIndexCount(); i++) {
                int attr = array.getIndex(i);
                if (attr == R.styleable.CircleOrFilletImageView_is_cover_image) {
                    isCoverImage = array.getBoolean(attr, isCoverImage);
                } else if (attr == R.styleable.CircleOrFilletImageView_image_shape) {
                    isCircle = array.getInteger(attr, FILLET) == CIRCLE;
                } else if (attr == R.styleable.CircleOrFilletImageView_border_width) {
                    borderWidth = array.getDimensionPixelSize(attr, 0);
                } else if (attr == R.styleable.CircleOrFilletImageView_border_color) {
                    borderColor = array.getColor(attr, borderColor);
                } else if (attr == R.styleable.CircleOrFilletImageView_inner_border_width) {
                    innerBorderWidth = array.getDimensionPixelSize(attr, innerBorderWidth);
                } else if (attr == R.styleable.CircleOrFilletImageView_inner_border_color) {
                    innerBorderColor = array.getColor(attr, innerBorderColor);
                } else if (attr == R.styleable.CircleOrFilletImageView_corner_radius) {
                    cornerRadius = array.getDimensionPixelSize(attr, cornerRadius);
                } else if (attr == R.styleable.CircleOrFilletImageView_corner_top_left_radius) {
                    cornerTopLeftRadius = array.getDimensionPixelSize(attr, cornerTopLeftRadius);
                } else if (attr == R.styleable.CircleOrFilletImageView_corner_top_right_radius) {
                    cornerTopRightRadius = array.getDimensionPixelSize(attr, cornerTopRightRadius);
                } else if (attr == R.styleable.CircleOrFilletImageView_corner_bottom_left_radius) {
                    cornerBottomLeftRadius = array.getDimensionPixelSize(attr, cornerBottomLeftRadius);
                } else if (attr == R.styleable.CircleOrFilletImageView_corner_bottom_right_radius) {
                    cornerBottomRightRadius = array.getDimensionPixelSize(attr, cornerBottomRightRadius);
                } else if (attr == R.styleable.CircleOrFilletImageView_mask_color) {
                    maskColor = array.getColor(attr, maskColor);
                }
            }
            array.recycle();
        }
        borderRadii = new float[8];
        srcRadii = new float[8];

        borderRectF = new RectF();
        srcRectF = new RectF();

        paint = new Paint();
        path = new Path();
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        srcPath = new Path();

        calculateRadii();
        clearInnerBorderWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 使用图形混合模式来显示指定区域的图片
        canvas.saveLayer(srcRectF, null);
        if (!isCoverImage){
            float sx = 1.0f * (width - 2 * borderWidth - 2 * innerBorderWidth) / width;
            float sy = 1.0f * (height - 2 * borderWidth - 2 * innerBorderWidth) / height;
            // 缩小画布，使图片内容不被borders覆盖
            canvas.scale(sx, sy, width / 2.0f, height / 2.0f);
        }

        super.onDraw(canvas);
        paint.reset();
        path.reset();

        if (isCircle){
            path.addCircle(width / 2.0f, height / 2.0f, radius, Path.Direction.CCW);
        }else {
            path.addRoundRect(srcRectF, srcRadii, Path.Direction.CCW);
        }

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(xfermode);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1){
            canvas.drawPath(path, paint);
        }else {
            srcPath.addRect(srcRectF, Path.Direction.CCW);
            srcPath.op(path, Path.Op.DIFFERENCE);   // 计算tempPath和path的差集
            canvas.drawPath(srcPath, paint);
        }
        paint.setXfermode(null);
        if (maskColor != 0){
            paint.setColor(maskColor);
            canvas.drawPath(path, paint);
        }
        // 恢复画布
        canvas.restore();
        drawBorders(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        initBorderRectF();
        initSrcRectF();
    }

    private void drawBorders(Canvas canvas) {
        if (isCircle) {
            if (borderWidth > 0) {
                drawCircleBorder(canvas, borderWidth, borderColor, radius - borderWidth / 2.0f);
            }
            if (innerBorderWidth > 0) {
                drawCircleBorder(canvas, innerBorderWidth, innerBorderColor, radius - borderWidth - innerBorderWidth / 2.0f);
            }
        } else {
            if (borderWidth > 0) {
                drawRectFBorder(canvas, borderWidth, borderColor, borderRectF, borderRadii);
            }
        }
    }

    private void drawCircleBorder(Canvas canvas, int borderWidth, int borderColor, float radius){
        initBorderPaint(borderWidth, borderColor);
        path.addCircle(width / 2.0f, height / 2.0f, radius, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }

    private void drawRectFBorder(Canvas canvas, int borderWidth, int borderColor, RectF rectF, float[] radii){
        initBorderPaint(borderWidth, borderColor);
        path.addRoundRect(rectF, radii, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }

    private void initBorderPaint(int borderWidth, int borderColor) {
        path.reset();
        paint.setStrokeWidth(borderWidth);
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 计算RectF的圆角半径
     */
    private void calculateRadii(){
        if (isCircle){
            return;
        }

        if (cornerRadius > 0){
            for (int i = 0; i < borderRadii.length; i++){
                borderRadii[i] = cornerRadius;
                srcRadii[i] = cornerRadius - borderWidth / 2f;
            }
        }else {
            borderRadii[0] = borderRadii[1] = cornerTopLeftRadius;
            borderRadii[2] = borderRadii[3] = cornerTopRightRadius;
            borderRadii[4] = borderRadii[5] = cornerBottomRightRadius;
            borderRadii[6] = borderRadii[7] = cornerBottomLeftRadius;

            srcRadii[0] = srcRadii[1] = cornerTopLeftRadius - borderWidth / 2.0f;
            srcRadii[2] = srcRadii[3] = cornerTopRightRadius - borderWidth / 2.0f;
            srcRadii[4] = srcRadii[5] = cornerBottomRightRadius - borderWidth / 2.0f;
            srcRadii[6] = srcRadii[7] = cornerBottomLeftRadius - borderWidth / 2.0f;
        }
    }

    /**
     * 目前圆角矩形情况下不支持inner_border，需要将其置0
     */
    private void clearInnerBorderWidth(){
        if (!isCircle){
            innerBorderWidth = 0;
        }
    }

    /**
     * 计算外边框的RectF
     */
    private void initBorderRectF() {
        if (!isCircle) {
            borderRectF.set(borderWidth / 2.0f, borderWidth / 2.0f, width - borderWidth / 2.0f, height - borderWidth / 2.0f);
        }
    }

    /**
     * 计算图片原始区域的RectF
     */
    private void initSrcRectF() {
        if (isCircle) {
            radius = Math.min(width, height) / 2.0f;
            srcRectF.set(width / 2.0f - radius, height / 2.0f - radius, width / 2.0f + radius, height / 2.0f + radius);
        } else {
            srcRectF.set(0, 0, width, height);
            if (isCoverImage) {
                srcRectF = borderRectF;
            }
        }
    }

    private void calculateRadiiAndRectF(boolean reset) {
        if (reset) {
            cornerRadius = 0;
        }
        calculateRadii();
        initBorderRectF();
        invalidate();
    }

    public void setImageShape(@ImageShapeType int imageShape){
        this.isCircle = imageShape == CIRCLE;
        clearInnerBorderWidth();
        initSrcRectF();
        invalidate();
    }

    public boolean isCircle(){
        return isCircle;
    }

    public void isCoverImage(boolean isCoverImage) {
        this.isCoverImage = isCoverImage;
        initSrcRectF();
        invalidate();
    }

    public void setBorderWidthDp(int borderWidth) {
        this.borderWidth = UIUtils.dp2px(context, borderWidth);
        calculateRadiiAndRectF(false);
    }

    public void setBorderWidthPx(int borderWidth) {
        this.borderWidth = borderWidth;
        calculateRadiiAndRectF(false);
    }

    public void setBorderColor(@ColorInt int borderColor) {
        this.borderColor = borderColor;
        invalidate();
    }

    public void setInnerBorderWidthDp(int innerBorderWidth) {
        this.innerBorderWidth = UIUtils.dp2px(context, innerBorderWidth);
        clearInnerBorderWidth();
        invalidate();
    }

    public void setInnerBorderWidthPx(int innerBorderWidth) {
        this.innerBorderWidth = innerBorderWidth;
        clearInnerBorderWidth();
        invalidate();
    }

    public void setInnerBorderColor(@ColorInt int innerBorderColor) {
        this.innerBorderColor = innerBorderColor;
        invalidate();
    }

    public void setCornerRadiusDp(int cornerRadius) {
        this.cornerRadius = UIUtils.dp2px(context, cornerRadius);
        calculateRadiiAndRectF(false);
    }

    public void setCornerTopLeftRadiusDp(int cornerTopLeftRadius) {
        this.cornerTopLeftRadius = UIUtils.dp2px(context, cornerTopLeftRadius);
        calculateRadiiAndRectF(true);
    }

    public void setCornerTopRightRadiusDp(int cornerTopRightRadius) {
        this.cornerTopRightRadius = UIUtils.dp2px(context, cornerTopRightRadius);
        calculateRadiiAndRectF(true);
    }

    public void setCornerBottomLeftRadiusDp(int cornerBottomLeftRadius) {
        this.cornerBottomLeftRadius = UIUtils.dp2px(context, cornerBottomLeftRadius);
        calculateRadiiAndRectF(true);
    }

    public void setCornerBottomRightRadiusDp(int cornerBottomRightRadius) {
        this.cornerBottomRightRadius = UIUtils.dp2px(context, cornerBottomRightRadius);
        calculateRadiiAndRectF(true);
    }

    public void setMaskColor(@ColorInt int maskColor) {
        this.maskColor = maskColor;
        invalidate();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CIRCLE, FILLET})
    private @interface ImageShapeType{}
}
