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
<%@ page language="java" import="core.capitalOrigen" %>
<%@ page language="java" import="core.pais" %>
<%@ page language="java" import="utileria.loadFile" %>
<%@ page language="java" import="utileria.sql" %>
<%@ page language="java" import="seguimiento.empresa" %>


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
<form id="form" name="form" method="post" action="" onsubmit="return camposObligatorios(['EMPR_NOMBRE', 'EMPR_SIGLA', 'CAOR_ID', 'EMPR_ESTADO', 'PAIS_ID', 'DEPA_ID', 'CEPO_ID' ], [], [] );">
<input type="hidden" name="MODU_ID" id="MODU_ID" value="<%= request.getParameter("MODU_ID")%>" >
<input type="hidden" name="FUNC_ID" id="FUNC_ID" value="<%= request.getParameter("FUNC_ID")%>" >
<input type="hidden" name="PERS_ID" id="PERS_ID" value="<%= (String)request.getSession().getAttribute("PERS_ID")%>" >
<h1>Nueva empresa</h1>
<p>Diligencie el siguiente formulario para crear una nueva empresa</p>

<label for="EMPR_NOMBRE">*DENOMINACIÓN
<span class="small">Nombre de la empresa</span>
<span id="EMPR_NOMBRE_M"></span>
</label>
<input class="" type="text" name="EMPR_NOMBRE" id="EMPR_NOMBRE" maxlength="200"  onkeypress="document.getElementById('EMPR_NOMBRE_M').style.display='none';">

<label for="EMPR_SIGLA">*SIGLA
<span class="small">Sigla de la empresa</span>
<span id="EMPR_SIGLA_M"></span>
</label>
<input class="" type="text" name="EMPR_SIGLA" id="EMPR_SIGLA" maxlength="50" onkeypress="document.getElementById('EMPR_SIGLA_M').style.display='none';">

<label for="CAOR_ID">*NATURALEZA
<span class="small">Privada/Pública</span>
<span id="CAOR_ID_M"></span>
</label>
<%
out.print(new capitalOrigen().generarCombo( "Activo", "0", "", "CAOR_ID", "document.getElementById('CAOR_ID_M').style.display='none';" ));
%>

<label for="EMPR_ESTADO">*ESTADO
<span class="small">Estado de la empresa</span>
<span id="EMPR_ESTADO_M"></span>
</label>
<select name="EMPR_ESTADO" id="EMPR_ESTADO" onchange="document.getElementById('EMPR_ESTADO_M').style.display='none';">
<option value=""></option>
<option value="Activa">Activa</option>
<option value="Inactiva">Inactiva</option>
</select>

<label for="PAIS_ID">*PAÍS
<span class="small">País de la empresa</span>
<span id="PAIS_ID_M"></span>
</label>
<%
String depa_funciones = "depa";
String cepo_funciones = "cepo";		
String pais ="'../utileria/load_departamento.jsp?cepo_funciones="+cepo_funciones+"&pais_id='+document.getElementById(\"PAIS_ID\").value";
String departamento ="'../utileria/load_centroPoblado.jsp?cepo_funciones="+cepo_funciones+"&depa_id='+document.getElementById(\"DEPA_ID\").value";
out.print(new pais().generarCombo( "Activo", "0", "", "PAIS_ID", "document.getElementById('CEPO_ID').selectedIndex = 0; document.getElementById('PAIS_ID_M').style.display='none'; "+depa_funciones+"Carga('GET', "+depa_funciones+" )" ));

 
out.print(new loadFile().cargaAjaxFuncion(depa_funciones, depa_funciones, pais)); 
out.print(new loadFile().cargaAjaxFuncion(cepo_funciones, cepo_funciones, departamento));
%>

<label for="DEPA_ID">*DEPARTAMENTO
<span class="small">Departamento/Estado</span>
<span id="DEPA_ID_M"></span>
</label>

<div id="<%=depa_funciones%>">
<select id="DEPA_ID"></select>
</div>


<label for="CEPO_ID">*CENTRO POBLADO
<span class="small">Municipio</span>
<span id="CEPO_ID_M"></span>
</label>

<div id="<%=cepo_funciones%>">
<select id="CEPO_ID"></select>
</div>

<div class="spacer"></div>
<button type="submit" name="accion" value="guardar">Guardar</button>
<div class="spacer"></div>

</form>
</div>

<% } %>

<% if(request.getParameter("accion") != null && request.getParameter("accion").equals("guardar")){ %>

	<% String empr_id = new sql().secuencia("seguimiento.s_empr_id"); %>
	<% out.print(new empresa().insertar(empr_id, request)); %>
	
	<div id="stylized" class="myform">
		<h1>Detalle de la empresa</h1>
		<p>Información de la empresa creada.</p>

		<%
		out.print(new empresa().visualizar( empr_id ));
		%>
		<div class="spacer"></div>
	</div>
	
<% } %>

</body>
</html>
<% } %>