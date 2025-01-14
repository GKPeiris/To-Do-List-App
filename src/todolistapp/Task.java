package todolistapp;
import java.io.Serializable;

public class Task implements Serializable {
    
    private static final long serialVersionUID = 6111277969744908042L;
    
    private int id;
    private String description;
    private boolean isCompleted; // Ensure consistent variable naming
    private String category;

    // Constructor
    public Task(int id, String description, String category) {
        this.id = id;
        this.description = description;
        this.isCompleted = false;
        this.category = category;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getCategory() {
        return category;
    }

    // Mark the task as completed
    public void markAsCompleted() {
        this.isCompleted = true;
    }

    // Toggle the task's completed status (used for unticking)
    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    // Override toString for clear task representation
    @Override
    public String toString() {
        return id + ". " + description + " [" + category + "] " +
                (isCompleted ? "(completed)" : "");
    }
}
