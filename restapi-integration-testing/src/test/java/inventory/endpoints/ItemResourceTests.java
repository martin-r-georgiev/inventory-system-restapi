package inventory.endpoints;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemResourceTests {

    public static final String BASE_URI = "http://localhost:9090/inventory/";
    public static final String AUTH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBZG1pbiIsImlhdCI6MTYxMTI0NDg1MH0.5x2C9eu1AN6Yy8yCUR732IXS0zchy2k7xLXreIqiMxQ";
    Client client = ClientBuilder.newClient();
    WebTarget target;

    @BeforeEach
    public void initialization() {
        target = client.target(BASE_URI);
    }

    @Test
    void getAllItemsTest () {
        WebTarget itemsPath = target.path("items");

        Response response = itemsPath.request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + AUTH_TOKEN)
                .get();

        String responseBody = response.readEntity(String.class);

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertNotNull(responseBody);
    }

    @Test
    void getAllWarehousesTest () {
        WebTarget itemsPath = target.path("items/warehouse");

        Response response = itemsPath.request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + AUTH_TOKEN)
                .get();

        String responseBody = response.readEntity(String.class);

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertNotNull(responseBody);
    }

}
