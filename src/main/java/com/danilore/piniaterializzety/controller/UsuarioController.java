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

        //Deshabilitar los botones
        this.vista.txtIdUsuario.setVisible(false);
        this.vista.btnActualizar.setEnabled(false);

        // Habilitar botones según permisos
        configurarBotonesSegunPermisos(usuario);

        llenarRoles();
        marcaAgua();
    }

    private void configurarBotonesSegunPermisos(Usuario usuario) {
        // Verificar si el usuario tiene el permiso para "Eliminar"


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


        vista.btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nuevoUsuario();
            }
        });



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

    //-----------------------  CRUD USUARIO -----------------------

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
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar usuario. Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar usuario: " + e.getMessage());
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
    }

    public void limpiarCampos() {
        vista.txtIdUsuario.setText("");
        vista.txtContraUsuario.setText("");
        vista.txtNombreUsuario.setText("");
        vista.txtUsernameUsuario.setText("");
        vista.jlistRolUser.clearSelection(); // Método correcto para limpiar la selección

        // Deshabilitar botones
        vista.btnActualizar.setEnabled(false);
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
