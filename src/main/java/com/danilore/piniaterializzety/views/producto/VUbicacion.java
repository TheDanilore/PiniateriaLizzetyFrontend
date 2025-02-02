package com.danilore.piniaterializzety.views.producto;

import com.danilore.piniaterializzety.models.producto.CategoriaProducto;
import com.danilore.piniaterializzety.models.producto.UnidadMedida;
import com.danilore.piniaterializzety.models.producto.Proveedor;
import com.danilore.piniaterializzety.models.producto.Ubicacion;
/**
 *
 * @author ASUS
 */
public final class VUbicacion extends javax.swing.JInternalFrame {

    public VUbicacion() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTextoCrearOEditar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        pnaelContent = new javax.swing.JPanel();
        panelGuardar = new javax.swing.JPanel();
        panelBotonesGuardar = new javax.swing.JPanel();
        cardGuardar = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnActualizar1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JLabel();
        panelContentGuardar = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtNombreUsuario2 = new javax.swing.JTextField();
        txtIdUsuario = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtNombreUsuario1 = new javax.swing.JTextField();
        panelVer = new javax.swing.JPanel();
        panelContentVer = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        lblCodigo1 = new javax.swing.JLabel();
        lblCodigo2 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        panelBotonesVer = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        btnLimpiar1 = new javax.swing.JLabel();

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
        jLabel10.setText("Ubicación");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHeaderLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnaelContent.setLayout(new java.awt.CardLayout());

        cardGuardar.setLayout(new java.awt.CardLayout());

        jPanel12.setBackground(new java.awt.Color(19, 227, 19));

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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

        cardGuardar.add(jPanel12, "card2");

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

        cardGuardar.add(jPanel5, "card4");

        jPanel6.setBackground(new java.awt.Color(19, 227, 19));

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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
                .addContainerGap(175, Short.MAX_VALUE)
                .addComponent(cardGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
        );
        panelBotonesGuardarLayout.setVerticalGroup(
            panelBotonesGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesGuardarLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panelBotonesGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cardGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setText("CÓDIGO:");

        txtNombreUsuario2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombreUsuario2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreUsuario2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNombreUsuario2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreUsuario2ActionPerformed(evt);
            }
        });
        txtNombreUsuario2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreUsuario2KeyTyped(evt);
            }
        });

        txtIdUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtIdUsuario.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtIdUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIdUsuario.setEnabled(false);

        lblCodigo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCodigo.setText("ID:");

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("DESCRIPCIÓN:");

        txtNombreUsuario1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombreUsuario1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombreUsuario1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNombreUsuario1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreUsuario1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelContentGuardarLayout = new javax.swing.GroupLayout(panelContentGuardar);
        panelContentGuardar.setLayout(panelContentGuardarLayout);
        panelContentGuardarLayout.setHorizontalGroup(
            panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentGuardarLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContentGuardarLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(10, 10, 10)
                        .addComponent(txtNombreUsuario1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                    .addGroup(panelContentGuardarLayout.createSequentialGroup()
                        .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreUsuario2)
                            .addComponent(txtIdUsuario))))
                .addGap(100, 100, 100))
        );
        panelContentGuardarLayout.setVerticalGroup(
            panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentGuardarLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreUsuario2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout panelGuardarLayout = new javax.swing.GroupLayout(panelGuardar);
        panelGuardar.setLayout(panelGuardarLayout);
        panelGuardarLayout.setHorizontalGroup(
            panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBotonesGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelContentGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGuardarLayout.setVerticalGroup(
            panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGuardarLayout.createSequentialGroup()
                .addGap(0, 197, Short.MAX_VALUE)
                .addComponent(panelBotonesGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelGuardarLayout.createSequentialGroup()
                    .addComponent(panelContentGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 145, Short.MAX_VALUE)))
        );

        pnaelContent.add(panelGuardar, "card2");

        panelVer.setBackground(new java.awt.Color(236, 233, 233));

        panelContentVer.setBackground(new java.awt.Color(236, 233, 233));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setText("Código:");

        lblCodigo1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCodigo1.setText("ID:");

        lblCodigo2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblCodigo2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCodigo2.setText("0000000000");
        lblCodigo2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(76, 170, 255), 1, true));

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("ubicacion desconocida");
        jLabel21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(76, 170, 255), 1, true));

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setText("Descripción");

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("ubicacion desconocida");
        jLabel23.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(76, 170, 255), 1, true));

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("ubicacion desconocida");
        jLabel24.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(76, 170, 255), 1, true));
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setText("Fecha Creación:");

        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setText("Fecha Edición:");

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("ubicacion desconocida");
        jLabel27.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(76, 170, 255), 1, true));

        javax.swing.GroupLayout panelContentVerLayout = new javax.swing.GroupLayout(panelContentVer);
        panelContentVer.setLayout(panelContentVerLayout);
        panelContentVerLayout.setHorizontalGroup(
            panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentVerLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContentVerLayout.createSequentialGroup()
                        .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo1)
                            .addComponent(jLabel20))
                        .addGap(65, 65, 65)
                        .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCodigo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelContentVerLayout.createSequentialGroup()
                        .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(70, 70, 70))
        );
        panelContentVerLayout.setVerticalGroup(
            panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentVerLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo2)
                    .addComponent(lblCodigo1))
                .addGap(18, 18, 18)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panelBotonesVer.setBackground(new java.awt.Color(236, 233, 233));

        jPanel8.setBackground(new java.awt.Color(37, 122, 255));

        btnLimpiar1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLimpiar1.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLimpiar1.setText("Regresar al Listado");
        btnLimpiar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLimpiar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelBotonesVerLayout = new javax.swing.GroupLayout(panelBotonesVer);
        panelBotonesVer.setLayout(panelBotonesVerLayout);
        panelBotonesVerLayout.setHorizontalGroup(
            panelBotonesVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesVerLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(68, 68, 68))
        );
        panelBotonesVerLayout.setVerticalGroup(
            panelBotonesVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesVerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelVerLayout = new javax.swing.GroupLayout(panelVer);
        panelVer.setLayout(panelVerLayout);
        panelVerLayout.setHorizontalGroup(
            panelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBotonesVer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelContentVer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelVerLayout.setVerticalGroup(
            panelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVerLayout.createSequentialGroup()
                .addGap(0, 211, Short.MAX_VALUE)
                .addComponent(panelBotonesVer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelVerLayout.createSequentialGroup()
                    .addComponent(panelContentVer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 78, Short.MAX_VALUE)))
        );

        pnaelContent.add(panelVer, "card3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnaelContent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 274, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(47, 47, 47)
                    .addComponent(pnaelContent, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)))
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

    private void txtNombreUsuario2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreUsuario2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreUsuario2ActionPerformed

    private void txtNombreUsuario2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreUsuario2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreUsuario2KeyTyped

    private void txtNombreUsuario1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreUsuario1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreUsuario1KeyTyped



    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel btnActualizar1;
    public javax.swing.JLabel btnGuardar;
    public javax.swing.JLabel btnLimpiar;
    public javax.swing.JLabel btnLimpiar1;
    private javax.swing.JPanel cardGuardar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    public javax.swing.JLabel lblCodigo;
    public javax.swing.JLabel lblCodigo1;
    public javax.swing.JLabel lblCodigo2;
    public javax.swing.JLabel lblTextoCrearOEditar;
    private javax.swing.JPanel panelBotonesGuardar;
    private javax.swing.JPanel panelBotonesVer;
    private javax.swing.JPanel panelContentGuardar;
    private javax.swing.JPanel panelContentVer;
    private javax.swing.JPanel panelGuardar;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelVer;
    private javax.swing.JPanel pnaelContent;
    public javax.swing.JTextField txtIdUsuario;
    public javax.swing.JTextField txtNombreUsuario1;
    public javax.swing.JTextField txtNombreUsuario2;
    // End of variables declaration//GEN-END:variables
}
