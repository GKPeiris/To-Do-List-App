package todolistapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    
    private List<Task> tasks;
    private int nextId;
    private static final String FILE_NAME = "tasks.bat";

    public TaskManager() {
        tasks = new ArrayList<>();
        loadTasksFromFile();
        nextId = tasks.isEmpty()? 1: tasks.get(tasks.size() - 1).getId()+ 1;
    }
    
    // add new task
    public void addTask(String description, String category){
        Task task = new Task(nextId++, description, category);
        tasks.add(task);
        saveTasksToFile();
    }
    
//    // view all task
//    public void viewTasks(){
//        if(tasks.isEmpty()){
//            System.out.println("No task available.");
//        }else{
//            for(Task task : tasks){
//                System.out.println(task);
//            }
//        }
//             
//    }
    
    //mark as done
    public void maskAsDone(int id){
        for(Task task : tasks){
            if(task.getId() == id){
                task.markAsCompleted();
                saveTasksToFile();
                return;
            }
        }
        System.out.println("Task not Found!");
    }
    
    // Delete a task by id
    public void deleteTask(int id){
        tasks.removeIf(task -> task.getId()== id); // remove task if id match
        saveTasksToFile();
    }
    
    public List<Task> getTasks(){
        return tasks;
    }
    
    public void saveTasksToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
            System.out.println("Tasks saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
            e.printStackTrace();
        }
    }


    
    private void loadTasksFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                tasks = (List<Task>) ois.readObject();
                if (!tasks.isEmpty()) {
                    nextId = tasks.get(tasks.size() - 1).getId() + 1;
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading tasks: " + e.getMessage());
            }
        }
    }
    
}
