package patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ConcurrencyDemo - Demonstrates Java Concurrency and Multithreading
 * 
 * This comprehensive demo covers:
 * - Thread basics and lifecycle
 * - Synchronization mechanisms
 * - Thread pools and ExecutorService
 * - Concurrent collections
 * - Atomic operations
 * - Producer-Consumer pattern
 * - Thread-safe singleton patterns
 * - CompletableFuture and asynchronous programming
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// Thread Basics Example

class SimpleTask implements Runnable {
    private final String taskName;
    private final int iterations;
    
    public SimpleTask(String taskName, int iterations) {
        this.taskName = taskName;
        this.iterations = iterations;
    }
    
    @Override
    public void run() {
        System.out.println("🚀 " + taskName + " started on thread: " + Thread.currentThread().getName());
        
        for (int i = 1; i <= iterations; i++) {
            try {
                System.out.println("📋 " + taskName + " - Step " + i + "/" + iterations);
                Thread.sleep(500); // Simulate work
            } catch (InterruptedException e) {
                System.out.println("⚠️ " + taskName + " was interrupted");
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        System.out.println("✅ " + taskName + " completed");
    }
}

// Synchronization Example

class BankAccount {
    private double balance;
    private final Object lock = new Object();
    private final String accountNumber;
    
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public void deposit(double amount) {
        synchronized (lock) {
            double oldBalance = balance;
            balance += amount;
            System.out.println("💰 Account " + accountNumber + ": Deposited $" + amount + 
                              " (Balance: $" + oldBalance + " → $" + balance + ")");
        }
    }
    
    public boolean withdraw(double amount) {
        synchronized (lock) {
            if (balance >= amount) {
                double oldBalance = balance;
                balance -= amount;
                System.out.println("💸 Account " + accountNumber + ": Withdrew $" + amount + 
                                  " (Balance: $" + oldBalance + " → $" + balance + ")");
                return true;
            } else {
                System.out.println("❌ Account " + accountNumber + ": Insufficient funds for $" + amount + 
                                  " (Balance: $" + balance + ")");
                return false;
            }
        }
    }
    
    public double getBalance() {
        synchronized (lock) {
            return balance;
        }
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
}

class BankTransaction implements Runnable {
    private final BankAccount account;
    private final String operation;
    private final double amount;
    private final String customerName;
    
    public BankTransaction(BankAccount account, String operation, double amount, String customerName) {
        this.account = account;
        this.operation = operation;
        this.amount = amount;
        this.customerName = customerName;
    }
    
    @Override
    public void run() {
        System.out.println("🏦 " + customerName + " attempting to " + operation + " $" + amount);
        
        if ("deposit".equals(operation)) {
            account.deposit(amount);
        } else if ("withdraw".equals(operation)) {
            account.withdraw(amount);
        }
    }
}

// Producer-Consumer Example

class ProducerConsumerExample {
    private final Queue<String> buffer = new LinkedList<>();
    private final int capacity = 5;
    private final Object lock = new Object();
    
    public void produce(String item) throws InterruptedException {
        synchronized (lock) {
            while (buffer.size() == capacity) {
                System.out.println("🔴 Buffer full, producer waiting...");
                lock.wait();
            }
            
            buffer.add(item);
            System.out.println("📦 Produced: " + item + " (Buffer size: " + buffer.size() + ")");
            lock.notifyAll();
        }
    }
    
    public String consume() throws InterruptedException {
        synchronized (lock) {
            while (buffer.isEmpty()) {
                System.out.println("🔴 Buffer empty, consumer waiting...");
                lock.wait();
            }
            
            String item = buffer.poll();
            System.out.println("📤 Consumed: " + item + " (Buffer size: " + buffer.size() + ")");
            lock.notifyAll();
            return item;
        }
    }
}

class Producer implements Runnable {
    private final ProducerConsumerExample buffer;
    private final String producerName;
    
    public Producer(ProducerConsumerExample buffer, String producerName) {
        this.buffer = buffer;
        this.producerName = producerName;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                String item = producerName + "-Item-" + i;
                buffer.produce(item);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final ProducerConsumerExample buffer;
    private final String consumerName;
    
    public Consumer(ProducerConsumerExample buffer, String consumerName) {
        this.buffer = buffer;
        this.consumerName = consumerName;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 1; i <= 3; i++) {
                String item = buffer.consume();
                System.out.println("✅ " + consumerName + " processed: " + item);
                Thread.sleep(1500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Atomic Operations Example

class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicLong totalTime = new AtomicLong(0);
    
    public void increment() {
        long startTime = System.nanoTime();
        int newValue = count.incrementAndGet();
        long endTime = System.nanoTime();
        totalTime.addAndGet(endTime - startTime);
        
        System.out.println("⚛️ Thread " + Thread.currentThread().getName() + 
                          " incremented counter to: " + newValue);
    }
    
    public int getValue() {
        return count.get();
    }
    
    public double getAverageTime() {
        return totalTime.get() / 1000000.0; // Convert to milliseconds
    }
}

// CompletableFuture Example

class AsyncTaskProcessor {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    
    public CompletableFuture<String> processDataAsync(String data) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000); // Simulate processing time
                return "Processed: " + data.toUpperCase();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }, executor);
    }
    
    public CompletableFuture<Integer> calculateAsync(int number) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return number * number;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }, executor);
    }
    
    public void shutdown() {
        executor.shutdown();
    }
}

public class ConcurrencyDemo {
    
    /**
     * Demonstrates basic thread creation and execution
     */
    public static void demonstrateBasicThreads() {
        System.out.println("=== BASIC THREAD DEMO ===");
        
        // Creating threads using Runnable
        Thread thread1 = new Thread(new SimpleTask("DataProcessor", 3));
        Thread thread2 = new Thread(new SimpleTask("FileHandler", 2));
        Thread thread3 = new Thread(new SimpleTask("NetworkClient", 4));
        
        // Setting thread names
        thread1.setName("DataProcessor-Thread");
        thread2.setName("FileHandler-Thread");
        thread3.setName("NetworkClient-Thread");
        
        System.out.println("🎯 Starting multiple threads...");
        
        // Start threads
        thread1.start();
        thread2.start();
        thread3.start();
        
        try {
            // Wait for all threads to complete
            thread1.join();
            thread2.join();
            thread3.join();
            
            System.out.println("🏁 All threads completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("⚠️ Main thread interrupted");
        }
    }
    
    /**
     * Demonstrates synchronization with bank account example
     */
    public static void demonstrateSynchronization() {
        System.out.println("\n=== SYNCHRONIZATION DEMO ===");
        
        BankAccount sharedAccount = new BankAccount("ACC-001", 1000.0);
        
        // Create multiple transactions
        List<Thread> transactions = Arrays.asList(
            new Thread(new BankTransaction(sharedAccount, "deposit", 200.0, "Alice")),
            new Thread(new BankTransaction(sharedAccount, "withdraw", 150.0, "Bob")),
            new Thread(new BankTransaction(sharedAccount, "withdraw", 300.0, "Charlie")),
            new Thread(new BankTransaction(sharedAccount, "deposit", 100.0, "David")),
            new Thread(new BankTransaction(sharedAccount, "withdraw", 500.0, "Eve")),
            new Thread(new BankTransaction(sharedAccount, "deposit", 250.0, "Frank"))
        );
        
        System.out.println("🏦 Initial balance: $" + sharedAccount.getBalance());
        System.out.println("🚀 Starting concurrent transactions...");
        
        // Start all transactions
        transactions.forEach(Thread::start);
        
        // Wait for all transactions to complete
        transactions.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        System.out.println("💼 Final balance: $" + sharedAccount.getBalance());
    }
    
    /**
     * Demonstrates ExecutorService and thread pools
     */
    public static void demonstrateThreadPools() {
        System.out.println("\n=== THREAD POOL DEMO ===");
        
        // Create different types of thread pools
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);
        
        System.out.println("🏊 Fixed Thread Pool (3 threads):");
        
        // Submit tasks to fixed thread pool
        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            fixedPool.submit(() -> {
                System.out.println("🔢 Fixed Pool Task " + taskId + " on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("✅ Fixed Pool Task " + taskId + " completed");
            });
        }
        
        System.out.println("\n🌊 Cached Thread Pool:");
        
        // Submit tasks to cached thread pool
        for (int i = 1; i <= 4; i++) {
            final int taskId = i;
            cachedPool.submit(() -> {
                System.out.println("🔄 Cached Pool Task " + taskId + " on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("✅ Cached Pool Task " + taskId + " completed");
            });
        }
        
        System.out.println("\n⏰ Scheduled Thread Pool:");
        
        // Schedule tasks
        scheduledPool.schedule(() -> {
            System.out.println("⚡ Delayed task executed after 2 seconds");
        }, 2, TimeUnit.SECONDS);
        
        scheduledPool.scheduleAtFixedRate(() -> {
            System.out.println("🔄 Periodic task executed every 3 seconds");
        }, 1, 3, TimeUnit.SECONDS);
        
        // Shutdown pools
        fixedPool.shutdown();
        cachedPool.shutdown();
        
        // Wait for tasks to complete
        try {
            if (!fixedPool.awaitTermination(10, TimeUnit.SECONDS)) {
                fixedPool.shutdownNow();
            }
            if (!cachedPool.awaitTermination(5, TimeUnit.SECONDS)) {
                cachedPool.shutdownNow();
            }
            
            // Let scheduled pool run for a bit, then shutdown
            Thread.sleep(8000);
            scheduledPool.shutdown();
            
        } catch (InterruptedException e) {
            fixedPool.shutdownNow();
            cachedPool.shutdownNow();
            scheduledPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Demonstrates Producer-Consumer pattern
     */
    public static void demonstrateProducerConsumer() {
        System.out.println("\n=== PRODUCER-CONSUMER DEMO ===");
        
        ProducerConsumerExample buffer = new ProducerConsumerExample();
        
        // Create producers and consumers
        Thread producer1 = new Thread(new Producer(buffer, "Producer-A"));
        Thread producer2 = new Thread(new Producer(buffer, "Producer-B"));
        Thread consumer1 = new Thread(new Consumer(buffer, "Consumer-X"));
        Thread consumer2 = new Thread(new Consumer(buffer, "Consumer-Y"));
        Thread consumer3 = new Thread(new Consumer(buffer, "Consumer-Z"));
        
        System.out.println("🏭 Starting producer-consumer simulation...");
        
        // Start all threads
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        
        try {
            // Wait for all to complete
            producer1.join();
            producer2.join();
            consumer1.join();
            consumer2.join();
            consumer3.join();
            
            System.out.println("🏁 Producer-Consumer simulation completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Demonstrates atomic operations
     */
    public static void demonstrateAtomicOperations() {
        System.out.println("\n=== ATOMIC OPERATIONS DEMO ===");
        
        AtomicCounter counter = new AtomicCounter();
        int numThreads = 5;
        int incrementsPerThread = 3;
        
        List<Thread> threads = new ArrayList<>();
        
        System.out.println("⚛️ Starting " + numThreads + " threads, each incrementing " + 
                          incrementsPerThread + " times...");
        
        // Create threads that increment the counter
        for (int i = 1; i <= numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }, "AtomicThread-" + i);
            
            threads.add(thread);
            thread.start();
        }
        
        // Wait for all threads to complete
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        System.out.println("🎯 Final counter value: " + counter.getValue());
        System.out.println("⏱️ Average operation time: " + counter.getAverageTime() + " ms");
        System.out.println("Expected value: " + (numThreads * incrementsPerThread));
    }
    
    /**
     * Demonstrates CompletableFuture and asynchronous programming
     */
    public static void demonstrateCompletableFuture() {
        System.out.println("\n=== COMPLETABLE FUTURE DEMO ===");
        
        AsyncTaskProcessor processor = new AsyncTaskProcessor();
        
        System.out.println("🚀 Starting asynchronous operations...");
        
        // Create multiple async tasks
        CompletableFuture<String> task1 = processor.processDataAsync("hello world");
        CompletableFuture<String> task2 = processor.processDataAsync("java programming");
        CompletableFuture<Integer> calc1 = processor.calculateAsync(5);
        CompletableFuture<Integer> calc2 = processor.calculateAsync(10);
        
        // Combine results
        CompletableFuture<String> combinedData = task1.thenCombine(task2, (result1, result2) -> {
            return "Combined: " + result1 + " + " + result2;
        });
        
        CompletableFuture<Integer> combinedCalc = calc1.thenCombine(calc2, Integer::sum);
        
        // Chain operations
        CompletableFuture<String> chainedResult = combinedData
            .thenApply(result -> result.toLowerCase())
            .thenApply(result -> "Final: " + result);
        
        // Wait for all results
        try {
            System.out.println("📊 Waiting for async results...");
            
            System.out.println("✅ Task 1 result: " + task1.get());
            System.out.println("✅ Task 2 result: " + task2.get());
            System.out.println("✅ Calculation 1 result: " + calc1.get());
            System.out.println("✅ Calculation 2 result: " + calc2.get());
            System.out.println("🔗 Combined data: " + combinedData.get());
            System.out.println("🧮 Combined calculation: " + combinedCalc.get());
            System.out.println("⛓️ Chained result: " + chainedResult.get());
            
            // Demonstrate async completion handlers
            CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, calc1, calc2);
            allTasks.thenRun(() -> {
                System.out.println("🎉 All async tasks completed!");
            }).get();
            
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("❌ Error in async operations: " + e.getMessage());
        } finally {
            processor.shutdown();
        }
    }
    
    /**
     * Demonstrates concurrent collections
     */
    public static void demonstrateConcurrentCollections() {
        System.out.println("\n=== CONCURRENT COLLECTIONS DEMO ===");
        
        // ConcurrentHashMap
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        
        // BlockingQueue
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(3);
        
        System.out.println("🗺️ ConcurrentHashMap operations:");
        
        List<Thread> mapThreads = new ArrayList<>();
        
        // Threads adding to concurrent map
        for (int i = 1; i <= 3; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                for (int j = 1; j <= 3; j++) {
                    String key = "Thread" + threadId + "-Key" + j;
                    int value = threadId * 10 + j;
                    concurrentMap.put(key, value);
                    System.out.println("📝 " + Thread.currentThread().getName() + 
                                     " added: " + key + " = " + value);
                    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }, "MapThread-" + i);
            
            mapThreads.add(thread);
            thread.start();
        }
        
        // Wait for map operations to complete
        mapThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        System.out.println("🗂️ Final map contents: " + concurrentMap);
        
        System.out.println("\n📦 BlockingQueue operations:");
        
        // Producer for queue
        Thread queueProducer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String item = "QueueItem-" + i;
                    queue.put(item);
                    System.out.println("📤 Queued: " + item + " (Queue size: " + queue.size() + ")");
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "QueueProducer");
        
        // Consumer for queue
        Thread queueConsumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String item = queue.take();
                    System.out.println("📥 Dequeued: " + item + " (Queue size: " + queue.size() + ")");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "QueueConsumer");
        
        queueProducer.start();
        queueConsumer.start();
        
        try {
            queueProducer.join();
            queueConsumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Analyzes concurrency best practices and common pitfalls
     */
    public static void analyzeConcurrency() {
        System.out.println("\n=== CONCURRENCY ANALYSIS ===");
        
        System.out.println("Key Concurrency Concepts:");
        System.out.println("• Thread Safety: Ensuring correct behavior in multithreaded environment");
        System.out.println("• Synchronization: Coordinating access to shared resources");
        System.out.println("• Atomicity: Operations that complete entirely or not at all");
        System.out.println("• Visibility: Ensuring changes are visible across threads");
        System.out.println("• Ordering: Controlling the sequence of operations");
        
        System.out.println("\nSynchronization Mechanisms:");
        System.out.println("• synchronized keyword: Built-in locking mechanism");
        System.out.println("• ReentrantLock: More flexible locking with try-lock capability");
        System.out.println("• ReadWriteLock: Separate locks for read and write operations");
        System.out.println("• Atomic classes: Lock-free thread-safe operations");
        System.out.println("• volatile: Ensures visibility without full synchronization");
        
        System.out.println("\nConcurrency Utilities:");
        System.out.println("• ExecutorService: Thread pool management");
        System.out.println("• CompletableFuture: Asynchronous programming support");
        System.out.println("• CountDownLatch: Thread coordination mechanism");
        System.out.println("• CyclicBarrier: Synchronization point for multiple threads");
        System.out.println("• Semaphore: Controls access to resources");
        
        System.out.println("\nBest Practices:");
        System.out.println("• Prefer immutable objects when possible");
        System.out.println("• Use concurrent collections instead of synchronized wrappers");
        System.out.println("• Minimize the scope of synchronization");
        System.out.println("• Avoid nested locks to prevent deadlocks");
        System.out.println("• Use thread pools instead of creating threads manually");
        System.out.println("• Handle InterruptedException properly");
        
        System.out.println("\nCommon Pitfalls:");
        System.out.println("• Race conditions: Multiple threads accessing shared data");
        System.out.println("• Deadlocks: Circular dependency on locks");
        System.out.println("• Memory consistency errors: Incorrect visibility of changes");
        System.out.println("• Resource leaks: Not properly shutting down threads/executors");
        System.out.println("• Performance issues: Excessive synchronization");
    }
    
    /**
     * Main method demonstrating concurrency concepts
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Concurrency and Multithreading Demonstration");
        System.out.println("=================================================");
        
        demonstrateBasicThreads();
        demonstrateSynchronization();
        demonstrateThreadPools();
        demonstrateProducerConsumer();
        demonstrateAtomicOperations();
        demonstrateCompletableFuture();
        demonstrateConcurrentCollections();
        analyzeConcurrency();
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("Java provides comprehensive concurrency support through:");
        System.out.println("• Thread creation and management");
        System.out.println("• Synchronization primitives");
        System.out.println("• High-level concurrency utilities");
        System.out.println("• Thread-safe collections");
        System.out.println("• Asynchronous programming capabilities");
        System.out.println("Choose the right tool based on your specific requirements!");
    }
}
