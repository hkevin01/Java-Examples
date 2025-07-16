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

class Benchmark {
    
    public static void measureExecution(String name, Runnable task) {
        // Warm up
        for (int i = 0; i < 1000; i++) {
            task.run();
        }
        
        // Actual measurement
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        
        double executionTimeMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("⏱️ %s: %.3f ms%n", name, executionTimeMs);
    }
    
    public static void comparePerformance(String operation, Runnable impl1, String name1, 
                                        Runnable impl2, String name2) {
        System.out.println("\n🏁 Performance Comparison: " + operation);
        measureExecution(name1, impl1);
        measureExecution(name2, impl2);
    }
    
    public static void runMultipleIterations(String name, Runnable task, int iterations) {
        System.out.println("\n📊 " + name + " (" + iterations + " iterations):");
        
        long totalTime = 0;
        long minTime = Long.MAX_VALUE;
        long maxTime = Long.MIN_VALUE;
        
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
            task.run();
            long endTime = System.nanoTime();
            
            long executionTime = endTime - startTime;
            totalTime += executionTime;
            minTime = Math.min(minTime, executionTime);
            maxTime = Math.max(maxTime, executionTime);
        }
        
        double avgTimeMs = (totalTime / iterations) / 1_000_000.0;
        double minTimeMs = minTime / 1_000_000.0;
        double maxTimeMs = maxTime / 1_000_000.0;
        
        System.out.printf("   Average: %.3f ms%n", avgTimeMs);
        System.out.printf("   Min: %.3f ms%n", minTimeMs);
        System.out.printf("   Max: %.3f ms%n", maxTimeMs);
    }
}

// Memory Management Examples

class MemoryOptimizationExample {
    
    /**
     * Demonstrates the performance impact of object creation
     */
    public static void demonstrateObjectCreationCost() {
        System.out.println("=== OBJECT CREATION PERFORMANCE ===");
        
        int iterations = 1_000_000;
        
        // String concatenation vs StringBuilder
        Benchmark.comparePerformance("String Building",
            () -> {
                String result = "";
                for (int i = 0; i < 1000; i++) {
                    result += "a";
                }
                // Use result to prevent optimization
                if (result.length() < 0) System.out.println(result);
            }, "String concatenation",
            () -> {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 1000; i++) {
                    sb.append("a");
                }
                String result = sb.toString();
                // Use result to prevent optimization
                if (result.length() < 0) System.out.println(result);
            }, "StringBuilder"
        );
        
        // Object pooling vs new instance creation
        List<StringBuilder> pool = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            pool.add(new StringBuilder());
        }
        
        Benchmark.comparePerformance("Object Creation",
            () -> {
                for (int i = 0; i < 10000; i++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("test").append(i);
                }
            }, "New instance each time",
            () -> {
                for (int i = 0; i < 10000; i++) {
                    StringBuilder sb = pool.get(i % pool.size());
                    sb.setLength(0);
                    sb.append("test").append(i);
                }
            }, "Object pooling"
        );
    }
    
    /**
     * Demonstrates primitive vs wrapper class performance
     */
    public static void demonstratePrimitivePerformance() {
        System.out.println("\n=== PRIMITIVE VS WRAPPER PERFORMANCE ===");
        
        int size = 10_000_000;
        
        Benchmark.comparePerformance("Array Sum Calculation",
            () -> {
                int[] primitives = new int[size];
                for (int i = 0; i < size; i++) {
                    primitives[i] = i;
                }
                long sum = 0;
                for (int value : primitives) {
                    sum += value;
                }
                if (sum < 0) System.out.print(""); // Prevent optimization
            }, "Primitive int array",
            () -> {
                Integer[] wrappers = new Integer[size];
                for (int i = 0; i < size; i++) {
                    wrappers[i] = i;
                }
                long sum = 0;
                for (Integer value : wrappers) {
                    sum += value;
                }
                if (sum < 0) System.out.print(""); // Prevent optimization
            }, "Integer wrapper array"
        );
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
     * Main method demonstrating performance optimization techniques
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Performance Optimization Demonstration");
        System.out.println("==========================================");
        
        demonstrateJVMMonitoring();
        MemoryOptimizationExample.demonstrateObjectCreationCost();
        MemoryOptimizationExample.demonstratePrimitivePerformance();
        CollectionPerformanceExample.compareListPerformance();
        CollectionPerformanceExample.compareSetPerformance();
        CollectionPerformanceExample.demonstrateInitialCapacityImpact();
        StreamPerformanceExample.compareStreamVsLoop();
        StreamPerformanceExample.demonstrateStreamOptimizations();
        CachingExample.demonstrateCaching();
        CachingExample.demonstrateCachingStrategies();
        AlgorithmComplexityExample.demonstrateComplexityImpact();
        ConcurrencyPerformanceExample.demonstrateConcurrencyPerformance();
        analyzePerformanceOptimization();
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("Performance optimization is a systematic process:");
        System.out.println("• Measure and profile to identify bottlenecks");
        System.out.println("• Choose appropriate algorithms and data structures");
        System.out.println("• Optimize memory usage and reduce GC pressure");
        System.out.println("• Implement effective caching strategies");
        System.out.println("• Leverage concurrency for CPU-bound operations");
        System.out.println("• Use modern Java features efficiently");
        System.out.println("• Continuously monitor and validate improvements");
        
        System.out.println("\nRecommended Tools:");
        System.out.println("• JMH: Microbenchmarking framework");
        System.out.println("• VisualVM: JVM monitoring and profiling");
        System.out.println("• JProfiler: Commercial Java profiler");
        System.out.println("• GCEasy: Online GC log analyzer");
        System.out.println("• JConsole: Built-in JVM monitoring tool");
    }
}
