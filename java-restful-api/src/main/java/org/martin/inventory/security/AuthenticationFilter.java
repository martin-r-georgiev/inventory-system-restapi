package org.martin.inventory.security;

import org.martin.inventory.annotations.Secured;
import org.martin.inventory.model.User;
import org.martin.inventory.service.UserManager;
import org.martin.inventory.utils.JWTUtil;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    // This resource stores the request itself and is provided by the Jersey framework automatically
    @Context
    private ResourceInfo resourceInfo;

    @Inject
    private UserManager manager;

    @Inject
    private JWTUtil jwtUtil;

    public static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        // Performing service authentication and authorization

        /* Token-based Authentication */

        // Authorization header retrieval from requests
        final String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Authorization header validation (Presence + Authentication Scheme)
        if (!isTokenBasedAuthentication(authHeader)) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // Token retrieval from the Authorization header
        String token = authHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            // Validate the token
            validateToken(token);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid token provided.").build());
            return;
        }

        // OLD

//        /* Authentication */
//        // Credentials verification
//
//        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
//        // Authorization header data retrieval
//        final List<String> authorization = headers.get(HttpHeaders.AUTHORIZATION);
//
//        // Return Response (UNAUTHORIZED) if the authorization header is not present
//        if (authorization == null || authorization.isEmpty()) {
//            Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Missing username and/or password.").build();
//            requestContext.abortWith(response);
//            return;
//        }
//
//        // Retrieval of encoded username and password
//        final String encodedCredentials = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
//        // Decoding username and password into one string
//        String credentials = new String(Base64.getDecoder().decode(encodedCredentials.getBytes()));
//        // Split username and password tokens in credentials
//        final StringTokenizer tokenizer = new StringTokenizer(credentials, ":");
//        final String username = tokenizer.nextToken();
//        final String password = tokenizer.nextToken();
//
//        // Check if username and password are valid
//        if (!isValidUser(username, password)) {
//            Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username and/or password.").build();
//            requestContext.abortWith(response);
//            return;
//        }

//        /* Authorization */
//        // Role Authorization - Workers and Managers
//
//        /* Get information about the service method which is being called. This information includes the annotated/permitted roles. */
//        Method method = resourceInfo.getResourceMethod();
//        if (method.isAnnotationPresent(PermitAll.class)) return;
//        // if access is denied for all: deny access
//        if (method.isAnnotationPresent(DenyAll.class)) {
//            Response response = Response.status(Response.Status.FORBIDDEN).build();
//            requestContext.abortWith(response);
//            return;
//        }
//
//        if (method.isAnnotationPresent(RolesAllowed.class)) {
//            // get allowed roles for this method
//            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
//            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
//            /* isUserAllowed : implement this method to check if this user has any of the roles in the rolesSet if not isUserAllowed abort the requestContext with FORBIDDEN response*/
//            if (!isUserAllowed(username, password, rolesSet)) {
//                Response response = Response.status(Response.Status.FORBIDDEN).build();
//                requestContext.abortWith(response);
//                return;
//            }
//        }
    }

    public static boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Checking if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void validateToken(String token) {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        // Use isValidUser + JWTUtil's token validation method
        final String subject = jwtUtil.extractUsername(token);
        if (!isValidUser(subject) && !jwtUtil.validateToken(token, subject))
        {
            throw new NotAuthorizedException("Invalid or expired authentication token provided.");
        }
    }

    private boolean isValidUser(String username) {
        User found = manager.getByUsername(username);
        return found != null;
    }

    //    private boolean isUserAllowed(String username, String password, Set<String> rolesSet) {
    //        // TODO: Check for user role or token
    //        return true;
    //    }
}
