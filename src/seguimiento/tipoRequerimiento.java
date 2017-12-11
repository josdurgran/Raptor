package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.conexion;

public class tipoRequerimiento {
	

	public String generarCombo( String estado, String selected, String clase, String campo,String cambio ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||tire_id||'\" '||CASE WHEN tire_id = "+selected+" THEN 'selected' ELSE '' END||'>'||tire_nombre||'</option>' AS ITEM "
				+ "FROM seguimiento.tiporequerimiento "
				+ "WHERE tire_estado = '"+estado+"' "
				+ "ORDER BY tire_nombre";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
		
		retorno = retorno+"<select name=\""+campo+"\" id=\""+campo+"\" onchange=\""+cambio+"\" ><option value=\"\"></option>";
		
		if(rset.next()){			
			do{	
				retorno = retorno+rset.getString("ITEM");			
			}while (rset.next());
				
		}
				retorno = retorno+"</select> <label for=\""+campo+"_M\"><span id=\""+campo+"_M\" class=\"error\"></span></label>";			
		
		
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
	
	
}
