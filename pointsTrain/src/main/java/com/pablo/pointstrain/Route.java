package com.pablo.pointstrain;

/**
 * 
 * @author pawBs
 */
public class Route {

  protected String startTown;
  protected String endTown;
  protected int distanceOfRoute;

  /**
   * First node in route linkedlist
   */
  protected Route nextRouteInList;
  
  /**
   *
   * @param startTown
   * @param endTown
   * @param distanceOfRoute
   */
  public Route(String startTown, String endTown, int distanceOfRoute) {
    this.startTown = startTown;
    this.endTown = endTown;
    this.distanceOfRoute = distanceOfRoute;
    this.nextRouteInList = null;
  }
  
  /**
   * Implementing an arraylist here would prevent the null check
   * @param route
   * @return
   */
  public int addRoute(Route route){
    
    if (this.nextRouteInList !=null) {
      nextRouteInList.addRoute(route);
    } else {
      nextRouteInList = route;
    }
    
    return 1;
  }
  
}
