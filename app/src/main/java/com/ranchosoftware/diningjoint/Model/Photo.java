package com.ranchosoftware.diningjoint.Model;

/**
 * Created by rob on 6/27/15.
 */
public class Photo {
  private String name = "";
  private int width = 0;
  private int height = 0;
  public Photo(){

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
