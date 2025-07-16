package advanced;

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
 * WHY CONCURRENCY MATTERS:
 * Modern applications need concurrency to:
 * - Utilize multiple CPU cores effectively (performance)
 * - Handle multiple users simultaneously (scalability)
 * - Perform I/O operations without blocking the entire application (responsiveness)
 * - Implement real-time systems and background processing (functionality)
 * 
 * CONCURRENCY CHALLENGES:
 * - Race conditions: Multiple threads accessing shared data simultaneously
 * - Deadlocks: Threads waiting for each other indefinitely
 * - Data corruption: Inconsistent state due to unsynchronized access
 * - Performance overhead: Synchronization and context switching costs
 * - Complexity: Harder to reason about, test, and debug
 * 
 * JAVA CONCURRENCY TOOLKIT:
 * 1. Basic threading: Thread class, Runnable interface, thread lifecycle
 * 2. Synchronization: synchronized keyword, locks, atomic operations
 * 3. Thread pools: ExecutorService for managing thread resources
 * 4. Concurrent collections: Thread-safe data structures
 * 5. High-level constructs: CompletableFuture, parallel streams
 * 
 * This comprehensive demo covers:
 * - Thread basics and lifecycle (creating, starting, joining threads)
 * - Synchronization mechanisms (synchronized, locks, atomic variables)
 * - Thread pools and ExecutorService (managing thread resources efficiently)
 * - Concurrent collections (thread-safe data structures)
 * - Atomic operations (lock-free programming for simple operations)
 * - Producer-Consumer pattern (classic concurrency problem)
 * - Thread-safe singleton patterns (safe initialization in concurrent environments)
 * - CompletableFuture and asynchronous programming (non-blocking operations)
 * 
 * LEARNING OBJECTIVES:
 * 1. Understand fundamental concepts of concurrent programming
 * 2. Learn to identify and prevent common concurrency problems
 * 3. Master Java's concurrency utilities and best practices
 * 4. Apply appropriate synchronization mechanisms for different scenarios
 * 5. Design thread-safe applications with good performance characteristics
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// Thread Basics Example
// This demonstrates the fundamental building blocks of Java threading

/**
 * SimpleTask - Demonstrates basic thread execution and lifecycle
 * 
 * EDUCATIONAL PURPOSE:
 * This class serves as a foundational example of how threads work in Java.
 * Understanding this pattern is crucial before moving to advanced concurrency concepts.
 * 
 * RUNNABLE INTERFACE BENEFITS:
 * - Preferred way to create tasks for threads (vs extending Thread class)
 * - Allows implementing other interfaces (single inheritance limitation)
 * - Separates the task (what to do) from the thread (how to execute)
 * - Enables better resource management with thread pools
 * - Promotes composition over inheritance design principle
 * 
 * THREAD LIFECYCLE EXPLAINED:
 * 1. NEW: Thread object created but not started (thread.start() not called)
 * 2. RUNNABLE: Thread is eligible to run (may be running or waiting for CPU)
 * 3. RUNNING: Thread is actually executing on a processor
 * 4. BLOCKED: Thread is waiting to acquire a lock/monitor
 * 5. WAITING: Thread is waiting indefinitely for another thread
 * 6. TIMED_WAITING: Thread is waiting for a specified period
 * 7. TERMINATED: Thread has completed execution or been terminated
 * 
 * REAL-WORLD APPLICATIONS:
 * - Background data processing tasks
 * - File I/O operations that shouldn't block the main thread
 * - Network requests and API calls
 * - Image/video processing pipelines
 * - Log file processing and cleanup tasks
 * 
 * DESIGN PATTERNS DEMONSTRATED:
 * - Command Pattern: Encapsulates a request as an object
 * - Template Method: Defines algorithm structure in run() method
 */
class SimpleTask implements Runnable {
    private final String taskName;    // Immutable task identifier
    private final int iterations;     // Number of work units to perform
    
    /**
     * Constructor for creating a named task with specified work amount
     * 
     * @param taskName human-readable name for debugging and logging
     * @param iterations number of work cycles to perform
     */
    public SimpleTask(String taskName, int iterations) {
        this.taskName = taskName;
        this.iterations = iterations;
    }
    
    /**
     * The actual work performed by the thread
     * 
     * CRITICAL UNDERSTANDING - THE run() METHOD:
     * - This is where the thread's work happens
     * - Called automatically when thread.start() is invoked
     * - NEVER call run() directly - it will execute on the current thread, not a new one
     * - Once run() completes, the thread moves to TERMINATED state
     * 
     * THREAD IDENTIFICATION IMPORTANCE:
     * Thread.currentThread().getName() helps identify which thread is executing
     * This is crucial for:
     * - Debugging concurrent applications
     * - Monitoring thread performance
     * - Identifying bottlenecks and resource contention
     * - Understanding thread pool behavior
     * 
     * INTERRUPTION HANDLING EXPLAINED:
     * - InterruptedException is checked exception thrown by blocking operations
     * - Signals that another thread wants this thread to stop
     * - BEST PRACTICE: Always restore interrupted status with Thread.currentThread().interrupt()
     * - Common blocking operations: Thread.sleep(), Object.wait(), queue.take()
     * 
     * WHY PROPER INTERRUPTION MATTERS:
     * - Enables graceful shutdown of applications
     * - Prevents threads from hanging indefinitely
     * - Essential for thread pool management
     * - Required for responsive user interfaces
     */
    @Override
    public void run() {
        // Log thread start with thread identification
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

// Synchronization Example - CRITICAL CONCEPT FOR THREAD SAFETY

/**
 * BankAccount - Demonstrates the ABSOLUTE NECESSITY of synchronization
 * 
 * EDUCATIONAL SCENARIO:
 * Banking systems are perfect examples of why synchronization matters.
 * Without proper synchronization, money could be created or destroyed!
 * 
 * RACE CONDITION EXPLANATION:
 * Imagine two threads trying to withdraw $500 from an account with $600:
 * 1. Thread A reads balance: $600 (thinks withdrawal is OK)
 * 2. Thread B reads balance: $600 (also thinks withdrawal is OK)  
 * 3. Thread A subtracts $500: balance = $100
 * 4. Thread B subtracts $500: balance = -$400 (DISASTER!)
 * 
 * This is called a "race condition" because the threads are racing to access shared data.
 * 
 * SYNCHRONIZATION SOLUTIONS IN JAVA:
 * 1. synchronized keyword: Built-in mutual exclusion
 * 2. ReentrantLock: More flexible locking with timeout capability
 * 3. Atomic variables: Lock-free operations for simple data types
 * 4. volatile keyword: Ensures visibility across threads (no caching)
 * 
 * WHY USE PRIVATE LOCK OBJECT:
 * - Prevents external code from interfering with synchronization
 * - More secure than synchronizing on 'this'
 * - Allows fine-grained control over what gets synchronized
 * - Best practice recommended by Java concurrency experts
 * 
 * PERFORMANCE CONSIDERATIONS:
 * - Synchronization has overhead (context switching, memory barriers)
 * - Only synchronize what's necessary
 * - Consider using concurrent collections for better performance
 * - Avoid holding locks for long operations
 * 
 * REAL-WORLD APPLICATIONS:
 * - Banking and financial systems
 * - Inventory management systems
 * - Resource allocation in operating systems
 * - Shared caches and connection pools
 */
class BankAccount {
    private double balance;
    private final Object lock = new Object();
    private final String accountNumber;
    
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    /**
     * Deposit money into the account - THREAD-SAFE OPERATION
     * 
     * SYNCHRONIZATION MECHANICS:
     * - synchronized(lock) creates a mutual exclusion zone
     * - Only ONE thread can execute this block at a time
     * - Other threads must wait until the lock is released
     * - JVM handles the lock acquisition and release automatically
     * 
     * ATOMIC OPERATION CONCEPT:
     * This method ensures the deposit happens completely or not at all:
     * - Read current balance
     * - Calculate new balance  
     * - Update balance
     * - Log the transaction
     * All these steps happen atomically (indivisibly)
     * 
     * MEMORY CONSISTENCY:
     * Synchronization provides two guarantees:
     * 1. Mutual Exclusion: Only one thread can execute at a time
     * 2. Memory Visibility: Changes are visible to other threads
     * 
     * @param amount the amount to deposit (must be positive)
     */
    public void deposit(double amount) {
        synchronized (lock) {
            double oldBalance = balance;
            balance += amount;
            System.out.println("💰 Account " + accountNumber + ": Deposited $" + amount + 
                              " (Balance: $" + oldBalance + " → $" + balance + ")");
        }
    }
    
    /**
     * Withdraw money from the account - DEMONSTRATES CONDITIONAL SYNCHRONIZATION
     * 
     * BUSINESS LOGIC WITH THREAD SAFETY:
     * This method combines business rules (sufficient funds check) with thread safety.
     * The entire operation (check + withdraw) must be atomic to prevent:
     * - Overdraft conditions
     * - Inconsistent account states
     * - Lost or duplicate transactions
     * 
     * CRITICAL SECTION ANALYSIS:
     * Everything inside synchronized(lock) is a "critical section":
     * - Code that accesses shared data
     * - Must be executed by only one thread at a time
     * - Should be kept as small as possible for performance
     * 
     * TRANSACTION SEMANTICS:
     * Banking systems require ACID properties:
     * - Atomicity: Operation completes fully or not at all
     * - Consistency: Account rules are always maintained
     * - Isolation: Concurrent transactions don't interfere
     * - Durability: Changes persist (handled by database layer)
     * 
     * @param amount the amount to withdraw
     * @return true if withdrawal successful, false if insufficient funds
     */
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

// Producer-Consumer Example - FUNDAMENTAL CONCURRENCY PATTERN

/**
 * ProducerConsumerExample - Demonstrates classic concurrency coordination
 * 
 * THE PRODUCER-CONSUMER PROBLEM:
 * This is one of the most important patterns in concurrent programming.
 * It models real-world scenarios where:
 * - Producers create data/work items
 * - Consumers process data/work items  
 * - They operate at different speeds
 * - A buffer mediates between them
 * 
 * REAL-WORLD EXAMPLES:
 * - Web servers: Requests (produced) → Worker threads (consumers)
 * - Operating systems: Keyboard input (producer) → Applications (consumers)
 * - Media streaming: Encoder (producer) → Player (consumer)
 * - Message queues: Publishers (producers) → Subscribers (consumers)
 * - Print spoolers: Applications (producers) → Printer driver (consumer)
 * 
 * KEY CONCURRENCY CONCEPTS DEMONSTRATED:
 * 1. BOUNDED BUFFER: Limited capacity prevents infinite memory usage
 * 2. BLOCKING OPERATIONS: Threads wait when buffer is full/empty
 * 3. CONDITION VARIABLES: wait() and notify() for thread coordination
 * 4. SPURIOUS WAKEUPS: Why we use while loops instead of if statements
 * 
 * WHY THIS PATTERN IS ESSENTIAL:
 * - Decouples producers from consumers (different speeds OK)
 * - Provides flow control (backpressure when buffer is full)
 * - Enables scalability (multiple producers/consumers)
 * - Prevents resource exhaustion (bounded buffer)
 * 
 * MONITOR PATTERN EXPLANATION:
 * This class implements the "Monitor" pattern:
 * - Synchronization object (lock)
 * - Condition variables (wait/notify)
 * - Encapsulated shared state (buffer)
 * - Atomic operations on shared state
 * 
 * ALTERNATIVE IMPLEMENTATIONS:
 * - Java 5+: BlockingQueue interface (LinkedBlockingQueue, ArrayBlockingQueue)
 * - Java 8+: CompletableFuture for async processing
 * - Reactive Streams: Backpressure-aware processing
 */
class ProducerConsumerExample {
    private final Queue<String> buffer = new LinkedList<>();
    private final int capacity = 5;
    private final Object lock = new Object();
    
    /**
     * Producer method - adds items to the bounded buffer
     * 
     * BLOCKING PRODUCER SEMANTICS:
     * When buffer is full, producer must wait. This provides:
     * - BACKPRESSURE: Slow consumers naturally slow down fast producers
     * - FLOW CONTROL: Prevents memory exhaustion from unbounded queuing
     * - COORDINATION: Automatic load balancing between producer/consumer speeds
     * 
     * WAIT-NOTIFY MECHANISM EXPLAINED:
     * 1. wait() releases the lock and suspends the thread
     * 2. Other threads can now acquire the lock and make progress
     * 3. notify()/notifyAll() wakes up waiting threads
     * 4. Awakened thread re-acquires lock before continuing
     * 
     * WHY WHILE LOOP, NOT IF?
     * Always use while(condition) with wait():
     * - Protects against spurious wakeups (thread wakes up randomly)
     * - Handles multiple threads competing for the same condition
     * - Ensures condition is still true after waking up
     * - Prevents race conditions between check and action
     * 
     * SPURIOUS WAKEUP EXAMPLE:
     * Thread A: checks buffer full → enters wait()
     * Thread B: consumes item → calls notifyAll()  
     * Thread C: produces item (fills buffer again) before A wakes up
     * Thread A: wakes up → if it used 'if', would proceed incorrectly!
     * 
     * @param item the item to add to the buffer
     * @throws InterruptedException if thread is interrupted while waiting
     */
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
    
    /**
     * Consumer method - removes items from the bounded buffer
     * 
     * BLOCKING CONSUMER SEMANTICS:
     * When buffer is empty, consumer must wait. This provides:
     * - DEMAND-DRIVEN PROCESSING: Consumers only work when there's data
     * - RESOURCE EFFICIENCY: No busy-waiting or polling overhead
     * - AUTOMATIC COORDINATION: Producers know when consumers need data
     * 
     * NOTIFY VS NOTIFYALL DECISION:
     * We use notifyAll() instead of notify() because:
     * - Multiple producers AND consumers might be waiting
     * - notify() only wakes ONE thread (might be wrong type)
     * - notifyAll() wakes ALL waiting threads (they compete for lock)
     * - Better correctness guarantee (slight performance cost)
     * 
     * EXAMPLE SCENARIO:
     * Buffer has 1 slot, currently empty:
     * - Producer A waiting (buffer was full)
     * - Consumer B waiting (buffer is empty) 
     * - Consumer C waiting (buffer is empty)
     * - Consumer D consumes item → should wake Producer A, not Consumer B!
     * 
     * PERFORMANCE CONSIDERATIONS:
     * - notifyAll() can cause "thundering herd" problem
     * - Modern Java: use Condition objects for specific waiting conditions
     * - BlockingQueue implementations are highly optimized
     * - For high-performance: consider lock-free algorithms
     * 
     * @return the item removed from the buffer
     * @throws InterruptedException if thread is interrupted while waiting
     */
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

// Atomic Operations Example - LOCK-FREE PROGRAMMING

/**
 * AtomicCounter - Demonstrates lock-free thread-safe programming
 * 
 * ATOMIC OPERATIONS EXPLAINED:
 * Atomic operations are indivisible - they complete entirely or not at all.
 * Unlike synchronized blocks, atomic operations don't use locks, making them:
 * - FASTER: No thread blocking or context switching
 * - DEADLOCK-FREE: No locks means no possibility of deadlock
 * - SCALABLE: Better performance under high contention
 * - SIMPLE: Less complex than manual synchronization
 * 
 * HOW ATOMIC VARIABLES WORK:
 * Built on hardware-level atomic instructions:
 * - Compare-And-Swap (CAS): Atomically compare and update value
 * - Memory barriers: Ensure proper ordering of operations
 * - Volatile semantics: Guarantee visibility across threads
 * - Hardware support: Modern CPUs provide atomic instruction sets
 * 
 * COMPARE-AND-SWAP ALGORITHM:
 * ```
 * do {
 *     current = getValue();
 *     newValue = current + 1;
 * } while (!compareAndSet(current, newValue));
 * ```
 * 
 * CAS succeeds only if value hasn't changed since we read it.
 * If it has changed, we retry with the new value.
 * 
 * WHEN TO USE ATOMIC VS SYNCHRONIZED:
 * 
 * USE ATOMIC WHEN:
 * - Simple operations (increment, add, compare)
 * - High contention scenarios
 * - Performance is critical
 * - Single variable updates
 * 
 * USE SYNCHRONIZED WHEN:
 * - Complex operations involving multiple variables
 * - Need to guarantee operation ordering
 * - Working with non-atomic data types
 * - Multiple steps must be atomic together
 * 
 * ATOMIC CLASSES IN JAVA:
 * - AtomicInteger, AtomicLong: Numeric operations
 * - AtomicBoolean: Boolean flag operations  
 * - AtomicReference: Object reference updates
 * - AtomicArray: Array element updates
 * - LongAdder, DoubleAdder: High-contention counters
 * 
 * PERFORMANCE CHARACTERISTICS:
 * - Low contention: Atomic ≈ Synchronized
 * - High contention: Atomic >> Synchronized (much faster)
 * - Memory usage: Atomic < Synchronized (no lock objects)
 */
class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicLong totalTime = new AtomicLong(0);
    
    /**
     * Atomically increment the counter and track timing
     * 
     * ATOMIC OPERATION BREAKDOWN:
     * incrementAndGet() is equivalent to ++counter but thread-safe:
     * 1. Read current value
     * 2. Calculate new value (current + 1)
     * 3. Use CAS to update if value hasn't changed
     * 4. If CAS fails, retry from step 1
     * 5. Return the new value
     * 
     * WHY THIS IS LOCK-FREE:
     * - No thread ever blocks waiting for a lock
     * - Failed CAS operations just retry immediately
     * - Hardware guarantees atomicity of the CAS instruction
     * - Multiple threads can attempt updates simultaneously
     * 
     * PERFORMANCE MONITORING:
     * We measure operation time to demonstrate:
     * - Atomic operations are very fast (nanoseconds)
     * - No lock contention delays
     * - Consistent performance under load
     * 
     * MEMORY ORDERING:
     * addAndGet() has volatile semantics:
     * - Happens-before relationship with subsequent operations
     * - Changes are immediately visible to other threads
     * - No CPU caching issues
     */
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

// CompletableFuture Example - ASYNCHRONOUS PROGRAMMING REVOLUTION

/**
 * AsyncTaskProcessor - Demonstrates modern asynchronous programming
 * 
 * ASYNCHRONOUS PROGRAMMING PARADIGM:
 * Traditional threading: "Do this work on a separate thread"
 * Async programming: "Do this work when convenient, notify me when done"
 * 
 * WHY ASYNCHRONOUS PROGRAMMING MATTERS:
 * 1. SCALABILITY: Handle thousands of concurrent operations
 * 2. RESPONSIVENESS: UI remains responsive during long operations
 * 3. RESOURCE EFFICIENCY: Better CPU and memory utilization
 * 4. COMPOSABILITY: Chain and combine operations easily
 * 5. ERROR HANDLING: Centralized exception handling
 * 
 * COMPLETABLEFUTURE VS TRADITIONAL THREADING:
 * 
 * Traditional Approach:
 * ```java
 * Thread t = new Thread(() -> {
 *     String result = processData(data);
 *     // How do I get result back to caller?
 *     // How do I handle exceptions?
 *     // How do I chain multiple operations?
 * });
 * t.start();
 * // How do I know when it's done?
 * ```
 * 
 * CompletableFuture Approach:
 * ```java
 * CompletableFuture<String> future = CompletableFuture
 *     .supplyAsync(() -> processData(data))
 *     .thenApply(result -> result.toUpperCase())
 *     .exceptionally(ex -> "Error: " + ex.getMessage());
 * ```
 * 
 * KEY COMPLETABLEFUTURE METHODS:
 * - supplyAsync(): Start async computation that returns a value
 * - runAsync(): Start async computation that returns void
 * - thenApply(): Transform result when computation completes
 * - thenCompose(): Chain dependent computations
 * - thenCombine(): Combine results from independent computations
 * - exceptionally(): Handle exceptions
 * - allOf(): Wait for multiple futures
 * - anyOf(): Wait for first future to complete
 * 
 * THREAD POOL INTEGRATION:
 * CompletableFuture uses ForkJoinPool.commonPool() by default, but
 * we provide our own ExecutorService for:
 * - Better resource control
 * - Custom thread naming
 * - Separate pools for different workload types
 * - Graceful shutdown handling
 * 
 * REAL-WORLD APPLICATIONS:
 * - Web service calls (REST APIs, database queries)
 * - File I/O operations (reading large files)
 * - Image/video processing pipelines
 * - Parallel algorithm execution
 * - Microservice orchestration
 */
class AsyncTaskProcessor {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    
    /**
     * Process data asynchronously - demonstrates async I/O pattern
     * 
     * ASYNC I/O PATTERN EXPLAINED:
     * This simulates common async operations like:
     * - HTTP API calls to external services
     * - Database queries with network latency
     * - File system operations
     * - Image/document processing
     * 
     * SUPPLIER FUNCTIONAL INTERFACE:
     * supplyAsync() takes a Supplier<T> - a function that:
     * - Takes no parameters: () -> T
     * - Returns a value of type T
     * - Can throw runtime exceptions
     * - Executes on the provided ExecutorService
     * 
     * EXCEPTION HANDLING IN ASYNC CODE:
     * Checked exceptions (like InterruptedException) must be handled inside
     * the supplier because functional interfaces don't declare checked exceptions.
     * We convert to RuntimeException to propagate to CompletableFuture's
     * exception handling mechanism.
     * 
     * EXECUTION MODEL:
     * 1. Method returns immediately with CompletableFuture<String>
     * 2. Actual work happens asynchronously on thread pool
     * 3. Caller can continue with other work
     * 4. When ready, caller can get() result or chain more operations
     * 
     * @param data input data to process
     * @return CompletableFuture that will contain processed result
     */
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
     * Demonstrates basic thread creation and execution patterns
     * 
     * LEARNING OBJECTIVES:
     * 1. How to create threads using Runnable interface
     * 2. Thread naming for debugging and monitoring
     * 3. Thread lifecycle management (start, join)
     * 4. Coordinating multiple threads
     * 5. Proper exception handling in threaded code
     * 
     * THREAD CREATION BEST PRACTICES:
     * - Use Runnable instead of extending Thread class
     * - Give threads meaningful names for debugging
     * - Always handle InterruptedException properly
     * - Use join() to wait for thread completion
     * - Don't ignore exceptions in thread code
     * 
     * EXECUTION PATTERN ANALYSIS:
     * You'll notice that threads execute in unpredictable order.
     * This demonstrates the fundamental nature of concurrent execution:
     * - Thread scheduling is controlled by the OS
     * - Execution order is non-deterministic
     * - Applications must be designed to handle any ordering
     * 
     * DEBUGGING CONCURRENT CODE:
     * Watch the output carefully:
     * - Thread names help identify which thread is executing
     * - Timestamps would show actual parallelism
     * - Different runs may produce different orderings
     * 
     * REAL-WORLD EQUIVALENT:
     * This pattern appears in:
     * - Background task processing
     * - Parallel file processing
     * - Concurrent web request handling
     * - Multi-threaded calculations
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
     * Demonstrates thread pools and ExecutorService benefits
     * 
     * WHY THREAD POOLS ARE ESSENTIAL:
     * Creating threads is expensive:
     * - OS system call overhead
     * - Memory allocation for thread stack
     * - Context switching costs
     * - Resource management complexity
     * 
     * Thread pools solve these problems by:
     * - REUSING threads instead of creating new ones
     * - LIMITING total thread count (prevents resource exhaustion)
     * - QUEUING work when all threads are busy
     * - AUTOMATIC lifecycle management
     * 
     * THREAD POOL TYPES EXPLAINED:
     * 
     * 1. FIXED THREAD POOL:
     *    - Fixed number of threads (e.g., 3)
     *    - Good for CPU-bound tasks
     *    - Predictable resource usage
     *    - Work queue grows when all threads busy
     * 
     * 2. CACHED THREAD POOL:
     *    - Creates threads as needed
     *    - Reuses idle threads
     *    - Good for I/O-bound tasks
     *    - Can grow unbounded (dangerous!)
     * 
     * 3. SCHEDULED THREAD POOL:
     *    - Supports delayed and periodic execution
     *    - Like cron jobs but in-process
     *    - Good for cleanup, monitoring, heartbeats
     *    - Fixed number of threads
     * 
     * CHOOSING THE RIGHT POOL:
     * - CPU-intensive: Fixed pool (size = CPU cores)
     * - I/O-intensive: Cached pool or larger fixed pool
     * - Mixed workload: Custom ThreadPoolExecutor
     * - Scheduled tasks: ScheduledThreadPoolExecutor
     * 
     * GRACEFUL SHUTDOWN PATTERN:
     * 1. shutdown() - stops accepting new tasks
     * 2. awaitTermination() - waits for running tasks
     * 3. shutdownNow() - forcibly stops if timeout exceeded
     * 
     * This prevents:
     * - Resource leaks
     * - JVM hanging on exit
     * - Lost work during shutdown
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
     * Demonstrates the classic Producer-Consumer coordination pattern
     * 
     * EDUCATIONAL VALUE:
     * This is THE fundamental pattern for understanding concurrency.
     * Master this, and you understand 80% of concurrent programming.
     * 
     * PATTERN COMPONENTS:
     * 1. SHARED BUFFER: Bounded queue between producer and consumer
     * 2. MULTIPLE PRODUCERS: Create data at their own pace
     * 3. MULTIPLE CONSUMERS: Process data at their own pace
     * 4. COORDINATION: wait/notify ensures proper synchronization
     * 
     * REAL-WORLD APPLICATIONS:
     * 
     * Operating Systems:
     * - Keyboard input → Application processing
     * - Network packets → Protocol stack
     * - Disk I/O → File system cache
     * 
     * Web Applications:
     * - HTTP requests → Worker threads
     * - Database queries → Connection pool
     * - Log messages → Log processing
     * 
     * Media Processing:
     * - Video frames → Encoder/Decoder
     * - Audio samples → DSP pipeline
     * - Image tiles → Rendering engine
     * 
     * OBSERVING THE COORDINATION:
     * Watch the output carefully for:
     * - Producers waiting when buffer is full
     * - Consumers waiting when buffer is empty
     * - Automatic balancing of production/consumption rates
     * - Multiple producers/consumers working together
     * 
     * SCALABILITY LESSONS:
     * - Adding more producers: Increases data generation rate
     * - Adding more consumers: Increases processing capacity
     * - Buffer size: Affects throughput vs memory usage
     * - Thread count: Balance between parallelism and overhead
     * 
     * MODERN ALTERNATIVES:
     * While this shows the fundamentals, modern Java offers:
     * - BlockingQueue: Built-in producer-consumer support
     * - Flow API: Reactive streams with backpressure
     * - CompletableFuture: Async pipeline processing
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
     * Demonstrates lock-free programming with atomic operations
     * 
     * LOCK-FREE PROGRAMMING REVOLUTION:
     * This represents a paradigm shift from traditional locking:
     * - OLD WAY: Use locks to prevent interference
     * - NEW WAY: Use atomic operations that can't be interfered with
     * 
     * PERFORMANCE CHARACTERISTICS:
     * Under high contention, atomic operations dramatically outperform locks:
     * - Locks: Threads block, context switch, cache misses
     * - Atomics: Threads retry immediately, no blocking
     * - Result: 10x-100x better performance in some scenarios
     * 
     * EDUCATIONAL EXPERIMENT:
     * Try modifying this code to use synchronized instead of AtomicInteger:
     * ```java
     * private int count = 0;
     * public synchronized void increment() { count++; }
     * ```
     * 
     * You'll notice:
     * - Synchronized version is slower
     * - More context switching overhead
     * - Possible thread contention delays
     * 
     * ATOMIC OPERATION GUARANTEES:
     * 1. ATOMICITY: Operation completes entirely or not at all
     * 2. VISIBILITY: Changes immediately visible to other threads
     * 3. ORDERING: Operations have happens-before relationships
     * 4. CONSISTENCY: No partial updates visible
     * 
     * WHEN ATOMICS AREN'T ENOUGH:
     * Atomic operations work for single variables, but not for:
     * - Multiple related variables
     * - Complex business logic
     * - Operations requiring specific ordering
     * - Non-numeric data types (use AtomicReference)
     * 
     * COMPARE-AND-SWAP DEEP DIVE:
     * Modern CPUs provide CAS instruction:
     * 1. Read memory location
     * 2. Compare with expected value
     * 3. If match, write new value
     * 4. If no match, operation fails (retry needed)
     * 5. Return success/failure flag
     * 
     * This enables lock-free algorithms that scale much better.
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
     * Demonstrates modern asynchronous programming with CompletableFuture
     * 
     * ASYNCHRONOUS PROGRAMMING MINDSET:
     * This represents a fundamental shift in how we think about concurrency:
     * - BLOCKING MODEL: "Wait for this operation to complete"
     * - ASYNC MODEL: "Start this operation, do other work, get result later"
     * 
     * COMPOSABILITY - THE KILLER FEATURE:
     * CompletableFuture enables functional-style operation chaining:
     * ```java
     * CompletableFuture.supplyAsync(this::fetchData)
     *     .thenApply(this::transform)
     *     .thenCompose(this::processAsync)
     *     .thenAccept(this::saveResult)
     *     .exceptionally(this::handleError);
     * ```
     * 
     * Each stage can run on different threads automatically!
     * 
     * KEY OPERATIONS DEMONSTRATED:
     * 
     * 1. INDEPENDENT ASYNC OPERATIONS:
     *    - Multiple operations start simultaneously
     *    - No blocking while waiting for results
     *    - CPU can work on other tasks
     * 
     * 2. COMBINING RESULTS:
     *    - thenCombine(): Merge results from independent operations
     *    - Results available when BOTH operations complete
     *    - Enables parallel processing patterns
     * 
     * 3. CHAINING TRANSFORMATIONS:
     *    - thenApply(): Transform result when available
     *    - Builds processing pipelines
     *    - Each stage runs when previous completes
     * 
     * 4. COORDINATING MULTIPLE FUTURES:
     *    - allOf(): Wait for ALL operations to complete
     *    - anyOf(): Wait for FIRST operation to complete
     *    - Enables scatter-gather patterns
     * 
     * EXCEPTION HANDLING:
     * Traditional threading makes exception handling complex.
     * CompletableFuture provides elegant solutions:
     * - exceptionally(): Handle exceptions functionally
     * - handle(): Process both success and failure cases
     * - whenComplete(): Cleanup regardless of outcome
     * 
     * PERFORMANCE BENEFITS:
     * - Better CPU utilization (no blocking threads)
     * - Reduced thread pool requirements
     * - Natural backpressure handling
     * - Composable error handling
     * 
     * REAL-WORLD APPLICATIONS:
     * - Microservice orchestration
     * - Parallel database queries
     * - Concurrent API calls
     * - Stream processing pipelines
     * - Reactive user interfaces
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
     * Demonstrates thread-safe collections and their importance
     * 
     * CONCURRENT COLLECTIONS - WHY THEY MATTER:
     * Regular collections (ArrayList, HashMap) are NOT thread-safe.
     * Concurrent access leads to:
     * - DATA CORRUPTION: Invalid internal state
     * - INFINITE LOOPS: Corrupted linked structures
     * - LOST UPDATES: Race conditions overwrite changes
     * - EXCEPTIONS: ConcurrentModificationException
     * 
     * JAVA'S CONCURRENT COLLECTION HIERARCHY:
     * 
     * 1. CONCURRENTHASHMAP:
     *    - Thread-safe alternative to HashMap
     *    - Uses segment-based locking (high concurrency)
     *    - Atomic operations for single-key operations
     *    - Consistent iteration (no ConcurrentModificationException)
     * 
     * 2. BLOCKINGQUEUE FAMILY:
     *    - Thread-safe queues with blocking operations
     *    - ArrayBlockingQueue: Fixed capacity
     *    - LinkedBlockingQueue: Optionally bounded
     *    - PriorityBlockingQueue: Sorted elements
     *    - SynchronousQueue: Zero capacity (direct handoff)
     * 
     * 3. COPYONWRITEARRAYLIST:
     *    - Thread-safe List for read-heavy scenarios
     *    - Copies array on every write operation
     *    - Excellent for listeners, observers
     * 
     * PERFORMANCE CHARACTERISTICS:
     * 
     * ConcurrentHashMap vs Synchronized HashMap:
     * - ConcurrentHashMap: Multiple readers + multiple writers
     * - Synchronized HashMap: Only one thread at a time
     * - Result: ConcurrentHashMap is much faster under contention
     * 
     * BlockingQueue vs Manual Synchronization:
     * - BlockingQueue: Built-in coordination (take/put)
     * - Manual sync: Complex wait/notify logic
     * - Result: BlockingQueue is simpler and more efficient
     * 
     * DESIGN PATTERNS ENABLED:
     * - Producer-Consumer: BlockingQueue handles coordination
     * - Worker Pool: Queue distributes tasks to workers
     * - Cache: ConcurrentHashMap for thread-safe caching
     * - Publisher-Subscriber: CopyOnWriteArrayList for listeners
     * 
     * LEARNING EXERCISE:
     * Try replacing ConcurrentHashMap with regular HashMap:
     * ```java
     * Map<String, Integer> regularMap = new HashMap<>();
     * ```
     * You'll likely see:
     * - Missing entries (lost updates)
     * - Inconsistent sizes
     * - Possible infinite loops or crashes
     * 
     * This demonstrates why concurrent collections are essential!
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
     * Comprehensive analysis of concurrency concepts and best practices
     * 
     * CONCURRENCY KNOWLEDGE HIERARCHY:
     * This method serves as a study guide for mastering concurrent programming.
     * Understanding these concepts in order will build a solid foundation.
     * 
     * LEVEL 1 - FUNDAMENTALS (Must Master First):
     * - Thread lifecycle and basic operations
     * - Race conditions and why they occur
     * - Critical sections and mutual exclusion
     * - Basic synchronization with synchronized keyword
     * 
     * LEVEL 2 - INTERMEDIATE CONCEPTS:
     * - Wait/notify mechanism for coordination
     * - Deadlock prevention and detection
     * - Thread pools and ExecutorService
     * - Atomic operations and lock-free programming
     * 
     * LEVEL 3 - ADVANCED PATTERNS:
     * - Producer-Consumer and other coordination patterns
     * - Concurrent collections and their use cases
     * - CompletableFuture and asynchronous programming
     * - Custom synchronizers (CountDownLatch, Semaphore)
     * 
     * CONCURRENCY DEBUGGING STRATEGIES:
     * 
     * 1. REPRODUCE ISSUES:
     *    - Use stress testing with multiple threads
     *    - Add Thread.sleep() to expose race conditions
     *    - Use different timing to reveal problems
     * 
     * 2. LOGGING AND MONITORING:
     *    - Thread names in log messages
     *    - Timestamps to understand ordering
     *    - Thread dumps for deadlock analysis
     * 
     * 3. TOOLS AND TECHNIQUES:
     *    - JVisualVM for thread monitoring
     *    - ThreadMXBean for programmatic monitoring
     *    - Stress testing frameworks
     *    - Static analysis tools (FindBugs, SpotBugs)
     * 
     * PERFORMANCE OPTIMIZATION PRINCIPLES:
     * 
     * 1. MINIMIZE SYNCHRONIZATION SCOPE:
     *    - Lock only what needs to be locked
     *    - Hold locks for shortest time possible
     *    - Use fine-grained locking when appropriate
     * 
     * 2. CHOOSE RIGHT TOOLS:
     *    - Atomic operations for simple updates
     *    - Concurrent collections for complex data
     *    - Thread pools for task management
     *    - Lock-free algorithms for high performance
     * 
     * 3. AVOID ANTI-PATTERNS:
     *    - Don't synchronize everything "just in case"
     *    - Don't use Thread.sleep() for synchronization
     *    - Don't ignore InterruptedException
     *    - Don't create excessive threads
     * 
     * TESTING CONCURRENT CODE:
     * 
     * Concurrent code requires special testing approaches:
     * - Unit tests with CountDownLatch for coordination
     * - Stress tests with many threads
     * - Property-based testing for race conditions
     * - Chaos engineering for resilience testing
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
        
        System.out.println("\n=== COMPREHENSIVE CONCURRENCY LEARNING SUMMARY ===");
        System.out.println("🎓 MASTERY CHECKLIST - Can you explain these concepts?");
        System.out.println();
        
        System.out.println("✅ THREAD FUNDAMENTALS:");
        System.out.println("   • What happens when you call thread.start() vs thread.run()?");
        System.out.println("   • Why use Runnable interface instead of extending Thread?");
        System.out.println("   • How does thread.join() work and why is it important?");
        System.out.println("   • What is InterruptedException and how should you handle it?");
        System.out.println();
        
        System.out.println("✅ SYNCHRONIZATION MASTERY:");
        System.out.println("   • What is a race condition and how do you prevent it?");
        System.out.println("   • Why use a private lock object instead of 'this'?");
        System.out.println("   • What's the difference between wait() and sleep()?");
        System.out.println("   • Why use while loops with wait() instead of if statements?");
        System.out.println();
        
        System.out.println("✅ ADVANCED PATTERNS:");
        System.out.println("   • How does the Producer-Consumer pattern solve coordination?");
        System.out.println("   • When should you use atomic operations vs synchronization?");
        System.out.println("   • What are the benefits of CompletableFuture over raw threads?");
        System.out.println("   • Why are concurrent collections better than synchronized wrappers?");
        System.out.println();
        
        System.out.println("✅ DESIGN PRINCIPLES:");
        System.out.println("   • How do you avoid deadlocks in your design?");
        System.out.println("   • When should you use thread pools vs creating threads?");
        System.out.println("   • What's the difference between blocking and non-blocking algorithms?");
        System.out.println("   • How do you choose the right concurrency tool for your problem?");
        System.out.println();
        
        System.out.println("🚀 NEXT STEPS FOR CONCURRENCY MASTERY:");
        System.out.println("   1. Study the java.util.concurrent package in detail");
        System.out.println("   2. Learn about memory models and happens-before relationships");
        System.out.println("   3. Explore reactive programming with RxJava or Project Reactor");
        System.out.println("   4. Practice with real-world concurrent programming challenges");
        System.out.println("   5. Study advanced topics: lock-free algorithms, NUMA awareness");
        System.out.println();
        
        System.out.println("💡 REMEMBER: Concurrency is about correctness first, performance second!");
        System.out.println("   Always ensure your code is thread-safe before optimizing for speed.");
        System.out.println("   Choose the simplest solution that meets your requirements.");
        System.out.println("   Test thoroughly - concurrent bugs are often rare and hard to reproduce.");
        
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
