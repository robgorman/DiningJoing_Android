package com.ranchosoftware.diningjoint.Model;

/**
 * Created by rob on 6/26/15.
 */
public interface SelectObject {
  int getNumberOfItems();
  int getSelection();
  void setSelection(int selection);
  String getLabelFor(int selection);
}
