package com.ranchosoftware.diningjoint;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ranchosoftware.diningjoint.Model.RestaurantFormatter;
import com.ranchosoftware.diningjoint.utility.AlertHelper;
import com.sandiegorestaurantsandbars.diningjoint.R;

public class InviteActivity extends DiningJointActivity {
  public static ProgressDialog Dialog;

  private EditText textSendTo = null;
  private EditText textFrom = null;
  private EditText textYourEmail = null;
  private EditText textMessage = null;
  private Spinner spinner;

  private String toEmailAddress;
  private String fromEmailAddress;
  private String fromName;
  private String message;
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_invite);

    spinner = (Spinner) findViewById(R.id.spinnerStyle);
    ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.style,
        android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);

    textSendTo = (EditText) findViewById(R.id.editTextSendTo);
    textFrom = (EditText) findViewById(R.id.editTextFrom);
    textYourEmail = (EditText) findViewById(R.id.editTextYourEmail);
    textMessage = (EditText) findViewById(R.id.editTextMessage);


    Button preview = (Button) findViewById(R.id.buttonPreview);
    preview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isValidEmailAddress(textSendTo.getText())){
          textSendTo.requestFocus();
          AlertHelper.showAlert(InviteActivity.this, "Send To", "Please enter an email address.");
          return;
        }

        if (textFrom.getText().length() == 0){
          textFrom.requestFocus();
          AlertHelper.showAlert(InviteActivity.this, "From", "Please enter your name.");
          return;
        }
        if (!isValidEmailAddress(textYourEmail.getText())){
          textSendTo.requestFocus();
          AlertHelper.showAlert(InviteActivity.this, "Your Email", "Please enter your email address.");
          return;
        } ;

        if (textMessage.getText().length() == 0){
          textMessage.requestFocus();
          AlertHelper.showAlert(InviteActivity.this, "From", "Please enter a message.");
          return;
        }

        toEmailAddress = textSendTo.getText().toString().trim();
        fromEmailAddress = textFrom.getText().toString().trim();
        fromName = textFrom.getText().toString().trim();
        message = textMessage.getText().toString().trim();

        getModel().setInviteCardBitmap(formInviteBitmap());
        getModel().setInviteToEmailAddress(toEmailAddress);
        getModel().setInviteFromEmailAddress(fromEmailAddress);
        getModel().setInviteFromName(fromName);
        getModel().setInviteMessage(message);

        launch(InviteCardActivity.class);
      }
    });



  }

  private Bitmap formInviteBitmap(){
    int drawableResId = R.drawable.coffee;
    switch (spinner.getSelectedItemPosition()) {
      case 0:
        // coffee
        drawableResId = R.drawable.coffee;
        break;
      case 1:
        // cocktails
        drawableResId = R.drawable.cocktails;
        break;
      case 2:
        // business
        drawableResId = R.drawable.business;
        break;
      case 3:
        // casual dining
        drawableResId = R.drawable.leopard;
        break;
      case 4:
        // romantic
        drawableResId = R.drawable.romantic;
        break;
      case 5:
        // fine dining
        drawableResId = R.drawable.fine_dining;
        break;
    }



    Bitmap image = BitmapFactory.decodeResource(getResources(), drawableResId);
    Bitmap mutableBitmap = image.copy(Bitmap.Config.ARGB_8888, true);

    float x = mutableBitmap.getWidth()/2.0f;
    float y = 0.15f * mutableBitmap.getHeight();
    float yDelta1 = 0.07f * mutableBitmap.getHeight();
    float yDelta2 = 0.14f * mutableBitmap.getHeight();


    Paint p = new Paint();
    p.setTextSize(.80f * yDelta1);
    p.setTypeface(Typeface.SERIF);

    Canvas canvas = new Canvas(mutableBitmap);
    // 800 x 486
    // canvas.drawText(messageS, 420, 70, p);
    RestaurantFormatter formatter = new RestaurantFormatter( getModel().getSelectedRestaurant());
    canvas.drawText(fromName + " invites you to:", x, y, p);
   // canvas.drawText(fromName + " inveeeites you to:", 420, 70, p);
    y += yDelta1;
    canvas.drawText(formatter.name(), x, y, p);
    y += yDelta2;
    canvas.drawText(message, x, y, p);
  //  canvas.drawText(fromEmailAddress, 600, 330, p);
    // if (child.getPicture() != null) {
    // Bitmap scaledImageChild = resizeImage(child.getPicture());

    // canvas.drawBitmap(scaledImageChild, 24, 29, p);
    // canvas.drawBitmap(mutableBitmap, 24, 29, p);

    // }
    // return template;
    return mutableBitmap;

  }


  public final static boolean isValidEmailAddress(CharSequence target) {
    if (TextUtils.isEmpty(target)) {
      return false;
    } else {
      return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
  }


  private void getDialog()
  {
    AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
    alertbox.setMessage("First fill all the fields");
    alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener()
    {

      // click listener on the alert box
      public void onClick(DialogInterface arg0, int arg1)
      {
        // the button was clicked
        arg0.dismiss();

      }
    });
    alertbox.show();

  }
}
