package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import utileria.sql;
import core.conexion;

public class sgbd {
	
	public String insertar( String secuencia, HttpServletRequest request ) {
		
		
		
		String sql = "INSERT INTO seguimiento.sgbd(sgbd_id, sgbd_estado, sgbd_registradopor, sgbd_fecharegistro, tisg_id, sgbd_puerto, sgbd_version, sgbd_licenciado, serv_id) VALUES ("
				+secuencia+ ",'"+request.getParameter("SGBD_ESTADO")+"', '"+(String)request.getSession().getAttribute("PERS_ID")+"', NOW(), "+request.getParameter("TISG_ID")+", '"+request.getParameter("SGBD_PUERTO")+"', '"+request.getParameter("SGBD_VERSION")+"', '"+request.getParameter("SGBD_LICENCIADO")+"', "+request.getParameter("SERV_ID")+")";
		//System.out.println(sql);		
		return new sql().insertar(sql, "SGBD guardado corectamente.", "No fue posible guardar el SGBD.", "mensaje_ok", "mensaje_err");
		
	}	
	

	public String generarCombo( String serv_id, String estado, String selected, String clase, String campo, String cambio ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||sgb.sgbd_id||','||sgb.tisg_id||'\" '||CASE WHEN sgb.sgbd_id = "+selected+" THEN 'selected' ELSE '' END||'>'||tis.tisg_nombre||' - '||coalesce(sgb.sgbd_version, 'N/R')||'</option>' AS ITEM "
				+ "FROM seguimiento.sgbd sgb, "
				+ "seguimiento.tiposgbd tis "
				+ "WHERE sgb.sgbd_estado = '"+estado+"' "
				+ "and sgb.serv_id = "+serv_id+" "
				+ "and sgb.tisg_id = tis.tisg_id "
				+ "ORDER BY sgb.tisg_id, sgb.sgbd_version";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
		
		retorno = retorno+"<select name=\""+campo+"\" id=\""+campo+"\" onchange=\""+cambio+"\" ><option value=\"\"></option>";
		
		if(rset.next()){			
			do{	
				retorno = retorno+rset.getString("ITEM");	
			}while (rset.next());
				
		}
				retorno = retorno+"</select>";			
		
		//System.out.println(retorno);
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}

	public String visualizar( String sgbd_id ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = null;
		ResultSet rset = null;

		String retorno = "";
		
		String sql = "SELECT * "
				+ "FROM seguimiento.servidor ser "
				+ "inner join seguimiento.sgbd sgb on ser.serv_id = sgb.serv_id "
				+ "inner join seguimiento.tiposgbd tsg on sgb.tisg_id = tsg.tisg_id "
				+ "WHERE sgb.sgbd_id = "+sgbd_id+" ";
		
		//System.out.println(sql);
			
			stmt = con.createStatement();			
			rset = stmt.executeQuery( sql ); 	
		
		if(rset.next()){			
			do{	
				retorno = retorno+
							"<div class=\"all\">SERVIDOR:"
							+ "<span class=\"small\">Servidor que lo aloja</span>"
							+ "</div>"
							+ "<div class=\"allres\">"
							+rset.getString("SERV_IPV4")+" ::: "+rset.getString("SERV_HOST")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">ESTADO:"
							+ "<span class=\"small\">Estado actual</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SGBD_ESTADO")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">TIPO:"
							+ "<span class=\"small\">Tipo</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("TISG_NOMBRE")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">PUERTO:"
							+ "<span class=\"small\">Elija el puerto</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SGBD_PUERTO")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">VERSIÓN:"
							+ "<span class=\"small\">Versión del SGBD</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SGBD_VERSION")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">LICENCIADO:"
							+ "<span class=\"small\">Sí/No</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("SGBD_LICENCIADO")
							+"</div>"
							+ "<div class=\"spacer\"></div>";			
			}while (rset.next());
				
		}
		//System.out.println(retorno);
		
		stmt.close();
		conn.cerrar(con);
		
		return retorno;			
		
	}
	
	
public String menuSgbd ( HttpServletRequest request, String filtro ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "<div class='acti_menu'><div class='acti_menu0'> Seleccione una empresa</div>";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT" 
					+ " sgb.sgbd_id, ser.serv_ipv4, COALESCE(ser.serv_host, 'No registra host') serv_host, sgb.sgbd_puerto, sgb.sgbd_version, tsg.tisg_nombre, sgb.sgbd_estado "
					+ "FROM "
					+ "seguimiento.sgbd sgb inner join seguimiento.servidor ser on sgb.serv_id = ser.serv_id "
					+ "inner join seguimiento.tiposgbd tsg on sgb.tisg_id = tsg.tisg_id "
					+ "where "+filtro+" "
					+ "ORDER BY "
					+ "sgb.sgbd_estado, ser.serv_ipv4, ser.serv_host "
					+ "limit 50";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
				
		if(rset.next()){			
			do{	
				retorno = retorno+"<form action=\"./deta_sgbd.jsp\" method=\"post\" name=\"visu_sgbd_id\" id=\""+rset.getString("sgbd_id")+"\" target=\"sgbd_contenido\">"
				+"<div class='reg_menu' onclick=\"document.getElementById('"+rset.getString("sgbd_id")+"').submit();\">"
				+"<input type=\"hidden\" name=\"SGBD_ID\" id=\"SGBD_ID\" value=\""+rset.getString("sgbd_id")+"\">"
				+"<div class='reg_menu0'>"+rset.getString("serv_ipv4")+"::::"+rset.getString("serv_host")+"</div>"
				+"<div class='reg_menu1'>"+rset.getString("tisg_nombre")+" - "+rset.getString("sgbd_version")+"</div>"
				+"<div class='reg_menu2'>"+rset.getString("sgbd_puerto")+" - "+rset.getString("sgbd_estado")+"</div>"
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

public String editar( String sgbd_id ) throws SQLException {
	
	conexion conn = new conexion();
	Connection con = conn.establecer();
	
	Statement stmt = null;
	ResultSet rset = null;

	String retorno = "";
	
	String sql = "SELECT * "
			+ "FROM seguimiento.servidor ser "
			+ "inner join seguimiento.sgbd sgb on ser.serv_id = sgb.serv_id "
			+ "inner join seguimiento.tiposgbd tsg on sgb.tisg_id = tsg.tisg_id "
			+ "WHERE sgb.sgbd_id = "+sgbd_id+" ";
	
	//System.out.println(sql);
		
		stmt = con.createStatement();			
		rset = stmt.executeQuery( sql ); 	
	
	if(rset.next()){			
		do{	
			retorno = retorno
					+"<input value=\""+rset.getString("sgbd_id")+"\" type=\"hidden\" name=\"SGBD_ID\" id=\"SGBD_ID\">"
					+ "<label for=\"SERV_ID\">*SERVIDOR"
					+"<span class=\"small\">Servidor que lo aloja</span>"
					+"<span id=\"SERV_ID_M\"></span>"
					+"</label>"
					+""					
					+new servidor().generarCombo( "Activo", rset.getString("serv_id"), "", "SERV_ID", "document.getElementById('SERV_ID_M').style.display='none';")
					+""
					+"<label for=\"SGBD_ESTADO\">*ESTADO"
					+"<span class=\"small\">Estado actual</span>"
					+"<span id=\"SGBD_ESTADO_M\"></span>"
					+"</label>"
					+"<select name=\"SGBD_ESTADO\" id=\"SGBD_ESTADO\" onchange=\"document.getElementById('SGBD_ESTADO_M').style.display='none';\">"
					+"<option value=\"\"></option>"
					+"<option value=\"Activo\" ";
			if(rset.getString("SGBD_ESTADO") !=  null && rset.getString("SGBD_ESTADO").equals("Activo")){ retorno= retorno+ "selected";}
					retorno= retorno+">Activo</option>"
					+"<option value=\"Inactivo\" ";
			if(rset.getString("SGBD_ESTADO") !=  null && rset.getString("SGBD_ESTADO").equals("Inactivo")){ retorno= retorno+ "selected";}
					retorno= retorno+">Inactivo</option>"
					+"</select>"
					+""
					+"<label for=\"TISG_ID\">*TIPO"
					+"<span class=\"small\">Elija un tipo</span>"
					+"<span id=\"TISG_ID_M\"></span>"
					+"</label>"
					+""					
					+new tipoSgbd().generarCombo( "Activo", rset.getString("tisg_id"), "", "TISG_ID", "document.getElementById('TISG_ID_M').style.display='none';" )
					+""
					+"<label for=\"SGBD_PUERTO\">PUERTO"
					+"<span class=\"small\">Elija el puerto</span>"
					+"<span id=\"SGBD_PUERTO_M\"></span>"
					+"</label>"
					+"<input value=\""+rset.getString("sgbd_puerto")+"\" class=\"\" type=\"text\" name=\"SGBD_PUERTO\" id=\"SGBD_PUERTO\" maxlength=\"7\"/>"
					+""
					+"<label for=\"SGBD_VERSION\">VERSIÓN"
					+"<span class=\"small\">Versión del SGBD</span>"
					+"<span id=\"SGBD_VERSION_M\"></span>"
					+"</label>"
					+"<input value=\""+rset.getString("sgbd_version")+"\" class=\"\" type=\"text\" name=\"SGBD_VERSION\" id=\"SGBD_VERSION\" maxlength=\"100\"/>"
					+""
					+"<label for=\"SGBD_LICENCIADO\">*LICENCIADO"
					+"<span class=\"small\">Sí/No</span>"
					+"<span id=\"SGBD_LICENCIADO_M\"></span>"
					+"</label>"
					+"<select name=\"SGBD_LICENCIADO\" id=\"SGBD_LICENCIADO\" onchange=\"document.getElementById('SGBD_LICENCIADO_M').style.display='none';\">"
					+"<option value=\"\"></option>"
					+"<option value=\"Sí\" ";
				if(rset.getString("SGBD_LICENCIADO") !=  null && rset.getString("SGBD_LICENCIADO").equals("Sí")){ retorno= retorno+ "selected";}
					retorno= retorno+">Sí</option>"
					+"<option value=\"No\" ";
			if(rset.getString("SGBD_LICENCIADO") !=  null && rset.getString("SGBD_LICENCIADO").equals("No")){ retorno= retorno+ "selected";}
					retorno= retorno+">No</option>"
					+"</select>"
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
	
	
	
	String sql = "UPDATE seguimiento.sgbd"
			+"   SET sgbd_estado='"+request.getParameter("SGBD_ESTADO")+"', "
			+"   sgbd_registradopor='"+(String)request.getSession().getAttribute("PERS_ID")+"', "
			+"   sgbd_fecharegistro=NOW(), "
			+"   tisg_id='"+request.getParameter("TISG_ID")+"', "
			+"   sgbd_puerto='"+request.getParameter("SGBD_PUERTO")+"', "
			+"   sgbd_version='"+request.getParameter("SGBD_VERSION")+"', "
			+"   sgbd_licenciado='"+request.getParameter("SGBD_LICENCIADO")+"' "
			+"	 WHERE serv_id="+request.getParameter("SERV_ID");
	//System.out.println(sql);		
	return new sql().insertar(sql, "SGBD actualizado corectamente.", "No fue posible actualizar el SGBD.", "mensaje_ok", "mensaje_err");
	
}

public String generarCombo( String estado, String selected, String clase, String campo,String cambio ) throws SQLException {
	
	conexion conn = new conexion();
	Connection con = conn.establecer();

	String retorno = "";
	
	Statement stmt = con.createStatement();
	
	String sql = "SELECT '<option value=\"'||sgb.sgbd_id||'\" '||CASE WHEN sgb.sgbd_id = "+selected+" THEN 'selected' ELSE '' END||'>'||serv_ipv4||' - '||COALESCE(ser.serv_host, 'No registra host')||':'||sgbd_puerto||' - '||tsg.tisg_nombre||'</option>' AS ITEM "
			+ "FROM seguimiento.sgbd sgb, "
			+ "seguimiento.servidor ser, "
			+ "seguimiento.tiposgbd tsg "
			+ "WHERE sgb.serv_id = ser.serv_id "
			+ "and sgb.tisg_id = tsg.tisg_id "
			+ "and sgb.sgbd_estado = '"+estado+"' "
			+ "ORDER BY ser.serv_ipv4, sgb.sgbd_puerto";
	
	//System.out.println(sql);
	
	ResultSet rset = stmt.executeQuery( sql ); 
	
	retorno = retorno+"<select class= \""+clase+"\" name=\""+campo+"\" id=\""+campo+"\" onchange=\""+cambio+"\" ><option value=\"0\"></option>";
	
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

public String generarComboEmpresa( String sgbd_id, String estado, String selected, String clase, String campo,String cambio ) throws SQLException {
	
	conexion conn = new conexion();
	Connection con = conn.establecer();

	String retorno = "";
	
	Statement stmt = con.createStatement();
	
	String sql = "SELECT '<option value=\"'||emp.empr_id||'\" '||CASE WHEN emp.empr_id = "+selected+" THEN 'selected' ELSE '' END||'>'||emp.empr_sigla||' - '||emp.empr_nombre||'</option>' AS ITEM "
			+"FROM "
			+"  seguimiento.sgbd sgb, "
			+"  seguimiento.servidor ser, "
			+"  seguimiento.empresaservidor ems, "
			+"  seguimiento.empresa emp "
			+"WHERE "
			+"  ser.serv_id = sgb.serv_id AND "
			+"  ems.serv_id = ser.serv_id AND "
			+"  ems.empr_id = emp.empr_id AND "
			+"  sgb.sgbd_id = "+sgbd_id+" AND "
			+"  emp.empr_estado = '"+estado+"' "
			+"ORDER BY"
			+"  emp.empr_sigla ASC, "
			+"  emp.empr_nombre ASC";
	
	//System.out.println(sql);
	
	ResultSet rset = stmt.executeQuery( sql ); 
	
	retorno = retorno+"<select class= \""+clase+"\" name=\""+campo+"\" id=\""+campo+"\" onchange=\""+cambio+"\" ><option value=\"\"></option>";
	
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
	
}
