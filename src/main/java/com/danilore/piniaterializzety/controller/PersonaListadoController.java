package com.danilore.piniaterializzety.controller;

import com.danilore.piniaterializzety.clases.TextPrompt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import com.danilore.piniaterializzety.models.persona.Persona;
import com.danilore.piniaterializzety.views.persona.VPersonaListado;

/**
 *
 * @author Danilore
 */
public final class PersonaListadoController {

    private final VPersonaListado vista;
    private static final String BASE_URL = "http://localhost:8080/api/personas";
    private List<Persona> personasCargadas = new ArrayList<>();

    //Paginacion    
    private int paginaActual = 0; // Página actual
    private final int tamanioPagina = 20; // Tamaño de página fijo
    private boolean esUltimaPagina = false;

    public PersonaListadoController(VPersonaListado v) {
        this.vista = v;

        vista.lblPagina.setText("Página: " + (paginaActual+1));
        // Ocultamos el txtIdPersona
        this.vista.txtId.setVisible(false);

        // Configurar eventos
        configurarEventos();

        listarPersonas();
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
                actualizarPersona();
            }
        });

        vista.btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminarPersona();
            }
        });

        vista.btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nuevaPersona();
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
                seleccionarPersonaDeTabla();
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
        listarPersonas();
        vista.lblPagina.setText("Página: " + (paginaActual+1));

    }

    private void siguientePagina() {
        if (esUltimaPagina) {
            mostrarMensaje("Ya estás en la última página.");
            return;
        }
        paginaActual++;
        listarPersonas();
        vista.lblPagina.setText("Página: " + (paginaActual+1));
    }

    //--------------------------------------------
    private void seleccionarPersonaDeTabla() {
        int filaSeleccionada = vista.table.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Persona persona = obtenerPersonaDesdeFila(filaSeleccionada);

            // Habilitar botón actualizar siempre que un usuario sea seleccionado
            vista.btnActualizar.setEnabled(true);
            vista.btnEliminar.setEnabled(true);
        }
    }

    private Persona obtenerPersonaDesdeFila(int fila) {
        if (fila >= 0 && fila < personasCargadas.size()) {
            return personasCargadas.get(fila); // Recupera los datos completos de la persona desde la lista
        }
        return null;
    }

    // ------------------ CRUD PERSONA ------------------
    public void listarPersonas() {
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

                personasCargadas = rootNode.get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Persona>>() {
                        });

                DefaultTableModel model = (DefaultTableModel) vista.table.getModel();
                model.setRowCount(0); // Limpia la tabla
                for (Persona persona : personasCargadas) {
                    model.addRow(new Object[]{
                        persona.getId(),
                        persona.getNombres(),
                        persona.getApellidos(),
                        persona.getDireccion(),
                        persona.getDistrito().getDescripcion()
                        + " - " + persona.getProvincia().getDescripcion()
                        + " - " + persona.getDepartamento().getDescripcion(), //Descripcion del destrito
                        persona.getTelefono(),
                        persona.getCorreo(),
                        persona.getFechaNacimiento() != null ? persona.getFechaNacimiento().toString() : "",
                        persona.getGenero() != null ? persona.getGenero().name() : "",
                        persona.getLugarNacimiento()
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

    public void guardarPersona() {
        try {


        } catch (Exception e) {
            mostrarError("Error inesperado al guardar persona: " + e.getMessage());
        }
    }

    public void actualizarPersona() {
        try {

           
        } catch (Exception e) {
            mostrarError("Error inesperado al actualizar persona: " + e.getMessage());
        }
    }

    public void eliminarPersona() {
        try {
            Long id = Long.valueOf(vista.txtId.getText());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if ("".equals(response.body())) {
                mostrarMensaje("Persona eliminada con éxito.");
                limpiarCampos();
                listarPersonas();
            } else {
                mostrarError("Error al eliminar persona. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al eliminar persona: " + e.getMessage());
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

    private void nuevaPersona() {
        limpiarCampos();
        listarPersonas();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void marcaAgua() {
        TextPrompt buscarPorIdPersona = new TextPrompt("Id Persona", vista.txtBuscar);
    }

    

}
