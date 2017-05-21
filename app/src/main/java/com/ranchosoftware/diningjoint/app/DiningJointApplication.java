package com.ranchosoftware.diningjoint.app;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.ranchosoftware.diningjoint.Model.Model;
import com.twitter.sdk.android.core.TwitterApiErrorConstants;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

/**
 * Created by rob on 6/26/15.
 */
public class DiningJointApplication extends Application {
  private Model model;

  @Override
  public void onCreate(){
    super.onCreate();
    MyVolley.init(this);
    FacebookSdk.sdkInitialize(getApplicationContext());
    TwitterAuthConfig authconfig = new TwitterAuthConfig("mU52n340Ja7oOpTk0CnBrQ", "IYFZE0chpm6bbyV46OeSFjXZ42cGi1subiBTJ9h7k");
    Fabric.with(this, new TwitterCore(authconfig), new TweetComposer());


    model = new Model(getApplicationContext());
  }

  public Model getModel(){
    return model;
  }

}
