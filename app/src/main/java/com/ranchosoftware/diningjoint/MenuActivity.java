package com.ranchosoftware.diningjoint;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ranchosoftware.diningjoint.Model.Restaurant;
import com.ranchosoftware.diningjoint.ui.Hud;
import com.sandiegorestaurantsandbars.diningjoint.R;


public class MenuActivity extends DiningJointActivity
{

  
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu);

    Hud.on(this);

    Restaurant restaurant = getModel().getSelectedRestaurant();

    WebView menu = (WebView) this.findViewById(R.id.webViewMenu);

    // TODO move the url to server

    String menuUrl = "http://www.sandiegorestaurantsandbars.com/restaurant_menus_mob.php?joint_id=" + restaurant.getId();

    menu.getSettings().setLoadWithOverviewMode(true);
    menu.getSettings().setUseWideViewPort(true);
    menu.getSettings().setBuiltInZoomControls(true);
    menu.getSettings().setJavaScriptEnabled(true);
 
    menu.setBackgroundColor(Color.parseColor("#000000"));
    menu.setWebViewClient(new WebViewClient()
    {
      @Override
      public void onPageFinished(WebView view, String url)
      {
        view.loadUrl("javascript:document.body.style.zoom = 2.0;");
       Hud.off(MenuActivity.this);
      }
      
    });
    menu.loadUrl(menuUrl);
    
 
   
  }
}
