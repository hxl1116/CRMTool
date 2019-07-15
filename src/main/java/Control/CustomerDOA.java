package Control;

import Model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerDOA {
    private static final String DATABASE_URL_PREFIX = "jdbc:sqlite:";

    private static final String SELECT_ALL_CUSTOMERS_QUERY = "SELECT * FROM Customer";
    private static final String SELECT_CUSTOMER_QUERY = "SELECT * FROM Customer WHERE ID IN (%s)";
    private static final String INSERT_CUSTOMER_QUERY = "INSERT INTO Customer(ID, FirstName, LastName, Address, Email, Age, Gender, Profession) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER_QUERY = "UPDATE Customer SET FirstName = ?, LastName = ?, Address = ?, Email = ?, Age = ?, Gender = ?, Profession = ? WHERE ID = ?";
    private static final String DELETE_CUSTOMER_QUERY = "DELETE FROM Customer WHERE ID = ?";

    private String databaseURL;
    private int currentID;

    public CustomerDOA(String databaseURL, int currentID) {
        this.databaseURL = databaseURL;
        this.currentID = currentID;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL_PREFIX + databaseURL);
    }

    public ArrayList<Customer> selectAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_CUSTOMERS_QUERY)) {
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("ID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Address"),
                        resultSet.getString("Email"),
                        resultSet.getInt("Age"),
                        resultSet.getString("Gender"),
                        resultSet.getString("Profession")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public ArrayList<Customer> selectCustomer(int... id) {
        ArrayList<Customer> selectedCustomers = new ArrayList<>();

        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(String.format(
                     SELECT_CUSTOMER_QUERY,
                     Arrays.toString(id).substring(1, Arrays.toString(id).length() - 1)
             ))
        ) {
            while (resultSet.next()) {
                selectedCustomers.add(new Customer(
                        resultSet.getInt("ID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Address"),
                        resultSet.getString("Email"),
                        resultSet.getInt("Age"),
                        resultSet.getString("Gender"),
                        resultSet.getString("Profession")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return selectedCustomers;
    }

    public void insertCustomer(
            String firstName,
            String lastName,
            String address,
            String email,
            int age,
            String gender,
            String profession
    ) {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_CUSTOMER_QUERY)) {
            statement.setInt(1, currentID);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, address);
            statement.setString(5, email);
            statement.setInt(6, age);
            statement.setString(7, gender);
            statement.setString(8, profession);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            currentID++;
        }
    }

    public void updateCustomer(
            int id,
            String firstName,
            String lastName,
            String address,
            String email,
            int age,
            String gender,
            String profession
    ) {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_CUSTOMER_QUERY)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, address);
            statement.setString(4, email);
            statement.setInt(5, age);
            statement.setString(6, gender);
            statement.setString(7, profession);
            statement.setInt(8, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int id) {
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_CUSTOMER_QUERY)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (currentID == id) currentID--;
        }
    }

    public int getCurrentID() {
        return currentID;
    }
}
