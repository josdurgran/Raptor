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

<div id="stylized" class="myform">
<form id="filtro" name="filtro" method="post" action="./menu_actividades.jsp" onsubmit="" target="acti_menu">
<input type="hidden" name="MODU_ID" id="MODU_ID" value="<%= request.getParameter("MODU_ID")%>" >
<input type="hidden" name="FUNC_ID" id="FUNC_ID" value="<%= request.getParameter("FUNC_ID")%>" >
<input type="hidden" name="PERS_ID" id="PERS_ID" value="<%= (String)request.getSession().getAttribute("PERS_ID")%>" >
<input type="hidden" name="condicion" id="condicion" value="true" >

<select name="campo" id="campo" onchange="">
<option value="SCR.SCRI_NOMBRE">SOLICITUD</option>
<option value="EMP.EMPR_SIGLA">EMPRESA (Sigla)</option>
<option value="SCR.SCRI_DESCRIPCION">DESCRIPCION</option>
<option value="PER.PERS_PRIMERNOMBRE||COALESCE(PER.PERS_SEGUNDONOMBRE,'')||PER.PERS_PRIMERAPELLIDO||COALESCE(PER.PERS_SEGUNDOAPELLIDO,'')">ASIGNADO A</option>
</select>

<input class="" type="text" name="valor" id="valor" onkeyup="document.getElementById('filtro').submit();">
</form>
</div>

<% if(request.getParameter("accion") == null){ %>

<div class="limpiar"></div>
<iframe src="menu_actividades.jsp" width="30%" class="interna"  frameBorder="0" name="acti_menu" /></iframe>
<iframe src="deta_actividades.jsp" width="69%" class="interna" frameBorder="0" name="acti_contenido" /></iframe>

<% } %>

</body>
</html>
<% } %>