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
        for(int i =0; i<data.length;i++){
            if(data[i]==null){
                data[i] = p;
                break;
            }
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
        
        for(int i = 0; i < data.length; i++){
            if(data[i]!=null){
                tmp.addElement(data[i].getPid());
            }
        }
        
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
}
