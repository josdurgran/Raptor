<%@ page session="true" %>

<%	
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@ page language="java" import="core.centroPoblado" %>



</head>
<body>
<% 
if(request.getParameter("DEPA_ID") != ""){
out.print(new centroPoblado().generarCombo(request.getParameter("depa_id"), "Activo", request.getParameter("cepo_id"), "", "CEPO_ID", "document.getElementById('CEPO_ID_M').style.display='none';"));
}
%>


</body>
</html>
<% } %>