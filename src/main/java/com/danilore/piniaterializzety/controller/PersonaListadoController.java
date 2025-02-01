package com.danilore.piniaterializzety.controller;

import com.danilore.piniaterializzety.clases.TextPrompt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import java.util.Map;
import java.util.List;
import com.danilore.piniaterializzety.models.persona.Departamento;
import com.danilore.piniaterializzety.models.persona.Distrito;
import com.danilore.piniaterializzety.models.persona.enums.GeneroEnum;
import com.danilore.piniaterializzety.models.persona.Persona;
import com.danilore.piniaterializzety.models.persona.Provincia;
import com.danilore.piniaterializzety.models.persona.TipoDocumentoIdentidad;
import com.danilore.piniaterializzety.views.persona.VPersona;
import com.danilore.piniaterializzety.views.persona.VPersonaListado;

/**
 *
 * @author Danilore
 */
public final class PersonaListadoController {

    private final VPersonaListado vista;
    private static final String BASE_URL = "http://localhost:8080/api/personas";
    private static final String BASE_URL_DEPARTAMENTOS = "http://localhost:8080/api/departamentos";
    private static final String BASE_URL_PROVINCIAS = "http://localhost:8080/api/provincias";
    private static final String BASE_URL_DISTRITOS = "http://localhost:8080/api/distritos";
    private static final String BASE_URL_TIPOS_DOCUMENTOS = "http://localhost:8080/api/tipodocumentoidentidad";
    private List<Departamento> departamentosCargados = new ArrayList<>();
    private List<Provincia> provinciasCargados = new ArrayList<>();
    private List<Distrito> distritosCargados = new ArrayList<>();
    private List<TipoDocumentoIdentidad> tiposDocumentoCargados = new ArrayList<>();
    private List<Persona> personasCargadas = new ArrayList<>();

    //Paginacion    
    private int paginaActual = 0; // Página actual
    private final int tamanioPagina = 20; // Tamaño de página fijo
    private boolean esUltimaPagina = false;

    public PersonaListadoController(VPersonaListado v) {
        this.vista = v;

        vista.lblPagina.setText("Página: " + (paginaActual+1));
        // Ocultamos el txtIdPersona
        this.vista.txtIdPersona.setVisible(false);

        // Configurar eventos
        configurarEventos();

        // Inicializar combos 
        cargarGeneros();
        cargarDepartamentos();
        listarPersonas();
        limpiarCampos();
        marcaAgua();

        // Botones inactivos al inicio
        this.vista.btnActualizar1.setEnabled(false);
        this.vista.btnEliminar1.setEnabled(false);
    }

    private void configurarEventos() {
        vista.btnActualizar1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarPersona();
            }
        });

        vista.btnEliminar1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eliminarPersona();
            }
        });

        vista.btnNuevo1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nuevaPersona();
            }
        });
        vista.btnBuscarPorId3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

            }
        });

        vista.tablePersona1.addMouseListener(new java.awt.event.MouseAdapter() {
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
        
        vista.cboDepartamento1.addActionListener(evt -> onDepartamentoChange());
        vista.cboProvincia1.addActionListener(evt -> onProvinciaChange());

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
        int filaSeleccionada = vista.tablePersona1.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Persona persona = obtenerPersonaDesdeFila(filaSeleccionada);


            // Mostrar departamento de la persona en combobox
            vista.cboDepartamento1.setSelectedItem(departamentosCargados.stream()
                    .filter(dep -> dep.getIdDepartamento() == persona.getDepartamento().getIdDepartamento())
                    .findFirst().orElse(null));

            // Mostrar provincia de la persona en combobox
            vista.cboProvincia1.setSelectedItem(provinciasCargados.stream()
                    .filter(pro -> pro.getIdProvincia() == persona.getProvincia().getIdProvincia())
                    .findFirst().orElse(null));
            // Mostrar distrito de la persona en combobox
            vista.cboDistrito1.setSelectedItem(distritosCargados.stream()
                    .filter(dis -> dis.getIdDistrito() == persona.getDistrito().getIdDistrito())
                    .findFirst().orElse(null));

            if (persona.getFechaNacimiento() != null) {
                vista.jCalendarCbxFechaNacimiento1.setDate(
                        Date.from(persona.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant())
                );
            } else {
                vista.jCalendarCbxFechaNacimiento1.setDate(null);
            }

            // Mostrar genero de la persona en combobox
            vista.cboGenero1.setSelectedItem(String.valueOf(persona.getGenero()));


            // Habilitar botón actualizar siempre que un usuario sea seleccionado
            vista.btnActualizar1.setEnabled(true);
            vista.btnEliminar1.setEnabled(true);
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

                DefaultTableModel model = (DefaultTableModel) vista.tablePersona1.getModel();
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
            Long id = Long.valueOf(vista.txtIdPersona.getText());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
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
        DefaultTableModel model = (DefaultTableModel) vista.tablePersona1.getModel();
        model.setRowCount(0);
    }

    private void limpiarCampos() {
        vista.txtIdPersona.setText("");
        vista.txtBuscarPorId1.setText("");
        vista.cboDepartamento1.setSelectedIndex(0);
        vista.jCalendarCbxFechaNacimiento1.setDate(null);
        vista.cboGenero1.setSelectedIndex(0);

        //Deshabilitar botones
        vista.btnActualizar1.setEnabled(false);
        vista.btnEliminar1.setEnabled(false);
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
        TextPrompt buscarPorIdPersona = new TextPrompt("Id Persona", vista.txtBuscarPorId1);
    }

    // ------------------ CARGAR COMBOS ------------------
    private void cargarGeneros() {
        vista.cboGenero1.removeAllItems();

        // Agregar la opción predeterminada "Seleccione Género"
        vista.cboGenero1.addItem("Seleccione Género");

        // Agregar los valores del enumerado GeneroEnum
        for (GeneroEnum genero : GeneroEnum.values()) {
            vista.cboGenero1.addItem(genero.name());
        }

        // Seleccionar la opción predeterminada explícitamente
        vista.cboGenero1.setSelectedIndex(0);
    }

    private void cargarDepartamentos() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL_DEPARTAMENTOS))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                departamentosCargados = mapper.readTree(response.body()) // Leer el árbol JSON
                        .get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Departamento>>() {
                        });

                vista.cboDepartamento1.removeAllItems();
                vista.cboDepartamento1.addItem(new Departamento(-1, "Seleccione Departamento"));

                for (Departamento d : departamentosCargados) {
                    vista.cboDepartamento1.addItem(d);
                }
            } else {
                mostrarError("Error al cargar departamentos. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al cargar departamentos: " + e.getMessage());
        }
    }

    private void onDepartamentoChange() {
        Departamento dep = (Departamento) vista.cboDepartamento1.getSelectedItem();
        if (dep == null || dep.getIdDepartamento() == -1) {
            vista.cboProvincia1.removeAllItems();
            vista.cboDistrito1.removeAllItems();
            return;
        }
        cargarProvincias(dep.getIdDepartamento());
    }

    private void cargarProvincias(int idDep) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL_PROVINCIAS + "/departamento/" + idDep))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                provinciasCargados = mapper.readTree(response.body()) // Leer el árbol JSON
                        .get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Provincia>>() {
                        });

                vista.cboProvincia1.removeAllItems();
                vista.cboProvincia1.addItem(new Provincia(-1, "Seleccione Provincia", null));

                for (Provincia p : provinciasCargados) {
                    vista.cboProvincia1.addItem(p);
                }

                vista.cboDistrito1.removeAllItems();
            } else {
                mostrarError("Error al cargar provincias. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error al cargar provincias: " + e.getMessage());
        }
    }

    private void onProvinciaChange() {
        Provincia prov = (Provincia) vista.cboProvincia1.getSelectedItem();
        if (prov == null || prov.getIdProvincia() == -1) {
            vista.cboDistrito1.removeAllItems();
            return;
        }
        cargarDistritos(prov.getIdProvincia());
    }

    private void cargarDistritos(int idProv) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL_DISTRITOS + "/provincia/" + idProv))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                distritosCargados = mapper.readTree(response.body()) // Leer el árbol JSON
                        .get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Distrito>>() {
                        });

                vista.cboDistrito1.removeAllItems();
                vista.cboDistrito1.addItem(new Distrito(-1, "Seleccione Distrito", null, null));

                for (Distrito d : distritosCargados) {
                    vista.cboDistrito1.addItem(d);
                }
            } else {
                mostrarError("Error al cargar distritos. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error al cargar distritos: " + e.getMessage());
        }
    }

}
