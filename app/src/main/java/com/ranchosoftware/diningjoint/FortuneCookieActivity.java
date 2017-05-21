package com.ranchosoftware.diningjoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import android.os.Bundle;

import android.widget.TextView;


import com.sandiegorestaurantsandbars.diningjoint.R;

public class FortuneCookieActivity extends DiningJointActivity
{

  public String      line;
  public InputStream instream;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fortune);

    try
    {
      instream = getAssets().open("phrases.txt");// openFileInput("phrases.txt");
    }
    catch (IOException e)
    {
      // do something if the myfilename.txt does not exits
    }

    // if file the available for reading
    // if (instream) {
    // prepare the file for reading
    InputStreamReader inputreader = new InputStreamReader(instream);
    BufferedReader buffreader = new BufferedReader(inputreader);

    Boolean isLine = true;
    int to = (int) (Math.random() * 460);
    int i = 0;
    // read every line of the file into the line-variable, on line at the time
    try
    {

      while (isLine)
      {

        // do something with the settings from the file
        line = buffreader.readLine();
        if (i < to)
        {
          i++;
        }
        else
        {
          isLine = false;
        }
      }

      // }

      // close the file again
      instream.close();
    }
    catch (IOException e)
    {
      // do something if the myfilename.txt does not exits
    }
    TextView fortuneLabel = (TextView) findViewById(R.id.fortuneCookieLabel);
    fortuneLabel.setText(line);

  }

}
