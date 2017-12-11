<%@ page session="true" %>

<%	
	if (request.getSession().getAttribute("PERS_DOCUMENTO") == null) {
		response.sendRedirect("error.jsp?error=SES-001");
		}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<%@ page language="java" import="seguimiento.sgbd" %>
<%@ page language="java" import="seguimiento.tipoProceso" %>

<script type="text/javascript" src="../../../../../javaScript/jscal2.js"></script>
<script type="text/javascript" src="../../../../../javaScript/lang/en.js"></script>
<link rel="stylesheet" href="../../../../../javaScript/css/jscal2.css" />

</head>
<body>

<input type="hidden" name="SCRI_ESTADO" id="SCRI_ESTADO" maxlength="15" value="Activo" />
<input type="hidden" name="SCRI_FECHA" id="SCRI_FECHA" value="now()"/>
<input type="hidden" name="PERS_ID" id="PERS_ID" value="<%= request.getSession().getAttribute("PERS_ID") %>"/>



<div class="top_registro cuarenta left"><label for="SCRI_NOMBRE">Solicitud:</label></div>
<div class="top_registro cinco left"><label for="SCRI_SOLICITANTE">Solicitado por:</label></div>
<div class="top_registro cinco left"><label for="SCRI_COMPLEJIDAD">Complejidad:</label></div>
<div class="top_registro cinco left"><label for="SOLI_PUBLICADO">Publicado:</label></div>
<div class="limpiar"></div>

<div class="contenido_registro cuarenta left">
<input class="typetext" type="text" name="SCRI_NOMBRE" id="SCRI_NOMBRE" maxlength="100" onkeypress="document.getElementById('SCRI_NOMBRE_M').style.display='none';" >
<label for="SCRI_NOMBRE_M"><span id="SCRI_NOMBRE_M" class="error"></span></label>
</div>

<div class="contenido_registro cinco left">
<input class="typetext" type="text" name="SCRI_SOLICITANTE" id="SCRI_SOLICITANTE" maxlength="150" onkeypress="document.getElementById('SCRI_SOLICITANTE_M').style.display='none';">
<label for="SCRI_SOLICITANTE_M"><span id="SCRI_SOLICITANTE_M" class="error"></span></label>
</div>

<div class="contenido_registro cinco left">
<select name="SCRI_COMPLEJIDAD" id="SCRI_COMPLEJIDAD" onchange="document.getElementById('SCRI_COMPLEJIDAD_M').style.display='none';">
<option value=""></option>
<option value="Baja">Baja</option>
<option value="Media">Media</option>
<option value="Alta">Alta</option>
</select>
<label for="SCRI_COMPLEJIDAD_M"><span id="SCRI_COMPLEJIDAD_M" class="error"></span></label>
</div>

<div class="contenido_registro cinco left">

<select name="SOLI_PUBLICADO" id="SOLI_PUBLICADO" onchange="document.getElementById('SOLI_PUBLICADO_M').style.display='none';">
<option value=""></option>
<option value="true">Sí</option>
<option value="false">No</option>
</select>
<label for="SOLI_PUBLICADO_M"><span id="SOLI_PUBLICADO_M" class="error"></span></label>
</div>


<div class="limpiar"></div>

<div class="top_registro"><label for="TIPR_ID">Tipo de proceso:</label></div>
<div class="limpiar"></div>
<div class="contenido_registro">
<%
out.print(new tipoProceso().generarCombo( "Activo", "0", "", "tipr_id", "document.getElementById('tipr_id_M').style.display='none';" ));
%>
</div>
<div class="limpiar"></div>

<div class="top_registro"><label for="SCRI_DESCRIPCION">Descripción:</label></div>
<div class="limpiar"></div>
<div class="contenido_registro">
<textarea style="text-align: justify" class="contenido_registro" name="SCRI_DESCRIPCION" id="SCRI_DESCRIPCION" cols="150" rows="5" onkeypress="document.getElementById('SCRI_DESCRIPCION_M').style.display='none';"></textarea>
<label for="SCRI_DESCRIPCION_M"><span id="SCRI_DESCRIPCION_M" class="error"></span></label>
</div>
<div class="limpiar"></div>

<div class="top_registro"><label for="SCRI_NOTA">Nota:</label></div>
<div class="limpiar"></div>
<div class="contenido_registro">
<textarea style="text-align: justify" class="contenido_registro" name="SCRI_NOTA" id="SCRI_NOTA" cols="150" rows="5" onkeypress="document.getElementById('SCRI_NOTA_M').style.display='none';"></textarea>
<label for="SCRI_NOTA_M"><span id="SCRI_NOTA_M" class="error"></span></label>
</div>
<div class="limpiar"></div>
<input name="accion" type="submit" class="guardar" value="Guardar" />

</body>
</html>
<% } %>