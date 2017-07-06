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
<form action="login" method="post">
    <p><input type="email" name="email" placeholder="email"></p>
    <p><input type="password" name="password" placeholder="password"></p>
    <input type="submit" value="Sign in">
</form>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
