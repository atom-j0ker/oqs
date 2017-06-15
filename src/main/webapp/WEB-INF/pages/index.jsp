<%@ page import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ page import="com.oqs.crud.UserDAO" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>online queue system</title>
</head>
<body>
<%
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserDAO userDAO = WebApplicationContextUtils.getWebApplicationContext(application).getBean(UserDAO.class);
    String username;
    long userId = 0;

    if (principal instanceof UserDetails) {
        username = ((UserDetails) principal).getUsername();
    } else {
        username = principal.toString();
    }

    if (!username.equals("anonymousUser"))
        userId = userDAO.getId(username);
    session.setAttribute("userId", userId);
%>

<a href="registration">Registration</a>
<a href="authorization">Authorization</a>

${userId}
</body>
</html>
