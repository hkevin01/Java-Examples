package patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ObserverPatternDemo - Demonstrates the Observer Design Pattern
 * 
 * The Observer pattern defines a one-to-many dependency between objects
 * so that when one object changes state, all dependents are notified automatically.
 * 
 * This demo covers:
 * - Classic Observer Pattern
 * - Event-driven programming
 * - Real-world applications (Stock market, News subscription, Weather station)
 * - Java's built-in Observer support
 * 
 * @author Java Examples Project
 * @version 1.0
 */

// Generic Observer interface
interface Observer<T> {
    void update(T data);
    String getObserverName();
}

// Generic Subject interface
interface Subject<T> {
    void attach(Observer<T> observer);
    void detach(Observer<T> observer);
    void notifyObservers();
}

// Stock Market Example

class Stock {
    private String symbol;
    private double price;
    private double change;
    private String company;
    
    public Stock(String symbol, String company, double price) {
        this.symbol = symbol;
        this.company = company;
        this.price = price;
        this.change = 0.0;
    }
    
    // Getters
    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
    public double getChange() { return change; }
    public String getCompany() { return company; }
    
    public void setPrice(double newPrice) {
        this.change = newPrice - this.price;
        this.price = newPrice;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s): $%.2f (%.2f%%)", 
            company, symbol, price, (change/price)*100);
    }
}

// Concrete Subject
class StockMarket implements Subject<Stock> {
    private List<Observer<Stock>> observers;
    private Map<String, Stock> stocks;
    
    public StockMarket() {
        this.observers = new ArrayList<>();
        this.stocks = new HashMap<>();
    }
    
    public void addStock(Stock stock) {
        stocks.put(stock.getSymbol(), stock);
    }
    
    public void updateStockPrice(String symbol, double newPrice) {
        Stock stock = stocks.get(symbol);
        if (stock != null) {
            System.out.println("\n📈 Stock Update: " + symbol + " price changing to $" + newPrice);
            stock.setPrice(newPrice);
            notifyObservers(stock);
        }
    }
    
    @Override
    public void attach(Observer<Stock> observer) {
        observers.add(observer);
        System.out.println("✅ " + observer.getObserverName() + " subscribed to stock updates");
    }
    
    @Override
    public void detach(Observer<Stock> observer) {
        observers.remove(observer);
        System.out.println("❌ " + observer.getObserverName() + " unsubscribed from stock updates");
    }
    
    @Override
    public void notifyObservers() {
        // This version notifies with all stocks
        for (Observer<Stock> observer : observers) {
            for (Stock stock : stocks.values()) {
                observer.update(stock);
            }
        }
    }
    
    public void notifyObservers(Stock updatedStock) {
        for (Observer<Stock> observer : observers) {
            observer.update(updatedStock);
        }
    }
    
    public List<Stock> getAllStocks() {
        return new ArrayList<>(stocks.values());
    }
}

// Concrete Observers
class Investor implements Observer<Stock> {
    private String name;
    private Map<String, Integer> portfolio;
    
    public Investor(String name) {
        this.name = name;
        this.portfolio = new HashMap<>();
    }
    
    public void buyStock(String symbol, int shares) {
        portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + shares);
        System.out.println(name + " bought " + shares + " shares of " + symbol);
    }
    
    @Override
    public void update(Stock stock) {
        if (portfolio.containsKey(stock.getSymbol())) {
            int shares = portfolio.get(stock.getSymbol());
            double value = shares * stock.getPrice();
            System.out.println("🏦 " + name + " portfolio update: " + 
                shares + " shares of " + stock.getSymbol() + 
                " = $" + String.format("%.2f", value));
            
            if (stock.getChange() > 0) {
                System.out.println("   💰 Gained $" + String.format("%.2f", shares * stock.getChange()));
            } else if (stock.getChange() < 0) {
                System.out.println("   📉 Lost $" + String.format("%.2f", Math.abs(shares * stock.getChange())));
            }
        }
    }
    
    @Override
    public String getObserverName() {
        return "Investor: " + name;
    }
}

class TradingBot implements Observer<Stock> {
    private String botName;
    private double threshold;
    private Map<String, Double> lastPrices;
    
    public TradingBot(String botName, double threshold) {
        this.botName = botName;
        this.threshold = threshold;
        this.lastPrices = new HashMap<>();
    }
    
    @Override
    public void update(Stock stock) {
        String symbol = stock.getSymbol();
        double currentPrice = stock.getPrice();
        Double lastPrice = lastPrices.get(symbol);
        
        if (lastPrice != null) {
            double changePercent = Math.abs((currentPrice - lastPrice) / lastPrice) * 100;
            
            if (changePercent > threshold) {
                if (currentPrice > lastPrice) {
                    System.out.println("🤖 " + botName + " SELL SIGNAL for " + symbol + 
                        " (+" + String.format("%.2f", changePercent) + "%)");
                } else {
                    System.out.println("🤖 " + botName + " BUY SIGNAL for " + symbol + 
                        " (-" + String.format("%.2f", changePercent) + "%)");
                }
            }
        }
        
        lastPrices.put(symbol, currentPrice);
    }
    
    @Override
    public String getObserverName() {
        return "Trading Bot: " + botName;
    }
}

class MarketAnalyst implements Observer<Stock> {
    private String analystName;
    private List<String> marketTrends;
    
    public MarketAnalyst(String analystName) {
        this.analystName = analystName;
        this.marketTrends = new ArrayList<>();
    }
    
    @Override
    public void update(Stock stock) {
        String trend;
        if (stock.getChange() > 5.0) {
            trend = "📈 STRONG BUY for " + stock.getSymbol();
        } else if (stock.getChange() > 0) {
            trend = "📊 HOLD/BUY for " + stock.getSymbol();
        } else if (stock.getChange() < -5.0) {
            trend = "📉 STRONG SELL for " + stock.getSymbol();
        } else {
            trend = "📊 HOLD for " + stock.getSymbol();
        }
        
        marketTrends.add(trend);
        System.out.println("📊 " + analystName + " analysis: " + trend);
    }
    
    @Override
    public String getObserverName() {
        return "Market Analyst: " + analystName;
    }
    
    public void printTrendSummary() {
        System.out.println("\n📊 " + analystName + " Trend Summary:");
        for (String trend : marketTrends) {
            System.out.println("   " + trend);
        }
    }
}

// News Subscription System

class NewsArticle {
    private String headline;
    private String content;
    private String category;
    private Date publishTime;
    
    public NewsArticle(String headline, String content, String category) {
        this.headline = headline;
        this.content = content;
        this.category = category;
        this.publishTime = new Date();
    }
    
    // Getters
    public String getHeadline() { return headline; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public Date getPublishTime() { return publishTime; }
    
    @Override
    public String toString() {
        return String.format("[%s] %s", category.toUpperCase(), headline);
    }
}

class NewsAgency implements Subject<NewsArticle> {
    private List<Observer<NewsArticle>> subscribers;
    private List<NewsArticle> articles;
    
    public NewsAgency() {
        this.subscribers = new ArrayList<>();
        this.articles = new ArrayList<>();
    }
    
    public void publishArticle(String headline, String content, String category) {
        NewsArticle article = new NewsArticle(headline, content, category);
        articles.add(article);
        System.out.println("\n📰 Breaking News Published: " + article);
        notifyObservers(article);
    }
    
    @Override
    public void attach(Observer<NewsArticle> observer) {
        subscribers.add(observer);
        System.out.println("✅ " + observer.getObserverName() + " subscribed to news updates");
    }
    
    @Override
    public void detach(Observer<NewsArticle> observer) {
        subscribers.remove(observer);
        System.out.println("❌ " + observer.getObserverName() + " unsubscribed from news updates");
    }
    
    @Override
    public void notifyObservers() {
        for (Observer<NewsArticle> subscriber : subscribers) {
            for (NewsArticle article : articles) {
                subscriber.update(article);
            }
        }
    }
    
    public void notifyObservers(NewsArticle article) {
        for (Observer<NewsArticle> subscriber : subscribers) {
            subscriber.update(article);
        }
    }
}

class NewsSubscriber implements Observer<NewsArticle> {
    private String name;
    private Set<String> interestedCategories;
    private List<NewsArticle> readingList;
    
    public NewsSubscriber(String name, String... categories) {
        this.name = name;
        this.interestedCategories = new HashSet<>(Arrays.asList(categories));
        this.readingList = new ArrayList<>();
    }
    
    @Override
    public void update(NewsArticle article) {
        if (interestedCategories.contains(article.getCategory().toLowerCase()) || 
            interestedCategories.contains("all")) {
            readingList.add(article);
            System.out.println("📱 " + name + " received notification: " + article);
        }
    }
    
    @Override
    public String getObserverName() {
        return "News Subscriber: " + name;
    }
    
    public void printReadingList() {
        System.out.println("\n📚 " + name + "'s Reading List:");
        for (NewsArticle article : readingList) {
            System.out.println("   " + article);
        }
    }
}

// Event System Example

class Event {
    private String type;
    private Object data;
    private long timestamp;
    
    public Event(String type, Object data) {
        this.type = type;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getType() { return type; }
    public Object getData() { return data; }
    public long getTimestamp() { return timestamp; }
}

class EventManager {
    private Map<String, List<Observer<Event>>> eventListeners;
    
    public EventManager() {
        this.eventListeners = new HashMap<>();
    }
    
    public void subscribe(String eventType, Observer<Event> listener) {
        eventListeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
        System.out.println("✅ " + listener.getObserverName() + " subscribed to '" + eventType + "' events");
    }
    
    public void unsubscribe(String eventType, Observer<Event> listener) {
        List<Observer<Event>> listeners = eventListeners.get(eventType);
        if (listeners != null) {
            listeners.remove(listener);
            System.out.println("❌ " + listener.getObserverName() + " unsubscribed from '" + eventType + "' events");
        }
    }
    
    public void publish(String eventType, Object data) {
        Event event = new Event(eventType, data);
        List<Observer<Event>> listeners = eventListeners.get(eventType);
        
        if (listeners != null) {
            System.out.println("\n🔔 Event Published: " + eventType);
            for (Observer<Event> listener : listeners) {
                listener.update(event);
            }
        }
    }
}

class EventListener implements Observer<Event> {
    private String name;
    
    public EventListener(String name) {
        this.name = name;
    }
    
    @Override
    public void update(Event event) {
        System.out.println("🎯 " + name + " handled event '" + event.getType() + 
            "' with data: " + event.getData());
    }
    
    @Override
    public String getObserverName() {
        return "Event Listener: " + name;
    }
}

public class ObserverPatternDemo {
    
    /**
     * Demonstrates the Stock Market Observer pattern
     */
    public static void demonstrateStockMarket() {
        System.out.println("=== STOCK MARKET OBSERVER DEMO ===");
        
        // Create stock market
        StockMarket market = new StockMarket();
        
        // Add stocks
        market.addStock(new Stock("AAPL", "Apple Inc.", 150.00));
        market.addStock(new Stock("GOOGL", "Alphabet Inc.", 2800.00));
        market.addStock(new Stock("TSLA", "Tesla Inc.", 800.00));
        
        // Create observers
        Investor investor1 = new Investor("Alice Johnson");
        Investor investor2 = new Investor("Bob Smith");
        TradingBot bot = new TradingBot("QuickTrade Bot", 3.0); // 3% threshold
        MarketAnalyst analyst = new MarketAnalyst("Wall Street Pro");
        
        // Set up portfolios
        investor1.buyStock("AAPL", 100);
        investor1.buyStock("GOOGL", 10);
        investor2.buyStock("TSLA", 50);
        investor2.buyStock("AAPL", 200);
        
        // Subscribe to market updates
        market.attach(investor1);
        market.attach(investor2);
        market.attach(bot);
        market.attach(analyst);
        
        // Simulate market changes
        market.updateStockPrice("AAPL", 155.25);  // +3.5% gain
        market.updateStockPrice("GOOGL", 2750.00); // -1.8% loss
        market.updateStockPrice("TSLA", 760.00);   // -5% loss
        market.updateStockPrice("AAPL", 162.00);   // Additional gain
        
        // Show analyst summary
        analyst.printTrendSummary();
        
        // Unsubscribe an observer
        market.detach(bot);
        market.updateStockPrice("GOOGL", 2900.00); // Bot won't receive this
    }
    
    /**
     * Demonstrates the News Subscription Observer pattern
     */
    public static void demonstrateNewsSubscription() {
        System.out.println("\n=== NEWS SUBSCRIPTION DEMO ===");
        
        // Create news agency
        NewsAgency newsAgency = new NewsAgency();
        
        // Create subscribers with different interests
        NewsSubscriber techFan = new NewsSubscriber("Tech Enthusiast", "technology", "business");
        NewsSubscriber sportsLover = new NewsSubscriber("Sports Fan", "sports", "entertainment");
        NewsSubscriber newsJunkie = new NewsSubscriber("News Junkie", "all");
        
        // Subscribe to news
        newsAgency.attach(techFan);
        newsAgency.attach(sportsLover);
        newsAgency.attach(newsJunkie);
        
        // Publish different types of news
        newsAgency.publishArticle(
            "Apple Announces New iPhone", 
            "Apple unveiled the latest iPhone with revolutionary features...", 
            "technology"
        );
        
        newsAgency.publishArticle(
            "World Cup Final Results", 
            "The World Cup final was an exciting match...", 
            "sports"
        );
        
        newsAgency.publishArticle(
            "Stock Market Reaches New High", 
            "Major indices hit record highs today...", 
            "business"
        );
        
        newsAgency.publishArticle(
            "Celebrity Wedding Announcement", 
            "Famous couple ties the knot in lavish ceremony...", 
            "entertainment"
        );
        
        // Show reading lists
        techFan.printReadingList();
        sportsLover.printReadingList();
        newsJunkie.printReadingList();
    }
    
    /**
     * Demonstrates a generic Event System using Observer pattern
     */
    public static void demonstrateEventSystem() {
        System.out.println("\n=== EVENT SYSTEM DEMO ===");
        
        EventManager eventManager = new EventManager();
        
        // Create event listeners
        EventListener userActionListener = new EventListener("User Action Handler");
        EventListener systemEventListener = new EventListener("System Monitor");
        EventListener errorHandler = new EventListener("Error Handler");
        EventListener logger = new EventListener("System Logger");
        
        // Subscribe to different event types
        eventManager.subscribe("user.login", userActionListener);
        eventManager.subscribe("user.logout", userActionListener);
        eventManager.subscribe("user.login", logger); // Multiple listeners for same event
        
        eventManager.subscribe("system.startup", systemEventListener);
        eventManager.subscribe("system.shutdown", systemEventListener);
        eventManager.subscribe("system.startup", logger);
        
        eventManager.subscribe("error.critical", errorHandler);
        eventManager.subscribe("error.warning", errorHandler);
        eventManager.subscribe("error.critical", logger);
        
        // Publish events
        eventManager.publish("user.login", "UserID: alice123");
        eventManager.publish("system.startup", "System initialized successfully");
        eventManager.publish("error.warning", "Low disk space detected");
        eventManager.publish("user.logout", "UserID: alice123");
        eventManager.publish("error.critical", "Database connection failed");
        
        // Unsubscribe and publish more events
        eventManager.unsubscribe("error.critical", errorHandler);
        eventManager.publish("error.critical", "Memory leak detected");
    }
    
    /**
     * Analyzes Observer pattern characteristics and best practices
     */
    public static void analyzeObserverPattern() {
        System.out.println("\n=== OBSERVER PATTERN ANALYSIS ===");
        
        System.out.println("Pattern Structure:");
        System.out.println("• Subject: Maintains list of observers, provides attach/detach methods");
        System.out.println("• Observer: Defines update interface for objects to be notified");
        System.out.println("• ConcreteSubject: Stores state, notifies observers when state changes");
        System.out.println("• ConcreteObserver: Implements update method to react to state changes");
        
        System.out.println("\nKey Benefits:");
        System.out.println("• Loose coupling between subject and observers");
        System.out.println("• Dynamic subscription/unsubscription");
        System.out.println("• Broadcast communication mechanism");
        System.out.println("• Supports Open/Closed Principle");
        
        System.out.println("\nPotential Drawbacks:");
        System.out.println("• Memory leaks if observers aren't properly detached");
        System.out.println("• Unexpected update chains can cause performance issues");
        System.out.println("• No guarantee on notification order");
        System.out.println("• Complex debugging with many observers");
        
        System.out.println("\nBest Practices:");
        System.out.println("• Weak references to prevent memory leaks");
        System.out.println("• Asynchronous notifications for better performance");
        System.out.println("• Exception handling in notification loops");
        System.out.println("• Consider using event-driven architectures");
        
        System.out.println("\nReal-world Applications:");
        System.out.println("• Model-View-Controller (MVC) architecture");
        System.out.println("• Event handling systems (GUI, web events)");
        System.out.println("• Publish-Subscribe messaging systems");
        System.out.println("• Data binding in frameworks");
        System.out.println("• Reactive programming (RxJava, etc.)");
    }
    
    /**
     * Main method demonstrating the Observer pattern
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Observer Design Pattern Demonstration");
        System.out.println("====================================");
        
        demonstrateStockMarket();
        demonstrateNewsSubscription();
        demonstrateEventSystem();
        analyzeObserverPattern();
        
        System.out.println("\n=== SUMMARY ===");
        System.out.println("The Observer pattern enables loose coupling between objects");
        System.out.println("through automatic notification of state changes. It's fundamental");
        System.out.println("to event-driven programming and reactive systems.");
    }
}
