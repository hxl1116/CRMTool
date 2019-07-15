package Model;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private int age;
    private String gender;
    private String profession;

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

    @Override
    public String toString() {
        return String.format("Customer ID: %d\n\t%s, %s, %d, %s\n\t%s, %s, %s",
                id, firstName, lastName, age, gender, address, email, profession);
    }
}
