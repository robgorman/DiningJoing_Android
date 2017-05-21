package com.ranchosoftware.diningjoint;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ranchosoftware.diningjoint.Model.Restaurant;
import com.ranchosoftware.diningjoint.Model.RestaurantFormatter;
import com.ranchosoftware.diningjoint.Server.Server;
import com.ranchosoftware.diningjoint.app.MyVolley;
import com.sandiegorestaurantsandbars.diningjoint.R;

public class RestaurantListAdapter extends ArrayAdapter<Restaurant>
{
  private static final String TAG = RestaurantListAdapter.TAG;
  private LayoutInflater inflater;
  private boolean moreDataAvailable;

  private ImageLoader imageLoader;
 
  public RestaurantListAdapter(Activity context, int resourceId, List<Restaurant> restaurants, boolean moreDataAvailable)
  {
    super(context, resourceId, restaurants);
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.moreDataAvailable = moreDataAvailable;
    imageLoader = MyVolley.getImageLoader();
  }

  @Override
  public int getViewTypeCount() {
    return 2;
  }

  @Override
  public int getItemViewType(int position){
    if (position < super.getCount())
      return 0;
    else
      return 1;
  }
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (position < super.getCount())
      return makeNormalCell(position, convertView);
    else
      return makeLoadMoreCell();
  }

  private View makeLoadMoreCell(){

    return inflater.inflate(R.layout.restaurant_list_item_load_more, null);
  }

  private View makeNormalCell(int position, View v) {
    if (v == null) {
      v = inflater.inflate(R.layout.restaurant_list_item, null);
    }

    Restaurant restaurant = getItem(position);
    RestaurantFormatter formatter = new RestaurantFormatter(restaurant);

    final NetworkImageView image = (NetworkImageView) v.findViewById(R.id.ivRestaurantLogo);

    String url = Server.getInstance().makeRestaurantLogoUrl(restaurant.getId());

    image.setImageUrl(url, imageLoader);
    image.setDefaultImageResId(R.drawable.diningjoint);


    TextView name = (TextView) v.findViewById(R.id.tvName);
    name.setText(formatter.name());

    TextView address = (TextView) v.findViewById(R.id.tvAddress);
    address.setText(formatter.address());
    TextView cityStateZip = (TextView) v.findViewById(R.id.tvCityStateZip);
    cityStateZip.setText(formatter.cityStateZip());
    TextView cuisine = (TextView) v.findViewById(R.id.tvCuisine);
    cuisine.setText(formatter.cuisine());
    TextView price = (TextView) v.findViewById(R.id.tvPrice);
    price.setText(formatter.price());
    TextView distance = (TextView) v.findViewById(R.id.tvDistance);
    distance.setText(formatter.distance());
    ImageView ivDrinks = (ImageView) v.findViewById(R.id.ivDrinks);
    ivDrinks.setImageDrawable(formatter.drinks(getContext()));

    ImageView ivFunPix = (ImageView) v.findViewById(R.id.ivFunpics);
    ivFunPix.setVisibility(restaurant.getHasFunPix() ? View.VISIBLE : View.GONE);
    return v;
  }

  @Override
  public int getCount() {
    if (moreDataAvailable)
      return super.getCount() + 1;
    else
      return super.getCount();

  }
}