package com.bridgelabs.com;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AddressBookService {
    public List<AddressBookData> contactList;
    private final AddressBookDBService addressBookDBService;

    public AddressBookService() {
        addressBookDBService = AddressBookDBService.getInstance();
    }

    @SuppressWarnings("unused")
    //constructor
    public AddressBookService(List<AddressBookData> contactList, AddressBookDBService addressBookDBService) {
        this.contactList = contactList;
        this.addressBookDBService = addressBookDBService;
    }

    public List<AddressBookData> readData() {
        this.contactList = addressBookDBService.read();
        return contactList;
    }

    public void updateDetails(String name, String address) {
        int result = addressBookDBService.updateEmployeeData(name, address);
        if (result == 0)
            return;
        AddressBookData personInfo = this.getContactData(name);
        if (personInfo != null)
            personInfo.address = address;
    }

    private AddressBookData getContactData(String firstName) {
        return this.contactList.stream().filter(contact -> contact.firstName.equals(firstName)).findFirst().orElse(null);
    }

    public boolean checkContactInSyncWithDB(String firstName) {
        List<AddressBookData> contactList = addressBookDBService.getDataByName(firstName);
        return contactList.get(0).equals(getContactData(firstName));
    }

    public Map<String, Integer> readByCityOrState() {
        return addressBookDBService.getDetailsByCityOrState();
    }


}
