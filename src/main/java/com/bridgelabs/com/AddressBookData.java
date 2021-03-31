package com.bridgelabs.com;

import java.util.Objects;

public class AddressBookData {
    public int id;
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public int zip;
    public String phoneNumber;
    public String email;
    public String type;
    public String addressbookname;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressBookData)) return false;
        AddressBookData that = (AddressBookData) o;
        return id == that.id && zip == that.zip && firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                address.equals(that.address) && city.equals(that.city) &&
                state.equals(that.state) && phoneNumber.equals(that.phoneNumber) &&
                email.equals(that.email) && type.equals(that.type) && addressbookname.equals(that.addressbookname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, city, state, zip, phoneNumber, email, type, addressbookname);
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
