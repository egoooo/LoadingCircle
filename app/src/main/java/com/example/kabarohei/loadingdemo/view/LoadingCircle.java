package com.example.kabarohei.loadingdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.kabarohei.loadingdemo.R;

/**
 * Created by kabarohei on 17/3/29.
 */

public class LoadingCircle extends View {
    private final String TAG = "LoadingView";
    private Paint mTestPaint;
    private Paint mOuterPaint;
    private RectF mOuterRectF;
    private Paint mInnerPaint;
    private RectF mInnerRectF;
    /**
     * mStart 起始值 弧度 mSweep 终点值 弧度
     */
    int mStart = 0;
    int mSweep = 50;

    int mWidth, mHeight;
    int mArcLenght = 60;
    /**
     * outer width
     */
    int mOuterWidth;
    /**
     * inner width
     */
    int mInnerWidth;
    /**
     * outer color
     */
    int mOuterColor;
    /**
     * inner color
     */
    int mInnerColor;
    /**
     * inner rotating speed
     */
    int mInnerRotatingSpeed = 1;
    /**
     * loading的模式
     */
    LoadingMode mLoadingMode = LoadingMode.ARC;
    /**
     * Circle degree
     */
    int mCircle = 0;

    /**
     * Circle speed,only mode==circle
     */
    int mCircleSpeed = 1;

    /**
     * custom degree,only mode==custom
     */
    int mCustomDegree = 0;

    enum LoadingMode {
        ARC, CIRCLE, CUSTOM;
    }

    public LoadingCircle(Context context) {
        this(context, null);
    }

    public LoadingCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);

    }

    private void setBounds() {
        mOuterRectF = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight()
                - getPaddingBottom());
        mInnerRectF = new RectF(mOuterRectF);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setBounds();
        initPaint();
    }

    private void initPaint() {
        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeWidth(mOuterWidth);
        //
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeWidth(mInnerWidth);
        //
        mTestPaint = new Paint();
        mTestPaint.setAntiAlias(true);
        mTestPaint.setColor(Color.BLACK);
        mTestPaint.setStyle(Paint.Style.STROKE);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Loading);
        mOuterWidth = array.getDimensionPixelSize(R.styleable.Loading_outer_width, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        mOuterColor = array.getColor(R.styleable.Loading_outer_color, Color.GRAY);
        mInnerWidth = array.getDimensionPixelOffset(R.styleable.Loading_inner_width, 0);
        mInnerColor = array.getColor(R.styleable.Loading_inner_color, Color.BLACK);
        mInnerRotatingSpeed = array.getInt(R.styleable.Loading_inner_rotating_speed, 1);
        if (array.getInt(R.styleable.Loading_mode, 0) == 1) {
            mLoadingMode = LoadingMode.ARC;
        } else if (array.getInt(R.styleable.Loading_mode, 0) == 2) {
            mLoadingMode = LoadingMode.CIRCLE;
            mCircleSpeed = array.getInt(R.styleable.Loading_inner_circle_speed, 1);
        } else if (array.getInt(R.styleable.Loading_mode, 0) == 3) {
            mLoadingMode = LoadingMode.CUSTOM;
            mCustomDegree = array.getInt(R.styleable.Loading_inner_custom_degree, 1);
        }
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // canvas.drawRect(mOuterRectF, mTestPaint);
        canvas.drawArc(mOuterRectF, 360, 360, false, mOuterPaint);
        if (mLoadingMode == LoadingMode.ARC) {
            canvas.drawArc(mInnerRectF, mStart, mSweep, false, mInnerPaint);
            // int d = mRandom.nextInt(8);
            mStart += mInnerRotatingSpeed;
            Log.i("angle_arc",mStart+"");
            if (mStart > 360) {
                mStart -= 360;
            }
            invalidate();
        } else if (mLoadingMode == LoadingMode.CIRCLE) {
            canvas.drawArc(mInnerRectF, mStart, mCircle, false, mInnerPaint);
            mCircle += mCircleSpeed;
            Log.i("angle_circle",mCircle+"");
            if (mCircle > 360) {
                mCircle -= 360;
            }
            invalidate();
        } else if (mLoadingMode == LoadingMode.CUSTOM) {
            float f = (float) mCustomDegree / 100f;
            canvas.drawArc(mInnerRectF, mStart, 360f * f, false, mInnerPaint);
        }

    }

}
