package com.ranchosoftware.diningjoint.Server;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.ranchosoftware.diningjoint.Model.Coupon;
import com.ranchosoftware.diningjoint.Model.Photo;
import com.ranchosoftware.diningjoint.Model.Restaurant;
import com.ranchosoftware.diningjoint.app.MyVolley;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by rob on 6/26/15.
 */
public class Server {

  private static String TAG = Server.class.getSimpleName();

  private static Server instance;

  public static Server getInstance() {
    if (instance == null) {
      instance = new Server();
    }
    return instance;
  }

  private final static String server = "http://www.diningjoint.com";
  private final static String restaurants = "/mobile_feed_android_ios.php";
  private final static String logos = "/logos/";
  private final static String restaurantMenu = "/restaurant_menus_mob.php";
  private final static String happyHour = "/mobile_specials.php";
  private final static String watermarkUrl = "/logos_png/images/";

  public static interface SuccessCallback {
    void success(List<Restaurant> restaurants, int currentPage, int totalPages);
  }

  public static interface ImageSuccessCallback{
    void success(Bitmap bitmap);
  }

  public static interface FailureCallback {
    void failure(String message);
  }

  String formGetParamString(final Map<String, String> parameters) {
    String params = "";
    for (String key : parameters.keySet()) {
      if (params.length() == 0) {
        params = key + "=" + parameters.get(key);
      } else {
        params += "&" + key + "=" + parameters.get(key);
      }

    }
    return params;
  }

  public void getCouponImage(String imageUrl, final ImageSuccessCallback success, final FailureCallback failure){
    RequestQueue queue = MyVolley.getRequestQueue();

    String url = server + "/" + imageUrl;

    ImageRequest request =new ImageRequest(url,
            new Response.Listener<Bitmap>() {
              @Override
              public void onResponse(Bitmap bitmap) {
                success.success(bitmap);

              }
            }, 0, 0, null,
            new Response.ErrorListener() {
              public void onErrorResponse(VolleyError error) {

                failure.failure(error.getMessage());
              }
            });
    queue.add(request);
  }

  public String makeRestaurantLogoUrl(String id){
    String adjustedName = id + "_lrg.jpg";
    String url = server + logos + adjustedName;
    return url;
  }

  public void getWatermarkImage(String id, final ImageSuccessCallback success, final FailureCallback failure ){
    RequestQueue queue = MyVolley.getRequestQueue();
    String adjustedName = id + ".png";
    String url = server + watermarkUrl + adjustedName;
    ImageRequest request =new ImageRequest(url,
            new Response.Listener<Bitmap>() {
              @Override
              public void onResponse(Bitmap bitmap) {
                success.success(bitmap);

              }
            }, 0, 0, null,
            new Response.ErrorListener() {
              public void onErrorResponse(VolleyError error) {

                failure.failure(error.getMessage());
              }
            });
    queue.add(request);
  }

  public void getRestaurantLogo(String id,  final ImageSuccessCallback success, final FailureCallback failure){
    RequestQueue queue = MyVolley.getRequestQueue();
    String adjustedName = id + "_lrg.jpg";
    String url = server + logos + adjustedName;

    ImageRequest request =new ImageRequest(url,
            new Response.Listener<Bitmap>() {
              @Override
              public void onResponse(Bitmap bitmap) {
                success.success(bitmap);

              }
            }, 0, 0, null,
            new Response.ErrorListener() {
              public void onErrorResponse(VolleyError error) {

                failure.failure(error.getMessage());
              }
            });
    queue.add(request);
  }


  public void getRestaurantData(final Map<String, String> parameters, final SuccessCallback success, final FailureCallback failure) {

    RequestQueue queue = MyVolley.getRequestQueue();
    String url = server + restaurants + "?" + formGetParamString(parameters);

    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
      @Override
      public void onResponse(String s) {
        GetRestaurantsResponse response = parseRestaurantDataRequest(s);
        success.success(response.getRestaurants(), response.getCurrentPage(), response.getTotalPages());
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError volleyError) {

        failure.failure(volleyError.getMessage());
      }
    });

    queue.add(request);
  }

  private Restaurant.GeoLocation parseLatLon(String latLon){
    String latLonArray[] = latLon.split(Pattern.quote("*"));
    double latitude = 0;
    double longitude = 0;
    if (latLonArray.length == 2){

      latitude = Double.parseDouble(latLonArray[0]);
      longitude = Double.parseDouble(latLonArray[1]);
      Restaurant.GeoLocation location = new Restaurant.GeoLocation(latitude, longitude);
      return location;
    }

   return null;

  }

  private void parseSocial(Restaurant restaurant, NodeList socialNodes){

    for (int i = 0; i < socialNodes.getLength(); i++){
      Node social = socialNodes.item(i);
      String name = social.getNodeName();
      String content = social.getTextContent();
      if (name.equals("facebook")){
        restaurant.setFacebook(content);
      } else if (name.equals("instagram")){
        restaurant.setInstagram(content);
      } else if (name.equals("twitter")){
        restaurant.setTwitter(content);
      } else if (name.equals("youtube")){
        restaurant.setYoutube(content);
      } else if (name.equals("google")){
        restaurant.setGoogle(content);
      }
    }
  }

  private void parsePhotos(Restaurant restaurant, NodeList photos) {
    ArrayList<Photo> photosArray = new ArrayList<Photo>();
    for (int i = 0; i < photos.getLength(); i++) {
      Node photoNode = photos.item(i);
      NodeList fields = photoNode.getChildNodes();
      Photo photo = new Photo();
      for (int j = 0; j < fields.getLength(); j++) {
        Node field = fields.item(j);
        String name = field.getNodeName();
        String content = field.getTextContent();
        if (name.equals("name")) {
          photo.setName(content);
        } else if (name.equals("width)")) {
          photo.setWidth(Integer.parseInt(content));
        } else if (name.equals("height")) {
          photo.setHeight(Integer.parseInt(content));

        }

      }
      photosArray.add(photo);
    }
    restaurant.setPhotos(photosArray);
  }

  private void parseCoupons(Restaurant restaurant, NodeList coupons) {
    ArrayList<Coupon> couponsArray = new ArrayList<Coupon>();

    for (int i = 0; i < coupons.getLength(); i++) {
      Node couponNode = coupons.item(i);
      if (couponNode.getNodeName() != null && couponNode.getNodeName().equals("coupon")) {
        NodeList fields = couponNode.getChildNodes();
        Coupon coupon = new Coupon();
        for (int j = 0; j < fields.getLength(); j++) {
          Node field = fields.item(j);
          String name = field.getNodeName();
          String content = field.getTextContent();
          if (name.equals("coup_id")) {
            coupon.setId(content);
          } else if (name.equals("coup_title)")) {
            coupon.setTitle(content);
          } else if (name.equals("coup_tagline")) {
            coupon.setTagline(content);
          } else if (name.equals("coup_msg")) {
            coupon.setMessage(content);
          } else if (name.equals("coup_fineprint")){
            coupon.setMessage(content);

          } else if (name.equals("coup_days")) {
            coupon.setDays(content);
          } else if (name.equals("coup_start")) {
            coupon.setStart(content);
          } else if (name.equals("coup_end")) {
            coupon.setEnd(content);
          } else if (name.equals("coup_img")) {
            coupon.setImageUrl(content);
          }


        }
        couponsArray.add(coupon);
      }
    }
    restaurant.setCoupons(couponsArray);
  }

  private Restaurant createRestaurantFromNode(Node recordNode) {
    NodeList fields = recordNode.getChildNodes();
    Restaurant restaurant = new Restaurant();
    for (int i= 0; i < fields.getLength(); i++) {

      Node field = fields.item(i);

      String name = field.getNodeName();
      String content = field.getTextContent();
      if (name.equals("id")) {
        restaurant.setId(content);
      } else if (name.equals("name")) {
        restaurant.setName(content);
      } else if (name.equals("addr")) {
        restaurant.setAddress(content);

      } else if (name.equals("city")) {
        restaurant.setCity(content);

      } else if (name.equals("st")) {
        restaurant.setState(content);

      } else if (name.equals("zip")) {
        restaurant.setZipCode(content);

      } else if (name.equals("hap")) {
        boolean hasHappy = content.equals("1") ? true : false;
        restaurant.setHasHappyHour(hasHappy);

      } else if (name.equals("time_hap")) {
        restaurant.setHappyHourTime(content);

      } else if (name.equals("busy_day")) {
        restaurant.setBestDay(content);

      } else if (name.equals("coups")) {


      } else if (name.equals("has_funpics")) {
        boolean hasFunpix = content.equals("1") ? true : false;
        restaurant.setHasFunPix(hasFunpix);

      } else if (name.equals("menu")) {
        boolean hasMenu = content.equals("1") ? true : false;
        restaurant.setHasMenu(hasMenu);
      } else if (name.equals("trans_logo")) {
        restaurant.setTransLogo(content);

      } else if (name.equals("cuis")) {
        restaurant.setCuisine(content);

      } else if (name.equals("ph")) {
        restaurant.setPhoneNumber(content);

      } else if (name.equals("price")) {
        restaurant.setPrice(content);

      } else if (name.equals("liq")) {
        restaurant.setDrinks(Restaurant.Drinks.Soda);
        if (content.equals("1")){
          restaurant.setDrinks(Restaurant.Drinks.Soda);
        } else if (content.equals("2")){
          restaurant.setDrinks(Restaurant.Drinks.BeerAndWine);
        } else if (content.equals("3")){
          restaurant.setDrinks(Restaurant.Drinks.Liquor);
        }

      } else if (name.equals("serv")) {
        // the string is 0*1*0*1.. and it should have 13 0 and 1 values. these
        // are the "features the restauant has

        String result[] = content.split(Pattern.quote("*"));
        for (int j = 0 ; j < result.length; j++){
          if (result[j].equals("1")){
            restaurant.featureFlags[j] = true;
          }
        }
      } else if (name.equals("latlong")) {
        restaurant.setLocation(parseLatLon(content));
      } else if (name.equals("hrs")) {

        restaurant.setHours(content);
      } else if (name.equals("dist")) {
        // distance of 0 means no distance availble
        if (content.equals("0")){
          restaurant.setDistance("");
        } else {
          restaurant.setDistance(content);
        }

      } else if (name.equals("url")) {
        restaurant.setUrl(content);

      }  else if (name.equals("pop_dish")) {
        restaurant.setPopularDish(content);

      } else if (name.equals("social")) {
        parseSocial(restaurant, recordNode.getChildNodes());

      } else if (name.equals("pics")) {


      } else if (name.equals("photos")) {

        parsePhotos(restaurant, recordNode.getChildNodes());
        // handle the photos
      } else if (name.equals("coupons")){
        parseCoupons(restaurant, field.getChildNodes());
      }
    }

    return restaurant;
  }



  private GetRestaurantsResponse parseRestaurantDataRequest(String s){
    GetRestaurantsResponse response = new GetRestaurantsResponse(new ArrayList<Restaurant>(), 0, 0);
    ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

      DocumentBuilder db = dbf.newDocumentBuilder();
      InputSource is = new InputSource();
      is.setCharacterStream(new StringReader(s));
      Document doc = db.parse(is);
      NodeList records  = doc.getElementsByTagName("record");

      for (int i = 0; i < records.getLength(); i++){
        Node nextRecord = records.item(i);
        Restaurant restaurant = createRestaurantFromNode(nextRecord);
        restaurants.add(restaurant);
      }
      response.setRestaurants(restaurants);

      NodeList navRecords = doc.getElementsByTagName("nav");
      if (navRecords.getLength() == 1){
        Node nav = navRecords.item(0);
        NodeList list = nav.getChildNodes();
        for (int j = 0; j < list.getLength(); j++){
          Node next = list.item(j);
          String name = next.getNodeName();
          String content = next.getTextContent();
          if (name.equals("page")){
            response.setCurrentPage(Integer.parseInt(content));
          } else if (name.equals("totalpages")){
            response.setTotalPages(Integer.parseInt(content));
          }
        }
      }



    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }
}
/* example
<?xml version="1.0" encoding="utf-8"?>
<joints>
	<record>
	<id>8253</id>
	<name>333 Pacific</name>
	<addr>333 N Pacific St</addr>
	<city>Oceanside</city>
	<st>CA</st>
	<zip>92054</zip>
	<hap>1</hap>
	<time_hap></time_hap>
	<busy_day></busy_day>
	<coups>0</coups>
	<activity_menu>1</activity_menu>
	<trans_logo>images/dj_logo_trans.png</trans_logo>
	<has_funpics>1</has_funpics>
	<cuis>Steakhouse</cuis>
	<ph>760-433-3333</ph>
	<price>3</price>
	<liq>3</liq>
	<serv>0*1*1*0*0*0*0*0*1*1*0*0*0</serv>
	<latlong>33.1949*-117.3839</latlong>
	<hrs>11:30 AM-10:00 PM</hrs>
	<dist>0</dist>
	<url>333pacific.com</url>
	<social>
		<facebook></facebook>
		<instagram></instagram>
		<twitter></twitter>
		<youtube></youtube>
		<google></google>
	</social>
	<pics>0</pics>
		</record>
	<record>

 */