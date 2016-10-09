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
public class Particion {
    
    public int tamanio;
    Solt s;
    Pagina paginas[];
    
    int ocupado;
    int disponible;
    
    int TAMANIO_DE_PAGINA = 64;
    
    public Particion(){
        
    }
    
    public Particion(int tamanio){
        this.tamanio = tamanio;
        paginas = new Pagina[this.tamanio/TAMANIO_DE_PAGINA];
    }
    
}
