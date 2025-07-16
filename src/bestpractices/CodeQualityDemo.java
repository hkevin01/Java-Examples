package bestpractices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CodeQualityDemo - Demonstrates Java Code Quality Standards and Best Practices
 * 
 * WHY CODE QUALITY MATTERS:
 * - Reduces bugs and maintenance costs
 * - Improves team productivity and collaboration
 * - Makes code easier to understand, modify, and extend
 * - Enables faster onboarding of new team members
 * - Reduces technical debt and long-term development costs
 * 
 * This comprehensive demo covers:
 * - Clean Code principles (meaningful names, small functions, clear intent)
 * - SOLID design principles (fundamental object-oriented design principles)
 * - Naming conventions (consistent and descriptive naming)
 * - Code organization and structure (logical grouping and hierarchy)
 * - Error handling best practices (proper exception management)
 * - Resource management (try-with-resources, proper cleanup)
 * - Immutability and defensive programming (preventing bugs through design)
 * - Documentation standards (JavaDoc and inline comments)
 * 
 * LEARNING OBJECTIVES:
 * 1. Understand what makes code "clean" and maintainable
 * 2. Apply SOLID principles in practical scenarios
 * 3. Write self-documenting code with proper naming
 * 4. Implement robust error handling strategies
 * 5. Design immutable objects for thread safety and reliability
 * 
 * @author Java Examples Project
 * @version 1.0
 * @since 1.0
 */

// Example of Clean Code: Well-named classes and methods
// The following class demonstrates several clean code principles:
// 1. Immutability - once created, the object cannot be changed
// 2. Clear naming - class and method names clearly express their purpose
// 3. Single Responsibility - this class only handles order data
// 4. Encapsulation - internal state is properly protected

/**
 * Represents a customer order in an e-commerce system.
 * 
 * DESIGN DECISIONS EXPLAINED:
 * 
 * 1. IMMUTABILITY: This class is immutable (final class, final fields, defensive copying)
 *    WHY: Immutable objects are thread-safe, easier to reason about, and prevent
 *    accidental modifications that could lead to bugs.
 * 
 * 2. BUILDER PATTERN: Uses a builder for construction
 *    WHY: Complex objects with many parameters are easier to construct and read
 *    with a builder. It also allows for validation during construction.
 * 
 * 3. DEFENSIVE COPYING: The items list is copied to prevent external modification
 *    WHY: Even though the object is immutable, if we exposed a mutable collection,
 *    external code could modify it, breaking the immutability contract.
 * 
 * 4. VALIDATION: Constructor validates all parameters
 *    WHY: Fail-fast principle - catch errors as early as possible rather than
 *    allowing invalid objects to exist and cause problems later.
 */
final class CustomerOrder {
    // All fields are final - once set in constructor, they cannot be changed
    // This is a key requirement for immutable objects
    private final String orderId;
    private final String customerId;
    private final List<OrderItem> items;        // Will be defensively copied
    private final double totalAmount;
    private final OrderStatus status;
    private final long orderTimestamp;          // Unix timestamp for precision
    
    /**
     * Creates a new customer order.
     * 
     * @param orderId unique identifier for the order
     * @param customerId identifier of the customer placing the order
     * @param items list of items in the order (defensive copy is made)
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public CustomerOrder(String orderId, String customerId, List<OrderItem> items) {
        this.orderId = validateOrderId(orderId);
        this.customerId = validateCustomerId(customerId);
        this.items = Collections.unmodifiableList(new ArrayList<>(validateItems(items)));
        this.totalAmount = calculateTotalAmount();
        this.status = OrderStatus.PENDING;
        this.orderTimestamp = System.currentTimeMillis();
    }
    
    // Private validation methods - single responsibility
    private String validateOrderId(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        return orderId.trim();
    }
    
    private String validateCustomerId(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        return customerId.trim();
    }
    
    private List<OrderItem> validateItems(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        return items;
    }
    
    private double calculateTotalAmount() {
        return items.stream()
                   .mapToDouble(OrderItem::getTotalPrice)
                   .sum();
    }
    
    // Getters only - immutable object
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return items; } // Already unmodifiable
    public double getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public long getOrderTimestamp() { return orderTimestamp; }
    
    /**
     * Creates a new order with updated status.
     * Demonstrates immutability - returns new instance instead of modifying current.
     */
    public CustomerOrder withStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        // Would create a new instance in real implementation
        return this; // Simplified for demo
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        CustomerOrder that = (CustomerOrder) obj;
        return Objects.equals(orderId, that.orderId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
    
    @Override
    public String toString() {
        return String.format("CustomerOrder{orderId='%s', customerId='%s', totalAmount=%.2f, status=%s}", 
                           orderId, customerId, totalAmount, status);
    }
}

/**
 * Represents an item in a customer order.
 * Demonstrates value object pattern.
 */
final class OrderItem {
    private final String productId;
    private final String productName;
    private final int quantity;
    private final double unitPrice;
    
    public OrderItem(String productId, String productName, int quantity, double unitPrice) {
        this.productId = validateProductId(productId);
        this.productName = validateProductName(productName);
        this.quantity = validateQuantity(quantity);
        this.unitPrice = validateUnitPrice(unitPrice);
    }
    
    private String validateProductId(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        return productId.trim();
    }
    
    private String validateProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        return productName.trim();
    }
    
    private int validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return quantity;
    }
    
    private double validateUnitPrice(double unitPrice) {
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        return unitPrice;
    }
    
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    
    public double getTotalPrice() {
        return quantity * unitPrice;
    }
    
    @Override
    public String toString() {
        return String.format("OrderItem{%s x%d @ $%.2f}", productName, quantity, unitPrice);
    }
}

enum OrderStatus {
    PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
}

// SOLID Principles Demonstration

/**
 * Single Responsibility Principle (SRP) - Each class has one reason to change
 */

// Order processing - only handles order logic
class OrderProcessor {
    private final OrderValidator validator;
    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    
    public OrderProcessor(OrderValidator validator, InventoryService inventoryService, PaymentService paymentService) {
        this.validator = Objects.requireNonNull(validator, "Validator cannot be null");
        this.inventoryService = Objects.requireNonNull(inventoryService, "Inventory service cannot be null");
        this.paymentService = Objects.requireNonNull(paymentService, "Payment service cannot be null");
    }
    
    public ProcessingResult processOrder(CustomerOrder order) {
        try {
            // Validate order
            ValidationResult validationResult = validator.validate(order);
            if (!validationResult.isValid()) {
                return ProcessingResult.failure("Validation failed: " + validationResult.getErrorMessage());
            }
            
            // Check inventory
            if (!inventoryService.isAvailable(order.getItems())) {
                return ProcessingResult.failure("Insufficient inventory");
            }
            
            // Process payment
            PaymentResult paymentResult = paymentService.processPayment(order.getTotalAmount());
            if (!paymentResult.isSuccessful()) {
                return ProcessingResult.failure("Payment failed: " + paymentResult.getErrorMessage());
            }
            
            return ProcessingResult.success("Order processed successfully");
            
        } catch (Exception e) {
            return ProcessingResult.failure("Processing error: " + e.getMessage());
        }
    }
}

// Order validation - only handles validation logic
class OrderValidator {
    private static final double MAX_ORDER_AMOUNT = 10000.0;
    private static final int MAX_ITEMS_PER_ORDER = 50;
    
    public ValidationResult validate(CustomerOrder order) {
        if (order == null) {
            return ValidationResult.invalid("Order cannot be null");
        }
        
        if (order.getTotalAmount() > MAX_ORDER_AMOUNT) {
            return ValidationResult.invalid("Order amount exceeds maximum limit");
        }
        
        if (order.getItems().size() > MAX_ITEMS_PER_ORDER) {
            return ValidationResult.invalid("Too many items in order");
        }
        
        return ValidationResult.valid();
    }
}

/**
 * Open/Closed Principle (OCP) - Open for extension, closed for modification
 */

// Abstract base for payment processing
abstract class PaymentProcessor {
    public final PaymentResult processPayment(double amount) {
        if (amount <= 0) {
            return PaymentResult.failure("Invalid amount");
        }
        
        return doProcessPayment(amount);
    }
    
    protected abstract PaymentResult doProcessPayment(double amount);
}

// Concrete implementations - can be added without modifying existing code
class CreditCardPaymentProcessor extends PaymentProcessor {
    @Override
    protected PaymentResult doProcessPayment(double amount) {
        // Credit card processing logic
        return PaymentResult.success("Credit card payment processed");
    }
}

class PayPalPaymentProcessor extends PaymentProcessor {
    @Override
    protected PaymentResult doProcessPayment(double amount) {
        // PayPal processing logic
        return PaymentResult.success("PayPal payment processed");
    }
}

/**
 * Interface Segregation Principle (ISP) - Clients shouldn't depend on unused interfaces
 */

// Separate interfaces for different responsibilities
interface OrderReader {
    Optional<CustomerOrder> findOrderById(String orderId);
    List<CustomerOrder> findOrdersByCustomer(String customerId);
}

interface OrderWriter {
    void saveOrder(CustomerOrder order);
    void updateOrderStatus(String orderId, OrderStatus status);
}

interface OrderDeleter {
    boolean cancelOrder(String orderId);
}

/**
 * Dependency Inversion Principle (DIP) - Depend on abstractions, not concretions
 */

// High-level modules depend on abstractions
interface InventoryService {
    boolean isAvailable(List<OrderItem> items);
    void reserveItems(List<OrderItem> items);
}

interface PaymentService {
    PaymentResult processPayment(double amount);
}

// Result classes for clean error handling
class ProcessingResult {
    private final boolean success;
    private final String message;
    
    private ProcessingResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public static ProcessingResult success(String message) {
        return new ProcessingResult(true, message);
    }
    
    public static ProcessingResult failure(String message) {
        return new ProcessingResult(false, message);
    }
    
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}

class ValidationResult {
    private final boolean valid;
    private final String errorMessage;
    
    private ValidationResult(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }
    
    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }
    
    public static ValidationResult invalid(String errorMessage) {
        return new ValidationResult(false, errorMessage);
    }
    
    public boolean isValid() { return valid; }
    public String getErrorMessage() { return errorMessage; }
}

class PaymentResult {
    private final boolean successful;
    private final String errorMessage;
    
    private PaymentResult(boolean successful, String errorMessage) {
        this.successful = successful;
        this.errorMessage = errorMessage;
    }
    
    public static PaymentResult success(String message) {
        return new PaymentResult(true, null);
    }
    
    public static PaymentResult failure(String errorMessage) {
        return new PaymentResult(false, errorMessage);
    }
    
    public boolean isSuccessful() { return successful; }
    public String getErrorMessage() { return errorMessage; }
}

// Resource Management Best Practices
class ResourceManagementExample {
    
    /**
     * Demonstrates proper resource management with try-with-resources
     */
    public static String readFileContent(String filename) {
        // Try-with-resources ensures automatic resource cleanup
        try (Scanner scanner = new Scanner(Objects.requireNonNull(
                ResourceManagementExample.class.getResourceAsStream("/" + filename)))) {
            
            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            return content.toString();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file: " + filename, e);
        }
    }
    
    /**
     * Demonstrates proper exception handling and resource cleanup
     */
    public static void processDataWithCleanup() {
        Map<String, Object> resources = new ConcurrentHashMap<>();
        
        try {
            // Acquire resources
            resources.put("connection", acquireConnection());
            resources.put("cache", initializeCache());
            
            // Process data
            performDataProcessing(resources);
            
        } catch (Exception e) {
            System.err.println("Error during processing: " + e.getMessage());
            throw new RuntimeException("Processing failed", e);
        } finally {
            // Clean up resources in reverse order
            cleanupResources(resources);
        }
    }
    
    private static Object acquireConnection() {
        return "Database Connection";
    }
    
    private static Object initializeCache() {
        return new HashMap<String, String>();
    }
    
    private static void performDataProcessing(Map<String, Object> resources) {
        System.out.println("Processing data with resources: " + resources.keySet());
    }
    
    private static void cleanupResources(Map<String, Object> resources) {
        resources.forEach((name, resource) -> {
            try {
                System.out.println("Cleaning up resource: " + name);
                // Actual cleanup logic would go here
            } catch (Exception e) {
                System.err.println("Failed to cleanup resource " + name + ": " + e.getMessage());
            }
        });
    }
}

// Naming Conventions and Code Organization
class NamingConventionsExample {
    
    // Constants: UPPER_SNAKE_CASE
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final long TIMEOUT_MILLISECONDS = 5000L;
    
    // Instance variables: camelCase
    private final String serviceName;
    private final List<String> supportedOperations;
    private volatile boolean isInitialized;
    
    public NamingConventionsExample(String serviceName) {
        this.serviceName = serviceName;
        this.supportedOperations = new ArrayList<>();
        this.isInitialized = false;
    }
    
    // Methods: camelCase, descriptive verbs
    public void initializeService() {
        if (isInitialized) {
            throw new IllegalStateException("Service already initialized");
        }
        
        performInitializationSteps();
        isInitialized = true;
    }
    
    public boolean isOperationSupported(String operationName) {
        return supportedOperations.contains(operationName);
    }
    
    public List<String> getSupportedOperations() {
        return Collections.unmodifiableList(supportedOperations);
    }
    
    // Private helper methods: clear, single purpose
    private void performInitializationSteps() {
        loadConfiguration();
        validateServiceSettings();
        registerServiceEndpoints();
        
        // Demonstrate usage of constants
        System.out.println("Max retry attempts: " + MAX_RETRY_ATTEMPTS);
        System.out.println("Default encoding: " + DEFAULT_ENCODING);
        System.out.println("Timeout: " + TIMEOUT_MILLISECONDS + "ms");
    }
    
    private void loadConfiguration() {
        System.out.println("Loading configuration for service: " + serviceName);
    }
    
    private void validateServiceSettings() {
        System.out.println("Validating settings for service: " + serviceName);
    }
    
    private void registerServiceEndpoints() {
        System.out.println("Registering endpoints for service: " + serviceName);
    }
}

public class CodeQualityDemo {
    
    /**
     * Demonstrates clean code principles and best practices
     */
    public static void demonstrateCleanCode() {
        System.out.println("=== CLEAN CODE DEMONSTRATION ===");
        
        // Create order items with meaningful names
        List<OrderItem> orderItems = Arrays.asList(
            new OrderItem("LAPTOP001", "Gaming Laptop", 1, 1299.99),
            new OrderItem("MOUSE001", "Wireless Mouse", 2, 29.99),
            new OrderItem("KEYBOARD001", "Mechanical Keyboard", 1, 89.99)
        );
        
        // Create customer order
        CustomerOrder order = new CustomerOrder("ORD-2025-001", "CUST-12345", orderItems);
        
        System.out.println("📋 Created order: " + order);
        System.out.println("💰 Order total: $" + String.format("%.2f", order.getTotalAmount()));
        System.out.println("📦 Items count: " + order.getItems().size());
        
        // Demonstrate immutability
        System.out.println("\n🔒 Demonstrating immutability:");
        System.out.println("Original order status: " + order.getStatus());
        CustomerOrder updatedOrder = order.withStatus(OrderStatus.CONFIRMED);
        System.out.println("Updated order status: " + updatedOrder.getStatus());
        
        // Show defensive copying
        List<OrderItem> items = order.getItems();
        System.out.println("Items list is unmodifiable: " + 
                         (items.getClass().getSimpleName().contains("Unmodifiable")));
    }
    
    /**
     * Demonstrates SOLID principles
     */
    public static void demonstrateSOLIDPrinciples() {
        System.out.println("\n=== SOLID PRINCIPLES DEMONSTRATION ===");
        
        // Create dependencies (would typically be injected)
        OrderValidator validator = new OrderValidator();
        InventoryService inventoryService = new MockInventoryService();
        PaymentService paymentService = new MockPaymentService();
        
        // Dependency Injection - high-level module depends on abstractions
        OrderProcessor processor = new OrderProcessor(validator, inventoryService, paymentService);
        
        // Create test order
        List<OrderItem> items = Arrays.asList(
            new OrderItem("PROD001", "Test Product", 2, 50.0)
        );
        CustomerOrder order = new CustomerOrder("ORD-001", "CUST-001", items);
        
        // Process order
        ProcessingResult result = processor.processOrder(order);
        System.out.println("🏭 Order processing result: " + result.getMessage());
        
        // Demonstrate Open/Closed Principle
        System.out.println("\n🔓 Payment processor extensions:");
        PaymentProcessor creditCard = new CreditCardPaymentProcessor();
        PaymentProcessor paypal = new PayPalPaymentProcessor();
        
        PaymentResult ccResult = creditCard.processPayment(100.0);
        PaymentResult ppResult = paypal.processPayment(100.0);
        
        System.out.println("💳 Credit card: " + ccResult.getErrorMessage());
        System.out.println("🅿️ PayPal: " + ppResult.getErrorMessage());
    }
    
    /**
     * Demonstrates resource management best practices
     */
    public static void demonstrateResourceManagement() {
        System.out.println("\n=== RESOURCE MANAGEMENT DEMONSTRATION ===");
        
        try {
            ResourceManagementExample.processDataWithCleanup();
            System.out.println("✅ Resource management completed successfully");
        } catch (Exception e) {
            System.out.println("❌ Resource management failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates naming conventions and code organization
     */
    public static void demonstrateNamingConventions() {
        System.out.println("\n=== NAMING CONVENTIONS DEMONSTRATION ===");
        
        NamingConventionsExample service = new NamingConventionsExample("OrderProcessingService");
        
        System.out.println("🔧 Initializing service...");
        service.initializeService();
        
        System.out.println("🔍 Supported operations: " + service.getSupportedOperations());
        System.out.println("✓ Service initialization completed");
    }
    
    /**
     * Demonstrates error handling best practices
     */
    public static void demonstrateErrorHandling() {
        System.out.println("\n=== ERROR HANDLING DEMONSTRATION ===");
        
        // Good: Specific exceptions with meaningful messages
        try {
            new CustomerOrder("", "CUST-001", Collections.emptyList());
        } catch (IllegalArgumentException e) {
            System.out.println("🚫 Caught validation error: " + e.getMessage());
        }
        
        // Good: Using Optional to avoid null pointer exceptions
        Optional<String> optionalValue = Optional.ofNullable(getValueThatMightBeNull());
        String safeValue = optionalValue.orElse("Default Value");
        System.out.println("🛡️ Safe value handling: " + safeValue);
        
        // Good: Result objects instead of exceptions for expected failures
        ValidationResult validation = new OrderValidator().validate(null);
        if (!validation.isValid()) {
            System.out.println("⚠️ Validation failed: " + validation.getErrorMessage());
        }
    }
    
    private static String getValueThatMightBeNull() {
        return Math.random() > 0.5 ? "Some Value" : null;
    }
    
    /**
     * Analyzes code quality principles and best practices
     */
    public static void analyzeCodeQuality() {
        System.out.println("\n=== CODE QUALITY ANALYSIS ===");
        
        System.out.println("Clean Code Principles:");
        System.out.println("• Meaningful names: Classes, methods, and variables should be self-explanatory");
        System.out.println("• Small functions: Each function should do one thing well");
        System.out.println("• Clear comments: Explain 'why', not 'what'");
        System.out.println("• Consistent formatting: Follow team/project standards");
        System.out.println("• No duplication: DRY (Don't Repeat Yourself)");
        
        System.out.println("\nSOLID Principles:");
        System.out.println("• Single Responsibility: One reason to change");
        System.out.println("• Open/Closed: Open for extension, closed for modification");
        System.out.println("• Liskov Substitution: Subtypes must be substitutable");
        System.out.println("• Interface Segregation: Many specific interfaces vs. one general");
        System.out.println("• Dependency Inversion: Depend on abstractions, not concretions");
        
        System.out.println("\nError Handling Best Practices:");
        System.out.println("• Use specific exception types");
        System.out.println("• Provide meaningful error messages");
        System.out.println("• Handle exceptions at appropriate levels");
        System.out.println("• Use Optional to avoid null pointer exceptions");
        System.out.println("• Consider Result objects for expected failures");
        
        System.out.println("\nResource Management:");
        System.out.println("• Use try-with-resources for automatic cleanup");
        System.out.println("• Always clean up in finally blocks");
        System.out.println("• Handle cleanup exceptions gracefully");
        System.out.println("• Follow RAII (Resource Acquisition Is Initialization)");
        
        System.out.println("\nCode Organization:");
        System.out.println("• Group related functionality");
        System.out.println("• Use packages to organize code logically");
        System.out.println("• Keep public APIs minimal");
        System.out.println("• Prefer composition over inheritance");
        System.out.println("• Use dependency injection for loose coupling");
    }
    
    /**
     * Main method demonstrating code quality practices
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Code Quality and Best Practices Demonstration");
        System.out.println("=================================================");
        
        demonstrateCleanCode();
        demonstrateSOLIDPrinciples();
        demonstrateResourceManagement();
        demonstrateNamingConventions();
        demonstrateErrorHandling();
        analyzeCodeQuality();
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("Code quality is essential for maintainable, scalable software:");
        System.out.println("• Write code that is easy to read and understand");
        System.out.println("• Follow established principles and patterns");
        System.out.println("• Handle errors gracefully and meaningfully");
        System.out.println("• Manage resources properly to avoid leaks");
        System.out.println("• Organize code logically with clear separations of concern");
    }
}

// Mock implementations for demonstration
class MockInventoryService implements InventoryService {
    @Override
    public boolean isAvailable(List<OrderItem> items) {
        return true; // Simplified for demo
    }
    
    @Override
    public void reserveItems(List<OrderItem> items) {
        System.out.println("📦 Reserved " + items.size() + " item types");
    }
}

class MockPaymentService implements PaymentService {
    @Override
    public PaymentResult processPayment(double amount) {
        if (amount > 0) {
            return PaymentResult.success("Payment processed: $" + amount);
        }
        return PaymentResult.failure("Invalid amount");
    }
}
