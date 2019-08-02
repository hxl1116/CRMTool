$(document).ready(() => {
    $('#show-customer-spinner').hide();
    $('#create-customer-form').hide();

    $('#show-customers-btn').click(() => showCustomers());
    $('#create-customer-btn').click(() => createCustomer());

    const showCustomers = () => {
        moveToCustomerSection();

        $('#create-customer-form').hide();
        $('#show-customer-spinner').show();

        setTimeout(() => {
            $('#show-customer-spinner').hide();
        }, 1500);

        getCustomers();
    };

    const createCustomer = () => {
        moveToCustomerSection();

        $('#create-customer-form').show()
    };

    const moveToCustomerSection = () => {
        let customerSection = $('#customer-section');
        $('html, body').animate({
            scrollTop: customerSection.offset().top
        }, 'slow');
    };

    const getCustomers = () => {
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
    };
});