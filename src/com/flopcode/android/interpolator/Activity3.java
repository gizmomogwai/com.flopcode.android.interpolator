package com.flopcode.android.interpolator;

import android.view.View;
import android.widget.TabWidget;

public class Activity3 extends Activity2 {

  int calcXPos(TabWidget tabWidget, int position, float positionOffset, android.widget.HorizontalScrollView scroller) {
    int childCount = tabWidget.getChildCount();
    View current = tabWidget.getChildAt(position);
    float middle = scroller.getWidth() / 2.0f;
    float currentMiddle = current.getLeft() + current.getWidth() / 2.0f;
    if (position >= childCount - 1) {
      return (int) (currentMiddle - middle);
    } else {
      View next = tabWidget.getChildAt(position + 1);
      float nextMiddle = next.getLeft() + next.getWidth() / 2.0f;
      return (int) (currentMiddle - middle + (nextMiddle - currentMiddle) * positionOffset);
    }
  }
}
