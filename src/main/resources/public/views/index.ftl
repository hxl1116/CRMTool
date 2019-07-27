<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Profile</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="php" href="../php/index.php">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="../js/index.js"></script>
</head>
<body>
<div id="form-container">
    <form id="customer_form" action="../php/index.php" method="post">
        <h1>Customer</h1>
        <h4>Fill out the fields below.</h4>
        <div>
            <label>
                <input name="firstName" placeholder="Your first name" type="text">
            </label>
        </div>
        <div>
            <label>
                <input name="lastName" placeholder="Your last name" type="text">
            </label>
        </div>
        <div>
            <label>
                <input name="address" placeholder="Your address" type="text">
            </label>
        </div>
        <div>
            <label>
                <input name="email" placeholder="Your Email Address" type="email">
            </label>
        </div>
        <div>
            <label>
                <input name="age" placeholder="age" type="text">
            </label>
        </div>
        <div>
            <label>
                <input name="gender" placeholder="gender" type="text">
            </label>
        </div>
        <div>
            <label>
                <input name="profession" placeholder="profession" type="text">
            </label>
        </div>
        <div class="button">
            <input type="submit" id="submit" value="Submit">
<#--            <button type="button" id="submit_btn">Submit</button>-->
        </div>
    </form>
</div>
</body>
</html>