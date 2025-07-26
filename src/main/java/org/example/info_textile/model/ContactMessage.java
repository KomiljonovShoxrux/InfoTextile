package org.example.info_textile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String message;

    private String adminReply;
    @CreatedDate// Admin yozgan javob
    private LocalDateTime createdDate = LocalDateTime.now();
    @CreatedDate
    private LocalDateTime repliedDate;

    public ContactMessage(Long id, String name, String email, String phone, String message, String adminReply, LocalDateTime createdDate, LocalDateTime repliedDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
        this.adminReply = adminReply;
        this.createdDate = createdDate;
        this.repliedDate = repliedDate;
    }

    public ContactMessage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdminReply() {
        return adminReply;
    }

    public void setAdminReply(String adminReply) {
        this.adminReply = adminReply;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getRepliedDate() {
        return repliedDate;
    }

    public void setRepliedDate(LocalDateTime repliedDate) {
        this.repliedDate = repliedDate;
    }

    @Override
    public String toString() {
        return "ContactMessage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                ", adminReply='" + adminReply + '\'' +
                ", createdDate=" + createdDate +
                ", repliedDate=" + repliedDate +
                '}';
    }
}
