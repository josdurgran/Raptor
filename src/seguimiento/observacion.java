package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.conexion;

public class observacion {
	

	public String ejecutarSql( String Sql ) {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		String retorno = "";
		
		try {
			con.setAutoCommit(false);

		//System.out.println(Sql);        
		PreparedStatement ps = con.prepareStatement(Sql);    
    
		if(ps.executeUpdate()>=1){
			con.commit();
			retorno = "<div class='mensaje_ok'>Observación registrada.</div>";
			}else{
				con.rollback();
				retorno = "<div class='mensaje_err'>No se ha registrado la observación.</div>";
			}    
		
		conn.cerrar(con);	
		
		} catch (SQLException e) {			
			e.printStackTrace();
		}	
		
		return retorno;
		
	}
	
	
	public boolean ejecutarSqlValidar( String Sql ) {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		boolean retorno = false;
		
		try {
			con.setAutoCommit(false);

		//System.out.println(Sql);        
		PreparedStatement ps = con.prepareStatement(Sql);    
    
		if(ps.executeUpdate()>=1){
			con.commit();
			retorno = true;
			}else{
				con.rollback();
				retorno = false;
			}    
		
		conn.cerrar(con);	
		
		} catch (SQLException e) {			
			e.printStackTrace();
		}	
		
		return retorno;
		
	}
	
	public String listar( String soli_id, String tiob_id, String clase, String caption) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		String par ="";
		int numerador = 1;
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT  "
				+ "  obs.obse_descripcion,  "
				+ "  obs.obse_id,  "
				+ "  obs.obse_fecha,  "
				+ "  COALESCE(obs.obse_documento, '') obse_documento, "
				+ "  COALESCE(obs.obse_documentosuma, '') obse_documentosuma, "
				+ "  obs.obse_estado, "
				+ "  (select count(1) from seguimiento.observacion where soli_id = "+soli_id+" and tiob_id = "+tiob_id+") as row "
				+ "FROM  "
				+ "  seguimiento.observacion obs "
				+ "WHERE  "
				+ "  obs.soli_id = "+soli_id+" AND "
				+ "  obs.tiob_id = "+tiob_id+" "
				+ "ORDER BY "
				+ "  obs.obse_estado,  "
				+ "  obs.obse_fecha DESC,  "
				+ "  obs.obse_id DESC ";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql );
				
			if(rset.next()){	
				retorno = "<table class='"+clase+"'>"
						+ "<caption>"+caption+"</caption>"
						+ "<tr>"
						+ "<th>No.</th>"
						+ "<th>Fecha</th>"
						+ "<th>Observación</th>"
						+ "<th>Estado</th>"
						+ "<th>Archivos</th>"
						+ "</tr>";
				numerador = Integer.parseInt(rset.getString("row"));
				
				do{	
					
					if( numerador % 2 == 0 ){ par = "td_par"; }else{ par=""; }
					retorno += "<tr>"
							+ "<td class = '"+par+"'>"+numerador+"</td>"
							+ "<td class = '"+par+"'>"+rset.getString("obse_fecha")+"</td>"
							+ "<td class = '"+par+"'>"+rset.getString("obse_descripcion").replaceAll("\n", "<br>")+"</td>"
							+ "<td class = '"+par+"'>"+rset.getString("obse_estado")+"</td>"
							+ "<td class = '"+par+"'>"+rset.getString("obse_documento").replaceAll(",", "<br>")+"</td>"
							+ "</tr>";
					numerador--;
				}while (rset.next());
				
				retorno += "</table>";						
			}
				
		
		//System.out.println(retorno);
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
}
