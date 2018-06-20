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
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.yeuyt.mygit.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 采用了模板设计模式
 */
public abstract class PathViewTemplate extends View {
    public static final int RESTART = 1;
    public static final int REVERSE = 2;

    /**
     * 参照ValueAnimator中的相应常量定义的
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
     * 要绘画的路径
     */
    protected Path mDrawPath;
    /**
     * 画笔的路径
     */
    protected Path mPenPath;

    /**
     * 绘画路径的画笔
     */
    protected Paint mPathPaint;
    /**
     * 绘画画笔的画笔
     */
    protected Paint mPenPaint;

    /**
     * 是否展示画笔, 默认为true
     */
    protected boolean mShowPen = true;


    protected ValueAnimator mAnimator;


    protected PathAnimatorListener mListener;
    /**
     * 动画进度
     */
    protected float mProgressAnimator;
    /**
     * pathview的动画持续时间, 默认为6秒
     */
    protected int mDuration = 6000;

    /**
     * 用来存放绘画的完整路径
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
    public PathViewTemplate(Context context) {
        super(context);
        initAttr(context, null);
    }

    public PathViewTemplate(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public PathViewTemplate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    public PathViewTemplate(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);

    }

    protected void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PathViewTemplate);
        if (array != null) {
            mDuration = array.getInt(R.styleable.PathViewTemplate_duration, mDuration);
            mShowPen = array.getBoolean(R.styleable.PathViewTemplate_showPen, mShowPen);
            mRepeatMode = array.getInt(R.styleable.PathViewTemplate_repeatMode, mRepeatMode);
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
    }

    /**
     * 初始化一些操作
     */
    protected abstract void initOperation();


    protected void initAnimator(float start, float end, @Repeat  int repeatMode, int repeatCount) {
        mAnimator = ValueAnimator.ofFloat(start, end);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgressAnimator = (float) animation.getAnimatedValue();
                drawPath(mProgressAnimator);
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

    protected  void initPaint() {
        mPathPaint = new Paint();
        mPathPaint.setColor(Color.BLACK);
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(3);

        mPenPaint = new Paint(mPathPaint);
        mPenPaint.setColor(Color.RED);

    }
    protected void initPath() {
        mDrawPath = new Path();
        mPenPath = new Path();
        mCompletePath = new Path();

        mPathMeasure = new PathMeasure();
    }

    /**
     * 路径的具体绘制方法
     * @param progressAnimator 动画进度，根据动画进度来绘画path
     */
    protected abstract void drawPath(float progressAnimator);
    /**
     * 画笔的具体绘制方法
     */
    protected abstract void drawPen(float x, float y, Path penPath);

    public void startAnimation(float start,float end) {
        startAnimation(start, end, mRepeatMode, ValueAnimator.INFINITE);
    }

    public void startAnimation(float start,float end, @Repeat int repeatMode, int repeatCount) {
        initAnimator(start, end, repeatMode, repeatCount);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float)animation.getAnimatedValue();
                drawPath(progress);
            }
        });
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
        mDrawPath.offset(centerX, centerY);
        canvas.drawPath(mDrawPath, mPathPaint);
    }

    /**
     * 在startAnimation之后才有效
     */
    public void setAnimatorListener(PathAnimatorListener animatorListener) {
        this.mListener = animatorListener;
        mAnimator.removeAllListeners();
        mAnimator.addListener(mListener);

    }
}
