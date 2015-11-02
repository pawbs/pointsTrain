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
  
  public int addRoute(Route route){
    
    if (this.nextRouteInList !=null) {
      nextRouteInList.addRoute(route);
    } else {
      nextRouteInList = route;
    }
    
    return 1;
  }
  
}
