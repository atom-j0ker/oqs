<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${organization.name}</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
    </style>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/resources/css/star-rating.css" media="all" type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="/resources/js/star-rating.js" type="text/javascript"></script>
</head>
<body>

<jsp:include page="fragments/header.jsp"/>

<div class="content">
    <div class="organization-left-part">
        <img class="organization-photo" src="${photo}"/>
    </div>
    <div class="organization-right-part">
        <p class="organization-name">${organization.name}</p>
        <input type="text" class="kv-fa rating-loading" value="${rating}" data-size="md" title="">
        <div class="organization-description">
            <p>Address: ${organization.address}</p>
            <p>Telephone: ${organization.phone}</p>
            <p>${organization.description}</p>
        </div>
    </div>

    <c:if test="${organization.id == user.business.id}">
        <div class="user-image-form">
            <form action="/organization/${organization.id}/change-photo" method="post" enctype="multipart/form-data">
                <input type="file" id="file" name="file" class="hidden-files" accept="image/*">
                <input type="submit" value="Upload" class="btn btn-default">
            </form>
        </div>
    </c:if>

    <div class="organization-services">
        <table id="service-list" class="table">
            <thead>
            <tr>
                <th>Service</th>
                <th>Price ( $ )</th>
                <th>Duration ( min )</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${services}" var="service">
                <tr>
                    <td><a href="/organization/${organization.id}/service/${service.id}">${service.name}</a></td>
                    <td>${service.price.price}</td>
                    <td>${service.duration}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>

<script type="text/javascript">

    $(function () {
        if (${user.business.id} == ${organization.id}) {
            $("#service-list > thead > tr").append(
                '<th><form action="/organization/${organization.id}/serviceAdd" method="get">' +
                '<input type="submit" class="btn btn-default change-service-btn" value="Add service"/>' +
                '</form></th>');
            $("#service-list > tbody > tr").append(
                '<td><form action="/2" method="get"><input type="submit" class="btn btn-default change-service-btn" value="Edit"/></form></td>' +
                '<td><form action="/3" method="get"><input type="submit" class="btn btn-danger change-service-btn" value="Delete"/></form></td>');
        }
    });

    $('.kv-fa').rating({
        filledStar: '<i class="fa fa-star"></i>',
        emptyStar: '<i class="fa fa-star-o"></i>'
    });

</script>
</html>
