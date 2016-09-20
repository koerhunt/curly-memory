/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

public class Proceso implements java.io.Serializable {
    
    //Atributos del proceso
    public static final int ESTADO_LISTO = 0;
    public static final int ESTADO_EN_EJECUCION = 1;
    public static final int ESTADO_BLOQUEADO = 2;
    public static final int ESTADO_SUSPENDIDO = 3;
    public static final int ESTADO_SUSPENDIDO_LISTO = 4;
    public static final int ESTADO_SUSPENDIDO_BLOQUEADO = 5;
    public static final int ESTADO_TERMINADO = 6;
    
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
        this.tiempo_de_ejecucion = 1;
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
    
    
    
}
