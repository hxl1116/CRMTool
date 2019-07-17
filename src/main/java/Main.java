import Control.CustomerDOA;
import Model.Customer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Main is the driver class for the CRM Tool API
 *
 * @author Henry Larson
 */
public class Main {
    // Configuration variables
    private static final String CONFIG_FILE = "src/main/resources/config.xml";
    private static final String PROPERTIES_COMMENT = "Properties";
    private static final String DATABASE_URL_PROPERTY = "database_url";
    private static final String CURRENT_ID_PROPERTY = "current_id";

    // Scanner for user input
    private static final Scanner scanner = new Scanner(System.in);

    // Configuration properties
    private static Properties properties = new Properties();
    // Data access object
    private static CustomerDOA customerDOA;

    // Input loop flag
    private static boolean flag = true;

    /**
     * Loads configuration properties, instantiates the <code>CustomerDOA</code> data access object, and takes in user
     * input.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        loadProperties();

        customerDOA = new CustomerDOA(
                properties.getProperty(DATABASE_URL_PROPERTY),
                Integer.parseInt(properties.getProperty(CURRENT_ID_PROPERTY))
        );

        printUsage();

        while (flag) {
            String input = scanner.nextLine();
            if (input.contains(";")) {
                String command = input.substring(0, input.indexOf(";"));
                String parameters = input.substring(input.indexOf(";") + 1);
                String[] data;
                switch (command) {
                    case "add":
                        data = parameters.split(",");
                        customerDOA.insertCustomer(
                                data[0],
                                data[1],
                                data[2],
                                data[3],
                                Integer.parseInt(data[4]),
                                data[5],
                                data[6]
                        );
                        break;
                    case "get":
                        ArrayList<Customer> selectedCustomers;

                        if (!parameters.contains(",")) {
                            if (parameters.equals("*") || parameters.equals("all")) {
                                selectedCustomers = customerDOA.selectAllCustomers();
                            } else {
                                selectedCustomers = customerDOA.selectCustomer(Integer.parseInt(parameters));
                            }
                        } else {
                            int[] ids = new int[parameters.split(",").length];
                            for (int i = 0; i < ids.length; i++) {
                                ids[i] = Integer.parseInt(parameters.split(",")[i]);
                            }

                            selectedCustomers = customerDOA.selectCustomer(ids);
                        }

                        for (Customer customer : selectedCustomers) {
                            System.out.println(customer.toString());
                        }
                        break;
                    case "update":
                        data = parameters.split(",");
                        customerDOA.updateCustomer(
                                Integer.parseInt(data[0]),
                                data[1],
                                data[2],
                                data[3],
                                data[4],
                                data[5],
                                data[6],
                                data[7]
                        );
                        break;
                    case "delete":
                        if (parameters.matches("[0-9]+")) customerDOA.deleteCustomer(Integer.parseInt(parameters));
                        else System.err.println("Invalid customer ID: " + parameters);
                        break;
                    case "exit":
                        flag = false;
                        saveProperties();
                        break;
                    default:
                        printCommandError(input);
                        printUsage();
                }
            } else {
                printCommandError(input);
                printUsage();
            }
        }
    }

    /**
     * Loads configuration properties from the config.xml file.
     */
    private static void loadProperties() {
        try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE)) {
            properties.loadFromXML(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves configuration properties to the config.xml file.
     */
    private static void saveProperties() {
        try (FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE)) {
            properties.setProperty("current_id", Integer.toString(customerDOA.getCurrentID()));
            properties.storeToXML(outputStream, PROPERTIES_COMMENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints an error message for an unknown command.
     *
     * @param input the unknown command.
     */
    private static void printCommandError(String input) {
        System.err.println("Unknown command: " + input);
    }

    /**
     * Prints the usage for standard input to access the API.
     */
    private static void printUsage() {
        System.out.println("Usage: command;parameters (comma separated)");
    }
}
