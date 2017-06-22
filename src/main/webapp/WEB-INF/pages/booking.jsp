<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${service.name}</title>
</head>
<body>
<p>${organization.name}</p>
<form action="/booking-add/${organization.id}/${user}/${service.id}" method="post">

    <p>${service.name}</p>

    <select id="mastersListId" name="mastersListName">
        <option value="0" disabled selected>-- Choose master --</option>
        <c:forEach items="${masters}" var="master">
            <option value="${master.id}" name="${master.id}">${master.id}</option>
        </c:forEach>
    </select>
    <p>
        <form class="form-horizontal" role="form">
            <fieldset>
    <p style="font-size: 20px; color: white">Choose Date:</p>
    <div class="input-group date form_date" data-date="" data-date-format="dd-mm-yyyy"
         data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
        <input id="datepicker" name="dateName" class="form-control" size="16" type="text" value=""
               readonly>
        <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
    </div>
    <input type="hidden" id="dtp_input2" value=""/><br/>
    <span class="error-msg" id="invalid-date"></span>

    <p style="font-size: 20px; color: white">Choose time:</p>
    <p><select class="form-control input-lg" id="timeDropDownListId" name="timeDropDownListName">
        <option value="0" disabled selected>-- Time --</option>
    </select>
    </p>
    <span class="error-msg" id="invalid-time"></span>
    </fieldset>

    <p><textarea class="form-control input-lg" name="bookingComment"
                 rows="3" placeholder="Write your wishes here"></textarea></p>

    <input type="submit" value="Booked">

</form>
</form>
</body>
</html>
