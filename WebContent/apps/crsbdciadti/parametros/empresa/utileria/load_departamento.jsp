<%@ page session="true" %>

<%	
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@ page language="java" import="core.departamento" %>



</head>
<body>
<% 
if(request.getParameter("pais_id") != ""){
out.print(new departamento().generarCombo(request.getParameter("pais_id"), "Activo", request.getParameter("depa_id"), "", "DEPA_ID", "document.getElementById('DEPA_ID_M').style.display='none'; "+request.getParameter("cepo_funciones")+"Carga('GET', '"+request.getParameter("cepo_funciones")+"' );"));
}
%>


</body>
</html>
<% } %>