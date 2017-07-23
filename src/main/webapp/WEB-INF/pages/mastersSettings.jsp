<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Master's settings</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
    </style>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<div class="content">

    <div class="master-settings-info">
        <select id="masters-settings-list" class="form-control">
            <option disabled selected>-- Choose master --</option>
            <c:forEach items="${masters}" var="master">
                <option id="${master.id}">${master.user.firstname} ${master.user.lastname}</option>
            </c:forEach>
        </select>
        <select id="add-masters-list" class="form-control">
            <option id="0" value="not selected" selected>-- Add new master --</option>
            <c:forEach items="${mastersFree}" var="master">
                <option id="${master.id}">${master.user.firstname} ${master.user.lastname}</option>
            </c:forEach>
        </select>
        <button id="add-master-btn" class="btn btn-default">Add master</button>

        <div class="master-settings-description"></div>
        <div class="change-masters-data">
            <p>Working start time: <input id="master-starttime" type="text" class="form-control"/></p>
            <p>Working experience: <input id="master-experience" type="text" class="form-control"/></p>
            <p>Description: <textarea id="master-description" class="form-control" rows="5"></textarea></p>
            <button id="master-data-save-btn" class="btn btn-success">Save changes</button>
            <button id="master-data-cancel-btn" class="btn btn-warning">Cancel</button>
        </div>
    </div>
    <div class="master-service">
        <button id="service-master-btn" class="btn btn-default">Change services for master</button>
        <div class="services-for-master">
            <div id="service-checkboxes"></div>
            <input type="button" id="save-master-service" class="btn btn-success" value="Save changes">
            <input type="button" id="cancel-master-service" class="btn btn-warning" value="Cancel">
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    var masterId;
    var masterFreeId;
    var masterName;
    var starttime;
    var experience;
    var description;

    $("#masters-settings-list").change(function () {
        masterId = $(this).children(":selected").attr("id");
        $.ajax({
            type: "GET",
            url: "/masterPopup",
            data: "masterId=" + masterId,
            dataType: 'json',
            success: function (master) {
                starttime = master.starttime;
                experience = master.experience;
                description = master.description;
                $(".master-settings-description").children().remove();
                clearMastersData();
                $(".master-settings-description").append('<img class="user-photo popup-left-part" src="' + master.photo + '"/>' +
                    '<div class="popup-right-part">' +
                    '<p>' + master.firstname + ' ' + master.lastname + '</p>' +
                    'Organization:<br><p>' + master.business + '</p>' +
                    '<p id="master-starttime-p">Working time:<br><span>' + starttime + ':00 - ' + (starttime + 9) + ':00</span></p>' +
                    '<p>Phone: ' + master.phone + '</p>' +
                    '<p id="master-experience-p"><span>Experience: ' + experience + ' years</span></p>' +
                    '<p id="master-description-p"><span>' + description + '<span></p></div>' +
                    '<div class="change-masters-data-btn"><button id="change-data" class="btn btn-default">Change data</button></div>');
                $(".master-service").css("display", "block");
                clearMasterService();
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    });

    $("#add-masters-list").on("change", function () {
        masterFreeId = $(this).children(":selected").attr("id");
        masterName = $(this).children(":selected").html();
        if (masterFreeId !== "0")
            $("#add-master-btn").css("display", "block");
        else
            $("#add-master-btn").css("display", "none");
    });

    $("#add-master-btn").on("click", function () {
        $.ajax({
            type: "GET",
            url: "/addFreeMaster/${organizationId}/" + masterFreeId,
            success: function (master) {
                $("#add-masters-list").val("not selected");
                $("#add-master-btn").css("display", "none");
                $("#add-masters-list").find("option[id=" + masterFreeId + "]").remove();
                $("#masters-settings-list").append('<option id="' + masterFreeId + '">' + masterName + '</option>');
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });

    });

    $(".master-settings-description").on("click", "#change-data", function () {
        $(this).css("display", "none");
        $("#master-starttime").val(starttime);
        $("#master-experience").val(experience);
        $("#master-description").val(description);
        $(".change-masters-data").css("display", "block");
    });

    $("#master-data-save-btn").on("click", function () {
        starttime = $("#master-starttime").val();
        experience = $("#master-experience").val();
        description = $("#master-description").val();
        $.ajax({
            type: "GET",
            url: "/mastersDataChange/" + masterId,
            data: "starttime=" + starttime + "&experience=" + experience + "&description=" + description,
            success: function (master) {
                var starttimeP = $("#master-starttime-p");
                starttimeP.children('span').remove();
                starttimeP.append('<span>' + starttime + ':00 - ' + (parseInt(starttime) + 9) + ':00</span>');
                var experienceP = $("#master-experience-p");
                experienceP.children('span').remove();
                experienceP.append('<span>Experience: ' + experience + ' years</span>');
                var descriptionP = $("#master-description-p");
                descriptionP.children('span').remove();
                descriptionP.append('<span>' + description + '</span>');
                clearMastersData();
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    });

    $("#master-data-cancel-btn").on("click", function () {
        clearMastersData();
    });

    $("#service-master-btn").on("click", function () {
        $("#service-master-btn").css("display", "none");
        $(".services-for-master").css("display", "block");
        var checkboxes = $("#service-checkboxes");
        checkboxes.children().remove();
        $.ajax({
            type: "GET",
            url: "/serviceCheckBoxes/${organizationId}/" + masterId,
            dataType: 'json',
            success: function (service) {
                for (var i = 0; i < service.first.length; i++) {
                    checkboxes.append('<p><input type="checkbox" id="checkbox' + service.first[i].id +
                        '" value="' + service.first[i].id + '"/>' +
                        '<label for="checkbox' + service.first[i].id + '" class="label-for-checkbox">' +
                        '&nbsp;' + service.first[i].name + '</label></p>');
                    for (var j = 0; j < service.second.length; j++)
                        if (service.first[i].id === service.second[j].id)
                            $("#checkbox" + service.first[i].id).prop('checked', true);
                }
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });

    });

    $("#save-master-service").on("click", function () {
        var selected = [];
        var nonSelected = [];
        $('#service-checkboxes').find('input:checked').each(function () {
            selected.push($(this).val());
        });
        $('#service-checkboxes').find('input:not(:checked)').each(function () {
            nonSelected.push($(this).val());
        });
        $.ajax({
            type: "GET",
            url: "/changeMasterService/" + masterId,
            data: "selected=" + selected + "&nonSelected=" + nonSelected,
            success: function () {
                clearMasterService();
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        })
    });

    $("#cancel-master-service").on("click", function () {
        clearMasterService();
    });

    function clearMastersData() {
        $("#master-starttime").val('');
        $("#master-experience").val('');
        $("#master-description").val('');
        $("#change-data").css("display", "block");
        $(".change-masters-data").css("display", "none");
    }

    function clearMasterService() {
        $("#service-master-btn").css("display", "block");
        $(".services-for-master").css("display", "none");
    }
</script>

</body>
</html>
