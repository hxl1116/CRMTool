$(document).ready(() => {
    $.get(
        'http://localhost:4567/customers',
        (data, status, xhr) => {
            let customers = JSON.parse(xhr.responseText);
            for (let i = 0; i < customers.length; i++) {
                let customer = customers[i];
                $('#customer-list').append(
                    '<li>' +
                        '<div class="customer-box">' +
                            '<h3>' +
                                customer.id + ': ' + customer.lastName + ', ' + customer.firstName +
                            '</h3>' +
                            '<p>' +
                                customer.address + '<br>' +
                                customer.email + '<br>' +
                                customer.age + '<br>' +
                                customer.gender + '<br>' +
                                customer.profession +
                            '</p>' +
                        '</div>' +
                    '</li>'
                );
            }
        }
    );
});