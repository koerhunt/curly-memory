/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrador.memoria;

import static administrador.memoria.Solt.particiones;
import curly.memory.Proceso;

/**
 *
 * @author jose_
 */
public class Prueba {
    
    
    static Solt memoria_fisica=new Solt(1024);
    static Solt memoria_virtual=new Solt(1024);
        
    public static void main(String args[]){
        
    }
    
    public static int mejorAjuste(Solt memoria,int cantidadSolicitada){
        int mejor=0,desperdicio=0,menor=0;
        
        for(int i=0;i<memoria.particiones.length;i++){
            desperdicio=(memoria.particiones[i].calcularEspacioDisponible())-cantidadSolicitada;
            if(i==0){
                mejor=i;
                menor=memoria.particiones[i].calcularEspacioDisponible();
            }
            else{
                if(desperdicio<menor && memoria.particiones[i].estaDisponible()){
                    mejor=i;
                    menor=desperdicio;
                    }
            }
            
            
        }
        return mejor;
    }
    
    public static void ColocarProcesoEnMemoriaFisica(Proceso p){
        
        int paginas_asignadas=0;
        int numero_paginas = (int) Math.ceil((p.getMemoria())/64);
        int residuo = p.getMemoria()%64;
        
        for(int i = 0; i <=2;i++){
           if(paginas_asignadas<numero_paginas){
                int mejor_particion = mejorAjuste(memoria_fisica,p.getMemoria());
                Particion particion_usar = particiones[mejor_particion];
        
                if(particion_usar.estaDisponible()){            
                    for(int j=0; j<=numero_paginas;j++){
                        if(numero_paginas==1){
                            particion_usar.paginas[j].setEspacio_ocupado(residuo);
                            particion_usar.paginas[j].setEspacio_disponible(64-residuo);
                            particion_usar.paginas[j].setEstado(false);
                            paginas_asignadas++;
                        }else{
                            if(particion_usar.paginas[j].estaDisponible() && j<numero_paginas ){
                                particion_usar.paginas[j].setEspacio_ocupado(64);
                                particion_usar.paginas[j].setEstado(false);
                                paginas_asignadas++;
                            }else{
                                particion_usar.paginas[j].setEspacio_ocupado(residuo);
                                particion_usar.paginas[j].setEspacio_disponible(64-residuo);
                                particion_usar.paginas[j].setEstado(false);
                                paginas_asignadas++;
                            }
                        }
                    }
                }
           }
        }
        
        if(paginas_asignadas<numero_paginas){    
            for(int i = 0; i <=2;i++){
                if(paginas_asignadas<numero_paginas){
                     int mejor_particion = mejorAjuste(memoria_virtual,p.getMemoria());
                     Particion particion_usar = particiones[mejor_particion];

                     if(particion_usar.estaDisponible()){            
                         for(int j=0; j<=numero_paginas;j++){
                             if(numero_paginas==1){
                                 particion_usar.paginas[j].setEspacio_ocupado(residuo);
                                 particion_usar.paginas[j].setEspacio_disponible(64-residuo);
                                 particion_usar.paginas[j].setEstado(false);
                                 paginas_asignadas++;
                             }else{
                                 if(particion_usar.paginas[j].estaDisponible() && j<numero_paginas ){
                                     particion_usar.paginas[j].setEspacio_ocupado(64);
                                     particion_usar.paginas[j].setEstado(false);
                                     paginas_asignadas++;
                                 }else{
                                     particion_usar.paginas[j].setEspacio_ocupado(residuo);
                                     particion_usar.paginas[j].setEspacio_disponible(64-residuo);
                                     particion_usar.paginas[j].setEstado(false);
                                     paginas_asignadas++;
                                 }
                             }
                         }
                     }
                }
            }
        }
        if(paginas_asignadas<numero_paginas){
            System.out.print("se desbordo la memoria jeheheh saludos");
        }
    }
}
