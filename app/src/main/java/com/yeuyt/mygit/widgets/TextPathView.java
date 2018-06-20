package com.yeuyt.mygit.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.yeuyt.mygit.R;


public class TextPathView extends PathViewTemplate {
    /**
     * 要绘画的文字
     */
    protected String mText;
    /**
     * 文字大小，默认为100sp
     */
    protected float mTextSize = 100;

    /**
     * 文字宽高
     */
    protected float mTextWidth, mTextHeight;


    public TextPathView(Context context) {
        super(context);
        initTextAttr(context, null);
    }

    public TextPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTextAttr(context, attrs);
    }

    public TextPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTextAttr(context, attrs);
    }

    public TextPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initTextAttr(context, attrs);
    }

    //子类的属性初始化，最好和父类分开；在java中的执行顺序为：
    //初始化父类成员变量，初始化父类构造函数
    //初始化子类成员变量，初始化子类构造函数
    protected void initTextAttr(Context context, AttributeSet attrs) {
        super.initAttr(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextPathView);
        if (array != null) {
            mText = array.getString(R.styleable.TextPathView_text);
            if(TextUtils.isEmpty(mText)) {
                mText = "Null 文字";
            }
            mTextSize = array.getDimension(R.styleable.TextPathView_textSize, mTextSize);
            array.recycle();
        }

        initOperation();
    }

    @Override
    protected void initOperation() {
        mDrawPath.reset();
        mCompletePath.reset();

        mPathPaint.setTextSize(mTextSize);
        //TextAlign可以决定text的绘画起点，
        //cneter表示起点在文字bottom的中点
        mPathPaint.setTextAlign(Paint.Align.CENTER);

        mTextWidth = mPathPaint.measureText(mText);

        Paint.FontMetrics metrics = mPathPaint.getFontMetrics();
        mTextHeight = metrics.bottom - metrics.top;

        //参数中的x, y表示mCompletePath在画布中的坐标,
        // 和TextAlign配合使用决定在画布中的绘画起点
        mPathPaint.getTextPath(mText, 0, mText.length(), 0, mTextHeight/2, mCompletePath);
        mPathMeasure.setPath(mCompletePath, false);

        do {
            mLengthSum +=mPathMeasure.getLength();
        } while (mPathMeasure.nextContour());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? measureWidth : (int)mTextWidth,
                (modeHeight == MeasureSpec.EXACTLY) ? measureHeight : (int)mTextHeight);
    }

    @Override
    protected void drawPath(float progressAnimator) {

        mDrawPath.reset();
        mPenPath.reset();
        float mCurrent = progressAnimator*mLengthSum;

        mPathMeasure.setPath(mCompletePath, false);

        while (mCurrent > mPathMeasure.getLength()) {
            mCurrent = mCurrent - mPathMeasure.getLength();
            mPathMeasure.getSegment(0, mPathMeasure.getLength(), mDrawPath, true);
            if (!mPathMeasure.nextContour()) {
                break;
            }
        }
        mPathMeasure.getSegment(0, mCurrent, mDrawPath, true);

        if (mShowPen && mCurrent != mLengthSum && mCurrent != 0) {
            mPathMeasure.getPosTan(mCurrent, mCurPos, null);
            drawPen(mCurPos[0], mCurPos[1], mPenPath);
        }
        postInvalidate();
    }

    @Override
    protected void drawPen(float x, float y, Path penPath) {
        final float r_nib = 30,r_pen = 100;
        penPath.addCircle(x,y,3, Path.Direction.CCW);
        penPath.moveTo(x, y);
        penPath.lineTo(x + r_nib, y);
        penPath.lineTo(x, y - r_nib);
        penPath.lineTo(x, y);
        penPath.moveTo(x + r_nib, y);
        penPath.lineTo(x + r_nib + r_pen, y - r_pen);
        penPath.lineTo(x + r_pen, y - r_pen - r_nib);
        penPath.lineTo(x, y - r_nib);
    }
}
