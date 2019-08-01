const userAction = 'delete';

$(document).ready(() => {
    let customerForm = $('#delete-customer-form');
    customerForm.submit((event) => {
        event.preventDefault();

        $.ajax({
            url: 'http://localhost:4567/customer/delete/' + $('#customer-id').val().toString(),
            type: 'DELETE',
            contentType: 'application/x-www-form-urlencoded',
            success: () => window.location = 'http://localhost:4567/customer/success/' + userAction,
            error: () => window.location = 'http://localhost:4567/customer/error/' + userAction
        })
    })
});