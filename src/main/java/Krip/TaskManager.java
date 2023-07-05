package Krip;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Task {
    private int id;
    private String name;
    private String description;
    private java.util.Date dueDate;
    private int priority;
    private String status;

    public Task(int id, String name, String description, java.util.Date dueDate, int priority, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public java.util.Date getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }
}

public class TaskManager {
    private Connection connection;
    String url = "jdbc:postgresql://localhost:5432/books";
    String user = "postgres";
    String password = "postgres";
    public TaskManager() {
        // Установить соединение с базой данных

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTask(String name, String description, Date dueDate, int priority) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Подготовка SQL-запроса
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO task2 (name, description, due_date, priority, status) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setDate(3, dueDate);
            statement.setInt(4, priority);
            statement.setString(5, "В процессе");
            // Выполнение SQL-запроса для вставки данных
            statement.executeUpdate();
            System.out.println("Задача успешно создана.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            // Выполнение SQL-запроса для получения списка задач
            ResultSet resultSet = statement.executeQuery("SELECT * FROM task2");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date dueDate = resultSet.getDate("due_date");
                int priority = resultSet.getInt("priority");
                String status = resultSet.getString("status");
                // Создание объекта Task и добавление его в список
                Task task = new Task(id, name, description, dueDate, priority, status);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void updateTask(int taskId, String name, String description, Date dueDate, int priority) {
        try {
            // Подготовка SQL-запроса для обновления задачи
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE task2 SET name=?, description=?, due_date=?, priority=? WHERE id=?"
            );
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setDate(3, dueDate);
            statement.setInt(4, priority);
            statement.setInt(5, taskId);
            // Выполнение SQL-запроса для обновления данных
            statement.executeUpdate();
            System.out.println("Задача успешно обновлена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM task2 WHERE id=?"
            );
            statement.setInt(1, taskId);
            // Выполнение SQL-запроса для удаления данных
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Задача успешно удалена.");
            } else {
                System.out.println("Нет задачи с таким идентификатором.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markTaskAsDone(int taskId) {
        try {
            // Подготовка SQL-запроса для отметки задачи как выполненной
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE task2 SET status=? WHERE id=?"
            );
            statement.setString(1, "Выполнено");
            statement.setInt(2, taskId);
            statement.executeUpdate();
            System.out.println("Задача отмечена как выполненная.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeTaskStatus(int taskId, String state) {
        try {
            // Подготовка SQL-запроса для изменения статуса задачи
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE task2 SET status=? WHERE id=?"
            );
            statement.setString(1, state);
            statement.setInt(2, taskId);
            statement.executeUpdate();
            System.out.println("Статус задачи изменен.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayTasks(List<Task> task2) {
        for (Task task : task2) {
            System.out.println("ID               : " + task.getId());
            System.out.println("Название         : " + task.getName());
            System.out.println("Описание         : " + task.getDescription());
            System.out.println("Дата выполнения  : " + task.getDueDate());
            System.out.println("Приоритет        : " + task.getPriority());
            System.out.println("Статус           : " + task.getStatus());
            System.out.println("----------------------------------------");
        }
    }
    public List<Task> getTasksForPeriod(Date startDate, Date endDate) {
        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM task2 WHERE due_date BETWEEN ? AND ?"
            );
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            // Выполнение SQL-запроса для получения данных
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Извлечение данных из ResultSet
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date dueDate = resultSet.getDate("due_date");
                int priority = resultSet.getInt("priority");
                String status = resultSet.getString("status");
                // Создание объекта Task и добавление его в список
                Task task = new Task(id, name, description, dueDate, priority, status);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);

        while (true) {
            System.out.println("========================================");
            System.out.println("Меню:");
            System.out.println("1. Создать задачу");
            System.out.println("2. Просмотреть список задач");
            System.out.println("3. Редактировать задачу");
            System.out.println("4. Удалить задачу");
            System.out.println("5. Отметить задачу как выполненную");
            System.out.println("6. Изменить статус задачи");
            System.out.println("0. Выход");
            System.out.println("========================================");

            int choice = scanner.nextInt();

            scanner.nextLine(); // Считывание символа новой строки после ввода числа

            if (choice == 1) {
                System.out.print("Введите название задачи:");
                String name = scanner.nextLine();
                System.out.print("Введите описание задачи:");
                String description = scanner.nextLine();
                System.out.print("Введите дату выполнения задачи (гггг-мм-дд):");
                String dueDateString = scanner.nextLine();
                Date dueDate = Date.valueOf(dueDateString);
                System.out.print("Введите приоритет задачи (целое число):");
                int priority = scannerInt.nextInt();
                taskManager.createTask(name, description, dueDate, priority);
            } else if (choice == 2) {
                List<Task> allTasks = taskManager.getAllTasks();
                taskManager.displayTasks(allTasks);
            } else if (choice == 3) {
                System.out.print("Введите ID задачи, которую хотите отредактировать:");
                int taskId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Введите новое название задачи:");
                String newName = scanner.nextLine();
                System.out.print("Введите новое описание задачи:");
                String newDescription = scanner.nextLine();
                System.out.print("Введите новую дату выполнения задачи (гггг-мм-дд):");
                String newDueDateString = scanner.nextLine();
                Date newDueDate = Date.valueOf(newDueDateString);
                System.out.print("Введите новый приоритет задачи (целое число):");
                int newPriority = scanner.nextInt();
                taskManager.updateTask(taskId, newName, newDescription, newDueDate, newPriority);
            } else if (choice == 4) {
                System.out.print("Введите ID задачи, которую хотите удалить:");
                int taskIdToDelete = scanner.nextInt();
                taskManager.deleteTask(taskIdToDelete);
            } else if (choice == 5) {
                System.out.print("Введите ID задачи, которую хотите отметить как выполненную:");
                int taskIdToMarkAsDone = scanner.nextInt();
                taskManager.markTaskAsDone(taskIdToMarkAsDone);
            } else if (choice == 6) {
                System.out.print("Введите ID задачи, для которой хотите изменить статус:");
                int taskIdToChangeStatus = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Введите новый статус задачи:");
                String newStatus = scanner.nextLine();
                taskManager.changeTaskStatus(taskIdToChangeStatus, newStatus);
            } else if (choice == 0) {
                System.exit(0);
            } else {
                System.err.println("Неверный выбор. Пожалуйста, выберите действие из меню.");
            }
        }
    }
}