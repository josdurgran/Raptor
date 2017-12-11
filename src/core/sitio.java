package core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sitio {
	
		public ResultSet  imprimir( Connection con, String siti_id ) throws SQLException{
		
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
												+ "FROM core.sitio sit "
												+ "where sit.siti_id = "+siti_id);			
		
			return rs;
		
		}	

}
