/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ListModel;
import com.danilore.piniaterializzety.models.EstadoEnum;
import com.danilore.piniaterializzety.models.usuario.Permiso;
import com.danilore.piniaterializzety.models.usuario.Rol;
import com.danilore.piniaterializzety.models.usuario.Usuario;
import com.danilore.piniaterializzety.services.RolService;
import com.danilore.piniaterializzety.views.usuario.VRol;
import com.danilore.piniaterializzety.views.usuario.VRolListado;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.swing.DefaultListModel;

/**
 *
 * @author Danilore
 */
public final class RolListadoController {

    RolService rolService = new RolService();
    private final VRolListado vista;
    private final VRol vistaRol;
    private static final String BASE_URL = "http://localhost:8080/api/roles";
    private List<Rol> rolesCargados = new ArrayList<>();
    private List<Permiso> permisosCargados = new ArrayList<>();

    String palabraSingular = "Rol";
    String palabraPlural = "Roles";

    //Paginacion    
    private int paginaActual = 0; // Página actual
    private final int tamanioPagina = 20; // Tamaño de página fijo
    private boolean esUltimaPagina = false;

    //Busqueda
    private boolean enModoBusqueda = false;
    private String criterioBusqueda = ""; // Criterio de búsqueda actual

    public RolListadoController(VRolListado vista, VRol vistaRol, Usuario usuario) {
        this.vista = vista;
        this.vistaRol = vistaRol;
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
        //if (!usuario.tienePermiso("GESTIONAR_ROLES_ADMIN")) {
        //  vista.btnEliminar.setVisible(false); // Ocultar el botón si no tiene el permiso
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
                seleccionarRolDeTabla();
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
        vistaRol.btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardarEnVista();
            }
        });

        vistaRol.btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarEnVista();
            }
        });

        vistaRol.btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                limpiarCampos();
            }
        });

        vistaRol.btnRegresarListado.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                enviarVistaListado();
            }
        });

        vistaRol.btnRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
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
    private void seleccionarRolDeTabla() {
        int filaSeleccionada = vista.table.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Rol rol = obtenerRolDesdeFila(filaSeleccionada);

            // Habilitar botones según el estado del usuario
            if (rol.getEstado() == EstadoEnum.ACTIVO) {
                vista.panelBtnDarBaja.setVisible(true);
                vista.btnDarBaja.setEnabled(true);
                vista.panelBtnActivar.setVisible(false);
            } else if (rol.getEstado() == EstadoEnum.INACTIVO) {
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

    private Rol obtenerRolDesdeFila(int fila) {
        if (fila >= 0 && fila < rolesCargados.size()) {
            return rolesCargados.get(fila); // Recupera los datos completos de la persona desde la lista
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

                // Procesar los permisos
                rolesCargados = rootNode.get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Rol>>() {
                        });

                DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
                model.setRowCount(0);

                // Poblar datos en la tabla
                for (Rol rol : rolesCargados) {

                    // Obtener las descripciones de los roles como una sola cadena
                    String permisosDescripcion = rol.getPermisos().stream()
                            .map(Permiso::getDescripcion)
                            .reduce((desc1, desc2) -> desc1 + ", " + desc2) // Unir descripciones con coma
                            .orElse("Sin permisos");

                    model.addRow(new Object[]{
                        rol.getId(),
                        rol.getDescripcion(),
                        permisosDescripcion,
                        rol.getEstado().name()
                    });

                }

                // Actualizar la información de paginación
                esUltimaPagina = rootNode.path("last").asBoolean(false);
                vista.lblPagina.setText("Página: " + (paginaActual + 1));
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

                rolesCargados = rootNode.get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Rol>>() {
                        });

                DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
                model.setRowCount(0);

                for (Rol rol : rolesCargados) {
                    // Obtener las descripciones de los roles como una sola cadena
                    String permisosDescripcion = rol.getPermisos().stream()
                            .map(Permiso::getDescripcion)
                            .reduce((desc1, desc2) -> desc1 + ", " + desc2) // Unir descripciones con coma
                            .orElse("Sin permisos");

                    model.addRow(new Object[]{
                        rol.getId(),
                        rol.getDescripcion(),
                        permisosDescripcion,
                        rol.getEstado().name()
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
        llenarPermisos();
        //PermisoController permisoGuardarController = new PermisoController(vistaPermiso, vista, usuario);
        // Configurar la vista
        vistaRol.lblCodigo.setVisible(false);
        vistaRol.txtId.setVisible(false);

        vistaRol.lblTextoEditarOCrearPermiso.setText("REGISTRAR PERMISO");
        // Activa los botones necesarios para edición
        vistaRol.panelBtnActualizar.setVisible(false);
        vistaRol.panelBtnGuardar.setVisible(true);
        vistaRol.btnLimpiar.setVisible(true);

        // Ocultar la vista de listado y mostrar la de crear
        vistaRol.setVisible(true); // Muestra la vista
        vista.setVisible(false);
    }

    public void actualizar() {
        try {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = vista.table.getSelectedRow();

            // Obtener el rol correspondiente a la fila seleccionada
            Rol rolSeleccionado = obtenerRolDesdeFila(filaSeleccionada);

            if (rolSeleccionado == null) {
                JOptionPane.showMessageDialog(vista, "No se pudo obtener " + palabraSingular + " seleccionado.");
                return;
            }

            // Enviar el rol seleccionado a la vista de edición
            enviarAVistaEdicion(rolSeleccionado);

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado actualizar " + palabraSingular + ": " + e.getMessage());
        }
    }

    public void llenarPermisos() {
        permisosCargados = rolService.cargarPermisos();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        permisosCargados.forEach(permiso -> listModel.addElement(permiso.getDescripcion()));
        vistaRol.jlistPermisosRoles.setModel(listModel);
    }

    private void enviarAVistaEdicion(Rol rol) {

        //RolController rolController = new RolController(vistaRol, new Usuario());
        // Configurar datos del rol seleccionado en la nueva ventana
        llenarPermisos();

        // Configurar la vista de edición con los datos seleccionados
        vistaRol.panelVer.setVisible(false);
        vistaRol.panelGuardar.setVisible(true);

        vistaRol.txtId.setText(String.valueOf(rol.getId()));
        vistaRol.txtDescripcion.setText(rol.getDescripcion());

        ListModel<String> model = vistaRol.jlistPermisosRoles.getModel();

        List<String> permisosRol = rol.getPermisos().stream()
                .map(Permiso::getDescripcion)
                .toList();

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < model.getSize(); i++) {
            if (permisosRol.contains(model.getElementAt(i))) {
                indices.add(i);
            }
        }

        int[] selectedIndices = indices.stream().mapToInt(Integer::intValue).toArray();
        vistaRol.jlistPermisosRoles.setSelectedIndices(selectedIndices);

        vistaRol.lblTextoCrearOEditar.setText("EDITAR ROL");

        // Activa los botones necesarios para edición
        vistaRol.panelBtnActualizar.setVisible(true);
        vistaRol.panelBtnGuardar.setVisible(false);
        vistaRol.btnLimpiar.setVisible(false);

        vista.setVisible(false); // Oculta la vista actual
        vistaRol.setVisible(true); // Muestra la vista de edición
    }

    public void visualizar() {
        try {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = vista.table.getSelectedRow();

            Rol rolSeleccionado = obtenerRolDesdeFila(filaSeleccionada);

            if (rolSeleccionado == null) {
                JOptionPane.showMessageDialog(vista, "No se pudo obtener el " + palabraSingular + " seleccionado.");
                return;
            }

            // Enviar el rol seleccionado a la vista de edición
            enviarAVistaVisualizacion(rolSeleccionado);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado visualizar " + palabraSingular + ": " + e.getMessage());
        }
    }

    private void enviarAVistaVisualizacion(Rol rol) {
        //PermisoController permisoGuardarController = new PermisoController(vistaPermiso, vista, usuario);
        // Configurar la vista
        vistaRol.panelGuardar.setVisible(false);
        vistaRol.panelVer.setVisible(true);

        vistaRol.lblCodigo1.setText(String.valueOf(rol.getId()));
        vistaRol.lblDescripcion.setText(rol.getDescripcion());

        // Obtener las descripciones de los roles como una sola cadena
        String permisosDescripcion = rol.getPermisos().stream()
                .map(Permiso::getDescripcion)
                .reduce((desc1, desc2) -> desc1 + ", " + desc2) // Unir descripciones con coma
                .orElse("Sin permisos");

        vistaRol.lblPermisos.setText(permisosDescripcion);
        vistaRol.lblEstado.setText(rol.getEstado().name());

        // Validar valores nulos y mostrar cadena vacía si es necesario
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String createdAt = (rol.getCreatedAt() != null) ? rol.getCreatedAt().format(formatter) : "";
        String updatedAt = (rol.getUpdatedAt() != null) ? rol.getUpdatedAt().format(formatter) : "";

        vistaRol.lblFCreacion.setText(createdAt);
        vistaRol.lblFEdicion.setText(updatedAt);

        vistaRol.lblTextoEditarOCrearPermiso.setText("ROL");

        // Ocultar la vista de listado y mostrar la de edición
        vistaRol.setVisible(true); // Muestra la vista de edición
        vista.setVisible(false);
    }

    private void cambiarEstado(String nuevoEstado) {
        try {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = vista.table.getSelectedRow();

            Rol rolSeleccionado = obtenerRolDesdeFila(filaSeleccionada);

            int id = rolSeleccionado.getId();

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

            Rol rolSeleccionado = obtenerRolDesdeFila(filaSeleccionada);

            int id = rolSeleccionado.getId();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                mostrarMensaje(palabraSingular + " eliminado con éxito.");
                listar();
            } else {
                mostrarError("Error al eliminar " + palabraSingular + ". Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al eliminar " + palabraSingular + ": " + e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////
    //Metodos de vista Guardar, editar o ver
    public void guardarEnVista() {
        try {
            if (!camposValidos()) {
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
                return;
            }

            Rol rol = new Rol();
            rol.setDescripcion(vistaRol.txtDescripcion.getText().trim());

            // Obtener roles seleccionados
            List<String> selectedPemisos = vistaRol.jlistPermisosRoles.getSelectedValuesList();
            List<Permiso> permisos = selectedPemisos.stream()
                    .map(desc -> permisosCargados.stream()
                    .filter(permiso -> permiso.getDescripcion().equals(desc))
                    .findFirst()
                    .orElse(null))
                    .filter(permiso -> permiso != null)
                    .toList();

            rol.setPermisos(permisos);

            // Configurar ObjectMapper con soporte para Java 8 Time API
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Agregar soporte para LocalDateTime

            // Convertir objeto a JSON
            String requestBody = mapper.writeValueAsString(rol);

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

            int id = Integer.parseInt(vistaRol.txtId.getText());
            Rol rol = new Rol();
            rol.setId(Integer.parseInt(vistaRol.txtId.getText()));
            rol.setDescripcion(vistaRol.txtDescripcion.getText().trim());

            // Obtener roles seleccionados
            List<String> selectedPermisos = vistaRol.jlistPermisosRoles.getSelectedValuesList();
            List<Permiso> permisos = selectedPermisos.stream()
                    .map(desc -> permisosCargados.stream()
                    .filter(permiso -> permiso.getDescripcion().equals(desc))
                    .findFirst()
                    .orElse(null))
                    .filter(permiso -> permiso != null)
                    .toList();

            rol.setPermisos(permisos);

            // Configurar ObjectMapper con soporte para Java 8 Time API
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Agregar soporte para LocalDateTime

            // Convertir objeto a JSON
            String requestBody = mapper.writeValueAsString(rol);

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
        } catch (Exception e) {
            mostrarError("Error al actualizar " + palabraSingular + ": " + e.getMessage());
        }
    }

    private void enviarVistaListado() {
        vistaRol.setVisible(false);
        vista.setVisible(true);
        limpiarCampos();
    }

    //-----------------------------------------------
    private void limpiarCampos() {
        vistaRol.lblCodigo.setVisible(false);
        vistaRol.txtId.setVisible(false);
        vistaRol.txtId.setText("");
        vistaRol.txtDescripcion.setText("");
        vistaRol.jlistPermisosRoles.clearSelection(); // Desmarcar todos los permisos

        vistaRol.lblTextoEditarOCrearPermiso.setText("REGISTRAR ROL");

        vistaRol.panelBtnActualizar.setVisible(false);
        vistaRol.panelBtnGuardar.setVisible(true);
        vistaRol.btnLimpiar.setVisible(true);
    }

    private boolean camposValidos() {
        return !vistaRol.txtDescripcion.getText().trim().isEmpty();
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
        TextPrompt btnBuscar = new TextPrompt("ID o Descripcion", vista.txtBuscar);

        //Guardar o editar
        TextPrompt descripcion = new TextPrompt("Descripción del Rol", vistaRol.txtDescripcion);
    }

}
