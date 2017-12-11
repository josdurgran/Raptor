package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.conexion;

public class producto {
	

	public String generarCombo( String tisg_id, String estado, String selected, String clase, String campo, String cambio ) throws SQLException {
		
		String [] tisg  = tisg_id.split(",");
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||psg.prsg_id||'\" '||CASE WHEN psg.prsg_id = "+selected+" THEN 'selected' ELSE '' END||'>'||pro.prod_sigla||' - '||coalesce(pro.prod_nombre, 'N/R')||'</option>' AS ITEM "
				+ "FROM seguimiento.producto pro, "
				+ "seguimiento.productosgbd psg "
				+ "WHERE pro.prod_estado = '"+estado+"' "
				+ "AND psg.prsg_estado = '"+estado+"' "
				+ "and psg.tisg_id = "+tisg[1]+" "
				+ "and pro.prod_id = psg.prod_id "
				+ "ORDER BY pro.prod_sigla, pro.prod_nombre";
		
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
