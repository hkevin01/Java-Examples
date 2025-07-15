package src.oop;

/**
 * PolymorphismDemo - Demonstrates different types of polymorphism in Java
 * 
 * This package demonstrates:
 * 1. Method Overloading (Compile-time polymorphism)
 * 2. Method Overriding (Runtime polymorphism)
 * 3. Interface implementation
 * 4. Abstract class usage
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// Abstract base class demonstrating inheritance and abstract methods
abstract class Animal {
    protected String name;
    protected int age;
    
    // Constructor
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Concrete method - can be inherited or overridden
    public void sleep() {
        System.out.println(name + " is sleeping...");
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract void makeSound();
    
    // Abstract method for movement
    public abstract void move();
    
    // Method that can be overridden
    public void displayInfo() {
        System.out.println("Animal: " + name + ", Age: " + age);
    }
}

// Interface demonstrating contract for trainable animals
interface Trainable {
    void performTrick();
    boolean isTrainable();
}

// Interface for animals that can be pets
interface Pet {
    void showAffection();
    String getOwner();
    void setOwner(String owner);
}

// Concrete class implementing multiple interfaces
class Dog extends Animal implements Trainable, Pet {
    private String breed;
    private String owner;
    
    // Constructor
    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }
    
    // Method overriding - Runtime polymorphism
    @Override
    public void makeSound() {
        System.out.println(name + " says: Woof! Woof!");
    }
    
    @Override
    public void move() {
        System.out.println(name + " is running on four legs");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo(); // Call parent method
        System.out.println("Breed: " + breed);
        if (owner != null) {
            System.out.println("Owner: " + owner);
        }
    }
    
    // Interface implementation
    @Override
    public void performTrick() {
        System.out.println(name + " sits, rolls over, and fetches!");
    }
    
    @Override
    public boolean isTrainable() {
        return true;
    }
    
    @Override
    public void showAffection() {
        System.out.println(name + " wags tail and licks your face!");
    }
    
    @Override
    public String getOwner() {
        return owner;
    }
    
    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    // Method overloading - Compile-time polymorphism
    public void bark() {
        System.out.println(name + " barks normally");
    }
    
    public void bark(int intensity) {
        if (intensity <= 3) {
            System.out.println(name + " barks softly: woof");
        } else if (intensity <= 7) {
            System.out.println(name + " barks loudly: WOOF!");
        } else {
            System.out.println(name + " barks aggressively: WOOF! WOOF! WOOF!");
        }
    }
    
    public void bark(String reason) {
        System.out.println(name + " barks because: " + reason);
    }
}

// Another concrete class
class Cat extends Animal implements Pet {
    private boolean isIndoor;
    private String owner;
    
    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " says: Meow! Purr...");
    }
    
    @Override
    public void move() {
        System.out.println(name + " moves gracefully and silently");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Indoor cat: " + isIndoor);
        if (owner != null) {
            System.out.println("Owner: " + owner);
        }
    }
    
    @Override
    public void showAffection() {
        System.out.println(name + " purrs and rubs against you");
    }
    
    @Override
    public String getOwner() {
        return owner;
    }
    
    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    // Method overloading
    public void meow() {
        System.out.println(name + " meows normally");
    }
    
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

// Bird class that doesn't implement Pet interface
class Bird extends Animal {
    private boolean canFly;
    
    public Bird(String name, int age, boolean canFly) {
        super(name, age);
        this.canFly = canFly;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " chirps and sings!");
    }
    
    @Override
    public void move() {
        if (canFly) {
            System.out.println(name + " flies through the air");
        } else {
            System.out.println(name + " hops on the ground");
        }
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Can fly: " + canFly);
    }
}

// Main class demonstrating polymorphism
public class PolymorphismDemo {
    
    /**
     * Demonstrates runtime polymorphism using method overriding
     * @param animal any Animal object
     */
    public static void demonstrateRuntimePolymorphism(Animal animal) {
        System.out.println("\n--- Runtime Polymorphism ---");
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
