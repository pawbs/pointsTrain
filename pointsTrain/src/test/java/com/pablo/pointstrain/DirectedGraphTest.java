/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablo.pointstrain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pawBs
 */
public class DirectedGraphTest {
  
  public DirectedGraphTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }

  @Test
  public void testSomeMethod() {
    // TODO review the generated test code and remove the default call to fail.
    DirectedGraph testGraph = new DirectedGraph();
    
    /*testGraph.addTown(new Town("A"));
    testGraph.addTown(new Town("B"));
    testGraph.addTown(new Town("C"));
    
    testGraph.addRoute(new Route("A", "B", 1));
    testGraph.addRoute(new Route("A", "C", 2));
    testGraph.addRoute(new Route("C", "B", 3));
    testGraph.addRoute(new Route("C", "A", 4));*/
    
    
    testGraph.addRoutesWithCodedString("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
    //testGraph.addRoutesWithCodedString("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AEA");
    
    testGraph.distanceOfRoute("A-B-C");
    testGraph.distanceOfRoute("A-D");
    testGraph.distanceOfRoute("A-D-C");
    testGraph.distanceOfRoute("A-E-B-C-D");
    testGraph.distanceOfRoute("A-E-D");
    
    
    assert(true);
  }
  
}
