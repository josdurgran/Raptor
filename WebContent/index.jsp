<!DOCTYPE html>

<html>
<head>
<%@ page language="java" import="java.sql.Connection" %>
<%@ page language="java" import="core.conexion" %>
<%@ page language="java" import="java.sql.ResultSet" %>
<%@ page language="java" import="core.header" %>
<%@ page language="java" import="core.sitio" %>

<%
	out.print( new header().imprimir("1", "'css', 'javascript'") );
%>

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
		
	}while(siti.next());
	
}		
conn.cerrar(con);
out.print(" <link rel=\"icon\" href=\""+siti_plantillasitio+"img/"+siti_favicon+"\" type=\"image/ico\" sizes=\"16x16\"> ");
out.print(siti_header);
%>


</head>

<body onload="Javascript:history.go(1);" onunload="Javascript:history.go(1);">


<% 
out.print( siti_tituloheader );
out.print("<div class=\"contenedor\">");
out.print( siti_cuerpo );
if(request.getParameter("error")!=null){ out.println("<br><br><strong class='error right'>"+request.getParameter("error")+"</strong>"); }
out.print( "<img src=\""+siti_plantillasitio+"img/web.png\" class=\"imagenes\">" );
out.print("</div>");
out.print( siti_titulofooter );
%>

</body>
</html>