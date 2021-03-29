package com.bridgelabs.com;

public class AddressBookData {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private int zip;
    private String phoneNumber;
    private String email;
    private String type;
    private String addressbookname;

    public AddressBookData(int id, String firstName, String lastName,
                           String address, String city, String state,
                           int zip, String phone, String email, String type, String addressbookname) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phone;
        this.email = email;
        this.type = type;
        this.addressbookname = addressbookname;
    }

    @Override
    public String toString() {
        return "AddressBookData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip=" + zip +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", addressbookname='" + addressbookname + '\'' +
                '\n';
    }
}
