<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>create new business</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
    </style>
</head>
<body>

<jsp:include page="fragments/header.jsp"/>

<form class="create-business-form" action="/createBusiness" method="post">
    <h2>Create business:</h2>
    <p><input class="form-control" type="text" name="name" placeholder="name"/></p>
    <p><input class="form-control" type="text" name="address" placeholder="address"/></p>
    <p><input class="form-control" type="text" name="description" placeholder="description"/></p>
    <p><input class="form-control" type="tel" name="phone" placeholder="telephone"></p>
    <p><input class="btn btn-primary submit-btn" type="submit" value="Create"></p>
</form>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
