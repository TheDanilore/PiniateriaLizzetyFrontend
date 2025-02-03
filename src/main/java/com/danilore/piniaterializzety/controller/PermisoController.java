/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.controller;

import com.danilore.piniaterializzety.clases.TextPrompt;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.JOptionPane;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.danilore.piniaterializzety.models.usuario.Permiso;
import com.danilore.piniaterializzety.models.usuario.Usuario;
import com.danilore.piniaterializzety.views.VPrincipal;
import com.danilore.piniaterializzety.views.usuario.VPermiso;
import com.danilore.piniaterializzety.views.usuario.VPermisoListado;

/**
 *
 * @author ASUS
 */
public final class PermisoController {

    private final VPermiso vista;
    private VPermisoListado vistaListado;
    private static final String BASE_URL = "http://localhost:8080/api/permisos";

    private VPrincipal principal;

    public PermisoController(VPermiso vista, VPermisoListado vistaListado, Usuario usuario) {
        this.vista = vista;
        this.vistaListado = vistaListado;
        configurarEventos();

        // Configuración inicial
        this.vista.txtId.setVisible(false);
        this.vista.lblCodigo.setVisible(false);

        this.vista.panelBtnActualizar.setVisible(false);
        this.vista.panelBtnGuardar.setVisible(true);

        // Habilitar botones según permisos
        configurarBotonesSegunPermisos(usuario);

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
                guardarPermiso();
            }
        });

        vista.btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarPermiso();
                enviarVistaListado();
            }
        });

        vista.btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                limpiarCampos();
            }
        });
    }

    //---------------------   CRUD DE PERMISO   --------------
    private void guardarPermiso() {
        try {
            if (!camposValidos()) {
                JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
                return;
            }

            Permiso permiso = new Permiso();
            permiso.setDescripcion(vista.txtDescripcion.getText().trim());
            permiso.setAccion(vista.txtAccion.getText().trim());

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(permiso);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                mostrarMensaje("Permiso guardado con éxito.");
                limpiarCampos();
            } else {
                mostrarError("Error al guardar permiso. Error: " + response.body());
            }

        } catch (Exception e) {
            mostrarError("Error al guardar el permiso: " + e.getMessage());
        }
    }

    private void actualizarPermiso() {
        try {
            if (!camposValidos()) {
                mostrarMensaje("Debe completar todos los campos.");
                return;
            }

            int id = Integer.parseInt(vista.txtId.getText());
            Permiso permiso = new Permiso();
            permiso.setId(Integer.parseInt(vista.txtId.getText()));
            permiso.setDescripcion(vista.txtDescripcion.getText().trim());
            permiso.setAccion(vista.txtAccion.getText().trim());

            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(permiso);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/editar/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                mostrarMensaje("Permiso actualizado con éxito.");
                limpiarCampos();
            } else {
                mostrarError("Error al actualizar permiso. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error al actualizar el permiso: " + e.getMessage());
        }
    }

    private void enviarVistaListado() {
        PermisoListadoController controller = new PermisoListadoController(vistaListado, vista, new Usuario()); // Pasar el usuario
        vista.setVisible(false);
        vistaListado.setVisible(true);
    }

    //-----------------------------------------------
    private void limpiarCampos() {
        vista.lblCodigo.setVisible(false);
        vista.txtId.setVisible(false);
        vista.txtId.setText("");
        vista.txtDescripcion.setText("");
        vista.txtAccion.setText("");

        vista.lblTextoEditarOCrearPermiso.setText("REGISTRAR PERMISO");

        vista.panelBtnActualizar.setVisible(false);
        vista.panelBtnGuardar.setVisible(true);
        vista.btnLimpiar.setVisible(true);
    }

    private boolean camposValidos() {
        return !vista.txtDescripcion.getText().trim().isEmpty()
                && !vista.txtAccion.getText().trim().isEmpty();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String error) {
        JOptionPane.showMessageDialog(vista, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void marcaAgua() {
        TextPrompt descripcion = new TextPrompt("Descripción del Permiso", vista.txtDescripcion);
        TextPrompt accion = new TextPrompt("Acción (ej. CREATE)", vista.txtAccion);
    }

}
