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
  
  public DirectedGraph(){
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
        LOGGER.debug("Destination not reached, no possible route found");
        return -1;
      }
    }
    
    LOGGER.debug("Destination Reached, distance is..: " + distance);
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
          //reached destination, whether we should increment depends on some of
          //the options
          
          
          if (calculateMax){
            if (!lessThan){
              if (maxN >= distance){
                LOGGER.debug("Destination Reached: " + pathHistory + startTown + endTown);
                count++;
              }
            }
            else {
              if (maxN > distance){
                LOGGER.debug("Destination Reached: " + pathHistory + startTown + endTown);
                count++;
              }
            }
          }
          else {
            if (maxN == distance) {
              LOGGER.debug("Destination Reached: " + pathHistory + startTown + endTown);
              count ++;
            }
          }
        }
        
        //recursion: move to next node
        count += numberOfTrips(firstRouteInInput.endTown, endTown, maxN - distance, calculateMax, useDistance, lessThan, pathHistory + startTown);
      }
    } while ((firstRouteInInput = firstRouteInInput.nextRouteInList) != null);
    
    return count;
  }
  
  public int distanceOfShortestRoute(String startTown, String endTown){
    
    //Setup visited-towns list, initialize start point to 0
    Town visitedTowns = new Town();
    Town candidateTowns;
    String lowestTown;
    int lowestTownValue;
    visitedTowns.addTown(new Town(startTown));
    visitedTowns.addTown(new Town(endTown));
    
    
    Route firstRouteInInput;
    
    firstRouteInInput = firstRouteInList;
    do {
      visitedTowns.addTown(new Town(firstRouteInInput.startTown));
      visitedTowns.neighborDistance = 0;
      visitedTowns.addTown(new Town(firstRouteInInput.endTown));
    } while ((firstRouteInInput = firstRouteInInput.nextRouteInList) != null);
    

    //start with initial node
    String nameOfCurrentTown = startTown;
    
    do {
      firstRouteInInput = firstRouteInList;
      do {
        if (firstRouteInInput.startTown.equals(nameOfCurrentTown)){

          //update neighboring towns
          if (visitedTowns.getTownByName(firstRouteInInput.endTown).getNeighborDistance() > firstRouteInInput.distanceOfRoute + visitedTowns.getTownByName(nameOfCurrentTown).getNeighborDistance()) {
            visitedTowns.getTownByName(firstRouteInInput.endTown).setNeighborDistance(firstRouteInInput.distanceOfRoute + visitedTowns.getTownByName(nameOfCurrentTown).getNeighborDistance());
            LOGGER.debug("set town " + firstRouteInInput.endTown);
            LOGGER.debug(visitedTowns.getTownByName(firstRouteInInput.endTown).getNeighborDistance());
          }

        }
      } while ((firstRouteInInput = firstRouteInInput.nextRouteInList) != null);
      visitedTowns.getTownByName(nameOfCurrentTown).setIsVisited(true);
      
      //get next lowest un-visited town
      candidateTowns = visitedTowns;
      lowestTown = "notFound";
      lowestTownValue=Integer.MAX_VALUE;
      do {
        if (!candidateTowns.isVisited && candidateTowns.neighborDistance < lowestTownValue){
          lowestTownValue = candidateTowns.neighborDistance;
          lowestTown = candidateTowns.nameOfTown;
        }
      } while ((candidateTowns = candidateTowns.nextTownInList) != null);
      
      if (lowestTown.equals("notFound")) {
        LOGGER.error("Route not found");
        return -1;
      } else if (lowestTown.equals(endTown)) {
        break;
      }
      else {
        nameOfCurrentTown = lowestTown;
      }
    } while (true);
    
    LOGGER.debug("shortest trip: " + visitedTowns.getTownByName(endTown).getNeighborDistance());
    return visitedTowns.getTownByName(endTown).getNeighborDistance();
  }
}
