/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

import java.util.Random;

public class Proceso implements java.io.Serializable {
    
    //Atributos del proceso
    public static final int ESTADO_LISTO = 0;
    public static final int ESTADO_EN_EJECUCION = 1;
    public static final int ESTADO_BLOQUEADO = 2;
    public static final int ESTADO_SUSPENDIDO = 3;
    public static final int ESTADO_SUSPENDIDO_LISTO = 4;
    public static final int ESTADO_SUSPENDIDO_BLOQUEADO = 5;
    public static final int ESTADO_TERMINADO = 6;
    public static final int MEMORIA = 0;
    
    //Identificador del proceso
    private int pid;
    
    //nombre del proceso
    private String nombre;
    //Estado del proceso
    private int estado;
    //Progreso (del 1 al 100)
    private int progreso;
    //Prioridad del proceso
    private int prioridad;
    
    //Tiempos para estadistica
    private int instante_de_llegada;
    private int instante_de_fin;
    private int tiempo_de_ejecucion;
    private int tiempo_de_servicio;
    private int tiempo_de_espera;
    
    //Requerimientos del proceso (tiempo y recurso(s))
    private int tiempo_requerido;    
    private int recurso;
    //Si ponemos el arreglo de recursos tendria que implementarse una
    //tecnica de resolucion de interbloqueos
    //private int recursos_nececitados[];
    
    private boolean recurso_asignado;

    public Proceso() {
    }

    public Proceso(int pid, String nombre, int prioridad, int tiempo_requerido, int recurso) {
        this.pid = pid;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.tiempo_requerido = tiempo_requerido;
        this.recurso = recurso;
        this.tiempo_de_ejecucion = 0;
        estado = ESTADO_LISTO;
        recurso_asignado = false;
    }
    
    //Grupos de Metodos Get y Set
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public int getInstanteDeLlegada() {
        return instante_de_llegada;
    }

    public void setInstanteDeLlegada(int instante_de_llegada) {
        this.instante_de_llegada = instante_de_llegada;
    }

    public int getInstante_de_fin() {
        return instante_de_fin;
    }

    public void setInstante_de_fin(int instante_de_fin) {
        this.instante_de_fin = instante_de_fin;
    }

    public int getTiempo_de_ejecucion() {
        return tiempo_de_ejecucion;
    }

    public void setTiempo_de_ejecucion(int tiempo_de_ejecucion) {
        this.tiempo_de_ejecucion = tiempo_de_ejecucion;
    }

    public int getTiempo_de_servicio() {
        return tiempo_de_servicio;
    }

    public void setTiempo_de_servicio(int tiempo_de_servicio) {
        this.tiempo_de_servicio = tiempo_de_servicio;
    }

    public int getTiempoRequerido() {
        return tiempo_requerido;
    }

    public void setTiempoRequerido(int tiempo_requerido) {
        this.tiempo_requerido = tiempo_requerido;
    }

    public int getRecurso() {
        return recurso;
    }

    public void setRecurso(int recurso) {
        this.recurso = recurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getInstante_de_llegada() {
        return instante_de_llegada;
    }

    public void setInstante_de_llegada(int instante_de_llegada) {
        this.instante_de_llegada = instante_de_llegada;
    }

    public int getTiempoDeEspera() {
        return tiempo_de_espera;
    }

    public void setTiempoDeEspera(int tiempo_de_espera) {
        this.tiempo_de_espera = tiempo_de_espera;
    }

    public boolean isRecurso_asignado() {
        return recurso_asignado;
    }

    public void setRecurso_asignado(boolean recurso_asignado) {
        this.recurso_asignado = recurso_asignado;
    }
    
    
    
    
    //Metodo para actualizar progreso del proceso
    public void actualizarProgreso(){
        tiempo_de_ejecucion++;
        progreso = (tiempo_de_ejecucion * 100)/tiempo_requerido;
    }
    
    //Metodo para calcular el tiempo de servicio
    public void calcularTiempoDeServicio(){
        tiempo_de_servicio = tiempo_de_ejecucion + tiempo_de_espera;
    }
    
    //Metodo para saber si al proceso tiene asignado el recurso
    public boolean tieneSuRecursoAsignado(){
        return recurso_asignado;
    }
    
    
    public static String NombreDeEstado(int e){
        switch(e){
            case ESTADO_LISTO:
                return "Listo/creado";
            case ESTADO_EN_EJECUCION:
                return "Ejecucion";
            case ESTADO_BLOQUEADO:
                return "Bloqueado";
            case ESTADO_SUSPENDIDO:
                return "Suspendido";
            case ESTADO_SUSPENDIDO_LISTO:
                return "Suspendido Listo";
            case ESTADO_SUSPENDIDO_BLOQUEADO:
                return "Suspendido Bloqueado";
            case ESTADO_TERMINADO:
                return "Finalizado/Terminado";
            default:
                return "estado not found";
        }
     
    }
    
//--------------------------Metodos de Administrador de Memoria-----------------------------------------
    
    //MÉTODO MEMORIA
    public static int memoria() {
        Random rd = new Random();
        //Generar Aleatoriamente num del 1 al 7:
        int a = rd.nextInt(7-1)+1;
        //Generar Aleatoriamente num del 0 al 1:
        int b = rd.nextInt(3-1)+1;
        //Generar Aleatoriamente num del 1 al 9:
        int c = rd.nextInt(9-1)+1;
        
        //Variable que contendrá la memoria a emplear:
        int memoria = 0;
        
        //se crean 2 switch para operación.
        switch (c) {
          case 1:
                c = 0;
                break;
            case 2:
                c = 4;
                break;
            case 3:
                c = 0;
                break;
            case 4:
                c = 8;
                break;
            case 5:
                c = 0;
                break;
            case 6:
                c = 16;
                break;
            case 7:
                c = 0;
                break;
            case 8:
                c = 32;
                break;
            case 9:
                c = 0;
                break; 
        }
        switch (a) {
            case 1:
                a = 16;
                if (b==1) {
                    memoria = a+c;
                }
                else {
                    memoria = a-c;
                }
                break;
            case 2:
                a = 32;
                if (b==1) {
                    memoria = a+c;
                }
                else {
                    memoria = a-c;
                }
                break;
            case 3:
                a = 64;
                if (b==1) {
                    memoria = a+c;
                }
                else {
                    memoria = a-c;
                }
                break;
            case 4:
                a = 128;
                if (b==1) {
                    memoria = a+c;
                }
                else {
                    memoria = a-c;
                }
                break;
            case 5:
                a = 256;
                if (b==1) {
                    memoria = a+c;
                }
                else {
                    memoria = a-c;
                }
                break;
            case 6:
                a = 512;
                if (b==1) {
                    memoria = a+c;
                }
                else {
                    memoria = a-c;
                }
                break;
            case 7:
                a = 1024;
                if (b==1) {
                    memoria = a+c;
                }
                else {
                    memoria = a-c;
                }
                break;                                           
        }
        return memoria;
    } 
    
    
    
}
