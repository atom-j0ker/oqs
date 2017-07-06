<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${service.name}</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
        <%@ include file="/resources/css/test-bootstrap.min.css" %>
        <%@ include file="/resources/css/test-bootstrap-datetimepicker.min.css" %>
    </style>
    <script src="<c:url value="/resources/js/jquery.js" />" charset="UTF-8"></script>
    <script src="<c:url value="/resources/js/test-bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/test-bootstrap-datetimepicker.js" />" charset="UTF-8"></script>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    <sec:authentication var="user" property="principal.username"/>
</sec:authorize>

<div class="booking-form">
    <h2>${organization.name}</h2>
    <form action="/booking-add/${organization.id}/${user}/${service.id}" method="post"
          onsubmit="return confirmBooking();">

        <h4>${service.name}</h4>

        <select id="mastersListId" name="mastersListName">
            <option value="0" disabled selected>-- Choose master --</option>
            <c:forEach items="${masters}" var="master">
                <option value="${master.id}">${master.user.firstname} ${master.user.lastname}</option>
            </c:forEach>
        </select>
        <p>
            <form class="form-horizontal" role="form">
                <fieldset>

        <p>Choose Date:</p>
        <div class="input-group date form_date" data-date="" data-date-format="dd-mm-yyyy"
             data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
            <input id="datepicker" name="dateName" class="form-control" size="16" type="text" value=""
                   readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
        </div>
        <input type="hidden" id="dtp_input2" value=""/><br/>
        <span class="error-msg" id="invalid-date"></span>

        <p>Choose time:</p>
        <p><select class="form-control input-lg" id="timeListId" name="timeListName">
            <option value="0" disabled selected>-- Time --</option>
        </select>
        </p>
        <span class="error-msg" id="invalid-time"></span>
        </fieldset>

        <p><textarea class="form-control input-lg" name="bookingComment"
                     rows="3" placeholder="Write your wishes here"></textarea></p>

        <c:choose>
            <c:when test="${pageContext.request.isUserInRole('USER')}">
                <input type="submit" value="Booked">
            </c:when>
            <c:otherwise>
                <input type="submit" value="Booked" title="please, sign in" disabled>
            </c:otherwise>
        </c:choose>


    </form>
    </form>
</div>

<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    var masterId;
    var date;
    var time;

    $("#mastersListId").change(function () {
        masterId = $(this).val();
        if (document.getElementById('datepicker').value != "") {
            fillTimeDropDownList();
        }
    });

    $("#datepicker").change(function () {
        date = $(this).val();
        if (document.getElementById('mastersListId').value != "0" && date != "") {
            fillTimeDropDownList();
        }
    });

    $("#timeListId").change(function () {
        time = $(this).val();
    });

    function confirmBooking() {
        var masterElement = document.getElementById("masterListId");
        var masterName = masterElement.options[masterElement.selectedIndex].text;
        if (confirm("Your booking:\n" + "master: " + masterName + "\ndate: " + date + "\ntime: " + time))
            return true;
        else
            return false;
    }

    function fillTimeDropDownList() {
        $.ajax({
            type: "GET",
            url: '/fill-time-list',
            data: {"masterId": masterId, "date": date},
            success: function (data) {
                var timeElement = document.getElementById("timeListId");
                timeElement.length = 1;
                for (var i = 0; i < data[0].length; i++) {
                    var option = new Option(data[0][i], data[0][i]);
//                    for (var j = 0; j < data[1].length; j++) {
//                        if (data[0][i] == data[1][j])
//                            option.disabled = true;
//                    }
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
