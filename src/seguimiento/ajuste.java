package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import utileria.archivo;
import core.conexion;

public class ajuste {
	

	public String generar( HttpServletRequest request ) {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		String retorno = "";
		String Sql = "";
		
		Date now = new Date();
		String SCRI_NOMBRE = "AJUSTE"+new SimpleDateFormat("-yyyyMMddHHmmss").format(now);
		String EXTENSION = ".sql";
		
		try {
			con.setAutoCommit(false);

		Sql = "INSERT INTO seguimiento.ajuste( ajus_nombre, ajus_descripcion, ajus_nota, ajus_estado, tiob_registradopor, tiob_fecharegistro, soli_id) VALUES ("
				+ "'"+SCRI_NOMBRE+EXTENSION+"', "
				+ "'"+request.getParameter("ajus_descripcion").replaceAll("'", "''")+"', "
				+ "'"+request.getParameter("ajus_nota").replaceAll("'", "''")+"', "
				+ "'Activo', "
				+ "'"+(String)request.getSession().getAttribute("PERS_ID")+"', "
				+ "NOW(), "
				+ "'"+request.getParameter("soli_id")+"' "
				+ ")";
		//System.out.println(Sql);        
		PreparedStatement ps = con.prepareStatement(Sql);    
    
		if(ps.executeUpdate()>=1){
			
			if(new archivo().generarDocumento( (String)request.getSession().getAttribute("ALMACENAMIENTO")+(String)request.getSession().getAttribute("SEP")+request.getParameter("scri_nombre")+(String)request.getSession().getAttribute("SEP"), SCRI_NOMBRE, EXTENSION,new archivo().script(request, SCRI_NOMBRE ))){
				con.commit();
				retorno = "<div class='mensaje_ok'>Se ha generado el ajuste.</div>";
			}else{
				con.rollback();			
				retorno = "<div class='mensaje_err'>No ha generado el ajuste.</div>";
			} 
			
		}else{
			con.rollback();
			System.out.println("Error");
				
		}    
		
		conn.cerrar(con);	
		
		} catch (SQLException e) {			
			e.printStackTrace();
			retorno = "<div class='mensaje_err'>No ha generado el ajuste.</div>";
		}	
		
		return retorno;
		
	}
	
	
}
