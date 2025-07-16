package advanced;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ThreadSyncDemo - Practical Thread Synchronization Console Application
 * 
 * REAL-WORLD SCENARIO:
 * This application simulates an image processing service that:
 * - Automatically processes images every minute
 * - Allows manual triggering of processing
 * - Provides real-time status updates
 * - Demonstrates proper thread coordination and synchronization
 * 
 * THREADING CONCEPTS DEMONSTRATED:
 * 1. SCHEDULED THREAD EXECUTION: Timer-based image processing
 * 2. THREAD SYNCHRONIZATION: Protecting shared resources with locks
 * 3. ATOMIC OPERATIONS: Thread-safe counters and flags
 * 4. EXECUTOR SERVICES: Managing background tasks efficiently
 * 5. REENTRANT LOCKS: Advanced synchronization with timeouts
 * 6. THREAD COORDINATION: Start/stop/pause operations
 * 
 * SYNCHRONIZATION MECHANISMS USED:
 * - ReentrantLock: For protecting image processing operations
 * - AtomicInteger: For thread-safe counters
 * - AtomicBoolean: For thread-safe status flags
 * - ScheduledExecutorService: For periodic task execution
 * - CountDownLatch: For coordinating system startup
 * 
 * WHY THIS EXAMPLE MATTERS:
 * - Shows real-world threading patterns used in production systems
 * - Demonstrates proper resource management and cleanup
 * - Illustrates how to coordinate multiple background tasks
 * - Provides interactive console interface for testing threading
 * - Shows proper exception handling in concurrent code
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class ThreadSyncDemo {
    
    // ============ THREAD-SAFE SHARED STATE ============
    
    /**
     * Thread-safe counter using AtomicInteger
     * WHY ATOMIC: Multiple threads will increment this counter
     * - No need for synchronized blocks
     * - Lock-free performance
     * - Guaranteed thread-safe operations
     */
    private final AtomicInteger imageProcessCount = new AtomicInteger(0);
    
    /**
     * Thread-safe boolean flag using AtomicBoolean
     * WHY ATOMIC: Prevents race conditions when checking/setting processing state
     */
    private final AtomicBoolean isProcessing = new AtomicBoolean(false);
    
    /**
     * Thread-safe boolean for pause state
     */
    private final AtomicBoolean isPaused = new AtomicBoolean(false);
    
    /**
     * ReentrantLock for protecting critical sections
     * WHY REENTRANT LOCK: More flexible than synchronized keyword
     * - Can attempt lock with timeout (tryLock with time)
     * - Can be interrupted (lockInterruptibly)
     * - Fair/unfair queueing options
     * - Better debugging and monitoring support
     * - Same thread can acquire lock multiple times
     */
    private final ReentrantLock processLock = new ReentrantLock(true); // Fair lock
    
    /**
     * ScheduledExecutorService for periodic tasks
     * WHY SCHEDULED EXECUTOR: Designed specifically for repeating tasks
     * - More precise timing than Timer class
     * - Better thread pool management
     * - Built-in exception handling
     * - Graceful shutdown capabilities
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    
    /**
     * ExecutorService for background tasks
     * WHY SEPARATE EXECUTOR: Isolate different types of work
     * - Scheduled tasks vs one-off tasks
     * - Different thread pool sizes for different workloads
     * - Independent shutdown and monitoring
     */
    private final ExecutorService backgroundExecutor = Executors.newFixedThreadPool(4);
    
    /**
     * CountDownLatch for coordinating startup
     * WHY COUNTDOWN LATCH: Ensures system is fully initialized before use
     * - Main thread waits for background services to start
     * - One-time coordination mechanism
     * - Thread-safe way to signal readiness
     */
    private final CountDownLatch startupLatch = new CountDownLatch(1);
    
    // ============ CONTROL STATE ============
    
    private ScheduledFuture<?> autoProcessTask;
    private ScheduledFuture<?> statusUpdateTask;
    private volatile boolean running = true; // volatile ensures visibility across threads
    
    // ============ SAMPLE DATA ============
    
    /**
     * Sample image/data identifiers for demonstration
     * In a real application, these might represent:
     * - File paths to process
     * - Database records to update
     * - API endpoints to call
     * - Tasks in a work queue
     */
    private final String[] SAMPLE_DATA = {
        "UserProfile_Image_001.jpg",
        "ProductPhoto_Item_425.png", 
        "ThumbnailGen_Video_882.mp4",
        "DocumentScan_Page_156.pdf",
        "AvatarResize_User_993.gif",
        "LogoOptimize_Brand_447.svg",
        "GalleryImage_Event_772.jpg",
        "IconGenerate_App_338.png"
    };
    
    public ThreadSyncDemo() {
        System.out.println("🚀 Thread Synchronization Demo - Automatic Image Processor");
        System.out.println("=" .repeat(65));
        initializeSystem();
        startBackgroundTasks();
        startUserInterface();
    }
    
    /**
     * Initialize the system components
     * 
     * INITIALIZATION PATTERN:
     * - Set up shared resources
     * - Initialize thread pools
     * - Prepare monitoring systems
     * - Signal readiness to other threads
     */
    private void initializeSystem() {
        logMessage("🔧 System initializing...");
        logMessage("📊 Thread pools created:");
        logMessage("   - Scheduler: 3 threads for periodic tasks");
        logMessage("   - Background: 4 threads for processing tasks");
        logMessage("✅ System ready for operation");
        startupLatch.countDown(); // Signal that system is ready
    }
    
    /**
     * Start all background tasks
     * 
     * TASK COORDINATION:
     * Multiple background tasks run independently:
     * 1. Auto-processing task (every 60 seconds)
     * 2. Status update task (every 5 seconds)
     * 3. System health monitor (every 30 seconds)
     */
    private void startBackgroundTasks() {
        startAutoProcessing();
        startStatusUpdater();
        startHealthMonitor();
        logMessage("🎯 All background tasks started");
    }
    
    /**
     * Start the periodic image processing task
     * 
     * SCHEDULED EXECUTION PATTERN:
     * - scheduleAtFixedRate(): Maintains consistent intervals
     * - Initial delay allows system to fully initialize
     * - Exception handling prevents task from stopping
     * - Task continues even if individual executions fail
     */
    private void startAutoProcessing() {
        autoProcessTask = scheduler.scheduleAtFixedRate(() -> {
            try {
                if (!isPaused.get() && running) {
                    processNextImage("Auto-Process");
                }
            } catch (Exception e) {
                logMessage("❌ Error in auto-process task: " + e.getMessage());
                // Task continues running despite exception
            }
        }, 3, 10, TimeUnit.SECONDS); // Process every 10 seconds for demo (normally 60)
        
        logMessage("⏰ Automatic processing started (every 10 seconds)");
    }
    
    /**
     * Start the status updater task
     * 
     * FREQUENT STATUS UPDATES:
     * - Updates status display every 5 seconds
     * - Shows current system state
     * - Demonstrates high-frequency background tasks
     */
    private void startStatusUpdater() {
        statusUpdateTask = scheduler.scheduleAtFixedRate(() -> {
            if (!isPaused.get() && running) {
                displayCurrentStatus();
            }
        }, 1, 8, TimeUnit.SECONDS);
        
        logMessage("📊 Status updater started (every 8 seconds)");
    }
    
    /**
     * Start system health monitor
     * 
     * MONITORING PATTERN:
     * - Checks thread pool health
     * - Reports system statistics
     * - Demonstrates system observability
     */
    private void startHealthMonitor() {
        scheduler.scheduleAtFixedRate(() -> {
            if (running) {
                displaySystemHealth();
            }
        }, 10, 30, TimeUnit.SECONDS);
        
        logMessage("🏥 Health monitor started (every 30 seconds)");
    }
    
    /**
     * Process the next image with full thread synchronization
     * 
     * CRITICAL SECTION PROTECTION:
     * This method demonstrates multiple synchronization techniques:
     * 1. AtomicBoolean prevents concurrent processing attempts
     * 2. ReentrantLock protects the entire processing operation
     * 3. AtomicInteger safely increments counter
     * 4. Proper exception handling ensures cleanup
     * 
     * LOCK TIMEOUT PATTERN:
     * - tryLock() with timeout prevents indefinite blocking
     * - Graceful degradation when lock cannot be acquired
     * - Better than blocking indefinitely
     * 
     * @param source String indicating what triggered this processing
     */
    private void processNextImage(String source) {
        String threadName = Thread.currentThread().getName();
        logMessage(String.format("🔄 [%s] Attempting to process image on thread: %s", source, threadName));
        
        // ============ ATOMIC CHECK-AND-SET ============
        // Prevent concurrent processing attempts using atomic operation
        if (!isProcessing.compareAndSet(false, true)) {
            logMessage(String.format("⚠️  [%s] Processing already in progress, skipping", source));
            return;
        }
        
        // ============ LOCK ACQUISITION WITH TIMEOUT ============
        boolean lockAcquired = false;
        try {
            // Try to acquire lock with 5-second timeout
            lockAcquired = processLock.tryLock(5, TimeUnit.SECONDS);
            
            if (!lockAcquired) {
                logMessage(String.format("🔒 [%s] Could not acquire lock within 5 seconds", source));
                return;
            }
            
            logMessage(String.format("🔓 [%s] Lock acquired, starting processing", source));
            
            // ============ CRITICAL SECTION BEGINS ============
            
            // Get next image to process (thread-safe counter increment)
            int currentCount = imageProcessCount.incrementAndGet();
            String imageFile = SAMPLE_DATA[(currentCount - 1) % SAMPLE_DATA.length];
            
            logMessage(String.format("📸 [%s] Processing image #%d: %s", source, currentCount, imageFile));
            
            // SIMULATE ACTUAL IMAGE PROCESSING:
            // In real applications, this might involve:
            // - Loading image from disk/network
            // - Applying filters or transformations
            // - Generating thumbnails
            // - Saving processed images
            // - Updating database records
            simulateImageProcessing(source, imageFile);
            
            logMessage(String.format("✅ [%s] Image #%d processed successfully: %s", 
                source, currentCount, imageFile));
            
            // ============ CRITICAL SECTION ENDS ============
            
        } catch (InterruptedException e) {
            // INTERRUPTION HANDLING: Proper cleanup when thread is interrupted
            Thread.currentThread().interrupt();
            logMessage(String.format("⚡ [%s] Thread interrupted during processing", source));
            
        } catch (Exception e) {
            // EXCEPTION HANDLING: Log errors without crashing the system
            logMessage(String.format("💥 [%s] Error processing image: %s", source, e.getMessage()));
            
        } finally {
            // CLEANUP: Always release resources in finally block
            if (lockAcquired) {
                processLock.unlock();
                logMessage(String.format("🔓 [%s] Lock released", source));
            }
            
            // ATOMIC RESET: Mark processing as complete
            isProcessing.set(false);
        }
    }
    
    /**
     * Simulate time-consuming image processing
     * 
     * SIMULATION PURPOSE:
     * Real image processing involves CPU-intensive operations:
     * - Image decoding/encoding
     * - Filter applications
     * - Color space conversions
     * - Resizing operations
     * - File I/O operations
     * 
     * This simulation helps demonstrate how long-running tasks
     * affect threading and synchronization
     */
    private void simulateImageProcessing(String source, String filename) throws InterruptedException {
        // Simulate different processing phases
        logMessage(String.format("🔍 [%s] Phase 1: Loading %s...", source, filename));
        Thread.sleep(800); // Simulate file loading
        
        logMessage(String.format("🎨 [%s] Phase 2: Applying filters to %s...", source, filename));
        Thread.sleep(1200); // Simulate image processing
        
        logMessage(String.format("💾 [%s] Phase 3: Saving processed %s...", source, filename));
        Thread.sleep(600); // Simulate file saving
    }
    
    /**
     * Display current system status
     * 
     * STATUS REPORTING PATTERN:
     * - Shows thread pool state
     * - Reports processing statistics
     * - Indicates system health
     * - Uses thread-safe operations only
     */
    private void displayCurrentStatus() {
        int processedCount = imageProcessCount.get();
        boolean processing = isProcessing.get();
        boolean paused = isPaused.get();
        
        System.out.println("\n" + "═".repeat(50));
        System.out.println("📊 SYSTEM STATUS REPORT");
        System.out.println("═".repeat(50));
        System.out.printf("⏰ Time: %s%n", 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.printf("📈 Images Processed: %d%n", processedCount);
        System.out.printf("🔄 Currently Processing: %s%n", processing ? "YES" : "NO");
        System.out.printf("⏸️  System Paused: %s%n", paused ? "YES" : "NO");
        System.out.printf("🎯 System Running: %s%n", running ? "YES" : "NO");
        
        if (processedCount > 0) {
            String lastProcessed = SAMPLE_DATA[(processedCount - 1) % SAMPLE_DATA.length];
            System.out.printf("📸 Last Processed: %s%n", lastProcessed);
        }
        System.out.println("═".repeat(50));
    }
    
    /**
     * Display system health information
     * 
     * HEALTH MONITORING:
     * - Thread pool statistics
     * - Resource utilization
     * - Performance metrics
     * - Early warning indicators
     */
    private void displaySystemHealth() {
        System.out.println("\n" + "💚 SYSTEM HEALTH CHECK");
        System.out.println("-".repeat(30));
        
        // Check thread pool health
        if (scheduler instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) scheduler;
            System.out.printf("🧵 Scheduler Pool - Active: %d, Completed: %d%n", 
                tpe.getActiveCount(), tpe.getCompletedTaskCount());
        }
        
        // Check lock state
        System.out.printf("🔒 Process Lock - Queued Threads: %d, Fair: %s%n",
            processLock.getQueueLength(), processLock.isFair());
        
        // Memory usage
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory() / 1024 / 1024;
        long freeMemory = runtime.freeMemory() / 1024 / 1024;
        System.out.printf("💾 Memory - Used: %d MB, Free: %d MB%n", 
            totalMemory - freeMemory, freeMemory);
        
        System.out.println("-".repeat(30));
    }
    
    /**
     * Start interactive user interface
     * 
     * USER INTERACTION PATTERN:
     * - Non-blocking input handling
     * - Real-time command processing
     * - Graceful command validation
     * - Help system for users
     */
    private void startUserInterface() {
        // Wait for system to be fully initialized
        try {
            startupLatch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logMessage("❌ System initialization timeout");
            return;
        }
        
        logMessage("🖥️  User interface ready");
        displayHelp();
        
        Scanner scanner = new Scanner(System.in);
        
        while (running) {
            System.out.print("\n🎮 Enter command (help for options): ");
            String command = scanner.nextLine().trim().toLowerCase();
            
            switch (command) {
                case "process":
                case "p":
                    backgroundExecutor.submit(() -> processNextImage("Manual"));
                    break;
                    
                case "pause":
                    pauseSystem();
                    break;
                    
                case "resume":
                    resumeSystem();
                    break;
                    
                case "status":
                case "s":
                    displayCurrentStatus();
                    break;
                    
                case "health":
                case "h":
                    displaySystemHealth();
                    break;
                    
                case "reset":
                    resetCounters();
                    break;
                    
                case "help":
                    displayHelp();
                    break;
                    
                case "quit":
                case "exit":
                case "q":
                    logMessage("🛑 Shutdown requested by user");
                    shutdown();
                    break;
                    
                default:
                    System.out.println("❓ Unknown command. Type 'help' for available commands.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Display help information
     */
    private void displayHelp() {
        System.out.println("\n🆘 AVAILABLE COMMANDS:");
        System.out.println("  process (p)  - Manually trigger image processing");
        System.out.println("  pause        - Pause automatic processing");
        System.out.println("  resume       - Resume automatic processing");
        System.out.println("  status (s)   - Show current system status");
        System.out.println("  health (h)   - Show system health information");
        System.out.println("  reset        - Reset processing counters");
        System.out.println("  help         - Show this help message");
        System.out.println("  quit (q)     - Exit the application");
    }
    
    /**
     * Pause the system
     * 
     * PAUSE PATTERN:
     * - Set atomic flag to prevent new processing
     * - Don't interrupt currently running tasks
     * - Allow resume without losing state
     */
    private void pauseSystem() {
        isPaused.set(true);
        logMessage("⏸️  System paused - automatic processing stopped");
        System.out.println("✅ System paused. Manual processing still available.");
    }
    
    /**
     * Resume the system
     */
    private void resumeSystem() {
        isPaused.set(false);
        logMessage("▶️  System resumed - automatic processing restarted");
        System.out.println("✅ System resumed. Automatic processing active.");
    }
    
    /**
     * Reset counters
     * 
     * RESET PATTERN:
     * - Use atomic operations for thread safety
     * - Log the operation for audit trail
     * - Provide user feedback
     */
    private void resetCounters() {
        imageProcessCount.set(0);
        logMessage("🔄 Processing counters reset to zero");
        System.out.println("✅ Counters reset successfully.");
    }
    
    /**
     * Thread-safe logging with timestamp
     * 
     * LOGGING PATTERN:
     * - Thread-safe console output
     * - Includes timestamp for debugging
     * - Shows thread name for troubleshooting
     * - Consistent format for parsing
     */
    private void logMessage(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        String threadName = Thread.currentThread().getName();
        System.out.printf("[%s] [%s] %s%n", timestamp, threadName, message);
    }
    
    /**
     * Graceful system shutdown
     * 
     * SHUTDOWN PATTERN:
     * 1. Stop accepting new tasks (set running flag)
     * 2. Cancel scheduled tasks
     * 3. Shutdown executors gracefully
     * 4. Wait for running tasks to complete
     * 5. Force shutdown if timeout exceeded
     * 
     * This prevents:
     * - Resource leaks
     * - Data corruption
     * - JVM hanging on exit
     */
    private void shutdown() {
        running = false;
        logMessage("🛑 Initiating system shutdown...");
        
        // Cancel scheduled tasks
        if (autoProcessTask != null) {
            autoProcessTask.cancel(false); // Don't interrupt running task
        }
        if (statusUpdateTask != null) {
            statusUpdateTask.cancel(false);
        }
        
        // Shutdown executors gracefully
        logMessage("📤 Shutting down thread pools...");
        scheduler.shutdown();
        backgroundExecutor.shutdown();
        
        try {
            // Wait for tasks to complete
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                logMessage("⚡ Force shutting down scheduler");
                scheduler.shutdownNow();
            }
            
            if (!backgroundExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
                logMessage("⚡ Force shutting down background executor");
                backgroundExecutor.shutdownNow();
            }
            
            logMessage("✅ All threads shut down successfully");
            System.out.println("\n🎉 System shutdown complete. Thank you for using the demo!");
            
        } catch (InterruptedException e) {
            logMessage("⚡ Thread shutdown interrupted - forcing immediate shutdown");
            scheduler.shutdownNow();
            backgroundExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Main method to run the demonstration
     */
    public static void main(String[] args) {
        System.out.println("🎓 Thread Synchronization Educational Demo");
        System.out.println("==========================================");
        System.out.println();
        System.out.println("This demo illustrates:");
        System.out.println("• ⏰ Scheduled thread execution");
        System.out.println("• 🔒 Thread synchronization with ReentrantLock");
        System.out.println("• ⚛️  Atomic operations for thread-safe counters");
        System.out.println("• 🎯 Background task coordination");
        System.out.println("• 🛡️  Proper exception handling in concurrent code");
        System.out.println("• 🧹 Graceful resource cleanup and shutdown");
        System.out.println();
        System.out.println("💡 Key Learning Points:");
        System.out.println("• Use AtomicInteger/AtomicBoolean for simple thread-safe operations");
        System.out.println("• ReentrantLock provides more flexibility than synchronized");
        System.out.println("• Always use try-finally blocks with manual locks");
        System.out.println("• ScheduledExecutorService is better than Timer for repeated tasks");
        System.out.println("• Proper shutdown prevents resource leaks");
        System.out.println();
        
        new ThreadSyncDemo();
    }
}
