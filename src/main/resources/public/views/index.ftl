<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Profile</title>
    <link href="../styles/style.css" rel="stylesheet" />
</head>
<body>
<div id="form-container">
    <form name="create-customer-form">
        <h1>Customer</h1>
        <h4>Fill out the fields below.</h4>
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
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript">
    function submitForm()
    {
        var data = {
            "firstName": document.getElementsByName("create-customer-form").item(0).elements.namedItem("firstName").value.toString(),
            "lastName": document.getElementsByName("create-customer-form").item(0).elements.namedItem("lastName").value.toString(),
            "address": document.getElementsByName("create-customer-form").item(0).elements.namedItem("address").value.toString(),
            "email": document.getElementsByName("create-customer-form").item(0).elements.namedItem("email").value.toString(),
            "age": document.getElementsByName("create-customer-form").item(0).elements.namedItem("age").value.toString(),
            "gender": document.getElementsByName("create-customer-form").item(0).elements.namedItem("gender").value.toString(),
            "profession": document.getElementsByName("create-customer-form").item(0).elements.namedItem("profession").value.toString()
        };
        $.ajax({
            url: '/customers/createCustomer',
            data: JSON.stringify(data),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                console.log(data['status']);
                location.href = "/success"
            },
            failure: function (errMsg) {
                console.log(errMsg);
                location.href = "/error"
            }
        });
    }
</script>
</div>
</body>
</html>