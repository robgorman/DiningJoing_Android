package com.ranchosoftware.diningjoint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ranchosoftware.diningjoint.Model.Restaurant;
import com.ranchosoftware.diningjoint.Model.RestaurantFormatter;
import com.ranchosoftware.diningjoint.Server.Server;
import com.sandiegorestaurantsandbars.diningjoint.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsActivity extends DiningJointActivity {
  private static String TAG = RestaurantDetailsActivity.class.getSimpleName();

  private Restaurant restaurant;
  private List<ImageView> dayImages = new ArrayList<ImageView>();

  private List<Drawable> onDays = new ArrayList<Drawable>();
  private List<Drawable> offDays = new ArrayList<Drawable>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_details);
    restaurant = getModel().getSelectedRestaurant();
    RestaurantFormatter fmt = new RestaurantFormatter(getModel().getSelectedRestaurant());

    final ImageView logo = (ImageView) findViewById(R.id.ivLogo);
    Server.getInstance().getRestaurantLogo(restaurant.getId(), new Server.ImageSuccessCallback() {
      @Override
      public void success(Bitmap bitmap) {
        logo.setImageBitmap(bitmap);

      }
    }, new Server.FailureCallback() {
      @Override
      public void failure(String message) {

        logo.setImageDrawable(getResources().getDrawable(R.drawable.shaded_logo));
      }
    });

    onDays.add(getResources().getDrawable(R.drawable.sunday_on));
    onDays.add(getResources().getDrawable(R.drawable.monday_on));
    onDays.add(getResources().getDrawable(R.drawable.tuesday_on));
    onDays.add(getResources().getDrawable(R.drawable.wednesday_on));
    onDays.add(getResources().getDrawable(R.drawable.thursday_on));
    onDays.add(getResources().getDrawable(R.drawable.friday_on));
    onDays.add(getResources().getDrawable(R.drawable.saturday_on));

    offDays.add(getResources().getDrawable(R.drawable.sunday_off));
    offDays.add(getResources().getDrawable(R.drawable.monday_off));
    offDays.add(getResources().getDrawable(R.drawable.tuesday_off));
    offDays.add(getResources().getDrawable(R.drawable.wednesday_off));
    offDays.add(getResources().getDrawable(R.drawable.thursday_off));
    offDays.add(getResources().getDrawable(R.drawable.friday_off));
    offDays.add(getResources().getDrawable(R.drawable.saturday_off));

    TextView tvHours = (TextView) findViewById(R.id.tvHours);
    tvHours.setText(fmt.hours());


    dayImages.add((ImageView) findViewById(R.id.ivSunday));
    dayImages.add((ImageView) findViewById(R.id.ivMonday));
    dayImages.add((ImageView) findViewById(R.id.ivTuesday));
    dayImages.add((ImageView) findViewById(R.id.ivWednesday));
    dayImages.add((ImageView) findViewById(R.id.ivThursday));
    dayImages.add((ImageView) findViewById(R.id.ivFriday));
    dayImages.add((ImageView) findViewById(R.id.ivSaturday));


    int day = 0;
    for (Boolean on : restaurant.getBestDays()){
      if (on){
        dayImages.get(day).setImageDrawable(onDays.get(day));
      } else {
        dayImages.get(day).setImageDrawable(offDays.get(day));
      }
    }

    TextView tvRestaurantName = (TextView) findViewById(R.id.tvRestaurantName);
    tvRestaurantName.setText(fmt.name());

    TextView tvBestDish = (TextView) findViewById(R.id.tvBestDish);
    tvBestDish.setText(fmt.bestDish());

    TextView tvCuisine = (TextView) findViewById(R.id.tvCuisine);
    tvCuisine.setText(fmt.cuisine());

    TextView tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
    tvPhoneNumber.setText(fmt.phoneNumber());

    TextView tvAddress1 = (TextView) findViewById(R.id.tvAddress1);
    tvAddress1.setText(fmt.address());

    TextView tvAddress2 = (TextView) findViewById(R.id.tvAddress2);
    tvAddress2.setText(fmt.cityStateZip());

    ImageButton ibCoupons = (ImageButton) findViewById(R.id.ibCoupons);
    ibCoupons.setVisibility(restaurant.getCoupons().size() == 0 ? View.INVISIBLE : View.VISIBLE);
    ibCoupons.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        launch(CouponsActivity.class);
      }
    });

    ImageButton ibHappyHour = (ImageButton) findViewById(R.id.ibHappyHour);
    ibHappyHour.setVisibility(restaurant.getHasHappyHour() ? View.VISIBLE : View.INVISIBLE);
    ibHappyHour.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //TODO happyhour view controller as web;
        launch(HappyHourActivity.class);
      }
    });
    ImageButton ibInvite = (ImageButton) findViewById(R.id.ibInvite);
    ibInvite.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        launch(InviteActivity.class);
      }
    });
    ImageButton ibFunPix = (ImageButton) findViewById(R.id.ibFunPix);
    ibFunPix.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        launch(FunPixActivity.class);

      }
    });


    View llPhone = findViewById(R.id.llCall);
    llPhone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String phoneNumber = restaurant.getPhoneNumber();
        phoneNumber = phoneNumber.replaceAll("-", "");
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
      }
    });
    View llDirections = findViewById(R.id.llDirections);
    llDirections.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String url = "http://maps.google.com/maps?daddr=" + restaurant.getLocation().latitude +
                "," + restaurant.getLocation().longitude + "&mode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);

      }
    });





    Button menu = (Button) findViewById(R.id.buttonMenu);
    menu.setVisibility(restaurant.getHasMenu() ? View.VISIBLE : View.INVISIBLE);
    menu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        launch(MenuActivity.class);
      }
    });

    ImageButton ibFacebook = (ImageButton) findViewById(R.id.ibFacebook);
    ibFacebook.setVisibility(restaurant.getFacebook().length() != 0 ? View.VISIBLE : View.INVISIBLE);
    ibFacebook.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getFacebook()));
        startActivity(intent);
      }
    });

    ImageButton ibTwitter = (ImageButton) findViewById(R.id.ibTwitter);
    ibTwitter.setVisibility(restaurant.getTwitter().length() != 0 ? View.VISIBLE : View.INVISIBLE);
    ibTwitter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getTwitter()));
        startActivity(intent);
      }
    });

    ImageButton ibInstagram = (ImageButton) findViewById(R.id.ibInstagram);
    ibInstagram.setVisibility(restaurant.getInstagram().length() != 0 ? View.VISIBLE : View.INVISIBLE);
    ibInstagram.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getInstagram()));
        startActivity(intent);
      }
    });

    //View funpixTop = findViewById(R.id.llFunpix);
    //funpixTop.setVisibility(restaurant.getHasFunPix() ? View.VISIBLE : View.GONE);
  }



}
