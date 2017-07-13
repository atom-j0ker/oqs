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

<div class="content">
    <form action="create-business" method="post">
        <p><input type="text" name="name" placeholder="name"/></p>
        <p><input type="text" name="address" placeholder="address"/></p>
        <p><input type="text" name="description" placeholder="description"/></p>
        <p><input type="tel" name="phone" placeholder="telephone"></p>
        <p><input type="submit" value="Create"></p>
    </form>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
