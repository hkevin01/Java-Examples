package datastructures;

/**
 * QueueImplementation - Custom implementation of a Queue data structure
 * 
 * This class demonstrates:
 * - Queue implementation using circular array
 * - FIFO (First In, First Out) principle
 * - Queue operations: enqueue, dequeue, front, rear, isEmpty, size
 * - Circular buffer implementation for efficient space usage
 * - Error handling for queue operations
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class QueueImplementation<T> {
    
    private T[] queueArray;
    private int front;
    private int rear;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;
    
    /**
     * Default constructor with default capacity
     */
    @SuppressWarnings("unchecked")
    public QueueImplementation() {
        this.capacity = DEFAULT_CAPACITY;
        this.queueArray = (T[]) new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }
    
    /**
     * Constructor with specified capacity
     * @param capacity initial capacity of the queue
     */
    @SuppressWarnings("unchecked")
    public QueueImplementation(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.queueArray = (T[]) new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }
    
    /**
     * Adds an element to the rear of the queue
     * @param element the element to add
     * @throws RuntimeException if queue is full
     */
    public void enqueue(T element) {
        if (isFull()) {
            throw new RuntimeException("Queue is full - cannot enqueue");
        }
        rear = (rear + 1) % capacity; // Circular increment
        queueArray[rear] = element;
        size++;
    }
    
    /**
     * Removes and returns the front element from the queue
     * @return the front element
     * @throws RuntimeException if queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot dequeue");
        }
        T element = queueArray[front];
        queueArray[front] = null; // Help GC
        front = (front + 1) % capacity; // Circular increment
        size--;
        return element;
    }
    
    /**
     * Returns the front element without removing it
     * @return the front element
     * @throws RuntimeException if queue is empty
     */
    public T front() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot get front");
        }
        return queueArray[front];
    }
    
    /**
     * Returns the rear element without removing it
     * @return the rear element
     * @throws RuntimeException if queue is empty
     */
    public T rear() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty - cannot get rear");
        }
        return queueArray[rear];
    }
    
    /**
     * Checks if the queue is empty
     * @return true if queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Checks if the queue is full
     * @return true if queue is full, false otherwise
     */
    public boolean isFull() {
        return size == capacity;
    }
    
    /**
     * Returns the number of elements in the queue
     * @return size of the queue
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns the capacity of the queue
     * @return capacity of the queue
     */
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * Clears all elements from the queue
     */
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            queueArray[i] = null;
        }
        front = 0;
        rear = -1;
        size = 0;
    }
    
    /**
     * Returns string representation of the queue
     * @return string representation
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Queue: [] (front -> rear)";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Queue: [");
        
        int current = front;
        for (int i = 0; i < size; i++) {
            sb.append(queueArray[current]);
            if (i < size - 1) {
                sb.append(", ");
            }
            current = (current + 1) % capacity;
        }
        
        sb.append("] (front -> rear)");
        return sb.toString();
    }
    
    /**
     * Returns detailed state information for debugging
     * @return detailed state string
     */
    public String getDetailedState() {
        return String.format("Queue State - Size: %d, Capacity: %d, Front: %d, Rear: %d", 
                           size, capacity, front, rear);
    }
    
    /**
     * Demonstrates practical applications of queue
     */
    public static void demonstrateQueueApplications() {
        System.out.println("\n=== QUEUE APPLICATIONS ===");
        
        // 1. Print queue simulation
        System.out.println("1. Print Queue Simulation:");
        simulatePrintQueue();
        
        // 2. Breadth-First Search simulation
        System.out.println("\n2. Breadth-First Search Simulation:");
        simulateBFS();
        
        // 3. Level order traversal simulation
        System.out.println("\n3. Level Order Traversal Simulation:");
        simulateLevelOrder();
    }
    
    /**
     * Simulates a print queue where documents are processed in order
     */
    public static void simulatePrintQueue() {
        QueueImplementation<String> printQueue = new QueueImplementation<>(5);
        
        // Add print jobs
        String[] documents = {"Report.pdf", "Invoice.doc", "Photo.jpg", "Presentation.ppt"};
        
        System.out.println("Adding documents to print queue:");
        for (String doc : documents) {
            printQueue.enqueue(doc);
            System.out.println("Added: " + doc + " | " + printQueue);
        }
        
        System.out.println("\nProcessing print queue:");
        while (!printQueue.isEmpty()) {
            String processing = printQueue.dequeue();
            System.out.println("Printing: " + processing + " | Remaining: " + printQueue);
            
            // Simulate printing time
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("All documents printed!");
    }
    
    /**
     * Simulates breadth-first search using queue
     */
    public static void simulateBFS() {
        QueueImplementation<String> bfsQueue = new QueueImplementation<>();
        
        // Simulate BFS on a simple graph: A -> B,C; B -> D,E; C -> F
        System.out.println("BFS traversal starting from node A:");
        
        bfsQueue.enqueue("A");
        System.out.println("Start with: " + bfsQueue);
        
        // Simulate BFS steps
        String[][] adjacencies = {
            {"A", "B", "C"},     // A connects to B and C
            {"B", "D", "E"},     // B connects to D and E
            {"C", "F"}           // C connects to F
        };
        
        int step = 1;
        while (!bfsQueue.isEmpty()) {
            String current = bfsQueue.dequeue();
            System.out.println("Step " + step + ": Visit " + current + " | Queue: " + bfsQueue);
            
            // Add neighbors (simplified simulation)
            for (String[] adj : adjacencies) {
                if (adj[0].equals(current)) {
                    for (int i = 1; i < adj.length; i++) {
                        bfsQueue.enqueue(adj[i]);
                        System.out.println("  -> Added neighbor " + adj[i] + " | Queue: " + bfsQueue);
                    }
                    break;
                }
            }
            step++;
        }
    }
    
    /**
     * Simulates level order traversal of a binary tree
     */
    public static void simulateLevelOrder() {
        QueueImplementation<String> levelQueue = new QueueImplementation<>();
        
        // Simulate tree: Level 0: A; Level 1: B,C; Level 2: D,E,F,G
        System.out.println("Level order traversal of binary tree:");
        System.out.println("Tree structure:");
        System.out.println("       A");
        System.out.println("      / \\");
        System.out.println("     B   C");
        System.out.println("    / \\ / \\");
        System.out.println("   D  E F  G");
        
        levelQueue.enqueue("A");
        int level = 0;
        
        while (!levelQueue.isEmpty()) {
            int levelSize = levelQueue.size();
            System.out.print("Level " + level + ": ");
            
            for (int i = 0; i < levelSize; i++) {
                String node = levelQueue.dequeue();
                System.out.print(node + " ");
                
                // Add children based on node
                switch (node) {
                    case "A":
                        levelQueue.enqueue("B");
                        levelQueue.enqueue("C");
                        break;
                    case "B":
                        levelQueue.enqueue("D");
                        levelQueue.enqueue("E");
                        break;
                    case "C":
                        levelQueue.enqueue("F");
                        levelQueue.enqueue("G");
                        break;
                }
            }
            System.out.println("| Remaining queue: " + levelQueue);
            level++;
        }
    }
    
    /**
     * Main method demonstrating queue implementation and applications
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Queue Implementation Demonstration");
        System.out.println("=================================");
        
        // Basic queue operations
        System.out.println("=== BASIC QUEUE OPERATIONS ===");
        QueueImplementation<Integer> intQueue = new QueueImplementation<>(5);
        
        System.out.println("Initial queue: " + intQueue);
        System.out.println(intQueue.getDetailedState());
        System.out.println("Is empty: " + intQueue.isEmpty());
        
        // Enqueue operations
        System.out.println("\nEnqueuing elements: 10, 20, 30, 40, 50");
        intQueue.enqueue(10);
        System.out.println("After enqueue(10): " + intQueue);
        System.out.println(intQueue.getDetailedState());
        
        intQueue.enqueue(20);
        intQueue.enqueue(30);
        intQueue.enqueue(40);
        intQueue.enqueue(50);
        
        System.out.println("Queue after all enqueues: " + intQueue);
        System.out.println(intQueue.getDetailedState());
        System.out.println("Is full: " + intQueue.isFull());
        
        // Front and rear operations
        System.out.println("\nFront element: " + intQueue.front());
        System.out.println("Rear element: " + intQueue.rear());
        
        // Dequeue operations
        System.out.println("\nDequeuing elements:");
        System.out.println("Dequeued: " + intQueue.dequeue() + " | Remaining: " + intQueue);
        System.out.println("Dequeued: " + intQueue.dequeue() + " | Remaining: " + intQueue);
        System.out.println(intQueue.getDetailedState());
        
        // Test circular nature
        System.out.println("\n=== CIRCULAR QUEUE TEST ===");
        System.out.println("Adding more elements to demonstrate circular behavior:");
        intQueue.enqueue(60);
        intQueue.enqueue(70);
        System.out.println("After adding 60, 70: " + intQueue);
        System.out.println(intQueue.getDetailedState());
        
        // Continue dequeuing
        System.out.println("\nContinuing to dequeue:");
        while (!intQueue.isEmpty()) {
            System.out.println("Dequeued: " + intQueue.dequeue() + " | Remaining: " + intQueue);
            System.out.println(intQueue.getDetailedState());
        }
        
        // Error handling demonstration
        System.out.println("\n=== ERROR HANDLING ===");
        QueueImplementation<String> emptyQueue = new QueueImplementation<>(3);
        
        try {
            emptyQueue.dequeue();
        } catch (RuntimeException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
        
        try {
            emptyQueue.front();
        } catch (RuntimeException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
        
        // Test full queue
        QueueImplementation<String> fullQueue = new QueueImplementation<>(2);
        fullQueue.enqueue("A");
        fullQueue.enqueue("B");
        
        try {
            fullQueue.enqueue("C");
        } catch (RuntimeException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
        
        // Demonstrate queue applications
        demonstrateQueueApplications();
        
        System.out.println("\n=== QUEUE CHARACTERISTICS ===");
        System.out.println("• FIFO (First In, First Out) principle");
        System.out.println("• O(1) time complexity for enqueue, dequeue, front operations");
        System.out.println("• Used in: BFS, level order traversal, scheduling, buffering");
        System.out.println("• Circular implementation provides efficient space usage");
        System.out.println("• Essential for managing resources in order of arrival");
        
        System.out.println("\n=== QUEUE VS STACK ===");
        System.out.println("Queue (FIFO):");
        System.out.println("  • First element added is first element removed");
        System.out.println("  • Operations: enqueue (rear), dequeue (front)");
        System.out.println("  • Use cases: Scheduling, BFS, buffering");
        
        System.out.println("\nStack (LIFO):");
        System.out.println("  • Last element added is first element removed");
        System.out.println("  • Operations: push (top), pop (top)");
        System.out.println("  • Use cases: Function calls, DFS, undo operations");
    }
}
