const userAction = 'add';

$(document).ready(() => {
    let customerForm = $('#create-customer-form');
    customerForm.submit((event) => {
        event.preventDefault();

        let data = {
            'firstName': $('#first-name').val().toString(),
            'lastName': $('#last-name').val().toString(),
            'address': $('#address').val().toString(),
            'email': $('#email').val().toString(),
            'age': $('#age').val().toString(),
            'gender': $('#gender').val().toString(),
            'profession': $('#profession').val().toString(),
        };

        $.ajax({
            url: 'http://localhost:4567/customer/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: () => window.location = 'http://localhost:4567/customer/success/' + userAction,
            error: () => window.location = 'http://localhost:4567/customer/error/' + userAction
        });
    });
});