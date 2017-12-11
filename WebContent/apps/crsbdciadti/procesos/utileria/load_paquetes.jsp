<%@ page session="true" %>

<%	
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@ page language="java" import="seguimiento.paquete" %>



</head>
<body>

<%
if(request.getParameter("prsg_id") != ""){
out.print(new paquete().generarCombo(request.getParameter("prsg_id"), "Activo", "0", "contenido_registro", "paqu_id", request.getParameter("soli_funciones")+"Carga('GET', '"+request.getParameter("soli_funciones")+"' );visual1([ 'paqu_id', 'soli' ]);"));
}
%>


</body>
</html>
<% } %>