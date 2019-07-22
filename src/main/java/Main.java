import Control.CustomerDOA;
import Model.Customer;
import Response.StandardResponse;
import Response.StatusResponse;
import com.google.gson.Gson;
import org.apache.log4j.BasicConfigurator;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;
import freemarker.template.Version;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static spark.Spark.*;

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
    public static void main(String[] args) throws IOException {
        loadProperties();

        customerDOA = new CustomerDOA(
                properties.getProperty(DATABASE_URL_PROPERTY),
                Integer.parseInt(properties.getProperty(CURRENT_ID_PROPERTY))
        );

        printUsage();

        while (flag) {
            BasicConfigurator.configure();

            Configuration config = new Configuration(new Version(2, 3, 23));
            config.setClassForTemplateLoading(Customer.class, "/views");

            get("/", Customer::message, new FreeMarkerEngine(config));
            /**
             * HTTP Request would look like: http://localhost:4567/customers  This gets all the customers in the database.
             */
            get("/customers", (req, res)->customerDOA.selectAllCustomers());

            /**
             * HTTP Request would look like: http://localhost:4567/customers/id  Where id is the customer you wish to retrieve.
             */
            get("/customers/:id", (req,res)->customerDOA.selectCustomer(Integer.parseInt(":id")));

            /**
             * HTTP Request would look like: http://localhost:4567/customers/createCustomer  Where the body would be a json object representing
             * the customer to be created. We need to add a system for creating ID's so the user does not create their own ID. Duplicate ID's
             * are going to get very messy.
             */
            post("/customers/createCustomer", (request, response) -> {
                System.out.println(request.body());
                response.type("application/json");
                System.out.println(request.body());
                Customer customer = new Gson().fromJson(request.body(), Customer.class);
                System.out.println(customer.toString());
                customerDOA.insertCustomer(customer);

                return new Gson()
                        .toJson(new StandardResponse(StatusResponse.SUCCESS));
            });

            /**
             * HTTP Request would look like: http://localhost:4567/customers/updateCustomer  Where the body would be a json object representing
             * the customer with updated information. ID's can be updated, but this should be removed in the future.
             */
            put("/customers/updateCustomer", (request, response) -> {
                response.type("application/json");
                Customer toEdit = new Gson().fromJson(request.body(), Customer.class);

                if(toEdit.getId() != 0){
                    customerDOA.updateCustomer(toEdit);
                }
                Customer editedCustomer = customerDOA.selectCustomer(toEdit.getId()).get(0);

                if (editedCustomer != null) {
                    return new Gson().toJson(
                            new StandardResponse(StatusResponse.SUCCESS,new Gson()
                                    .toJsonTree(editedCustomer)));
                } else {
                    return new Gson().toJson(
                            new StandardResponse(StatusResponse.ERROR,new Gson()
                                    .toJson("Customer not found or error in edit")));
                }
            });

            /**
             * HTTP Request would look like: http://localhost:4567/customers/deleteCustomer/id  Where id of the customer you wish to delete
             */
            delete("/customers/deleteCustomer/:id", (request, response) -> {
                response.type("application/json");
                customerDOA.deleteCustomer(Integer.parseInt(request.params(":id")));
                return new Gson().toJson(
                        new StandardResponse(StatusResponse.SUCCESS, "user deleted"));
            });

            String input = scanner.nextLine();
            if (input.contains(";")) {
                String command = input.substring(0, input.indexOf(";"));
                String parameters = input.substring(input.indexOf(";") + 1);
                String[] data;
                switch (command) {
                    case "add":
                        data = parameters.split(",");
                        Customer newCustomer = new Customer(customerDOA.getCurrentID(), data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), data[5], data[6]);
                        customerDOA.insertCustomer(newCustomer);
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
                        Customer customer = new Customer(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), data[6], data[7]);
                        customerDOA.updateCustomer(customer);
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
    private String renderContent(String htmlFile) {
        try {
            // If you are using maven then your files
            // will be in a folder called resources.
            // getResource() gets that folder
            // and any files you specify.
            URL url = getClass().getResource(htmlFile);

            // Return a String which has all
            // the contents of the file.
            Path path = Paths.get(url.toURI());
            return new String(Files.readAllBytes(path), Charset.defaultCharset());
        } catch (IOException | URISyntaxException e) {
            System.out.println("ERROR: Invalid URL.");
        }
        return null;
    }
}
