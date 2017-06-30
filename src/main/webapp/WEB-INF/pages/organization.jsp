<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${organization.name}</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
    </style>
</head>
<body>
<img class="organization-photo" src="${organization.photo.photo}"/>
<P>${organization.name}</P>
<P>${organization.address}</P>
<P>${organization.description}</P>
<P>${organization.phone}</P>

<ul id="service-list">
    <c:forEach items="${services}" var="service">
        <li id="li${service.id}"><a href="/organization/${organization.id}/service/${service.id}"
                                    htmlEscape="true">${service.name}</a></li>
    </c:forEach>
</ul>
</body>
</html>
