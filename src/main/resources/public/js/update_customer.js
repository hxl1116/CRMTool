const userAction = 'update';

$(document).ready(() => {
    $('#update-customer-form').submit((event) => {
        event.preventDefault();

        let customerID = $('#customer-id').val().toString();

        $('#update-form-btn').click(() => {
            window.location = 'http://localhost:4567/customer/update/' + customerID
        });

        $('#update-customer-btn').click(() => {
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
                url: 'http://localhost:4567/customer/update/' + customerID,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: () => window.location = 'http://localhost:4567/customer/success/' + userAction,
                error: () => window.location = 'http://localhost:4567/customer/error/' + userAction
            })
        })
    });
});