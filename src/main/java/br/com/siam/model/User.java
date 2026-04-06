package br.com.siam.model;

public class User {

    private Integer id;
    private String name;
    private String login;
    private String registration;
    private String passwordHash;
    private String userType;

    public User() {}

    public User(Integer id, String name, String login, String registration, String passwordHash, String userType) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.registration = registration;
        this.passwordHash = passwordHash;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRegistration() { return registration; }

    public void setRegistration(String registration) { this.registration = registration; }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
