/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrador.memoria;

/**
 *
 * @author elias
 */
public class Solt {
    
    int tamanio;
    public Particion particiones[];
    
    int ocupado;
    int disponible;
    
    public Solt(){
        
    }
    
    public Solt(int tamanio){
        this.tamanio = tamanio;
        particiones = new Particion[3];
        particiones[0] = new Particion(128);
        particiones[1] = new Particion(512);
        particiones[2] = new Particion(384);
        
    }    
    
}
