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
public class Town {
  protected String nameOfTown;
  protected Town nextTownInList;
  
  public Town(String nameOfTown){
    this.nameOfTown = nameOfTown;
  }
  
  public int addTown(Town town){
    
    if (this.nextTownInList !=null) {
      nextTownInList.addTown(town);
    } else {
      nextTownInList = town;
    }
    
    return 1;
  }
  
}
