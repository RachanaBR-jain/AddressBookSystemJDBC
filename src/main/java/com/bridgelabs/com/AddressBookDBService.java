package com.bridgelabs.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

    private static AddressBookDBService addressDBService;

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

}

