/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.controller;

import com.danilore.piniaterializzety.clases.TextPrompt;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.danilore.piniaterializzety.views.usuario.VRoles;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import com.danilore.piniaterializzety.models.usuario.Permiso;
import com.danilore.piniaterializzety.models.usuario.Rol;
import com.danilore.piniaterializzety.models.usuario.Usuario;
import com.danilore.piniaterializzety.services.RolService;
import com.danilore.piniaterializzety.views.usuario.VRolesListado;

/**
 *
 * @author Danilore
 */
public final class RolController {

    RolService rolService = new RolService();
    VRolesListado vistaRolesListado = new VRolesListado();
    private final VRoles vista;
    private static final String BASE_URL = "http://localhost:8080/api/roles";
    private static final String BASE_URL_Permisos = "http://localhost:8080/api/permisos";
    private List<Permiso> permisosCargados = new ArrayList<>();

    public RolController(VRoles v, Usuario usuario) {
        this.vista = v;
        configurarEventos();

        // Configuración inicial
        this.vista.lblCodigo.setVisible(false);
        this.vista.txtIdRolesUsuario.setVisible(false);
        this.vista.btnActualizar.setVisible(false);

        // Habilitar botones según permisos
        configurarBotonesSegunPermisos(usuario);

        // Inicializar tabla y cargar datos
        llenarPermisos();

        // Añadir placeholders
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
                guardar();
            }
        });

        vista.btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizar();
            }
        });

        vista.btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nuevoRol();
            }
        });

    }

    //--------------------------------------------
    public void llenarPermisos() {
        permisosCargados = rolService.cargarPermisos();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        permisosCargados.forEach(permiso -> listModel.addElement(permiso.getDescripcion()));
        vista.jlistPermisosRoles.setModel(listModel);
    }

    //--------------------------  CRUD ROLES  ----------------------
    public void guardar() {
        try {
            if (!camposValidos()) {
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
                return;
            }

            Rol rol = new Rol();
            rol.setDescripcion(vista.txtDescripcion.getText());

            // Obtener roles seleccionados
            List<String> selectedPemisos = vista.jlistPermisosRoles.getSelectedValuesList();
            List<Permiso> permisos = selectedPemisos.stream()
                    .map(desc -> permisosCargados.stream()
                    .filter(permiso -> permiso.getDescripcion().equals(desc))
                    .findFirst()
                    .orElse(null))
                    .filter(permiso -> permiso != null)
                    .toList();

            rol.setPermisos(permisos);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(rol);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                JOptionPane.showMessageDialog(vista, "Rol guardado con éxito.");
                limpiarCampos();
                //RolControllerListado rolController = new RolControllerListado(vistaRolesListado, new Usuario());
                //vista.setVisible(false);
                //vistaRolesListado.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar rol. Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado al guardar rol: " + e.getMessage());
        }
    }

    public void actualizar() {
        try {

            int id = Integer.parseInt(vista.txtIdRolesUsuario.getText());
            Rol rol = new Rol();
            rol.setDescripcion(vista.txtDescripcion.getText());

            // Obtener roles seleccionados
            List<String> selectedPermisos = vista.jlistPermisosRoles.getSelectedValuesList();
            List<Permiso> permisos = selectedPermisos.stream()
                    .map(desc -> permisosCargados.stream()
                    .filter(permiso -> permiso.getDescripcion().equals(desc))
                    .findFirst()
                    .orElse(null))
                    .filter(permiso -> permiso != null)
                    .toList();

            rol.setPermisos(permisos);

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(rol);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/editar/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(null, "Rol actualizado con éxito.");
                nuevoRol();
                //vista.setVisible(false);
                //vistaRolesListado.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar Rol. Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado actualizar rol: " + e.getMessage());
        }
    }

    public void nuevoRol() {
        this.vista.btnGuardar.setVisible(true);
        this.vista.lblCodigo.setVisible(false);
        this.vista.lblTextoCrearOEditar.setText("REGISTRAR NUEVO ROL");
        this.vista.txtIdRolesUsuario.setVisible(false);
        this.vista.btnActualizar.setVisible(false);
        limpiarCampos();
    }

    public void limpiarCampos() {
        vista.txtIdRolesUsuario.setText("");
        vista.txtDescripcion.setText("");
        vista.jlistPermisosRoles.clearSelection(); // Desmarcar todos los permisos

        // Deshabilitar botones
        this.vista.btnActualizar.setVisible(false);
    }

    public boolean camposValidos() {
        return !vista.txtDescripcion.getText().isEmpty();
    }

    public void marcaAgua() {
        TextPrompt descripcion = new TextPrompt("Nombre del cargo", vista.txtDescripcion);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
