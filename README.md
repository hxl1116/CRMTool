# Customer Relationship Management Tool (CRM Tool)

CRM Tool is a Java API used to access customer data from a database

## Project Status
CRM Tool currently exists as a desktop CRUD application, but will be updated to a local-machine, web-based, 
REST-full API. A product backlog is located under Projects here on GitHub.

## Installation
Clone the project into an IDE. For IntelliJ, open the Database tab on the right sidebar and add the customer.db by
selecting "Data Source from Path".

## Configuration
The config.xml file contains the database url and current customer ID for the project. If the database url connection is unsuccessful, the 
property may need to be modified for the user.

## Usage
Compile and run. Enter commands through HTTP Requests or standard input.

Running Local: http://localhost:4567/

If you add a Customer to the database and turn the program off, remember to
update the current_id field in the config.xml file located under src/main/resources/config.xml

```text
// Displays formatted customer data
get;id

// Adds a new customer to the database
add;firstName,lastName,address,email,age,gender,profession

// Updates an existing customer in the database
update;id,firstName,lastName,address,email,age,gender,profession

// Deletes a customer from the database
delete;id
```

## Contributing
This project is being worked on by [Yasiru Karunawansa](https://github.com/yasiru98), 
[Henry Larson](https://github.com/hxl1116), and [Griffin Seibold](https://github.com/gxs1619).

## License
[MIT](https://choosealicense.com/licenses/mit/)
