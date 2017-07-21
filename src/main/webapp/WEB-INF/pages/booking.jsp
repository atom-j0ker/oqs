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

<jsp:include page="fragments/header.jsp"/>

<sec:authorize access="isAuthenticated()">
    <sec:authentication var="user" property="principal.username"/>
</sec:authorize>

<div class="content">
    <div class="booking-form">
        <h2>${organization.name}</h2>
        <form action="/booking-add/${organization.id}/${user}/${service.id}" method="post"
              onsubmit="return confirmBooking();">

            <h4>${service.name}</h4>

            <select class="form-control" id="mastersListId" name="mastersListName">
                <option value="0" disabled selected>-- Choose master --</option>
                <c:forEach items="${masters}" var="master">
                    <option id="${master.id}">${master.user.firstname} ${master.user.lastname}</option>
                </c:forEach>
            </select>
            <img class="master-info-img" src="/resources/img/information.png"/>

            <div id="modal-form">
                <span id="modal-close">X</span>
            </div>
            <div id="overlay"></div>

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

                    <p>Choose time:</p>
                    <p><select class="form-control" id="timeListId" name="timeListName">
                        <option value="0" disabled selected>-- Time --</option>
                    </select>
                    </p>
                    <span class="error-msg" id="invalid-time"></span>
                </fieldset>

                <p><textarea class="form-control input-lg" name="bookingComment"
                             rows="5" placeholder="Write your wishes here"></textarea></p>

                <c:choose>
                    <c:when test="${pageContext.request.isUserInRole('USER')}">
                        <input type="submit" value="Booked" id="booked-btn" class="btn btn-primary submit-btn">
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="Booked" class="btn btn-primary submit-btn" title="please, sign in"
                               disabled>
                    </c:otherwise>
                </c:choose>

            </form>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<script type="text/javascript">
    var masterId;
    var date;
    var time;

    $("#mastersListId").change(function () {
        masterId = $(this).children(":selected").attr("id");
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

    $("#booked-btn").on("click", function confirmbooking() {
        var masterElement = document.getElementById("masterListId");
        var masterName = masterElement.options[masterElement.selectedIndex].text;
        if (confirm("Your booking:\n" + "master: " + masterName + "\ndate: " + date + "\ntime: " + time))
            return true;
        else
            return false;
    });

    function fillTimeDropDownList() {
        $.ajax({
            type: "GET",
            url: '/fillTimeList',
            data: {"masterId": masterId, "date": date},
            success: function (data) {
                var timeElement = document.getElementById("timeListId");
                timeElement.length = 1;
                for (var i = 0; i < data.first.length; i++) {
                    var option = new Option(data.first[i], data.first[i]);
                    for (var j = 0; j < data.second.length; j++) {
                        if (data.first[i] === data.second[j])
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

<script src="<c:url value="/resources/js/date-picker-params.js" />" charset="UTF-8"></script>
<script src="<c:url value="/resources/js/master-info-popup.js" />" charset="UTF-8"></script>
</body>
</html>
