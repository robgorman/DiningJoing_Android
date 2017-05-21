package com.ranchosoftware.diningjoint.Model;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rob on 6/27/15.
 */
public class Restaurant {

  public enum Drinks {Soda, BeerAndWine, Liquor};

  public static class GeoLocation{
    public double latitude;
    public double longitude;
    public GeoLocation(double latitude, double longitude){
      this.latitude = latitude;
      this.longitude = longitude;
    }
  }

  private String id;
  
  private String name ="";
  private String address ="";
  private String city ="";
  private String state ="";
  private String zipCode ="";
  private boolean hasHappyHour = false;
  private boolean hasFunPix = false;
  private String happyHourTime ="";
  private String bestDay = "";
  private List<Coupon> coupons = new ArrayList<Coupon>();
  private boolean hasMenu;
  private String transLogo ="";
  private String cuisine ="";
  private String phoneNumber ="";
  private String price ="";
  private Drinks drinks;

  public Boolean featureFlags[] = {false, false, false, false, false, false, false,
          false, false, false, false, false, false};
  private GeoLocation location;
  private String hours ="";
  private String distance = "";
  private String url ="";
  private String popularDish ="";
  private List<Photo> photos = new ArrayList<Photo>();
  private String watermarkUri ="";

  private String facebook = "";
  private String instagram = "";
  private String twitter = "";
  private String youtube  = "";
  private String google = "";

  public Restaurant(){

  }

  public List<Boolean> getBestDays(){

    ArrayList<Boolean> bestDays = new ArrayList<Boolean>();

    if (this.bestDay == "Sunday"){
      bestDays.add(new Boolean(true));
    } else {
      bestDays.add(new Boolean(false));
    }

    if (this.bestDay == "Monday"){
      bestDays.add(new Boolean(true));
    } else {
      bestDays.add(new Boolean(false));
    }

    if (this.bestDay == "Tuesday"){
      bestDays.add(new Boolean(true));
    } else {
      bestDays.add(new Boolean(false));
    }

    if (this.bestDay == "Wednesday"){
      bestDays.add(new Boolean(true));
    } else {
      bestDays.add(new Boolean(false));
    }

    if (this.bestDay == "Thursday"){
      bestDays.add(new Boolean(true));
    } else {
      bestDays.add(new Boolean(false));
    }

    if (this.bestDay == "Friday"){
      bestDays.add(new Boolean(true));
    } else {
      bestDays.add(new Boolean(false));
    }

    if (this.bestDay == "Saturday"){
      bestDays.add(new Boolean(true));
    } else {
      bestDays.add(new Boolean(false));
    }

    return bestDays;

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public boolean getHasHappyHour() {
    return hasHappyHour;
  }

  public void setHasHappyHour(boolean hasHappyHour) {
    this.hasHappyHour = hasHappyHour;
  }

  public void setHasFunPix(boolean hasFunPix){
    this.hasFunPix = hasFunPix;
  }

  public boolean getHasFunPix(){
    return hasFunPix;
  }
  public String getHappyHourTime() {
    return happyHourTime;
  }

  public void setHappyHourTime(String happyHourTime) {
    this.happyHourTime = happyHourTime;
  }

  public String getBestDay() {
    return bestDay;
  }

  public void setBestDay(String bestDay) {
    this.bestDay = bestDay;
  }

  public List<Coupon> getCoupons() {
    return coupons;
  }

  public void setCoupons(List<Coupon> coupons) {
    this.coupons = coupons;
  }

  public boolean getHasMenu() {
    return hasMenu;
  }

  public void setHasMenu(boolean hasMenu) {
    this.hasMenu = hasMenu;
  }

  public String getTransLogo() {
    return transLogo;
  }

  public void setTransLogo(String transLogo) {
    this.transLogo = transLogo;
  }

  public String getCuisine() {
    return cuisine;
  }

  public void setCuisine(String cuisine) {
    this.cuisine = cuisine;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public Drinks getDrinks() {
    return drinks;
  }

  public void setDrinks(Drinks drinks) {
    this.drinks = drinks;
  }



  public GeoLocation getLocation() {
    return location;
  }

  public void setLocation(GeoLocation location) {
    this.location = location;
  }

  public void setFeature(int i, boolean value){
    featureFlags[i] = false;
  }

  public boolean getFeature(int i){
    return featureFlags[i];
  }

  public String getHours() {
    return hours;
  }

  public void setHours(String hours) {
    this.hours = hours;
  }

  public String getDistance() {
    return distance;
  }

  public void setDistance(String distance) {
    this.distance = distance;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPopularDish() {
    return popularDish;
  }

  public void setPopularDish(String popularDish) {
    this.popularDish = popularDish;
  }

  public List<Photo> getPhotos() {
    return photos;
  }

  public void setPhotos(List<Photo> photos) {
    this.photos = photos;
  }

  public String getWatermarkUri() {
    return watermarkUri;
  }

  public void setWatermarkUri(String watermarkUri) {
    this.watermarkUri = watermarkUri;
  }

  public String getFacebook() {
    return facebook;
  }

  public void setFacebook(String facebook) {
    this.facebook = facebook;
  }

  public String getInstagram() {
    return instagram;
  }

  public void setInstagram(String instagram) {
    this.instagram = instagram;
  }

  public String getTwitter() {
    return twitter;
  }

  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }

  public String getYoutube() {
    return youtube;
  }

  public void setYoutube(String youtube) {
    this.youtube = youtube;
  }

  public String getGoogle() {
    return google;
  }

  public void setGoogle(String google) {
    this.google = google;
  }
}
