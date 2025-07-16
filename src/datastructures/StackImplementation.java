package datastructures;

/**
 * StackImplementation - Custom implementation of a Stack data structure
 * 
 * This class demonstrates:
 * - Stack implementation using arrays
 * - LIFO (Last In, First Out) principle
 * - Stack operations: push, pop, peek, isEmpty, size
 * - Dynamic resizing
 * - Error handling for stack operations
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class StackImplementation<T> {
    
    private T[] stackArray;
    private int top;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;
    
    /**
     * Default constructor with default capacity
     */
    @SuppressWarnings("unchecked")
    public StackImplementation() {
        this.capacity = DEFAULT_CAPACITY;
        this.stackArray = (T[]) new Object[capacity];
        this.top = -1;
    }
    
    /**
     * Constructor with specified capacity
     * @param capacity initial capacity of the stack
     */
    @SuppressWarnings("unchecked")
    public StackImplementation(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.stackArray = (T[]) new Object[capacity];
        this.top = -1;
    }
    
    /**
     * Pushes an element onto the stack
     * @param element the element to push
     */
    public void push(T element) {
        if (isFull()) {
            resize();
        }
        stackArray[++top] = element;
    }
    
    /**
     * Removes and returns the top element from the stack
     * @return the top element
     * @throws RuntimeException if stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty - cannot pop");
        }
        T element = stackArray[top];
        stackArray[top--] = null; // Help GC
        return element;
    }
    
    /**
     * Returns the top element without removing it
     * @return the top element
     * @throws RuntimeException if stack is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty - cannot peek");
        }
        return stackArray[top];
    }
    
    /**
     * Checks if the stack is empty
     * @return true if stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return top == -1;
    }
    
    /**
     * Checks if the stack is full
     * @return true if stack is full, false otherwise
     */
    public boolean isFull() {
        return top == capacity - 1;
    }
    
    /**
     * Returns the number of elements in the stack
     * @return size of the stack
     */
    public int size() {
        return top + 1;
    }
    
    /**
     * Returns the capacity of the stack
     * @return capacity of the stack
     */
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * Resizes the stack array when full
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        T[] newArray = (T[]) new Object[newCapacity];
        
        // Copy existing elements
        for (int i = 0; i <= top; i++) {
            newArray[i] = stackArray[i];
        }
        
        stackArray = newArray;
        capacity = newCapacity;
        System.out.println("Stack resized to capacity: " + capacity);
    }
    
    /**
     * Clears all elements from the stack
     */
    public void clear() {
        for (int i = 0; i <= top; i++) {
            stackArray[i] = null;
        }
        top = -1;
    }
    
    /**
     * Returns string representation of the stack
     * @return string representation
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Stack: []";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Stack: [");
        for (int i = 0; i <= top; i++) {
            sb.append(stackArray[i]);
            if (i < top) {
                sb.append(", ");
            }
        }
        sb.append("] (top -> bottom)");
        return sb.toString();
    }
    
    /**
     * Demonstrates practical applications of stack
     */
    public static void demonstrateStackApplications() {
        System.out.println("\n=== STACK APPLICATIONS ===");
        
        // 1. Balanced Parentheses Checker
        System.out.println("1. Balanced Parentheses Checker:");
        String[] expressions = {"()", "((()))", "(()", "()())", "((())"};
        
        for (String expr : expressions) {
            boolean isBalanced = isBalancedParentheses(expr);
            System.out.println("\"" + expr + "\" is " + (isBalanced ? "balanced" : "not balanced"));
        }
        
        // 2. Reverse a string
        System.out.println("\n2. String Reversal:");
        String original = "Hello World";
        String reversed = reverseString(original);
        System.out.println("Original: \"" + original + "\"");
        System.out.println("Reversed: \"" + reversed + "\"");
        
        // 3. Evaluate postfix expression
        System.out.println("\n3. Postfix Expression Evaluation:");
        String postfix = "2 3 + 4 *";  // Equivalent to: (2 + 3) * 4 = 20
        int result = evaluatePostfix(postfix);
        System.out.println("Postfix: \"" + postfix + "\" = " + result);
    }
    
    /**
     * Checks if parentheses in a string are balanced
     * @param expression string to check
     * @return true if balanced, false otherwise
     */
    public static boolean isBalancedParentheses(String expression) {
        StackImplementation<Character> stack = new StackImplementation<>();
        
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        
        return stack.isEmpty();
    }
    
    /**
     * Reverses a string using stack
     * @param str string to reverse
     * @return reversed string
     */
    public static String reverseString(String str) {
        StackImplementation<Character> stack = new StackImplementation<>();
        
        // Push all characters onto stack
        for (char c : str.toCharArray()) {
            stack.push(c);
        }
        
        // Pop all characters to build reversed string
        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) {
            reversed.append(stack.pop());
        }
        
        return reversed.toString();
    }
    
    /**
     * Evaluates a postfix expression
     * @param expression postfix expression (space-separated)
     * @return result of evaluation
     */
    public static int evaluatePostfix(String expression) {
        StackImplementation<Integer> stack = new StackImplementation<>();
        String[] tokens = expression.split(" ");
        
        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                // It's an operator
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result;
                
                switch (token) {
                    case "+":
                        result = operand1 + operand2;
                        break;
                    case "-":
                        result = operand1 - operand2;
                        break;
                    case "*":
                        result = operand1 * operand2;
                        break;
                    case "/":
                        result = operand1 / operand2;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator: " + token);
                }
                
                stack.push(result);
            }
        }
        
        return stack.pop();
    }
    
    /**
     * Helper method to check if a string is numeric
     * @param str string to check
     * @return true if numeric, false otherwise
     */
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Main method demonstrating stack implementation and applications
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Stack Implementation Demonstration");
        System.out.println("=================================");
        
        // Basic stack operations
        System.out.println("=== BASIC STACK OPERATIONS ===");
        StackImplementation<Integer> intStack = new StackImplementation<>(5);
        
        System.out.println("Initial stack: " + intStack);
        System.out.println("Is empty: " + intStack.isEmpty());
        System.out.println("Size: " + intStack.size());
        
        // Push operations
        System.out.println("\nPushing elements: 10, 20, 30, 40, 50");
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);
        intStack.push(40);
        intStack.push(50);
        
        System.out.println("Stack after pushes: " + intStack);
        System.out.println("Size: " + intStack.size());
        System.out.println("Is full: " + intStack.isFull());
        
        // Peek operation
        System.out.println("\nPeek (top element): " + intStack.peek());
        System.out.println("Stack after peek: " + intStack);
        
        // Pop operations
        System.out.println("\nPopping elements:");
        while (!intStack.isEmpty()) {
            System.out.println("Popped: " + intStack.pop() + ", Remaining: " + intStack);
        }
        
        // Test dynamic resizing
        System.out.println("\n=== DYNAMIC RESIZING TEST ===");
        StackImplementation<String> stringStack = new StackImplementation<>(3);
        System.out.println("Initial capacity: " + stringStack.getCapacity());
        
        stringStack.push("A");
        stringStack.push("B");
        stringStack.push("C");
        System.out.println("After 3 pushes: " + stringStack);
        
        stringStack.push("D"); // This should trigger resizing
        System.out.println("After 4th push: " + stringStack);
        System.out.println("New capacity: " + stringStack.getCapacity());
        
        // Error handling demonstration
        System.out.println("\n=== ERROR HANDLING ===");
        StackImplementation<Integer> emptyStack = new StackImplementation<>();
        
        try {
            emptyStack.pop();
        } catch (RuntimeException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
        
        try {
            emptyStack.peek();
        } catch (RuntimeException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
        
        // Demonstrate stack applications
        demonstrateStackApplications();
        
        System.out.println("\n=== STACK CHARACTERISTICS ===");
        System.out.println("• LIFO (Last In, First Out) principle");
        System.out.println("• O(1) time complexity for push, pop, peek operations");
        System.out.println("• Used in: function calls, expression evaluation, undo operations");
        System.out.println("• Memory efficient for temporary storage");
        System.out.println("• Essential for recursion and parsing algorithms");
    }
}
