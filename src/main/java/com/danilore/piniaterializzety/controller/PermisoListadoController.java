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
import com.danilore.piniaterializzety.views.usuario.VPermiso;
import com.danilore.piniaterializzety.views.usuario.VPermisoListado;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ASUS
 */
public final class PermisoListadoController {

    private final VPermisoListado vista;
    private final VPermiso vistaPermiso;
    private static final String BASE_URL = "http://localhost:8080/api/permisos";
    private List<Permiso> permisosCargados = new ArrayList<>();

    String palabraSingular = "Permiso";
    String palabraPlural = "Permisos";

    //Paginacion    
    private int paginaActual = 0; // Página actual
    private final int tamanioPagina = 20; // Tamaño de página fijo
    private boolean esUltimaPagina = false;

    //Busqueda
    private boolean enModoBusqueda = false;
    private String criterioBusqueda = ""; // Criterio de búsqueda actual

    public PermisoListadoController(VPermisoListado vistaListado, VPermiso vistaPermiso, Usuario usuario) {
        this.vista = vistaListado;
        this.vistaPermiso = vistaPermiso; // Asocia la vista de edición
        configurarEventos();

        vista.lblPagina.setText("Página: " + (paginaActual + 1));

        // Configuración inicial
        this.vista.btnActualizar.setEnabled(false);
        this.vista.btnEliminar.setEnabled(false);

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

        vista.btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminar();
            }
        });

        vista.table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarPermisoDeTabla();
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
    private void seleccionarPermisoDeTabla() {
        int filaSeleccionada = vista.table.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Permiso permiso = obtenerPermisoDesdeFila(filaSeleccionada);
            //vista.txtId.setText(String.valueOf(permiso.getId()));

            // Habilitar botón actualizar siempre que un usuario sea seleccionado
            vista.btnActualizar.setEnabled(true);
            vista.btnEliminar.setEnabled(true);
        }
    }

    private Permiso obtenerPermisoDesdeFila(int fila) {
        if (fila >= 0 && fila < permisosCargados.size()) {
            return permisosCargados.get(fila); // Recupera los datos completos de la persona desde la lista
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
                permisosCargados = rootNode.get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Permiso>>() {
                        });

                DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
                model.setRowCount(0);

                // Poblar datos en la tabla
                for (Permiso permiso : permisosCargados) {
                    // Validar valores nulos y mostrar cadena vacía si es necesario
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String createdAt = (permiso.getCreatedAt() != null) ? permiso.getCreatedAt().format(formatter) : "";
                    String updatedAt = (permiso.getUpdatedAt() != null) ? permiso.getUpdatedAt().format(formatter) : "";
                    model.addRow(new Object[]{
                        permiso.getId(),
                        permiso.getDescripcion(),
                        permiso.getAccion(),
                        createdAt,
                        updatedAt
                    });

                }

                // Actualizar la información de paginación
                esUltimaPagina = rootNode.path("last").asBoolean(false);
                vista.lblPagina.setText("Página: " + (paginaActual + 1));
            } else {
                JOptionPane.showMessageDialog(vista, "Error al listar " + palabraPlural + ". Código: " + response.statusCode());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al listar " + palabraPlural + ": " + e.getMessage());
        }
    }

    //---------------------   CRUD DE PERMISO   --------------
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

                permisosCargados = rootNode.get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Permiso>>() {
                        });

                DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
                model.setRowCount(0);

                // Poblar datos en la tabla
                for (Permiso permiso : permisosCargados) {
                    // Validar valores nulos y mostrar cadena vacía si es necesario
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String createdAt = (permiso.getCreatedAt() != null) ? permiso.getCreatedAt().format(formatter) : "";
                    String updatedAt = (permiso.getUpdatedAt() != null) ? permiso.getUpdatedAt().format(formatter) : "";
                    model.addRow(new Object[]{
                        permiso.getId(),
                        permiso.getDescripcion(),
                        permiso.getAccion(),
                        createdAt,
                        updatedAt
                    });

                }

                // Actualizar el estado de si es la última página
                esUltimaPagina = rootNode.get("last").asBoolean();

                this.vista.btnActualizar.setEnabled(false);
                this.vista.btnEliminar.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al listar " + palabraPlural + ". Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar " + palabraPlural + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void nuevo() {
        PermisoController permisoGuardarController = new PermisoController(vistaPermiso, vista, new Usuario());
        vistaPermiso.setVisible(true); // Muestra la vista de edición
        vista.setVisible(false);
    }

    public void actualizar() {
        try {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = vista.table.getSelectedRow();

            Permiso permisoSeleccionado = obtenerPermisoDesdeFila(filaSeleccionada);

            if (permisoSeleccionado == null) {
                JOptionPane.showMessageDialog(vista, "No se pudo obtener el " + palabraSingular + " seleccionado.");
                return;
            }

            // Enviar el rol seleccionado a la vista de edición
            enviarAVistaEdicion(permisoSeleccionado);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado actualizar " + palabraSingular + ": " + e.getMessage());
        }
    }

    private void enviarAVistaEdicion(Permiso permiso) {
        PermisoController permisoEditarController = new PermisoController(vistaPermiso, vista, new Usuario());

        // Configurar la vista de edición con los datos seleccionados
        vistaPermiso.lblCodigo.setVisible(true);
        vistaPermiso.txtId.setText(String.valueOf(permiso.getId()));
        vistaPermiso.txtId.setVisible(true);
        vistaPermiso.txtDescripcion.setText(permiso.getDescripcion());
        vistaPermiso.txtAccion.setText(permiso.getAccion());

        vistaPermiso.lblTextoEditarOCrearPermiso.setText("EDITAR PERMISO");
        // Activa los botones necesarios para edición
        vistaPermiso.panelBtnActualizar.setVisible(true);
        vistaPermiso.panelBtnGuardar.setVisible(false);
        vistaPermiso.btnLimpiar.setVisible(false);

        // Ocultar la vista de listado y mostrar la de edición
        vistaPermiso.setVisible(true); // Muestra la vista de edición
        vista.setVisible(false);
    }

    public void eliminar() {
        try {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = vista.table.getSelectedRow();

            Permiso permisoSeleccionado = obtenerPermisoDesdeFila(filaSeleccionada);

            int id = permisoSeleccionado.getId();

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

    //-----------------------------------------------
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

    private void mostrarError(String error) {
        JOptionPane.showMessageDialog(vista, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void marcaAgua() {
        TextPrompt txtBuscar = new TextPrompt("ID o Descripcion", vista.txtBuscar);
    }

}
