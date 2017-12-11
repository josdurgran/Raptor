<!DOCTYPE html>

<html>
<head>

<%@ page language="java" import="java.sql.Connection" %>
<%@ page language="java" import="core.conexion" %>
<%@ page language="java" import="java.sql.ResultSet" %>
<%@ page language="java" import="core.header" %>
<%@ page language="java" import="core.sitio" %>
<%@ page language="java" import="utileria.archivo" %>
<%@ page language="java" import="seguimiento.servidor" %>

<%
	out.print( new header().imprimir("(select siti_id from core.sitio where siti_descripcion = 'Neptuno - Centro de monitoreo')", "'css', 'javascript'") );
%>


<%
	Connection con = new conexion().establecer();

HttpSession sesionOk = request.getSession();

ResultSet siti = new sitio().imprimir( con, "(select siti_id from core.sitio where siti_descripcion = 'Neptuno - Centro de monitoreo')"); 

String siti_id = "";
String siti_descripcion = "";
String siti_plantillasitio = "";
String siti_tituloheader = "";
String siti_titulofooter = "";
String siti_favicon = "";
String siti_ruteador = "";
String siti_rutaalmacenamiento = "";
String siti_almacenamientotmp = "";
String siti_header = "";
String siti_cuerpo = "";



if (siti.next()){
	
	do{
		
		siti_id = siti.getString("siti_id");
		siti_descripcion = siti.getString("siti_descripcion");
		siti_plantillasitio = siti.getString("siti_plantillasitio");
		siti_tituloheader = siti.getString("siti_tituloheader");
		siti_titulofooter = siti.getString("siti_titulofooter");
		siti_favicon = siti.getString("siti_favicon");
		siti_ruteador = siti.getString("siti_ruteador");
		siti_almacenamientotmp = siti.getString("siti_almacenamientotmp");
		siti_header = siti.getString("siti_header");
		siti_cuerpo = siti.getString("siti_cuerpo");
		sesionOk.setAttribute("ALMACENAMIENTO", siti_rutaalmacenamiento);
		sesionOk.setAttribute("ALMACENAMIENTOTMP", siti_almacenamientotmp);
		sesionOk.setAttribute("PLANTMONITOR", siti_plantillasitio);
		sesionOk.setAttribute("BAR", siti_ruteador);
		
	}while(siti.next());
	
}		

new conexion().cerrar(con);

out.print(" <link rel=\"icon\" href=\""+siti_plantillasitio+"img/"+siti_favicon+"\" type=\"image/ico\" sizes=\"16x16\"> ");
out.print(siti_header);
%>

</head>

<body>
		
<% out.print( new servidor().monitor( request, "Activo" )); %>




</body>
</html>