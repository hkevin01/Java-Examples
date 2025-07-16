package basics;

/**
 * MethodsAndFunctionsDemo - Demonstrates Java methods and functions
 * 
 * This class covers:
 * - Method declaration and definition
 * - Parameters and arguments
 * - Return types and return statements
 * - Method overloading
 * - Static vs instance methods
 * - Variable arguments (varargs)
 * - Recursion
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class MethodsAndFunctionsDemo {
    
    // Instance variable for demonstration
    private String instanceName = "MethodsDemo";
    
    /**
     * Basic method with no parameters and no return value
     */
    public static void basicMethod() {
        System.out.println("This is a basic method with no parameters and no return value");
    }
    
    /**
     * Method with parameters and return value
     * @param a first number
     * @param b second number
     * @return sum of a and b
     */
    public static int addNumbers(int a, int b) {
        return a + b;
    }
    
    /**
     * Method with multiple parameters of different types
     * @param name person's name
     * @param age person's age
     * @param height person's height in meters
     * @return formatted string with person's information
     */
    public static String formatPersonInfo(String name, int age, double height) {
        return String.format("Name: %s, Age: %d, Height: %.2f meters", name, age, height);
    }
    
    /**
     * Method demonstrating method overloading - same name, different parameters
     * @param number integer to multiply
     * @return number multiplied by 2
     */
    public static int multiply(int number) {
        return number * 2;
    }
    
    /**
     * Overloaded method with two parameters
     * @param a first number
     * @param b second number
     * @return product of a and b
     */
    public static int multiply(int a, int b) {
        return a * b;
    }
    
    /**
     * Overloaded method with double parameters
     * @param a first number
     * @param b second number
     * @return product of a and b
     */
    public static double multiply(double a, double b) {
        return a * b;
    }
    
    /**
     * Method with variable arguments (varargs)
     * @param numbers variable number of integers
     * @return sum of all numbers
     */
    public static int sumNumbers(int... numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
    
    /**
     * Method with varargs and other parameters
     * @param message prefix message
     * @param numbers variable number of integers
     * @return formatted string with message and sum
     */
    public static String sumWithMessage(String message, int... numbers) {
        int sum = sumNumbers(numbers);
        return message + ": " + sum;
    }
    
    /**
     * Recursive method - calculates factorial
     * @param n number to calculate factorial for
     * @return factorial of n
     */
    public static long factorial(int n) {
        // Base case
        if (n <= 1) {
            return 1;
        }
        // Recursive case
        return n * factorial(n - 1);
    }
    
    /**
     * Recursive method - calculates Fibonacci number
     * @param n position in Fibonacci sequence
     * @return Fibonacci number at position n
     */
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
    
    /**
     * Instance method - requires object to be called
     * @return instance variable value
     */
    public String getInstanceName() {
        return instanceName;
    }
    
    /**
     * Instance method that modifies instance state
     * @param newName new name to set
     */
    public void setInstanceName(String newName) {
        this.instanceName = newName;
    }
    
    /**
     * Instance method that uses both instance and static methods
     * @param multiplier number to multiply with
     * @return modified instance name
     */
    public String processInstanceName(int multiplier) {
        // Using static method from within instance method
        int result = multiply(instanceName.length(), multiplier);
        return instanceName + " (length × " + multiplier + " = " + result + ")";
    }
    
    /**
     * Method that demonstrates different return types
     * @param operation type of operation to perform
     * @param value input value
     * @return result based on operation type
     */
    public static Object performOperation(String operation, double value) {
        switch (operation.toLowerCase()) {
            case "square":
                return value * value;  // Returns Double
            case "sqrt":
                return Math.sqrt(value);  // Returns Double
            case "string":
                return "Value: " + value;  // Returns String
            case "boolean":
                return value > 0;  // Returns Boolean
            case "integer":
                return (int) value;  // Returns Integer
            default:
                return null;  // Returns null
        }
    }
    
    /**
     * Method demonstrating parameter passing (pass by value)
     * @param number primitive parameter (passed by value)
     * @param array reference parameter (reference passed by value)
     */
    public static void demonstrateParameterPassing(int number, int[] array) {
        System.out.println("Inside method - original number: " + number);
        System.out.println("Inside method - original array: " + java.util.Arrays.toString(array));
        
        // Modifying primitive parameter (won't affect original)
        number = 999;
        
        // Modifying array contents (will affect original)
        if (array.length > 0) {
            array[0] = 999;
        }
        
        System.out.println("Inside method - modified number: " + number);
        System.out.println("Inside method - modified array: " + java.util.Arrays.toString(array));
    }
    
    /**
     * Utility method for validating input
     * @param value value to validate
     * @param min minimum allowed value
     * @param max maximum allowed value
     * @return true if value is within range, false otherwise
     */
    public static boolean isValidRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
    
    /**
     * Demonstrates method chaining pattern
     * @return MethodsAndFunctionsDemo instance for chaining
     */
    public MethodsAndFunctionsDemo chainableMethod() {
        System.out.println("Chainable method called on: " + instanceName);
        return this;  // Return this for chaining
    }
    
    /**
     * Another chainable method
     * @param suffix string to append
     * @return MethodsAndFunctionsDemo instance for chaining
     */
    public MethodsAndFunctionsDemo appendToName(String suffix) {
        this.instanceName += suffix;
        return this;
    }
    
    /**
     * Main method demonstrating all method concepts
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Methods and Functions Demonstration");
        System.out.println("=======================================");
        
        // Basic method call
        System.out.println("=== BASIC METHOD CALLS ===");
        basicMethod();
        
        // Methods with parameters and return values
        System.out.println("\n=== METHODS WITH PARAMETERS AND RETURN VALUES ===");
        int sum = addNumbers(5, 3);
        System.out.println("5 + 3 = " + sum);
        
        String personInfo = formatPersonInfo("Alice", 25, 1.65);
        System.out.println(personInfo);
        
        // Method overloading
        System.out.println("\n=== METHOD OVERLOADING ===");
        System.out.println("multiply(5): " + multiply(5));
        System.out.println("multiply(4, 6): " + multiply(4, 6));
        System.out.println("multiply(2.5, 3.2): " + multiply(2.5, 3.2));
        
        // Variable arguments
        System.out.println("\n=== VARIABLE ARGUMENTS (VARARGS) ===");
        System.out.println("sumNumbers(1, 2, 3): " + sumNumbers(1, 2, 3));
        System.out.println("sumNumbers(10, 20, 30, 40, 50): " + sumNumbers(10, 20, 30, 40, 50));
        System.out.println(sumWithMessage("Total", 1, 2, 3, 4, 5));
        
        // Recursion
        System.out.println("\n=== RECURSIVE METHODS ===");
        System.out.println("factorial(5): " + factorial(5));
        System.out.println("fibonacci(8): " + fibonacci(8));
        
        // Fibonacci sequence
        System.out.print("Fibonacci sequence (first 10): ");
        for (int i = 0; i < 10; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        System.out.println();
        
        // Instance methods
        System.out.println("\n=== INSTANCE METHODS ===");
        MethodsAndFunctionsDemo demo = new MethodsAndFunctionsDemo();
        System.out.println("Instance name: " + demo.getInstanceName());
        
        demo.setInstanceName("MyDemo");
        System.out.println("Modified instance name: " + demo.getInstanceName());
        System.out.println(demo.processInstanceName(3));
        
        // Different return types
        System.out.println("\n=== DIFFERENT RETURN TYPES ===");
        System.out.println("square(4): " + performOperation("square", 4));
        System.out.println("sqrt(16): " + performOperation("sqrt", 16));
        System.out.println("string(42): " + performOperation("string", 42));
        System.out.println("boolean(-5): " + performOperation("boolean", -5));
        System.out.println("integer(3.14): " + performOperation("integer", 3.14));
        
        // Parameter passing
        System.out.println("\n=== PARAMETER PASSING ===");
        int originalNumber = 100;
        int[] originalArray = {1, 2, 3, 4, 5};
        
        System.out.println("Before method call:");
        System.out.println("Number: " + originalNumber);
        System.out.println("Array: " + java.util.Arrays.toString(originalArray));
        
        demonstrateParameterPassing(originalNumber, originalArray);
        
        System.out.println("After method call:");
        System.out.println("Number: " + originalNumber + " (unchanged)");
        System.out.println("Array: " + java.util.Arrays.toString(originalArray) + " (first element changed)");
        
        // Input validation
        System.out.println("\n=== INPUT VALIDATION ===");
        int testValue = 75;
        boolean isValid = isValidRange(testValue, 0, 100);
        System.out.println("Is " + testValue + " in range 0-100? " + isValid);
        
        // Method chaining
        System.out.println("\n=== METHOD CHAINING ===");
        demo.chainableMethod()
            .appendToName("_Chained")
            .appendToName("_More");
        System.out.println("Final instance name after chaining: " + demo.getInstanceName());
        
        System.out.println("\n=== BEST PRACTICES ===");
        System.out.println("1. Use meaningful method names");
        System.out.println("2. Keep methods focused on a single task");
        System.out.println("3. Use appropriate access modifiers");
        System.out.println("4. Document methods with JavaDoc comments");
        System.out.println("5. Validate input parameters");
        System.out.println("6. Handle edge cases");
        System.out.println("7. Use method overloading wisely");
    }
}
