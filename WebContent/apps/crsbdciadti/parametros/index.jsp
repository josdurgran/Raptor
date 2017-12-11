<%@ page session="true" %>

<%
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page language="java" import="java.sql.ResultSet, java.sql.Connection" %>   
<%@ page language="java" import="core.conexion" %>
<%@ page language="java" import="core.permisos" %>

<link rel="stylesheet" href="../../../<%=request.getSession().getAttribute("PLANTILLA")%>css/pages.css" />

</head>
<body>
<%
	conexion con;
Connection conn;
%>

<%   
out.print(new permisos().generarMenuFuncionalidad( request.getParameter("MODU_ID"), request.getParameter("PERS_ID"), true ));
%>






</body>
</html>
<% } %>