<%@ page language="java" import="java.sql.Connection" %>
<%@ page language="java" import="core.conexion" %>
<%@ page language="java" import="core.sitio" %>
<%@ page language="java" import="java.sql.ResultSet" %>



<%
	conexion conn = new conexion();
Connection con = conn.establecer();

ResultSet siti = new sitio().imprimir( con, "1"); 
String plantilla = "";

if (siti.next()){
	
	do{
		
	plantilla = siti.getString("siti_plantillasitio");	
		
	}while(siti.next());
	
}		
conn.cerrar(con);
%>

