package com.bridgelabs.com;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class AddressBookDBService {

    private static AddressBookDBService addressDBService;
    private PreparedStatement addressDataStatement;


    public AddressBookDBService() {

    }

    public static AddressBookDBService getInstance() {
        if (addressDBService == null)
            addressDBService = new AddressBookDBService();
        return addressDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbookservice?useSSL=false";
        String user = "root";
        String password = "beerleke";

        Connection connection;
        System.out.println("Connection to database: " + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, user, password);
        System.out.println("Connection is successful!!!!" + connection);
        return connection;
    }

    public List<AddressBookData> read() {
        String sql = "SELECT * FROM addressBookTable;";
        return this.readQuery(sql);
    }

    private List<AddressBookData> readQuery(String sql) {
        try (Connection connection = this.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            return this.getAddressList(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<AddressBookData> getAddressList(ResultSet result) {
        List<AddressBookData> addressDBList = new ArrayList<>();
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String address = result.getString("address");
                String city = result.getString("city");
                String state = result.getString("state");
                int zip = result.getInt("zip");
                String phoneNumber = result.getString("phoneNumber");
                String email = result.getString("email");
                String type = result.getString("type");
                String addressbookname = result.getString("addressbookname");


                addressDBList.add(new AddressBookData(id, firstName, lastName, address, city, state, zip,
                        phoneNumber, email, type, addressbookname));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressDBList;
    }

    public int updateEmployeeData(String name, String address) {
        return this.updateContactDataUsingPreparedStatement(name, address);
    }

    private int updateContactDataUsingPreparedStatement(String firstName, String address) {
        try (Connection connection = addressDBService.getConnection()) {
            String sql = "update addressbooktable set address=? where firstName=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, firstName);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public List<AddressBookData> getDataByName(String name) {
        List<AddressBookData> contactList = null;
        if (this.addressDataStatement == null)
            this.prepareStatementForContactData();
        try {
            addressDataStatement.setString(1, name);
            ResultSet resultSet = addressDataStatement.executeQuery();
            contactList = this.getAddressList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    private void prepareStatementForContactData() {
        try {
            Connection connection = addressDBService.getConnection();
            String sql = "SELECT * from  addressbooktable WHERE firstName=?; ";
            addressDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getDetailsByCityOrState() {
        Map<String, Integer> map = new HashMap<>();
        ResultSet resultSet;
        String countOfCity = "SELECT city, count(firstName) as count from addressbooktable group by city; ";
        String countOfState = "SELECT state, count(firstName) as count from addressbooktable group by state; ";
        try (Connection connection = addressDBService.getConnection()) {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(countOfCity);
            while (resultSet.next()) {
                String city = resultSet.getString("city");
                Integer count = resultSet.getInt("count");
                map.put(city, count);
            }
            resultSet = statement.executeQuery(countOfState);
            while (resultSet.next()) {
                String state = resultSet.getString("state");
                Integer count = resultSet.getInt("count");
                map.put(state, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }


    public AddressBookData addData(String firstName, String lastName, String address, String city, String state,
                                   int zip, String phoneNumber, String email, String type, String addressBookName) {
        int addressId = -1;
        Connection connection = null;
        AddressBookData addressData = null;
        try {
            connection = this.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = connection.createStatement()) {
            String sql = String.format(
                    "INSERT INTO addressbooktable (firstName,lastName,address,city,state,zip,phoneNumber,email,type,addressbookname) "
                            + "VALUES ('%s', %s, '%s','%s','%s',%s,%s,'%s','%s','%s')",
                    firstName, lastName, address, city, state, zip, phoneNumber, email, type, addressBookName);
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet result = statement.getGeneratedKeys();
                if (result.next())
                    addressId = result.getInt(1);

                addressData = new AddressBookData(addressId, firstName, lastName, address, city, state, zip, phoneNumber, email, type, addressBookName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addressData;
    }

}







