package utileria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conectar {
	
private Connection con = null;
    	
	public Connection establecer( String driver, String host, String port, String database, String user, String password )	{
			
			String connectString = "jdbc:postgresql://"+host+":"+port+"/"+database;
			
			try{
			Class.forName( driver );
			con = DriverManager.getConnection(connectString, user , password );	
			}catch ( Exception e ){
				//System.out.println(e.getMessage());
				System.out.println("Ha ocurrido un error con la conexión a la base de datos.");
				
				/* Se cierra la conexion en caso de que se halla establecido y ocurra algun error. */
				
				if(con != null){
					this.cerrar(con);
				}
			}
			
			return con;
		
	}	
	
    public void cerrar( Connection connection ){
        
    	try {
			if (connection != null && connection.isClosed() == false) {
			    connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}
