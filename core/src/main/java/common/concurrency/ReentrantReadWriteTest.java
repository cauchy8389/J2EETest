package common.concurrency;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyTask {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void read() {
        try {
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void write() {
        try {
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}

public class ReentrantReadWriteTest {
    public static void main(String[] args) {
        final MyTask myTask = new MyTask();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                myTask.read();
            }
        });
        t1.setName("t1(read)");

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                myTask.read();
            }
        });
        t2.setName("t2(read)");

        t1.start();
        t2.start();

        //  读写互斥

        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                myTask.read();
            }
        });
        t3.setName("t3(read)");

        Thread t4 = new Thread(new Runnable() {

            @Override
            public void run() {
                myTask.write();
            }
        });
        t4.setName("t4(write)");

        t3.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t4.start();
    }
}
