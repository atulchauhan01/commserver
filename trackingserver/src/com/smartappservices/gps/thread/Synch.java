/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.smartappservices.gps.thread;

import java.text.NumberFormat;


 /**

    A benchmark to test the overhead of synchronization on a simple
    method invocation. Benchmarking java, particularly when Hot-
    Spot is in the equation, is tricky. There's a good tech note on this
    subject at http://java.sun.com/products/hotspot/Q+A.html.
  */

 public class Synch
 {
     private static long[]       locking_time    = new long[100];
     private static long[]       not_locking_time = new long[100];
     private static final int    ITERATIONS      = 1000000;

     synchronized long locking     (long a, long b){return a + b;}
     long              not_locking (long a, long b){return a + b;}

     private void test( int id )
     {
         long start = System.currentTimeMillis();

         for(long i = ITERATIONS; --i >= 0 ;)
         { locking(i,i);
         }

         locking_time[id] = System.currentTimeMillis() - start;
         start            = System.currentTimeMillis();

         for(long i = ITERATIONS; --i >= 0 ;)
         {   not_locking(i,i);
         }

         not_locking_time[id] = System.currentTimeMillis() - start;
     }

     static void print_results( int id )
     {

         NumberFormat compositor = NumberFormat.getInstance();
         compositor.setMaximumFractionDigits( 2 );

         double time_in_synchronization = locking_time[id] - not_locking_time[id];

         System.out.println( "Pass " + id + ": Time lost:  "
                 + compositor.format( time_in_synchronization                    )
                 + " ms. "
                 + compositor.format( ((double)locking_time[id]/
                                                   not_locking_time[id])*100.0)
                 + "% increase"
                 );
     }

     static public void main(String[] args) throws InterruptedException
     {
         // First, with no contention:

         final Synch tester = new Synch();
         tester.test(0); print_results(0);
         tester.test(1); print_results(1);
         tester.test(2); print_results(2);
         tester.test(3); print_results(3);
         tester.test(4); print_results(4);
         tester.test(5); print_results(5);
         tester.test(6); print_results(6);

         // Now let's do it again with contention. I'm assuming that
         // hotspot has optimized the test method by now, so am only
         // calling it once.

         final Object start_gate = new Object();

         Thread t1 = new Thread()
         {   public void run()
             {   try{ synchronized(start_gate) { start_gate.wait(); } }
                 catch( InterruptedException e ){}

                 tester.test(7);
             }
         };
         Thread t2 = new Thread()
         { public void run()
           {   try{ synchronized(start_gate) { start_gate.wait(); } }
               catch( InterruptedException e ){}

               tester.test(8);
           }
         };

         Thread.currentThread().setPriority( Thread.MIN_PRIORITY );

         t1.start();
         t2.start();

         synchronized(start_gate){ start_gate.notifyAll(); }

         t1.join();
         t2.join();

         print_results( 7 );
         print_results( 8 );
     }
}