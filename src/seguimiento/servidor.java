package seguimiento;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.paint.*;

import utileria.archivo;
import utileria.sql;

import javax.servlet.http.HttpServletRequest;

import core.conexion;

public class servidor {
	

	public String insertar( String secuencia, HttpServletRequest request ) {
		
		
		
		String sql = "INSERT INTO seguimiento.servidor( serv_id, serv_estado, serv_ipv4, serv_ipv6, serv_so, serv_memoriaram, "
				+ "serv_discoduro, serv_mac, serv_procesador, serv_host, serv_virtualizado, serv_fecharegistro, serv_registradopor, ceda_id, serv_descripcion, serv_monitoreo, serv_alias)"
				+ " VALUES ("+secuencia+ ",'"+request.getParameter("SERV_ESTADO")+"'," + "'"+request.getParameter("SERV_IPV4")+"', " + "'"+request.getParameter("SERV_IPV6")+"', " + "'"+request.getParameter("SERV_SO")+"', " + "'"+request.getParameter("SERV_MEMORIARAM")+"', " + "'"+request.getParameter("SERV_DISCODURO")+"', " + "'"+request.getParameter("SERV_MAC")+"', " + "'"+request.getParameter("SERV_PROCESADOR")+"', " + "'"+request.getParameter("SERV_HOST")+"', " + "'"+request.getParameter("SERV_VIRTUALIZADO")+"', " + "NOW(), '"+(String)request.getSession().getAttribute("PERS_ID")+"', "+request.getParameter("CEDA_ID")+", '"+request.getParameter("SERV_DESCRIPCION")+"', '"+request.getParameter("SERV_MONITOREO")+"', '"+request.getParameter("SERV_ALIAS")+"')";
		//System.out.println(sql);		
		return new sql().insertar(sql, "Servidor guardado corectamente.", "No fue posible guardar el servidor.", "mensaje_ok", "mensaje_err");
		
	}	
	
	public String update( HttpServletRequest request ) {	
		
		
		String sql = "UPDATE seguimiento.servidor"
				+"   SET serv_estado='"+request.getParameter("SERV_ESTADO")+"', "
				+"   serv_ipv4='"+request.getParameter("SERV_IPV4")+"', "
				+"   serv_ipv6='"+request.getParameter("SERV_IPV6")+"', "
				+"   serv_so='"+request.getParameter("SERV_SO")+"', "
				+"   serv_memoriaram='"+request.getParameter("SERV_MEMORIARAM")+"', "
				+"   serv_discoduro='"+request.getParameter("SERV_DISCODURO")+"', "
				+"   serv_mac='"+request.getParameter("SERV_MAC")+"', "
				+"   serv_procesador='"+request.getParameter("SERV_PROCESADOR")+"', "
				+"   serv_host='"+request.getParameter("SERV_HOST")+"', "
				+"   serv_virtualizado='"+request.getParameter("SERV_VIRTUALIZADO")+"', "
				+"   serv_fecharegistro=NOW(), "
				+"   serv_registradopor='"+(String)request.getSession().getAttribute("PERS_ID")+"', "
				+"   ceda_id="+request.getParameter("CEDA_ID")+", "
				+"   serv_descripcion='"+request.getParameter("SERV_DESCRIPCION")+"',"
				+"   serv_monitoreo='"+request.getParameter("SERV_MONITOREO")+"',"
				+"   serv_alias='"+request.getParameter("SERV_ALIAS")+"'"
				+"	 WHERE serv_id="+request.getParameter("SERV_ID");
		//System.out.println(sql);		
		return new sql().insertar(sql, "Servidor actualizado corectamente.", "No fue posible actualizar el servidor.", "mensaje_ok", "mensaje_err");
		
	}
	
	
	public String visualizar( String serv_id ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = null;
		ResultSet rset = null;

		String retorno = "";
		
		String sql = "SELECT * "
				+ "FROM seguimiento.servidor ser "
				+ "inner join seguimiento.centrodatos ced on ser.ceda_id = ced.ceda_id "
				+ "WHERE ser.serv_id = "+serv_id+" ";
		
		//System.out.println(sql);
			
			stmt = con.createStatement();			
			rset = stmt.executeQuery( sql ); 	
		
		if(rset.next()){			
			do{	
				retorno = retorno+
							"<div class=\"all\">CENTRO DE DATOS:"
							+ "<span class=\"small\">Ubicación</span>"
							+ "</div>"
							+ "<div class=\"allres\">"
							+rset.getString("CEDA_NOMBRE")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">HOST:"
							+ "<span class=\"small\">Nombre del servidor</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_HOST")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">IPV4:"
							+ "<span class=\"small\">IPv4 del servidor</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_IPV4")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">IPV6:"
							+ "<span class=\"small\">IPv6 del servidor</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_IPV6")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">MAC:"
							+ "<span class=\"small\">MAC del servidor</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_MAC")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">SO:"
							+ "<span class=\"small\">Sistema operativo</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_SO")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">RAM:"
							+ "<span class=\"small\">Memoria RAM MB</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_MEMORIARAM")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">DD:"
							+ "<span class=\"small\">Disco duro MB</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_DISCODURO")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">PROCESADOR:"
							+ "<span class=\"small\">Referencia del procesador</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_PROCESADOR")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">ESTADO:"
							+ "<span class=\"small\">Estado del servidor</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_ESTADO")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">VIRTUALIZADO:"
							+ "<span class=\"small\">Si/No</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_VIRTUALIZADO")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">DESCRIPCION:"
							+ "<span class=\"small\">Usos del dispositivo</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_DESCRIPCION")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">MONITOREO:"
							+ "<span class=\"small\">Monitoreo del dispositivo</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_MONITOREO")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">ALIAS:"
							+ "<span class=\"small\">Nombre para mostrar</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_ALIAS")
							+"</div>"
							;			
			}while (rset.next());
				
		}
		//System.out.println(retorno);
		
		stmt.close();
		conn.cerrar(con);
		
		return retorno;			
		
	}
	
	public String menuServidor ( HttpServletRequest request, String filtro ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "<div class='acti_menu'><div class='acti_menu0'> Seleccione un servidor</div>";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT" 
					+ " ser.serv_id, ser.serv_ipv4, ser.serv_host, ser.serv_descripcion "
					+ "FROM "
					+ "seguimiento.servidor ser inner join seguimiento.centrodatos ced on ser.ceda_id = ced.ceda_id "
					+ "where "+filtro+" "
					+ "ORDER BY"
					+ " ser.serv_estado, ser.serv_ipv4 "
					+ "limit 50";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
				
		if(rset.next()){			
			do{	
				retorno = retorno+"<form action=\"./deta_servidor.jsp\" method=\"post\" name=\"visu_serv_id\" id=\""+rset.getString("serv_id")+"\" target=\"serv_contenido\">"
				+"<div class='reg_menu' onclick=\"document.getElementById('"+rset.getString("serv_id")+"').submit();\">"
				+"<input type=\"hidden\" name=\"SERV_ID\" id=\"SERV_ID\" value=\""+rset.getString("serv_id")+"\">"
				+"<div class='reg_menu0'>"+rset.getString("serv_ipv4")+"</div>"
				+"<div class='reg_menu1'>"+rset.getString("serv_host")+"</div>"
				+"<div class='reg_menu2'>"+rset.getString("serv_descripcion")+"</div>"
				+"</div>"
				+"</form>";	
			}while (rset.next());
				
		}
		
		retorno += "</div>";
		
		//System.out.println(retorno);
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
	
	
	public String editar( String serv_id ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = null;
		ResultSet rset = null;

		String retorno = "";
		
		String sql = "SELECT * "
				+ "FROM seguimiento.servidor ser "
				+ "inner join seguimiento.centrodatos ced on ser.ceda_id = ced.ceda_id "
				+ "WHERE ser.serv_id = "+serv_id+" ";
		
		//System.out.println(sql);
			
			stmt = con.createStatement();			
			rset = stmt.executeQuery( sql ); 	
		
		if(rset.next()){			
			do{	
				retorno = retorno
						+"<input value=\""+rset.getString("SERV_ID")+"\" class=\"\" type=\"hidden\" name=\"SERV_ID\" id=\"SERV_ID\" >"
						+"<label for=\"SERV_HOST\">*HOST"
						+"<span class=\"small\">Ingrese el host</span>"
						+"<span id=\"SERV_HOST_M\"></span>"
						+"</label>"
						+"<input value=\""+rset.getString("SERV_HOST")+"\" class=\"\" type=\"text\" name=\"SERV_HOST\" id=\"SERV_HOST\" maxlength=\"100\" onkeypress=\"document.getElementById('SERV_HOST_M').style.display='none';\">"
						+""
						+"<label for=\"SERV_IPV4\">*IPV4"
						+"<span class=\"small\">IPv4 del servidor</span>"
						+"<span id=\"SERV_IPV4_M\"></span>"
						+"</label>"
						+"<input value=\""+rset.getString("SERV_IPV4")+"\" class=\"\" type=\"text\" name=\"SERV_IPV4\" id=\"SERV_IPV4\" maxlength=\"15\" onkeypress=\"document.getElementById('SERV_IPV4_M').style.display='none';\">"
						+""
						+"<label for=\"SERV_IPV6\">IPV6"
						+"<span class=\"small\">IPv6 del servidor</span>"
						+"</label>"
						+"<input value=\""+rset.getString("SERV_IPV6")+"\" class=\"\" type=\"text\" name=\"SERV_IPV6\" id=\"SERV_IPV6\" maxlength=\"50\"/>"
						+""
						+"<label for=\"SERV_MAC\">MAC"
						+"<span class=\"small\">MAC del servidor</span>"
						+"</label>"
						+"<input value=\""+rset.getString("SERV_MAC")+"\" class=\"\" type=\"text\" name=\"SERV_MAC\" id=\"SERV_MAC\" maxlength=\"20\"/>"
						+""
						+"<label for=\"SERV_SO\">SO"
						+"<span class=\"small\">Sistema operativo</span>"
						+"</label>"
						+"<input value=\""+rset.getString("SERV_SO")+"\" class=\"\" type=\"text\" name=\"SERV_SO\" id=\"SERV_SO\" maxlength=\"150\"/>"
						+""
						+"<label for=\"SERV_MEMORIARAM\">RAM"
						+"<span class=\"small\">Memoria RAM MB</span>"
						+"<span id=\"SERV_MEMORIARAM_M\"></span>"
						+"</label>"
						+"<input value=\""+rset.getString("SERV_MEMORIARAM")+"\" class=\"\" type=\"text\" name=\"SERV_MEMORIARAM\" id=\"SERV_MEMORIARAM\" maxlength=\"20\" onkeypress=\"document.getElementById('SERV_MEMORIARAM_M').style.display='none';\">"
						+""
						+"<label for=\"SERV_DISCODURO\">DD"
						+"<span class=\"small\">Disco duro MB</span>"
						+"<span id=\"SERV_DISCODURO_M\"></span>"
						+"</label>"
						+"<input value=\""+rset.getString("SERV_DISCODURO")+"\" class=\"\" type=\"text\" name=\"SERV_DISCODURO\" id=\"SERV_DISCODURO\" maxlength=\"20\" onkeypress=\"document.getElementById('SERV_DISCODURO_M').style.display='none';\">"
						+""
						+"<label for=\"SERV_PROCESADOR\">PROCESADOR"
						+"<span class=\"small\">Referencia del procesador</span>"
						+"</label>"
						+"<input value=\""+rset.getString("SERV_PROCESADOR")+"\" class=\"\" type=\"text\" name=\"SERV_PROCESADOR\" id=\"SERV_PROCESADOR\" maxlength=\"200\"/>"
						+""
						+"<label for=\"SERV_ESTADO\">*ESTADO"
						+"<span class=\"small\">Estado del servidor</span>"
						+"<span id=\"SERV_ESTADO_M\"></span>"
						+"</label>"
						+"<select name=\"SERV_ESTADO\" id=\"SERV_ESTADO\" onchange=\"document.getElementById('SERV_ESTADO_M').style.display='none';\">"
						+"<option value=\"\"></option>"
						+"<option value=\"Activo\" ";
						if(rset.getString("SERV_ESTADO") !=  null && rset.getString("SERV_ESTADO").equals("Activo")){ retorno= retorno+ "selected";}
						retorno= retorno+ ">Activo</option>"
						+"<option value=\"Inactivo\" ";
						if(rset.getString("SERV_ESTADO") !=  null && rset.getString("SERV_ESTADO").equals("Inactivo")){ retorno= retorno+ "selected";}
						retorno= retorno+">Inactivo</option>"
						+"</select>"
						+""
						+"<label for=\"SERV_VIRTUALIZADO\">*VIRTUALIZADO"
						+"<span class=\"small\">Si/No</span>"
						+"<span id=\"SERV_VIRTUALIZADO_M\"></span>"
						+"</label>"
						+"<select name=\"SERV_VIRTUALIZADO\" id=\"SERV_VIRTUALIZADO\" onchange=\"document.getElementById('SERV_VIRTUALIZADO_M').style.display='none';\">"
						+"<option value=\"\"></option>"
						+"<option value=\"Sí\" ";
						if(rset.getString("SERV_VIRTUALIZADO") !=  null && rset.getString("SERV_VIRTUALIZADO").equals("Sí")){ retorno= retorno+ "selected";}
						retorno= retorno+">Sí</option>"
						+"<option value=\"No\" ";
						if(rset.getString("SERV_VIRTUALIZADO") !=  null && rset.getString("SERV_VIRTUALIZADO").equals("No")){ retorno= retorno+ "selected";}
						retorno= retorno+">No</option>"
						+"</select>"
						+""
						+"<label for=\"CEDA_ID\">*CENTRO DE DATOS"
						+"<span class=\"small\">Ubicación</span>"
						+"<span id=\"CEDA_ID_M\"></span>"
						+"</label>"
						+new centroDatos().generarCombo( "Activo", rset.getString("CEDA_ID"), "", "CEDA_ID", "document.getElementById('CEDA_ID_M').style.display='none';" )
						+""
						+"<label for=\"SERV_DESCRIPCION\">*DESCRIPCION"
						+"<span class=\"small\">Usos del dispositivo</span>"
						+"<span id=\"SERV_DESCRIPCION_M\"></span>"
						+"</label>"
						+"<input value=\""+rset.getString("SERV_DESCRIPCION")+"\" class=\"\" type=\"text\" name=\"SERV_DESCRIPCION\" id=\"SERV_DESCRIPCION\" maxlength=\"500\" onkeypress=\"document.getElementById('SERV_DESCRIPCION_M').style.display='none';\">"
						+""
						+"<label for=\"SERV_MONITOREO\">*MONITOREO"
						+"<span class=\"small\">Si/No</span>"
						+"<span id=\"SERV_MONITOREO_M\"></span>"
						+"</label>"
						+"<select name=\"SERV_MONITOREO\" id=\"SERV_MONITOREO\" onchange=\"document.getElementById('SERV_MONITOREO_M').style.display='none';\">"
						+"<option value=\"\"></option>"
						+"<option value=\"Sí\" ";
						if(rset.getString("SERV_MONITOREO") !=  null && rset.getString("SERV_MONITOREO").equals("Sí")){ retorno= retorno+ "selected";}
						retorno= retorno+">Sí</option>"
						+"<option value=\"No\" ";
						if(rset.getString("SERV_MONITOREO") !=  null && rset.getString("SERV_MONITOREO").equals("No")){ retorno= retorno+ "selected";}
						retorno= retorno+">No</option>"
						+"</select>"
						+""
						+"<label for=\"SERV_ALIAS\">ALIAS"
						+"<span class=\"small\">Nombre para mostrar</span>"
						+"<span id=\"SERV_ALIAS_M\"></span>"
						+"</label>"
						+"<input value=\""+rset.getString("SERV_ALIAS")+"\" class=\"\" type=\"text\" name=\"SERV_ALIAS\" id=\"SERV_ALIAS\" maxlength=\"250\" onkeypress=\"document.getElementById('SERV_ALIAS_M').style.display='none';\">"
						+""
						+"<div class=\"spacer\"></div>"
						+"<button type=\"submit\" name=\"accion\" value=\"guardar\">Guardar</button>"
						+"<div class=\"spacer\"></div>";			
			}while (rset.next());
				
		}
		//System.out.println(retorno);
		
		stmt.close();
		conn.cerrar(con);
		
		return retorno;			
		
	}
	
	public String generarCombo( String estado, String selected, String clase, String campo,String cambio ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||serv_id||'\" '||CASE WHEN serv_id = "+selected+" THEN 'selected' ELSE '' END||'>'||serv_ipv4||' - '||COALESCE( serv_host, 'No registra host')||'</option>' AS ITEM "
				+ "FROM seguimiento.servidor "
				+ "WHERE serv_estado = '"+estado+"' "
				+ "ORDER BY serv_ipv4, serv_host";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
		
		retorno = retorno+"<select name=\""+campo+"\" id=\""+campo+"\" onchange=\""+cambio+"\" ><option value=\"\"></option>";
		
		if(rset.next()){			
			do{	
				retorno = retorno+rset.getString("ITEM");			
			}while (rset.next());
				
		}
				retorno = retorno+"</select>";			
		
		
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
	
	public String monitor( HttpServletRequest request, String estado ) throws SQLException, IOException {
				
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		String retorno = "";
		Statement stmt = con.createStatement();
		
		String freetotal = "";
		String freelibre = "";
		String freeusada = "";
		String freebuffer = "";
		String freeactive = "";
		String freeinactive = "";
		                  
		String ddtotal = "";
		String ddused = "";
		String ddavailable = "";
		String ddmount = "";
		                  
		String cpuinactivo = "";
		String cpuusario = "";
		String cpukernel = "";
		String cpuespera = "";
		                  
		String date = "";
		
		String sql = "SELECT  serv_alias "
				+ "FROM seguimiento.servidor "
				+ "WHERE serv_estado = '"+estado+"' "
				+ "AND serv_monitoreo = 'Sí' "
				+ "ORDER BY serv_alias";
		
		//System.out.println(sql);
		
		String[] memtotal;
		String[] memactive;
		boolean memory = false;
		
		String[] disktotal;
		String[] diskused;
		boolean disk = false;
		
		String[] cpulibre;
		int cpu = 0;
		
		int sound = 0;
		int top = 70;
		boolean control = true;
		
		
		ResultSet rset = stmt.executeQuery( sql );
		
		if(rset.next()){			
			do{	
						freetotal = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"freetotal.txt");
						freelibre = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"freelibre.txt");
						freeusada = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"freeusada.txt");
						freebuffer = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"freebuffer.txt");
						freeactive = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"freeactive.txt");
						freeinactive = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"freeinactive.txt");

						ddtotal = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"ddtotal.txt");
						ddused = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"ddused.txt");
						ddavailable = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"ddavailable.txt");
						ddmount = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"ddmount.txt");

						cpuinactivo = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"cpuinactivo.txt");
						cpuusario = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"cpuusario.txt");
						cpukernel = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"cpukernel.txt");
						cpuespera = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"cpuespera.txt");

						date = new archivo().leerTxt((String)request.getSession().getAttribute("ALMACENAMIENTOTMP")+(String)request.getSession().getAttribute("BAR")+rset.getString("SERV_ALIAS")+(String)request.getSession().getAttribute("BAR")+"date.txt");
					
						if ( freetotal != null && !freetotal.equals("ERROR ::: FILE-00") && freetotal.length() > 1 &&
								freelibre != null && !freelibre.equals("ERROR ::: FILE-00") && freelibre.length() > 1 && 
								freeusada != null && !freeusada.equals("ERROR ::: FILE-00") && freeusada.length() > 1 && 
								freebuffer != null && !freebuffer.equals("ERROR ::: FILE-00") && freebuffer.length() > 1 && 
								freeactive != null && !freeactive.equals("ERROR ::: FILE-00") && freeactive.length() > 1 && 
								freeinactive != null && !freeinactive.equals("ERROR ::: FILE-00") && freeinactive.length() > 1 &&
								ddtotal != null && !ddtotal.equals("ERROR ::: FILE-00") && ddtotal.length() > 1 && 
								ddused != null && !ddused.equals("ERROR ::: FILE-00") && ddused.length() > 1 && 
								ddavailable != null && !ddavailable.equals("ERROR ::: FILE-00") && ddavailable.length() > 1 && 
								ddmount != null && !ddmount.equals("ERROR ::: FILE-00") && ddmount.length() > 1 && 
								cpuinactivo != null && !cpuinactivo.equals("ERROR ::: FILE-00") && cpuinactivo.length() > 1 && 
								cpuusario != null && !cpuusario.equals("ERROR ::: FILE-00") && cpuusario.length() > 1 && 
								cpukernel != null && !cpukernel.equals("ERROR ::: FILE-00") && cpukernel.length() > 1 && 
								cpuespera != null && !cpuespera.equals("ERROR ::: FILE-00") && cpuespera.length() > 1 && 
								date != null && !date.equals("ERROR ::: FILE-00") && date.length() > 1 ){
				
				retorno +="<div class=\"banner\"><h1><img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/logo.png\">Dashboard ::: Servidor "+rset.getString("SERV_ALIAS")+"</h1>";
				
				/* Notificacion de estado*/
				
				/*Memoria*/
				memtotal = freetotal.split(",");
				memactive = freeactive.split(",");
				
		        for (int i=0; i < memtotal.length-1 ; i++) {
		      		      		            		            
		            if( ( Integer.parseInt(memactive[i].trim())*100)/Integer.parseInt(memtotal[i].trim()) >= top ){ sound+=1; memory = true; }
		            
		        }     
		        
		        if( memory ){
		        retorno += "<h1 class=\"notifica\"><img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/err_memory.png\"></h1>";				
		        }else{
		        	retorno += "<h1 class=\"notifica\"><img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/ok_memory.png\"></h1>";
		        }
		        /*Fin memoria*/
		        
				/*Disco*/
		        disktotal = ddtotal.split(",");
		        diskused = ddused.split(",");
				
		        for (int i=0; i < disktotal.length-1 ; i++) {
		            		            
		            if( ( Integer.parseInt(diskused[i].trim())*100)/Integer.parseInt(disktotal[i].trim()) >= top ){ sound+=1; disk = true; }
		            
		        }     
		        
		        if( disk ){
		        retorno += "<h1 class=\"notifica\"><img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/err_disco.png\"></h1>";				
		        }else{
		        	retorno += "<h1 class=\"notifica\"><img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/ok_disco.png\"></h1>";
		        }
		        /*Fin disco*/
		        
				/*CPU*/
		        cpulibre = cpuinactivo.split(",");
		       
				
		        for (int i=0; i < cpulibre.length-1; i++) {
		            		            
		            if( Double.parseDouble(cpulibre[i].trim()) <= top ){ 
		            	sound+=1; 
		            	cpu += 1; 
		            	}else if(cpu<3){
		            		
		            		cpu = 0;
		            		
		            	}
		            
		        }     
		        
		        if( cpu >= 3 ){
		        retorno += "<h1 class=\"notifica\"><img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/err_cpu.png\"></h1>";				
		        }else{
		        	retorno += "<h1 class=\"notifica\"><img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/ok_cpu.png\"></h1>";
		        }
		        /*Fin CPU*/
		        
		        
		        
		        
		        /*Auditiva*/
		        if( sound > 1 ){
		        retorno += "<audio src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"sound/alarma.mp3\" preload=\"auto\" autoplay >Tu navegador no implementa el elemento <code>Audio</code>.</audio>";				
		        }
		        /*Fin auditiva*/
		        
				/* Fin notificacion de estado*/
		        
		        
				retorno += "<section class=\"fila\">"
						+""
						+"<div class=\"container_full\">"
						+"  <h2>Monitor de memoria </h2>"
						+"  <div>"
						+"    <canvas id=\"myChart"+rset.getString("SERV_ALIAS")+"\" width=\"900\"></canvas>"
						+"  </div>"
						+"</div>"
						+""
						+"<div class=\"container\">"
						+"  <h2>Monitor de disco duro</h2>"
						+"  <div>"
						+"    <canvas id=\"graDisco"+rset.getString("SERV_ALIAS")+"\" width=\"900\"></canvas>"
						+"  </div>"
						+"</div>"
						+""
						+"<div class=\"container\">"
						+"  <h2>Monitor de CPU servidor</h2>"
						+"  <div>"
						+"    <canvas id=\"monitor_cpu"+rset.getString("SERV_ALIAS")+"\" width=\"900\"></canvas>"
						+"  </div>"
						+"</div>"
						+""
						+"</section>"
						+ ""
						+ ""
						+ ""
						+"			<script>"
						+"			"
						+"			var ctx"+rset.getString("SERV_ALIAS")+" = document.getElementById('myChart"+rset.getString("SERV_ALIAS")+"').getContext('2d');"
						+"			var myChart"+rset.getString("SERV_ALIAS")+" = new Chart(ctx"+rset.getString("SERV_ALIAS")+", {"
						+"			  type: 'line',"
						+"			  data: {"
						+"			    labels: ["+date.substring(0, date.length()-1)+"],"
						+"			    datasets: [		"
						+"			     {"
						+"					label: \"Memoria total\","
						+"					backgroundColor : \"rgba(220,220,220,0.2)\","
						+"					borderColor : \"#6b9dfa\","
						+"					pointBackgroundColor : \"#1e45d7\","
						+"					pointHoverBackgroundColor : \"#fff\","
						+"					pointHoverBorderColor : \"#fff\","
						+"					pointHoverBackgroundColor : \"rgba(220,220,220,1)\","
						+"					data : ["+freetotal.substring(0, freetotal.length()-1)+"]"
						+"				},			"
						+"				"
						+"				{"
						+"					label: \"Memoria libre\","
						+"					backgroundColor : \"rgba( 213, 245, 227 , 0.2)\","
						+"					borderColor : \"#27ae60\","
						+"					pointBackgroundColor : \"#1e8449\","
						+"					pointHoverBackgroundColor : \"#fff\","
						+"					pointHoverBorderColor : \"#fff\","
						+"					pointHoverBackgroundColor : \"rgba( 213, 245, 227 , 1)\","
						+"					data : ["+freelibre.substring(0, freelibre.length()-1)+"]"
						+"				},			"
						+"				"
						+"				{"
						+"					label: \"Memoria usada\","
						+"					backgroundColor : \"rgba(  241, 148, 138 , 0.2)\","
						+"					borderColor : \"#c0392b\","
						+"					pointBackgroundColor : \"#922b21\","
						+"					pointHoverBackgroundColor : \"#fff\","
						+"					pointHoverBorderColor : \"#fff\","
						+"					pointHoverBackgroundColor : \"rgba(  241, 148, 138 , 1)\","
						+"					data : ["+freeusada.substring(0, freeusada.length()-1)+"]"
						+"				},			"
						+"				"
						+"				{"
						+"					label: \"Memoria en buffer\","
						+"					backgroundColor : \"rgba(250, 215, 160,0.2)\","
						+"					borderColor : \"#e9e225\","
						+"					pointBackgroundColor : \"#faab12\","
						+"					pointHoverBackgroundColor : \"#fff\","
						+"					pointHoverBorderColor : \"#fff\","
						+"					pointHoverBackgroundColor : \"rgba(250, 215, 160,1)\","
						+"					data : ["+freebuffer.substring(0, freebuffer.length()-1)+"]"
						+"				},				"
						+"				"
						+"				{"
						+"					label: \"Memoria activa\","
						+"					backgroundColor : \"rgba( 171, 178, 185 ,0.2)\","
						+"					borderColor : \"#99a3a4\","
						+"					pointBackgroundColor : \"#212f3c\","
						+"					pointHoverBackgroundColor : \"#fff\","
						+"					pointHoverBorderColor : \"#fff\","
						+"					pointHoverBackgroundColor : \"rgba( 171, 178, 185 ,1)\","
						+"					data : ["+freeactive.substring(0, freeactive.length()-1)+"]"
						+"				},			"
						+"				"
						+"				{"
						+"					label: \"Memoria inactiva\","
						+"					backgroundColor : \"rgba(232, 218, 239, 0.2)\","
						+"					borderColor : \"#c39bd3\","
						+"					pointBackgroundColor : \"#8e44ad\","
						+"					pointHoverBackgroundColor : \"#fff\","
						+"					pointHoverBorderColor : \"#fff\","
						+"					pointHoverBackgroundColor : \"rgba(232, 218, 239, 1)\","
						+"					data : ["+freeinactive.substring(0, freeinactive.length()-1)+"]"
						+"				}"
						+"				]"
						+"			  }"
						+"			});"
						+"			"
						+"			</script>"
						+"			"
						+"			"
						+"			<script>"
						+"			var disco"+rset.getString("SERV_ALIAS")+" = document.getElementById(\"graDisco"+rset.getString("SERV_ALIAS")+"\").getContext('2d');"
						+"			"
						+"			var original"+rset.getString("SERV_ALIAS")+" = Chart.defaults.global.legend.onClick;"
						+"			Chart.defaults.global.legend.onClick = function(e, legendItem) {"
						+"			  update_caption(legendItem);"
						+"			  original.call(this, e, legendItem);"
						+"			};"
						+"			"
						+"			var graDisco"+rset.getString("SERV_ALIAS")+" = new Chart(disco"+rset.getString("SERV_ALIAS")+", {"
						+"			  type: 'bar',"
						+"			  data: {"
						+"			    labels: ["+ddmount.substring(0, ddmount.length()-1)+"],"
						+"			    datasets: [{"
						+"			      label: 'Capacidad',"
						+"					backgroundColor : \"#6b9dfa\","
						+"					borderColor : \"#1e45d7\","
						+"					borderWidth : 2,"
						+"			      data: ["+ddtotal.substring(0, ddtotal.length()-1)+"],"
						+"			    }, {"
						+"			        label: 'Disponible',"
						+"					backgroundColor : \"#27ae60\","
						+"					borderColor : \"#1e8449\","
						+"					borderWidth : 2, "
						+"			        data: ["+ddavailable.substring(0, ddavailable.length()-1)+"],"
						+"			      }, {"
						+"			        label: 'Usado',"
						+"					backgroundColor : \"#c0392b\","
						+"					borderColor : \"#922b21\","
						+"					borderWidth : 2,"
						+"			        data: ["+ddused.substring(0, ddused.length()-1)+"],"
						+"			      }]"
						+"			  }"
						+"			});"
						+"			"
						+"			"
						+"			var caption"+rset.getString("SERV_ALIAS")+" = document.getElementById(\"caption"+rset.getString("SERV_ALIAS")+"\");"
						+"			"
						+"			var update_caption = function(legend) {"
						+"			  labels[legend.text] = legend.hidden;"
						+"			"
						+"			  var selected = Object.keys(labels).filter(function(key) {"
						+"			    return labels[key];"
						+"			  });"
						+"			"
						+"			  var text = selected.length ? selected.join(\" & \") : \"nothing\";"
						+"			"
						+"			  caption.innerHTML = \"The chart is displaying \" + text;"
						+"			};"
						+"			"
						+"			</script>"
						+"			"
						+"			"
						+"			"
						+"			"
						+"			<script>"
						+"			"
						+"			var ctx_cpu"+rset.getString("SERV_ALIAS")+" = document.getElementById('monitor_cpu"+rset.getString("SERV_ALIAS")+"').getContext('2d');"
						+"			var monitor_cpu"+rset.getString("SERV_ALIAS")+" = new Chart(ctx_cpu"+rset.getString("SERV_ALIAS")+", {"
						+"			  type: 'line',"
						+"			  data: {"
						+"			    labels: ["+date.substring(0, date.length()-1)+"],"
						+"			    datasets: [		"
						+"			     {"
						+"					label: \"%CPU libre\","
						+"					backgroundColor : \"rgba( 213, 245, 227 , 0.2)\","
						+"					borderColor : \"#27ae60\","
						+"					pointBackgroundColor : \"#1e8449\","
						+"					pointHoverBackgroundColor : \"#fff\","
						+"					pointHoverBorderColor : \"#fff\","
						+"					pointHoverBackgroundColor : \"rgba( 213, 245, 227 , 1)\","
						+"					data : ["+cpuinactivo.substring(0, cpuinactivo.length()-1)+"]"
						+"				},			"
						+"				"
						+"				{"
						+"					label: \"%CPU usuarios\","
						+"					backgroundColor : \"rgba(  241, 148, 138 , 0.2)\","
						+"					borderColor : \"#c0392b\","
						+"					pointBackgroundColor : \"#922b21\","
						+"					pointHoverBackgroundColor : \"#fff\","
						+"					pointHoverBorderColor : \"#fff\","
						+"					pointHoverBackgroundColor : \"rgba(  241, 148, 138 , 1)\","
						+"					data : ["+cpuusario.substring(0, cpuusario.length()-1)+"]"
						+"				},			"
						+"				"
						+"				{"
						+"					label: \"%CPU systema\","
						+"					backgroundColor : \"rgba(250, 215, 160,0.2)\","
						+"					borderColor : \"#e9e225\","
						+"					pointBackgroundColor : \"#faab12\","
						+"					pointHoverBackgroundColor : \"#fff\","
						+"					pointHoverBorderColor : \"#fff\","
						+"					pointHoverBackgroundColor : \"rgba(250, 215, 160,1)\","
						+"					data : ["+cpukernel.substring(0, cpukernel.length()-1)+"]"
						+"				},				"
						+"				"
						+"				{"
						+"					label: \"%CPU en espera\","
						+"					backgroundColor : \"rgba( 171, 178, 185 ,0.2)\","
						+"					borderColor : \"#99a3a4\","
						+"					pointBackgroundColor : \"#212f3c\","
						+"					pointHoverBackgroundColor : \"#fff\","
						+"					pointHoverBorderColor : \"#fff\","
						+"					pointHoverBackgroundColor : \"rgba( 171, 178, 185 ,1)\","
						+"					data : ["+cpuespera.substring(0, cpuespera.length()-1)+"]"
						+"				}"
						+"				]"
						+"			  }"
						+"			});"
						+"			"
						+"			</script></div>";
						control = true;
						}else {
							
							retorno+="ERROR ::: FILE-00 ::: "+rset.getString("SERV_ALIAS")+"<br>";
							control = false;
							
						}

				
			}while (rset.next());
			
			
					if( control ){
						retorno += "<div class=\"clear\"></div>"
								+"  <div class=\"w3-left\">       "
								+"    <img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/arrow_down.png\" onclick=\"velocidaddown()\">"
								+"    <img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/previous_arrow.png\" onclick=\"plusDivs(-1)\"> "
								+"  </div>"
								+"    <div class=\"w3-center\">"
								+"    Centro de investigación aplicada y desarrollo en tecnologías de información - CIADTI.<br>"
								+"    <span class=\"up\">Universidad de Pamplona - Colombia</span><br><span class=\"up\">2017</span>"
								+"    </div>"
								+"   <div class=\"w3-right\">          "
								+"    <img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/arrow_up.png\" onclick=\"velocidadup()\">    "
								+"    <img src=\""+(String)request.getSession().getAttribute("PLANTMONITOR")+"img/next_arrow.png\" onclick=\"plusDivs(1)\">"
								+"  </div>"
								+""
								+"<script>"
								+"var slideIndex = 0;"
								+"var speed = 10000;"
								+"carousel();"
								+""
								+"function plusDivs(n) {"
								+"	if (typeof slideIndex !== 'undefined') {"
								+"	carouselman(slideIndex += n);"
								+"	}"
								+"	}"
								+""
								+"function carousel() {"
								+"	if (typeof slideIndex !== 'undefined') {"
								+"    var i;"
								+"    var x = document.getElementsByClassName(\"banner\");"
								+"    for (i = 0; i < x.length; i++) {"
								+"      x[i].style.display = \"none\";"
								+"    }"
								+"    "
								+"    slideIndex++;"
								+"    if (slideIndex > x.length) {location.reload();}"
								+"    x[slideIndex-1].style.display = \"block\";"
								+"    setTimeout(carousel, speed); /* Change image every 5 seconds */"
								+"	}"
								+"    "
								+"}"
								+""
								+"function carouselman() {"
								+"	if (typeof slideIndex !== 'undefined') {"
								+"    var i;"
								+"    var x = document.getElementsByClassName(\"banner\");"
								+"    for (i = 0; i < x.length; i++) {"
								+"      x[i].style.display = \"none\";"
								+"    }"
								+"    if(slideIndex <= 0){slideIndex = 1;} "
								+"    /*alert(slideIndex);*/"
								+"    if (slideIndex > x.length) {location.reload();}"
								+"    x[slideIndex-1].style.display = \"block\";"
								+"    setTimeout(carousel, speed); /* Change image every 5 seconds */"
								+"	}"
								+"}"
								+""
								+"function velocidaddown() {"
								+"    speed = speed*2;"
								+"}"
								+""
								+"function velocidadup() {"
								+"    speed = speed-(speed*2);"
								+"    if(speed<1000){speed = 1000;}"
								+"}"
								+"</script>";
						
					}
					
				
		}else{ retorno+="No se encontraron servidores para monitorear..."; }
				
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
		
}
