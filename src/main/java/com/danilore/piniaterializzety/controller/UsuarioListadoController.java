package com.danilore.piniaterializzety.controller;

import com.danilore.piniaterializzety.clases.TextPrompt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.swing.ListModel;
import com.danilore.piniaterializzety.models.EstadoEnum;
import com.danilore.piniaterializzety.models.usuario.Rol;
import com.danilore.piniaterializzety.models.usuario.Usuario;
import com.danilore.piniaterializzety.services.UsuarioService;
import com.danilore.piniaterializzety.views.usuario.VUsuario;
import com.danilore.piniaterializzety.views.usuario.VUsuarioListado;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Danilore
 */
public final class UsuarioListadoController {

    UsuarioService usuarioService = new UsuarioService();
    private final VUsuarioListado vista;
    private final VUsuario vistaUsuario;
    private static final String BASE_URL = "http://localhost:8080/api/usuarios";
    private static final String BASE_URL_AVATAR = "http://localhost:8080/api/usuarios/upload-avatar";
    private List<Usuario> usuariosCargados = new ArrayList<>();
    private List<Rol> rolesCargados = new ArrayList<>();

    String palabraSingular = "Usuario";
    String palabraPlural = "Usuarios";

    //Paginacion    
    private int paginaActual = 0; // Página actual
    private final int tamanioPagina = 12; // Tamaño de página fijo
    private boolean esUltimaPagina = false;

    //Busqueda
    private boolean enModoBusqueda = false;
    private String criterioBusqueda = ""; // Criterio de búsqueda actual

    private JLabel imageLabel;
    private File selectedFile;

    public UsuarioListadoController(VUsuarioListado vista, VUsuario vistaUsuario, Usuario usuario) {
        this.vista = vista;
        this.vistaUsuario = vistaUsuario;
        configurarEventos();

        vista.lblPagina.setText("Página: " + (paginaActual + 1));

        // Configuración inicial
        this.vista.btnActualizar.setEnabled(false);
        this.vista.panelBtnDarBaja.setVisible(true);
        this.vista.btnDarBaja.setEnabled(false);
        this.vista.btnEliminar.setEnabled(false);
        this.vista.btnVisualizar.setEnabled(false);

        // Habilitar botones según permisos
        configurarBotonesSegunPermisos(usuario);

        // Inicializar tabla y cargar datos
        limpiarTable();
        listar();

        // Añadir placeholders
        marcaAgua();
    }

    private void configurarBotonesSegunPermisos(Usuario usuario) {
        // Verificar si el usuario tiene el permiso para "Eliminar"
        //if (!usuario.tienePermiso("GESTIONAR_USUARIOS_ADMIN")) {
        //vista.btnEliminar.setVisible(false); // Ocultar el botón si no tiene el permiso
        //}

        // Puedes agregar más verificaciones para otros botones si es necesario
    }

    private void configurarEventos() {
        vista.btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                enModoBusqueda = true; // Activar modo búsqueda
                criterioBusqueda = vista.txtBuscar.getText().trim(); // Guardar el criterio actual
                paginaActual = 0; // Reiniciar a la primera página
                listarPorCriterio();
            }
        });

        vista.btnListar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listar();
            }
        });

        vista.btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nuevo();
            }
        });

        vista.btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizar();
            }
        });

        vista.btnDarBaja.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarEstado("INACTIVO");
            }
        });

        vista.btnActivar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarEstado("ACTIVO");
            }
        });

        vista.btnVisualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                visualizar();
            }
        });

        vista.btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminar();
            }
        });

        vista.table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarUsuarioDeTabla();
            }
        });

        //Botones de paginacion
        vista.btnAnterior.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                anteriorPagina();
            }
        });

        vista.btnSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                siguientePagina();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////
        //Vista Guardar o Editar o Visualizar
        vistaUsuario.btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardarEnVista();
            }
        });

        vistaUsuario.btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarEnVista();
            }
        });

        vistaUsuario.btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                limpiarCampos();
            }
        });

        vistaUsuario.btnRegresarListado.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                enviarVistaListado();
            }
        });

        vistaUsuario.btnRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                enviarVistaListado();
            }
        });

    }

    // -------- Metodos de Paginacion ----------------
    private void anteriorPagina() {
        if (paginaActual == 0) {
            mostrarMensaje("Ya estás en la primera página.");
            return;
        }
        paginaActual--;
        listar();
        vista.lblPagina.setText("Página: " + (paginaActual + 1));

    }

    private void siguientePagina() {
        if (esUltimaPagina) {
            mostrarMensaje("Ya estás en la última página.");
            return;
        }
        paginaActual++;
        listar();
        vista.lblPagina.setText("Página: " + (paginaActual + 1));
    }

    //--------------------------------------------
    private void seleccionarUsuarioDeTabla() {
        int filaSeleccionada = vista.table.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Usuario usuario = obtenerUsuarioDesdeFila(filaSeleccionada);

            // Habilitar botones según el estado del usuario
            if (usuario.getEstado() == EstadoEnum.ACTIVO) {
                vista.panelBtnDarBaja.setVisible(true);
                vista.btnDarBaja.setEnabled(true);
                vista.panelBtnActivar.setVisible(false);
            } else if (usuario.getEstado() == EstadoEnum.INACTIVO) {
                vista.panelBtnDarBaja.setVisible(false);
                vista.panelBtnActivar.setVisible(true);
                vista.btnActivar.setEnabled(true);
            }

            // Habilitar botón actualizar siempre que un usuario sea seleccionado
            vista.btnActualizar.setEnabled(true);
            vista.btnEliminar.setEnabled(true);
            vista.btnVisualizar.setEnabled(true);
        }
    }

    private Usuario obtenerUsuarioDesdeFila(int fila) {
        if (fila >= 0 && fila < usuariosCargados.size()) {
            return usuariosCargados.get(fila); // Recupera los datos completos de la persona desde la lista
        }
        return null;
    }

    public void listarPorCriterio() {
        try {
            // Construir la URL con el criterio de búsqueda
            String url = BASE_URL + "/buscar?criterio=" + URLEncoder.encode(criterioBusqueda, StandardCharsets.UTF_8)
                    + "&page=" + paginaActual + "&size=" + tamanioPagina;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                JsonNode rootNode = mapper.readTree(response.body());

                // Verificar si el nodo 'content' existe
                JsonNode contentNode = rootNode.get("content");
                if (contentNode == null || !contentNode.isArray()) {
                    JOptionPane.showMessageDialog(vista, "No se encontraron resultados para el criterio especificado.");
                    return;
                }

                // Procesar los usuarios
                usuariosCargados = contentNode.traverse(mapper)
                        .readValueAs(new TypeReference<List<Usuario>>() {
                        });

                DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
                model.setRowCount(0);

                // Poblar datos en la tabla
                for (Usuario usuario : usuariosCargados) {
                    // Obtener las descripciones de los roles como una sola cadena
                    String rolesDescripcion = usuario.getRoles().stream()
                            .map(Rol::getDescripcion)
                            .reduce((desc1, desc2) -> desc1 + ", " + desc2) // Unir descripciones con coma
                            .orElse("Sin roles");

                    model.addRow(new Object[]{
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getEmail(),
                        usuario.getPassword(),
                        rolesDescripcion,
                        usuario.getAvatar(),
                        usuario.getEstado().name()
                    });
                }

                // Actualizar la información de paginación
                esUltimaPagina = rootNode.path("last").asBoolean(false);
                vista.lblPagina.setText("Página: " + (paginaActual + 1));

                this.vista.btnActualizar.setEnabled(false);
                this.vista.btnEliminar.setEnabled(false);
                this.vista.btnVisualizar.setEnabled(false);
                this.vista.panelBtnDarBaja.setVisible(true);
                this.vista.btnDarBaja.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al listar " + palabraPlural + ". Código: " + response.statusCode());
            }
        } catch (HeadlessException | IOException | InterruptedException | URISyntaxException e) {
            JOptionPane.showMessageDialog(vista, "Error al listar " + palabraPlural + ": " + e.getMessage());
        }
    }

    //--------------------------  CRUD ROLES  ----------------------
    public void listar() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "?page=" + paginaActual + "&size=" + tamanioPagina))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule()); // Soporte para LocalDate
                JsonNode rootNode = mapper.readTree(response.body());

                usuariosCargados = rootNode.get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Usuario>>() {
                        });

                DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
                model.setRowCount(0);

                for (Usuario usuario : usuariosCargados) {
                    // Obtener las descripciones de los roles como una sola cadena
                    String rolesDescripcion = usuario.getRoles().stream()
                            .map(Rol::getDescripcion)
                            .reduce((desc1, desc2) -> desc1 + ", " + desc2) // Unir descripciones con coma
                            .orElse("Sin roles");

                    model.addRow(new Object[]{
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getEmail(),
                        usuario.getPassword(),
                        rolesDescripcion,
                        usuario.getAvatar(),
                        usuario.getEstado().name()
                    });
                }

                // Actualizar el estado de si es la última página
                esUltimaPagina = rootNode.get("last").asBoolean();

                this.vista.btnActualizar.setEnabled(false);
                this.vista.btnEliminar.setEnabled(false);
                this.vista.btnVisualizar.setEnabled(false);
                this.vista.panelBtnDarBaja.setVisible(true);
                this.vista.btnDarBaja.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al listar " + palabraPlural + ". Error: " + response.body());
            }
        } catch (HeadlessException | IOException | InterruptedException | URISyntaxException e) {
            JOptionPane.showMessageDialog(null, "Error al listar " + palabraPlural + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void nuevo() {
        seleccionarAvatar();
        llenarRoles();
        //PermisoController permisoGuardarController = new PermisoController(vistaPermiso, vista, usuario);
        // Configurar la vista
        vistaUsuario.lblCodigo.setVisible(false);
        vistaUsuario.txtId.setVisible(false);

        vistaUsuario.lblTextoEditarOCrearPermiso.setText("REGISTRAR USUARIO");
        // Activa los botones necesarios para edición
        vistaUsuario.panelBtnActualizar.setVisible(false);
        vistaUsuario.panelBtnGuardar.setVisible(true);
        vistaUsuario.btnLimpiar.setVisible(true);

        // Ocultar la vista de listado y mostrar la de crear
        vistaUsuario.setVisible(true); // Muestra la vista
        vista.setVisible(false);
    }

    public void actualizar() {
        try {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = vista.table.getSelectedRow();

            if (filaSeleccionada < 0) {
                JOptionPane.showMessageDialog(vista, "Seleccione un " + palabraSingular + " de la tabla para actualizar.");
                return;
            }

            // Obtener el rol correspondiente a la fila seleccionada
            Usuario usuarioSeleccionado = obtenerUsuarioDesdeFila(filaSeleccionada);

            if (usuarioSeleccionado == null) {
                JOptionPane.showMessageDialog(vista, "No se pudo obtener el " + palabraSingular + " seleccionado.");
                return;
            }

            // Enviar el rol seleccionado a la vista de edición
            enviarAVistaEdicion(usuarioSeleccionado);

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado actualizar " + palabraSingular + ": " + e.getMessage());
        }
    }

    private void enviarAVistaEdicion(Usuario usuario) {

        //RolController rolController = new RolController(vistaRol, new Usuario());
        // Configurar datos del rol seleccionado en la nueva ventana
        seleccionarAvatar();
        llenarRoles();

        // Configurar la vista de edición con los datos seleccionados
        vistaUsuario.panelVer.setVisible(false);
        vistaUsuario.panelGuardar.setVisible(true);

        vistaUsuario.txtId.setText(String.valueOf(usuario.getId()));
        vistaUsuario.txtNombres.setText(usuario.getNombre());
        vistaUsuario.txtEmail.setText(usuario.getEmail());

        ListModel<String> model = vistaUsuario.jlistRolUser.getModel();

        List<String> rolesUsuario = usuario.getRoles().stream()
                .map(Rol::getDescripcion)
                .toList();

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < model.getSize(); i++) {
            if (rolesUsuario.contains(model.getElementAt(i))) {
                indices.add(i);
            }
        }

        int[] selectedIndices = indices.stream().mapToInt(Integer::intValue).toArray();
        vistaUsuario.jlistRolUser.setSelectedIndices(selectedIndices);

        vistaUsuario.lblTextoCrearOEditar.setText("EDITAR USUARIO");

        // Activa los botones necesarios para edición
        vistaUsuario.panelBtnActualizar.setVisible(true);
        vistaUsuario.panelBtnGuardar.setVisible(false);
        vistaUsuario.btnLimpiar.setVisible(false);

        vista.setVisible(false); // Oculta la vista actual
        vistaUsuario.setVisible(true); // Muestra la vista de edición
    }

    public void visualizar() {
        try {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = vista.table.getSelectedRow();

            Usuario usuarioSeleccionado = obtenerUsuarioDesdeFila(filaSeleccionada);

            if (usuarioSeleccionado == null) {
                JOptionPane.showMessageDialog(vista, "No se pudo obtener el " + palabraSingular + " seleccionado.");
                return;
            }

            // Enviar el rol seleccionado a la vista de edición
            enviarAVistaVisualizacion(usuarioSeleccionado);

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado visualizar " + palabraSingular + ": " + e.getMessage());
        }
    }

    private void enviarAVistaVisualizacion(Usuario usuario) {
        //PermisoController permisoGuardarController = new PermisoController(vistaPermiso, vista, usuario);
        // Configurar la vista
        seleccionarAvatar();

        llenarRoles();

        vistaUsuario.panelGuardar.setVisible(false);
        vistaUsuario.panelVer.setVisible(true);

        vistaUsuario.lblCodigo1.setText(String.valueOf(usuario.getId()));
        vistaUsuario.lblNombres.setText(usuario.getNombre());
        vistaUsuario.lblCorreo.setText(usuario.getEmail());
        vistaUsuario.lblPassword.setText(usuario.getPassword());

        // Obtener las descripciones de los roles como una sola cadena
        String rolesDescripcion = usuario.getRoles().stream()
                .map(Rol::getDescripcion)
                .reduce((desc1, desc2) -> desc1 + ", " + desc2) // Unir descripciones con coma
                .orElse("Sin roles");

        vistaUsuario.lblRoles.setText(rolesDescripcion);
        vistaUsuario.lblEstado.setText(usuario.getEstado().name());

        // Validar valores nulos y mostrar cadena vacía si es necesario
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String createdAt = (usuario.getCreatedAt() != null) ? usuario.getCreatedAt().format(formatter) : "";
        String updatedAt = (usuario.getUpdatedAt() != null) ? usuario.getUpdatedAt().format(formatter) : "";

        vistaUsuario.lblFCreacion.setText(createdAt);
        vistaUsuario.lblFEdicion.setText(updatedAt);

        vistaUsuario.lblTextoEditarOCrearPermiso.setText("USUARIO");

        // Ocultar la vista de listado y mostrar la de edición
        vistaUsuario.setVisible(true); // Muestra la vista de edición
        vista.setVisible(false);
    }

    private void cambiarEstado(String nuevoEstado) {
        try {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = vista.table.getSelectedRow();

            Usuario usuarioSeleccionado = obtenerUsuarioDesdeFila(filaSeleccionada);

            Long id = usuarioSeleccionado.getId();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/cambiar-estado/" + id + "?nuevoEstado=" + nuevoEstado))
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(vista, "Estado del " + palabraSingular + " cambiado a " + nuevoEstado + ".");
                listar();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al cambiar estado. Error: " + response.body());
            }
        } catch (HeadlessException | IOException | InterruptedException | URISyntaxException e) {
            JOptionPane.showMessageDialog(vista, "Error al cambiar estado: " + e.getMessage());
        }
    }

    public void eliminar() {
        try {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = vista.table.getSelectedRow();

            Usuario usuarioSeleccionado = obtenerUsuarioDesdeFila(filaSeleccionada);

            Long id = usuarioSeleccionado.getId();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if ("".equals(response.body())) {
                mostrarMensaje(palabraSingular + " eliminado con éxito.");
                listar();
            } else {
                mostrarError("Error al eliminar " + palabraSingular + ". Error: " + response.body());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            mostrarError("Error inesperado al eliminar " + palabraSingular + ": " + e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////
    //Metodos de vista Guardar, editar o ver
    public void guardarEnVista() {
        try {
            if (!camposValidos() && vistaUsuario.txtPassword.getText().isEmpty()) {
                mostrarMensaje("Debe completar todos los campos.");
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setNombre(vistaUsuario.txtNombres.getText().trim());

            String email = vistaUsuario.txtEmail.getText().trim();

            if (!validarFormatoEmail(email)) {
                mostrarError("Por favor, ingrese un correo electrónico válido.");
                return;
            }

            usuario.setEmail(email);

            String password = vistaUsuario.txtPassword.getText().trim();

            if (!validarPassword(password)) {
                return;
            }
            //usuario.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            usuario.setPassword(password);

            // Obtener roles seleccionados
            List<String> selectedRoles = vistaUsuario.jlistRolUser.getSelectedValuesList();

            if (vistaUsuario.jlistRolUser.isSelectionEmpty()) {
                mostrarError("Debes seleccionar al menos un Rol");
                return;
            }

            List<Rol> roles = selectedRoles.stream()
                    .map(desc -> rolesCargados.stream()
                    .filter(rol -> rol.getDescripcion().equals(desc))
                    .findFirst()
                    .orElse(null))
                    .filter(rol -> rol != null)
                    .toList();

            usuario.setRoles(roles);

            // Configurar ObjectMapper con soporte para Java 8 Time API
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Agregar soporte para LocalDateTime

            // Convertir objeto a JSON
            String requestBody = mapper.writeValueAsString(usuario);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                mostrarMensaje(palabraSingular + " guardado con éxito.");
                limpiarCampos();
                listar();
                guardarUsuarioConImagen(usuario);
            } else {
                mostrarError("Error al guardar " + palabraSingular + ". Error: " + response.body());
                System.out.println("Error al guardar " + palabraSingular + ". Error: " + response.body());
            }

        } catch (HeadlessException | IOException | InterruptedException | URISyntaxException e) {
            mostrarError("Error (Catch) al guardar " + palabraSingular + ": " + e.getMessage());
            System.out.println("Error (Catch) al guardar " + palabraSingular + ": " + e.getMessage());
        }
    }

    public void actualizarEnVista() {
        try {
            if (!camposValidos()) {
                mostrarMensaje("Debe completar todos los campos.");
                return;
            }

            int id = Integer.parseInt(vistaUsuario.txtId.getText());
            Usuario usuario = new Usuario();
            usuario.setId(Long.valueOf(vistaUsuario.txtId.getText()));
            usuario.setNombre(vistaUsuario.txtNombres.getText().trim());

            String email = vistaUsuario.txtEmail.getText().trim();

            if (!validarFormatoEmail(email)) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un correo electrónico válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuario.setEmail(email);

            String password = vistaUsuario.txtPassword.getText().trim();

            if (!validarPasswordActualizar(password)) {
                return;
            }
            //usuario.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            usuario.setPassword(password);

            //usuario.setPassword(vistaUsuario.txtPassword.getText().trim());
            // Obtener roles seleccionados
            List<String> selectedRoles = vistaUsuario.jlistRolUser.getSelectedValuesList();

            if (vistaUsuario.jlistRolUser.isSelectionEmpty()) {
                mostrarError("Debes seleccionar al menos un Rol");
                return;
            }

            List<Rol> roles = selectedRoles.stream()
                    .map(desc -> rolesCargados.stream()
                    .filter(rol -> rol.getDescripcion().equals(desc))
                    .findFirst()
                    .orElse(null))
                    .filter(rol -> rol != null)
                    .toList();

            usuario.setRoles(roles);

            // Configurar ObjectMapper con soporte para Java 8 Time API
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Agregar soporte para LocalDateTime

            // Convertir objeto a JSON
            String requestBody = mapper.writeValueAsString(usuario);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/editar/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                mostrarMensaje(palabraSingular + " actualizado con éxito.");
                limpiarCampos();
                enviarVistaListado();
                listar();
            } else {
                mostrarError("Error al actualizar " + palabraSingular + ". Error: " + response.body());
            }
        } catch (IOException | InterruptedException | NumberFormatException | URISyntaxException e) {
            mostrarError("Error al actualizar " + palabraSingular + ": " + e.getMessage());
        }
    }

    private void enviarVistaListado() {
        vistaUsuario.setVisible(false);
        vista.setVisible(true);
        limpiarCampos();
    }

    public void llenarRoles() {
        rolesCargados = usuarioService.cargarRoles();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        rolesCargados.forEach(rol -> listModel.addElement(rol.getDescripcion()));
        vistaUsuario.jlistRolUser.setModel(listModel);
    }

    //-------------------- IMAGEN ---------------------
    // Habilitar Drag and Drop
    public void seleccionarAvatar() {

        imageLabel = vistaUsuario.lblAvatar;
        imageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.LIGHT_GRAY);
        imageLabel.setPreferredSize(new Dimension(100, 100));

        // Habilitar Drag and Drop
        new DropTarget(imageLabel, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    Object transferData = event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    if (transferData instanceof java.util.List) {
                        java.util.List<File> fileList = (java.util.List<File>) transferData;
                        if (!fileList.isEmpty()) {
                            File file = fileList.get(0);
                            if (isImageFile(file)) {
                                displayImage(file);
                                selectedFile = file; // Guardar archivo seleccionado
                            } else {
                                JOptionPane.showMessageDialog(null, "Solo se permiten imágenes.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    event.dropComplete(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        //add(imageLabel, BorderLayout.CENTER);
    }

    // Método para verificar si el archivo es una imagen
    private boolean isImageFile(File file) {
        String[] validExtensions = {"jpg", "jpeg", "png", "gif"};
        String fileName = file.getName().toLowerCase();
        for (String ext : validExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    // Método para mostrar la imagen en el JLabel
    private void displayImage(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            imageLabel.setText(""); // Quitar el texto
            imageLabel.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Método para subir la imagen al backend
    public String uploadImage(File file) {
        if (file == null) {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna imagen.");
            return null;
        }

        try {    
            URL url = new URL(BASE_URL_AVATAR);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=*****");

            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = reader.readLine();
            reader.close();

            return response; // Retorna la URL de la imagen

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
       // Método para guardar usuario con la imagen
    public void guardarUsuarioConImagen(Usuario usuario) {
        String imageUrl = uploadImage(selectedFile);
        if (imageUrl != null) {
            usuario.setAvatar(imageUrl);
            // Llamar al método para guardar usuario en el backend
        }
    }

    //-----------------------------------------------
    private void limpiarCampos() {
        vistaUsuario.lblCodigo.setVisible(false);
        vistaUsuario.txtId.setVisible(false);
        vistaUsuario.txtId.setText("");
        vistaUsuario.txtNombres.setText("");
        vistaUsuario.txtEmail.setText("");
        vistaUsuario.txtPassword.setText("");
        vistaUsuario.jlistRolUser.clearSelection(); // Desmarcar todos los roles

        vistaUsuario.lblTextoEditarOCrearPermiso.setText("REGISTRAR USUARIO");

        vistaUsuario.panelBtnActualizar.setVisible(false);
        vistaUsuario.panelBtnGuardar.setVisible(true);
        vistaUsuario.btnLimpiar.setVisible(true);
    }

    public boolean camposValidos() {
        return !vistaUsuario.txtNombres.getText().isEmpty()
                && !vistaUsuario.txtEmail.getText().isEmpty();
    }

    private boolean validarFormatoEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    private boolean validarPassword(String password) {
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(vistaUsuario, "La contraseña debe tener al menos 6 caracteres.");
            return false;
        }
        if (password.matches(".*\\s+.*")) {
            JOptionPane.showMessageDialog(vistaUsuario, "La contraseña no puede contener espacios.");
            return false;
        }
        return true;
    }

    private boolean validarPasswordActualizar(String password) {
        if (password.length() >= 1 && password.length() < 6) {
            JOptionPane.showMessageDialog(vistaUsuario, "La contraseña debe tener al menos 6 caracteres.");
            return false;
        }
        if (password.matches(".*\\s+.*")) {
            JOptionPane.showMessageDialog(vistaUsuario, "La contraseña no puede contener espacios.");
            return false;
        }
        return true;
    }

    ////////////////////////////

    public void limpiarTable() {
        DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
        model.setRowCount(0);
    }

    private int mostrarMensajeConfirmacion(String tipo) {
        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de " + tipo + " este " + palabraSingular + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
        return confirmacion;
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void marcaAgua() {
        TextPrompt btnBuscar = new TextPrompt("ID o Nombres o Correo", vista.txtBuscar);

        // Vista Guardar o Editar o Visualizar
        TextPrompt nombreUser = new TextPrompt("Nombres del Usuario", vistaUsuario.txtNombres);
        TextPrompt user = new TextPrompt("Correo", vistaUsuario.txtEmail);
        TextPrompt contra = new TextPrompt("Contraseña", vistaUsuario.txtPassword);
    }

}
