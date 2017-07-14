<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>organizations</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
    </style>
    <script src="<c:url value="/resources/js/sortTable.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>

<jsp:include page="fragments/header.jsp"/>

<div class="content">
    <div class="container organization-search">
        <div class="dropdown container-left-part-1">
            <input type="text" id="search-service" class="form-control" placeholder="Search service">
            <button id="choose-category-btn" class="btn btn-default dropdown-toggle" type="button"
                    data-toggle="dropdown">
                Choose category
                <span class="caret"></span></button>
            <ul class="dropdown-menu">
                <c:forEach items="${categories}" var="category">
                    <li class="dropdown-submenu">
                        <a id="${category.id}" class="test" tabindex="-1" href="#">${category.name}<span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <c:forEach items="${category.categories}" var="subcategory">
                                <li><a id="${subcategory.id}" class="test" href="#">${subcategory.name}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="container-right-part">
            <table id="organizationTable" class="sortable table table-bordered">
                <tr>
                    <th>Organization</th>
                    <th>Address</th>
                    <th>Telephone</th>
                </tr>
            </table>
        </div>
        <div class="dropdown container-left-part-2">
            <button id="show-service-btn" class="btn btn-default" type="button" disabled>Show services</button>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    var categoryId;

    if ("${categoryId}" != "") {
        categoryId = ${categoryId}
            fillTable("${categoryId}", "${categoryName}");
    }

    $("#search-service").on("keyup change", function () {

        var string = $(this).val();
        $.ajax({
            type: "GET",
            url: "/search-service",
            data: "string=" + string,
            dataType: 'json',
            success: function (service) {
                var table = document.getElementById("organizationTable");
                createOrganizationTableTh(table);
                for (i = 0; i < service.length; i++)
                    fillOrganizationTable(table, service);
                sorttable.makeSortable(table);
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        })
    });

    $(".dropdown-submenu a.test").on("mouseenter", function (e) {
        $(this).next('ul').toggle();
        e.stopPropagation();
        e.preventDefault();
    });
    $(".dropdown-submenu a.test").on("mouseleave", function (e) {
        $('dropdown-menu a.test').on("mouseleave", function (e) {
            $(this).hide();
        });
    });

    $('a.test').on("click", function (e) {
        categoryId = e.target.id;
        var categoryName = e.target.text;
        fillTable(categoryId, categoryName);
    });

    function fillTable(categoryId, categoryName) {
        $.ajax({
            type: "GET",
            url: "/create-organization-table",
            data: "categoryId=" + categoryId,
            dataType: 'json',
            success: function (data) {
                document.getElementById("choose-category-btn").innerHTML =
                    categoryName + ' <span class="caret"></span>';
                $("#organizationTable tr").remove();
                var table = document.getElementById("organizationTable");
                $("#organizationTable thead").append("<tr><th>Organization</th><th>Address</th><th>Telephone</th></tr>");
                for (var i = 0; i < data.length; i++) {
                    $("#organizationTable tbody").append("<tr>" +
                        "<td><a href='/organization/" + data[i].id + "'>" + data[i].name + "</a></td>" +
                        "<td>" + data[i].address + "</td>" +
                        "<td>" + data[i].phone + "</td>" +
                        "</tr>");
                }
                sorttable.makeSortable(table);
                $('#show-service-btn').prop("disabled", false);
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    }

    $('ul').on("click", "li a.service", function (e) {
        var serviceId = e.target.id;
        var serviceName = e.target.text;
        var table = document.getElementById("organizationTable");
        createOrganizationTableTh(table);

        $.ajax({
            type: "GET",
            url: "/fill-choosed-service-table",
            data: "serviceId=" + serviceId,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++)
                    fillOrganizationTable(table, data);
                sorttable.makeSortable(table);
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    });

    $('#show-service-btn').on("click", function (e) {
        var table = document.getElementById("organizationTable");
        createOrganizationTableTh(table);
        $.ajax({
            type: "GET",
            url: "/fill-service-table",
            data: "categoryId=" + categoryId,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++)
                    fillOrganizationTable(table, data);
                sorttable.makeSortable(table);
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    });

    function fillOrganizationTable(table, data) {
        $("#organizationTable tbody").append("<tr>" +
            "<td><a href='/organization/" + data[i].organizationId + "/service/" + data[i].serviceId + "'>" + data[i].serviceName + "</a></td>" +
            "<td>" + data[i].servicePrice + "</td>" +
            "<td><a href='/organization/" + data[i].organizationId + "'>" + data[i].organizationName + "</a></td>" +
            "<td>" + data[i].organizationAddress + "</td>" +
            "<td>" + data[i].organizationTelephone + "</td>" +
            "</tr>");
    }

    function createOrganizationTableTh(table) {
        $("#organizationTable tr").remove();
        $("#organizationTable thead").append(
            "<tr><th>Service</th><th>Price ( $ )</th><th>Organization</th><th>Address</th><th>Telephone</th></tr>"
        );
    }
</script>

</body>
</html>
