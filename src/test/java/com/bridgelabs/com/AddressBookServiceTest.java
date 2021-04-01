package com.bridgelabs.com;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
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

    @Test
    public void givenNewContact_WhenAdded_ShouldSyncWithDB() {
        addressBookService.addNewContactToDatabase("Mouni", "1", "Vijayvada", "Hyderabad", "Andra", 34567,
                "09876678901", "mouni@gmail.com", " Profession", "UST");
        boolean result = addressBookService.checkContactInSyncWithDB("Mouni");
        Assertions.assertTrue(result);
    }

    @Test
    public void givenMultipleAdressBook_WhenAdded_ShoulReturnCount() {
        AddressBookData[] addressArray = {new AddressBookData(10, "Rathnaraj",
                "99", "Kalasa", "Manglore", "Karnataka",
                47100, "224578962", "rathnaraj@gmail.com", "Family", "Beerleke"),
                new AddressBookData(11, "Shushan", "88", "Samsa", "Banglore",
                        "Karnataka", 10222, "784752698", "sushuhu@gmail.com",
                        "Friends", "Trip")};
        Instant threadStart = Instant.now();
        addressBookService.addAddressBooks(Arrays.asList(addressArray));
        Instant threadEnd = Instant.now();
        System.out.println("Duration with thread " + Duration.between(threadStart, threadEnd));
        Assertions.assertEquals(8, addressBookService.contactList.size());
    }

}


