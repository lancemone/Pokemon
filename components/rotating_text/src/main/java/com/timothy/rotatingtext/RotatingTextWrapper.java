package com.timothy.rotatingtext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RotatingTextWrapper extends RelativeLayout {
    ArrayList<TextView> textViews;
    private String text;
    private ArrayList<Rotatable> rotatableList;
    private List<RotatingAnimationTextView> switcherList;
    private boolean isContentSet = false;

    private Context context;

    private RelativeLayout.LayoutParams lp;

    private int prevId;

    private Typeface typeface;
    private int size = 24;

    private double changedSize = 0;
    private boolean adaptable = false;

    public RotatingTextWrapper(Context context) {
        super(context);
        this.context = context;
    }

    public RotatingTextWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public RotatingTextWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public RotatingTextWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public void setContent(String text, Rotatable... rotatables) {
        this.text = text;
        rotatableList = new ArrayList<>();
        switcherList = new ArrayList<>();
        textViews = new ArrayList<>();
        Collections.addAll(rotatableList, rotatables);
        isContentSet = true;
        requestLayout();
    }

    public void setContent(String text, ArrayList<Rotatable> rotatables) {
        this.text = text;
        rotatableList = new ArrayList<>(rotatables);
        switcherList = new ArrayList<>();
        isContentSet = true;
        requestLayout();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isContentSet) {
            removeAllViews();
            String[] array = text.split("\\?");

            if (array.length == 0) {
                final RotatingAnimationTextView textSwitcher = new RotatingAnimationTextView(context);
                switcherList.add(textSwitcher);
                textSwitcher.setRotatable(rotatableList.get(0));
                textSwitcher.setId(View.generateViewId());
                prevId = textSwitcher.getId();
                lp = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.addRule(CENTER_VERTICAL);
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                addView(textSwitcher, lp);
            }

            for (int i = 0; i < array.length; i++) {
                final TextView textView = new TextView(context);
                textView.setText(array[i]);
                textView.setId(View.generateViewId());
                textView.setTextSize(size);
                textViews.add(textView);

                if (typeface != null) textView.setTypeface(typeface);

                lp = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.addRule(CENTER_VERTICAL);
                if (i == 0)
                    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                else
                    lp.addRule(RIGHT_OF, prevId);
                addView(textView, lp);

                if (i < rotatableList.size()) {
                    final RotatingAnimationTextView textSwitcher = new RotatingAnimationTextView(context);
                    switcherList.add(textSwitcher);
                    textSwitcher.setRotatable(rotatableList.get(i));
                    textSwitcher.setId(View.generateViewId());
                    prevId = textSwitcher.getId();
                    lp = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(CENTER_VERTICAL);
                    lp.addRule(RIGHT_OF, textView.getId());
                    addView(textSwitcher, lp);
                }
            }
            isContentSet = false;
        }
    }

    public void replaceWord(int rotatableIndex, int wordIndex, String newWord) {
        if (!TextUtils.isEmpty(newWord) && (!newWord.contains("\n"))) {

            final RotatingAnimationTextView switcher = switcherList.get(rotatableIndex);
            final Rotatable toChange = rotatableList.get(rotatableIndex);

            Rect result = new Rect();
            Paint paint = getPaint(toChange);
            paint.getTextBounds(toChange.getLargestWord(), 0, toChange.getLargestWord().length(), result);

            double originalSize = result.width();

            String toDeleteWord = toChange.getTextAt(wordIndex);

            result = new Rect();
            paint.getTextBounds(toChange.peekLargestReplaceWord(wordIndex, newWord), 0, toChange.peekLargestReplaceWord(wordIndex, newWord).length(), result);
            double finalSize = result.width();

            if (finalSize < originalSize) {
                //we are replacing the largest word with a smaller new word

                if (toChange.getCurrentWord().equals(toDeleteWord) && switcher.getAnimationInterface().isAnimationRunning()) {
                    //largest word is entering
                    toChange.setTextAt(wordIndex, newWord);

                    switcher.getAnimationInterface().setAnimationListener(new AnimationInterface.AnimationListener() {

                        @Override
                        public void onAnimationStatusChanged(boolean newValue) {
                            if (!switcher.getAnimationInterface().isAnimationRunning()) {
                                switcher.getAnimationInterface().setAnimationListener(null);
                                switcher.getAnimationInterface().setAnimationListener(new AnimationInterface.AnimationListener() {
                                    @Override
                                    public void onAnimationStatusChanged(boolean newValue) {
                                        if (!switcher.getAnimationInterface().isAnimationRunning()) {
                                            switcher.getAnimationInterface().setAnimationListener(null);
                                            setChanges(switcher, toChange);
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else if (toChange.getCurrentWord().equals(toDeleteWord) && !switcher.getAnimationInterface().isAnimationRunning()) {
                    //largest word is the screen waiting for going out
                    toChange.setTextAt(wordIndex, newWord);

                    switcher.getAnimationInterface().setAnimationListener(new AnimationInterface.AnimationListener() {
                        @Override
                        public void onAnimationStatusChanged(boolean newValue) {
                            if (!switcher.getAnimationInterface().isAnimationRunning()) {
                                switcher.getAnimationInterface().setAnimationListener(null);
                                setChanges(switcher, toChange);
                            }
                        }
                    });

                } else if (toChange.getPreviousWord().equals(toDeleteWord)) {
                    // largest word is leaving
                    toChange.setTextAt(wordIndex, newWord);

                    if (!switcher.getAnimationInterface().isAnimationRunning()) {
                        setChanges(switcher, toChange);
                    } else {
                        switcher.getAnimationInterface().setAnimationListener(new AnimationInterface.AnimationListener() {
                            @Override
                            public void onAnimationStatusChanged(boolean newValue) {
                                switcher.getAnimationInterface().setAnimationListener(null);
                                setChanges(switcher, toChange);
                            }

                        });
                    }
                } else {
                    //largest word is not in the screen
                    toChange.setTextAt(wordIndex, newWord);
                    setChanges(switcher, toChange);
                }
            } else {
                toChange.setTextAt(wordIndex, newWord);

                switcher.setText(toChange.getLargestWordWithSpace());//provides space
                if (adaptable && finalSize != originalSize) {
                    int actualPixel = findRequiredPixel();

                    if (adaptable && actualPixel > availablePixels()) {
                        reduceSize((double) actualPixel / (double) availablePixels());
                    }
                }
            }
        }
    }

    private Paint getPaint(Rotatable toChange) {
        Paint paint = new Paint();
        paint.setTextSize(toChange.getSize() * getContext().getResources().getDisplayMetrics().density);
        paint.setTypeface(toChange.getTypeface());
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    public void addWord(int rotatableIndex, int wordIndex, String newWord) {
        if (!TextUtils.isEmpty(newWord) && (!newWord.contains("\n"))) {

            RotatingAnimationTextView switcher = switcherList.get(rotatableIndex);
            Rotatable toChange = rotatableList.get(rotatableIndex);

            Paint paint = getPaint(toChange);
            Rect result = new Rect();

            paint.getTextBounds(toChange.getLargestWord(), 0, toChange.getLargestWord().length(), result);

            double originalSize = result.width();

            result = new Rect();
            paint.getTextBounds(toChange.peekLargestAddWord(wordIndex, newWord), 0, toChange.peekLargestAddWord(wordIndex, newWord).length(), result);
            double finalSize = result.width();
            toChange.addTextAt(wordIndex, newWord);

            switcher.setText(toChange.getLargestWordWithSpace());//provides space
            if (adaptable && finalSize != originalSize) {
                int actualPixel = findRequiredPixel();

                if (adaptable && actualPixel > availablePixels()) {
                    reduceSize((double) actualPixel / (double) availablePixels());
                }
            }
        }
    }

    private void setChanges(RotatingAnimationTextView switcher, Rotatable toChange) {
        switcher.setText(toChange.getLargestWordWithSpace());
        if (adaptable && getSize() != (int) changedSize && changedSize != 0) {
            if ((double) availablePixels() / (double) findRequiredPixel() < getSize() / changedSize)
                reduceSize((double) findRequiredPixel() / (double) availablePixels());
            else reduceSize(changedSize / getSize());
        }
    }

    private int availablePixels() {
        //returns total pixel available with parent
        View parent = (View) getParent();
        return parent.getMeasuredWidth() - parent.getPaddingLeft() - parent.getPaddingRight();
    }

    private int findRequiredPixel() {
        //returns observed wrapper size on screen including padding and margin in pixels
        int actualPixel = 0;
        MarginLayoutParams margins;
        for (RotatingAnimationTextView switcher : switcherList) {
            switcher.measure(0, 0);
            actualPixel += switcher.getMeasuredWidth();
        }
        for (TextView id : textViews) {
            id.measure(0, 0);
            actualPixel += id.getMeasuredWidth();
        }
        margins = (MarginLayoutParams) getLayoutParams();
        actualPixel += margins.leftMargin;
        actualPixel += margins.rightMargin;
        actualPixel += getPaddingLeft();
        actualPixel += getPaddingRight();
        return actualPixel;
    }

    public void reduceSize(double factor) {
        double initialSizeWrapper = (changedSize == 0) ? getSize() : changedSize;

        double newWrapperSize = (double) initialSizeWrapper / factor;

        for (RotatingAnimationTextView switcher : switcherList) {
            double initialSizeRotatable = switcher.getTextSize();

            double newRotatableSize = initialSizeRotatable / factor;
            switcher.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) newRotatableSize);

        }
        for (TextView id : textViews) {
            id.setTextSize((float) newWrapperSize);

        }
        MarginLayoutParams margins = (MarginLayoutParams) getLayoutParams();

        if (margins != null) {
            margins.leftMargin = (int) (margins.leftMargin / factor);
            margins.rightMargin = (int) (margins.rightMargin / factor);
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        if (paddingLeft != 0) {
            if (paddingRight == 0)
                setPadding((int) (paddingLeft / factor), 0, 0, 0);
            else
                setPadding((int) ((paddingLeft / factor)), 0, (int) ((paddingRight / factor)), 0);

        } else if (paddingRight != 0) {
            setPadding(0, 0, (int) (paddingRight / factor), 0);
        }

        changedSize = (changedSize == 0) ? getSize() / factor : changedSize / factor;

        if (adaptable && findRequiredPixel() > availablePixels()) {
            reduceSize((double) findRequiredPixel() / (double) availablePixels());
        }
    }

    public void setAdaptable(boolean adaptable) {
        this.adaptable = adaptable;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void pause(int position) {
        switcherList.get(position).setPaused(true);
    }

    public void resume(int position) {
        switcherList.get(position).setPaused(false);
    }

    public List<RotatingAnimationTextView> getSwitcherList() {
        return switcherList;
    }
}
