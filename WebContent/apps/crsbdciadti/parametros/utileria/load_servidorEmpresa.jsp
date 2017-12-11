<%@ page session="true" %>

<%	
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@ page language="java" import="seguimiento.empresaServidor" %>



</head>
<body>

<%   
out.print(new empresaServidor().generarComboServidor(request.getParameter("empr_id"), "Activo", "0", "contenido_registro", "visual1([ 'serv_id', 'sgbd', 'bada', 'prod', 'pate', 'soli' ]);"+request.getParameter("sgbd_funciones")+"Carga('GET', '"+request.getParameter("sgbd_funciones")+"' ); servidor('serv_id'); sgbd('sgbd_id');"));
%>

</body>
</html>
<% } %>