package com.danilore.piniaterializzety.controller;

import com.danilore.piniaterializzety.clases.TextPrompt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
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

/**
 *
 * @author Danilore
 */
public final class PersonaController {

    private final VPersona vista;
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

    public PersonaController(VPersona v) {
        this.vista = v;
        // Ocultamos el txtIdPersona
        this.vista.txtIdPersona.setVisible(false);

        // Configurar eventos
        configurarEventos();

        // Inicializar combos 
        cargarGeneros();
        cargarTiposDocumento();
        cargarDepartamentos();
        limpiarCampos();
        marcaAgua();

        // Botones inactivos al inicio
        this.vista.btnActualizar1.setEnabled(false);
    }

    private void configurarEventos() {
        vista.btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardarPersona();
            }
        });

        vista.cboDepartamento.addActionListener(evt -> onDepartamentoChange());
        vista.cboProvincia.addActionListener(evt -> onProvinciaChange());

    }

    // ------------------ CRUD PERSONA ------------------
    public void guardarPersona() {
        try {
            if (!camposValidos()) {
                mostrarError("Debe llenar todos los campos.");
                return;
            }

            Persona persona = obtenerPersonaDesdeVista();

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String requestBody = mapper.writeValueAsString(persona);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                // Intentar analizar el cuerpo de la respuesta
                String body = response.body();
                try {
                    ObjectMapper mapper2 = new ObjectMapper();
                    Map<String, Object> responseBody = mapper2.readValue(body, new TypeReference<>() {
                    });

                    mostrarMensaje(responseBody.get("message").toString());
                } catch (Exception e) {
                    mostrarMensaje("Persona registrada correctamente.");
                }
                limpiarCampos();
            } else {
                mostrarError("Error al guardar persona. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al guardar persona: " + e.getMessage());
        }
    }

    public void actualizarPersona() {
        try {
            if (!camposValidos()) {
                mostrarError("Debe llenar todos los campos.");
                return;
            }

            Long id = Long.valueOf(vista.txtIdPersona.getText());

            Persona persona = obtenerPersonaDesdeVista();

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String requestBody = mapper.writeValueAsString(persona);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/editar/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                mostrarMensaje("Persona actualizada correctamente.");
                limpiarCampos();
            } else {
                mostrarError("Error al actualizar persona. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al actualizar persona: " + e.getMessage());
        }
    }

    private Persona obtenerPersonaDesdeVista() {
        Persona persona = new Persona();
        persona.setNombres(vista.txtNombres.getText());
        persona.setApellidos(vista.txtApellidos.getText());
        persona.setDireccion(vista.txtDireccion.getText());
        persona.setTelefono(vista.txtTelefono.getText());
        persona.setCorreo(vista.txtCorreo.getText());

        if (vista.cboTipoDocI.getSelectedItem() != null) {
            persona.setTipoDocumentoIdentidad((TipoDocumentoIdentidad) vista.cboTipoDocI.getSelectedItem());
        }

        persona.setNumeroDocumento(vista.txtNumeroDoc.getText());

        if (vista.cboTipoDocI.getSelectedItem() != null) {
            persona.setDepartamento(((Departamento) vista.cboDepartamento.getSelectedItem()));
        }

        if (vista.cboTipoDocI.getSelectedItem() != null) {
            persona.setProvincia(((Provincia) vista.cboProvincia.getSelectedItem()));
        }

        if (vista.cboTipoDocI.getSelectedItem() != null) {
            persona.setDistrito(((Distrito) vista.cboDistrito.getSelectedItem()));
        }

        // Convertir la fecha de nacimiento
        if (vista.jCalendarCbxFechaNacimiento.getDate() != null) {
            persona.setFechaNacimiento(vista.jCalendarCbxFechaNacimiento.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
        }

        if (vista.cboTipoDocI.getSelectedItem() != null) {
            persona.setGenero(GeneroEnum.valueOf(vista.cboGenero.getSelectedItem().toString()));
        }

        persona.setLugarNacimiento(vista.txtLugarNacimiento.getText());
        return persona;
    }

    // ------------------ UTILITARIOS ------------------
    private void limpiarCampos() {
        vista.txtIdPersona.setText("");
        vista.txtNombres.setText("");
        vista.txtApellidos.setText("");
        vista.txtDireccion.setText("");
        vista.txtTelefono.setText("");
        vista.cboTipoDocI.setSelectedIndex(0);
        vista.txtNumeroDoc.setText("");
        vista.txtCorreo.setText("");
        vista.cboDepartamento.setSelectedIndex(0);
        vista.jCalendarCbxFechaNacimiento.setDate(null);
        vista.cboGenero.setSelectedIndex(0);
        vista.txtLugarNacimiento.setText("");

        //Deshabilitar botones
        vista.btnActualizar1.setEnabled(false);
    }

    private boolean camposValidos() {
        Date selectedDate = vista.jCalendarCbxFechaNacimiento.getDate();
        return !vista.txtNombres.getText().trim().isEmpty()
                && !vista.txtApellidos.getText().trim().isEmpty()
                && !vista.txtDireccion.getText().trim().isEmpty()
                && !vista.txtTelefono.getText().trim().isEmpty()
                && vista.cboTipoDocI.getSelectedIndex() > 0
                && !vista.txtNumeroDoc.getText().trim().isEmpty()
                && !vista.txtCorreo.getText().trim().isEmpty()
                && selectedDate != null
                && !selectedDate.after(new Date())
                && vista.cboGenero.getSelectedIndex() > 0
                && vista.cboDepartamento.getSelectedIndex() >= 0
                && vista.cboProvincia.getSelectedIndex() >= 0
                && vista.cboDistrito.getSelectedIndex() >= 0
                && !vista.txtLugarNacimiento.getText().trim().isEmpty();
    }

    private void nuevaPersona() {
        limpiarCampos();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void marcaAgua() {
        TextPrompt nombres = new TextPrompt("Nombres", vista.txtNombres);
        TextPrompt apellidos = new TextPrompt("Apellidos", vista.txtApellidos);
        TextPrompt direccion = new TextPrompt("Direccion donde vive", vista.txtDireccion);
        TextPrompt correo = new TextPrompt("Correo electronico", vista.txtCorreo);
        TextPrompt telefono = new TextPrompt("N° de Telefono", vista.txtTelefono);
        TextPrompt nDocumento = new TextPrompt("N° de Documento", vista.txtNumeroDoc);
        TextPrompt lugarNacimiento = new TextPrompt("Lugar donde nació", vista.txtNumeroDoc);
    }

    // ------------------ CARGAR COMBOS ------------------
    private void cargarGeneros() {
        vista.cboGenero.removeAllItems();

        // Agregar la opción predeterminada "Seleccione Género"
        vista.cboGenero.addItem("Seleccione Género");

        // Agregar los valores del enumerado GeneroEnum
        for (GeneroEnum genero : GeneroEnum.values()) {
            vista.cboGenero.addItem(genero.name());
        }

        // Seleccionar la opción predeterminada explícitamente
        vista.cboGenero.setSelectedIndex(0);
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

                vista.cboDepartamento.removeAllItems();
                vista.cboDepartamento.addItem(new Departamento(-1, "Seleccione Departamento"));

                for (Departamento d : departamentosCargados) {
                    vista.cboDepartamento.addItem(d);
                }
            } else {
                mostrarError("Error al cargar departamentos. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al cargar departamentos: " + e.getMessage());
        }
    }

    private void onDepartamentoChange() {
        Departamento dep = (Departamento) vista.cboDepartamento.getSelectedItem();
        if (dep == null || dep.getIdDepartamento() == -1) {
            vista.cboProvincia.removeAllItems();
            vista.cboDistrito.removeAllItems();
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

                vista.cboProvincia.removeAllItems();
                vista.cboProvincia.addItem(new Provincia(-1, "Seleccione Provincia", null));

                for (Provincia p : provinciasCargados) {
                    vista.cboProvincia.addItem(p);
                }

                vista.cboDistrito.removeAllItems();
            } else {
                mostrarError("Error al cargar provincias. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error al cargar provincias: " + e.getMessage());
        }
    }

    private void onProvinciaChange() {
        Provincia prov = (Provincia) vista.cboProvincia.getSelectedItem();
        if (prov == null || prov.getIdProvincia() == -1) {
            vista.cboDistrito.removeAllItems();
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

                vista.cboDistrito.removeAllItems();
                vista.cboDistrito.addItem(new Distrito(-1, "Seleccione Distrito", null, null));

                for (Distrito d : distritosCargados) {
                    vista.cboDistrito.addItem(d);
                }
            } else {
                mostrarError("Error al cargar distritos. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error al cargar distritos: " + e.getMessage());
        }
    }

    private void cargarTiposDocumento() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL_TIPOS_DOCUMENTOS))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule()); // Registrar el módulo
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignorar propiedades desconocidas

                tiposDocumentoCargados = mapper.readTree(response.body()) // Leer el árbol JSON
                        .get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<TipoDocumentoIdentidad>>() {
                        });

                // Limpiar los elementos existentes
                vista.cboTipoDocI.removeAllItems();

                // Agregar la opción predeterminada "Seleccione Tipo Documento"
                TipoDocumentoIdentidad opcionDefault = new TipoDocumentoIdentidad(null, "Seleccione Tipo Documento", null, null, null);
                vista.cboTipoDocI.addItem(opcionDefault);

                for (TipoDocumentoIdentidad tipo : tiposDocumentoCargados) {
                    vista.cboTipoDocI.addItem(tipo);
                }

                // Seleccionar la opción "Seleccione" como predeterminada
                vista.cboTipoDocI.setSelectedIndex(0);
            } else {
                mostrarError("Error al cargar tipos de documento. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al cargar tipos de documento: " + e.getMessage());
        }
    }

}
