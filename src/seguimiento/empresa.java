package seguimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import utileria.loadFile;
import utileria.sql;
import core.capitalOrigen;
import core.centroPoblado;
import core.conexion;
import core.departamento;
import core.pais;

public class empresa {
	

	public String generarCombo( String estado, String selected, String clase, String campo,String cambio ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||empr_id||'\" '||CASE WHEN empr_id = "+selected+" THEN 'selected' ELSE '' END||'>'||empr_sigla||' - '||empr_nombre||'</option>' AS ITEM "
				+ "FROM seguimiento.empresa "
				+ "WHERE empr_estado = 'Activa' "
				+ "and empr_estado = '"+estado+"' "
				+ "ORDER BY empr_sigla, empr_nombre";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
		
		retorno = retorno+"<select name=\""+campo+"\" id=\""+campo+"\" onchange=\""+cambio+"\" ><option value=\"\"></option>";
		
		if(rset.next()){			
			do{	
				retorno = retorno+rset.getString("ITEM");			
			}while (rset.next());
				
		}
				retorno = retorno+"</select> <label for=\"empr_id_M\"><span id=\"empr_id_M\" class=\"error\"></span></label>";			
		
		
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
	
	public String generarCombo1( String estado, String selected, String clase, String campo,String cambio ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT '<option value=\"'||empr_id||'\" '||CASE WHEN empr_id = "+selected+" THEN 'selected' ELSE '' END||'>'||empr_sigla||' - '||empr_nombre||'</option>' AS ITEM "
				+ "FROM seguimiento.empresa "
				+ "WHERE empr_estado = '"+estado+"' "
				+ "ORDER BY empr_sigla, empr_nombre";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
		
		retorno = retorno+"<select name=\""+campo+"\" id=\""+campo+"\" onchange=\""+cambio+"\" ><option value=\"\"></option>";
		
		if(rset.next()){			
			do{	
				retorno = retorno+rset.getString("ITEM");			
			}while (rset.next());
				
		}
				retorno = retorno+"</select>";			
		
		
		stmt.close();		
		conn.cerrar(con);		
		
		return retorno;
		
	}
	
	
	public String insertar( String secuencia, HttpServletRequest request ) {		
		
		String sql = "INSERT INTO seguimiento.empresa( empr_id, empr_nombre, empr_sigla, cepo_id, caor_id, empr_estado, empr_registradopor, empr_fechacambio) "
					+ " VALUES ("+secuencia+ ",'"+request.getParameter("EMPR_NOMBRE")+"'," + "'"+request.getParameter("EMPR_SIGLA")+"', " + request.getParameter("CEPO_ID")+", " +request.getParameter("CAOR_ID")+", " + "'"+request.getParameter("EMPR_ESTADO")+"', " + "'"+(String)request.getSession().getAttribute("PERS_ID")+"', NOW())";
		//System.out.println(sql);		
		return new sql().insertar(sql, "Empresa guardada corectamente.", "No fue posible guardar la empresa.", "mensaje_ok", "mensaje_err");
		
	}
	
	public String update( HttpServletRequest request ) {
		
		
		
		String sql = "UPDATE seguimiento.empresa"
				+"   SET empr_nombre='"+request.getParameter("EMPR_NOMBRE")+"', "
				+"   empr_sigla='"+request.getParameter("EMPR_SIGLA")+"', "
				+"   cepo_id='"+request.getParameter("CEPO_ID")+"', "
				+"   caor_id='"+request.getParameter("CAOR_ID")+"', "
				+"   empr_estado='"+request.getParameter("EMPR_ESTADO")+"', "
				+"   empr_registradopor='"+(String)request.getSession().getAttribute("PERS_ID")+"', "
				+"   empr_fechacambio=NOW() "
				+"	 WHERE empr_id="+request.getParameter("EMPR_ID");
		//System.out.println(sql);		
		return new sql().insertar(sql, "Empresa actualizada corectamente.", "No fue posible actualizar la empresa.", "mensaje_ok", "mensaje_err");
		
	}
	
	public String visualizar( String empr_id ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = null;
		ResultSet rset = null;

		String retorno = "";
		
		String sql = "SELECT * "
				+ "FROM seguimiento.empresa emp "
				+ "inner join core.centropoblado cep on cep.cepo_id = emp.cepo_id "
				+ "inner join core.municipio mun on cep.muni_id = mun.muni_id "
				+ "inner join core.departamento dep on mun.depa_id = dep.depa_id "
				+ "inner join core.pais pai on dep.pais_id = pai.pais_id "
				+ "inner join core.tipocentropoblado tcp on tcp.ticp_id = cep.ticp_id "
				+ "inner join core.capitalorigen cao on emp.caor_id = cao.caor_id "
				+ "WHERE emp.empr_id = "+empr_id+" ";
		
		//System.out.println(sql);
			
			stmt = con.createStatement();			
			rset = stmt.executeQuery( sql ); 	
		
		if(rset.next()){			
			do{	
				retorno = retorno+
							"<div class=\"all\">DENOMINACIÓN:"
							+ "<span class=\"small\">Nombre de la empresa</span>"
							+ "</div>"
							+ "<div class=\"allres\">"
							+rset.getString("EMPR_NOMBRE")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">SIGLA:"
							+ "<span class=\"small\">Sigla de la empresa</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("EMPR_SIGLA")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">NATURALEZA:"
							+ "<span class=\"small\">Privada/Pública</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("CAOR_NOMBRE")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">ESTADO:"
							+ "<span class=\"small\">Estado de la empresa</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("EMPR_ESTADO")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">PAÍS:"
							+ "<span class=\"small\">País de la empresa</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("PAIS_CODIGOISODOS")+" - "+rset.getString("PAIS_NOMBRE")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">DEPARTAMENTO:"
							+ "<span class=\"small\">Departamento/Estado</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("DEPA_NOMBRE")
							+"</div>"
							+ "<div class=\"spacer\"></div>"
							+"<div class=\"all\">CENTRO POBLADO:"
							+ "<span class=\"small\">Municipio</span>"
							+ "</div><div class=\"allres\">"
							+rset.getString("CEPO_NOMBRE")+" - "+rset.getString("TICP_SIGLA")
							+"</div>";			
			}while (rset.next());
				
		}
		//System.out.println(retorno);
		
		stmt.close();
		conn.cerrar(con);
		
		return retorno;			
		
	}
	
public String menuEmpresa ( HttpServletRequest request, String filtro ) throws SQLException {
		
		conexion conn = new conexion();
		Connection con = conn.establecer();

		String retorno = "<div class='acti_menu'><div class='acti_menu0'> Seleccione una empresa</div>";
		
		Statement stmt = con.createStatement();
		
		String sql = "SELECT" 
					+ " emp.empr_id, emp.empr_sigla, emp.empr_nombre, cao.caor_nombre, emp.empr_estado "
					+ "FROM "
					+ "seguimiento.empresa emp inner join core.capitalorigen cao on emp.caor_id = cao.caor_id "
					+ "where "+filtro+" "
					+ "ORDER BY "
					+ "emp.empr_estado, emp.empr_sigla "
					+ "limit 50";
		
		//System.out.println(sql);
		
		ResultSet rset = stmt.executeQuery( sql ); 
				
		if(rset.next()){			
			do{	
				retorno = retorno+"<form action=\"./deta_empresa.jsp\" method=\"post\" name=\"visu_empr_id\" id=\""+rset.getString("empr_id")+"\" target=\"empr_contenido\">"
				+"<div class='reg_menu' onclick=\"document.getElementById('"+rset.getString("empr_id")+"').submit();\">"
				+"<input type=\"hidden\" name=\"EMPR_ID\" id=\"EMPR_ID\" value=\""+rset.getString("empr_id")+"\">"
				+"<div class='reg_menu0'>"+rset.getString("empr_sigla")+"::::"+rset.getString("empr_estado")+"</div>"
				+"<div class='reg_menu1'>"+rset.getString("empr_nombre")+"</div>"
				+"<div class='reg_menu2'>"+rset.getString("caor_nombre")+"</div>"
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

public String editar( String empr_id ) throws SQLException {
	
	conexion conn = new conexion();
	Connection con = conn.establecer();
	
	Statement stmt = null;
	ResultSet rset = null;

	String retorno = "";
	
	String sql = "SELECT * "
			+ "FROM seguimiento.empresa emp "
			+ "inner join core.centropoblado cep on cep.cepo_id = emp.cepo_id "
			+ "inner join core.municipio mun on cep.muni_id = mun.muni_id "
			+ "inner join core.departamento dep on mun.depa_id = dep.depa_id "
			+ "inner join core.pais pai on dep.pais_id = pai.pais_id "
			+ "inner join core.tipocentropoblado tcp on tcp.ticp_id = cep.ticp_id "
			+ "inner join core.capitalorigen cao on emp.caor_id = cao.caor_id "
			+ "WHERE emp.empr_id = "+empr_id+" ";
	
	//System.out.println(sql);
		
		stmt = con.createStatement();			
		rset = stmt.executeQuery( sql ); 	
	
	if(rset.next()){			
		do{
			
			retorno = retorno
					+"<input type=\"hidden\" name=\"EMPR_ID\" id=\"EMPR_ID\" value=\""+rset.getString("EMPR_ID")+"\" >"
					+ "<label for=\"EMPR_NOMBRE\">*DENOMINACIÓN"
					+"<span class=\"small\">Nombre de la empresa</span>"
					+"<span id=\"EMPR_NOMBRE_M\"></span>"
					+"</label>"
					+"<input value=\""+rset.getString("EMPR_NOMBRE")+"\" class=\"\" type=\"text\" name=\"EMPR_NOMBRE\" id=\"EMPR_NOMBRE\" maxlength=\"200\"  onkeypress=\"document.getElementById('EMPR_NOMBRE_M').style.display='none';\">"
					+""
					+"<label for=\"EMPR_SIGLA\">*SIGLA"
					+"<span class=\"small\">Sigla de la empresa</span>"
					+"<span id=\"EMPR_SIGLA_M\"></span>"
					+"</label>"
					+"<input value=\""+rset.getString("EMPR_SIGLA")+"\" class=\"\" type=\"text\" name=\"EMPR_SIGLA\" id=\"EMPR_SIGLA\" maxlength=\"50\" onkeypress=\"document.getElementById('EMPR_SIGLA_M').style.display='none';\">"
					+""
					+"<label for=\"CAOR_ID\">*NATURALEZA"
					+"<span class=\"small\">Privada/Pública</span>"
					+"<span id=\"CAOR_ID_M\"></span>"
					+"</label>"
					+new capitalOrigen().generarCombo( "Activo", rset.getString("CAOR_ID"), "", "CAOR_ID", "document.getElementById('CAOR_ID_M').style.display='none';" )
					+"<label for=\"EMPR_ESTADO\">*ESTADO"
					+"<span class=\"small\">Estado de la empresa</span>"
					+"<span id=\"EMPR_ESTADO_M\"></span>"
					+"</label>"
					+"<select name=\"EMPR_ESTADO\" id=\"EMPR_ESTADO\" onchange=\"document.getElementById('EMPR_ESTADO_M').style.display='none';\">"
					+"<option value=\"\"></option>"
					+"<option value=\"Activa\" ";
						if(rset.getString("EMPR_ESTADO") !=  null && rset.getString("EMPR_ESTADO").equals("Activa")){ retorno= retorno+ "selected";}
						retorno= retorno+">Activa</option>"
					+"<option value=\"Inactiva\" ";
						if(rset.getString("EMPR_ESTADO") !=  null && rset.getString("EMPR_ESTADO").equals("Inactiva")){ retorno= retorno+ "selected";}
						retorno= retorno+">Inactiva</option>"
					+"</select>"
					+ "<label for=\"PAIS_ID\">*PAÍS"
					+"<span class=\"small\">País de la empresa</span>"
					+"<span id=\"PAIS_ID_M\"></span>"
					+"</label>";
						
						String depa_funciones = "depa";
						String cepo_funciones = "cepo";		
						String pais ="'../utileria/load_departamento.jsp?cepo_funciones="+cepo_funciones+"&depa_id="+rset.getString("DEPA_ID")+"&pais_id='+document.getElementById(\"PAIS_ID\").value";
						String departamento ="'../utileria/load_centroPoblado.jsp?cepo_funciones="+cepo_funciones+"&cepo_id="+rset.getString("CEPO_ID")+"&depa_id='+document.getElementById(\"DEPA_ID\").value";
					
					retorno = retorno
					+new pais().generarCombo( "Activo", rset.getString("PAIS_ID"), "", "PAIS_ID", "document.getElementById('CEPO_ID').selectedIndex = 0; document.getElementById('PAIS_ID_M').style.display='none'; "+depa_funciones+"Carga('GET', "+depa_funciones+" )" )
					+new loadFile().cargaAjaxFuncion(depa_funciones, depa_funciones, pais)
					+new loadFile().cargaAjaxFuncion(cepo_funciones, cepo_funciones, departamento)
					+"<label for=\"DEPA_ID\">*DEPARTAMENTO"
					+"<span class=\"small\">Departamento/Estado</span>"
					+"<span id=\"DEPA_ID_M\"></span>"
					+"</label>"
					+"<div id=\""+depa_funciones+"\">"
					+new departamento().generarCombo(rset.getString("PAIS_ID"), "Activo", rset.getString("DEPA_ID"), "", "DEPA_ID", "document.getElementById('DEPA_ID_M').style.display='none'; "+cepo_funciones+"Carga('GET', '"+cepo_funciones+"' );")
					+"</div>"
					+"<label for=\"CEPO_ID\">*CENTRO POBLADO"
					+"<span class=\"small\">Municipio</span>"
					+"<span id=\"CEPO_ID_M\"></span>"
					+"</label>"
					+"<div id=\""+cepo_funciones+"\">"
					+new centroPoblado().generarCombo(rset.getString("DEPA_ID"), "Activo", rset.getString("CEPO_ID"), "", "CEPO_ID", "document.getElementById('CEPO_ID_M').style.display='none';")
					+"</div>"
					+"<div class=\"spacer\"></div>"
					+"<button type=\"submit\" name=\"accion\" value=\"guardar\">Guardar</button>"
					+"<div class=\"spacer\"></div>";			
		}while (rset.next());
			
	}
	//System.out.println(retorno);
	
	stmt.close();
	conn.cerrar(con);
	
	return retorno;			
	
}
	
	
}
