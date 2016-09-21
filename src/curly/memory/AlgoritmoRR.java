/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author elias
 */
public class AlgoritmoRR extends Simulador implements Runnable {

    //Metodo que inicia el procedimiento de manera asincrona (paralela por medio de un hilo)
    @Override
    public void run() {
        //Comienza fifo
        ComenzarRR();
    }
    
    
     //Metodo para iniciar planificacion RR
    public  void ComenzarRR(){
        System.out.println("*-*-*-*-*-*-*-*-*-  COMIENZA RR *-*-*-*-*-*-*-*-*-*-*-*");
        int contador;
        while(!terminoRR()){
            
            for(int i=0;i<procesos_listos.length;i++){
                contador=1;
                //Verificamos que en la posiciin i se encuentre un objeto
                if (procesos_listos[i]!=null){
                    while(contador<=quantum){
                        //Si el proceso no tiene progreso ni tiempo de espera
                        //es la primera vez que va a ser ejecutado
                        if(procesos_listos[i].getProgreso()==0&&procesos_listos[i].getTiempoDeEspera()==0){
                            //Asignamos el instante de llegada al proceso
                            procesos_listos[i].setInstanteDeLlegada(tiempo_cpu);
                            System.out.println("El proceso "+procesos_listos[i].getNombre()+" - tiene un tiempo te espera de "+tiempo_cpu);

                            procesos_listos[i].setEstado(Proceso.ESTADO_EN_EJECUCION);
                            System.out.println("El proceso "+procesos_listos[i].getNombre()+" - Cambio estado a ejecucion");
                            
                            if(procesos_listos[i].getRecurso()!=0){
                                if(!procesos_listos[i].tieneSuRecursoAsignado()){
                                   solicitarRecurso(procesos_listos[i]); 
                                }
                            }
                            
                            if(procesos_listos[i].tieneSuRecursoAsignado()||procesos_listos[i].getRecurso()==0){
                                //El proceso puede entrar a trabajar
                                procesos_listos[i].setEstado(Proceso.ESTADO_EN_EJECUCION);
                                actualizarProgresoDelProceso(procesos_listos[i]);
                                contador++;
                                if(procesos_listos[i].getEstado()==Proceso.ESTADO_TERMINADO)
                                    //romper el ciclo del contador
                                    contador = 99;
                            }else{
                                //Se bloquea l proceso
                                bloquearProceso(procesos_listos[i]);
                                //Para romper conducion y avance al siguiente proceso
                                contador = 99;
                            }
                            
                        }else{
                            if(procesos_listos[i].getEstado()!=Proceso.ESTADO_TERMINADO){
                                //-----------------------------------------------
                                if(procesos_listos[i].tieneSuRecursoAsignado()||procesos_listos[i].getRecurso()==0){
                                    //El proceso continuar su trabajo
                                    procesos_listos[i].setEstado(Proceso.ESTADO_EN_EJECUCION);
                                    actualizarProgresoDelProceso(procesos_listos[i]);
                                    contador++;
                                    if(procesos_listos[i].getEstado()==Proceso.ESTADO_TERMINADO)
                                        //romper el ciclo del contador
                                        contador = 99;
                                }else{
                                    //Esta en ejecucion o bloqueado y lo esta solicitando
                                    if(procesos_listos[i].getRecurso()!=0){
                                        if(!procesos_listos[i].tieneSuRecursoAsignado()){
                                           solicitarRecurso(procesos_listos[i]); 
                                        }
                                    }
                                    if(procesos_listos[i].tieneSuRecursoAsignado()||procesos_listos[i].getRecurso()==0){
                                        //El proceso puede entrar a trabajar
                                        procesos_listos[i].setEstado(Proceso.ESTADO_EN_EJECUCION);
                                        actualizarProgresoDelProceso(procesos_listos[i]);
                                        contador++;
                                        if(procesos_listos[i].getEstado()==Proceso.ESTADO_TERMINADO)
                                            //romper el ciclo del contador
                                            contador = 99;
                                    }else{
                                        //Se bloquea l proceso
                                        bloquearProceso(procesos_listos[i]);
                                        //Para romper conducion y avance al siguiente proceso
                                        contador = 99;
                                    }
                                }
                            }else{
                                contador = 99;
                            }
                        }
                    }
                    if(contador>quantum){
                        if(procesos_listos[i].getEstado()!=Proceso.ESTADO_LISTO&&procesos_listos[i].getEstado()!=Proceso.ESTADO_TERMINADO&&procesos_listos[i].getEstado()!=Proceso.ESTADO_BLOQUEADO){
                            procesos_listos[i].setEstado(Proceso.ESTADO_LISTO);
                        }
                        continue;   
                    }
                }else{
                    continue;
                }
            }
        
        }
        System.out.println("*-*-*-*-*-*-*-*-*-  Termina RR *-*-*-*-*-*-*-*-*-*-*-*");
        JOptionPane.showMessageDialog(null,"Procedimiento por RR Terminado");
        InterfazG.algoritmoTerminado();
    }
    
    public void actualizarProgresoDelProceso(Proceso p){
        if(p.getProgreso()<100){
            //Se aumenta una unidad de tiempo a el procesador
            tiempo_cpu++;
            ActualizarTiempoDeEsperaDeTodosLosProcesos(p);
            //Se actualiza el progreso del proceso
            p.actualizarProgreso();
            System.out.println("El proceso "+p.getNombre()+" - tiene un tiempo de ejecucion de "+p.getTiempo_de_ejecucion());
            System.out.println("El proceso lleva un progreso de "+p.getProgreso()+"%");
            //si el progreso esta terminado se actualiza su estado
            if (p.getProgreso()==100){
                //Se libera el recurso que estba utilizando en caso de que haya requerido alguno
                if(p.getRecurso()!=0 && p.tieneSuRecursoAsignado() ){
                    liberarRecurso(p);
                }
                //se cambia el estado del proceso a terminado
                p.setEstado(Proceso.ESTADO_TERMINADO);
                System.out.println("El proceso "+p.getNombre()+" - Cambio estado a terminado");
                //se asigna el instante de fin
                p.setInstante_de_fin(tiempo_cpu);
                System.out.println("El proceso "+p.getNombre()+" - termino el en el momento "+tiempo_cpu);
                //se calcula el tiempo de servicio del proceso
                p.calcularTiempoDeServicio();
                System.out.println("El proceso "+p.getNombre()+" - tuvo un tiempo de servicio de "+p.getTiempo_de_servicio());
            }
            try {
                //Actaualizamos la tabla de procesos
                InterfazG.actualizarTablaRes(procesos_listos);
                //Actaualizamos la barra de progreso
                InterfazG.actulizarBarraDeProgreso(p.getProgreso());
                //Relentizamos (alargamos) el proceso un segundo
                java.lang.Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AlgoritmoRR.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //bloquea a un proceso
    public void bloquearProceso(Proceso p){
        p.setEstado(Proceso.ESTADO_BLOQUEADO);
    }

    public boolean terminoRR(){
        boolean terminado = true;
        for(int i=0;i<procesos_listos.length;i++){
            if(procesos_listos[i]!=null){
                //Si el proceso tiene estado de listo aun no ha terminado
                if(procesos_listos[i].getEstado()!=Proceso.ESTADO_TERMINADO){
                    terminado = false;
                }
            }
        }
        return terminado;
    }
    
    public void verificarProcesosBloqueados(){
        for(int i=0;i<procesos_listos.length;i++){
            if(procesos_listos[i]!=null){
                //Si el proceso tiene estado de listo aun no ha terminado
                if(procesos_listos[i].getEstado()==Proceso.ESTADO_BLOQUEADO){
                    if(recursos[procesos_listos[i].getRecurso()-1].estaDisponible()){
                        procesos_listos[i].setEstado(Proceso.ESTADO_LISTO);
                    }
                }
            }
        }
    }
    
}
