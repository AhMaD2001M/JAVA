import java.util.ArrayList;
import java.util.List;

// Observer interface
interface Observer {
    void update(String message);
}

// Subject interface
interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}

// User class (Subject)
class User implements Subject {
    private final List<Observer> observers; // List to store observers
    private final String name; // Name of the user

    public User(String name) {
        this.name = name;
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        } else {
            System.out.println(observer + " is already registered.");
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.remove(observer)) {
            System.out.println(observer + " removed successfully.");
        } else {
            System.out.println(observer + " was not found in the observer list.");
        }
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void post(String message) {
        if (message == null || message.trim().isEmpty()) {
            System.out.println("Post cannot be empty.");
            return;
        }
        System.out.println(name + " posted: " + message);
        notifyObservers(name + ": " + message);
    }
}

// Follower class (Observer)
class Follower implements Observer {
    private final String name;

    public Follower(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received update: " + message);
    }

    @Override
    public String toString() {
        return "Follower: " + name;
    }
}

// NotificationService class (Observer)
class NotificationService implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Notification Service received: " + message);
    }

    @Override
    public String toString() {
        return "NotificationService";
    }
}

// Main class to run the program
public class main {
    public static void main(String[] args) {
        // Create a user (Subject)
        User user = new User("JohnDoe");

        // Create observers
        Follower follower1 = new Follower("JaneDoe");
        Follower follower2 = new Follower("BobSmith");
        NotificationService notificationService = new NotificationService();

        // Register observers
        user.registerObserver(follower1);
        user.registerObserver(follower2);
        user.registerObserver(notificationService);

        System.out.println("\n== User Posts a Message ==");
        user.post("Hello, world!");

        // Remove an observer
        System.out.println("\n== Removing Follower: BobSmith ==");
        user.removeObserver(follower2);

        // Post another message
        System.out.println("\n== User Posts Another Message ==");
        user.post("This is my second post.");
    }
}
