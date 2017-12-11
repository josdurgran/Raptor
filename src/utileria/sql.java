package utileria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.conexion;

public class sql {
	
	
	public String insertar( String Sql, String positivo, String negativo, String clasepositivo, String clasenegativo ) {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		String retorno = "";
		
		try {
			con.setAutoCommit(false);

		//System.out.println(Sql);        
		PreparedStatement ps = con.prepareStatement(Sql);    
    
		if(ps.executeUpdate()>=1){
			con.commit();
			retorno = "<div class='"+clasepositivo+"'>"+positivo+"</div>";
			}else{
				con.rollback();
				retorno = "<div class='"+clasenegativo+"'>"+negativo+"</div>";
			}    
		
		conn.cerrar(con);	
		
		} catch (SQLException e) {			
			e.printStackTrace();
		}	
		
		return retorno;
		
	}
	
	public String secuencia( String secuencia ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT NEXTVAL('"+secuencia+"') ITEM";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
		
				
		if(rset.next()){			
			do{	
				retorno = retorno+rset.getString("ITEM");			
			}while (rset.next());
				
		}		
		
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
	
	public ResultSet consulta ( String sql ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = null;
		ResultSet rset = null;
	
			stmt = con.createStatement();			
			rset = stmt.executeQuery( sql ); 	
			
			stmt.close();
			conn.cerrar(con);	
			
			return rset;
			
		}	
		
		
	
}
