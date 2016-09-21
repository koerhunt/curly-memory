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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    //Lista de procesos bloqueados
    public static Proceso procesos_bloqueados[];
    //Lista de procesos suspendidos
    public static Proceso procesos_suspendidos[];
    //Lista de procesos suspendidos listos
    public static Proceso procesos_suspendidos_listos[];
    //Lista de procesos suspendidos bloqueados
    public static Proceso procesos_suspendidos_bloqueados[];
    //Hilo que crea los procesos por defecto
    public static Thread t;
    
    static int secuencia_id = 5;
    static int tiempo_cpu=0;
    
    //Metodo principal
    public static void main(String[] args) {       
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
                        
        t = new Thread(new CrearProcesos());        
        
        //Inicializando listas de procesos para los demas estados
        procesos_suspendidos_listos = new Proceso[30];
        procesos_suspendidos_bloqueados = new Proceso[30];
        
        //Inicializando secuencia del ID
        secuencia_id = 1;
        //Iniciando tiempo del cpu
        tiempo_cpu=0;
        
    }
    
    public static void crearProcesosPorDefecto(){
        t.start();
    }
    
    public static String ObtenerNombreDelRecurso(int r){
        if(r==0){
         return "No requiere recurso";
        }else{
         int rec = r-1;
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
                    p.setEstado(Proceso.ESTADO_BLOQUEADO);
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
            if(recursos[i].getId()==recurso){
                r = recursos[i];
                break;
            }
        }
        p.setEstado(Recurso.LIBRE);
        p.setRecurso_asignado(false);
        
        System.out.println("El proceso "+p.getNombre()+" ha liberado el recurso "+r.getNombre());
    }
 
    //Método para suspender un proceso (listo)
    public static void suspenderProcesoListo(Proceso p){
        introducirProcesoALista(p, procesos_suspendidos_listos);
        try{
            ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("procesos_suspendidos_listos.obj"));
            for(int i = 0; i< procesos_suspendidos_listos.length; i++){
                if(procesos_suspendidos_listos[i]!=null){
                    salida.writeObject(procesos_suspendidos_listos[i]);
                }
            }
            salida.close();
        } catch (IOException e) {
            System.out.println("No se pudo Guardar el proceso en memoria secundaria");
        }   
    }
    
    //Metodo para restaurar un proceso
    public static void restaurarProcesosSuspendidosListos(){
        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("procesos_suspendidos_listos.obj"));
            Proceso p = null;
            do{
                p = (Proceso)entrada.readObject();
                System.out.println(p.getNombre());
            }while(p!=null);
            entrada.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se encontraron procesos suspendidos");
        } catch (IOException ex) {
            System.out.println("No se pudo leer el proceso en memoria secundaria");
        } catch (ClassNotFoundException ex) {
            System.out.println("No se encontro la clase perteneciente al tipo de archivo");
        }
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
    
    //Método para suspender un proceso (bloqueado)
    public static void suspenderProcesoBloqueado(Proceso p){
        introducirProcesoALista(p, procesos_suspendidos_bloqueados);
        try{
            ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("procesos_suspendidos_bloqueados.obj"));
            for(int i = 0; i< procesos_suspendidos_bloqueados.length; i++){
                if(procesos_suspendidos_bloqueados[i]!=null){
                    salida.writeObject(procesos_suspendidos_bloqueados[i]);
                }
            }
            salida.close();
        } catch (IOException e) {
            System.out.println("No se pudo Guardar el proceso en memoria secundaria");
        }   
    }
    
    //Metodo para restaurar un proceso (bloqueado)
    public static void restaurarProcesosSuspendidosBloqueados(){
        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("procesos_suspendidos_bloqueados.obj"));
            Proceso p = null;
            do{
                p = (Proceso)entrada.readObject();
                System.out.println(p.getNombre());
            }while(p!=null);
            entrada.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se encontraron procesos suspendidos");
        } catch (IOException ex) {
            System.out.println("No se pudo leer el proceso en memoria secundaria");
        } catch (ClassNotFoundException ex) {
            System.out.println("No se encontro la clase perteneciente al tipo de archivo");
        }
    }
    
}
