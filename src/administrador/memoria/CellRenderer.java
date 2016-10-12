/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administrador.memoria;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author HDEZ. OCHOA D. SEBASTIAN
 */
public class CellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
    
       @Override
       public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //establecemos el fondo blanco o vacío
        setBackground(null);
        //COnstructor de la clase DefaultTableCellRenderer
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
 
        //Establecemos las filas que queremos cambiar el color. == 0 para pares y != 0 para impares
        //boolean oddRow = (row % 2 == 0);
        //Creamos un color para las filas. El 200, 200, 200 en RGB es un color gris
        Color c = new Color(238, 169, 184); // lila
        Color c2 = new Color(188, 238, 104); //verde feo
        Color c3 = new Color (255, 160, 122); //salmon
        
        //ASIGNACIÖN de colores
        if (column==1) {
            switch (row) {
                case 0: 
                    setBackground(c);
                    break;
                case 1:
                    setBackground(c);
                    break;    
                case 2:
                    setBackground(c2);
                    break;
                case 3:
                    setBackground(c2);
                    break;
                case 4:
                    setBackground(c2);
                    break;
                case 5:
                    setBackground(c2);
                    break;
                case 6:
                    setBackground(c2);
                    break;
                case 7:
                    setBackground(c2);
                    break;
                case 8:
                    setBackground(c2);
                    break;
                case 9:
                    setBackground(c2);
                    break;
                case 10:
                    setBackground(c3);
                    break;
                case 11:
                    setBackground(c3);
                    break;
                case 12:
                    setBackground(c3);
                    break;
                case 13:
                    setBackground(c3);
                    break;
                case 14:
                    setBackground(c3);
                    break;
                case 15:
                    setBackground(c3);
                    break;
            }
        }
           
        return this;
        
    }

    
}
