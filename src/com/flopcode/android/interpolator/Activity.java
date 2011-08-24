package com.flopcode.android.interpolator;

import java.util.Arrays;

import android.os.Bundle;
import android.view.View;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;

public class Activity extends android.app.Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    final InterpolatorView interpolatorView = (InterpolatorView) findViewById(R.id.interpolator);
    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    final BaseAdapter adapter = new ArrayAdapter<Interpolator>(this, android.R.layout.simple_spinner_item, Arrays.asList(new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(), new AnticipateInterpolator(), new AnticipateOvershootInterpolator(), new BounceInterpolator(), new CycleInterpolator(2), new DecelerateInterpolator(), new LinearInterpolator(), new OvershootInterpolator()));
    spinner.setAdapter(adapter);
    final OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Interpolator i = (Interpolator) adapter.getItem(pos);
        interpolatorView.setInterpolator(i);
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
        interpolatorView.setInterpolator(null);
      }
    };
    spinner.setOnItemSelectedListener(onItemSelectedListener);
    onItemSelectedListener.onItemSelected(null, null, 0, 0);
  }
}