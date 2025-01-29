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
import com.danilore.piniaterializzety.views.VRoles;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ListModel;
import com.danilore.piniaterializzety.models.EstadoEnum;
import com.danilore.piniaterializzety.models.Permiso;
import com.danilore.piniaterializzety.models.Rol;
import com.danilore.piniaterializzety.models.Usuario;
import com.danilore.piniaterializzety.views.VRolesListado;

/**
 *
 * @author Danilore
 */
public final class RolListadoController {

    private final VRolesListado vista;
    private final VRoles vistaRoles;
    private static final String BASE_URL = "http://localhost:8080/api/roles";
    private List<Rol> rolesCargados = new ArrayList<>();

    //Paginacion    
    private int paginaActual = 0; // Página actual
    private final int tamanioPagina = 12; // Tamaño de página fijo
    private boolean esUltimaPagina = false;

    public RolListadoController(VRolesListado vista, VRoles vistaRoles, Usuario usuario) {
        this.vista = vista;
        this.vistaRoles = vistaRoles;
        configurarEventos();

        vista.lblPagina.setText("Página: " + (paginaActual + 1));

        // Configuración inicial
        this.vista.txtIdRolesUsuario.setVisible(false);
        this.vista.btnActualizar.setEnabled(false);
        this.vista.btnDarBaja.setEnabled(false);
        this.vista.btnActivar.setEnabled(false);
        this.vista.btnEliminar.setEnabled(false);

        // Habilitar botones según permisos
        configurarBotonesSegunPermisos(usuario);

        // Inicializar tabla y cargar datos
        limpiarTable();
        listarRoles();

        // Añadir placeholders
        marcaAgua();
    }

    private void configurarBotonesSegunPermisos(Usuario usuario) {
        // Verificar si el usuario tiene el permiso para "Eliminar"
        if (!usuario.tienePermiso("GESTIONAR_ROLES_ADMIN")) {
            vista.btnEliminar.setVisible(false); // Ocultar el botón si no tiene el permiso
        }

        // Puedes agregar más verificaciones para otros botones si es necesario
    }

    private void configurarEventos() {
        vista.btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizar();
            }
        });

        vista.btnDarBaja.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarEstado("Inactivo");
            }
        });

        vista.btnActivar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cambiarEstado("Activo");
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
                nuevoRol();
            }
        });

        vista.btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bucarPorIdODescripcion();
            }
        });

        vista.tableRol.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarRolDeTabla();
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
        listarRoles();
        vista.lblPagina.setText("Página: " + (paginaActual + 1));

    }

    private void siguientePagina() {
        if (esUltimaPagina) {
            mostrarMensaje("Ya estás en la última página.");
            return;
        }
        paginaActual++;
        listarRoles();
        vista.lblPagina.setText("Página: " + (paginaActual + 1));
    }

    //--------------------------------------------
    private void seleccionarRolDeTabla() {
        int filaSeleccionada = vista.tableRol.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Rol rol = obtenerRolDesdeFila(filaSeleccionada);
            vista.txtIdRolesUsuario.setText(String.valueOf(rol.getId()));

            // Habilitar botones según el estado del usuario
            if (rol.getEstado() == EstadoEnum.ACTIVO) {
                vista.btnDarBaja.setEnabled(true);
                vista.btnActivar.setEnabled(false);
            } else if (rol.getEstado() == EstadoEnum.INACTIVO) {
                vista.btnDarBaja.setEnabled(false);
                vista.btnActivar.setEnabled(true);
            }

            // Habilitar botón actualizar siempre que un usuario sea seleccionado
            vista.btnActualizar.setEnabled(true);
            vista.btnEliminar.setEnabled(true);
        }
    }

    private Rol obtenerRolDesdeFila(int fila) {
        if (fila >= 0 && fila < rolesCargados.size()) {
            return rolesCargados.get(fila); // Recupera los datos completos de la persona desde la lista
        }
        return null;
    }

    private void bucarPorIdODescripcion() {
        int idRol = Integer.parseInt(vista.txtBuscar.getText()); // Obtén el ID desde la tabla

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + idRol))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule()); // Soporte para LocalDate
                Rol rol = mapper.readValue(response.body(), Rol.class);

                // Se lista por Id o Descripcion
                // Habilitar botones
                vista.btnActualizar.setEnabled(true);
                vista.btnEliminar.setEnabled(true);
            } else {
                mostrarError("Error al buscar rol. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al obtener datos del rol: " + e.getMessage());
        }
    }

    //--------------------------  CRUD ROLES  ----------------------
    public void listarRoles() {
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

                rolesCargados = rootNode.get("content") // Acceder al array dentro de "content"
                        .traverse(mapper)
                        .readValueAs(new TypeReference<List<Rol>>() {
                        });

                DefaultTableModel model = (DefaultTableModel) vista.tableRol.getModel();
                model.setRowCount(0);

                for (Rol rol : rolesCargados) {
                    // Obtener las descripciones de los roles como una sola cadena
                    String permisosDescripcion = rol.getPermisos().stream()
                            .map(Permiso::getDescripcion)
                            .reduce((desc1, desc2) -> desc1 + ", " + desc2) // Unir descripciones con coma
                            .orElse("Sin permisos");

                    // Validar valores nulos y mostrar cadena vacía si es necesario
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String createdAt = (rol.getCreatedAt()!= null) ? rol.getCreatedAt().format(formatter) : "";
                    String updatedAt = (rol.getUpdatedAt()!= null) ? rol.getUpdatedAt().format(formatter) : "";

                    model.addRow(new Object[]{
                        rol.getId(),
                        rol.getDescripcion(),
                        permisosDescripcion,
                        rol.getEstado().name(),
                        createdAt,
                        updatedAt
                    });
                }

                // Actualizar el estado de si es la última página
                esUltimaPagina = rootNode.get("last").asBoolean();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al listar roles. Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar roles: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizar() {
        try {
            // Obtener la fila seleccionada en la tabla
            int filaSeleccionada = vista.tableRol.getSelectedRow();

            if (filaSeleccionada < 0) {
                JOptionPane.showMessageDialog(vista, "Seleccione un rol de la tabla para actualizar.");
                return;
            }

            // Obtener el rol correspondiente a la fila seleccionada
            Rol rolSeleccionado = obtenerRolDesdeFila(filaSeleccionada);

            if (rolSeleccionado == null) {
                JOptionPane.showMessageDialog(vista, "No se pudo obtener el rol seleccionado.");
                return;
            }

            // Enviar el rol seleccionado a la vista de edición
            enviarAVistaEdicion(rolSeleccionado);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado actualizar rol: " + e.getMessage());
        }
    }

    private void enviarAVistaEdicion(Rol rol) {

        RolController rolController = new RolController(vistaRoles, new Usuario());

        // Configurar datos del rol seleccionado en la nueva ventana
        rolController.llenarPermisos();

        // Configurar la vista de edición con los datos seleccionados
        vistaRoles.txtIdRolesUsuario.setText(String.valueOf(rol.getId()));
        vistaRoles.txtDescripcion.setText(rol.getDescripcion());

        ListModel<String> model = vistaRoles.jlistPermisosRoles.getModel();

        List<String> permisosRol = rol.getPermisos().stream()
                .map(Permiso::getDescripcion)
                .toList();

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < model.getSize(); i++) {
            if (permisosRol.contains(model.getElementAt(i))) {
                indices.add(i);
            }
        }

        int[] selectedIndices = indices.stream().mapToInt(Integer::intValue).toArray();
        vistaRoles.jlistPermisosRoles.setSelectedIndices(selectedIndices);

        vistaRoles.lblTextoCrearOEditar.setText("EDITAR ROL");
        // Activa los botones necesarios para edición
        vistaRoles.txtIdRolesUsuario.setVisible(true);
        vistaRoles.btnActualizar.setVisible(true);
        vistaRoles.btnGuardar.setVisible(false);

        // Vincular la vista con su controlador
        //new RolController(vistaRoles, new Usuario()); // Inicializa el controlador con la vista y el usuario
        // Ocultar la vista de listado y mostrar la de edición
        vista.setVisible(false); // Oculta la vista actual
        vistaRoles.setVisible(true); // Muestra la vista de edición
    }

    private void cambiarEstado(String nuevoEstado) {
        try {
            int id = Integer.parseInt(vista.txtIdRolesUsuario.getText());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/cambiar-estado/" + id + "?nuevoEstado=" + nuevoEstado))
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JOptionPane.showMessageDialog(vista, "Estado del rol cambiado a " + nuevoEstado + ".");
                listarRoles();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al cambiar estado. Error: " + response.body());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cambiar estado: " + e.getMessage());
        }
    }

    public void eliminar() {
        try {
            Long id = Long.valueOf(vista.txtIdRolesUsuario.getText());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                mostrarMensaje("Rol eliminado con éxito.");
                limpiarCampos();
                listarRoles();
            } else {
                mostrarError("Error al eliminar rol. Error: " + response.body());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al eliminar rol: " + e.getMessage());
        }
    }

    public void nuevoRol() {
        RolController rolController = new RolController(vistaRoles, new Usuario());
        vista.setVisible(false); // Oculta la vista actual
        vistaRoles.setVisible(true); // Muestra la vista de edición
    }

    public void limpiarCampos() {
        vista.txtIdRolesUsuario.setText("");

        // Deshabilitar botones
        vista.btnActualizar.setEnabled(false);
        vista.btnDarBaja.setEnabled(false);
        vista.btnActivar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
    }

    public void limpiarTable() {
        DefaultTableModel model = (DefaultTableModel) vista.tableRol.getModel();
        model.setRowCount(0);
    }

    public void marcaAgua() {
        TextPrompt btnBuscar = new TextPrompt("ID o Descripcion", vista.txtBuscar);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
