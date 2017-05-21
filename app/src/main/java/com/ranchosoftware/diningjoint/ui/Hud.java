package com.ranchosoftware.diningjoint.ui;

import android.content.Context;

/**
 * Created by rob on 2/19/15.
 */
public class Hud {



  private static ProgressHud hud;
  public static void on(Context context){

    if (hud == null) {
      hud = ProgressHud.show(context, "", true, false, null);
    }
  }

  public static void off(Context context){
    if (hud != null) {
      hud.dismiss();
      hud = null;
    }
  }
}
