<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body onload="Javascript:history.go(1);" onunload="Javascript:history.go(1);">

<%@ page session="true" %>
<%
response.setContentType("text/html;charset=UTF-8");
request.setCharacterEncoding("UTF-8");
HttpSession sesionOk = request.getSession();
sesionOk.invalidate();
response.sendRedirect("./index.jsp?error=Su sesión se ha cerrado correctamente.");
%>

</body>
</html>