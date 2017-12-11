<%@ page session="true" %>

<%@ page language="java" import="java.sql.Connection" %>
<%@ page language="java" import="core.conexion" %>
<%@ page language="java" import="java.sql.ResultSet" %>
<%@ page language="java" import="core.header" %> 
<%@ page language="java" import="core.sitio" %> 

<%
 	out.print( new header().imprimir("1", "'css', 'javascript'") );
 %>

<%
String PERS_DOCUMENTO = "";
String PERS_PRIMERNOMBRE = "";
String PERS_SEGUNDONOMBRE = "";
String PERS_PRIMERAPELLIDO = "";
String PERS_SEGUNDOAPELLIDO = "";

HttpSession sesionOk = request.getSession();
if (sesionOk.getAttribute("PERS_DOCUMENTO") == null) {

	response.sendRedirect("./index.jsp?error=Es obligatorio identificarse para acceder al recurso solicitado");
	
} else {
	
	PERS_DOCUMENTO = (String)sesionOk.getAttribute("PERS_DOCUMENTO");
	PERS_PRIMERNOMBRE = (String)sesionOk.getAttribute("PERS_PRIMERNOMBRE");
	PERS_SEGUNDONOMBRE = (String)sesionOk.getAttribute("PERS_SEGUNDONOMBRE");
	PERS_PRIMERAPELLIDO = (String)sesionOk.getAttribute("PERS_PRIMERAPELLIDO");
	PERS_SEGUNDOAPELLIDO = (String)sesionOk.getAttribute("PERS_SEGUNDOAPELLIDO");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
	conexion conn = new conexion();
Connection con = conn.establecer();

ResultSet siti = new sitio().imprimir( con, "1"); 

String siti_id= "";
String siti_descripcion= "";
String siti_plantillasitio= "";
String siti_tituloheader= "";
String siti_titulofooter= "";
String siti_favicon= "";
String siti_ruteador= "";
String siti_rutaalmacenamiento= "";
String siti_almacenamientotmp= "";
String siti_header= "";
String siti_cuerpo= "";



if (siti.next()){
	
	do{
		
		siti_id = siti.getString("siti_id");
		siti_descripcion = siti.getString("siti_descripcion");
		siti_plantillasitio = siti.getString("siti_plantillasitio");
		siti_tituloheader = siti.getString("siti_tituloheader");
		siti_titulofooter = siti.getString("siti_titulofooter");
		siti_favicon = siti.getString("siti_favicon");
		siti_ruteador = siti.getString("siti_ruteador");
		siti_rutaalmacenamiento = siti.getString("siti_rutaalmacenamiento");
		siti_almacenamientotmp = siti.getString("siti_almacenamientotmp");
		siti_header = siti.getString("siti_header");
		siti_cuerpo = siti.getString("siti_cuerpo");
		sesionOk.setAttribute("PLANTILLA", siti_plantillasitio);
		sesionOk.setAttribute("ALMACENAMIENTO", siti_rutaalmacenamiento);
		sesionOk.setAttribute("ALMACENAMIENTOTMP", siti_almacenamientotmp);
		sesionOk.setAttribute("SEP", siti_ruteador);
		
	}while(siti.next());
	
}		
conn.cerrar(con);
out.print(" <link rel=\"icon\" href=\""+siti_plantillasitio+"img/"+siti_favicon+"\" type=\"image/ico\" sizes=\"16x16\"> ");
out.print(siti_header);
%>

</head>
<body onload="Javascript:history.go(1);" onunload="Javascript:history.go(1);">

<% out.print( siti_tituloheader ); %>

<div class="limpiar"></div>
<div class="contenedor">
<h5 class="nombre right">
Saludos, <%=PERS_PRIMERNOMBRE %> <%=PERS_SEGUNDONOMBRE %> <%=PERS_PRIMERAPELLIDO %> <%=PERS_SEGUNDOAPELLIDO %>
<a href="./logout.jsp" target="_top" class="menuprincipal bordestop">[Salir]</a>
</h5>
<br>
<div class="limpiar"></div>
<iframe src="menu.jsp" width="10%" height="72%"  frameBorder="0" name="menu" /></iframe>
<iframe src="bienvenida.jsp" width="89%" height="72%" frameBorder="0" name="contenido" /></iframe>
</div>
<% out.print( siti_titulofooter ); %>

</body>
</html>
<% } %>