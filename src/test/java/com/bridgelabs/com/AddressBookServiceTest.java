package com.bridgelabs.com;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class AddressBookServiceTest {
    AddressBookDBService addressBookDBService;
    AddressBookService addressBookService;


    @BeforeEach
    public void setup() {
        addressBookDBService = new AddressBookDBService();
        addressBookService = new AddressBookService();
        addressBookService.readData();
    }

    @Test
    public void givenAddressDB_WhenRetreived_ShouldReturnCount() {
        List<AddressBookData> addressList = addressBookDBService.read();
        System.out.println(addressList);
        Assertions.assertEquals(5, addressList.size());
    }

    @Test
    public void givenNewAddressForPerson_WhenUpdatedUsing_ShouldSyncWithDB() {
        addressBookService.updateDetails("Rachana", "Belendur");
        System.out.println(addressBookService.contactList.get(0));
        boolean result = addressBookService.checkContactInSyncWithDB("Rachana");
        Assertions.assertTrue(result);
    }

    @Test
    public void givenContacts_Retrieve_ByCityOrState() {
        Map<String, Integer> count = addressBookService.readByCityOrState();
        int countbyCity = count.get("Banglore");
        int countbyState = count.get("Karnataka");
        System.out.println(addressBookDBService.getDetailsByCityOrState());
        Assertions.assertEquals(countbyCity, 1);
        Assertions.assertEquals(countbyState, 3);
    }

}

