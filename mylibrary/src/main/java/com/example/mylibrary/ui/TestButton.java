package com.example.mylibrary.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

public class TestButton extends TextView {
    private static final String TAG = "TestButton";
    private int mScaledTouchSlop;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;


    private int mInitialX=0;
    private int mInitialY = 0;

    public TestButton(Context context) {
        this(context, null);
    }

    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext())
                .getScaledTouchSlop();
        Log.d(TAG, "sts:" + mScaledTouchSlop);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mInitialX = (int)getX();
        mInitialY = (int)getY();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDesiredMeasureSpec(widthMeasureSpec),
                getDesiredMeasureSpec(heightMeasureSpec));
    }

    private int getDesiredMeasureSpec(int measureSpec){
        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);

        int result = 200;
        if (measureMode == MeasureSpec.EXACTLY){
            result = measureSize;
        }else if (measureMode == MeasureSpec.AT_MOST){
            result = Math.min(result , measureSize);

        }else {

        }
        return MeasureSpec.makeMeasureSpec(result,measureMode);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            int deltaX = x - mLastX;
            int deltaY = y - mLastY;
            Log.d(TAG, "move, deltaX:" + deltaX + " deltaY:" + deltaY);
//            int translationX = (int)ViewHelper.getTranslationX(this) + deltaX;
//            int translationY = (int)ViewHelper.getTranslationY(this) + deltaY;
//            ViewHelper.setTranslationX(this, translationX);
//            ViewHelper.setTranslationY(this, translationY);
            offsetLeftAndRight(deltaX);
            offsetTopAndBottom(deltaY);

            break;
        }
        case MotionEvent.ACTION_UP: {

            setX(mInitialX);
            setY(mInitialY);

            break;
        }
        default:
            break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

}
