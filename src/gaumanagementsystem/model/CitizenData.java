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
    private String citizen_id;
    private String name;
    private String email;
    private String address;
    private String gender;
    private String phone;
    private String father_name;
    private String mother_name;

    // Full constructor (order matches DB columns)
    public CitizenData(String citizen_id, String name, String email, String address,
                       String gender, String phone, String father_name, String mother_name) {
        this.citizen_id = citizen_id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.father_name = father_name;
        this.mother_name = mother_name;
    }

    // Empty constructor
    public CitizenData() {}

    // Getters
    public String getCitizen_id() {
        return citizen_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getFathername() {
        return father_name;
    }

    public String getMothername() {
        return mother_name;
    }

    // Setters
    public void setCitizen_id(String citizen_id) {
        this.citizen_id = citizen_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFathername(String fathername) {
        this.father_name = fathername;
    }

    public void setMothername(String mothername) {
        this.mother_name = mothername;
    }

    @Override
    public String toString() {
        return "CitizenData{" +
                "citizen_id='" + citizen_id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", father_name='" + father_name + '\'' +
                ", mother_name='" + mother_name + '\'' +
                '}';
    }
}
