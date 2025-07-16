package datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * DataStructuresLearningGUI - Interactive JavaFX Learning Platform for Data Structures
 * 
 * EDUCATIONAL PURPOSE:
 * This JavaFX GUI provides an interactive environment to learn and experiment with:
 * - Arrays and Dynamic Arrays
 * - Linked Lists and Variants
 * - Stacks and Queues
 * - Trees and Binary Search Trees
 * - Hash Tables and Maps
 * - Graphs and Graph Algorithms
 * 
 * LEARNING APPROACH:
 * - Visual representations of data structures
 * - Interactive operations and manipulations
 * - Performance comparison demonstrations
 * - Real-world usage examples
 * - Step-by-step algorithm visualizations
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class DataStructuresLearningGUI extends Application {
    
    private TextArea outputArea;
    private TextField inputField;
    private Label statusLabel;
    private ComboBox<String> operationSelector;
    
    // Data structure instances for demonstrations
    private List<String> arrayList = new ArrayList<>();
    private LinkedList<String> linkedList = new LinkedList<>();
    private Stack<String> stack = new Stack<>();
    private Queue<String> queue = new LinkedList<>();
    private Map<String, String> hashMap = new HashMap<>();
    private Set<String> hashSet = new HashSet<>();
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data Structures Learning Platform");
        
        // Create main layout using GridPane
        GridPane mainLayout = new GridPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setHgap(10);
        mainLayout.setVgap(10);
        
        // Configure column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(34);
        mainLayout.getColumnConstraints().addAll(col1, col2, col3);
        
        // Create sections
        createHeaderSection(mainLayout);
        createLinearStructuresSection(mainLayout);
        createNonLinearStructuresSection(mainLayout);
        createAdvancedStructuresSection(mainLayout);
        createInteractiveSection(mainLayout);
        createOutputSection(mainLayout);
        createControlSection(mainLayout);
        
        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        initializeDataStructures();
        appendOutput("Welcome to Data Structures Learning Platform!");
        appendOutput("Explore different data structures through interactive demonstrations.");
        appendOutput("Use the interactive section to perform operations on live data structures.\n");
    }
    
    private void createHeaderSection(GridPane layout) {
        Label titleLabel = new Label("Data Structures Learning Platform");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        statusLabel = new Label("Ready to explore data structures!");
        statusLabel.setStyle("-fx-text-fill: #2e7d32;");
        
        VBox headerBox = new VBox(5);
        headerBox.getChildren().addAll(titleLabel, statusLabel);
        headerBox.setAlignment(Pos.CENTER);
        
        layout.add(headerBox, 0, 0, 3, 1);
    }
    
    private void createLinearStructuresSection(GridPane layout) {
        Label sectionLabel = new Label("1. Linear Structures");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Button arrayBtn = new Button("Arrays & ArrayList");
        arrayBtn.setMaxWidth(Double.MAX_VALUE);
        arrayBtn.setOnAction(e -> demonstrateArrays());
        
        Button linkedListBtn = new Button("Linked Lists");
        linkedListBtn.setMaxWidth(Double.MAX_VALUE);
        linkedListBtn.setOnAction(e -> demonstrateLinkedLists());
        
        Button stackBtn = new Button("Stacks (LIFO)");
        stackBtn.setMaxWidth(Double.MAX_VALUE);
        stackBtn.setOnAction(e -> demonstrateStacks());
        
        Button queueBtn = new Button("Queues (FIFO)");
        queueBtn.setMaxWidth(Double.MAX_VALUE);
        queueBtn.setOnAction(e -> demonstrateQueues());
        
        VBox linearBox = new VBox(5);
        linearBox.getChildren().addAll(sectionLabel, arrayBtn, linkedListBtn, stackBtn, queueBtn);
        linearBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(linearBox, 0, 1);
    }
    
    private void createNonLinearStructuresSection(GridPane layout) {
        Label sectionLabel = new Label("2. Non-Linear Structures");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Button treeBtn = new Button("Trees & BST");
        treeBtn.setMaxWidth(Double.MAX_VALUE);
        treeBtn.setOnAction(e -> demonstrateTrees());
        
        Button heapBtn = new Button("Heaps & Priority Queues");
        heapBtn.setMaxWidth(Double.MAX_VALUE);
        heapBtn.setOnAction(e -> demonstrateHeaps());
        
        Button graphBtn = new Button("Graphs");
        graphBtn.setMaxWidth(Double.MAX_VALUE);
        graphBtn.setOnAction(e -> demonstrateGraphs());
        
        Button trieBtn = new Button("Tries");
        trieBtn.setMaxWidth(Double.MAX_VALUE);
        trieBtn.setOnAction(e -> demonstrateTries());
        
        VBox nonLinearBox = new VBox(5);
        nonLinearBox.getChildren().addAll(sectionLabel, treeBtn, heapBtn, graphBtn, trieBtn);
        nonLinearBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(nonLinearBox, 1, 1);
    }
    
    private void createAdvancedStructuresSection(GridPane layout) {
        Label sectionLabel = new Label("3. Advanced Structures");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Button hashBtn = new Button("Hash Tables & Maps");
        hashBtn.setMaxWidth(Double.MAX_VALUE);
        hashBtn.setOnAction(e -> demonstrateHashTables());
        
        Button setBtn = new Button("Sets & Collections");
        setBtn.setMaxWidth(Double.MAX_VALUE);
        setBtn.setOnAction(e -> demonstrateSets());
        
        Button performanceBtn = new Button("Performance Comparison");
        performanceBtn.setMaxWidth(Double.MAX_VALUE);
        performanceBtn.setOnAction(e -> demonstratePerformance());
        
        Button applicationsBtn = new Button("Real-World Applications");
        applicationsBtn.setMaxWidth(Double.MAX_VALUE);
        applicationsBtn.setOnAction(e -> demonstrateApplications());
        
        VBox advancedBox = new VBox(5);
        advancedBox.getChildren().addAll(sectionLabel, hashBtn, setBtn, performanceBtn, applicationsBtn);
        advancedBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(advancedBox, 2, 1);
    }
    
    private void createInteractiveSection(GridPane layout) {
        Label sectionLabel = new Label("Interactive Operations");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        operationSelector = new ComboBox<>();
        operationSelector.getItems().addAll(
            "ArrayList Operations",
            "LinkedList Operations", 
            "Stack Operations",
            "Queue Operations",
            "HashMap Operations",
            "HashSet Operations"
        );
        operationSelector.setValue("ArrayList Operations");
        operationSelector.setMaxWidth(Double.MAX_VALUE);
        
        Button executeBtn = new Button("Execute Operation");
        executeBtn.setMaxWidth(Double.MAX_VALUE);
        executeBtn.setOnAction(e -> executeInteractiveOperation());
        
        Button viewStateBtn = new Button("View Current State");
        viewStateBtn.setMaxWidth(Double.MAX_VALUE);
        viewStateBtn.setOnAction(e -> viewDataStructureState());
        
        Button resetBtn = new Button("Reset All Structures");
        resetBtn.setMaxWidth(Double.MAX_VALUE);
        resetBtn.setOnAction(e -> resetAllDataStructures());
        
        VBox interactiveBox = new VBox(5);
        interactiveBox.getChildren().addAll(sectionLabel, operationSelector, executeBtn, viewStateBtn, resetBtn);
        interactiveBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(interactiveBox, 0, 2, 3, 1);
    }
    
    private void createOutputSection(GridPane layout) {
        Label outputLabel = new Label("Output Console");
        outputLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(12);
        outputArea.setStyle("-fx-font-family: 'Courier New', monospace; -fx-font-size: 12px; " +
                          "-fx-background-color: #f4f4f4; -fx-text-fill: #000000;");
        
        VBox outputBox = new VBox(5);
        outputBox.getChildren().addAll(outputLabel, outputArea);
        
        layout.add(outputBox, 0, 3, 3, 1);
    }
    
    private void createControlSection(GridPane layout) {
        Label noteLabel = new Label("Input:");
        inputField = new TextField();
        inputField.setPromptText("Enter value for operations...");
        
        Button addNoteBtn = new Button("Add Note");
        addNoteBtn.setOnAction(e -> addNote());
        
        Button clearBtn = new Button("Clear Output");
        clearBtn.setOnAction(e -> clearOutput());
        
        HBox controlBox = new HBox(10);
        controlBox.setAlignment(Pos.CENTER_LEFT);
        controlBox.getChildren().addAll(noteLabel, inputField, addNoteBtn, clearBtn);
        
        layout.add(controlBox, 0, 4, 3, 1);
    }
    
    private void initializeDataStructures() {
        // Initialize with sample data
        arrayList.addAll(Arrays.asList("Apple", "Banana", "Cherry"));
        linkedList.addAll(Arrays.asList("Node1", "Node2", "Node3"));
        stack.addAll(Arrays.asList("Bottom", "Middle", "Top"));
        queue.addAll(Arrays.asList("First", "Second", "Third"));
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");
        hashSet.addAll(Arrays.asList("unique1", "unique2", "unique3"));
    }
    
    private void demonstrateArrays() {
        setCurrentDemo("Arrays & ArrayList Demo");
        appendOutput("=== ARRAYS & ARRAYLIST DEMONSTRATION ===");
        appendOutput("");
        appendOutput("// Array - Fixed size, contiguous memory");
        appendOutput("int[] fixedArray = new int[5];");
        appendOutput("fixedArray[0] = 10; // Direct index access O(1)");
        appendOutput("fixedArray[1] = 20;");
        appendOutput("// Array size cannot change after creation");
        appendOutput("");
        appendOutput("// ArrayList - Dynamic array, resizable");
        appendOutput("ArrayList<String> dynamicList = new ArrayList<>();");
        appendOutput("dynamicList.add(\"First\");     // O(1) amortized");
        appendOutput("dynamicList.add(\"Second\");    // O(1) amortized");
        appendOutput("dynamicList.add(0, \"Zero\");   // O(n) - shifts elements");
        appendOutput("dynamicList.get(1);             // O(1) - direct access");
        appendOutput("dynamicList.remove(0);          // O(n) - shifts elements");
        appendOutput("");
        appendOutput("Current ArrayList: " + arrayList);
        appendOutput("Size: " + arrayList.size());
        appendOutput("");
        appendOutput("CHARACTERISTICS:");
        appendOutput("• Access: O(1) by index");
        appendOutput("• Search: O(n) linear search");
        appendOutput("• Insertion: O(1) at end, O(n) at beginning/middle");
        appendOutput("• Deletion: O(1) at end, O(n) at beginning/middle");
        appendOutput("• Memory: Contiguous, cache-friendly");
        appendOutput("• Use when: Need fast random access, mostly append operations");
    }
    
    private void demonstrateLinkedLists() {
        setCurrentDemo("Linked Lists Demo");
        appendOutput("=== LINKED LIST DEMONSTRATION ===");
        appendOutput("");
        appendOutput("// LinkedList - Doubly linked list in Java");
        appendOutput("LinkedList<String> list = new LinkedList<>();");
        appendOutput("list.addFirst(\"Head\");    // O(1)");
        appendOutput("list.addLast(\"Tail\");     // O(1)");
        appendOutput("list.add(1, \"Middle\");    // O(n) - traverse to position");
        appendOutput("list.removeFirst();        // O(1)");
        appendOutput("list.removeLast();         // O(1)");
        appendOutput("");
        appendOutput("Visual representation:");
        appendOutput("null <- [Data|Prev|Next] <-> [Data|Prev|Next] <-> [Data|Prev|Next] -> null");
        appendOutput("");
        appendOutput("Current LinkedList: " + linkedList);
        appendOutput("First element: " + (linkedList.isEmpty() ? "None" : linkedList.getFirst()));
        appendOutput("Last element: " + (linkedList.isEmpty() ? "None" : linkedList.getLast()));
        appendOutput("");
        appendOutput("CHARACTERISTICS:");
        appendOutput("• Access: O(n) - must traverse from head/tail");
        appendOutput("• Search: O(n) - linear search");
        appendOutput("• Insertion: O(1) at head/tail, O(n) at middle");
        appendOutput("• Deletion: O(1) if have reference, O(n) if searching");
        appendOutput("• Memory: Non-contiguous, extra pointer overhead");
        appendOutput("• Use when: Frequent insertion/deletion at ends, size varies greatly");
    }
    
    private void demonstrateStacks() {
        setCurrentDemo("Stack (LIFO) Demo");
        appendOutput("=== STACK (LIFO - Last In, First Out) DEMONSTRATION ===");
        appendOutput("");
        appendOutput("// Stack operations");
        appendOutput("Stack<String> stack = new Stack<>();");
        appendOutput("stack.push(\"Bottom\");   // Add to top");
        appendOutput("stack.push(\"Middle\");   // Add to top");
        appendOutput("stack.push(\"Top\");      // Add to top");
        appendOutput("String top = stack.pop(); // Remove from top -> \"Top\"");
        appendOutput("String peek = stack.peek(); // Look at top without removing");
        appendOutput("");
        appendOutput("Visual representation (top to bottom):");
        appendOutput("┌─────────┐");
        for (int i = stack.size() - 1; i >= 0; i--) {
            appendOutput("│ " + stack.get(i) + " │ <- " + (i == stack.size() - 1 ? "TOP" : ""));
        }
        appendOutput("└─────────┘");
        appendOutput("");
        appendOutput("Current Stack: " + stack);
        appendOutput("Size: " + stack.size());
        appendOutput("Top element: " + (stack.isEmpty() ? "None" : stack.peek()));
        appendOutput("");
        appendOutput("APPLICATIONS:");
        appendOutput("• Function call management (call stack)");
        appendOutput("• Expression evaluation and syntax parsing");
        appendOutput("• Undo operations in applications");
        appendOutput("• Browser back button functionality");
        appendOutput("• Depth-First Search (DFS) algorithms");
    }
    
    private void demonstrateQueues() {
        setCurrentDemo("Queue (FIFO) Demo");
        appendOutput("=== QUEUE (FIFO - First In, First Out) DEMONSTRATION ===");
        appendOutput("");
        appendOutput("// Queue operations");
        appendOutput("Queue<String> queue = new LinkedList<>();");
        appendOutput("queue.offer(\"First\");   // Add to rear (enqueue)");
        appendOutput("queue.offer(\"Second\");  // Add to rear");
        appendOutput("queue.offer(\"Third\");   // Add to rear");
        appendOutput("String first = queue.poll(); // Remove from front (dequeue)");
        appendOutput("String peek = queue.peek();  // Look at front without removing");
        appendOutput("");
        appendOutput("Visual representation:");
        appendOutput("Front -> [" + String.join("] -> [", queue.toArray(new String[0])) + "] <- Rear");
        appendOutput("");
        appendOutput("Current Queue: " + queue);
        appendOutput("Size: " + queue.size());
        appendOutput("Front element: " + (queue.isEmpty() ? "None" : queue.peek()));
        appendOutput("");
        appendOutput("QUEUE VARIANTS:");
        appendOutput("• Simple Queue: Basic FIFO behavior");
        appendOutput("• Circular Queue: Fixed size, wraps around");
        appendOutput("• Priority Queue: Elements have priorities");
        appendOutput("• Deque: Double-ended queue (add/remove both ends)");
        appendOutput("");
        appendOutput("APPLICATIONS:");
        appendOutput("• Task scheduling in operating systems");
        appendOutput("• Breadth-First Search (BFS) algorithms");
        appendOutput("• Print job queues");
        appendOutput("• Buffer for data streams");
        appendOutput("• Web server request handling");
    }
    
    private void demonstrateTrees() {
        setCurrentDemo("Trees & Binary Search Trees");
        appendOutput("=== TREES & BINARY SEARCH TREES DEMONSTRATION ===");
        appendOutput("");
        appendOutput("// Binary Search Tree properties:");
        appendOutput("// - Left subtree contains only nodes with values less than parent");
        appendOutput("// - Right subtree contains only nodes with values greater than parent");
        appendOutput("// - Both left and right subtrees are also BSTs");
        appendOutput("");
        appendOutput("Visual BST example:");
        appendOutput("        50");
        appendOutput("       /  \\");
        appendOutput("      30   70");
        appendOutput("     / \\   / \\");
        appendOutput("    20 40 60 80");
        appendOutput("");
        appendOutput("BST Operations:");
        appendOutput("insert(25) -> Finds correct position maintaining BST property");
        appendOutput("search(40) -> Compares values, goes left or right");
        appendOutput("delete(30) -> Complex operation, may need restructuring");
        appendOutput("");
        appendOutput("TREE TYPES:");
        appendOutput("• Binary Tree: Each node has at most 2 children");
        appendOutput("• Binary Search Tree: Ordered binary tree");
        appendOutput("• AVL Tree: Self-balancing BST");
        appendOutput("• Red-Black Tree: Self-balancing with color properties");
        appendOutput("• B-Tree: Multi-way tree for databases");
        appendOutput("");
        appendOutput("TIME COMPLEXITIES (Balanced BST):");
        appendOutput("• Search: O(log n)");
        appendOutput("• Insertion: O(log n)");
        appendOutput("• Deletion: O(log n)");
        appendOutput("• Traversal: O(n)");
        appendOutput("");
        appendOutput("APPLICATIONS:");
        appendOutput("• Database indexing");
        appendOutput("• File systems");
        appendOutput("• Expression parsing");
        appendOutput("• Decision trees in AI");
    }
    
    private void demonstrateHeaps() {
        setCurrentDemo("Heaps & Priority Queues");
        appendOutput("=== HEAPS & PRIORITY QUEUES DEMONSTRATION ===");
        appendOutput("");
        appendOutput("// Heap - Complete binary tree with heap property");
        appendOutput("// Max Heap: Parent >= children");
        appendOutput("// Min Heap: Parent <= children");
        appendOutput("");
        appendOutput("Max Heap example:");
        appendOutput("        90");
        appendOutput("       /  \\");
        appendOutput("      80   70");
        appendOutput("     / \\   /");
        appendOutput("    60 50 40");
        appendOutput("");
        appendOutput("Array representation: [90, 80, 70, 60, 50, 40]");
        appendOutput("Parent of index i: (i-1)/2");
        appendOutput("Left child of i: 2*i + 1");
        appendOutput("Right child of i: 2*i + 2");
        appendOutput("");
        appendOutput("PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());");
        appendOutput("maxHeap.offer(50);");
        appendOutput("maxHeap.offer(80);");
        appendOutput("maxHeap.offer(30);");
        appendOutput("int highest = maxHeap.poll(); // Removes and returns 80");
        appendOutput("");
        appendOutput("HEAP OPERATIONS:");
        appendOutput("• Insert: O(log n) - add at end, bubble up");
        appendOutput("• Extract Max/Min: O(log n) - remove root, bubble down");
        appendOutput("• Peek: O(1) - view root without removing");
        appendOutput("• Build Heap: O(n) from array");
        appendOutput("");
        appendOutput("APPLICATIONS:");
        appendOutput("• Priority queues");
        appendOutput("• Heap sort algorithm");
        appendOutput("• Dijkstra's shortest path");
        appendOutput("• Task scheduling");
        appendOutput("• Memory management");
    }
    
    private void demonstrateGraphs() {
        setCurrentDemo("Graph Data Structures");
        appendOutput("=== GRAPH DATA STRUCTURES DEMONSTRATION ===");
        appendOutput("");
        appendOutput("// Graph - Set of vertices connected by edges");
        appendOutput("// Can be directed or undirected, weighted or unweighted");
        appendOutput("");
        appendOutput("Graph representations:");
        appendOutput("");
        appendOutput("1. ADJACENCY MATRIX:");
        appendOutput("   A B C D");
        appendOutput("A [0 1 1 0]");
        appendOutput("B [1 0 0 1]");
        appendOutput("C [1 0 0 1]");
        appendOutput("D [0 1 1 0]");
        appendOutput("");
        appendOutput("2. ADJACENCY LIST:");
        appendOutput("A -> [B, C]");
        appendOutput("B -> [A, D]");
        appendOutput("C -> [A, D]");
        appendOutput("D -> [B, C]");
        appendOutput("");
        appendOutput("Java implementation:");
        appendOutput("Map<String, List<String>> adjList = new HashMap<>();");
        appendOutput("adjList.put(\"A\", Arrays.asList(\"B\", \"C\"));");
        appendOutput("adjList.put(\"B\", Arrays.asList(\"A\", \"D\"));");
        appendOutput("");
        appendOutput("GRAPH ALGORITHMS:");
        appendOutput("• Depth-First Search (DFS): Uses stack/recursion");
        appendOutput("• Breadth-First Search (BFS): Uses queue");
        appendOutput("• Dijkstra's Algorithm: Shortest path");
        appendOutput("• Kruskal's/Prim's: Minimum spanning tree");
        appendOutput("• Topological Sort: For directed acyclic graphs");
        appendOutput("");
        appendOutput("APPLICATIONS:");
        appendOutput("• Social networks");
        appendOutput("• GPS navigation systems");
        appendOutput("• Network routing protocols");
        appendOutput("• Dependency resolution");
        appendOutput("• Web page ranking");
    }
    
    private void demonstrateTries() {
        setCurrentDemo("Trie Data Structures");
        appendOutput("=== TRIE (PREFIX TREE) DEMONSTRATION ===");
        appendOutput("");
        appendOutput("// Trie - Tree where each path represents a string");
        appendOutput("// Efficient for prefix-based operations");
        appendOutput("");
        appendOutput("Trie example for words: CAT, CAR, CARD, CARE, CAREFUL");
        appendOutput("");
        appendOutput("      root");
        appendOutput("       |");
        appendOutput("       C");
        appendOutput("       |");
        appendOutput("       A");
        appendOutput("       |");
        appendOutput("       R ---- T*");
        appendOutput("       |");
        appendOutput("      [*] ---- D*");
        appendOutput("       |");
        appendOutput("       E");
        appendOutput("       |");
        appendOutput("      [*] ---- F-U-L*");
        appendOutput("");
        appendOutput("* indicates end of word");
        appendOutput("");
        appendOutput("class TrieNode {");
        appendOutput("    Map<Character, TrieNode> children = new HashMap<>();");
        appendOutput("    boolean isEndOfWord = false;");
        appendOutput("}");
        appendOutput("");
        appendOutput("TRIE OPERATIONS:");
        appendOutput("• Insert: O(m) where m is word length");
        appendOutput("• Search: O(m) where m is word length");
        appendOutput("• Delete: O(m) where m is word length");
        appendOutput("• Prefix Search: O(p) where p is prefix length");
        appendOutput("");
        appendOutput("APPLICATIONS:");
        appendOutput("• Autocomplete/suggestions");
        appendOutput("• Spell checkers");
        appendOutput("• IP routing tables");
        appendOutput("• Dictionary implementations");
        appendOutput("• Search engines");
    }
    
    private void demonstrateHashTables() {
        setCurrentDemo("Hash Tables & Maps");
        appendOutput("=== HASH TABLES & MAPS DEMONSTRATION ===");
        appendOutput("");
        appendOutput("// HashMap - Uses hash function to map keys to array indices");
        appendOutput("HashMap<String, String> map = new HashMap<>();");
        appendOutput("map.put(\"name\", \"John\");      // O(1) average");
        appendOutput("map.put(\"age\", \"25\");        // O(1) average");
        appendOutput("String name = map.get(\"name\"); // O(1) average");
        appendOutput("");
        appendOutput("Hash function example:");
        appendOutput("hash(\"name\") = hashCode(\"name\") % arraySize");
        appendOutput("             = 3373707 % 16 = 11");
        appendOutput("");
        appendOutput("Current HashMap: " + hashMap);
        appendOutput("Size: " + hashMap.size());
        appendOutput("Keys: " + hashMap.keySet());
        appendOutput("Values: " + hashMap.values());
        appendOutput("");
        appendOutput("COLLISION HANDLING:");
        appendOutput("• Chaining: Each bucket contains a linked list");
        appendOutput("• Open Addressing: Find next available slot");
        appendOutput("  - Linear Probing: Check next slot");
        appendOutput("  - Quadratic Probing: Check i² slots away");
        appendOutput("  - Double Hashing: Use second hash function");
        appendOutput("");
        appendOutput("HASH TABLE VARIANTS:");
        appendOutput("• HashMap: Basic hash table, not thread-safe");
        appendOutput("• LinkedHashMap: Maintains insertion order");
        appendOutput("• TreeMap: Sorted map using red-black tree");
        appendOutput("• ConcurrentHashMap: Thread-safe version");
        appendOutput("");
        appendOutput("TIME COMPLEXITIES (Average case):");
        appendOutput("• Search: O(1)");
        appendOutput("• Insertion: O(1)");
        appendOutput("• Deletion: O(1)");
        appendOutput("• Worst case: O(n) if many collisions");
    }
    
    private void demonstrateSets() {
        setCurrentDemo("Sets & Collections");
        appendOutput("=== SETS & COLLECTIONS DEMONSTRATION ===");
        appendOutput("");
        appendOutput("// HashSet - No duplicate elements, uses hash table");
        appendOutput("HashSet<String> set = new HashSet<>();");
        appendOutput("set.add(\"apple\");     // Returns true");
        appendOutput("set.add(\"banana\");   // Returns true");
        appendOutput("set.add(\"apple\");    // Returns false (duplicate)");
        appendOutput("boolean has = set.contains(\"apple\"); // O(1) average");
        appendOutput("");
        appendOutput("Current HashSet: " + hashSet);
        appendOutput("Size: " + hashSet.size());
        appendOutput("");
        appendOutput("SET IMPLEMENTATIONS:");
        appendOutput("");
        appendOutput("1. HashSet:");
        appendOutput("   • No duplicates, no order");
        appendOutput("   • O(1) add, remove, contains (average)");
        appendOutput("   • Uses hash table internally");
        appendOutput("");
        appendOutput("2. LinkedHashSet:");
        appendOutput("   • No duplicates, maintains insertion order");
        appendOutput("   • O(1) operations with slightly more overhead");
        appendOutput("");
        appendOutput("3. TreeSet:");
        appendOutput("   • No duplicates, sorted order");
        appendOutput("   • O(log n) operations");
        appendOutput("   • Uses red-black tree internally");
        appendOutput("");
        appendOutput("SET OPERATIONS:");
        appendOutput("Set<String> set1 = Set.of(\"A\", \"B\", \"C\");");
        appendOutput("Set<String> set2 = Set.of(\"B\", \"C\", \"D\");");
        appendOutput("");
        appendOutput("Union: set1 ∪ set2 = {A, B, C, D}");
        appendOutput("Intersection: set1 ∩ set2 = {B, C}");
        appendOutput("Difference: set1 - set2 = {A}");
        appendOutput("");
        appendOutput("APPLICATIONS:");
        appendOutput("• Removing duplicates from data");
        appendOutput("• Membership testing");
        appendOutput("• Mathematical set operations");
        appendOutput("• Tracking unique visitors");
    }
    
    private void demonstratePerformance() {
        setCurrentDemo("Performance Comparison");
        appendOutput("=== DATA STRUCTURE PERFORMANCE COMPARISON ===");
        appendOutput("");
        appendOutput("TIME COMPLEXITY COMPARISON:");
        appendOutput("");
        appendOutput("Data Structure    | Access | Search | Insertion | Deletion");
        appendOutput("------------------|--------|--------|-----------|----------");
        appendOutput("Array             | O(1)   | O(n)   | O(n)      | O(n)");
        appendOutput("ArrayList         | O(1)   | O(n)   | O(1)*     | O(n)");
        appendOutput("LinkedList        | O(n)   | O(n)   | O(1)      | O(1)**");
        appendOutput("Stack             | O(n)   | O(n)   | O(1)      | O(1)");
        appendOutput("Queue             | O(n)   | O(n)   | O(1)      | O(1)");
        appendOutput("HashMap           | N/A    | O(1)*  | O(1)*     | O(1)*");
        appendOutput("TreeMap           | N/A    | O(log n)| O(log n) | O(log n)");
        appendOutput("HashSet           | N/A    | O(1)*  | O(1)*     | O(1)*");
        appendOutput("TreeSet           | N/A    | O(log n)| O(log n) | O(log n)");
        appendOutput("Binary Search Tree| O(log n)| O(log n)| O(log n)| O(log n)");
        appendOutput("Heap              | O(1)*** | O(n)  | O(log n) | O(log n)");
        appendOutput("");
        appendOutput("* Average case, O(n) worst case");
        appendOutput("** If you have reference to the node");
        appendOutput("*** Access to min/max element only");
        appendOutput("");
        appendOutput("SPACE COMPLEXITY:");
        appendOutput("• Array: O(n)");
        appendOutput("• Linked structures: O(n) + pointer overhead");
        appendOutput("• Hash tables: O(n) + hash table overhead");
        appendOutput("• Trees: O(n) + pointer overhead");
        appendOutput("");
        appendOutput("CHOOSING THE RIGHT DATA STRUCTURE:");
        appendOutput("• Need fast random access? → Array/ArrayList");
        appendOutput("• Frequent insertion/deletion at ends? → LinkedList");
        appendOutput("• LIFO operations? → Stack");
        appendOutput("• FIFO operations? → Queue");
        appendOutput("• Fast lookups by key? → HashMap");
        appendOutput("• Sorted data with fast operations? → TreeMap/TreeSet");
        appendOutput("• No duplicates needed? → HashSet");
        appendOutput("• Priority-based processing? → PriorityQueue/Heap");
    }
    
    private void demonstrateApplications() {
        setCurrentDemo("Real-World Applications");
        appendOutput("=== REAL-WORLD DATA STRUCTURE APPLICATIONS ===");
        appendOutput("");
        appendOutput("1. ARRAYS & ARRAYLISTS:");
        appendOutput("   • Database records and rows");
        appendOutput("   • Image pixels in graphics");
        appendOutput("   • Dynamic lists in GUIs");
        appendOutput("   • Buffer arrays in streaming");
        appendOutput("");
        appendOutput("2. LINKED LISTS:");
        appendOutput("   • Music playlists (next/previous)");
        appendOutput("   • Browser history navigation");
        appendOutput("   • Undo/redo operations");
        appendOutput("   • Implementation of other data structures");
        appendOutput("");
        appendOutput("3. STACKS:");
        appendOutput("   • Function call management");
        appendOutput("   • Expression evaluation calculators");
        appendOutput("   • Browser back button");
        appendOutput("   • Syntax parsing in compilers");
        appendOutput("   • Depth-First Search algorithms");
        appendOutput("");
        appendOutput("4. QUEUES:");
        appendOutput("   • Print job scheduling");
        appendOutput("   • CPU task scheduling");
        appendOutput("   • Breadth-First Search");
        appendOutput("   • Buffer for keyboard input");
        appendOutput("   • Call center systems");
        appendOutput("");
        appendOutput("5. HASH TABLES:");
        appendOutput("   • Database indexing");
        appendOutput("   • Caching systems (Redis, Memcached)");
        appendOutput("   • Password verification");
        appendOutput("   • Compiler symbol tables");
        appendOutput("   • Distributed systems (consistent hashing)");
        appendOutput("");
        appendOutput("6. TREES:");
        appendOutput("   • File system directories");
        appendOutput("   • Database B-trees for indexing");
        appendOutput("   • Decision trees in machine learning");
        appendOutput("   • Abstract syntax trees in compilers");
        appendOutput("   • Hierarchical data (org charts, taxonomies)");
        appendOutput("");
        appendOutput("7. GRAPHS:");
        appendOutput("   • Social media friend networks");
        appendOutput("   • GPS navigation and routing");
        appendOutput("   • Network topology in telecommunications");
        appendOutput("   • Dependency graphs in build systems");
        appendOutput("   • Web page link analysis (PageRank)");
        appendOutput("");
        appendOutput("8. HEAPS:");
        appendOutput("   • Priority queues in operating systems");
        appendOutput("   • Dijkstra's shortest path algorithm");
        appendOutput("   • Heap sort implementation");
        appendOutput("   • Memory management (garbage collection)");
        appendOutput("   • Event simulation systems");
    }
    
    private void executeInteractiveOperation() {
        String operation = operationSelector.getValue();
        String input = inputField.getText().trim();
        
        setCurrentDemo("Interactive: " + operation);
        
        switch (operation) {
            case "ArrayList Operations":
                executeArrayListOperation(input);
                break;
            case "LinkedList Operations":
                executeLinkedListOperation(input);
                break;
            case "Stack Operations":
                executeStackOperation(input);
                break;
            case "Queue Operations":
                executeQueueOperation(input);
                break;
            case "HashMap Operations":
                executeHashMapOperation(input);
                break;
            case "HashSet Operations":
                executeHashSetOperation(input);
                break;
        }
        
        inputField.clear();
    }
    
    private void executeArrayListOperation(String input) {
        if (input.isEmpty()) {
            appendOutput("ArrayList operations: add <value>, remove <index>, get <index>, clear");
            return;
        }
        
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        
        try {
            switch (command) {
                case "add":
                    if (parts.length > 1) {
                        arrayList.add(parts[1]);
                        appendOutput("Added '" + parts[1] + "' to ArrayList");
                    }
                    break;
                case "remove":
                    if (parts.length > 1) {
                        int index = Integer.parseInt(parts[1]);
                        String removed = arrayList.remove(index);
                        appendOutput("Removed '" + removed + "' from index " + index);
                    }
                    break;
                case "get":
                    if (parts.length > 1) {
                        int index = Integer.parseInt(parts[1]);
                        String value = arrayList.get(index);
                        appendOutput("Element at index " + index + ": '" + value + "'");
                    }
                    break;
                case "clear":
                    arrayList.clear();
                    appendOutput("ArrayList cleared");
                    break;
                default:
                    appendOutput("Unknown command. Use: add, remove, get, clear");
            }
        } catch (Exception e) {
            appendOutput("Error: " + e.getMessage());
        }
    }
    
    private void executeLinkedListOperation(String input) {
        if (input.isEmpty()) {
            appendOutput("LinkedList operations: addFirst <value>, addLast <value>, removeFirst, removeLast, clear");
            return;
        }
        
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        
        try {
            switch (command) {
                case "addfirst":
                    if (parts.length > 1) {
                        linkedList.addFirst(parts[1]);
                        appendOutput("Added '" + parts[1] + "' to front of LinkedList");
                    }
                    break;
                case "addlast":
                    if (parts.length > 1) {
                        linkedList.addLast(parts[1]);
                        appendOutput("Added '" + parts[1] + "' to end of LinkedList");
                    }
                    break;
                case "removefirst":
                    if (!linkedList.isEmpty()) {
                        String removed = linkedList.removeFirst();
                        appendOutput("Removed '" + removed + "' from front");
                    } else {
                        appendOutput("LinkedList is empty");
                    }
                    break;
                case "removelast":
                    if (!linkedList.isEmpty()) {
                        String removed = linkedList.removeLast();
                        appendOutput("Removed '" + removed + "' from end");
                    } else {
                        appendOutput("LinkedList is empty");
                    }
                    break;
                case "clear":
                    linkedList.clear();
                    appendOutput("LinkedList cleared");
                    break;
                default:
                    appendOutput("Unknown command. Use: addFirst, addLast, removeFirst, removeLast, clear");
            }
        } catch (Exception e) {
            appendOutput("Error: " + e.getMessage());
        }
    }
    
    private void executeStackOperation(String input) {
        if (input.isEmpty()) {
            appendOutput("Stack operations: push <value>, pop, peek, clear");
            return;
        }
        
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        
        try {
            switch (command) {
                case "push":
                    if (parts.length > 1) {
                        stack.push(parts[1]);
                        appendOutput("Pushed '" + parts[1] + "' onto stack");
                    }
                    break;
                case "pop":
                    if (!stack.isEmpty()) {
                        String popped = stack.pop();
                        appendOutput("Popped '" + popped + "' from stack");
                    } else {
                        appendOutput("Stack is empty");
                    }
                    break;
                case "peek":
                    if (!stack.isEmpty()) {
                        String top = stack.peek();
                        appendOutput("Top of stack: '" + top + "'");
                    } else {
                        appendOutput("Stack is empty");
                    }
                    break;
                case "clear":
                    stack.clear();
                    appendOutput("Stack cleared");
                    break;
                default:
                    appendOutput("Unknown command. Use: push, pop, peek, clear");
            }
        } catch (Exception e) {
            appendOutput("Error: " + e.getMessage());
        }
    }
    
    private void executeQueueOperation(String input) {
        if (input.isEmpty()) {
            appendOutput("Queue operations: offer <value>, poll, peek, clear");
            return;
        }
        
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        
        try {
            switch (command) {
                case "offer":
                    if (parts.length > 1) {
                        queue.offer(parts[1]);
                        appendOutput("Added '" + parts[1] + "' to queue");
                    }
                    break;
                case "poll":
                    if (!queue.isEmpty()) {
                        String polled = queue.poll();
                        appendOutput("Removed '" + polled + "' from queue");
                    } else {
                        appendOutput("Queue is empty");
                    }
                    break;
                case "peek":
                    if (!queue.isEmpty()) {
                        String front = queue.peek();
                        appendOutput("Front of queue: '" + front + "'");
                    } else {
                        appendOutput("Queue is empty");
                    }
                    break;
                case "clear":
                    queue.clear();
                    appendOutput("Queue cleared");
                    break;
                default:
                    appendOutput("Unknown command. Use: offer, poll, peek, clear");
            }
        } catch (Exception e) {
            appendOutput("Error: " + e.getMessage());
        }
    }
    
    private void executeHashMapOperation(String input) {
        if (input.isEmpty()) {
            appendOutput("HashMap operations: put <key> <value>, get <key>, remove <key>, clear");
            return;
        }
        
        String[] parts = input.split(" ", 3);
        String command = parts[0].toLowerCase();
        
        try {
            switch (command) {
                case "put":
                    if (parts.length >= 3) {
                        String key = parts[1];
                        String value = parts[2];
                        hashMap.put(key, value);
                        appendOutput("Put '" + key + "' -> '" + value + "' in HashMap");
                    }
                    break;
                case "get":
                    if (parts.length > 1) {
                        String key = parts[1];
                        String value = hashMap.get(key);
                        appendOutput("Value for '" + key + "': " + (value != null ? "'" + value + "'" : "null"));
                    }
                    break;
                case "remove":
                    if (parts.length > 1) {
                        String key = parts[1];
                        String removed = hashMap.remove(key);
                        appendOutput("Removed '" + key + "': " + (removed != null ? "'" + removed + "'" : "not found"));
                    }
                    break;
                case "clear":
                    hashMap.clear();
                    appendOutput("HashMap cleared");
                    break;
                default:
                    appendOutput("Unknown command. Use: put, get, remove, clear");
            }
        } catch (Exception e) {
            appendOutput("Error: " + e.getMessage());
        }
    }
    
    private void executeHashSetOperation(String input) {
        if (input.isEmpty()) {
            appendOutput("HashSet operations: add <value>, remove <value>, contains <value>, clear");
            return;
        }
        
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        
        try {
            switch (command) {
                case "add":
                    if (parts.length > 1) {
                        boolean added = hashSet.add(parts[1]);
                        appendOutput(added ? "Added '" + parts[1] + "' to HashSet" : "'" + parts[1] + "' already exists");
                    }
                    break;
                case "remove":
                    if (parts.length > 1) {
                        boolean removed = hashSet.remove(parts[1]);
                        appendOutput(removed ? "Removed '" + parts[1] + "' from HashSet" : "'" + parts[1] + "' not found");
                    }
                    break;
                case "contains":
                    if (parts.length > 1) {
                        boolean contains = hashSet.contains(parts[1]);
                        appendOutput("HashSet contains '" + parts[1] + "': " + contains);
                    }
                    break;
                case "clear":
                    hashSet.clear();
                    appendOutput("HashSet cleared");
                    break;
                default:
                    appendOutput("Unknown command. Use: add, remove, contains, clear");
            }
        } catch (Exception e) {
            appendOutput("Error: " + e.getMessage());
        }
    }
    
    private void viewDataStructureState() {
        appendOutput("=== CURRENT DATA STRUCTURE STATES ===");
        appendOutput("ArrayList: " + arrayList + " (size: " + arrayList.size() + ")");
        appendOutput("LinkedList: " + linkedList + " (size: " + linkedList.size() + ")");
        appendOutput("Stack: " + stack + " (size: " + stack.size() + ")");
        appendOutput("Queue: " + queue + " (size: " + queue.size() + ")");
        appendOutput("HashMap: " + hashMap + " (size: " + hashMap.size() + ")");
        appendOutput("HashSet: " + hashSet + " (size: " + hashSet.size() + ")");
        appendOutput("");
    }
    
    private void resetAllDataStructures() {
        arrayList.clear();
        linkedList.clear();
        stack.clear();
        queue.clear();
        hashMap.clear();
        hashSet.clear();
        
        initializeDataStructures();
        appendOutput("All data structures have been reset to initial state");
        viewDataStructureState();
    }
    
    // Utility methods
    private void setCurrentDemo(String demoName) {
        Platform.runLater(() -> {
            statusLabel.setText("Current Demo: " + demoName);
        });
    }
    
    private void appendOutput(String text) {
        Platform.runLater(() -> {
            outputArea.appendText(text + "\n");
        });
    }
    
    private void addNote() {
        String note = inputField.getText().trim();
        if (!note.isEmpty()) {
            appendOutput("Note: " + note);
            inputField.clear();
        }
    }
    
    private void clearOutput() {
        Platform.runLater(() -> {
            outputArea.clear();
            statusLabel.setText("Ready to explore data structures!");
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
