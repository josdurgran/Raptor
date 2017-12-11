package seguimiento;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import core.conexion;

public class script {
	

	public String insertarActividad( HttpServletRequest request )  {
			
		Statement stmt;
		String retorno = "";
		conexion conn = new conexion();
		Connection con = conn.establecer();
		String sep = (String)request.getSession().getAttribute("SEP");
		Date now = new Date();
		String SCRI_NOMBRE = request.getParameter("SCRI_NOMBRE").trim()+new SimpleDateFormat("-yyyyMMddHHmmss").format(now);
		retorno = "<div class='";
				
		try {
			
		if (this.controlDuplicidad(request)) {
				
			con.setAutoCommit(false);
			stmt = con.createStatement();
			
        String Sql = "INSERT INTO seguimiento.script( scri_nombre, scri_estado, scri_solicitante, scri_fecha, "
        		+ "scri_complejidad, scri_mes, scri_esquema, scri_registradopor, scri_fecharegistro, scri_descripcion, "
        		+ "scri_nota, pers_id) VALUES ( '"+SCRI_NOMBRE+"', "
        		+ "'"+request.getParameter("SCRI_ESTADO")+"', '"+request.getParameter("SCRI_SOLICITANTE")+"', NOW(), "
        		+ "'"+request.getParameter("SCRI_COMPLEJIDAD")+"', '"+request.getParameter("SCRI_MES")+"', "
        		+ "'"+request.getParameter("SCRI_ESQUEMA")+"', '"+request.getParameter("PERS_ID")+"', NOW(), "
        		+ "'"+request.getParameter("SCRI_DESCRIPCION").replaceAll("'", "''")+"', '"+request.getParameter("SCRI_NOTA").replaceAll("'", "''")+"', "
        		+ ""+request.getParameter("PERS_ID")+")";
        //System.out.println(Sql);        
        PreparedStatement ps = null;
        ps=con.prepareStatement(Sql);
        
        if(ps.executeUpdate()>=1){        
        	
        	/* Registro en seguimiento.scriptpaquete */
        	
        	Sql = "INSERT INTO seguimiento.scriptpaquete( scpa_estado, scpa_registradopor, scpa_fecharegistro, scri_id, "
        		+ "paqu_id) VALUES ( '"+request.getParameter("SCRI_ESTADO")+"', '"+request.getParameter("PERS_ID")+"', NOW(), "
        		+ "(select scri_id from seguimiento.script where scri_nombre = '"+SCRI_NOMBRE+"' ), "
        		+ ""+request.getParameter("paqu_id")+" )";
            //System.out.println(Sql);
            ps = null;
            ps=con.prepareStatement(Sql);

            if(ps.executeUpdate()>=1){

            	/* Registro en seguimiento.scriptpaquetebasedatos */
            	
            	Sql = "INSERT INTO seguimiento.scriptpaquetebasedatos(spbd_estado, "
            			+ "spbd_registradopor, "
            			+ "spbd_fecharegistro, "
            			+ "spbd_fecha, "
            			+ "spbd_ejecutadopor, " 
            			+ "spbd_solicitadopor, " 
            			+ "bada_id, "
            			+ "scpa_id) "
            			+ "VALUES ('"+request.getParameter("SCRI_ESTADO")+"', " 
            			+ "'"+request.getParameter("PERS_ID")+"', "
            			+ "NOW(), "
            			+ "NOW(), "
            			+ "'"+(String)request.getSession().getAttribute("PERS_PRIMERNOMBRE")+" "+(String)request.getSession().getAttribute("PERS_SEGUNDONOMBRE")+" "+(String)request.getSession().getAttribute("PERS_PRIMERAPELLIDO")+" "+(String)request.getSession().getAttribute("PERS_SEGUNDOAPELLIDO")+"', " 
            			+ "'"+request.getParameter("SCRI_SOLICITANTE")+"', "
            			+ ""+request.getParameter("bada_id")+", "
            			+ "(SELECT scp.scpa_id FROM seguimiento.scriptpaquete scp WHERE scp.scri_id = (select scri_id from seguimiento.script where scri_nombre = '"+SCRI_NOMBRE+"') AND scp.paqu_id = "+request.getParameter("paqu_id")+"))";
            	
            			        //System.out.println(Sql);
            			        ps = null;
            			        ps=con.prepareStatement(Sql);

            			        if(ps.executeUpdate()>=1){
            			        	/* Registro en seguimiento.solicitud */
            			        	
            			        	Sql = "INSERT INTO seguimiento.solicitud( soli_estado, "
            			        			+ "soli_fechainicio, "
            			        			+ "soli_fechafin, "
            			        			+ "soli_publicado, "
            			        			+ "soli_registradopor, " 
            			        			+ "soli_fecharegistro, "
            			        			+ "spbd_id, "
            			        			+ "tipr_id, "
            			        			+ "espr_id, "
            			        			+ "tire_id) VALUES ( '"+request.getParameter("SCRI_ESTADO")+"', " 
            			        			+ "'"+request.getParameter("SOLI_FECHAINICIO")+"', "
            			        			+ "'"+request.getParameter("SOLI_FECHAFIN")+"', "
            			        			+ ""+request.getParameter("SOLI_PUBLICADO")+", "
            			        			+ "'"+request.getParameter("PERS_ID")+"', "
            			        			+ "NOW(), "
            			        			+ "( SELECT spbd_id FROM seguimiento.scriptpaquetebasedatos WHERE bada_id = "+request.getParameter("bada_id")+" AND scpa_id = ( select scpa_id from seguimiento.scriptpaquete where scri_id = (select scri_id from seguimiento.script where scri_nombre = '"+SCRI_NOMBRE+"' ) and paqu_id = "+request.getParameter("paqu_id")+" ) ), " 
            			        			+ ""+request.getParameter("tipr_id")+", " 
            			        			+ "1, "
            			        			+ ""+request.getParameter("tire_id")+")";
            			        			        //System.out.println(Sql);
            			        			        ps = null;
            			        			        ps=con.prepareStatement(Sql);

            			        			        if(ps.executeUpdate()>=1){
            			        			        	
            			        			        	String ruta = (String)request.getSession().getAttribute("ALMACENAMIENTO")+sep+SCRI_NOMBRE;
            			        			        	File folder = new File( ruta );
            			        			        	File adjuntos = new File( ruta+"/adjuntos" );
            			        			        	File fichero = new File ( ruta, SCRI_NOMBRE+".sql");
            			        			        	
            			        			        	if(!folder.exists()){
            			        			        		
            			        			        		if(folder.mkdirs()){ 
            			        			        			          			        			        			
            			        			        			
                    			        			        	if(!adjuntos.exists()){
                    			        			        		
                    			        			        		adjuntos.mkdirs();          			        			        			
                    			        			        	}
                    			        			        	
            			        			        			try {
																	if(fichero.createNewFile()){
																																				
																		BufferedWriter bw = null;
																		
																		if (fichero.exists()){
																	
																		bw = new BufferedWriter(new FileWriter(fichero));	
																		bw.write( plantilla ( request, SCRI_NOMBRE ) );																		
																		bw.close();			
																		fichero.setWritable(true);
																		
																		}else {
																			
			                    			        			        	con.rollback();
			                    			        			        	retorno = retorno+"notificacion'>ERROR NO EXT FIC: No fue posible realizar el registro.";	
																			
																		}
																		
																		
																																			
																		
																		
																	retorno = retorno+"notificacion1'>Se guardo la actividad correctamente.";
																	con.commit();	
																	}
																} catch (IOException e) {
	                    			        			        	con.rollback();
	                    			        			        	retorno = retorno+"notificacion'>ERROR ADD FIC: No fue posible realizar el registro.";
																	// TODO Auto-generated catch block
																	e.printStackTrace();
																}
            			        			        		}else{
            			        			        			
                    			        			        	con.rollback();
                    			        			        	retorno = retorno+"notificacion'>ERROR ADD FOL: No fue posible realizar el registro.";
            			        			        			
            			        			        		}
            			        			        		
            			        			        	}else{
            			        			        		
                			        			        	con.rollback();
                			        			        	retorno = retorno+"notificacion'>ERROR EXT FOL: No fue posible realizar el registro.";
            			        			        		
            			        			        	}
            			        			        	
            			        			        	

            			        			        }else{
            			        			        	con.rollback();
            			        			        	retorno = retorno+"notificacion'>ERROR: No fue posible realizar el registro.";
            			        			        }
            			        	
            			        	/* Fin Registro en seguimiento.solicitud */
            			        	
            			        	
            			        }else{
            			        	con.rollback();
            			        	retorno = retorno+"notificacion'>ERROR: No fue posible realizar el registro.";
            			        }
            	
            	/* Fin Registro en seguimiento.scriptpaquetebasedatos */
            	
            }else{
            	con.rollback();
            	retorno = retorno+"notificacion'>ERROR: No fue posible realizar el registro.";
            }       	
        	
        	/* Fin Registro en seguimiento.scriptpaquete */
        	
        }else{  
        	con.rollback();
        	retorno = retorno+"notificacion'>ERROR: No fue posible realizar el registro.";     	
        }        
        
		stmt.close();		
		conn.cerrar(con);
		
		}else{			
			retorno = retorno+"notificacion'>NOTIFICACIÓN: La solicitud ya existe; no es posible registrarla nuevamente.";				
		}
        
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			retorno = retorno+"notificacion'>ERROR: No fue posible realizar el registro.";
			e.printStackTrace();
		}
		
		return retorno;
		
	}
	
	public String plantilla ( HttpServletRequest request, String SCRI_NOMBRE ) throws SQLException  {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		String paquete = "";
		String descripcion = "";
		String nota = "";
		Statement stmt = con.createStatement();
		
		String sql = "SELECT "
				+ "UPPER( tsg.tisg_nombre ) MOTOR, "
				+ "paq.paqu_nombre "
				+ "FROM "
				+ "seguimiento.paquete paq, "
				+ "seguimiento.productosgbd psg, "
				+ "seguimiento.tiposgbd tsg "
				+ "WHERE "
				+ "psg.prsg_id = paq.prsg_id AND "
				+ "tsg.tisg_id = psg.tisg_id AND "
				+ "paq.paqu_id = "+ request.getParameter( "paqu_id" );
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
		
		descripcion=request.getParameter("SCRI_DESCRIPCION").replaceAll("\r", "");
		descripcion=descripcion.replaceAll("\n", "\n-- ");
		
		nota=request.getParameter("SCRI_NOTA").replaceAll("\r", "");
		nota=nota.replaceAll("\n", "\n-- ");
						
		if(rset.next()){			
			do{	
				retorno += rset.getString("MOTOR");
				paquete = rset.getString("paqu_nombre");
			}while (rset.next());
				
		}
							
		stmt.close();		
		conn.cerrar(con);
		
		
		if(retorno != null && retorno.equals( "POSTGRESQL" )){
			
			retorno =     "-- "+SCRI_NOMBRE+".sql\n"
						+ "-- DESCRIPCION: "+descripcion+"\n"
						+ "-- IMPLEMENTADO POR: "+(String)request.getSession().getAttribute("PERS_PRIMERNOMBRE")+" "+(String)request.getSession().getAttribute("PERS_SEGUNDONOMBRE")+" "+(String)request.getSession().getAttribute("PERS_PRIMERAPELLIDO")+" "+(String)request.getSession().getAttribute("PERS_SEGUNDOAPELLIDO")+"\n"
						+ "-- SOLICITADO POR: "+request.getParameter("SCRI_SOLICITANTE")+"\n"
						+ "-- FECHA DE REALIZACION: "+request.getParameter("SOLI_FECHAINICIO")+"\n"
						+ "-- EJECUTADO CON EL USUARIO: POSTGRES\n"
						+ "-- NOTA:"+nota+"\n"
						+ "------------------------------------------------------------------------------\n"
						+ "\n"
						+ "\n"
						+ "\n"
						+ "INSERT INTO GENERAL.EJECUCION VALUES('"+SCRI_NOMBRE+".sql', '"+paquete+"', NOW());\n";
			
		}else if( retorno != null && retorno.equals( "ORACLE" ) ){
			
			retorno = "-- "+SCRI_NOMBRE+".sql\n"
					+ "-- DESCRIPCION: "+descripcion+"\n"
					+ "-- IMPLEMENTADO POR: "+(String)request.getSession().getAttribute("PERS_PRIMERNOMBRE")+" "+(String)request.getSession().getAttribute("PERS_SEGUNDONOMBRE")+" "+(String)request.getSession().getAttribute("PERS_PRIMERAPELLIDO")+" "+(String)request.getSession().getAttribute("PERS_SEGUNDOAPELLIDO")+"\n"
					+ "-- SOLICITADO POR: "+request.getParameter("SCRI_SOLICITANTE")+"\n"
					+ "-- FECHA DE REALIZACION: "+request.getParameter("SOLI_FECHAINICIO")+"\n"
					+ "-- EJECUTADO CON EL USUARIO: ORACLE\n"
					+ "-- NOTA:"+nota+"\n"
					+ "------------------------------------------------------------------------------\n"
					+ "\n"
					+ "SET PAGES 50000\n"
					+ "SET LINES 1000\n"
					+ "SET ECHO OFF;\n"
					+ "SPOOL "+SCRI_NOMBRE+".log;\n"
					+ "\n"
					+ "\n"
					+ "\n"
					+ "PROMPT INSERCION EN LA TABLA DE EJECUCION;\n"
					+ "INSERT INTO GENERAL.EJECUCION VALUES('"+SCRI_NOMBRE+".sql', '"+paquete+"', SYSDATE);\n"
					+ "\n"
					+ "COMMIT;\n"
					+ "SPOOL OFF;\n"
					+ "SET ECHO ON;\n";
			
			
		}else{		

			retorno = "---No existe plabtilla para el SGBD seleccionado...";
			
		}
		
		return retorno;
	}
	
	public boolean controlDuplicidad ( HttpServletRequest request ) throws SQLException  {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		boolean retorno = false;
				
		Statement stmt = con.createStatement();
		
			String sql = "SELECT COUNT(1) valor "
						+ "FROM "
						+ "seguimiento.script scr "
						+ "WHERE scr.scri_nombre like substring( substring (scr.scri_nombre from 1 for 2) from '[0-9][0-9]+')||'%' "
						+ " AND scr.scri_nombre like '"+ request.getParameter( "SCRI_NOMBRE" ) + "%'";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
						
		if(rset.next()){			
			do{	
				
				if ( rset.getString("valor") != null && rset.getString("valor").equals( "0") ){
					retorno = true;
				}else{
					retorno = false;															
				}
			}while (rset.next());
				
		}
							
		stmt.close();		
		conn.cerrar(con);
						
		return retorno;
	}
	
	
	public String misActividades ( HttpServletRequest request, String filtro ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "<div class='acti_menu'><div class='acti_menu0'>Actividades</div>";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT" 
					+ "  sol.soli_id, "
					+ "  scr.scri_nombre, "
					+ "  emp.empr_sigla, "
					+ "  sol.soli_fechainicio, "
					+ "  sol.soli_fechafin, "
					+ "  substring(scr.scri_descripcion from 1 for 50) scri_descripcion "
					+ "FROM "
					+ "  seguimiento.script scr, "
					+ "  seguimiento.scriptpaquete spa, "
					+ "  seguimiento.scriptpaquetebasedatos spb, "
					+ "  seguimiento.basedatos bda, "
					+ "  seguimiento.empresa emp, "
					+ "  seguimiento.solicitud sol, "
					+ "  seguimiento.estadoproceso esp,"
					+ "  core.persona per "
					+ "where "+filtro+" AND "
					+ "  scr.scri_id = spa.scri_id AND"
					+ "  spb.scpa_id = spa.scpa_id AND"
					+ "  spb.spbd_id = sol.spbd_id AND"
					+ "  bda.bada_id = spb.bada_id AND"
					+ "  bda.empr_id = emp.empr_id AND"
					+ "  sol.espr_id = esp.espr_id AND"
					+ "  scr.pers_id = per.pers_id "
					+ "ORDER BY"
					+ "  sol.soli_fechainicio DESC,"
					+ "  sol.soli_fechafin DESC, "
					+ "  scr.scri_complejidad DESC"
					+ "  limit 50";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
				
		if(rset.next()){			
			do{	
				retorno = retorno+"<form action=\"./deta_actividades.jsp\" method=\"post\" name=\"visu_actividades\" id=\""+rset.getString("soli_id")+"\" target=\"acti_contenido\">"
				+"<div class='reg_menu' onclick=\"document.getElementById('"+rset.getString("soli_id")+"').submit();\">"
				+"<input type=\"hidden\" name=\"soli_id\" id=\"soli_id\" value=\""+rset.getString("soli_id")+"\">"
				+"<input type=\"hidden\" name=\"scri_nombre\" id=\"scri_nombre\" value=\""+rset.getString("scri_nombre")+"\">"
				+"<div class='reg_menu0'>"+rset.getString("empr_sigla")+" ::: "+rset.getString("scri_nombre")+"</div>"
				+"<div class='reg_menu1'>"+rset.getString("scri_descripcion")+"</div>"
				+"<div class='reg_menu2'>"+rset.getString("soli_fechainicio")+" -> "+rset.getString("soli_fechafin")+"</div>"
				+"</div>"
				+"</form>";	
			}while (rset.next());
				
		}
		
		retorno += "</div>";
		
		//System.out.println(retorno);
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
	
}
