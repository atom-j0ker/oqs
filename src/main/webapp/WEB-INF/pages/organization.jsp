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
    <script src="<c:url value="/resources/js/sortTable.js" />"></script>
</head>
<body>

<jsp:include page="fragments/header.jsp"/>

<div class="content">
    <div class="organization-left-part">
        <img class="organization-photo" src="${organization.photo.photo}"/>

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

    <%--<div class="organization-services">--%>
        <%--<p>Organization services:</p>--%>
        <%--<ul id="service-list">--%>
            <%--<c:forEach items="${services}" var="service">--%>
                <%--<li><a href="/organization/${organization.id}/service/${service.id}">${service.name}</a></li>--%>
            <%--</c:forEach>--%>
        <%--</ul>--%>
    <%--</div>--%>

    <div class="organization-services">
        <table id="service-list" class="sortable table">
            <tr>
                <th>Service</th>
                <th>Price ( $ )</th>
                <th>Duration ( min )</th>
            </tr>
            <c:forEach items="${services}" var="service">
                <tr>
                    <td><a href="/organization/${organization.id}/service/${service.id}">${service.name}</a></td>
                    <td>${service.price.price}</td>
                    <td>${service.duration}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <c:if test="${organization.id == user.business.id}">

        <form action="/organization/${organization.id}/change-photo" method="post" enctype="multipart/form-data">
            <input id="file" name="file" class="hidden-files" type="file" accept="image/*">
            <input type="submit" value="Upload">
        </form>

        <div class="add-service">
            <p id="add-service-btn">Add service:</p>
            <div class="add-service-form">
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
        </div>
    </c:if>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>

<script type="text/javascript">

    $('.kv-fa').rating({
        filledStar: '<i class="fa fa-star"></i>',
        emptyStar: '<i class="fa fa-star-o"></i>'
    });

    $("#add-service-btn").click(function () {
        if ($('.add-service-form').css('display') === 'none')
            $(".add-service-form").css('display', 'block');
        else
            $(".add-service-form").css('display', 'none');
    });

    $("#categoryListId").change(function () {
        var categoryId = $(this).children(":selected").attr("id");
        $.ajax({
            type: "GET",
            url: "/fillSubcategories",
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
                $("#service-list").append("<li><a href='/organization/" + ${organization.id} +"/service/" + service.id + "'>" + service.name + "</a></li>");
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        })
    });
</script>
</html>
