package com.ranchosoftware.diningjoint;

import android.app.SearchManager;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ranchosoftware.diningjoint.Model.Model;
import com.ranchosoftware.diningjoint.Model.Query;
import com.ranchosoftware.diningjoint.Model.Restaurant;
import com.ranchosoftware.diningjoint.Server.Server;
import com.ranchosoftware.diningjoint.ui.Hud;
import com.sandiegorestaurantsandbars.diningjoint.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends DiningJointActivity {

  private Model model;

  private List<Restaurant> restaurants = new ArrayList<>();
  private int currentPage;
  private boolean moreDataAvailable = true;
  private ListView listView;
  private RestaurantListAdapter restaurantAdapter;

  private final LocationListener locationListener = new LocationListener(){
    @Override
    public void onLocationChanged(Location location) {

      model.getQuery().setLocation(location);
      getRestaurantData();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

      // TODO; Maybe shoudl getRestaurantData()
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_list);

    Intent intent = getIntent();
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query = intent.getStringExtra(SearchManager.QUERY);
      getModel().getQuery().setFreeSearchText(query);
      //use the query to search
    }

    TextView tvQueryCriteria = (TextView) findViewById(R.id.tvQueryCriteria);
    listView = (ListView) findViewById(R.id.listView);

    model = getModel();
    if (model.getQuery().getIncludeLocation()){
      LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
      lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);

      Hud.on(this);
    } else {
      getRestaurantData();
    }

    if (model.getQuery().getSearchCoupons()) {
      tvQueryCriteria.setText("Joints With Coupons");
    } else if (model.getQuery().getSearchHappyHour()){
      tvQueryCriteria.setText("Joints With Happy Hour");
    } else if (model.getQuery().getLocation() != null) {
      tvQueryCriteria.setText("Nearby");
    } else if (model.getQuery().getSearchFunpix()){
      tvQueryCriteria.setText("Joints With Social Pics");
    } else {
      tvQueryCriteria.setText("Tap For Details");
    }

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == restaurants.size()){
          getMoreRestaurantData();
        } else {
          // show details
          model.setSelectedRestaurant(restaurants.get(position));
          launch(RestaurantDetailsActivity.class);
        }
      }
    });

  }

  private void getMoreRestaurantData(){
    Hud.on(this);
    Query query = model.getQuery();
    query.setPage(Integer.toString(currentPage+1));
    Server.getInstance().getRestaurantData(query.composeQueryParameter(), new Server.SuccessCallback() {
      @Override
      public void success(List<Restaurant> restaurants, int currentPage, int totalPages) {

        Hud.off(RestaurantListActivity.this);
        RestaurantListActivity.this.restaurants.addAll(restaurants);
        RestaurantListActivity.this.currentPage = currentPage;
        moreDataAvailable = (currentPage < totalPages);
        restaurantAdapter.notifyDataSetChanged();
      }
    }, new Server.FailureCallback() {
      @Override
      public void failure(String message) {

        Hud.off(RestaurantListActivity.this);

      }
    });

  }


  private void getRestaurantData(){
    Hud.on(this);
    Query query = model.getQuery();

    Server.getInstance().getRestaurantData(query.composeQueryParameter(), new Server.SuccessCallback() {
      @Override
      public void success(List<Restaurant> restaurants, int currentPage, int totalPages) {

        Hud.off(RestaurantListActivity.this);
        RestaurantListActivity.this.restaurants = restaurants;
        RestaurantListActivity.this.currentPage = currentPage;
        moreDataAvailable = (currentPage < totalPages);
        restaurantAdapter = new RestaurantListAdapter(RestaurantListActivity.this, R.layout.restaurant_list_item, restaurants,  moreDataAvailable);
        listView.setAdapter(restaurantAdapter);
      }
    }, new Server.FailureCallback(){
      @Override
      public void failure(String message) {

        Hud.off(RestaurantListActivity.this);
      }
    });

  }
}
