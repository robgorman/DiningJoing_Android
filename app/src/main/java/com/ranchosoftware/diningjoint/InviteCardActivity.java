package com.ranchosoftware.diningjoint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sandiegorestaurantsandbars.diningjoint.R;

public class InviteCardActivity extends DiningJointActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_invite_card);

    Button cancel = (Button) findViewById(R.id.buttonCancel);
    Button send = (Button) findViewById(R.id.buttonSend);
    ImageView card = (ImageView) findViewById(R.id.ivCard);


    card.setImageBitmap(getModel().getInviteCardBitmap());

    cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    send.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendCard();
      }
    });
  }

  private void sendCard()
  {
    String path = MediaStore.Images.Media.insertImage(getContentResolver(), getModel().getInviteCardBitmap(), "cardBitmap" + Math.random(),
            null);
    Uri screenshotUri = Uri.parse(path);
    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { getModel().getInviteToEmailAddress() });
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Invitation From DiningJoint");
    emailIntent.putExtra(Intent.EXTRA_TEXT, getModel().getInviteMessage());
    emailIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
    emailIntent.setType("image/jpg");

    try
    {
      startActivity(Intent.createChooser(emailIntent, "Send mail"));
      finish();
    }
    catch (android.content.ActivityNotFoundException ex)
    {
      Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
    }
  }
}
