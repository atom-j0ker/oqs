<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Service</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/test-bootstrap.min.css" %>
        <%@ include file="/resources/css/test-bootstrap-datetimepicker.min.css" %>
    </style>

    <script src="<c:url value="/resources/js/bookingValidation.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.js" />" charset="UTF-8"></script>
    <script src="<c:url value="/resources/js/test-bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/test-bootstrap-datetimepicker.js" />" charset="UTF-8"></script>
</head>
<body>

<sec:authorize access="isAuthenticated()">
    <sec:authentication var="user" property="principal.username"/>
</sec:authorize>

<div class="service-middle">

    <h3 style="color: white">${organization.name}</h3>

    <form action="/booking-add/${organization.id}/${user}/${service.id}" method="post"
          onsubmit="return validateForm() && confirmBooking();">

        <p style="font-size: 20px; color: white">${service.name}</p>

        <select class="form-control input-lg" id="masterDropDownListId" name="masterDropDownListName">
            <option value="0" disabled selected>-- Choose master --</option>
            <c:forEach items="${masters}" var="master">
                <option value="${master.id}" name="${master.name}" style="font-size: 20px;">${master.name}</option>
            </c:forEach>
        </select>
        <span class="error-msg" id="invalid-master"></span>
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

        <sec:authorize access="hasRole('ROLE_USER')">
            <input type="submit" class="btn btn-lg btn-success" value="Booked">

        </sec:authorize>

    </form>
    </form>
</div>


<script type="text/javascript">
    var masterId;
    var date;
    var time;
    $("#masterDropDownListId").change(function () {
        masterId = $(this).val();
        if (document.getElementById('datepicker').value != "") {
            fillTimeDropDownList();
        }
    });
    $("#datepicker").change(function () {
        date = $(this).val();
        if (document.getElementById('masterDropDownListId').value != "0" && date != "") {
            fillTimeDropDownList();
        }
    });
    $("#timeDropDownListId").change(function () {
        time = $(this).val();
    });
    function confirmBooking() {
        var masterDropDownElement = document.getElementById("masterDropDownListId");
        var masterName = masterDropDownElement.options[masterDropDownElement.selectedIndex].text;
        if (confirm("Your booking:\n" + "master: " + masterName + "\ndate: " + date + "\ntime: " + time))
            return true;
        else
            return false;
    }
    function fillTimeDropDownList() {
        $.ajax({
            type: "GET",
            url: '/scheduleByMaster',
            data: {"masterId": masterId, "date": date},
            success: function (data) {
                var timeElement = document.getElementById("timeDropDownListId");
                timeElement.length = 1;
                for (var i = 0; i < data[0].length; i++) {
                    var option = new Option(data[0][i], data[0][i]);
                    for (var j = 0; j < data[1].length; j++) {
                        if (data[0][i] == data[1][j])
                            option.disabled = true;
                    }
                    timeElement.options[timeElement.length] = option;
                }
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        })
    }
</script>

<script type="text/javascript">
    $('.form_date').datetimepicker({
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });
    $('.form_time').datetimepicker({
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 1,
        minView: 0,
        maxView: 1,
        forceParse: 0
    });
</script>

</body>
</html>
