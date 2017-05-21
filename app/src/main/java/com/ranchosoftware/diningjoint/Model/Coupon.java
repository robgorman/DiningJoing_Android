package com.ranchosoftware.diningjoint.Model;

/**
 * Created by rob on 6/27/15.
 */
public class Coupon {

  private String id = "";
  private String title = "";
  private String tagline = "";
  private String message = "";
  private String fineprint = "";
  private String days = "";
  private String start = "";
  private String end = "";
  private String imageUrl = "";

  public Coupon(){}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTagline() {
    return tagline;
  }

  public void setTagline(String tagline) {
    this.tagline = tagline;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getFineprint() {
    return fineprint;
  }

  public void setFineprint(String fineprint) {
    this.fineprint = fineprint;
  }

  public String getDays() {
    return days;
  }

  public void setDays(String days) {
    this.days = days;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
