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
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ListModel;
import com.danilore.piniaterializzety.models.EstadoEnum;
import com.danilore.piniaterializzety.models.Rol;
import com.danilore.piniaterializzety.models.Usuario;
import com.danilore.piniaterializzety.views.VUsuarios;
import com.danilore.piniaterializzety.views.VUsuariosListado;

/**
 *
 * @author Danilore
 */
public final class UsuarioListadoController {

    private final VUsuariosListado vista;
    private final VUsuarios vistaUsuarios;
    private static final String BASE_URL = "http://localhost:8080/api/usuarios";
    private List<Usuario> usuariosCargados = new ArrayList<>();

    String palabraSingular = "Usuario";
    String palabraPlural = "Usuarios";

    //Paginacion    
    private int paginaActual = 0; // Página actual
    private final int tamanioPagina = 12; // Tamaño de página fijo
    private boolean esUltimaPagina = false;

    //Busqueda
    private boolean enModoBusqueda = false;
    private String criterioBusqueda = ""; // Criterio de búsqueda actual

    public UsuarioListadoController(VUsuariosListado vista, VUsuarios vistaUsuarios, Usuario usuario) {
        this.vista = vista;
        this.vistaUsuarios = vistaUsuarios;
        configurarEventos();

        vista.lblPagina.setText("Página: " + (paginaActual + 1));

        // Configuración inicial
        this.vista.txtId.setVisible(false);
        this.vista.btnActualizar.setEnabled(false);
        this.vista.btnDarBaja.setEnabled(false);
        this.vista.btnActivar.setEnabled(false);
        this.vista.btnEliminar.setEnabled(false);

        // Habilitar botones según permisos
        configurarBotonesSegunPermisos(usuario);

        // Inicializar tabla y cargar datos
        limpiarTable();
        listarUsuarios();

        // Añadir placeholders
        marcaAgua();
    }

    private void configurarBotonesSegunPermisos(Usuario usuario) {
        // Verificar si el usuario tiene el permiso para "Eliminar"
        if (!usuario.tienePermiso("GESTIONAR_USUARIOS_ADMIN")) {
            vista.btnEliminar.setVisible(false); // Ocultar el botón si no tiene el permiso
        }

        // Puedes agregar más verificaciones para otros botones si es necesario
    }

    private void configurarEventos() {
        vista.btnListar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nuevoListado();
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
                cambiarEstado("Inactivo");
            }
        });

        vista.btnActivar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarEstado("Activo");
            }
        });

        vista.btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminar();
            }
        });

        vista.btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nuevo();
            }
        });

        vista.btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                enModoBusqueda = true; // Activar modo búsqueda
                criterioBusqueda = vista.txtBuscar.getText().trim(); // Guardar el criterio actual
                paginaActual = 0; // Reiniciar a la primera página
                listarUsuariosPorCriterio();
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

    }

    // -------- Metodos de Paginacion ----------------
    private void anteriorPagina() {
        if (paginaActual == 0) {
            mostrarMensaje("Ya estás en la primera página.");
            return;
        }
        paginaActual--;
        listarUsuarios();
        vista.lblPagina.setText("Página: " + (paginaActual + 1));

    }

    private void siguientePagina() {
        if (esUltimaPagina) {
            mostrarMensaje("Ya estás en la última página.");
            return;
        }
        paginaActual++;
        listarUsuarios();
        vista.lblPagina.setText("Página: " + (paginaActual + 1));
    }

    //--------------------------------------------
    private void seleccionarRolDeTabla() {
        int filaSeleccionada = vista.table.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Usuario usuario = obtenerRolDesdeFila(filaSeleccionada);
            vista.txtId.setText(String.valueOf(usuario.getId()));

            // Habilitar botones según el estado del usuario
            if (usuario.getEstado() == EstadoEnum.ACTIVO) {
                vista.btnDarBaja.setEnabled(true);
                vista.btnActivar.setEnabled(false);
            } else if (usuario.getEstado() == EstadoEnum.INACTIVO) {
                vista.btnDarBaja.setEnabled(false);
                vista.btnActivar.setEnabled(true);
            }

            // Habilitar botón actualizar siempre que un usuario sea seleccionado
            vista.btnActualizar.setEnabled(true);
            vista.btnEliminar.setEnabled(true);
        }
    }

    private Usuario obtenerRolDesdeFila(int fila) {
        if (fila >= 0 && fila < usuariosCargados.size()) {
            return usuariosCargados.get(fila); // Recupera los datos completos de la persona desde la lista
        }
        return null;
    }

    private void bucarPorIdODescripcion() {
        int id = Integer.parseInt(vista.txtBuscar.getText()); // Obtén el ID desde la tabla

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule()); // Soporte para LocalDate
                Usuario usuario = mapper.readValue(response.body(), Usuario.class);

                // Se lista por Id o Descripcion
                // Habilitar botones
                vista.btnActualizar.setEnabled(true);
                vista.btnEliminar.setEnabled(true);
            } else {
                mostrarError("Error al buscar " + palabraSingular + ". Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al obtener datos del " + palabraSingular + ": " + e.getMessage());
        }
    }

    public void listarUsuariosPorCriterio() {
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

                // Actualizar la tabla
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
                        usuario.getEstado().name()
                    });
                }

                // Actualizar la información de paginación
                esUltimaPagina = rootNode.path("last").asBoolean(false);
                vista.lblPagina.setText("Página: " + (paginaActual + 1));
            } else {
                JOptionPane.showMessageDialog(vista, "Error al listar usuarios. Código: " + response.statusCode());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al listar usuarios: " + e.getMessage());
        }
    }

    //--------------------------  CRUD ROLES  ----------------------
    public void listarUsuarios() {
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

                    // Validar valores nulos y mostrar cadena vacía si es necesario
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String createdAt = (usuario.getCreatedAt()!= null) ? usuario.getCreatedAt().format(formatter) : "";
                    String updatedAt = (usuario.getUpdatedAt()!= null) ? usuario.getUpdatedAt().format(formatter) : "";

                    model.addRow(new Object[]{
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getEmail(),
                        usuario.getPassword(),
                        rolesDescripcion,
                        usuario.getEstado().name(),
                        createdAt,
                        updatedAt
                    });
                }

                // Actualizar el estado de si es la última página
                esUltimaPagina = rootNode.get("last").asBoolean();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al listar " + palabraPlural + ". Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar " + palabraPlural + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
            Usuario usuarioSeleccionado = obtenerRolDesdeFila(filaSeleccionada);

            if (usuarioSeleccionado == null) {
                JOptionPane.showMessageDialog(vista, "No se pudo obtener el " + palabraSingular + " seleccionado.");
                return;
            }

            // Enviar el rol seleccionado a la vista de edición
            enviarAVistaEdicion(usuarioSeleccionado);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado actualizar " + palabraSingular + ": " + e.getMessage());
        }
    }

    private void enviarAVistaEdicion(Usuario usuario) {

        UsuarioController usuarioController = new UsuarioController(vistaUsuarios, new Usuario());

        // Configurar datos del rol seleccionado en la nueva ventana
        usuarioController.llenarRoles();

        // Configurar la vista de edición con los datos seleccionados
        vistaUsuarios.txtIdUsuario.setText(String.valueOf(usuario.getId()));
        vistaUsuarios.txtNombreUsuario.setText(String.valueOf(usuario.getNombre()));
        vistaUsuarios.txtUsernameUsuario.setText(String.valueOf(usuario.getEmail()));

        ListModel<String> model = vistaUsuarios.jlistRolUser.getModel();

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
        vistaUsuarios.jlistRolUser.setSelectedIndices(selectedIndices);

        vistaUsuarios.lblTextoCrearOEditar.setText("EDITAR " + palabraSingular);
        // Activa los botones necesarios para edición
        vistaUsuarios.txtIdUsuario.setVisible(true);
        vistaUsuarios.btnActualizar.setVisible(true);
        vistaUsuarios.btnGuardar.setVisible(false);

        // Vincular la vista con su controlador
        //new RolController(vistaRoles, new Usuario()); // Inicializa el controlador con la vista y el usuario
        // Ocultar la vista de listado y mostrar la de edición
        vista.setVisible(false); // Oculta la vista actual
        vistaUsuarios.setVisible(true); // Muestra la vista de edición
    }

    private void cambiarEstado(String nuevoEstado) {
        try {
            int id = Integer.parseInt(vista.txtId.getText());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/cambiar-estado/" + id + "?nuevoEstado=" + nuevoEstado))
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(vista, "Estado del " + palabraSingular + " cambiado a " + nuevoEstado + ".");
                listarUsuarios();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al cambiar estado. Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cambiar estado: " + e.getMessage());
        }
    }

    public void eliminar() {
        try {
            Long id = Long.valueOf(vista.txtId.getText());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                mostrarMensaje(palabraSingular + " eliminado con éxito.");
                limpiarCampos();
                listarUsuarios();
            } else {
                mostrarError("Error al eliminar " + palabraSingular + ". Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al eliminar " + palabraSingular + ": " + e.getMessage());
        }
    }

    public void nuevo() {
        UsuarioController usuarioController = new UsuarioController(vistaUsuarios, new Usuario());
        vista.setVisible(false); // Oculta la vista actual
        vistaUsuarios.setVisible(true); // Muestra la vista de edición
    }

    public void nuevoListado() {
        enModoBusqueda = false; // Desactivar modo búsqueda
        criterioBusqueda = ""; // Limpiar el criterio de búsqueda
        paginaActual = 0; // Reiniciar a la primera página
        limpiarCampos();
        listarUsuarios();
    }

    public void limpiarCampos() {
        vista.txtId.setText("");

        // Deshabilitar botones
        vista.btnActualizar.setEnabled(false);
        vista.btnDarBaja.setEnabled(false);
        vista.btnActivar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
    }

    public void limpiarTable() {
        DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
        model.setRowCount(0);
    }

    public void marcaAgua() {
        TextPrompt btnBuscar = new TextPrompt("ID o Descripcion", vista.txtBuscar);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
