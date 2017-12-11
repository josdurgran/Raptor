<%@ page session="true" %>

<%	
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@ page language="java" import="seguimiento.sgbd " %>



</head>
<body>
<%   
if(request.getParameter("serv_id") != ""){
out.print(new sgbd().generarCombo(request.getParameter("serv_id"), "Activo", "0", "contenido_registro", "sgbd_id", "visual1([ 'sgbd_id', 'bada', 'prod', 'pate', 'soli' ]);"+request.getParameter("bada_funciones")+"Carga('GET', '"+request.getParameter("bada_funciones")+"' );"));
}
%>


</body>
</html>
<% } %>