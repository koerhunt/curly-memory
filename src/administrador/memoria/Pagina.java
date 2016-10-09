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
public class Pagina {
    int tamanio;
    int ocupado;
    int disponible;
    
    Particion particion;
    
    public Pagina(){
        
    }
    
    public Pagina(int t, Particion p){
        this.tamanio = t;
        this.particion = p;
    }
    
}
