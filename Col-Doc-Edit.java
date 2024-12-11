import java.util.ArrayList;
import java.util.List;


interface Observer {
    void update(String message);
}


class Collaborator implements Observer {
    private String name;

    public Collaborator(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " (Collaborator) received notification: " + message);
    }
}

class Editor implements Observer {
    private String name;

    public Editor(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " (Editor) received notification: " + message);
    }
}

class Reviewer implements Observer {
    private String name;

    public Reviewer(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " (Reviewer) received notification: " + message);
    }
}


class SharedDocument {
    private List<Observer> observers = new ArrayList<>();
    private String content;

    public SharedDocument(String initialContent) {
        this.content = initialContent;
    }


    public void addObserver(Observer observer) {
        observers.add(observer);
    }

  
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

 
    private void notifyObservers(String changeDescription) {
        for (Observer observer : observers) {
            observer.update("Document updated: " + changeDescription);
        }
    }

  
    public void editDocument(String newContent) {
        this.content = newContent;
        notifyObservers("New content: " + newContent);
    }


    public String getContent() {
        return content;
    }
}


public class Main {
    public static void main(String[] args) {
       
        SharedDocument document = new SharedDocument("Initial content");

       
        Observer collaborator1 = new Collaborator("Alice");
        Observer collaborator2 = new Collaborator("Bob");
        Observer editor = new Editor("Charlie");
        Observer reviewer = new Reviewer("Diana");

     
        document.addObserver(collaborator1);
        document.addObserver(collaborator2);
        document.addObserver(editor);
        document.addObserver(reviewer);

        
        System.out.println("== Editing the Document ==");
        document.editDocument("Content updated by Alice.");
        document.editDocument("Charlie fixed a typo.");
    }
}
