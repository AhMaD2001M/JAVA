import java.util.ArrayList;
import java.util.List;


interface Observer {
    void update(String message);
}

class Bidder implements Observer {
    private String name;
    private double currentBid;

    public Bidder(String name) {
        this.name = name;
        this.currentBid = 0.0;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received notification: " + message);
    }

    public void placeBid(AuctionItem auctionItem, double bidAmount) {
        if (bidAmount > auctionItem.getCurrentBid()) {
            auctionItem.setCurrentBid(bidAmount, this);
            System.out.println(name + " placed a new bid of $" + bidAmount);
        } else {
            System.out.println(name + " bid of $" + bidAmount + " is too low. Current bid: $" + auctionItem.getCurrentBid());
        }
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double bid) {
        this.currentBid = bid;
    }

    @Override
    public String toString() {
        return name;
    }

class Auctioneer implements Observer {
    private String name;

    public Auctioneer(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Auctioneer " + name + " received notification: " + message);
    }
}


class AuctionItem {
    private String itemName;
    private double currentBid;
    private Bidder highestBidder;
    private List<Observer> observers;

    public AuctionItem(String itemName) {
        this.itemName = itemName;
        this.currentBid = 0.0;
        this.highestBidder = null;
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

  
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

  
    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

   
    public void setCurrentBid(double bidAmount, Bidder bidder) {
        this.currentBid = bidAmount;
        this.highestBidder = bidder;
        bidder.setCurrentBid(bidAmount);
        notifyObservers("New bid of $" + bidAmount + " placed by " + bidder + " on item " + itemName);
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void endAuction() {
        if (highestBidder != null) {
            notifyObservers("Auction for " + itemName + " has ended. Winner: " + highestBidder + " with a bid of $" + currentBid);
        } else {
            notifyObservers("Auction for " + itemName + " has ended with no bids.");
        }
    }
}


class Main {
    public static void main(String[] args) {
      
        AuctionItem item = new AuctionItem("Antique Vase");

       
        Bidder alice = new Bidder("Alice");
        Bidder bob = new Bidder("Bob");
        Auctioneer auctioneer = new Auctioneer("John");

     
        item.addObserver(alice);
        item.addObserver(bob);
        item.addObserver(auctioneer);

        
        System.out.println("\n== Auction Process Begins ==");
        alice.placeBid(item, 100);
        bob.placeBid(item, 150);
        alice.placeBid(item, 120); 

        
        item.endAuction();
    }
}
