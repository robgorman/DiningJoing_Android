package com.ranchosoftware.diningjoint.Model;

import android.content.Context;
import android.location.Location;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rob on 6/26/15.
 */
public class Query {



  private String queryString = "";
  private String page = null;
  private boolean searchHappyHour = false;
  private boolean searchCoupons = false;
  private boolean searchFunpix = false;
  private boolean includeLocation = false;
  private Location location = null;
  private Purpose purpose = new Purpose();
  private Occasion occasion= new Occasion();
  private City city;  // filled in at construction requires context
  private Cuisine cuisine ; // ditto
  private ArrayList<Boolean> flags = new ArrayList<Boolean>();
  private String freeSearchText = "";
  private Integer dayOfWeekOrdinal = null;


  public Query(Context context){
    city = new City(context);
    cuisine = new Cuisine(context);
    for (int i = 0; i <= 12; i++){
      flags.add(false);

    }
  }

  public HashMap<String, String> composeQueryParameter(){

    HashMap<String, String> parameters = new HashMap<>();

    parameters.put("find_jt", "GO");

    if (page != null)
      parameters.put("page", page);

    if (searchHappyHour) {
      parameters.put("hap","1");
    }

    if (searchFunpix){
      parameters.put("hfp", "1");
    }

    if (searchCoupons) {
      parameters.put("coups", "1");
    }

    if (location != null) {
      parameters.put("my_lat", Double.toString(location.getLatitude()));
      parameters.put("my_long", Double.toString(location.getLongitude()));
    }

    if ( purpose.getSelection() != -1) {
      parameters.put("reason", purpose.encode(purpose.getLabelFor(purpose.getSelection())));
    }
    if (occasion.getSelection()  != -1) {
      parameters.put("mealtype", occasion.encode(occasion.getLabelFor(occasion.getSelection())));

    }
    if (city.getSelection()  != -1) {
      parameters.put("city", city.encode(city.getLabelFor(city.getSelection())));
    }
    if (cuisine.getSelection()  != -1) {
      parameters.put("sic_id", cuisine.encode(cuisine.getLabelFor(cuisine.getSelection())));
    }

    if (flags.get(0)){
      parameters.put("feat[deliver]", "1");
    }

    if (flags.get(1)) {
      parameters.put("feat[inet]", "1");
    }

    if (flags.get(2)) {
      parameters.put("feat[sports]", "1");
    }
    if (flags.get(3)) {
      parameters.put("feat[live]", "1");
    }
    if (flags.get(4)) {
      parameters.put("feat[karaoke]", "1");
    }

    if (flags.get(5)) {
      parameters.put("feat[dance]", "1");
    }

    if (flags.get(6)) {
      parameters.put("feat[cater]", "1");
    }
    if (flags.get(7)) {
      parameters.put("feat[banquet]", "1");
    }
    if (flags.get(8)) {
      parameters.put("feat[ocean]", "1");
    }
    if (flags.get(9)) {
      parameters.put("feat[reserve]", "1");
    }
    if (flags.get(10)) {
      parameters.put("feat[glut_free]", "1");
    }
    if (flags.get(11)) {
      parameters.put("feat[vegan]", "1");
    }
    if (flags.get(12)) {
      parameters.put("feat[dog_friendly]", "1");
    }

    if (dayOfWeekOrdinal != null) {
      parameters.put("day", Integer.toString(dayOfWeekOrdinal));
    }

    if (freeSearchText != "") {
      try {
        String encoded = URLEncoder.encode(freeSearchText, "UTF-8");
        parameters.put("part_jt", encoded);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }


    }

    return parameters;
  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public void setSearchHappyHour(boolean searchHappyHour) {
    this.searchHappyHour = searchHappyHour;
  }

  public boolean getSearchHappyHour(){return searchHappyHour;}

  public void setSearchFunpix(boolean searchFunpix){
    this.searchFunpix = searchFunpix;
  }
  public boolean getSearchFunpix(){return searchFunpix;}

  public void setSearchCoupons(boolean searchCoupons) {
    this.searchCoupons = searchCoupons;
  }
  public boolean getSearchCoupons(){return searchCoupons;}

  public void setIncludeLocation(boolean includeLocation) {
    this.includeLocation = includeLocation;
  }

  public boolean getIncludeLocation(){return includeLocation;}

  public void setLocation(Location location) {
    this.location = location;
  }

  // may be null
  public Location getLocation(){return location;}

  public void setPurpose(Purpose purpose) {
    this.purpose = purpose;
  }

  public void setOccasion(Occasion occasion) {
    this.occasion = occasion;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public void setCuisine(Cuisine cuisine) {
    this.cuisine = cuisine;
  }

  public boolean getFlag(int i){
    return flags.get(i);
  }
  public void setFlag(int i, boolean value){

    flags.set(i, value);

  }


  public void setFreeSearchText(String freeSearchText) {
    this.freeSearchText = freeSearchText;
  }

  public void setDayOfWeekOrdinal(Integer dayOfWeekOrdinal) {
    this.dayOfWeekOrdinal = dayOfWeekOrdinal;
  }

  public Purpose getPurpose() {
    return purpose;
  }

  public Occasion getOccasion() {
    return occasion;
  }

  public City getCity() {
    return city;
  }

  public Cuisine getCuisine() {
    return cuisine;
  }
}
