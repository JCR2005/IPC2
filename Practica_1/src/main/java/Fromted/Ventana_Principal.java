/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Fromted;

import Backend.Imagenes;
import Backend.Procesos;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author carlosrodriguez
 */
public class Ventana_Principal extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    static JButton[] botones =new JButton[9];; 
    static Movimientos mt=new Movimientos();
    static Solicitud st=new Solicitud();
    static Consulta ct=new Consulta();
    static Autorizacion at=new Autorizacion();
    static Cancelacion cat=new Cancelacion();
    Procesos proceso=new Procesos();
    
    public Ventana_Principal() {
        initComponents();
        botones[0]=boton_solicitud;
        botones[1]=boton_movimiento;
        botones[2]=boton_consulta;
        botones[3]=boton_autorizacion;
        botones[4]=boton_cancelacion;
        botones[5]=boton_estado_cuenta;
        botones[6]=boton_listado_tarjetas;
        botones[7]=boton_listado_solicitudes;
        botones[8]=boton_reportes;
        Imagenes.logo(jLabel1);
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ejecucion = new javax.swing.JLabel();
        boton_cancelacion = new javax.swing.JButton();
        boton_consulta = new javax.swing.JButton();
        boton_autorizacion = new javax.swing.JButton();
        boton_listado_solicitudes = new javax.swing.JButton();
        boton_listado_tarjetas = new javax.swing.JButton();
        boton_reportes = new javax.swing.JButton();
        boton_estado_cuenta = new javax.swing.JButton();
        boton_cerrar = new javax.swing.JButton();
        boton_solicitud = new javax.swing.JButton();
        boton_movimiento = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        boton_inicio = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        boton_ayuda = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manejo De Tarjetas De Credito");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(228, 207, 154));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 650));
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 650));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 650));
        jPanel1.setLayout(null);

        ejecucion.setFont(new java.awt.Font("Manjari Thin", 3, 24)); // NOI18N
        ejecucion.setForeground(new java.awt.Color(44, 68, 85));
        ejecucion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ejecucion.setInheritsPopupMenu(false);
        ejecucion.setRequestFocusEnabled(false);
        ejecucion.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(ejecucion);
        ejecucion.setBounds(610, 90, 390, 33);

        boton_cancelacion.setBackground(new java.awt.Color(255, 255, 254));
        boton_cancelacion.setFont(new java.awt.Font("Century Schoolbook L", 3, 12)); // NOI18N
        boton_cancelacion.setForeground(new java.awt.Color(119, 136, 147));
        boton_cancelacion.setText("CANCELACION DE TARJETA");
        boton_cancelacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_cancelacion.setEnabled(true
        );
        boton_cancelacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelacionActionPerformed(evt);
            }
        });
        jPanel1.add(boton_cancelacion);
        boton_cancelacion.setBounds(0, 320, 220, 40);

        boton_consulta.setBackground(new java.awt.Color(255, 255, 254));
        boton_consulta.setFont(new java.awt.Font("Century Schoolbook L", 3, 15)); // NOI18N
        boton_consulta.setForeground(new java.awt.Color(119, 136, 147));
        boton_consulta.setText("CONSULTAR TARJETA");
        boton_consulta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_consulta.setEnabled(true
        );
        boton_consulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_consultaActionPerformed(evt);
            }
        });
        jPanel1.add(boton_consulta);
        boton_consulta.setBounds(0, 200, 220, 40);

        boton_autorizacion.setBackground(new java.awt.Color(255, 255, 254));
        boton_autorizacion.setFont(new java.awt.Font("Century Schoolbook L", 3, 12)); // NOI18N
        boton_autorizacion.setForeground(new java.awt.Color(119, 136, 147));
        boton_autorizacion.setText("AUTORIZACION DE TARJETA");
        boton_autorizacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_autorizacion.setEnabled(true
        );
        boton_autorizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_autorizacionActionPerformed(evt);
            }
        });
        jPanel1.add(boton_autorizacion);
        boton_autorizacion.setBounds(0, 260, 220, 40);

        boton_listado_solicitudes.setBackground(new java.awt.Color(255, 255, 254));
        boton_listado_solicitudes.setFont(new java.awt.Font("Century Schoolbook L", 3, 15)); // NOI18N
        boton_listado_solicitudes.setForeground(new java.awt.Color(119, 136, 147));
        boton_listado_solicitudes.setText("LISTA DE SOLICITUDES");
        boton_listado_solicitudes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_listado_solicitudes.setEnabled(true
        );
        boton_listado_solicitudes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_listado_solicitudesActionPerformed(evt);
            }
        });
        jPanel1.add(boton_listado_solicitudes);
        boton_listado_solicitudes.setBounds(0, 500, 220, 40);

        boton_listado_tarjetas.setBackground(new java.awt.Color(255, 255, 254));
        boton_listado_tarjetas.setFont(new java.awt.Font("Century Schoolbook L", 3, 15)); // NOI18N
        boton_listado_tarjetas.setForeground(new java.awt.Color(119, 136, 147));
        boton_listado_tarjetas.setText("LISTA DE TARJETAS");
        boton_listado_tarjetas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_listado_tarjetas.setEnabled(true
        );
        boton_listado_tarjetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_listado_tarjetasActionPerformed(evt);
            }
        });
        jPanel1.add(boton_listado_tarjetas);
        boton_listado_tarjetas.setBounds(0, 440, 220, 40);

        boton_reportes.setBackground(new java.awt.Color(255, 255, 254));
        boton_reportes.setFont(new java.awt.Font("Century Schoolbook L", 3, 15)); // NOI18N
        boton_reportes.setForeground(new java.awt.Color(119, 136, 147));
        boton_reportes.setText("REPORTES");
        boton_reportes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_reportes.setEnabled(true
        );
        boton_reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_reportesActionPerformed(evt);
            }
        });
        jPanel1.add(boton_reportes);
        boton_reportes.setBounds(0, 560, 220, 40);

        boton_estado_cuenta.setBackground(new java.awt.Color(255, 255, 254));
        boton_estado_cuenta.setFont(new java.awt.Font("Century Schoolbook L", 3, 15)); // NOI18N
        boton_estado_cuenta.setForeground(new java.awt.Color(119, 136, 147));
        boton_estado_cuenta.setText("ESTADO DE CUENTA");
        boton_estado_cuenta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_estado_cuenta.setEnabled(true
        );
        boton_estado_cuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_estado_cuentaActionPerformed(evt);
            }
        });
        jPanel1.add(boton_estado_cuenta);
        boton_estado_cuenta.setBounds(0, 380, 220, 40);

        boton_cerrar.setBackground(new java.awt.Color(255, 255, 254));
        boton_cerrar.setFont(new java.awt.Font("Century Schoolbook L", 3, 15)); // NOI18N
        boton_cerrar.setForeground(new java.awt.Color(119, 136, 147));
        boton_cerrar.setText("Cerrar");
        boton_cerrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_cerrar.setEnabled(true
        );
        boton_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cerrarActionPerformed(evt);
            }
        });
        jPanel1.add(boton_cerrar);
        boton_cerrar.setBounds(40, 620, 140, 20);

        boton_solicitud.setBackground(new java.awt.Color(255, 255, 254));
        boton_solicitud.setFont(new java.awt.Font("Century Schoolbook L", 3, 15)); // NOI18N
        boton_solicitud.setForeground(new java.awt.Color(119, 136, 147));
        boton_solicitud.setText("SOLICITUD");
        boton_solicitud.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_solicitud.setEnabled(true
        );
        boton_solicitud.setFocusCycleRoot(true);
        boton_solicitud.setFocusTraversalPolicyProvider(true);
        boton_solicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_solicitudActionPerformed(evt);
            }
        });
        jPanel1.add(boton_solicitud);
        boton_solicitud.setBounds(0, 80, 220, 40);

        boton_movimiento.setBackground(new java.awt.Color(255, 255, 254));
        boton_movimiento.setFont(new java.awt.Font("Century Schoolbook L", 3, 15)); // NOI18N
        boton_movimiento.setForeground(new java.awt.Color(119, 136, 147));
        boton_movimiento.setText("MOVIMIENTO");
        boton_movimiento.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_movimiento.setEnabled(true
        );
        boton_movimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_movimientoActionPerformed(evt);
            }
        });
        jPanel1.add(boton_movimiento);
        boton_movimiento.setBounds(0, 140, 220, 40);

        jPanel3.setBackground(new java.awt.Color(44, 68, 85));
        jPanel3.setPreferredSize(new java.awt.Dimension(230, 570));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        boton_inicio.setBackground(new java.awt.Color(255, 255, 254));
        boton_inicio.setFont(new java.awt.Font("Century Schoolbook L", 3, 15)); // NOI18N
        boton_inicio.setForeground(new java.awt.Color(119, 136, 147));
        boton_inicio.setText("inicio");
        boton_inicio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_inicio.setEnabled(true
        );
        boton_inicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_inicioActionPerformed(evt);
            }
        });
        jPanel3.add(boton_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 120, 30));

        jPanel1.add(jPanel3);
        jPanel3.setBounds(0, 0, 230, 650);

        jPanel2.setBackground(new java.awt.Color(44, 68, 85));
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 80));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(680, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(220, 0, 790, 80);

        boton_ayuda.setBackground(new java.awt.Color(255, 255, 254));
        boton_ayuda.setFont(new java.awt.Font("AvantGarde LT Medium", 3, 15)); // NOI18N
        boton_ayuda.setForeground(new java.awt.Color(119, 136, 147));
        boton_ayuda.setText("ayuda");
        boton_ayuda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_ayuda.setEnabled(true
        );
        boton_ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_ayudaActionPerformed(evt);
            }
        });
        jPanel1.add(boton_ayuda);
        boton_ayuda.setBounds(230, 620, 30, 30);

        jPanel4.setBackground(new java.awt.Color(228, 207, 154));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel4);
        jPanel4.setBounds(270, 130, 730, 490);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

   static void cambiar_estado_botones(JButton boton_activo, JButton[] inactivos ){
    
        
        for (int i = 0; i < inactivos.length; i++) {
            
            if (inactivos[i].getWidth()==270){
                inactivos[i].setForeground(new java.awt.Color(119, 136, 147));
                inactivos[i].setBackground(new java.awt.Color(255,255,254));
                inactivos[i].setSize(220, 40);  
            }
                 
        

        }
        
        boton_activo.setForeground(new java.awt.Color(255,255,254));
        boton_activo.setBackground(new java.awt.Color(178,195,212));
        boton_activo.setSize(270, 40);
    
       
        ejecucion.setText(boton_activo.getText());
        
    }
    
   
    private void boton_solicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_solicitudActionPerformed
        
        cambiar_estado_botones(boton_solicitud,botones);
        ocultar_paneles();
        jPanel4.add(st);
        st.setVisible(true);
        jPanel4.revalidate();
        jPanel4.repaint();
    }//GEN-LAST:event_boton_solicitudActionPerformed

    private void boton_movimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_movimientoActionPerformed
        cambiar_estado_botones( boton_movimiento,botones);
        
        ocultar_paneles();
        jPanel4.add(mt);
   
        mt.setVisible(true);
        jPanel4.revalidate();
        jPanel4.repaint();
    }//GEN-LAST:event_boton_movimientoActionPerformed

    private void boton_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_consultaActionPerformed
       
       cambiar_estado_botones( boton_consulta,botones);
        ocultar_paneles();
        jPanel4.add(ct);
        ct.setVisible(true);
        jPanel4.revalidate();
        jPanel4.repaint();
    }//GEN-LAST:event_boton_consultaActionPerformed

    private void boton_autorizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_autorizacionActionPerformed
        cambiar_estado_botones( boton_autorizacion,botones);
        ocultar_paneles();
        jPanel4.add(at);
        at.setVisible(true);
        jPanel4.revalidate();
        jPanel4.repaint();
        
    }//GEN-LAST:event_boton_autorizacionActionPerformed

    private void boton_cancelacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelacionActionPerformed
       cambiar_estado_botones( boton_cancelacion,botones);
       ocultar_paneles();
        jPanel4.add(cat);
        cat.setVisible(true);
        jPanel4.revalidate();
        jPanel4.repaint();
    }//GEN-LAST:event_boton_cancelacionActionPerformed

    private void boton_listado_solicitudesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_listado_solicitudesActionPerformed
        cambiar_estado_botones(boton_listado_solicitudes,botones);
    }//GEN-LAST:event_boton_listado_solicitudesActionPerformed

    private void boton_listado_tarjetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_listado_tarjetasActionPerformed
       cambiar_estado_botones( boton_listado_tarjetas,botones);
    }//GEN-LAST:event_boton_listado_tarjetasActionPerformed

    private void boton_reportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_reportesActionPerformed
        cambiar_estado_botones( boton_reportes,botones);
    }//GEN-LAST:event_boton_reportesActionPerformed

    private void boton_estado_cuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_estado_cuentaActionPerformed
        cambiar_estado_botones( boton_estado_cuenta,botones);
    }//GEN-LAST:event_boton_estado_cuentaActionPerformed

    private void boton_inicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_inicioActionPerformed
        
    }//GEN-LAST:event_boton_inicioActionPerformed

    private void boton_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cerrarActionPerformed
      
    }//GEN-LAST:event_boton_cerrarActionPerformed

    private void boton_ayudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_ayudaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boton_ayudaActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Ventana_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana_Principal().setVisible(true);
            }
        });
    }

    
    private void ocultar_paneles(){
        st.setVisible(false);
        mt.setVisible(false);
        ct.setVisible(false);
        at.setVisible(false);
        cat.setVisible(false);
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_autorizacion;
    private javax.swing.JButton boton_ayuda;
    private javax.swing.JButton boton_cancelacion;
    private javax.swing.JButton boton_cerrar;
    private javax.swing.JButton boton_consulta;
    private javax.swing.JButton boton_estado_cuenta;
    private javax.swing.JButton boton_inicio;
    private javax.swing.JButton boton_listado_solicitudes;
    private javax.swing.JButton boton_listado_tarjetas;
    private javax.swing.JButton boton_movimiento;
    private javax.swing.JButton boton_reportes;
    private javax.swing.JButton boton_solicitud;
    private static javax.swing.JLabel ejecucion;
    private javax.swing.JLabel jLabel1;
    private static javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
