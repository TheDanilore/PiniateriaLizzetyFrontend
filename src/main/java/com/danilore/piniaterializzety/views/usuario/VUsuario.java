package com.danilore.piniaterializzety.views.usuario;

/**
 *
 * @author ASUS
 */
public final class VUsuario extends javax.swing.JInternalFrame {

    public VUsuario() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTextoCrearOEditar = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        lblTextoEditarOCrearPermiso = new javax.swing.JLabel();
        pnaelContent = new javax.swing.JPanel();
        panelGuardar = new javax.swing.JPanel();
        panelBotonesGuardar = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JLabel();
        cardGuardar = new javax.swing.JPanel();
        panelBtnGuardar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JLabel();
        panelBtnActualizar = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        btnRegresarListado = new javax.swing.JLabel();
        panelContentGuardar = new javax.swing.JPanel();
        txtId = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlistRolUser = new javax.swing.JList<>();
        jLabel28 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        lblAvatar = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        btnEliminarImagen = new javax.swing.JLabel();
        panelVer = new javax.swing.JPanel();
        panelContentVer = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        lblCodigo222 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        lblFCreacion = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        lblFEdicion = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lblAvatar1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lblRoles = new javax.swing.JTextArea();
        lblCodigo1 = new javax.swing.JTextField();
        lblNombres = new javax.swing.JTextField();
        lblEmail = new javax.swing.JTextField();
        panelBotonesVer = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JLabel();

        lblTextoCrearOEditar.setFont(new java.awt.Font("72", 1, 14)); // NOI18N
        lblTextoCrearOEditar.setForeground(new java.awt.Color(51, 153, 0));
        lblTextoCrearOEditar.setText("Registra una Nueva Unidad de Medida");

        setBackground(new java.awt.Color(236, 233, 233));
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Registro");

        jPanel1.setBackground(new java.awt.Color(236, 233, 233));
        jPanel1.setPreferredSize(new java.awt.Dimension(495, 591));

        panelHeader.setBackground(new java.awt.Color(49, 168, 250));
        panelHeader.setPreferredSize(new java.awt.Dimension(613, 46));

        lblTextoEditarOCrearPermiso.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblTextoEditarOCrearPermiso.setForeground(new java.awt.Color(255, 255, 255));
        lblTextoEditarOCrearPermiso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTextoEditarOCrearPermiso.setText("USUARIO");
        lblTextoEditarOCrearPermiso.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTextoEditarOCrearPermiso, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTextoEditarOCrearPermiso, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnaelContent.setLayout(new java.awt.CardLayout());

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
            .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        cardGuardar.setLayout(new java.awt.CardLayout());

        panelBtnGuardar.setBackground(new java.awt.Color(19, 227, 19));

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardar.setText("Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelBtnGuardarLayout = new javax.swing.GroupLayout(panelBtnGuardar);
        panelBtnGuardar.setLayout(panelBtnGuardarLayout);
        panelBtnGuardarLayout.setHorizontalGroup(
            panelBtnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(panelBtnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBtnGuardarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelBtnGuardarLayout.setVerticalGroup(
            panelBtnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(panelBtnGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBtnGuardarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        cardGuardar.add(panelBtnGuardar, "card2");

        panelBtnActualizar.setBackground(new java.awt.Color(51, 153, 255));

        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnActualizar.setText("Actualizar");
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelBtnActualizarLayout = new javax.swing.GroupLayout(panelBtnActualizar);
        panelBtnActualizar.setLayout(panelBtnActualizarLayout);
        panelBtnActualizarLayout.setHorizontalGroup(
            panelBtnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(panelBtnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBtnActualizarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelBtnActualizarLayout.setVerticalGroup(
            panelBtnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(panelBtnActualizarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBtnActualizarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        cardGuardar.add(panelBtnActualizar, "card4");

        jPanel9.setBackground(new java.awt.Color(71, 173, 255));

        btnRegresarListado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRegresarListado.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresarListado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnRegresarListado.setText("Regresar");
        btnRegresarListado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegresarListado, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegresarListado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelBotonesGuardarLayout = new javax.swing.GroupLayout(panelBotonesGuardar);
        panelBotonesGuardar.setLayout(panelBotonesGuardarLayout);
        panelBotonesGuardarLayout.setHorizontalGroup(
            panelBotonesGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesGuardarLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(cardGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        panelBotonesGuardarLayout.setVerticalGroup(
            panelBotonesGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesGuardarLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(panelBotonesGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cardGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        txtId.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtId.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtId.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtId.setEnabled(false);

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("ROLES:");

        lblCodigo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCodigo.setText("CÓDIGO:");

        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setText("NOMBRES:");

        txtNombres.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNombres.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNombres.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombresActionPerformed(evt);
            }
        });
        txtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombresKeyTyped(evt);
            }
        });

        jlistRolUser.setBorder(null);
        jlistRolUser.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(jlistRolUser);

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setText("CORREO:");

        txtEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtEmail.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEmail.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailKeyTyped(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel29.setText("CONTRASEÑA:");

        txtPassword.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPassword.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPasswordKeyTyped(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel33.setText("AVATAR:");

        lblAvatar.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lblAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAvatar.setText("Arrastra una imagen aquí");

        jPanel10.setBackground(new java.awt.Color(255, 102, 102));

        btnEliminarImagen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminarImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminarImagen.setText("X");
        btnEliminarImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminarImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEliminarImagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelContentGuardarLayout = new javax.swing.GroupLayout(panelContentGuardar);
        panelContentGuardar.setLayout(panelContentGuardarLayout);
        panelContentGuardarLayout.setHorizontalGroup(
            panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentGuardarLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContentGuardarLayout.createSequentialGroup()
                        .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelContentGuardarLayout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPassword))
                            .addGroup(panelContentGuardarLayout.createSequentialGroup()
                                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel28)
                                    .addComponent(lblCodigo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtId)
                                    .addComponent(txtEmail)
                                    .addComponent(txtNombres)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContentGuardarLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel19)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1)))
                        .addGap(50, 50, 50))
                    .addGroup(panelContentGuardarLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAvatar)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(139, Short.MAX_VALUE))))
        );
        panelContentGuardarLayout.setVerticalGroup(
            panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentGuardarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContentGuardarLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(lblAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelContentGuardarLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelContentGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(jLabel19))
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
                .addContainerGap(480, Short.MAX_VALUE)
                .addComponent(panelBotonesGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelGuardarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelGuardarLayout.createSequentialGroup()
                    .addComponent(panelContentGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(117, 117, 117)))
        );

        pnaelContent.add(panelGuardar, "card2");

        panelVer.setBackground(new java.awt.Color(236, 233, 233));

        panelContentVer.setBackground(new java.awt.Color(236, 233, 233));
        panelContentVer.setPreferredSize(new java.awt.Dimension(495, 455));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setText("Nombres:");

        lblCodigo222.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCodigo222.setText("Código:");

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setText("Correo:");

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setText("Roles:");

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setText("Estado:");

        lblEstado.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEstado.setText("ubicacion desconocida");
        lblEstado.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(76, 170, 255), 1, true));
        lblEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblFCreacion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblFCreacion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFCreacion.setText("ubicacion desconocida");
        lblFCreacion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(76, 170, 255), 1, true));
        lblFCreacion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setText("Fecha Creación:");

        jLabel31.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel31.setText("Fecha Edición:");

        lblFEdicion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblFEdicion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFEdicion.setText("ubicacion desconocida");
        lblFEdicion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(76, 170, 255), 1, true));
        lblFEdicion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel32.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel32.setText("Avatar:");

        lblAvatar1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblAvatar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAvatar1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(76, 170, 255), 1, true));
        lblAvatar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblAvatar1.setMaximumSize(new java.awt.Dimension(200, 200));

        lblRoles.setEditable(false);
        lblRoles.setBackground(new java.awt.Color(236, 233, 233));
        lblRoles.setColumns(20);
        lblRoles.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblRoles.setLineWrap(true);
        lblRoles.setRows(5);
        lblRoles.setBorder(null);
        jScrollPane4.setViewportView(lblRoles);

        lblCodigo1.setEditable(false);
        lblCodigo1.setBackground(new java.awt.Color(236, 233, 233));
        lblCodigo1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblCodigo1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblCodigo1.setOpaque(true);

        lblNombres.setEditable(false);
        lblNombres.setBackground(new java.awt.Color(236, 233, 233));
        lblNombres.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNombres.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblNombres.setOpaque(true);

        lblEmail.setEditable(false);
        lblEmail.setBackground(new java.awt.Color(236, 233, 233));
        lblEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblEmail.setOpaque(true);

        javax.swing.GroupLayout panelContentVerLayout = new javax.swing.GroupLayout(panelContentVer);
        panelContentVer.setLayout(panelContentVerLayout);
        panelContentVerLayout.setHorizontalGroup(
            panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentVerLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContentVerLayout.createSequentialGroup()
                        .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(lblCodigo222))
                        .addGap(50, 50, 50)
                        .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo1)
                            .addComponent(lblNombres)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContentVerLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEmail)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)))
                    .addGroup(panelContentVerLayout.createSequentialGroup()
                        .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel32)
                            .addComponent(jLabel23))
                        .addGap(66, 66, 66)
                        .addComponent(lblAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelContentVerLayout.createSequentialGroup()
                        .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblFCreacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFEdicion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(50, 50, 50))
        );
        panelContentVerLayout.setVerticalGroup(
            panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContentVerLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo222)
                    .addComponent(lblCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(lblNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(lblAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(lblEstado))
                .addGap(18, 18, 18)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(lblFCreacion))
                .addGap(18, 18, 18)
                .addGroup(panelContentVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(lblFEdicion))
                .addGap(15, 15, 15))
        );

        panelBotonesVer.setBackground(new java.awt.Color(236, 233, 233));

        jPanel8.setBackground(new java.awt.Color(37, 122, 255));

        btnRegresar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnRegresar.setText("Regresar al Listado");
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRegresar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelBotonesVerLayout = new javax.swing.GroupLayout(panelBotonesVer);
        panelBotonesVer.setLayout(panelBotonesVerLayout);
        panelBotonesVerLayout.setHorizontalGroup(
            panelBotonesVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesVerLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(72, 72, 72))
        );
        panelBotonesVerLayout.setVerticalGroup(
            panelBotonesVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesVerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelVerLayout = new javax.swing.GroupLayout(panelVer);
        panelVer.setLayout(panelVerLayout);
        panelVerLayout.setHorizontalGroup(
            panelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVerLayout.createSequentialGroup()
                .addComponent(panelBotonesVer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addGroup(panelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelVerLayout.createSequentialGroup()
                    .addComponent(panelContentVer, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        panelVerLayout.setVerticalGroup(
            panelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVerLayout.createSequentialGroup()
                .addGap(0, 481, Short.MAX_VALUE)
                .addComponent(panelBotonesVer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelVerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelVerLayout.createSequentialGroup()
                    .addComponent(panelContentVer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(91, 91, 91)))
        );

        pnaelContent.add(panelVer, "card3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnaelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(pnaelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jScrollPane3.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombresKeyTyped

    private void txtNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombresActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyTyped

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void txtPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordKeyTyped



    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel btnActualizar;
    public javax.swing.JLabel btnEliminarImagen;
    public javax.swing.JLabel btnGuardar;
    public javax.swing.JLabel btnLimpiar;
    public javax.swing.JLabel btnRegresar;
    public javax.swing.JLabel btnRegresarListado;
    private javax.swing.JPanel cardGuardar;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JList<String> jlistRolUser;
    public javax.swing.JLabel lblAvatar;
    public javax.swing.JLabel lblAvatar1;
    public javax.swing.JLabel lblCodigo;
    public javax.swing.JTextField lblCodigo1;
    private javax.swing.JLabel lblCodigo222;
    public javax.swing.JTextField lblEmail;
    public javax.swing.JLabel lblEstado;
    public javax.swing.JLabel lblFCreacion;
    public javax.swing.JLabel lblFEdicion;
    public javax.swing.JTextField lblNombres;
    public javax.swing.JTextArea lblRoles;
    public javax.swing.JLabel lblTextoCrearOEditar;
    public javax.swing.JLabel lblTextoEditarOCrearPermiso;
    private javax.swing.JPanel panelBotonesGuardar;
    private javax.swing.JPanel panelBotonesVer;
    public javax.swing.JPanel panelBtnActualizar;
    public javax.swing.JPanel panelBtnGuardar;
    private javax.swing.JPanel panelContentGuardar;
    private javax.swing.JPanel panelContentVer;
    public javax.swing.JPanel panelGuardar;
    private javax.swing.JPanel panelHeader;
    public javax.swing.JPanel panelVer;
    private javax.swing.JPanel pnaelContent;
    public javax.swing.JTextField txtEmail;
    public javax.swing.JTextField txtId;
    public javax.swing.JTextField txtNombres;
    public javax.swing.JTextField txtPassword;
    // End of variables declaration//GEN-END:variables
}
