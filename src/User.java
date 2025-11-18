package src;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private Date dob;
    private String email;
    private String phoneNo;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User() {}

    public User(int id, String name, Date dob, String email, String phoneNo,
                Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.phoneNo = phoneNo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(String name, Date dob, String email, String phoneNo) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "id: " + id +
                ", name: " + name +
                ", dob: " + dob +
                ", email: " + email +
                ", phone: " + phoneNo +
                ", created_at: " + createdAt +
                ", updated_at: " + updatedAt;
    }
}
