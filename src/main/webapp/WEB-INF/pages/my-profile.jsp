<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>my-profile</title>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
    <script src="<c:url value="/resources/js/sortTable.js" />"></script>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
    </style>
</head>
<body>
<sec:authentication var="currentUser" property="principal"/>
<h2>My profile</h2>
<p>${user.email}</p>
<sec:authorize access="isAuthenticated()">
    <p>${currentUser.authorities}</p>
</sec:authorize>
<p>${user.firstname} ${user.lastname}</p>

<sec:authorize access="hasRole('ROLE_USER')">
    <c:if test="${!empty schedule}">
        <div class="my-bookings">
            <p class="title">My bookings</p>
            <table class="sortable table table-bordered">
                <tr>
                    <th>Organization</th>
                    <th>Service</th>
                    <th>Master</th>
                    <th>Date</th>
                    <th>Time</th>
                </tr>
                <c:forEach items="${schedule}" var="sch">
                    <tr id="${sch.id}">
                        <c:set value="${sch.id}" var="a"></c:set>
                        <td>${sch.service.business.name}</td>
                        <td>${sch.service.name}</td>
                        <td>${sch.master.user.firstname} ${sch.master.user.lastname}</td>
                        <td><fmt:formatDate value="${sch.date}" pattern="dd-MM-yyyy"/></td>
                        <td>${sch.time}</td>
                        <c:if test="${currentUser.username == user.email}">
                            <td>
                                <input type="button" class="delete-booking-btn" value="Delete">
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_MASTER')">

</sec:authorize>
<sec:authorize access="hasRole('ROLE_BUSINESS')">
    <p><a href="/user/${user.id}/create-business">create business</a></p>
</sec:authorize>

<jsp:include page="fragments/footer.jsp"/>

<script>
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
    });
</script>
</body>
</html>
