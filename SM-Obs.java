import java.util.ArrayList;
import java.util.List;


class StockExchange {
    private List<Observer> observers = new ArrayList<>();
    private String stockPrice;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockPrice);
        }
    }
}
interface Observer {
    void update(String stockPrice);
}


class Investor implements Observer {
    @Override
    public void update(String stockPrice) {
        System.out.println("Investor notified of stock price: " + stockPrice);
    }
}

class Trader implements Observer {
    @Override
    public void update(String stockPrice) {
        System.out.println("Trader notified of stock price: " + stockPrice);
    }
}

public class Main {
    public static void main(String[] args) {
       
        StockExchange stockExchange = new StockExchange();

        Investor investor = new Investor();
        Trader trader = new Trader();

        stockExchange.addObserver(investor);
        stockExchange.addObserver(trader);

       
        stockExchange.setStockPrice("$100");
        stockExchange.setStockPrice("$150");
    }
}
