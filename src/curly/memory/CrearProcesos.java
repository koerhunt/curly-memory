/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elias
 */
public class CrearProcesos extends Simulador implements Runnable{

    //Este metodo crea 5 procesos por defectos conforme avanza el tiempo
    @Override
    public void run() {
        try {
            //Crea un proceso y lo agrega a la lista de listos
            Simulador.procesos_listos.agregarProceso(new Proceso(Simulador.secuencia_id, "Google chrome",10,true,false));
            //Incrementa la secuencia del identificador del proceso
            Simulador.secuencia_id++;
            //Espera 1000 milisegundos
            java.lang.Thread.sleep(1000);
            
            //Para los demas procesos son iguales
            Simulador.procesos_listos.agregarProceso(new Proceso(Simulador.secuencia_id, "Microsoft Word",4,false,false));
            Simulador.secuencia_id++;
            java.lang.Thread.sleep(4000);
            
            Simulador.procesos_listos.agregarProceso(new Proceso(Simulador.secuencia_id, "Paint",5, true,true));
            Simulador.secuencia_id++;
            java.lang.Thread.sleep(7000);
            
            Simulador.procesos_listos.agregarProceso(new Proceso(Simulador.secuencia_id, "Spotify",6,false,true));
            Simulador.secuencia_id++;
            java.lang.Thread.sleep(1500);
            
            Simulador.procesos_listos.agregarProceso(new Proceso(Simulador.secuencia_id, "Avast",9,true,false));
            Simulador.secuencia_id++;
            
        } catch (InterruptedException ex) {
            Logger.getLogger(CrearProcesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
