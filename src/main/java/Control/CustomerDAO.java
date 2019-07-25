package Control;

import Model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * CustomerDAO is the data access object for the customer database.
 *
 * @author Henry Larson
 */
public class CustomerDAO {
    // Driver prefix for database url
    private static final String DATABASE_URL_PREFIX = "jdbc:sqlite:";

    // SELECT query for all customers
    private static final String SELECT_ALL_CUSTOMERS_QUERY = "SELECT * FROM Customer";
    // SELECT query for specified customers
    private static final String SELECT_CUSTOMER_QUERY = "SELECT * FROM Customer WHERE ID IN (%s)";
    // INSERT query for a customer
    private static final String INSERT_CUSTOMER_QUERY = "INSERT INTO Customer(ID, FirstName, LastName, Address, Email, Age, Gender, Profession) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    // UPDATE query for a customer
    private static final String UPDATE_CUSTOMER_QUERY = "UPDATE Customer SET FirstName = ?, LastName = ?, Address = ?, Email = ?, Age = ?, Gender = ?, Profession = ? WHERE ID = ?";
    // DELETE query for a customer
    private static final String DELETE_CUSTOMER_QUERY = "DELETE FROM Customer WHERE ID = ?";

    // The local database url
    private String databaseURL;
    // The current unique customer ID
    private int currentID;

    /**
     * Creates a new <code>CustomerDAO</code> data access object.
     *
     * @param databaseURL the local database url.
     * @param currentID   the current unique customer ID.
     */
    public CustomerDAO(String databaseURL, int currentID) {
        this.databaseURL = databaseURL;
        this.currentID = currentID;
    }

    /**
     * Gets the <code>Connection</code> to the local database.
     *
     * @return the <code>Connection</code> to the local database.
     * @throws SQLException the exception thrown when connection fails.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL_PREFIX + databaseURL);
    }

    /**
     * Performs a selection query on the local database.
     *
     * @return the list of <code>Customer</code> objects.
     */
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

    /**
     * Performs a selection query on the local database with specific ID's
     *
     * @param id the variable array of customer ID's
     * @return the list of <code>Customer</code> objects
     */
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

    /**
     * Performs an insertion query on the local database.
     *
     * @param customer an object representing a customer
     */
    public String insertCustomer(Customer customer) {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_CUSTOMER_QUERY)) {
            statement.setInt(1, currentID);
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getLastName());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getEmail());
            statement.setInt(6, customer.getAge());
            statement.setString(7, customer.getGender());
            statement.setString(8, customer.getProfession());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            currentID++;
        }
        return "Successfully added Customer.";
    }

    /**
     * Performs an update query on the local database.
     *
     * @param customer object that represents a Customer.
     */
    // TODO: 7/16/2019 Refactor so that SQL statement is dynamically generated from non-null parameters.
    public void updateCustomer(Customer customer) {
        Customer selectedCustomer = selectCustomer(customer.getId()).get(0);

        if (customer.getFirstName().equals("")) customer.setFirstName(selectedCustomer.getFirstName());
        if (customer.getLastName().equals("")) customer.setLastName(selectedCustomer.getLastName());
        if (customer.getAddress().equals("")) customer.setAddress(selectedCustomer.getAddress());
        if (customer.getEmail().equals("")) customer.setEmail(selectedCustomer.getEmail());
        if (customer.getAge() == 0) customer.setAge(selectedCustomer.getAge());
        if (customer.getGender().equals("")) customer.setGender(selectedCustomer.getGender());
        if (customer.getProfession().equals("")) customer.setProfession(selectedCustomer.getProfession());

        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_CUSTOMER_QUERY)) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getEmail());
            statement.setInt(5, customer.getAge());
            statement.setString(6, customer.getGender());
            statement.setString(7, customer.getProfession());
            statement.setInt(8, customer.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs a deletion query on the local database.
     *
     * @param id the customer's ID.
     */
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

    /**
     * Gets the current unique customer ID.
     *
     * @return the current unique customer ID.
     */
    public int getCurrentID() {
        return currentID;
    }
}
