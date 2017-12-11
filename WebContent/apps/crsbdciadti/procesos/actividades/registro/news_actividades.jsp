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
<%@ page language="java" import="seguimiento.empresa" %>
<%@ page language="java" import="seguimiento.sgbd" %>
<%@ page language="java" import="utileria.loadFile" %>
<%@ page language="java" import="seguimiento.tipoRequerimiento" %>
<%@ page language="java" import="seguimiento.script" %>


<link rel="stylesheet" href="../../../../../<%=request.getSession().getAttribute("PLANTILLA")%>css/pages.css" />

<script type="text/javascript" src="../../../../../javaScript/jscal2.js"></script>
<script type="text/javascript" src="../../../../../javaScript/lang/en.js"></script>
<link rel="stylesheet" href="../../../../../javaScript/css/jscal2.css" />

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

<script type="text/javascript">
function validadorCamposObligatorios( campos ) {
		
	var index;
	var control=0;
		for	( index = 0; index < campos.length; index++) {
		var elemento = document.getElementById(campos[index]).value;
				if (elemento.length == 0 || /^\s+$/.test(elemento)) {
				document.getElementById(campos[index]+"_M").style.display='block';
				document.getElementById(campos[index]+"_M").innerHTML = '';
				document.getElementById(campos[index]+"_M").innerHTML = 'El campo '+document.getElementById(campos[index]+"_M").textContent+' es obligatorio';
				document.getElementById(campos[index]+"_M").className = "campoobligatorio";
				control+=1;
				
				} 
		}
		
		if(control>0){ return false;}
		
		var campo1 = document.getElementById("SOLI_FECHAINICIO").value;
		var campo2 = document.getElementById("SOLI_FECHAFIN").value;
		
				if (campo1 > campo2) {
				document.getElementById("SOLI_FECHAINICIO_M").style.display='block';
				document.getElementById("SOLI_FECHAINICIO_M").innerHTML = 'La fecha inicio debe ser inferior a la de finalización';
				document.getElementById("SOLI_FECHAINICIO_M").className = "campoobligatorio";
				return false;
				} 
		
		return true;
	}
	
function visual1( campos ) {

	var index=0;
	    
	    var elemento = document.getElementById(campos[index]).value;
	    if (elemento.length == 0 || /^\s+$/.test(elemento)) {

			for	( index = 1; index < campos.length; index++) {			
				
				document.getElementById(campos[index]).innerHTML = "";		
								
			}
		}else{					
			
			for	( index = 2; index < campos.length; index++) {			
				
				document.getElementById(campos[index]).innerHTML = "";		
								
			}				
			
		}
	    
}
	</script>

<form action="" method="post" name="reg_actividades" id="reg_actividades" class="formgeneral" onsubmit="return validadorCamposObligatorios(['empr_id', 'serv_id', 'sgbd_id', 'bada_id', 'prsg_id', 'paqu_id', 'SCRI_ESQUEMA', 'SCRI_MES', 'SOLI_FECHAINICIO', 'SOLI_FECHAFIN', 'tire_id', 'SCRI_NOMBRE', 'SCRI_SOLICITANTE', 'SCRI_COMPLEJIDAD', 'SOLI_PUBLICADO', 'tipr_id', 'SCRI_DESCRIPCION' ])">

<input type="hidden" name="MODU_ID" id="MODU_ID" value="<%= request.getParameter("MODU_ID")%>" >
<input type="hidden" name="FUNC_ID" id="FUNC_ID" value="<%= request.getParameter("FUNC_ID")%>" >
<input type="hidden" name="PERS_ID" id="PERS_ID" value="<%= (String)request.getSession().getAttribute("PERS_ID")%>" >

<div class="top_registro"><label for="empr_id">EMPRESA:</label></div>
<div class="contenido_registro">
<%
String sgbd_funciones = "sgbd";
String bada_funciones = "bada";
String prod_funciones = "prod";
String pate_funciones = "pate";
String soli_funciones = "soli";
String servidores ="'../../utileria/load_servidorEmpresa.jsp?sgbd_funciones="+sgbd_funciones+"&empr_id='+document.getElementById(\"empr_id\").value";
String sgbds ="'../../utileria/load_sgbd.jsp?bada_funciones="+bada_funciones+"&serv_id='+document.getElementById(\"serv_id\").value";
String badas ="'../../utileria/load_basedatos.jsp?prod_funciones="+prod_funciones+"&empr_id='+document.getElementById(\"empr_id\").value+'&sgbd_id='+document.getElementById(\"sgbd_id\").value";
String prods ="'../../utileria/load_productos.jsp?pate_funciones="+pate_funciones+"&sgbd_id='+document.getElementById(\"sgbd_id\").value";
String paqus ="'../../utileria/load_paquetes.jsp?soli_funciones="+soli_funciones+"&prsg_id='+document.getElementById(\"prsg_id\").value";
String solis ="'./load_news_actividades.jsp'";
String empr_funciones = "servidor";
out.print(new empresa().generarCombo( "Activa", "0", "", "empr_id", "document.getElementById('empr_id_M').style.display='none'; "+empr_funciones+"Carga('GET', "+empr_funciones+" ); visual1([ 'empr_id', 'servidor','sgbd', 'bada', 'prod', 'pate', 'soli' ]);" )); 

out.print(new loadFile().cargaAjaxFuncion(soli_funciones, soli_funciones, solis));
out.print(new loadFile().cargaAjaxFuncion(pate_funciones, pate_funciones, paqus));
out.print(new loadFile().cargaAjaxFuncion(prod_funciones, prod_funciones, prods)); 
out.print(new loadFile().cargaAjaxFuncion(bada_funciones, bada_funciones, badas)); 
out.print(new loadFile().cargaAjaxFuncion(sgbd_funciones, sgbd_funciones, sgbds)); 
out.print(new loadFile().cargaAjaxFuncion(empr_funciones, empr_funciones, servidores)); 

%>
</div>

<div class="top_registro cuarenta left"><label for="serv_id">SERVIDOR:</label></div>
<div class="top_registro cinco left"><label for="sgbd_id">SGBD:</label></div>
<div class="top_registro cuarenta left"><label for="bada_id">BASE DE DATOS:</label></div>
<div class="limpiar"></div>

<div class="contenido_registro cuarenta left">
<%
out.print("<div id=\""+empr_funciones+"\"></div>");
%>
</div>


<div class="contenido_registro cinco left">
<%
out.print("<div id=\""+sgbd_funciones+"\"></div>");
%>
</div>


<div class="contenido_registro cuarenta left">
<%
out.print("<div id=\""+bada_funciones+"\"></div>");
%>
</div>
<div class="limpiar"></div>

<div class="top_registro dos left"><label for="prsg_id">PRODUCTO:</label></div>
<div class="top_registro dos left"><label for="paqu_id">PAQUETE:</label></div>

<div class="limpiar"></div>

<div class="contenido_registro dos left">
<%
out.print("<div id=\""+prod_funciones+"\"></div>");
%>
</div>

<div class="contenido_registro dos left">
<%
out.print("<div id=\""+pate_funciones+"\"></div>");
%>
</div>
<div class="limpiar"></div>



<div class="limpiar"></div>

<div class="top_registro cinco left"><label for="SCRI_ESQUEMA">Esquema:</label></div>
<div class="top_registro cinco left"><label for="SCRI_MES">Mes:</label></div>
<div class="top_registro cinco left"><label for="SOLI_FECHAINICIO">Fecha inicio:</label></div>
<div class="top_registro cinco left"><label for="SOLI_FECHAFIN">Fecha fin:</label></div>
<div class="top_registro cinco left"><label for="TIRE_ID">Tipo de requerimiento:</label></div>

<div class="limpiar"></div>

<div class="contenido_registro cinco left">
<input class="typetext" type="text" name="SCRI_ESQUEMA" id="SCRI_ESQUEMA" maxlength="50" onkeypress="document.getElementById('SCRI_ESQUEMA_M').style.display='none';">
<label for="SCRI_ESQUEMA_M"><span id="SCRI_ESQUEMA_M" class="error"></span></label>
</div>

<div class="contenido_registro cinco left">

<select name="SCRI_MES" id="SCRI_MES" onchange="document.getElementById('SCRI_MES_M').style.display='none';">
<option value=""></option>
<option value="Enero">Enero</option>
<option value="Febrero">Febrero</option>
<option value="Marzo">Marzo</option>
<option value="Abril">Abril</option>
<option value="Mayo">Mayo</option>
<option value="Junio">Junio</option>
<option value="Julio">Julio</option>
<option value="Agosto">Agosto</option>
<option value="Septiembre">Septiembre</option>
<option value="Octubre">Octubre</option>
<option value="Noviembre">Noviembre</option>
<option value="Diciembre">Diciembre</option>
</select>

<label for="SCRI_MES_M"><span id="SCRI_MES_M" class="error"></span></label>
</div>


<div class="contenido_registro cinco left">

<input class="typetext" type="text" name="SOLI_FECHAINICIO" id="SOLI_FECHAINICIO" maxlength="0" onclick="document.getElementById('SOLI_FECHAINICIO_M').style.display='none';">
<label for="SOLI_FECHAINICIO_M"><span id="SOLI_FECHAINICIO_M" class="error"></span></label>
<script>
Calendar.setup({
    inputField : "SOLI_FECHAINICIO",
    trigger    : "SOLI_FECHAINICIO",
    onSelect   : function() { this.hide() }
});
</script>
</div>

<div class="contenido_registro cinco left">
<input class="typetext" type="text" name="SOLI_FECHAFIN" id="SOLI_FECHAFIN" maxlength="0" onclick="document.getElementById('SOLI_FECHAFIN_M').style.display='none';">
<label for="SOLI_FECHAFIN_M"><span id="SOLI_FECHAFIN_M" class="error"></span></label>
<script>
Calendar.setup({
    inputField : "SOLI_FECHAFIN",
    trigger    : "SOLI_FECHAFIN",
    onSelect   : function() { this.hide() }
});
</script>
</div>

<div class="contenido_registro cinco left">
<%
out.print(new tipoRequerimiento().generarCombo( "Activo", "0", "", "tire_id", "document.getElementById('tire_id_M').style.display='none';" ));
%>
</div>
<%
out.print("<div id=\""+soli_funciones+"\"></div>");
%>
</form>

<% } %>

<% if(request.getParameter("accion") != null && request.getParameter("accion").equals("Guardar")){ %>

<% out.print(new script().insertarActividad(request)); %>

<% } %>

</body>
</html>
<% } %>