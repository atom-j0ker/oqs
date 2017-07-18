<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>create new service</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
    </style>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
</head>
<body>

<jsp:include page="fragments/header.jsp"/>

    <form class="service-add-form" action="/organization/${organizationId}/serviceAdd" method="post">
        <h2 id="add-service-btn">Add service:</h2>
            <select class="form-control" id="categoryListId">
                <option value="0" selected disabled> -- Choose category --</option>
                <c:forEach items="${categories}" var="category">
                    <option id="${category.id}" value="${category.name}">${category.name}</option>
                </c:forEach>
            </select><br>
            <select class="form-control" id="subcategoryListId">
                <option value="0" selected disabled> -- Choose subcategory --</option>
            </select><br>
            <input class="form-control" type="text" name="name" placeholder="Service"/><br>
            <input class="form-control" type="text" name="price" placeholder="Price ( $ ) E.g. 15"/><br>
            <input class="form-control" type="text" name="duration" placeholder="Duration (min), E.g. 60"/><br>
            <input type="hidden" id="subcategory" name="subcategory" value="hahaha"/>
            <input class="btn btn-primary submit-btn" type="submit" value="Add service"/>
    </form>

<jsp:include page="fragments/footer.jsp"/>

</body>

<script>
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

    $("#subcategoryListId").change(function () {
        $("#subcategory").val($(this).children(":selected").attr("id"));
    });
</script>
</html>
