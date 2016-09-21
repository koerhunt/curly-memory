/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author HDEZ. OCHOA D. SEBASTIAN
 */
public class Simulador {
    //Lista de recursos disponibles
    public static Recurso recursos[];
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
        
        //Inicializando recursos
        recursos = new Recurso[4];
        //Creamos los recursos que estaran disponibles para los procesos
        recursos[0] = new Recurso(1,"Impresora");
        recursos[1] = new Recurso(2,"Raton");
        recursos[2] = new Recurso(3,"Teclado");
        recursos[3] = new Recurso(4,"CD-ROM");
        
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
    
    //Metodo que regresa el nombre del recurso 'r'
    public static String ObtenerNombreDelRecurso(int r){
        //si r es igual a 0 el proceso no requiere nungun recurso
        if(r==0){
         return "No requiere recurso";
        }else{
          int rec = r-1;
          //regresa el nombre del recurso
          return recursos[rec].getNombre();
        }
    }
    
    //Metodo para solicitar un recurso
    public static String solicitarRecurso(Proceso p){
        String respuesta = "solicitado";
        int recurso = p.getRecurso();
        //Buscamos el recurso que se esta solicitando en la lista de recursos
        for(int i=0; i<recursos.length;i++){
            //Obtenemos el identificador del recurso y lo comparamos con el solicitado
            //cuando coincidan significa que abremos encontrado el recurso solicitado
            if(recursos[i].getId()==recurso){
                //Imprimimos Log
                System.out.println("El proceso "+p.getNombre()+" esta solicitando el recurso: "+recursos[i].getNombre());
                //Revisamos si el recurso solicitado esta disponible
                if (recursos[i].estaDisponible()){
                    //Se cambia el estado del recurso a "en uso o asignado"
                    recursos[i].setEstado(Recurso.ASIGNADO);
                    //Imprimimos Log
                    p.setRecurso_asignado(true);
                    System.out.println("se asigno el recurso: "+recursos[i].getNombre()+" al proceso "+p.getNombre());
                    //asignamos el resultado a la parte logica
                    respuesta= "asignado";
                }else{
                    //Imprimimos Log
                    System.out.println("El recurso: "+recursos[i].getNombre()+" esta siendo utilizado por otro proceso");
                    //Se le asigna al proceso el estado de bloqueado
                    //p.setEstado(Proceso.ESTADO_BLOQUEADO);
                    //No se le asigno su recurso
                    p.setRecurso_asignado(false);
                    //Imprimimos Log
                    System.out.println("El proceso "+p.getNombre()+" se ha bloqueado");
                    //asignamos el resultado a la parte logica
                    respuesta = "denegado";
                }
                break;
            }
        }
        //Regresamos que es lo que sucedio
        return respuesta;
    }   
    
    //Metodo para liberar un recurso (listo)
    public static void liberarRecurso(Proceso p){
        int recurso = p.getRecurso();
        Recurso r = null;
        for(int i=0; i<recursos.length;i++){
            if(recursos[i]!=null){
                if(recursos[i].getId()==recurso){
                    r = recursos[i];
                    break;
                }
            }
        }
        if(r!=null){
            r.setEstado(Recurso.LIBRE);
            p.setRecurso_asignado(false);
        }
        System.out.println("El proceso "+p.getNombre()+" ha liberado el recurso "+r.getNombre());
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
