<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    <%@ include file="/resources/css/oqs.css" %>
</style>

<div class="footer navbar-fixed-bottom">
<c:set var="date" value="<%=new java.util.Date()%>"/>
<div class="col-md-4">
    <span class="copyright">Copyright &copy; Online queue system 2017</span>

<p><fmt:formatDate value="${date}" pattern="dd.MM.yyyy"/></p> </div>

<p>  online.queue.system@gmail.com</p>
</div>