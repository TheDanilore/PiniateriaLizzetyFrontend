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
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.danilore.piniaterializzety.models.usuario.Permiso;
import com.danilore.piniaterializzety.models.usuario.Usuario;
import com.danilore.piniaterializzety.views.usuario.VPermisos;
import com.danilore.piniaterializzety.views.usuario.VPermisosListado1;

/**
 *
 * @author ASUS
 */
public final class PermisoListadoController {

    private final VPermisosListado1 vista;
    private final VPermisos vistaPermiso;
    private static final String BASE_URL = "http://localhost:8080/api/permisos";
    private List<Permiso> permisosCargados = new ArrayList<>();

    //Paginacion    
    private int paginaActual = 0; // Página actual
    private final int tamanioPagina = 20; // Tamaño de página fijo
    private boolean esUltimaPagina = false;

    public PermisoListadoController(VPermisosListado1 vistaListado, VPermisos vistaPermiso, Usuario usuario) {
        this.vista = vistaListado;
        this.vistaPermiso = vistaPermiso; // Asocia la vista de edición
        configurarEventos();

        vista.lblPagina.setText("Página: " + (paginaActual + 1));

        // Habilitar botones según permisos
        configurarBotonesSegunPermisos(usuario);

        // Inicializar tabla y cargar datos
        limpiarTable();
        listarPermisos();

        // Añadir placeholders
        marcaAgua();
    }

    private void configurarBotonesSegunPermisos(Usuario usuario) {
        // Verificar si el usuario tiene el permiso para "Eliminar"

        // Puedes agregar más verificaciones para otros botones si es necesario
    }

    private void configurarEventos() {

        vista.btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

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
        listarPermisos();
        vista.lblPagina.setText("Página: " + (paginaActual + 1));

    }

    private void siguientePagina() {
        if (esUltimaPagina) {
            mostrarMensaje("Ya estás en la última página.");
            return;
        }
        paginaActual++;
        listarPermisos();
        vista.lblPagina.setText("Página: " + (paginaActual + 1));
    }

    //---------------------   CRUD DE PERMISO   --------------
    public void listarPermisos() {
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

                permisosCargados = rootNode.get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Permiso>>() {
                        });

                DefaultTableModel model = (DefaultTableModel) vista.tablePermisos.getModel();
                model.setRowCount(0);


                // Poblar datos en la tabla
                for (Permiso permiso : permisosCargados) {
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    panel.add(new JButton("Editar"));
                    panel.add(new JButton("Eliminar"));

                    model.addRow(new Object[]{
                        permiso.getId(), // Aquí almacenas el objeto completo
                        permiso.getDescripcion(),
                        permiso.getAccion(),
                        permiso.getCreatedAt(),
                        permiso.getUpdatedAt()
                    });

                }

                // Actualizar el estado de si es la última página
                esUltimaPagina = rootNode.get("last").asBoolean();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al listar permisos. Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar permisos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enviarAVistaEdicion(Permiso permiso) {
        // Configurar la vista de edición con los datos seleccionados
        vistaPermiso.txtIdPermiso.setText(String.valueOf(permiso.getId()));
        vistaPermiso.txtDescripcion.setText(permiso.getDescripcion());
        vistaPermiso.txtAccion.setText(permiso.getAccion());

        vistaPermiso.lblTextoEditarOCrearPermiso.setText("EDITAR PERMISO");
        // Activa los botones necesarios para edición
        vistaPermiso.btnActualizar.setEnabled(true);

        // Ocultar la vista de listado y mostrar la de edición
        vista.setVisible(false); // Oculta la vista actual
        vistaPermiso.setVisible(true); // Muestra la vista de edición
    }

    private void eliminarPermiso(int id) {
        try {

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                mostrarMensaje("Permiso eliminado correctamente.");
                listarPermisos();
            } else {
                mostrarError("Error al eliminar el permiso. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar el permiso: " + e.getMessage());
        }
    }

    //-----------------------------------------------
    public void limpiarTable() {
        DefaultTableModel model = (DefaultTableModel) vista.tablePermisos.getModel();
        model.setRowCount(0);
    }

    private int mostrarMensajeConfirmacion(String tipo) {
        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de " + tipo + " este permiso?", "Confirmación", JOptionPane.YES_NO_OPTION);
        return confirmacion;
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String error) {
        JOptionPane.showMessageDialog(vista, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void marcaAgua() {
        TextPrompt txtBuscar = new TextPrompt("ID o Descripcion", vista.txtBuscar);
    }

}
