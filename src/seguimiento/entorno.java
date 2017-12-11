package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import core.conexion;

public class entorno {
	
	public String generarCombo( String estado, String selected, String clase, String campo, String cambio ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||ent.ento_id||'\" '||CASE WHEN ent.ento_id = "+selected+" THEN 'selected' ELSE '' END||'>'||ent.ento_nombre||'</option>' AS ITEM "
				+ "FROM seguimiento.entorno ent "
				+ "WHERE ent.ento_estado = '"+estado+"' "
				+ "ORDER BY ent.ento_nombre";
		
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
	
}
