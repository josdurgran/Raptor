<%@ page session="true" %>

<%
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@ page language="java" import="seguimiento.baseDatos" %>



</head>
<body>
<%
	if(request.getParameter("sgbd_id") != ""){
out.print(new baseDatos().generarCombo(request.getParameter("sgbd_id"), request.getParameter("empr_id"), "Activa", "0", "contenido_registro", "bada_id", request.getParameter("prod_funciones")+"Carga('GET', '"+request.getParameter("prod_funciones")+"' ); visual1([ 'bada_id', 'prod', 'pate', 'soli' ]); basedatos('bada_id')"));
}
%>


</body>
</html>
<% } %>