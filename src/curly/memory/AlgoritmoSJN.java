/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

import static curly.memory.Simulador.procesos_listos;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AlgoritmoSJN  extends Simulador implements Runnable {

    @Override
    public void run() {
        ComenzarSJN();
    }    
    
    //Metodo para iniciar planificacion SJN   
    public static void ComenzarSJN(){
        System.out.println("*-*-*-*-*-*-*-*-*-  COMIENZA SJN *-*-*-*-*-*-*-*-*-*-*-*");
        //declaramos una variable para guradar el proceso mas corto
        Proceso proceso_mas_corto;
        //Hacer:
        do{
            //Obtenemos el proceso con menor requerimiento de tiempo de la lista de listos
            proceso_mas_corto = ObtenerProcesoConMenorTiempoRequerido();
            //Si el objeto 'proceso_mas_corto' no es nulo, significa que hay procesos que 
            //aun no han sido terminados
            if(proceso_mas_corto!=null){
                System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
                //Se asigna el tiempo de espera
                proceso_mas_corto.setTiempoDeEspera(tiempo_cpu);
                System.out.println("El proceso "+proceso_mas_corto.getNombre()+" - tuvo un tiempo de espera de "+tiempo_cpu);
                //Se cambia el estado del proceso
                proceso_mas_corto.setEstado(Proceso.ESTADO_EN_EJECUCION);
                System.out.println("El proceso "+proceso_mas_corto.getNombre()+" - Cambio estado a ejecucion");
                
                if(proceso_mas_corto.getRecurso()!=0){
                    solicitarRecurso(proceso_mas_corto);
                }
                
                while(proceso_mas_corto.getProgreso()<100){
                    //Se aumenta una unidad de tiempo a el procesador
                    tiempo_cpu++;
                    //Se actualiza el progreso del proceso
                    proceso_mas_corto.actualizarProgreso();
                    //Tiempo de ejecucion
                    System.out.println("El proceso "+proceso_mas_corto.getNombre()+" - tiene un tiempo de ejecucion de "+proceso_mas_corto.getTiempo_de_ejecucion());
                    System.out.println("El proceso lleva un progreso de "+proceso_mas_corto.getProgreso()+"%");
                    //si el progreso esta terminado se actualiza su estado
                    if (proceso_mas_corto.getProgreso()==100){
                        //Se libera el recurso que estba utilizando en caso de que haya requerido alguno
                        if(proceso_mas_corto.getRecurso()!=0 && proceso_mas_corto.tieneSuRecursoAsignado() ){
                            liberarRecurso(proceso_mas_corto);
                        }
                        //se cambia el estado del proceso
                        proceso_mas_corto.setEstado(Proceso.ESTADO_TERMINADO);
                        System.out.println("El proceso "+proceso_mas_corto.getNombre()+" - Cambio estado a terminado");
                        //se asigna el instante de fin
                        proceso_mas_corto.setInstante_de_fin(tiempo_cpu);
                        System.out.println("El proceso "+proceso_mas_corto.getNombre()+" - termino el en el momento "+tiempo_cpu);
                        //se calcula el tiempo de servicio
                        proceso_mas_corto.calcularTiempoDeServicio();
                        System.out.println("El proceso "+proceso_mas_corto.getNombre()+" - tuvo un tiempo de servicio de "+proceso_mas_corto.getTiempo_de_servicio());
                    }
                    try {
                        //Actaualizamos la tabla de procesos
                        InterfazG.actualizarTablaRes(procesos_listos);
                        //Actaualizamos la barra de progreso
                        InterfazG.actulizarBarraDeProgreso(proceso_mas_corto.getProgreso());
                        //Relentizamos (alargamos) el proceso un segundo
                        java.lang.Thread.sleep(700);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AlgirtmoFIFO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        //Actaualizamos la tabla de procesos
        InterfazG.actualizarTablaRes(procesos_listos);
        //Mientras el proceso mas corto sea diferente de nulo
        }while(true);
        //System.out.println("*-*-*-*-*-*-*-*-*-  Termina SJN *-*-*-*-*-*-*-*-*-*-*-*");
        //InterfazG.algoritmoTerminado();
        //JOptionPane.showMessageDialog(null,"Procedimiento por SJN Terminado");
    }
    
    //Metodo para obtener el proceso con menor tiempo requerido
    public static Proceso ObtenerProcesoConMenorTiempoRequerido(){
        //variable auxiliar para almacenar el tiempo a mejorar
        int menor_tiempo = 0;
        //variable auxiliar para almacenar la posicion del proceso a retornar
        int posicion_menor_tiempo = -1;
        //variable auxiliar proceso para almacenar el objeto a retornar
        Proceso p = null;
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto}
        for(int i=0;i<procesos_listos.length;i++){
            if(procesos_listos[i]!=null){
                //comparamos el estado del proceso actual
                if(procesos_listos[i].getEstado() == Proceso.ESTADO_LISTO){
                    //Comparamos si el tiempo requerido por el proceso es mayor o igual
                    //al registrado anteriormente o si aun no se a registrado algun tiempo
                    if((menor_tiempo>=procesos_listos[i].getTiempoRequerido())||menor_tiempo==0){
                        //Se asigna a menor_tiempo el tiempo del proceso que tiene menos requerimiento
                        menor_tiempo=procesos_listos[i].getTiempoRequerido();
                        //Se guarda la posicion del proceso
                        posicion_menor_tiempo=i;
                    }
                }
            }
        }
        
        //Si la posicion es mayor a 0 significa que si se encontro un proceso
        //if(posicion_menor_tiempo>0){
           //se asigna a p el proceso que se encuentra en la posicion asignada
           p = procesos_listos[posicion_menor_tiempo];
        //}
        //Se retorna el objeto proceso
        return p;
    }
    
    
    
    
}
