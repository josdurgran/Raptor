package utileria;

import java.util.Timer;
import java.util.TimerTask;

public class planificador {
	
    public void iniciar( int inicio, int periodo, TimerTask tarea, Timer timer ){

    //Se crea un planificador que ejecuta la tarea pasados inicio segundos y luego periódicamente cada periodo segundos     
    timer.schedule ( tarea, inicio, periodo ) ;    
    
    }
    
    public void terminar( Timer timer ){
    	
    //Se finaliza la tarea
    timer.cancel();    
    
    }

}
    

