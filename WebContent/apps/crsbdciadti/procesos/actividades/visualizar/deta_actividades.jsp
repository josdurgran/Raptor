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
<%@ page language="java" import="seguimiento.solicitud" %>
<%@ page language="java" import="seguimiento.observacion" %>
<%@ page language="java" import="seguimiento.estadoProceso" %>
<%@ page language="java" import="seguimiento.ajuste" %>
<%@ page language="java" import="utileria.archivo" %>


<link rel="stylesheet" href="../../../../../<%=request.getSession().getAttribute("PLANTILLA")%>css/pages.css" />

<script type="text/javascript" src="../../../../../javaScript/jscal2.js"></script>
<script type="text/javascript" src="../../../../../javaScript/lang/en.js"></script>
<link rel="stylesheet" href="../../../../../javaScript/css/jscal2.css" />

</head>
<body>

<%
	conexion con;
Connection conn;
%>

<script type="text/javascript">
function mostrareldiv(ventana) {
document.getElementById(ventana).style.display = "block" ; // permite asignar display:block al elemento #modal
}

function ocultareldiv(ventana) {
document.getElementById(ventana).style.display = "none" ; // permite ocultar el contenedor al hacer clic en alguna parte de Ã©ste mediante display:none en el elemento #modal
}

function maximizareldiv() {
document.getElementById("contenido-formulario").style.width = "90%" ;
document.getElementById("maximizar").onclick = minimizareldiv;

}

function minimizareldiv() {
	document.getElementById("contenido-formulario").style.width = "60%" ;
	document.getElementById("maximizar").onclick = maximizareldiv;
	}

</script>


<div id="modal" class="modal" style="display:none">

<div id="contenido-formulario">
<div class="contenido-formulario_1">Agregar observación 
<span onclick="ocultareldiv('modal')" id="cerrar"><img border="0" src="../../../../../<%=(String)request.getSession().getAttribute("PLANTILLA")%>img/cerrar.png" /></span> 
<span onclick="maximizareldiv()" id="maximizar"><img border="0" src="../../../../../<%=(String)request.getSession().getAttribute("PLANTILLA")%>img/screen.png" /></span>
</div>

<div class="contenido-formulario_2">

<div class="contenido-formulario_3">
<img border="0" src="../../../../../<%=(String)request.getSession().getAttribute("PLANTILLA")%>img/registro.png" />
</div>

<div class="contenido-formulario_4">

<%
String acceso = "";
String scri_nombre = "";
String soli_id = "";
String mensaje = "";
archivo subirArchivo = new archivo();
acceso = subirArchivo.subir(request);
String[] disolucion = acceso.split(":::");
if(disolucion.length > 0){
acceso = disolucion[0];
scri_nombre = disolucion[1];
mensaje = disolucion[2];
}
%>


<form action="" method="post" name="gest_observacion" id="gest_observacion" enctype="multipart/form-data">
<input type="hidden" name="funcion" id="funcion" value="archivar">
<input type="hidden" name="soli_id" id="soli_id" value="<% if( request.getParameter("soli_id") != null ){ out.print(request.getParameter("soli_id"));}else{ out.print(acceso);} %>">
<input type="hidden" name="scri_nombre" id="scri_nombre" value="<% if( request.getParameter("scri_nombre") != null ){ out.print(request.getParameter("scri_nombre"));}else{ out.print(scri_nombre);} %>">
<label for="tiob_id">Tipo:</label><select name="tiob_id" id="tiob_id">
<option value="3">Seguimiento</option>
<option value="4">Informe</option>
</select>
<label for="obse_fecha">Fecha:</label><input type="text" name="obse_fecha" id="obse_fecha" value="" readonly="readonly"> 
<script>
Calendar.setup({
    inputField : "obse_fecha",
    trigger    : "obse_fecha",
    onSelect   : function() { this.hide() }
});
</script>
<div class="limpiar"></div>
<label for="obse_descripcion">Observación:</label>
<div class="limpiar"></div>
<textarea id="obse_descripcion" rows="5" cols="70" name="obse_descripcion" ></textarea>
<div class="limpiar"></div>
<input type="file" name="obse_documento" id="obse_documento" multiple="multiple">
<div class="limpiar"></div>
<input type="submit" name="observacion" id="observacion" value="Guardar">
</form>

</div>

</div>

</div>

</div>	

<!-- Estado -->

<div id="estado" class="modal" style="display:none">

<div id="contenido-formulario">
<div class="contenido-formulario_1">Modificación de estado 
<span onclick="ocultareldiv('estado')" id="cerrar"><img border="0" src="../../../../../<%=(String)request.getSession().getAttribute("PLANTILLA")%>img/cerrar.png" /></span> 
<span onclick="maximizareldiv()" id="maximizar"><img border="0" src="../../../../../<%=(String)request.getSession().getAttribute("PLANTILLA")%>img/screen.png" /></span>
</div>

<div class="contenido-formulario_2">

<div class="contenido-formulario_3">
<img border="0" src="../../../../../<%=(String)request.getSession().getAttribute("PLANTILLA")%>img/registro.png" />
</div>

<div class="contenido-formulario_4">


<form action="" method="post" name="gest_observacion" id="gest_observacion" >
<input type="hidden" name="funcion" id="funcion" value="archivar">
<input type="hidden" name="soli_id" id="soli_id" value="<% if( request.getParameter("soli_id") != null ){ out.print(request.getParameter("soli_id"));}else{ out.print(acceso);} %>">
<input type="hidden" name="scri_nombre" id="scri_nombre" value="<% if( request.getParameter("scri_nombre") != null ){ out.print(request.getParameter("scri_nombre"));}else{ out.print(scri_nombre);} %>">
<input type="hidden" name="tiob_id" id="tiob_id" value="2">
<label for="espr_id">Estado:</label>
<%
out.print(new estadoProceso().generarCombo("Activo", "0", "", "espr_id", "")); 
%>
<label for="obse_fecha1">Fecha:</label><input type="text" name="obse_fecha1" id="obse_fecha1" value="" readonly="readonly"> 
<script>
Calendar.setup({
    inputField : "obse_fecha1",
    trigger    : "obse_fecha1",
    onSelect   : function() { this.hide() }
});
</script>
<div class="limpiar"></div>
<label for="obse_descripcion">Observación:</label>
<div class="limpiar"></div>
<textarea id="obse_descripcion" rows="5" cols="70" name="obse_descripcion" ></textarea>
<div class="limpiar"></div>
<input type="submit" name="observacion" id="observacion" value="Modificar">
</form>

</div>

</div>

</div>

</div>	

<!--  Fin estado -->


<!-- Ajuste -->

<div id="ajuste" class="modal" style="display:none">

<div id="contenido-formulario">
<div class="contenido-formulario_1">Adicionar ajuste 
<span onclick="ocultareldiv('ajuste')" id="cerrar"><img border="0" src="../../../../../<%=(String)request.getSession().getAttribute("PLANTILLA")%>img/cerrar.png" /></span> 
<span onclick="maximizareldiv()" id="maximizar"><img border="0" src="../../../../../<%=(String)request.getSession().getAttribute("PLANTILLA")%>img/screen.png" /></span>
</div>

<div class="contenido-formulario_2">

<div class="contenido-formulario_3">
<img border="0" src="../../../../../<%=(String)request.getSession().getAttribute("PLANTILLA")%>img/registro.png" />
</div>

<div class="contenido-formulario_4">


<form action="" method="post" name="gest_observacion" id="gest_observacion" >
<input type="hidden" name="funcion" id="funcion" value="archivar">
<input type="hidden" name="soli_id" id="soli_id" value="<% if( request.getParameter("soli_id") != null ){ out.print(request.getParameter("soli_id"));}else{ out.print(acceso);} %>">
<input type="hidden" name="scri_nombre" id="scri_nombre" value="<% if( request.getParameter("scri_nombre") != null ){ out.print(request.getParameter("scri_nombre"));}else{ out.print(scri_nombre);} %>">
<label for="ajus_descripcion">Descripción:</label>
<div class="limpiar"></div>
<textarea id="ajus_descripcion" rows="5" cols="70" name="ajus_descripcion" ></textarea>
<div class="limpiar"></div>
<label for="ajus_nota">Nota:</label>
<div class="limpiar"></div>
<textarea id="ajus_nota" rows="5" cols="70" name="ajus_nota" ></textarea>
<div class="limpiar"></div>
<input type="submit" name="observacion" id="observacion" value="Generar">
</form>

</div>

</div>

</div>

</div>	

<!--  Fin ajuste -->


<% if(request.getParameter("soli_id") != null || acceso != "" ){ 

	if(request.getParameter("soli_id") != null){		
		soli_id = request.getParameter("soli_id");		
	}else{
		soli_id = acceso;
	}
%>

	<% 
	if(request.getParameter("observacion") != null && request.getParameter("observacion").equals("Modificar")){
		mensaje = new solicitud().actualizarEstado(request, soli_id);
	}
	
	if(request.getParameter("observacion") != null && request.getParameter("observacion").equals("Generar")){
		mensaje = new ajuste().generar(request);
	}
	
	%>

<% if(!soli_id.equals("::::::")){ %>

<div class="deta_bar">
<form action="" method="post" name="gest_deta_solicitud" id="gest_deta_solicitud" >
<input type="button" class="deta_gestion" name="observacion" id="observacion" value="Observación" onclick="mostrareldiv('modal');">
<input type="button" class="deta_gestion" name="estado" id="estado" value="Estado" onclick="mostrareldiv('estado');">
<input type="button" class="deta_gestion" name="ajuste" id="ajuste" value="Ajuste" onclick="mostrareldiv('ajuste');">
</form> 
</div>
<div class="limpiar"></div>
<div class='mensaje'>
<% 
out.print(mensaje);
%>
</div>
<div class="limpiar"></div>

	<div id="stylized" class="myform">
		<h1>Detalle de la solicitud</h1>
			<p>Información detallada de la solicitud.</p>
<%
out.print(new solicitud().detalleActividad(soli_id)); 
%>
		<div class="spacer"></div>
	</div>
	
<%
out.print(new observacion().listar(soli_id, "3", "tabla_detalles", "Observaciones de seguimiento")); 
%>

<%
}
%>
<% } %>


</body>
</html>
<% } %>