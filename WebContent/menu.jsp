<%@ page session="true" %>

<%
	String PERS_DOCUMENTO = "";
HttpSession sesionOk = request.getSession();
if (sesionOk.getAttribute("PERS_DOCUMENTO") == null) {

	response.sendRedirect("./index.jsp?error=Es obligatorio identificarse para acceder al recurso solicitado");
	
} else {
	
	PERS_DOCUMENTO = (String)sesionOk.getAttribute("PERS_DOCUMENTO");
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page language="java" import="java.sql.ResultSet, java.sql.Connection" %>   
<%@ page language="java" import="core.conexion" %>
<%@ page language="java" import="core.permisos" %>


</head>
<body>
<%
	conexion con;
Connection conn;
%>


<%   
out.print(new permisos().generarMenuModulos( PERS_DOCUMENTO ));
%>







</body>
</html>

<% } %>