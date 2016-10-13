/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrador.memoria;

import curly.memory.Proceso;

/**
 *
 * @author elias
 */
public class Pagina {
    int tamanio;
    int espacio_ocupado=0;
    int espacio_disponible=64;
    boolean estado=true;
    
    Particion particion;
    Proceso proceso;
    
    public Pagina(){
        
    }
    
    public Pagina(int t, Particion p){
        this.tamanio = t;
        this.particion = p;
    }
    
    
    public boolean estaDisponible(){
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setEspacio_ocupado(int espacio_ocupado) {
        this.espacio_ocupado = espacio_ocupado;
    }

    public void setEspacio_disponible(int espacio_disponible) {
        this.espacio_disponible = espacio_disponible;
    }

    public int getEspacio_ocupado() {
        return espacio_ocupado;
    }

    public int getEspacio_disponible() {
        return espacio_disponible;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }
    
}
