package com.jomqr.app;

public class Users {

    String firstName;
    String lastName;
    String icNumber;
    String phoneNumber;

    public Users() {

    }

    public Users(String firstName, String lastName, String icNumber, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.icNumber = icNumber;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIcNumber() {
        return icNumber;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}


