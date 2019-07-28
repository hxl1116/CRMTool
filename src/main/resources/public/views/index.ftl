<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Profile</title>
    <link rel="stylesheet" href="../styles/style.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="../js/index.js"></script>
</head>
<body>
<div>
    <form id="customer-form">
        <h1>Customer</h1>
        <h4>Fill out the fields below.</h4>
        <div>
            <input name="firstName" placeholder="Your first name" type="text">
        </div>
        <div>
            <input name="lastName" placeholder="Your last name" type="text">
        </div>
        <div>
            <input name="address" placeholder="Your address" type="text">
        </div>
        <div>
            <input name="email" placeholder="Your Email Address" type="email">
        </div>
        <div>
            <input name="age" placeholder="age" type="text">
        </div>
        <div>
            <input name="gender" placeholder="gender" type="text">
        </div>
        <div>
            <input name="profession" placeholder="profession" type="text">
        </div>
        <div class="button">
            <button type="button" id="submit-btn">Submit</button>
        </div>
    </form>
</div>
</body>
</html>