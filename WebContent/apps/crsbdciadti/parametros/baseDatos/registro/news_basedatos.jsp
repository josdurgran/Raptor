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
<%@ page language="java" import="seguimiento.sgbd" %>
<%@ page language="java" import="utileria.loadFile" %>
<%@ page language="java" import="utileria.sql" %>
<%@ page language="java" import="seguimiento.entorno" %>

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
out.print(new loadFile().cargaAjax());
%>

<%   
out.print(new permisos().generarMenuFuncionalidad( request.getParameter("MODU_ID"), (String)request.getSession().getAttribute("PERS_ID"), false ));
%>
<%   
out.print(new permisos().generarMenuActividades( request.getParameter("MODU_ID"), request.getParameter("FUNC_ID"), (String)request.getSession().getAttribute("PERS_ID"), false ));
%>


<% if(request.getParameter("accion") == null){ %>


<div id="stylized" class="myform">
<form id="form" name="form" method="post" action="" onsubmit="return camposObligatorios(['BADA_NOMBRE', 'BADA_DESCRIPCION', 'BADA_PUERTO', 'BADA_ESTADO', 'SGBD_ID', 'EMPR_ID', 'ENTO_ID'], ['BADA_PUERTO'], [] );">
<input type="hidden" name="MODU_ID" id="MODU_ID" value="<%= request.getParameter("MODU_ID")%>" >
<input type="hidden" name="FUNC_ID" id="FUNC_ID" value="<%= request.getParameter("FUNC_ID")%>" >
<input type="hidden" name="PERS_ID" id="PERS_ID" value="<%= (String)request.getSession().getAttribute("PERS_ID")%>" >
<h1>Nueva Base de datos</h1>
<p>Diligencie el siguiente formulario para crear una nueva base de datos</p>

<label for="BADA_NOMBRE">*NOMBRE
<span class="small">De la base de datos</span>
<span id="BADA_NOMBRE_M"></span>
</label>
<input class="" type="text" name="BADA_NOMBRE" id="BADA_NOMBRE" maxlength="100"/>

<label for="BADA_DESCRIPCION">*DESCRIPCIÓN
<span class="small">Describa la BD</span>
<span id="BADA_DESCRIPCION_M"></span>
</label>
<input class="" type="text" name="BADA_DESCRIPCION" id="BADA_DESCRIPCION" maxlength="1000"/>

<label for="BADA_ENCODING">ENCODING
<span class="small">Usado por la BD</span>
<span id="BADA_ENCODING_M"></span>
</label>
<input class="" type="text" name="BADA_ENCODING" id="BADA_ENCODING" maxlength="100"/>

<label for="BADA_PUERTO">*PUERTO
<span class="small">Usado por la BD</span>
<span id="BADA_PUERTO_M"></span>
</label>
<input class="" type="text" name="BADA_PUERTO" id="BADA_PUERTO" maxlength="7"/>

<label for="BADA_ESTADO">*ESTADO
<span class="small">Actual</span>
<span id="BADA_ESTADO_M"></span>
</label>
<select name="BADA_ESTADO" id="BADA_ESTADO" onchange="document.getElementById('BADA_ESTADO_M').style.display='none';">
<option value=""></option>
<option value="Activa">Activa</option>
<option value="Inactiva">Inactiva</option>
<option value="Eliminada">Eliminada</option>
</select>

<label for="SGBD_ID">*SGBD
<span class="small">De la BD</span>
<span id="SGBD_ID_M"></span>
</label>

<%
String empr_funciones = "empr";
String empresaservidor ="'../utileria/load_empresaservidor.jsp?empr_funciones="+empr_funciones+"&sgbd_id='+document.getElementById(\"SGBD_ID\").value";
out.print(new sgbd().generarCombo( "Activo", "0", "", "SGBD_ID", "document.getElementById('SGBD_ID_M').style.display='none'; "+empr_funciones+"Carga('GET', "+empr_funciones+" )" ));
out.print(new loadFile().cargaAjaxFuncion(empr_funciones, empr_funciones, empresaservidor));
%>

<label for="EMPR_ID">EMPRESA
<span class="small">Empresa</span>
<span id="EMPR_ID_M"></span>
</label>

<div id="<%=empr_funciones%>">
<select id="EMPR_ID"></select>
</div>

<label for="ENTO_ID">*ENTORNO
<span class="small">De uso</span>
<span id="ENTO_ID_M"></span>
</label>

<%
out.print(new entorno().generarCombo( "Activo", "0", "", "ENTO_ID", "document.getElementById('ENTO_ID_M').style.display='none';" ));
%>

<div class="spacer"></div>
<button type="submit" name="accion" value="guardar">Guardar</button>
<div class="spacer"></div>

</form>
</div>

<% } %>

<% if(request.getParameter("accion") != null && request.getParameter("accion").equals("guardar")){ %>

	<% String bada_id = new sql().secuencia("seguimiento.s_bada_id"); %>
	<% out.print(new baseDatos().insertar(bada_id, request)); %>
	
	<div id="stylized" class="myform">
		<h1>Detalle de la empresa</h1>
		<p>Información de la empresa creada.</p>

		<%
		out.print(new baseDatos().visualizar( bada_id ));
		%>
		<div class="spacer"></div>
	</div>
	
<% } %>

</body>
</html>
<% } %>