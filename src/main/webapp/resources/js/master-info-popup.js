$('.master-info-img').on("click", function (event) {
    if ($('#mastersListId').val()) {
        var masterId = $('#mastersListId').children(":selected").attr("id");

        $.ajax({
            type: "GET",
            url: "/masterPopup",
            data: "masterId=" + masterId,
            dataType: 'json',
            success: function (master) {
                $("#modal-form").append(
                    '<div id="master-info"><img class="user-photo popup-left-part" src="' + master.photo + '"/>' +
                    '<div class="popup-right-part">' +
                    '<p>' + master.firstname + ' ' + master.lastname + '</p>' +
                    'Organization:<br><p>' + master.business + '</p>' +
                    '<p>Working time:<br>' + master.starttime + ':00 - ' + (master.starttime + 9) + ':00</p>' +
                    '<p>Phone: ' + master.phone + '</p>' +
                    '<p>Experience: ' + master.experience + ' years</p>' +
                    '<p>' + master.description + '</p></div></div>'
                );
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });

        event.preventDefault();
        $('#overlay').fadeIn(400,
            function () {
                $('#modal-form')
                    .css('display', 'block')
                    .animate({opacity: 1, top: '50%'}, 200);
            });
    }
});

$('#modal-close, #overlay').on("click", function () {
    $('#modal-form').find("#master-info").remove();
    $('#modal-form')
        .animate({opacity: 0, top: '45%'}, 200,
            function () {
                $(this).css('display', 'none');
                $('#overlay').fadeOut(400);
            }
        );
});
