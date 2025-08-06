package com.medilabo.patient.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

//@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "id"})
@JsonFilter("patientFilter")
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 25)
    private String name;

    @Size(min = 2, max = 50)
    private String firstName;

    @Past
    private Date birthDate;

    @Length(min = 1, max = 1)
    private String gender;

    private String address;

    private String phoneNumber;

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

    @Override
    public String toString() {
        return "PatientBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthDate=" + birthDate;
    }
}