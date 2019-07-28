$(document).ready(function () {
    let customerForm = $('#customer-form');
    customerForm.submit(function (event) {
        event.preventDefault();

        let data = {
            'firstName': $('#first-name').val().toString(),
            'lastName': $('#last-name').val().toString(),
            'address': $('#address').val().toString(),
            'email': $('#email').val().toString(),
            'age': $('#age').val().toString(),
            'gender': $('#gender').val().toString(),
            'profession': $('#profession').val().toString()
        };

        $.post(
            'http://localhost:4567/customer/add',
            JSON.stringify(data),
            function (data, status) {
                if (status === 'success') location.replace('http://localhost:4567customer/success');
                else location.replace('http://localhost:4567/customer/error')
            }
        )
    });
});