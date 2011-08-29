package com.flopcode.android.interpolator;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

public class Activity1 extends android.app.Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main1);
    final List<Interpolator> interpolators = Arrays.asList(new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(), new AnticipateInterpolator(), new AnticipateOvershootInterpolator(), new BounceInterpolator(), new CycleInterpolator(2), new DecelerateInterpolator(), new LinearInterpolator(), new OvershootInterpolator());
    ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
    viewPager.setAdapter(new PagerAdapter() {

      @Override
      public void destroyItem(View collection, int arg1, Object view) {
        ((ViewGroup) collection).removeView((View) view);
      }

      @Override
      public void finishUpdate(View view) {
      }

      @Override
      public int getCount() {
        return interpolators.size();
      }

      @Override
      public Object instantiateItem(View collection, int i) {
        InterpolatorView newView = new InterpolatorView(Activity1.this);
        final Interpolator interpolator = interpolators.get(i);
        newView.setInterpolator(interpolator);
        ((ViewGroup) collection).addView(newView);
        return newView;
      }

      @Override
      public boolean isViewFromObject(View view, Object o) {
        return view == o;
      }

      @Override
      public void restoreState(Parcelable arg0, ClassLoader arg1) {
      }

      @Override
      public Parcelable saveState() {
        return null;
      }

      @Override
      public void startUpdate(View v) {
      }

    });
  }
}
