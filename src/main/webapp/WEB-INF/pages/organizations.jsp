<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>organizations</title>
    <script src="<c:url value="/resources/js/sortTable.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
</head>
<body>

<select id="sortByCategoryId" name="sortByCategoryName">
    <option value="0"> -- Choose category -- </option>
    <c:forEach items="${categories}" var="category">
        <option value="${category.id}">${category.name}</option>
    </c:forEach>
</select>

<select id="sortBySubcategoryId" name="sortBySubcategoryName">

</select>

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

<script type="text/javascript">
    $("#sortByCategoryId").change(function () {
        var categoryId = $(this).val();
        $.ajax({
            type: "GET",
            url: 'subcategories',
            data: "categoryId=" + categoryId,
            success: function (data) {
                alert("good")
//                for(i=0;i<data.length;i++)
//                    alert(data[i].name);
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        })
    });
</script>
</body>
</html>
