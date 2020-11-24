# Basic concurrency classes

- The Thread class:

  1.  t1.join: wait unit t1 is completed
  2.  Thread.currentThread().yield(), other thread to excute
  3.  sleep()与 wait()的区别和联系：

  二者都是让线程暂停运行。
  sleep()不会释放任何锁，wait()会释放对象上的锁。
  sleep()正常恢复的方式只能是等待时间耗尽，wait()除了等待时间耗尽，还可以被其他线程唤醒（notify()和 notifyAll 4.

- The Runnable interface
- The ThreadLocal class
- The ThreadFactory interface

# Synchronization mechanisms

- The synchronized keyword
- The Lock interface:
  - ReentrantLock,
  - ReentrantReadWriteLock
  - StampedLock
- The Semaphore class [ /ˈseməfɔː(r)/ ]

As semaphore typically has two uses:

To guard a critical section against entry by more than N threads at a time.
To send signals between two thread
控制线程的并发数量

```
acquire()
release()
```

- The CountDownLatch class
  所有线程完成后才继续： 倒计时锁

```
//main thread
CountDownLatch latch = new CountDownLatch(num);
latch.wait()

//thread
latch.countDown();
```

- The CyclicBarrier class
  CyclicBarrier:可看成是个障碍，所有的线程必须到齐后才能一起通过这个障碍

  CountDownLatch 是计数器，线程完成一个记录一个，只不过计数不是递增而是递减，而 CyclicBarrier 更像是一个阀门，需要所有线程都到达，阀门才能打开，然后继续执行。

- The Phaser class

# Executors

- ThreadPoolExecutor
- ScheduledThreadPoolExecutor
- Executors
- The Callable interface
- The Future interface

# The fork/join framework

- ForkJoinPool:
- ForkJoinTask:
- ForkJoinWorkerThread:

# Concurrency design patterns

- Signaling
- Rendezvous
- Mutex: ReentrantLock, `synchronized`
- Multiplex: Semaphore
- Barrier: CyclicBarrier
- Read-write lock: ReentrantReadWriteLock
- Double-checked locking
- Thread pool : ExecutorService
- Thread local storage: `ThreadLocal`

CAS： Compare and Swap
