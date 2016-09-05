package com.example.santa.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by santa on 16/6/13.
 */
public class SunDrawable extends Drawable {
    private float mRotation;
    private Context mContext;
    private Sun mSun;
    private int mDefaultColor = Color.YELLOW;

    public SunDrawable(Context context) {
        mContext = context;
        mSun = new Sun();
        mSun.setColor(mDefaultColor);
    }

    public void setAnimotion() {
        //AnimatorSet set = new Ani
        //ValueAnimator animator = ObjectAnimator.ofFloat()
    }

    public void setColor(int color) {
        mSun.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();
        final int saveCount = canvas.save();
        canvas.rotate(mRotation, bounds.exactCenterX(), bounds.exactCenterY());
        mSun.draw(canvas, bounds);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    private class Sun{
        private int mAngle = 45;
        private int mColor;
        private Paint mPaint = new Paint();
        private Paint mCirclePaint = new Paint();

        public Sun() {
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeJoin(Paint.Join.ROUND);

            mCirclePaint.setAntiAlias(true);
            mCirclePaint.setStyle(Paint.Style.FILL);
        }

        public int getColor() {
            return mColor;
        }

        public void setColor(int Color) {
            this.mColor = Color;
            mCirclePaint.setColor(mColor);
            mPaint.setColor(mColor);
        }

        public void draw(Canvas canvas, Rect bounds) {
            int CircleRadius = bounds.width() / 4;

            canvas.drawCircle(bounds.exactCenterX(), bounds.exactCenterY(), CircleRadius, mCirclePaint);

            int strokeWidth = CircleRadius / 3;
            mPaint.setStrokeWidth(strokeWidth);

            Path path = new Path();
            int extent = CircleRadius / 5;
            path.moveTo(bounds.exactCenterX() + CircleRadius, bounds.exactCenterY() + CircleRadius);
            path.lineTo(bounds.exactCenterX() + CircleRadius + extent, bounds.exactCenterY() + CircleRadius + extent);
            path.close();
            canvas.drawPath(path, mPaint);

            for (int i = 0; i < 360 / mAngle; i++) {
                canvas.rotate(mAngle, bounds.exactCenterX(), bounds.exactCenterY());
                canvas.drawPath(path, mPaint);
            }



        }
    }
}
