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

    <%--<script src="<c:url value="/resources/js/jquery.js" />"></script>--%>

</head>

<jsp:include page="fragments/header.jsp"/>

<body>
<sec:authorize access="isAuthenticated()">
    <sec:authentication var="currentUser" property="principal"/>
</sec:authorize>

<img class="organization-photo" src="${organization.photo.photo}"/>
<input type="text" class="kv-fa rating-loading" value="${rating}" data-size="md" title="">
<br>
<P>${organization.name}</P>
<P>${organization.address}</P>
<P>${organization.description}</P>
<P>${organization.phone}</P>

<ul id="service-list">
    <c:forEach items="${services}" var="service">
        <li><a href="/organization/${organization.id}/service/${service.id}">${service.name}</a></li>
    </c:forEach>
</ul>

<c:if test="${organization.id == user.business.id}">
    <div class="add-service">
        <p>Add service:</p>
        <select id="categoryListId">
            <option value="0" selected disabled> -- Choose category --</option>
            <c:forEach items="${categories}" var="category">
                <option id="${category.id}" value="${category.name}">${category.name}</option>
            </c:forEach>
        </select><br>
        <select id="subcategoryListId">
            <option value="0" selected disabled> -- Choose subcategory --</option>
        </select><br>
        <input type="text" id="newService" placeholder="Service"><br>
        <input type="text" id="newPrice" placeholder="Price (E.g. 10-15$)"><br>
        <input type="text" id="newDuration" placeholder="Duration (E.g. 60 min)"><br>
        <input type="button" id="add-service" value="Add service">
    </div>
</c:if>

<jsp:include page="fragments/footer.jsp"/>

</body>

<script type="text/javascript">
    $('.kv-fa').rating({
        filledStar: '<i class="fa fa-star"></i>',
        emptyStar: '<i class="fa fa-star-o"></i>'
    });

    $("#categoryListId").change(function () {
        var categoryId = $(this).children(":selected").attr("id");
        $.ajax({
            type: "GET",
            url: "/fill-subcategories",
            data: "categoryId=" + categoryId,
            dataType: 'json',
            success: function (data) {
                $("#subcategoryListId option").remove();
                if (data.length != 0) {
                    $("#subcategoryListId").append("<option value='0' disabled selected> -- Choose subcategory -- </option>");
                }
                for (var i = 0; i < data.length; i++) {
                    $("#subcategoryListId").append("<option id='" + data[i].id + "' value='" + data[i].name + "'>" + data[i].name + "</option>");
                }
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        })
    });


    $("#add-service").click(function () {
        var subcategoryId = $("#subcategoryListId").children(":selected").attr("id");
        var serviceName = $("#newService").val();
        var priceValue = $("#newPrice").val();
        var duration = $("#newDuration").val();

        $.ajax({
            type: "GET",
            url: "/add-service/" + ${organization.id},
            data: "subcategoryId=" + subcategoryId + "&serviceName=" + serviceName + "&priceValue=" + priceValue + "&duration=" + duration,
            success: function (service) {
                $("#service-list").append("<li><a href='/organization/" + ${organization.id} + "/service/" + service.id + "'>" + service.name + "</a></li>");
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        })
    });
</script>
</html>
