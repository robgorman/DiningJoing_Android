package com.ranchosoftware.diningjoint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ranchosoftware.diningjoint.Model.ContentItem;
import com.sandiegorestaurantsandbars.diningjoint.R;

import java.util.ArrayList;

/**
 * Created by rob on 6/26/15.
 */
public class ContentItemAdapter extends ArrayAdapter<ContentItem> {

  private int selectedItem = -1;
  private LayoutInflater inflater;
  public ContentItemAdapter(Context context, int textViewResourceId,
                          ArrayList<ContentItem> items, int initialSelectedIndex)
  {
    super(context, textViewResourceId, items);
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent)
  {
    View view = convertView;
    if (view == null)
    {

      view = inflater.inflate(R.layout.radio_list_item, null);
    }
    ContentItem item = this.getItem(position);

    TextView label = (TextView) view.findViewById(R.id.textView1);
    label.setText(item.getLabel());

    RadioButton rb = (RadioButton) view.findViewById(R.id.select_btn);
    if (position == selectedItem)
    {

      rb.setChecked(true);
    }
    else
    {
      rb.setChecked(false);
    }


    return view;
  }

  public void setSelectedItem(int item){
    selectedItem = item;
    notifyDataSetChanged();
  }
}
