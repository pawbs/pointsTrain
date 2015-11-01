/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablo.pointstrain;

/**
 *
 * @author pawBs
 */
public class Route {
  protected String startTown;
  protected String endTown;
  protected int distanceOfRoute;
  protected Route nextRouteInList;

  public Route(String startTown, String endTown, int distanceOfRoute) {
    this.startTown = startTown;
    this.endTown = endTown;
    this.distanceOfRoute = distanceOfRoute;
    this.nextRouteInList = null;
  }
  
}
