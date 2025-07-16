package basics;

/**
 * ControlStructuresDemo - Demonstrates Java control flow statements
 * 
 * This class covers:
 * - Conditional statements (if, if-else, switch)
 * - Loops (for, while, do-while, enhanced for)
 * - Jump statements (break, continue, return)
 * - Nested structures and best practices
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class ControlStructuresDemo {
    
    /**
     * Demonstrates if-else conditional statements
     */
    public static void demonstrateIfElse() {
        System.out.println("=== IF-ELSE STATEMENTS ===");
        
        int score = 85;
        
        // Simple if statement
        if (score >= 90) {
            System.out.println("Excellent! Grade: A");
        } else if (score >= 80) {
            System.out.println("Good! Grade: B");
        } else if (score >= 70) {
            System.out.println("Average. Grade: C");
        } else if (score >= 60) {
            System.out.println("Below Average. Grade: D");
        } else {
            System.out.println("Failed. Grade: F");
        }
        
        // Ternary operator (conditional operator)
        String result = (score >= 60) ? "Pass" : "Fail";
        System.out.println("Result using ternary operator: " + result);
        
        // Logical operators in conditions
        int age = 25;
        boolean hasLicense = true;
        boolean hasInsurance = true;
        
        if (age >= 18 && hasLicense && hasInsurance) {
            System.out.println("Eligible to drive");
        } else {
            System.out.println("Not eligible to drive");
        }
        
        // Complex conditions
        boolean isWeekend = false;
        boolean isHoliday = true;
        
        if ((isWeekend || isHoliday) && age >= 18) {
            System.out.println("Can sleep in today!");
        }
    }
    
    /**
     * Demonstrates switch statements
     */
    public static void demonstrateSwitch() {
        System.out.println("\n=== SWITCH STATEMENTS ===");
        
        // Traditional switch with break statements
        int dayOfWeek = 3;
        String dayName;
        
        switch (dayOfWeek) {
            case 1:
                dayName = "Monday";
                break;
            case 2:
                dayName = "Tuesday";
                break;
            case 3:
                dayName = "Wednesday";
                break;
            case 4:
                dayName = "Thursday";
                break;
            case 5:
                dayName = "Friday";
                break;
            case 6:
                dayName = "Saturday";
                break;
            case 7:
                dayName = "Sunday";
                break;
            default:
                dayName = "Invalid day";
        }
        System.out.println("Day " + dayOfWeek + " is: " + dayName);
        
        // Switch with multiple cases
        char grade = 'B';
        switch (grade) {
            case 'A':
            case 'a':
                System.out.println("Excellent work!");
                break;
            case 'B':
            case 'b':
                System.out.println("Good job!");
                break;
            case 'C':
            case 'c':
                System.out.println("You can do better!");
                break;
            case 'D':
            case 'd':
                System.out.println("Need improvement!");
                break;
            case 'F':
            case 'f':
                System.out.println("Please see instructor!");
                break;
            default:
                System.out.println("Invalid grade!");
        }
        
        // Switch with strings (Java 7+)
        String season = "spring";
        switch (season.toLowerCase()) {
            case "spring":
                System.out.println("Time for planting!");
                break;
            case "summer":
                System.out.println("Time for vacation!");
                break;
            case "fall":
            case "autumn":
                System.out.println("Time for harvest!");
                break;
            case "winter":
                System.out.println("Time for hot cocoa!");
                break;
            default:
                System.out.println("Unknown season!");
        }
    }
    
    /**
     * Demonstrates different types of loops
     */
    public static void demonstrateLoops() {
        System.out.println("\n=== LOOP STATEMENTS ===");
        
        // For loop
        System.out.println("For loop - counting from 1 to 5:");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // While loop
        System.out.println("\nWhile loop - countdown from 5:");
        int count = 5;
        while (count > 0) {
            System.out.print(count + " ");
            count--;
        }
        System.out.println("Blast off!");
        
        // Do-while loop (executes at least once)
        System.out.println("\nDo-while loop - user input simulation:");
        int userInput = 0;
        int attempts = 0;
        do {
            attempts++;
            userInput = (int) (Math.random() * 10) + 1; // Simulate random input 1-10
            System.out.println("Attempt " + attempts + ": Generated number = " + userInput);
        } while (userInput != 7 && attempts < 5);
        
        if (userInput == 7) {
            System.out.println("Success! Found the lucky number 7!");
        } else {
            System.out.println("Max attempts reached.");
        }
        
        // Enhanced for loop (for-each)
        System.out.println("\nEnhanced for loop - array iteration:");
        int[] numbers = {10, 20, 30, 40, 50};
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println();
        
        // Nested loops
        System.out.println("\nNested loops - multiplication table:");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.printf("%d x %d = %d\t", i, j, i * j);
            }
            System.out.println();
        }
    }
    
    /**
     * Demonstrates jump statements (break, continue, return)
     */
    public static void demonstrateJumpStatements() {
        System.out.println("\n=== JUMP STATEMENTS ===");
        
        // Break statement
        System.out.println("Break statement - find first even number:");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                System.out.println("First even number found: " + i);
                break; // Exit the loop
            }
            System.out.println("Checking: " + i);
        }
        
        // Continue statement
        System.out.println("\nContinue statement - print only odd numbers:");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue; // Skip the rest of this iteration
            }
            System.out.print(i + " ");
        }
        System.out.println();
        
        // Labeled break and continue (for nested loops)
        System.out.println("\nLabeled break - exit nested loops:");
        outer: for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    System.out.println("Breaking out of both loops at i=" + i + ", j=" + j);
                    break outer; // Break out of the outer loop
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }
    }
    
    /**
     * Demonstrates practical examples combining control structures
     */
    public static void demonstratePracticalExamples() {
        System.out.println("\n=== PRACTICAL EXAMPLES ===");
        
        // Example 1: Number guessing game logic
        System.out.println("Number guessing game simulation:");
        int secretNumber = 7;
        int[] guesses = {3, 5, 7, 9};
        
        for (int i = 0; i < guesses.length; i++) {
            int guess = guesses[i];
            System.out.print("Guess " + (i + 1) + ": " + guess + " - ");
            
            if (guess == secretNumber) {
                System.out.println("Correct! You win!");
                break;
            } else if (guess < secretNumber) {
                System.out.println("Too low!");
            } else {
                System.out.println("Too high!");
            }
        }
        
        // Example 2: Grade calculator
        System.out.println("\nGrade calculator:");
        int[] scores = {95, 87, 76, 64, 58};
        int total = 0;
        int count = 0;
        
        for (int score : scores) {
            total += score;
            count++;
            
            // Determine letter grade
            char letterGrade;
            if (score >= 90) {
                letterGrade = 'A';
            } else if (score >= 80) {
                letterGrade = 'B';
            } else if (score >= 70) {
                letterGrade = 'C';
            } else if (score >= 60) {
                letterGrade = 'D';
            } else {
                letterGrade = 'F';
            }
            
            System.out.println("Score: " + score + " -> Grade: " + letterGrade);
        }
        
        double average = (double) total / count;
        System.out.printf("Average: %.2f\n", average);
    }
    
    /**
     * Main method demonstrating all control structure concepts
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Control Structures Demonstration");
        System.out.println("====================================");
        
        demonstrateIfElse();
        demonstrateSwitch();
        demonstrateLoops();
        demonstrateJumpStatements();
        demonstratePracticalExamples();
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("Control structures allow you to:");
        System.out.println("1. Make decisions (if-else, switch)");
        System.out.println("2. Repeat actions (for, while, do-while)");
        System.out.println("3. Control flow (break, continue, return)");
        System.out.println("4. Create complex logic by combining them");
    }
}
