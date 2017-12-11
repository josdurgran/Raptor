package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import core.conexion;

public class departamento {
	

	public String generarCombo( String pais, String estado, String selected, String clase, String campo,String cambio ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		if(pais == null){ pais = "0"; }
		
		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||depa_id||'\" '||CASE WHEN depa_id = "+selected+" THEN 'selected' ELSE '' END||'>'||depa_nombre||'</option>' AS ITEM "
				+ "FROM core.departamento "
				+ "WHERE depa_estado = '"+estado+"' "
				+ "and pais_id = "+pais+" "
				+ "ORDER BY depa_nombre";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
		
		retorno = retorno+"<select name=\""+campo+"\" id=\""+campo+"\" onchange=\""+cambio+"\" class=\""+clase+"\" ><option value=\"\"></option>";
		
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
