package Fromted;

import Backend.Procesos;
import Backend.Listado_De_Tarjetas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author carlosrodriguez
 */
public class Listado_De_Tarjeta extends javax.swing.JPanel {

    /**
     * Creates new form Solicitud
     */
    Procesos proceso = new Procesos();
    private JTable table;
    private JScrollPane scrollPane;
    public Listado_De_Tarjeta() {
        initComponents();
        mostrarDatos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        boton_ayuda = new javax.swing.JButton();
        boton_procesar = new javax.swing.JButton();
        borrar_filtros = new javax.swing.JButton();
        Tipo = new javax.swing.JComboBox<>();
        monto = new javax.swing.JTextField();
        despues_de = new javax.swing.JTextField();
        antes_de = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Estado = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(228, 207, 154));
        setPreferredSize(new java.awt.Dimension(730, 490));
        setLayout(null);

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
        add(boton_ayuda);
        boton_ayuda.setBounds(0, 460, 60, 30);

        boton_procesar.setBackground(new java.awt.Color(44, 68, 85));
        boton_procesar.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        boton_procesar.setForeground(new java.awt.Color(255, 255, 255));
        boton_procesar.setText("Procesar Busqueda");
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
        add(boton_procesar);
        boton_procesar.setBounds(0, 380, 150, 30);

        borrar_filtros.setBackground(new java.awt.Color(44, 68, 85));
        borrar_filtros.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        borrar_filtros.setForeground(new java.awt.Color(255, 255, 255));
        borrar_filtros.setText("Borrar Filtros");
        borrar_filtros.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        borrar_filtros.setEnabled(true
        );
        borrar_filtros.setFocusCycleRoot(true);
        borrar_filtros.setFocusTraversalPolicyProvider(true);
        borrar_filtros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                borrar_filtrosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                borrar_filtrosMouseExited(evt);
            }
        });
        borrar_filtros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrar_filtrosActionPerformed(evt);
            }
        });
        add(borrar_filtros);
        borrar_filtros.setBounds(0, 420, 120, 30);

        Tipo.setBackground(new java.awt.Color(158, 144, 100));
        Tipo.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        Tipo.setForeground(new java.awt.Color(255, 255, 255));
        Tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo", "NACIONAL", "REGIONAL", "INTERNACIONAL" }));
        add(Tipo);
        Tipo.setBounds(340, 400, 180, 26);
        add(monto);
        monto.setBounds(540, 440, 180, 24);
        add(despues_de);
        despues_de.setBounds(160, 440, 140, 24);
        add(antes_de);
        antes_de.setBounds(160, 400, 140, 24);
        add(nombre);
        nombre.setBounds(540, 400, 180, 24);

        jLabel1.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Antes de...");
        add(jLabel1);
        jLabel1.setBounds(200, 380, 80, 18);

        jLabel2.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Monto mayor a ...");
        add(jLabel2);
        jLabel2.setBounds(570, 470, 120, 20);

        Estado.setBackground(new java.awt.Color(158, 144, 100));
        Estado.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        Estado.setForeground(new java.awt.Color(255, 255, 255));
        Estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estado", "AUTORIZADA", "CANCELADA" }));
        add(Estado);
        Estado.setBounds(340, 440, 180, 26);

        jLabel3.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Depues de...");
        add(jLabel3);
        jLabel3.setBounds(190, 470, 90, 20);

        jLabel4.setFont(new java.awt.Font("Century Schoolbook L", 2, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Nombre");
        add(jLabel4);
        jLabel4.setBounds(600, 380, 60, 20);
    }// </editor-fold>//GEN-END:initComponents

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
        String tipo = (String) Tipo.getSelectedItem();
        String estado = (String) Estado.getSelectedItem();

        try {
            if (monto.getText().isEmpty()) {
                monto.setText("0");
            }
           
            proceso.ListadoTarjetas(tipo, nombre.getText(), Double.valueOf(monto.getText()), despues_de.getText(), antes_de.getText(), estado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Monto no legible.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        mostrarDatos();

    }//GEN-LAST:event_boton_procesarActionPerformed

    private void borrar_filtrosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrar_filtrosMouseEntered

        borrar_filtros.setBackground(Color.red);
    }//GEN-LAST:event_borrar_filtrosMouseEntered

    private void borrar_filtrosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrar_filtrosMouseExited
        borrar_filtros.setBackground(new java.awt.Color(44, 68, 85));
    }//GEN-LAST:event_borrar_filtrosMouseExited

    private void borrar_filtrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrar_filtrosActionPerformed

        reiniciar_formulario();
    }//GEN-LAST:event_borrar_filtrosActionPerformed

  void mostrarDatos() {
    // Usar null layout
    setLayout(null);

    // Eliminar el JScrollPane que contiene la tabla, si existe
    for (Component comp : getComponents()) {
        if (comp instanceof JScrollPane) {
            remove(comp);
        }
    }

    // Crear el arreglo de datos utilizando solo `numeroTarjeta`
    Object[][] datos = new Object[Listado_De_Tarjetas.getNumeroTarjeta().size()][7];
    for (int i = 0; i < Listado_De_Tarjetas.getNumeroTarjeta().size(); i++) {
        datos[i][0] = Listado_De_Tarjetas.getNumeroTarjeta().get(i);
        datos[i][1] = Listado_De_Tarjetas.getTipoTarjeta().get(i);
        datos[i][2] = Listado_De_Tarjetas.getLimite().get(i);
        datos[i][3] = Listado_De_Tarjetas.getNombreCliente().get(i);
        datos[i][4] = Listado_De_Tarjetas.getDireccion().get(i);
        datos[i][5] = Listado_De_Tarjetas.getEstadoTarjeta().get(i);
        datos[i][6] = Listado_De_Tarjetas.getFecha().get(i);
    }

    // Columnas de la tabla
    String[] columnas = {"NÚMERO TARJETA", "TIPO", "LÍMITE", "NOMBRE", "DIRECCIÓN", "ESTADO","FECHA"};

    // Crear el modelo de la tabla
    DefaultTableModel model = new DefaultTableModel(datos, columnas);
    JTable table = new JTable(model);

    // Colocar la tabla en un JScrollPane
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(10, 10, 720, 360);

    // Agregar el JScrollPane al JPanel
    add(scrollPane);

    // Asegurarse de que el panel se dibuje correctamente
    revalidate();
    repaint();
}


    void reiniciar_formulario() {

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Estado;
    private javax.swing.JComboBox<String> Tipo;
    private javax.swing.JTextField antes_de;
    private javax.swing.JButton borrar_filtros;
    private javax.swing.JButton boton_ayuda;
    private javax.swing.JButton boton_procesar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JTextField despues_de;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField monto;
    private javax.swing.JTextField nombre;
    // End of variables declaration//GEN-END:variables
}