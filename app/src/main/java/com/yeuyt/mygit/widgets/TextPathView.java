package com.yeuyt.mygit.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.yeuyt.mygit.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class TextPathView extends View{

    public static final int RESTART = 1;
    public static final int REVERSE = 2;

    /**
     * 参照ValueAnimator中的RESTART, REVERSE常量定义的
     */
    @IntDef({RESTART, REVERSE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Repeat{}

    /**
     * 动画播放模式，默认为重复播放模式
     */
    @Repeat
    protected int mRepeatMode = RESTART;

    /**
     * 要绘画文字的路径
     */
    protected Path mTextPath;
    /**
     * 画笔的路径
     */
    protected Path mPenPath;

    /**
     * 绘画路径的画笔
     */
    protected Paint mTextPaint;
    /**
     * 绘画画笔的画笔
     */
    protected Paint mPenPaint;

    /**
     * 是否在绘画过程中展示画笔, 默认为true
     */
    protected boolean mShowPen = true;


    protected ValueAnimator mAnimator;
    protected PathAnimatorListener mListener;

    /**
     * 动画进度
     */
    protected float mProgressAnimator;
    /**
     * 绘画的持续时间, 默认为6秒
     */
    protected int mDuration = 6000;

    /**
     * 用来存放text的完整路径
     */
    protected Path mCompletePath;

    /**
     * 测量mCompletePath
     */
    protected PathMeasure mPathMeasure;

    /**
     * mCompletePath的总长度
     */
    protected float mLengthSum;

    /**
     * 画布的中心坐标
     */
    int centerX, centerY;
    /**
     * 当前绘画位置,用来确定画笔位置
     */
    protected float[] mCurPos = new float[2];

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
        initAttr(context, null);
    }

    public TextPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public TextPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    public TextPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
    }

    protected void initAttr(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextPathView);
        if (array != null) {
            mDuration = array.getInt(R.styleable.TextPathView_duration, mDuration);
            mShowPen = array.getBoolean(R.styleable.TextPathView_showPen, mShowPen);
            mRepeatMode = array.getInt(R.styleable.TextPathView_repeatMode, mRepeatMode);

            mText = array.getString(R.styleable.TextPathView_text);
            if(TextUtils.isEmpty(mText)) {
                mText = "Null 文字";
            }
            mTextSize = array.getDimension(R.styleable.TextPathView_textSize, mTextSize);
            array.recycle();
        }
        initCommon();

    }
    /**
     * 初始化一些公共操作
     */
    private void initCommon() {
        initPaint();
        initPath();

        initMeasure();
    }

    private void initMeasure() {
        mTextPath.reset();
        mCompletePath.reset();

        mTextPaint.setTextSize(mTextSize);

        //TextAlign可以决定text的绘画起点，cneter表示起点在文字bottom的中点
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mTextWidth = mTextPaint.measureText(mText);

        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextHeight = metrics.bottom - metrics.top;

        //参数中的x, y表示mCompletePath在画布中的坐标,
        // 和TextAlign配合使用决定在画布中的绘画起点
        mTextPaint.getTextPath(mText, 0, mText.length(), 0, mTextHeight/2, mCompletePath);
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

        //考虑到边缘溢出画布，应该加个1
        int width = (int)mTextWidth + 1;
        int height = (int)mTextHeight + 1;
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? measureWidth : width,
                (modeHeight == MeasureSpec.EXACTLY) ? measureHeight : height);
    }
    /**
     * 路径的具体绘制方法
     * @param progressAnimator 动画进度，根据动画进度来绘画path
     */
    private void drawPath(float progressAnimator) {

        mTextPath.reset();
        mPenPath.reset();
        float mCurrent = progressAnimator*mLengthSum;

        mPathMeasure.setPath(mCompletePath, false);

        while (mCurrent > mPathMeasure.getLength()) {
            mCurrent = mCurrent - mPathMeasure.getLength();
            mPathMeasure.getSegment(0, mPathMeasure.getLength(), mTextPath, true);
            if (!mPathMeasure.nextContour()) {
                break;
            }
        }
        mPathMeasure.getSegment(0, mCurrent, mTextPath, true);

        if (mShowPen && mCurrent != mLengthSum && mCurrent != 0) {
            mPathMeasure.getPosTan(mCurrent, mCurPos, null);
            drawPen(mCurPos[0], mCurPos[1], mPenPath);
        }
        postInvalidate();
    }


    private void initAnimator(float start, float end, @Repeat int repeatMode, int repeatCount) {
        mAnimator = ValueAnimator.ofFloat(start, end);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float)animation.getAnimatedValue();
                drawPath(progress);
            }
        });

        mAnimator.setDuration(mDuration);
        //设置插补器
        mAnimator.setInterpolator(new LinearInterpolator());
        switch (repeatMode) {
            case RESTART:
                mAnimator.setRepeatMode(ValueAnimator.RESTART);
                break;
            case REVERSE:
                mAnimator.setRepeatMode(ValueAnimator.REVERSE);
                break;
        }
        mAnimator.setRepeatCount(repeatCount);
    }

    private   void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);

        mPenPaint = new Paint(mTextPaint);
        mPenPaint.setStyle(Paint.Style.STROKE);
        mPenPaint.setStrokeWidth(3);
        mPenPaint.setColor(Color.RED);

    }
    protected void initPath() {
        mTextPath = new Path();
        mPenPath = new Path();
        mCompletePath = new Path();

        mPathMeasure = new PathMeasure();
    }


    public void startAnimation(float start,float end) {
        startAnimation(start, end, mRepeatMode, ValueAnimator.INFINITE);
    }

    public void startAnimation(float start, float end, @Repeat int repeatMode, int repeatCount) {
        initAnimator(start, end, repeatMode, repeatCount);

        mAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = w/2;
        centerY = h/2;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShowPen) {
            mPenPath.offset(centerX, centerY);
            canvas.drawPath(mPenPath, mPenPaint);
        }
        mTextPath.offset(centerX, centerY);
        canvas.drawPath(mTextPath, mTextPaint);
    }

    /**
     * 在startAnimation之后才有效
     */
    public void setAnimatorListener(PathAnimatorListener animatorListener) {
        this.mListener = animatorListener;
        mAnimator.removeAllListeners();
        mAnimator.addListener(mListener);

    }
    /**
     * 画笔的具体绘制方法
     */
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
