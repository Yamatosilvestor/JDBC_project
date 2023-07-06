package Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserManagementSystem system = new UserManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Меню ===");
            System.out.println("1. Создать новую учетную запись");
            System.out.println("2. Войти в систему");
            System.out.println("3. Выйти из системы");
            System.out.println("4. Вывести информацию");
            System.out.println("5. Изменить пароль");
            System.out.println("6. Вывести информацию о текущем пользователе");
            System.out.println("7. Выход");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Считываем символ новой строки после считывания числа
            switch (choice) {
                case 1:
                    System.out.print("Введите имя пользователя: ");
                    String username = scanner.nextLine();
                    System.out.print("Введите адрес электронной почты: ");
                    String email = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    system.createUser(username, email, password);
                    break;
                case 2:
                    System.out.print("Введите имя пользователя: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String loginPassword = scanner.nextLine();
                    system.login(loginUsername, loginPassword);
                    break;
                case 3:
                    system.logout();
                    break;
                case 4:
                    system.printUserInfo();
                    break;
                case 5:
                    System.out.print("Введите текущий пароль: ");
                    String currentPassword = scanner.nextLine();
                    System.out.print("Введите новый пароль: ");
                    String newPassword = scanner.nextLine();
                    system.changePassword(currentPassword, newPassword);
                    break;
                case 6:
                    system.printUserInfo();
                    break;
                case 7:
                    System.out.println("Программа завершена.");
                    System.exit(0);
                default:
                    System.out.println("Некорректный ввод. Попробуйте снова.");
            }
        }
    }
}


public static class User {
        private String username;
        private String email;
        private String password;

        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public boolean authenticate(String password) {
            return this.password.equals(password);
        }

        public void changePassword(String newPassword) {
            this.password = newPassword;
            System.out.println("Пароль успешно изменен.");
        }
    }

    public class UserManagementSystem {
        private Map<String, User> users;
        private User currentUser;

        public UserManagementSystem() {
            users = new HashMap<>();
            currentUser = null;
        }

        public void createUser(String username, String email, String password) {
            User newUser = new User(username, email, password);
            users.put(username, newUser);
            System.out.println("Новая учетная запись создана.");
        }

        public void login(String username, String password) {
            User user = users.get(username);
            if (user != null && user.authenticate(password)) {
                currentUser = user;
                System.out.println("Вход выполнен.");
            } else {
                System.out.println("Неверное имя пользователя или пароль.");
            }
        }

        public void logout() {
            currentUser = null;
            System.out.println("Выход выполнен.");
        }

        public void printUserInfo() {
            if (currentUser != null) {
                System.out.println("Имя пользователя: " + currentUser.getUsername());
                System.out.println("Адрес электронной почты: " + currentUser.getEmail());
            } else {
                System.out.println("Вы не вошли в систему.");
            }
        }

        public void changePassword(String currentPassword, String newPassword) {
            if (currentUser != null && currentUser.authenticate(currentPassword)) {
                currentUser.changePassword(newPassword);
            } else {
                System.out.println("Для изменения пароля необходимо войти в систему и указать текущий пароль.");
            }
        }


        public static class UserManager {
        private final Map<String, User> users;
        User user;

        public UserManager() {
            users = new HashMap<>();
            user = null;
        }

        public void addTask(String name, String email, String password) {
            User user = new User(name, password, email);
            users.put(email, user);
            System.out.println("Пользователь был создан");
        }

        public void logIn(String email, String password) {
            User user1 = users.get(email);
            if (user1 != null && user1.checkPassword(password)) {
                user = user1;
                System.out.println("Пользователь верен");
            } else {
                System.out.println("Пользователь не верен");
            }
        }

        public void logOut() {
            user = null;
            System.out.println("Пользователь вышел");
        }

        public void printInfo() {
            if (user != null) {
                System.out.println(user.getInfo() + "\n");
            } else {
                System.out.println("Пользователь не найден");
            }
        }
        public void changePassword(String password) {
            user.changePassword(password);
        }
    }
}
