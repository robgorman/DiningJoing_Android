package com.ranchosoftware.diningjoint;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.sandiegorestaurantsandbars.diningjoint.R;

public class SocialActivity extends DiningJointActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_social);

    View instagram = findViewById(R.id.ibInstagram);
    View facebook = findViewById(R.id.ibFacebook);
    View twitter = findViewById(R.id.ibTwitter);
    View pinterest = findViewById(R.id.ibPinterest);
    View snapchat = findViewById(R.id.ibSnapChat);
    View subscribe = findViewById(R.id.ibSubscribe);

    instagram.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/diningjoint"));
        startActivity(intent);
      }
    });
    facebook.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/DiningJoint"));
        startActivity(intent);
      }
    });
    twitter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/diningjoint"));
        startActivity(intent);
      }
    });
    pinterest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pinterest.com/diningjoint"));
        startActivity(intent);
      }
    });
    snapchat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        launch(SnapChatActivity.class);
      }
    });
    subscribe.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SubscribeDialog dialog = new SubscribeDialog(SocialActivity.this);
        dialog.setOwnerActivity(SocialActivity.this);
        dialog.show();
      }
    });
  }

}
