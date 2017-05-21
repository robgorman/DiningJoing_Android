package com.ranchosoftware.diningjoint;

import com.sandiegorestaurantsandbars.diningjoint.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

// cd Workspace/DiningJoint/bin/
// ls
// adb install DiningJoint.apk

public class SplashActivity extends Activity
{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "1kyZ82uFIsidj56TFQWsB51Pc";
    private static final String TWITTER_SECRET = "KqAEr6zHAVYhXjafpzBv2birt9llg51BbrE2CEJOHpx9KbwMIq";


  protected boolean active = true;
  protected int splashTime = 5000; // time to display the activity_splash screen in
                                        // ms

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
    Fabric.with(this, new Twitter(authConfig));

    setContentView(R.layout.activity_splash);

    // thread for displaying the SplashScreen
    Thread splashTread = new Thread()
    {
      @Override
      public void run()
      {
        try
        {
          int waited = 0;
          while (active && (waited < splashTime))
          {
            sleep(100);
            if (active)
            {
              waited += 100;
            }
          }
        }
        catch (InterruptedException e)
        {
          // do nothing
        }
        finally
        {
          Intent intent = new Intent();
          intent.setClass(SplashActivity.this, MainActivity.class);
          SplashActivity.this.startActivity(intent);
          finish();
        }
      }
    };
    splashTread.start();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    if (event.getAction() == MotionEvent.ACTION_DOWN)
    {
      active = false;
    }
    return true;
  }

}
