<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CRM Tool</title>
    <link rel="stylesheet" href="../../../css/style.css">
</head>
<body>
<header id="main-header">
    <div class="container">
        <h1>Customers</h1>
    </div>
</header>
<nav class="nav-bar">
    <div class="container">
        <ul>
            <li><a href="http://localhost:4567/">Home</a></li>
            <li><a href="http://localhost:4567/customer/add">Create Customer</a></li>
            <li><a href="#">Update Customer</a></li>
            <li><a href="#">Delete Customer</a></li>
        </ul>
    </div>
</nav>
<section id="main-section">
    <div class="container">
        <h2>Customers</h2>
        <#list customers as customer>
            <ul id="customer-list">
                <li id="customer-item">
                    <div id="customer-block">
                        <h3>${customer.id}: ${customer.lastName}, ${customer.firstName}</h3>
                        <p>
                            Address: ${customer.address}<br>
                            Email: ${customer.email}<br>
                            Age: ${customer.age}<br>
                            Gender: ${customer.gender}<br>
                            Profession: ${customer.profession}<br>
                        </p>
                    </div>
                </li>
            </ul>
        </#list>
    </div>
</section>
</body>
</html>