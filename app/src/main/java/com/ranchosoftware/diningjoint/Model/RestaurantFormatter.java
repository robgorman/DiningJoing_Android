package com.ranchosoftware.diningjoint.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.sandiegorestaurantsandbars.diningjoint.R;

/**
 * Created by rob on 6/26/15.
 */
public class RestaurantFormatter {

  private Restaurant restaurant;

  public RestaurantFormatter(Restaurant restaurant){
    this.restaurant = restaurant;
  }

  public static class BooleanImage{

    private int selectedResourceId;
    private int unselectedResourceId;

    public BooleanImage(int selectedResourceId, int unselectedResourceId){
      this.selectedResourceId = selectedResourceId;
      this.unselectedResourceId = unselectedResourceId;
    }

    public int getSelectedResourceId() {
      return selectedResourceId;
    }

    public int getUnselectedResourceId() {
      return unselectedResourceId;
    }
  }

  public static BooleanImage[] booleanImages = {
          new BooleanImage(R.drawable.delivery, R.drawable.deliveryshaded),
          new BooleanImage(R.drawable.wifi, R.drawable.wifishaded),
          new BooleanImage(R.drawable.tv, R.drawable.tvshaded),
          new BooleanImage(R.drawable.livemusic, R.drawable.livemusicshaded),
          new BooleanImage(R.drawable.karaoke, R.drawable.karaokeshaded),
          new BooleanImage(R.drawable.dancing, R.drawable.dancingshaded),
          new BooleanImage(R.drawable.catering, R.drawable.cateringshaded),
          new BooleanImage(R.drawable.takeout, R.drawable.takeoutshaded),
          new BooleanImage(R.drawable.oceanview, R.drawable.oceanviewshaded),
          new BooleanImage(R.drawable.reservations, R.drawable.reservationsshaded),
          new BooleanImage(R.drawable.gluttenfree, R.drawable.gluttenfreeshaded),
          new BooleanImage(R.drawable.veganoptions, R.drawable.veganoptionsshaded),
          new BooleanImage(R.drawable.dogfriendly, R.drawable.dogfriendlyshaded)

  };

  public String name(){
    return restaurant.getName();
  }

  public String address(){
    return restaurant.getAddress();
  }

  public String distance(){
    if (restaurant.getDistance() != ""){
      return restaurant.getDistance() + " miles";
    }
    return "";
  }

  public String cityStateZip(){
    if (restaurant.getCity() != null && restaurant.getState() != null && restaurant.getZipCode() != null){
      return restaurant.getCity() + ", " + restaurant.getState() + " " + restaurant.getZipCode();
    }
    return "";
  }

  public String phoneNumber(){
    if (restaurant.getPhoneNumber() != null){
      return restaurant.getPhoneNumber();
    }
    return "";
  }

  public String cuisine(){
    if (restaurant.getCuisine() != null){
      return restaurant.getCuisine();
    }
    return "";
  }

  public String price(){
    String price = "$$$";
    if (restaurant.getPrice() != null){
      if (restaurant.getPrice().equals("1")){
        price = "$";
      } else if (restaurant.getPrice().equals("2")){
        price = "$$";
      } else if (restaurant.getPrice().equals("3")){
        price = "$$$";
      } else if (restaurant.getPrice().equals("4")){
        price = "$$$$";
      }
    }
    return price;
  }



  public Drawable drinks(Context context){
    Drawable image;
    if (restaurant.getDrinks() == Restaurant.Drinks.Soda){
      image = context.getResources().getDrawable(R.drawable.soda);
    } else if (restaurant.getDrinks() == Restaurant.Drinks.BeerAndWine){
      image = context.getResources().getDrawable(R.drawable.wine);
    } else if (restaurant.getDrinks() == Restaurant.Drinks.Liquor){
      image = context.getResources().getDrawable(R.drawable.martini);
    } else{
      image = context.getResources().getDrawable(R.drawable.can);
    }
    return image;
  }

  public String bestDay(){
    if (restaurant.getBestDay() != ""){
      return restaurant.getBestDay();
    }
    return "Any Day";
  }

  public String bestDish(){
    if (restaurant.getPopularDish() != ""){
      return restaurant.getPopularDish();
    }
    return "Ask Server";
  }

  public String hours(){
    if (restaurant.getHours() != ""){
      return restaurant.getHours();
    }
    return "";
  }
}
