package core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import core.conexion;

public class permisos {
	
	public String generarMenuModulos( String pers_documento ) throws SQLException	{
				
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = con.createStatement();
		String sql = "select distinct per.pers_id, mod.modu_id, mod.modu_nombre, mod.modu_imagen, apl.apli_ubicacion, mod.modu_ubicacion, mod.modu_orden  " 
				+ "from core.persona per, "
				+ "core.permisos pem, "
				+ "core.aplicacionmodulofuncionalidadactividad ama, "
				+ "core.aplicacionmodulofuncionalidad amf, "
				+ "core.aplicacionmodulo amo, "
				+ "core.modulo mod, "
				+ "core.aplicacion apl "
				+ "where per.pers_id = pem.pers_id "
				+ "and pem.amfa_id = ama.amfa_id "
				+ "and ama.amfu_id = amf.amfu_id "
				+ "and amf.apmo_id = amo.apmo_id "
				+ "and amo.modu_id = mod.modu_id "
				+ "and amo.apli_id = apl.apli_id "
				+ "and per.pers_documento = '"+pers_documento+"' "
				+ "and mod.modu_estado = 'Activo' "
				+ "and (pem.perm_fechainicio <= now()::timestamp::date and (perm_fechafin is null or perm_fechafin >= now()::timestamp::date)) "
				+ "order by mod.modu_orden";
		System.out.print(sql);
		ResultSet rs = stmt.executeQuery(sql);
		String retorno = "";

		while (rs.next()) {
		
		retorno += "<form method=\"post\" name=\"modulos\" target=\"contenido\" action=\"./"+rs.getString("APLI_UBICACION")+rs.getString("MODU_UBICACION")+"\">"
				+ "<input name=\"MODU_NOMBRE\" type=\"submit\" class=\"modulos\" value=\""+rs.getString("MODU_NOMBRE")+"\" />"
				+ "<input name=\"MODU_ID\" type=\"hidden\" value=\""+rs.getString("MODU_ID")+"\" />"
				+ "<input name=\"PERS_ID\" type=\"hidden\" value=\""+rs.getString("PERS_ID")+"\" />"
				+ "</form>";
				
		}
		
		retorno +="<div class=\"limpiar\"></div>";
		
		stmt.close();		
		conn.cerrar(con);
		
		return retorno;
		
	}
	
	
	public String generarMenuFuncionalidad( String modu_id, String pers_id, boolean ubicacion ) throws SQLException	{
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = con.createStatement();
		
		String sql = "select distinct per.pers_id, mod.modu_id, mod.modu_nombre, fun.func_id, fun.func_nombre, fun.func_class, mod.modu_imagen, apl.apli_ubicacion, mod.modu_ubicacion, fun.func_ubicacion, fun.func_orden  "
				+ "from core.persona per, "
				+ "core.permisos pem, "
				+ "core.aplicacionmodulofuncionalidadactividad ama, "
				+ "core.aplicacionmodulofuncionalidad amf, "
				+ "core.aplicacionmodulo amo, "
				+ "core.funcionalidad fun, "
				+ "core.modulo mod, "
				+ "core.aplicacion apl "
				+ "where per.pers_id = pem.pers_id "
				+ "and pem.amfa_id = ama.amfa_id "
				+ "and ama.amfu_id = amf.amfu_id "
				+ "and amf.func_id = fun.func_id "
				+ "and amf.apmo_id = amo.apmo_id "
				+ "and amo.modu_id = mod.modu_id "
				+ "and amo.apli_id = apl.apli_id "
				+ "and per.pers_id = "+pers_id+" "
				+ "and mod.modu_id = "+modu_id+" "
				+ "and fun.func_estado = 'Activa' "
				+ "and (pem.perm_fechainicio <= now()::timestamp::date and (perm_fechafin is null or perm_fechafin >= now()::timestamp::date)) "
				+ "order by fun.func_orden ";
		
		//System.out.println(sql);
		
		ResultSet rs = stmt.executeQuery(sql);
		String retorno = "";		
		String path = "";
		
		

		if (rs.next()) {
			
			
		retorno += "<strong>Mis funcionalidades en el modulo "+rs.getString("MODU_NOMBRE")+".</strong><hr>";
		
				
		
		do{	
		if(ubicacion){path = "./"+rs.getString("FUNC_UBICACION"); }else { path = "../"; }
		retorno += "<form class=\"menu_funcionalidades\" method=\"post\" name=\"modulos\" target=\"contenido\" action=\""+path+"\">"
				+ "<input name=\"PERS_ID\" type=\"hidden\" value=\""+pers_id+"\" />"
				+ "<input name=\"MODU_ID\" type=\"hidden\" value=\""+rs.getString("MODU_ID")+"\" />"
				+ "<input name=\"FUNC_NOMBRE\" type=\"submit\" class=\""+rs.getString("FUNC_CLASS")+"\" value=\""+rs.getString("FUNC_NOMBRE")+"\" />"
				+ "<input name=\"FUNC_ID\" type=\"hidden\" value=\""+rs.getString("FUNC_ID")+"\" />"
				+ "</form>";
				
		}while(rs.next());
		
		}else{
			
			retorno += "<strong>No tengo funcionalidades asignadas para el modulo.</strong><hr>";	
			
		}
		
		retorno +="<div class=\"limpiar\"></div>";
		//System.out.println(retorno);
		stmt.close();		
		conn.cerrar(con);
		
		return retorno;
		
	}	
	
	
	public String generarMenuActividades( String modu_id, String func_id, String pers_id, boolean ubicacion ) throws SQLException	{
		
		conexion conn = new conexion();
		Connection con = conn.establecer();
		
		Statement stmt = con.createStatement();
		String sql ="select distinct per.pers_id, mod.modu_id, mod.modu_nombre, fun.func_id, fun.func_nombre, act.acti_id, act.acti_nombre, act.acti_class, mod.modu_imagen, apl.apli_ubicacion, mod.modu_ubicacion, fun.func_ubicacion, act.acti_ubicacion, act.acti_fichero, act.acti_orden  "
										+ "from core.persona per, "
										+ "core.permisos pem, "
										+ "core.aplicacionmodulofuncionalidadactividad ama, "
										+ "core.aplicacionmodulofuncionalidad amf, "
										+ "core.aplicacionmodulo amo, "
										+ "core.actividad act, "
										+ "core.funcionalidad fun, "
										+ "core.modulo mod, "
										+ "core.aplicacion apl "
										+ "where per.pers_id = pem.pers_id "
										+ "and pem.amfa_id = ama.amfa_id "
										+ "and ama.amfu_id = amf.amfu_id "
										+ "and amf.func_id = fun.func_id "
										+ "and ama.acti_id = act.acti_id "
										+ "and amf.apmo_id = amo.apmo_id "
										+ "and amo.modu_id = mod.modu_id "
										+ "and amo.apli_id = apl.apli_id "
										+ "and per.pers_id = "+pers_id+" "
										+ "and mod.modu_id = "+modu_id+" "
										+ "and fun.func_id = "+func_id+" "
										+ "and act.acti_estado = 'Activa' "
										+ "and (pem.perm_fechainicio <= now()::timestamp::date and (perm_fechafin is null or perm_fechafin >= now()::timestamp::date)) "
										+ "order by acti_orden ";
		//System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		
		String retorno = "";
		String path = "";

		if (rs.next()) {
			
		retorno += "<strong>Mis actividades en la funcionalidad "+rs.getString("FUNC_NOMBRE")+".</strong><hr>";
			
		do{
			
			if(ubicacion){ path = "./"+rs.getString("ACTI_UBICACION")+rs.getString("ACTI_FICHERO"); }else { path = "../"; }		
		
		retorno += "<form class=\"menu_actividades\" method=\"post\" name=\"modulos\" target=\"contenido\" action=\""+path+"\">"
				+ "<input name=\"PERS_ID\" type=\"hidden\" value=\""+pers_id+"\" />"
				+ "<input name=\"MODU_ID\" type=\"hidden\" value=\""+rs.getString("MODU_ID")+"\" />"
				+ "<input name=\"FUNC_ID\" type=\"hidden\" value=\""+rs.getString("FUNC_ID")+"\" />"
				+ "<input name=\"ACTI_NOMBRE\" type=\"submit\" class=\""+rs.getString("ACTI_CLASS")+"\" value=\""+rs.getString("ACTI_NOMBRE")+"\" />"
				+ "<input name=\"ACTI_ID\" type=\"hidden\" value=\""+rs.getString("ACTI_ID")+"\" />"
				+ "</form>";
		
		}while(rs.next());
		
		}else{
			
			retorno += "<strong>No tengo actividades asignadas para la funcionalidad.</strong><hr>";	
			
		}
				
				
		
		retorno +="<div class=\"limpiar\"></div>";
		
		stmt.close();		
		conn.cerrar(con);
		
		return retorno;
		
	}

}
