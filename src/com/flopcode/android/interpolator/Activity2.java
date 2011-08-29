package com.flopcode.android.interpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class Activity2 extends android.app.Activity {

  public static class DummyViewTabFactory implements TabContentFactory {

    private Context fContext;

    public DummyViewTabFactory(Context c) {
      fContext = c;
    }

    @Override
    public View createTabContent(String tag) {
      final View view = new View(fContext);
      view.setMinimumHeight(0);
      view.setMinimumHeight(0);
      return view;
    }

  }

  public class TabHostAdapter extends PagerAdapter implements OnPageChangeListener, OnTabChangeListener {

    private List<Interpolator> fTabs = new ArrayList<Interpolator>();
    private final Context fContext;

    public TabHostAdapter(Context c) {
      fContext = c;
    }

    @Override
    public void destroyItem(View collection, int arg1, Object view) {
      ((ViewGroup) collection).removeView((View) view);
    }

    @Override
    public void finishUpdate(View view) {
    }

    @Override
    public int getCount() {
      return fTabs.size();
    }

    @Override
    public Object instantiateItem(View collection, int i) {
      InterpolatorView newView = new InterpolatorView(fContext);
      final Interpolator interpolator = fTabs.get(i);
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

    // OnPageChangeListener
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      final TabWidget tabWidget = fTabHost.getTabWidget();
      final HorizontalScrollView scroller = (HorizontalScrollView) tabWidget.getParent();
      final View current = tabWidget.getChildAt(position);
      final int xPos = (int)(current.getLeft() + current.getWidth()*positionOffset);
      scroller.smoothScrollTo(xPos, 0);
    }

    @Override
    public void onPageSelected(int arg0) {
      fTabHost.setCurrentTab(arg0);
    }

    // OnTabChangeListener
    @Override
    public void onTabChanged(String tabId) {
      int pos = fTabHost.getCurrentTab();
      fViewPager.setCurrentItem(pos);
    }

    public void addTab(Interpolator i) {
      String n = i.getClass().getSimpleName();
      TabSpec tabSpec = fTabHost.newTabSpec(n);
      TextView tv = new TextView(fContext) {
        @Override
        public void setSelected(boolean selected) {
          super.setSelected(selected);
          if (selected) {
            setTextColor(0xffffffff);
          } else {
            setTextColor(0x70ffffff);
          }
        }
      };
      tv.setText(n);
      tv.setTextSize(32);
      tv.setTextColor(0x70ffffff);
      
      
      tabSpec.setIndicator(tv);
      tabSpec.setContent(new DummyViewTabFactory(fContext));
      fTabs.add(i);
      fTabHost.addTab(tabSpec);
      notifyDataSetChanged();
    }

  }

  private ViewPager fViewPager;
  private TabHost fTabHost;
  private TabHostAdapter fTabHostAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main2);
    List<Interpolator> fInterpolators = Arrays.asList(new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(), new AnticipateInterpolator(), new AnticipateOvershootInterpolator(), new BounceInterpolator(), new CycleInterpolator(2), new DecelerateInterpolator(), new LinearInterpolator(), new OvershootInterpolator());
    fViewPager = (ViewPager) findViewById(R.id.pager);
    fTabHost = (TabHost) findViewById(android.R.id.tabhost);
    fTabHost.setup();
    fTabHost.getTabWidget().setStripEnabled(false);
    fTabHostAdapter = new TabHostAdapter(this);

    fTabHost.setOnTabChangedListener(fTabHostAdapter);
    fViewPager.setAdapter(fTabHostAdapter);
    fViewPager.setOnPageChangeListener(fTabHostAdapter);

    for (Interpolator i : fInterpolators) {
      fTabHostAdapter.addTab(i);
    }
    final TabWidget tabWidget = fTabHost.getTabWidget();
    for (int i=0; i<tabWidget.getChildCount(); i++) {
      final View v = tabWidget.getChildAt(i);
      v.setPadding(20, 0, 0, 0);
      v.setBackgroundDrawable(null);
    }
  }
}
