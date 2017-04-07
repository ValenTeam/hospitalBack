package models.auth;


/**
 * Created by Caeus on 2/16/2015.
 */
public class LoginCredentials {

    public enum UserRole {
        admin, medico, paciente, medicoJefe;
    }

    private UserRole role;

    private String email;

    private String password;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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
}