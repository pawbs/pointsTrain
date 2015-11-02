/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablo.pointstrain;
import org.apache.log4j.Logger;


/**
 * Handles user input and contains most of the logic
 * @author pawBs
 */
public class DirectedGraph {
  
  private final Logger LOGGER = Logger.getLogger(this.getClass());
  /**
   * Linked list of routes fed by the user to create the directed graph. 
   * "firstRouteInList" contains the first node, and links to other nodes.
   */
  protected Route firstRouteInList;
  
  /**
   *
   */
  public DirectedGraph(){
  }
  
  /**
   * 
   * @param route
   * @return
   */
  public int addRoute(Route route) {
    if (this.firstRouteInList !=null) {
      this.firstRouteInList.addRoute(route);
    } else {
      this.firstRouteInList = route;
    }
    
    return 1;
  }
  
  /**
   * wrapper for "addRoute" so that the user can input a string similar to
   * the graph input string in 
   * "/SoftwareEngineeringRecruitingTechnicalAssignment-Java.pdf" to create
   * the directed graph
   * @param input
   * encoded as such..: "SDX, SDX, SDX, SDX, SDX, SDX, .."
   * ..where  S=Source city
   *          D=Destination city
   *          X=Distance of the route
   * @return
   * return 0: badly formatted number
   * return 1: success
   */
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
            firstRouteInList.addRoute(new Route(item.substring(0, 1), 
                    item.substring(1, 2), Integer.parseInt(item.substring(2, 3))));
          } else {
            firstRouteInList = new Route(item.substring(0, 1), 
                    item.substring(1, 2), Integer.parseInt(item.substring(2, 3)));
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

  /**
   * Algorithm for 1-5
   * Calculates distance of a given input route in the directed graph
   * 
   * @param input
   * encoded as such..: X-X-X-X-X-..."
   * ..where:   X=city name
   * @return
   * return -1: route not found
   * else, returns distance between the two routes
   */
  public int distanceOfRoute(String input){
    
    String[] splitInput = input.split("-");
    Route firstRouteInInput;
    int distance = 0;
    boolean routeFound;
    
    for (int i=0; i<splitInput.length - 1; i++) {
      
      firstRouteInInput = firstRouteInList;
      routeFound = false;
      
      //this block below (...and every "do" block from here on out) can be 
      //simplified with a HashTable. finds matching route from list
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
        //NOTE: if this function needs to handle negative distances, this can
        //be refactored to return an exception instead of -1
        return -1;
      }
    }
    
    LOGGER.debug("Destination Reached, distance is..: " + distance);
    return distance;
  }
  
  /**
   * Wrapper for 6
   * @param startTown
   * @param endTown
   * @param maxNStops
   * @return
   */
  public int numberOfTripsWithMaxNStops(String startTown, 
          String endTown, int maxNStops) {
    return numberOfTrips(startTown, endTown, maxNStops, true, false, false, "");
  }
  
  /**
   * Wrapper for 7
   * @param startTown
   * @param endTown
   * @param maxNStops
   * @return
   */
  public int numberOfTripsWithExactlyNStops(String startTown, 
          String endTown, int maxNStops) {
    return numberOfTrips(startTown, endTown, maxNStops, false, false, false, "");
  }
  
  /**
   * Wrapper for 10
   * @param startTown
   * @param endTown
   * @param maxNDistance
   * @return
   */
  public int numberOfTripsWithDistanceLessThanN(String startTown, 
          String endTown, int maxNDistance) {
    return numberOfTrips(startTown, endTown, maxNDistance, true, true, true, "");
  }
  
  /**
   * perform an "exhaustive search" on the directed graph to find routes that 
   * match the below criteria
   * @param startTown
   * letter representing start town
   * @param endTown
   * letter representing destination town
   * @param maxN
   * maximum distance criteria to search for
   * @param calculateMax
   * if enabled, numberOfTrips will return all trips that are equal to or less
   * than the given distance. Otherwise, numberOfTrips will return only trips
   * that are exactly the given distance.
   * @param useDistance
   * if enabled, numberOfTrips will calculate the shortest distance including
   * information from the weights on the routes
   * @param lessThan
   * if enabled, numberOfTrips will consider a route that has the same distance
   * as the value maxN, to be invalid
   * 
   * @param pathHistory
   * @return
   */
  public int numberOfTrips(String startTown, String endTown, int maxN, 
          boolean calculateMax, boolean useDistance, boolean lessThan, 
          String pathHistory){
    
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
                LOGGER.debug("Destination Reached: " + pathHistory + startTown + 
                        endTown);
                count++;
              }
            }
            else {
              if (maxN > distance){
                LOGGER.debug("Destination Reached: " + pathHistory + startTown + 
                        endTown);
                count++;
              }
            }
          }
          else {
            if (maxN == distance) {
              LOGGER.debug("Destination Reached: " + pathHistory + startTown + 
                      endTown);
              count ++;
            }
          }
        }
        
        //recursion: move to next node
        count += numberOfTrips(firstRouteInInput.endTown, endTown, 
                maxN - distance, calculateMax, useDistance, lessThan, 
                pathHistory + startTown);
      }
    } while ((firstRouteInInput = firstRouteInInput.nextRouteInList) != null);
    
    return count;
  }
  
  /**
   * Implementation of Dijkstra's Algorithm.
   * Source: https://www.youtube.com/watch?v=gdmfOwyQlcI
   * 
   * algorithm:
   * 1) Start in first city
   * 2) update connected cities with the "neighbor distance value" based on
   *    the distance of the route taken
   * 3) mark current city as "visited"
   * 4) go to the next unmarked city with the shortest "neighbor distance value"
   * 5) go back to (2) and repeat until the destination city is visited, or 
   *    until all cities have been exhausted
   * 
   * @param startTown
   * letter representing start town
   * @param endTown
   * letter representing destination town
   * @param ignoreOriginFirstIter
   * Special case for Q9. In this scenario, the user wants to find a loop to
   * return to his origin. In this case, the algorithm will return "route not
   * found". This option changes the algorithm to "unvisit" the origin after
   * leaving, thus it will attempt to find a way to return to the origin
   * @return
   * return -1: route not found
   * otherwise, returns integer representing the distance between the start
   * and end towns.
   */
  public int distanceOfShortestRoute(String startTown, String endTown, boolean ignoreOriginFirstIter){
    
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
    
    //main loop
    do {
      firstRouteInInput = firstRouteInList;
      do {
        if (firstRouteInInput.startTown.equals(nameOfCurrentTown)){

          //update neighboring towns
          if (visitedTowns.getTownByName(firstRouteInInput.endTown).getNeighborDistance() 
                  > firstRouteInInput.distanceOfRoute + visitedTowns.getTownByName(nameOfCurrentTown).getNeighborDistance()) {
            
            visitedTowns.getTownByName(firstRouteInInput.endTown).setNeighborDistance(
                    firstRouteInInput.distanceOfRoute + visitedTowns.getTownByName(nameOfCurrentTown).getNeighborDistance());
            LOGGER.debug("set town " + firstRouteInInput.endTown);
            LOGGER.debug(visitedTowns.getTownByName(firstRouteInInput.endTown).getNeighborDistance());
            
          }

        }
      } while ((firstRouteInInput = firstRouteInInput.nextRouteInList) != null);
      
      //special case.. see javadoc for scenario
      //if this is the first iteration, then mark the start location as
      //unvisited and reset the neighbor distance. (Thus the algorithm begins
      //to search for a way back to the origin
      //..-otherwise, proceed as normal and mark this city as visited.
      if (!ignoreOriginFirstIter){
        visitedTowns.getTownByName(nameOfCurrentTown).setIsVisited(true);
      }
      else {
        visitedTowns.getTownByName(nameOfCurrentTown).setNeighborDistance(Integer.MAX_VALUE);
      }
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
      
      //check end conditions if destination is reached or route has not been 
      //found
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
    
    LOGGER.debug("shortest trip: " + 
            visitedTowns.getTownByName(endTown).getNeighborDistance());
    return visitedTowns.getTownByName(endTown).getNeighborDistance();
  }
}
