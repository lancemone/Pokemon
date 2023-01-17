package com.timothy.common.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.orhanobut.logger.Logger;
import com.timothy.common.utils.BitmapUtils;
import com.timothy.common.R;
import com.timothy.common.constant.LoggerConstant;
import com.timothy.common.utils.UIUtils;

public class SplitterImageView extends AppCompatImageView {

    public static final int DEFAULT_DIVISION_WIDTH_DP = 1;
    public static final int DEFAULT_ROW_COUNT = 3;      // 默认行数
    public static final int DEFAULT_COLUMN_COUNT = 3;   // 默认列数
    private static final String TAG = SplitterImageView.class.getSimpleName();
    private int rowCount;   // 行数
    private int columnCount; // 列数

    private int divisionColor;
    private int divisionWidth;

    private int bitmapWidthPer;     // 每个小bitmap的宽
    private int bitmapHeightPer;    // 每个小bitmap的高
    private Paint divisionPaint;

    public SplitterImageView(@NonNull Context context) {
        this(context, null);
        Log.d(TAG, "SplitterImageView init Context");
    }

    public SplitterImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        Log.d(TAG, "SplitterImageView init Context AttributeSet");
    }

    public SplitterImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "SplitterImageView init Context AttributeSet defStyleAttr");
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SplitterImageView, defStyleAttr, 0);
        divisionWidth = typedArray.getDimensionPixelSize(R.styleable.SplitterImageView_division_width, UIUtils.dp2px(getContext(), DEFAULT_DIVISION_WIDTH_DP));
        divisionColor = typedArray.getColor(R.styleable.SplitterImageView_division_color, Color.WHITE);
        rowCount = typedArray.getInteger(R.styleable.SplitterImageView_row_count, DEFAULT_ROW_COUNT);
        columnCount = typedArray.getInteger(R.styleable.SplitterImageView_column_count, DEFAULT_COLUMN_COUNT);
    }

    /**
     * onMeasure是measure方法引起的回调,而measure方法是父VIew在测量子VIew会调用子的View的measure方法
     * 所以widthMeasureSpec和heightMeasureSpec是父VIew在调用子View的measure方法时计算好的
     * MeasureSpec： 测量规则，由size和mode2个因素组成:
     * size: 就是指定的大小值
     * mode: MeasureSpec.AT_MOST : 对应的是warp_content;
     * MeasureSpec.EXACTLY : 对应的是具体的dp值，match_parent
     * MeasureSpec.UNSPECIFIED: 未定义的，一般用adapter的view的测量中
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        Bitmap srcBitmap = BitmapUtils.drawableToBitmap(getDrawable());
        if (srcBitmap == null) {
            return;
        }
        printLog("MeasuredWidth=" + getMeasuredWidth() + " MeasuredHeight=" + getMeasuredHeight() + " divisionWidth=" + divisionWidth);
        int compressWidth = getMeasuredWidth() - divisionWidth * (columnCount - 1);
        int compressHeight = getMeasuredHeight() - divisionWidth * (rowCount - 1);
        if (compressWidth <= 0 || compressHeight <= 0) {
            throw new RuntimeException("divisionWidth too large");
        }
        printLog("compressWidth=" + compressWidth + " compressHeight=" + compressHeight);
        bitmapWidthPer = (int) (compressWidth / (columnCount * 1.0f));
        bitmapHeightPer = (int) (compressHeight / (rowCount * 1.0f));
        printLog("bitmapWidthPer=" + bitmapWidthPer + " bitmapHeightPer=" + bitmapHeightPer);
        srcBitmap = compressBitmap(srcBitmap, compressWidth, compressHeight);
        Bitmap[][] bitmapMatrix = segmentImage(srcBitmap, rowCount, columnCount);
        if (bitmapMatrix != null) {
            drawBitmapMatrix(canvas, bitmapMatrix);
        } else {
            super.onDraw(canvas);
        }
    }

    private void drawBitmapMatrix(Canvas canvas, Bitmap[][] bitmapMatrix) {
        Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setAntiAlias(true);

        divisionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        divisionPaint.setStyle(Paint.Style.FILL);
        divisionPaint.setColor(divisionColor);
        divisionPaint.setAntiAlias(true);

        for (int r = 0; r < bitmapMatrix.length; r++) {
            Bitmap[] rowBitmaps = bitmapMatrix[r];
            for (int c = 0; c < rowBitmaps.length; c++) {
                Bitmap childBitmap = rowBitmaps[c];
                int srcWidth = childBitmap.getWidth();
                int srcHeight = childBitmap.getHeight();
                printLog("r=" + r + " c=" + c);
                printLog("srcWidth=" + srcWidth + " srcHeight=" + srcHeight);
                int bitmapLeftCoordinate = srcWidth * c + divisionWidth * c;
                int bitmapTopCoordinate = srcHeight * r + divisionWidth * r;
                printLog("bitmapLeftCoordinate=" + bitmapLeftCoordinate + " bitmapTopCoordinate=" + bitmapTopCoordinate);
                canvas.drawBitmap(childBitmap, bitmapLeftCoordinate, bitmapTopCoordinate, bitmapPaint);
            }
        }

        // 画横向的分割线
        for (int i = 1; i < rowCount; i++) {
            int top = bitmapHeightPer * i + divisionWidth * (i - 1);
            RectF rectF = new RectF(0, top, getMeasuredWidth(), top + divisionWidth);
            printLog("draw row division rectF=" + rectF);
            drawDivision(canvas, rectF);
        }
        // 画纵向的分割线
        for (int i = 1; i < columnCount; i++) {
            int left = bitmapWidthPer * i + divisionWidth * (i - 1);
            RectF rectF = new RectF(left, 0, left + divisionWidth, getMeasuredHeight());
            printLog("draw column division rectF=" + rectF);
            drawDivision(canvas, rectF);
        }
    }

    private void drawDivision(Canvas canvas, RectF rectF) {
        canvas.drawRect(rectF, divisionPaint);
    }

    /**
     * 分割图片
     *
     * @return 分割后的bitmap矩阵
     */
    private Bitmap[][] segmentImage(Bitmap bitmap, int row, int column) {
        if (bitmap == null || row <= 0 || column <= 0) {
            return null;
        }
        Bitmap[][] bitmaps = new Bitmap[row][column];
        int widthPer = (int) (bitmap.getWidth() / (column * 1.0f));
        int heightPer = (int) (bitmap.getHeight() / (row * 1.0f));
        for (int r = 0; r < row; r++) {
            Bitmap[] rowBitmaps = new Bitmap[column];
            for (int c = 0; c < column; c++) {
                int xValue = c * widthPer;
                int yValue = r * heightPer;
                Bitmap childBitmap = Bitmap.createBitmap(bitmap, xValue, yValue, widthPer, heightPer);
                rowBitmaps[c] = childBitmap;
            }
            bitmaps[r] = rowBitmaps;
        }
        return bitmaps;
    }

    /**
     * 根据view宽高等比压缩bitmap
     *
     * @param source 原资源
     * @return 压缩后bitmap
     */
    private Bitmap compressBitmap(Bitmap source, int targetWidth, int targetHeight) {
        int srcWidth = source.getWidth();
        int srcHeight = source.getHeight();
        float widthScale = targetWidth * 1.0f / srcWidth;
        float heightScale = targetHeight * 1.0f / srcHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale, 0, 0);
        Bitmap bmpRet = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmpRet);
        Paint paint = new Paint();
        canvas.drawBitmap(source, matrix, paint);
        return bmpRet;
    }

    public int getDivisionWidth() {
        return divisionWidth;
    }

    public void setDivisionWidth(int divisionWidth) {
        this.divisionWidth = divisionWidth;
        invalidate();
    }

    public void setDivisionWidthDP(int divisionWidth) {
        this.divisionWidth = UIUtils.dp2px(getContext(), divisionWidth);
        invalidate();
    }

    public int getDivisionColor() {
        return divisionColor;
    }

    public void setDivisionColor(@ColorInt int divisionColor) {
        this.divisionColor = divisionColor;
        invalidate();
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        invalidate();
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        invalidate();
    }


    private void printLog(String s) {
        Logger.t(LoggerConstant.CUSTOM_VIEW_TAG).d("SplitterImageView-> " + s);
    }
}
