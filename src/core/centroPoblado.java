package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import core.conexion;

public class centroPoblado {
	

	public String generarCombo( String depa_id, String estado, String selected, String clase, String campo,String cambio ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		if( depa_id == null || depa_id == "" ){ depa_id = "0"; }

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||cep.cepo_id||'\" '||CASE WHEN cep.cepo_id = "+selected+" THEN 'selected' ELSE '' END||'>'||cep.cepo_nombre||' - '||tcp.ticp_sigla||'</option>' AS ITEM "
				+ "FROM core.centropoblado cep, "
				+ "core.municipio mun, "
				+ "core.tipocentropoblado tcp "
				+ "WHERE cep.ticp_id = tcp.ticp_id "
				+ "and mun.muni_id = cep.muni_id "
				+ "and cep.cepo_estado = '"+estado+"' "
				+ "and mun.depa_id = "+depa_id+" "
				+ "ORDER BY cep.cepo_nombre";
		
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
