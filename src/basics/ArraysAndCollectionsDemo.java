package basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ArraysAndCollectionsDemo - Demonstrates Java arrays and basic collections
 * 
 * This class covers:
 * - Array declaration, initialization, and manipulation
 * - Multi-dimensional arrays
 * - ArrayList, LinkedList, HashMap, HashSet
 * - Basic collection operations
 * - Iteration techniques
 * - Common pitfalls and best practices
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class ArraysAndCollectionsDemo {
    
    /**
     * Demonstrates basic array operations
     */
    public static void demonstrateArrays() {
        System.out.println("=== BASIC ARRAYS ===");
        
        // Array declaration and initialization
        int[] numbers1 = new int[5];  // Creates array with default values (0)
        int[] numbers2 = {1, 2, 3, 4, 5};  // Array literal
        int[] numbers3 = new int[]{10, 20, 30, 40, 50};  // Explicit initialization
        
        // Array assignment
        numbers1[0] = 100;
        numbers1[1] = 200;
        numbers1[2] = 300;
        numbers1[3] = 400;
        numbers1[4] = 500;
        
        System.out.println("Array 1: " + Arrays.toString(numbers1));
        System.out.println("Array 2: " + Arrays.toString(numbers2));
        System.out.println("Array 3: " + Arrays.toString(numbers3));
        
        // Array properties and methods
        System.out.println("Array length: " + numbers2.length);
        
        // Array iteration
        System.out.print("Traditional for loop: ");
        for (int i = 0; i < numbers2.length; i++) {
            System.out.print(numbers2[i] + " ");
        }
        System.out.println();
        
        System.out.print("Enhanced for loop: ");
        for (int number : numbers2) {
            System.out.print(number + " ");
        }
        System.out.println();
        
        // String arrays
        String[] fruits = {"apple", "banana", "orange", "grape"};
        System.out.println("Fruits: " + Arrays.toString(fruits));
        
        // Array operations
        Arrays.sort(fruits);
        System.out.println("Sorted fruits: " + Arrays.toString(fruits));
        
        int index = Arrays.binarySearch(fruits, "orange");
        System.out.println("Index of 'orange': " + index);
        
        // Array copying
        int[] copiedArray = Arrays.copyOf(numbers2, numbers2.length);
        System.out.println("Copied array: " + Arrays.toString(copiedArray));
        
        int[] partialCopy = Arrays.copyOfRange(numbers2, 1, 4);
        System.out.println("Partial copy (index 1-3): " + Arrays.toString(partialCopy));
    }
    
    /**
     * Demonstrates multi-dimensional arrays
     */
    public static void demonstrateMultiDimensionalArrays() {
        System.out.println("\n=== MULTI-DIMENSIONAL ARRAYS ===");
        
        // 2D array declaration and initialization
        int[][] matrix1 = new int[3][4];  // 3 rows, 4 columns
        int[][] matrix2 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        
        // Filling 2D array
        int value = 1;
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                matrix1[i][j] = value++;
            }
        }
        
        // Printing 2D array
        System.out.println("Matrix 1:");
        for (int[] row : matrix1) {
            System.out.println(Arrays.toString(row));
        }
        
        System.out.println("Matrix 2:");
        for (int[] row : matrix2) {
            System.out.println(Arrays.toString(row));
        }
        
        // Jagged arrays (arrays with different row lengths)
        int[][] jaggedArray = {
            {1, 2},
            {3, 4, 5, 6},
            {7, 8, 9}
        };
        
        System.out.println("Jagged array:");
        for (int i = 0; i < jaggedArray.length; i++) {
            System.out.println("Row " + i + ": " + Arrays.toString(jaggedArray[i]));
        }
    }
    
    /**
     * Demonstrates ArrayList operations
     */
    public static void demonstrateArrayList() {
        System.out.println("\n=== ARRAYLIST ===");
        
        // ArrayList creation and initialization
        ArrayList<String> cities = new ArrayList<>();
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        
        // Adding elements
        cities.add("New York");
        cities.add("London");
        cities.add("Tokyo");
        cities.add("Paris");
        
        System.out.println("Cities: " + cities);
        System.out.println("Numbers: " + numbers);
        
        // Accessing elements
        System.out.println("First city: " + cities.get(0));
        System.out.println("Last city: " + cities.get(cities.size() - 1));
        
        // Modifying elements
        cities.set(1, "Los Angeles");
        System.out.println("After modification: " + cities);
        
        // Inserting at specific position
        cities.add(2, "Berlin");
        System.out.println("After insertion: " + cities);
        
        // Removing elements
        cities.remove("Tokyo");
        cities.remove(0);  // Remove by index
        System.out.println("After removal: " + cities);
        
        // Searching
        boolean containsParis = cities.contains("Paris");
        int indexOfBerlin = cities.indexOf("Berlin");
        System.out.println("Contains Paris: " + containsParis);
        System.out.println("Index of Berlin: " + indexOfBerlin);
        
        // Size and empty check
        System.out.println("Size: " + cities.size());
        System.out.println("Is empty: " + cities.isEmpty());
        
        // Iteration
        System.out.print("Using enhanced for loop: ");
        for (String city : cities) {
            System.out.print(city + " ");
        }
        System.out.println();
        
        // Using Iterator
        System.out.print("Using Iterator: ");
        Iterator<String> iterator = cities.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }
    
    /**
     * Demonstrates HashMap operations
     */
    public static void demonstrateHashMap() {
        System.out.println("\n=== HASHMAP ===");
        
        // HashMap creation and initialization
        HashMap<String, Integer> ageMap = new HashMap<>();
        HashMap<Integer, String> gradeMap = new HashMap<Integer, String>() {{
            put(90, "A");
            put(80, "B");
            put(70, "C");
            put(60, "D");
        }};
        
        // Adding key-value pairs
        ageMap.put("Alice", 25);
        ageMap.put("Bob", 30);
        ageMap.put("Charlie", 35);
        ageMap.put("Diana", 28);
        
        System.out.println("Age map: " + ageMap);
        System.out.println("Grade map: " + gradeMap);
        
        // Accessing values
        Integer aliceAge = ageMap.get("Alice");
        String gradeFor90 = gradeMap.get(90);
        System.out.println("Alice's age: " + aliceAge);
        System.out.println("Grade for 90: " + gradeFor90);
        
        // Checking existence
        boolean hasCharlie = ageMap.containsKey("Charlie");
        boolean hasAge25 = ageMap.containsValue(25);
        System.out.println("Has Charlie: " + hasCharlie);
        System.out.println("Has age 25: " + hasAge25);
        
        // Updating values
        ageMap.put("Alice", 26);  // Updates existing value
        System.out.println("After updating Alice's age: " + ageMap);
        
        // Using putIfAbsent
        ageMap.putIfAbsent("Eve", 22);
        ageMap.putIfAbsent("Alice", 99);  // Won't update since Alice exists
        System.out.println("After putIfAbsent: " + ageMap);
        
        // Removing entries
        ageMap.remove("Bob");
        System.out.println("After removing Bob: " + ageMap);
        
        // Iteration through HashMap
        System.out.println("Iterating through entries:");
        for (Map.Entry<String, Integer> entry : ageMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        
        System.out.println("All keys: " + ageMap.keySet());
        System.out.println("All values: " + ageMap.values());
    }
    
    /**
     * Demonstrates HashSet operations
     */
    public static void demonstrateHashSet() {
        System.out.println("\n=== HASHSET ===");
        
        // HashSet creation and initialization
        HashSet<String> colors = new HashSet<>();
        HashSet<Integer> numbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 3, 2, 1));
        
        // Adding elements
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("red");  // Duplicate - won't be added
        
        System.out.println("Colors: " + colors);
        System.out.println("Numbers (duplicates removed): " + numbers);
        
        // Checking existence
        boolean hasBlue = colors.contains("blue");
        System.out.println("Has blue: " + hasBlue);
        
        // Removing elements
        colors.remove("green");
        System.out.println("After removing green: " + colors);
        
        // Set operations
        HashSet<String> primaryColors = new HashSet<>(Arrays.asList("red", "blue", "yellow"));
        HashSet<String> warmColors = new HashSet<>(Arrays.asList("red", "orange", "yellow"));
        
        // Union (using addAll)
        HashSet<String> allColors = new HashSet<>(primaryColors);
        allColors.addAll(warmColors);
        System.out.println("Union of primary and warm colors: " + allColors);
        
        // Intersection (using retainAll)
        HashSet<String> intersection = new HashSet<>(primaryColors);
        intersection.retainAll(warmColors);
        System.out.println("Intersection of primary and warm colors: " + intersection);
        
        // Difference (using removeAll)
        HashSet<String> difference = new HashSet<>(primaryColors);
        difference.removeAll(warmColors);
        System.out.println("Primary colors not in warm colors: " + difference);
        
        // Size and empty check
        System.out.println("Size: " + colors.size());
        System.out.println("Is empty: " + colors.isEmpty());
    }
    
    /**
     * Demonstrates LinkedList operations
     */
    public static void demonstrateLinkedList() {
        System.out.println("\n=== LINKEDLIST ===");
        
        LinkedList<String> queue = new LinkedList<>();
        
        // Adding elements (queue operations)
        queue.addFirst("First");
        queue.addLast("Last");
        queue.add("Middle");
        queue.offer("Offered");  // Same as addLast
        
        System.out.println("LinkedList: " + queue);
        
        // Accessing elements
        System.out.println("First element: " + queue.getFirst());
        System.out.println("Last element: " + queue.getLast());
        System.out.println("Peek first: " + queue.peekFirst());
        
        // Removing elements (queue operations)
        String removed = queue.removeFirst();
        System.out.println("Removed first: " + removed);
        System.out.println("After removal: " + queue);
        
        String polled = queue.poll();  // Same as removeFirst
        System.out.println("Polled: " + polled);
        System.out.println("After polling: " + queue);
        
        // Using as stack (LIFO)
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        System.out.println("Stack: " + stack);
        System.out.println("Pop: " + stack.pop());
        System.out.println("Stack after pop: " + stack);
    }
    
    /**
     * Demonstrates common collection operations and utilities
     */
    public static void demonstrateCollectionUtilities() {
        System.out.println("\n=== COLLECTION UTILITIES ===");
        
        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9, 3));
        System.out.println("Original list: " + numbers);
        
        // Sorting
        Collections.sort(numbers);
        System.out.println("Sorted list: " + numbers);
        
        // Reverse
        Collections.reverse(numbers);
        System.out.println("Reversed list: " + numbers);
        
        // Shuffle
        Collections.shuffle(numbers);
        System.out.println("Shuffled list: " + numbers);
        
        // Min and Max
        System.out.println("Min: " + Collections.min(numbers));
        System.out.println("Max: " + Collections.max(numbers));
        
        // Fill
        List<String> words = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        Collections.fill(words, "filled");
        System.out.println("Filled list: " + words);
        
        // Frequency
        List<String> letters = Arrays.asList("a", "b", "a", "c", "a", "b");
        int frequency = Collections.frequency(letters, "a");
        System.out.println("Frequency of 'a': " + frequency);
    }
    
    /**
     * Main method demonstrating all array and collection concepts
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Arrays and Collections Demonstration");
        System.out.println("========================================");
        
        demonstrateArrays();
        demonstrateMultiDimensionalArrays();
        demonstrateArrayList();
        demonstrateHashMap();
        demonstrateHashSet();
        demonstrateLinkedList();
        demonstrateCollectionUtilities();
        
        System.out.println("\n=== COMPARISON: ARRAYS VS COLLECTIONS ===");
        System.out.println("Arrays:");
        System.out.println("  + Fixed size, memory efficient");
        System.out.println("  + Direct element access by index");
        System.out.println("  + Can store primitives directly");
        System.out.println("  - Size cannot be changed after creation");
        System.out.println("  - Limited built-in methods");
        
        System.out.println("\nCollections:");
        System.out.println("  + Dynamic size");
        System.out.println("  + Rich set of methods for manipulation");
        System.out.println("  + Type safety with generics");
        System.out.println("  - Only store objects (primitives are auto-boxed)");
        System.out.println("  - Slight memory overhead");
        
        System.out.println("\n=== WHEN TO USE WHAT ===");
        System.out.println("Use Arrays when:");
        System.out.println("  - Size is known and fixed");
        System.out.println("  - Performance is critical");
        System.out.println("  - Working with primitives heavily");
        
        System.out.println("\nUse Collections when:");
        System.out.println("  - Size may change during runtime");
        System.out.println("  - Need rich manipulation methods");
        System.out.println("  - Working with objects");
        System.out.println("  - Need specific data structure behavior (Set, Map, Queue)");
    }
}
