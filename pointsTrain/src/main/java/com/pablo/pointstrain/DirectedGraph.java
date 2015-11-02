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
        LOGGER.info("Destination not reached, no possible route found");
        return -1;
      }
    }
    
    LOGGER.info("Destination Reached, distance is..: " + distance);
    return distance;
  }
  
  public int numberOfTripsWithMaxNStops(String startTown, String endTown, int maxNStops) {
    return numberOfTrips(startTown, endTown, maxNStops, true, false, false, "");
  }
  
  public int numberOfTripsWithExactlyNStops(String startTown, String endTown, int maxNStops) {
    return numberOfTrips(startTown, endTown, maxNStops, false, false, false, "");
  }
  
  public int numberOfTripsWithDistanceLessThanN(String startTown, String endTown, int maxNDistance) {
    return numberOfTrips(startTown, endTown, maxNDistance, true, true, true, "");
  }
  
  public int numberOfTrips(String startTown, String endTown, int maxN, boolean calculateMax, boolean useDistance, boolean lessThan, String pathHistory){
    
    Route firstRouteInInput;
    int distance;
    int count = 0;
    
    if (maxN <= 0) {
      //reached the end
      return 0;
    }
    
    firstRouteInInput = firstRouteInList;
    do {
      if (startTown.equals(firstRouteInInput.startTown)){
        
        if (useDistance){
          distance = firstRouteInInput.distanceOfRoute;
        }
        else {
          //we are counting stops, each edge has a weight of 1
          distance = 1;
        }
        if (endTown.equals(firstRouteInInput.endTown)){
          //reached destination
          
          
          if (calculateMax){
            if (!lessThan){
              if (maxN >= distance){
                LOGGER.info("Destination Reached: " + pathHistory + startTown + endTown);
                count++;
              }
            }
            else {
              if (maxN > distance){
                LOGGER.info("Destination Reached: " + pathHistory + startTown + endTown);
                count++;
              }
            }
          }
          else {
            if (maxN == distance) {
              LOGGER.info("Destination Reached: " + pathHistory + startTown + endTown);
              count ++;
            }
          }
        }
        
        //move to next node
        count += numberOfTrips(firstRouteInInput.endTown, endTown, maxN - distance, calculateMax, useDistance, lessThan, pathHistory + startTown);
      }
    } while ((firstRouteInInput = firstRouteInInput.nextRouteInList) != null);
    
    return count;
  }
  
  public int distanceOfShortestRoute(){
    return 1;
  }
}
