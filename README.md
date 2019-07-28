# Customer Relationship Management Tool (CRM Tool)

CRM Tool is a Java API used to access customer data from a database.

## Project Status
CRM Tool currently exists as a desktop CRUD application, but will be updated to a local-machine, web-based, 
REST-ful API. A product backlog is located under Projects here on GitHub.

## Installation
Clone the project into an IDE. For IntelliJ, open the Database tab on the right sidebar and add the customer.db by
selecting "Data Source from Path".

For Windows, run the setup-db.bat file in resources/db-setup. This will create the CRMTool folder in the local user
Documents directory as well as the customers.db with the proper schema.

## Configuration
The config.xml file contains the database url and current customer ID for the project. If the database url connection is 
unsuccessful, the property can be modified by the user.

## Usage
Compile and run. The Spark jetty runs on [localhost](http://localhost:4567/) with the default port, <em>4567</em>. 
Enter commands through standard input or HTTP routes.

```text
/* I/O Commands */

// Displays formatted customer data
get;id

// Adds a new customer to the database
add;firstName,lastName,address,email,age,gender,profession

// Updates an existing customer in the database
update;id,firstName,lastName,address,email,age,gender,profession

// Deletes a customer from the database
delete;id

/* Routes */

// Default route, displays the customer creation form
http://localhost:4567/

// Customer creation success route
http://localhost:4567/success

// Customer creation error route
http://localhost:4567/error

// Customers display route
http://localhost:4567/customers/show

// Customer display route
http://localhost:4567/show/:id

// Customer creation route
http://localhost:4567/create

// Customer information update route
http://localhost:4567/update/:id

// Customer deletion route
http://localhost:4567/delete/:id
```

## Contributing
This project is being worked on by [Yasiru Karunawansa](https://github.com/yasiru98), 
[Henry Larson](https://github.com/hxl1116), and [Griffin Seibold](https://github.com/gxs1619).

## License
[MIT](https://choosealicense.com/licenses/mit/)
