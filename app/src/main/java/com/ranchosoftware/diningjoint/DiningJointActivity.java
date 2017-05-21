package com.ranchosoftware.diningjoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ranchosoftware.diningjoint.Model.Model;
import com.ranchosoftware.diningjoint.app.DiningJointApplication;
import com.sandiegorestaurantsandbars.diningjoint.R;

/**
 * Created by rob on 6/26/15.
 */
public class DiningJointActivity extends AppCompatActivity {

  protected Activity thisActivity;
  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    ActionBar bar = getSupportActionBar();
    bar.setLogo(R.drawable.icon_action_bar);
    bar.setDisplayUseLogoEnabled(true);
    bar.setDisplayShowHomeEnabled(true);
    bar.setDisplayHomeAsUpEnabled(true);
    bar.setDisplayShowTitleEnabled(false);
    thisActivity = this;
  }

  public Model getModel(){
    DiningJointApplication app = (DiningJointApplication) getApplication();
    return app.getModel();
  }

  public void launch(Class c){
    Intent intent = new Intent(this, c);
    startActivity(intent);
  }

  @Override
  public void onBackPressed()
  {
    super.onBackPressed();
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

  }

  public boolean onOptionsItemSelected(MenuItem item) {

    if (item.getItemId() == android.R.id.home) {
      NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));

      return true;
    }
    return false;
  }

  @Override
  public void startActivity(Intent intent)
  {
    super.startActivity(intent);
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
  }

  @Override
  public void startActivityForResult(Intent intent, int requestCode)
  {
    super.startActivityForResult(intent, requestCode);
    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left );
  }

  @Override
  public void finish()
  {
    super.finish();
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
  }
}
