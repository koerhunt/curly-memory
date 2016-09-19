/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;

public class CurlyMemory {
    
    //Lista de recursos disponibles
    static Recurso recursos[];
    //Lista de procesos listos
    private static Proceso procesos_listos[];
    //Lista de procesos bloqueados
    private static Proceso procesos_bloqueados[];
    //Lista de procesos suspendidos
    private static Proceso procesos_suspendidos[];
    //Lista de procesos suspendidos listos
    private static Proceso procesos_suspendidos_listos[];
    //Lista de procesos suspendidos bloqueados
    private static Proceso procesos_suspendidos_bloqueados[];
    
    
    //Metodo principal
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Inicializamos la lista de recursos
        recursos = new Recurso[4];
        //Creamos los recursos que estaran disponibles para los procesos
        recursos[0] = new Recurso(1,"Impresora");
        recursos[1] = new Recurso(2,"Raton");
        recursos[2] = new Recurso(3,"Teclado");
        recursos[3] = new Recurso(4,"CD-ROM");
    }
    
}
