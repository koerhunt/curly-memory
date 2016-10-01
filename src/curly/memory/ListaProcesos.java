/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author elias
 */
public class ListaProcesos {
    
    private Proceso data[];
    
    public ListaProcesos(int t){
        data = new Proceso[t];
    }
    
    public void agregarProceso(Proceso p){
        try{
            for(int i =0; i<data.length;i++){
                if(data[i]==null){
                    data[i] = p;
                    break;
                }
            }
        }catch(Exception e){
            
        }
    }
    
    public Proceso extraerProceso(int pid){
        Proceso p = null;
        for(int i =0; i<data.length;i++){
            if(data[i]!=null){
                if(data[i].getPid()==pid){
                    p = data[i];
                    data[i] = null;
                }
            }
        }
        reordenarLista();
        return p;
    }
    
    public Proceso verPrimerProceso(){
        Proceso p = null;
        for(int i =0; i<data.length;i++){
            if(data[i]!=null){
                p = data[i];
            }
        }
        return p;
    }
    
    public Proceso extraerPrimerProceso(){
        Proceso p = null;
        for(int i =0; i<data.length;i++){
            if(data[i]!=null){
                p = data[i];
                data[i] = null;
                break;
            }
        }
        reordenarLista();
        return p;
    }
    
     public Proceso extraerUltimoProceso(){
        Proceso p = null;
        for(int i = data.length; i>=0; i--){
            if(data[i]!=null){
                p = data[i];
                data[i] = null;
                break;
            }
        }
        return p;
    }
 
    public void reordenarLista(){
        for(int i=0; i<data.length;i++){
            if(data[i]==null&&i+1<data.length){
                if(data[i+1]!=null){
                  data[i] = data[i+1];
                  data[i+1] = null;   
                }
            }
        }
    }
    
    
    //Metodo para obtener el proceso con menor tiempo requerido
    public void actualizarTiempoDeEspera(){
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto}
        for(int i=0;i<data.length;i++){
            if(data[i]!=null){
                data[i].aumentarTiempoDeEspera();
            }else{
                break;
            }
        }
    }

    //Metodo para obtener el proceso con menor tiempo requerido
    public void CalcularPrioridadDeLosProcesos(){
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto}
        for(int i=0;i<data.length;i++){
            if(data[i]!=null){
                data[i].calcularPrioridad();
            }
        }
    }
    
     //Metodo para obtener el proceso con menor tiempo requerido
    public void ActualizarTiempoDeServicioDeLosProcesos(){
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto}
        for(int i=0;i<data.length;i++){
            if(data[i]!=null){
                //comparamos el estado del proceso actual
                data[i].calcularTiempoDeServicio();
            }
        }
    }

    public void imprimirEnJlist(JList jlist_listos) {
        DefaultListModel tmp;
        
        tmp = (DefaultListModel)jlist_listos.getModel();
        tmp.clear();
        
        try{
            for(int i = 0; i < data.length; i++){
                if(data[i]!=null){
                    tmp.addElement(data[i].getPid());
                }
            }
        }catch(Exception e){
            
        }
        
    }
    
    public void limpiarJlist(JList jlist_listos) {
        DefaultListModel tmp;
        
        tmp = (DefaultListModel)jlist_listos.getModel();
        tmp.clear();
        
    }
    
    public boolean estaVacia(){
        boolean vacia = true;
        for(int i = 0; i < data.length; i++){
            if(data[i]!=null){
                vacia = false;
            }
        }
        return vacia;
    }
    
    //Metodo para obtener el proceso con menor tiempo requerido
    public Proceso extraerProcesoConMenorTiempoRequerido(){
        //variable auxiliar para almacenar el tiempo a mejorar
        int menor_tiempo = 0;
        //variable auxiliar para almacenar la posicion del proceso a retornar
        int posicion_menor_tiempo = -1;
        //variable auxiliar proceso para almacenar el objeto a retornar
        Proceso p = null;
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto}
        for(int i=0;i<data.length;i++){
            if(data[i]!=null){
                //comparamos el estado del proceso actual
                if(data[i].getEstado() == Proceso.ESTADO_LISTO){
                    //Comparamos si el tiempo requerido por el proceso es mayor o igual
                    //al registrado anteriormente o si aun no se a registrado algun tiempo
                    if((menor_tiempo>=data[i].getTiempoRequerido())||menor_tiempo==0){
                        //Se asigna a menor_tiempo el tiempo del proceso que tiene menos requerimiento
                        menor_tiempo=data[i].getTiempoRequerido();
                        //Se guarda la posicion del proceso
                        posicion_menor_tiempo=i;
                    }
                }
            }
        }
        
        //Si la posicion es mayor a 0 significa que si se encontro un proceso
        //if(posicion_menor_tiempo>0){
           //se asigna a p el proceso que se encuentra en la posicion asignada
            try {
               p = data[posicion_menor_tiempo];
            } catch (Exception e) {
               p = null;
            }
        //}
        //Se retorna el objeto proceso
        return p;
    }  
    
    //Metodo para obtener el proceso con mayor prioridad
    public Proceso extraerProcesoConMayorPrioridad(){
        //variable auxiliar para almacenar el tiempo a mejorar
        double prioridad = 0;
        //variable auxiliar para almacenar la posicion del proceso a retornar
        int posicion_mayor_prioridad = -1;
        //variable auxiliar proceso para almacenar el objeto a retornar
        Proceso p = null;
        //Hacemos una busqueda secuencial tomando el tiempo del primer objeto}
        for(int i=(data.length-1);i>=0;i--){
            if(data[i]!=null){
                //comparamos el estado del proceso actual
                if(data[i].getEstado() == Proceso.ESTADO_LISTO){
                    //Comparamos si el tiempo requerido por el proceso es mayor o igual
                    //al registrado anteriormente o si aun no se a registrado algun tiempo
                    if((prioridad<=data[i].getPrioridad())||prioridad==0){
                        //Se asigna a menor_tiempo el tiempo del proceso que tiene menos requerimiento
                        prioridad=data[i].getPrioridad();
                        //Se guarda la posicion del proceso
                        posicion_mayor_prioridad=i;
                    }
                }
            }
        }
        
        //Si la posicion es mayor a 0 significa que si se encontro un proceso
        //if(posicion_menor_tiempo>0){
        //se asigna a p el proceso que se encuentra en la posicion asignada
         try {
            p = data[posicion_mayor_prioridad];
         } catch (Exception e) {
            p = null;
         }
        //}
        //Se retorna el objeto proceso
        return p;
    }   
}
