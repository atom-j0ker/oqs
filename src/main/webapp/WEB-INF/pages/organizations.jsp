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

<%--<table id="organizationTable" class="sortable">--%>
<%--<tr>--%>
<%--<th>Name</th>--%>
<%--<th>Address</th>--%>
<%--<th>Telephone</th>--%>
<%--</tr>--%>
<%--<c:forEach items="${organizations}" var="organization">--%>
<%--<tr>--%>
<%--<td>--%>
<%--<a href="<spring:url value="/organization/{organizationId}" htmlEscape="true">--%>
<%--<spring:param name="organizationId" value="${organization.id}"/></spring:url>">--%>
<%--${organization.name}--%>
<%--</a>--%>
<%--</td>--%>
<%--<td>${organization.address}</td>--%>
<%--<td>${organization.phone}</td>--%>
<%--</tr>--%>
<%--</c:forEach>--%>
<%--</table>--%>


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
                <th>Name</th>
                <th>Address</th>
                <th>Telephone</th>
            </tr>
        </table>
    </div>
    <div class="dropdown container-left-part-2">
        <button id="choose-service-btn" class="btn btn-default dropdown-toggle" type="button"
                data-toggle="dropdown">
            Choose service
            <span class="caret"></span></button>
        <ul id="service-dropdown" class="dropdown-menu">

            <li><a id="1" class="service" href="#">perviy</a></li>
            <li><a id="2" class="service" href="#">vtoroi</a></li>

        </ul>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {

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
                var categoryId = e.target.id;
                var categoryName = e.target.text;
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
                        var tr = document.createElement('tr');
                        var thName, thAddress, thTelephone;
                        appendTh(tr, thName, "Name");
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
                        fillService(categoryId);
                    },
                    error: function (xhr, textStatus) {
                        alert([xhr.status, textStatus]);
                    }
                });
            });
            function appendTh(tr, th, name) {
                th = document.createElement('th');
                th.innerHTML = name;
                tr.appendChild(th);
            }

            function fillService(categoryId) {
                $.ajax({
                    type: "GET",
                    url: "/fill-service",
                    data: "categoryId=" + categoryId,
                    dataType: 'json',
                    success: function (data) {
                        $("#service-dropdown li").remove();
                        var service = document.getElementById("service-dropdown");
                        for (i = 0; i < data.length; i++) {
                            var li = document.createElement('li');
                            var a = document.createElement('a');
                            a.innerHTML = data[i].name;
                            a.id = data[i].id;
                            a.href = "#";
                            a.classList.add("service");
                            li.appendChild(a);
                            service.appendChild(li);
                        }
                    },
                    error: function (xhr, textStatus) {
                        alert([xhr.status, textStatus]);
                    }
                });
            }

            $('a.service').on("click", function (e) {
                var serviceId = e.target.id;
                var serviceName = e.target.text;
                alert(serviceId + " " + serviceName)
            });
        }
    );
</script>

</body>
</html>
