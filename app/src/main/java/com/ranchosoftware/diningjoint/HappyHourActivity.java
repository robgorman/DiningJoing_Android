package com.ranchosoftware.diningjoint;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ranchosoftware.diningjoint.Model.Restaurant;
import com.ranchosoftware.diningjoint.ui.Hud;
import com.sandiegorestaurantsandbars.diningjoint.R;


public class HappyHourActivity extends DiningJointActivity
{

  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
   // boolean wf = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.activity_happy_hour);
    //if (wf)
   //   getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

    Hud.on(this);
    Restaurant restaurant = getModel().getSelectedRestaurant();

    WebView happyHour = (WebView) findViewById(R.id.webViewHappyHour);

    // TODO this url should come from the server
    String url = "http://www.sandiegorestaurantsandbars.com/mobile_specials.php?joint_id="
        + restaurant.getId();
    //happyHour.getSettings().setLoadWithOverviewMode(true);
    //happyHour.getSettings().setUseWideViewPort(true);
    happyHour.getSettings().setJavaScriptEnabled(true);
    happyHour.setWebViewClient(new WebViewClient()
    {
      @Override
      public void onPageFinished(WebView view, String url)
      {
       
       Hud.off(HappyHourActivity.this);
      
      }
    });
    happyHour.setVisibility(View.VISIBLE);
    happyHour.loadUrl(url);
  }
}
