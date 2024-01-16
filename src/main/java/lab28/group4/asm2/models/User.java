package lab28.group4.asm2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lab28.group4.asm2.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
public class User {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    @NotBlank
    @Column(name = "full_name")
    private String fullName;

    public User() {
    }

    public User(String username, String password, Role role, String phone, String email, String fullName) {
        this.username = username;
        this.password = passwordEncoder.encode(password);
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String password) {
        return passwordEncoder.matches(password, this.password);
    }

    public Role getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }



}
