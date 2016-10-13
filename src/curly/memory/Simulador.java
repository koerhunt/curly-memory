/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

import curly.memory.administrador.estados.InterfazG;
import curly.memory.administrador.estados.ListaProcesos;
import curly.memory.administrador.estados.CrearProcesos;
import java.util.Random;

/**
 *
 * @author HDEZ. OCHOA D. SEBASTIAN
 */

public class Simulador {
    //Lista de procesos listos
    public static ListaProcesos procesos_listos;
    
    //Lista de procesos listos
    public static ListaProcesos procesos_bloqueados;
    
    //Lista de procesos listos
    public static ListaProcesos suspendidos_listos;
    
    //Lista de procesos listos
    public static ListaProcesos suspendidos_bloqueados;
    
    //Lista de procesos listos
    public static ListaProcesos proceso_en_ejecucion;
    
    //Lista de procesos terminados
    public static ListaProcesos procesos_terminados;
    
    //Todos los procesos
    public static Proceso todos_los_procesos[];
    
    //lista de Callbacks
    public static Thread callbacks[];
    
    //Hilo creador de procesos
    public static Thread t;
    
    public static int secuencia_id = 5;
    
    //tiempo transcurrido en el cpu
    public static int tiempo_cpu=0;

    //cuantum para RR
    public static int quantum;
    
    //Velocidad del simulador en ms
    public static int velocidad = 1000;
    
    //creamos un objeto random para obtener numeros aleatorios
    static Random rd;
    
    //Metodo principal
    public static void main(String[] args) {
        //Se crean los recursos, se prepara la memoria para la lista de listos
        inicializar();
    }
    
    public static void inicializar(){
        //Inicializando una lista para hacer referencia a todos los procesos
        todos_los_procesos = new Proceso[60];
        
        //Inicializando grupo de listas para los diferentes estados de los procesos
        procesos_listos = new ListaProcesos(60);
        procesos_bloqueados = new ListaProcesos(60);
        
        suspendidos_listos = new ListaProcesos(60);
        suspendidos_bloqueados = new ListaProcesos(60);
        
        proceso_en_ejecucion = new ListaProcesos(1);
        procesos_terminados = new ListaProcesos(60);
        
        //Preparamos la lista de callbacks
        callbacks = new Thread[200];
        
        //Inicializando secuencia del ID
        secuencia_id = 1;
        //Iniciando tiempo del cpu
        tiempo_cpu=0;
        //Definiendo el valor del cuantum
        quantum=3;
        
        //Inicializamos objeto para crear numeros aleatorios
        rd = new Random();
    }
    
    //Metodo para crear los 5 procesos por defecto conforme la ejecucion del programa avanza
    public static void crearProcesosPorDefecto(){
        t = new Thread(new CrearProcesos());        
        t.start();
    }
    
    //Metodo para solicitar un recurso
    public static void solicitarRecurso(Proceso p){
        System.out.println("El proceso "+p.getNombre()+" esta solicitando un recurso de E/S");
        System.out.println("se asigno el recurso al proceso "+p.getNombre());
    }   
    
    //Metodo para meter un proceso a la lista de todos los procesos
    public static void introducirProcesoALista(Proceso p){
        //Recorremos la lista
        for(int i =0; i<todos_los_procesos.length;i++){
            //Si encontramos una posicion vacia
            if(todos_los_procesos[i]==null){
                //guardamos el proceso en esa posicion
                todos_los_procesos[i] = p;
                //rompemos el ciclo
                break;
            }
        }
    }
    
    //Metodo para meter un callback a la lista de callbacks
    public static void introducirCallbackALista(Thread callback){
        //Recorremos la lista
        for(int i =0; i<callbacks.length;i++){
            //Si encontramos una posicion vacia
            if(callbacks[i]==null){
                //guardamos el callback en esa posicion
                callbacks[i] = callback;
                //rompemos el ciclo
                break;
            }
        }
    }
    
    public static void actualizarDatos(){
        
        procesos_listos.actualizarTiempoDeEspera();
        procesos_listos.ActualizarTiempoDeServicioDeLosProcesos();
        procesos_listos.CalcularPrioridadDeLosProcesos();
        
        procesos_bloqueados.actualizarTiempoDeEspera();
        procesos_bloqueados.ActualizarTiempoDeServicioDeLosProcesos();
        procesos_bloqueados.CalcularPrioridadDeLosProcesos();
        
    }

    public static void suspenderCallbacks() {
        for(int i =0; i<callbacks.length;i++){
            if(callbacks[i]!=null){
                callbacks[i].suspend();
            }
        }
    }

    public static void resumirCallbacks() {
        for(int i =0; i<callbacks.length;i++){
            if(callbacks[i]!=null){
                callbacks[i].resume();
            }
        }
    }
    
    public void actualizarInterface(Proceso p,boolean bloquear_o_suspender){
        if(bloquear_o_suspender){
            int j;

            if(p.requiereEntradaSalida()&&p.entraraASuspencion()){
                j = rd.nextInt(2-0)+1;   
                switch(j){
                   case 0:
                       p.setEstado(Proceso.ESTADO_BLOQUEADO);
                       break;
                   case 1:
                       p.setEstado(Proceso.ESTADO_SUSPENDIDO_LISTO);
                       break;
                   case 2:
                       p.setEstado(Proceso.ESTADO_SUSPENDIDO_BLOQUEADO);
                       break;
               }
            }else if(p.entraraASuspencion()){
                j = rd.nextInt(1-0)+1;
                switch(j){
                   case 0:
                       p.setEstado(Proceso.ESTADO_SUSPENDIDO_LISTO);
                       break;
                   case 1:
                       p.setEstado(Proceso.ESTADO_SUSPENDIDO_BLOQUEADO);
                       break;
               }
            }else{
                p.setEstado(Proceso.ESTADO_BLOQUEADO);
            }
        }
    }
    
    
    public void actualizarInterfaceSinEsperar(){
            //Actualizar Ambiente grafico
            InterfazG.actualizarAmbienteGrafico();
    }
    
}
