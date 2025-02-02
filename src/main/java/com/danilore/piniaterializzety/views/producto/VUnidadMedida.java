package com.danilore.piniaterializzety.views.producto;

import com.danilore.piniaterializzety.models.producto.CategoriaProducto;
import com.danilore.piniaterializzety.models.producto.UnidadMedida;
import com.danilore.piniaterializzety.models.producto.Proveedor;
import com.danilore.piniaterializzety.models.producto.Ubicacion;
/**
 *
 * @author ASUS
 */
public final class VUnidadMedida extends javax.swing.JInternalFrame {

    public VUnidadMedida() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTextoCrearOEditar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panelGuardarContent = new javax.swing.JPanel();
        panelBotonesGuardar = new javax.swing.JPanel();
        CardGuardarActualizar = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnActualizar1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JLabel();
        panelContentGuardar = new javax.swing.JPanel();
        txtIdGuardar = new javax.swing.JTextField();
        txtNombreUsuario1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();

        lblTextoCrearOEditar.setFont(new java.awt.Font("72", 1, 14)); // NOI18N
        lblTextoCrearOEditar.setForeground(new java.awt.Color(51, 153, 0));
        lblTextoCrearOEditar.setText("Registra una Nueva Unidad de Medida");

        setBackground(new java.awt.Color(236, 233, 233));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Registro de Categorias");

        jPanel1.setBackground(new java.awt.Color(236, 233, 233));

        panelHeader.setBackground(new java.awt.Color(49, 168, 250));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("UNIDAD DE MEDIDA");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        CardGuardarActualizar.setLayout(new java.awt.CardLayout());

        jPanel12.setBackground(new java.awt.Color(19, 227, 19));

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardar.setText("Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        CardGuardarActualizar.add(jPanel12, "card2");

        jPanel5.setBackground(new java.awt.Color(51, 153, 255));

        btnActualizar1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnActualizar1.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnActualizar1.setText("Actualizar");
        btnActualizar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnActualizar1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnActualizar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        CardGuardarActualizar.add(jPanel5, "card4");

        jPanel6.setBackground(new java.awt.Color(19, 227, 19));

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout panelBotonesGuardarLayout = new javax.swing.GroupLayout(panelBotonesGuardar);
        panelBotonesGuardar.setLayout(panelBotonesGuardarLayout);
        panelBotonesGuardarLayout.setHorizontalGroup(
            panelBotonesGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesGuardarLayout.createSequentialGroup()
                .addContainerGap(162, Short.MAX_VALUE)
                .addComponent(CardGuardarActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(162, Short.MAX_VALUE))
        );
        panelBotonesGuardarLayout.setVerticalGroup(
            panelBotonesGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesGuardarLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(panelBotonesGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CardGuardarActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        txtIdGuardar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtIdGuardar.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtIdGuardar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIdGuardar.setEnabled(false);

        txtNombreUsuario1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombreUsuario1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreUsuario1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNombreUsuario1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreUsuario1KeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("DESCRIPCIÓN:");

        lblCodigo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCodigo.setText("CÓDIGO:");

        javax.swing.GroupLayout panelContentGuardarLayout = new javax.swing.GroupLayout(panelContentGuardar);
        panelContentGuardar.setLayout(panelContentGuardarLayout);
        panelContentGuardarLayout.setHorizontalGroup(
            panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentGuardarLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContentGuardarLayout.createSequentialGroup()
                        .addComponent(lblCodigo)
                        .addGap(49, 49, 49)
                        .addComponent(txtIdGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                        .addGap(219, 219, 219))
                    .addGroup(panelContentGuardarLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreUsuario1)
                        .addGap(135, 135, 135))))
        );
        panelContentGuardarLayout.setVerticalGroup(
            panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentGuardarLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtIdGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtNombreUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelGuardarContentLayout = new javax.swing.GroupLayout(panelGuardarContent);
        panelGuardarContent.setLayout(panelGuardarContentLayout);
        panelGuardarContentLayout.setHorizontalGroup(
            panelGuardarContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBotonesGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelGuardarContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelContentGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGuardarContentLayout.setVerticalGroup(
            panelGuardarContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGuardarContentLayout.createSequentialGroup()
                .addContainerGap(150, Short.MAX_VALUE)
                .addComponent(panelBotonesGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelGuardarContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelGuardarContentLayout.createSequentialGroup()
                    .addComponent(panelContentGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(84, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(panelGuardarContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelGuardarContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreUsuario1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreUsuario1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreUsuario1KeyTyped



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CardGuardarActualizar;
    public javax.swing.JLabel btnActualizar1;
    public javax.swing.JLabel btnGuardar;
    public javax.swing.JLabel btnLimpiar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JLabel lblCodigo;
    public javax.swing.JLabel lblTextoCrearOEditar;
    private javax.swing.JPanel panelBotonesGuardar;
    private javax.swing.JPanel panelContentGuardar;
    private javax.swing.JPanel panelGuardarContent;
    private javax.swing.JPanel panelHeader;
    public javax.swing.JTextField txtIdGuardar;
    public javax.swing.JTextField txtNombreUsuario1;
    // End of variables declaration//GEN-END:variables
}
