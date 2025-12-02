package com.medilabo.assessment.proxies.patient;

import com.medilabo.assessment.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;

/**
 * Data Transfer Object (DTO) representing a patient.
 * This class contains patient details such as ID, name, birth
 */
public class PatientDto {
    private int id;
    private String name;
    private String firstName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String gender;
    private String address;
    private String phoneNumber;

    public PatientDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
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

    public int getAge() {
        LocalDate currentDate = LocalDate.now();

        return Period.between(birthDate, currentDate).getYears();
    }

    public boolean isMale() {
        return gender.equalsIgnoreCase(Gender.MALE.getLabel());
    }

    public boolean isFemale() {
        return gender.equalsIgnoreCase(Gender.FEMALE.getLabel());
    }
}
