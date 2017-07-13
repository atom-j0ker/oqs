$(".delete-booking-btn").click(function () {
    var bookingId = $(this).closest('tr').attr('id');
    $.ajax({
        type: "GET",
        url: "/delete-booking/" + bookingId,
        success: $(this).closest("tr").remove(),
        error: function (xhr, textStatus) {
            alert([xhr.status, textStatus]);
        }
    })
})