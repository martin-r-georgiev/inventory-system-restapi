package org.martin.inventory.resources;

import com.google.common.hash.Hashing;
import org.martin.inventory.model.UserRole;
import org.martin.inventory.annotations.Secured;
import org.martin.inventory.model.User;
import org.martin.inventory.model.Warehouse;
import org.martin.inventory.service.UserManager;
import org.martin.inventory.service.WarehouseManager;
import org.martin.inventory.utils.JWTUtil;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.nio.charset.StandardCharsets;

import static org.martin.inventory.security.AuthenticationFilter.AUTHENTICATION_SCHEME;
import static org.martin.inventory.security.AuthenticationFilter.isTokenBasedAuthentication;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserManager userManager;

    @Inject
    private WarehouseManager whManager;

    @Inject
    private JWTUtil jwtUtil;

    @POST
    @Path("/authentication")
    public Response createAuthenticationToken(UserDTO user) {
        try {
            UserRole userRole = userManager.authenticate(user.getUsername(), user.getPassword());

            String token = jwtUtil.generateToken(user.getUsername(), userRole);

            return Response.ok().header(HttpHeaders.AUTHORIZATION,"Bearer " + token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @POST
    @Secured
    @Path("/refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response refreshAuthenticationToken(@Context HttpHeaders requestHeaders) {
        final String authHeader = requestHeaders.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!isTokenBasedAuthentication(authHeader)) return Response.status(Response.Status.UNAUTHORIZED).build();

        String oldToken = authHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        String newToken = jwtUtil.generateToken(jwtUtil.extractUsername(oldToken), jwtUtil.extractRole(oldToken));

        return Response.ok().header(HttpHeaders.AUTHORIZATION,"Bearer " + newToken).build();
    }

    @GET
    @Secured
    @Path("/user")
    public Response verifyUserLogin(@Context HttpHeaders requestHeaders) {
        final String authHeader = requestHeaders.getHeaderString(HttpHeaders.AUTHORIZATION);
        final String username = jwtUtil.extractUsername(authHeader.substring(AUTHENTICATION_SCHEME.length()).trim());
        User user = userManager.getByUsername(username);
        if(user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("The requested item was not found.").build();
        } else {
            UserDetails output = new UserDetails(user.getUsername(), user.getWarehouseId(), user.getRole().toString());
            return Response.ok(output).build();
        }
    }

    @POST //POST (./users/register)
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(RegistrationDTO data) {
        try {
            UserRole userRole = UserRole.User;
            if (data.getWarehouseId() == null) {
                if (data.getWarehouseName().isEmpty()) {
                    throw new IllegalArgumentException("Failed to create manager account. Please provide a warehouse name");
                } else {
                    Warehouse newWarehouse = data.GetWarehouseEntity();
                    whManager.add(newWarehouse);
                    data.setWarehouseId(newWarehouse.getId());
                    userRole = UserRole.Manager;
                }
            } else {
                Warehouse found = whManager.getById(data.getWarehouseId());
                if (found == null) {
                    throw new IllegalArgumentException("Invalid warehouse identifier provided.");
                }
            }
            if (data.getWarehouseId() == null) {
                throw new IllegalArgumentException("Failed to create user account. Please provide a warehouse identifier.");
            }

            // Hashing password before persisting it to the database
            String hashedPassword  = Hashing.sha256().hashString(data.getPassword(), StandardCharsets.UTF_8).toString();
            data.setPassword(hashedPassword);

            userManager.add(data.GetUserEntity(userRole));
            return Response.status(Response.Status.CREATED).entity("User account successfully created.").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}
