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
            Simulador.introducirProcesoALista(new Proceso(Simulador.secuencia_id, "Google chrome",0, 18, 3), procesos_listos);
            //Incrementa la secuencia del identificador del proceso
            Simulador.secuencia_id++;
            //Espera 1000 milisegundos
            java.lang.Thread.sleep(1000);
            
            //Para los demas procesos son iguales
            Simulador.introducirProcesoALista(new Proceso(Simulador.secuencia_id, "Microsoft Word",0, 8, 3), procesos_listos);
            Simulador.secuencia_id++;
            java.lang.Thread.sleep(4000);
            Simulador.introducirProcesoALista(new Proceso(Simulador.secuencia_id, "Paint",0, 5, 1), procesos_listos);
            Simulador.secuencia_id++;
            java.lang.Thread.sleep(7000);
            Simulador.introducirProcesoALista(new Proceso(Simulador.secuencia_id, "Spotify",0, 15, 0), procesos_listos);
            Simulador.secuencia_id++;
            java.lang.Thread.sleep(1500);
            Simulador.introducirProcesoALista(new Proceso(Simulador.secuencia_id, "Avast",0, 9, 4), procesos_listos);
            Simulador.secuencia_id++;
        } catch (InterruptedException ex) {
            Logger.getLogger(CrearProcesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}