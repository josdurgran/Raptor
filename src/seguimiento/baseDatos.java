package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import utileria.loadFile;
import utileria.sql;
import core.conexion;

public class baseDatos {
	
	public String insertar( String secuencia, HttpServletRequest request ) {
		
		
		
		String sql = "INSERT INTO seguimiento.basedatos(bada_id, bada_nombre, bada_estado, bada_registradopor, bada_fecharegistro, bada_descripcion, ento_id, sgbd_id, bada_encoding, bada_puerto, empr_id) VALUES ("
				+secuencia+ ",'"+request.getParameter("BADA_NOMBRE")+"', '"+request.getParameter("BADA_ESTADO")+"', '"+(String)request.getSession().getAttribute("PERS_ID")+"', NOW(), '"+request.getParameter("BADA_DESCRIPCION")+"', "+request.getParameter("ENTO_ID")+", "+request.getParameter("SGBD_ID")+", '"+request.getParameter("BADA_ENCODING")+"', '"+request.getParameter("BADA_PUERTO")+"', "+request.getParameter("EMPR_ID")+")";
		//System.out.println(sql);		
		return new sql().insertar(sql, "La base de datos se ha guardado corectamente.", "No fue posible guardar la base de datos.", "mensaje_ok", "mensaje_err");
		
	}
	
	public String generarCombo( String sgbd_id, String empr_id, String estado, String selected, String clase, String campo, String cambio ) throws SQLException {
		
		String [] sgbd  = sgbd_id.split(",");
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||bda.bada_id||'\" '||CASE WHEN bda.bada_id = "+selected+" THEN 'selected' ELSE '' END||'>'||bda.bada_nombre||' - '||coalesce(ent.ento_nombre, 'N/R')||' - '||coalesce(bda.bada_puerto, 'N/R')||'</option>' AS ITEM "
				+ "FROM seguimiento.basedatos bda, "
				+ "seguimiento.entorno ent "
				+ "WHERE bda.bada_estado = '"+estado+"' "
				+ "and bda.sgbd_id = "+sgbd[0]+" "
				+ "and bda.empr_id = "+empr_id+" "
				+ "and bda.ento_id = ent.ento_id "
				+ "ORDER BY bda.ento_id, bda.bada_nombre, bda.bada_puerto";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
		
		retorno = retorno+"<select name=\""+campo+"\" id=\""+campo+"\" onchange=\""+cambio+"\" ><option value=\"\"></option>";
		
		if(rset.next()){			
			do{	
				retorno = retorno+rset.getString("ITEM");			
			}while (rset.next());
				
		}
				retorno = retorno+"</select> <label for=\"empr_id_M\"><span id=\"empr_id_M\" class=\"error\"></span></label>";			
		
		//System.out.println(retorno);
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
	
	public String visualizar( String bada_id ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = null;
		ResultSet rset = null;

		String retorno = "";
		
		String sql = "SELECT bda.BADA_NOMBRE,  bda.BADA_DESCRIPCION, bda.BADA_ENCODING, bda.BADA_PUERTO, bda.BADA_ESTADO, ser.serv_ipv4, ser.serv_host, sgb.sgbd_puerto, tsg.tisg_nombre, emp.empr_nombre, ent.ento_nombre "
				+ "FROM seguimiento.empresa emp "
				+ "inner join seguimiento.empresaservidor ems on emp.empr_id = ems.empr_id "
				+ "inner join seguimiento.servidor ser on ems.serv_id = ser.serv_id "
				+ "inner join seguimiento.sgbd sgb on ser.serv_id = sgb.serv_id "
				+ "inner join seguimiento.basedatos bda on sgb.sgbd_id = bda.sgbd_id and emp.empr_id = bda.empr_id "
				+ "inner join seguimiento.entorno ent on bda.ento_id = ent.ento_id "
				+ "inner join seguimiento.tiposgbd tsg on sgb.tisg_id = tsg.tisg_id "
				+ "WHERE bda.bada_id = "+bada_id+" ";
		
		//System.out.println(sql);
			
			stmt = con.createStatement();			
			rset = stmt.executeQuery( sql ); 	
		
		if(rset.next()){			
			do{	
				retorno = retorno+
							"<div class=\"all\">NOMBRE:"
							+ "<span class=\"small\">De la base de datos</span>"
							+ "</div>"
							+ "<div class=\"allres\">"
							+rset.getString("BADA_NOMBRE")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">DESCRIPCIÓN:"
							+ "<span class=\"small\">Describa la BD</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("BADA_DESCRIPCION")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">ENCODING:"
							+ "<span class=\"small\">Usado por la BD</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("BADA_ENCODING")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">PUERTO:"
							+ "<span class=\"small\">Usado por la BD</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("BADA_PUERTO")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">ESTADO:"
							+ "<span class=\"small\">Actual</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("BADA_ESTADO")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">SGBD:"
							+ "<span class=\"small\">De la BD</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SERV_IPV4")+" - "+rset.getString("SERV_HOST")+":"+rset.getString("SGBD_PUERTO")+" - "+rset.getString("TISG_NOMBRE")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">EMPRESA:"
							+ "<span class=\"small\">Empresa</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("EMPR_NOMBRE")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">ENTORNO:"
							+ "<span class=\"small\">De uso</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("ENTO_NOMBRE")
							+"</div>"
							+ "<div class=\"spacer\"></div>";			
			}while (rset.next());
				
		}
		//System.out.println(retorno);
		
		stmt.close();
		conn.cerrar(con);
		
		return retorno;			
		
	}
	
	
public String menuBaseDatos ( HttpServletRequest request, String filtro ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "<div class='acti_menu'><div class='acti_menu0'> Seleccione una base de datos</div>";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT bda.bada_id,  bda.bada_nombre, bda.bada_estado, ser.serv_ipv4, COALESCE(ser.serv_host, 'No registra Host') serv_host, bda.bada_puerto, tsg.tisg_nombre, empr_sigla, empr_nombre "
				+ "FROM seguimiento.empresa emp "
				+ "inner join seguimiento.empresaservidor ems on emp.empr_id = ems.empr_id "
				+ "inner join seguimiento.servidor ser on ems.serv_id = ser.serv_id "
				+ "inner join seguimiento.sgbd sgb on ser.serv_id = sgb.serv_id "
				+ "inner join seguimiento.basedatos bda on sgb.sgbd_id = bda.sgbd_id and emp.empr_id = bda.empr_id "
				+ "inner join seguimiento.entorno ent on bda.ento_id = ent.ento_id "
				+ "inner join seguimiento.tiposgbd tsg on sgb.tisg_id = tsg.tisg_id "
				+ "WHERE "+filtro+" "
				+ "ORDER BY bda.bada_estado, bda.bada_nombre "
				+ "limit 50";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
				
		if(rset.next()){			
			do{	
				retorno = retorno+"<form action=\"./deta_bada.jsp\" method=\"post\" name=\"visu_bada_id\" id=\""+rset.getString("bada_id")+"\" target=\"bada_contenido\">"
				+"<div class='reg_menu' onclick=\"document.getElementById('"+rset.getString("bada_id")+"').submit();\">"
				+"<input type=\"hidden\" name=\"BADA_ID\" id=\"BADA_ID\" value=\""+rset.getString("bada_id")+"\">"
				+"<div class='reg_menu0'>"+rset.getString("bada_nombre")+" - "+rset.getString("bada_estado")+"</div>"
				+"<div class='reg_menu1'>"+rset.getString("serv_ipv4")+" - "+rset.getString("serv_host")+":"+rset.getString("bada_puerto")+" - "+rset.getString("tisg_nombre")+"</div>"
				+"<div class='reg_menu2'>"+rset.getString("empr_sigla")+" - "+rset.getString("empr_nombre")+"</div>"
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

public String editar( String bada_id ) throws SQLException {
	
	conexion conn = new conexion();
	Connection con = conn.establecer();
	
	Statement stmt = null;
	ResultSet rset = null;

	String retorno = "";
	
	String sql = "SELECT sgb.sgbd_id, bda.bada_id,  bda.bada_nombre, bda.bada_descripcion, bda.bada_encoding, bda.bada_estado, ser.serv_ipv4, COALESCE(ser.serv_host, 'No registra Host') serv_host, bda.bada_puerto, tsg.tisg_nombre, empr_sigla, empr_nombre, ent.ento_id, emp.empr_id "
				+ "FROM seguimiento.empresa emp "
				+ "inner join seguimiento.empresaservidor ems on emp.empr_id = ems.empr_id "
				+ "inner join seguimiento.servidor ser on ems.serv_id = ser.serv_id "
				+ "inner join seguimiento.sgbd sgb on ser.serv_id = sgb.serv_id "
				+ "inner join seguimiento.basedatos bda on sgb.sgbd_id = bda.sgbd_id and emp.empr_id = bda.empr_id "
				+ "inner join seguimiento.entorno ent on bda.ento_id = ent.ento_id "
				+ "inner join seguimiento.tiposgbd tsg on sgb.tisg_id = tsg.tisg_id "
				+ "WHERE bda.bada_id = "+bada_id+" ";
	
	//System.out.println(sql);
		
		stmt = con.createStatement();			
		rset = stmt.executeQuery( sql ); 	
	
	if(rset.next()){			
		do{	
			retorno = retorno
					+"<input value=\""+rset.getString("BADA_ID")+"\" type=\"hidden\" name=\"BADA_ID\" id=\"BADA_ID\">"
					+"<label for=\"BADA_NOMBRE\">*NOMBRE"
					+"<span class=\"small\">De la base de datos</span>"
					+"<span id=\"BADA_NOMBRE_M\"></span>"
					+"</label>"
					+"<input value=\""+rset.getString("BADA_NOMBRE")+"\" class=\"\" type=\"text\" name=\"BADA_NOMBRE\" id=\"BADA_NOMBRE\" maxlength=\"100\"/>"
					+""
					+"<label for=\"BADA_DESCRIPCION\">*DESCRIPCIÓN"
					+"<span class=\"small\">Describa la BD</span>"
					+"<span id=\"BADA_DESCRIPCION_M\"></span>"
					+"</label>"
					+"<input value=\""+rset.getString("BADA_DESCRIPCION")+"\" class=\"\" type=\"text\" name=\"BADA_DESCRIPCION\" id=\"BADA_DESCRIPCION\" maxlength=\"1000\"/>"
					+""
					+"<label for=\"BADA_ENCODING\">ENCODING"
					+"<span class=\"small\">Usado por la BD</span>"
					+"<span id=\"BADA_ENCODING_M\"></span>"
					+"</label>"
					+"<input value=\""+rset.getString("BADA_ENCODING")+"\" class=\"\" type=\"text\" name=\"BADA_ENCODING\" id=\"BADA_ENCODING\" maxlength=\"100\"/>"
					+""
					+"<label for=\"BADA_PUERTO\">*PUERTO"
					+"<span class=\"small\">Usado por la BD</span>"
					+"<span id=\"BADA_PUERTO_M\"></span>"
					+"</label>"
					+"<input value=\""+rset.getString("BADA_PUERTO")+"\" class=\"\" type=\"text\" name=\"BADA_PUERTO\" id=\"BADA_PUERTO\" maxlength=\"7\"/>"
					+""
					+"<label for=\"BADA_ESTADO\">*ESTADO"
					+"<span class=\"small\">Actual</span>"
					+"<span id=\"BADA_ESTADO_M\"></span>"
					+"</label>"
					+"<select name=\"BADA_ESTADO\" id=\"BADA_ESTADO\" onchange=\"document.getElementById('BADA_ESTADO_M').style.display='none';\">"
					+"<option value=\"\"></option>"
					+"<option value=\"Activa\" ";
				if(rset.getString("BADA_ESTADO") !=  null && rset.getString("BADA_ESTADO").equals("Activa")){ retorno= retorno+ "selected";}
					retorno= retorno+" >Activa</option>"
					+"<option value=\"Inactiva\" ";
				if(rset.getString("BADA_ESTADO") !=  null && rset.getString("BADA_ESTADO").equals("Inactiva")){ retorno= retorno+ "selected";}
					retorno= retorno+" >Inactiva</option>"
					+"<option value=\"Eliminada\" ";
				if(rset.getString("BADA_ESTADO") !=  null && rset.getString("BADA_ESTADO").equals("Eliminada")){ retorno= retorno+ "selected";}
					retorno= retorno+" >Eliminada</option>"
					+"</select>"
					+""
					+"<label for=\"SGBD_ID\">*SGBD"
					+"<span class=\"small\">De la BD</span>"
					+"<span id=\"SGBD_ID_M\"></span>"
					+"</label>";
					
					String empr_funciones = "empr";
					String empresaservidor ="'../utileria/load_empresaservidor.jsp?empr_funciones="+empr_funciones+"&sgbd_id='+document.getElementById(\"SGBD_ID\").value";
					
					retorno = retorno 
					+new sgbd().generarCombo( "Activo", rset.getString("SGBD_ID"), "", "SGBD_ID", "document.getElementById('SGBD_ID_M').style.display='none'; "+empr_funciones+"Carga('GET', "+empr_funciones+" )" )
					+new loadFile().cargaAjaxFuncion(empr_funciones, empr_funciones, empresaservidor)
					+""
					+"<label for=\"EMPR_ID\">EMPRESA"
					+"<span class=\"small\">Empresa</span>"
					+"<span id=\"EMPR_ID_M\"></span>"
					+"</label>"
					+""
					+"<div id=\""+empr_funciones+"\">"
					+new empresa().generarCombo( "Activa", rset.getString("EMPR_ID"), "", "EMPR_ID", "document.getElementById('SGBD_ID').value='0'; document.getElementById('EMPR_ID').value='0';" )
					+"</div>"
					+""
					+"<label for=\"ENTO_ID\">*ENTORNO"
					+"<span class=\"small\">De uso</span>"
					+"<span id=\"ENTO_ID_M\"></span>"
					+"</label>"
					+new entorno().generarCombo( "Activo", rset.getString("ENTO_ID"), "", "ENTO_ID", "document.getElementById('ENTO_ID_M').style.display='none';" )
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

public String update( HttpServletRequest request ) {
	
	
	
	String sql = "UPDATE seguimiento.basedatos"
			+"   SET bada_nombre='"+request.getParameter("BADA_NOMBRE")+"', "
			+"   bada_registradopor='"+(String)request.getSession().getAttribute("PERS_ID")+"', "
			+"   bada_fecharegistro=NOW(), "
			+"   bada_estado='"+request.getParameter("BADA_ESTADO")+"', "
			+"   bada_descripcion='"+request.getParameter("BADA_DESCRIPCION")+"', "
			+"   ento_id="+request.getParameter("ENTO_ID")+", "
			+"   sgbd_id="+request.getParameter("SGBD_ID")+", "
			+"   bada_encoding='"+request.getParameter("BADA_ENCODING")+"', "
			+"   bada_puerto='"+request.getParameter("BADA_PUERTO")+"', "
			+"   empr_id="+request.getParameter("EMPR_ID")+" "
			+"	 WHERE bada_id="+request.getParameter("BADA_ID");
	//System.out.println(sql);		
	return new sql().insertar(sql, "Base de datos actualizada corectamente.", "No fue posible actualizar la Base de Datos.", "mensaje_ok", "mensaje_err");
	
}

	
	
}
