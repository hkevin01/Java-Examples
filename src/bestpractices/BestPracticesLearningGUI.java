package bestpractices;

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
 * BestPracticesLearningGUI - Interactive JavaFX Learning Platform for Java Best Practices
 * 
 * EDUCATIONAL PURPOSE:
 * This JavaFX GUI provides an interactive environment to learn and experiment with:
 * - Code Documentation and Comments
 * - Naming Conventions
 * - Error Handling Strategies
 * - Performance Optimization
 * - Security Best Practices
 * - Code Organization
 * 
 * LEARNING APPROACH:
 * - Visual demonstrations of good vs bad practices
 * - Interactive code examples
 * - Real-time validation
 * - Performance comparisons
 * - Security vulnerability examples
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class BestPracticesLearningGUI extends Application {
    
    private TextArea outputArea;
    private TextField inputField;
    private Label statusLabel;
    private Button currentDemoButton;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Java Best Practices Learning Platform");
        
        // Create main layout using GridPane for precise control
        GridPane mainLayout = new GridPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setHgap(10);
        mainLayout.setVgap(10);
        
        // Configure column constraints for responsive layout
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        mainLayout.getColumnConstraints().addAll(col1, col2);
        
        // Create sections
        createHeaderSection(mainLayout);
        createNamingConventionsSection(mainLayout);
        createErrorHandlingSection(mainLayout);
        createDocumentationSection(mainLayout);
        createPerformanceSection(mainLayout);
        createOutputSection(mainLayout);
        createControlSection(mainLayout);
        
        Scene scene = new Scene(mainLayout, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Prevent resizing to avoid layout issues
        primaryStage.show();
        
        appendOutput("Welcome to Java Best Practices Learning Platform!");
        appendOutput("Learn industry-standard coding practices through interactive examples.");
        appendOutput("Click any demonstration button to see best practices in action.\n");
    }
    
    private void createHeaderSection(GridPane layout) {
        Label titleLabel = new Label("Java Best Practices Learning Platform");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        statusLabel = new Label("Ready to learn best practices!");
        statusLabel.setStyle("-fx-text-fill: #2e7d32;");
        
        VBox headerBox = new VBox(5);
        headerBox.getChildren().addAll(titleLabel, statusLabel);
        headerBox.setAlignment(Pos.CENTER);
        
        layout.add(headerBox, 0, 0, 2, 1);
    }
    
    private void createNamingConventionsSection(GridPane layout) {
        Label sectionLabel = new Label("1. Naming Conventions");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Button goodNamingBtn = new Button("Good Naming Examples");
        goodNamingBtn.setMaxWidth(Double.MAX_VALUE);
        goodNamingBtn.setOnAction(e -> demonstrateGoodNaming());
        
        Button badNamingBtn = new Button("Bad Naming Examples");
        badNamingBtn.setMaxWidth(Double.MAX_VALUE);
        badNamingBtn.setOnAction(e -> demonstrateBadNaming());
        
        Button conventionsBtn = new Button("Convention Guidelines");
        conventionsBtn.setMaxWidth(Double.MAX_VALUE);
        conventionsBtn.setOnAction(e -> showNamingConventions());
        
        VBox namingBox = new VBox(5);
        namingBox.getChildren().addAll(sectionLabel, goodNamingBtn, badNamingBtn, conventionsBtn);
        namingBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(namingBox, 0, 1);
    }
    
    private void createErrorHandlingSection(GridPane layout) {
        Label sectionLabel = new Label("2. Error Handling");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Button tryWithResourcesBtn = new Button("Try-With-Resources");
        tryWithResourcesBtn.setMaxWidth(Double.MAX_VALUE);
        tryWithResourcesBtn.setOnAction(e -> demonstrateTryWithResources());
        
        Button exceptionHandlingBtn = new Button("Exception Handling");
        exceptionHandlingBtn.setMaxWidth(Double.MAX_VALUE);
        exceptionHandlingBtn.setOnAction(e -> demonstrateExceptionHandling());
        
        Button customExceptionsBtn = new Button("Custom Exceptions");
        customExceptionsBtn.setMaxWidth(Double.MAX_VALUE);
        customExceptionsBtn.setOnAction(e -> demonstrateCustomExceptions());
        
        VBox errorBox = new VBox(5);
        errorBox.getChildren().addAll(sectionLabel, tryWithResourcesBtn, exceptionHandlingBtn, customExceptionsBtn);
        errorBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(errorBox, 1, 1);
    }
    
    private void createDocumentationSection(GridPane layout) {
        Label sectionLabel = new Label("3. Documentation");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Button javadocBtn = new Button("Javadoc Best Practices");
        javadocBtn.setMaxWidth(Double.MAX_VALUE);
        javadocBtn.setOnAction(e -> demonstrateJavadoc());
        
        Button commentsBtn = new Button("Effective Comments");
        commentsBtn.setMaxWidth(Double.MAX_VALUE);
        commentsBtn.setOnAction(e -> demonstrateComments());
        
        Button readmeBtn = new Button("README Guidelines");
        readmeBtn.setMaxWidth(Double.MAX_VALUE);
        readmeBtn.setOnAction(e -> demonstrateReadme());
        
        VBox docBox = new VBox(5);
        docBox.getChildren().addAll(sectionLabel, javadocBtn, commentsBtn, readmeBtn);
        docBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(docBox, 0, 2);
    }
    
    private void createPerformanceSection(GridPane layout) {
        Label sectionLabel = new Label("4. Performance & Security");
        sectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Button performanceBtn = new Button("Performance Tips");
        performanceBtn.setMaxWidth(Double.MAX_VALUE);
        performanceBtn.setOnAction(e -> demonstratePerformance());
        
        Button securityBtn = new Button("Security Practices");
        securityBtn.setMaxWidth(Double.MAX_VALUE);
        securityBtn.setOnAction(e -> demonstrateSecurity());
        
        Button memoryBtn = new Button("Memory Management");
        memoryBtn.setMaxWidth(Double.MAX_VALUE);
        memoryBtn.setOnAction(e -> demonstrateMemoryManagement());
        
        VBox perfBox = new VBox(5);
        perfBox.getChildren().addAll(sectionLabel, performanceBtn, securityBtn, memoryBtn);
        perfBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10;");
        
        layout.add(perfBox, 1, 2);
    }
    
    private void createOutputSection(GridPane layout) {
        Label outputLabel = new Label("Output Console");
        outputLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(10);
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
    
    // Event handler methods will continue in the next part...
    
    private void demonstrateGoodNaming() {
        setCurrentDemo("Good Naming Examples");
        appendOutput("=== GOOD NAMING CONVENTIONS ===");
        appendOutput("");
        appendOutput("// Classes: PascalCase");
        appendOutput("public class CustomerAccountManager {");
        appendOutput("    // Constants: UPPER_SNAKE_CASE");
        appendOutput("    private static final int MAX_RETRY_ATTEMPTS = 3;");
        appendOutput("    private static final String DEFAULT_CURRENCY = \"USD\";");
        appendOutput("");
        appendOutput("    // Variables: camelCase with meaningful names");
        appendOutput("    private String customerEmailAddress;");
        appendOutput("    private double accountBalanceInDollars;");
        appendOutput("    private boolean isAccountActiveStatus;");
        appendOutput("");
        appendOutput("    // Methods: verbs describing what they do");
        appendOutput("    public void calculateMonthlyInterest() { }");
        appendOutput("    public boolean validateEmailFormat(String email) { }");
        appendOutput("    public void sendWelcomeEmailToCustomer() { }");
        appendOutput("}");
        appendOutput("");
        appendOutput("Benefits of good naming:");
        appendOutput("• Code is self-documenting");
        appendOutput("• Reduces need for comments");
        appendOutput("• Makes debugging easier");
        appendOutput("• Improves team collaboration");
    }
    
    private void demonstrateBadNaming() {
        setCurrentDemo("Bad Naming Examples");
        appendOutput("=== BAD NAMING CONVENTIONS (AVOID THESE!) ===");
        appendOutput("");
        appendOutput("// Bad class name - unclear purpose");
        appendOutput("public class mgr {");
        appendOutput("    // Bad constant names");
        appendOutput("    private static final int x = 3;");
        appendOutput("    private static final String s1 = \"USD\";");
        appendOutput("");
        appendOutput("    // Bad variable names - unclear meaning");
        appendOutput("    private String e;");
        appendOutput("    private double amt;");
        appendOutput("    private boolean flag;");
        appendOutput("");
        appendOutput("    // Bad method names - don't describe purpose");
        appendOutput("    public void calc() { }");
        appendOutput("    public boolean check(String s) { }");
        appendOutput("    public void doStuff() { }");
        appendOutput("}");
        appendOutput("");
        appendOutput("Problems with bad naming:");
        appendOutput("• Code is hard to understand");
        appendOutput("• Requires extensive comments");
        appendOutput("• Makes debugging difficult");
        appendOutput("• Reduces team productivity");
    }
    
    private void showNamingConventions() {
        setCurrentDemo("Naming Convention Guidelines");
        appendOutput("=== JAVA NAMING CONVENTION GUIDELINES ===");
        appendOutput("");
        appendOutput("1. CLASSES AND INTERFACES:");
        appendOutput("   • Use PascalCase (UpperCamelCase)");
        appendOutput("   • Nouns describing what the class represents");
        appendOutput("   • Examples: CustomerAccount, EmailValidator, PaymentProcessor");
        appendOutput("");
        appendOutput("2. METHODS:");
        appendOutput("   • Use camelCase (lowerCamelCase)");
        appendOutput("   • Verbs describing what the method does");
        appendOutput("   • Examples: calculateTotal(), isValidEmail(), processPayment()");
        appendOutput("");
        appendOutput("3. VARIABLES:");
        appendOutput("   • Use camelCase");
        appendOutput("   • Descriptive nouns");
        appendOutput("   • Examples: customerName, totalAmount, isAccountActive");
        appendOutput("");
        appendOutput("4. CONSTANTS:");
        appendOutput("   • Use UPPER_SNAKE_CASE");
        appendOutput("   • Examples: MAX_RETRY_ATTEMPTS, DEFAULT_TIMEOUT_SECONDS");
        appendOutput("");
        appendOutput("5. PACKAGES:");
        appendOutput("   • Use lowercase with dots");
        appendOutput("   • Examples: com.company.project.utils, org.example.banking");
    }
    
    private void demonstrateTryWithResources() {
        setCurrentDemo("Try-With-Resources Pattern");
        appendOutput("=== TRY-WITH-RESOURCES BEST PRACTICE ===");
        appendOutput("");
        appendOutput("// GOOD: Try-with-resources automatically closes resources");
        appendOutput("try (FileReader fileReader = new FileReader(\"data.txt\");");
        appendOutput("     BufferedReader bufferedReader = new BufferedReader(fileReader)) {");
        appendOutput("    ");
        appendOutput("    String line = bufferedReader.readLine();");
        appendOutput("    System.out.println(line);");
        appendOutput("    ");
        appendOutput("    // Resources are automatically closed here!");
        appendOutput("    // Even if an exception occurs");
        appendOutput("    ");
        appendOutput("} catch (IOException e) {");
        appendOutput("    System.err.println(\"Error reading file: \" + e.getMessage());");
        appendOutput("}");
        appendOutput("");
        appendOutput("// BAD: Manual resource management (error-prone)");
        appendOutput("FileReader fileReader = null;");
        appendOutput("BufferedReader bufferedReader = null;");
        appendOutput("try {");
        appendOutput("    fileReader = new FileReader(\"data.txt\");");
        appendOutput("    bufferedReader = new BufferedReader(fileReader);");
        appendOutput("    String line = bufferedReader.readLine();");
        appendOutput("} catch (IOException e) {");
        appendOutput("    // Handle exception");
        appendOutput("} finally {");
        appendOutput("    // Manual cleanup - can forget or have exceptions here!");
        appendOutput("    if (bufferedReader != null) bufferedReader.close();");
        appendOutput("    if (fileReader != null) fileReader.close();");
        appendOutput("}");
        appendOutput("");
        appendOutput("Benefits of try-with-resources:");
        appendOutput("• Automatic resource cleanup");
        appendOutput("• No resource leaks");
        appendOutput("• Cleaner, more readable code");
        appendOutput("• Exception handling is simpler");
    }
    
    private void demonstrateExceptionHandling() {
        setCurrentDemo("Exception Handling Best Practices");
        appendOutput("=== EXCEPTION HANDLING BEST PRACTICES ===");
        appendOutput("");
        appendOutput("// GOOD: Specific exception handling");
        appendOutput("public void processPayment(double amount) throws PaymentException {");
        appendOutput("    try {");
        appendOutput("        validateAmount(amount);");
        appendOutput("        processTransaction(amount);");
        appendOutput("        updateAccountBalance(amount);");
        appendOutput("        ");
        appendOutput("    } catch (InvalidAmountException e) {");
        appendOutput("        logger.error(\"Invalid amount: \" + amount, e);");
        appendOutput("        throw new PaymentException(\"Payment failed: invalid amount\", e);");
        appendOutput("        ");
        appendOutput("    } catch (InsufficientFundsException e) {");
        appendOutput("        logger.warn(\"Insufficient funds for amount: \" + amount);");
        appendOutput("        throw new PaymentException(\"Payment failed: insufficient funds\", e);");
        appendOutput("        ");
        appendOutput("    } catch (NetworkException e) {");
        appendOutput("        logger.error(\"Network error during payment processing\", e);");
        appendOutput("        // Retry logic could go here");
        appendOutput("        throw new PaymentException(\"Payment failed: network error\", e);");
        appendOutput("    }");
        appendOutput("}");
        appendOutput("");
        appendOutput("// BAD: Generic exception handling");
        appendOutput("try {");
        appendOutput("    // Some risky operation");
        appendOutput("} catch (Exception e) {");
        appendOutput("    // This catches everything - too broad!");
        appendOutput("    e.printStackTrace(); // Don't just print stack trace");
        appendOutput("}");
        appendOutput("");
        appendOutput("Best practices:");
        appendOutput("• Catch specific exceptions, not generic Exception");
        appendOutput("• Log exceptions with context");
        appendOutput("• Don't swallow exceptions silently");
        appendOutput("• Use proper exception chaining");
        appendOutput("• Clean up resources in finally blocks");
    }
    
    private void demonstrateCustomExceptions() {
        setCurrentDemo("Custom Exception Design");
        appendOutput("=== CUSTOM EXCEPTION BEST PRACTICES ===");
        appendOutput("");
        appendOutput("// GOOD: Well-designed custom exception");
        appendOutput("public class PaymentProcessingException extends Exception {");
        appendOutput("    private final String paymentId;");
        appendOutput("    private final double amount;");
        appendOutput("    private final PaymentErrorCode errorCode;");
        appendOutput("    ");
        appendOutput("    public PaymentProcessingException(String message, ");
        appendOutput("                                     String paymentId,");
        appendOutput("                                     double amount,");
        appendOutput("                                     PaymentErrorCode errorCode) {");
        appendOutput("        super(message);");
        appendOutput("        this.paymentId = paymentId;");
        appendOutput("        this.amount = amount;");
        appendOutput("        this.errorCode = errorCode;");
        appendOutput("    }");
        appendOutput("    ");
        appendOutput("    public PaymentProcessingException(String message, Throwable cause,");
        appendOutput("                                     String paymentId, double amount,");
        appendOutput("                                     PaymentErrorCode errorCode) {");
        appendOutput("        super(message, cause);");
        appendOutput("        this.paymentId = paymentId;");
        appendOutput("        this.amount = amount;");
        appendOutput("        this.errorCode = errorCode;");
        appendOutput("    }");
        appendOutput("    ");
        appendOutput("    // Getters for additional context");
        appendOutput("    public String getPaymentId() { return paymentId; }");
        appendOutput("    public double getAmount() { return amount; }");
        appendOutput("    public PaymentErrorCode getErrorCode() { return errorCode; }");
        appendOutput("}");
        appendOutput("");
        appendOutput("Custom exception benefits:");
        appendOutput("• Provides specific context");
        appendOutput("• Enables targeted error handling");
        appendOutput("• Improves debugging capabilities");
        appendOutput("• Supports error recovery strategies");
    }
    
    private void demonstrateJavadoc() {
        setCurrentDemo("Javadoc Best Practices");
        appendOutput("=== JAVADOC DOCUMENTATION BEST PRACTICES ===");
        appendOutput("");
        appendOutput("/**");
        appendOutput(" * Calculates the monthly interest for a customer account.");
        appendOutput(" * <p>");
        appendOutput(" * This method applies the current interest rate to the account balance");
        appendOutput(" * and returns the calculated monthly interest amount. The calculation");
        appendOutput(" * takes into account the account type and any applicable bonuses.");
        appendOutput(" * </p>");
        appendOutput(" *");
        appendOutput(" * @param accountBalance the current balance in the account (must be >= 0)");
        appendOutput(" * @param interestRate the annual interest rate as a decimal (e.g., 0.05 for 5%)");
        appendOutput(" * @param accountType the type of account (SAVINGS, CHECKING, etc.)");
        appendOutput(" * @return the calculated monthly interest amount");
        appendOutput(" * @throws IllegalArgumentException if accountBalance is negative");
        appendOutput(" * @throws IllegalArgumentException if interestRate is negative or > 1.0");
        appendOutput(" * @throws NullPointerException if accountType is null");
        appendOutput(" * @since 1.2");
        appendOutput(" * @see AccountType");
        appendOutput(" * @see #calculateAnnualInterest(double, double, AccountType)");
        appendOutput(" */");
        appendOutput("public double calculateMonthlyInterest(double accountBalance,");
        appendOutput("                                      double interestRate,");
        appendOutput("                                      AccountType accountType) {");
        appendOutput("    // Implementation here...");
        appendOutput("}");
        appendOutput("");
        appendOutput("Javadoc best practices:");
        appendOutput("• Write clear, concise descriptions");
        appendOutput("• Document all parameters and return values");
        appendOutput("• List all possible exceptions");
        appendOutput("• Include examples when helpful");
        appendOutput("• Use @since for version tracking");
        appendOutput("• Reference related methods with @see");
    }
    
    private void demonstrateComments() {
        setCurrentDemo("Effective Code Comments");
        appendOutput("=== EFFECTIVE CODE COMMENTS ===");
        appendOutput("");
        appendOutput("// GOOD COMMENTS: Explain WHY, not WHAT");
        appendOutput("");
        appendOutput("// Apply 10% discount for premium customers to encourage loyalty");
        appendOutput("if (customer.isPremium()) {");
        appendOutput("    total *= 0.9;");
        appendOutput("}");
        appendOutput("");
        appendOutput("// Use exponential backoff to avoid overwhelming the payment gateway");
        appendOutput("int retryDelay = (int) Math.pow(2, attemptNumber) * 1000;");
        appendOutput("Thread.sleep(retryDelay);");
        appendOutput("");
        appendOutput("// TODO: Implement caching to improve performance (Issue #123)");
        appendOutput("// FIXME: This fails when timezone changes during execution");
        appendOutput("// HACK: Workaround for bug in third-party library v2.1");
        appendOutput("");
        appendOutput("// BAD COMMENTS: Stating the obvious");
        appendOutput("");
        appendOutput("// Increment i by 1");
        appendOutput("i++;");
        appendOutput("");
        appendOutput("// Check if user is null");
        appendOutput("if (user == null) {");
        appendOutput("    return;");
        appendOutput("}");
        appendOutput("");
        appendOutput("Comment guidelines:");
        appendOutput("• Explain WHY, not WHAT the code does");
        appendOutput("• Document complex business logic");
        appendOutput("• Explain non-obvious algorithms");
        appendOutput("• Use TODO/FIXME/HACK appropriately");
        appendOutput("• Keep comments up-to-date with code changes");
        appendOutput("• Remove commented-out code");
    }
    
    private void demonstrateReadme() {
        setCurrentDemo("README Documentation");
        appendOutput("=== README DOCUMENTATION BEST PRACTICES ===");
        appendOutput("");
        appendOutput("Essential README sections:");
        appendOutput("");
        appendOutput("1. PROJECT TITLE AND DESCRIPTION");
        appendOutput("   Brief explanation of what the project does");
        appendOutput("");
        appendOutput("2. INSTALLATION INSTRUCTIONS");
        appendOutput("   Step-by-step setup guide");
        appendOutput("   Required dependencies and versions");
        appendOutput("");
        appendOutput("3. USAGE EXAMPLES");
        appendOutput("   Basic usage with code examples");
        appendOutput("   Common use cases");
        appendOutput("");
        appendOutput("4. API DOCUMENTATION");
        appendOutput("   Key classes and methods");
        appendOutput("   Parameters and return values");
        appendOutput("");
        appendOutput("5. CONFIGURATION");
        appendOutput("   Environment variables");
        appendOutput("   Configuration files");
        appendOutput("");
        appendOutput("6. CONTRIBUTING GUIDELINES");
        appendOutput("   How to contribute");
        appendOutput("   Code style requirements");
        appendOutput("");
        appendOutput("7. LICENSE INFORMATION");
        appendOutput("   License type and terms");
        appendOutput("");
        appendOutput("8. CHANGELOG");
        appendOutput("   Recent changes and versions");
        appendOutput("");
        appendOutput("README best practices:");
        appendOutput("• Write for your target audience");
        appendOutput("• Include working code examples");
        appendOutput("• Keep it concise but comprehensive");
        appendOutput("• Update regularly");
        appendOutput("• Use clear formatting and sections");
    }
    
    private void demonstratePerformance() {
        setCurrentDemo("Performance Optimization");
        appendOutput("=== PERFORMANCE OPTIMIZATION BEST PRACTICES ===");
        appendOutput("");
        appendOutput("// GOOD: Efficient string concatenation");
        appendOutput("StringBuilder result = new StringBuilder();");
        appendOutput("for (String item : items) {");
        appendOutput("    result.append(item).append(\", \");");
        appendOutput("}");
        appendOutput("String finalResult = result.toString();");
        appendOutput("");
        appendOutput("// BAD: Inefficient string concatenation");
        appendOutput("String result = \"\";");
        appendOutput("for (String item : items) {");
        appendOutput("    result += item + \", \"; // Creates new String objects!");
        appendOutput("}");
        appendOutput("");
        appendOutput("// GOOD: Use appropriate collections");
        appendOutput("Set<String> uniqueIds = new HashSet<>(); // O(1) lookups");
        appendOutput("List<String> orderedItems = new ArrayList<>(); // Good for indexed access");
        appendOutput("Map<String, User> userCache = new HashMap<>(); // O(1) lookups by key");
        appendOutput("");
        appendOutput("// GOOD: Lazy initialization");
        appendOutput("private List<String> expensiveList;");
        appendOutput("public List<String> getExpensiveList() {");
        appendOutput("    if (expensiveList == null) {");
        appendOutput("        expensiveList = createExpensiveList();");
        appendOutput("    }");
        appendOutput("    return expensiveList;");
        appendOutput("}");
        appendOutput("");
        appendOutput("Performance tips:");
        appendOutput("• Use StringBuilder for string concatenation");
        appendOutput("• Choose appropriate collection types");
        appendOutput("• Implement lazy initialization when appropriate");
        appendOutput("• Avoid premature optimization");
        appendOutput("• Profile before optimizing");
        appendOutput("• Cache expensive computations");
    }
    
    private void demonstrateSecurity() {
        setCurrentDemo("Security Best Practices");
        appendOutput("=== SECURITY BEST PRACTICES ===");
        appendOutput("");
        appendOutput("// GOOD: Input validation");
        appendOutput("public void processUserInput(String input) {");
        appendOutput("    if (input == null || input.trim().isEmpty()) {");
        appendOutput("        throw new IllegalArgumentException(\"Input cannot be null or empty\");");
        appendOutput("    }");
        appendOutput("    ");
        appendOutput("    // Sanitize input to prevent injection attacks");
        appendOutput("    String sanitizedInput = input.replaceAll(\"[<>\\\"'&]\", \"\");");
        appendOutput("    ");
        appendOutput("    if (sanitizedInput.length() > MAX_INPUT_LENGTH) {");
        appendOutput("        throw new IllegalArgumentException(\"Input too long\");");
        appendOutput("    }");
        appendOutput("    ");
        appendOutput("    // Process the validated input");
        appendOutput("}");
        appendOutput("");
        appendOutput("// GOOD: Secure password handling");
        appendOutput("public boolean verifyPassword(char[] password, String hashedPassword) {");
        appendOutput("    try {");
        appendOutput("        String inputHash = hashPassword(password);");
        appendOutput("        return constantTimeEquals(inputHash, hashedPassword);");
        appendOutput("    } finally {");
        appendOutput("        // Clear sensitive data from memory");
        appendOutput("        Arrays.fill(password, '\\0');");
        appendOutput("    }");
        appendOutput("}");
        appendOutput("");
        appendOutput("// GOOD: SQL injection prevention");
        appendOutput("String sql = \"SELECT * FROM users WHERE id = ? AND active = ?\";");
        appendOutput("try (PreparedStatement stmt = connection.prepareStatement(sql)) {");
        appendOutput("    stmt.setLong(1, userId);");
        appendOutput("    stmt.setBoolean(2, true);");
        appendOutput("    ResultSet rs = stmt.executeQuery();");
        appendOutput("    // Process results");
        appendOutput("}");
        appendOutput("");
        appendOutput("Security principles:");
        appendOutput("• Validate all inputs");
        appendOutput("• Use parameterized queries");
        appendOutput("• Hash passwords with salt");
        appendOutput("• Clear sensitive data from memory");
        appendOutput("• Follow principle of least privilege");
        appendOutput("• Keep dependencies updated");
    }
    
    private void demonstrateMemoryManagement() {
        setCurrentDemo("Memory Management");
        appendOutput("=== MEMORY MANAGEMENT BEST PRACTICES ===");
        appendOutput("");
        appendOutput("// GOOD: Proper resource cleanup");
        appendOutput("public void processLargeFile(String filename) {");
        appendOutput("    try (FileInputStream fis = new FileInputStream(filename);");
        appendOutput("         BufferedInputStream bis = new BufferedInputStream(fis)) {");
        appendOutput("         ");
        appendOutput("        byte[] buffer = new byte[8192]; // Reasonable buffer size");
        appendOutput("        int bytesRead;");
        appendOutput("        ");
        appendOutput("        while ((bytesRead = bis.read(buffer)) != -1) {");
        appendOutput("            processBuffer(buffer, bytesRead);");
        appendOutput("        }");
        appendOutput("        ");
        appendOutput("    } catch (IOException e) {");
        appendOutput("        logger.error(\"Error processing file: \" + filename, e);");
        appendOutput("    }");
        appendOutput("    // Resources automatically closed by try-with-resources");
        appendOutput("}");
        appendOutput("");
        appendOutput("// GOOD: Avoid memory leaks in collections");
        appendOutput("public class EventManager {");
        appendOutput("    private final Set<EventListener> listeners = new WeakHashSet<>();");
        appendOutput("    ");
        appendOutput("    public void addListener(EventListener listener) {");
        appendOutput("        listeners.add(listener);");
        appendOutput("    }");
        appendOutput("    ");
        appendOutput("    // WeakHashSet allows listeners to be garbage collected");
        appendOutput("    // when they're no longer referenced elsewhere");
        appendOutput("}");
        appendOutput("");
        appendOutput("// GOOD: Control object creation in loops");
        appendOutput("StringBuilder result = new StringBuilder(); // Create once");
        appendOutput("for (int i = 0; i < 1000; i++) {");
        appendOutput("    result.append(\"Item \").append(i); // Reuse StringBuilder");
        appendOutput("}");
        appendOutput("");
        appendOutput("Memory management tips:");
        appendOutput("• Use try-with-resources for automatic cleanup");
        appendOutput("• Be careful with static collections");
        appendOutput("• Use WeakReferences when appropriate");
        appendOutput("• Avoid creating objects in tight loops");
        appendOutput("• Close streams and connections explicitly");
        appendOutput("• Monitor memory usage in production");
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
            statusLabel.setText("Ready to learn best practices!");
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
