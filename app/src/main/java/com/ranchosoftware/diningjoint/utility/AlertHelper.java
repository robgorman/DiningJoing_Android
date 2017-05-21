package com.ranchosoftware.diningjoint.utility;


import android.content.Context;

/**
 * Created by rob on 2/19/15.
 */
public class AlertHelper {

  public static void showAlert(Context context, String title, String message){

    new CustomAlertDialog(context, title, message).show();

  }
}
