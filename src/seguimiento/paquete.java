package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.conexion;

public class paquete {
	

	public String generarCombo( String prsg_id, String estado, String selected, String clase, String campo, String cambio ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||paq.paqu_id||'\" '||CASE WHEN paq.paqu_id = "+selected+" THEN 'selected' ELSE '' END||'>'||paq.paqu_nombre||'</option>' AS ITEM "
				+ "FROM seguimiento.paquete paq "
				+ "WHERE paq.paqu_estado = '"+estado+"' "
				+ "and paq.prsg_id = "+prsg_id+" "
				+ "ORDER BY paq.paqu_nombre";
		
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
	
	
}
