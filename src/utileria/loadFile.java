package utileria;


public class loadFile {
	
    public String cargaAjax( ) {

    	String retorno = "";
    	
    	retorno = "<script type=\"text/javascript\">"
    			+ ""
    			+ "var READY_STATE_UNINITIALIZED=0;"
    			+ "var READY_STATE_LOADING=1;"
    			+ "var READY_STATE_LOADED=2;"
    			+ "var READY_STATE_INTERACTIVE=3;"
    			+ "var READY_STATE_COMPLETE=4;"
    			+ ""
    			+ "var peticion_http;"
    			+ ""
    			+ "function inicializa_xhr() {"
    			+ "  if(window.XMLHttpRequest) {"
    			+ "    return new XMLHttpRequest();"
    			+ "  }"
    			+ "  else if(window.ActiveXObject) {"
    			+ "    return new ActiveXObject(\"Microsoft.XMLHTTP\");"
    			+ "  }"
    			+ "}"
    			+ ""
    			+ "</script>"; 
    	
    	return retorno;
    }
    
    
    public String cargaAjaxFuncion( String funcion, String devolver, String variables ) {
    	
    	String retorno = "";
    	
    	retorno = "<script type=\"text/javascript\">"
    			+ ""
    			+ "function "+funcion+"Carga( metodo, "+funcion+") {"
    			+ ""
    			+ "  peticion_http = inicializa_xhr();"
    			+ "  if(peticion_http) {"
    			+ "    peticion_http.open(metodo, "+variables+", false);"
    			+ "    peticion_http.send(null);"
    			+ "  if(peticion_http.readyState == READY_STATE_COMPLETE) {"
    			+ "    if(peticion_http.status == 200) {"
    			+ "      document.getElementById(\""+devolver+"\").innerHTML = peticion_http.responseText;"
    			+ "    }else{ document.getElementById(\""+devolver+"\").innerHTML = 'ERROR-000::Ha ocurrido un error en la carga del archivo.'; }"
    			+ "  }"
    			+ "  }"
    			+ "}"
    			+ ""
    			+ "</script>"; 
    	
    	//System.out.println(retorno);
    	
    	return retorno;
    }

}
