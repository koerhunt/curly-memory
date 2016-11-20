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
    
    public Pagina paginas[];
    
    int ocupado;
    int disponible;
    
    public Solt(){
        
    }
    
    public Solt(int tamanio){
        this.tamanio = tamanio;
        
        paginas = new Pagina[this.tamanio/64];
        for(int i = 0; i< this.tamanio/64;i++){
            paginas[i] = new Pagina();
        }
        
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

    public int getTamanio() {
        return tamanio;
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
