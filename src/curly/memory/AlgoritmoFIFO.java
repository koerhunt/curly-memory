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
 * @author HDEZ. OCHOA D. SEBASTIAN
 */
public class AlgoritmoFIFO extends Simulador implements Runnable{
    
    
    //Metodo que inicia el procedimiento de manera asincrona (paralela por medio de un hilo)
    @Override
    public void run() {
        //Comienza a trabajar el algoritmo fifo
        ComenzarFifo();
    }
    
     //Metodo para iniciar planificacion FIFO
    public  void ComenzarFifo(){
        
        System.out.println("*-*-*-*-*-*-*-*-*-  COMIENZA FIFO *-*-*-*-*-*-*-*-*-*-*-*");
        
        int procesos_atendidos = 0;
        int contador_progreso = 0;
        
        Proceso p;
        
        while(procesos_atendidos<5||!Simulador.procesos_listos.estaVacia()||!Simulador.procesos_bloqueados.estaVacia()
                ||!Simulador.suspendidos_listos.estaVacia()||!Simulador.suspendidos_bloqueados.estaVacia()||!Simulador.proceso_en_ejecucion.estaVacia()){
            
            try {
                InterfazG.actualizarAmbienteGrafico();
                Thread.sleep(velocidad);
            } catch (InterruptedException ex) {
                Logger.getLogger(AlgoritmoFIFO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            p = Simulador.procesos_listos.extraerPrimerProceso();
            
            if(p!=null){
                //Checamos y hacemos cambios de estado
                p.setEstado(Proceso.ESTADO_EN_EJECUCION);
                
                try {                    
                    InterfazG.actualizarAmbienteGrafico();
                    Thread.sleep(velocidad);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AlgoritmoFIFO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                 
                //Si el progreso es igual a cero entonces el proceso acaba de llegar
                if(p.getProgreso()==0){
                    //Asignamos el instante de llegada al proceso
                    p.setInstanteDeLlegada(tiempo_cpu);
                    System.out.println("El proceso "+p.getNombre()+" - tiene un tiempo te espera de "+tiempo_cpu);
                }
                
                //Si requiere de algun recurso E/S
                if(p.requiereEntradaSalida()){
                    //Se solicita el recurso
                    solicitarRecurso(p);
                    contador_progreso = 0;
                }
                
                while(!p.requiereEntradaSalida()||contador_progreso<=2){
                    
                    if(p.requiereEntradaSalida()){
                        contador_progreso++;
                    }
                    
                    //Se aumenta una unidad de tiempo a el procesador
                    tiempo_cpu++;
                    p.actualizarProgreso();
                    
                    System.out.println("El proceso "+p.getNombre()+" - tiene un tiempo de ejecucion de "+p.getTiempo_de_ejecucion());
                    System.out.println("El proceso lleva un progreso de "+p.getProgreso()+"%");
                    
                    if (p.getProgreso()>=100){
                        p.setEstado(Proceso.ESTADO_TERMINADO);
                        procesos_atendidos++;

                        //se asigna el instante de fin
                        p.setInstante_de_fin(tiempo_cpu);
                        System.out.println("El proceso "+p.getNombre()+" - termino el en el momento "+tiempo_cpu);
                        //se calcula el tiempo de servicio del proceso
                        p.calcularTiempoDeServicio();
                        System.out.println("El proceso "+p.getNombre()+" - tuvo un tiempo de servicio de "+p.getTiempo_de_servicio());
                        break;
                    }
                    
                    try {                    
                        InterfazG.actualizarAmbienteGrafico();
                        Thread.sleep(velocidad);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AlgoritmoFIFO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                if(p.getProgreso()<100&&(p.requiereEntradaSalida() || p.entraraASuspencion())){
                    actualizarInterface(p,true);
                }
                
            }else{
                try {
                    Thread.sleep(velocidad);
                    Simulador.actualizarDatos();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AlgoritmoFIFO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("*-*-*-*-*-*-*-*-*-  Termina FIFO *-*-*-*-*-*-*-*-*-*-*-*");
        try {
            Thread.sleep(500);
            InterfazG.actualizarAmbienteGrafico();
        } catch (InterruptedException ex) {
            Logger.getLogger(AlgoritmoFIFO.class.getName()).log(Level.SEVERE, null, ex);
        }
        InterfazG.algoritmoTerminado();
    }    
    
}
