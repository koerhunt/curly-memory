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
    public static Proceso procesos_listos[];
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
        procesos_listos = new Proceso[30];
        
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
        String respuesta = "solicitado";
        System.out.println("El proceso "+p.getNombre()+" esta solicitando un recurso de E/S");
        System.out.println("se asigno el recurso al proceso "+p.getNombre());
    }   
    
    //Metodo para meter un proceso a alguna lista que almacene procesos
    public static void introducirProcesoALista(Proceso p, Proceso lista[]){
        //Recorremos la lista
        for(int i =0; i<lista.length;i++){
            //Si encontramos una posicion vacia
            if(lista[i]==null){
                //guardamos el proceso en esa posicion
                lista[i] = p;
                //rompemos el ciclo
                break;
            }
        }
    }
    
    //Metodo para obtener el proceso con menor tiempo requerido
    public static void ActualizarTiempoDeEsperaDeTodosLosProcesos(Proceso p){
        
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto}
        for(int i=0;i<procesos_listos.length;i++){
            if(procesos_listos[i]!=null){
                //comparamos el estado del proceso actual
                if((p.getPid() != procesos_listos[i].getPid())&&(procesos_listos[i].getEstado()==Proceso.ESTADO_LISTO)){
                    procesos_listos[i].aumentarTiempoDeEspera();
                }
            }
        }
    }
    
    //Metodo para obtener el proceso con menor tiempo requerido
    public static void CalcularPrioridadDeTodosLosProcesos(){
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto}
        for(int i=0;i<procesos_listos.length;i++){
            if(procesos_listos[i]!=null){
                //comparamos el estado del proceso actual
                if(procesos_listos[i].getEstado()==Proceso.ESTADO_LISTO){
                    procesos_listos[i].calcularPrioridad();
                }
            }
        }
    }
    
     //Metodo para obtener el proceso con menor tiempo requerido
    public static void ActualizarTiempoDeServicioDeTodosLosProcesos(){
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto}
        for(int i=0;i<procesos_listos.length;i++){
            if(procesos_listos[i]!=null){
                //comparamos el estado del proceso actual
                procesos_listos[i].calcularTiempoDeServicio();
            }
        }
    }
    
}
