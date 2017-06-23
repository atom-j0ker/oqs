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

<table id="organizationTable" class="sortable">
    <tr>
        <th>Name</th>
        <th>Address</th>
        <th>Telephone</th>
    </tr>
    <c:forEach items="${organizations}" var="organization">
        <tr>
            <td>
                <a href="<spring:url value="/organization/{organizationId}" htmlEscape="true">
                    <spring:param name="organizationId" value="${organization.id}"/></spring:url>">
                        ${organization.name}
                </a>
            </td>
            <td>${organization.address}</td>
            <td>${organization.phone}</td>
        </tr>
    </c:forEach>
</table>


<div class="container">
    <div class="dropdown">
        <button id="choose-category-btn" class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
            Choose category
            <span class="caret"></span></button>
        <ul class="dropdown-menu">
            <c:forEach items="${categories}" var="category">
                <li class="dropdown-submenu">
                    <a class="test" tabindex="-1" href="#">${category.name}<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach items="${category.categories}" var="subcategory">
                            <li><a class="test" href="#">${subcategory.name}</a></li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
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
    });
</script>

</body>
</html>
