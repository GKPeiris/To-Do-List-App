package todolistapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ToDoListApp {

    private TaskManager taskManager;
    private JFrame frame;
    private JPanel taskPanel;

    public ToDoListApp() {
        taskManager = new TaskManager();
        initialize();
        updateTaskList();
    }

    private void initialize() {
        frame = new JFrame("To-Do List App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("To-Do List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);

        // Task Panel
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField taskField = new JTextField(20);
        taskField.setFont(new Font("Arial", Font.PLAIN, 14));
        taskField.setPreferredSize(new Dimension(300,30));
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Work", "Home", "Personal"});
        JButton addButton = new JButton("Add Task");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(Color.GREEN);
        addButton.setForeground(Color.BLACK);
        addButton.setFocusPainted(false);
        

        addButton.addActionListener(e -> {
            String description = taskField.getText().trim();
            String category = (String) categoryBox.getSelectedItem();
            if (!description.isEmpty()) {
                taskManager.addTask(description, category);
                taskField.setText("");
                updateTaskList();
            }
        });

        inputPanel.add(taskField);
        inputPanel.add(categoryBox);
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void updateTaskList() {
        taskPanel.removeAll(); // Clear previous task list
        List<Task> tasks = taskManager.getTasks();

        for (Task task : tasks) {
            // Panel for each task
            JPanel taskItemPanel = new JPanel(new BorderLayout());
            taskItemPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            taskItemPanel.setBackground(Color.WHITE); // Task background color

            // Apply strikethrough using HTML when task is completed
            String taskText = "<html><span style='font-weight:bold;" +
                  (task.isCompleted() ? "text-decoration:line-through;" : "") +
                  "'>" + task.getDescription() + " [" + task.getCategory() + "]</span></html>";

            JCheckBox taskCheckbox = new JCheckBox(taskText);
            taskCheckbox.setSelected(task.isCompleted());
            taskCheckbox.setFont(new Font("Arial", Font.PLAIN, 14)); // Base font style

            taskCheckbox.addActionListener(e -> {
                if (taskCheckbox.isSelected()) {
                    task.markAsCompleted();
                } else {
                    task.setCompleted(false);
                }
                taskManager.saveTasksToFile();
                updateTaskList();
});


            taskItemPanel.add(taskCheckbox, BorderLayout.CENTER);

            // Delete button for each task
            JButton deleteButton = new JButton("Delete");
            deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
            deleteButton.setFocusPainted(false);
            deleteButton.setBackground(Color.RED);
            deleteButton.setForeground(Color.WHITE);

            deleteButton.addActionListener(e -> {
                taskManager.deleteTask(task.getId());
                updateTaskList();
            });

            taskItemPanel.add(deleteButton, BorderLayout.EAST);

            // Add the task panel to the main panel
            taskPanel.add(taskItemPanel);

            // Add small gap between tasks
            taskPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        taskPanel.revalidate();
        taskPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListApp::new);
    }
}
