package com.ranchosoftware.diningjoint.Model;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by rob on 6/26/15.
 */
public class Model {

  private Context context;
  private Query query;
  private Restaurant selectedRestaurant;
  private Bitmap inviteCardBitmap;
  private String inviteToEmailAddress;
  private String inviteFromEmailAddress;
  private String inviteFromName;
  private String inviteMessage;
  public Model(Context context){
    this.context = context;
    query = new Query(context);
  }

  public void resetQuery(){
    query = new Query(context);
  }

  public Query getQuery(){
    return query;
  }

  public void setSelectedRestaurant(Restaurant restaurant) {
    this.selectedRestaurant = restaurant;
  }
  public Restaurant getSelectedRestaurant(){
    return selectedRestaurant;
  }

  public void setInviteCardBitmap(Bitmap bitmap) {
    inviteCardBitmap = bitmap;
  }
  public Bitmap getInviteCardBitmap(){
    return inviteCardBitmap;
  }

  public String getInviteToEmailAddress() {
    return inviteToEmailAddress;
  }

  public void setInviteToEmailAddress(String inviteToEmailAddress) {
    this.inviteToEmailAddress = inviteToEmailAddress;
  }

  public String getInviteFromEmailAddress() {
    return inviteFromEmailAddress;
  }

  public void setInviteFromEmailAddress(String inviteFromEmailAddress) {
    this.inviteFromEmailAddress = inviteFromEmailAddress;
  }

  public String getInviteFromName() {
    return inviteFromName;
  }

  public void setInviteFromName(String inviteFromName) {
    this.inviteFromName = inviteFromName;
  }

  public String getInviteMessage() {
    return inviteMessage;
  }

  public void setInviteMessage(String inviteMessage) {
    this.inviteMessage = inviteMessage;
  }
}
