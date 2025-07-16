package algorithms;

import java.util.Arrays;
import java.util.Random;

/**
 * SortingAlgorithmsDemo - Demonstrates various sorting algorithms with detailed analysis
 * 
 * WHY SORTING MATTERS:
 * Sorting is one of the most fundamental algorithms in computer science because:
 * - It enables efficient searching (binary search requires sorted data)
 * - It's a building block for many other algorithms
 * - It demonstrates key algorithmic concepts like divide-and-conquer
 * - Real-world applications: databases, search engines, data analysis
 * - Interview preparation: commonly asked in technical interviews
 * 
 * ALGORITHM SELECTION CRITERIA:
 * - Data size: Small vs large datasets require different approaches
 * - Data characteristics: Nearly sorted, random, or reverse sorted
 * - Memory constraints: In-place vs external memory requirements
 * - Stability requirements: Preserve relative order of equal elements
 * - Performance requirements: Best, average, or worst-case guarantees
 * 
 * This class implements and compares:
 * - Bubble Sort (O(n²) - educational, simple but inefficient)
 * - Selection Sort (O(n²) - minimal swaps, good for limited memory)
 * - Insertion Sort (O(n²) - excellent for small/nearly sorted arrays)
 * - Merge Sort (O(n log n) - stable, consistent performance, requires extra memory)
 * - Quick Sort (O(n log n) average - fast in practice, in-place, unstable)
 * 
 * LEARNING OBJECTIVES:
 * 1. Understand different sorting strategies and their trade-offs
 * 2. Analyze time and space complexity in practical scenarios
 * 3. Learn when to choose each algorithm based on requirements
 * 4. Observe performance characteristics with different data patterns
 * 5. Master algorithmic analysis and optimization techniques
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class SortingAlgorithmsDemo {
    
    // Global counters to track algorithm performance metrics
    // These help us understand the actual work done by each algorithm
    private static int comparisons = 0;  // Number of element comparisons
    private static int swaps = 0;        // Number of element swaps/moves
    
    /**
     * Bubble Sort implementation - The simplest sorting algorithm to understand
     * 
     * ALGORITHM STRATEGY:
     * Like bubbles rising to the surface, larger elements "bubble up" to their correct positions.
     * Each pass through the array moves the largest unsorted element to its final position.
     * 
     * HOW IT WORKS:
     * 1. Compare adjacent elements starting from the beginning
     * 2. If left element > right element, swap them
     * 3. Continue until the end of array (largest element is now in correct position)
     * 4. Repeat for remaining unsorted portion (array length - 1, then - 2, etc.)
     * 5. Stop early if no swaps occur in a pass (array is sorted)
     * 
     * WHY THIS IMPLEMENTATION:
     * - Uses optimized version with early termination (swapped flag)
     * - Clones input array to avoid modifying original data
     * - Tracks comparisons and swaps for performance analysis
     * 
     * WHEN TO USE:
     * - Educational purposes to understand sorting concepts
     * - Very small datasets (< 10 elements)
     * - When simplicity is more important than efficiency
     * - Never use for production code with large datasets!
     * 
     * Time Complexity: O(n²) worst/average case, O(n) best case (already sorted)
     * Space Complexity: O(1) - sorts in place (plus O(n) for cloning input)
     * Stability: Stable (maintains relative order of equal elements)
     * 
     * @param arr array to sort (original array is not modified)
     * @return new sorted array
     */
    public static int[] bubbleSort(int[] arr) {
        int[] array = arr.clone();  // Defensive copying - don't modify original
        int n = array.length;
        boolean swapped;            // Optimization: detect if array becomes sorted early
        
        // Outer loop: each iteration places one element in its final position
        for (int i = 0; i < n - 1; i++) {
            swapped = false;  // Assume no swaps needed this pass
            
            // Inner loop: compare adjacent elements in unsorted portion
            // Note: (n - i - 1) because last i elements are already in place
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;  // Track for performance analysis
                
                // Core comparison: if elements are out of order, swap them
                if (array[j] > array[j + 1]) {
                    // Manual swap (could use utility method, but this shows the work)
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swaps++;      // Track for performance analysis
                    swapped = true;  // Mark that we made a change
                }
            }
            
            // OPTIMIZATION: If no swapping occurred, array is already sorted
            // This makes best-case time complexity O(n) instead of O(n²)
            if (!swapped) break;
        }
        
        return array;
    }
    
    /**
     * Selection Sort implementation - Minimizes the number of swaps
     * 
     * ALGORITHM STRATEGY:
     * "Select" the smallest element from the unsorted portion and place it at the beginning.
     * This creates a sorted portion that grows from left to right.
     * 
     * HOW IT WORKS:
     * 1. Find the minimum element in the entire array
     * 2. Swap it with the first element (position 0)
     * 3. Find the minimum element in the remaining array (positions 1 to n-1)
     * 4. Swap it with the second element (position 1)
     * 5. Repeat until the entire array is sorted
     * 
     * KEY CHARACTERISTICS:
     * - Always performs exactly n-1 swaps (where n is array length)
     * - Number of comparisons is always the same regardless of input
     * - Performance doesn't improve for nearly sorted arrays
     * - Useful when memory writes are expensive (minimizes swaps)
     * 
     * WHY THIS IMPLEMENTATION:
     * - Tracks minimum index instead of minimum value for efficiency
     * - Only swaps when necessary (minIndex != i)
     * - Provides consistent, predictable performance
     * 
     * WHEN TO USE:
     * - When minimizing swaps is important (e.g., swapping large objects)
     * - When memory writes are expensive
     * - Small datasets where simplicity matters
     * - When you need predictable performance regardless of input
     * 
     * Time Complexity: O(n²) in all cases (best, average, worst)
     * Space Complexity: O(1) - sorts in place
     * Stability: Unstable (can change relative order of equal elements)
     * 
     * @param arr array to sort (original array is not modified)
     * @return new sorted array
     */
    public static int[] selectionSort(int[] arr) {
        int[] array = arr.clone();  // Defensive copying
        int n = array.length;
        
        // Outer loop: each iteration finds the next smallest element
        for (int i = 0; i < n - 1; i++) {
            // Find minimum element in remaining unsorted array
            int minIndex = i;  // Assume current position has minimum
            
            // Inner loop: search for actual minimum in unsorted portion
            for (int j = i + 1; j < n; j++) {
                comparisons++;  // Track for performance analysis
                
                // If we find a smaller element, remember its position
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            
            // OPTIMIZATION: Only swap if we found a smaller element
            // This avoids unnecessary swaps when element is already in correct position
            if (minIndex != i) {
                // Swap minimum element with current position
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
                swaps++;  // Track for performance analysis
            }
        }
        
        return array;
    }
    
    /**
     * Insertion Sort implementation - Excellent for small and nearly sorted arrays
     * 
     * ALGORITHM STRATEGY:
     * Like sorting playing cards in your hand - take one card at a time and insert it
     * into the correct position among the already sorted cards.
     * 
     * HOW IT WORKS:
     * 1. Start with the second element (index 1) - first element is trivially sorted
     * 2. Compare this element with elements to its left
     * 3. Shift larger elements one position to the right
     * 4. Insert the current element in the correct position
     * 5. Repeat for each remaining element
     * 
     * KEY CHARACTERISTICS:
     * - Adaptive: performs better on nearly sorted arrays
     * - Online: can sort data as it arrives
     * - Stable: maintains relative order of equal elements
     * - In-place: requires only O(1) extra memory
     * - Efficient for small datasets (often used as a subroutine in hybrid algorithms)
     * 
     * WHY THIS IMPLEMENTATION:
     * - Uses while loop with early break for efficiency
     * - Tracks comparisons accurately (including failed comparisons)
     * - Minimizes unnecessary operations
     * 
     * REAL-WORLD USAGE:
     * - Often used in hybrid algorithms (e.g., Timsort uses insertion sort for small subarrays)
     * - Good for sorting small collections in practice
     * - Used in scenarios where data arrives incrementally
     * 
     * WHEN TO USE:
     * - Small datasets (typically < 50 elements)
     * - Nearly sorted data (best case: O(n) time)
     * - As a subroutine in more complex algorithms
     * - When stability is required and dataset is small
     * 
     * Time Complexity: O(n²) worst/average case, O(n) best case (already sorted)
     * Space Complexity: O(1) - sorts in place
     * Stability: Stable (maintains relative order of equal elements)
     * 
     * @param arr array to sort (original array is not modified)
     * @return new sorted array
     */
    public static int[] insertionSort(int[] arr) {
        int[] array = arr.clone();  // Defensive copying
        int n = array.length;
        
        // Start from second element (index 1) since first element is trivially sorted
        for (int i = 1; i < n; i++) {
            int key = array[i];  // Element to be inserted into sorted portion
            int j = i - 1;       // Start comparing with element to the left
            
            // Move elements of array[0..i-1] that are greater than key
            // one position ahead of their current position
            while (j >= 0) {
                comparisons++;  // Track for performance analysis
                
                if (array[j] > key) {
                    // Shift element to the right to make space
                    array[j + 1] = array[j];
                    j--;  // Move to next element to the left
                    swaps++;  // Count this as a move operation
                } else {
                    // Found the correct position - no more shifting needed
                    break;
                }
            }
            
            // Insert the key at its correct position
            // j+1 is the correct position because:
            // - If we exited loop due to j < 0, then key belongs at position 0
            // - If we exited due to array[j] <= key, then key belongs after position j
            array[j + 1] = key;
        }
        
        return array;
    }
    
    /**
     * Merge Sort implementation - The gold standard for stable, guaranteed O(n log n) sorting
     * 
     * ALGORITHM STRATEGY:
     * "Divide and Conquer" - break the problem into smaller subproblems, solve them,
     * then combine the solutions. This is a classic example of recursive thinking.
     * 
     * HOW IT WORKS:
     * 1. DIVIDE: Split the array into two halves
     * 2. CONQUER: Recursively sort both halves
     * 3. COMBINE: Merge the two sorted halves into a single sorted array
     * 
     * WHY IT'S EFFICIENT:
     * - Dividing creates log n levels (binary tree structure)
     * - Merging at each level takes O(n) time
     * - Total time: O(n) × O(log n) = O(n log n)
     * 
     * KEY CHARACTERISTICS:
     * - Stable: maintains relative order of equal elements
     * - Consistent: same performance regardless of input distribution
     * - Predictable: guaranteed O(n log n) in all cases
     * - External: works well with data that doesn't fit in memory
     * 
     * TRADE-OFFS:
     * - Requires O(n) extra space for temporary arrays
     * - Slightly slower than quicksort in practice due to memory allocation
     * - More complex to implement than simple quadratic algorithms
     * 
     * WHEN TO USE:
     * - When stability is required (equal elements maintain their relative order)
     * - When guaranteed O(n log n) performance is needed
     * - Large datasets where consistency matters more than average-case speed
     * - External sorting (sorting data larger than available memory)
     * - When working with linked lists (can be done in O(1) space)
     * 
     * Time Complexity: O(n log n) in all cases (best, average, worst)
     * Space Complexity: O(n) for temporary arrays
     * Stability: Stable (maintains relative order of equal elements)
     * 
     * @param arr array to sort (original array is not modified)
     * @return new sorted array
     */
    public static int[] mergeSort(int[] arr) {
        int[] array = arr.clone();  // Defensive copying
        // Kick off the recursive sorting process
        mergeSortRecursive(array, 0, array.length - 1);
        return array;
    }
    
    /**
     * Recursive helper method for merge sort - implements the divide and conquer strategy
     * 
     * RECURSION EXPLANATION:
     * This method calls itself with smaller and smaller subarrays until it reaches
     * the base case (left >= right), then builds the solution back up.
     * 
     * DIVIDE STRATEGY:
     * - Split array roughly in half using midpoint
     * - Recursively sort left half: [left...mid]
     * - Recursively sort right half: [mid+1...right]
     * - Merge the two sorted halves
     * 
     * @param array the array being sorted (modified in place)
     * @param left starting index of the subarray to sort
     * @param right ending index of the subarray to sort
     */
    private static void mergeSortRecursive(int[] array, int left, int right) {
        // BASE CASE: if left >= right, subarray has 0 or 1 element (already sorted)
        if (left < right) {
            // DIVIDE: Calculate midpoint carefully to avoid integer overflow
            // Using left + (right - left) / 2 instead of (left + right) / 2
            // This prevents overflow when left and right are large
            int mid = left + (right - left) / 2;
            
            // CONQUER: Recursively sort both halves
            mergeSortRecursive(array, left, mid);        // Sort left half
            mergeSortRecursive(array, mid + 1, right);   // Sort right half
            
            // COMBINE: Merge the two sorted halves
            merge(array, left, mid, right);
        }
    }
    
    /**
     * Merge two sorted subarrays into a single sorted array
     * 
     * MERGING STRATEGY:
     * Like merging two sorted piles of cards - always take the smaller card
     * from the top of either pile until one pile is empty, then take all
     * remaining cards from the other pile.
     * 
     * IMPLEMENTATION DETAILS:
     * - Create temporary arrays for left and right subarrays
     * - Use three pointers: i (left array), j (right array), k (result array)
     * - Compare elements and copy smaller one to result
     * - Handle remaining elements when one array is exhausted
     * 
     * @param array the main array containing both subarrays
     * @param left starting index of left subarray
     * @param mid ending index of left subarray (starting index of right subarray is mid + 1)
     * @param right ending index of right subarray
     */
    private static void merge(int[] array, int left, int mid, int right) {
        // Create temporary arrays for left and right subarrays
        // Left subarray: array[left...mid]
        // Right subarray: array[mid+1...right]
        int[] leftArray = new int[mid - left + 1];
        int[] rightArray = new int[right - mid];
        
        // Copy data to temporary arrays using System.arraycopy for efficiency
        // This is faster than manual loops for array copying
        System.arraycopy(array, left, leftArray, 0, leftArray.length);
        System.arraycopy(array, mid + 1, rightArray, 0, rightArray.length);
        
        // Merge the temporary arrays back into array[left..right]
        int i = 0;       // Pointer for leftArray
        int j = 0;       // Pointer for rightArray
        int k = left;    // Pointer for merged array (starting at left index)
        
        // CORE MERGING LOGIC: Compare elements and copy smaller one
        while (i < leftArray.length && j < rightArray.length) {
            comparisons++;  // Track for performance analysis
            
            // Use <= to maintain stability (equal elements from left array come first)
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;  // Move to next element in left array
            } else {
                array[k] = rightArray[j];
                j++;  // Move to next element in right array
            }
            k++;  // Move to next position in result array
        }
        
        // Copy any remaining elements from left array
        // This happens when right array is exhausted first
        while (i < leftArray.length) {
            array[k] = leftArray[i];
            i++;
            k++;
        }
        
        // Copy any remaining elements from right array
        // This happens when left array is exhausted first
        while (j < rightArray.length) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
    
    /**
     * Quick Sort implementation
     * Time Complexity: O(n log n) average case, O(n²) worst case
     * Space Complexity: O(log n) average case
     * 
     * @param arr array to sort
     * @return sorted array
     */
    public static int[] quickSort(int[] arr) {
        int[] array = arr.clone();
        quickSortRecursive(array, 0, array.length - 1);
        return array;
    }
    
    /**
     * Recursive helper method for quick sort
     */
    private static void quickSortRecursive(int[] array, int low, int high) {
        if (low < high) {
            // Partition the array and get pivot index
            int pivotIndex = partition(array, low, high);
            
            // Recursively sort elements before and after partition
            quickSortRecursive(array, low, pivotIndex - 1);
            quickSortRecursive(array, pivotIndex + 1, high);
        }
    }
    
    /**
     * Partition method for quick sort
     */
    private static int partition(int[] array, int low, int high) {
        // Choose rightmost element as pivot
        int pivot = array[high];
        int i = low - 1; // Index of smaller element
        
        for (int j = low; j < high; j++) {
            comparisons++;
            // If current element is smaller than or equal to pivot
            if (array[j] <= pivot) {
                i++;
                // Swap elements
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                swaps++;
            }
        }
        
        // Swap pivot with element at i+1
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        swaps++;
        
        return i + 1;
    }
    
    /**
     * Demonstrates sorting algorithms with different data sets
     */
    public static void demonstrateSortingPerformance() {
        System.out.println("\n=== SORTING PERFORMANCE COMPARISON ===");
        
        // Test different scenarios
        int[][] testArrays = {
            {5, 2, 8, 1, 9, 3},           // Random small array
            {1, 2, 3, 4, 5, 6},           // Already sorted
            {6, 5, 4, 3, 2, 1},           // Reverse sorted
            generateRandomArray(10),       // Random medium array
            {3, 3, 3, 3, 3, 3}            // All duplicates
        };
        
        String[] scenarios = {
            "Random Small Array",
            "Already Sorted",
            "Reverse Sorted", 
            "Random Medium Array",
            "All Duplicates"
        };
        
        String[] algorithms = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort"};
        
        for (int i = 0; i < testArrays.length; i++) {
            System.out.println("\n--- " + scenarios[i] + " ---");
            System.out.println("Original: " + Arrays.toString(testArrays[i]));
            
            // Test each algorithm
            for (int j = 0; j < algorithms.length; j++) {
                resetCounters();
                long startTime = System.nanoTime();
                
                int[] result = null;
                switch (j) {
                    case 0: result = bubbleSort(testArrays[i]); break;
                    case 1: result = selectionSort(testArrays[i]); break;
                    case 2: result = insertionSort(testArrays[i]); break;
                    case 3: result = mergeSort(testArrays[i]); break;
                    case 4: result = quickSort(testArrays[i]); break;
                }
                
                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                
                System.out.printf("%-15s: %s | Comparisons: %d, Swaps: %d, Time: %d ns\n",
                    algorithms[j], Arrays.toString(result), comparisons, swaps, duration);
            }
        }
    }
    
    /**
     * Generates a random array for testing
     */
    private static int[] generateRandomArray(int size) {
        Random random = new Random(42); // Fixed seed for reproducible results
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }
    
    /**
     * Resets performance counters
     */
    private static void resetCounters() {
        comparisons = 0;
        swaps = 0;
    }
    
    /**
     * Verifies if an array is sorted
     */
    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Main method demonstrating sorting algorithms
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Sorting Algorithms Demonstration");
        System.out.println("===============================");
        
        // Sample array for demonstration
        int[] originalArray = {64, 34, 25, 12, 22, 11, 90, 88, 76, 50, 42};
        System.out.println("Original Array: " + Arrays.toString(originalArray));
        System.out.println("Array Length: " + originalArray.length);
        
        // Demonstrate each sorting algorithm
        System.out.println("\n=== SORTING ALGORITHM DEMONSTRATIONS ===");
        
        // Bubble Sort
        System.out.println("\n1. BUBBLE SORT");
        System.out.println("How it works: Repeatedly steps through the list, compares adjacent elements and swaps them if they're in wrong order");
        resetCounters();
        int[] bubbleSorted = bubbleSort(originalArray);
        System.out.println("Result: " + Arrays.toString(bubbleSorted));
        System.out.println("Comparisons: " + comparisons + ", Swaps: " + swaps);
        System.out.println("Is sorted: " + isSorted(bubbleSorted));
        
        // Selection Sort
        System.out.println("\n2. SELECTION SORT");
        System.out.println("How it works: Finds the minimum element and places it at the beginning, then repeats for the rest");
        resetCounters();
        int[] selectionSorted = selectionSort(originalArray);
        System.out.println("Result: " + Arrays.toString(selectionSorted));
        System.out.println("Comparisons: " + comparisons + ", Swaps: " + swaps);
        System.out.println("Is sorted: " + isSorted(selectionSorted));
        
        // Insertion Sort
        System.out.println("\n3. INSERTION SORT");
        System.out.println("How it works: Builds the final sorted array one item at a time, inserting each element in its correct position");
        resetCounters();
        int[] insertionSorted = insertionSort(originalArray);
        System.out.println("Result: " + Arrays.toString(insertionSorted));
        System.out.println("Comparisons: " + comparisons + ", Swaps: " + swaps);
        System.out.println("Is sorted: " + isSorted(insertionSorted));
        
        // Merge Sort
        System.out.println("\n4. MERGE SORT");
        System.out.println("How it works: Divides array into halves, sorts them separately, then merges the sorted halves");
        resetCounters();
        int[] mergeSorted = mergeSort(originalArray);
        System.out.println("Result: " + Arrays.toString(mergeSorted));
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Is sorted: " + isSorted(mergeSorted));
        
        // Quick Sort
        System.out.println("\n5. QUICK SORT");
        System.out.println("How it works: Picks a pivot element and partitions array around it, then recursively sorts partitions");
        resetCounters();
        int[] quickSorted = quickSort(originalArray);
        System.out.println("Result: " + Arrays.toString(quickSorted));
        System.out.println("Comparisons: " + comparisons + ", Swaps: " + swaps);
        System.out.println("Is sorted: " + isSorted(quickSorted));
        
        // Performance comparison
        demonstrateSortingPerformance();
        
        // Algorithm complexity summary
        System.out.println("\n=== TIME COMPLEXITY SUMMARY ===");
        System.out.println("Algorithm     | Best Case | Average Case | Worst Case | Space");
        System.out.println("------------- | --------- | ------------ | ---------- | -----");
        System.out.println("Bubble Sort   | O(n)      | O(n²)        | O(n²)      | O(1)");
        System.out.println("Selection Sort| O(n²)     | O(n²)        | O(n²)      | O(1)");
        System.out.println("Insertion Sort| O(n)      | O(n²)        | O(n²)      | O(1)");
        System.out.println("Merge Sort    | O(n log n)| O(n log n)   | O(n log n) | O(n)");
        System.out.println("Quick Sort    | O(n log n)| O(n log n)   | O(n²)      | O(log n)");
        
        System.out.println("\n=== WHEN TO USE EACH ALGORITHM ===");
        System.out.println("Bubble Sort:    Educational purposes, very small datasets");
        System.out.println("Selection Sort: Small datasets, memory is limited");
        System.out.println("Insertion Sort: Small datasets, nearly sorted data, online sorting");
        System.out.println("Merge Sort:     Large datasets, stable sorting required, consistent performance");
        System.out.println("Quick Sort:     Large datasets, average case performance, in-place sorting");
        
        System.out.println("\n=== STABILITY ===");
        System.out.println("Stable sorts maintain relative order of equal elements:");
        System.out.println("Stable:   Bubble Sort, Insertion Sort, Merge Sort");
        System.out.println("Unstable: Selection Sort, Quick Sort (standard implementation)");
    }
}
