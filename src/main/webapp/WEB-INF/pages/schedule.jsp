<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>schedule</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
    </style>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<div class="content">
    <table id="business-schedule-table" class="table">
        <tr>
            <th>#</th>
            <th>Client</th>
            <th>Client email</th>
            <th>Service</th>
            <th>Master</th>
            <th>Date</th>
            <th>Time</th>
            <th>Status</th>
        </tr>
        <c:forEach items="${schedule}" var="sch">
            <tr>
                <td>${sch.id}</td>
                <td>${sch.user.firstname} ${sch.user.lastname}</td>
                <td>${sch.user.email}</td>
                <td>${sch.service.name}
                    <img class="info-img" src="/resources/img/information.png" title="${sch.comment}"/></td>
                <td>${sch.master.user.firstname} ${sch.master.user.lastname}</td>
                <td><fmt:formatDate value="${sch.date}" pattern="dd-MM-yyyy"/></td>
                <td>${sch.time}</td>
                <td>
                    <select id="booking${sch.id}" class="form-control status">
                        <option id="1" <c:if test="${sch.status.id==1}">selected</c:if>>Waiting</option>
                        <option id="2" <c:if test="${sch.status.id==2}">selected</c:if>>Visited</option>
                        <option id="3" <c:if test="${sch.status.id==3}">selected</c:if>>Not visited</option>
                    </select>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    $(".status").change(function () {
        var statusId = $(this).children(":selected").attr("id");
        var bookingId = $(this).attr("id");
        bookingId = bookingId.substring(7, bookingId.length);
        $.ajax({
            type: "GET",
            url: '/changeStatus',
            data: "bookingId=" + bookingId + "&statusId=" + statusId,
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        })
    });
</script>

</body>
</html>
