<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>my-profile</title>
</head>
<body>
<sec:authentication var="currentUser" property="principal"/>
<p>${user.email}</p>
<sec:authorize access="isAuthenticated()">
    <p>${currentUser.authorities}</p>
</sec:authorize>
<p>${user.firstname} ${user.lastname}</p>

<sec:authorize access="hasRole('ROLE_USER')">
    loh
</sec:authorize>
<sec:authorize access="hasRole('ROLE_MASTER')">

</sec:authorize>
<sec:authorize access="hasRole('ROLE_BUSINESS')">
    <p><a href="/user/${user.id}/create-business">create business</a></p>
</sec:authorize>
</body>
</html>
