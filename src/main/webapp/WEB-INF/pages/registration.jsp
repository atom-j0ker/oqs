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

<jsp:include page="fragments/header.jsp"/>

<form class="registration-form" action="/registration" method="post">
    <h2>Registration</h2>
    <p><input class="form-control" type="email" name="email" placeholder="Enter email"/></p>
    <p><input class="form-control" type="password" name="password" placeholder="Enter password"/></p>
    <p><input class="form-control" type="text" name="firstname" placeholder="Enter first name"/></p>
    <p><input class="form-control" type="text" name="lastname" placeholder="Enter last name"/></p>
    <p><input class="form-control" type="tel" name="phone" placeholder="Enter telephone"></p>
    <p><input type="radio" name="role" value="ROLE_USER"> client</p>
    <p><input type="radio" name="role" value="ROLE_MASTER"> master</p>
    <p><input type="radio" name="role" value="ROLE_BUSINESS"> business</p>
    <p><input class="btn btn-primary submit-btn" type="submit" value="Sign up"></p>
</form>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
