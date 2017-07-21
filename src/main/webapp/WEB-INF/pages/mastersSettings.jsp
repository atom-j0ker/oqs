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

    <select id="mastersListId" class="form-control">
        <option value="0" disabled selected>-- Choose master --</option>
        <c:forEach items="${masters}" var="master">
            <option id="${master.id}">${master.user.firstname} ${master.user.lastname}</option>
        </c:forEach>
    </select>

    <div class="master-info"></div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    $("#mastersListId").change(function () {
        var masterId = $(this).children(":selected").attr("id");
        alert(masterId);
        $.ajax({
            type: "GET",
            url: "/masterPopup",
            data: "masterId=" + masterId,
            dataType: 'json',
            success: function (master) {
                $(".master-info").append('<div id="master-info"><img class="user-photo popup-left-part" src="' + master.photo + '"/>' +
                    '<div class="popup-right-part">' +
                    '<p>' + master.firstname + ' ' + master.lastname + '</p>' +
                    'Organization:<br><p>' + master.business + '</p>' +
                    '<p>Working time:<br>' + master.starttime + ':00 - ' + (master.starttime + 9) + ':00</p>' +
                    '<p>Phone: ' + master.phone + '</p>' +
                    '<p>Experience: ' + master.experience + ' years</p>' +
                    '<p>' + master.description + '</p></div></div>');
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    });
</script>

</body>
</html>
