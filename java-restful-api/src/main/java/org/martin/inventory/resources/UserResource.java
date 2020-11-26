package org.martin.inventory.resources;

import org.martin.inventory.annotations.Secured;
import org.martin.inventory.service.UserManager;
import org.martin.inventory.utils.JWTUtil;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import java.net.URI;

import static org.martin.inventory.security.AuthenticationFilter.AUTHENTICATION_SCHEME;
import static org.martin.inventory.security.AuthenticationFilter.isTokenBasedAuthentication;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserManager manager;

    @Inject
    private JWTUtil jwtUtil;

    @POST
    @Path("/authentication")
    public Response createAuthenticationToken(UserDTO user) {
        try {
            manager.authenticate(user.getUsername(), user.getPassword());

            String token = jwtUtil.generateToken(user.getUsername());

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
        String newToken = jwtUtil.generateToken(jwtUtil.extractUsername(oldToken));

        return Response.ok().header(HttpHeaders.AUTHORIZATION,"Bearer " + newToken).build();
    }

    @POST //POST (./users/)
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserDTO user) {
        try {
            manager.add(user.convertToEntity());
            String url = String.format("%s/%s", uriInfo.getAbsolutePath(), user.getId());
            URI uri = URI.create(url);
            return Response.created(uri).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex).build();
        }
    }
}
