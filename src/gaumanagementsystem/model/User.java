package gaumanagementsystem.model;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String gender;
    private String password;

    public User() {
    }

    public User(String name, String username, String email, String gender, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
} 