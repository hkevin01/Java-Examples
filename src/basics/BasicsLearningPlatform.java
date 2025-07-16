package basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * BasicsLearningPlatform - Interactive JavaFX Learning Platform for Java Basics
 * 
 * EDUCATIONAL PURPOSE:
 * This JavaFX GUI provides an interactive environment to learn and experiment with:
 * - Variables and Data Types
 * - Control Structures (if/else, loops)
 * - Methods and Parameters
 * - Arrays and Basic Collections
 * - String Manipulation
 * - Basic I/O Operations
 * 
 * LEARNING APPROACH:
 * - Visual demonstrations of concepts
 * - Interactive code execution
 * - Real-time feedback
 * - Step-by-step explanations
 * - Hands-on experimentation
 * 
 * JavaFX COMPONENTS:
 * - TabPane for lesson organization
 * - Interactive input controls
 * - Real-time output displays
 * - Progress tracking
 * - Educational tooltips
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class BasicsLearningPlatform extends Application {
    
    private TabPane tabPane;
    private TextArea outputArea;
    private TextField inputField;
    private Label statusLabel;
    private ProgressBar progressBar;
    private int completedLessons = 0;
    private final int totalLessons = 6;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Java Basics Learning Platform");
        
        // Create main layout
        BorderPane mainLayout = new BorderPane();
        
        // Create header
        VBox header = createHeader();
        mainLayout.setTop(header);
        
        // Create main content with tabs
        tabPane = new TabPane();
        tabPane.getTabs().addAll(
            createVariablesTab(),
            createControlFlowTab(),
            createMethodsTab(),
            createArraysTab(),
            createStringsTab(),
            createCollectionsTab()
        );
        mainLayout.setCenter(tabPane);
        
        // Create output panel
        VBox outputPanel = createOutputPanel();
        mainLayout.setBottom(outputPanel);
        
        // Create scene
        Scene scene = new Scene(mainLayout, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Initial welcome message
        appendOutput("Welcome to Java Basics Learning Platform!");
        appendOutput("Click on tabs above to start learning different concepts.");
        appendOutput("Each tab contains interactive examples and exercises.\n");
        
        // Update progress
        updateProgress();
    }
    
    /**
     * Create header with title and progress
     */
    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049);");
        
        Label titleLabel = new Label("🎓 Java Basics Learning Platform");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);
        
        statusLabel = new Label("Ready to learn! Start with Variables tab.");
        statusLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        statusLabel.setTextFill(Color.WHITE);
        
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        progressBar.setStyle("-fx-accent: white;");
        
        header.getChildren().addAll(titleLabel, statusLabel, progressBar);
        header.setAlignment(Pos.CENTER);
        
        return header;
    }
    
    /**
     * Create Variables and Data Types tab
     */
    private Tab createVariablesTab() {
        Tab tab = new Tab("📚 Variables");
        tab.setClosable(false);
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        // Theory section
        VBox theorySection = createTheorySection(
            "Variables and Data Types",
            "VARIABLES IN JAVA:\n" +
            "• Variables are containers for storing data values\n" +
            "• Java is strongly typed - each variable has a specific type\n" +
            "• Primitive types: int, double, boolean, char, byte, short, long, float\n" +
            "• Reference types: String, Arrays, Objects\n\n" +
            "VARIABLE DECLARATION:\n" +
            "• Syntax: dataType variableName = value;\n" +
            "• Example: int age = 25;\n" +
            "• Variables must be declared before use\n" +
            "• Good naming: use camelCase, descriptive names"
        );
        
        // Interactive section
        VBox interactiveSection = createInteractiveSection("Variables Demo");
        
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        
        Label nameLabel = new Label("Enter your name:");
        TextField nameField = new TextField();
        nameField.setPromptText("e.g., John Doe");
        
        Label ageLabel = new Label("Enter your age:");
        TextField ageField = new TextField();
        ageField.setPromptText("e.g., 25");
        
        Label heightLabel = new Label("Enter height (meters):");
        TextField heightField = new TextField();
        heightField.setPromptText("e.g., 1.75");
        
        Button demonstrateBtn = new Button("Demonstrate Variables");
        demonstrateBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        
        inputGrid.add(nameLabel, 0, 0);
        inputGrid.add(nameField, 1, 0);
        inputGrid.add(ageLabel, 0, 1);
        inputGrid.add(ageField, 1, 1);
        inputGrid.add(heightLabel, 0, 2);
        inputGrid.add(heightField, 1, 2);
        inputGrid.add(demonstrateBtn, 0, 3, 2, 1);
        
        // Event handler
        demonstrateBtn.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                String heightText = heightField.getText().trim();
                
                if (name.isEmpty() || ageText.isEmpty() || heightText.isEmpty()) {
                    appendOutput("Please fill in all fields!");
                    return;
                }
                
                int age = Integer.parseInt(ageText);
                double height = Double.parseDouble(heightText);
                
                appendOutput("=== VARIABLE DEMONSTRATION ===");
                appendOutput("// Creating variables with different data types:");
                appendOutput("String name = \"" + name + "\";  // String (reference type)");
                appendOutput("int age = " + age + ";  // int (primitive type)");
                appendOutput("double height = " + height + ";  // double (primitive type)");
                appendOutput("boolean isAdult = " + (age >= 18) + ";  // boolean (calculated)");
                appendOutput("");
                appendOutput("Variable Analysis:");
                appendOutput("• Name type: " + name.getClass().getSimpleName());
                appendOutput("• Age type: int (32-bit integer)");
                appendOutput("• Height type: double (64-bit floating point)");
                appendOutput("• Is Adult: boolean (true/false)");
                appendOutput("• Memory usage: ~" + (name.length() * 2 + 4 + 8 + 1) + " bytes");
                appendOutput("");
                
                completedLessons = Math.max(completedLessons, 1);
                updateProgress();
                
            } catch (NumberFormatException ex) {
                appendOutput("Error: Please enter valid numbers for age and height!");
            }
        });
        
        interactiveSection.getChildren().add(inputGrid);
        content.getChildren().addAll(theorySection, interactiveSection);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }
    
    /**
     * Create Control Flow tab
     */
    private Tab createControlFlowTab() {
        Tab tab = new Tab("🔀 Control Flow");
        tab.setClosable(false);
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        // Theory section
        VBox theorySection = createTheorySection(
            "Control Flow Structures",
            "CONTROL FLOW IN JAVA:\n" +
            "• Controls the order in which statements are executed\n" +
            "• Decision structures: if, else if, else, switch\n" +
            "• Loop structures: for, while, do-while, enhanced for\n" +
            "• Jump statements: break, continue, return\n\n" +
            "IF-ELSE STATEMENTS:\n" +
            "• if (condition) { statements }\n" +
            "• Used for decision making based on conditions\n" +
            "• Can chain with else if for multiple conditions\n\n" +
            "LOOPS:\n" +
            "• for: when you know iteration count\n" +
            "• while: when condition is checked before execution\n" +
            "• do-while: when you want at least one execution"
        );
        
        // Interactive section
        VBox interactiveSection = createInteractiveSection("Control Flow Demo");
        
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        
        Label numberLabel = new Label("Enter a number (1-100):");
        TextField numberField = new TextField();
        numberField.setPromptText("e.g., 42");
        
        Button ifElseBtn = new Button("🔀 Test If-Else");
        ifElseBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        
        Button loopBtn = new Button("🔄 Demonstrate Loops");
        loopBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; -fx-font-weight: bold;");
        
        inputBox.getChildren().addAll(numberLabel, numberField, ifElseBtn, loopBtn);
        
        // Event handlers
        ifElseBtn.setOnAction(e -> demonstrateIfElse(numberField));
        loopBtn.setOnAction(e -> demonstrateLoops(numberField));
        
        interactiveSection.getChildren().add(inputBox);
        content.getChildren().addAll(theorySection, interactiveSection);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }
    
    /**
     * Create Methods tab
     */
    private Tab createMethodsTab() {
        Tab tab = new Tab("⚙️ Methods");
        tab.setClosable(false);
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        // Theory section
        VBox theorySection = createTheorySection(
            "Methods",
            "METHODS IN JAVA:\n" +
            "• Methods are blocks of code that perform specific tasks\n" +
            "• Enable code reuse and organization\n" +
            "• Can accept parameters and return values\n" +
            "• Method signature: access modifier + return type + name + parameters\n\n" +
            "METHOD SYNTAX:\n" +
            "• public static returnType methodName(parameters) { body }\n" +
            "• public: access modifier (who can call it)\n" +
            "• static: belongs to class, not instance\n" +
            "• returnType: what the method gives back (void for nothing)\n" +
            "• parameters: input values the method needs\n\n" +
            "BENEFITS:\n" +
            "• Code reusability • Better organization • Easier testing\n" +
            "• Modularity • Easier maintenance"
        );
        
        // Interactive section
        VBox interactiveSection = createInteractiveSection("Methods Demo");
        
        GridPane methodGrid = new GridPane();
        methodGrid.setHgap(10);
        methodGrid.setVgap(10);
        
        Label num1Label = new Label("First Number:");
        TextField num1Field = new TextField();
        num1Field.setPromptText("e.g., 10");
        
        Label num2Label = new Label("Second Number:");
        TextField num2Field = new TextField();
        num2Field.setPromptText("e.g., 5");
        
        Button addBtn = new Button("➕ Add");
        addBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        Button multiplyBtn = new Button("✖️ Multiply");
        multiplyBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        
        Button powerBtn = new Button("⬆️ Power");
        powerBtn.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white;");
        
        Button factorialBtn = new Button("❗ Factorial");
        factorialBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
        
        methodGrid.add(num1Label, 0, 0);
        methodGrid.add(num1Field, 1, 0);
        methodGrid.add(num2Label, 0, 1);
        methodGrid.add(num2Field, 1, 1);
        methodGrid.add(addBtn, 0, 2);
        methodGrid.add(multiplyBtn, 1, 2);
        methodGrid.add(powerBtn, 0, 3);
        methodGrid.add(factorialBtn, 1, 3);
        
        // Event handlers
        addBtn.setOnAction(e -> demonstrateAddMethod(num1Field, num2Field));
        multiplyBtn.setOnAction(e -> demonstrateMultiplyMethod(num1Field, num2Field));
        powerBtn.setOnAction(e -> demonstratePowerMethod(num1Field, num2Field));
        factorialBtn.setOnAction(e -> demonstrateFactorialMethod(num1Field));
        
        interactiveSection.getChildren().add(methodGrid);
        content.getChildren().addAll(theorySection, interactiveSection);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }
    
    /**
     * Create Arrays tab
     */
    private Tab createArraysTab() {
        Tab tab = new Tab("Arrays");
        tab.setClosable(false);
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        // Theory section
        VBox theorySection = createTheorySection(
            "Arrays",
            "ARRAYS IN JAVA:\n" +
            "• Arrays store multiple values of the same type\n" +
            "• Fixed size once created\n" +
            "• Elements accessed by index (0-based)\n" +
            "• Declaration: dataType[] arrayName = new dataType[size];\n\n" +
            "ARRAY OPERATIONS:\n" +
            "• Access: array[index]\n" +
            "• Length: array.length\n" +
            "• Initialization: {value1, value2, value3}\n" +
            "• Iteration: for loops or enhanced for loops\n\n" +
            "COMMON USES:\n" +
            "• Storing lists of data • Mathematical operations\n" +
            "• Lookup tables • Temporary storage"
        );
        
        // Interactive section
        VBox interactiveSection = createInteractiveSection("Arrays Demo");
        
        VBox arrayControls = new VBox(10);
        
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        
        Label arrayLabel = new Label("Enter numbers (comma-separated):");
        TextField arrayField = new TextField();
        arrayField.setPromptText("e.g., 5,2,8,1,9,3");
        arrayField.setPrefWidth(200);
        
        inputBox.getChildren().addAll(arrayLabel, arrayField);
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        
        Button createBtn = new Button("Create Array");
        createBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        Button analyzeBtn = new Button("Analyze Array");
        analyzeBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        
        Button sortBtn = new Button("📈 Sort Array");
        sortBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        
        Button searchBtn = new Button("🔎 Search Array");
        searchBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
        
        buttonBox.getChildren().addAll(createBtn, analyzeBtn, sortBtn, searchBtn);
        
        // Event handlers
        createBtn.setOnAction(e -> demonstrateArrayCreation(arrayField));
        analyzeBtn.setOnAction(e -> demonstrateArrayAnalysis(arrayField));
        sortBtn.setOnAction(e -> demonstrateArraySorting(arrayField));
        searchBtn.setOnAction(e -> demonstrateArraySearch(arrayField));
        
        arrayControls.getChildren().addAll(inputBox, buttonBox);
        interactiveSection.getChildren().add(arrayControls);
        content.getChildren().addAll(theorySection, interactiveSection);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }
    
    /**
     * Create Strings tab
     */
    private Tab createStringsTab() {
        Tab tab = new Tab("📝 Strings");
        tab.setClosable(false);
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        // Theory section
        VBox theorySection = createTheorySection(
            "Strings",
            "STRINGS IN JAVA:\n" +
            "• Strings are objects that represent text\n" +
            "• Immutable - cannot be changed once created\n" +
            "• String literal: \"Hello World\"\n" +
            "• String object: new String(\"Hello\")\n\n" +
            "COMMON STRING METHODS:\n" +
            "• length() - get string length\n" +
            "• charAt(index) - get character at position\n" +
            "• substring(start, end) - extract part of string\n" +
            "• toUpperCase(), toLowerCase() - change case\n" +
            "• contains(text) - check if contains substring\n" +
            "• split(delimiter) - split into array\n\n" +
            "STRING CONCATENATION:\n" +
            "• Using +: \"Hello\" + \" World\"\n" +
            "• Using StringBuilder for efficiency"
        );
        
        // Interactive section
        VBox interactiveSection = createInteractiveSection("Strings Demo");
        
        VBox stringControls = new VBox(10);
        
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        
        Label textLabel = new Label("Enter text:");
        TextField textField = new TextField();
        textField.setPromptText("e.g., Hello World Java Programming");
        textField.setPrefWidth(300);
        
        inputBox.getChildren().addAll(textLabel, textField);
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        
        Button analyzeBtn = new Button("Analyze String");
        analyzeBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        Button manipulateBtn = new Button("✂️ Manipulate String");
        manipulateBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        
        Button searchBtn = new Button("🔎 Search & Replace");
        searchBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        
        Button builderBtn = new Button("🏗️ StringBuilder Demo");
        builderBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
        
        buttonBox.getChildren().addAll(analyzeBtn, manipulateBtn, searchBtn, builderBtn);
        
        // Event handlers
        analyzeBtn.setOnAction(e -> demonstrateStringAnalysis(textField));
        manipulateBtn.setOnAction(e -> demonstrateStringManipulation(textField));
        searchBtn.setOnAction(e -> demonstrateStringSearch(textField));
        builderBtn.setOnAction(e -> demonstrateStringBuilder(textField));
        
        stringControls.getChildren().addAll(inputBox, buttonBox);
        interactiveSection.getChildren().add(stringControls);
        content.getChildren().addAll(theorySection, interactiveSection);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }
    
    /**
     * Create Collections tab
     */
    private Tab createCollectionsTab() {
        Tab tab = new Tab("💾 Collections");
        tab.setClosable(false);
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        // Theory section
        VBox theorySection = createTheorySection(
            "Collections",
            "COLLECTIONS IN JAVA:\n" +
            "• Framework for storing and manipulating groups of objects\n" +
            "• Dynamic size (unlike arrays)\n" +
            "• Common interfaces: List, Set, Map\n" +
            "• Common implementations: ArrayList, LinkedList, HashMap, HashSet\n\n" +
            "LIST INTERFACE:\n" +
            "• Ordered collection (maintains insertion order)\n" +
            "• Allows duplicates\n" +
            "• Examples: ArrayList, LinkedList, Vector\n\n" +
            "SET INTERFACE:\n" +
            "• No duplicates allowed\n" +
            "• Examples: HashSet, TreeSet, LinkedHashSet\n\n" +
            "MAP INTERFACE:\n" +
            "• Key-value pairs\n" +
            "• Keys must be unique\n" +
            "• Examples: HashMap, TreeMap, LinkedHashMap"
        );
        
        // Interactive section
        VBox interactiveSection = createInteractiveSection("Collections Demo");
        
        VBox collectionControls = new VBox(10);
        
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        
        Label itemsLabel = new Label("Enter items (comma-separated):");
        TextField itemsField = new TextField();
        itemsField.setPromptText("e.g., apple,banana,apple,cherry,banana");
        itemsField.setPrefWidth(300);
        
        inputBox.getChildren().addAll(itemsLabel, itemsField);
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        
        Button listBtn = new Button("📋 ArrayList Demo");
        listBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        Button setBtn = new Button("🔗 HashSet Demo");
        setBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        
        Button mapBtn = new Button("🗺️ HashMap Demo");
        mapBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        
        Button compareBtn = new Button("⚖️ Compare Collections");
        compareBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
        
        buttonBox.getChildren().addAll(listBtn, setBtn, mapBtn, compareBtn);
        
        // Event handlers
        listBtn.setOnAction(e -> demonstrateArrayList(itemsField));
        setBtn.setOnAction(e -> demonstrateHashSet(itemsField));
        mapBtn.setOnAction(e -> demonstrateHashMap(itemsField));
        compareBtn.setOnAction(e -> demonstrateCollectionComparison(itemsField));
        
        collectionControls.getChildren().addAll(inputBox, buttonBox);
        interactiveSection.getChildren().add(collectionControls);
        content.getChildren().addAll(theorySection, interactiveSection);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        
        return tab;
    }
    
    /**
     * Create output panel
     */
    private VBox createOutputPanel() {
        VBox outputPanel = new VBox(10);
        outputPanel.setPadding(new Insets(15));
        outputPanel.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #ddd; -fx-border-width: 1px;");
        
        Label outputLabel = new Label("Output Console");
        outputLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(8);
        outputArea.setStyle("-fx-font-family: 'Courier New', monospace; -fx-font-size: 12px; " +
                          "-fx-background-color: #f4f4f4; -fx-text-fill: #000000;");
        
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        
        Label noteLabel = new Label("Notes:");
        inputField = new TextField();
        inputField.setPromptText("Enter your notes here...");
        inputField.setPrefWidth(400);
        
        Button clearBtn = new Button("🗑️ Clear");
        clearBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        
        inputBox.getChildren().addAll(noteLabel, inputField, clearBtn);
        
        // Event handlers
        inputField.setOnAction(e -> {
            String note = inputField.getText().trim();
            if (!note.isEmpty()) {
                appendOutput("Note: " + note);
                inputField.clear();
            }
        });
        
        clearBtn.setOnAction(e -> outputArea.clear());
        
        outputPanel.getChildren().addAll(outputLabel, outputArea, inputBox);
        outputPanel.setPrefHeight(250);
        
        return outputPanel;
    }
    
    /**
     * Helper method to create theory sections
     */
    private VBox createTheorySection(String title, String content) {
        VBox section = new VBox(10);
        section.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; " +
                        "-fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 15px;");
        
        Label titleLabel = new Label("📖 Theory: " + title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        
        TextArea theoryArea = new TextArea(content);
        theoryArea.setEditable(false);
        theoryArea.setPrefRowCount(8);
        theoryArea.setStyle("-fx-font-family: 'Courier New', monospace; -fx-font-size: 12px; " +
                          "-fx-background-color: #ffffff; -fx-border-color: #ced4da;");
        
        section.getChildren().addAll(titleLabel, theoryArea);
        return section;
    }
    
    /**
     * Helper method to create interactive sections
     */
    private VBox createInteractiveSection(String title) {
        VBox section = new VBox(10);
        section.setStyle("-fx-background-color: #e8f5e8; -fx-border-color: #4CAF50; " +
                        "-fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 15px;");
        
        Label titleLabel = new Label("🧪 Interactive: " + title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web("#2e7d32"));
        
        section.getChildren().add(titleLabel);
        return section;
    }
    
    // ============ DEMONSTRATION METHODS ============
    
    private void demonstrateIfElse(TextField numberField) {
        try {
            int number = Integer.parseInt(numberField.getText().trim());
            
            appendOutput("=== IF-ELSE DEMONSTRATION ===");
            appendOutput("Testing number: " + number);
            appendOutput("");
            appendOutput("// If-else logic:");
            appendOutput("if (number > 50) {");
            
            if (number > 50) {
                appendOutput("    System.out.println(\"Large number!\");");
                appendOutput("} // This block executed ✅");
            } else {
                appendOutput("    System.out.println(\"Large number!\");");
                appendOutput("} // This block skipped ❌");
            }
            
            appendOutput("else if (number > 25) {");
            if (number <= 50 && number > 25) {
                appendOutput("    System.out.println(\"Medium number!\");");
                appendOutput("} // This block executed ✅");
            } else {
                appendOutput("    System.out.println(\"Medium number!\");");
                appendOutput("} // This block skipped ❌");
            }
            
            appendOutput("else {");
            if (number <= 25) {
                appendOutput("    System.out.println(\"Small number!\");");
                appendOutput("} // This block executed ✅");
            } else {
                appendOutput("    System.out.println(\"Small number!\");");
                appendOutput("} // This block skipped ❌");
            }
            
            appendOutput("");
            appendOutput("Result: " + (number > 50 ? "Large" : number > 25 ? "Medium" : "Small") + " number!");
            appendOutput("");
            
            completedLessons = Math.max(completedLessons, 2);
            updateProgress();
            
        } catch (NumberFormatException ex) {
            appendOutput("❌ Error: Please enter a valid number!");
        }
    }
    
    private void demonstrateLoops(TextField numberField) {
        try {
            int number = Integer.parseInt(numberField.getText().trim());
            if (number < 1 || number > 10) {
                appendOutput("❌ Please enter a number between 1 and 10 for loop demo!");
                return;
            }
            
            appendOutput("=== LOOP DEMONSTRATION ===");
            appendOutput("Creating multiplication table for: " + number);
            appendOutput("");
            
            appendOutput("// For loop:");
            appendOutput("for (int i = 1; i <= 10; i++) {");
            appendOutput("    System.out.println(\"" + number + " x \" + i + \" = \" + (" + number + " * i));");
            appendOutput("}");
            appendOutput("");
            appendOutput("Result: Output:");
            
            for (int i = 1; i <= 10; i++) {
                appendOutput(number + " x " + i + " = " + (number * i));
            }
            appendOutput("");
            
            completedLessons = Math.max(completedLessons, 2);
            updateProgress();
            
        } catch (NumberFormatException ex) {
            appendOutput("❌ Error: Please enter a valid number!");
        }
    }
    
    private void demonstrateAddMethod(TextField num1Field, TextField num2Field) {
        try {
            double num1 = Double.parseDouble(num1Field.getText().trim());
            double num2 = Double.parseDouble(num2Field.getText().trim());
            
            appendOutput("=== ADD METHOD DEMONSTRATION ===");
            appendOutput("// Method definition:");
            appendOutput("public static double add(double a, double b) {");
            appendOutput("    double result = a + b;");
            appendOutput("    return result;");
            appendOutput("}");
            appendOutput("");
            appendOutput("// Method call:");
            appendOutput("double result = add(" + num1 + ", " + num2 + ");");
            appendOutput("");
            appendOutput("🔢 Step-by-step execution:");
            appendOutput("1. Method called with parameters: a=" + num1 + ", b=" + num2);
            appendOutput("2. Calculate: " + num1 + " + " + num2 + " = " + (num1 + num2));
            appendOutput("3. Return result: " + (num1 + num2));
            appendOutput("");
            appendOutput("✅ Final result: " + add(num1, num2));
            appendOutput("");
            
            completedLessons = Math.max(completedLessons, 3);
            updateProgress();
            
        } catch (NumberFormatException ex) {
            appendOutput("❌ Error: Please enter valid numbers!");
        }
    }
    
    private void demonstrateMultiplyMethod(TextField num1Field, TextField num2Field) {
        try {
            double num1 = Double.parseDouble(num1Field.getText().trim());
            double num2 = Double.parseDouble(num2Field.getText().trim());
            double result = multiply(num1, num2);
            
            appendOutput("=== MULTIPLY METHOD DEMONSTRATION ===");
            appendOutput("// Method: public static double multiply(double a, double b)");
            appendOutput("Result: " + num1 + " × " + num2 + " = " + result);
            appendOutput("");
            
        } catch (NumberFormatException ex) {
            appendOutput("❌ Error: Please enter valid numbers!");
        }
    }
    
    private void demonstratePowerMethod(TextField num1Field, TextField num2Field) {
        try {
            double base = Double.parseDouble(num1Field.getText().trim());
            double exponent = Double.parseDouble(num2Field.getText().trim());
            double result = power(base, exponent);
            
            appendOutput("=== POWER METHOD DEMONSTRATION ===");
            appendOutput("// Method: public static double power(double base, double exponent)");
            appendOutput("Result: " + base + "^" + exponent + " = " + result);
            appendOutput("");
            
        } catch (NumberFormatException ex) {
            appendOutput("❌ Error: Please enter valid numbers!");
        }
    }
    
    private void demonstrateFactorialMethod(TextField numField) {
        try {
            int num = Integer.parseInt(numField.getText().trim());
            if (num < 0 || num > 10) {
                appendOutput("❌ Please enter a number between 0 and 10!");
                return;
            }
            
            appendOutput("=== FACTORIAL METHOD DEMONSTRATION ===");
            appendOutput("// Recursive method definition:");
            appendOutput("public static long factorial(int n) {");
            appendOutput("    if (n <= 1) {");
            appendOutput("        return 1;  // Base case");
            appendOutput("    }");
            appendOutput("    return n * factorial(n - 1);  // Recursive call");
            appendOutput("}");
            appendOutput("");
            appendOutput("// Method call:");
            appendOutput("long result = factorial(" + num + ");");
            appendOutput("");
            appendOutput("🔄 Recursive execution trace:");
            
            long result = demonstrateFactorial(num, 1);
            
            appendOutput("");
            appendOutput("✅ Final result: " + num + "! = " + result);
            appendOutput("");
            
        } catch (NumberFormatException ex) {
            appendOutput("❌ Error: Please enter a valid integer!");
        }
    }
    
    private long demonstrateFactorial(int n, int depth) {
        String indent = "  ".repeat(depth);
        if (n <= 1) {
            appendOutput(indent + "factorial(" + n + ") = 1 (base case)");
            return 1;
        }
        
        appendOutput(indent + "factorial(" + n + ") = " + n + " * factorial(" + (n-1) + ")");
        long subResult = demonstrateFactorial(n - 1, depth + 1);
        long result = n * subResult;
        appendOutput(indent + "factorial(" + n + ") = " + n + " * " + subResult + " = " + result);
        
        return result;
    }
    
    private void demonstrateArrayCreation(TextField arrayField) {
        try {
            String input = arrayField.getText().trim();
            String[] stringNumbers = input.split(",");
            int[] numbers = new int[stringNumbers.length];
            
            for (int i = 0; i < stringNumbers.length; i++) {
                numbers[i] = Integer.parseInt(stringNumbers[i].trim());
            }
            
            appendOutput("=== ARRAY CREATION DEMONSTRATION ===");
            appendOutput("// Step 1: Declare array");
            appendOutput("int[] numbers = new int[" + numbers.length + "];");
            appendOutput("");
            appendOutput("// Step 2: Initialize with values");
            for (int i = 0; i < numbers.length; i++) {
                appendOutput("numbers[" + i + "] = " + numbers[i] + ";");
            }
            appendOutput("");
            appendOutput("// Alternative: Array literal");
            appendOutput("int[] numbers = {" + String.join(", ", input.split(",")) + "};");
            appendOutput("");
            appendOutput("Result: Array created successfully!");
            appendOutput("• Length: " + numbers.length);
            appendOutput("• Type: int[]");
            appendOutput("• Memory: ~" + (numbers.length * 4) + " bytes");
            appendOutput("");
            
            completedLessons = Math.max(completedLessons, 4);
            updateProgress();
            
        } catch (NumberFormatException ex) {
            appendOutput("❌ Error: Please enter valid integers separated by commas!");
        }
    }
    
    private void demonstrateArrayAnalysis(TextField arrayField) {
        try {
            String input = arrayField.getText().trim();
            String[] stringNumbers = input.split(",");
            int[] numbers = new int[stringNumbers.length];
            
            for (int i = 0; i < stringNumbers.length; i++) {
                numbers[i] = Integer.parseInt(stringNumbers[i].trim());
            }
            
            appendOutput("=== ARRAY ANALYSIS DEMONSTRATION ===");
            appendOutput("// Iterating through array");
            appendOutput("for (int i = 0; i < numbers.length; i++) {");
            appendOutput("    System.out.println(\"Element \" + i + \": \" + numbers[i]);");
            appendOutput("}");
            appendOutput("");
            appendOutput("Result: Array contents:");
            
            int sum = 0;
            int min = numbers[0];
            int max = numbers[0];
            
            for (int i = 0; i < numbers.length; i++) {
                appendOutput("Element " + i + ": " + numbers[i]);
                sum += numbers[i];
                if (numbers[i] < min) min = numbers[i];
                if (numbers[i] > max) max = numbers[i];
            }
            
            appendOutput("");
            appendOutput("🔢 Statistics:");
            appendOutput("• Sum: " + sum);
            appendOutput("• Average: " + (double)sum / numbers.length);
            appendOutput("• Minimum: " + min);
            appendOutput("• Maximum: " + max);
            appendOutput("");
            
        } catch (NumberFormatException ex) {
            appendOutput("❌ Error: Please enter valid integers!");
        }
    }
    
    private void demonstrateArraySorting(TextField arrayField) {
        try {
            String input = arrayField.getText().trim();
            String[] stringNumbers = input.split(",");
            int[] numbers = new int[stringNumbers.length];
            
            for (int i = 0; i < stringNumbers.length; i++) {
                numbers[i] = Integer.parseInt(stringNumbers[i].trim());
            }
            
            appendOutput("=== ARRAY SORTING DEMONSTRATION ===");
            appendOutput("Original array: " + Arrays.toString(numbers));
            appendOutput("");
            
            // Bubble sort with steps
            int[] sortedArray = numbers.clone();
            appendOutput("// Bubble sort algorithm:");
            appendOutput("for (int i = 0; i < array.length - 1; i++) {");
            appendOutput("    for (int j = 0; j < array.length - i - 1; j++) {");
            appendOutput("        if (array[j] > array[j + 1]) {");
            appendOutput("            // Swap elements");
            appendOutput("            int temp = array[j];");
            appendOutput("            array[j] = array[j + 1];");
            appendOutput("            array[j + 1] = temp;");
            appendOutput("        }");
            appendOutput("    }");
            appendOutput("}");
            appendOutput("");
            
            for (int i = 0; i < sortedArray.length - 1; i++) {
                for (int j = 0; j < sortedArray.length - i - 1; j++) {
                    if (sortedArray[j] > sortedArray[j + 1]) {
                        int temp = sortedArray[j];
                        sortedArray[j] = sortedArray[j + 1];
                        sortedArray[j + 1] = temp;
                        appendOutput("Swap: " + Arrays.toString(sortedArray));
                    }
                }
            }
            
            appendOutput("");
            appendOutput("✅ Sorted array: " + Arrays.toString(sortedArray));
            appendOutput("");
            
        } catch (NumberFormatException ex) {
            appendOutput("❌ Error: Please enter valid integers!");
        }
    }
    
    private void demonstrateArraySearch(TextField arrayField) {
        try {
            String input = arrayField.getText().trim();
            String[] stringNumbers = input.split(",");
            int[] numbers = new int[stringNumbers.length];
            
            for (int i = 0; i < stringNumbers.length; i++) {
                numbers[i] = Integer.parseInt(stringNumbers[i].trim());
            }
            
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Array Search");
            dialog.setHeaderText("Enter value to search:");
            dialog.setContentText("Value:");
            
            Optional<String> result = dialog.showAndWait();
            if (!result.isPresent()) return;
            
            int target = Integer.parseInt(result.get().trim());
            
            appendOutput("=== ARRAY SEARCH DEMONSTRATION ===");
            appendOutput("Searching for: " + target);
            appendOutput("In array: " + Arrays.toString(numbers));
            appendOutput("");
            appendOutput("// Linear search algorithm:");
            appendOutput("for (int i = 0; i < array.length; i++) {");
            appendOutput("    if (array[i] == target) {");
            appendOutput("        return i;  // Found at index i");
            appendOutput("    }");
            appendOutput("}");
            appendOutput("return -1;  // Not found");
            appendOutput("");
            appendOutput("🔎 Search steps:");
            
            int foundIndex = -1;
            for (int i = 0; i < numbers.length; i++) {
                appendOutput("Check index " + i + ": " + numbers[i] + " == " + target + " ? " + (numbers[i] == target));
                if (numbers[i] == target) {
                    foundIndex = i;
                    break;
                }
            }
            
            appendOutput("");
            if (foundIndex != -1) {
                appendOutput("✅ Found at index: " + foundIndex);
            } else {
                appendOutput("❌ Value not found in array");
            }
            appendOutput("");
            
        } catch (NumberFormatException ex) {
            appendOutput("❌ Error: Please enter valid integers!");
        }
    }
    
    private void demonstrateStringAnalysis(TextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            appendOutput("❌ Please enter some text!");
            return;
        }
        
        appendOutput("=== STRING ANALYSIS DEMONSTRATION ===");
        appendOutput("Analyzing text: \"" + text + "\"");
        appendOutput("");
        appendOutput("// String methods:");
        appendOutput("String text = \"" + text + "\";");
        appendOutput("text.length() = " + text.length());
        appendOutput("text.charAt(0) = '" + (text.length() > 0 ? text.charAt(0) : "N/A") + "'");
        appendOutput("text.toUpperCase() = \"" + text.toUpperCase() + "\"");
        appendOutput("text.toLowerCase() = \"" + text.toLowerCase() + "\"");
        appendOutput("");
        
        // Character analysis
        int vowels = 0, consonants = 0, digits = 0, spaces = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if ("aeiouAEIOU".indexOf(c) >= 0) vowels++;
                else consonants++;
            } else if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isWhitespace(c)) {
                spaces++;
            }
        }
        
        appendOutput("Result: Character analysis:");
        appendOutput("• Total characters: " + text.length());
        appendOutput("• Vowels: " + vowels);
        appendOutput("• Consonants: " + consonants);
        appendOutput("• Digits: " + digits);
        appendOutput("• Spaces: " + spaces);
        appendOutput("");
        
        completedLessons = Math.max(completedLessons, 5);
        updateProgress();
    }
    
    private void demonstrateStringManipulation(TextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            appendOutput("❌ Please enter some text!");
            return;
        }
        
        appendOutput("=== STRING MANIPULATION DEMONSTRATION ===");
        appendOutput("Original: \"" + text + "\"");
        appendOutput("");
        
        if (text.length() >= 3) {
            String substring = text.substring(0, Math.min(3, text.length()));
            appendOutput("text.substring(0, 3) = \"" + substring + "\"");
        }
        
        String reversed = new StringBuilder(text).reverse().toString();
        appendOutput("Reversed = \"" + reversed + "\"");
        
        String[] words = text.split("\\s+");
        appendOutput("text.split(\" \") = " + Arrays.toString(words));
        appendOutput("Word count: " + words.length);
        
        String trimmed = text.trim();
        appendOutput("text.trim() = \"" + trimmed + "\"");
        
        appendOutput("");
    }
    
    private void demonstrateStringSearch(TextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            appendOutput("❌ Please enter some text!");
            return;
        }
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("String Search");
        dialog.setHeaderText("Enter text to search for:");
        dialog.setContentText("Search term:");
        
        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent() || result.get().isEmpty()) return;
        
        String searchTerm = result.get();
        
        appendOutput("=== STRING SEARCH DEMONSTRATION ===");
        appendOutput("Text: \"" + text + "\"");
        appendOutput("Searching for: \"" + searchTerm + "\"");
        appendOutput("");
        
        boolean contains = text.contains(searchTerm);
        int indexOf = text.indexOf(searchTerm);
        int lastIndexOf = text.lastIndexOf(searchTerm);
        
        appendOutput("text.contains(\"" + searchTerm + "\") = " + contains);
        appendOutput("text.indexOf(\"" + searchTerm + "\") = " + indexOf);
        appendOutput("text.lastIndexOf(\"" + searchTerm + "\") = " + lastIndexOf);
        
        if (contains) {
            TextInputDialog replaceDialog = new TextInputDialog();
            replaceDialog.setTitle("String Replace");
            replaceDialog.setHeaderText("Enter replacement text:");
            replaceDialog.setContentText("Replace with:");
            
            Optional<String> replaceResult = replaceDialog.showAndWait();
            if (replaceResult.isPresent()) {
                String replacement = replaceResult.get();
                String replaced = text.replace(searchTerm, replacement);
                appendOutput("text.replace(\"" + searchTerm + "\", \"" + replacement + "\") = \"" + replaced + "\"");
            }
        }
        
        appendOutput("");
    }
    
    private void demonstrateStringBuilder(TextField textField) {
        String text = textField.getText();
        
        appendOutput("=== STRINGBUILDER DEMONSTRATION ===");
        appendOutput("// StringBuilder is mutable and efficient for concatenation");
        appendOutput("StringBuilder sb = new StringBuilder();");
        appendOutput("");
        
        StringBuilder sb = new StringBuilder();
        appendOutput("sb.append(\"" + text + "\");");
        sb.append(text);
        
        appendOutput("sb.append(\" - Added text\");");
        sb.append(" - Added text");
        
        appendOutput("sb.insert(0, \"Start: \");");
        sb.insert(0, "Start: ");
        
        appendOutput("sb.reverse();");
        String beforeReverse = sb.toString();
        sb.reverse();
        
        appendOutput("");
        appendOutput("Result: Results:");
        appendOutput("• Before reverse: \"" + beforeReverse + "\"");
        appendOutput("• After reverse: \"" + sb.toString() + "\"");
        appendOutput("• Final length: " + sb.length());
        appendOutput("");
    }
    
    private void demonstrateArrayList(TextField itemsField) {
        String input = itemsField.getText().trim();
        if (input.isEmpty()) {
            appendOutput("❌ Please enter some items!");
            return;
        }
        
        String[] items = input.split(",");
        
        appendOutput("=== ARRAYLIST DEMONSTRATION ===");
        appendOutput("// Creating and using ArrayList");
        appendOutput("ArrayList<String> list = new ArrayList<>();");
        appendOutput("");
        
        ArrayList<String> list = new ArrayList<>();
        
        appendOutput("// Adding elements:");
        for (String item : items) {
            String trimmedItem = item.trim();
            list.add(trimmedItem);
            appendOutput("list.add(\"" + trimmedItem + "\");  // Size: " + list.size());
        }
        
        appendOutput("");
        appendOutput("// List operations:");
        appendOutput("list.size() = " + list.size());
        appendOutput("list.get(0) = \"" + (list.size() > 0 ? list.get(0) : "N/A") + "\"");
        appendOutput("list.contains(\"" + (list.size() > 0 ? list.get(0) : "test") + "\") = " + 
                   (list.size() > 0 ? list.contains(list.get(0)) : false));
        
        appendOutput("");
        appendOutput("// Iterating through list:");
        appendOutput("for (String item : list) {");
        for (String item : list) {
            appendOutput("    System.out.println(item);  // " + item);
        }
        appendOutput("}");
        appendOutput("");
        
        completedLessons = Math.max(completedLessons, 6);
        updateProgress();
    }
    
    private void demonstrateHashSet(TextField itemsField) {
        String input = itemsField.getText().trim();
        if (input.isEmpty()) {
            appendOutput("❌ Please enter some items!");
            return;
        }
        
        String[] items = input.split(",");
        
        appendOutput("=== HASHSET DEMONSTRATION ===");
        appendOutput("// HashSet automatically removes duplicates");
        appendOutput("HashSet<String> set = new HashSet<>();");
        appendOutput("");
        
        HashSet<String> set = new HashSet<>();
        
        appendOutput("// Adding elements (duplicates will be ignored):");
        for (String item : items) {
            String trimmedItem = item.trim();
            boolean added = set.add(trimmedItem);
            appendOutput("set.add(\"" + trimmedItem + "\") = " + added + 
                       "  // " + (added ? "Added" : "Duplicate, not added"));
        }
        
        appendOutput("");
        appendOutput("Result: Results:");
        appendOutput("• Original items: " + items.length);
        appendOutput("• Unique items in set: " + set.size());
        appendOutput("• Set contents: " + set);
        appendOutput("");
    }
    
    private void demonstrateHashMap(TextField itemsField) {
        String input = itemsField.getText().trim();
        if (input.isEmpty()) {
            appendOutput("❌ Please enter some items!");
            return;
        }
        
        String[] items = input.split(",");
        
        appendOutput("=== HASHMAP DEMONSTRATION ===");
        appendOutput("// HashMap stores key-value pairs");
        appendOutput("HashMap<String, Integer> map = new HashMap<>();");
        appendOutput("");
        
        HashMap<String, Integer> map = new HashMap<>();
        
        appendOutput("// Adding items and counting occurrences:");
        for (String item : items) {
            String trimmedItem = item.trim();
            Integer count = map.get(trimmedItem);
            if (count == null) {
                map.put(trimmedItem, 1);
                appendOutput("map.put(\"" + trimmedItem + "\", 1);  // First occurrence");
            } else {
                map.put(trimmedItem, count + 1);
                appendOutput("map.put(\"" + trimmedItem + "\", " + (count + 1) + ");  // Increment count");
            }
        }
        
        appendOutput("");
        appendOutput("Result: Item frequency:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            appendOutput("• \"" + entry.getKey() + "\": " + entry.getValue() + " times");
        }
        appendOutput("");
    }
    
    private void demonstrateCollectionComparison(TextField itemsField) {
        String input = itemsField.getText().trim();
        if (input.isEmpty()) {
            appendOutput("❌ Please enter some items!");
            return;
        }
        
        String[] items = input.split(",");
        
        appendOutput("=== COLLECTION COMPARISON ===");
        appendOutput("Comparing ArrayList vs HashSet vs HashMap with same data:");
        appendOutput("");
        
        // ArrayList
        ArrayList<String> list = new ArrayList<>();
        for (String item : items) {
            list.add(item.trim());
        }
        
        // HashSet
        HashSet<String> set = new HashSet<>();
        for (String item : items) {
            set.add(item.trim());
        }
        
        // HashMap
        HashMap<String, Integer> map = new HashMap<>();
        for (String item : items) {
            String trimmedItem = item.trim();
            map.put(trimmedItem, map.getOrDefault(trimmedItem, 0) + 1);
        }
        
        appendOutput("Result: Comparison Results:");
        appendOutput("• ArrayList size: " + list.size() + " (keeps duplicates)");
        appendOutput("• HashSet size: " + set.size() + " (removes duplicates)");
        appendOutput("• HashMap size: " + map.size() + " (unique keys with counts)");
        appendOutput("");
        
        appendOutput("When to use each:");
        appendOutput("• ArrayList: When you need ordered data with duplicates");
        appendOutput("• HashSet: When you need unique items only");
        appendOutput("• HashMap: When you need key-value relationships");
        appendOutput("");
    }
    
    // Helper methods
    private double add(double a, double b) { return a + b; }
    private double multiply(double a, double b) { return a * b; }
    private double power(double base, double exponent) { return Math.pow(base, exponent); }
    
    /**
     * Append text to output area
     */
    private void appendOutput(String text) {
        Platform.runLater(() -> {
            outputArea.appendText(text + "\n");
        });
    }
    
    /**
     * Update progress status
     */
    private void updateProgress() {
        Platform.runLater(() -> {
            double progress = (double) completedLessons / totalLessons;
            progressBar.setProgress(progress);
            
            statusLabel.setText(String.format("🎓 Progress: %d/%d lessons completed (%.1f%%)", 
                completedLessons, totalLessons, progress * 100));
            
            if (completedLessons == totalLessons) {
                statusLabel.setText("🏆 Congratulations! All Java Basics lessons completed!");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Learning Complete!");
                alert.setHeaderText("🎉 Excellent work! You've completed all Java Basics lessons!");
                alert.setContentText("Next steps:\n" +
                    "• Practice with more complex examples\n" +
                    "• Explore Object-Oriented Programming\n" +
                    "• Learn about Data Structures\n" +
                    "• Study Advanced Java concepts");
                alert.showAndWait();
            }
        });
    }
    
    /**
     * Main method to launch the learning platform
     */
    public static void main(String[] args) {
        launch(args);
    }
}
