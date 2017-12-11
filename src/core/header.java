package core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import core.conexion;

public class header {
	
	public String imprimir( String siti_id, String head_tipo ) throws SQLException	{
				
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * "
											+ "FROM core.sitio sit, "
											+ "core.header hea "
											+ "where sit.siti_id = hea.siti_id "
											+ "and sit.siti_id = "+siti_id+" "
											+ "and hea.head_tipo in ("+head_tipo+") "
											+ "order by hea.head_orden");
		String retorno = "";
		while (rs.next()){
			if(rs.getString("head_tipo").equals("css")){	
				retorno += "<link rel=\"stylesheet\" href=\""+rs.getString("siti_plantillasitio")+"css/"+rs.getString("head_nombre")+"\" />";
			}
			if(rs.getString("head_tipo").equals("javascript")){	
				
				retorno += "<script language=\"javascript\" src=\""+rs.getString("siti_plantillasitio")+"javascript/"+rs.getString("head_nombre")+"\"></script>";
			}
			
			if(rs.getString("head_tipo").equals("template")){	
				
				retorno += rs.getString("siti_plantillasitio")+rs.getString("head_nombre")+",";
											
			}
			
		}
		
		stmt.close();		
		conn.cerrar(con);
		
		return retorno;
		
	}	

}
