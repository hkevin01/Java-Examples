package oop;

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
 * OOPLearningGUI - Interactive JavaFX Learning Platform for Object-Oriented Programming
 * 
 * EDUCATIONAL PURPOSE:
 * This JavaFX GUI provides an interactive environment to learn and experiment with:
 * - Classes and Objects
 * - Inheritance and Polymorphism
 * - Encapsulation and Abstraction
 * - Interfaces and Abstract Classes
 * - Design Patterns
 * - SOLID Principles
 * 
 * LEARNING APPROACH:
 * - Interactive class design demonstrations
 * - Real-time object creation and manipulation
 * - Inheritance hierarchy visualizations
 * - Design pattern implementations
 * - Best practices examples
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class OOPLearningGUI extends Application {
    
    private TextArea outputArea;
    private TextField inputField;
    private Label statusLabel;
    private ComboBox<String> conceptSelector;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("OOP Learning Platform");
        
        // Create main layout using GridPane
        GridPane mainLayout = new GridPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setHgap(10);
        mainLayout.setVgap(10);
        
        // Configure column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        mainLayout.getColumnConstraints().addAll(col1, col2);
        
        // Create sections
        createHeaderSection(mainLayout);
        createCoreConceptsSection(mainLayout);
        createAdvancedConceptsSection(mainLayout);
        createInteractiveSection(mainLayout);
        createOutputSection(mainLayout);
        createControlSection(mainLayout);
        
        Scene scene = new Scene(mainLayout, 1100, 750);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        appendOutput("Welcome to Object-Oriented Programming Learning Platform!");
        appendOutput("Learn OOP concepts through interactive examples and demonstrations.");
        appendOutput("Click any concept button to see detailed explanations and code examples.\n");
    }
    
    private void createHeaderSection(GridPane layout) {
        Label titleLabel = new Label("Object-Oriented Programming Learning Platform");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        statusLabel = new Label("Ready to learn OOP concepts!");
        statusLabel.setStyle("-fx-text-fill: #2e7d32;");
        
        VBox headerBox = new VBox(5);
        headerBox.getChildren().addAll(titleLabel, statusLabel);
        headerBox.setAlignment(Pos.CENTER);
        
        layout.add(headerBox, 0, 0, 2, 1);
    }
    
    private void createCoreConceptsSection(GridPane layout) {
        Label sectionLabel = new Label("1. Core OOP Concepts");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Button classesBtn = new Button("Classes & Objects");
        classesBtn.setMaxWidth(Double.MAX_VALUE);
        classesBtn.setOnAction(e -> demonstrateClassesAndObjects());
        
        Button encapsulationBtn = new Button("Encapsulation");
        encapsulationBtn.setMaxWidth(Double.MAX_VALUE);
        encapsulationBtn.setOnAction(e -> demonstrateEncapsulation());
        
        Button inheritanceBtn = new Button("Inheritance");
        inheritanceBtn.setMaxWidth(Double.MAX_VALUE);
        inheritanceBtn.setOnAction(e -> demonstrateInheritance());
        
        Button polymorphismBtn = new Button("Polymorphism");
        polymorphismBtn.setMaxWidth(Double.MAX_VALUE);
        polymorphismBtn.setOnAction(e -> demonstratePolymorphism());
        
        Button abstractionBtn = new Button("Abstraction");
        abstractionBtn.setMaxWidth(Double.MAX_VALUE);
        abstractionBtn.setOnAction(e -> demonstrateAbstraction());
        
        VBox coreBox = new VBox(5);
        coreBox.getChildren().addAll(sectionLabel, classesBtn, encapsulationBtn, 
                                    inheritanceBtn, polymorphismBtn, abstractionBtn);
        coreBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(coreBox, 0, 1);
    }
    
    private void createAdvancedConceptsSection(GridPane layout) {
        Label sectionLabel = new Label("2. Advanced OOP Concepts");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Button interfacesBtn = new Button("Interfaces");
        interfacesBtn.setMaxWidth(Double.MAX_VALUE);
        interfacesBtn.setOnAction(e -> demonstrateInterfaces());
        
        Button designPatternsBtn = new Button("Design Patterns");
        designPatternsBtn.setMaxWidth(Double.MAX_VALUE);
        designPatternsBtn.setOnAction(e -> demonstrateDesignPatterns());
        
        Button solidBtn = new Button("SOLID Principles");
        solidBtn.setMaxWidth(Double.MAX_VALUE);
        solidBtn.setOnAction(e -> demonstrateSOLIDPrinciples());
        
        Button compositionBtn = new Button("Composition vs Inheritance");
        compositionBtn.setMaxWidth(Double.MAX_VALUE);
        compositionBtn.setOnAction(e -> demonstrateComposition());
        
        Button bestPracticesBtn = new Button("OOP Best Practices");
        bestPracticesBtn.setMaxWidth(Double.MAX_VALUE);
        bestPracticesBtn.setOnAction(e -> demonstrateBestPractices());
        
        VBox advancedBox = new VBox(5);
        advancedBox.getChildren().addAll(sectionLabel, interfacesBtn, designPatternsBtn,
                                       solidBtn, compositionBtn, bestPracticesBtn);
        advancedBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(advancedBox, 1, 1);
    }
    
    private void createInteractiveSection(GridPane layout) {
        Label sectionLabel = new Label("Interactive Examples");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        conceptSelector = new ComboBox<>();
        conceptSelector.getItems().addAll(
            "Create Simple Class",
            "Demonstrate Inheritance",
            "Show Polymorphism",
            "Interface Implementation",
            "Design Pattern Example"
        );
        conceptSelector.setValue("Create Simple Class");
        conceptSelector.setMaxWidth(Double.MAX_VALUE);
        
        Button executeBtn = new Button("Run Interactive Example");
        executeBtn.setMaxWidth(Double.MAX_VALUE);
        executeBtn.setOnAction(e -> runInteractiveExample());
        
        Button simulateBtn = new Button("Simulate OOP Scenario");
        simulateBtn.setMaxWidth(Double.MAX_VALUE);
        simulateBtn.setOnAction(e -> simulateOOPScenario());
        
        VBox interactiveBox = new VBox(5);
        interactiveBox.getChildren().addAll(sectionLabel, conceptSelector, executeBtn, simulateBtn);
        interactiveBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(interactiveBox, 0, 2, 2, 1);
    }
    
    private void createOutputSection(GridPane layout) {
        Label outputLabel = new Label("Output Console");
        outputLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(15);
        outputArea.setStyle("-fx-font-family: 'Courier New', monospace; -fx-font-size: 12px; " +
                          "-fx-background-color: #f4f4f4; -fx-text-fill: #000000;");
        
        VBox outputBox = new VBox(5);
        outputBox.getChildren().addAll(outputLabel, outputArea);
        
        layout.add(outputBox, 0, 3, 2, 1);
    }
    
    private void createControlSection(GridPane layout) {
        Label noteLabel = new Label("Notes:");
        inputField = new TextField();
        inputField.setPromptText("Enter your notes here...");
        
        Button addNoteBtn = new Button("Add Note");
        addNoteBtn.setOnAction(e -> addNote());
        
        Button clearBtn = new Button("Clear Output");
        clearBtn.setOnAction(e -> clearOutput());
        
        HBox controlBox = new HBox(10);
        controlBox.setAlignment(Pos.CENTER_LEFT);
        controlBox.getChildren().addAll(noteLabel, inputField, addNoteBtn, clearBtn);
        
        layout.add(controlBox, 0, 4, 2, 1);
    }
    
    private void appendOutput(String text) {
        Platform.runLater(() -> {
            outputArea.appendText(text + "\n");
            outputArea.setScrollTop(Double.MAX_VALUE); // Auto-scroll to bottom
        });
    }
    
    private void demonstrateClassesAndObjects() {
        // Implementation for demonstrating classes and objects
        appendOutput("Demonstrating Classes and Objects:");
        appendOutput("1. Class Definition: A class is defined using the 'class' keyword.");
        appendOutput("2. Creating Objects: Objects are created using the 'new' keyword.");
        appendOutput("3. Accessing Members: Use '.' to access class members (attributes/methods).");
        appendOutput("4. Example: See console for class and object example.");
        
        // Example code (to be executed)
        String exampleCode = "public class Car {\n" +
                             "    String color;\n" +
                             "    void displayColor() {\n" +
                             "        System.out.println(\"Car color is \" + color);\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Main {\n" +
                             "    public static void main(String[] args) {\n" +
                             "        Car myCar = new Car(); // Creating an object\n" +
                             "        myCar.color = \"Red\"; // Setting attribute value\n" +
                             "        myCar.displayColor(); // Calling method\n" +
                             "    }\n" +
                             "}";
        appendOutput(exampleCode);
    }
    
    private void demonstrateEncapsulation() {
        // Implementation for demonstrating encapsulation
        appendOutput("Demonstrating Encapsulation:");
        appendOutput("1. What is Encapsulation? Bundling data (attributes) and methods that operate on the data within one unit (class).");
        appendOutput("2. Why Encapsulation? To restrict direct access to some of an object's components, which is a means of preventing unintended interference and misuse of the methods and data.");
        appendOutput("3. How? Using access modifiers (private, protected, public) to set visibility of class members.");
        appendOutput("4. Example: See console for encapsulation example.");
        
        // Example code (to be executed)
        String exampleCode = "public class Account {\n" +
                             "    private double balance; // private variable\n" +
                             "    \n" +
                             "    // public method to access private variable\n" +
                             "    public double getBalance() {\n" +
                             "        return balance;\n" +
                             "    }\n" +
                             "    \n" +
                             "    // public method to modify private variable\n" +
                             "    public void deposit(double amount) {\n" +
                             "        if (amount > 0) {\n" +
                             "            balance += amount;\n" +
                             "        }\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Main {\n" +
                             "    public static void main(String[] args) {\n" +
                             "        Account myAccount = new Account();\n" +
                             "        myAccount.deposit(1000);\n" +
                             "        System.out.println(\"Account balance: \" + myAccount.getBalance());\n" +
                             "    }\n" +
                             "}";
        appendOutput(exampleCode);
    }
    
    private void demonstrateInheritance() {
        // Implementation for demonstrating inheritance
        appendOutput("Demonstrating Inheritance:");
        appendOutput("1. What is Inheritance? Mechanism where a new class is derived from an existing class.");
        appendOutput("2. Why Inheritance? To promote code reusability and establish a relationship between classes.");
        appendOutput("3. How? Using the 'extends' keyword to inherit attributes and methods from the parent class.");
        appendOutput("4. Example: See console for inheritance example.");
        
        // Example code (to be executed)
        String exampleCode = "public class Animal {\n" +
                             "    void eat() {\n" +
                             "        System.out.println(\"This animal eats food.\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Dog extends Animal {\n" +
                             "    void bark() {\n" +
                             "        System.out.println(\"The dog barks.\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Main {\n" +
                             "    public static void main(String[] args) {\n" +
                             "        Dog myDog = new Dog();\n" +
                             "        myDog.eat(); // Inherited method\n" +
                             "        myDog.bark(); // Dog's own method\n" +
                             "    }\n" +
                             "}";
        appendOutput(exampleCode);
    }
    
    private void demonstratePolymorphism() {
        // Implementation for demonstrating polymorphism
        appendOutput("Demonstrating Polymorphism:");
        appendOutput("1. What is Polymorphism? Ability of a variable, function or object to take on multiple forms.");
        appendOutput("2. Why Polymorphism? To allow methods to do different things based on the object it is acting upon.");
        appendOutput("3. How? Method overriding and overloading.");
        appendOutput("4. Example: See console for polymorphism example.");
        
        // Example code (to be executed)
        String exampleCode = "class Animal {\n" +
                             "    void sound() {\n" +
                             "        System.out.println(\"Animal makes a sound\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Cat extends Animal {\n" +
                             "    void sound() {\n" +
                             "        System.out.println(\"Cat meows\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Dog extends Animal {\n" +
                             "    void sound() {\n" +
                             "        System.out.println(\"Dog barks\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Main {\n" +
                             "    public static void main(String[] args) {\n" +
                             "        Animal myAnimal;\n" +
                             "        myAnimal = new Cat(); // Cat object\n" +
                             "        myAnimal.sound(); // Calls Cat's sound()\n" +
                             "        myAnimal = new Dog(); // Dog object\n" +
                             "        myAnimal.sound(); // Calls Dog's sound()\n" +
                             "    }\n" +
                             "}";
        appendOutput(exampleCode);
    }
    
    private void demonstrateAbstraction() {
        // Implementation for demonstrating abstraction
        appendOutput("Demonstrating Abstraction:");
        appendOutput("1. What is Abstraction? Hiding the complex implementation details and showing only the essential features of the object.");
        appendOutput("2. Why Abstraction? To reduce complexity and increase efficiency.");
        appendOutput("3. How? Using abstract classes and interfaces.");
        appendOutput("4. Example: See console for abstraction example.");
        
        // Example code (to be executed)
        String exampleCode = "abstract class Shape {\n" +
                             "    abstract void draw(); // abstract method\n" +
                             "}\n" +
                             "\n" +
                             "class Circle extends Shape {\n" +
                             "    void draw() {\n" +
                             "        System.out.println(\"Drawing a circle\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Square extends Shape {\n" +
                             "    void draw() {\n" +
                             "        System.out.println(\"Drawing a square\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Main {\n" +
                             "    public static void main(String[] args) {\n" +
                             "        Shape s1 = new Circle();\n" +
                             "        s1.draw(); // Calls Circle's draw()\n" +
                             "        Shape s2 = new Square();\n" +
                             "        s2.draw(); // Calls Square's draw()\n" +
                             "    }\n" +
                             "}";
        appendOutput(exampleCode);
    }
    
    private void demonstrateInterfaces() {
        // Implementation for demonstrating interfaces
        appendOutput("Demonstrating Interfaces:");
        appendOutput("1. What are Interfaces? A reference type in Java, similar to a class, that can contain only constants, method signatures, default methods, static methods, and nested types.");
        appendOutput("2. Why Interfaces? To achieve abstraction and multiple inheritance in Java.");
        appendOutput("3. How? Using the 'interface' keyword to declare an interface, and 'implements' keyword in the class to implement the interface.");
        appendOutput("4. Example: See console for interface example.");
        
        // Example code (to be executed)
        String exampleCode = "interface Animal {\n" +
                             "    void eat(); // interface method (does not have a body)\n" +
                             "}\n" +
                             "\n" +
                             "class Dog implements Animal {\n" +
                             "    public void eat() {\n" +
                             "        System.out.println(\"Dog eats bones\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Cat implements Animal {\n" +
                             "    public void eat() {\n" +
                             "        System.out.println(\"Cat eats fish\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Main {\n" +
                             "    public static void main(String[] args) {\n" +
                             "        Animal myDog = new Dog();\n" +
                             "        myDog.eat(); // Calls Dog's eat()\n" +
                             "        Animal myCat = new Cat();\n" +
                             "        myCat.eat(); // Calls Cat's eat()\n" +
                             "    }\n" +
                             "}";
        appendOutput(exampleCode);
    }
    
    private void demonstrateDesignPatterns() {
        // Implementation for demonstrating design patterns
        appendOutput("Demonstrating Design Patterns:");
        appendOutput("1. What are Design Patterns? General reusable solutions to commonly occurring problems within a given context in software design.");
        appendOutput("2. Why Design Patterns? To speed up the development process by providing tested, proven development paradigms.");
        appendOutput("3. How? By implementing standard design patterns like Singleton, Factory, Observer, etc.");
        appendOutput("4. Example: See console for design pattern example.");
        
        // Example code (to be executed)
        String exampleCode = "// Singleton Pattern\n" +
                             "class Singleton {\n" +
                             "    private static Singleton instance;\n" +
                             "    \n" +
                             "    private Singleton() {} // private constructor\n" +
                             "    \n" +
                             "    public static Singleton getInstance() {\n" +
                             "        if (instance == null) {\n" +
                             "            instance = new Singleton();\n" +
                             "        }\n" +
                             "        return instance;\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "// Factory Pattern\n" +
                             "class Shape {\n" +
                             "    String type;\n" +
                             "    \n" +
                             "    Shape(String type) {\n" +
                             "        this.type = type;\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class ShapeFactory {\n" +
                             "    static Shape getShape(String type) {\n" +
                             "        return new Shape(type);\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Main {\n" +
                             "    public static void main(String[] args) {\n" +
                             "        // Singleton\n" +
                             "        Singleton s1 = Singleton.getInstance();\n" +
                             "        Singleton s2 = Singleton.getInstance();\n" +
                             "        System.out.println(s1 == s2); // true (same instance)\n" +
                             "        \n" +
                             "        // Factory\n" +
                             "        Shape shape1 = ShapeFactory.getShape(\"Circle\");\n" +
                             "        Shape shape2 = ShapeFactory.getShape(\"Square\");\n" +
                             "        System.out.println(shape1.type + \" \" + shape2.type); // Circle Square\n" +
                             "    }\n" +
                             "}";
        appendOutput(exampleCode);
    }
    
    private void demonstrateSOLIDPrinciples() {
        // Implementation for demonstrating SOLID principles
        appendOutput("Demonstrating SOLID Principles:");
        appendOutput("1. What are SOLID Principles? A set of five design principles intended to make software designs more understandable, flexible, and maintainable.");
        appendOutput("2. Why SOLID Principles? To avoid code smells and ensure a well-structured, maintainable codebase.");
        appendOutput("3. How? By following each of the SOLID principles: Single Responsibility, Open-Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion.");
        appendOutput("4. Example: See console for SOLID principles example.");
        
        // Example code (to be executed)
        String exampleCode = "// Single Responsibility Principle\n" +
                             "class User {\n" +
                             "    private String name;\n" +
                             "    \n" +
                             "    public void setName(String name) {\n" +
                             "        this.name = name;\n" +
                             "    }\n" +
                             "    \n" +
                             "    public String getName() {\n" +
                             "        return name;\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "// Open-Closed Principle\n" +
                             "abstract class Shape {\n" +
                             "    abstract double area();\n" +
                             "}\n" +
                             "\n" +
                             "class Rectangle extends Shape {\n" +
                             "    private double width, height;\n" +
                             "    \n" +
                             "    Rectangle(double width, double height) {\n" +
                             "        this.width = width;\n" +
                             "        this.height = height;\n" +
                             "    }\n" +
                             "    \n" +
                             "    double area() {\n" +
                             "        return width * height;\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Circle extends Shape {\n" +
                             "    private double radius;\n" +
                             "    \n" +
                             "    Circle(double radius) {\n" +
                             "        this.radius = radius;\n" +
                             "    }\n" +
                             "    \n" +
                             "    double area() {\n" +
                             "        return Math.PI * radius * radius;\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "// Liskov Substitution Principle\n" +
                             "class Bird {\n" +
                             "    void fly() {\n" +
                             "        System.out.println(\"Bird can fly\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Ostrich extends Bird {\n" +
                             "    void fly() {\n" +
                             "        throw new UnsupportedOperationException(\"Ostrich can't fly\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "// Interface Segregation Principle\n" +
                             "interface Animal {\n" +
                             "    void eat();\n" +
                             "    void fly();\n" +
                             "}\n" +
                             "\n" +
                             "class Dog implements Animal {\n" +
                             "    public void eat() {\n" +
                             "        System.out.println(\"Dog eats\");\n" +
                             "    }\n" +
                             "    \n" +
                             "    public void fly() {\n" +
                             "        throw new UnsupportedOperationException(\"Dog can't fly\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "// Dependency Inversion Principle\n" +
                             "class LightBulb {\n" +
                             "    void turnOn() {\n" +
                             "        System.out.println(\"LightBulb turned on\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Switch {\n" +
                             "    private LightBulb bulb;\n" +
                             "    \n" +
                             "    Switch(LightBulb bulb) {\n" +
                             "        this.bulb = bulb;\n" +
                             "    }\n" +
                             "    \n" +
                             "    void operate() {\n" +
                             "        bulb.turnOn();\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Main {\n" +
                             "    public static void main(String[] args) {\n" +
                             "        // Single Responsibility\n" +
                             "        User user = new User();\n" +
                             "        user.setName(\"John Doe\");\n" +
                             "        System.out.println(user.getName());\n" +
                             "        \n" +
                             "        // Open-Closed\n" +
                             "        Shape rect = new Rectangle(10, 5);\n" +
                             "        System.out.println(\"Rectangle area: \" + rect.area());\n" +
                             "        Shape circ = new Circle(7);\n" +
                             "        System.out.println(\"Circle area: \" + circ.area());\n" +
                             "        \n" +
                             "        // Liskov Substitution\n" +
                             "        Bird bird = new Bird();\n" +
                             "        bird.fly();\n" +
                             "        Bird ostrich = new Ostrich();\n" +
                             "        ostrich.fly(); // Exception\n" +
                             "        \n" +
                             "        // Interface Segregation\n" +
                             "        Animal dog = new Dog();\n" +
                             "        dog.eat();\n" +
                             "        dog.fly(); // Exception\n" +
                             "        \n" +
                             "        // Dependency Inversion\n" +
                             "        LightBulb bulb = new LightBulb();\n" +
                             "        Switch sw = new Switch(bulb);\n" +
                             "        sw.operate();\n" +
                             "    }\n" +
                             "}";
        appendOutput(exampleCode);
    }
    
    private void demonstrateComposition() {
        // Implementation for demonstrating composition vs inheritance
        appendOutput("Demonstrating Composition vs Inheritance:");
        appendOutput("1. Composition: A 'has-a' relationship where a class contains references to objects of other classes as part of its state.");
        appendOutput("2. Inheritance: An 'is-a' relationship where a class derives from another class.");
        appendOutput("3. When to use Composition over Inheritance? Prefer composition when you want to use functionalities of other classes without inheriting their type.");
        appendOutput("4. Example: See console for composition vs inheritance example.");
        
        // Example code (to be executed)
        String exampleCode = "class Engine {\n" +
                             "    void start() {\n" +
                             "        System.out.println(\"Engine starts\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Car {\n" +
                             "    private Engine engine; // Composition\n" +
                             "    \n" +
                             "    Car() {\n" +
                             "        engine = new Engine();\n" +
                             "    }\n" +
                             "    \n" +
                             "    void start() {\n" +
                             "        engine.start();\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Vehicle {\n" +
                             "    void honk() {\n" +
                             "        System.out.println(\"Vehicle honks\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Truck extends Vehicle {\n" +
                             "    void load() {\n" +
                             "        System.out.println(\"Truck loads goods\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Main {\n" +
                             "    public static void main(String[] args) {\n" +
                             "        Car myCar = new Car();\n" +
                             "        myCar.start();\n" +
                             "        \n" +
                             "        Truck myTruck = new Truck();\n" +
                             "        myTruck.honk();\n" +
                             "        myTruck.load();\n" +
                             "    }\n" +
                             "}";
        appendOutput(exampleCode);
    }
    
    private void demonstrateBestPractices() {
        // Implementation for demonstrating OOP best practices
        appendOutput("Demonstrating OOP Best Practices:");
        appendOutput("1. Follow SOLID principles for object-oriented design.");
        appendOutput("2. Prefer composition over inheritance.");
        appendOutput("3. Use interfaces to define contracts for classes.");
        appendOutput("4. Keep classes focused and manageable (Single Responsibility Principle).");
        appendOutput("5. Example: See console for best practices example.");
        
        // Example code (to be executed)
        String exampleCode = "// Best Practice 1: Follow SOLID principles\n" +
                             "// Best Practice 2: Prefer composition over inheritance\n" +
                             "// Best Practice 3: Use interfaces to define contracts\n" +
                             "// Best Practice 4: Keep classes focused\n" +
                             "\n" +
                             "// Example class following best practices\n" +
                             "interface PaymentProcessor {\n" +
                             "    void processPayment(double amount);\n" +
                             "}\n" +
                             "\n" +
                             "class PayPalProcessor implements PaymentProcessor {\n" +
                             "    public void processPayment(double amount) {\n" +
                             "        System.out.println(\"Processing payment of \" + amount + \" through PayPal\");\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "class Order {\n" +
                             "    private PaymentProcessor processor;\n" +
                             "    \n" +
                             "    Order(PaymentProcessor processor) {\n" +
                             "        this.processor = processor;\n" +
                             "    }\n" +
                             "    \n" +
                             "    void completeOrder(double amount) {\n" +
                             "        processor.processPayment(amount);\n" +
                             "    }\n" +
                             "}\n" +
                             "\n" +
                             "public class Main {\n" +
                             "    public static void main(String[] args) {\n" +
                             "        PaymentProcessor processor = new PayPalProcessor();\n" +
                             "        Order order = new Order(processor);\n" +
                             "        order.completeOrder(250.75);\n" +
                             "    }\n" +
                             "}";
        appendOutput(exampleCode);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
