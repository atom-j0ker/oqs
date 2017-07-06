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

<div class="container">
    <div class="dropdown container-left-part-1">
        <button id="choose-category-btn" class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
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
        <button id="choose-service-btn" class="btn btn-default dropdown-toggle" type="button"
                data-toggle="dropdown">
            Choose service
            <span class="caret"></span></button>
        <ul id="service-dropdown" class="dropdown-menu"/>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    var categoryId;

    if("${categoryId}" != "") {
        fillTable("${categoryId}", "${categoryName}");
    }

    $('.dropdown-submenu a.test').on("mouseenter", function (e) {
        $(this).next('ul').toggle();
        e.stopPropagation();
        e.preventDefault();
    });
    $('.dropdown-submenu a.test').on("mouseleave", function (e) {
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
                document.getElementById("choose-service-btn").innerHTML =
                    "Choose service <span class='caret'></span>";
                $("#organizationTable tr").remove();
                var table = document.getElementById("organizationTable");
                var tr = document.createElement('tr');
                var thName, thAddress, thTelephone;
                appendTh(tr, thName, "Organization");
                appendTh(tr, thAddress, "Address");
                appendTh(tr, thTelephone, "Telephone");
                table.tHead.appendChild(tr);
                for (var i = 0; i < data.length; i++) {
                    tr = document.createElement('tr');
                    var a = document.createElement('a');
                    var td = document.createElement('td');
                    a.innerHTML = data[i].name;
                    a.style.color = "blue";
                    td.appendChild(a);
                    a.href = "/organization/" + data[i].id;
                    tr.appendChild(td);
                    var tdAddress = document.createElement('td');
                    tdAddress.innerHTML = data[i].address;
                    tr.appendChild(tdAddress);
                    var tdPhone = document.createElement('td');
                    tdPhone.innerHTML = data[i].phone;
                    tr.appendChild(tdPhone);
                    table.tBodies[0].appendChild(tr);
                }
                sorttable.makeSortable(table);
                $('#show-service-btn').prop("disabled", false);

                fillService(categoryId);
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    }

    function fillService(categoryId) {
        $.ajax({
            type: "GET",
            url: "/fill-service",
            data: "categoryId=" + categoryId,
            dataType: 'json',
            success: function (data) {
                $("#service-dropdown li").remove();
                for (i = 0; i < data.length; i++)
                    $("#service-dropdown").append('<li><a id="' + data[i].id + '" class="service" href="#">' + data[i].name + '</a></li>');
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
        document.getElementById("choose-service-btn").innerHTML =
            serviceName + ' <span class="caret"></span>';
        createOrganizationTableTh(table);

        $.ajax({
            type: "GET",
            url: "/fill-choosed-service-table",
            data: "serviceId=" + serviceId,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++)
                    fillOrganizationTable(table, data);
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
                for (i = 0; i < data.length; i++) {
                    fillOrganizationTable(table, data);
                }
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    });

    function createOrganizationTableTh(table) {
        $("#organizationTable tr").remove();
        var tr = document.createElement('tr');
        var thService, thName, thAddress, thTelephone;
        appendTh(tr, thService, "Service");
        appendTh(tr, thName, "Price");
        appendTh(tr, thName, "Organization");
        appendTh(tr, thAddress, "Address");
        appendTh(tr, thTelephone, "Telephone");
        table.tHead.appendChild(tr);
    }

    function fillOrganizationTable(table, data) {
        tr = document.createElement('tr');
        var tdService = document.createElement('td');
        tdService.innerHTML = data[i].serviceName;
        tr.appendChild(tdService);
        var tdPrice = document.createElement('td');
        tdPrice.innerHTML = data[i].servicePrice;
        tr.appendChild(tdPrice);
        var a = document.createElement('a');
        var td = document.createElement('td');
        a.innerHTML = data[i].organizationName;
        a.style.color = "blue";
        td.appendChild(a);
        a.href = "/organization/" + data[i].organizationId;
        tr.appendChild(td);
        var tdAddress = document.createElement('td');
        tdAddress.innerHTML = data[i].organizationAddress;
        tr.appendChild(tdAddress);
        var tdPhone = document.createElement('td');
        tdPhone.innerHTML = data[i].organizationTelephone;
        tr.appendChild(tdPhone);
        table.tBodies[0].appendChild(tr);
        sorttable.makeSortable(table);
    }

    function appendTh(tr, th, name) {
        th = document.createElement('th');
        th.innerHTML = name;
        tr.appendChild(th);
    }
</script>

</body>
</html>
