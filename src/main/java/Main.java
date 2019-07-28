import Control.CustomerDAO;
import Model.Customer;
import Response.StandardResponse;
import Response.StatusResponse;
import com.google.gson.Gson;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import spark.ModelAndView;
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
    // Main class logger
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    // DAO configuration variables
    private static final String CONFIG_FILE = "src/main/resources/config.xml";
    private static final String PROPERTIES_COMMENT = "Properties";
    private static final String DATABASE_URL_PROPERTY = "database_url";
    private static final String CURRENT_ID_PROPERTY = "current_id";
    private static Properties properties = new Properties();

    // Freemarker configuration variables
    private static final Version CONFIG_VERSION = Configuration.VERSION_2_3_26;
    private static final Configuration CONFIGURATION = new Configuration(CONFIG_VERSION);
    private static final String TEMPLATE_DIRECTORY = "/public/views/";

    // Scanner for user input
    private static final Scanner scanner = new Scanner(System.in);

    // Data access object
    private static CustomerDAO customerDAO;

    // Input loop flag
    private static boolean flag = true;

    /**
     * Loads configuration properties, instantiates the <code>CustomerDAO</code> data access object, and takes in user
     * input.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        configureDAO();
        configureLogger();
        configureFreemarker();
        printUsage();

        // Gets the landing page
        // TODO: 7/28/2019 Change view to home.html when built.
        get("/", (request, response) -> new FreeMarkerEngine(CONFIGURATION).render(
                new ModelAndView(new HashMap<String, Object>(), "index.html")
        ));

        // Gets the index page
        get("/index", (request, response) -> new FreeMarkerEngine(CONFIGURATION).render(
                new ModelAndView(new HashMap<String, Object>(), "index.html")
        ));

        // Customer paths
        path("/customer", () -> {
            path("/show", () -> {
                get("/all", (request, response) -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("customers", customerDAO.selectAllCustomers());

                    response.status(StatusResponse.OK.getCode());
                    return new FreeMarkerEngine(CONFIGURATION).render(
                            new ModelAndView(model, "customer/show/show_customer.ftl")
                    );
                });
                get("/:id", (request, response) -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("customers", customerDAO.selectCustomer(Integer.parseInt(request.params("id"))));

                    response.status(StatusResponse.OK.getCode());
                    return new FreeMarkerEngine(CONFIGURATION).render(
                            new ModelAndView(model, "customer/show/show_customer.ftl")
                    );
                });
            });
            // TODO: 7/28/2019 Get new customer and display on success page.
            get("/add", (request, response) ->
                    new FreeMarkerEngine(CONFIGURATION).render(new ModelAndView(
                            new HashMap<String, Object>(),
                            "customer/create_customer.html"
                    )));
            post("/add", (request, response) -> {
                Customer customer = new Gson().fromJson(request.body(), Customer.class);
                customerDAO.insertCustomer(customer);

                response.type("application/json");
                response.status(StatusResponse.CREATED.getCode());
                return new Gson().toJson("Customer created.");
            });
            // TODO: 7/28/2019 Update selected customer in database.
            put("/update/:id", (request, response) -> {
                response.type("application/json");
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.NOT_IMPLEMENTED,
                        "Customer information updating has note been implemented."
                ));
            });
            delete("/delete/:id", (request, response) -> {
                customerDAO.deleteCustomer(Integer.parseInt(request.params("id")));

                return new Gson().toJson(new StandardResponse(
                        StatusResponse.NO_CONTENT,
                        "Customer deleted."
                ));
            });
            get("/success", (request, response) -> {
                response.status(StatusResponse.OK.getCode());
                return new FreeMarkerEngine(CONFIGURATION).render(
                        new ModelAndView(new HashMap<String, Object>(), "customer/success.html")
                );
            });
            get("/error", (request, response) -> {
                response.status(StatusResponse.INTERNAL_SERVER_ERROR.getCode());
                return new FreeMarkerEngine(CONFIGURATION).render(
                        new ModelAndView(new HashMap<String, Object>(), "customer/error.html")
                );
            });
            // Saves the current_id property after each request
            after((request, response) -> saveProperties());
        });

        while (flag) {
            String input = scanner.nextLine();
            if (input.contains(";")) {
                String command = input.substring(0, input.indexOf(";"));
                String parameters = input.substring(input.indexOf(";") + 1);
                String[] data;
                switch (command) {
                    case "add":
                        data = parameters.split(",");
                        Customer newCustomer = new Customer(
                                customerDAO.getCurrentID(),
                                data[0],
                                data[1],
                                data[2],
                                data[3],
                                Integer.parseInt(data[4]),
                                data[5],
                                data[6]
                        );
                        customerDAO.insertCustomer(newCustomer);
                        saveProperties();
                        break;
                    case "get":
                        ArrayList<Customer> selectedCustomers;

                        if (!parameters.contains(",")) {
                            if (parameters.equals("*") || parameters.equals("all")) {
                                selectedCustomers = customerDAO.selectAllCustomers();
                            } else {
                                selectedCustomers = customerDAO.selectCustomer(Integer.parseInt(parameters));
                            }
                        } else {
                            int[] ids = new int[parameters.split(",").length];
                            for (int i = 0; i < ids.length; i++) {
                                ids[i] = Integer.parseInt(parameters.split(",")[i]);
                            }

                            selectedCustomers = customerDAO.selectCustomer(ids);
                        }

                        for (Customer customer : selectedCustomers) {
                            System.out.println(customer.toString());
                        }
                        break;
                    case "update":
                        data = parameters.split(",");
                        Customer customer = new Customer(
                                Integer.parseInt(data[0]),
                                data[1],
                                data[2],
                                data[3],
                                data[4],
                                Integer.parseInt(data[5]),
                                data[6],
                                data[7]
                        );
                        customerDAO.updateCustomer(customer);
                        break;
                    case "delete":
                        if (parameters.matches("[0-9]+")) customerDAO.deleteCustomer(
                                Integer.parseInt(parameters)
                        );
                        else System.err.println("Invalid customer ID: " + parameters);
                        saveProperties();
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
    private static void configureDAO() {
        try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE)) {
            properties.loadFromXML(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            customerDAO = new CustomerDAO(
                    properties.getProperty(DATABASE_URL_PROPERTY),
                    Integer.parseInt(properties.getProperty(CURRENT_ID_PROPERTY))
            );
        }
    }

    private static void configureFreemarker() {
        staticFileLocation("/public");

        CONFIGURATION.setClassForTemplateLoading(Main.class, TEMPLATE_DIRECTORY);
        CONFIGURATION.setObjectWrapper(new DefaultObjectWrapper(CONFIG_VERSION));
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CONFIGURATION.setLogTemplateExceptions(false);
    }

    private static void configureLogger() {
        LOGGER.addAppender(new ConsoleAppender());
        BasicConfigurator.configure();
    }

    /**
     * Saves configuration properties to the config.xml file.
     */
    private static void saveProperties() {
        try (FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE)) {
            properties.setProperty("current_id", Integer.toString(customerDAO.getCurrentID()));
            properties.storeToXML(outputStream, PROPERTIES_COMMENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the usage for standard input to access the API.
     */
    private static void printUsage() {
        System.out.println("Usage: command;parameters (comma separated)");
    }

    /**
     * Prints an error message for an unknown command.
     *
     * @param input the unknown command.
     */
    private static void printCommandError(String input) {
        System.err.println("Unknown command: " + input);
    }
}
