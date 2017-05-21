package com.ranchosoftware.diningjoint;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

public class SubscribeTask extends AsyncTask<Void, Void,Boolean>
{
  
  public interface Callback
  {
    void results(Boolean results, String errorMessage);
  }
  
  private Callback callback; 
  private String emailAddress; 
  private String errorMessage;
  
  public SubscribeTask(String emailAddress, Callback callback)
  {
    this.callback = callback;
    this.emailAddress = emailAddress;
    this.errorMessage = null; 
  }

  @Override
  protected Boolean doInBackground(Void... params)
  {
    Boolean result = true;
    try
    {
      URL url;
   
      url = new URL(
            "http://us2.api.mailchimp.com/1.3/?method=listSubscribe&apikey=e7dbd25ccf68858523e87ed914a88da9-us2&id=40a8f37a2c&email_address="
                + emailAddress + "&double_optin=false");
      
      url.openStream();
    }
    catch (MalformedURLException e)
    {
      
      result = false; 
      errorMessage = e.getMessage(); 
    }
    catch (IOException e)
    {
      result = false; 
      errorMessage = e.getMessage(); 
    }
     
   
    return result; 
  }

  @Override
  protected void onPostExecute(Boolean result)
  {
    callback.results(result, errorMessage);
    
  }
}
