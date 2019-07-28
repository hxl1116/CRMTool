$(document).ready(function () {
    $('#submit-btn').click(function (event) {
        event.preventDefault();

        let customerForm = document.getElementById('customer-form');
        let data = {
            'firstName': customerForm.elements.namedItem('firstName').value.toString(),
            'lastName': customerForm.elements.namedItem('lastName').value.toString(),
            'address': customerForm.elements.namedItem('address').value.toString(),
            'email': customerForm.elements.namedItem('email').value.toString(),
            'age': customerForm.elements.namedItem('age').value.toString(),
            'gender': customerForm.elements.namedItem('gender').value.toString(),
            'profession': customerForm.elements.namedItem('profession').value.toString()
        };

        $.post(
            '/customers/create/submit',
            JSON.stringify(data),
            function (data, status) {
                if (status === 'success') location.replace('customers/create/success');
                else location.replace('customers/create/error')
            }
        )
    });
});