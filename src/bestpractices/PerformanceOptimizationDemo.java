package bestpractices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * PerformanceOptimizationDemo - Demonstrates Java Performance Best Practices
 * 
 * This comprehensive demo covers:
 * - Memory management and GC optimization
 * - Collection performance and selection
 * - Stream API optimization
 * - Caching strategies
 * - Algorithm complexity analysis
 * - Profiling and benchmarking techniques
 * - Concurrency performance patterns
 * - JVM tuning considerations
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// Simple Benchmarking Utility
// This class provides basic performance measurement capabilities for educational purposes.
// In production, consider using JMH (Java Microbenchmark Harness) for more accurate measurements.

class Benchmark {
    
    /**
     * Measures the execution time of a task with JVM warm-up.
     * 
     * WHY: The JVM performs just-in-time (JIT) compilation and optimizations that can 
     * dramatically affect performance measurements. Without warm-up, the first execution
     * includes compilation overhead, making measurements unreliable.
     * 
     * WHAT: This method runs the task 1000 times to warm up the JVM, then measures
     * a single execution to get a representative performance figure.
     * 
     * @param name Human-readable name for the benchmark
     * @param task The code to benchmark (wrapped in a Runnable)
     */
    public static void measureExecution(String name, Runnable task) {
        // Warm up the JVM - this allows the JIT compiler to optimize the code
        // before we take our actual measurement. Critical for accurate results!
        for (int i = 0; i < 1000; i++) {
            task.run();
        }
        
        // Actual measurement using System.nanoTime() for high precision
        // nanoTime() is preferred over currentTimeMillis() for performance measurements
        // because it's monotonic (not affected by system clock changes)
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        
        // Convert nanoseconds to milliseconds for human-readable output
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("⏱️ %s: %.3f ms%n", name, executionTimeMs);
    }
    
    /**
     * Compares the performance of two different implementations of the same operation.
     * 
     * WHY: Performance comparisons help us understand the relative cost of different
     * approaches and make informed decisions about which implementation to use.
     * 
     * WHAT: This method runs both implementations with proper JVM warm-up and 
     * displays their execution times side by side for easy comparison.
     * 
     * @param operation Description of what operation is being compared
     * @param impl1 First implementation to test
     * @param name1 Name/description of the first implementation
     * @param impl2 Second implementation to test  
     * @param name2 Name/description of the second implementation
     */
    public static void comparePerformance(String operation, Runnable impl1, String name1, 
                                        Runnable impl2, String name2) {
        System.out.println("\n🏁 Performance Comparison: " + operation);
        
        // Measure each implementation separately with proper warm-up
        // This gives us a fair comparison by ensuring both benefit from JIT optimization
        measureExecution(name1, impl1);
        measureExecution(name2, impl2);
    }
    
    /**
     * Runs a task multiple times and provides statistical analysis of performance.
     * 
     * WHY: Single measurements can be unreliable due to JVM garbage collection,
     * system load, and other factors. Multiple iterations give us better insights
     * into performance characteristics including variability.
     * 
     * WHAT: Executes the task multiple times and calculates average, minimum,
     * and maximum execution times to show performance consistency.
     * 
     * @param name Description of the operation being measured
     * @param task The operation to benchmark
     * @param iterations Number of times to run the task
     */
    public static void runMultipleIterations(String name, Runnable task, int iterations) {
        System.out.println("\n📊 " + name + " (" + iterations + " iterations):");
        
        // Track timing statistics across multiple runs
        long totalTime = 0;
        long minTime = Long.MAX_VALUE;  // Start with maximum possible value
        long maxTime = Long.MIN_VALUE;  // Start with minimum possible value
        
        // Run the task multiple times to gather statistical data
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
            task.run();
            long endTime = System.nanoTime();
            
            long executionTime = endTime - startTime;
            totalTime += executionTime;
            minTime = Math.min(minTime, executionTime);  // Track fastest execution
            maxTime = Math.max(maxTime, executionTime);  // Track slowest execution
        }
        
        // Calculate and display statistics in human-readable format
        double avgTimeMs = (totalTime / iterations) / 1_000_000.0;
        double minTimeMs = minTime / 1_000_000.0;
        double maxTimeMs = maxTime / 1_000_000.0;
        
        System.out.printf("   Average: %.3f ms%n", avgTimeMs);
        System.out.printf("   Min: %.3f ms%n", minTimeMs);
        System.out.printf("   Max: %.3f ms%n", maxTimeMs);
    }
}

// Memory Management Examples
// This section demonstrates how memory allocation patterns significantly impact performance.
// Understanding these patterns is crucial for writing efficient Java applications.

class MemoryOptimizationExample {
    
    /**
     * Demonstrates the performance impact of object creation patterns.
     * 
     * WHY IMPORTANT: Object creation is one of the most expensive operations in Java.
     * Every object allocation involves:
     * 1. Memory allocation from the heap
     * 2. Object initialization 
     * 3. Eventual garbage collection
     * 
     * PERFORMANCE IMPACT: Excessive object creation can lead to:
     * - Increased memory usage
     * - More frequent garbage collection pauses
     * - Reduced application throughput
     * 
     * LEARNING OBJECTIVES:
     * - Understand the cost difference between string concatenation methods
     * - Learn about object pooling as an optimization technique
     * - See practical examples of memory-efficient coding
     */
    public static void demonstrateObjectCreationCost() {
        System.out.println("=== OBJECT CREATION PERFORMANCE ===");
        
        // Remove unused variable and add explanation
        // We're focusing on the comparison patterns rather than iteration count
        
        /* 
         * STRING CONCATENATION COMPARISON
         * 
         * This comparison shows why StringBuilder is dramatically faster than
         * string concatenation with the + operator.
         * 
         * String concatenation problem:
         * - Strings are immutable in Java
         * - Each += operation creates a new String object
         * - For n concatenations, this creates n temporary objects
         * - Time complexity: O(n²) due to copying
         * 
         * StringBuilder solution:
         * - Uses a resizable buffer internally
         * - Only creates one String object at the end
         * - Time complexity: O(n) - much more efficient
         */
        Benchmark.comparePerformance("String Building",
            () -> {
                String result = "";
                for (int i = 0; i < 1000; i++) {
                    result += "a";  // Creates new String object each time!
                }
                // Use result to prevent compiler optimization that might skip the work
                if (result.length() < 0) System.out.println(result);
            }, "String concatenation (+= operator)",
            () -> {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 1000; i++) {
                    sb.append("a");  // Reuses internal buffer
                }
                String result = sb.toString();  // Only one String object created
                // Use result to prevent compiler optimization
                if (result.length() < 0) System.out.println(result);
            }, "StringBuilder (recommended)"
        );
        
        /*
         * OBJECT POOLING DEMONSTRATION
         * 
         * Object pooling is a memory management technique where expensive objects
         * are reused instead of creating new ones repeatedly.
         * 
         * Benefits:
         * - Reduces object allocation overhead
         * - Decreases garbage collection pressure
         * - Can improve performance for expensive-to-create objects
         * 
         * Trade-offs:
         * - Increased code complexity
         * - Memory overhead (pool holds references)
         * - Thread safety considerations in concurrent environments
         */
        
        // Create a pool of StringBuilder objects for reuse
        // Pre-populate with 100 StringBuilders to avoid allocation during benchmark
        List<StringBuilder> pool = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            pool.add(new StringBuilder());
        }
        
        Benchmark.comparePerformance("Object Creation Strategy",
            () -> {
                // Traditional approach: create new object each time
                for (int i = 0; i < 10000; i++) {
                    StringBuilder sb = new StringBuilder();  // New allocation every iteration
                    sb.append("test").append(i);
                    // In real code, you'd use the StringBuilder result here
                }
            }, "New instance creation (higher memory allocation)",
            () -> {
                // Object pooling approach: reuse existing objects
                for (int i = 0; i < 10000; i++) {
                    // Get an object from the pool (round-robin selection)
                    StringBuilder sb = pool.get(i % pool.size());
                    sb.setLength(0);  // Clear previous content (faster than creating new)
                    sb.append("test").append(i);
                    // Object returns to pool for reuse (implicit in this example)
                }
            }, "Object pooling (memory reuse)"
        );
    }
    
    /**
     * Demonstrates the significant performance difference between primitive types and wrapper classes.
     * 
     * WHY THIS MATTERS: This is one of the most impactful yet often overlooked performance considerations.
     * 
     * PRIMITIVE TYPES (int, long, double, etc.):
     * - Stored directly on the stack (for local variables) or inline in objects
     * - No object header overhead (8-16 bytes per object in most JVMs)
     * - No indirection - direct memory access
     * - No garbage collection overhead
     * - CPU cache-friendly due to memory locality
     * 
     * WRAPPER CLASSES (Integer, Long, Double, etc.):
     * - Full objects stored on the heap
     * - Include object header (metadata) - typically 12-16 bytes overhead
     * - Require pointer dereferencing to access the actual value
     * - Subject to garbage collection
     * - Can cause memory fragmentation
     * 
     * PERFORMANCE IMPACT:
     * - Memory usage: 4-5x more memory for wrapper vs primitive
     * - Speed: Can be 2-10x slower depending on the operation
     * - Garbage collection: Wrapper classes create GC pressure
     * 
     * WHEN TO USE EACH:
     * - Primitives: For performance-critical code, large arrays, mathematical calculations
     * - Wrappers: When you need null values, generics, collections (which require objects)
     */
    public static void demonstratePrimitivePerformance() {
        System.out.println("\n=== PRIMITIVE VS WRAPPER PERFORMANCE ===");
        
        int size = 10_000_000;  // 10 million elements to see clear performance difference
        
        System.out.println("💡 Comparing performance with " + size + " elements:");
        System.out.println("   - Primitive int[]: ~40MB memory (4 bytes × 10M)");
        System.out.println("   - Integer[]: ~160MB+ memory (16+ bytes × 10M)");
        
        Benchmark.comparePerformance("Array Sum Calculation",
            () -> {
                // PRIMITIVE APPROACH: Maximum performance
                int[] primitives = new int[size];
                
                // Initialize array - direct memory writes, very fast
                for (int i = 0; i < size; i++) {
                    primitives[i] = i;  // Direct assignment, no object creation
                }
                
                // Sum calculation - direct memory reads, CPU cache friendly
                long sum = 0;
                for (int value : primitives) {
                    sum += value;  // Direct primitive addition, no unboxing needed
                }
                
                // Prevent compiler optimization by using the result
                if (sum < 0) System.out.print(""); // This will never execute, but prevents dead code elimination
            }, "Primitive int array (fast, memory efficient)",
            () -> {
                // WRAPPER APPROACH: More memory, slower performance
                Integer[] wrappers = new Integer[size];
                
                // Initialize array - creates 10 million Integer objects!
                for (int i = 0; i < size; i++) {
                    wrappers[i] = i;  // Autoboxing: creates new Integer(i) behind the scenes
                }
                
                // Sum calculation - requires unboxing each Integer to int
                long sum = 0;
                for (Integer value : wrappers) {
                    sum += value;  // Autounboxing: calls value.intValue() internally
                }
                
                // Prevent compiler optimization by using the result
                if (sum < 0) System.out.print(""); // This will never execute, but prevents dead code elimination
            }, "Integer wrapper array (slower, more memory)"
        );
        
        System.out.println("\n📊 Key Takeaways:");
        System.out.println("   • Primitive arrays are significantly faster and use less memory");
        System.out.println("   • Autoboxing/unboxing creates hidden performance costs");
        System.out.println("   • Use primitives in performance-critical code when possible");
        System.out.println("   • Consider primitive collections (TIntList, etc.) for better performance");
    }
}

// Collection Performance Examples

class CollectionPerformanceExample {
    
    /**
     * Compares performance of different List implementations
     */
    public static void compareListPerformance() {
        System.out.println("\n=== LIST PERFORMANCE COMPARISON ===");
        
        int size = 100_000;
        
        // Random access performance
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
        
        Benchmark.comparePerformance("Random Access (middle element)",
            () -> {
                for (int i = 0; i < 1000; i++) {
                    arrayList.get(size / 2);
                }
            }, "ArrayList",
            () -> {
                for (int i = 0; i < 1000; i++) {
                    linkedList.get(size / 2);
                }
            }, "LinkedList"
        );
        
        // Sequential iteration
        Benchmark.comparePerformance("Sequential Iteration",
            () -> {
                long sum = 0;
                for (Integer value : arrayList) {
                    sum += value;
                }
            }, "ArrayList",
            () -> {
                long sum = 0;
                for (Integer value : linkedList) {
                    sum += value;
                }
            }, "LinkedList"
        );
    }
    
    /**
     * Compares performance of different Set implementations
     */
    public static void compareSetPerformance() {
        System.out.println("\n=== SET PERFORMANCE COMPARISON ===");
        
        int size = 100_000;
        
        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> treeSet = new TreeSet<>();
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        
        // Insertion performance
        Benchmark.comparePerformance("Insertion Performance",
            () -> {
                Set<Integer> set = new HashSet<>();
                for (int i = 0; i < size; i++) {
                    set.add(i);
                }
            }, "HashSet",
            () -> {
                Set<Integer> set = new TreeSet<>();
                for (int i = 0; i < size; i++) {
                    set.add(i);
                }
            }, "TreeSet"
        );
        
        // Prepare sets for lookup test
        for (int i = 0; i < size; i++) {
            hashSet.add(i);
            treeSet.add(i);
            linkedHashSet.add(i);
        }
        
        // Lookup performance
        Random random = new Random(42);
        Benchmark.comparePerformance("Lookup Performance",
            () -> {
                for (int i = 0; i < 10000; i++) {
                    hashSet.contains(random.nextInt(size));
                }
            }, "HashSet",
            () -> {
                for (int i = 0; i < 10000; i++) {
                    treeSet.contains(random.nextInt(size));
                }
            }, "TreeSet"
        );
    }
    
    /**
     * Demonstrates the impact of initial capacity on performance
     */
    public static void demonstrateInitialCapacityImpact() {
        System.out.println("\n=== INITIAL CAPACITY IMPACT ===");
        
        int size = 1_000_000;
        
        Benchmark.comparePerformance("ArrayList with many elements",
            () -> {
                List<Integer> list = new ArrayList<>(); // Default capacity: 10
                for (int i = 0; i < size; i++) {
                    list.add(i);
                }
            }, "Default capacity",
            () -> {
                List<Integer> list = new ArrayList<>(size); // Pre-sized
                for (int i = 0; i < size; i++) {
                    list.add(i);
                }
            }, "Pre-sized capacity"
        );
        
        Benchmark.comparePerformance("HashMap with many elements",
            () -> {
                Map<Integer, String> map = new HashMap<>(); // Default capacity
                for (int i = 0; i < size; i++) {
                    map.put(i, "value" + i);
                }
            }, "Default capacity",
            () -> {
                Map<Integer, String> map = new HashMap<>(size); // Pre-sized
                for (int i = 0; i < size; i++) {
                    map.put(i, "value" + i);
                }
            }, "Pre-sized capacity"
        );
    }
}

// Stream API Performance

class StreamPerformanceExample {
    
    /**
     * Compares traditional loops vs Stream API performance
     */
    public static void compareStreamVsLoop() {
        System.out.println("\n=== STREAM VS LOOP PERFORMANCE ===");
        
        List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000)
                                        .boxed()
                                        .collect(Collectors.toList());
        
        // Filter and sum even numbers
        Benchmark.comparePerformance("Filter and Sum Even Numbers",
            () -> {
                long sum = 0;
                for (Integer number : numbers) {
                    if (number % 2 == 0) {
                        sum += number;
                    }
                }
            }, "Traditional for-each loop",
            () -> {
                long sum = numbers.stream()
                                 .filter(n -> n % 2 == 0)
                                 .mapToLong(Integer::longValue)
                                 .sum();
            }, "Stream API"
        );
        
        // Parallel streams
        Benchmark.comparePerformance("Parallel Processing",
            () -> {
                long sum = numbers.stream()
                                 .filter(n -> n % 2 == 0)
                                 .mapToLong(Integer::longValue)
                                 .sum();
            }, "Sequential Stream",
            () -> {
                long sum = numbers.parallelStream()
                                 .filter(n -> n % 2 == 0)
                                 .mapToLong(Integer::longValue)
                                 .sum();
            }, "Parallel Stream"
        );
    }
    
    /**
     * Demonstrates performance considerations for Stream operations
     */
    public static void demonstrateStreamOptimizations() {
        System.out.println("\n=== STREAM OPTIMIZATION TECHNIQUES ===");
        
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date", "elderberry",
                                          "fig", "grape", "honeydew", "kiwi", "lemon");
        
        // Repeat words to create larger dataset
        List<String> largeWordList = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            largeWordList.addAll(words);
        }
        
        // Eager vs lazy evaluation demonstration
        Benchmark.comparePerformance("Finding First Long Word",
            () -> {
                List<String> longWords = largeWordList.stream()
                                                     .filter(word -> word.length() > 5)
                                                     .collect(Collectors.toList());
                String first = longWords.isEmpty() ? null : longWords.get(0);
            }, "Collect all then get first",
            () -> {
                Optional<String> first = largeWordList.stream()
                                                     .filter(word -> word.length() > 5)
                                                     .findFirst();
            }, "findFirst() - lazy evaluation"
        );
        
        // Method reference vs lambda performance
        Benchmark.comparePerformance("String Length Mapping",
            () -> {
                List<Integer> lengths = largeWordList.stream()
                                                    .map(word -> word.length())
                                                    .collect(Collectors.toList());
            }, "Lambda expression",
            () -> {
                List<Integer> lengths = largeWordList.stream()
                                                    .map(String::length)
                                                    .collect(Collectors.toList());
            }, "Method reference"
        );
    }
}

// Caching Strategies

class CachingExample {
    
    private static final Map<Integer, Long> fibonacciCache = new ConcurrentHashMap<>();
    
    /**
     * Demonstrates the performance impact of caching
     */
    public static void demonstrateCaching() {
        System.out.println("\n=== CACHING PERFORMANCE IMPACT ===");
        
        int targetNumber = 40;
        
        Benchmark.comparePerformance("Fibonacci Calculation (n=" + targetNumber + ")",
            () -> {
                long result = fibonacciRecursive(targetNumber);
            }, "Without caching",
            () -> {
                long result = fibonacciWithCache(targetNumber);
            }, "With caching"
        );
        
        // Demonstrate cache effectiveness over multiple calls
        System.out.println("\n📈 Cache effectiveness over multiple calls:");
        
        long startTime = System.nanoTime();
        for (int i = 1; i <= 45; i++) {
            fibonacciWithCache(i);
        }
        long endTime = System.nanoTime();
        
        System.out.printf("⏱️ Cached Fibonacci (1-45): %.3f ms%n", 
                         (endTime - startTime) / 1_000_000.0);
        System.out.println("📊 Cache size: " + fibonacciCache.size() + " entries");
    }
    
    private static long fibonacciRecursive(int n) {
        if (n <= 1) return n;
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }
    
    private static long fibonacciWithCache(int n) {
        if (n <= 1) return n;
        
        return fibonacciCache.computeIfAbsent(n, key -> 
            fibonacciWithCache(key - 1) + fibonacciWithCache(key - 2)
        );
    }
    
    /**
     * Demonstrates different caching strategies
     */
    public static void demonstrateCachingStrategies() {
        System.out.println("\n=== CACHING STRATEGIES ===");
        
        // Simple cache with size limit
        Cache<String, String> lruCache = new LRUCache<>(100);
        
        // Populate cache
        for (int i = 0; i < 150; i++) {
            lruCache.put("key" + i, "value" + i);
        }
        
        System.out.println("🗄️ LRU Cache size after 150 insertions: " + lruCache.size());
        System.out.println("   Contains key0: " + lruCache.containsKey("key0"));
        System.out.println("   Contains key100: " + lruCache.containsKey("key100"));
        
        // Time-based expiration cache
        TimeBasedCache<String, String> timeCache = new TimeBasedCache<>(1000); // 1 second TTL
        
        timeCache.put("temp", "temporary value");
        System.out.println("\n⏰ Time-based cache:");
        System.out.println("   Immediately after insert: " + timeCache.get("temp"));
        
        try {
            Thread.sleep(1100); // Wait for expiration
            System.out.println("   After 1.1 seconds: " + timeCache.get("temp"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Simple cache implementations for demonstration

interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
    boolean containsKey(K key);
    int size();
}

class LRUCache<K, V> implements Cache<K, V> {
    private final int maxSize;
    private final LinkedHashMap<K, V> cache;
    
    public LRUCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<K, V>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCache.this.maxSize;
            }
        };
    }
    
    @Override
    public synchronized V get(K key) {
        return cache.get(key);
    }
    
    @Override
    public synchronized void put(K key, V value) {
        cache.put(key, value);
    }
    
    @Override
    public synchronized boolean containsKey(K key) {
        return cache.containsKey(key);
    }
    
    @Override
    public synchronized int size() {
        return cache.size();
    }
}

class TimeBasedCache<K, V> implements Cache<K, V> {
    private final long ttlMillis;
    private final Map<K, CacheEntry<V>> cache;
    
    public TimeBasedCache(long ttlMillis) {
        this.ttlMillis = ttlMillis;
        this.cache = new ConcurrentHashMap<>();
    }
    
    @Override
    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry == null || entry.isExpired()) {
            cache.remove(key);
            return null;
        }
        return entry.getValue();
    }
    
    @Override
    public void put(K key, V value) {
        cache.put(key, new CacheEntry<>(value, System.currentTimeMillis() + ttlMillis));
    }
    
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    @Override
    public int size() {
        // Clean expired entries first
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
        return cache.size();
    }
    
    private static class CacheEntry<V> {
        private final V value;
        private final long expirationTime;
        
        public CacheEntry(V value, long expirationTime) {
            this.value = value;
            this.expirationTime = expirationTime;
        }
        
        public V getValue() {
            return value;
        }
        
        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }
}

// Algorithm Complexity Analysis

class AlgorithmComplexityExample {
    
    /**
     * Demonstrates the impact of algorithm complexity
     */
    public static void demonstrateComplexityImpact() {
        System.out.println("\n=== ALGORITHM COMPLEXITY IMPACT ===");
        
        List<Integer> data = IntStream.rangeClosed(1, 10000)
                                    .boxed()
                                    .collect(Collectors.toList());
        Collections.shuffle(data);
        
        // Search algorithms comparison
        int searchTarget = 5000;
        
        Benchmark.comparePerformance("Search for element " + searchTarget,
            () -> {
                // Linear search O(n)
                for (Integer item : data) {
                    if (item.equals(searchTarget)) {
                        break;
                    }
                }
            }, "Linear Search O(n)",
            () -> {
                // Binary search O(log n) - requires sorted data
                List<Integer> sortedData = new ArrayList<>(data);
                Collections.sort(sortedData);
                Collections.binarySearch(sortedData, searchTarget);
            }, "Binary Search O(log n)"
        );
        
        // Sorting algorithms with different complexities
        demonstrateSortingComplexity();
    }
    
    private static void demonstrateSortingComplexity() {
        System.out.println("\n=== SORTING ALGORITHM COMPLEXITY ===");
        
        int[] sizes = {1000, 5000, 10000};
        
        for (int size : sizes) {
            System.out.println("\n📊 Array size: " + size);
            
            Integer[] data = IntStream.rangeClosed(1, size)
                                    .boxed()
                                    .toArray(Integer[]::new);
            Collections.shuffle(Arrays.asList(data));
            
            // Bubble sort O(n²)
            Benchmark.measureExecution("Bubble Sort O(n²)", () -> {
                Integer[] copy = data.clone();
                bubbleSort(copy);
            });
            
            // Merge sort O(n log n)
            Benchmark.measureExecution("Merge Sort O(n log n)", () -> {
                Integer[] copy = data.clone();
                Arrays.sort(copy); // Uses Timsort, which is O(n log n)
            });
        }
    }
    
    private static void bubbleSort(Integer[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    Integer temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}

// Concurrency Performance

class ConcurrencyPerformanceExample {
    
    /**
     * Demonstrates performance characteristics of different concurrent approaches
     */
    public static void demonstrateConcurrencyPerformance() {
        System.out.println("\n=== CONCURRENCY PERFORMANCE ===");
        
        int arraySize = 10_000_000;
        int[] data = IntStream.rangeClosed(1, arraySize).toArray();
        
        // Sequential vs parallel sum calculation
        Benchmark.comparePerformance("Array Sum Calculation",
            () -> {
                long sum = 0;
                for (int value : data) {
                    sum += value;
                }
            }, "Sequential",
            () -> {
                long sum = Arrays.stream(data)
                                .parallel()
                                .asLongStream()
                                .sum();
            }, "Parallel Stream"
        );
        
        // Thread pool size impact
        demonstrateThreadPoolSizing();
    }
    
    private static void demonstrateThreadPoolSizing() {
        System.out.println("\n=== THREAD POOL SIZING IMPACT ===");
        
        int taskCount = 1000;
        int[] poolSizes = {1, 2, 4, 8, 16, Runtime.getRuntime().availableProcessors()};
        
        for (int poolSize : poolSizes) {
            long startTime = System.nanoTime();
            
            ExecutorService executor = Executors.newFixedThreadPool(poolSize);
            CountDownLatch latch = new CountDownLatch(taskCount);
            
            for (int i = 0; i < taskCount; i++) {
                executor.submit(() -> {
                    try {
                        // Simulate CPU-bound work
                        double result = 0;
                        for (int j = 0; j < 10000; j++) {
                            result += Math.sin(j) * Math.cos(j);
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            }
            
            try {
                latch.await();
                long endTime = System.nanoTime();
                double executionTimeMs = (endTime - startTime) / 1_000_000.0;
                
                System.out.printf("🔧 Pool size %2d: %.0f ms%n", poolSize, executionTimeMs);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                executor.shutdown();
            }
        }
        
        System.out.println("💡 Available processors: " + Runtime.getRuntime().availableProcessors());
    }
}

public class PerformanceOptimizationDemo {
    
    /**
     * Demonstrates JVM performance monitoring
     */
    public static void demonstrateJVMMonitoring() {
        System.out.println("=== JVM PERFORMANCE MONITORING ===");
        
        Runtime runtime = Runtime.getRuntime();
        
        System.out.println("💾 Memory Information:");
        System.out.printf("   Total Memory: %.2f MB%n", runtime.totalMemory() / (1024.0 * 1024.0));
        System.out.printf("   Free Memory: %.2f MB%n", runtime.freeMemory() / (1024.0 * 1024.0));
        System.out.printf("   Used Memory: %.2f MB%n", 
                         (runtime.totalMemory() - runtime.freeMemory()) / (1024.0 * 1024.0));
        System.out.printf("   Max Memory: %.2f MB%n", runtime.maxMemory() / (1024.0 * 1024.0));
        
        System.out.println("\n🔧 System Information:");
        System.out.println("   Available Processors: " + runtime.availableProcessors());
        System.out.println("   Java Version: " + System.getProperty("java.version"));
        System.out.println("   JVM Name: " + System.getProperty("java.vm.name"));
    }
    
    /**
     * Analyzes performance optimization principles
     */
    public static void analyzePerformanceOptimization() {
        System.out.println("\n=== PERFORMANCE OPTIMIZATION ANALYSIS ===");
        
        System.out.println("Performance Optimization Principles:");
        System.out.println("1. 📏 Measure Before Optimizing");
        System.out.println("   • Profile to identify bottlenecks");
        System.out.println("   • Use tools like JProfiler, VisualVM, JMH");
        System.out.println("   • Focus on hotspots (80/20 rule)");
        
        System.out.println("\n2. 🧠 Algorithm and Data Structure Choice");
        System.out.println("   • Choose appropriate time/space complexity");
        System.out.println("   • Consider access patterns for collections");
        System.out.println("   • Use primitive collections when appropriate");
        
        System.out.println("\n3. 💾 Memory Management");
        System.out.println("   • Minimize object creation in hot paths");
        System.out.println("   • Use object pooling for expensive objects");
        System.out.println("   • Choose appropriate collection initial sizes");
        System.out.println("   • Avoid memory leaks and excessive GC pressure");
        
        System.out.println("\n4. 🔄 Caching Strategies");
        System.out.println("   • Cache expensive computations");
        System.out.println("   • Use appropriate cache eviction policies");
        System.out.println("   • Consider cache coherency in concurrent systems");
        
        System.out.println("\n5. 🧵 Concurrency Optimization");
        System.out.println("   • Use parallel processing for CPU-bound tasks");
        System.out.println("   • Size thread pools appropriately");
        System.out.println("   • Minimize synchronization overhead");
        System.out.println("   • Use lock-free data structures when possible");
        
        System.out.println("\n6. 📊 Stream and Lambda Optimization");
        System.out.println("   • Use primitive streams to avoid boxing");
        System.out.println("   • Prefer method references over lambdas");
        System.out.println("   • Use lazy evaluation with findFirst(), anyMatch()");
        System.out.println("   • Consider parallel streams for large datasets");
        
        System.out.println("\nCommon Performance Anti-Patterns:");
        System.out.println("• Premature optimization without measurement");
        System.out.println("• Using reflection in performance-critical code");
        System.out.println("• Excessive string concatenation in loops");
        System.out.println("• Creating unnecessary objects in hot paths");
        System.out.println("• Poor choice of collection types");
        System.out.println("• Synchronizing unnecessarily");
        
        System.out.println("\nJVM Tuning Considerations:");
        System.out.println("• Heap size (-Xms, -Xmx)");
        System.out.println("• Garbage collector selection (-XX:+UseG1GC)");
        System.out.println("• JIT compiler optimizations");
        System.out.println("• Method inlining and escape analysis");
    }
    
    /**
     * Main method demonstrating performance optimization techniques.
     * 
     * EXECUTION ORDER EXPLAINED:
     * The demonstrations are ordered from foundational concepts to advanced techniques:
     * 
     * 1. JVM Monitoring: Understanding your runtime environment
     * 2. Memory Optimization: Object creation and primitive vs wrapper performance
     * 3. Collection Performance: Choosing the right data structures
     * 4. Stream Optimization: Modern Java performance considerations
     * 5. Caching Strategies: Trading memory for computation speed
     * 6. Algorithm Complexity: Understanding Big O impact on real performance
     * 7. Concurrency Performance: Leveraging multiple cores effectively
     * 
     * RUNNING THIS DEMO:
     * - Expected runtime: 30-60 seconds depending on hardware
     * - Memory usage: Peak ~200MB for large dataset demonstrations
     * - CPU usage: Will utilize multiple cores during concurrency tests
     * 
     * INTERPRETING RESULTS:
     * - Execution times will vary based on hardware and JVM implementation
     * - Focus on relative performance differences, not absolute numbers
     * - Multiple runs may show different results due to JVM optimizations
     * - Garbage collection can cause timing variations
     * 
     * @param args command line arguments (not used in this demo)
     */
    public static void main(String[] args) {
        System.out.println("Java Performance Optimization Demonstration");
        System.out.println("==========================================");
        System.out.println("💡 This demo shows practical performance optimization techniques");
        System.out.println("⏱️ Each benchmark includes JVM warm-up for accurate measurements");
        System.out.println("📊 Focus on relative performance differences between approaches");
        System.out.println();
        
        // Foundation: Understanding the runtime environment
        demonstrateJVMMonitoring();
        
        // Memory optimization: The foundation of Java performance
        MemoryOptimizationExample.demonstrateObjectCreationCost();
        MemoryOptimizationExample.demonstratePrimitivePerformance();
        
        // Collection performance: Choosing the right data structures
        CollectionPerformanceExample.compareListPerformance();
        CollectionPerformanceExample.compareSetPerformance();
        CollectionPerformanceExample.demonstrateInitialCapacityImpact();
        
        // Modern Java features: Stream API optimization
        StreamPerformanceExample.compareStreamVsLoop();
        StreamPerformanceExample.demonstrateStreamOptimizations();
        
        // Caching: Trading memory for speed
        CachingExample.demonstrateCaching();
        CachingExample.demonstrateCachingStrategies();
        
        // Algorithm fundamentals: Why algorithm choice matters most
        AlgorithmComplexityExample.demonstrateComplexityImpact();
        
        // Concurrency: Leveraging multiple cores
        ConcurrencyPerformanceExample.demonstrateConcurrencyPerformance();
        
        // Analysis and best practices
        analyzePerformanceOptimization();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🎯 PERFORMANCE OPTIMIZATION SUMMARY");
        System.out.println("=".repeat(50));
        
        System.out.println("\n📈 KEY TAKEAWAYS FROM THIS DEMONSTRATION:");
        System.out.println("Performance optimization is a systematic, data-driven process that requires:");
        
        System.out.println("\n1. 📏 MEASUREMENT FIRST (Most Important!)");
        System.out.println("   • Profile to identify actual bottlenecks, not assumed ones");
        System.out.println("   • Use tools like JProfiler, VisualVM, JMH for accurate measurements");
        System.out.println("   • Focus on hotspots using the 80/20 rule (80% of time spent in 20% of code)");
        System.out.println("   • Measure before and after optimizations to verify improvements");
        
        System.out.println("\n2. 🧠 ALGORITHM AND DATA STRUCTURE CHOICE (Biggest Impact!)");
        System.out.println("   • Choose appropriate time/space complexity for your use case");
        System.out.println("   • O(n²) vs O(n log n) can mean the difference between seconds and hours");
        System.out.println("   • Consider access patterns when selecting collections");
        System.out.println("   • Use primitive collections when boxing overhead matters");
        
        System.out.println("\n3. 💾 MEMORY MANAGEMENT (Foundation of Performance)");
        System.out.println("   • Minimize object creation in performance-critical paths");
        System.out.println("   • Use object pooling judiciously for expensive-to-create objects");
        System.out.println("   • Set appropriate initial capacities for collections");
        System.out.println("   • Prefer primitives over wrappers when possible");
        System.out.println("   • Avoid memory leaks that cause excessive garbage collection");
        
        System.out.println("\n4. 🔄 CACHING STRATEGIES (Time vs Space Trade-offs)");
        System.out.println("   • Cache expensive computations, not cheap ones");
        System.out.println("   • Choose appropriate cache eviction policies (LRU, TTL, size-based)");
        System.out.println("   • Consider cache coherency in concurrent/distributed systems");
        System.out.println("   • Monitor cache hit rates and adjust strategies accordingly");
        
        System.out.println("\n5. 🧵 CONCURRENCY OPTIMIZATION (Scaling with Hardware)");
        System.out.println("   • Use parallel processing for CPU-bound tasks with sufficient data");
        System.out.println("   • Size thread pools based on workload characteristics");
        System.out.println("   • Minimize synchronization overhead and lock contention");
        System.out.println("   • Consider lock-free data structures for high-contention scenarios");
        
        System.out.println("\n6. 📊 MODERN JAVA OPTIMIZATION (Leveraging Language Features)");
        System.out.println("   • Use primitive streams to avoid autoboxing overhead");
        System.out.println("   • Prefer method references over lambda expressions");
        System.out.println("   • Leverage lazy evaluation with findFirst(), anyMatch(), etc.");
        System.out.println("   • Use parallel streams wisely - not always faster!");
        
        System.out.println("\n⚠️ COMMON PERFORMANCE ANTI-PATTERNS TO AVOID:");
        System.out.println("   ❌ Premature optimization without measurement");
        System.out.println("   ❌ Using reflection in performance-critical code");
        System.out.println("   ❌ String concatenation in loops (use StringBuilder)");
        System.out.println("   ❌ Creating unnecessary objects in hot paths");
        System.out.println("   ❌ Poor choice of collection types for access patterns");
        System.out.println("   ❌ Over-synchronization leading to contention");
        System.out.println("   ❌ Ignoring garbage collection patterns and impact");
        
        System.out.println("\n🔧 JVM TUNING CONSIDERATIONS:");
        System.out.println("   • Heap sizing (-Xms, -Xmx) based on application needs");
        System.out.println("   • Garbage collector selection (-XX:+UseG1GC, -XX:+UseZGC)");
        System.out.println("   • Enable JIT compiler optimizations");
        System.out.println("   • Monitor method inlining and escape analysis effects");
        System.out.println("   • Use JVM flags to enable detailed performance logging");
        
        System.out.println("\n🛠️ RECOMMENDED TOOLS FOR CONTINUED LEARNING:");
        System.out.println("   📊 JMH (Java Microbenchmark Harness): Gold standard for microbenchmarking");
        System.out.println("   📈 VisualVM: Free JVM monitoring and profiling tool");
        System.out.println("   💰 JProfiler: Commercial profiler with advanced features");
        System.out.println("   📋 GCEasy.io: Online garbage collection log analyzer");
        System.out.println("   🔍 JConsole: Built-in JVM monitoring tool");
        System.out.println("   📱 Flight Recorder: Low-overhead production profiling");
        
        System.out.println("\n🎓 PERFORMANCE OPTIMIZATION METHODOLOGY:");
        System.out.println("   1. Identify performance requirements and acceptable thresholds");
        System.out.println("   2. Measure current performance with representative workloads");
        System.out.println("   3. Profile to find actual bottlenecks (not assumed ones)");
        System.out.println("   4. Optimize the biggest bottlenecks first");
        System.out.println("   5. Measure again to verify improvements");
        System.out.println("   6. Repeat until requirements are met");
        System.out.println("   7. Monitor performance in production continuously");
        
        System.out.println("\n💡 REMEMBER: The fastest code is code that doesn't run at all!");
        System.out.println("   Sometimes the best optimization is eliminating unnecessary work entirely.");
        System.out.println();
        System.out.println("🚀 Performance optimization is an ongoing journey, not a destination!");
    }
}
