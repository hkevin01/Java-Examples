package bestpractices;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;

/**
 * SecurityConsiderationsDemo - Demonstrates Java Security Best Practices
 * 
 * WHY SECURITY IS PARAMOUNT:
 * Security vulnerabilities can lead to:
 * - Data breaches exposing sensitive customer information
 * - Financial losses due to fraud or regulatory fines
 * - Reputation damage and loss of customer trust
 * - Legal liabilities and compliance violations
 * - Service disruptions and business continuity issues
 * 
 * SECURITY PRINCIPLES:
 * 1. Defense in Depth: Multiple layers of security controls
 * 2. Principle of Least Privilege: Grant minimal necessary permissions
 * 3. Fail Securely: When something goes wrong, fail to a secure state
 * 4. Security by Design: Build security in from the beginning
 * 5. Never Trust User Input: Validate and sanitize everything
 * 6. Keep It Simple: Complex security is often insecure security
 * 
 * COMMON VULNERABILITY CATEGORIES (OWASP Top 10):
 * 1. Injection attacks (SQL, NoSQL, Command injection)
 * 2. Broken authentication and session management
 * 3. Cross-Site Scripting (XSS)
 * 4. Insecure direct object references
 * 5. Security misconfiguration
 * 6. Sensitive data exposure
 * 7. Missing function level access control
 * 8. Cross-Site Request Forgery (CSRF)
 * 9. Using components with known vulnerabilities
 * 10. Unvalidated redirects and forwards
 * 
 * This comprehensive demo covers:
 * - Input validation and sanitization (preventing injection attacks)
 * - Secure password handling (proper hashing and storage)
 * - Cryptographic operations (encryption, signatures, hashing)
 * - Secure random number generation (for keys, tokens, etc.)
 * - SQL injection prevention (parameterized queries)
 * - Cross-site scripting (XSS) prevention (output encoding)
 * - Path traversal protection (file access security)
 * - Secure coding practices (comprehensive security mindset)
 * 
 * CRYPTOGRAPHIC BEST PRACTICES:
 * - Use well-established algorithms (AES, RSA, SHA-256)
 * - Never implement custom cryptographic algorithms
 * - Use appropriate key sizes (AES-256, RSA-2048+)
 * - Generate truly random keys and initialization vectors
 * - Implement proper key management and rotation
 * - Use authenticated encryption modes (GCM, CCM)
 * 
 * LEARNING OBJECTIVES:
 * 1. Understand common security vulnerabilities and how to prevent them
 * 2. Implement robust input validation and output sanitization
 * 3. Use cryptography correctly and securely
 * 4. Apply secure coding practices throughout development
 * 5. Design security controls that are both effective and usable
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// Input Validation Examples
// Input validation is the first line of defense against many security vulnerabilities.
// The principle: "Never trust user input" - validate everything that comes from outside your application.

class InputValidator {
    
    /*
     * REGEX PATTERN SECURITY CONSIDERATIONS:
     * 
     * 1. Email validation: Strict enough to prevent obvious attacks but not so strict
     *    as to reject valid email addresses. Real email validation is complex!
     * 
     * 2. Phone validation: Supports international E.164 format
     * 
     * 3. Username validation: Alphanumeric and underscore only, reasonable length limits
     * 
     * 4. Filename validation: Prevents path traversal and dangerous characters
     * 
     * WHY PRE-COMPILED PATTERNS:
     * - Regex compilation is expensive - do it once, reuse many times
     * - Static final ensures thread safety and immutability
     * - Better performance in high-throughput applications
     */
    
    // Email pattern: reasonably strict but not overly restrictive
    // This catches most malicious input while allowing legitimate email formats
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+?[1-9]\\d{1,14}$" // E.164 format
    );
    
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_]{3,20}$"
    );
    
    private static final Pattern SAFE_FILENAME_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._-]+$"
    );
    
    /**
     * Validates email address format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Validates phone number format
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null) {
            return false;
        }
        String cleanPhone = phone.replaceAll("[\\s()-]", "");
        return PHONE_PATTERN.matcher(cleanPhone).matches();
    }
    
    /**
     * Validates username format
     */
    public static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }
    
    /**
     * Validates numeric input within range
     */
    public static boolean isValidNumber(String input, int min, int max) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        
        try {
            int number = Integer.parseInt(input.trim());
            return number >= min && number <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates filename to prevent path traversal attacks
     */
    public static boolean isSafeFilename(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }
        
        String normalized = filename.trim();
        
        // Check for path traversal attempts
        if (normalized.contains("..") || 
            normalized.contains("/") || 
            normalized.contains("\\") ||
            normalized.startsWith(".")) {
            return false;
        }
        
        return SAFE_FILENAME_PATTERN.matcher(normalized).matches();
    }
    
    /**
     * Sanitizes string input to prevent XSS attacks
     */
    public static String sanitizeForHTML(String input) {
        if (input == null) {
            return "";
        }
        
        return input.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#x27;")
                   .replace("/", "&#x2F;");
    }
    
    /**
     * Validates and sanitizes SQL parameters to prevent injection
     */
    public static String sanitizeForSQL(String input) {
        if (input == null) {
            return "";
        }
        
        // Remove or escape dangerous characters
        return input.replace("'", "''")
                   .replace("\"", "\"\"")
                   .replace(";", "")
                   .replace("--", "")
                   .replace("/*", "")
                   .replace("*/", "")
                   .replace("xp_", "")
                   .replace("sp_", "");
    }
}

// Secure Password Handling

class SecurePasswordManager {
    
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int SALT_LENGTH = 32;
    private static final int HASH_ITERATIONS = 100000; // PBKDF2 iterations
    private static final int HASH_LENGTH = 256; // bits
    
    /**
     * Generates a cryptographically secure salt
     */
    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SECURE_RANDOM.nextBytes(salt);
        return salt;
    }
    
    /**
     * Hashes password using PBKDF2 with SHA-256
     */
    public static byte[] hashPassword(String password, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(
                password.toCharArray(), 
                salt, 
                HASH_ITERATIONS, 
                HASH_LENGTH
            );
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }
    
    /**
     * Verifies password against stored hash
     */
    public static boolean verifyPassword(String password, byte[] salt, byte[] hash) {
        byte[] testHash = hashPassword(password, salt);
        return MessageDigest.isEqual(testHash, hash);
    }
    
    /**
     * Generates a secure random password
     */
    public static String generateSecurePassword(int length) {
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        
        String allChars = upperCase + lowerCase + digits + special;
        StringBuilder password = new StringBuilder();
        
        // Ensure at least one character from each category
        password.append(upperCase.charAt(SECURE_RANDOM.nextInt(upperCase.length())));
        password.append(lowerCase.charAt(SECURE_RANDOM.nextInt(lowerCase.length())));
        password.append(digits.charAt(SECURE_RANDOM.nextInt(digits.length())));
        password.append(special.charAt(SECURE_RANDOM.nextInt(special.length())));
        
        // Fill remaining length with random characters
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(SECURE_RANDOM.nextInt(allChars.length())));
        }
        
        // Shuffle the password
        List<Character> chars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            chars.add(c);
        }
        Collections.shuffle(chars, SECURE_RANDOM);
        
        StringBuilder shuffled = new StringBuilder();
        for (char c : chars) {
            shuffled.append(c);
        }
        
        return shuffled.toString();
    }
    
    /**
     * Validates password strength
     */
    public static PasswordStrength assessPasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            return PasswordStrength.WEAK;
        }
        
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(ch) >= 0);
        
        int criteriaCount = 0;
        if (hasUpper) criteriaCount++;
        if (hasLower) criteriaCount++;
        if (hasDigit) criteriaCount++;
        if (hasSpecial) criteriaCount++;
        
        if (password.length() >= 12 && criteriaCount >= 3) {
            return PasswordStrength.STRONG;
        } else if (password.length() >= 8 && criteriaCount >= 2) {
            return PasswordStrength.MEDIUM;
        } else {
            return PasswordStrength.WEAK;
        }
    }
    
    public enum PasswordStrength {
        WEAK, MEDIUM, STRONG
    }
}

// Cryptographic Operations

class CryptographyExample {
    
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;
    
    /**
     * Generates a secure AES key
     */
    public static SecretKey generateAESKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(AES_ALGORITHM);
            keyGen.init(256); // AES-256
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("AES key generation failed", e);
        }
    }
    
    /**
     * Encrypts data using AES-GCM
     */
    public static EncryptionResult encryptAES(String plaintext, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            
            // Generate random IV
            byte[] iv = new byte[GCM_IV_LENGTH];
            new SecureRandom().nextBytes(iv);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
            
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            
            return new EncryptionResult(ciphertext, iv);
            
        } catch (Exception e) {
            throw new RuntimeException("AES encryption failed", e);
        }
    }
    
    /**
     * Decrypts data using AES-GCM
     */
    public static String decryptAES(EncryptionResult encryptionResult, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(
                GCM_TAG_LENGTH * 8, 
                encryptionResult.getIv()
            );
            
            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
            byte[] plaintext = cipher.doFinal(encryptionResult.getCiphertext());
            
            return new String(plaintext, StandardCharsets.UTF_8);
            
        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }
    
    /**
     * Creates a digital signature using RSA
     */
    public static byte[] signData(String data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException("Digital signing failed", e);
        }
    }
    
    /**
     * Verifies a digital signature using RSA
     */
    public static boolean verifySignature(String data, byte[] signatureBytes, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            throw new RuntimeException("Signature verification failed", e);
        }
    }
    
    /**
     * Generates RSA key pair
     */
    public static KeyPair generateRSAKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048); // RSA-2048
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RSA key generation failed", e);
        }
    }
    
    /**
     * Computes SHA-256 hash
     */
    public static byte[] computeSHA256Hash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 hashing failed", e);
        }
    }
    
    static class EncryptionResult {
        private final byte[] ciphertext;
        private final byte[] iv;
        
        public EncryptionResult(byte[] ciphertext, byte[] iv) {
            this.ciphertext = ciphertext.clone();
            this.iv = iv.clone();
        }
        
        public byte[] getCiphertext() {
            return ciphertext.clone();
        }
        
        public byte[] getIv() {
            return iv.clone();
        }
    }
}

// Secure Random Number Generation

class SecureRandomExample {
    
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    
    /**
     * Generates cryptographically secure random bytes
     */
    public static byte[] generateSecureRandomBytes(int length) {
        byte[] bytes = new byte[length];
        SECURE_RANDOM.nextBytes(bytes);
        return bytes;
    }
    
    /**
     * Generates secure random string
     */
    public static String generateSecureRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(SECURE_RANDOM.nextInt(chars.length())));
        }
        
        return result.toString();
    }
    
    /**
     * Generates secure session token
     */
    public static String generateSessionToken() {
        byte[] tokenBytes = generateSecureRandomBytes(32);
        return Base64.getEncoder().encodeToString(tokenBytes);
    }
    
    /**
     * Demonstrates insecure vs secure random number generation
     */
    public static void demonstrateRandomSecurity() {
        System.out.println("=== SECURE RANDOM DEMONSTRATION ===");
        
        // Insecure: predictable pseudo-random
        Random insecureRandom = new Random(12345); // Fixed seed
        System.out.println("🔴 Insecure Random (fixed seed):");
        for (int i = 0; i < 5; i++) {
            System.out.println("   " + insecureRandom.nextInt(1000));
        }
        
        // Reset with same seed - will produce same sequence
        insecureRandom = new Random(12345);
        System.out.println("🔴 Same sequence with same seed:");
        for (int i = 0; i < 5; i++) {
            System.out.println("   " + insecureRandom.nextInt(1000));
        }
        
        // Secure: cryptographically strong random
        System.out.println("🟢 Secure Random:");
        for (int i = 0; i < 5; i++) {
            System.out.println("   " + SECURE_RANDOM.nextInt(1000));
        }
        
        System.out.println("🔑 Secure session token: " + generateSessionToken());
    }
}

// SQL Injection Prevention

class SQLInjectionPrevention {
    
    /**
     * Demonstrates vulnerable SQL construction (DO NOT USE)
     */
    public static void demonstrateVulnerableSQL() {
        System.out.println("\n=== SQL INJECTION VULNERABILITY DEMONSTRATION ===");
        
        String userInput = "admin'; DROP TABLE users; --";
        
        // VULNERABLE: String concatenation
        String vulnerableSQL = "SELECT * FROM users WHERE username = '" + userInput + "'";
        System.out.println("🔴 Vulnerable SQL:");
        System.out.println("   " + vulnerableSQL);
        System.out.println("   ❌ This would execute: DROP TABLE users!");
        
        // SECURE: Parameterized query (simulated)
        String secureSQL = "SELECT * FROM users WHERE username = ?";
        System.out.println("\n🟢 Secure parameterized query:");
        System.out.println("   SQL: " + secureSQL);
        System.out.println("   Parameter: " + userInput);
        System.out.println("   ✅ Input treated as data, not code");
    }
    
    /**
     * Demonstrates secure SQL parameter handling
     */
    public static String prepareSecureQuery(String baseQuery, String... parameters) {
        StringBuilder result = new StringBuilder(baseQuery);
        result.append(" [Parameters: ");
        for (int i = 0; i < parameters.length; i++) {
            if (i > 0) result.append(", ");
            result.append("$").append(i + 1).append("='")
                  .append(InputValidator.sanitizeForSQL(parameters[i]))
                  .append("'");
        }
        result.append("]");
        return result.toString();
    }
}

// File Security

class FileSecurityExample {
    
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
        ".txt", ".pdf", ".jpg", ".jpeg", ".png", ".gif", ".doc", ".docx"
    );
    
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    
    /**
     * Validates file upload security
     */
    public static FileValidationResult validateFileUpload(String filename, long fileSize, byte[] content) {
        List<String> errors = new ArrayList<>();
        
        // Validate filename
        if (!InputValidator.isSafeFilename(filename)) {
            errors.add("Invalid filename format");
        }
        
        // Check extension
        String extension = getFileExtension(filename);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            errors.add("File type not allowed: " + extension);
        }
        
        // Check file size
        if (fileSize > MAX_FILE_SIZE) {
            errors.add("File size exceeds limit: " + fileSize + " bytes");
        }
        
        // Validate file content (basic check)
        if (content != null && content.length > 0) {
            if (!isValidFileContent(content, extension)) {
                errors.add("File content doesn't match extension");
            }
        }
        
        return new FileValidationResult(errors.isEmpty(), errors);
    }
    
    private static String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return lastDot > 0 ? filename.substring(lastDot) : "";
    }
    
    private static boolean isValidFileContent(byte[] content, String extension) {
        // Basic file signature validation
        if (extension.equals(".pdf") && content.length >= 4) {
            return content[0] == 0x25 && content[1] == 0x50 && 
                   content[2] == 0x44 && content[3] == 0x46; // %PDF
        }
        
        if ((extension.equals(".jpg") || extension.equals(".jpeg")) && content.length >= 3) {
            return content[0] == (byte)0xFF && content[1] == (byte)0xD8 && 
                   content[2] == (byte)0xFF; // JPEG signature
        }
        
        // For demonstration, assume other types are valid
        return true;
    }
    
    static class FileValidationResult {
        private final boolean valid;
        private final List<String> errors;
        
        public FileValidationResult(boolean valid, List<String> errors) {
            this.valid = valid;
            this.errors = new ArrayList<>(errors);
        }
        
        public boolean isValid() { return valid; }
        public List<String> getErrors() { return new ArrayList<>(errors); }
    }
}

public class SecurityConsiderationsDemo {
    
    /**
     * Demonstrates input validation best practices
     */
    public static void demonstrateInputValidation() {
        System.out.println("=== INPUT VALIDATION DEMONSTRATION ===");
        
        String[] testEmails = {
            "user@example.com",
            "invalid.email",
            "test@domain",
            "user+tag@example.org",
            "<script>alert('xss')</script>@test.com"
        };
        
        System.out.println("📧 Email Validation:");
        for (String email : testEmails) {
            boolean valid = InputValidator.isValidEmail(email);
            System.out.printf("   %s: %s%n", 
                             email, 
                             valid ? "✅ Valid" : "❌ Invalid");
        }
        
        String[] testUsernames = {
            "validuser123",
            "user_name",
            "ab", // too short
            "user with spaces",
            "user!@#$" // special chars
        };
        
        System.out.println("\n👤 Username Validation:");
        for (String username : testUsernames) {
            boolean valid = InputValidator.isValidUsername(username);
            System.out.printf("   %s: %s%n", 
                             username, 
                             valid ? "✅ Valid" : "❌ Invalid");
        }
        
        // XSS Prevention
        String maliciousInput = "<script>alert('XSS Attack!');</script>";
        String sanitized = InputValidator.sanitizeForHTML(maliciousInput);
        System.out.println("\n🛡️ XSS Prevention:");
        System.out.println("   Original: " + maliciousInput);
        System.out.println("   Sanitized: " + sanitized);
    }
    
    /**
     * Demonstrates secure password handling
     */
    public static void demonstratePasswordSecurity() {
        System.out.println("\n=== PASSWORD SECURITY DEMONSTRATION ===");
        
        String password = "MySecurePassword123!";
        
        // Generate salt and hash password
        byte[] salt = SecurePasswordManager.generateSalt();
        byte[] hash = SecurePasswordManager.hashPassword(password, salt);
        
        System.out.println("🔐 Password Hashing:");
        System.out.println("   Password: " + password);
        System.out.println("   Salt: " + Base64.getEncoder().encodeToString(salt));
        System.out.println("   Hash: " + Base64.getEncoder().encodeToString(hash));
        
        // Verify password
        boolean verified = SecurePasswordManager.verifyPassword(password, salt, hash);
        System.out.println("   Verification: " + (verified ? "✅ Success" : "❌ Failed"));
        
        // Test wrong password
        boolean wrongPassword = SecurePasswordManager.verifyPassword("WrongPassword", salt, hash);
        System.out.println("   Wrong password: " + (wrongPassword ? "❌ Accepted" : "✅ Rejected"));
        
        // Generate secure password
        String generatedPassword = SecurePasswordManager.generateSecurePassword(16);
        System.out.println("\n🎲 Generated secure password: " + generatedPassword);
        
        // Assess password strength
        String[] testPasswords = {
            "123456",
            "password",
            "Password123",
            "MyVerySecureP@ssw0rd!"
        };
        
        System.out.println("\n💪 Password Strength Assessment:");
        for (String pwd : testPasswords) {
            SecurePasswordManager.PasswordStrength strength = 
                SecurePasswordManager.assessPasswordStrength(pwd);
            System.out.printf("   %-25s: %s%n", pwd, strength);
        }
    }
    
    /**
     * Demonstrates cryptographic operations
     */
    public static void demonstrateCryptography() {
        System.out.println("\n=== CRYPTOGRAPHY DEMONSTRATION ===");
        
        // AES Encryption
        String plaintext = "This is sensitive data that needs encryption.";
        SecretKey aesKey = CryptographyExample.generateAESKey();
        
        CryptographyExample.EncryptionResult encrypted = 
            CryptographyExample.encryptAES(plaintext, aesKey);
        
        String decrypted = CryptographyExample.decryptAES(encrypted, aesKey);
        
        System.out.println("🔒 AES Encryption:");
        System.out.println("   Original: " + plaintext);
        System.out.println("   Encrypted: " + Base64.getEncoder().encodeToString(encrypted.getCiphertext()));
        System.out.println("   Decrypted: " + decrypted);
        System.out.println("   Match: " + (plaintext.equals(decrypted) ? "✅" : "❌"));
        
        // Digital Signature
        KeyPair rsaKeyPair = CryptographyExample.generateRSAKeyPair();
        String document = "This is an important document.";
        
        byte[] signature = CryptographyExample.signData(document, rsaKeyPair.getPrivate());
        boolean signatureValid = CryptographyExample.verifySignature(
            document, signature, rsaKeyPair.getPublic()
        );
        
        System.out.println("\n✍️ Digital Signature:");
        System.out.println("   Document: " + document);
        System.out.println("   Signature: " + Base64.getEncoder().encodeToString(signature));
        System.out.println("   Valid: " + (signatureValid ? "✅" : "❌"));
        
        // Hash computation
        byte[] hash = CryptographyExample.computeSHA256Hash(document);
        System.out.println("   SHA-256: " + Base64.getEncoder().encodeToString(hash));
    }
    
    /**
     * Demonstrates file security validation
     */
    public static void demonstrateFileSecurity() {
        System.out.println("\n=== FILE SECURITY DEMONSTRATION ===");
        
        String[] testFiles = {
            "document.pdf",
            "image.jpg",
            "script.exe",
            "../../../etc/passwd",
            "normal_file.txt"
        };
        
        System.out.println("📁 File Upload Validation:");
        for (String filename : testFiles) {
            FileSecurityExample.FileValidationResult result = 
                FileSecurityExample.validateFileUpload(filename, 1024, new byte[0]);
            
            System.out.printf("   %-25s: %s%n", 
                             filename, 
                             result.isValid() ? "✅ Valid" : "❌ " + String.join(", ", result.getErrors()));
        }
    }
    
    /**
     * Analyzes security best practices
     */
    public static void analyzeSecurityPractices() {
        System.out.println("\n=== SECURITY BEST PRACTICES ANALYSIS ===");
        
        System.out.println("🛡️ Input Validation Principles:");
        System.out.println("• Validate all input at entry points");
        System.out.println("• Use whitelist validation over blacklist");
        System.out.println("• Sanitize output based on context (HTML, SQL, etc.)");
        System.out.println("• Implement length limits and format validation");
        System.out.println("• Never trust client-side validation alone");
        
        System.out.println("\n🔐 Authentication & Authorization:");
        System.out.println("• Use strong password policies");
        System.out.println("• Implement proper session management");
        System.out.println("• Use secure password hashing (PBKDF2, bcrypt, Argon2)");
        System.out.println("• Implement multi-factor authentication");
        System.out.println("• Follow principle of least privilege");
        
        System.out.println("\n🔒 Cryptography Best Practices:");
        System.out.println("• Use well-established algorithms (AES, RSA, SHA-256)");
        System.out.println("• Never implement custom cryptographic algorithms");
        System.out.println("• Use appropriate key sizes (AES-256, RSA-2048+)");
        System.out.println("• Generate truly random keys and IVs");
        System.out.println("• Implement proper key management");
        
        System.out.println("\n💾 Data Protection:");
        System.out.println("• Encrypt sensitive data at rest and in transit");
        System.out.println("• Use HTTPS for all communications");
        System.out.println("• Implement proper logging without sensitive data");
        System.out.println("• Follow data minimization principles");
        System.out.println("• Implement secure backup and recovery");
        
        System.out.println("\n🔍 Common Vulnerabilities (OWASP Top 10):");
        System.out.println("• Injection attacks (SQL, NoSQL, Command)");
        System.out.println("• Broken authentication and session management");
        System.out.println("• Cross-Site Scripting (XSS)");
        System.out.println("• Insecure direct object references");
        System.out.println("• Security misconfiguration");
        System.out.println("• Sensitive data exposure");
        System.out.println("• Missing function level access control");
        System.out.println("• Cross-Site Request Forgery (CSRF)");
        System.out.println("• Using components with known vulnerabilities");
        System.out.println("• Unvalidated redirects and forwards");
        
        System.out.println("\n🛠️ Security Tools and Practices:");
        System.out.println("• Static Application Security Testing (SAST)");
        System.out.println("• Dynamic Application Security Testing (DAST)");
        System.out.println("• Dependency vulnerability scanning");
        System.out.println("• Regular security code reviews");
        System.out.println("• Penetration testing");
        System.out.println("• Security awareness training");
    }
    
    /**
     * Main method demonstrating security considerations
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Security Considerations Demonstration");
        System.out.println("=========================================");
        
        demonstrateInputValidation();
        demonstratePasswordSecurity();
        demonstrateCryptography();
        SecureRandomExample.demonstrateRandomSecurity();
        SQLInjectionPrevention.demonstrateVulnerableSQL();
        demonstrateFileSecurity();
        analyzeSecurityPractices();
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("Security is a multi-layered approach that includes:");
        System.out.println("• Robust input validation and sanitization");
        System.out.println("• Secure authentication and session management");
        System.out.println("• Proper cryptographic implementations");
        System.out.println("• Protection against common vulnerabilities");
        System.out.println("• Regular security testing and updates");
        System.out.println("• Security-aware development practices");
        
        System.out.println("\nRemember: Security is not a feature, it's a requirement!");
        System.out.println("Always follow the principle of 'Security by Design'");
    }
}
