package com.ranchosoftware.diningjoint;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sandiegorestaurantsandbars.diningjoint.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends DiningJointActivity {

  private static ArrayList<String> labelsTop = new ArrayList<String>();
  private static ArrayList<String> labelsMiddle = new ArrayList<String>();

  static{
    labelsTop.add("FUNDAY");
    labelsTop.add("MIX IT UP");
    labelsTop.add("TACO");
    labelsTop.add("WINO");
    labelsTop.add("THIRSTY");
    labelsTop.add("FRISKY");
    labelsTop.add("SOCIAL");

    labelsMiddle.add("SUN");
    labelsMiddle.add("MON");
    labelsMiddle.add("TUES");
    labelsMiddle.add("WEDS");
    labelsMiddle.add("THURS");
    labelsMiddle.add("FRI");
    labelsMiddle.add("SAT");

  }

  private ArrayList<View> dayViews = new ArrayList<View>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calendar);

    dayViews.add(findViewById(R.id.rlday0));
    dayViews.add(findViewById(R.id.rlday1));
    dayViews.add(findViewById(R.id.rlday2));
    dayViews.add(findViewById(R.id.rlday3));
    dayViews.add(findViewById(R.id.rlday4));
    dayViews.add(findViewById(R.id.rlday5));
    dayViews.add(findViewById(R.id.rlday6));

    for (int i = 0 ; i <  7; i++){
      int ordinal = dayOfWeekOrdinalFor(i);
      ViewGroup container = (ViewGroup) dayViews.get(i);

      TextView labelTop = (TextView) container.getChildAt(1);
      TextView lableMiddle = (TextView) container.getChildAt(2);

      labelTop.setText(labelsTop.get(ordinal));
      lableMiddle.setText(labelsMiddle.get(ordinal));

      final int position = i;
      container.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          getModel().resetQuery();
          getModel().getQuery().setSearchHappyHour(true);
          getModel().getQuery().setDayOfWeekOrdinal(position);
          getModel().getQuery().setIncludeLocation(true);
          launch(RestaurantListActivity.class);
        }
      });

    }
  }

  private int dayOfWeekOrdinalForToday(){
    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_WEEK);
    return day - 1;
  }

  private int dayOfWeekOrdinalFor(int i){
    int ordinalToday = dayOfWeekOrdinalForToday();
    int newOrdinal = ordinalToday + i;
    return newOrdinal % 7;
  }


}
