/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curly.memory;



/**
 *
 * @author elias
 */
public class MainClass {
    
    public static void main(String args[]) {
        
        Simulador s = new Simulador();
        
        //Correr interfaz del pcb
        new administrador.estados.InterfazG().setVisible(true);
        //correr interface de memoria
        new administrador.memoria.InterfazG().setVisible(true);
        
        
    }
}
