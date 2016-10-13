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
    
    int tamanio;
    boolean estado=true;
    Solt s;
    public Pagina paginas[];
    
    int ocupado;
    int disponible=tamanio-ocupado;
    
    int TAMANIO_DE_PAGINA = 64;
    
    public Particion(){
        
    }
    
    public Particion(int tamanio){
        this.tamanio = tamanio;
        paginas = new Pagina[this.tamanio/64];
        for(int i = 0; i< this.tamanio/64;i++){
            paginas[i] = new Pagina();
        }
    }
    public int getTamanio(){
    return tamanio;
    }
    public int getOcupado() {
        return ocupado;
    }

    public void setOcupado(int ocupado) {
        this.ocupado = ocupado;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }
    
    
    
    public int calcularPaginasOcupadas(){
        int pagOcupadas=0;
        for(int i=0;i<paginas.length;i++){
            if(!paginas[i].estaDisponible())
                pagOcupadas=pagOcupadas+1;
        }
        return pagOcupadas;
    }
    
    public int calcularEspacioOcupado(){
        int espacio_ocupado = 0;
        for(int i=0;i<paginas.length;i++){
            if(!paginas[i].estaDisponible())
                espacio_ocupado+=paginas[i].getEspacio_ocupado();
        }
        this.ocupado = espacio_ocupado;
        return espacio_ocupado;
    }
    
    public int calcularEspacioDisponible(){
        this.disponible = this.tamanio - calcularEspacioOcupado();
        return this.disponible;
    }
    
    public boolean estaDisponible(){
        boolean es = false;
        
        for(int i=0;i<paginas.length;i++){
           if(paginas[i].estaDisponible()){
                es = true;
                break;
           }
        }
        
        
        return es;
    }
    
    
}
