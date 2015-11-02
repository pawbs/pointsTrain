/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablo.pointstrain;

/**
 * Used for (8) and (9) to implement node-list and visited list via 
 * Dijkstra's Algorithm
 * source: https://www.youtube.com/watch?v=gdmfOwyQlcI
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
