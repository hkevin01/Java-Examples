package algorithms;

import java.util.Arrays;
import java.util.Random;

/**
 * SortingAlgorithmsDemo - Demonstrates various sorting algorithms
 * 
 * This class implements and compares:
 * - Bubble Sort
 * - Selection Sort
 * - Insertion Sort
 * - Merge Sort
 * - Quick Sort
 * - Performance analysis and comparison
 * - Best/worst case scenarios
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class SortingAlgorithmsDemo {
    
    private static int comparisons = 0;
    private static int swaps = 0;
    
    /**
     * Bubble Sort implementation
     * Time Complexity: O(n²) worst/average case, O(n) best case
     * Space Complexity: O(1)
     * 
     * @param arr array to sort
     * @return sorted array
     */
    public static int[] bubbleSort(int[] arr) {
        int[] array = arr.clone();
        int n = array.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (array[j] > array[j + 1]) {
                    // Swap elements
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }
            // If no swapping occurred, array is sorted
            if (!swapped) break;
        }
        
        return array;
    }
    
    /**
     * Selection Sort implementation
     * Time Complexity: O(n²) in all cases
     * Space Complexity: O(1)
     * 
     * @param arr array to sort
     * @return sorted array
     */
    public static int[] selectionSort(int[] arr) {
        int[] array = arr.clone();
        int n = array.length;
        
        for (int i = 0; i < n - 1; i++) {
            // Find minimum element in remaining unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Swap minimum element with first element
            if (minIndex != i) {
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
                swaps++;
            }
        }
        
        return array;
    }
    
    /**
     * Insertion Sort implementation
     * Time Complexity: O(n²) worst/average case, O(n) best case
     * Space Complexity: O(1)
     * 
     * @param arr array to sort
     * @return sorted array
     */
    public static int[] insertionSort(int[] arr) {
        int[] array = arr.clone();
        int n = array.length;
        
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;
            
            // Move elements greater than key one position ahead
            while (j >= 0) {
                comparisons++;
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                    swaps++;
                } else {
                    break;
                }
            }
            array[j + 1] = key;
        }
        
        return array;
    }
    
    /**
     * Merge Sort implementation
     * Time Complexity: O(n log n) in all cases
     * Space Complexity: O(n)
     * 
     * @param arr array to sort
     * @return sorted array
     */
    public static int[] mergeSort(int[] arr) {
        int[] array = arr.clone();
        mergeSortRecursive(array, 0, array.length - 1);
        return array;
    }
    
    /**
     * Recursive helper method for merge sort
     */
    private static void mergeSortRecursive(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            // Sort first and second halves
            mergeSortRecursive(array, left, mid);
            mergeSortRecursive(array, mid + 1, right);
            
            // Merge the sorted halves
            merge(array, left, mid, right);
        }
    }
    
    /**
     * Merge two sorted subarrays
     */
    private static void merge(int[] array, int left, int mid, int right) {
        // Create temporary arrays for left and right subarrays
        int[] leftArray = new int[mid - left + 1];
        int[] rightArray = new int[right - mid];
        
        // Copy data to temporary arrays
        System.arraycopy(array, left, leftArray, 0, leftArray.length);
        System.arraycopy(array, mid + 1, rightArray, 0, rightArray.length);
        
        // Merge the temporary arrays back into array[left..right]
        int i = 0, j = 0, k = left;
        
        while (i < leftArray.length && j < rightArray.length) {
            comparisons++;
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        // Copy remaining elements
        while (i < leftArray.length) {
            array[k] = leftArray[i];
            i++;
            k++;
        }
        
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
