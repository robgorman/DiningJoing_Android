package com.ranchosoftware.diningjoint.Server;

import com.ranchosoftware.diningjoint.Model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rob on 6/27/15.
 */
public class GetRestaurantsResponse {

  private List<Restaurant> restaurants;
  private int currentPage;
  private int totalPages;

  public GetRestaurantsResponse(ArrayList<Restaurant> restaurants, int currentPage, int totalPages) {
    this.restaurants = restaurants;
    this.currentPage = currentPage;
    this.totalPages = totalPages;
  }

  public List<Restaurant> getRestaurants() {
    return restaurants;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setRestaurants(List<Restaurant> restaurants) {
    this.restaurants = restaurants;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }
}
