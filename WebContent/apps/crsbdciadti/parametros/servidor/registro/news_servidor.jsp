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

<%
	out.print(new permisos().generarMenuFuncionalidad( request.getParameter("MODU_ID"), (String)request.getSession().getAttribute("PERS_ID"), false ));
%>
<%
	out.print(new permisos().generarMenuActividades( request.getParameter("MODU_ID"), request.getParameter("FUNC_ID"), (String)request.getSession().getAttribute("PERS_ID"), false ));
%>


<%
	if(request.getParameter("accion") == null){
%>


<div id="stylized" class="myform">
<form id="form" name="form" method="post" action="" onsubmit="return camposObligatorios(['SERV_HOST', 'SERV_IPV4', 'SERV_ESTADO', 'SERV_VIRTUALIZADO', 'CEDA_ID', 'SERV_DESCRIPCION', 'SERV_MONITOREO' ], ['SERV_MEMORIARAM', 'SERV_DISCODURO'], ['SERV_IPV4'] );">
<input type="hidden" name="MODU_ID" id="MODU_ID" value="<%=request.getParameter("MODU_ID")%>" >
<input type="hidden" name="FUNC_ID" id="FUNC_ID" value="<%=request.getParameter("FUNC_ID")%>" >
<input type="hidden" name="PERS_ID" id="PERS_ID" value="<%=(String)request.getSession().getAttribute("PERS_ID")%>" >
<h1>Nuevo servidor</h1>
<p>Diligencie el siguiente formulario para crear un nuevo servidor</p>

<label for="SERV_HOST">*HOST
<span class="small">Ingrese el host</span>
<span id="SERV_HOST_M"></span>
</label>
<input class="" type="text" name="SERV_HOST" id="SERV_HOST" maxlength="100" onkeypress="document.getElementById('SERV_HOST_M').style.display='none';">

<label for="SERV_IPV4">*IPV4
<span class="small">IPv4 del servidor</span>
<span id="SERV_IPV4_M"></span>
</label>
<input class="" type="text" name="SERV_IPV4" id="SERV_IPV4" maxlength="15" onkeypress="document.getElementById('SERV_IPV4_M').style.display='none';">

<label for="SERV_IPV6">IPV6
<span class="small">IPv6 del servidor</span>
</label>
<input class="" type="text" name="SERV_IPV6" id="SERV_IPV6" maxlength="50"/>

<label for="SERV_MAC">MAC
<span class="small">MAC del servidor</span>
</label>
<input class="" type="text" name="SERV_MAC" id="SERV_MAC" maxlength="20"/>

<label for="SERV_SO">SO
<span class="small">Sistema operativo</span>
</label>
<input class="" type="text" name="SERV_SO" id="SERV_SO" maxlength="150"/>

<label for="SERV_MEMORIARAM">RAM
<span class="small">Memoria RAM MB</span>
<span id="SERV_MEMORIARAM_M"></span>
</label>
<input class="" type="text" name="SERV_MEMORIARAM" id="SERV_MEMORIARAM" maxlength="20" onkeypress="document.getElementById('SERV_MEMORIARAM_M').style.display='none';">

<label for="SERV_DISCODURO">DD
<span class="small">Disco duro MB</span>
<span id="SERV_DISCODURO_M"></span>
</label>
<input class="" type="text" name="SERV_DISCODURO" id="SERV_DISCODURO" maxlength="20" onkeypress="document.getElementById('SERV_DISCODURO_M').style.display='none';">

<label for="SERV_PROCESADOR">PROCESADOR
<span class="small">Referencia del procesador</span>
</label>
<input class="" type="text" name="SERV_PROCESADOR" id="SERV_PROCESADOR" maxlength="200"/>

<label for="SERV_ESTADO">*ESTADO
<span class="small">Estado del servidor</span>
<span id="SERV_ESTADO_M"></span>
</label>
<select name="SERV_ESTADO" id="SERV_ESTADO" onchange="document.getElementById('SERV_ESTADO_M').style.display='none';">
<option value=""></option>
<option value="Activo">Activo</option>
<option value="Inactivo">Inactivo</option>
</select>

<label for="SERV_VIRTUALIZADO">*VIRTUALIZADO
<span class="small">Si/No</span>
<span id="SERV_VIRTUALIZADO_M"></span>
</label>
<select name="SERV_VIRTUALIZADO" id="SERV_VIRTUALIZADO" onchange="document.getElementById('SERV_VIRTUALIZADO_M').style.display='none';">
<option value=""></option>
<option value="Sí">Sí</option>
<option value="No">No</option>
</select>

<label for="CEDA_ID">*CENTRO DE DATOS
<span class="small">Ubicación</span>
<span id="CEDA_ID_M"></span>
</label>
<%
	out.print(new centroDatos().generarCombo( "Activo", "0", "", "CEDA_ID", "document.getElementById('CEDA_ID_M').style.display='none';" ));
%>
<label for="SERV_DESCRIPCION">*DESCRIPCION
<span class="small">Usos del dispositivo</span>
<span id="SERV_DESCRIPCION_M"></span>
</label>
<input class="" type="text" name="SERV_DESCRIPCION" id="SERV_DESCRIPCION" maxlength="500" onkeypress="document.getElementById('SERV_DESCRIPCION_M').style.display='none';">

<label for="SERV_MONITOREO">*MONITOREO
<span class="small">Si/No</span>
<span id="SERV_MONITOREO_M"></span>
</label>
<select name="SERV_MONITOREO" id="SERV_MONITOREO" onchange="document.getElementById('SERV_MONITOREO_M').style.display='none';">
<option value=""></option>
<option value="Sí">Sí</option>
<option value="No">No</option>
</select>

<label for="SERV_ALIAS">*ALIAS
<span class="small">Nombre para mostrar</span>
<span id="SERV_ALIAS_M"></span>
</label>
<input class="" type="text" name="SERV_ALIAS" id="SERV_ALIAS" maxlength="250" onkeypress="document.getElementById('SERV_ALIAS_M').style.display='none';">

<div class="spacer"></div>
<button type="submit" name="accion" value="guardar">Guardar</button>
<div class="spacer"></div>

</form>
</div>

<% } %>

<% if(request.getParameter("accion") != null && request.getParameter("accion").equals("guardar")){ %>


	<% String serv_id = new sql().secuencia("seguimiento.s_serv_id"); %>
	<% out.print(new servidor().insertar(serv_id, request)); %>
	
	<div id="stylized" class="myform">
	<form id="form" name="form" method="post" action="" onsubmit="return camposObligatorios(['EMPR_ID'], [], [] );">
		<input type="hidden" name="MODU_ID" id="MODU_ID" value="<%= request.getParameter("MODU_ID")%>" >
		<input type="hidden" name="FUNC_ID" id="FUNC_ID" value="<%= request.getParameter("FUNC_ID")%>" >
		<input type="hidden" name="SERV_ID" id="SERV_ID" value="<%=serv_id%>" >
		<input type="hidden" name="PERS_ID" id="PERS_ID" value="<%= (String)request.getSession().getAttribute("PERS_ID")%>" >
		<h1>Agregue empresas</h1>
		<p>Asocie las empresas a las que pertenece el servidor.</p>
		
		<label for="EMPR_ID">EMPRESA
		<span class="small">Asociada al servidor</span>
		<span id="EMPR_ID_M"></span>
		</label>
		<%
		out.print(new empresaServidor().generarEmpresas( serv_id, "EMPR_ID", "0", "", "document.getElementById('EMPR_ID_M').style.display='none';" ));
		%>
		
		<div class="spacer"></div>
		<button type="submit" name="accion" value="agregar">Agregar</button>
		<div class="spacer"></div>
</form>
</div>

<div id="stylized" class="myform">
<h1>Detalle del servidor</h1>
<p>Información del servidor creado.</p>

<%
out.print(new servidor().visualizar( serv_id ));
%>
<div class="spacer"></div>
</div>

<div id="stylized" class="myform">
<h1>Empresas asociadas</h1>
<p>Detalle de empresas asociadas al servidor.</p>

<%
out.print(new empresaServidor().visualizarAsociacion( request, serv_id, false ));
%>
<div class="spacer"></div>
</div>
	
<% } %>


<% if(request.getParameter("accion") != null && request.getParameter("accion").equals("agregar")){ %>

	<% out.print(new empresaServidor().insertar(request)); %>
	
<div id="stylized" class="myform">
<form id="form" name="form" method="post" action="" onsubmit="return camposObligatorios(['EMPR_ID'], [], [] );">
<input type="hidden" name="MODU_ID" id="MODU_ID" value="<%= request.getParameter("MODU_ID")%>" >
<input type="hidden" name="FUNC_ID" id="FUNC_ID" value="<%= request.getParameter("FUNC_ID")%>" >
<input type="hidden" name="SERV_ID" id="SERV_ID" value="<%= request.getParameter("SERV_ID")%>" >
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
<h1>Detalle del servidor</h1>
<p>Información del servidor creado.</p>

<%
out.print(new servidor().visualizar( request.getParameter("SERV_ID")));
%>
<div class="spacer"></div>
</div>

<div id="stylized" class="myform">
<h1>Empresas asociadas</h1>
<p>Detalle de empresas asociadas al servidor.</p>

<%
out.print(new empresaServidor().visualizarAsociacion( request, request.getParameter("SERV_ID"), false ));
%>
<div class="spacer"></div>
</div>
	
<% } %>

</body>
</html>
<% } %>