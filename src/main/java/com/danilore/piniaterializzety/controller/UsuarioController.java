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
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import org.mindrot.jbcrypt.BCrypt;
import com.danilore.piniaterializzety.models.usuario.Usuario;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import com.danilore.piniaterializzety.models.usuario.Rol;
import com.danilore.piniaterializzety.views.usuario.VUsuario;

/**
 *
 * @author Danilore
 */
public final class UsuarioController {

    VUsuario vista = new VUsuario();
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

    public UsuarioController(VUsuario v, Usuario usuario) {
        this.vista = v;

        //Deshabilitar los botones
        this.vista.txtId.setVisible(false);
        this.vista.btnActualizar.setEnabled(false);

        // Habilitar botones según permisos
        configurarBotonesSegunPermisos(usuario);

        llenarRoles();
    }

    private void configurarBotonesSegunPermisos(Usuario usuario) {
        // Verificar si el usuario tiene el permiso para "Eliminar"


        // Puedes agregar más verificaciones para otros botones si es necesario
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



   

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
