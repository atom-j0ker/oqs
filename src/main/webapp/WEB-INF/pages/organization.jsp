<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${organization.name}</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
    </style>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/resources/css/star-rating.css" media="all" type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="/resources/js/star-rating.js" type="text/javascript"></script>
</head>
<body>

<input type="text" class="kv-fa rating-loading" value="${rating}" data-size="md" title="">
<br>

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

<script type="text/javascript">
    $('.kv-fa').rating({
        filledStar: '<i class="fa fa-star"></i>',
        emptyStar: '<i class="fa fa-star-o"></i>'
    });
</script>
</html>
