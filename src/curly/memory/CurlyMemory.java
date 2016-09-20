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
    
    //Metodo para iniciar planificacion FIFO
    public static void ComenzarFifo(){
        //Recorremos la lista de procesos
        for(int i=0;i<procesos_listos.length;i++){
            //Verificamos que en la posiciin i se encuentre un objeto
            if (procesos_listos[i]!=null){
                //Cambiamos el estado del proceso a ejecucion
                procesos_listos[i].setEstado(Proceso.ESTADO_EN_EJECUCION);
                //Asignamos el instante de llegada al proceso
                procesos_listos[i].setInstanteDeLlegada(tiempo_cpu);
                //Mientras el proceso no haya terminado se estara trabajando en 'el
                while(procesos_listos[i].getProgreso()==100){
                    //Por ser FIFO el tiempo de espera sera igual al tiempo del cpu
                    procesos_listos[i].setTiempoDeEspera(tiempo_cpu);
                    //Se actualiza el progreso del proceso
                    procesos_listos[i].actualizarProgreso();
                    //si el progreso esta terminado se actualiza su estado
                    if (procesos_listos[i].getProgreso()==100){
                        //se cambia el estado del proceso a terminado
                        procesos_listos[i].setEstado(Proceso.ESTADO_TERMINADO);
                        //se asigna el instante de fin
                        procesos_listos[i].setInstante_de_fin(tiempo_cpu);
                        //se calcula el tiempo de servicio del proceso
                        procesos_listos[i].calcularTiempoDeServicio();
                    }
                    //Se aumenta una unidad de tiempo a el procesador
                    tiempo_cpu++;
                }       
            }
        }
    } 
    
    //Metodo para iniciar planificacion SJN   
    public static void ComenzarSJN(){        
        //declaramos una variable para guradar el proceso mas corto
        Proceso proceso_mas_corto;
        //Hacer:
        do{
            //Obtenemos el proceso con menor requerimiento de tiempo de la lista de listos
            proceso_mas_corto = ObtenerProcesoConMenorTiempoRequerido();
            //Si el objeto 'proceso_mas_corto' no es nulo, significa que hay procesos que 
            //aun no han sido terminados
            if(proceso_mas_corto!=null){
                //Se asigna el tiempo de espera
                proceso_mas_corto.setTiempoDeEspera(tiempo_cpu);
                //Se actualiza el progreso del proceso
                proceso_mas_corto.actualizarProgreso();
                //si el progreso esta terminado se actualiza su estado
                if (proceso_mas_corto.getProgreso()==100){
                    //se cambia el estado del proceso
                    proceso_mas_corto.setEstado(Proceso.ESTADO_TERMINADO);
                    //se asigna el instante de fin
                    proceso_mas_corto.setInstante_de_fin(tiempo_cpu);
                    //se calcula el tiempo de servicio
                    proceso_mas_corto.calcularTiempoDeServicio();
                }
                //Se aumenta una unidad de tiempo a el procesador
                tiempo_cpu++;
            }
        //Mientras el proceso mas corto sea diferente de nulo
        }while(proceso_mas_corto!=null);
    }
    
    public static Proceso ObtenerProcesoConMenorTiempoRequerido(){
        //variable auxiliar para almacenar el tiempo a mejorar
        int menor_tiempo = 0;
        //variable auxiliar para almacenar la posicion del proceso a retornar
        int posicion_menor_tiempo = -1;
        //variable auxiliar proceso para almacenar el objeto a retornar
        Proceso p = null;
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto
        for(int i=0;i<procesos_listos.length;i++){
            //comparamos el estado del proceso actual
            if(procesos_listos[i].getEstado() != Proceso.ESTADO_TERMINADO){
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
        
        //Si la posicion es mayor a 0 significa que si se encontro un proceso
        if(posicion_menor_tiempo>0){
           //se asigna a p el proceso que se encuentra en la posicion asignada
           p = procesos_listos[posicion_menor_tiempo];
        }
        //Se retorna el objeto proceso
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
