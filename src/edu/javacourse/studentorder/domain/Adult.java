package edu.javacourse.studentorder.domain;

import java.time.LocalDate;

public class Adult extends Person{
    private String passportSerial;
    private String passportNumber;
    private LocalDate issueDate;
    private PassportOffice passportOffice;
    private University university;
    private String studentID;

    public Adult() {
    }

    public Adult(String surName, String givenName, String patronymic, LocalDate dayOfBirth) {
        super(surName, givenName, patronymic, dayOfBirth);
    }

    public String getPassportSerial() {
        return passportSerial;
    }

    public void setPassportSerial(String passportSerial) {
        this.passportSerial = passportSerial;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public PassportOffice getPassportOffice() {
        return passportOffice;
    }

    public void setPassportOffice(PassportOffice passportOffice) {
        this.passportOffice = passportOffice;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "Adult{" +
                "passportSerial='" + passportSerial + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", issueDate=" + issueDate +
                ", issueDepartment='" + passportOffice + '\'' +
                ", university='" + university + '\'' +
                ", studentID='" + studentID + '\'' +
                '}';
    }
}
