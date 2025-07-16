package src.oop;

/**
 * PolymorphismDemo - Demonstrates different types of polymorphism in Java
 * 
 * WHAT IS POLYMORPHISM?
 * Polymorphism (from Greek: "many forms") is the ability of objects of different types
 * to be treated as objects of a common base type, while maintaining their own specific behavior.
 * 
 * WHY POLYMORPHISM MATTERS:
 * - Code Reusability: Write code once, use with many different types
 * - Extensibility: Add new types without modifying existing code
 * - Maintainability: Changes to specific implementations don't affect client code
 * - Flexibility: Runtime behavior can vary based on actual object type
 * - Design Patterns: Enables powerful patterns like Strategy, Observer, Factory
 * 
 * TYPES OF POLYMORPHISM IN JAVA:
 * 
 * 1. COMPILE-TIME POLYMORPHISM (Static Polymorphism):
 *    - Method Overloading: Same method name, different parameters
 *    - Operator Overloading: Built-in (e.g., + for strings and numbers)
 *    - Resolution happens at compile time based on method signatures
 * 
 * 2. RUNTIME POLYMORPHISM (Dynamic Polymorphism):
 *    - Method Overriding: Subclass provides specific implementation of parent method
 *    - Interface Implementation: Classes implement interface methods differently
 *    - Resolution happens at runtime based on actual object type (late binding)
 * 
 * KEY CONCEPTS DEMONSTRATED:
 * - Abstract classes: Provide common interface with some implementation
 * - Interfaces: Define contracts that implementing classes must fulfill
 * - Method overriding: Subclasses provide specific implementations
 * - Method overloading: Multiple methods with same name, different signatures
 * - Dynamic method dispatch: JVM calls the correct method at runtime
 * 
 * This package demonstrates:
 * 1. Method Overloading (Compile-time polymorphism)
 * 2. Method Overriding (Runtime polymorphism)
 * 3. Interface implementation (Contract-based polymorphism)
 * 4. Abstract class usage (Template-based polymorphism)
 * 
 * REAL-WORLD APPLICATIONS:
 * - GUI frameworks: Different components handle events differently
 * - Database drivers: Same interface, different database implementations
 * - Payment systems: Various payment methods with common interface
 * - Graphics systems: Different shapes with common drawing interface
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// Abstract base class demonstrating inheritance and abstract methods
// DESIGN DECISION: Abstract class vs Interface
// - Use abstract class when you have common implementation to share
// - Use interface when you only want to define a contract

/**
 * Animal - Abstract base class demonstrating template method pattern
 * 
 * ABSTRACT CLASS BENEFITS:
 * - Provides common fields and methods for all animals
 * - Enforces implementation of specific behaviors (abstract methods)
 * - Allows code reuse while ensuring consistency
 * - Represents "is-a" relationship clearly
 * 
 * WHY ABSTRACT:
 * - An "Animal" by itself is too generic to instantiate
 * - Forces subclasses to define species-specific behaviors
 * - Provides framework that all animals must follow
 */
abstract class Animal {
    // Protected fields: accessible to subclasses but not external classes
    // This balances encapsulation with inheritance needs
    protected String name;
    protected int age;
    
    /**
     * Constructor ensures all animals have basic attributes
     * Abstract classes can have constructors (unlike interfaces)
     * 
     * @param name the animal's name
     * @param age the animal's age in years
     */
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    /**
     * Concrete method - provides default behavior that can be inherited or overridden
     * This demonstrates code reuse: all animals sleep similarly
     */
    public void sleep() {
        System.out.println(name + " is sleeping peacefully...");
    }
    
    /**
     * Abstract method - forces subclasses to provide specific implementation
     * Each animal species makes different sounds, so this cannot have a default implementation
     */
    public abstract void makeSound();
    
    /**
     * Abstract method for movement - each animal moves differently
     * Dogs run, birds fly, fish swim - this enforces specific implementations
     */
    public abstract void move();
    
    /**
     * Method that can be overridden to provide more specific information
     * Default implementation provides basic info, subclasses can enhance it
     */
    public void displayInfo() {
        System.out.printf("Animal: %s, Age: %d years%n", name, age);
    }
}

/**
 * Trainable - Interface demonstrating contract for trainable animals
 * 
 * INTERFACE DESIGN PRINCIPLES:
 * - Single Responsibility: Focused only on training-related capabilities
 * - Interface Segregation: Animals that can't be trained don't need this interface
 * - Dependency Inversion: Code depends on abstraction, not concrete classes
 * 
 * WHY SEPARATE INTERFACE:
 * - Not all animals can be trained (e.g., wild animals)
 * - Allows composition of capabilities (an animal can be trainable AND a pet)
 * - Enables multiple inheritance of contracts
 * 
 * REAL-WORLD APPLICATION:
 * - Service animals must implement training interfaces
 * - Entertainment animals (circus) need specialized training contracts
 * - Research animals may need specific behavioral contracts
 */
interface Trainable {
    /**
     * Contract method: All trainable animals must be able to perform tricks
     * Implementation varies by species and training level
     */
    void performTrick();
    
    /**
     * Contract method: Determine if the animal can be trained
     * Some species or individual animals may not be trainable
     * 
     * @return true if the animal can learn new behaviors
     */
    boolean isTrainable();
}

/**
 * Pet - Interface for animals that can be companions
 * 
 * INTERFACE COMPOSITION:
 * - Animals can implement multiple interfaces (Trainable AND Pet)
 * - Each interface represents a different aspect or capability
 * - Allows flexible design: not all pets are trainable, not all trainable animals are pets
 * 
 * BUSINESS LOGIC EXAMPLE:
 * - Pet stores need to track ownership
 * - Veterinary systems need owner contact information
 * - Animal shelters track adoption status
 */
interface Pet {
    /**
     * Contract method: All pets should be able to show affection
     * Implementation varies by species (dogs wag tails, cats purr, birds chirp)
     */
    void showAffection();
    
    /**
     * Contract method: Get the current owner of the pet
     * Essential for legal and medical record keeping
     * 
     * @return the name of the current owner, or null if no owner
     */
    String getOwner();
    
    /**
     * Contract method: Set or change the owner of the pet
     * Used during adoption, sale, or ownership transfer
     * 
     * @param owner the name of the new owner
     */
    void setOwner(String owner);
}

/**
 * Dog - Concrete class demonstrating multiple interface implementation
 * 
 * MULTIPLE INHERITANCE OF CONTRACTS:
 * - Inherits from Animal (is-a relationship)
 * - Implements Trainable (can-do relationship)
 * - Implements Pet (role relationship)
 * 
 * POLYMORPHIC VERSATILITY:
 * - Can be referenced as Animal, Trainable, Pet, or Dog
 * - Each reference type exposes different methods
 * - Runtime behavior remains consistent regardless of reference type
 * 
 * DESIGN PATTERNS DEMONSTRATED:
 * - Strategy: Different dogs can implement behaviors differently
 * - Adapter: Dog adapts Animal interface to specific dog behaviors
 * - Composite: Dog composes multiple interfaces into single class
 * 
 * REAL-WORLD MODELING:
 * - Represents actual dog characteristics and capabilities
 * - Models relationships between inheritance and composition
 * - Shows how objects can fulfill multiple roles simultaneously
 */
class Dog extends Animal implements Trainable, Pet {
    private String breed; // Additional field specific to dogs
    private String owner; // Pet ownership tracking
    
    /**
     * Constructor initializes dog with breed information
     * Calls parent constructor to handle common animal attributes
     * 
     * CONSTRUCTOR CHAINING: super() must be first statement
     * 
     * @param name the dog's name
     * @param age the dog's age in years  
     * @param breed the dog's breed (e.g., "Golden Retriever", "German Shepherd")
     */
    public Dog(String name, int age, String breed) {
        super(name, age); // Initialize Animal portion
        this.breed = breed;
        // owner is null until set - represents shelter/stray dogs
    }
    
    /**
     * RUNTIME POLYMORPHISM: Override abstract method from Animal
     * 
     * DYNAMIC METHOD DISPATCH:
     * - When called on Animal reference, this method executes
     * - JVM determines correct method at runtime based on actual object type
     * - Enables "program to interfaces, not implementations" principle
     * 
     * METHOD OVERRIDING RULES:
     * - Same method signature as parent
     * - Cannot reduce visibility (protected -> private not allowed)
     * - Can throw fewer checked exceptions, not more
     * - Return type must be same or subtype (covariant return types)
     */
    @Override
    public void makeSound() {
        System.out.println(name + " says: Woof! Woof!");
    }
    
    /**
     * RUNTIME POLYMORPHISM: Override abstract method from Animal
     * Each animal species has unique movement patterns
     */
    @Override
    public void move() {
        System.out.println(name + " is running on four legs");
    }
    
    /**
     * METHOD OVERRIDING WITH ENHANCEMENT: Template Method Pattern
     * - Calls parent implementation first (preserves base behavior)
     * - Adds dog-specific information
     * - Maintains LSP (Liskov Substitution Principle)
     * 
     * LISKOV SUBSTITUTION PRINCIPLE:
     * - Dog objects can replace Animal objects without breaking functionality
     * - Behavior is enhanced, not fundamentally changed
     * - Clients using Animal reference get expected behavior plus extras
     */
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call parent method - preserves contract
        System.out.println("Breed: " + breed);
        if (owner != null) {
            System.out.println("Owner: " + owner);
        } else {
            System.out.println("Status: Available for adoption");
        }
    }
    
    /**
     * INTERFACE IMPLEMENTATION: Trainable contract
     * 
     * STRATEGY PATTERN: Different dog breeds might implement training differently
     * - Working dogs: complex multi-step commands
     * - Toy breeds: simple tricks
     * - Guard dogs: protection-focused training
     */
    @Override
    public void performTrick() {
        System.out.println(name + " sits, rolls over, and fetches!");
    }
    
    /**
     * INTERFACE IMPLEMENTATION: Trainable contract
     * Dogs are generally highly trainable compared to other animals
     * 
     * BUSINESS LOGIC: Used by training facilities to filter animals
     */
    @Override
    public boolean isTrainable() {
        return true; // Most dogs are trainable with proper methods
    }
    
    /**
     * INTERFACE IMPLEMENTATION: Pet contract
     * Dogs show affection through species-specific behaviors
     * 
     * POLYMORPHIC BEHAVIOR: Called when object is referenced as Pet
     */
    @Override
    public void showAffection() {
        System.out.println(name + " wags tail and licks your face!");
    }
    
    /**
     * INTERFACE IMPLEMENTATION: Pet contract - Getter
     * Encapsulation: Controlled access to private field
     * 
     * @return current owner name, or null if no owner
     */
    @Override
    public String getOwner() {
        return owner;
    }
    
    /**
     * INTERFACE IMPLEMENTATION: Pet contract - Setter
     * Encapsulation: Controlled modification of private field
     * 
     * BUSINESS RULE: Could add validation (owner registration, etc.)
     * 
     * @param owner the new owner's name
     */
    @Override
    public void setOwner(String owner) {
        this.owner = owner;
        if (owner != null) {
            System.out.println(name + " has been adopted by " + owner + "!");
        }
    }
    
    /**
     * METHOD OVERLOADING: Compile-time polymorphism demonstration
     * Multiple methods with same name but different parameter signatures
     * 
     * COMPILE-TIME RESOLUTION:
     * - Compiler determines which method to call based on arguments
     * - No runtime overhead for method selection
     * - Method signature includes: name + parameter types + parameter order
     * 
     * DESIGN BENEFIT: Natural API - same action, different contexts
     * Real-world example: System.out.println() is heavily overloaded
     */
    
    /**
     * Basic bark - no parameters
     * Default behavior when no specific context is provided
     */
    public void bark() {
        System.out.println(name + " barks normally");
    }
    
    /**
     * Intensity-based bark - int parameter
     * Demonstrates method overloading with different parameter types
     * 
     * @param intensity bark intensity from 1-10
     */
    public void bark(int intensity) {
        if (intensity <= 3) {
            System.out.println(name + " barks softly: woof");
        } else if (intensity <= 7) {
            System.out.println(name + " barks loudly: WOOF!");
        } else {
            System.out.println(name + " barks aggressively: WOOF! WOOF! WOOF!");
        }
    }
    
    /**
     * Contextual bark - String parameter
     * Demonstrates method overloading with different parameter types
     * 
     * @param reason why the dog is barking
     */
    public void bark(String reason) {
        System.out.println(name + " barks because: " + reason);
    }
}

/**
 * Cat - Another concrete class demonstrating different polymorphic behavior
 * 
 * BEHAVIORAL POLYMORPHISM:
 * - Same interface as Dog (both extend Animal, implement Pet)
 * - Completely different implementations of shared methods
 * - Demonstrates how polymorphism enables diverse behaviors under common interface
 * 
 * DESIGN COMPARISON WITH DOG:
 * - Different personality traits (independent vs loyal)
 * - Different movement patterns (graceful vs energetic)  
 * - Different affection display (purring vs tail wagging)
 * - Shows polymorphism isn't just technical - it models real-world diversity
 */
class Cat extends Animal implements Pet {
    private boolean isIndoor; // Cat-specific attribute
    private String owner;     // Pet interface requirement
    
    /**
     * Constructor for Cat with indoor/outdoor specification
     * Different from Dog constructor - shows how subclasses can have unique attributes
     * 
     * @param name the cat's name
     * @param age the cat's age in years
     * @param isIndoor true if indoor cat, false if outdoor cat
     */
    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }
    
    /**
     * RUNTIME POLYMORPHISM: Cat-specific sound implementation
     * Same method signature as Dog.makeSound(), completely different behavior
     * 
     * POLYMORPHIC PRINCIPLE: Common interface, species-specific implementation
     */
    @Override
    public void makeSound() {
        System.out.println(name + " says: Meow! Purr...");
    }
    
    /**
     * RUNTIME POLYMORPHISM: Cat-specific movement implementation
     * Demonstrates how different animals can implement same abstract method differently
     */
    @Override
    public void move() {
        if (isIndoor) {
            System.out.println(name + " moves gracefully around the house");
        } else {
            System.out.println(name + " prowls silently through the neighborhood");
        }
    }
    
    /**
     * METHOD OVERRIDING: Enhanced display with cat-specific information
     * Follows same pattern as Dog but shows different attributes
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Indoor cat: " + (isIndoor ? "Yes" : "No - Outdoor cat"));
        if (owner != null) {
            System.out.println("Owner: " + owner);
        } else {
            System.out.println("Status: " + (isIndoor ? "House cat seeking home" : "Feral/Community cat"));
        }
    }
    
    /**
     * INTERFACE IMPLEMENTATION: Cat-specific affection display
     * Same contract as Dog.showAffection(), different behavior
     * 
     * POLYMORPHIC BEHAVIOR: Client code can call showAffection() on any Pet
     * without knowing whether it's a Dog or Cat
     */
    @Override
    public void showAffection() {
        System.out.println(name + " purrs loudly and rubs against your leg");
    }
    
    /**
     * INTERFACE IMPLEMENTATION: Getter for owner
     * Identical implementation to Dog - shows interface consistency
     */
    @Override
    public String getOwner() {
        return owner;
    }
    
    /**
     * INTERFACE IMPLEMENTATION: Setter for owner
     * Similar to Dog but with cat-specific behavior message
     */
    @Override
    public void setOwner(String owner) {
        this.owner = owner;
        if (owner != null) {
            System.out.println(name + " has found a new home with " + owner + "!");
        }
    }
    
    /**
     * Cat-specific method: Demonstrates class-specific capabilities
     * Only available when reference type is Cat (not Animal or Pet)
     * 
     * ENCAPSULATION: Uses private isIndoor field to determine behavior
     */
    public void hunt() {
        if (isIndoor) {
            System.out.println(name + " stalks a toy mouse around the house");
        } else {
            System.out.println(name + " hunts real prey in the wild");
        }
    }
    
    /**
     * METHOD OVERLOADING: Cat-specific communication methods
     * Demonstrates compile-time polymorphism specific to Cat class
     */
    
    /**
     * Basic meow - default cat communication
     */
    public void meow() {
        System.out.println(name + " meows normally");
    }
    
    /**
     * Mood-based meow - demonstrates method overloading with String parameter
     * Shows how cats communicate differently based on their emotional state
     * 
     * @param mood the cat's current emotional state
     */
    public void meow(String mood) {
        switch (mood.toLowerCase()) {
            case "happy":
                System.out.println(name + " meows happily: Purr meow!");
                break;
            case "hungry":
                System.out.println(name + " meows demandingly: MEOW! MEOW!");
                break;
            case "sleepy":
                System.out.println(name + " meows softly: mew...");
                break;
            default:
                System.out.println(name + " meows: Meow!");
        }
    }
}

/**
 * Bird - Demonstrates selective interface implementation
 * 
 * DESIGN DECISION: Birds don't implement Pet interface
 * - Not all animals make suitable pets
 * - Shows how polymorphism allows selective capabilities
 * - Wild birds vs pet birds would be different classes in real system
 * 
 * INHERITANCE WITHOUT ALL INTERFACES:
 * - Still inherits from Animal (is-a relationship)
 * - Doesn't implement Pet or Trainable (capability-based exclusion)
 * - Shows flexibility of polymorphic design
 */
class Bird extends Animal {
    private boolean canFly; // Not all birds can fly (penguins, ostriches)
    
    /**
     * Constructor for Bird with flight capability
     * Different attribute focus than mammals - emphasizes flight
     * 
     * @param name the bird's name
     * @param age the bird's age in years
     * @param canFly true if the bird can fly, false otherwise
     */
    public Bird(String name, int age, boolean canFly) {
        super(name, age);
        this.canFly = canFly;
    }
    
    /**
     * RUNTIME POLYMORPHISM: Bird-specific sound implementation
     * Different from mammals - demonstrates diverse animal kingdom sounds
     */
    @Override
    public void makeSound() {
        System.out.println(name + " chirps and sings melodiously!");
    }
    
    /**
     * RUNTIME POLYMORPHISM: Flight-based movement implementation
     * Demonstrates conditional behavior based on capabilities
     * 
     * REAL-WORLD MODELING: Some birds can't fly (penguins, ostriches, kiwis)
     */
    @Override
    public void move() {
        if (canFly) {
            System.out.println(name + " soars gracefully through the air");
        } else {
            System.out.println(name + " hops and walks on the ground");
        }
    }
    
    /**
     * METHOD OVERRIDING: Bird-specific information display
     * Shows flight capability - key differentiator for birds
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Flight capable: " + (canFly ? "Yes" : "No"));
        System.out.println("Type: " + (canFly ? "Flying bird" : "Flightless bird"));
    }
    
    /**
     * Bird-specific method: Demonstrates class-specific capabilities
     * Only available when reference type is Bird
     * 
     * POLYMORPHIC LIMITATION: Cannot be called on Animal reference
     */
    public void sing() {
        if (canFly) {
            System.out.println(name + " sings beautiful songs from the treetops");
        } else {
            System.out.println(name + " makes ground-level vocalizations");
        }
    }
}

/**
 * PolymorphismDemo - Main demonstration class
 * 
 * DEMONSTRATION STRUCTURE:
 * 1. Runtime polymorphism (method overriding)
 * 2. Compile-time polymorphism (method overloading)
 * 3. Interface polymorphism (multiple interfaces)
 * 4. Reference type vs object type behavior
 * 5. Practical polymorphic design patterns
 * 
 * LEARNING OBJECTIVES:
 * - Understand different types of polymorphism
 * - See how polymorphism enables flexible design
 * - Learn when to use interfaces vs inheritance
 * - Observe real-world modeling with polymorphic principles
 */
public class PolymorphismDemo {
    
    /**
     * Demonstrates runtime polymorphism using method overriding
     * 
     * KEY CONCEPT: Same method call, different behavior based on object type
     * The JVM determines which method to call at runtime (dynamic dispatch)
     * 
     * @param animal any Animal object (Dog, Cat, Bird, etc.)
     */
    public static void demonstrateRuntimePolymorphism(Animal animal) {
        System.out.println("\n=== RUNTIME POLYMORPHISM DEMONSTRATION ===");
        System.out.println("Object type: " + animal.getClass().getSimpleName());
        System.out.println("Reference type: Animal");
        System.out.println();
        
        // Same method calls, different behaviors based on actual object type
        System.out.println("1. Displaying information:");
        animal.displayInfo();           // Calls overridden method
        
        System.out.println("\n2. Making sound:");
        animal.makeSound();             // Calls overridden method
        
        System.out.println("\n3. Movement:");
        animal.move();                  // Calls overridden method
        
        System.out.println("\n4. Common behavior:");
        animal.sleep();                 // Calls inherited method
        
        System.out.println("\nEXPLANATION:");
        System.out.println("- Same Animal reference type used for all calls");
        System.out.println("- JVM selects correct method based on actual object type");
        System.out.println("- This is 'late binding' or 'dynamic method dispatch'");
        System.out.println("- Enables writing generic code that works with specific types");
    }
        animal.displayInfo();
        animal.makeSound();
        animal.move();
        animal.sleep();
    }
    
    /**
     * Demonstrates interface polymorphism
     * @param pet any object implementing Pet interface
     */
    public static void demonstrateInterfacePolymorphism(Pet pet) {
        System.out.println("\n--- Interface Polymorphism ---");
        pet.showAffection();
        System.out.println("Pet owner: " + pet.getOwner());
    }
    
    /**
     * Demonstrates compile-time polymorphism (method overloading)
     * @param dog Dog object to demonstrate overloaded methods
     */
    public static void demonstrateCompileTimePolymorphism(Dog dog) {
        System.out.println("\n--- Compile-time Polymorphism (Method Overloading) ---");
        dog.bark();                    // No parameters
        dog.bark(5);                   // Integer parameter
        dog.bark("stranger at door");   // String parameter
    }
    
    /**
     * Main method demonstrating all types of polymorphism
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Java Polymorphism Demonstration");
        System.out.println("================================");
        
        // Create different animal objects
        Dog dog = new Dog("Buddy", 3, "Golden Retriever");
        Cat cat = new Cat("Whiskers", 2, true);
        Bird bird = new Bird("Tweety", 1, true);
        
        // Set owners for pets
        dog.setOwner("Alice");
        cat.setOwner("Bob");
        
        // Array of animals demonstrating polymorphism
        Animal[] animals = {dog, cat, bird};
        
        System.out.println("\n=== RUNTIME POLYMORPHISM DEMONSTRATION ===");
        // Runtime polymorphism - same method call, different behavior
        for (Animal animal : animals) {
            demonstrateRuntimePolymorphism(animal);
        }
        
        System.out.println("\n=== INTERFACE POLYMORPHISM DEMONSTRATION ===");
        // Interface polymorphism - only works with objects implementing Pet
        Pet[] pets = {dog, cat}; // Note: bird is not a Pet
        for (Pet pet : pets) {
            demonstrateInterfacePolymorphism(pet);
        }
        
        System.out.println("\n=== COMPILE-TIME POLYMORPHISM DEMONSTRATION ===");
        // Method overloading demonstration
        demonstrateCompileTimePolymorphism(dog);
        
        System.out.println("\n--- Cat Method Overloading ---");
        cat.meow();
        cat.meow("happy");
        cat.meow("hungry");
        cat.meow("sleepy");
        
        System.out.println("\n=== TRAINABLE INTERFACE DEMONSTRATION ===");
        // Check if animals are trainable
        if (dog instanceof Trainable) {
            Trainable trainableDog = dog; // Upcasting
            System.out.println("Is " + dog.name + " trainable? " + trainableDog.isTrainable());
            trainableDog.performTrick();
        }
        
        System.out.println("\n=== INSTANCEOF OPERATOR DEMONSTRATION ===");
        // Demonstrate instanceof operator
        for (Animal animal : animals) {
            System.out.println(animal.name + " is an Animal: " + (animal instanceof Animal));
            System.out.println(animal.name + " is a Pet: " + (animal instanceof Pet));
            System.out.println(animal.name + " is Trainable: " + (animal instanceof Trainable));
            System.out.println("---");
        }
    }
}
