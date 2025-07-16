package patterns;

/**
 * SingletonPatternDemo - Demonstrates the Singleton Design Pattern
 * 
 * The Singleton pattern ensures that a class has only one instance
 * and provides a global point of access to that instance.
 * 
 * This demo covers:
 * - Basic Singleton implementation
 * - Thread-safe Singleton (synchronized)
 * - Double-checked locking
 * - Enum-based Singleton
 * - Bill Pugh Singleton (Initialization-on-demand holder)
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// 1. Basic Singleton (not thread-safe)
class BasicSingleton {
    private static BasicSingleton instance;
    private String data;
    
    // Private constructor prevents instantiation
    private BasicSingleton() {
        this.data = "Basic Singleton Instance";
    }
    
    // Global access point
    public static BasicSingleton getInstance() {
        if (instance == null) {
            instance = new BasicSingleton();
        }
        return instance;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
}

// 2. Thread-Safe Singleton (synchronized method)
class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;
    private String data;
    
    private ThreadSafeSingleton() {
        this.data = "Thread-Safe Singleton Instance";
    }
    
    // Synchronized method ensures thread safety
    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
}

// 3. Double-Checked Locking Singleton
class DoubleCheckedLockingSingleton {
    private static volatile DoubleCheckedLockingSingleton instance;
    private String data;
    
    private DoubleCheckedLockingSingleton() {
        this.data = "Double-Checked Locking Singleton Instance";
    }
    
    public static DoubleCheckedLockingSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
        }
        return instance;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
}

// 4. Bill Pugh Singleton (Initialization-on-demand holder)
class BillPughSingleton {
    private String data;
    
    private BillPughSingleton() {
        this.data = "Bill Pugh Singleton Instance";
    }
    
    // Inner static class - loaded only when getInstance() is called
    private static class SingletonHolder {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }
    
    public static BillPughSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
}

// 5. Enum Singleton (recommended approach)
enum EnumSingleton {
    INSTANCE;
    
    private String data;
    
    EnumSingleton() {
        this.data = "Enum Singleton Instance";
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public void doSomething() {
        System.out.println("Enum Singleton performing action with data: " + data);
    }
}

// Real-world example: Database Connection Manager
class DatabaseConnectionManager {
    private static volatile DatabaseConnectionManager instance;
    private String connectionString;
    private boolean isConnected;
    
    private DatabaseConnectionManager() {
        // Simulate database connection setup
        this.connectionString = "jdbc:mysql://localhost:3306/mydb";
        this.isConnected = false;
        System.out.println("Database Connection Manager initialized");
    }
    
    public static DatabaseConnectionManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnectionManager.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionManager();
                }
            }
        }
        return instance;
    }
    
    public void connect() {
        if (!isConnected) {
            System.out.println("Connecting to database: " + connectionString);
            isConnected = true;
        } else {
            System.out.println("Already connected to database");
        }
    }
    
    public void disconnect() {
        if (isConnected) {
            System.out.println("Disconnecting from database");
            isConnected = false;
        } else {
            System.out.println("Not connected to database");
        }
    }
    
    public boolean isConnected() {
        return isConnected;
    }
    
    public String getConnectionString() {
        return connectionString;
    }
}

// Application Configuration Manager
class ConfigurationManager {
    private static volatile ConfigurationManager instance;
    private java.util.Map<String, String> properties;
    
    private ConfigurationManager() {
        properties = new java.util.HashMap<>();
        loadDefaultConfiguration();
    }
    
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }
    
    private void loadDefaultConfiguration() {
        properties.put("app.name", "Java Examples");
        properties.put("app.version", "1.0");
        properties.put("debug.enabled", "true");
        properties.put("max.connections", "100");
        System.out.println("Default configuration loaded");
    }
    
    public String getProperty(String key) {
        return properties.get(key);
    }
    
    public void setProperty(String key, String value) {
        properties.put(key, value);
    }
    
    public void displayConfiguration() {
        System.out.println("Application Configuration:");
        properties.forEach((key, value) -> 
            System.out.println("  " + key + " = " + value));
    }
}

public class SingletonPatternDemo {
    
    /**
     * Demonstrates thread safety issues with basic singleton
     */
    public static void demonstrateThreadSafetyIssues() {
        System.out.println("=== THREAD SAFETY DEMONSTRATION ===");
        
        // Create multiple threads trying to get singleton instance
        Runnable task = () -> {
            BasicSingleton singleton = BasicSingleton.getInstance();
            System.out.println("Thread " + Thread.currentThread().getName() + 
                             " got instance: " + singleton.hashCode());
        };
        
        // Start multiple threads
        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        Thread thread3 = new Thread(task, "Thread-3");
        
        thread1.start();
        thread2.start();
        thread3.start();
        
        // Wait for threads to complete
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Demonstrates different singleton implementations
     */
    public static void demonstrateSingletonImplementations() {
        System.out.println("\n=== SINGLETON IMPLEMENTATIONS ===");
        
        // 1. Basic Singleton
        System.out.println("1. Basic Singleton:");
        BasicSingleton basic1 = BasicSingleton.getInstance();
        BasicSingleton basic2 = BasicSingleton.getInstance();
        System.out.println("Same instance? " + (basic1 == basic2));
        System.out.println("Data: " + basic1.getData());
        
        // 2. Thread-Safe Singleton
        System.out.println("\n2. Thread-Safe Singleton:");
        ThreadSafeSingleton threadSafe1 = ThreadSafeSingleton.getInstance();
        ThreadSafeSingleton threadSafe2 = ThreadSafeSingleton.getInstance();
        System.out.println("Same instance? " + (threadSafe1 == threadSafe2));
        System.out.println("Data: " + threadSafe1.getData());
        
        // 3. Double-Checked Locking
        System.out.println("\n3. Double-Checked Locking Singleton:");
        DoubleCheckedLockingSingleton dcl1 = DoubleCheckedLockingSingleton.getInstance();
        DoubleCheckedLockingSingleton dcl2 = DoubleCheckedLockingSingleton.getInstance();
        System.out.println("Same instance? " + (dcl1 == dcl2));
        System.out.println("Data: " + dcl1.getData());
        
        // 4. Bill Pugh Singleton
        System.out.println("\n4. Bill Pugh Singleton:");
        BillPughSingleton billPugh1 = BillPughSingleton.getInstance();
        BillPughSingleton billPugh2 = BillPughSingleton.getInstance();
        System.out.println("Same instance? " + (billPugh1 == billPugh2));
        System.out.println("Data: " + billPugh1.getData());
        
        // 5. Enum Singleton
        System.out.println("\n5. Enum Singleton:");
        EnumSingleton enum1 = EnumSingleton.INSTANCE;
        EnumSingleton enum2 = EnumSingleton.INSTANCE;
        System.out.println("Same instance? " + (enum1 == enum2));
        System.out.println("Data: " + enum1.getData());
        enum1.doSomething();
    }
    
    /**
     * Demonstrates real-world singleton usage
     */
    public static void demonstrateRealWorldUsage() {
        System.out.println("\n=== REAL-WORLD SINGLETON USAGE ===");
        
        // Database Connection Manager
        System.out.println("1. Database Connection Manager:");
        DatabaseConnectionManager dbManager1 = DatabaseConnectionManager.getInstance();
        DatabaseConnectionManager dbManager2 = DatabaseConnectionManager.getInstance();
        
        System.out.println("Same manager instance? " + (dbManager1 == dbManager2));
        System.out.println("Connection string: " + dbManager1.getConnectionString());
        
        dbManager1.connect();
        dbManager2.connect(); // Should show already connected
        System.out.println("Is connected: " + dbManager1.isConnected());
        
        dbManager1.disconnect();
        System.out.println("Is connected after disconnect: " + dbManager1.isConnected());
        
        // Configuration Manager
        System.out.println("\n2. Configuration Manager:");
        ConfigurationManager config1 = ConfigurationManager.getInstance();
        ConfigurationManager config2 = ConfigurationManager.getInstance();
        
        System.out.println("Same config instance? " + (config1 == config2));
        config1.displayConfiguration();
        
        config1.setProperty("new.feature.enabled", "true");
        System.out.println("\nAfter adding new property:");
        config2.displayConfiguration(); // Should show the new property
    }
    
    /**
     * Analyzes singleton pattern characteristics
     */
    public static void analyzeSingletonPattern() {
        System.out.println("\n=== SINGLETON PATTERN ANALYSIS ===");
        
        System.out.println("Advantages:");
        System.out.println("• Ensures only one instance exists");
        System.out.println("• Global access point");
        System.out.println("• Lazy initialization possible");
        System.out.println("• Memory efficient");
        System.out.println("• Thread-safe implementations available");
        
        System.out.println("\nDisadvantages:");
        System.out.println("• Violates Single Responsibility Principle");
        System.out.println("• Difficult to unit test");
        System.out.println("• Hidden dependencies");
        System.out.println("• Global state can cause issues");
        System.out.println("• Difficult to extend");
        
        System.out.println("\nImplementation Comparison:");
        System.out.println("Basic Singleton:           Simple but not thread-safe");
        System.out.println("Synchronized Method:       Thread-safe but performance overhead");
        System.out.println("Double-Checked Locking:    Good performance and thread-safe");
        System.out.println("Bill Pugh (Inner Class):   Lazy loading without synchronization");
        System.out.println("Enum Singleton:            Simplest, handles serialization automatically");
        
        System.out.println("\nWhen to Use:");
        System.out.println("• Database connection pools");
        System.out.println("• Configuration managers");
        System.out.println("• Logging services");
        System.out.println("• Cache managers");
        System.out.println("• Thread pools");
        
        System.out.println("\nBest Practices:");
        System.out.println("• Use Enum for simple singletons");
        System.out.println("• Use Double-Checked Locking for complex singletons");
        System.out.println("• Consider using Dependency Injection instead");
        System.out.println("• Make fields volatile when using DCL");
        System.out.println("• Handle serialization properly if needed");
    }
    
    /**
     * Main method demonstrating the Singleton pattern
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Singleton Design Pattern Demonstration");
        System.out.println("=====================================");
        
        demonstrateSingletonImplementations();
        demonstrateThreadSafetyIssues();
        demonstrateRealWorldUsage();
        analyzeSingletonPattern();
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("The Singleton pattern ensures a class has only one instance");
        System.out.println("while providing global access to that instance.");
        System.out.println("Choose the implementation based on your specific requirements:");
        System.out.println("- Enum for simplicity and automatic serialization handling");
        System.out.println("- Double-Checked Locking for performance in multithreaded environments");
        System.out.println("- Consider alternatives like Dependency Injection for better testability");
    }
}
