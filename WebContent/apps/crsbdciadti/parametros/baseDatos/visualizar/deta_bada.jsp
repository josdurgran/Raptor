<%@ page session="true" %>

<%
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page language="java" import="java.sql.ResultSet, java.sql.Connection" %>   
<%@ page language="java" import="core.conexion" %>
<%@ page language="java" import="core.permisos" %>
<%@ page language="java" import="seguimiento.baseDatos" %>


<link rel="stylesheet" href="../../../../../<%=request.getSession().getAttribute("PLANTILLA")%>css/pages.css" />

<script type="text/javascript" src="../../../../../javaScript/jscal2.js"></script>
<script type="text/javascript" src="../../../../../javaScript/lang/en.js"></script>
<link rel="stylesheet" href="../../../../../javaScript/css/jscal2.css" />


	<script type="text/javascript">
	function camposObligatorios( campos, numeros, ip ) {
		
		var index;
		var control=0;
			for	( index = 0; index < campos.length; index++) {
			var elemento = document.getElementById(campos[index]).value;
					if (elemento.length == 0 || /^\s+$/.test(elemento)) {
					document.getElementById(campos[index]+"_M").style.display='block';
					document.getElementById(campos[index]+"_M").innerHTML = '';
					document.getElementById(campos[index]+"_M").innerHTML = '*Campo obligatorio';
					document.getElementById(campos[index]+"_M").className = "validar";
					control+=1;
					
					} 
			}
			
			for	( index = 0; index < numeros.length; index++) {
				var elemento = document.getElementById(numeros[index]).value;
				if (!elemento.length == 0 || !/^\s+$/.test(elemento)) {				
					
					if (!/^([0-9])*$/.test(elemento)) {
						document.getElementById(numeros[index]+"_M").style.display='block';
						document.getElementById(numeros[index]+"_M").innerHTML = '';
						document.getElementById(numeros[index]+"_M").innerHTML = '*Debe ser númerico';
						document.getElementById(numeros[index]+"_M").className = "validar";
						control+=1;
					  							  	
					}								
				}				
			}
			
			
			for	( index = 0; index < ip.length; index++) {
				
				var elemento = document.getElementById(ip[index]).value;
				if (!elemento.length == 0 || !/^\s+$/.test(elemento)) {				
					
					if (!/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(elemento)) {
						document.getElementById(ip[index]+"_M").style.display='block';
						document.getElementById(ip[index]+"_M").innerHTML = '';
						document.getElementById(ip[index]+"_M").innerHTML = '*Debe ser una IPv4';
						document.getElementById(ip[index]+"_M").className = "validar";
						control+=1;
					  							  	
					}								
				}				
			}
			
			if(control>0){ return false;}
			
			return true;
	}
		
	</script>

</head>
<body>
<%
	conexion con;
Connection conn;
%>

<% if(request.getParameter("BADA_ID") == null){ %>

<% } %>


<% if(request.getParameter("BADA_ID") != null){ %>

	<div id="stylized" class="myform">
		<h1>Detalle de la Base de datos</h1>
			<p>Información de la Base de Datos.</p>

			<%
			out.print(new baseDatos().visualizar( request.getParameter("BADA_ID")));
			%>
		<div class="spacer"></div>
	</div>


<% } %>
	
</body>
</html>
<% } %>