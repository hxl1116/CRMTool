$(document).ready(() => {
    let customerForm = $('#customer-form');
    customerForm.submit((event) => {
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
            (data, status) => {
                if (status === 'success') window.location = 'http://localhost:4567/customer/success';
                else window.location = 'http://localhost:4567/customer/error'
            }
        )
    });
});