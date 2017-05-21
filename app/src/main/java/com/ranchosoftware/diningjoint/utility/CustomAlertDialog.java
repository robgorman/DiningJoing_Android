package com.ranchosoftware.diningjoint.utility;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sandiegorestaurantsandbars.diningjoint.R;

/**
 * Created by rob on 2/23/15.
 */
public class CustomAlertDialog {

  Dialog dialog;

  public  CustomAlertDialog(final Context context, String title, String message){

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    dialog = new Dialog(context);
    dialog.setContentView(R.layout.custom_alert);
    dialog.setTitle(title);

    TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
    tvMessage.setText(message);



    Button ok = (Button) dialog.findViewById(R.id.buttonOk);

    ok.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dialog.dismiss();
        dialog = null;
      }
    });
  }

  public void show()
  {
    dialog.show();
  }


}
