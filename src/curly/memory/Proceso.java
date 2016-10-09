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
    public static final int ESTADO_SUSPENDIDO_LISTO = 3;
    public static final int ESTADO_SUSPENDIDO_BLOQUEADO = 4;
    public static final int ESTADO_TERMINADO = 5;
    
    //Identificador del proceso
    private int pid;
    //nombre del proceso
    private String nombre;
    //Estado del proceso
    private int estado;
    //Progreso (del 1 al 100)
    private int progreso;
    //Prioridad del proceso
    private double prioridad;
    //Variable para determinar si el proceso entrara a algun estado suspendido
    private boolean suspender;
    
    //Tiempos para estadistica
    private int instante_de_llegada;
    private int instante_de_fin;
    private int tiempo_de_ejecucion;
    private int tiempo_de_servicio;
    private int tiempo_de_espera;
    
    //Requerimientos del proceso (tiempo y recurso(s))
    private int tiempo_requerido;    
    private boolean recurso;

    public Proceso() {
    }

    public Proceso(int pid, String nombre, int tiempo_requerido, boolean recurso,boolean suspender) {
        this.pid = pid;
        this.nombre = nombre;
        this.tiempo_requerido = tiempo_requerido;
        this.recurso = recurso;
        this.tiempo_de_ejecucion = 0;
        this.tiempo_de_espera = 0;
        this.suspender = suspender;
        estado = ESTADO_LISTO;
        Simulador.introducirProcesoALista(this);
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

    public void setEstado(int e){
        int estado_anterior = this.estado;
        //declaramos el callback
        Thread callback;
         switch(this.estado){
            case ESTADO_LISTO:
                Simulador.procesos_listos.extraerProceso(this.pid);
                break;
            case ESTADO_EN_EJECUCION:
                Simulador.proceso_en_ejecucion.extraerProceso(this.pid);
                break;
            case ESTADO_BLOQUEADO:
                Simulador.procesos_bloqueados.extraerProceso(this.pid);
                break;
            case ESTADO_SUSPENDIDO_LISTO:
                Simulador.suspendidos_listos.extraerProceso(this.pid);
                break;
            case ESTADO_SUSPENDIDO_BLOQUEADO:
                Simulador.suspendidos_bloqueados.extraerProceso(this.pid);
                break;
        }
        switch(e){
            case ESTADO_LISTO:
                Simulador.procesos_listos.agregarProceso(this);
                break;
            case ESTADO_EN_EJECUCION:
                Simulador.proceso_en_ejecucion.agregarProceso(this);
                break;
            case ESTADO_BLOQUEADO:
                Simulador.procesos_bloqueados.agregarProceso(this);
                callback = new Thread(new CallBackDesbloquearProceso());
                Simulador.introducirCallbackALista(callback);
                callback.start();
                break;
            case ESTADO_SUSPENDIDO_LISTO:
                Simulador.suspendidos_listos.agregarProceso(this);
                callback = new Thread(new CallBackRestaurarProcesoListo());
                Simulador.introducirCallbackALista(callback);
                callback.start();
                break;
            case ESTADO_SUSPENDIDO_BLOQUEADO:
                Simulador.suspendidos_bloqueados.agregarProceso(this);
                callback = new Thread(new CallBackRestaurarProcesoBloqueado());
                Simulador.introducirCallbackALista(callback);
                callback.start();
                
                break;
            case ESTADO_TERMINADO:
                Simulador.procesos_terminados.agregarProceso(this);
                break;
        }
        this.estado = e;
        InterfazG.actualizarAmbienteGrafico();
        System.out.println("El proceso "+this.nombre+" - Cambio de estado "+NombreDeEstado(estado_anterior)+" a -> "+NombreDeEstado(e));
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrioridad() {
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

    public boolean entraraASuspencion() {
        return suspender;
    }

    public void setSuspender(boolean suspender) {
        this.suspender = suspender;
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
    
    //Metodo para calcular el indice de respuesta
    public double calcularPrioridad() {
        //P=(w+t)/t
        //Donde: P=prioridad, w=tiempo de espera en listos, t=tiempo de ejecución.
        try{
            // P=(w+t)/t
            //Donde: P=prioridad, w=tiempo de espera en listos, t=tiempo de ejecución.
            this.prioridad = (tiempo_de_espera+tiempo_de_servicio)/(tiempo_de_servicio);
        }catch(Exception e){
            this.prioridad =0;
        }
        
        return prioridad;
    }
    
    //Metodo aumenta en uno el tiempo de espera
    public void aumentarTiempoDeEspera(){
        tiempo_de_espera++;
    }
    
    public boolean requiereEntradaSalida() {
        return recurso;
    }

    public boolean requiereRecurso() {
        return recurso;
    }

    public void setRecurso(boolean recurso) {
        this.recurso = recurso;
    }
    
    
    
    //Este metodo devuelve el nombre del estado correspondiente a un numero entero
    public static String NombreDeEstado(int e){
        switch(e){
            case ESTADO_LISTO:
                return "Listo/creado";
            case ESTADO_EN_EJECUCION:
                return "Ejecucion";
            case ESTADO_BLOQUEADO:
                return "Bloqueado";
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
    
    
    
}
