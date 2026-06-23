package domain;

import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String name;
    private String role;

    public Employee(String username, String name, String role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return name + " [" + role + "]";
    }    
}
