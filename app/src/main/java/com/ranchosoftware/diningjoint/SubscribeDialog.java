package com.ranchosoftware.diningjoint;

import java.util.regex.Pattern;

import com.sandiegorestaurantsandbars.diningjoint.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class SubscribeDialog extends Dialog implements OnClickListener
{

  public SubscribeDialog(Context context)
  {
    super(context);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.setContentView(R.layout.dialog_subscribe);

    this.setTitle("Subscribe");
    getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);


    // register button callbacks
    Button subscribeButton = (Button) findViewById(R.id.buttonSubscribe);
    subscribeButton.setOnClickListener(this);
    Button cancelButton = (Button) findViewById(R.id.buttonCancel);
    cancelButton.setOnClickListener(this);
  }
  
  // pulled from
  // http://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address-on-android
  public static final Pattern emailAddressPattern
  = Pattern.compile(
      "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
      "\\@" +
      "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
      "(" +
          "\\." +
          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
      ")+"
  );
  private boolean isValidEmailAddress(String email)
  {
    if (email.length() < 0)
      return false; 
    
    Pattern pattern = emailAddressPattern;
    return pattern.matcher(email).matches(); 
  }

  public void onClick(View clicked)
  {
    Log.d(this.getClass().getName(), "Clicked: " + clicked.toString());

    switch (clicked.getId())
    {
    case R.id.buttonCancel:
      // clear the email field
      EditText emailField = (EditText) findViewById(R.id.textEmailAddress);
      emailField.setText("");

      hideKeyboard(emailField);

      this.cancel();
      break;
    case R.id.buttonSubscribe:
      // show progress dialog_subscribe
      final EditText text = (EditText) findViewById(R.id.textEmailAddress);
      String emailAddress = text.getText().toString();
      
      if (isValidEmailAddress(emailAddress))
      {
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Uploading",
            "Please wait", true, false);// not cancelable, TODO: this dialog_subscribe
                                        // needs
        // to be accessible elsewhere

        progressDialog.show();
        SubscribeTask task = new SubscribeTask(emailAddress, new SubscribeTask.Callback()
        {

          @Override
          public void results(Boolean results, String errorMessage)
          {
            progressDialog.hide();
            SubscribeDialog.this.hide();
            // TODO Auto-generated method stub
            if (results == true)
              showResult("Subscription Successfull");
            else
              showResult("Subscription Failed:" + errorMessage);

            hideKeyboard(text);

          }
        });

        task.execute();
      }
      else
      {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Invalid Email Address");
        builder.setMessage("You must enter a valid email address");
     
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) 
            { 
                
            }
         });
        
         builder.show();
      }

      break;
    default:
      Log.e("MailChimp", "Unable to handle onClick for view " + clicked.toString());
    }
  }

  private void hideKeyboard(EditText emailField)
  {
    InputMethodManager imm = (InputMethodManager)SubscribeDialog.this.getContext().getSystemService(
        Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(emailField.getWindowToken(), 0);
  }

  private void showResult(final String message)
  {

    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setMessage(message).setPositiveButton("OK", new Dialog.OnClickListener()
    {
      public void onClick(DialogInterface dialog, int which)
      {
        dialog.dismiss();
      }
    });
    builder.create().show();
  }

}
