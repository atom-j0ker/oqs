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
            <span id="table-info">Here you will see organizations, services and more details...</span>
            <div class="sort-by">
                <span class="sort-by-text">Sort by: </span>
                <select class="form-control sort-form"></select>
            </div>
            <table id="organizationTable" class="table table-bordered"></table>
            <p id="pages"></p>
        </div>
        <div class="dropdown container-left-part-2">
            <button id="show-service-btn" class="btn btn-default" disabled>Show services</button>
            <button id="reset-btn" class="btn btn-default">Reset params</button>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    var sortByCheck;
    var sortBy;
    var string;
    var categoryId;
    var categoryName;
    var totalPages;
    var startPage = 1;
    var rowsOnPage = 5;

    if ("${categoryId}" !== "") {
        categoryId = "${categoryId}";
        categoryName = "${categoryName}";
        fillTable(categoryId, categoryName, startPage, rowsOnPage);
    }

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

    //Search service
    $("#search-service").on("keyup change", function () {
        string = $(this).val();
        if (string !== "") {
            $("#table-info").css("display", "none");
            sortByForServicesForm();
            fillServiceTableByParams(string, categoryId, sortBy, startPage, rowsOnPage);
        }
    });

    //Choose category
    $("a.test").on("click", function (e) {
        categoryId = e.target.id;
        categoryName = e.target.text;
        sortByForOrganizationsForm();
        if (!$("#search-service").val())
            fillTable(categoryId, categoryName, startPage, rowsOnPage);
        else {
            $("#choose-category-btn").html(categoryName + ' <span class="caret"></span>');
            $('#show-service-btn').prop("disabled", false);
            sortByForServicesForm();
            fillServiceTableByParams(string, categoryId, sortBy, startPage, rowsOnPage);
        }
    });

    //Show services
    $("#show-service-btn").on("click", function (e) {
        $("#search-service").val("");
        string = undefined;
        sortByForServicesForm();
        fillServiceTableByParams(string, categoryId, sortBy, startPage, rowsOnPage);
    });

    //Reset params
    $("#reset-btn").on("click", function () {
        $("#search-service").val("");
        string = undefined;
        $("#choose-category-btn").html('Choose category <span class="caret"></span>');
        categoryId = undefined;
        categoryName = undefined;
        $("#show-service-btn").prop("disabled", true);
        $("#table-info").css("display", "block");
        $(".sort-by").css("display", "none");
        $("#organizationTable").find("tr").remove();
        $("#pages").find("a").remove();
    });

    //Sort by
    $(".sort-form").on("change", function (e) {
        sortBy = this.value;
        if (sortByCheck === "service")
            fillServiceTableByParams(string, categoryId, sortBy, startPage, rowsOnPage);
    });

    //Page
    $("#pages").on('click', 'a', function () {
        var page = $(this).closest('a').text();
        if (sortByCheck === "service")
            fillServiceTableByParams(string, categoryId, sortBy, page, rowsOnPage);
        else
            fillTable(categoryId, categoryName, page, rowsOnPage);
    });

    function fillServiceTableByParams(string, categoryId, sortBy, page, rowsOnPage) {
        $.ajax({
            type: "GET",
            url: "/fillServiceTable",
            data: "string=" + string + "&categoryId=" + categoryId + "&sortBy=" + sortBy + "&page=" +
            page + "&rowsOnPage=" + rowsOnPage,
            dataType: 'json',
            success: function (service) {
                createServiceTableTh();
                for (i = 0; i < service.second.length; i++)
                    fillServiceTable(service.second);
                fillPages(service.first);
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    }

    function fillTable(categoryId, categoryName, page, rowsOnPage) {
        $.ajax({
            type: "GET",
            url: "/fillOrganizationTable",
            data: "categoryId=" + categoryId + "&page=" + page + "&rowsOnPage=" + rowsOnPage,
            dataType: 'json',
            success: function (business) {
                $("#choose-category-btn").html(categoryName + ' <span class="caret"></span>');
                $("#table-info").css("display", "none");
                createOrganizationTableTh();
                for (var j = 0; j < business.second.length; j++) {
                    $("#organizationTable").append("<tr>" +
                        "<td><a href='/organization/" + business.second[j].id + "'>" +
                        business.second[j].name + "</a></td>" +
                        "<td>" + business.second[j].address + "</td>" +
                        "<td>" + business.second[j].phone + "</td>" +
                        "</tr>");
                }
                fillPages(business.first);
                $('#show-service-btn').prop("disabled", false);
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    }

    function fillServiceTable(service) {
        $("#organizationTable").append("<tr>" +
            "<td><a href='/organization/" + service[i].organizationId +
            "/service/" + service[i].serviceId + "'>" + service[i].serviceName + "</a></td>" +
            "<td>" + service[i].servicePrice + "</td>" +
            "<td><a href='/organization/" + service[i].organizationId + "'>" + service[i].organizationName + "</a></td>" +
            "<td>" + service[i].organizationAddress + "</td>" +
            "<td>" + service[i].organizationTelephone + "</td>" +
            "</tr>");
    }

    function createOrganizationTableTh() {
        $("#organizationTable").find("tr").remove();
        $("#organizationTable").append("<tr><th>Organization</th><th>Address</th><th>Telephone</th></tr>");
    }

    function createServiceTableTh() {
        $("#organizationTable").find("tr").remove();
        $("#organizationTable").append("<tr><th>Service</th><th>Price ( $ )</th><th>Organization</th>" +
            "<th>Address</th><th>Telephone</th></tr>");
    }

    function sortByForOrganizationsForm() {
        sortByCheck = "organization";
        $(".sort-form option").remove();
        $(".sort-form").append("<option value='name'>name</option>");
        $(".sort-by").css("display", "block");
    }

    function sortByForServicesForm() {
        sortByCheck = "service";
        $(".sort-form option").remove();
        $(".sort-form").append(
            "<option value='s.name'>name (service)</option>" +
            "<option value='s.business.name'>name (organization)</option>" +
            "<option value='s.price.price asc'>price (ascending)</option>" +
            "<option value='s.price.price desc'>price (descending)</option>"
        );
        sortBy = "s.name";
        $(".sort-by").css("display", "block");
    }

    function fillPages(rows) {
        totalPages = parseInt(rows / rowsOnPage);
        if (rows % rowsOnPage !== 0)
            totalPages++;
        $("#pages").find("a").remove();
        for (i = 1; i <= totalPages; i++)
            $("#pages").append("<a href='#' class='page'>" + i + "</a>");
    }
</script>

</body>
</html>
