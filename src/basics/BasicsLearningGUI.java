package basics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 * BasicsLearningGUI - Interactive Learning Platform for Java Basics
 * 
 * EDUCATIONAL PURPOSE:
 * This GUI provides an interactive environment to learn and experiment with:
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
 * GUI COMPONENTS:
 * - Code demonstration panels
 * - Interactive input areas
 * - Output displays
 * - Progress tracking
 * - Educational tooltips
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class BasicsLearningGUI extends JFrame {
    
    private JTabbedPane tabbedPane;
    private JTextArea outputArea;
    private JTextField inputField;
    private JLabel statusLabel;
    private int completedLessons = 0;
    private final int totalLessons = 6;
    
    public BasicsLearningGUI() {
        initializeGUI();
        setupEventHandlers();
    }
    
    /**
     * Initialize the main GUI components
     */
    private void initializeGUI() {
        setTitle("🎓 Java Basics Learning Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add lesson tabs
        tabbedPane.addTab("📚 Variables", createVariablesPanel());
        tabbedPane.addTab("🔀 Control Flow", createControlFlowPanel());
        tabbedPane.addTab("⚙️ Methods", createMethodsPanel());
        tabbedPane.addTab("📊 Arrays", createArraysPanel());
        tabbedPane.addTab("📝 Strings", createStringsPanel());
        tabbedPane.addTab("💾 Collections", createCollectionsPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Create output panel
        JPanel outputPanel = createOutputPanel();
        add(outputPanel, BorderLayout.SOUTH);
        
        // Create status panel
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.NORTH);
        
        // Window setup
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Initial message
        appendOutput("🎉 Welcome to Java Basics Learning Platform!");
        appendOutput("👆 Click on tabs above to start learning different concepts.");
        appendOutput("💡 Each tab contains interactive examples and exercises.\n");
    }
    
    /**
     * Create Variables and Data Types panel
     */
    private JPanel createVariablesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Theory panel
        JPanel theoryPanel = new JPanel(new GridLayout(2, 1));
        theoryPanel.setBorder(new TitledBorder("📖 Theory: Variables and Data Types"));
        
        JTextArea theoryText = new JTextArea(
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
        theoryText.setEditable(false);
        theoryText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        theoryText.setBackground(new Color(248, 248, 255));
        
        theoryPanel.add(new JScrollPane(theoryText));
        
        // Interactive panel
        JPanel interactivePanel = new JPanel(new GridBagLayout());
        interactivePanel.setBorder(new TitledBorder("🧪 Interactive Demo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Variable creation demo
        gbc.gridx = 0; gbc.gridy = 0;
        interactivePanel.add(new JLabel("Enter your name:"), gbc);
        
        JTextField nameField = new JTextField(15);
        gbc.gridx = 1;
        interactivePanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        interactivePanel.add(new JLabel("Enter your age:"), gbc);
        
        JTextField ageField = new JTextField(15);
        gbc.gridx = 1;
        interactivePanel.add(ageField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        interactivePanel.add(new JLabel("Enter height (meters):"), gbc);
        
        JTextField heightField = new JTextField(15);
        gbc.gridx = 1;
        interactivePanel.add(heightField, gbc);
        
        JButton demonstrateBtn = new JButton("🔍 Demonstrate Variables");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        interactivePanel.add(demonstrateBtn, gbc);
        
        // Event handler for demonstration
        demonstrateBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                String heightText = heightField.getText().trim();
                
                if (name.isEmpty() || ageText.isEmpty() || heightText.isEmpty()) {
                    appendOutput("❌ Please fill in all fields!");
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
                appendOutput("📊 Variable Analysis:");
                appendOutput("• Name type: " + name.getClass().getSimpleName());
                appendOutput("• Age type: int (32-bit integer)");
                appendOutput("• Height type: double (64-bit floating point)");
                appendOutput("• Is Adult: boolean (true/false)");
                appendOutput("• Memory usage: ~" + (name.length() * 2 + 4 + 8 + 1) + " bytes");
                appendOutput("");
                
                completedLessons = Math.max(completedLessons, 1);
                updateProgress();
                
            } catch (NumberFormatException ex) {
                appendOutput("❌ Error: Please enter valid numbers for age and height!");
            }
        });
        
        panel.add(theoryPanel, BorderLayout.NORTH);
        panel.add(interactivePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create Control Flow panel
     */
    private JPanel createControlFlowPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Theory panel
        JPanel theoryPanel = new JPanel();
        theoryPanel.setBorder(new TitledBorder("📖 Theory: Control Flow Structures"));
        
        JTextArea theoryText = new JTextArea(
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
        theoryText.setEditable(false);
        theoryText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        theoryText.setBackground(new Color(248, 255, 248));
        theoryPanel.add(new JScrollPane(theoryText));
        
        // Interactive panel
        JPanel interactivePanel = new JPanel(new GridBagLayout());
        interactivePanel.setBorder(new TitledBorder("🧪 Interactive Demo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        interactivePanel.add(new JLabel("Enter a number (1-100):"), gbc);
        
        JTextField numberField = new JTextField(15);
        gbc.gridx = 1;
        interactivePanel.add(numberField, gbc);
        
        JButton ifElseBtn = new JButton("🔀 Test If-Else");
        gbc.gridx = 0; gbc.gridy = 1;
        interactivePanel.add(ifElseBtn, gbc);
        
        JButton loopBtn = new JButton("🔄 Demonstrate Loops");
        gbc.gridx = 1;
        interactivePanel.add(loopBtn, gbc);
        
        // If-Else demonstration
        ifElseBtn.addActionListener(e -> {
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
                appendOutput("🎯 Result: " + (number > 50 ? "Large" : number > 25 ? "Medium" : "Small") + " number!");
                appendOutput("");
                
                completedLessons = Math.max(completedLessons, 2);
                updateProgress();
                
            } catch (NumberFormatException ex) {
                appendOutput("❌ Error: Please enter a valid number!");
            }
        });
        
        // Loop demonstration
        loopBtn.addActionListener(e -> {
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
                appendOutput("📊 Output:");
                
                for (int i = 1; i <= 10; i++) {
                    appendOutput(number + " x " + i + " = " + (number * i));
                }
                appendOutput("");
                
                completedLessons = Math.max(completedLessons, 2);
                updateProgress();
                
            } catch (NumberFormatException ex) {
                appendOutput("❌ Error: Please enter a valid number!");
            }
        });
        
        panel.add(theoryPanel, BorderLayout.NORTH);
        panel.add(interactivePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create Methods panel
     */
    private JPanel createMethodsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Theory panel
        JPanel theoryPanel = new JPanel();
        theoryPanel.setBorder(new TitledBorder("📖 Theory: Methods"));
        
        JTextArea theoryText = new JTextArea(
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
        theoryText.setEditable(false);
        theoryText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        theoryText.setBackground(new Color(255, 248, 248));
        theoryPanel.add(new JScrollPane(theoryText));
        
        // Interactive panel
        JPanel interactivePanel = new JPanel(new GridBagLayout());
        interactivePanel.setBorder(new TitledBorder("🧪 Interactive Demo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        interactivePanel.add(new JLabel("First Number:"), gbc);
        
        JTextField num1Field = new JTextField(10);
        gbc.gridx = 1;
        interactivePanel.add(num1Field, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        interactivePanel.add(new JLabel("Second Number:"), gbc);
        
        JTextField num2Field = new JTextField(10);
        gbc.gridx = 1;
        interactivePanel.add(num2Field, gbc);
        
        JButton addBtn = new JButton("➕ Add");
        gbc.gridx = 0; gbc.gridy = 2;
        interactivePanel.add(addBtn, gbc);
        
        JButton multiplyBtn = new JButton("✖️ Multiply");
        gbc.gridx = 1;
        interactivePanel.add(multiplyBtn, gbc);
        
        JButton powBtn = new JButton("⬆️ Power");
        gbc.gridx = 0; gbc.gridy = 3;
        interactivePanel.add(powBtn, gbc);
        
        JButton factorialBtn = new JButton("❗ Factorial");
        gbc.gridx = 1;
        interactivePanel.add(factorialBtn, gbc);
        
        // Method demonstrations
        addBtn.addActionListener(e -> demonstrateAddMethod(num1Field, num2Field));
        multiplyBtn.addActionListener(e -> demonstrateMultiplyMethod(num1Field, num2Field));
        powBtn.addActionListener(e -> demonstratePowerMethod(num1Field, num2Field));
        factorialBtn.addActionListener(e -> demonstrateFactorialMethod(num1Field));
        
        panel.add(theoryPanel, BorderLayout.NORTH);
        panel.add(interactivePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Demonstrate addition method
     */
    private void demonstrateAddMethod(JTextField num1Field, JTextField num2Field) {
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
    
    /**
     * Demonstrate factorial method
     */
    private void demonstrateFactorialMethod(JTextField numField) {
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
    
    /**
     * Helper method for factorial demonstration
     */
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
    
    // Helper methods for demonstrations
    private double add(double a, double b) { return a + b; }
    private double multiply(double a, double b) { return a * b; }
    private double power(double base, double exponent) { return Math.pow(base, exponent); }
    
    private void demonstrateMultiplyMethod(JTextField num1Field, JTextField num2Field) {
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
    
    private void demonstratePowerMethod(JTextField num1Field, JTextField num2Field) {
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
    
    /**
     * Create Arrays panel
     */
    private JPanel createArraysPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Theory panel
        JPanel theoryPanel = new JPanel();
        theoryPanel.setBorder(new TitledBorder("📖 Theory: Arrays"));
        
        JTextArea theoryText = new JTextArea(
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
        theoryText.setEditable(false);
        theoryText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        theoryText.setBackground(new Color(255, 255, 248));
        theoryPanel.add(new JScrollPane(theoryText));
        
        // Interactive panel
        JPanel interactivePanel = new JPanel(new GridBagLayout());
        interactivePanel.setBorder(new TitledBorder("🧪 Interactive Demo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        interactivePanel.add(new JLabel("Enter numbers (comma-separated):"), gbc);
        
        JTextField arrayField = new JTextField(20);
        gbc.gridx = 1;
        interactivePanel.add(arrayField, gbc);
        
        JButton createBtn = new JButton("📊 Create Array");
        gbc.gridx = 0; gbc.gridy = 1;
        interactivePanel.add(createBtn, gbc);
        
        JButton analyzeBtn = new JButton("🔍 Analyze Array");
        gbc.gridx = 1;
        interactivePanel.add(analyzeBtn, gbc);
        
        JButton sortBtn = new JButton("📈 Sort Array");
        gbc.gridx = 0; gbc.gridy = 2;
        interactivePanel.add(sortBtn, gbc);
        
        JButton searchBtn = new JButton("🔎 Search Array");
        gbc.gridx = 1;
        interactivePanel.add(searchBtn, gbc);
        
        // Array operations
        createBtn.addActionListener(e -> demonstrateArrayCreation(arrayField));
        analyzeBtn.addActionListener(e -> demonstrateArrayAnalysis(arrayField));
        sortBtn.addActionListener(e -> demonstrateArraySorting(arrayField));
        searchBtn.addActionListener(e -> demonstrateArraySearch(arrayField));
        
        panel.add(theoryPanel, BorderLayout.NORTH);
        panel.add(interactivePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void demonstrateArrayCreation(JTextField arrayField) {
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
            appendOutput("📊 Array created successfully!");
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
    
    private void demonstrateArrayAnalysis(JTextField arrayField) {
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
            appendOutput("📊 Array contents:");
            
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
    
    private void demonstrateArraySorting(JTextField arrayField) {
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
    
    private void demonstrateArraySearch(JTextField arrayField) {
        try {
            String input = arrayField.getText().trim();
            String[] stringNumbers = input.split(",");
            int[] numbers = new int[stringNumbers.length];
            
            for (int i = 0; i < stringNumbers.length; i++) {
                numbers[i] = Integer.parseInt(stringNumbers[i].trim());
            }
            
            String searchValue = JOptionPane.showInputDialog(this, "Enter value to search:");
            if (searchValue == null) return;
            
            int target = Integer.parseInt(searchValue.trim());
            
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
    
    /**
     * Create Strings panel
     */
    private JPanel createStringsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Theory panel
        JPanel theoryPanel = new JPanel();
        theoryPanel.setBorder(new TitledBorder("📖 Theory: Strings"));
        
        JTextArea theoryText = new JTextArea(
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
        theoryText.setEditable(false);
        theoryText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        theoryText.setBackground(new Color(248, 255, 255));
        theoryPanel.add(new JScrollPane(theoryText));
        
        // Interactive panel
        JPanel interactivePanel = new JPanel(new GridBagLayout());
        interactivePanel.setBorder(new TitledBorder("🧪 Interactive Demo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        interactivePanel.add(new JLabel("Enter text:"), gbc);
        
        JTextField textField = new JTextField(20);
        gbc.gridx = 1;
        interactivePanel.add(textField, gbc);
        
        JButton analyzeBtn = new JButton("🔍 Analyze String");
        gbc.gridx = 0; gbc.gridy = 1;
        interactivePanel.add(analyzeBtn, gbc);
        
        JButton manipulateBtn = new JButton("✂️ Manipulate String");
        gbc.gridx = 1;
        interactivePanel.add(manipulateBtn, gbc);
        
        JButton searchBtn = new JButton("🔎 Search & Replace");
        gbc.gridx = 0; gbc.gridy = 2;
        interactivePanel.add(searchBtn, gbc);
        
        JButton builderBtn = new JButton("🏗️ StringBuilder Demo");
        gbc.gridx = 1;
        interactivePanel.add(builderBtn, gbc);
        
        // String operations
        analyzeBtn.addActionListener(e -> demonstrateStringAnalysis(textField));
        manipulateBtn.addActionListener(e -> demonstrateStringManipulation(textField));
        searchBtn.addActionListener(e -> demonstrateStringSearch(textField));
        builderBtn.addActionListener(e -> demonstrateStringBuilder(textField));
        
        panel.add(theoryPanel, BorderLayout.NORTH);
        panel.add(interactivePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void demonstrateStringAnalysis(JTextField textField) {
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
        
        appendOutput("📊 Character analysis:");
        appendOutput("• Total characters: " + text.length());
        appendOutput("• Vowels: " + vowels);
        appendOutput("• Consonants: " + consonants);
        appendOutput("• Digits: " + digits);
        appendOutput("• Spaces: " + spaces);
        appendOutput("");
        
        completedLessons = Math.max(completedLessons, 5);
        updateProgress();
    }
    
    private void demonstrateStringManipulation(JTextField textField) {
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
    
    private void demonstrateStringSearch(JTextField textField) {
        String text = textField.getText();
        if (text.isEmpty()) {
            appendOutput("❌ Please enter some text!");
            return;
        }
        
        String searchTerm = JOptionPane.showInputDialog(this, "Enter text to search for:");
        if (searchTerm == null || searchTerm.isEmpty()) return;
        
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
            String replacement = JOptionPane.showInputDialog(this, "Enter replacement text:");
            if (replacement != null) {
                String replaced = text.replace(searchTerm, replacement);
                appendOutput("text.replace(\"" + searchTerm + "\", \"" + replacement + "\") = \"" + replaced + "\"");
            }
        }
        
        appendOutput("");
    }
    
    private void demonstrateStringBuilder(JTextField textField) {
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
        appendOutput("📊 Results:");
        appendOutput("• Before reverse: \"" + beforeReverse + "\"");
        appendOutput("• After reverse: \"" + sb.toString() + "\"");
        appendOutput("• Final length: " + sb.length());
        appendOutput("");
    }
    
    /**
     * Create Collections panel
     */
    private JPanel createCollectionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Theory panel
        JPanel theoryPanel = new JPanel();
        theoryPanel.setBorder(new TitledBorder("📖 Theory: Collections"));
        
        JTextArea theoryText = new JTextArea(
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
        theoryText.setEditable(false);
        theoryText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        theoryText.setBackground(new Color(255, 248, 255));
        theoryPanel.add(new JScrollPane(theoryText));
        
        // Interactive panel
        JPanel interactivePanel = new JPanel(new GridBagLayout());
        interactivePanel.setBorder(new TitledBorder("🧪 Interactive Demo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        interactivePanel.add(new JLabel("Enter items (comma-separated):"), gbc);
        
        JTextField itemsField = new JTextField(20);
        gbc.gridx = 1;
        interactivePanel.add(itemsField, gbc);
        
        JButton listBtn = new JButton("📋 ArrayList Demo");
        gbc.gridx = 0; gbc.gridy = 1;
        interactivePanel.add(listBtn, gbc);
        
        JButton setBtn = new JButton("🔗 HashSet Demo");
        gbc.gridx = 1;
        interactivePanel.add(setBtn, gbc);
        
        JButton mapBtn = new JButton("🗺️ HashMap Demo");
        gbc.gridx = 0; gbc.gridy = 2;
        interactivePanel.add(mapBtn, gbc);
        
        JButton compareBtn = new JButton("⚖️ Compare Collections");
        gbc.gridx = 1;
        interactivePanel.add(compareBtn, gbc);
        
        // Collection operations
        listBtn.addActionListener(e -> demonstrateArrayList(itemsField));
        setBtn.addActionListener(e -> demonstrateHashSet(itemsField));
        mapBtn.addActionListener(e -> demonstrateHashMap(itemsField));
        compareBtn.addActionListener(e -> demonstrateCollectionComparison(itemsField));
        
        panel.add(theoryPanel, BorderLayout.NORTH);
        panel.add(interactivePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void demonstrateArrayList(JTextField itemsField) {
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
    
    private void demonstrateHashSet(JTextField itemsField) {
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
        appendOutput("📊 Results:");
        appendOutput("• Original items: " + items.length);
        appendOutput("• Unique items in set: " + set.size());
        appendOutput("• Set contents: " + set);
        appendOutput("");
    }
    
    private void demonstrateHashMap(JTextField itemsField) {
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
        appendOutput("📊 Item frequency:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            appendOutput("• \"" + entry.getKey() + "\": " + entry.getValue() + " times");
        }
        appendOutput("");
    }
    
    private void demonstrateCollectionComparison(JTextField itemsField) {
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
        
        appendOutput("📊 Comparison Results:");
        appendOutput("• ArrayList size: " + list.size() + " (keeps duplicates)");
        appendOutput("• HashSet size: " + set.size() + " (removes duplicates)");
        appendOutput("• HashMap size: " + map.size() + " (unique keys with counts)");
        appendOutput("");
        
        appendOutput("🎯 When to use each:");
        appendOutput("• ArrayList: When you need ordered data with duplicates");
        appendOutput("• HashSet: When you need unique items only");
        appendOutput("• HashMap: When you need key-value relationships");
        appendOutput("");
    }
    
    /**
     * Create output panel
     */
    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("💬 Output Console"));
        
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.GREEN);
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JButton clearBtn = new JButton("🗑️ Clear");
        
        inputPanel.add(new JLabel("💭 Notes: "), BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(clearBtn, BorderLayout.EAST);
        
        panel.add(inputPanel, BorderLayout.SOUTH);
        
        // Clear button action
        clearBtn.addActionListener(e -> outputArea.setText(""));
        
        return panel;
    }
    
    /**
     * Create status panel
     */
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("📈 Learning Progress"));
        
        statusLabel = new JLabel("🎯 Ready to learn! Start with Variables tab.");
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        
        panel.add(statusLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Setup event handlers
     */
    private void setupEventHandlers() {
        // Tab change listener
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            String[] tabNames = {"Variables", "Control Flow", "Methods", "Arrays", "Strings", "Collections"};
            if (selectedIndex >= 0 && selectedIndex < tabNames.length) {
                appendOutput("📖 Switched to: " + tabNames[selectedIndex] + " lesson");
            }
        });
        
        // Enter key in input field
        inputField.addActionListener(e -> {
            String note = inputField.getText().trim();
            if (!note.isEmpty()) {
                appendOutput("📝 Note: " + note);
                inputField.setText("");
            }
        });
    }
    
    /**
     * Append text to output area
     */
    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
    
    /**
     * Update progress status
     */
    private void updateProgress() {
        double progress = (double) completedLessons / totalLessons * 100;
        statusLabel.setText(String.format("🎓 Progress: %d/%d lessons completed (%.1f%%)", 
            completedLessons, totalLessons, progress));
        
        if (completedLessons == totalLessons) {
            statusLabel.setText("🏆 Congratulations! All Java Basics lessons completed!");
            JOptionPane.showMessageDialog(this, 
                "🎉 Excellent work! You've completed all Java Basics lessons!\n\n" +
                "Next steps:\n" +
                "• Practice with more complex examples\n" +
                "• Explore Object-Oriented Programming\n" +
                "• Learn about Data Structures\n" +
                "• Study Advanced Java concepts",
                "Learning Complete!", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Main method to launch the learning platform
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                // Use default look and feel
            }
            
            new BasicsLearningGUI().setVisible(true);
        });
    }
}
