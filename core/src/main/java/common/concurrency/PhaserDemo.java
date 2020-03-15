package common.concurrency;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class PhaserDemo
{
   public static void main(String[] args)
   {
      List<Runnable> tasks = new ArrayList<>();
      tasks.add(() -> System.out.printf("%s running at %d%n", 
                                        Thread.currentThread().getName(),
                                        System.currentTimeMillis()));
      tasks.add(() -> System.out.printf("%s running at %d%n", 
                                        Thread.currentThread().getName(),
                                        System.currentTimeMillis()));
      runTasks(tasks);
   }

   static void runTasks(List<Runnable> tasks) 
   {
      final Phaser phaser = new Phaser(1); // "1" (register self)
//      phaser.bulkRegister(3);
      // create and start threads
      for (final Runnable task: tasks)
      {
         System.out.println("register:" + phaser.register());
//         phaser.
         Runnable r = () ->
                      {
                         while (!phaser.isTerminated()) {
                            try {
                               Thread.sleep(50 + (int) (Math.random() * 300));
                            } catch (InterruptedException ie) {
                               System.out.println("interrupted thread");
                            }
                            System.out.println("arrived Runnable:" + phaser.arriveAndAwaitAdvance()); // await the ...
                            // creation of ...
                            // all tasks
                            task.run();
                         }
                      };
         //Executors.newSingleThreadExecutor().execute(r);
         new Thread(r).start();
      }
      // allow threads to start and deregister self
      System.out.println("arrived out:" + phaser.arriveAndDeregister());

      System.out.println("over");
   }
}