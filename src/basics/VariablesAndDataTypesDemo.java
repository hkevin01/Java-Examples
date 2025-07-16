package basics;

/**
 * VariablesAndDataTypesDemo - Demonstrates Java variables and primitive data types
 * 
 * This class covers:
 * - Primitive data types (byte, short, int, long, float, double, boolean, char)
 * - Variable declaration and initialization
 * - Type casting and conversion
 * - Constants and final variables
 * - Variable scope
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class VariablesAndDataTypesDemo {
    
    // Class-level (static) variables
    static int classVariable = 100;
    static final double PI = 3.14159265359; // Constant
    
    // Instance variables
    private String instanceVariable = "Instance level";
    
    /**
     * Demonstrates primitive data types and their ranges
     */
    public static void demonstratePrimitiveTypes() {
        System.out.println("=== PRIMITIVE DATA TYPES ===");
        
        // Integer types
        byte byteVar = 127;                    // 8-bit: -128 to 127
        short shortVar = 32767;                // 16-bit: -32,768 to 32,767
        int intVar = 2147483647;               // 32-bit: -2^31 to 2^31-1
        long longVar = 9223372036854775807L;   // 64-bit: -2^63 to 2^63-1
        
        // Floating-point types
        float floatVar = 3.14159f;             // 32-bit single precision
        double doubleVar = 3.14159265359;      // 64-bit double precision
        
        // Boolean type
        boolean booleanVar = true;             // true or false
        
        // Character type
        char charVar = 'A';                    // 16-bit Unicode character
        char unicodeChar = '\u0041';           // Unicode representation of 'A'
        
        System.out.println("byte value: " + byteVar + " (range: " + Byte.MIN_VALUE + " to " + Byte.MAX_VALUE + ")");
        System.out.println("short value: " + shortVar + " (range: " + Short.MIN_VALUE + " to " + Short.MAX_VALUE + ")");
        System.out.println("int value: " + intVar + " (range: " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ")");
        System.out.println("long value: " + longVar + " (range: " + Long.MIN_VALUE + " to " + Long.MAX_VALUE + ")");
        System.out.println("float value: " + floatVar + " (range: " + Float.MIN_VALUE + " to " + Float.MAX_VALUE + ")");
        System.out.println("double value: " + doubleVar + " (range: " + Double.MIN_VALUE + " to " + Double.MAX_VALUE + ")");
        System.out.println("boolean value: " + booleanVar);
        System.out.println("char value: " + charVar + " (Unicode: " + unicodeChar + ")");
    }
    
    /**
     * Demonstrates type casting and conversion
     */
    public static void demonstrateTypeCasting() {
        System.out.println("\n=== TYPE CASTING AND CONVERSION ===");
        
        // Implicit casting (widening)
        int intValue = 100;
        long longValue = intValue;        // int to long (automatic)
        double doubleValue = intValue;    // int to double (automatic)
        
        System.out.println("Implicit casting:");
        System.out.println("int: " + intValue + " -> long: " + longValue + " -> double: " + doubleValue);
        
        // Explicit casting (narrowing)
        double largeDouble = 123.456;
        int truncatedInt = (int) largeDouble;    // Truncates decimal part
        byte smallByte = (byte) intValue;        // May lose data if out of range
        
        System.out.println("\nExplicit casting:");
        System.out.println("double: " + largeDouble + " -> int: " + truncatedInt);
        System.out.println("int: " + intValue + " -> byte: " + smallByte);
        
        // String conversion
        String numberString = "123";
        int parsedInt = Integer.parseInt(numberString);
        String backToString = String.valueOf(parsedInt);
        
        System.out.println("\nString conversion:");
        System.out.println("String: \"" + numberString + "\" -> int: " + parsedInt + " -> String: \"" + backToString + "\"");
    }
    
    /**
     * Demonstrates variable scope
     */
    public static void demonstrateVariableScope() {
        System.out.println("\n=== VARIABLE SCOPE ===");
        
        // Method-level variable
        int methodVariable = 50;
        System.out.println("Method variable: " + methodVariable);
        System.out.println("Class variable: " + classVariable);
        System.out.println("Constant PI: " + PI);
        
        // Block scope
        if (true) {
            int blockVariable = 25;
            System.out.println("Block variable: " + blockVariable);
            System.out.println("Method variable accessible in block: " + methodVariable);
            
            // Nested block
            {
                int nestedBlockVariable = 10;
                System.out.println("Nested block variable: " + nestedBlockVariable);
            }
            // nestedBlockVariable is not accessible here
        }
        // blockVariable is not accessible here
        
        // Loop scope
        for (int loopVariable = 0; loopVariable < 3; loopVariable++) {
            System.out.println("Loop variable: " + loopVariable);
        }
        // loopVariable is not accessible here
    }
    
    /**
     * Demonstrates constants and final variables
     */
    public static void demonstrateConstants() {
        System.out.println("\n=== CONSTANTS AND FINAL VARIABLES ===");
        
        // Final local variable
        final int FINAL_LOCAL = 42;
        System.out.println("Final local variable: " + FINAL_LOCAL);
        
        // Class constant
        System.out.println("Class constant PI: " + PI);
        
        // Final reference (object can be modified, but reference cannot change)
        final StringBuilder finalBuilder = new StringBuilder("Hello");
        finalBuilder.append(" World");  // This is allowed
        System.out.println("Final reference (modifiable content): " + finalBuilder.toString());
        
        // finalBuilder = new StringBuilder(); // This would cause compilation error
    }
    
    /**
     * Demonstrates default values for different types
     */
    public static void demonstrateDefaultValues() {
        System.out.println("\n=== DEFAULT VALUES ===");
        
        // Create an instance to show default values of instance variables
        class DefaultValuesExample {
            byte defaultByte;
            short defaultShort;
            int defaultInt;
            long defaultLong;
            float defaultFloat;
            double defaultDouble;
            boolean defaultBoolean;
            char defaultChar;
            String defaultString;
        }
        
        DefaultValuesExample example = new DefaultValuesExample();
        System.out.println("Default byte: " + example.defaultByte);
        System.out.println("Default short: " + example.defaultShort);
        System.out.println("Default int: " + example.defaultInt);
        System.out.println("Default long: " + example.defaultLong);
        System.out.println("Default float: " + example.defaultFloat);
        System.out.println("Default double: " + example.defaultDouble);
        System.out.println("Default boolean: " + example.defaultBoolean);
        System.out.println("Default char: '" + example.defaultChar + "' (ASCII: " + (int)example.defaultChar + ")");
        System.out.println("Default String: " + example.defaultString);
    }
    
    /**
     * Main method demonstrating all variable and data type concepts
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Variables and Data Types Demonstration");
        System.out.println("==========================================");
        
        demonstratePrimitiveTypes();
        demonstrateTypeCasting();
        demonstrateVariableScope();
        demonstrateConstants();
        demonstrateDefaultValues();
        
        System.out.println("\n=== PRACTICAL EXAMPLES ===");
        
        // Real-world usage example
        int studentAge = 20;
        double gpa = 3.75;
        boolean isEnrolled = true;
        char grade = 'A';
        
        System.out.println("Student Information:");
        System.out.println("Age: " + studentAge + " years");
        System.out.println("GPA: " + gpa);
        System.out.println("Enrolled: " + (isEnrolled ? "Yes" : "No"));
        System.out.println("Grade: " + grade);
        
        // Mathematical operations with different types
        int a = 10;
        int b = 3;
        System.out.println("\nMathematical Operations:");
        System.out.println(a + " + " + b + " = " + (a + b));
        System.out.println(a + " - " + b + " = " + (a - b));
        System.out.println(a + " * " + b + " = " + (a * b));
        System.out.println(a + " / " + b + " = " + (a / b) + " (integer division)");
        System.out.println(a + " / " + b + " = " + ((double)a / b) + " (double division)");
        System.out.println(a + " % " + b + " = " + (a % b) + " (remainder)");
    }
}
