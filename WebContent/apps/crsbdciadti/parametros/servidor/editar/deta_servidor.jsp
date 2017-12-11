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
<%@ page language="java" import="seguimiento.centroDatos" %>
<%@ page language="java" import="seguimiento.servidor" %>
<%@ page language="java" import="seguimiento.empresa" %>
<%@ page language="java" import="seguimiento.empresaServidor" %>
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

<% if(request.getParameter("SERV_ID") == null){ %>

<% } %>


<% if(request.getParameter("SERV_ID") != null){ %>

<% if(request.getParameter("accion") != null && request.getParameter("accion").equals("agregar")){ %>

	<% out.print(new empresaServidor().insertar(request)); %>
	
<% } %>

<% if(request.getParameter("accion") != null && request.getParameter("accion").equals("quitar")){ %>

	<% out.print(new empresaServidor().eliminar(request)); %>
	
<% } %>

<div id="stylized" class="myform">
<form id="form" name="form" method="post" action="" onsubmit="return camposObligatorios(['SERV_HOST', 'SERV_IPV4', 'SERV_ESTADO', 'SERV_VIRTUALIZADO', 'CEDA_ID', 'SERV_DESCRIPCION', 'SERV_MONITOREO' ], ['SERV_MEMORIARAM', 'SERV_DISCODURO'], ['SERV_IPV4'] );">
<input type="hidden" name="PERS_ID" id="PERS_ID" value="<%= (String)request.getSession().getAttribute("PERS_ID")%>" >
<h1>Nuevo servidor</h1>
<p>Diligencie el siguiente formulario para crear un nuevo servidor</p>

<%
out.print(new servidor().editar( request.getParameter("SERV_ID")));
%>
<div class="spacer"></div>
</form>
</div>

<div id="stylized" class="myform">
	<form id="form" name="form" method="post" action="" onsubmit="return camposObligatorios(['EMPR_ID'], [], [] );">
		<input type="hidden" name="MODU_ID" id="MODU_ID" value="<%= request.getParameter("MODU_ID")%>" >
		<input type="hidden" name="FUNC_ID" id="FUNC_ID" value="<%= request.getParameter("FUNC_ID")%>" >
		<input type="hidden" name="SERV_ID" id="SERV_ID" value="<%=request.getParameter("SERV_ID") %>" >
		<input type="hidden" name="PERS_ID" id="PERS_ID" value="<%= (String)request.getSession().getAttribute("PERS_ID")%>" >
		<h1>Agregue empresas</h1>
		<p>Asocie las empresas a las que pertenece el servidor.</p>
		
		<label for="EMPR_ID">EMPRESA
		<span class="small">Asociada al servidor</span>
		<span id="EMPR_ID_M"></span>
		</label>
		<%
		out.print(new empresaServidor().generarEmpresas( request.getParameter("SERV_ID"), "EMPR_ID", "0", "", "document.getElementById('EMPR_ID_M').style.display='none';" ));
		%>
		
		<div class="spacer"></div>
		<button type="submit" name="accion" value="agregar">Agregar</button>
		<div class="spacer"></div>
</form>
</div>

<div id="stylized" class="myform">
<h1>Empresas asociadas</h1>
<p>Detalle de empresas asociadas al servidor.</p>

<%
out.print(new empresaServidor().visualizarAsociacion(request, request.getParameter("SERV_ID"), true));
%>
<div class="spacer"></div>
</div>
<% } %>


<% if(request.getParameter("accion") != null && request.getParameter("accion").equals("guardar")){ %>

	<% out.print(new servidor().update( request )); %>

<% } %>	
</body>
</html>
<% } %>