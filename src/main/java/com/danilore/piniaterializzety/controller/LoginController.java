/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.danilore.piniaterializzety.controller;

import com.danilore.piniaterializzety.clases.TextPrompt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import com.danilore.piniaterializzety.views.VLogin;
import com.danilore.piniaterializzety.models.Usuario;
import com.danilore.piniaterializzety.views.VPrincipal;

/**
 *
 * @author ASUS
 */
public final class LoginController {

    VLogin vista = new VLogin();
    private Usuario modelo;

    private static final List<String> CONTRASENAS_PROHIBIDAS = Arrays.asList("123456", "password", "qwerty", "12345678", "1234");
    private int intentosFallidos = 0;
    private static final int MAX_INTENTOS = 3;

    private static final String MSG_CONTRASENA_INVALIDA = "La contraseña debe tener al menos 6 caracteres.";
    private static final String MSG_AUTENTICACION_EXITOSA = "Inicio de sesión exitoso.";
    private static final String MSG_CREDENCIALES_INVALIDAS = "Usuario o contraseña incorrectos.";

    private static final String BASE_URL = "http://localhost:8080/api/login";

    public LoginController(VLogin v) throws SQLException {
        this.vista = v;
        configurarEventos(); // Registrar eventos correctamente
        recuperarUsuario(); // Recuperar usuario recordado
        marcaAgua(); // Agregar placeholders en los campos
        resetLoginView();
    }

    private void configurarEventos() {
        vista.lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    log();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public Usuario getUsuario() {
        return modelo;
    }

    private String sanitizarEntrada(String input) {
        return input.replaceAll("[<>\"'%;()&+]", "").trim();
    }

    private boolean esContrasenaProhibida(String password) {
        return CONTRASENAS_PROHIBIDAS.contains(password);
    }

    private boolean validarFormatoEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    public void log() throws SQLException {
        try {
            if (!validarUser()) {
                JOptionPane.showMessageDialog(null, "Rellene todos los campos");
                return;
            }

            String email = vista.txtUser.getText().trim();
            String password = new String(vista.txtPassword.getPassword());

            if (!validarPassword(password)) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 6 caracteres");
                return;
            }

            if (!validarFormatoEmail(email)) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese un correo electrónico válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Bloqueo por intentos fallidos
            if (intentosFallidos >= MAX_INTENTOS) {
                JOptionPane.showMessageDialog(vista, "Cuenta bloqueada temporalmente por demasiados intentos fallidos. Inténtelo más tarde.", "Bloqueo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Creación del objeto usuario para enviar al backend
            Usuario usuario = new Usuario(email, password);

            // Preparación del objeto JSON
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Registrar soporte para LocalDate

            String requestBody = mapper.writeValueAsString(usuario);

            // Configuración de la solicitud HTTP
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Envío de la solicitud y procesamiento de la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                intentosFallidos = 0; // Reiniciar intentos fallidos
                
                // Convertir la respuesta JSON en un objeto Usuario
                Usuario usuarioAutenticado = mapper.readValue(response.body(), Usuario.class);

                if (usuarioAutenticado == null || usuarioAutenticado.getId() == null) {
                    JOptionPane.showMessageDialog(vista, "Error: Usuario no autenticado.");
                    return;
                }

                // Autenticación exitosa
                JOptionPane.showMessageDialog(vista, MSG_AUTENTICACION_EXITOSA);
                VPrincipal ventanaPrincipal = new VPrincipal(usuarioAutenticado);
                ventanaPrincipal.setVisible(true);
                vista.dispose();
            } else if (response.statusCode() == 401) {
                intentosFallidos++;
                JOptionPane.showMessageDialog(vista, "Credenciales inválidas.");
            } else {
                JOptionPane.showMessageDialog(vista, "Error desconocido: " + response.body());
            }
        } catch (URISyntaxException e) {
            JOptionPane.showMessageDialog(vista, "Error en la URL: " + e.getMessage());
        } catch (JsonProcessingException e) {
            JOptionPane.showMessageDialog(vista, "Error al procesar la respuesta JSON: " + e.getMessage());
            System.out.println("Error al procesar la respuesta JSON: "+ e.getMessage());
        } catch (IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(vista, "Error al comunicarse con el servidor: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void resetLoginView() {
        vista.txtUser.setText("");
        vista.txtPassword.setText("");
        vista.jCheckBox1.setSelected(false);
    }

    private boolean validarUsuario(String username) {
        return username != null && !username.isEmpty();
    }

    private boolean validarPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public boolean validarUser() {
        String username = vista.txtUser.getText();

        boolean userValido = validarUsuario(username);

        return userValido;
    }

    private void recordarUsuario() {
        Preferences prefs = Preferences.userRoot().node("com.myapp.preferences");
        if (vista.jCheckBox1.isSelected()) {
            prefs.put("username", vista.txtUser.getText().trim()); // Guardar el correo
        } else {
            prefs.remove("username"); // Eliminar el correo guardado
        }
    }

    private void recuperarUsuario() {
        Preferences prefs = Preferences.userRoot().node("com.myapp.preferences");
        String savedUsername = prefs.get("username", "");
        if (!savedUsername.isEmpty()) {
            vista.txtUser.setText(savedUsername);
            vista.jCheckBox1.setSelected(true); // Marcar el checkbox si hay un usuario guardado
        }
    }

    public void marcaAgua() {
        TextPrompt usuario = new TextPrompt("Correo", vista.txtUser);
        TextPrompt contra = new TextPrompt("Contraseña", vista.txtPassword);
    }

}
