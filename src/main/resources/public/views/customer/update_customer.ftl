<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CRM Tool</title>
    <link rel="stylesheet" href="../../css/style.css">
    <script src="../../js/update_customer.js"></script>
</head>
<body>
<header id="main-header">
    <div class="container">
        <h1>Update Customer</h1>
    </div>
</header>
<nav class="nav-bar">
    <div class="container">
        <ul>
            <li><a href="http://localhost:4567/">Home</a></li>
            <li><a href="http://localhost:4567/customer/show/all">Show Customers</a></li>
            <li><a href="http://localhost:4567/customer/add">Add Customer</a></li>
            <li><a href="http://localhost:4567/customer/delete">Delete Customer</a></li>
        </ul>
    </div>
</nav>
<div class="container">
    <form id="update-customer-form" class="customer-form">
        <#if customer??>
            <input type="hidden" id="customer-id" value="${customer.id}">
            <div class="form-group">
                <label for="first-name">First Name:</label>
                <input type="text" id="first-name" name="firstName" value="${customer.firstName}">
            </div>
            <div class="form-group">
                <label for="last-name">Last Name:</label>
                <input type="text" id="last-name" name="lastName" value="${customer.lastName}">
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="${customer.address}">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${customer.email}">
            </div>
            <div class="form-group">
                <label for="age">Age:</label>
                <input type="number" id="age" name="age" value="${customer.age}">
            </div>
            <div class="form-group">
                <label for="gender">Gender:</label>
                <datalist id="genders">
                    <option value="Male"></option>
                    <option value="Female"></option>
                    <option value="Other"></option>
                </datalist>
                <input list="genders" id="gender" name="gender" value="${customer.gender}">
            </div>
            <div class="form-group">
                <label for="profession">Profession:</label>
                <input type="text" id="profession" name="profession" value="${customer.profession}">
            </div>
            <div class="form-group">
                <label for="submit-btn"></label>
                <input type="submit" class="button" id="update-customer-btn" value="Submit">
            </div>
        <#else>
            <div class="form-group">
                <label for="customer-id">Customer ID:</label>
                <input type="number" id="customer-id" name="customerID" placeholder="100">
            </div>
            <div class="form-group">
                <label for="submit-btn"></label>
                <input type="submit" class="button" id="update-form-btn" value="Submit">
            </div>
        </#if>
    </form>
</div>
</body>
</html>