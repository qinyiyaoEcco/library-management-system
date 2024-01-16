package lab28.group4.asm2.models;

import jakarta.persistence.*;
import lab28.group4.asm2.Application;
import org.apache.commons.codec.binary.Hex;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

@Entity
@Table(name = "scrolls")
public class Scroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    protected Scroll() {
    }

    public Scroll(String name, User user, byte[] data) {
        this.name = name;
        this.user = user;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data);
            byte[] digest = md.digest();
            return Hex.encodeHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            Application.LOG.error(e.getMessage());
        }
        return null;
    }

    public int getSize() {
        return data.length;
    }

    public byte[] getData() {
        return data;
    }

    public void print() {
        Application.LOG.info("Scroll Details:");
        Application.LOG.info("ID: {}", id);
        Application.LOG.info("Name: {}", name);
        Application.LOG.info("Added by: {}", user.getUsername());
        Application.LOG.info("Scroll hash: {}", getHash());
        Application.LOG.info("Scroll size: {}", getSize());
    }

}
