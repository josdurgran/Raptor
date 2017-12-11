<%@ page session="true" %>

<%	
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@ page language="java" import="seguimiento.producto" %>



</head>
<body>
<%  
out.print(new producto().generarCombo(request.getParameter("sgbd_id"), "Activo", "0", "contenido_registro", "prsg_id", "visual1([ 'prsg_id', 'pate', 'soli' ]);"+request.getParameter("pate_funciones")+"Carga('GET', '"+request.getParameter("pate_funciones")+"' ); producto('prsg_id')"));
%>


</body>
</html>
<% } %>