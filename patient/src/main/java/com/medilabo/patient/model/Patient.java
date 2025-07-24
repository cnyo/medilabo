package com.medilabo.patient.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.Date;

//@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "id"})
@JsonFilter("patientFilter")
@Entity
public class Patient {
    @Id
    private int id;
    private String name;
    private String firstName;
    private Date birthDate;
    private String gender;
    private String address;
    private String phoneNumber;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Patient() {

    }

//    public Patient(Long id, String name, String firstName, Date birthDate, String gender, String address, String phoneNumber) {
//        this.id = id;
//        this.name = name;
//        this.firstName = firstName;
//        this.birthDate = birthDate;
//        this.gender = gender;
//        this.address = address;
//        this.phoneNumber = phoneNumber;
//    }



    //    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

}