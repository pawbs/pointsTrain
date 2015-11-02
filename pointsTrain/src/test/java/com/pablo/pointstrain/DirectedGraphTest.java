/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablo.pointstrain;

import java.util.Collections;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 *
 * @author pawBs
 */
public class DirectedGraphTest {
  
  private final Logger LOGGER = Logger.getLogger(this.getClass());
  
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

  /**
   * Tests that I ran while i developed the application. comment out ignore to
   * try them
   */
  @Ignore
  @Test
  public void developmentTests() {
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
    
    testGraph.numberOfTripsWithMaxNStops("C", "C", 3);
    System.out.println("========");
    testGraph.numberOfTripsWithExactlyNStops("A", "C", 4);
    System.out.println("========");
    testGraph.numberOfTripsWithDistanceLessThanN("C", "C", 30);
    System.out.println("========"); 
    int result = testGraph.distanceOfShortestRoute("A", "C", false);
    
    assert(true);
  }
  
  /**
   *
   */
  @Test
  public void mainTest() {
    List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
    loggers.add(LogManager.getRootLogger());
    for ( Logger logger : loggers ) {
        logger.setLevel(Level.INFO);
        //logger.setLevel(Level.DEBUG);
    }
    int result;
    DirectedGraph testGraph = new DirectedGraph();
    testGraph.addRoutesWithCodedString("AB5, BC4, CD8, DC8, DE6, "
            + "AD5, CE2, EB3, AE7");
    
    
    LOGGER.info("Output #1: " + testGraph.distanceOfRoute("A-B-C"));
    LOGGER.info("Output #2: " + testGraph.distanceOfRoute("A-D"));
    LOGGER.info("Output #3: " + testGraph.distanceOfRoute("A-D-C"));
    LOGGER.info("Output #4: " + testGraph.distanceOfRoute("A-E-B-C-D"));
    
    //LOGGER.info("Output #5: " + testGraph.distanceOfRoute("A-E-D"));
    result = testGraph.distanceOfRoute("A-E-D");
    assert(result == -1);
    LOGGER.info("Output #5: NO SUCH ROUTE");
    
    LOGGER.info("Output #6: " + testGraph.numberOfTripsWithMaxNStops("C", "C", 3));
    LOGGER.info("Output #7: " + testGraph.numberOfTripsWithExactlyNStops("A", "C", 4));
    LOGGER.info("Output #8: " + testGraph.distanceOfShortestRoute("A", "C", false));
    LOGGER.info("Output #9: " + testGraph.distanceOfShortestRoute("B", "B", true));
    LOGGER.info("Output #10: " + testGraph.numberOfTripsWithDistanceLessThanN("C", "C", 30));
    LOGGER.info("==========");
            
    
  }
  
  /**
   * Error handling testcases here
   */
  @Ignore
  @Test
  public void sanitizeInputs() {
    
    List<Logger> loggers = Collections.<Logger>list(LogManager.getCurrentLoggers());
    loggers.add(LogManager.getRootLogger());
    for ( Logger logger : loggers ) {
        //logger.setLevel(Level.DEBUG);
        logger.setLevel(Level.INFO);
    }
    
    DirectedGraph testGraph = new DirectedGraph();
    testGraph.addRoutesWithCodedString("AB5, BC4, CD8, DC8, DE6, "
            + "AD5, CE2, EB3, AE7");
    
    assert(testGraph.distanceOfRoute("asdfasdf") == -1);
    assert(testGraph.distanceOfRoute("") == -1);
    
    DirectedGraph emptyTestGraph = new DirectedGraph();
    testGraph.addRoutesWithCodedString("");
    assert(emptyTestGraph.distanceOfRoute("asdfasdf") == -1);
    assert(emptyTestGraph.distanceOfRoute("A-B-C") == -1);
    assert(emptyTestGraph.numberOfTripsWithExactlyNStops("A", "C", 4) == -1);
    assert(emptyTestGraph.distanceOfShortestRoute("A", "C", false) == -1);
    
  }
}
