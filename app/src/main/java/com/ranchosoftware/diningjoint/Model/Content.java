package com.ranchosoftware.diningjoint.Model;

import java.util.ArrayList;

/**
 * Created by rob on 6/26/15.
 */
public class Content implements SelectObject{

  protected ArrayList<ContentItem> content = new ArrayList<ContentItem>();

  public Content(){

  }

  public ArrayList<ContentItem> getContent(){return content;}

  public String encode(String label){

    for (ContentItem item : content){
      if (item.getLabel().equals(label)){
        return item.getCode();
      }
    }
    return "";
  }

  public int getNumberOfItems(){
    return content.size();
  }

  public int getSelection() {
    for (int i = 0; i < content.size(); i++){
      ContentItem item = content.get(i);
      if (item.isSelected()){
        return i;
      }
    }
    return -1;
  }

  public void setSelection(int selection){
    for (int i = 0; i < content.size(); i++){
      ContentItem item = content.get(i);
      item.setSelected(false);
    }
    content.get(selection).setSelected(true);
  }

  public String getLabelFor(int selection){
    return content.get(selection).getLabel();
  }

}
