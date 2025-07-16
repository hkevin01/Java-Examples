package advanced;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * FileIODemo - Comprehensive demonstration of Java File I/O operations
 * 
 * WHAT IS FILE I/O?
 * File Input/Output operations allow programs to read from and write to files,
 * enabling data persistence, configuration management, and data exchange between applications.
 * 
 * JAVA I/O EVOLUTION:
 * 1. Classic I/O (java.io): Stream-based, blocking operations
 * 2. New I/O (java.nio): Buffer-based, non-blocking capabilities
 * 3. New I/O.2 (java.nio.file): Path-based, modern file operations
 * 
 * KEY CONCEPTS:
 * - Streams: Sequential data flow (InputStream, OutputStream)
 * - Readers/Writers: Character-based I/O operations
 * - Buffers: Efficient bulk data operations
 * - Paths: Modern file system navigation
 * - File Attributes: Metadata and permissions
 * 
 * PERFORMANCE CONSIDERATIONS:
 * - Buffered streams are faster than unbuffered
 * - NIO.2 operations are generally more efficient
 * - Always close resources (use try-with-resources)
 * - Consider file size when choosing I/O approach
 * 
 * @author Java Examples Project
 * @version 1.0
 */
public class FileIODemo {
    
    private static final String DEMO_DIR = "demo_files";
    private static final String SAMPLE_FILE = DEMO_DIR + "/sample.txt";
    private static final String BINARY_FILE = DEMO_DIR + "/binary_data.dat";
    private static final String LARGE_FILE = DEMO_DIR + "/large_text.txt";
    
    /**
     * BASIC FILE OPERATIONS: Creating, reading, writing, and deleting files
     * 
     * TRADITIONAL APPROACH: Using java.io package
     * - File class for file manipulation
     * - FileInputStream/FileOutputStream for binary data
     * - FileReader/FileWriter for text data
     * - BufferedReader/BufferedWriter for efficiency
     */
    public static void demonstrateBasicFileOperations() {
        System.out.println("=== BASIC FILE OPERATIONS DEMONSTRATION ===\n");
        
        try {
            // Create demo directory
            setupDemoDirectory();
            
            // WRITING TO FILE
            System.out.println("1. Writing to File (Traditional I/O):");
            String content = "Hello, File I/O World!\n" +
                           "This is a sample file created by Java.\n" +
                           "It demonstrates basic file operations.\n" +
                           "Java makes file handling relatively easy.";
            
            // Method 1: Using FileWriter (simple but less efficient)
            try (FileWriter writer = new FileWriter(SAMPLE_FILE)) {
                writer.write(content);
                System.out.println("✓ Content written using FileWriter");
            }
            
            // READING FROM FILE
            System.out.println("\n2. Reading from File (Traditional I/O):");
            
            // Method 1: Using FileReader with BufferedReader (efficient)
            try (BufferedReader reader = new BufferedReader(new FileReader(SAMPLE_FILE))) {
                String line;
                int lineNumber = 1;
                System.out.println("File contents:");
                while ((line = reader.readLine()) != null) {
                    System.out.printf("%d: %s%n", lineNumber++, line);
                }
            }
            
            // Method 2: Reading entire file at once
            System.out.println("\n3. Reading Entire File at Once:");
            try (Scanner scanner = new Scanner(new File(SAMPLE_FILE))) {
                StringBuilder fileContent = new StringBuilder();
                while (scanner.hasNextLine()) {
                    fileContent.append(scanner.nextLine()).append("\n");
                }
                System.out.println("File length: " + fileContent.length() + " characters");
            }
            
            // FILE INFORMATION
            System.out.println("\n4. File Information:");
            File file = new File(SAMPLE_FILE);
            System.out.println("File exists: " + file.exists());
            System.out.println("File size: " + file.length() + " bytes");
            System.out.println("Last modified: " + new Date(file.lastModified()));
            System.out.println("Can read: " + file.canRead());
            System.out.println("Can write: " + file.canWrite());
            System.out.println("Is directory: " + file.isDirectory());
            System.out.println("Absolute path: " + file.getAbsolutePath());
            
        } catch (IOException e) {
            System.err.println("Error in basic file operations: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * MODERN FILE OPERATIONS: Using java.nio.file package (NIO.2)
     * 
     * ADVANTAGES OF NIO.2:
     * - Path-based operations (more intuitive than File)
     * - Better error handling with specific exceptions
     * - Atomic operations and better performance
     * - Support for file attributes and symbolic links
     * - Watch service for monitoring file changes
     */
    public static void demonstrateModernFileOperations() {
        System.out.println("=== MODERN FILE OPERATIONS (NIO.2) DEMONSTRATION ===\n");
        
        try {
            Path samplePath = Paths.get(SAMPLE_FILE);
            
            // WRITING WITH NIO.2
            System.out.println("1. Writing with NIO.2:");
            List<String> lines = Arrays.asList(
                "Modern Java File I/O using NIO.2",
                "Path-based operations are more intuitive",
                "Better performance and error handling",
                "Support for atomic operations",
                "Excellent for modern applications"
            );
            
            Files.write(samplePath, lines, StandardCharsets.UTF_8);
            System.out.println("✓ Content written using Files.write()");
            
            // READING WITH NIO.2
            System.out.println("\n2. Reading with NIO.2:");
            
            // Method 1: Read all lines
            List<String> readLines = Files.readAllLines(samplePath, StandardCharsets.UTF_8);
            System.out.println("Read " + readLines.size() + " lines:");
            for (int i = 0; i < readLines.size(); i++) {
                System.out.printf("%d: %s%n", i + 1, readLines.get(i));
            }
            
            // Method 2: Read as Stream (memory efficient for large files)
            System.out.println("\n3. Reading as Stream (NIO.2):");
            try (Stream<String> streamLines = Files.lines(samplePath)) {
                long wordCount = streamLines
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .count();
                System.out.println("Total words in file: " + wordCount);
            }
            
            // FILE ATTRIBUTES WITH NIO.2
            System.out.println("\n4. Advanced File Attributes:");
            System.out.println("File size: " + Files.size(samplePath) + " bytes");
            System.out.println("Last modified: " + Files.getLastModifiedTime(samplePath));
            System.out.println("Is regular file: " + Files.isRegularFile(samplePath));
            System.out.println("Is readable: " + Files.isReadable(samplePath));
            System.out.println("Is writable: " + Files.isWritable(samplePath));
            
            // COPYING FILES
            System.out.println("\n5. File Copying:");
            Path copyPath = Paths.get(DEMO_DIR + "/sample_copy.txt");
            Files.copy(samplePath, copyPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✓ File copied to: " + copyPath);
            
            // MOVING FILES
            System.out.println("\n6. File Moving:");
            Path movedPath = Paths.get(DEMO_DIR + "/sample_moved.txt");
            Files.move(copyPath, movedPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("✓ File moved to: " + movedPath);
            
            // DELETING FILES
            System.out.println("\n7. File Deletion:");
            Files.deleteIfExists(movedPath);
            System.out.println("✓ Moved file deleted");
            
        } catch (IOException e) {
            System.err.println("Error in modern file operations: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * BINARY FILE OPERATIONS: Working with binary data
     * 
     * USE CASES:
     * - Images, audio, video files
     * - Serialized objects
     * - Network protocols
     * - Custom binary formats
     * 
     * TECHNIQUES:
     * - DataInputStream/DataOutputStream for primitive types
     * - ObjectInputStream/ObjectOutputStream for serialization
     * - ByteArrayInputStream/ByteArrayOutputStream for in-memory operations
     */
    public static void demonstrateBinaryFileOperations() {
        System.out.println("=== BINARY FILE OPERATIONS DEMONSTRATION ===\n");
        
        try {
            // WRITING BINARY DATA
            System.out.println("1. Writing Binary Data:");
            try (DataOutputStream dos = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(BINARY_FILE)))) {
                
                // Write different primitive types
                dos.writeInt(42);
                dos.writeDouble(3.14159);
                dos.writeBoolean(true);
                dos.writeUTF("Binary String Data");
                dos.writeLong(System.currentTimeMillis());
                
                System.out.println("✓ Binary data written to file");
            }
            
            // READING BINARY DATA
            System.out.println("\n2. Reading Binary Data:");
            try (DataInputStream dis = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(BINARY_FILE)))) {
                
                int intValue = dis.readInt();
                double doubleValue = dis.readDouble();
                boolean booleanValue = dis.readBoolean();
                String stringValue = dis.readUTF();
                long longValue = dis.readLong();
                
                System.out.println("Read integer: " + intValue);
                System.out.println("Read double: " + doubleValue);
                System.out.println("Read boolean: " + booleanValue);
                System.out.println("Read string: " + stringValue);
                System.out.println("Read long: " + longValue);
            }
            
            // OBJECT SERIALIZATION
            System.out.println("\n3. Object Serialization:");
            
            // Create a serializable object
            SampleData data = new SampleData("Sample Object", 100, Arrays.asList("A", "B", "C"));
            
            // Serialize object to file
            String objectFile = DEMO_DIR + "/object_data.ser";
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(objectFile))) {
                oos.writeObject(data);
                System.out.println("✓ Object serialized to file");
            }
            
            // Deserialize object from file
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(objectFile))) {
                SampleData readData = (SampleData) ois.readObject();
                System.out.println("✓ Object deserialized: " + readData);
            } catch (ClassNotFoundException e) {
                System.err.println("Class not found during deserialization: " + e.getMessage());
            }
            
        } catch (IOException e) {
            System.err.println("Error in binary file operations: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * DIRECTORY OPERATIONS: Working with directories and file trees
     * 
     * OPERATIONS:
     * - Creating directories
     * - Listing directory contents
     * - Walking file trees
     * - Finding files
     * - Directory monitoring
     */
    public static void demonstrateDirectoryOperations() {
        System.out.println("=== DIRECTORY OPERATIONS DEMONSTRATION ===\n");
        
        try {
            Path demoDir = Paths.get(DEMO_DIR);
            
            // CREATE SUBDIRECTORIES
            System.out.println("1. Creating Directory Structure:");
            Path subDir1 = demoDir.resolve("subdir1");
            Path subDir2 = demoDir.resolve("subdir2");
            Path nestedDir = subDir1.resolve("nested");
            
            Files.createDirectories(nestedDir);
            Files.createDirectories(subDir2);
            System.out.println("✓ Created directory structure");
            
            // CREATE SAMPLE FILES IN DIRECTORIES
            Files.write(subDir1.resolve("file1.txt"), "Content 1".getBytes());
            Files.write(subDir2.resolve("file2.txt"), "Content 2".getBytes());
            Files.write(nestedDir.resolve("nested_file.txt"), "Nested Content".getBytes());
            
            // LIST DIRECTORY CONTENTS
            System.out.println("\n2. Listing Directory Contents:");
            try (Stream<Path> paths = Files.list(demoDir)) {
                paths.forEach(path -> {
                    try {
                        String type = Files.isDirectory(path) ? "DIR " : "FILE";
                        long size = Files.isDirectory(path) ? 0 : Files.size(path);
                        System.out.printf("%s: %s (%d bytes)%n", type, path.getFileName(), size);
                    } catch (IOException e) {
                        System.err.println("Error reading: " + path);
                    }
                });
            }
            
            // WALK FILE TREE
            System.out.println("\n3. Walking File Tree:");
            try (Stream<Path> paths = Files.walk(demoDir)) {
                paths.filter(Files::isRegularFile)
                      .forEach(file -> {
                          try {
                              System.out.printf("Found file: %s (%d bytes)%n", 
                                  file, Files.size(file));
                          } catch (IOException e) {
                              System.err.println("Error reading file size: " + file);
                          }
                      });
            }
            
            // FIND FILES BY PATTERN
            System.out.println("\n4. Finding Files by Pattern:");
            try (Stream<Path> paths = Files.find(demoDir, 10, 
                    (path, attrs) -> path.toString().endsWith(".txt"))) {
                paths.forEach(file -> System.out.println("Found .txt file: " + file));
            }
            
            // DIRECTORY SIZE CALCULATION
            System.out.println("\n5. Directory Size Calculation:");
            long totalSize = Files.walk(demoDir)
                .filter(Files::isRegularFile)
                .mapToLong(file -> {
                    try {
                        return Files.size(file);
                    } catch (IOException e) {
                        return 0;
                    }
                })
                .sum();
            System.out.println("Total directory size: " + totalSize + " bytes");
            
        } catch (IOException e) {
            System.err.println("Error in directory operations: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * PERFORMANCE CONSIDERATIONS: Efficient file I/O techniques
     * 
     * PERFORMANCE TIPS:
     * - Use buffered streams for small, frequent operations
     * - Use NIO.2 for large file operations
     * - Consider memory mapping for very large files
     * - Use appropriate buffer sizes
     * - Always close resources properly
     */
    public static void demonstratePerformanceConsiderations() {
        System.out.println("=== PERFORMANCE CONSIDERATIONS DEMONSTRATION ===\n");
        
        try {
            // CREATE LARGE FILE FOR TESTING
            createLargeTestFile();
            
            Path largePath = Paths.get(LARGE_FILE);
            
            // BUFFERED VS UNBUFFERED READING
            System.out.println("1. Buffered vs Unbuffered Reading Performance:");
            
            // Unbuffered reading (slower)
            long startTime = System.currentTimeMillis();
            try (FileInputStream fis = new FileInputStream(LARGE_FILE)) {
                int byteRead;
                int count = 0;
                while ((byteRead = fis.read()) != -1) {
                    count++;
                }
                long unbufferedTime = System.currentTimeMillis() - startTime;
                System.out.printf("Unbuffered reading: %d bytes in %d ms%n", count, unbufferedTime);
            }
            
            // Buffered reading (faster)
            startTime = System.currentTimeMillis();
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(LARGE_FILE))) {
                int byteRead;
                int count = 0;
                while ((byteRead = bis.read()) != -1) {
                    count++;
                }
                long bufferedTime = System.currentTimeMillis() - startTime;
                System.out.printf("Buffered reading: %d bytes in %d ms%n", count, bufferedTime);
            }
            
            // NIO.2 READING PERFORMANCE
            System.out.println("\n2. NIO.2 Reading Performance:");
            startTime = System.currentTimeMillis();
            byte[] allBytes = Files.readAllBytes(largePath);
            long nioTime = System.currentTimeMillis() - startTime;
            System.out.printf("NIO.2 reading: %d bytes in %d ms%n", allBytes.length, nioTime);
            
            // STREAMING LARGE FILES
            System.out.println("\n3. Streaming Large Files (Memory Efficient):");
            startTime = System.currentTimeMillis();
            try (Stream<String> lines = Files.lines(largePath)) {
                long lineCount = lines.count();
                long streamTime = System.currentTimeMillis() - startTime;
                System.out.printf("Streamed counting: %d lines in %d ms%n", lineCount, streamTime);
            }
            
        } catch (IOException e) {
            System.err.println("Error in performance demonstration: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Helper method to create demo directory
     */
    private static void setupDemoDirectory() throws IOException {
        Path demoPath = Paths.get(DEMO_DIR);
        if (!Files.exists(demoPath)) {
            Files.createDirectories(demoPath);
            System.out.println("✓ Created demo directory: " + DEMO_DIR);
        }
    }
    
    /**
     * Helper method to create a large test file
     */
    private static void createLargeTestFile() throws IOException {
        Path largePath = Paths.get(LARGE_FILE);
        if (!Files.exists(largePath)) {
            System.out.println("Creating large test file...");
            try (BufferedWriter writer = Files.newBufferedWriter(largePath)) {
                for (int i = 0; i < 10000; i++) {
                    writer.write("This is line number " + i + " in our large test file for performance testing.\n");
                }
            }
            System.out.println("✓ Large test file created");
        }
    }
    
    /**
     * Cleanup method to remove demo files and directories
     */
    public static void cleanup() {
        try {
            Path demoPath = Paths.get(DEMO_DIR);
            if (Files.exists(demoPath)) {
                // Delete all files and subdirectories
                Files.walk(demoPath)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            System.err.println("Could not delete: " + path);
                        }
                    });
                System.out.println("✓ Cleanup completed");
            }
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
    
    /**
     * Sample data class for serialization demonstration
     */
    static class SampleData implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String name;
        private int value;
        private List<String> items;
        
        public SampleData(String name, int value, List<String> items) {
            this.name = name;
            this.value = value;
            this.items = new ArrayList<>(items);
        }
        
        @Override
        public String toString() {
            return String.format("SampleData{name='%s', value=%d, items=%s}", name, value, items);
        }
    }
    
    /**
     * Main method demonstrating all file I/O concepts
     */
    public static void main(String[] args) {
        System.out.println("🚀 JAVA FILE I/O COMPREHENSIVE DEMO");
        System.out.println("=" .repeat(60));
        
        demonstrateBasicFileOperations();
        System.out.println("-".repeat(60));
        
        demonstrateModernFileOperations();
        System.out.println("-".repeat(60));
        
        demonstrateBinaryFileOperations();
        System.out.println("-".repeat(60));
        
        demonstrateDirectoryOperations();
        System.out.println("-".repeat(60));
        
        demonstratePerformanceConsiderations();
        
        System.out.println("✅ All File I/O demonstrations completed!");
        
        // Cleanup demo files
        System.out.println("\n🧹 Cleaning up demo files...");
        cleanup();
        
        System.out.println("\n📚 KEY TAKEAWAYS:");
        System.out.println("• Use try-with-resources for automatic resource management");
        System.out.println("• NIO.2 (java.nio.file) is preferred for modern applications");
        System.out.println("• Buffered streams improve performance for small, frequent operations");
        System.out.println("• Streaming is memory-efficient for large files");
        System.out.println("• Always handle IOException appropriately");
        System.out.println("• Consider file size and access patterns when choosing I/O approach");
        System.out.println("• Binary operations are essential for non-text data");
    }
}
