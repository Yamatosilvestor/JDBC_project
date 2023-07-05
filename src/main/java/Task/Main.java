package Task;

import java.util.HashMap;
import java.util.Map;

public class Main {
    Map<String, User> users;
    private User user;
    public Main(){
        users = new HashMap<>();
        user = null;
    }

    private void createUser(String name, String email, String password){
        User user = new User(name, email, password);
        users.put(email, user);
        System.out.println("User создан");
    }
    public void loginUser(String email, String password) {
        User user1 = users.get(email);
        if (user1 != null && user1.checkPassword(password)) {
            user = user1;
            System.out.println("Вход выполнен!");
        } else {
            System.out.println("Неправильный пароль или Email!");
        }
    }
    public void logoutUser(){
        user = null;
        System.out.println("Вы вышли из системы");
    }
    public void ViewUser(){
        if (user != null){
            System.out.println("\n email" + user.getEmail());
            System.out.println("\n name" + user.getName());
        } else {
            System.out.println("Not found");
        }
    }
    public static void main(String[] args) {

    }
}

class User{
    private String name;
    private String email;
    private String password;
    User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    public void changePassword(String password) {
        this.password = password;
        System.out.println("Пароль успешно изменен.");
    }
}