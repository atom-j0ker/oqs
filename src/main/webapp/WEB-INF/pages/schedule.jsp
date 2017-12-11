<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>schedule</title>
    <style>
        <%@ include file="/resources/css/oqs.css" %>
        <%@ include file="/resources/css/bootstrap.css" %>
        <%@ include file="/resources/css/test-bootstrap.min.css" %>
        <%@ include file="/resources/css/test-bootstrap-datetimepicker.min.css" %>
    </style>
    <script src="<c:url value="/resources/js/jquery.js" />"></script>
    <script src="<c:url value="/resources/js/test-bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/test-bootstrap-datetimepicker.js" />" charset="UTF-8"></script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<div class="content">

    <div class="schedule-params">
        <span class="schedule-text">Choose Date:</span>
        <div class="input-group date form_date schedule-date" data-date="" data-date-format="dd-mm-yyyy"
             data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
            <input id="datepicker" name="dateName" class="form-control" size="16" type="text" value=""
                   readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
        </div>
        <input type="hidden" id="dtp_input2" value=""/><br/>

        <select class="form-control schedule-master-list" id="mastersListId" name="mastersListName">
            <option value="0" disabled selected>-- Choose master --</option>
            <c:forEach items="${masters}" var="master">
                <option id="${master.id}">${master.user.firstname} ${master.user.lastname}</option>
            </c:forEach>
        </select>
        <img class="master-info-img" style="float: left" src="/resources/img/information.png"/>
    </div>

    <div id="modal-form">
        <span id="modal-close">X</span>
    </div>
    <div id="overlay"></div>

    <table id="business-schedule-table" class="table">
        <tr>
            <th>#</th>
            <th>Client</th>
            <th>Client email</th>
            <th>Service</th>
            <th>Master</th>
            <th>Date</th>
            <th>Time</th>
            <th>Status</th>
        </tr>
        <c:forEach items="${schedule}" var="sch">
            <tr>
                <td>${sch.id}</td>
                <td>${sch.user.firstname} ${sch.user.lastname}</td>
                <td>${sch.user.email}</td>
                <td>${sch.service.name}
                    <img class="info-img" src="/resources/img/information.png" title="${sch.comment}"/></td>
                <td>${sch.master.user.firstname} ${sch.master.user.lastname}</td>
                <td><fmt:formatDate value="${sch.date}" pattern="dd-MM-yyyy"/></td>
                <td>${sch.startTime}</td>
                <td>
                    <select id="booking${sch.id}" class="form-control status">
                        <option <c:if test="${sch.status=='WAITING'}">selected</c:if>>Waiting</option>
                        <option <c:if test="${sch.status=='VISITED'}">selected</c:if>>Visited</option>
                        <option <c:if test="${sch.status=='NOT WISITED'}">selected</c:if>>Not visited</option>
                    </select>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="fragments/footer.jsp"/>

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

    $("#datepicker").change(function () {
        fillScheduleTable();
    });

    $("#mastersListId").change(function () {
        fillScheduleTable();
    });

    function fillScheduleTable() {
        date = $("#datepicker").val();
        masterId = $("#mastersListId").children(":selected").attr("id");
        $.ajax({
            type: "GET",
            url: "/fillScheduleTable",
            data: "date=" + date + "&masterId=" + masterId,
            dataType: 'json',
            success: function (schedule) {
                createScheduleTableTh();
                for (var i = 0; i < schedule.length; i++) {
                    $("#business-schedule-table").append("<tr>" +
                        "<td>" + schedule[i].id + "</td>" +
                        "<td>" + schedule[i].clientFirstname + " " + schedule[i].clientLastname + "</td>" +
                        "<td>" + schedule[i].email + "</td>" +
                        "<td>" + schedule[i].service + "<img class='info-img' src='/resources/img/information.png' title=" + schedule[i].comment + "/></td></td>" +
                        "<td>" + schedule[i].masterFirstname + " " + schedule[i].masterLastname + "</td>" +
                        "<td>" + schedule[i].date + "</td>" +
                        "<td>" + schedule[i].startTime + "</td>" +
                        "<td><select id='booking" + schedule[i].id + "' class='form-control status'>" +
                        "<option <c:if test='" + schedule[i].id == 1 + "'>selected</c:if>>Waiting</option>" +
                        "<option <c:if test='" + schedule[i].id == 2 + "'>selected</c:if>>Visited</option>" +
                        "<option <c:if test='" + schedule[i].id == 3 + "'>selected</c:if>>Not visited</option>" +
                        "</select>" +
                        "</td>" +
                        "</tr>");
                }
            },
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        });
    }

    $(".status").change(function () {
        var status = $(this).children(":selected").val();
        var bookingId = $(this).attr("id");
        bookingId = bookingId.substring(7, bookingId.length);
        $.ajax({
            type: "GET",
            url: '/changeStatus',
            data: "bookingId=" + bookingId + "&status=" + status,
            error: function (xhr, textStatus) {
                alert([xhr.status, textStatus]);
            }
        })
    });

    function createScheduleTableTh() {
        $("#business-schedule-table").find("tr").remove();
        $("#business-schedule-table").append("<tr><th>#</th><th>Client</th><th>Client email</th> " +
            "<th>Service</th><th>Master</th><th>Date</th><th>Time</th><th>Status</th></tr>");
    }
</script>

<script src="<c:url value="/resources/js/date-picker-params.js" />" charset="UTF-8"></script>
<script src="<c:url value="/resources/js/master-info-popup.js" />" charset="UTF-8"></script>

</body>
</html>
