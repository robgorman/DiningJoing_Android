package com.ranchosoftware.diningjoint.Model;

/**
 * Created by rob on 6/26/15.
 */
public class ContentItem {
  private String label;
  private String code;
  private boolean selected;

  public ContentItem(String label, String code){
    this.label = label;
    this.code = code;
    selected = false;
  }

  public String getLabel() {
    return label;
  }



  public String getCode() {
    return code;
  }



  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }
}
