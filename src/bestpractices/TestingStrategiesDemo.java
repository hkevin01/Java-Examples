package bestpractices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TestingStrategiesDemo - Demonstrates Java Testing Best Practices
 * 
 * This comprehensive demo covers:
 * - Unit Testing principles and patterns
 * - Test-Driven Development (TDD)
 * - Mocking and stubbing
 * - Integration testing concepts
 * - Test organization and naming
 * - Test data builders and fixtures
 * - Parameterized testing
 * - Performance testing basics
 * 
 * Note: This demo shows testing concepts without external frameworks
 * but demonstrates patterns commonly used with JUnit, Mockito, etc.
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// Simple Test Framework Implementation for Demonstration

/**
 * Simple assertion utility for demonstration purposes
 */
class Assertions {
    
    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }
    
    public static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }
    
    public static void assertEquals(Object expected, Object actual, String message) {
        assertTrue(Objects.equals(expected, actual), 
                  message + " - Expected: " + expected + ", Actual: " + actual);
    }
    
    public static void assertNotNull(Object object, String message) {
        assertTrue(object != null, message + " - Object should not be null");
    }
    
    public static void assertThrows(Class<? extends Exception> expectedType, 
                                   Runnable code, String message) {
        try {
            code.run();
            throw new AssertionError(message + " - Expected exception " + expectedType.getSimpleName());
        } catch (Exception e) {
            assertTrue(expectedType.isInstance(e), 
                      message + " - Expected " + expectedType.getSimpleName() + 
                      " but got " + e.getClass().getSimpleName());
        }
    }
}

/**
 * Test runner for demonstration
 */
class TestRunner {
    
    public static void runTest(String testName, Runnable test) {
        try {
            test.run();
            System.out.println("✅ " + testName + " - PASSED");
        } catch (AssertionError e) {
            System.out.println("❌ " + testName + " - FAILED: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("💥 " + testName + " - ERROR: " + e.getMessage());
        }
    }
}

// Classes Under Test

/**
 * Calculator class for testing demonstrations
 */
class Calculator {
    
    public int add(int a, int b) {
        return a + b;
    }
    
    public int subtract(int a, int b) {
        return a - b;
    }
    
    public int multiply(int a, int b) {
        return a * b;
    }
    
    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed");
        }
        return (double) a / b;
    }
    
    public double squareRoot(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        return Math.sqrt(value);
    }
    
    public boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}

/**
 * Bank account class for more complex testing scenarios
 */
class BankAccount {
    private String accountNumber;
    private double balance;
    private boolean isActive;
    private List<Transaction> transactionHistory;
    
    public BankAccount(String accountNumber, double initialBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.isActive = true;
        this.transactionHistory = new ArrayList<>();
    }
    
    public void deposit(double amount) {
        validateAccount();
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        balance += amount;
        transactionHistory.add(new Transaction("DEPOSIT", amount, balance));
    }
    
    public void withdraw(double amount) {
        validateAccount();
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds");
        }
        
        balance -= amount;
        transactionHistory.add(new Transaction("WITHDRAWAL", amount, balance));
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void closeAccount() {
        isActive = false;
    }
    
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
    
    private void validateAccount() {
        if (!isActive) {
            throw new IllegalStateException("Account is closed");
        }
    }
}

class Transaction {
    private final String type;
    private final double amount;
    private final double balanceAfter;
    private final long timestamp;
    
    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public long getTimestamp() { return timestamp; }
}

// Mock Implementation for Demonstration

/**
 * Simple mock for external service dependency
 */
interface ExternalService {
    String fetchData(String id);
    boolean isServiceAvailable();
}

class MockExternalService implements ExternalService {
    private final Map<String, String> mockData;
    private boolean serviceAvailable;
    
    public MockExternalService() {
        this.mockData = new HashMap<>();
        this.serviceAvailable = true;
        setupMockData();
    }
    
    private void setupMockData() {
        mockData.put("user1", "John Doe");
        mockData.put("user2", "Jane Smith");
        mockData.put("user3", "Bob Johnson");
    }
    
    @Override
    public String fetchData(String id) {
        if (!serviceAvailable) {
            throw new RuntimeException("Service unavailable");
        }
        return mockData.get(id);
    }
    
    @Override
    public boolean isServiceAvailable() {
        return serviceAvailable;
    }
    
    public void setServiceAvailable(boolean available) {
        this.serviceAvailable = available;
    }
    
    public void addMockData(String id, String data) {
        mockData.put(id, data);
    }
}

// Service class that depends on external service
class UserService {
    private final ExternalService externalService;
    
    public UserService(ExternalService externalService) {
        this.externalService = Objects.requireNonNull(externalService);
    }
    
    public String getUserDisplayName(String userId) {
        if (!externalService.isServiceAvailable()) {
            return "Service Unavailable";
        }
        
        String userData = externalService.fetchData(userId);
        return userData != null ? userData : "Unknown User";
    }
}

// Test Data Builders

/**
 * Builder pattern for creating test data
 */
class BankAccountBuilder {
    private String accountNumber = "DEFAULT-123";
    private double initialBalance = 0.0;
    
    public BankAccountBuilder withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }
    
    public BankAccountBuilder withInitialBalance(double balance) {
        this.initialBalance = balance;
        return this;
    }
    
    public BankAccount build() {
        return new BankAccount(accountNumber, initialBalance);
    }
}

// Test Classes

class CalculatorTest {
    
    public static void runAllTests() {
        System.out.println("=== CALCULATOR UNIT TESTS ===");
        
        testAddition();
        testSubtraction();
        testMultiplication();
        testDivision();
        testDivisionByZero();
        testSquareRoot();
        testSquareRootNegative();
        testIsPrime();
        testParameterizedPrimeNumbers();
    }
    
    public static void testAddition() {
        TestRunner.runTest("Calculator Addition", () -> {
            Calculator calc = new Calculator();
            
            // Test positive numbers
            Assertions.assertEquals(5, calc.add(2, 3), "2 + 3 should equal 5");
            
            // Test negative numbers
            Assertions.assertEquals(-1, calc.add(-3, 2), "-3 + 2 should equal -1");
            
            // Test zero
            Assertions.assertEquals(5, calc.add(5, 0), "5 + 0 should equal 5");
        });
    }
    
    public static void testSubtraction() {
        TestRunner.runTest("Calculator Subtraction", () -> {
            Calculator calc = new Calculator();
            
            Assertions.assertEquals(2, calc.subtract(5, 3), "5 - 3 should equal 2");
            Assertions.assertEquals(-5, calc.subtract(-2, 3), "-2 - 3 should equal -5");
        });
    }
    
    public static void testMultiplication() {
        TestRunner.runTest("Calculator Multiplication", () -> {
            Calculator calc = new Calculator();
            
            Assertions.assertEquals(15, calc.multiply(3, 5), "3 * 5 should equal 15");
            Assertions.assertEquals(0, calc.multiply(0, 5), "0 * 5 should equal 0");
            Assertions.assertEquals(-10, calc.multiply(-2, 5), "-2 * 5 should equal -10");
        });
    }
    
    public static void testDivision() {
        TestRunner.runTest("Calculator Division", () -> {
            Calculator calc = new Calculator();
            
            Assertions.assertEquals(2.5, calc.divide(5, 2), "5 / 2 should equal 2.5");
            Assertions.assertEquals(-2.0, calc.divide(-6, 3), "-6 / 3 should equal -2.0");
        });
    }
    
    public static void testDivisionByZero() {
        TestRunner.runTest("Calculator Division by Zero", () -> {
            Calculator calc = new Calculator();
            
            Assertions.assertThrows(IllegalArgumentException.class, 
                                   () -> calc.divide(5, 0), 
                                   "Division by zero should throw IllegalArgumentException");
        });
    }
    
    public static void testSquareRoot() {
        TestRunner.runTest("Calculator Square Root", () -> {
            Calculator calc = new Calculator();
            
            Assertions.assertEquals(3.0, calc.squareRoot(9), "Square root of 9 should be 3");
            Assertions.assertEquals(0.0, calc.squareRoot(0), "Square root of 0 should be 0");
        });
    }
    
    public static void testSquareRootNegative() {
        TestRunner.runTest("Calculator Square Root Negative", () -> {
            Calculator calc = new Calculator();
            
            Assertions.assertThrows(IllegalArgumentException.class,
                                   () -> calc.squareRoot(-1),
                                   "Square root of negative number should throw exception");
        });
    }
    
    public static void testIsPrime() {
        TestRunner.runTest("Calculator Is Prime", () -> {
            Calculator calc = new Calculator();
            
            Assertions.assertTrue(calc.isPrime(2), "2 should be prime");
            Assertions.assertTrue(calc.isPrime(17), "17 should be prime");
            Assertions.assertFalse(calc.isPrime(4), "4 should not be prime");
            Assertions.assertFalse(calc.isPrime(1), "1 should not be prime");
            Assertions.assertFalse(calc.isPrime(0), "0 should not be prime");
        });
    }
    
    public static void testParameterizedPrimeNumbers() {
        TestRunner.runTest("Parameterized Prime Number Tests", () -> {
            Calculator calc = new Calculator();
            
            // Test data: number, expected result
            int[][] testData = {
                {2, 1}, {3, 1}, {5, 1}, {7, 1}, {11, 1},  // primes
                {4, 0}, {6, 0}, {8, 0}, {9, 0}, {10, 0}   // non-primes
            };
            
            for (int[] data : testData) {
                int number = data[0];
                boolean expected = data[1] == 1;
                boolean actual = calc.isPrime(number);
                
                Assertions.assertEquals(expected, actual, 
                                       "isPrime(" + number + ") should return " + expected);
            }
        });
    }
}

class BankAccountTest {
    
    public static void runAllTests() {
        System.out.println("\n=== BANK ACCOUNT INTEGRATION TESTS ===");
        
        testAccountCreation();
        testDeposit();
        testWithdrawal();
        testInsufficientFunds();
        testClosedAccount();
        testTransactionHistory();
        testBuilderPattern();
    }
    
    public static void testAccountCreation() {
        TestRunner.runTest("Bank Account Creation", () -> {
            BankAccount account = new BankAccount("ACC-001", 100.0);
            
            Assertions.assertEquals("ACC-001", account.getAccountNumber(), 
                                   "Account number should match");
            Assertions.assertEquals(100.0, account.getBalance(), 
                                   "Initial balance should match");
            Assertions.assertTrue(account.isActive(), "Account should be active");
        });
    }
    
    public static void testDeposit() {
        TestRunner.runTest("Bank Account Deposit", () -> {
            BankAccount account = new BankAccount("ACC-002", 100.0);
            
            account.deposit(50.0);
            
            Assertions.assertEquals(150.0, account.getBalance(), 
                                   "Balance should be updated after deposit");
        });
    }
    
    public static void testWithdrawal() {
        TestRunner.runTest("Bank Account Withdrawal", () -> {
            BankAccount account = new BankAccount("ACC-003", 100.0);
            
            account.withdraw(30.0);
            
            Assertions.assertEquals(70.0, account.getBalance(), 
                                   "Balance should be updated after withdrawal");
        });
    }
    
    public static void testInsufficientFunds() {
        TestRunner.runTest("Bank Account Insufficient Funds", () -> {
            BankAccount account = new BankAccount("ACC-004", 50.0);
            
            Assertions.assertThrows(IllegalStateException.class,
                                   () -> account.withdraw(100.0),
                                   "Should throw exception for insufficient funds");
        });
    }
    
    public static void testClosedAccount() {
        TestRunner.runTest("Bank Account Closed Operations", () -> {
            BankAccount account = new BankAccount("ACC-005", 100.0);
            account.closeAccount();
            
            Assertions.assertFalse(account.isActive(), "Account should be inactive");
            
            Assertions.assertThrows(IllegalStateException.class,
                                   () -> account.deposit(50.0),
                                   "Should not allow deposit on closed account");
        });
    }
    
    public static void testTransactionHistory() {
        TestRunner.runTest("Bank Account Transaction History", () -> {
            BankAccount account = new BankAccount("ACC-006", 100.0);
            
            account.deposit(50.0);
            account.withdraw(25.0);
            
            List<Transaction> history = account.getTransactionHistory();
            
            Assertions.assertEquals(2, history.size(), "Should have 2 transactions");
            Assertions.assertEquals("DEPOSIT", history.get(0).getType(), 
                                   "First transaction should be deposit");
            Assertions.assertEquals("WITHDRAWAL", history.get(1).getType(), 
                                   "Second transaction should be withdrawal");
        });
    }
    
    public static void testBuilderPattern() {
        TestRunner.runTest("Bank Account Builder Pattern", () -> {
            BankAccount account = new BankAccountBuilder()
                .withAccountNumber("BUILDER-001")
                .withInitialBalance(250.0)
                .build();
            
            Assertions.assertEquals("BUILDER-001", account.getAccountNumber(),
                                   "Builder should set account number");
            Assertions.assertEquals(250.0, account.getBalance(),
                                   "Builder should set initial balance");
        });
    }
}

class UserServiceTest {
    
    public static void runAllTests() {
        System.out.println("\n=== USER SERVICE MOCK TESTS ===");
        
        testGetUserDisplayName();
        testServiceUnavailable();
        testUserNotFound();
    }
    
    public static void testGetUserDisplayName() {
        TestRunner.runTest("User Service Get Display Name", () -> {
            MockExternalService mockService = new MockExternalService();
            UserService userService = new UserService(mockService);
            
            String displayName = userService.getUserDisplayName("user1");
            
            Assertions.assertEquals("John Doe", displayName, 
                                   "Should return correct user name");
        });
    }
    
    public static void testServiceUnavailable() {
        TestRunner.runTest("User Service Unavailable", () -> {
            MockExternalService mockService = new MockExternalService();
            mockService.setServiceAvailable(false);
            UserService userService = new UserService(mockService);
            
            String result = userService.getUserDisplayName("user1");
            
            Assertions.assertEquals("Service Unavailable", result,
                                   "Should return unavailable message");
        });
    }
    
    public static void testUserNotFound() {
        TestRunner.runTest("User Service User Not Found", () -> {
            MockExternalService mockService = new MockExternalService();
            UserService userService = new UserService(mockService);
            
            String result = userService.getUserDisplayName("nonexistent");
            
            Assertions.assertEquals("Unknown User", result,
                                   "Should return unknown user message");
        });
    }
}

// Performance Testing Example

class PerformanceTest {
    
    public static void runPerformanceTests() {
        System.out.println("\n=== PERFORMANCE TESTS ===");
        
        testCalculatorPerformance();
        testConcurrentBankOperations();
    }
    
    public static void testCalculatorPerformance() {
        TestRunner.runTest("Calculator Performance Test", () -> {
            Calculator calc = new Calculator();
            int iterations = 1000000;
            
            long startTime = System.nanoTime();
            
            for (int i = 0; i < iterations; i++) {
                calc.add(i, i + 1);
                calc.multiply(i, 2);
                if (i % 100 == 0) {
                    calc.isPrime(i + 1);
                }
            }
            
            long endTime = System.nanoTime();
            double executionTimeMs = (endTime - startTime) / 1_000_000.0;
            
            System.out.println("    📊 Executed " + iterations + " operations in " + 
                             String.format("%.2f", executionTimeMs) + " ms");
            
            // Performance assertion - should complete within reasonable time
            Assertions.assertTrue(executionTimeMs < 1000, 
                                 "Performance test should complete within 1 second");
        });
    }
    
    public static void testConcurrentBankOperations() {
        TestRunner.runTest("Concurrent Bank Operations", () -> {
            BankAccount account = new BankAccount("PERF-001", 10000.0);
            int numThreads = 10;
            int operationsPerThread = 100;
            
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            CountDownLatch latch = new CountDownLatch(numThreads);
            
            long startTime = System.nanoTime();
            
            for (int i = 0; i < numThreads; i++) {
                executor.submit(() -> {
                    try {
                        for (int j = 0; j < operationsPerThread; j++) {
                            if (j % 2 == 0) {
                                account.deposit(1.0);
                            } else {
                                try {
                                    account.withdraw(1.0);
                                } catch (IllegalStateException e) {
                                    // Ignore insufficient funds for this test
                                }
                            }
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            }
            
            try {
                latch.await();
                long endTime = System.nanoTime();
                double executionTimeMs = (endTime - startTime) / 1_000_000.0;
                
                System.out.println("    📊 Concurrent operations completed in " + 
                                 String.format("%.2f", executionTimeMs) + " ms");
                System.out.println("    💰 Final balance: $" + account.getBalance());
                System.out.println("    📋 Transaction count: " + account.getTransactionHistory().size());
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Test interrupted", e);
            } finally {
                executor.shutdown();
            }
        });
    }
}

public class TestingStrategiesDemo {
    
    /**
     * Demonstrates Test-Driven Development approach
     */
    public static void demonstrateTDD() {
        System.out.println("=== TEST-DRIVEN DEVELOPMENT APPROACH ===");
        
        System.out.println("TDD follows the Red-Green-Refactor cycle:");
        System.out.println("1. 🔴 RED: Write a failing test");
        System.out.println("2. 🟢 GREEN: Write minimal code to make test pass");
        System.out.println("3. 🔄 REFACTOR: Improve code while keeping tests green");
        
        System.out.println("\nBenefits of TDD:");
        System.out.println("• Better design through testability focus");
        System.out.println("• Comprehensive test coverage");
        System.out.println("• Confidence in refactoring");
        System.out.println("• Documentation through tests");
        System.out.println("• Faster feedback loop");
    }
    
    /**
     * Demonstrates different testing levels
     */
    public static void demonstrateTestingLevels() {
        System.out.println("\n=== TESTING LEVELS ===");
        
        System.out.println("🔬 Unit Tests:");
        System.out.println("• Test individual methods/classes in isolation");
        System.out.println("• Fast execution, easy to debug");
        System.out.println("• Use mocks for external dependencies");
        System.out.println("• High code coverage target: 80-90%");
        
        System.out.println("\n🔗 Integration Tests:");
        System.out.println("• Test interaction between components");
        System.out.println("• Verify data flow and communication");
        System.out.println("• Test with real or test-specific dependencies");
        System.out.println("• Focus on interface contracts");
        
        System.out.println("\n🎭 End-to-End Tests:");
        System.out.println("• Test complete user scenarios");
        System.out.println("• Use real environment and data");
        System.out.println("• Slower but highest confidence");
        System.out.println("• Focus on critical business flows");
    }
    
    /**
     * Demonstrates test organization best practices
     */
    public static void demonstrateTestOrganization() {
        System.out.println("\n=== TEST ORGANIZATION BEST PRACTICES ===");
        
        System.out.println("📁 Test Structure:");
        System.out.println("• Mirror production package structure");
        System.out.println("• Use descriptive test class names (ClassNameTest)");
        System.out.println("• Group related tests in nested classes");
        System.out.println("• Separate unit, integration, and e2e tests");
        
        System.out.println("\n🏷️ Test Naming:");
        System.out.println("• Use descriptive method names");
        System.out.println("• Follow pattern: should_ExpectedBehavior_When_StateUnderTest");
        System.out.println("• Example: should_ThrowException_When_DividingByZero");
        
        System.out.println("\n📋 Test Documentation:");
        System.out.println("• Use Given-When-Then structure");
        System.out.println("• Document test purpose and expectations");
        System.out.println("• Include edge cases and boundary conditions");
        System.out.println("• Maintain test data fixtures");
    }
    
    /**
     * Analyzes testing best practices and patterns
     */
    public static void analyzeTestingStrategies() {
        System.out.println("\n=== TESTING STRATEGY ANALYSIS ===");
        
        System.out.println("Test Pyramid Levels:");
        System.out.println("• Unit Tests (70%): Fast, isolated, comprehensive");
        System.out.println("• Integration Tests (20%): Component interaction");
        System.out.println("• End-to-End Tests (10%): Full system validation");
        
        System.out.println("\nTest Characteristics (F.I.R.S.T.):");
        System.out.println("• Fast: Quick execution for rapid feedback");
        System.out.println("• Independent: Tests don't depend on each other");
        System.out.println("• Repeatable: Same results in any environment");
        System.out.println("• Self-Validating: Clear pass/fail without manual verification");
        System.out.println("• Timely: Written just before or with production code");
        
        System.out.println("\nMocking Best Practices:");
        System.out.println("• Mock external dependencies only");
        System.out.println("• Verify interactions when behavior matters");
        System.out.println("• Use stubs for simple return values");
        System.out.println("• Avoid mocking value objects");
        System.out.println("• Keep mocks simple and focused");
        
        System.out.println("\nTest Data Management:");
        System.out.println("• Use builders for complex test objects");
        System.out.println("• Create minimal test data sets");
        System.out.println("• Isolate test data between tests");
        System.out.println("• Use factories for common scenarios");
        System.out.println("• Parameterize tests for multiple inputs");
        
        System.out.println("\nCommon Testing Anti-Patterns:");
        System.out.println("• Testing implementation details instead of behavior");
        System.out.println("• Overly complex test setup");
        System.out.println("• Tests that are too broad or too narrow");
        System.out.println("• Flaky tests with non-deterministic behavior");
        System.out.println("• Tests that depend on external state");
    }
    
    /**
     * Main method demonstrating testing strategies
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Testing Strategies and Best Practices Demonstration");
        System.out.println("=======================================================");
        
        demonstrateTDD();
        demonstrateTestingLevels();
        
        // Run actual tests
        CalculatorTest.runAllTests();
        BankAccountTest.runAllTests();
        UserServiceTest.runAllTests();
        PerformanceTest.runPerformanceTests();
        
        demonstrateTestOrganization();
        analyzeTestingStrategies();
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("Effective testing strategies include:");
        System.out.println("• Following the test pyramid for balanced coverage");
        System.out.println("• Writing tests that are fast, independent, and reliable");
        System.out.println("• Using appropriate mocking and stubbing techniques");
        System.out.println("• Organizing tests clearly with descriptive names");
        System.out.println("• Focusing on behavior verification over implementation");
        System.out.println("• Continuously refactoring both production and test code");
        
        System.out.println("\nRecommended Tools:");
        System.out.println("• JUnit 5: Modern testing framework");
        System.out.println("• Mockito: Mocking framework");
        System.out.println("• AssertJ: Fluent assertions");
        System.out.println("• TestContainers: Integration testing with real dependencies");
        System.out.println("• JMH: Microbenchmarking for performance tests");
    }
}
