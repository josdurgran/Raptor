package utileria;

import java.util.Timer;
import java.util.TimerTask;

public class plan {
	
    public void tarea(){
	Timer timer = new Timer();

    TimerTask task = new TimerTask(){
        int tic = 0;

        @Override
        public void run(){
            System.out.println((tic++%2==0)?"TIC":"TOC");
        }
        };

        timer.schedule( task, 10, 100 );
        
        try{
        Thread.sleep(1000);
        }catch (Exception e){
        }
    task.cancel();
    }
    
}