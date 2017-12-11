<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ page language="java" import="java.sql.ResultSet, java.sql.Connection" %> 
<%@ page language="java" import="core.conexion" %>
<%@ page language="java" import="utileria.conversor" %>
<%@ page language="java" import="core.persona" %>


</head>
<body>

	<%
		conexion conn = new conexion();
		Connection con = conn.establecer();
	%>

<%
	if(request.getParameter("PERS_DOCUMENTO")!= null && request.getParameter("PERS_PASSWORD")!= null){
	
	String PERS_PASSWORD = new conversor().md5(request.getParameter("PERS_PASSWORD"));
	String referencia = "";
	String PERS_ID = "";
	String PERS_PRIMERNOMBRE = "";
	String PERS_SEGUNDONOMBRE = "";
	String PERS_PRIMERAPELLIDO = "";
	String PERS_SEGUNDOAPELLIDO = "";
	String PLANTILLA = "";
	String ALMACENAMIENTOTMP = "";
	
	try {  
	
	String where = "per.pers_documento = '"+request.getParameter("PERS_DOCUMENTO")+"' "+
					"and per.pers_acceso = TRUE";	
		
	ResultSet rsetpersona = new persona().where( where, con );
	
	while (rsetpersona.next()) {
	
		referencia = rsetpersona.getString("PERS_PASSWORD");		
		PERS_ID = rsetpersona.getString("PERS_ID");
		PERS_PRIMERNOMBRE = rsetpersona.getString("PERS_PRIMERNOMBRE");
		PERS_SEGUNDONOMBRE = rsetpersona.getString("PERS_SEGUNDONOMBRE");
		PERS_PRIMERAPELLIDO = rsetpersona.getString("PERS_PRIMERAPELLIDO");
		PERS_SEGUNDOAPELLIDO = rsetpersona.getString("PERS_SEGUNDOAPELLIDO");

	}
	}catch (Exception ex){}
	con.close();
	
	if( PERS_PASSWORD.equals(referencia) ){
		
		HttpSession sesionOk = request.getSession();
		sesionOk.setAttribute("PERS_ID",PERS_ID);
		sesionOk.setAttribute("PERS_DOCUMENTO", request.getParameter("PERS_DOCUMENTO"));
		sesionOk.setAttribute("PERS_PRIMERNOMBRE", PERS_PRIMERNOMBRE);
		sesionOk.setAttribute("PERS_SEGUNDONOMBRE", PERS_SEGUNDONOMBRE);
		sesionOk.setAttribute("PERS_PRIMERAPELLIDO", PERS_PRIMERAPELLIDO);
		sesionOk.setAttribute("PERS_SEGUNDOAPELLIDO", PERS_SEGUNDOAPELLIDO);
		sesionOk.setAttribute("PLANTILLA", "");
		sesionOk.setAttribute("ALMACENAMIENTOTMP", "");
		
		
		response.sendRedirect("./login.jsp");
		
	}else {
%>
	<jsp:forward page="index.jsp">
	<jsp:param name="error" value="Usuario y/o clave incorrectos.<br>Vuelve a intentarlo."/>
	</jsp:forward>
	<%
	
	}
	
}else {
	%>
	<jsp:forward page="index.jsp">
	<jsp:param name="error" value="Usuario y/o clave incorrectos.<br>Vuelve a intentarlo."/>
	</jsp:forward>
	<%
	}

%>

</body>
</html>