package com.flopcode.android.interpolator;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

public class InterpolatorView extends View {
  private static final float DELTA = 0.001f;
  private Interpolator fInterpolator;
  private Paint fPointPaint;
  private Paint fLinePaint;
  private List<PointF> fPoints = new ArrayList<PointF>();
  private float fMin;
  private float fMax;
  private int fHeight;
  private int fWidth;
  private float fYFactor;

  {
    fPointPaint = new Paint();
    fPointPaint.setColor(0xffffffff);
    fPointPaint.setStyle(Style.STROKE);
    fPointPaint.setStrokeWidth(1f);
    fPointPaint.setAntiAlias(true);
    fLinePaint = new Paint();
    fLinePaint.setColor(0xff707070);
    fLinePaint.setStyle(Style.STROKE);
    fLinePaint.setStrokeWidth(1f);
    fLinePaint.setAntiAlias(true);
  }

  public InterpolatorView(Context context) {
    super(context);
  }

  public InterpolatorView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public InterpolatorView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void setInterpolator(Interpolator interpolator) {
    fInterpolator = interpolator;
    fPoints.clear();
    recalcPoints();
    invalidate();
  }

  private void recalcPoints() {
    fMin = Float.POSITIVE_INFINITY;
    fMax = Float.NEGATIVE_INFINITY;
    float t = 0f;
    fPoints.add(new PointF(0, fInterpolator.getInterpolation(0)));
    t += DELTA;
    while (t < 1.0f) {
      final float y = fInterpolator.getInterpolation(t);
      fMin = Math.min(fMin, y);
      fMax = Math.max(fMax, y);
      fPoints.add(new PointF(t, y));
      t += DELTA;
    }
    fPoints.add(new PointF(1, fInterpolator.getInterpolation(1)));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawARGB(0xff, 0, 0, 0);
    if (fInterpolator == null)
      return;
    
    Rect r = new Rect();
    getGlobalVisibleRect(r);
    fWidth = r.width();
    fHeight = r.height();
    float yRange = fMax - fMin;
    fYFactor = fHeight / yRange;
    canvas.drawRect(0, 0, fWidth, fHeight, fLinePaint);
    canvas.drawRect(0, getY(0), fWidth, getY(0), fLinePaint);
    canvas.drawRect(0, getY(1), fWidth, getY(1), fLinePaint);
    for (PointF current : fPoints) {
      final float y = getY(current.y);
      final float x = getX(current.x);
      canvas.drawLine(x, y, x + 1, y + 1, fPointPaint);
    }
  }

  private float getY(float v) {
    return fHeight - (v - fMin) * fYFactor;
  }

  private float getX(float v) {
    return v * fWidth;
  }
}
