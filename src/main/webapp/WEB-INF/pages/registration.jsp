<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>registration</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
    </style>
</head>
<body>
<form action="registration" method="post">
    <p><input type="email" name="email" placeholder="email"/></p>
    <p><input type="password" name="password" placeholder="password"/></p>
    <p><input type="text" name="firstname" placeholder="first name"/></p>
    <p><input type="text" name="lastname" placeholder="last name"/></p>
    <p><input type="tel" name="phone" placeholder="telephone"></p>
    <p><input type="radio" name="role" value="ROLE_USER">client</p>
    <p><input type="radio" name="role" value="ROLE_MASTER">master</p>
    <p><input type="radio" name="role" value="ROLE_BUSINESS">business</p>
    <p><input type="submit" value="Sign up"></p>
</form>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
