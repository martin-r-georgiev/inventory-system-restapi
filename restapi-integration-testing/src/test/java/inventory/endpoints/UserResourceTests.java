package inventory.endpoints;

import inventory.models.User;
import inventory.models.UserRole;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserResourceTests {

    public static final String BASE_URI = "http://localhost:9090/inventory/";
    public static final String AUTH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBZG1pbiIsImlhdCI6MTYxMTI0NDg1MH0.5x2C9eu1AN6Yy8yCUR732IXS0zchy2k7xLXreIqiMxQ";
    Client client = ClientBuilder.newClient();
    WebTarget target;

    @BeforeEach
    public void initialization() {
        target = client.target(BASE_URI);
    }

    @Test
    void authenticateUserTest() {
        User adminUser = new User("admin", "admin", UserRole.Admin, UUID.randomUUID());
        WebTarget userPath = target.path("users/authentication");

        Invocation.Builder invocationBuilder = userPath.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(adminUser, MediaType.APPLICATION_JSON));

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertNotNull(response.getHeaderString(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void refreshUserTokenTest() {
        WebTarget userPath = target.path("users/refresh");

        Response response = userPath.request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + AUTH_TOKEN)
                .post(Entity.json(null));

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertNotNull(response.getHeaderString(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void verifyUserLoginTest() {
        WebTarget userPath = target.path("users/user");

        Response response = userPath.request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + AUTH_TOKEN).get();

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertNotNull(response.getEntity());
    }
}

