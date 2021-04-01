package com.bridgelabs.com;

import java.time.LocalDate;
import java.util.HashMap;
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


    public void addNewContactToDatabase(String firstName, String lastName, String address, String city, String state,
                                        int zip, String phoneNumber, String email, String type, String addressbookname) {
        contactList.add(addressBookDBService.addData(firstName, lastName, address, city, state, zip, phoneNumber, email, type, addressbookname));
    }

    public void addAddressBooks(List<AddressBookData> addressList) {
        Map<Integer, Boolean> addressAdditionStatus = new HashMap<>();
        addressList.forEach(addressData -> {
            Runnable task = () -> {
                try {
                    addressAdditionStatus.put(addressData.hashCode(), false);
                    System.out.println("Address being added: " + Thread.currentThread().getName());
                    this.addAddressData(addressData.firstName, addressData.lastName, addressData.address, addressData.city, addressData.state,
                            addressData.zip, addressData.phoneNumber, addressData.email, addressData.type, addressData.addressbookname);
                    addressAdditionStatus.put(addressData.hashCode(), true);
                    System.out.println("Address added: " + Thread.currentThread().getName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            Thread thread = new Thread(task, addressData.firstName);
            thread.start();
        });
        while (addressAdditionStatus.containsValue(false)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addAddressData(String firstName, String lastName, String address, String city, String state, int zip, String phoneNumber, String email, String type, String addressBookName) {
        contactList.add(addressBookDBService.addData(firstName, lastName, address, city, state, zip, phoneNumber, email, type, addressBookName));
    }

}



