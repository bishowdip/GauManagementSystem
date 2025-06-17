/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/

package gaumanagementsystem.model;

/**
 *
 * @author wangel
 */

public class CitizenData {
    private String citizenId;
    private String name;
    private String email;
    private String dateOfBirth;
    private String address;
    private String gender;
    private String phone;
    private String fatherName;
    private String motherName;
    private String imagePath;

    // Empty constructor
    public CitizenData() {}

    // Full constructor
    public CitizenData(String citizenId, String name, String email, String dateOfBirth, String address, String gender, String phone, String fatherName, String motherName, String imagePath) {
        this.citizenId = citizenId;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public String getCitizenId() { return citizenId; }
    public void setCitizenId(String citizenId) { this.citizenId = citizenId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }
    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}

