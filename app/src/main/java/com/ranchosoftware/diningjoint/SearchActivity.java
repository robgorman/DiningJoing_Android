package com.ranchosoftware.diningjoint;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ranchosoftware.diningjoint.Model.Model;
import com.ranchosoftware.diningjoint.Model.Query;
import com.ranchosoftware.diningjoint.Model.SelectObject;
import com.sandiegorestaurantsandbars.diningjoint.R;

public class SearchActivity extends DiningJointActivity
{
  private String currentPurpose = ""; 
  private String currentOccasion = ""; 
  private String currentLocation = ""; 
  private String currentCuisine = ""; 
  
  public static int CONTENT_SOURCE_PURPOSE = 0;
  public static int CONTENT_SOURCE_CITY = 1;
  public static int CONTENT_SOURCE_OCCASION = 2;
  public static int CONTENT_SOURCE_CUISINE = 3;


  // buttons. these are members because they change label upon selection
  private Button purpose; 
  private Button location; 
  private Button occasion; 
  private Button cousine;
  
  private ArrayList<LinearLayout> booleanViews = new ArrayList<LinearLayout>();

  private Model model;

  @Override
  public void onCreate(Bundle sBundle)
  {
    super.onCreate(sBundle);
    setContentView(R.layout.activity_search);

    model = getModel();
    for (int i = 0; i < 13; i++){
      String name = "ll" + i;
      int resourceId = getResources().getIdentifier(name, "id", "com.sandiegorestaurantsandbars.diningjoint");
      booleanViews.add( (LinearLayout) findViewById(resourceId));
    }

    for (LinearLayout limitItem : booleanViews){
      limitItem.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          int index = booleanViews.indexOf(v);
          Query q = model.getQuery();
          q.setFlag(index, !q.getFlag(index));

          updateViewFromModel();
        }
      });
    }


    
    purpose = (Button) findViewById(R.id.buttonPurpose);
    purpose.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View arg0)
      {
        Intent intent = new Intent(SearchActivity.this, RadioListActivity.class);
        intent.putExtra("contentSource", CONTENT_SOURCE_PURPOSE);
        startActivity(intent);
      }});
    
    occasion = (Button) findViewById(R.id.buttonOccasion);
    occasion.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View arg0)
      {
        Intent intent = new Intent(SearchActivity.this, RadioListActivity.class);
        intent.putExtra("contentSource", CONTENT_SOURCE_OCCASION);
        startActivity(intent);
      }});
    
    location = (Button) findViewById(R.id.buttonLocation);
    location.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View arg0)
      {
        Intent intent = new Intent(SearchActivity.this, RadioListActivity.class);
        intent.putExtra("contentSource", CONTENT_SOURCE_CITY);
        startActivity(intent);
      }});
    
    cousine = (Button) findViewById(R.id.buttonCousine);
    cousine.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View arg0)
      {
        Intent intent = new Intent(SearchActivity.this, RadioListActivity.class);
        intent.putExtra("contentSource", CONTENT_SOURCE_CUISINE);
        startActivity(intent);
      }});
   
    
    Button find = (Button) findViewById(R.id.buttonFindJoints);
    find.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View arg0)
      {
        Intent intent = new Intent(SearchActivity.this, RestaurantListActivity.class);
        startActivity(intent);
      }});
  }

  private void setButtonTitle(Button button, SelectObject select, String defaultTitle){
    String title = defaultTitle;
    int textColor = R.color.White;
    int imageResource = R.drawable.button_search_criteria;
    if (select.getSelection() != -1){
      title = select.getLabelFor(select.getSelection());
      textColor = R.color.DJMaroon;
      imageResource = R.drawable.button_search_criteria_selected;
    }
    button.setTextColor(getResources().getColor(textColor));
    button.setText(title);
    button.setBackground(getResources().getDrawable(imageResource));
  }

  private void updateViewFromModel(){
    setButtonTitle(purpose, model.getQuery().getPurpose(), "PURPOSE");
    setButtonTitle(occasion, model.getQuery().getOccasion(), "OCCASION");
    setButtonTitle(location, model.getQuery().getCity(), "NEIGHBORHOOD");
    setButtonTitle(cousine, model.getQuery().getCuisine(), "CUISINE");

    for (int i = 0; i < booleanViews.size(); i++){
      LinearLayout container = (LinearLayout) booleanViews.get(i);
      ImageView checked = (ImageView) container.getChildAt(1);
      if (model.getQuery().getFlag(i)){
        checked.setImageResource(R.drawable.checked);
      } else {
        checked.setImageResource(R.drawable.unchecked);
      }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    updateViewFromModel();
  }
}
