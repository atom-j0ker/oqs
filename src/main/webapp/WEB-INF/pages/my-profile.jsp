<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>my-profile</title>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
    </style>
</head>
<body>

<jsp:include page="fragments/header.jsp"/>

<div class="content">
    <sec:authentication var="currentUser" property="principal"/>

    <div class="user-info">
        <h2>My profile</h2>
        <img class="user-photo" src="${photo}"/>
        <div class="user-image-form">
            <form action="/user/${user.id}/change-photo" method="post" enctype="multipart/form-data">
                <input type="file" id="file" name="file" class="hidden-files" accept="image/*">
                <input type="submit" value="Upload" class="btn btn-default">
            </form>
        </div>
        <p>${user.firstname} ${user.lastname}</p>
        <p>phone: ${user.phone}</p>
    </div>

    <sec:authorize access="hasRole('ROLE_USER')">
        <c:if test="${!empty schedule}">
            <div class="my-bookings">
                <h4 style="text-align: center">My bookings</h4>
                <table id="schedule-table" class="table schedule-table">
                    <tr>
                        <th>Service</th>
                        <th>Organization</th>
                        <th>Master</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Status</th>
                    </tr>
                    <c:forEach items="${schedule}" var="sch">
                        <tr id="${sch.id}">
                            <td><img class="info-img" src="/resources/img/information.png"
                                     title="${sch.comment}"/> ${sch.service.name}</td>
                            <td>${sch.service.business.name}</td>
                            <td>${sch.master.user.firstname} ${sch.master.user.lastname}</td>
                            <td><fmt:formatDate value="${sch.date}" pattern="dd-MM-yyyy"/></td>
                            <td>${sch.time}</td>
                            <td>${sch.status.name}</td>
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
        <c:choose>
            <c:when test="${empty user.business.id}">
                <p><a href="/user/${user.id}/createBusiness">create business</a></p>
            </c:when>
            <c:otherwise>
                <p><a href="/organization/${user.business.id}">my business</a></p>
            </c:otherwise>
        </c:choose>
    </sec:authorize>
</div>

<jsp:include page="fragments/footer.jsp"/>

<script src="<c:url value="/resources/js/my-profile.js" />"></script>
</body>
</html>
