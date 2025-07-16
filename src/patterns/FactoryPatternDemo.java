package patterns;

/**
 * FactoryPatternDemo - Demonstrates the Factory Design Pattern
 * 
 * The Factory pattern provides an interface for creating objects
 * without exposing the instantiation logic to the client.
 * 
 * This demo covers:
 * - Simple Factory
 * - Factory Method Pattern
 * - Abstract Factory Pattern
 * - Real-world applications
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// Product interface
interface Vehicle {
    void start();
    void stop();
    void displayInfo();
    String getType();
}

// Concrete Products
class Car implements Vehicle {
    private String model;
    private int doors;
    
    public Car(String model, int doors) {
        this.model = model;
        this.doors = doors;
    }
    
    @Override
    public void start() {
        System.out.println("Car engine started with key ignition");
    }
    
    @Override
    public void stop() {
        System.out.println("Car engine stopped");
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Car - Model: " + model + ", Doors: " + doors);
    }
    
    @Override
    public String getType() {
        return "Car";
    }
}

class Motorcycle implements Vehicle {
    private String brand;
    private int engineCC;
    
    public Motorcycle(String brand, int engineCC) {
        this.brand = brand;
        this.engineCC = engineCC;
    }
    
    @Override
    public void start() {
        System.out.println("Motorcycle started with kick/electric start");
    }
    
    @Override
    public void stop() {
        System.out.println("Motorcycle engine stopped");
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Motorcycle - Brand: " + brand + ", Engine: " + engineCC + "cc");
    }
    
    @Override
    public String getType() {
        return "Motorcycle";
    }
}

class Truck implements Vehicle {
    private String model;
    private double loadCapacity;
    
    public Truck(String model, double loadCapacity) {
        this.model = model;
        this.loadCapacity = loadCapacity;
    }
    
    @Override
    public void start() {
        System.out.println("Truck diesel engine started");
    }
    
    @Override
    public void stop() {
        System.out.println("Truck engine stopped");
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Truck - Model: " + model + ", Load Capacity: " + loadCapacity + " tons");
    }
    
    @Override
    public String getType() {
        return "Truck";
    }
}

// 1. Simple Factory
class SimpleVehicleFactory {
    public static Vehicle createVehicle(String type) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car("Sedan", 4);
            case "motorcycle":
                return new Motorcycle("SportBike", 600);
            case "truck":
                return new Truck("Heavy Duty", 10.0);
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
    
    public static Vehicle createVehicle(String type, String... params) {
        switch (type.toLowerCase()) {
            case "car":
                String carModel = params.length > 0 ? params[0] : "Default";
                int doors = params.length > 1 ? Integer.parseInt(params[1]) : 4;
                return new Car(carModel, doors);
            case "motorcycle":
                String bikeBrand = params.length > 0 ? params[0] : "Default";
                int engineCC = params.length > 1 ? Integer.parseInt(params[1]) : 500;
                return new Motorcycle(bikeBrand, engineCC);
            case "truck":
                String truckModel = params.length > 0 ? params[0] : "Default";
                double capacity = params.length > 1 ? Double.parseDouble(params[1]) : 5.0;
                return new Truck(truckModel, capacity);
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}

// 2. Factory Method Pattern

// Abstract Creator
abstract class VehicleFactory {
    // Factory method - to be implemented by concrete factories
    public abstract Vehicle createVehicle();
    
    // Template method that uses the factory method
    public Vehicle orderVehicle() {
        Vehicle vehicle = createVehicle();
        
        // Common operations for all vehicles
        System.out.println("Processing order for: " + vehicle.getType());
        vehicle.displayInfo();
        System.out.println("Quality check passed");
        System.out.println("Vehicle ready for delivery");
        
        return vehicle;
    }
}

// Concrete Creators
class CarFactory extends VehicleFactory {
    private String model;
    private int doors;
    
    public CarFactory(String model, int doors) {
        this.model = model;
        this.doors = doors;
    }
    
    @Override
    public Vehicle createVehicle() {
        return new Car(model, doors);
    }
}

class MotorcycleFactory extends VehicleFactory {
    private String brand;
    private int engineCC;
    
    public MotorcycleFactory(String brand, int engineCC) {
        this.brand = brand;
        this.engineCC = engineCC;
    }
    
    @Override
    public Vehicle createVehicle() {
        return new Motorcycle(brand, engineCC);
    }
}

class TruckFactory extends VehicleFactory {
    private String model;
    private double loadCapacity;
    
    public TruckFactory(String model, double loadCapacity) {
        this.model = model;
        this.loadCapacity = loadCapacity;
    }
    
    @Override
    public Vehicle createVehicle() {
        return new Truck(model, loadCapacity);
    }
}

// 3. Abstract Factory Pattern

// Abstract products for different categories
interface Engine {
    void start();
    void stop();
    String getType();
}

interface Transmission {
    void shiftGear(int gear);
    String getType();
}

// Concrete products for Economy category
class EconomyEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Economy engine started - 4 cylinder, fuel efficient");
    }
    
    @Override
    public void stop() {
        System.out.println("Economy engine stopped");
    }
    
    @Override
    public String getType() {
        return "Economy Engine";
    }
}

class EconomyTransmission implements Transmission {
    @Override
    public void shiftGear(int gear) {
        System.out.println("Economy CVT transmission shifted to gear: " + gear);
    }
    
    @Override
    public String getType() {
        return "CVT Transmission";
    }
}

// Concrete products for Sports category
class SportsEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Sports engine started - V6 turbo, high performance");
    }
    
    @Override
    public void stop() {
        System.out.println("Sports engine stopped");
    }
    
    @Override
    public String getType() {
        return "Sports Engine";
    }
}

class SportsTransmission implements Transmission {
    @Override
    public void shiftGear(int gear) {
        System.out.println("Sports manual transmission shifted to gear: " + gear);
    }
    
    @Override
    public String getType() {
        return "Manual Transmission";
    }
}

// Concrete products for Luxury category
class LuxuryEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Luxury engine started - V8, smooth and powerful");
    }
    
    @Override
    public void stop() {
        System.out.println("Luxury engine stopped");
    }
    
    @Override
    public String getType() {
        return "Luxury Engine";
    }
}

class LuxuryTransmission implements Transmission {
    @Override
    public void shiftGear(int gear) {
        System.out.println("Luxury automatic transmission smoothly shifted to gear: " + gear);
    }
    
    @Override
    public String getType() {
        return "Automatic Transmission";
    }
}

// Abstract Factory
interface VehicleComponentFactory {
    Engine createEngine();
    Transmission createTransmission();
}

// Concrete Factories
class EconomyVehicleFactory implements VehicleComponentFactory {
    @Override
    public Engine createEngine() {
        return new EconomyEngine();
    }
    
    @Override
    public Transmission createTransmission() {
        return new EconomyTransmission();
    }
}

class SportsVehicleFactory implements VehicleComponentFactory {
    @Override
    public Engine createEngine() {
        return new SportsEngine();
    }
    
    @Override
    public Transmission createTransmission() {
        return new SportsTransmission();
    }
}

class LuxuryVehicleFactory implements VehicleComponentFactory {
    @Override
    public Engine createEngine() {
        return new LuxuryEngine();
    }
    
    @Override
    public Transmission createTransmission() {
        return new LuxuryTransmission();
    }
}

// Vehicle Configurator using Abstract Factory
class VehicleConfigurator {
    private Engine engine;
    private Transmission transmission;
    
    public VehicleConfigurator(VehicleComponentFactory factory) {
        this.engine = factory.createEngine();
        this.transmission = factory.createTransmission();
    }
    
    public void startVehicle() {
        System.out.println("Starting vehicle configuration:");
        System.out.println("Engine: " + engine.getType());
        System.out.println("Transmission: " + transmission.getType());
        engine.start();
        transmission.shiftGear(1);
    }
    
    public void stopVehicle() {
        transmission.shiftGear(0);
        engine.stop();
        System.out.println("Vehicle stopped");
    }
}

public class FactoryPatternDemo {
    
    /**
     * Demonstrates Simple Factory pattern
     */
    public static void demonstrateSimpleFactory() {
        System.out.println("=== SIMPLE FACTORY PATTERN ===");
        
        // Create vehicles using simple factory
        Vehicle car = SimpleVehicleFactory.createVehicle("car");
        Vehicle motorcycle = SimpleVehicleFactory.createVehicle("motorcycle");
        Vehicle truck = SimpleVehicleFactory.createVehicle("truck");
        
        // Test vehicles
        Vehicle[] vehicles = {car, motorcycle, truck};
        for (Vehicle vehicle : vehicles) {
            vehicle.displayInfo();
            vehicle.start();
            vehicle.stop();
            System.out.println();
        }
        
        // Create vehicles with custom parameters
        System.out.println("Custom vehicles:");
        Vehicle customCar = SimpleVehicleFactory.createVehicle("car", "BMW X5", "5");
        Vehicle customBike = SimpleVehicleFactory.createVehicle("motorcycle", "Harley Davidson", "1200");
        
        customCar.displayInfo();
        customBike.displayInfo();
    }
    
    /**
     * Demonstrates Factory Method pattern
     */
    public static void demonstrateFactoryMethod() {
        System.out.println("\n=== FACTORY METHOD PATTERN ===");
        
        // Create different factories
        VehicleFactory carFactory = new CarFactory("Tesla Model 3", 4);
        VehicleFactory motorcycleFactory = new MotorcycleFactory("Kawasaki Ninja", 650);
        VehicleFactory truckFactory = new TruckFactory("Volvo FH", 25.0);
        
        // Order vehicles through factories
        System.out.println("Ordering vehicles through factories:");
        
        Vehicle orderedCar = carFactory.orderVehicle();
        System.out.println();
        
        Vehicle orderedMotorcycle = motorcycleFactory.orderVehicle();
        System.out.println();
        
        Vehicle orderedTruck = truckFactory.orderVehicle();
        System.out.println();
        
        // Test the ordered vehicles
        System.out.println("Testing ordered vehicles:");
        orderedCar.start();
        orderedMotorcycle.start();
        orderedTruck.start();
    }
    
    /**
     * Demonstrates Abstract Factory pattern
     */
    public static void demonstrateAbstractFactory() {
        System.out.println("\n=== ABSTRACT FACTORY PATTERN ===");
        
        // Create different vehicle configurations
        VehicleComponentFactory economyFactory = new EconomyVehicleFactory();
        VehicleComponentFactory sportsFactory = new SportsVehicleFactory();
        VehicleComponentFactory luxuryFactory = new LuxuryVehicleFactory();
        
        // Configure vehicles using different factories
        System.out.println("1. Economy Vehicle Configuration:");
        VehicleConfigurator economyVehicle = new VehicleConfigurator(economyFactory);
        economyVehicle.startVehicle();
        economyVehicle.stopVehicle();
        
        System.out.println("\n2. Sports Vehicle Configuration:");
        VehicleConfigurator sportsVehicle = new VehicleConfigurator(sportsFactory);
        sportsVehicle.startVehicle();
        sportsVehicle.stopVehicle();
        
        System.out.println("\n3. Luxury Vehicle Configuration:");
        VehicleConfigurator luxuryVehicle = new VehicleConfigurator(luxuryFactory);
        luxuryVehicle.startVehicle();
        luxuryVehicle.stopVehicle();
    }
    
    /**
     * Demonstrates real-world factory usage scenarios
     */
    public static void demonstrateRealWorldScenarios() {
        System.out.println("\n=== REAL-WORLD SCENARIOS ===");
        
        // Scenario 1: Vehicle rental system
        System.out.println("Scenario 1: Vehicle Rental System");
        String[] customerRequests = {"car", "motorcycle", "truck", "car"};
        
        for (int i = 0; i < customerRequests.length; i++) {
            System.out.println("\nCustomer " + (i + 1) + " requested: " + customerRequests[i]);
            try {
                Vehicle rentalVehicle = SimpleVehicleFactory.createVehicle(customerRequests[i]);
                System.out.println("Assigned vehicle:");
                rentalVehicle.displayInfo();
                System.out.println("Vehicle ready for rental");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        // Scenario 2: Vehicle manufacturing line
        System.out.println("\n\nScenario 2: Manufacturing Line");
        VehicleFactory[] productionLine = {
            new CarFactory("Honda Civic", 4),
            new CarFactory("Toyota Camry", 4),
            new MotorcycleFactory("Yamaha R1", 1000),
            new TruckFactory("Mercedes Actros", 40.0)
        };
        
        System.out.println("Daily production:");
        for (int i = 0; i < productionLine.length; i++) {
            System.out.println("\nProduction slot " + (i + 1) + ":");
            productionLine[i].orderVehicle();
        }
    }
    
    /**
     * Analyzes factory pattern characteristics
     */
    public static void analyzeFactoryPatterns() {
        System.out.println("\n=== FACTORY PATTERN ANALYSIS ===");
        
        System.out.println("1. Simple Factory:");
        System.out.println("   Pros: Easy to implement, centralizes object creation");
        System.out.println("   Cons: Violates Open/Closed Principle, not a true design pattern");
        System.out.println("   Use when: Simple object creation with few types");
        
        System.out.println("\n2. Factory Method:");
        System.out.println("   Pros: Follows Open/Closed Principle, flexible and extensible");
        System.out.println("   Cons: Can create complex class hierarchies");
        System.out.println("   Use when: Need to delegate object creation to subclasses");
        
        System.out.println("\n3. Abstract Factory:");
        System.out.println("   Pros: Creates families of related objects, ensures consistency");
        System.out.println("   Cons: Complex to implement, difficult to extend");
        System.out.println("   Use when: Need to create families of related objects");
        
        System.out.println("\nCommon Benefits:");
        System.out.println("• Loose coupling between client and concrete classes");
        System.out.println("• Centralizes object creation logic");
        System.out.println("• Easier to maintain and extend");
        System.out.println("• Supports polymorphism");
        
        System.out.println("\nReal-world Applications:");
        System.out.println("• GUI framework components");
        System.out.println("• Database connection creation");
        System.out.println("• Parser creation based on file type");
        System.out.println("• Game object creation");
        System.out.println("• Plugin architecture");
    }
    
    /**
     * Main method demonstrating the Factory patterns
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Factory Design Pattern Demonstration");
        System.out.println("===================================");
        
        demonstrateSimpleFactory();
        demonstrateFactoryMethod();
        demonstrateAbstractFactory();
        demonstrateRealWorldScenarios();
        analyzeFactoryPatterns();
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("Factory patterns provide flexible ways to create objects:");
        System.out.println("• Simple Factory: Basic creation with static methods");
        System.out.println("• Factory Method: Delegates creation to subclasses");
        System.out.println("• Abstract Factory: Creates families of related objects");
        System.out.println("Choose based on complexity and extensibility requirements.");
    }
}
