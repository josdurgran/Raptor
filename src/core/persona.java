package core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import core.conexion;

public class persona {
	
	public ResultSet where ( String filtro, Connection con ) throws SQLException	{
				
		Statement stmt = con.createStatement();
		
		String sql = "SELECT * "
				+ "FROM core.persona per "
				+ "where "+filtro+" " 
				+ "order by per.tido_id, per.pers_documento";
		//System.out.println(sql);	
		ResultSet rs = stmt.executeQuery(sql);
				
		return rs;
		
	}	

}
