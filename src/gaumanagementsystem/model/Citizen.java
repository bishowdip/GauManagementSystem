package gaumanagementsystem.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Citizen model class for citizen profile management
 */
public class Citizen {
    private int citizenId;
    private int userId;
    private String name;
    private int ward;
    private String gender;
    private String phone;
    private String address;
    private String email;
    private Date dateOfBirth;
    private String fatherName;
    private String motherName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public Citizen() {}
    
    // Constructor for basic citizen info
    public Citizen(String name, int ward, String gender, String phone, String address, String email) {
        this.name = name;
        this.ward = ward;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }
    
    // Full constructor
    public Citizen(int citizenId, int userId, String name, int ward, String gender, String phone, 
                   String address, String email, Date dateOfBirth, String fatherName, String motherName,
                   Timestamp createdAt, Timestamp updatedAt) {
        this.citizenId = citizenId;
        this.userId = userId;
        this.name = name;
        this.ward = ward;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getCitizenId() {
        return citizenId;
    }
    
    public void setCitizenId(int citizenId) {
        this.citizenId = citizenId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getWard() {
        return ward;
    }
    
    public void setWard(int ward) {
        this.ward = ward;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getFatherName() {
        return fatherName;
    }
    
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
    
    public String getMotherName() {
        return motherName;
    }
    
    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Citizen{" +
                "citizenId=" + citizenId +
                ", name='" + name + '\'' +
                ", ward=" + ward +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
} 