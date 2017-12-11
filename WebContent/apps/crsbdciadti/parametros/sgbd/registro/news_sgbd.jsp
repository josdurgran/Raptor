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
<%@ page language="java" import="seguimiento.servidor" %>
<%@ page language="java" import="seguimiento.sgbd" %>
<%@ page language="java" import="seguimiento.tipoSgbd" %>
<%@ page language="java" import="utileria.sql" %>

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

<%   
out.print(new permisos().generarMenuFuncionalidad( request.getParameter("MODU_ID"), (String)request.getSession().getAttribute("PERS_ID"), false ));
%>
<%   
out.print(new permisos().generarMenuActividades( request.getParameter("MODU_ID"), request.getParameter("FUNC_ID"), (String)request.getSession().getAttribute("PERS_ID"), false ));
%>


<% if(request.getParameter("accion") == null){ %>


<div id="stylized" class="myform">
<form id="form" name="form" method="post" action="" onsubmit="return camposObligatorios(['SERV_ID', 'SGBD_ESTADO', 'TISG_ID', 'SGBD_LICENCIADO'], ['SGBD_PUERTO'], [] );">
<input type="hidden" name="MODU_ID" id="MODU_ID" value="<%= request.getParameter("MODU_ID")%>" >
<input type="hidden" name="FUNC_ID" id="FUNC_ID" value="<%= request.getParameter("FUNC_ID")%>" >
<input type="hidden" name="PERS_ID" id="PERS_ID" value="<%= (String)request.getSession().getAttribute("PERS_ID")%>" >
<h1>Nuevo SGBD</h1>
<p>Diligencie el siguiente formulario para crear un nuevo SGBD</p>

<label for="SERV_ID">*SERVIDOR
<span class="small">Servidor que lo aloja</span>
<span id="SERV_ID_M"></span>
</label>

		<%
		out.print(new servidor().generarCombo( "Activo", "0", "", "SERV_ID", "document.getElementById('SERV_ID_M').style.display='none';" ));
		%>

<label for="SGBD_ESTADO">*ESTADO
<span class="small">Estado actual</span>
<span id="SGBD_ESTADO_M"></span>
</label>
<select name="SGBD_ESTADO" id="SGBD_ESTADO" onchange="document.getElementById('SGBD_ESTADO_M').style.display='none';">
<option value=""></option>
<option value="Activo">Activo</option>
<option value="Inactivo">Inactivo</option>
</select>

<label for="TISG_ID">*TIPO
<span class="small">Elija un tipo</span>
<span id="TISG_ID_M"></span>
</label>

<%
	out.print(new tipoSgbd().generarCombo( "Activo", "0", "", "TISG_ID", "document.getElementById('TISG_ID_M').style.display='none';" ));
%>

<label for="SGBD_PUERTO">PUERTO
<span class="small">Elija el puerto</span>
<span id="SGBD_PUERTO_M"></span>
</label>
<input class="" type="text" name="SGBD_PUERTO" id="SGBD_PUERTO" maxlength="7"/>

<label for="SGBD_VERSION">VERSIÓN
<span class="small">Versión del SGBD</span>
<span id="SGBD_VERSION_M"></span>
</label>
<input class="" type="text" name="SGBD_VERSION" id="SGBD_VERSION" maxlength="100"/>

<label for="SGBD_LICENCIADO">*LICENCIADO
<span class="small">Sí/No</span>
<span id="SGBD_LICENCIADO_M"></span>
</label>
<select name="SGBD_LICENCIADO" id="SGBD_LICENCIADO" onchange="document.getElementById('SGBD_LICENCIADO_M').style.display='none';">
<option value=""></option>
<option value="Sí">Sí</option>
<option value="No">No</option>
</select>

<div class="spacer"></div>
<button type="submit" name="accion" value="guardar">Guardar</button>
<div class="spacer"></div>

</form>
</div>

<% } %>

<% if(request.getParameter("accion") != null && request.getParameter("accion").equals("guardar")){ %>

	<% String sgbd_id = new sql().secuencia("seguimiento.s_sgbd_id"); %>
	<% out.print(new sgbd().insertar(sgbd_id, request)); %>
	
	<div id="stylized" class="myform">
		<h1>Detalle del SGBD</h1>
		<p>Información del Sistema Gestor de Base de Datos.</p>

		<%
		out.print(new sgbd().visualizar( sgbd_id ));
		%>
		<div class="spacer"></div>
	</div>
	
<% } %>

</body>
</html>
<% } %>