/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

import static curly.memory.AlgoritmoSJN.ObtenerProcesoConMenorTiempoRequerido;
import static curly.memory.Simulador.procesos_listos;
import static curly.memory.Simulador.tiempo_cpu;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodarte Fernández
 */
public class AlgoritmoHRN extends Simulador implements Runnable{
    
    private int procesos_atendidos = 0;
    public void run() {
        ComenzarHRN();
    }
    
     //Metodo para iniciar planificacion FIFO
    public  void ComenzarHRN(){
        System.out.println("*-*-*-*-*-*-*-*-*-  COMIENZA HRN *-*-*-*-*-*-*-*-*-*-*-*");
        //declaramos una variable para guradar el proceso mas corto
        Proceso proceso_con_mayor_prioridad;
        //Hacer:
        do{
            //Obtenemos el proceso con menor requerimiento de tiempo de la lista de listos
            proceso_con_mayor_prioridad = ObtenerProcesoConMayorPrioridad();
            //Si el objeto 'proceso_mas_corto' no es nulo, significa que hay procesos que 
            //aun no han sido terminados
            if(proceso_con_mayor_prioridad!=null){
                System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
                //Se asigna el tiempo de espera
                //proceso_mas_corto.setTiempoDeEspera(tiempo_cpu);
                //System.out.println("El proceso "+proceso_mas_corto.getNombre()+" - tuvo un tiempo de espera de "+tiempo_cpu);
                //Se cambia el estado del proceso
                proceso_con_mayor_prioridad.setEstado(Proceso.ESTADO_EN_EJECUCION);
                System.out.println("El proceso "+proceso_con_mayor_prioridad.getNombre()+" - Cambio estado a ejecucion");
                
                if(proceso_con_mayor_prioridad.getRecurso()!=0){
                    solicitarRecurso(proceso_con_mayor_prioridad);
                }
                
                while(proceso_con_mayor_prioridad.getProgreso()<100){
                    //Se aumenta una unidad de tiempo a el procesador
                    tiempo_cpu++;
                    ActualizarTiempoDeEsperaDeTodosLosProcesos(proceso_con_mayor_prioridad);
                    CalcularPrioridadDeTodosLosProcesos();
                    //Se actualiza el progreso del proceso
                    proceso_con_mayor_prioridad.actualizarProgreso();
                    //Tiempo de ejecucion
                    System.out.println("El proceso "+proceso_con_mayor_prioridad.getNombre()+" - tiene un tiempo de ejecucion de "+proceso_con_mayor_prioridad.getTiempo_de_ejecucion());
                    System.out.println("El proceso lleva un progreso de "+proceso_con_mayor_prioridad.getProgreso()+"%");
                    //si el progreso esta terminado se actualiza su estado
                    if (proceso_con_mayor_prioridad.getProgreso()==100){
                        //Se libera el recurso que estba utilizando en caso de que haya requerido alguno
                        if(proceso_con_mayor_prioridad.getRecurso()!=0 && proceso_con_mayor_prioridad.tieneSuRecursoAsignado() ){
                            liberarRecurso(proceso_con_mayor_prioridad);
                        }
                        //se cambia el estado del proceso
                        proceso_con_mayor_prioridad.setEstado(Proceso.ESTADO_TERMINADO);
                        System.out.println("El proceso "+proceso_con_mayor_prioridad.getNombre()+" - Cambio estado a terminado");
                        //se asigna el instante de fin
                        proceso_con_mayor_prioridad.setInstante_de_fin(tiempo_cpu);
                        System.out.println("El proceso "+proceso_con_mayor_prioridad.getNombre()+" - termino el en el momento "+tiempo_cpu);
                        //se calcula el tiempo de servicio
                        proceso_con_mayor_prioridad.calcularTiempoDeServicio();
                        System.out.println("El proceso "+proceso_con_mayor_prioridad.getNombre()+" - tuvo un tiempo de servicio de "+proceso_con_mayor_prioridad.getTiempo_de_servicio());
                        procesos_atendidos++;
                    }
                    try {
                        //Actaualizamos la tabla de procesos
                        InterfazG.actualizarTablaRes(procesos_listos);
                        //Actaualizamos la etiqueta de que proceso se esta atentiendo
                        InterfazG.actualizarLabelEjecutando(String.valueOf(proceso_con_mayor_prioridad.getPid()));
                        //Actaualizamos la barra de progreso
                        InterfazG.actulizarBarraDeProgreso(proceso_con_mayor_prioridad.getProgreso());
                        //Relentizamos (alargamos) el proceso un segundo
                        java.lang.Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AlgirtmoFIFO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        //Actaualizamos la tabla de procesos
        InterfazG.actualizarTablaRes(procesos_listos);
        //Mientras el proceso mas corto sea diferente de nulo
        }while(procesos_atendidos<5 || proceso_con_mayor_prioridad!=null);
        System.out.println("*-*-*-*-*-*-*-*-*-  Termina HRN *-*-*-*-*-*-*-*-*-*-*-*");
        InterfazG.algoritmoTerminado();
    } 
    
    
     //Metodo para obtener el proceso con mayor prioridad
    public static Proceso ObtenerProcesoConMayorPrioridad(){
        //variable auxiliar para almacenar el tiempo a mejorar
        double prioridad = 0;
        //variable auxiliar para almacenar la posicion del proceso a retornar
        int posicion_mayor_prioridad = -1;
        //variable auxiliar proceso para almacenar el objeto a retornar
        Proceso p = null;
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto}
        for(int i=0;i<procesos_listos.length;i++){
            if(procesos_listos[i]!=null){
                //comparamos el estado del proceso actual
                if(procesos_listos[i].getEstado() == Proceso.ESTADO_LISTO){
                    //Comparamos si el tiempo requerido por el proceso es mayor o igual
                    //al registrado anteriormente o si aun no se a registrado algun tiempo
                    if((prioridad<=procesos_listos[i].getPrioridad())||prioridad==0){
                        //Se asigna a menor_tiempo el tiempo del proceso que tiene menos requerimiento
                        prioridad=procesos_listos[i].getPrioridad();
                        //Se guarda la posicion del proceso
                        posicion_mayor_prioridad=i;
                    }
                }
            }
        }
        
        //Si la posicion es mayor a 0 significa que si se encontro un proceso
        //if(posicion_menor_tiempo>0){
           //se asigna a p el proceso que se encuentra en la posicion asignada
            try {
               p = procesos_listos[posicion_mayor_prioridad];
            } catch (Exception e) {
               p = null;
            }
        //}
        //Se retorna el objeto proceso
        return p;
    }    
    
}
