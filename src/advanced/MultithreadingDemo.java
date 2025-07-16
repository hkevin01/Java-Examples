package advanced;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * MultithreadingDemo - Comprehensive demonstration of Java multithreading concepts
 * 
 * WHAT IS MULTITHREADING?
 * Multithreading allows a program to execute multiple threads concurrently within a single process.
 * Each thread represents an independent path of execution that can run simultaneously with other threads.
 * 
 * WHY MULTITHREADING?
 * - Performance: Utilize multiple CPU cores for parallel processing
 * - Responsiveness: Keep UI responsive while background tasks run
 * - Throughput: Handle multiple requests simultaneously (web servers)
 * - Resource Utilization: Better CPU and I/O resource usage
 * - Scalability: Handle increasing load by adding more threads
 * 
 * MULTITHREADING CHALLENGES:
 * - Thread Safety: Ensuring data consistency across threads
 * - Race Conditions: Multiple threads accessing shared data simultaneously
 * - Deadlocks: Threads waiting for each other indefinitely
 * - Resource Contention: Threads competing for limited resources
 * - Complexity: Harder to debug and reason about program behavior
 * 
 * JAVA THREADING TOOLKIT:
 * 1. Thread Creation: Thread class, Runnable interface, Callable interface
 * 2. Synchronization: synchronized keyword, locks, atomic variables
 * 3. Thread Pools: ExecutorService for efficient thread management
 * 4. Concurrent Collections: Thread-safe data structures
 * 5. High-level Synchronizers: CountDownLatch, Semaphore, CyclicBarrier
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class MultithreadingDemo {
    
    /**
     * THREAD BASICS: Creating and managing threads
     * 
     * THREAD CREATION METHODS:
     * 1. Extend Thread class (less flexible - single inheritance)
     * 2. Implement Runnable interface (preferred - composition over inheritance)
     * 3. Implement Callable interface (for tasks that return results)
     * 
     * THREAD LIFECYCLE:
     * NEW -> RUNNABLE -> BLOCKED/WAITING/TIMED_WAITING -> TERMINATED
     */
    public static void demonstrateThreadBasics() {
        System.out.println("=== THREAD BASICS DEMONSTRATION ===\n");
        
        // METHOD 1: Extending Thread class
        System.out.println("1. Creating Thread by extending Thread class:");
        class CustomThread extends Thread {
            private final String threadName;
            
            public CustomThread(String name) {
                this.threadName = name;
            }
            
            @Override
            public void run() {
                for (int i = 1; i <= 5; i++) {
                    System.out.println(threadName + " - Count: " + i + 
                        " [Thread: " + Thread.currentThread().getName() + "]");
                    try {
                        Thread.sleep(1000); // Pause for 1 second
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(threadName + " was interrupted");
                        return;
                    }
                }
                System.out.println(threadName + " completed!");
            }
        }
        
        CustomThread thread1 = new CustomThread("CustomThread-1");
        thread1.start(); // Start the thread (calls run() method)
        
        // METHOD 2: Implementing Runnable interface (PREFERRED)
        System.out.println("\n2. Creating Thread by implementing Runnable:");
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            for (int i = 1; i <= 3; i++) {
                System.out.println("Runnable Task - Count: " + i + 
                    " [Thread: " + threadName + "]");
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };
        
        Thread thread2 = new Thread(task, "RunnableThread-1");
        Thread thread3 = new Thread(task, "RunnableThread-2");
        
        thread2.start();
        thread3.start();
        
        // THREAD JOINING: Wait for threads to complete
        try {
            System.out.println("\n3. Waiting for threads to complete...");
            thread1.join(); // Wait for thread1 to finish
            thread2.join(); // Wait for thread2 to finish
            thread3.join(); // Wait for thread3 to finish
            System.out.println("All threads completed!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println();
    }
    
    /**
     * THREAD POOLS: Efficient thread management using ExecutorService
     * 
     * WHY THREAD POOLS?
     * - Reuse threads instead of creating new ones (performance)
     * - Control number of concurrent threads (resource management)
     * - Queue tasks when all threads are busy (load management)
     * - Automatic thread lifecycle management
     * 
     * TYPES OF THREAD POOLS:
     * - Fixed Thread Pool: Fixed number of threads
     * - Cached Thread Pool: Creates threads as needed, reuses idle threads
     * - Single Thread Executor: Single worker thread
     * - Scheduled Thread Pool: For delayed and periodic tasks
     */
    public static void demonstrateThreadPools() {
        System.out.println("=== THREAD POOLS DEMONSTRATION ===\n");
        
        // FIXED THREAD POOL: Fixed number of worker threads
        System.out.println("1. Fixed Thread Pool (3 threads):");
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        
        // Submit multiple tasks
        for (int i = 1; i <= 6; i++) {
            final int taskNumber = i;
            fixedPool.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Task " + taskNumber + " started on " + threadName);
                try {
                    Thread.sleep(2000); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskNumber + " completed on " + threadName);
            });
        }
        
        // Shutdown the thread pool
        fixedPool.shutdown();
        try {
            if (!fixedPool.awaitTermination(10, TimeUnit.SECONDS)) {
                fixedPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            fixedPool.shutdownNow();
        }
        
        // CACHED THREAD POOL: Creates threads as needed
        System.out.println("\n2. Cached Thread Pool:");
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        
        for (int i = 1; i <= 3; i++) {
            final int taskNumber = i;
            cachedPool.submit(() -> {
                System.out.println("Cached pool task " + taskNumber + 
                    " on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        cachedPool.shutdown();
        try {
            cachedPool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            cachedPool.shutdownNow();
        }
        
        // SCHEDULED THREAD POOL: For delayed and periodic tasks
        System.out.println("\n3. Scheduled Thread Pool:");
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);
        
        // Schedule a one-time delayed task
        scheduledPool.schedule(() -> {
            System.out.println("Delayed task executed after 2 seconds");
        }, 2, TimeUnit.SECONDS);
        
        // Schedule a periodic task
        ScheduledFuture<?> periodicTask = scheduledPool.scheduleAtFixedRate(() -> {
            System.out.println("Periodic task executed at " + new Date());
        }, 1, 3, TimeUnit.SECONDS);
        
        // Cancel periodic task after 10 seconds
        scheduledPool.schedule(() -> {
            periodicTask.cancel(false);
            System.out.println("Periodic task cancelled");
            scheduledPool.shutdown();
        }, 10, TimeUnit.SECONDS);
        
        try {
            scheduledPool.awaitTermination(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            scheduledPool.shutdownNow();
        }
        
        System.out.println();
    }
    
    /**
     * SYNCHRONIZATION: Ensuring thread safety when accessing shared resources
     * 
     * SYNCHRONIZATION MECHANISMS:
     * 1. synchronized keyword: Method-level or block-level synchronization
     * 2. ReentrantLock: More flexible than synchronized
     * 3. ReadWriteLock: Separate locks for read and write operations
     * 4. Atomic variables: Lock-free thread-safe operations
     * 
     * RACE CONDITION EXAMPLE: Multiple threads incrementing a counter
     */
    public static void demonstrateSynchronization() {
        System.out.println("=== SYNCHRONIZATION DEMONSTRATION ===\n");
        
        // UNSYNCHRONIZED COUNTER (demonstrates race condition)
        class UnsafeCounter {
            private int count = 0;
            
            public void increment() {
                count++; // This is NOT atomic: read, increment, write
            }
            
            public int getCount() {
                return count;
            }
        }
        
        // SYNCHRONIZED COUNTER (thread-safe)
        class SafeCounter {
            private int count = 0;
            
            public synchronized void increment() {
                count++; // Synchronized method ensures atomic operation
            }
            
            public synchronized int getCount() {
                return count;
            }
        }
        
        // LOCK-BASED COUNTER (more flexible than synchronized)
        class LockBasedCounter {
            private int count = 0;
            private final ReentrantLock lock = new ReentrantLock();
            
            public void increment() {
                lock.lock();
                try {
                    count++;
                } finally {
                    lock.unlock(); // Always unlock in finally block
                }
            }
            
            public int getCount() {
                lock.lock();
                try {
                    return count;
                } finally {
                    lock.unlock();
                }
            }
        }
        
        // Test race condition with unsafe counter
        System.out.println("1. Demonstrating Race Condition (Unsafe Counter):");
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        testCounter("Unsafe", () -> unsafeCounter.increment(), unsafeCounter::getCount);
        
        // Test thread safety with synchronized counter
        System.out.println("\n2. Thread-Safe Synchronized Counter:");
        SafeCounter safeCounter = new SafeCounter();
        testCounter("Safe", () -> safeCounter.increment(), safeCounter::getCount);
        
        // Test thread safety with lock-based counter
        System.out.println("\n3. Thread-Safe Lock-Based Counter:");
        LockBasedCounter lockCounter = new LockBasedCounter();
        testCounter("Lock-based", () -> lockCounter.increment(), lockCounter::getCount);
        
        System.out.println();
    }
    
    /**
     * Helper method to test counter implementations with multiple threads
     */
    private static void testCounter(String counterType, Runnable incrementTask, Supplier<Integer> getCount) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        final int increments = 1000;
        final int numThreads = 10;
        
        // Submit increment tasks
        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < increments; j++) {
                    incrementTask.run();
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        int finalCount = getCount.get();
        int expectedCount = numThreads * increments;
        System.out.printf("%s Counter - Expected: %d, Actual: %d, Correct: %b%n",
            counterType, expectedCount, finalCount, finalCount == expectedCount);
    }
    
    /**
     * ATOMIC OPERATIONS: Lock-free thread-safe operations
     * 
     * ATOMIC CLASSES:
     * - AtomicInteger, AtomicLong: Atomic numeric operations
     * - AtomicBoolean: Atomic boolean operations
     * - AtomicReference: Atomic object reference operations
     * - AtomicIntegerArray: Atomic array operations
     * 
     * ADVANTAGES:
     * - Better performance than synchronized (no locking overhead)
     * - Lock-free programming (no deadlock possibility)
     * - Compare-and-swap (CAS) operations
     */
    public static void demonstrateAtomicOperations() {
        System.out.println("=== ATOMIC OPERATIONS DEMONSTRATION ===\n");
        
        // ATOMIC INTEGER OPERATIONS
        System.out.println("1. AtomicInteger Operations:");
        AtomicInteger atomicCounter = new AtomicInteger(0);
        
        // Basic operations
        System.out.println("Initial value: " + atomicCounter.get());
        System.out.println("Increment and get: " + atomicCounter.incrementAndGet());
        System.out.println("Get and increment: " + atomicCounter.getAndIncrement());
        System.out.println("Add 5: " + atomicCounter.addAndGet(5));
        System.out.println("Current value: " + atomicCounter.get());
        
        // COMPARE AND SET (CAS) operation
        System.out.println("\n2. Compare-and-Set Operations:");
        boolean success = atomicCounter.compareAndSet(7, 100);
        System.out.println("CAS(7, 100): " + success + ", Value: " + atomicCounter.get());
        
        success = atomicCounter.compareAndSet(6, 100);
        System.out.println("CAS(6, 100): " + success + ", Value: " + atomicCounter.get());
        
        // ATOMIC COUNTER PERFORMANCE TEST
        System.out.println("\n3. Atomic Counter Performance Test:");
        AtomicInteger atomicPerformanceCounter = new AtomicInteger(0);
        
        long startTime = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 100000; j++) {
                    atomicPerformanceCounter.incrementAndGet();
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.printf("Atomic counter final value: %d (Time: %d ms)%n",
            atomicPerformanceCounter.get(), endTime - startTime);
        
        // ATOMIC REFERENCE EXAMPLE
        System.out.println("\n4. AtomicReference Example:");
        AtomicReference<String> atomicString = new AtomicReference<>("Initial");
        
        System.out.println("Initial: " + atomicString.get());
        atomicString.set("Updated");
        System.out.println("After set: " + atomicString.get());
        
        boolean updated = atomicString.compareAndSet("Updated", "Final");
        System.out.println("CAS result: " + updated + ", Value: " + atomicString.get());
        
        System.out.println();
    }
    
    /**
     * PRODUCER-CONSUMER PATTERN: Classic concurrency pattern
     * 
     * PATTERN DESCRIPTION:
     * - Producer threads create/generate data
     * - Consumer threads process/consume data
     * - Shared buffer coordinates between producers and consumers
     * 
     * IMPLEMENTATION APPROACHES:
     * 1. BlockingQueue: Built-in thread-safe queue
     * 2. wait()/notify(): Low-level synchronization
     * 3. Locks and Conditions: More flexible control
     */
    public static void demonstrateProducerConsumer() {
        System.out.println("=== PRODUCER-CONSUMER PATTERN DEMONSTRATION ===\n");
        
        // Using BlockingQueue (recommended approach)
        System.out.println("1. Producer-Consumer with BlockingQueue:");
        
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5); // Bounded queue
        
        // Producer task
        Runnable producer = () -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    queue.put(i); // Blocks if queue is full
                    System.out.println("Produced: " + i + " [Queue size: " + queue.size() + "]");
                    Thread.sleep(500); // Simulate production time
                }
                queue.put(-1); // Poison pill to signal end
                System.out.println("Producer finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        // Consumer task
        Runnable consumer = () -> {
            try {
                while (true) {
                    Integer item = queue.take(); // Blocks if queue is empty
                    if (item == -1) { // Poison pill
                        queue.put(-1); // Put back for other consumers
                        break;
                    }
                    System.out.println("Consumed: " + item + " [Queue size: " + queue.size() + "]");
                    Thread.sleep(1000); // Simulate processing time
                }
                System.out.println("Consumer finished");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        // Start producer and consumer threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(producer);
        executor.submit(consumer);
        executor.submit(consumer); // Multiple consumers
        
        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println();
    }
    
    /**
     * CONCURRENT COLLECTIONS: Thread-safe data structures
     * 
     * CONCURRENT COLLECTIONS:
     * - ConcurrentHashMap: Thread-safe HashMap
     * - CopyOnWriteArrayList: Thread-safe ArrayList for read-heavy scenarios
     * - ConcurrentLinkedQueue: Thread-safe queue
     * - BlockingQueue implementations: Producer-consumer scenarios
     * 
     * ADVANTAGES:
     * - Better performance than synchronized collections
     * - Fine-grained locking strategies
     * - Non-blocking read operations (in most cases)
     */
    public static void demonstrateConcurrentCollections() {
        System.out.println("=== CONCURRENT COLLECTIONS DEMONSTRATION ===\n");
        
        // CONCURRENT HASH MAP
        System.out.println("1. ConcurrentHashMap Example:");
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        
        // Populate map concurrently
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            executor.submit(() -> {
                for (int j = 0; j < 3; j++) {
                    String key = "Thread-" + threadId + "-Item-" + j;
                    concurrentMap.put(key, threadId * 10 + j);
                }
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("ConcurrentHashMap size: " + concurrentMap.size());
        concurrentMap.forEach((key, value) -> 
            System.out.println(key + " = " + value));
        
        // CONCURRENT MODIFICATIONS
        System.out.println("\n2. Concurrent Modifications:");
        
        // Atomic operations on ConcurrentHashMap
        concurrentMap.compute("NewKey", (key, val) -> val == null ? 1 : val + 1);
        concurrentMap.computeIfAbsent("AnotherKey", key -> key.length());
        concurrentMap.merge("MergeKey", 100, Integer::sum);
        
        System.out.println("After atomic operations:");
        System.out.println("NewKey: " + concurrentMap.get("NewKey"));
        System.out.println("AnotherKey: " + concurrentMap.get("AnotherKey"));
        System.out.println("MergeKey: " + concurrentMap.get("MergeKey"));
        
        // COPY ON WRITE ARRAY LIST
        System.out.println("\n3. CopyOnWriteArrayList Example:");
        CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();
        
        cowList.add("Initial Item");
        
        // Iterator that won't see concurrent modifications
        Iterator<String> iterator = cowList.iterator();
        
        // Add items while iterator exists (safe with CopyOnWriteArrayList)
        cowList.add("Added After Iterator");
        cowList.add("Another Addition");
        
        System.out.println("Iterator sees: " + (iterator.hasNext() ? iterator.next() : "nothing more"));
        System.out.println("Full list: " + cowList);
        
        System.out.println();
    }
    
    /**
     * Main method demonstrating all multithreading concepts
     */
    public static void main(String[] args) {
        System.out.println("🚀 JAVA MULTITHREADING COMPREHENSIVE DEMO");
        System.out.println("=" .repeat(60));
        
        demonstrateThreadBasics();
        System.out.println("-".repeat(60));
        
        demonstrateThreadPools();
        System.out.println("-".repeat(60));
        
        demonstrateSynchronization();
        System.out.println("-".repeat(60));
        
        demonstrateAtomicOperations();
        System.out.println("-".repeat(60));
        
        demonstrateProducerConsumer();
        System.out.println("-".repeat(60));
        
        demonstrateConcurrentCollections();
        
        System.out.println("✅ All multithreading demonstrations completed!");
        System.out.println("\n📚 KEY TAKEAWAYS:");
        System.out.println("• Threads enable parallel execution and improved performance");
        System.out.println("• Thread pools manage thread resources efficiently");
        System.out.println("• Synchronization prevents race conditions but can impact performance");
        System.out.println("• Atomic operations provide lock-free thread safety");
        System.out.println("• Producer-Consumer pattern solves classic concurrency problems");
        System.out.println("• Concurrent collections offer better performance than synchronized collections");
        System.out.println("• Always consider thread safety when designing multithreaded applications");
    }
}
