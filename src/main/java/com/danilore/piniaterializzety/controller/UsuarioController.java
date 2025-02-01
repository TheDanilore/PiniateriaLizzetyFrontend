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
import com.danilore.piniaterializzety.views.usuario.VUsuarios;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import org.mindrot.jbcrypt.BCrypt;
import com.danilore.piniaterializzety.models.usuario.Usuario;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.danilore.piniaterializzety.models.EstadoEnum;
import com.danilore.piniaterializzety.models.usuario.Rol;

/**
 *
 * @author Danilore
 */
public final class UsuarioController {

    VUsuarios vista = new VUsuarios();
    private static final String BASE_URL = "http://localhost:8080/api/usuarios";
    private static final String BASE_URL_ROLES = "http://localhost:8080/api/roles";
    private List<Rol> rolesCargados = new ArrayList<>(); // Declarar rolesCargados
    private List<Usuario> usuariosCargados = new ArrayList<>();

    //Paginacion    
    private int paginaActual = 0; // Página actual
    private final int tamanioPagina = 15; // Tamaño de página fijo
    private boolean esUltimaPagina = false;

    //Busqueda
    private boolean enModoBusqueda = false;
    private String criterioBusqueda = ""; // Criterio de búsqueda actual

    public UsuarioController(VUsuarios v, Usuario usuario) {
        this.vista = v;
        configurarEventos();

        vista.lblPagina.setText("Página: " + (paginaActual + 1));

        //Deshabilitar los botones
        this.vista.txtIdUsuario.setVisible(false);
        this.vista.btnActualizar.setEnabled(false);
        this.vista.btnDarBaja.setEnabled(false);
        this.vista.btnActivar.setEnabled(false);
        this.vista.btnEliminar.setEnabled(false);

        // Habilitar botones según permisos
        configurarBotonesSegunPermisos(usuario);

        llenarRoles();
        limpiarTable();
        listarUsuarios();
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
        vista.btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardarUsuario();
            }
        });

        vista.btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarUsuario();
            }
        });

        vista.btnDarBaja.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarEstadoUsuario("Inactivo");
            }
        });

        vista.btnActivar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarEstadoUsuario("Activo");
            }
        });
        vista.btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminarUsuario();
            }
        });

        vista.btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nuevoUsuario();
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

        vista.tableUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
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

    }

    // -------- Metodos de Paginacion ----------------
    private void anteriorPagina() {
        if (paginaActual == 0) {
            mostrarMensaje("Ya estás en la primera página.");
            return;
        }
        paginaActual--;
        if (enModoBusqueda) {
            listarUsuariosPorCriterio();
        } else {
            listarUsuarios();
        }
        vista.lblPagina.setText("Página: " + (paginaActual + 1));
    }

    private void siguientePagina() {
        if (esUltimaPagina) {
            mostrarMensaje("Ya estás en la última página.");
            return;
        }
        paginaActual++;
        if (enModoBusqueda) {
            listarUsuariosPorCriterio();
        } else {
            listarUsuarios();
        }
        vista.lblPagina.setText("Página: " + (paginaActual + 1));
    }

    //--------------------------------------------
    private void mostrarRolesSeleccionados(Usuario usuario) {
        ListModel<String> model = vista.jlistRolUser.getModel();
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
        vista.jlistRolUser.setSelectedIndices(selectedIndices);
    }

    private void seleccionarUsuarioDeTabla() {
        int filaSeleccionada = vista.tableUsuario.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Usuario usuario = obtenerUsuarioDesdeFila(filaSeleccionada);
            vista.txtIdUsuario.setText(String.valueOf(usuario.getId()));
            vista.txtNombreUsuario.setText(usuario.getNombre());
            vista.txtUsernameUsuario.setText(usuario.getEmail());

            // Mostrar roles seleccionados
            mostrarRolesSeleccionados(usuario);

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
            this.vista.btnEliminar.setEnabled(true);
        }
    }

    private Usuario obtenerUsuarioDesdeFila(int fila) {
        if (fila >= 0 && fila < usuariosCargados.size()) {
            return usuariosCargados.get(fila); // Recupera los datos completos de la persona desde la lista
        }
        return null;
    }

    public void llenarRoles() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL_ROLES + "?page=" + 0 + "&size=" + 30))
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

                DefaultListModel<String> listModel = new DefaultListModel<>();

                for (Rol rol : rolesCargados) {
                    listModel.addElement(rol.getDescripcion()); // Solo añade las descripciones al modelo
                }
                vista.jlistRolUser.setModel(listModel); // Actualiza la lista de roles en la vista
            } else {
                JOptionPane.showMessageDialog(vista, "Error al cargar roles. Código: " + response.statusCode());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar roles: " + e.getMessage());
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
                DefaultTableModel model = (DefaultTableModel) vista.tableUsuario.getModel();
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

    //-----------------------  CRUD USUARIO -----------------------
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

                DefaultTableModel model = (DefaultTableModel) vista.tableUsuario.getModel();
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
                        rolesDescripcion, // Mostrar las descripciones de los roles
                        usuario.getEstado().name()
                    });
                }

                // Actualizar el estado de si es la última página
                esUltimaPagina = rootNode.get("last").asBoolean();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al listar usuarios. Código: " + response.statusCode());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al listar usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuario() {
        try {
            if (!camposValidos()) {
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
                return;
            }
            Usuario usuario = new Usuario();
            usuario.setNombre(vista.txtNombreUsuario.getText());
            usuario.setEmail(vista.txtUsernameUsuario.getText());
            String password = vista.txtContraUsuario.getText();

            if (!validarPassword(password)) {
                return;
            }
            usuario.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

            // Obtener roles seleccionados
            List<String> selectedRoles = vista.jlistRolUser.getSelectedValuesList();
            List<Rol> roles = selectedRoles.stream()
                    .map(desc -> rolesCargados.stream()
                    .filter(rol -> rol.getDescripcion().equals(desc))
                    .findFirst()
                    .orElse(null))
                    .filter(rol -> rol != null)
                    .toList();

            usuario.setRoles(roles);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(usuario);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                JOptionPane.showMessageDialog(vista, "Usuario guardado con éxito.");
                listarUsuarios();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar usuario. Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar usuario: " + e.getMessage());
        }
    }

    private void actualizarUsuario() {
        try {
            if (!camposValidos()) {
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
                return;
            }
            int id = Integer.parseInt(vista.txtIdUsuario.getText());
            Usuario usuario = new Usuario();
            usuario.setNombre(vista.txtNombreUsuario.getText());
            usuario.setEmail(vista.txtUsernameUsuario.getText());
            usuario.setPassword(vista.txtContraUsuario.getText());

            // Obtener roles seleccionados
            List<String> selectedRoles = vista.jlistRolUser.getSelectedValuesList();
            List<Rol> roles = selectedRoles.stream()
                    .map(desc -> rolesCargados.stream()
                    .filter(rol -> rol.getDescripcion().equals(desc))
                    .findFirst()
                    .orElse(null))
                    .filter(rol -> rol != null)
                    .toList();

            usuario.setRoles(roles);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(usuario);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/editar/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(vista, "Usuario actualizado con éxito.");
                listarUsuarios();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar usuario. Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar usuario: " + e.getMessage());
        }
    }

    private void cambiarEstadoUsuario(String nuevoEstado) {
        try {
            int id = Integer.parseInt(vista.txtIdUsuario.getText());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/cambiar-estado/" + id + "?nuevoEstado=" + nuevoEstado))
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.noBody()) // Usar .method() para PATCH
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(vista, "Estado del usuario cambiado a " + nuevoEstado + ".");
                listarUsuarios();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al cambiar estado. Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cambiar estado: " + e.getMessage());
        }
    }

    public void eliminarUsuario() {
        try {
            Long id = Long.valueOf(vista.txtIdUsuario.getText());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                mostrarMensaje("Usuario eliminado con éxito.");
                limpiarCampos();
                listarUsuarios();
            } else {
                mostrarError("Error al eliminar usuario. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al eliminar usuario: " + e.getMessage());
        }
    }

    //-------------------------------------------------
    private boolean validarPassword(String password) {
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(vista, "La contraseña debe tener al menos 6 caracteres.");
            return false;
        }
        if (password.matches(".*\\s+.*")) {
            JOptionPane.showMessageDialog(vista, "La contraseña no puede contener espacios.");
            return false;
        }
        return true;
    }

    public void nuevoUsuario() {
        enModoBusqueda = false; // Desactivar modo búsqueda
        criterioBusqueda = ""; // Limpiar el criterio de búsqueda
        paginaActual = 0; // Reiniciar a la primera página
        limpiarCampos();
        listarUsuarios();
    }

    public void limpiarCampos() {
        vista.txtIdUsuario.setText("");
        vista.txtContraUsuario.setText("");
        vista.txtNombreUsuario.setText("");
        vista.txtUsernameUsuario.setText("");
        vista.jlistRolUser.clearSelection(); // Método correcto para limpiar la selección

        // Deshabilitar botones
        vista.btnActualizar.setEnabled(false);
        vista.btnDarBaja.setEnabled(false);
        vista.btnActivar.setEnabled(false);
    }

    public void limpiarTable() {
        DefaultTableModel model = (DefaultTableModel) vista.tableUsuario.getModel();
        model.setRowCount(0);
    }

    public boolean camposValidos() {
        return !vista.txtContraUsuario.getText().isEmpty()
                && !vista.txtNombreUsuario.getText().isEmpty()
                && !vista.txtUsernameUsuario.getText().isEmpty();
    }

    public void marcaAgua() {
        TextPrompt nombreUser = new TextPrompt("Nombres del Usuario", vista.txtNombreUsuario);
        TextPrompt user = new TextPrompt("Correo", vista.txtUsernameUsuario);
        TextPrompt contra = new TextPrompt("Contraseña", vista.txtContraUsuario);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
