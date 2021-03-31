package com.bridgelabs.com;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class AddressBookServiceTest {
    AddressBookDBService addressBookService;


    @BeforeEach
    public void setup() {
        addressBookService = new AddressBookDBService();
    }

    @Test
    public void givenAddressDB_WhenRetreived_ShouldReturnCount() {
        List<AddressBookData> addressList = addressBookService.read();
        System.out.println(addressList);
        Assertions.assertEquals(5, addressList.size());
    }

    @Test
    public void givenNewAddressForPerson_WhenUpdatedUsing_ShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readData();
        addressBookService.updateDetails("Rachana", "Belendur");
        System.out.println(addressBookService.contactList.get(2));
        boolean result = addressBookService.checkContactInSyncWithDB("Rachana");
        Assertions.assertTrue(result);
    }

}

