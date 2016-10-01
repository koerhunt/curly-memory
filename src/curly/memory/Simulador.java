/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;


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
    public static ListaProcesos proceso_en_ejecucion;
    
    //Lista de procesos terminados
    public static ListaProcesos procesos_terminados;
    
    //Todos los procesos
    public static Proceso todos_los_procesos[];
    
    public static Thread t;
    
    static int secuencia_id = 5;
    //tiempo transcurrido en el cpu
    static int tiempo_cpu=0;
    //cuantum para RR
    static int quantum;
    
    //Metodo principal
    public static void main(String[] args) {
        //Se crean los recursos, se prepara la memoria para la lista de listos
        inicializar();
    }
    
    public static void inicializar(){        
        //Inicializando gurpo de procesos predefinidos
        procesos_listos = new ListaProcesos(60);
        
        procesos_bloqueados = new ListaProcesos(60);
        
        proceso_en_ejecucion = new ListaProcesos(1);
        
        procesos_terminados = new ListaProcesos(60);
        
        todos_los_procesos = new Proceso[160];
        
        //Inicializando secuencia del ID
        secuencia_id = 1;
        //Iniciando tiempo del cpu
        tiempo_cpu=0;
        //Definiendo el valor del cuantum
        quantum=3;
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
    
    //Metodo para meter un proceso a alguna lista que almacene procesos
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
    
    public static void actualizarDatos(){
        
        procesos_listos.actualizarTiempoDeEspera();
        procesos_listos.ActualizarTiempoDeServicioDeLosProcesos();
        procesos_listos.CalcularPrioridadDeLosProcesos();
        
        procesos_bloqueados.actualizarTiempoDeEspera();
        procesos_bloqueados.ActualizarTiempoDeServicioDeLosProcesos();
        procesos_bloqueados.CalcularPrioridadDeLosProcesos();
        
    }
    
}
