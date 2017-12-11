package seguimiento;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;





import javax.servlet.http.HttpServletRequest;

import core.conexion;

public class solicitud {	
	
	public String actualizarEstado ( HttpServletRequest request, String soli_id ) {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		String retorno = "";
		
		try {
			con.setAutoCommit(false);
	    String Sql = "UPDATE seguimiento.solicitud SET soli_registradopor = '"+(String)request.getSession().getAttribute("PERS_ID")+"', soli_fecharegistro = now(), espr_id = "+request.getParameter("espr_id")+" WHERE soli_id = "+soli_id+"";
		//System.out.println(Sql);        
		PreparedStatement ps = con.prepareStatement(Sql);    
    
		if(ps.executeUpdate()>=1){
			
			String sentencia = "INSERT INTO seguimiento.observacion( obse_descripcion, "
					+ "obse_estado, "
					+ "obse_registradopor, "
					+ "obse_fecharegistro, "
					+ "obse_fecha, "
					+ "tiob_id, "
					+ "soli_id) VALUES ( '"+request.getParameter("obse_descripcion").replaceAll("'", "''")+"', "
							+ "'Activa', "
							+ "'"+(String)request.getSession().getAttribute("PERS_ID")+"', "
							+ "now(), "
							+ "'"+request.getParameter("obse_fecha1")+"', "
							+ request.getParameter("tiob_id")+", "
							+ soli_id+")";
			
			if(new seguimiento.observacion().ejecutarSqlValidar(sentencia)){
				
			con.commit();
			retorno = "<div class='mensaje_ok'>Se ha cambiado el estado de la solicitud.</div>";
			}else{
				
				con.rollback();
				retorno = "<div class='mensaje_err'>No se cambiado el estado de la solicitud.</div>";				
				
			}
			
			}else{
				con.rollback();
				retorno = "<div class='mensaje_err'>No se cambiado el estado de la solicitud.</div>";
			}    
		
		conn.cerrar(con);	
		
		} catch (SQLException e) {			
			e.printStackTrace();
		}	
		
		return retorno;
		
	}
	
	public String detalleActividad ( String soli_id ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		String retorno = "<div class='acti_menu'>";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT "
				+ "   emp.empr_sigla, "
				+ "   emp.empr_nombre, "
				+ "   ser.serv_ipv4, "
				+ "   ser.serv_host, "
				+ "   tiposgbd.tisg_nombre, "
				+ "   sgb.sgbd_version, "
				+ "   bda.bada_nombre, "
				+ "   bda.bada_puerto, "
				+ "   pro.prod_sigla, "
				+ "   pro.prod_nombre, "
				+ "   scr.scri_esquema, "
				+ "   scr.scri_mes, "
				+ "   sol.soli_fechainicio, "
				+ "   sol.soli_fechafin, "
				+ "   tre.tire_nombre, "
				+ "   scr.scri_nombre, "
				+ "   scr.scri_solicitante, "
				+ "   scr.scri_complejidad, "
				+ "   CASE WHEN sol.soli_publicado = true then 'Sí' else 'No' end soli_publicado, "
				+ "   tip.tipr_nombre, "
				+ "   esp.espr_nombre, "
				+ "   scr.scri_descripcion, "
				+ "   COALESCE(scr.scri_nota, '') scri_nota,"
				+ "   PERS_PRIMERNOMBRE||' '||COALESCE(PERS_SEGUNDONOMBRE, '')||' '||PERS_PRIMERAPELLIDO||' '||COALESCE(PERS_SEGUNDOAPELLIDO, '') SCRI_ASIGNADO"
				+ " FROM "
				+ "   seguimiento.script scr, "
				+ "   seguimiento.scriptpaquete spa, "
				+ "   seguimiento.scriptpaquetebasedatos spb, "
				+ "   seguimiento.basedatos bda, "
				+ "   seguimiento.empresa emp, "
				+ "   seguimiento.solicitud sol, "
				+ "   seguimiento.estadoproceso esp, "
				+ "   seguimiento.sgbd sgb, "
				+ "   seguimiento.servidor ser, "
				+ "   seguimiento.tiposgbd, "
				+ "   seguimiento.paquete paq, "
				+ "   seguimiento.productosgbd prs, "
				+ "   seguimiento.producto pro, "
				+ "   seguimiento.tipoproceso tip, "
				+ "   seguimiento.tiporequerimiento tre, "
				+ "   core.persona per "
				+ " WHERE "
				+ "   scr.scri_id = spa.scri_id AND"
				+ "   spa.paqu_id = paq.paqu_id AND"
				+ "   spb.scpa_id = spa.scpa_id AND"
				+ "   spb.spbd_id = sol.spbd_id AND"
				+ "   bda.bada_id = spb.bada_id AND"
				+ "   bda.empr_id = emp.empr_id AND"
				+ "   sol.espr_id = esp.espr_id AND"
				+ "   sol.tipr_id = tip.tipr_id AND"
				+ "   sgb.sgbd_id = bda.sgbd_id AND"
				+ "   ser.serv_id = sgb.serv_id AND"
				+ "   tiposgbd.tisg_id = sgb.tisg_id AND"
				+ "   prs.prsg_id = paq.prsg_id AND"
				+ "   pro.prod_id = prs.prod_id AND"
				+ "   tre.tire_id = sol.tire_id AND"
				+ "   scr.pers_id = per.pers_id AND"
				+ "   sol.soli_id = "+ soli_id;
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
				
		if(rset.next()){			
			do{					
							
				retorno = retorno+
				"<div class=\"all\">SOLICITUD:"
				+ "<span class=\"small\">Nombre de la solicitud</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SCRI_NOMBRE")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">ASIGNADO A:"
				+ "<span class=\"small\">Persona asignada</span>"
				+ "</div>"
				+ "<div class=\"allres\">"
				+rset.getString("SCRI_ASIGNADO")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">EMPRESA:"
				+ "<span class=\"small\">Propietaria</span>"
				+ "</div>"
				+ "<div class=\"allres\">"
				+rset.getString("EMPR_SIGLA")+" ::: "+rset.getString("EMPR_NOMBRE")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">SERVIDOR:"
				+ "<span class=\"small\">Servidor</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SERV_IPV4")+" ::: "+rset.getString("SERV_HOST")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">SGBD:"
				+ "<span class=\"small\">Tipo</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("TISG_NOMBRE")+" ::: "+rset.getString("SGBD_VERSION")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">BASE DE DATOS:"
				+ "<span class=\"small\">Base de datos</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("BADA_NOMBRE")+" : "+rset.getString("BADA_PUERTO")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">PRODUCTO:"
				+ "<span class=\"small\">Que se efecta</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("PROD_SIGLA")+" "+rset.getString("PROD_NOMBRE")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">MES:"
				+ "<span class=\"small\">Mes</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SCRI_MES")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">FECHA INICIO:"
				+ "<span class=\"small\">Fecha inicio</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SOLI_FECHAINICIO")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">FECHA FIN:"
				+ "<span class=\"small\">Fecha fin</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SOLI_FECHAFIN")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">SOLICITANTE:"
				+ "<span class=\"small\">ó Solicitud</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SCRI_SOLICITANTE")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">ESQUEMA: "
				+ "<span class=\"small\">Que se afecta</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SCRI_ESQUEMA")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">COMPLEJIDAD: "
				+ "<span class=\"small\">Complejidad</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SCRI_COMPLEJIDAD")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">TIPO REQUERIMIENTO:"
				+ "<span class=\"small\">Tipo</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("TIRE_NOMBRE")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">PUBLICADO:"
				+ "<span class=\"small\">Sí/No</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SOLI_PUBLICADO")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">ESTADO:"
				+ "<span class=\"small\">Actual</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("ESPR_NOMBRE")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">TIPO PROCESO:"
				+ "<span class=\"small\">Proceso</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("TIPR_NOMBRE")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">DESCRIPCIÓN:"
				+ "<span class=\"small\">De la solicitud</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SCRI_DESCRIPCION").replaceAll("\n", "<br>")
				+"</div>"
				+ "<div class=\"spacer\"></div>"
				+"<div class=\"all\">NOTA:"
				+ "<span class=\"small\">Aclaración</span>"
				+ "</div><div class=\"allres\">"
				+rset.getString("SCRI_NOTA").replaceAll("\n", "<br>")
				+"</div>"
				+ "<div class=\"spacer\"></div>";
				
				
			}while (rset.next());
				
		}
		
		retorno += "</div>";
		
		//System.out.println(retorno);
		
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
	
}
