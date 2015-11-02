package com.pablo.pointstrain;

/**
 * Used for (8) and (9) to implement node-list and visited list via 
 * Dijkstra's Algorithm
 * @author pawBs
 */
public class Town {

  protected String nameOfTown;
  protected Town nextTownInList;
  protected int neighborDistance;
  protected boolean isVisited;

  public boolean isVisited() {
    return isVisited;
  }

  public void setIsVisited(boolean isVisited) {
    this.isVisited = isVisited;
  }

  public int getNeighborDistance() {
    return neighborDistance;
  }

  public void setNeighborDistance(int neighborDistance) {
    this.neighborDistance = neighborDistance;
  }
  
  public Town() {
    //nullobject constructor
  }
  
  public Town(String nameOfTown){
    this.nameOfTown = nameOfTown;
    this.neighborDistance = Integer.MAX_VALUE;
    isVisited = false;
  }
  
  /**
   * Implementing an arraylist here would prevent the null check
   * @param town
   * @return
   */
  public int addTown(Town town){
    
    if (this.nextTownInList !=null) {
      if (this.findTownByName(town.nameOfTown)){
        return 1;
      }
      nextTownInList.addTown(town);
    } else {
      if (this.nameOfTown != null){
        nextTownInList = town;
      } else {
        this.nameOfTown = town.nameOfTown;
        this.neighborDistance = Integer.MAX_VALUE;
        isVisited = false;
      }
    }
    
    return 1;
  }
  
  /**
   *
   * @param searchName
   * @return
   * True if found otherwise false
   */
  public boolean findTownByName(String searchName){
    if (this.nameOfTown.equals(searchName)) {
      return true;
    }
    
    if (this.nextTownInList !=null) {
      return this.nextTownInList.findTownByName(searchName);
    } else {
      return false;
    }
  }
  
  /**
   *
   * @param searchName
   * @return
   * Town if found, otherwise null object
   */
  public Town getTownByName(String searchName){
    if (this.nameOfTown.equals(searchName)) {
      return this;
    }
    
    if (this.nextTownInList !=null) {
      return this.nextTownInList.getTownByName(searchName);
    } else {
      return null;
    }
  }
  
}
