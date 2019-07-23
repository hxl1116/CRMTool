<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Profile</title>
    <style type="text/css"> <#include "style.css"> </style>
</head>
<body>
<div id="form-container">
    <form name="create-customer-form">
        <h1>Customer</h1>
        <h4>Fill out the fields below.</h4>
        <div>
            <input name="id" placeholder="ID" type="text" tabindex="1" required autofocus>
        </div>
        <div>
            <input name="firstName" placeholder="Your first name" type="text" tabindex="2" required autofocus>
        </div>
        <div>
            <input name="lastName" placeholder="Your last name" type="text" tabindex="3" required autofocus>
        </div>
        <div>
            <input name="address" placeholder="Your address" type="text" tabindex="4" required autofocus>
        </div>
        <div>
            <input name="email" placeholder="Your Email Address" type="email" tabindex="5" required>
        </div>
        <div>
            <input name="age" placeholder="age" type="text" tabindex="6" required autofocus>
        </div>
        <div>
            <input name="gender" placeholder="gender" type="text" tabindex="7" required autofocus>
        </div>
        <div>
            <input name="profession" placeholder="profession" type="text" tabindex="8" required autofocus>
        </div>
        <div class="button">
            <button type="button" onclick="submitForm()">Submit</button>
        </div>
    </form>
<script type="text/javascript">
    function submitForm()
    {
        console.log("made it here");
        var data = {
            "id": document.getElementsByName("create-customer-form").item(0).elements.namedItem("id").value.toString(),
            "firstName": document.getElementsByName("create-customer-form").item(0).elements.namedItem("firstName").value.toString(),
            "lastName": document.getElementsByName("create-customer-form").item(0).elements.namedItem("lastName").value.toString(),
            "address": document.getElementsByName("create-customer-form").item(0).elements.namedItem("address").value.toString(),
            "email": document.getElementsByName("create-customer-form").item(0).elements.namedItem("email").value.toString(),
            "age": document.getElementsByName("create-customer-form").item(0).elements.namedItem("age").value.toString(),
            "gender": document.getElementsByName("create-customer-form").item(0).elements.namedItem("gender").value.toString(),
            "profession": document.getElementsByName("create-customer-form").item(0).elements.namedItem("profession").value.toString()
        };
        console.log(data);
        var xhr = new XMLHttpRequest();
        xhr.open("POST", '/customers/createCustomer',false);

        xhr.setRequestHeader("Content-Type", "application/json")
        xhr.send(JSON.stringify(data))

    }
</script>
</div>
</body>
</html>