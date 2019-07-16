package Model;

/**
 * Customer is the object representation of a customer.
 *
 * @author Henry Larson
 */
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private int age;
    private String gender;
    private String profession;

    /**
     * Creates a new <code>Customer</code> object.
     *
     * @param id the customer unique ID.
     * @param firstName the customer's first name.
     * @param lastName the customer's last name.
     * @param address the customer's address.
     * @param email the customer's email.
     * @param age the customer's age.
     * @param gender the customer's gender.
     * @param profession the customer's profession.
     */
    public Customer(
            int id,
            String firstName,
            String lastName,
            String address,
            String email,
            int age,
            String gender,
            String profession
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.profession = profession;
    }

    /**
     * Gets a string representation of a <code>Customer</code>
     *
     * Customer ID: <code>id</code>
     *      <code>firstName</code>
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("Customer ID: %d\n\t%s, %s, %d, %s\n\t%s, %s, %s",
                id, firstName, lastName, age, gender, address, email, profession);
    }
}
