package com.ranchosoftware.diningjoint;


import android.Manifest;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


//import com.arellomobile.android.push.BasePushMessageReceiver;
//import com.arellomobile.android.push.PushManager;
//import com.arellomobile.android.push.utils.RegisterBroadcastReceiver;
import com.facebook.appevents.AppEventsLogger;
import com.pushwoosh.BasePushMessageReceiver;
import com.pushwoosh.BaseRegistrationReceiver;
import com.pushwoosh.PushManager;
import com.ranchosoftware.diningjoint.Model.Model;
import com.ranchosoftware.diningjoint.utility.Permissions;
import com.sandiegorestaurantsandbars.diningjoint.R;

public class MainActivity extends DiningJointActivity {

  private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION_FOR_NEARBY = 1;
  private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION_FOR_FUNPIX = 2;
  private static final String TAG = MainActivity.class.getSimpleName();

  Model model;

  private SensorManager sensorManager;
  private ShakeEventListener shakeEventListener;
  private Sensor accelerometer;

  @Override
  public void onResume() {
    super.onResume();
    // Add the following line to register the Session Manager Listener onResume
    sensorManager.registerListener(shakeEventListener, accelerometer,    SensorManager.SENSOR_DELAY_UI);
    ActionBar bar = getSupportActionBar();
    bar.setDisplayHomeAsUpEnabled(false);
    assert bar != null;
    facebookTrackingActivate();
    //Re-register pushwoosh receivers on resume
    registerReceivers();
  }

  private void facebookTrackingActivate(){
    AppEventsLogger.activateApp(this);
  }

  private void facebookTrackingDeactivate(){
    AppEventsLogger.deactivateApp(this);
  }

  @Override
  public void onPause() {
    // Add the following line to unregister the Sensor Manager onPause
    sensorManager.unregisterListener(shakeEventListener);
    super.onPause();
    facebookTrackingDeactivate();
    //Unregister pushwoosh on pause
    unregisterReceivers();
  }

  private void doPushWooshInitialization(){
    //Register receivers for push notifications
    registerReceivers();

    //Create and start push manager
    PushManager pushManager = PushManager.getInstance(this);

    //Start push manager, this will count app open for Pushwoosh stats as well
    try {
      pushManager.onStartup(this);
    }
    catch(Exception e)
    {
      //push notifications are not available or AndroidManifest.xml is not configured properly
    }

    //Register for push!
    pushManager.registerForPushNotifications();

    checkMessage(getIntent());
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    doPushWooshInitialization();

    model = getModel();
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    accelerometer = sensorManager
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    shakeEventListener  = new ShakeEventListener();
    shakeEventListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
      @Override
      public void onShake() {
        Log.d(TAG, "shake");
        launch(FortuneCookieActivity.class);
      }
    });



    ImageButton find = (ImageButton) findViewById(R.id.ibFind);
    find.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        model.resetQuery();
        launch(SearchActivity.class);

      }
    });

    ImageButton nearby= (ImageButton) findViewById(R.id.ibNearby);
    nearby.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (Permissions.hasPermission(thisActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
          launchRestaurantListActivityForNearby();
        } else{
          Permissions.requestPermission(thisActivity, Manifest.permission.ACCESS_FINE_LOCATION,
                  PERMISSION_REQUEST_ACCESS_FINE_LOCATION_FOR_NEARBY);
        }

      }
    });

    View social = findViewById(R.id.ibSocial);
    social.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        launch(SocialActivity.class);
      }
    });

    View funpix = findViewById(R.id.ibFunPix);
    funpix.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if (Permissions.hasPermission(thisActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
          launchRestaurantListActivityForNearbyFunPix();
        } else{
          Permissions.requestPermission(thisActivity, Manifest.permission.ACCESS_FINE_LOCATION,
                  PERMISSION_REQUEST_ACCESS_FINE_LOCATION_FOR_FUNPIX);
        }



      }
    });

    View happyHour = findViewById(R.id.ibHappyHour);
    happyHour.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        launch(CalendarActivity.class);
      }
    });

    ImageButton tipCalculator = (ImageButton) findViewById(R.id.ibTipCalculator);
    tipCalculator.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        launch(TipCalculatorActivity.class);
      }
    });
  }

  private void launchRestaurantListActivityForNearbyFunPix(){
    model.resetQuery();
    model.getQuery().setSearchFunpix(true);
    model.getQuery().setIncludeLocation(true);
    //model.getQuery().setSearchCoupons(true);
    launch(RestaurantListActivity.class);
  }
  private void launchRestaurantListActivityForNearby(){
    model.resetQuery();
    model.getQuery().setIncludeLocation(true);
    launch(RestaurantListActivity.class);
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    // Associate searchable configuration with the SearchView
    SearchManager searchManager =
            (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView =
            (SearchView) menu.findItem(R.id.menu_search).getActionView();
    searchView.setSearchableInfo(
            searchManager.getSearchableInfo(getComponentName()));

    return true;

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }


  // All stuff below put in for push-whoosh integration
  //Registration receiver
  BroadcastReceiver mBroadcastReceiver = new BaseRegistrationReceiver() {
    @Override
    public void onRegisterActionReceive(Context context, Intent intent)
    {
      checkMessage(intent);
    }
  };

  //Push message receiver
  private BroadcastReceiver mReceiver = new BasePushMessageReceiver()
  {
    @Override
    protected void onMessageReceive(Intent intent)
    {
      //JSON_DATA_KEY contains JSON payload of push notification.
      showMessage("push message is " + intent.getExtras().getString(JSON_DATA_KEY));
    }
  };

  //Registration of the receivers
  public void registerReceivers()
  {
    IntentFilter intentFilter = new IntentFilter(getPackageName() + ".action.PUSH_MESSAGE_RECEIVE");

    registerReceiver(mReceiver, intentFilter, getPackageName() +".permission.C2D_MESSAGE", null);

    registerReceiver(mBroadcastReceiver, new IntentFilter(getPackageName() + "." + PushManager.REGISTER_BROAD_CAST_ACTION));
  }

  public void unregisterReceivers()
  {
    //Unregister receivers on pause
    try
    {
      unregisterReceiver(mReceiver);
    }
    catch (Exception e)
    {
      // pass.
    }

    try
    {
      unregisterReceiver(mBroadcastReceiver);
    }
    catch (Exception e)
    {
      //pass through
    }
  }

  private void checkMessage(Intent intent)
  {
    if (null != intent)
    {
      if (intent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
      {
        showMessage("push message is " + intent.getExtras().getString(PushManager.PUSH_RECEIVE_EVENT));
      }
      else if (intent.hasExtra(PushManager.REGISTER_EVENT))
      {
        //showMessage("register");
      }
      else if (intent.hasExtra(PushManager.UNREGISTER_EVENT))
      {
        showMessage("unregister");
      }
      else if (intent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
      {
        showMessage("register error");
      }
      else if (intent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
      {
        showMessage("unregister error");
      }

      resetIntentValues();
    }
  }

  /**
   * Will check main Activity intent and if it contains any PushWoosh data, will clear it
   */
  private void resetIntentValues()
  {
    Intent mainAppIntent = getIntent();

    if (mainAppIntent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
    {
      mainAppIntent.removeExtra(PushManager.PUSH_RECEIVE_EVENT);
    }
    else if (mainAppIntent.hasExtra(PushManager.REGISTER_EVENT))
    {
      mainAppIntent.removeExtra(PushManager.REGISTER_EVENT);
    }
    else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_EVENT))
    {
      mainAppIntent.removeExtra(PushManager.UNREGISTER_EVENT);
    }
    else if (mainAppIntent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
    {
      mainAppIntent.removeExtra(PushManager.REGISTER_ERROR_EVENT);
    }
    else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
    {
      mainAppIntent.removeExtra(PushManager.UNREGISTER_ERROR_EVENT);
    }

    setIntent(mainAppIntent);
  }

  private void showMessage(String message)
  {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  @Override
  protected void onNewIntent(Intent intent)
  {
    super.onNewIntent(intent);
    setIntent(intent);

    checkMessage(intent);
  }
  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         String permissions[], int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_REQUEST_ACCESS_FINE_LOCATION_FOR_NEARBY: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          // permission was granted, yay! Do the
          // contacts-related task you need to do.
          launchRestaurantListActivityForNearby();

        } else {

          // permission denied, boo! Disable the
          // functionality that depends on this permission.
        }
        return;
      }
      case PERMISSION_REQUEST_ACCESS_FINE_LOCATION_FOR_FUNPIX:{
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          // permission was granted, yay! Do the
          // contacts-related task you need to do.
          launchRestaurantListActivityForNearbyFunPix();

        } else {

          // permission denied, boo! Disable the
          // functionality that depends on this permission.
        }
        return;
      }

      // other 'case' lines to check for other
      // permissions this app might request
    }
  }

}
