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
        <select id="mastersSettingsListId" class="form-control">
            <option value="0" disabled selected>-- Choose master --</option>
            <c:forEach items="${masters}" var="master">
                <option id="${master.id}">${master.user.firstname} ${master.user.lastname}</option>
            </c:forEach>
        </select>

        <div class="master-settings-description"></div>
        <div class="change-masters-data">
            <p>Working start time: <input id="master-starttime" type="text" class="form-control"/></p>
            <p>Working experience: <input id="master-experience" type="text" class="form-control"/></p>
            <p>Description: <textarea id="master-description" class="form-control" rows="5"></textarea></p>
            <button id="master-data-save-btn" class="btn btn-success">Save</button>
            <button id="master-data-cancel-btn" class="btn btn-warning">Cancel</button>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    var masterId;
    var starttime;
    var experience;
    var description;

    $("#mastersSettingsListId").change(function () {
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
                $(".master-settings-description").append('<img class="user-photo popup-left-part" src="' + master.photo + '"/>' +
                    '<div class="popup-right-part">' +
                    '<p>' + master.firstname + ' ' + master.lastname + '</p>' +
                    'Organization:<br><p>' + master.business + '</p>' +
                    '<p id="master-starttime-p">Working time:<br><span>' + starttime + ':00 - ' + (starttime + 9) + ':00</span></p>' +
                    '<p>Phone: ' + master.phone + '</p>' +
                    '<p id="master-experience-p"><span>Experience: ' + experience + ' years</span></p>' +
                    '<p id="master-description-p"><span>' + description + '<span></p></div>' +
                    '<div class="change-masters-data-btn"><button id="change-data" class="btn btn-default">Change data</button></div>');
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    });

    $(".master-settings-description").on("click", "#change-data", function () {
        $(this).css("display", "block");
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

    function clearMastersData() {
        $("#master-starttime").val('');
        $("#master-experience").val('');
        $("#master-description").val('');
        $("#change-data").css("display", "block");
        $(".change-masters-data").css("display", "none");
    }
</script>

</body>
</html>
