package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import utileria.sql;
import core.conexion;

public class empresaServidor {
	

	public String generarComboServidor( String empr_id, String estado, String selected, String clase, String cambio ) throws SQLException {

		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||ser.serv_id||'\" '||CASE WHEN ser.serv_id = "+selected+" THEN 'selected' ELSE '''' END||'>'||ser.serv_ipv4||' - '||coalesce(ser.serv_host, 'N/R')||'</option>' AS ITEM "
				+ "FROM seguimiento.empresaservidor ems, "
				+ "seguimiento.servidor ser "
				+ "WHERE ems.serv_id = ser.serv_id "
				+ "and ems.emse_estado = '"+estado+"' "
				+ "and  empr_id = "+empr_id+" "
				+ "ORDER BY ser.serv_ipv4, ser.serv_host";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
		
		retorno = retorno+"<select name=\"serv_id\" id=\"serv_id\" onchange=\""+cambio+"\" ><option value=\"\"></option>";
		
		if(rset.next()){			
			do{	
				retorno = retorno+rset.getString("ITEM");			
			}while (rset.next());
				
		}
				retorno = retorno+"</select> <label for=\"serv_id_M\"><span id=\"serv_id_M\" class=\"error\"></span></label>";			
		
		
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
	
	public String generarEmpresas( String serv_id, String campo, String selected, String clase, String cambio ) throws SQLException {

		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||emp.empr_id||'\" >'||emp.empr_sigla||' - '||emp.empr_nombre||'</option>' AS ITEM "
				+ "from seguimiento.empresa emp "
				+ "where not exists (select 1 from seguimiento.empresaservidor ems where emp.empr_id = ems.empr_id and ems.serv_id = "+serv_id+" ) "
				+ "and emp.empr_estado = 'Activa' "
				+ "order by emp.empr_sigla, emp.empr_nombre";
		
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
	
	public String insertar( HttpServletRequest request ) {
		
		String sql = "INSERT INTO seguimiento.empresaservidor( emse_fecha, emse_estado, emse_registradopor, emse_fecharegistro, empr_id, serv_id) VALUES ("
				+ "NOW()," + "'Activo', '"+(String)request.getSession().getAttribute("PERS_ID")+"', NOW(), "+request.getParameter("EMPR_ID")+", "+request.getParameter("SERV_ID")+")";
		//System.out.println(sql);		
		return new sql().insertar(sql, "Asociación realizada.", "No fue posible la asociación.", "mensaje_ok", "mensaje_err");
		
	}	
	
	public String eliminar ( HttpServletRequest request ) {
		
		String sql = "delete from seguimiento.empresaservidor where emse_id = "+request.getParameter("EMSE_ID");
		//System.out.println(sql);		
		return new sql().insertar(sql, "Se ha desasociado la empresa.", "No fue posible desasociar la empresa.", "mensaje_ok", "mensaje_err");
		
	}	
	
	public String visualizarAsociacion( HttpServletRequest request, String serv_id, boolean formulario ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = null;
		ResultSet rset = null;

		String retorno = "";
		
		String sql = "SELECT * "
				+ "from seguimiento.empresaservidor ems "
				+ "inner join seguimiento.empresa emp on ems.empr_id = emp.empr_id and ems.serv_id = "+serv_id+" "
				+ "order by ems.emse_estado, emp.empr_sigla, emp.empr_nombre";
		
		//System.out.println(sql);
			
			stmt = con.createStatement();			
			rset = stmt.executeQuery( sql ); 	
		
		if(rset.next()){			
			do{	
				retorno = retorno+
							"<div class=\"all\">EMPRESA:"
							+ "<span class=\"small\">Asociada al servidor</span>"
							+ "</div>"
							+ "<div class=\"allres\">"
							+rset.getString("EMPR_SIGLA")+" - "+rset.getString("EMPR_NOMBRE")
							+"</div>";
				
							if(formulario){
								
								retorno = retorno
										+ "<form id=\"form\" name=\"form\" method=\"post\" action=\"\" onsubmit=\"\">"
										+ "<input type=\"hidden\" name=\"EMSE_ID\" id=\"EMSE_ID\" value=\""+rset.getString("EMSE_ID")+"\" >"
										+ "<input type=\"hidden\" name=\"SERV_ID\" id=\"SERV_ID\" value=\""+serv_id+"\" >"
										+ "<input type=\"hidden\" name=\"PERS_ID\" id=\"PERS_ID\" value=\""+(String)request.getSession().getAttribute("PERS_ID")+"\" >"
										+ "<button class=\"quitar\" type=\"submit\" name=\"accion\" value=\"quitar\">(Quitar)</button>"
										+ "</form>";
								
							}
				
				retorno = retorno
							+ "<div class=\"spacer\"></div>";			
			}while (rset.next());
				
		}else{
			
			retorno = "Sin empresas asociadas.";
			
		}
		//System.out.println(retorno);
		
		stmt.close();
		conn.cerrar(con);
		
		return retorno;			
		
	}
	
}
