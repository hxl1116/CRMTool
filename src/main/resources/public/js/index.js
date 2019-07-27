$(document).ready(function () {
    $("#submit").onsubmit = function (event) {
        $.ajax({
            url: 'customers/create',
            data: JSON.stringify($("#customer_form").serialize()),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                console.log(data['status'] + "\n" + data['message']);
                location.href = '/success'
            },
            failure: function (data) {
                console.log(data['status'] + "\n" + data['message']);
                location.href = '/error'
            }
        });
    }
});