<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="footer">
    <c:set var="date" value="<%=new java.util.Date()%>"/>
    <div class="col-md-4">
        <span>Copyright &copy; Online queue system 2017</span>

        <p><fmt:formatDate value="${date}" pattern="dd.MM.yyyy"/></p> </div>

    <p style="float: right">online.queue.system@gmail.com</p>
</div>