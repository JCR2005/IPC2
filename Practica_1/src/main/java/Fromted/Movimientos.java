/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Fromted;

import Backend.Procesos;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author carlosrodriguez
 */
public class Movimientos extends javax.swing.JPanel {

    /**
     * Creates new form Solicitud
     */
    Procesos proceso = new Procesos();

    public Movimientos() {
        initComponents();
        jPanel1.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        establecimiento = new javax.swing.JTextField();
        tipomovimiento = new javax.swing.JComboBox<>();
        boton_ayuda = new javax.swing.JButton();
        No_tarjeta = new javax.swing.JTextField();
        monto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        boton_procesar = new javax.swing.JButton();
        boton_cancelar = new javax.swing.JButton();
        fecha = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        descripcion = new javax.swing.JTextArea();
        boton_formulario = new javax.swing.JButton();
        boton_archivo_entrada = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setBackground(new java.awt.Color(228, 207, 154));
        setPreferredSize(new java.awt.Dimension(730, 490));
        setLayout(null);

        jPanel1.setBackground(new java.awt.Color(228, 207, 154));
        jPanel1.setLayout(null);

        establecimiento.setBackground(new java.awt.Color(158, 144, 100));
        establecimiento.setFont(new java.awt.Font("Comic Sans MS", 3, 15)); // NOI18N
        establecimiento.setForeground(new java.awt.Color(255, 255, 255));
        establecimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        establecimiento.setToolTipText("");
        establecimiento.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(establecimiento);
        establecimiento.setBounds(420, 230, 300, 30);

        tipomovimiento.setBackground(new java.awt.Color(158, 144, 100));
        tipomovimiento.setForeground(new java.awt.Color(255, 255, 255));
        tipomovimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo de Movimiento ...", "CARGO ", "ABONO", " " }));
        tipomovimiento.setToolTipText("");
        tipomovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipomovimientoActionPerformed(evt);
            }
        });
        jPanel1.add(tipomovimiento);
        tipomovimiento.setBounds(110, 140, 150, 32);

        boton_ayuda.setBackground(new java.awt.Color(44, 68, 85));
        boton_ayuda.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        boton_ayuda.setForeground(new java.awt.Color(255, 255, 255));
        boton_ayuda.setText("Ayuda");
        boton_ayuda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_ayuda.setEnabled(true
        );
        boton_ayuda.setFocusCycleRoot(true);
        boton_ayuda.setFocusTraversalPolicyProvider(true);
        boton_ayuda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton_ayudaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton_ayudaMouseExited(evt);
            }
        });
        boton_ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_ayudaActionPerformed(evt);
            }
        });
        jPanel1.add(boton_ayuda);
        boton_ayuda.setBounds(530, 460, 200, 30);

        No_tarjeta.setBackground(new java.awt.Color(158, 144, 100));
        No_tarjeta.setFont(new java.awt.Font("Comic Sans MS", 3, 15)); // NOI18N
        No_tarjeta.setForeground(new java.awt.Color(255, 255, 255));
        No_tarjeta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        No_tarjeta.setToolTipText("");
        No_tarjeta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(No_tarjeta);
        No_tarjeta.setBounds(420, 50, 300, 30);

        monto.setBackground(new java.awt.Color(158, 144, 100));
        monto.setFont(new java.awt.Font("Comic Sans MS", 3, 15)); // NOI18N
        monto.setForeground(new java.awt.Color(255, 255, 255));
        monto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        monto.setToolTipText("");
        monto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(monto);
        monto.setBounds(110, 230, 150, 30);

        jLabel1.setBackground(new java.awt.Color(20, 12, 77));
        jLabel1.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 49, 74));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Monto:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 240, 140, 18);

        jLabel2.setBackground(new java.awt.Color(20, 12, 77));
        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 49, 74));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("No. Tarjeta:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(310, 60, 130, 20);

        jLabel3.setBackground(new java.awt.Color(20, 12, 77));
        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 49, 74));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Fecha:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 60, 140, 18);

        jLabel4.setBackground(new java.awt.Color(20, 12, 77));
        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 49, 74));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Descripcion:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(300, 150, 140, 18);

        jLabel5.setBackground(new java.awt.Color(20, 12, 77));
        jLabel5.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 49, 74));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Establecimiento:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(290, 240, 140, 18);

        boton_procesar.setBackground(new java.awt.Color(44, 68, 85));
        boton_procesar.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        boton_procesar.setForeground(new java.awt.Color(255, 255, 255));
        boton_procesar.setText("Procesar Movimiento ");
        boton_procesar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_procesar.setEnabled(true
        );
        boton_procesar.setFocusCycleRoot(true);
        boton_procesar.setFocusTraversalPolicyProvider(true);
        boton_procesar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton_procesarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton_procesarMouseExited(evt);
            }
        });
        boton_procesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_procesarActionPerformed(evt);
            }
        });
        jPanel1.add(boton_procesar);
        boton_procesar.setBounds(270, 440, 200, 30);

        boton_cancelar.setBackground(new java.awt.Color(44, 68, 85));
        boton_cancelar.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        boton_cancelar.setForeground(new java.awt.Color(255, 255, 255));
        boton_cancelar.setText("Cancelar");
        boton_cancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_cancelar.setEnabled(true
        );
        boton_cancelar.setFocusCycleRoot(true);
        boton_cancelar.setFocusTraversalPolicyProvider(true);
        boton_cancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton_cancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton_cancelarMouseExited(evt);
            }
        });
        boton_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cancelarActionPerformed(evt);
            }
        });
        jPanel1.add(boton_cancelar);
        boton_cancelar.setBounds(0, 420, 200, 30);

        fecha.setBackground(new java.awt.Color(158, 144, 100));
        fecha.setFont(new java.awt.Font("Comic Sans MS", 3, 15)); // NOI18N
        fecha.setForeground(new java.awt.Color(255, 255, 255));
        fecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fecha.setToolTipText("");
        fecha.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fecha.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        fecha.setEnabled(false);
        jPanel1.add(fecha);
        fecha.setBounds(110, 50, 150, 30);

        descripcion.setBackground(new java.awt.Color(158, 144, 100));
        descripcion.setColumns(20);
        descripcion.setForeground(new java.awt.Color(255, 255, 255));
        descripcion.setLineWrap(true);
        descripcion.setRows(5);
        descripcion.setTabSize(0);
        descripcion.setAutoscrolls(false);
        descripcion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setViewportView(descripcion);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(420, 110, 300, 96);

        add(jPanel1);
        jPanel1.setBounds(0, 0, 730, 490);

        boton_formulario.setBackground(new java.awt.Color(44, 68, 85));
        boton_formulario.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        boton_formulario.setForeground(new java.awt.Color(255, 255, 255));
        boton_formulario.setText("formulario");
        boton_formulario.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_formulario.setEnabled(true
        );
        boton_formulario.setFocusCycleRoot(true);
        boton_formulario.setFocusTraversalPolicyProvider(true);
        boton_formulario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_formularioActionPerformed(evt);
            }
        });
        add(boton_formulario);
        boton_formulario.setBounds(215, 170, 300, 25);

        boton_archivo_entrada.setBackground(new java.awt.Color(44, 68, 85));
        boton_archivo_entrada.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        boton_archivo_entrada.setForeground(new java.awt.Color(255, 255, 255));
        boton_archivo_entrada.setText("Archivo de entrada");
        boton_archivo_entrada.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        boton_archivo_entrada.setEnabled(true
        );
        boton_archivo_entrada.setFocusCycleRoot(true);
        boton_archivo_entrada.setFocusTraversalPolicyProvider(true);
        boton_archivo_entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_archivo_entradaActionPerformed(evt);
            }
        });
        add(boton_archivo_entrada);
        boton_archivo_entrada.setBounds(215, 280, 300, 25);
    }// </editor-fold>//GEN-END:initComponents

    private void boton_archivo_entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_archivo_entradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boton_archivo_entradaActionPerformed

    private void boton_formularioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_formularioActionPerformed
        jPanel1.setVisible(true);
        fecha.setText(proceso.fechaActual());
        boton_archivo_entrada.setVisible(false);
        boton_formulario.setVisible(false);
    }//GEN-LAST:event_boton_formularioActionPerformed

    private void tipomovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipomovimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipomovimientoActionPerformed

    private void boton_ayudaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_ayudaMouseEntered
        boton_ayuda.setBackground(Color.white);
        boton_ayuda.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_boton_ayudaMouseEntered

    private void boton_ayudaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_ayudaMouseExited
        boton_ayuda.setBackground(new java.awt.Color(44, 68, 85));
        boton_ayuda.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_boton_ayudaMouseExited

    private void boton_ayudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_ayudaActionPerformed

    }//GEN-LAST:event_boton_ayudaActionPerformed

    private void boton_procesarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_procesarMouseEntered
        boton_procesar.setBackground(Color.GREEN);
    }//GEN-LAST:event_boton_procesarMouseEntered

    private void boton_procesarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_procesarMouseExited
        boton_procesar.setBackground(new java.awt.Color(44, 68, 85));
    }//GEN-LAST:event_boton_procesarMouseExited

    private void boton_procesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_procesarActionPerformed
        String tipo = (String) tipomovimiento.getSelectedItem();
        if (verificar_campos_Vacios(No_tarjeta.getText(), fecha.getText(), establecimiento.getText(), descripcion.getText(), monto.getText(), tipo)==false) {

           JOptionPane.showMessageDialog(null, "Llene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
               proceso.procesarMovimiento(No_tarjeta.getText(), fecha.getText(), establecimiento.getText(), descripcion.getText(), Double.valueOf(monto.getText()), tipo);
     
            } catch (NumberFormatException e) {
                 JOptionPane.showMessageDialog(null, "Monto no legible.", "Error", JOptionPane.ERROR_MESSAGE);
            }
               }

    }//GEN-LAST:event_boton_procesarActionPerformed

    private void boton_cancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_cancelarMouseEntered

        boton_cancelar.setBackground(Color.red);
    }//GEN-LAST:event_boton_cancelarMouseEntered

    private void boton_cancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_cancelarMouseExited
        boton_cancelar.setBackground(new java.awt.Color(44, 68, 85));
    }//GEN-LAST:event_boton_cancelarMouseExited

    private void boton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cancelarActionPerformed
        jPanel1.setVisible(false);
        boton_archivo_entrada.setVisible(true);
        boton_formulario.setVisible(true);
        reiniciar_formulario();
    }//GEN-LAST:event_boton_cancelarActionPerformed

    private boolean verificar_campos_Vacios(String nt, String f, String e, String d, String m, String tm){
    
        boolean verificacion;
        if (nt.isEmpty() || f.isEmpty() || e.isEmpty() || d.isEmpty() || m.isEmpty() || tm.isEmpty()) {
            verificacion=false;
        }else{
            verificacion=true;
        }
        
    
        return verificacion;
    
    
    }
    
    void reiniciar_formulario() {
        establecimiento.setText("");
        No_tarjeta.setText("");
        fecha.setText("");
        descripcion.setText("");
        monto.setText("");

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField No_tarjeta;
    private javax.swing.JButton boton_archivo_entrada;
    private javax.swing.JButton boton_ayuda;
    private javax.swing.JButton boton_cancelar;
    private javax.swing.JButton boton_formulario;
    private javax.swing.JButton boton_procesar;
    private javax.swing.JTextArea descripcion;
    private javax.swing.JTextField establecimiento;
    private javax.swing.JTextField fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField monto;
    private javax.swing.JComboBox<String> tipomovimiento;
    // End of variables declaration//GEN-END:variables
}