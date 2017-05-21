package com.ranchosoftware.diningjoint.Model;

/**
 * Created by rob on 6/26/15.
 */
public class Purpose extends Content {

  public Purpose(){
    content.add(new ContentItem("Meet a Date", "gf_date"));
    content.add(new ContentItem("Meet for Business", "gf_bus"));
    content.add(new ContentItem("Meet a Friend", "gf_friend"));
    content.add(new ContentItem("Fly Solo", "gf_solo"));

  }
}
