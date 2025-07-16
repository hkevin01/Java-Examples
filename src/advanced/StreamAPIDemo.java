package advanced;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * StreamAPIDemo - Comprehensive demonstration of Java Stream API and Functional Programming
 * 
 * WHAT IS THE STREAM API?
 * The Stream API (introduced in Java 8) provides a functional programming approach to processing
 * collections of data. Streams are NOT data structures - they're a way to express computations
 * on data in a declarative, functional style.
 * 
 * WHY USE STREAMS?
 * - Declarative Programming: Express WHAT you want, not HOW to do it
 * - Composability: Chain operations together naturally
 * - Parallelization: Easy parallel processing with .parallel()
 * - Readability: More expressive than traditional loops
 * - Efficiency: Lazy evaluation and optimized operations
 * 
 * KEY CONCEPTS:
 * 1. FUNCTIONAL INTERFACES: Single-method interfaces that can be lambda targets
 * 2. LAMBDA EXPRESSIONS: Anonymous functions for concise code
 * 3. METHOD REFERENCES: Shortcuts for lambda expressions
 * 4. INTERMEDIATE OPERATIONS: Transform streams (lazy evaluation)
 * 5. TERMINAL OPERATIONS: Produce results (trigger evaluation)
 * 
 * STREAM LIFECYCLE:
 * Source → Intermediate Operations → Terminal Operation
 * 
 * PERFORMANCE CONSIDERATIONS:
 * - Streams have overhead for simple operations
 * - Best for complex processing pipelines
 * - Parallel streams aren't always faster (overhead vs benefit)
 * - Stateful operations can hurt performance
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class StreamAPIDemo {
    
    /**
     * LAMBDA EXPRESSIONS: Anonymous functions that implement functional interfaces
     * 
     * SYNTAX: (parameters) -> expression
     * OR:     (parameters) -> { statements }
     * 
     * WHY LAMBDAS?
     * - Replace verbose anonymous inner classes
     * - Enable functional programming paradigms
     * - Make code more readable and maintainable
     * - Required for Stream API operations
     * 
     * LAMBDA RULES:
     * - Can only implement functional interfaces (single abstract method)
     * - Type inference reduces boilerplate
     * - Can capture effectively final variables from enclosing scope
     * - Create no new scope (this refers to enclosing instance)
     */
    public static void demonstrateLambdaExpressions() {
        System.out.println("=== LAMBDA EXPRESSIONS DEMONSTRATION ===\n");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana", "Edward");
        
        // TRADITIONAL APPROACH: Anonymous inner class
        System.out.println("1. Traditional Anonymous Inner Class:");
        names.forEach(new Consumer<String>() {
            @Override
            public void accept(String name) {
                System.out.println("Hello, " + name);
            }
        });
        
        System.out.println("\n2. Lambda Expression - Simplified:");
        // LAMBDA APPROACH: Much more concise
        names.forEach(name -> System.out.println("Hello, " + name));
        
        System.out.println("\n3. Method Reference - Even simpler:");
        // METHOD REFERENCE: Ultimate conciseness for simple operations
        names.forEach(System.out::println);
        
        // LAMBDA WITH MULTIPLE PARAMETERS
        System.out.println("\n4. Lambda with Multiple Parameters:");
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", 92);
        
        scores.forEach((name, score) -> 
            System.out.printf("%s scored %d points%n", name, score));
        
        // LAMBDA WITH BLOCK BODY
        System.out.println("\n5. Lambda with Block Body (Multiple Statements):");
        names.stream()
            .filter(name -> name.length() > 4)
            .forEach(name -> {
                String message = "Processing: " + name;
                System.out.println(message.toUpperCase());
            });
        
        System.out.println();
    }
    
    /**
     * FUNCTIONAL INTERFACES: Interfaces with exactly one abstract method
     * 
     * BUILT-IN FUNCTIONAL INTERFACES:
     * - Predicate<T>: T -> boolean (testing/filtering)
     * - Function<T,R>: T -> R (transformation/mapping)
     * - Consumer<T>: T -> void (side effects/actions)
     * - Supplier<T>: () -> T (creation/generation)
     * - BiFunction<T,U,R>: (T,U) -> R (two-input functions)
     * 
     * @FunctionalInterface annotation ensures interface has exactly one abstract method
     */
    public static void demonstrateFunctionalInterfaces() {
        System.out.println("=== FUNCTIONAL INTERFACES DEMONSTRATION ===\n");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // PREDICATE: Testing/Filtering (T -> boolean)
        System.out.println("1. Predicate<T> - Testing/Filtering:");
        Predicate<Integer> isEven = number -> number % 2 == 0;
        Predicate<Integer> isGreaterThanFive = number -> number > 5;
        
        System.out.println("Even numbers: " + 
            numbers.stream().filter(isEven).collect(Collectors.toList()));
        
        // PREDICATE COMPOSITION: and(), or(), negate()
        System.out.println("Even AND greater than 5: " + 
            numbers.stream().filter(isEven.and(isGreaterThanFive)).collect(Collectors.toList()));
        
        // FUNCTION: Transformation/Mapping (T -> R)
        System.out.println("\n2. Function<T,R> - Transformation/Mapping:");
        Function<Integer, String> numberToWord = number -> {
            switch(number) {
                case 1: return "one";
                case 2: return "two";
                case 3: return "three";
                default: return "number(" + number + ")";
            }
        };
        
        System.out.println("Numbers as words: " + 
            numbers.stream().limit(3).map(numberToWord).collect(Collectors.toList()));
        
        // FUNCTION COMPOSITION: andThen(), compose()
        Function<Integer, Integer> multiplyByTwo = x -> x * 2;
        Function<Integer, Integer> addTen = x -> x + 10;
        Function<Integer, Integer> multiplyThenAdd = multiplyByTwo.andThen(addTen);
        
        System.out.println("Multiply by 2 then add 10 to 5: " + multiplyThenAdd.apply(5));
        
        // CONSUMER: Side Effects/Actions (T -> void)
        System.out.println("\n3. Consumer<T> - Side Effects/Actions:");
        Consumer<String> printUpperCase = str -> System.out.println(str.toUpperCase());
        Consumer<String> printLength = str -> System.out.println("Length: " + str.length());
        
        // CONSUMER COMPOSITION: andThen()
        Consumer<String> combinedConsumer = printUpperCase.andThen(printLength);
        combinedConsumer.accept("Hello World");
        
        // SUPPLIER: Creation/Generation (() -> T)
        System.out.println("\n4. Supplier<T> - Creation/Generation:");
        Supplier<String> randomGreeting = () -> {
            String[] greetings = {"Hello", "Hi", "Hey", "Greetings"};
            return greetings[new Random().nextInt(greetings.length)];
        };
        
        System.out.println("Random greeting: " + randomGreeting.get());
        System.out.println("Another random greeting: " + randomGreeting.get());
        
        System.out.println();
    }
    
    /**
     * STREAM OPERATIONS: Intermediate and Terminal operations
     * 
     * INTERMEDIATE OPERATIONS (return Stream):
     * - filter(), map(), flatMap(), distinct(), sorted()
     * - limit(), skip(), peek()
     * - Lazy evaluation: only executed when terminal operation is called
     * 
     * TERMINAL OPERATIONS (return result):
     * - forEach(), collect(), reduce(), count()
     * - anyMatch(), allMatch(), noneMatch()
     * - findFirst(), findAny(), min(), max()
     */
    public static void demonstrateStreamOperations() {
        System.out.println("=== STREAM OPERATIONS DEMONSTRATION ===\n");
        
        List<String> words = Arrays.asList(
            "Java", "Stream", "Lambda", "Functional", "Programming",
            "Concurrency", "Performance", "Clean", "Code", "Design"
        );
        
        // FILTERING: Select elements based on criteria
        System.out.println("1. FILTERING Operations:");
        List<String> longWords = words.stream()
            .filter(word -> word.length() > 6)
            .collect(Collectors.toList());
        System.out.println("Words longer than 6 characters: " + longWords);
        
        // MAPPING: Transform elements
        System.out.println("\n2. MAPPING Operations:");
        List<Integer> wordLengths = words.stream()
            .map(String::length)
            .collect(Collectors.toList());
        System.out.println("Word lengths: " + wordLengths);
        
        List<String> upperCaseWords = words.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Uppercase words: " + upperCaseWords);
        
        // SORTING: Order elements
        System.out.println("\n3. SORTING Operations:");
        List<String> sortedWords = words.stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Alphabetically sorted: " + sortedWords);
        
        List<String> sortedByLength = words.stream()
            .sorted(Comparator.comparing(String::length))
            .collect(Collectors.toList());
        System.out.println("Sorted by length: " + sortedByLength);
        
        // COMPLEX PIPELINE: Multiple operations chained
        System.out.println("\n4. COMPLEX PIPELINE:");
        List<String> result = words.stream()
            .filter(word -> word.length() > 4)        // Only words > 4 chars
            .map(String::toLowerCase)                 // Convert to lowercase
            .sorted()                                 // Sort alphabetically
            .distinct()                               // Remove duplicates
            .limit(5)                                 // Take first 5
            .collect(Collectors.toList());
        System.out.println("Filtered, mapped, sorted, distinct, limited: " + result);
        
        // STATISTICAL OPERATIONS
        System.out.println("\n5. STATISTICAL Operations:");
        IntSummaryStatistics stats = words.stream()
            .mapToInt(String::length)
            .summaryStatistics();
        
        System.out.printf("Word length statistics: min=%d, max=%d, average=%.2f, count=%d%n",
            stats.getMin(), stats.getMax(), stats.getAverage(), stats.getCount());
        
        System.out.println();
    }
    
    /**
     * COLLECTORS: Powerful way to accumulate stream elements
     * 
     * BUILT-IN COLLECTORS:
     * - toList(), toSet(), toMap()
     * - joining(), groupingBy(), partitioningBy()
     * - counting(), summingInt(), averagingDouble()
     * - reducing(), collectingAndThen()
     * 
     * CUSTOM COLLECTORS: Can create using Collector.of()
     */
    public static void demonstrateCollectors() {
        System.out.println("=== COLLECTORS DEMONSTRATION ===\n");
        
        List<Person> people = Arrays.asList(
            new Person("Alice", 28, "Engineering"),
            new Person("Bob", 35, "Marketing"),
            new Person("Charlie", 32, "Engineering"),
            new Person("Diana", 29, "Sales"),
            new Person("Edward", 41, "Engineering"),
            new Person("Fiona", 26, "Marketing")
        );
        
        // BASIC COLLECTION
        System.out.println("1. Basic Collection:");
        List<String> names = people.stream()
            .map(Person::getName)
            .collect(Collectors.toList());
        System.out.println("Names: " + names);
        
        Set<String> departments = people.stream()
            .map(Person::getDepartment)
            .collect(Collectors.toSet());
        System.out.println("Unique departments: " + departments);
        
        // GROUPING BY
        System.out.println("\n2. Grouping By Department:");
        Map<String, List<Person>> byDepartment = people.stream()
            .collect(Collectors.groupingBy(Person::getDepartment));
        
        byDepartment.forEach((dept, empList) -> {
            System.out.println(dept + ": " + 
                empList.stream().map(Person::getName).collect(Collectors.toList()));
        });
        
        // PARTITIONING BY
        System.out.println("\n3. Partitioning By Age (30+):");
        Map<Boolean, List<Person>> partitioned = people.stream()
            .collect(Collectors.partitioningBy(person -> person.getAge() >= 30));
        
        System.out.println("30 or older: " + 
            partitioned.get(true).stream().map(Person::getName).collect(Collectors.toList()));
        System.out.println("Under 30: " + 
            partitioned.get(false).stream().map(Person::getName).collect(Collectors.toList()));
        
        // DOWNSTREAM COLLECTORS
        System.out.println("\n4. Downstream Collectors (Count by Department):");
        Map<String, Long> countByDepartment = people.stream()
            .collect(Collectors.groupingBy(Person::getDepartment, Collectors.counting()));
        System.out.println("Department counts: " + countByDepartment);
        
        // STATISTICS
        System.out.println("\n5. Statistics Collection:");
        IntSummaryStatistics ageStats = people.stream()
            .collect(Collectors.summarizingInt(Person::getAge));
        System.out.printf("Age statistics: min=%d, max=%d, average=%.2f%n",
            ageStats.getMin(), ageStats.getMax(), ageStats.getAverage());
        
        // JOINING
        System.out.println("\n6. Joining Strings:");
        String allNames = people.stream()
            .map(Person::getName)
            .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("All names joined: " + allNames);
        
        System.out.println();
    }
    
    /**
     * PARALLEL STREAMS: Leverage multiple CPU cores for processing
     * 
     * WHEN TO USE PARALLEL STREAMS:
     * - Large data sets (thousands+ elements)
     * - CPU-intensive operations
     * - Independent operations (no shared mutable state)
     * - Operations that benefit from parallelization
     * 
     * WHEN NOT TO USE:
     * - Small data sets (overhead > benefit)
     * - I/O bound operations
     * - Operations requiring ordering
     * - Operations with side effects
     * 
     * PARALLEL STREAM CONSIDERATIONS:
     * - Uses ForkJoinPool.commonPool() by default
     * - Split data into chunks for parallel processing
     * - Results combined after parallel execution
     * - May not preserve order unless specifically requested
     */
    public static void demonstrateParallelStreams() {
        System.out.println("=== PARALLEL STREAMS DEMONSTRATION ===\n");
        
        // Create large dataset for meaningful parallel processing
        List<Integer> largeList = IntStream.range(1, 1_000_000)
            .boxed()
            .collect(Collectors.toList());
        
        // SEQUENTIAL PROCESSING
        System.out.println("1. Sequential vs Parallel Processing Comparison:");
        long startTime = System.currentTimeMillis();
        
        long sequentialSum = largeList.stream()
            .filter(n -> n % 2 == 0)
            .mapToLong(n -> n * n)
            .sum();
        
        long sequentialTime = System.currentTimeMillis() - startTime;
        
        // PARALLEL PROCESSING
        startTime = System.currentTimeMillis();
        
        long parallelSum = largeList.parallelStream()
            .filter(n -> n % 2 == 0)
            .mapToLong(n -> n * n)
            .sum();
        
        long parallelTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Sequential result: " + sequentialSum + " (Time: " + sequentialTime + "ms)");
        System.out.println("Parallel result: " + parallelSum + " (Time: " + parallelTime + "ms)");
        System.out.println("Speedup: " + (double)sequentialTime / parallelTime + "x");
        
        // PARALLEL STREAM CREATION
        System.out.println("\n2. Ways to Create Parallel Streams:");
        
        // Method 1: From collection
        Stream<Integer> parallelFromCollection = Arrays.asList(1, 2, 3, 4, 5).parallelStream();
        
        // Method 2: Convert sequential to parallel
        Stream<Integer> convertedToParallel = Stream.of(1, 2, 3, 4, 5).parallel();
        
        // Method 3: Parallel range
        IntStream parallelRange = IntStream.range(1, 1000).parallel();
        
        System.out.println("Parallel stream processing thread names:");
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .parallelStream()
            .forEach(num -> System.out.println("Processing " + num + 
                " on thread: " + Thread.currentThread().getName()));
        
        System.out.println();
    }
    
    /**
     * COMPLETABLEFUTURE: Asynchronous programming with functional composition
     * 
     * WHY COMPLETABLEFUTURE?
     * - Non-blocking asynchronous computation
     * - Functional composition of async operations
     * - Exception handling in async chains
     * - Combining multiple async operations
     * 
     * KEY METHODS:
     * - supplyAsync(), runAsync(): Start async computation
     * - thenApply(), thenCompose(): Transform results
     * - thenCombine(), allOf(), anyOf(): Combine futures
     * - handle(), exceptionally(): Exception handling
     */
    public static void demonstrateCompletableFuture() {
        System.out.println("=== COMPLETABLEFUTURE DEMONSTRATION ===\n");
        
        // BASIC ASYNC COMPUTATION
        System.out.println("1. Basic Async Computation:");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // Simulate long-running computation
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Hello from async computation!";
        });
        
        System.out.println("Started async computation...");
        try {
            String result = future.get(); // Blocking call to get result
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        // CHAINING ASYNC OPERATIONS
        System.out.println("\n2. Chaining Async Operations:");
        CompletableFuture<String> chainedFuture = CompletableFuture
            .supplyAsync(() -> "Initial")
            .thenApply(s -> s + " -> Transformed")
            .thenApply(s -> s + " -> Further Transformed")
            .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " -> Composed"));
        
        try {
            System.out.println("Chained result: " + chainedFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        // COMBINING MULTIPLE FUTURES
        System.out.println("\n3. Combining Multiple Futures:");
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);
        
        CompletableFuture<Integer> combinedFuture = future1.thenCombine(future2, Integer::sum);
        
        try {
            System.out.println("Combined result: " + combinedFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        // ALL OF - Wait for all futures to complete
        System.out.println("\n4. Waiting for All Futures:");
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "Task 1");
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "Task 2");
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> "Task 3");
        
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(f1, f2, f3);
        
        try {
            allFutures.get(); // Wait for all to complete
            System.out.println("All futures completed:");
            System.out.println("F1: " + f1.get());
            System.out.println("F2: " + f2.get());
            System.out.println("F3: " + f3.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    /**
     * Person class for demonstration purposes
     */
    static class Person {
        private final String name;
        private final int age;
        private final String department;
        
        public Person(String name, int age, String department) {
            this.name = name;
            this.age = age;
            this.department = department;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getDepartment() { return department; }
        
        @Override
        public String toString() {
            return String.format("Person{name='%s', age=%d, department='%s'}", name, age, department);
        }
    }
    
    /**
     * Main method demonstrating all Stream API and functional programming concepts
     */
    public static void main(String[] args) {
        System.out.println("🚀 JAVA STREAM API AND FUNCTIONAL PROGRAMMING DEMO");
        System.out.println("=" .repeat(60));
        
        demonstrateLambdaExpressions();
        System.out.println("-".repeat(60));
        
        demonstrateFunctionalInterfaces();
        System.out.println("-".repeat(60));
        
        demonstrateStreamOperations();
        System.out.println("-".repeat(60));
        
        demonstrateCollectors();
        System.out.println("-".repeat(60));
        
        demonstrateParallelStreams();
        System.out.println("-".repeat(60));
        
        demonstrateCompletableFuture();
        
        System.out.println("✅ All Stream API and Functional Programming demonstrations completed!");
        System.out.println("\n📚 KEY TAKEAWAYS:");
        System.out.println("• Lambda expressions enable functional programming in Java");
        System.out.println("• Streams provide declarative data processing");
        System.out.println("• Functional interfaces are the foundation of lambda expressions");
        System.out.println("• Collectors provide powerful ways to accumulate results");
        System.out.println("• Parallel streams can improve performance for large datasets");
        System.out.println("• CompletableFuture enables non-blocking asynchronous programming");
        System.out.println("• Functional programming leads to more readable and maintainable code");
    }
}
