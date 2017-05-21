package com.ranchosoftware.diningjoint;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ranchosoftware.diningjoint.Model.Content;
import com.sandiegorestaurantsandbars.diningjoint.R;

public class RadioListActivity extends DiningJointActivity
{


  private Content currentContent;
  public void onCreate(Bundle bundle)
  {
    super.onCreate(bundle);
    setContentView(R.layout.activity_radio_list);


    Bundle extras = getIntent().getExtras();
    int contentSource = extras.getInt("contentSource");
    if (contentSource == SearchActivity.CONTENT_SOURCE_PURPOSE){
      currentContent = getModel().getQuery().getPurpose();
    } else if (contentSource == SearchActivity.CONTENT_SOURCE_CITY){
      currentContent = getModel().getQuery().getCity();
    } else if (contentSource == SearchActivity.CONTENT_SOURCE_CUISINE){
      currentContent = getModel().getQuery().getCuisine();
    } else {
      currentContent = getModel().getQuery().getOccasion();
    }


    final ListView listView = (ListView) findViewById(R.id.listView1);

    final ContentItemAdapter adapter = new ContentItemAdapter(this, R.layout.radio_list_item,
            currentContent.getContent(), currentContent.getSelection());
    listView.setAdapter(adapter);

    listView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        adapter.setSelectedItem(position);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            currentContent.setSelection(position);
            RadioListActivity.this.finish();
          }
        }, 100);

      }
    });



  }
 

  
  
  
}
