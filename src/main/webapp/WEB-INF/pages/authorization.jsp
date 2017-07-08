<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>authorization</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
    </style>
</head>
<body>

<jsp:include page="fragments/header.jsp"/>

<form class="authorization-form" action="login" method="post">
    <p class="authorization-title">Authorization</p>
    <p><input class="form-control" type="email" name="email" placeholder="Enter email"></p>
    <p><input class="form-control" type="password" name="password" placeholder="Enter password"></p>
    <input class="btn btn-primary authorization-btn" type="submit" value="Sign in">
</form>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
