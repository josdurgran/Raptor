<%@ page session="true" %>

<%	
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@ page language="java" import="seguimiento.sgbd" %>



</head>
<body>
<% 
if(request.getParameter("sgbd_id") != ""){
out.print(new sgbd().generarComboEmpresa(request.getParameter("sgbd_id"), "Activa", "0", "", "EMPR_ID", "document.getElementById('DEPA_ID_M').style.display='none';"));
}
%>


</body>
</html>
<% } %>