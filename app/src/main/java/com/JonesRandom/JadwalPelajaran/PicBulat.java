package com.JonesRandom.JadwalPelajaran;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class PicBulat extends Drawable {

    final Bitmap mBitmap;
    final Paint mPaint;
    final RectF mRectF;
    final int mBitmapWidth;
    final int mBitmapHeight;

    public PicBulat(Bitmap bitmap) {
        mBitmap = bitmap;
        mPaint = new Paint();
        mRectF = new RectF();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawOval(mRectF,mPaint);
    }

    @Override
    public void setAlpha(int i) {
        if (mPaint.getAlpha() != i) {
            mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mRectF.set(bounds);
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmapHeight < mBitmapWidth ? mBitmapHeight: mBitmapWidth;
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmapWidth < mBitmapHeight ? mBitmapWidth: mBitmapHeight;
    }

    public void setAntiAlias(boolean aa) {
        mPaint.setAntiAlias(aa);
        invalidateSelf();
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        mPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override
    public void setDither(boolean dither) {
        mPaint.setDither(dither);
        invalidateSelf();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

}
