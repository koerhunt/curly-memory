/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

public class Recurso {
  
  //Estados en los que puede estar un proceso
    public static final int ASIGNADO = 1;
    public static final int LIBRE =0;
    
  //Identificador del recurso
    private int id;
    
  //Nombre del recurso
    private String nombre;
    
  //Estado
  // 0 -> libre,  1 -> ocupado
    private int estado;
    
    
  //Constructores
    public Recurso() {
        this.estado = 0;
    }
    
    public Recurso(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.estado = 0;
    }
    
    public Recurso(String nombre) {
        this.nombre = nombre;
        this.estado = 0;
    }
    
  //Grupo de Metodos Get y Set

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }   
    
    //Metodos de utileria
    //Al invocar a este metodo devuelve un verdadero o falso
    //Significando si el recurso esta disponible o no
    public boolean estaDisponible(){
        if(this.estado==0)
            return true;
        else
            return false;
    }
    
    
    
}
