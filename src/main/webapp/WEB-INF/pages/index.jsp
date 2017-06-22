<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>online queue system</title>
</head>
<body>
<sec:authentication var="user" property="principal"/>

<a href="organizations">Organizations</a>
<a href="registration">Registration</a>
<a href="authorization">Authorization</a>

<sec:authorize access="isAuthenticated()">
    <c:set var="username" value="${user.username}"/>
    <p>${username}</p>
    <form action="my-profile" method="post">
        <input type="hidden" name="username" value="${username}">
        <input type="submit" value="my profile">
    </form>
</sec:authorize>

</body>
</html>
