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
public class CallBackRestaurarProcesoBloqueado extends Simulador implements Runnable{

    @Override
    public void run() {
        try {
            java.lang.Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AlgoritmoFIFO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!suspendidos_bloqueados.estaVacia()){
            suspendidos_bloqueados.extraerPrimerProceso().setEstado(Proceso.ESTADO_BLOQUEADO);
        }   
    }
    
}
