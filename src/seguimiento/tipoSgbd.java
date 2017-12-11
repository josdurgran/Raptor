package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import utileria.loadFile;
import utileria.sql;
import core.capitalOrigen;
import core.centroPoblado;
import core.conexion;
import core.departamento;
import core.pais;

public class tipoSgbd {
	

	public String generarCombo( String estado, String selected, String clase, String campo,String cambio ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||tisg_id||'\" '||CASE WHEN tisg_id = "+selected+" THEN 'selected' ELSE '' END||'>'||tisg_nombre||'</option>' AS ITEM "
				+ "FROM seguimiento.tiposgbd "
				+ "WHERE tisg_estado = '"+estado+"' "
				+ "ORDER BY tisg_nombre";
		
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
		
}
