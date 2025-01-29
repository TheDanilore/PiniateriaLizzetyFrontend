package com.danilore.piniaterializzety.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.danilore.piniaterializzety.models.Permiso;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RolService {

    private static final String BASE_URL_PERMISOS = "http://localhost:8080/api/permisos";
    private static final Logger LOGGER = Logger.getLogger(RolService.class.getName());

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RolService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Soporte para LocalDate
    }

    /**
     * Carga la lista de permisos desde el servidor.
     *
     * @return Lista de permisos o una lista vacía en caso de error.
     */
    public List<Permiso> cargarPermisos() {
        String url = BASE_URL_PERMISOS + "?page=0&size=30";
        return realizarSolicitud(url, new TypeReference<List<Permiso>>() {
        });
    }

    /**
     * Realiza una solicitud HTTP GET y procesa la respuesta.
     *
     * @param url URL de la solicitud.
     * @param typeReference Tipo de referencia para convertir el contenido de la
     * respuesta.
     * @param <T> Tipo de datos esperado en la respuesta.
     * @return Lista de datos obtenidos o una lista vacía en caso de error.
     */
    private <T> List<T> realizarSolicitud(String url, TypeReference<List<T>> typeReference) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode contentNode = objectMapper.readTree(response.body()).get("content");
                if (contentNode != null) {
                    return contentNode.traverse(objectMapper).readValueAs(typeReference);
                } else {
                    LOGGER.log(Level.WARNING, "No se encontró el nodo 'content' en la respuesta.");
                }
            } else {
                LOGGER.log(Level.WARNING, "Error en la solicitud: {0}", response.body());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en la solicitud HTTP: {0}", e.getMessage());
        }
        return List.of(); // Devuelve una lista vacía en caso de error
    }

}
