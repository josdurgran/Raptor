package utileria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import core.conexion;
import seguimiento.observacion;



public class archivo extends HttpServlet{  

      

    	private static final long serialVersionUID = 1L;

    	    public String subir (HttpServletRequest request) throws ServletException, IOException 
    	          {  
    	            boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
    	           
    	            String soli_id = "";
    	            String scri_nombre = "";
    	            String insert ="";
    	            String sep = (String)request.getSession().getAttribute("SEP");
    	            String abc = "";
                    String fileName = "";
                    String obse_documento = "";
                    String suma = "";
    	            
    	            if (isMultipart){  
    	            	
    	            	boolean cnt_upload = true;
    	            	
    	                // Create a factory for disk-based file items  
    	                FileItemFactory factory = new DiskFileItemFactory();  
    	                // Create a new file upload handler  
    	                ServletFileUpload upload = new ServletFileUpload(factory);  
    	                try 
    	                {  
    	                    // Parse the request  
    	                    List items = upload.parseRequest(request);  
    	                    Iterator iterator = items.iterator(); 
    	                    
    	                    List itemscnt = upload.parseRequest(request);  
    	                    Iterator iteratorcnt = items.iterator(); 
    	                    
    	                    while (iteratorcnt.hasNext()){
    	                    	FileItem itemcnt = (FileItem) iteratorcnt.next();
    	                    	if (itemcnt.isFormField() && itemcnt.getFieldName().equals("funcion") && itemcnt.getString().equals("archivar") ){
    	                    
    	                    abc = "";
    	                    while (iterator.hasNext()){ 
    	                    	
    	                        FileItem item = (FileItem) iterator.next();  
    	                        if (!item.isFormField()){  
    	                        	
    	                            fileName = item.getName();
    	                            
    	                            if(fileName!="" ){
    	                                	                        
    	                            String root = (String)request.getSession().getAttribute("ALMACENAMIENTO")+sep+scri_nombre+sep;      	                          
    	                            File path = new File(root + "adjuntos"); 	                            
    	                               	                            
    	                            if (!path.exists()){ path.mkdirs(); }  
    	                            
    	                            File uploadedFile = new File(path + sep + fileName);
    	                                	                            
    	                            
    	                            String doc = "";
    	                            boolean act_documento = true;
    	                            String md5 = fileName;
    	                            
    	                            for(int version = 1; uploadedFile.exists(); version++ ){
    	                    	    act_documento = false;
    	                    	    String sTextoBuscado = ".";    	                    	    
    	                    	    
    	                    	    String extension = fileName;
    	                    	    String nombreFile = fileName;
    	                    	        	                    	    
    	                    	    int finalNombreFile = 0;   	                    	    

    	                    	    while (extension.indexOf(sTextoBuscado) > -1) {
    	                    	    	    	                    	    	  	                    	    	
    	                    	    	finalNombreFile += extension.indexOf( sTextoBuscado)+sTextoBuscado.length();
    	                    	    	extension = extension.substring(extension.indexOf( sTextoBuscado)+sTextoBuscado.length(),extension.length());
    	                    	    	   
    	                    	    }
    	                    	    
    	                    	    if(fileName.indexOf(sTextoBuscado) > -1){
    	                    	    uploadedFile = new File(path + sep + nombreFile.substring(0, finalNombreFile-1)+"_v"+version+"."+extension);
    	                    	    doc = nombreFile.substring(0, finalNombreFile-1)+"_v"+version+"."+extension+",";
    	                    	    md5 = nombreFile.substring(0, finalNombreFile-1)+"_v"+version+"."+extension;
    	                    	    }else{
    	                    	    	
        	                    	    uploadedFile = new File(path + sep + nombreFile+"_v"+version);
        	                    	    doc = nombreFile+"_v"+version+",";
        	                    	    md5 = nombreFile+"_v"+version;
    	                    	    	
    	                    	    }
    	                            
    	                            }               
    	                                	                            
    	                            obse_documento += doc;
    	                            System.out.println (md5);                    
    	    	                        	
    	    	                        	if(act_documento){
    	    	                        	obse_documento += item.getName()+",";
    	    	                        	}
    	    	                        	
    	    	                            item.write(uploadedFile);
    	    	        	    	    	
    	    	                            
    	    	                            File fichero = new File(path+sep+md5+".md5");
    	    	                            BufferedWriter bw;
    	    	                            
    	    	        	    	    	Date fecha = new Date();
    	    	        	    	    	
    	    	        	    	    	suma += this.getMD5Checksum(uploadedFile)+",";
    	    	        	    	    	
    	    	        	    	    	String contenidoArchivoMd5 = ";Archivo de Checksum usando MD5 \n;Generado con la aplicación Neptuno a partir de la versión NEPT.0.0.00.00\n;Fecha de creación: "+fecha+"\n;Universidad de Pamplona - Colombia\n"+this.getMD5Checksum(uploadedFile)+" *"+md5;
    	    	                            
    	    	        	    	        bw = new BufferedWriter(new FileWriter(fichero));
    	    	        	    	        bw.write(contenidoArchivoMd5);
    	    	                            bw.close();
    	    	                            
    	    	                            cnt_upload = false;
    	    	                            
    	    	                            if(uploadedFile.exists()){ 
    	    	                            	cnt_upload = true;
    	    	                            	if(!fichero.exists()){
    	    	                            		cnt_upload = false; 
    	    	                            		uploadedFile.delete();
    	    	                            		}
    	    	                            }else{    	    	                            
    	    	                            if(fichero.exists()){ 
    	    	                            	cnt_upload = false; 
    	    	                            	fichero.delete();
    	    	                            	}                         	
    	    	                            }
    	    	                            
    	    	                            
    	    	                                	    	                            
    	    	                        }else{  
    	    	                        
    	    	                        	obse_documento = "null";
    	    	                        	suma = "null";
    	    	                        			
    	    	                        }  	                            

    	                    }else{ 
    	                    	
    	                    	if(!item.getFieldName().equals("funcion") && !item.getFieldName().equals("observacion")){   	                        
    	                    		
    	                    		if(item.getFieldName().equals("soli_id")){
    	                    			abc += item.getString()+", ";
    	                    			soli_id = item.getString();
    	                    		}else{
    	                    			if(item.getFieldName().equals("tiob_id")){
    	                    				abc += item.getString()+", ";
    	                    				}else{
    	                    			
    	                    			if(item.getFieldName().equals("scri_nombre")){
    	                    				
    	                    				scri_nombre = item.getString();
    	                    				
    	                    			}else{
    	                    				
    	                    				if(item.getFieldName().equals("obse_descripcion")){
    	                    					
    	                    					abc += "'"+item.getString().replaceAll("'", "''")+"', ";
    	                    					
    	                    				}else{
    	                    					
    	                    					abc += "'"+item.getString()+"', ";
    	                    				}
    	                    			
    	                    			}
    	                    			}
    	                    		}
    	                        
    	                    	}
    	                    	
    	                    } 
    	                      
    	                        
    	                        
    	                }    	                    
    	                
    	                if(!obse_documento.equals("null")){
    	                	
    	                	obse_documento = "'"+obse_documento.substring(0, obse_documento.length()-1)+"'";
    	                	suma = "'"+suma.substring(0, suma.length()-1)+"'";
    	                }    
    	                    
    	                abc += obse_documento+", "+suma;
    	                    
    	                    	}else{ 
    	                    		//System.out.println("No se encuentra control de ingreso..."); 
    	                    	}
    	                    	}
    	                    
    	               
    	            } 
    	            catch (FileUploadException e) 
    	            {  
    	            System.out.println(e);  
    	            } 
    	            catch (Exception e) 
    	            {  
    	            System.out.println(e);  
    	            }  
    	                
        	            if(cnt_upload){
        	            	abc = "INSERT INTO seguimiento.observacion( soli_id, tiob_id, obse_fecha, obse_descripcion, obse_documento, obse_documentosuma, obse_estado, obse_registradopor, obse_fecharegistro) values ( "+abc+", 'Activa', '"+(String)request.getSession().getAttribute("PERS_ID")+"', now())";
        	            	observacion registro = new observacion();        	            	
        	            	insert = registro.ejecutarSql(abc);        	            	
            	            //System.out.println(abc);
            	            }
    	        }  
    	        else{  
    	            //System.out.println("Not Multipart");
    	            } 
    	            

    	            return soli_id+":::"+scri_nombre+":::"+insert;
    	            
    	      }
    	  
    	     	    
    	    
    	    public String getMD5Checksum(File archivo) {

    	        byte[] textBytes = new byte[1024];

    	        MessageDigest md = null;

    	        int read = 0;

    	        String md5 = null;

    	        try {

    	            InputStream is = new FileInputStream(archivo);

    	            md = MessageDigest.getInstance("MD5");

    	            while ((read = is.read(textBytes)) > 0) {

    	                md.update(textBytes, 0, read);

    	            }

    	            is.close();

    	            byte[] md5sum = md.digest();

    	            md5 = toHexadecimal(md5sum);

    	        } catch (FileNotFoundException e) {

    	        } catch (NoSuchAlgorithmException e) {

    	        } catch (IOException e) {

    	        }

    	    return md5;

    	    }
    	    
    	    
    	    private static String toHexadecimal(byte[] digest){

    	        String hash = "";

    	        for(byte aux : digest) {

    	            int b = aux & 0xff;

    	            if (Integer.toHexString(b).length() == 1) hash += "0";

    	            hash += Integer.toHexString(b);

    	        }
    	        return hash;
    	    } 
    	    
    	    public boolean generarDocumento( String ruta, String archivo, String extencion, String contenido){

                boolean validacion = false;
                
                if(ruta.length()>0 && archivo.length()>0 && ruta!=null && archivo!=null){
                
                try {                	
                 	
                File path = new File( ruta);
                	if (!path.exists()){ path.mkdirs(); }
                
                File fichero = new File( ruta+archivo+extencion);
    	    	BufferedWriter bw;       	    	
    	        bw = new BufferedWriter(new FileWriter(fichero));
    	        bw.write(contenido);
                bw.close();
                
                if(fichero.exists()){
                	
                	validacion = true;
                	
                }
                
				} catch (IOException e) {					
					e.printStackTrace();
				}
                
                }

    	        return validacion;
    	    }
    	    
    		public String script ( HttpServletRequest request, String SCRI_NOMBRE ){
    			
    			String retorno = "";
    			String paquete = "";
    			String descripcion = "";
    			String nota = "";
    			
    			Date now = new Date();
    			String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(now);
    			    			
    			try {
    			Statement stmt;
    			conexion conn = new conexion();
    			Connection con = conn.establecer();
				
				stmt = con.createStatement();
    			
    			String sql = "SELECT "
    					+ "UPPER( tsg.tisg_nombre ) MOTOR, "
    					+ "paq.paqu_nombre, "
    					+ "scr.scri_nombre "
    					+ "FROM " 
    					+ "  seguimiento.script scr,  "
    					+ "  seguimiento.scriptpaquete spa,  "
    					+ "  seguimiento.paquete paq,  "
    					+ "  seguimiento.productosgbd psg,  "
    					+ "  seguimiento.tiposgbd tsg "
    					+ "WHERE "
    					+ "  scr.scri_id = spa.scri_id AND "
    					+ "  spa.paqu_id = paq.paqu_id AND "
    					+ "  psg.prsg_id = paq.prsg_id AND "
    					+ "  tsg.tisg_id = psg.tisg_id AND "
    					+ "  scr.scri_nombre = '"+ request.getParameter( "scri_nombre" )+"'";
    			
    			//System.out.println(sql);
    			
    			ResultSet rset = stmt.executeQuery( sql ); 
    			
    			descripcion=request.getParameter("ajus_descripcion").replaceAll("\r", "");
    			descripcion=descripcion.replaceAll("\n", "\n-- ");
    			
    			nota=request.getParameter("ajus_nota").replaceAll("\r", "");
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
    							+ "-- SOLICITADO POR: "+request.getParameter( "scri_nombre" )+"\n"
    							+ "-- FECHA DE REALIZACION: "+fecha+"\n"
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
    						+ "-- SOLICITADO POR: "+request.getParameter( "scri_nombre" )+"\n"
    						+ "-- FECHA DE REALIZACION: "+fecha+"\n"
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
    			
				} catch (SQLException e) {
					
					e.printStackTrace();
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
    		
  
    		public String leerTxt ( String ruta ) {
    			
    			String retorno = "";
    			String ret = "";
    			
    		try {
    		if( new File(ruta).exists()){	
    			
    			BufferedReader bf = new BufferedReader(new FileReader( ruta ));
    		    	
						while ((ret = bf.readLine())!=null) {
							retorno += ret;
						}	
						
					bf.close();    
    		    	
    		    
    		}else{
    			
    			retorno = "ERROR ::: FILE-00";
    			
    		}
    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    			return retorno;
    		}
    	    

    	}

	
