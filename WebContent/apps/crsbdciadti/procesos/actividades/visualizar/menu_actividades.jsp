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
<%@ page language="java" import="seguimiento.empresa" %>
<%@ page language="java" import="seguimiento.sgbd" %>
<%@ page language="java" import="utileria.loadFile" %>
<%@ page language="java" import="seguimiento.tipoRequerimiento" %>
<%@ page language="java" import="seguimiento.script" %>


<link rel="stylesheet" href="../../../../../<%=request.getSession().getAttribute("PLANTILLA")%>css/pages.css" />

<script type="text/javascript" src="../../../../../javaScript/jscal2.js"></script>
<script type="text/javascript" src="../../../../../javaScript/lang/en.js"></script>
<link rel="stylesheet" href="../../../../../javaScript/css/jscal2.css" />

</head>
<body>

<%
	conexion con;
Connection conn;
%>

	<% if(request.getParameter("condicion") == null || (request.getParameter("valor") != null && request.getParameter("valor").equals(""))){ %>
		
		<% out.print(new script().misActividades(request, "esp.espr_nombre NOT IN ('FINALIZADO', 'APLAZADO') AND scr.pers_id = "+(String)request.getSession().getAttribute("PERS_ID")+" ")); %>
	
	<% }else{ %>
	
		<% out.print(new script().misActividades( request, "upper("+request.getParameter("campo")+") like upper('%"+request.getParameter("valor")+"%')" )); %>
	
	<% } %>


</body>
</html>
<% } %>