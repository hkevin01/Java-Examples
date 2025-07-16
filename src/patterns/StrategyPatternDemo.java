package patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StrategyPatternDemo - Demonstrates the Strategy Design Pattern
 * 
 * The Strategy pattern defines a family of algorithms, encapsulates each one,
 * and makes them interchangeable. Strategy lets the algorithm vary independently
 * from clients that use it.
 * 
 * This demo covers:
 * - Payment processing strategies
 * - Sorting algorithms strategies
 * - Compression strategies
 * - Navigation strategies
 * - Discount calculation strategies
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// Strategy interface
interface Strategy {
    void execute();
}

// Payment Strategy Example

interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentType();
    boolean validatePayment();
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String holderName;
    private String cvv;
    private String expiryDate;
    
    public CreditCardPayment(String cardNumber, String holderName, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }
    
    @Override
    public boolean validatePayment() {
        // Simplified validation
        return cardNumber.length() == 16 && cvv.length() == 3 && 
               expiryDate.matches("\\d{2}/\\d{2}");
    }
    
    @Override
    public boolean pay(double amount) {
        if (validatePayment()) {
            System.out.println("💳 Processing credit card payment of $" + amount);
            System.out.println("   Card: ****" + cardNumber.substring(12));
            System.out.println("   Holder: " + holderName);
            System.out.println("   ✅ Payment successful via Credit Card");
            return true;
        } else {
            System.out.println("   ❌ Credit card validation failed");
            return false;
        }
    }
    
    @Override
    public String getPaymentType() {
        return "Credit Card";
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;
    
    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    @Override
    public boolean validatePayment() {
        return email.contains("@") && password.length() >= 6;
    }
    
    @Override
    public boolean pay(double amount) {
        if (validatePayment()) {
            System.out.println("🅿️ Processing PayPal payment of $" + amount);
            System.out.println("   Account: " + email);
            System.out.println("   ✅ Payment successful via PayPal");
            return true;
        } else {
            System.out.println("   ❌ PayPal validation failed");
            return false;
        }
    }
    
    @Override
    public String getPaymentType() {
        return "PayPal";
    }
}

class CryptoPayment implements PaymentStrategy {
    private String walletAddress;
    private String privateKey;
    private String cryptoType;
    
    public CryptoPayment(String walletAddress, String privateKey, String cryptoType) {
        this.walletAddress = walletAddress;
        this.privateKey = privateKey;
        this.cryptoType = cryptoType;
    }
    
    @Override
    public boolean validatePayment() {
        return walletAddress.length() >= 26 && privateKey.length() >= 51;
    }
    
    @Override
    public boolean pay(double amount) {
        if (validatePayment()) {
            System.out.println("₿ Processing " + cryptoType + " payment of $" + amount);
            System.out.println("   Wallet: " + walletAddress.substring(0, 6) + "..." + 
                              walletAddress.substring(walletAddress.length() - 6));
            System.out.println("   ✅ Payment successful via " + cryptoType);
            return true;
        } else {
            System.out.println("   ❌ Crypto wallet validation failed");
            return false;
        }
    }
    
    @Override
    public String getPaymentType() {
        return cryptoType + " Cryptocurrency";
    }
}

class BankTransferPayment implements PaymentStrategy {
    private String accountNumber;
    private String routingNumber;
    private String bankName;
    
    public BankTransferPayment(String accountNumber, String routingNumber, String bankName) {
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.bankName = bankName;
    }
    
    @Override
    public boolean validatePayment() {
        return accountNumber.length() >= 10 && routingNumber.length() == 9;
    }
    
    @Override
    public boolean pay(double amount) {
        if (validatePayment()) {
            System.out.println("🏦 Processing bank transfer of $" + amount);
            System.out.println("   Bank: " + bankName);
            System.out.println("   Account: ****" + accountNumber.substring(accountNumber.length() - 4));
            System.out.println("   ✅ Payment successful via Bank Transfer");
            return true;
        } else {
            System.out.println("   ❌ Bank account validation failed");
            return false;
        }
    }
    
    @Override
    public String getPaymentType() {
        return "Bank Transfer";
    }
}

// Payment Processor (Context)
class PaymentProcessor {
    private PaymentStrategy paymentStrategy;
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
        System.out.println("📋 Payment method set to: " + strategy.getPaymentType());
    }
    
    public boolean processPayment(double amount) {
        if (paymentStrategy == null) {
            System.out.println("❌ No payment method selected");
            return false;
        }
        
        System.out.println("\n💰 Processing payment of $" + amount);
        return paymentStrategy.pay(amount);
    }
}

// Sorting Strategy Example

interface SortingStrategy<T extends Comparable<T>> {
    void sort(List<T> data);
    String getAlgorithmName();
    String getTimeComplexity();
}

class BubbleSortStrategy<T extends Comparable<T>> implements SortingStrategy<T> {
    @Override
    public void sort(List<T> data) {
        int n = data.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (data.get(j).compareTo(data.get(j + 1)) > 0) {
                    Collections.swap(data, j, j + 1);
                }
            }
        }
    }
    
    @Override
    public String getAlgorithmName() {
        return "Bubble Sort";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n²)";
    }
}

class QuickSortStrategy<T extends Comparable<T>> implements SortingStrategy<T> {
    @Override
    public void sort(List<T> data) {
        quickSort(data, 0, data.size() - 1);
    }
    
    private void quickSort(List<T> data, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(data, low, high);
            quickSort(data, low, pivotIndex - 1);
            quickSort(data, pivotIndex + 1, high);
        }
    }
    
    private int partition(List<T> data, int low, int high) {
        T pivot = data.get(high);
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (data.get(j).compareTo(pivot) <= 0) {
                i++;
                Collections.swap(data, i, j);
            }
        }
        Collections.swap(data, i + 1, high);
        return i + 1;
    }
    
    @Override
    public String getAlgorithmName() {
        return "Quick Sort";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n log n) average, O(n²) worst";
    }
}

class MergeSortStrategy<T extends Comparable<T>> implements SortingStrategy<T> {
    @Override
    public void sort(List<T> data) {
        if (data.size() <= 1) return;
        mergeSort(data, 0, data.size() - 1);
    }
    
    private void mergeSort(List<T> data, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(data, left, mid);
            mergeSort(data, mid + 1, right);
            merge(data, left, mid, right);
        }
    }
    
    private void merge(List<T> data, int left, int mid, int right) {
        List<T> temp = new ArrayList<>(data.subList(left, right + 1));
        int i = 0, j = mid - left + 1, k = left;
        
        while (i <= mid - left && j < temp.size()) {
            if (temp.get(i).compareTo(temp.get(j)) <= 0) {
                data.set(k++, temp.get(i++));
            } else {
                data.set(k++, temp.get(j++));
            }
        }
        
        while (i <= mid - left) {
            data.set(k++, temp.get(i++));
        }
        while (j < temp.size()) {
            data.set(k++, temp.get(j++));
        }
    }
    
    @Override
    public String getAlgorithmName() {
        return "Merge Sort";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n log n)";
    }
}

// Sorting Context
class DataSorter<T extends Comparable<T>> {
    private SortingStrategy<T> sortingStrategy;
    
    public void setSortingStrategy(SortingStrategy<T> strategy) {
        this.sortingStrategy = strategy;
        System.out.println("📊 Sorting algorithm set to: " + strategy.getAlgorithmName() + 
                          " [" + strategy.getTimeComplexity() + "]");
    }
    
    public void sortData(List<T> data) {
        if (sortingStrategy == null) {
            System.out.println("❌ No sorting algorithm selected");
            return;
        }
        
        System.out.println("🔄 Sorting data using " + sortingStrategy.getAlgorithmName());
        List<T> dataCopy = new ArrayList<>(data);
        long startTime = System.nanoTime();
        sortingStrategy.sort(dataCopy);
        long endTime = System.nanoTime();
        
        System.out.println("✅ Sorting completed in " + (endTime - startTime) / 1000000.0 + " ms");
        System.out.println("   Sorted data: " + dataCopy);
    }
}

// Discount Strategy Example

interface DiscountStrategy {
    double calculateDiscount(double originalPrice);
    String getDiscountDescription();
}

class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(double originalPrice) {
        return 0.0;
    }
    
    @Override
    public String getDiscountDescription() {
        return "No discount applied";
    }
}

class PercentageDiscountStrategy implements DiscountStrategy {
    private double percentage;
    
    public PercentageDiscountStrategy(double percentage) {
        this.percentage = percentage;
    }
    
    @Override
    public double calculateDiscount(double originalPrice) {
        return originalPrice * (percentage / 100.0);
    }
    
    @Override
    public String getDiscountDescription() {
        return percentage + "% discount";
    }
}

class FixedAmountDiscountStrategy implements DiscountStrategy {
    private double discountAmount;
    
    public FixedAmountDiscountStrategy(double discountAmount) {
        this.discountAmount = discountAmount;
    }
    
    @Override
    public double calculateDiscount(double originalPrice) {
        return Math.min(discountAmount, originalPrice);
    }
    
    @Override
    public String getDiscountDescription() {
        return "$" + discountAmount + " off";
    }
}

class BuyTwoGetOneDiscountStrategy implements DiscountStrategy {
    private double itemPrice;
    private int quantity;
    
    public BuyTwoGetOneDiscountStrategy(double itemPrice, int quantity) {
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }
    
    @Override
    public double calculateDiscount(double originalPrice) {
        int freeItems = quantity / 3;
        return freeItems * itemPrice;
    }
    
    @Override
    public String getDiscountDescription() {
        return "Buy 2 Get 1 Free";
    }
}

// Shopping Cart (Context)
class ShoppingCart {
    private List<String> items;
    private double totalPrice;
    private DiscountStrategy discountStrategy;
    
    public ShoppingCart() {
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        this.discountStrategy = new NoDiscountStrategy();
    }
    
    public void addItem(String item, double price) {
        items.add(item);
        totalPrice += price;
        System.out.println("🛒 Added: " + item + " ($" + price + ")");
    }
    
    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
        System.out.println("🎫 Discount applied: " + strategy.getDiscountDescription());
    }
    
    public void checkout() {
        System.out.println("\n🧾 CHECKOUT SUMMARY");
        System.out.println("Items: " + items);
        System.out.println("Subtotal: $" + totalPrice);
        
        double discount = discountStrategy.calculateDiscount(totalPrice);
        double finalPrice = totalPrice - discount;
        
        System.out.println("Discount: -$" + discount + " (" + discountStrategy.getDiscountDescription() + ")");
        System.out.println("Final Total: $" + finalPrice);
    }
}

public class StrategyPatternDemo {
    
    /**
     * Demonstrates payment processing strategies
     */
    public static void demonstratePaymentStrategies() {
        System.out.println("=== PAYMENT STRATEGY DEMO ===");
        
        PaymentProcessor processor = new PaymentProcessor();
        double orderAmount = 299.99;
        
        // Credit Card Payment
        PaymentStrategy creditCard = new CreditCardPayment(
            "1234567890123456", "John Doe", "123", "12/25"
        );
        processor.setPaymentStrategy(creditCard);
        processor.processPayment(orderAmount);
        
        // PayPal Payment
        PaymentStrategy paypal = new PayPalPayment("john.doe@email.com", "secure123");
        processor.setPaymentStrategy(paypal);
        processor.processPayment(orderAmount);
        
        // Cryptocurrency Payment
        PaymentStrategy crypto = new CryptoPayment(
            "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", 
            "5JeJzKveF3fhFvGRvE8xSfFJuGz7Yt6jKe2zfJ9J7Dj2ZfF8xJ3",
            "Bitcoin"
        );
        processor.setPaymentStrategy(crypto);
        processor.processPayment(orderAmount);
        
        // Bank Transfer Payment
        PaymentStrategy bankTransfer = new BankTransferPayment(
            "1234567890", "123456789", "Wells Fargo"
        );
        processor.setPaymentStrategy(bankTransfer);
        processor.processPayment(orderAmount);
        
        // Invalid payment attempt
        PaymentStrategy invalidCard = new CreditCardPayment("123", "Invalid", "1", "");
        processor.setPaymentStrategy(invalidCard);
        processor.processPayment(orderAmount);
    }
    
    /**
     * Demonstrates sorting strategies
     */
    public static void demonstrateSortingStrategies() {
        System.out.println("\n=== SORTING STRATEGY DEMO ===");
        
        DataSorter<Integer> sorter = new DataSorter<>();
        List<Integer> numbers = Arrays.asList(64, 34, 25, 12, 22, 11, 90, 5, 77, 30);
        
        System.out.println("Original data: " + numbers);
        
        // Bubble Sort
        sorter.setSortingStrategy(new BubbleSortStrategy<>());
        sorter.sortData(numbers);
        
        // Quick Sort
        sorter.setSortingStrategy(new QuickSortStrategy<>());
        sorter.sortData(numbers);
        
        // Merge Sort
        sorter.setSortingStrategy(new MergeSortStrategy<>());
        sorter.sortData(numbers);
        
        // Sorting strings
        System.out.println("\nSorting string data:");
        DataSorter<String> stringSorter = new DataSorter<>();
        List<String> words = Arrays.asList("banana", "apple", "cherry", "date", "elderberry");
        System.out.println("Original words: " + words);
        
        stringSorter.setSortingStrategy(new QuickSortStrategy<>());
        stringSorter.sortData(words);
    }
    
    /**
     * Demonstrates discount strategies in shopping cart
     */
    public static void demonstrateDiscountStrategies() {
        System.out.println("\n=== DISCOUNT STRATEGY DEMO ===");
        
        // Scenario 1: No discount
        ShoppingCart cart1 = new ShoppingCart();
        cart1.addItem("Laptop", 999.99);
        cart1.addItem("Mouse", 29.99);
        cart1.addItem("Keyboard", 79.99);
        cart1.checkout();
        
        // Scenario 2: Percentage discount
        System.out.println("\n--- Scenario 2: Percentage Discount ---");
        ShoppingCart cart2 = new ShoppingCart();
        cart2.addItem("Smartphone", 699.99);
        cart2.addItem("Case", 24.99);
        cart2.addItem("Charger", 49.99);
        cart2.setDiscountStrategy(new PercentageDiscountStrategy(15.0));
        cart2.checkout();
        
        // Scenario 3: Fixed amount discount
        System.out.println("\n--- Scenario 3: Fixed Amount Discount ---");
        ShoppingCart cart3 = new ShoppingCart();
        cart3.addItem("Tablet", 399.99);
        cart3.addItem("Stylus", 99.99);
        cart3.setDiscountStrategy(new FixedAmountDiscountStrategy(50.0));
        cart3.checkout();
        
        // Scenario 4: Buy 2 Get 1 Free
        System.out.println("\n--- Scenario 4: Buy 2 Get 1 Free ---");
        ShoppingCart cart4 = new ShoppingCart();
        double bookPrice = 19.99;
        cart4.addItem("Book 1", bookPrice);
        cart4.addItem("Book 2", bookPrice);
        cart4.addItem("Book 3", bookPrice);
        cart4.addItem("Book 4", bookPrice);
        cart4.addItem("Book 5", bookPrice);
        cart4.setDiscountStrategy(new BuyTwoGetOneDiscountStrategy(bookPrice, 5));
        cart4.checkout();
    }
    
    /**
     * Demonstrates strategy pattern in navigation systems
     */
    public static void demonstrateNavigationStrategies() {
        System.out.println("\n=== NAVIGATION STRATEGY DEMO ===");
        
        // Define navigation strategies as lambda expressions (functional programming)
        Map<String, Runnable> navigationStrategies = new HashMap<>();
        
        navigationStrategies.put("car", () -> {
            System.out.println("🚗 Car Navigation:");
            System.out.println("   - Taking highways for fastest route");
            System.out.println("   - Avoiding toll roads: OFF");
            System.out.println("   - Estimated time: 45 minutes");
            System.out.println("   - Distance: 35 miles");
        });
        
        navigationStrategies.put("walking", () -> {
            System.out.println("🚶 Walking Navigation:");
            System.out.println("   - Using pedestrian paths and sidewalks");
            System.out.println("   - Avoiding busy roads");
            System.out.println("   - Estimated time: 2 hours 30 minutes");
            System.out.println("   - Distance: 8.5 miles");
        });
        
        navigationStrategies.put("bike", () -> {
            System.out.println("🚴 Bicycle Navigation:");
            System.out.println("   - Using bike lanes and paths");
            System.out.println("   - Avoiding steep hills");
            System.out.println("   - Estimated time: 1 hour 15 minutes");
            System.out.println("   - Distance: 12 miles");
        });
        
        navigationStrategies.put("public_transport", () -> {
            System.out.println("🚌 Public Transport Navigation:");
            System.out.println("   - Using bus and train connections");
            System.out.println("   - Next departure: 15 minutes");
            System.out.println("   - Estimated time: 1 hour 5 minutes");
            System.out.println("   - Cost: $3.50");
        });
        
        System.out.println("Navigation from Downtown to Airport:");
        
        // Execute different navigation strategies
        String[] transportModes = {"car", "walking", "bike", "public_transport"};
        for (String mode : transportModes) {
            System.out.println();
            navigationStrategies.get(mode).run();
        }
    }
    
    /**
     * Analyzes Strategy pattern characteristics
     */
    public static void analyzeStrategyPattern() {
        System.out.println("\n=== STRATEGY PATTERN ANALYSIS ===");
        
        System.out.println("Pattern Structure:");
        System.out.println("• Strategy: Defines interface for all algorithms");
        System.out.println("• ConcreteStrategy: Implements specific algorithm");
        System.out.println("• Context: Uses Strategy interface to call algorithm");
        
        System.out.println("\nKey Benefits:");
        System.out.println("• Algorithms can vary independently from clients");
        System.out.println("• Easy to add new algorithms without changing existing code");
        System.out.println("• Eliminates conditional statements for algorithm selection");
        System.out.println("• Promotes composition over inheritance");
        System.out.println("• Runtime algorithm switching capability");
        
        System.out.println("\nPotential Drawbacks:");
        System.out.println("• Clients must be aware of different strategies");
        System.out.println("• Increased number of objects and classes");
        System.out.println("• Communication overhead between Strategy and Context");
        
        System.out.println("\nWhen to Use:");
        System.out.println("• Multiple ways to perform a task");
        System.out.println("• Need to switch algorithms at runtime");
        System.out.println("• Want to isolate algorithm implementation details");
        System.out.println("• Have complex conditional statements for algorithm selection");
        
        System.out.println("\nReal-world Applications:");
        System.out.println("• Payment processing systems");
        System.out.println("• Sorting and searching algorithms");
        System.out.println("• Compression algorithms");
        System.out.println("• Authentication mechanisms");
        System.out.println("• Game AI behavior selection");
        System.out.println("• Route calculation in navigation apps");
    }
    
    /**
     * Main method demonstrating the Strategy pattern
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Strategy Design Pattern Demonstration");
        System.out.println("====================================");
        
        demonstratePaymentStrategies();
        demonstrateSortingStrategies();
        demonstrateDiscountStrategies();
        demonstrateNavigationStrategies();
        analyzeStrategyPattern();
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("The Strategy pattern enables selecting algorithms at runtime,");
        System.out.println("promoting flexibility and maintainability by encapsulating");
        System.out.println("different behaviors and making them interchangeable.");
    }
}
