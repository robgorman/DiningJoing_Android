package com.ranchosoftware.diningjoint.Model;

/**
 * Created by rob on 6/26/15.
 */
public class Occasion extends Content{
  public Occasion(){
    content.add(new ContentItem("Breakfast", "gf_breakfast"));
    content.add(new ContentItem("Sunday Brunch", "gf_brunch"));
    content.add(new ContentItem("Lunch", "gf_lunch"));
    content.add(new ContentItem("Dinner", "gf_dinner"));
    content.add(new ContentItem("Drinks", "gf_drinks"));
    content.add(new ContentItem("Happy Hour", "gf_happy_hr"));
    content.add(new ContentItem("Coffee", "gf_coffee"));
    content.add(new ContentItem("Cake", "gf_cake"));
    content.add(new ContentItem("Late Night", "gf_late"));
    content.add(new ContentItem("Cheap Eats", "gf_cheap"));
    content.add(new ContentItem("Taco Tuesday", "gf_taco"));
  }
}
