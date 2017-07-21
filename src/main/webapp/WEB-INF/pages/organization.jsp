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
        <table id="organization-functions" class="table">
            <tr>
                <td>
                    <div class="add-image-form">
                        <form action="/organization/${organization.id}/change-photo" method="post"
                              enctype="multipart/form-data">
                            <input type="file" id="file" name="file" class="hidden-files" accept="image/*">
                            <input type="submit" value="Upload" class="btn btn-default">
                        </form>

                    </div>
                </td>
                <td>
                    <form action="/organization/${organization.id}/mastersSettings" method="get">
                        <input type="submit" value="Master's settings" class="btn btn-default">
                    </form>
                </td>
                <td>
                    <form action="/organization/${organization.id}/schedule" method="get">
                        <input type="submit" value="Schedule" class="btn btn-default">
                    </form>
                </td>
            </tr>
        </table>
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
                <tr id="${service.id}">
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
    $('.kv-fa').rating({
        filledStar: '<i class="fa fa-star"></i>',
        emptyStar: '<i class="fa fa-star-o"></i>'
    });
</script>
<script type="text/javascript">

    $(function () {
        if (${user.business.id} == ${organization.id}) {
            $("#service-list > thead > tr").append(
                '<th><form action="/organization/${organization.id}/serviceAdd" method="get">' +
                '<input type="submit" class="btn btn-default change-service-btn" value="Add service"/>' +
                '</form></th>');
            $("#service-list > tbody > tr").append(
                '<td><input type="button" id="service-edit-btn" class="btn btn-default change-service-btn" value="Edit"/>' +
                '<input type="button" id="service-save-btn" class="btn btn-success change-service-btn" value="Save"/>' +
                '<input type="button" id="service-cancel-btn" class="btn btn-warning change-service-btn" value="Cancel"/></td>' +
                '<td><input type="button" id="service-delete-btn" class="btn btn-danger change-service-btn" value="Delete"/></td>');
        }
    });

    $('#service-list').on('click', '#service-edit-btn', function () {
        var tr = $(this).closest('tr');
        var serviceId = tr.attr('id');
        var name = $(this).closest('tr').find('td a').text();
        var price = tr.find('td:nth-child(2)').html();
        var duration = tr.find('td:nth-child(3)').html();
        for (var i = 0; i < 3; i++)
            tr.find('td:first').remove();
        tr.find('#service-edit-btn').css("display", "none");
        tr.find('#service-save-btn').css("display", "block");
        tr.find('#service-cancel-btn').css("display", "block");
        tr.prepend('<td><input class="form-control" type="text" value="' + name + '"/></td>' +
            '<td><input class="form-control" type="text" value="' + price + '"/></td>' +
            '<td><input class="form-control" type="text" value="' + duration + '"/></td>');
    });

    $('#service-list').on('click', '#service-save-btn', function () {
        var tr = $(this).closest('tr');
        var serviceId = tr.attr('id');
        var newName = tr.find('td:first input').val();
        var newPrice = tr.find('td:nth-child(2) input').val();
        var newDuration = tr.find('td:nth-child(3) input').val();
        $.ajax({
            type: "GET",
            url: "/organization/updateService/" + serviceId,
            data: "newName=" + newName + "&newPrice=" + newPrice + "&newDuration=" + newDuration,
            success: function () {
                for (var i = 0; i < 3; i++)
                    tr.find('td:first').remove();
                tr.find('#service-edit-btn').css("display", "block");
                tr.find('#service-save-btn').css("display", "none");
                tr.find('#service-cancel-btn').css("display", "none");
                tr.prepend('<td><a href="/organization/' + ${organization.id} +'/service/' + serviceId + '">' + newName + '</a></td>' +
                    '<td>' + newPrice + '</td>' +
                    '<td>' + newDuration + '</td>');
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        })
        ;
    });

    $('#service-list').on('click', '#service-cancel-btn', function () {
        var tr = $(this).closest('tr');
        var serviceId = tr.attr('id');
        $.ajax({
            type: "GET",
            url: "/organization/cancelService/" + serviceId,
            dataType: 'json',
            success: function (service) {
                for (var i = 0; i < 3; i++)
                    tr.find('td:first').remove();
                tr.find('#service-edit-btn').css("display", "block");
                tr.find('#service-save-btn').css("display", "none");
                tr.find('#service-cancel-btn').css("display", "none");
                tr.prepend('<td><a href="/organization/' + service.organizationId +'/service/' + serviceId + '">' + service.name + '</a></td>' +
                    '<td>' + service.price + '</td>' +
                    '<td>' + service.duration + '</td>');
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    });

    $('#service-list').on('click', '#service-delete-btn', function () {
        var serviceId = $(this).closest('tr').attr('id');
        $.ajax({
            type: "GET",
            url: "/organization/deleteService",
            data: "serviceId=" + serviceId,
            success: function () {
                $("#service-list tr#" + serviceId).remove();
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    });

</script>
</html>
