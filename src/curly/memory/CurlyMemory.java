/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

public class CurlyMemory {
    
    //Lista de recursos disponibles
    static Recurso recursos[];
    //Lista de procesos listos
    private static Proceso procesos_listos[];
    //Lista de procesos bloqueados
    private static Proceso procesos_bloqueados[];
    //Lista de procesos suspendidos
    private static Proceso procesos_suspendidos[];
    //Lista de procesos suspendidos listos
    private static Proceso procesos_suspendidos_listos[];
    //Lista de procesos suspendidos bloqueados
    private static Proceso procesos_suspendidos_bloqueados[];
    
    static int secuencia_id = 5;
    static int tiempo_cpu=1;
    //Metodo principal
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Inicializamos la lista de recursos
        recursos = new Recurso[4];
        //Creamos los recursos que estaran disponibles para los procesos
        recursos[0] = new Recurso(1,"Impresora");
        recursos[1] = new Recurso(2,"Raton");
        recursos[2] = new Recurso(3,"Teclado");
        recursos[3] = new Recurso(4,"CD-ROM");
        
        //Grupo de procesos predefinidos
        procesos_listos = new Proceso[30];
        
        procesos_listos[0] = new Proceso(1, "Google chrome",0, 18, 0);
        procesos_listos[1] = new Proceso(2, "Microsoft Word",0, 8, 0);
        procesos_listos[2] = new Proceso(3, "Paint",0, 5, 0);
        procesos_listos[3] = new Proceso(4, "Spotify",0, 15, 0);
        procesos_listos[4] = new Proceso(5, "Avast",0, 9, 0);
        
        
        escribir(procesos_listos);
        leer(procesos_listos);
        
    }
    
    
    public static void ComenzarFifo(){
        for(int i=0;i<procesos_listos.length;i++){
            if (procesos_listos[i]!=null){
                procesos_listos[i].setEstado(Proceso.ESTADO_EN_EJECUCION);
                procesos_listos[i].setInstanteDeLlegada(tiempo_cpu);
                        while(procesos_listos[i].getProgreso()==100){
                            procesos_listos[i].setTiempoDeEspera(tiempo_cpu);
                            procesos_listos[i].actualizarProgreso();
                            if (procesos_listos[i].getProgreso()==100){
                                procesos_listos[i].setEstado(Proceso.ESTADO_TERMINADO);
                                procesos_listos[i].setInstante_de_fin(tiempo_cpu);
                                procesos_listos[i].calcularTiempoDeServicio();
                            }
                            tiempo_cpu++;
                        }
                        
            }
        }
    } 
    
       
    public static void ComenzarSJN(){        
        Proceso proceso_mas_corto;
        do{
            proceso_mas_corto = ObtenerProcesoConMenorTiempoRequerido();
            
            if(proceso_mas_corto!=null){
                proceso_mas_corto.setTiempoDeEspera(tiempo_cpu);
                proceso_mas_corto.actualizarProgreso();
                if (proceso_mas_corto.getProgreso()==100){
                    proceso_mas_corto.setEstado(Proceso.ESTADO_TERMINADO);
                    proceso_mas_corto.setInstante_de_fin(tiempo_cpu);
                    proceso_mas_corto.calcularTiempoDeServicio();
                }
                tiempo_cpu++;
            }
        }while(proceso_mas_corto!=null);
    }
    
    public static Proceso ObtenerProcesoConMenorTiempoRequerido(){
        int menor_tiempo = 0;
        int posicion_menor_tiempo = -1;
        Proceso p = null;
        for(int i=0;i<procesos_listos.length;i++){
            if(i==0){
                if(procesos_listos[i].getEstado() != Proceso.ESTADO_TERMINADO){
                  menor_tiempo=procesos_listos[i].getTiempoRequerido();
                  posicion_menor_tiempo=i;
                }                
            }else{
                if(procesos_listos[i].getEstado() != Proceso.ESTADO_TERMINADO){
                    if(menor_tiempo>=procesos_listos[i].getTiempoRequerido()){
                          menor_tiempo=procesos_listos[i].getTiempoRequerido();
                          posicion_menor_tiempo=i;                
                    }
                }
            }
        }
        
        if(posicion_menor_tiempo>0){
           p = procesos_listos[posicion_menor_tiempo];
        }
        
        return p;
    }
    
    
    public static String solicitarRecurso(Proceso p, int recurso){
        String respuesta = "solicitado";
        //Buscamos el recurso que se esta solicitando en la lista de recursos
        for(int i=0; i<recursos.length;i++){
            //Obtenemos el identificador del recurso y lo comparamos con el solicitado
            //cuando coincidan significa que abremos encontrado el recurso solicitado
            if(recursos[i].getId()==recurso){
                //Imprimimos Log
                System.out.println("El proceso "+p.getPid()+" esta solicitando el recurso: "+recursos[i].getNombre());
                //Revisamos si el recurso solicitado esta disponible
                if (recursos[i].estaDisponible()){
                    //Se cambia el estado del recurso a "en uso o asignado"
                    recursos[i].setEstado(Recurso.ASIGNADO);
                    //Imprimimos Log
                    System.out.println("se asigno el recurso: "+recursos[i].getNombre()+" al proceso "+p.getPid());
                    //asignamos el resultado a la parte logica
                    respuesta= "asignado";
                }else{
                    //Imprimimos Log
                    System.out.println("El recurso: "+recursos[i].getNombre()+" esta siendo utilizado");
                    //Se le asigna al proceso el estado de bloqueado
                    p.setEstado(Proceso.ESTADO_BLOQUEADO);
                    //Imprimimos Log
                    System.out.println("El proceso "+p.getPid()+" se ha bloqueado");
                    //asignamos el resultado a la parte logica
                    respuesta = "denegado";
                }
            }
        }
        //Regresamos que es lo que sucedio
        return respuesta;
    }   
 
    //Método para guardar procesos en archivos
    public static  void escribir (Proceso lista[]){
        try{
          ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("procesos_suspendido_listo.obj"));
            for(int i=0 ; i<30; i=i+1){
                if(lista[i]!=null) {
                    salida.writeObject(lista[i]);
                    salida.close();
                }
            }
            salida.close();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo Guardar el Archivo");
        }
           
        }
    static int n=0;
    //Método para leer archivos de procesos
    public static void leer (Proceso lista[]){
        //for (int x=0; x<30; x++) {
        try{
        boolean seguir=true;
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("proceso.obj"));
        do{
                           
                Proceso obj1 = (Proceso)entrada.readObject();
                if(obj1==null){
                seguir=false;
                }            
              
        }while(seguir);
        entrada.close();
        }
        catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo Leer el Archivo");
            }
            catch (ClassNotFoundException exc){
                
            }
    }
    
    
    
}
