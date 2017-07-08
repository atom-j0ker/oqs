<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    <%@ include file="/resources/css/oqs.css" %>
</style>
<%--<script src="/resources/js/jquery.js"></script>--%>

<c:set var="url" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<sec:authentication var="user" property="principal"/>
<sec:authorize access="isAuthenticated()">
    <c:set var="username" value="${user.username}"/>
</sec:authorize>

<nav class="navbar navbar-default navbar-fixed-top header-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/"><span class="header-name">OQS</span></a>
        </div>

        <ul class="nav navbar-nav navbar-right">
            <li><a href="/"><span class="header-text">Home page</span></a></li>
            <li><a href="#services" id="organizations-btn" style="visibility: hidden">
                <span class="header-text">Organizations</span></a></li>
            <li><a href="#about" id="about-btn" style="visibility: hidden">
                <span class="header-text">About</span></a></li>
            <li><a href="#contact" id="contact-btn" style="visibility: hidden">
                <span class="header-text">Contact</span></a></li>

            <sec:authorize access="isAuthenticated()">
                <li><a href="/logout"><span class="header-text">Logout</span></a></li>
                <li><a href="#" onclick="document.getElementById('my-profile').submit();">
                    <span class="header-text">My profile</span></a></li>
                <form id="my-profile" action="my-profile" method="post">
                    <input type="hidden" name="username" value="${username}">
                </form>
            </sec:authorize>
            <sec:authorize access="isAnonymous()">
                <li><a href="/authorization"><span class="header-text">Sign in</span></a></li>
                <li><a href="/registration"><span class="header-text">Sign up</span></a></li>
            </sec:authorize>
        </ul>
    </div>
</nav>

<script>
    if (${url == "/"}) {
        document.getElementById("organizations-btn").style.visibility = "visible";
        document.getElementById("about-btn").style.visibility = "visible";
        document.getElementById("contact-btn").style.visibility = "visible";
    }
</script>