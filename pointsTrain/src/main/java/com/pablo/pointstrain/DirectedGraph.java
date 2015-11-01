/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablo.pointstrain;
import org.apache.log4j.Logger;


/**
 *
 * @author pawBs
 */
public class DirectedGraph {
  
  private final Logger LOGGER = Logger.getLogger(this.getClass());
  
  protected Route firstRouteInList;
  protected Town firstTownInList;
  
  public DirectedGraph(){
  }
  
  public int addTown(Town town) {
    if (this.firstTownInList !=null) {
      this.firstTownInList.addTown(town);
    } else {
      this.firstTownInList = town;
    }
    return 1;
  }
  
  public int addRoute(Route route) {
    if (this.firstRouteInList !=null) {
      this.firstRouteInList.addRoute(route);
    } else {
      this.firstRouteInList = route;
    }
    
    return 1;
  }
  
  public int addRoutesWithCodedString(String input) {
    
    Route firstRouteInList = null;
    
    String[] splitRoutes = input.split(", ");
    for (String item : splitRoutes) {
      if (item.length() != 3) {
        LOGGER.error("length of code incorrect");
        return 0;
      } else {
        try {
          if (firstRouteInList != null) {
            firstRouteInList.addRoute(new Route(item.substring(0, 1), item.substring(1, 2), Integer.parseInt(item.substring(2, 3))));
          } else {
            firstRouteInList = new Route(item.substring(0, 1), item.substring(1, 2), Integer.parseInt(item.substring(2, 3)));
          }
        } catch (java.lang.NumberFormatException e) {
          LOGGER.error("badly formatted number");
          return 0;
        }
      }
    }
    
    this.firstRouteInList = firstRouteInList;
    return 1;
  }

  
  
  public int distanceOfRoute(String input){
    
    String[] splitInput = input.split("-");
    Route firstRouteInInput;
    int distance = 0;
    boolean routeFound;
    
    for (int i=0; i<splitInput.length - 1; i++) {
      
      firstRouteInInput = firstRouteInList;
      routeFound = false;
      
      //..this block below can be simplified with a HashTable. find matching
      //route from list
      do{
        if (splitInput[i].equals(firstRouteInInput.startTown) && 
                splitInput[i+1].equals(firstRouteInInput.endTown)){
          distance += firstRouteInInput.distanceOfRoute;
          routeFound = true;
          break;
        }
      } while((firstRouteInInput = firstRouteInInput.nextRouteInList) != null);
      if (!routeFound){
        return -1;
      }
    }
    
    return distance;
  }
  
  public int numberOfTripsWithMaxNStops(String startTown, String endTown){
    return 1;
  }
}
