


package src.oop;

/**
 * AccessModifiersDemo - Demonstrates Java access modifiers and variable types
 * 
 * This class showcases the four access modifiers in Java:
 * - public: accessible from anywhere
 * - protected: accessible within package and subclasses
 * - default (package): accessible within package only
 * - private: accessible within the same class only
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class AccessModifiersDemo {
    
    //-------------------------------------
    // Data Type Examples
    //-------------------------------------
    // Primitive Data Types:
    // byte: 8-bit signed integer (-128 to 127)
    // short: 16-bit signed integer (-32,768 to 32,767)
    // int: 32-bit signed integer (-2^31 to 2^31-1)
    // long: 64-bit signed integer (-2^63 to 2^63-1)
    // float: 32-bit floating point
    // double: 64-bit double-precision floating point
    // boolean: true or false
    // char: 16-bit Unicode character
    
    //-------------------------------------
    // Access Modifier Examples
    //-------------------------------------
    
    // PRIVATE: Only accessible within this class
    private int privateVariable = 10;
    private static int privateStaticCounter = 0;
    
    // PROTECTED: Accessible within package and by subclasses
    protected String protectedMessage = "Protected access";
    
    // PUBLIC: Accessible from anywhere the class is accessible
    public double publicValue = 3.14159;
    
    // PACKAGE (default): Accessible within the same package only
    int packageVariable = 42;
    
    // STATIC: Class-level variable, shared across all instances
    public static final String CLASS_NAME = "AccessModifiersDemo";
    private static int instanceCount = 0;
    
    // FINAL: Cannot be reassigned after initialization
    public final int FINAL_CONSTANT = 100;
    private final String finalInstanceVar;
    
    //-------------------------------------
    // Constructors
    //-------------------------------------
    
    /**
     * Default constructor
     */
    public AccessModifiersDemo() {
        this.finalInstanceVar = "Initialized in constructor";
        instanceCount++;
        privateStaticCounter++;
    }
    
    /**
     * Parameterized constructor
     * @param value initial value for public variable
     */
    public AccessModifiersDemo(double value) {
        this();
        this.publicValue = value;
    }
    
    //-------------------------------------
    // Public Methods
    //-------------------------------------
    
    /**
     * Public method - accessible from anywhere
     * @return current private variable value
     */
    public int getPrivateVariable() {
        return privateVariable;
    }
    
    /**
     * Public setter with validation
     * @param value new value for private variable
     */
    public void setPrivateVariable(int value) {
        if (value >= 0) {
            this.privateVariable = value;
        } else {
            System.out.println("Value must be non-negative");
        }
    }
    
    /**
     * Demonstrates all access levels within the same class
     */
    public void demonstrateAccess() {
        System.out.println("=== Access Demonstration ===");
        System.out.println("Private variable: " + privateVariable);
        System.out.println("Protected message: " + protectedMessage);
        System.out.println("Public value: " + publicValue);
        System.out.println("Package variable: " + packageVariable);
        System.out.println("Final constant: " + FINAL_CONSTANT);
        System.out.println("Final instance var: " + finalInstanceVar);
        
        // Can access private methods within same class
        privateMethod();
        protectedMethod();
    }
    
    /**
     * Static method example
     * @return total number of instances created
     */
    public static int getInstanceCount() {
        return instanceCount;
    }
    
    //-------------------------------------
    // Protected Methods
    //-------------------------------------
    
    /**
     * Protected method - accessible within package and by subclasses
     */
    protected void protectedMethod() {
        System.out.println("Protected method called");
        System.out.println("Private static counter: " + privateStaticCounter);
    }
    
    //-------------------------------------
    // Package Methods
    //-------------------------------------
    
    /**
     * Package method - accessible within same package only
     */
    void packageMethod() {
        System.out.println("Package method called");
    }
    
    //-------------------------------------
    // Private Methods
    //-------------------------------------
    
    /**
     * Private method - only accessible within this class
     */
    private void privateMethod() {
        System.out.println("Private method called");
        System.out.println("Only accessible from within AccessModifiersDemo class");
    }
    
    /**
     * Private helper method for validation
     * @param value value to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidValue(int value) {
        return value >= 0 && value <= 1000;
    }
    
    //-------------------------------------
    // Main method for testing
    //-------------------------------------
    
    /**
     * Main method to demonstrate access modifiers
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Access Modifiers Demonstration");
        System.out.println("===================================");
        
        // Create instances
        AccessModifiersDemo demo1 = new AccessModifiersDemo();
        AccessModifiersDemo demo2 = new AccessModifiersDemo(2.718);
        
        // Demonstrate access within same class
        demo1.demonstrateAccess();
        
        System.out.println("\n=== Static Variable Access ===");
        System.out.println("Class name: " + CLASS_NAME);
        System.out.println("Total instances: " + getInstanceCount());
        
        System.out.println("\n=== Public Access ===");
        System.out.println("Demo1 public value: " + demo1.publicValue);
        System.out.println("Demo2 public value: " + demo2.publicValue);
        
        // Modify through public methods
        demo1.setPrivateVariable(50);
        System.out.println("Modified private variable: " + demo1.getPrivateVariable());
        
        // Package access (works within same package)
        demo1.packageMethod();
        System.out.println("Package variable: " + demo1.packageVariable);
        
        // Protected access (works within same package)
        demo1.protectedMethod();
    }
}
