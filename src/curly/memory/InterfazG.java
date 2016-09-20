/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//*****************************************************************************************************************


//*****************************************************************************************************************
//interfaz
package curly.memory;

import java.util.Random;
import javax.swing.*;
import javax.swing.table.TableModel;

/**
 *
 * @author HDEZ. OCHOA D. SEBASTIAN
 */
public class InterfazG extends javax.swing.JFrame {
    
    //Variables para ubicar el modelo de la tabla y la barra de progreso
    //El modelo es el formato que se da a la tabla
    static TableModel model;
    static JProgressBar barra;
    static JButton btn_iniciar;
    
    static Thread hilo_ejecutando;
   
    /**
    * Creates new form InterfazG
    */
    public InterfazG() {
        initComponents();
        Simulador.inicializar();
        model = tablaRes.getModel();
        barra = jProgressBar1;
        btn_iniciar = boton_iniciar;
        actualizarTablaRes(Simulador.procesos_listos);
        
    }
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        new InterfazG().setVisible(true);
        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(new Runnable() {
          //  public void run() {
                //new InterfazG().setVisible(true);
         //   }
        //});
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        boton_iniciar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        criterios = new javax.swing.JComboBox<>();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        boton_parar = new javax.swing.JButton();
        boton_reiniciar = new javax.swing.JButton();

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Crear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        boton_iniciar.setText("Iniciar");
        boton_iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_iniciarActionPerformed(evt);
            }
        });

        tablaRes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Estado", "Tiempo Requerido", "Tiempo En Ejecucionl", "Recursos", "Recurso en uso?"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaRes);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("TABLA DE PROCESOS");

        jButton4.setText("Salir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        criterios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FIFO", "SJN", "RR" }));
        criterios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criteriosActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Barra de Progreso");

        jLabel3.setText("Seleccione Algoritmo");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("SIMULADOR DE ADMINISTRADOR DE PROCESOS");

        boton_parar.setText("Parar");
        boton_parar.setEnabled(false);
        boton_parar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_pararActionPerformed(evt);
            }
        });

        boton_reiniciar.setText("Reiniciar");
        boton_reiniciar.setEnabled(false);
        boton_reiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_reiniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jButton1)
                                .addGap(57, 57, 57)
                                .addComponent(criterios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(boton_iniciar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boton_parar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boton_reiniciar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jLabel4))
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(criterios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_iniciar)
                    .addComponent(boton_parar)
                    .addComponent(boton_reiniciar))
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    //cuando se hace clic en el boton de iniciar
    private void boton_iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_iniciarActionPerformed
        
        if(hilo_ejecutando!=null){
            if(hilo_ejecutando.isAlive()){
                hilo_ejecutando.resume();
                boton_iniciar.setEnabled(false);
                boton_parar.setEnabled(true);
                boton_reiniciar.setEnabled(false);
            }
        }else{
            String a=criterios.getSelectedItem().toString();
            switch(a){
                case "FIFO":
                    //Se inicia la ejecucion del algoritmo FIFO
                    hilo_ejecutando = new Thread(new AlgirtmoFIFO()); //Crea un nuevo hilo
                    hilo_ejecutando.start();
                    boton_iniciar.setEnabled(false);
                    boton_parar.setEnabled(true);
                break;
                case "SJN":
                    //Se inicia la ejecucion del algoritmo FIFO
                    hilo_ejecutando = new Thread(new AlgoritmoSJN()); //Crea un nuevo hilo
                    hilo_ejecutando.start();
                    boton_iniciar.setEnabled(false);
                    boton_parar.setEnabled(true);
                break;
                case "RR":
                break;
            }
        }
    }//GEN-LAST:event_boton_iniciarActionPerformed

    private void criteriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criteriosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_criteriosActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //Pregunta si desea crar otro programa:
            //opciones disponibles a seleccionar
            String x1="MANUAL";
            //Objeto de opciones
            Object[] options = {x1, "ALEATORIO"};
            //obtener que desea hacer el usuario
            int r = JOptionPane.showOptionDialog(null,"¿Desea crear un proceso...?\n",
            " - CREAR PROCESO -",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,x1);
            
            //creamos un objeto random para obtener numeros aleatorios
            Random rd = new Random();
            if (r==JOptionPane.YES_OPTION){
                //Si es manual
                Proceso p = new Proceso(); //se crea un proceso
                p.setPid(Simulador.secuencia_id); //se obtiene el id que sigue para el proceso
                Simulador.secuencia_id++; //se incrementa el contador de id
                p.setNombre(JOptionPane.showInputDialog(null,"Teclea el Nombre del proceso\n")); // se asigna un nombre al proceso
                p.setTiempoRequerido(Integer.parseInt(JOptionPane.showInputDialog(null,"Teclea el Tiempo Requerido del Proceso\n"))); //se asigna el tiempo que el proceso requiere
                //Se genera un numero aleatorio para el recurso a utilizar
                int i = rd.nextInt((Simulador.recursos.length-1)-0)+1;
                //Se asigna que el recurso untilizara que recurso
                p.setRecurso(i);
                //Se introduce el proceso a la lista de procesos creados/listos
                Simulador.introducirProcesoALista(p, Simulador.procesos_listos);
            }
            else {
                //Si es aleatorio
                //Se obtiene un numero aleatorio para el recurso
                int i = rd.nextInt((Simulador.recursos.length-1)-0)+1;
                //Se obtiene un numero para el tiempo que el recurso necesita (numero del 2 al 3)
                int j = rd.nextInt(13-2)+1;
                //Se crea el proceso con el id que sigue
                Proceso p = new Proceso(Simulador.secuencia_id, "Proceso "+ Simulador.secuencia_id, 0, j, i);
                //Se incrementa el id
                Simulador.secuencia_id++;
                //se introduce el proceso a la lista
                Simulador.introducirProcesoALista(p, Simulador.procesos_listos);
            }
            actualizarTablaRes(Simulador.procesos_listos);
            
    }//GEN-LAST:event_jButton1ActionPerformed

    private void boton_pararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_pararActionPerformed
        // TODO add your handling code here:
        if(hilo_ejecutando.isAlive()){
            hilo_ejecutando.suspend();
            boton_iniciar.setText("reanudar");
            boton_iniciar.setEnabled(true);
            boton_parar.setEnabled(false);
            boton_reiniciar.setEnabled(true);
        }
    }//GEN-LAST:event_boton_pararActionPerformed

    private void boton_reiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_reiniciarActionPerformed
        // TODO add your handling code here:
        hilo_ejecutando.stop();
        Simulador.inicializar();
        barra.setValue(0);
        btn_iniciar.setText("Iniciar");
        btn_iniciar.setEnabled(true);
        boton_parar.setEnabled(false);
        boton_reiniciar.setEnabled(false);
        hilo_ejecutando = null;
        actualizarTablaRes(Simulador.procesos_listos);
        
    }//GEN-LAST:event_boton_reiniciarActionPerformed
    
    //Métodos
    public static void actulizarBarraDeProgreso(int porcentaje){
        //Se actualiza el progreso de la barra
        barra.setValue(porcentaje);
    }
    
    //Metodo que se utiliza para actualizar la tabla con las caracteristicas de los procesos
    public static void actualizarTablaRes(Proceso ListaP[]) {
     //Se recorre la lista de procesos
     for (int x=0; x<ListaP.length; x++) {
         //Mientras se encuentre un proceso en la posicion x se rellenara un renglon con su informacion
         //model es un auxiliar para meter la informacion a la tabla
         if(ListaP[x]!=null){
            //x indica el renglon
            //el numero que le sigue indica la columna
            model.setValueAt(ListaP[x].getPid(), x, 0);
            model.setValueAt(ListaP[x].getNombre(), x, 1);
            //Se obitene el nombre del estado en el que se encuentra el proceso
            model.setValueAt(Proceso.NombreDeEstado(ListaP[x].getEstado()), x, 2);
            model.setValueAt(ListaP[x].getTiempoRequerido(), x, 3);
            model.setValueAt(ListaP[x].getTiempo_de_ejecucion(), x, 4);
            //Se obitene el nombre del recurso que utiliza
            model.setValueAt(Simulador.ObtenerNombreDelRecurso(ListaP[x].getRecurso()), x, 5);
            if(ListaP[x].tieneSuRecursoAsignado()){
                model.setValueAt("Si", x, 6);
            }else{
                model.setValueAt("No", x, 6);
            }
         }
      } 
    }
    
    //Al terminar el algoritmo se reinicializa la lista de procesos y la barra vuelve a cero
    public static void algoritmoTerminado(){
        Simulador.inicializar();
        barra.setValue(0);
        btn_iniciar.setText("Iniciar");
    }
    
    //Variables para la interfaz grafica
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_iniciar;
    private javax.swing.JButton boton_parar;
    private javax.swing.JButton boton_reiniciar;
    private javax.swing.JComboBox<String> criterios;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tablaRes;
    // End of variables declaration//GEN-END:variables
}
