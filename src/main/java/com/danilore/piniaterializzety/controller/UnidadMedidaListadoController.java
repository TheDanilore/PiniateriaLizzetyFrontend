package com.danilore.piniaterializzety.controller;

import com.danilore.piniaterializzety.clases.TextPrompt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import com.danilore.piniaterializzety.models.persona.Departamento;
import com.danilore.piniaterializzety.models.persona.Distrito;
import com.danilore.piniaterializzety.models.persona.enums.GeneroEnum;
import com.danilore.piniaterializzety.models.persona.Persona;
import com.danilore.piniaterializzety.models.persona.Provincia;
import com.danilore.piniaterializzety.models.persona.TipoDocumentoIdentidad;
import com.danilore.piniaterializzety.models.producto.UnidadMedida;
import com.danilore.piniaterializzety.views.persona.VPersonaListado;
import com.danilore.piniaterializzety.views.producto.VUnidadMedidaListado;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Danilore
 */
public final class UnidadMedidaListadoController {

    private final VUnidadMedidaListado vista;
    private static final String BASE_URL = "http://localhost:8080/api/unidad-medida";
    private List<UnidadMedida> unidadesCargados = new ArrayList<>();

    String palabraSingular = "Unidad de Medida";
    String palabraPlural = "Unidades de Medida";

    //Paginacion    
    private int paginaActual = 0; // Página actual
    private final int tamanioPagina = 20; // Tamaño de página fijo
    private boolean esUltimaPagina = false;

    public UnidadMedidaListadoController(VUnidadMedidaListado v) {
        this.vista = v;

        vista.lblPagina.setText("Página: " + (paginaActual + 1));
        // Ocultamos el txtIdPersona
        this.vista.txtId.setVisible(false);

        // Configurar eventos
        configurarEventos();

        // Inicializar combos 
        listar();
        limpiarCampos();
        marcaAgua();

        // Botones inactivos al inicio
        this.vista.btnActualizar.setEnabled(false);
        this.vista.btnEliminar.setEnabled(false);
    }

    private void configurarEventos() {
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

        vista.btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nuevo();
            }
        });
        vista.btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

            }
        });

        vista.table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarUnidadDeTabla();
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
    private void seleccionarUnidadDeTabla() {
        int filaSeleccionada = vista.table.getSelectedRow();
        if (filaSeleccionada >= 0) {
            UnidadMedida unidad = obtenerUnidadDesdeFila(filaSeleccionada);

            // Habilitar botón actualizar siempre que un usuario sea seleccionado
            vista.btnActualizar.setEnabled(true);
            vista.btnEliminar.setEnabled(true);
        }
    }

    private UnidadMedida obtenerUnidadDesdeFila(int fila) {
        if (fila >= 0 && fila < unidadesCargados.size()) {
            return unidadesCargados.get(fila); // Recupera los datos completos de la persona desde la lista
        }
        return null;
    }

    // ------------------ CRUD ------------------
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

                unidadesCargados = rootNode.get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<UnidadMedida>>() {
                        });

                DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
                model.setRowCount(0); // Limpia la tabla

                for (UnidadMedida unidad : unidadesCargados) {
                    // Validar valores nulos y mostrar cadena vacía si es necesario
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SS");
                    String createdAt = (unidad.getCreatedAt() != null) ? unidad.getCreatedAt().format(formatter) : "";
                    String updatedAt = (unidad.getUpdatedAt() != null) ? unidad.getUpdatedAt().format(formatter) : "";

                    model.addRow(new Object[]{
                        unidad.getId(),
                        unidad.getAbreviatura(),
                        unidad.getDescripcion(),
                        createdAt,
                        updatedAt
                    });
                }

                // Actualizar el estado de si es la última página
                esUltimaPagina = rootNode.get("last").asBoolean();
            } else {
                mostrarError("Error al listar personas. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al listar personas: " + e.getMessage());
        }
    }

    public void guardar() {
        try {

        } catch (Exception e) {
            mostrarError("Error inesperado al guardar persona: " + e.getMessage());
        }
    }

    public void actualizar() {
        try {

        } catch (Exception e) {
            mostrarError("Error inesperado al actualizar persona: " + e.getMessage());
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
                mostrarMensaje("Persona eliminada con éxito.");
                limpiarCampos();
                listar();
            } else {
                mostrarError("Error al eliminar" + palabraSingular + ". Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al eliminar : " + e.getMessage());
        }
    }

    // ------------------ UTILITARIOS ------------------
    public void limpiarTable() {
        DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
        model.setRowCount(0);
    }

    private void limpiarCampos() {
        vista.txtId.setText("");
        vista.txtBuscar.setText("");

        //Deshabilitar botones
        vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
    }

    private void nuevo() {
        limpiarCampos();
        listar();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void marcaAgua() {
        TextPrompt buscarPorIdPersona = new TextPrompt("Id, descripcion o abreviatura", vista.txtBuscar);
    }

}
