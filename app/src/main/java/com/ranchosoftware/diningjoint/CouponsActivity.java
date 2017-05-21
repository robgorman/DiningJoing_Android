package com.ranchosoftware.diningjoint;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ranchosoftware.diningjoint.Model.Restaurant;
import com.sandiegorestaurantsandbars.diningjoint.R;

public class CouponsActivity extends DiningJointActivity {

  private ViewPager pager;
  private PagerAdapter pagerAdapter;

  private Restaurant restaurant;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coupons);


    restaurant = getModel().getSelectedRestaurant();
    pager = (ViewPager) findViewById(R.id.pager);
    pagerAdapter = new CouponsPagerAdapter(getSupportFragmentManager());
    pager.setAdapter(pagerAdapter);
    pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        // When changing pages, reset the action bar actions since they are dependent
        // on which page is currently active. An alternative approach is to have each
        // fragment expose actions itself (rather than the activity exposing actions),
        // but for simplicity, the activity provides the actions in this sample.
        invalidateOptionsMenu();
      }
    });
  }

//
//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    super.onCreateOptionsMenu(menu);
//    getMenuInflater().inflate(R.menu.activity_coupons, menu);
//
//    menu.findItem(R.id.action_previous).setEnabled(pager.getCurrentItem() > 0);
//
//    // Add either a "next" or "finish" button to the action bar, depending on which page
//    // is currently selected.
//    MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
//            (pager.getCurrentItem() == pagerAdapter.getCount() - 1)
//                    ? R.string.action_finish
//                    : R.string.action_next);
//    item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//    return true;
//  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        // Navigate "up" the demo structure to the launchpad activity.
        // See http://developer.android.com/design/patterns/navigation.html for more.
        NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
        return true;

      case R.id.action_previous:
        // Go to the previous step in the wizard. If there is no previous step,
        // setCurrentItem will do nothing.
        pager.setCurrentItem(pager.getCurrentItem() - 1);
        return true;

      case R.id.action_next:
        // Advance to the next step in the wizard. If there is no next step, setCurrentItem
        // will do nothing.
        pager.setCurrentItem(pager.getCurrentItem() + 1);
        return true;
    }

    return super.onOptionsItemSelected(item);
  }


  private class CouponsPagerAdapter extends FragmentStatePagerAdapter {
    public CouponsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return CouponsPageFragment.create(position);
    }

    @Override
    public int getCount() {
      return restaurant.getCoupons().size();
    }
  }

}


