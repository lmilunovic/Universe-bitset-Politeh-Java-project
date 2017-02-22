package com.ladislav;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ladislav on 2/16/2017.
 * This is class for testing
 */
public class Testing {
    // TODO (5) write test for all methods

  @Test
  public void testConstructor() {
      int n = 10;
      Universe universe = new Universe(10);
      assertEquals(n, universe.size());

  }
}
