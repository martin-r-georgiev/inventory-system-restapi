package org.martin.inventory.security;

import org.martin.inventory.annotations.Secured;
import org.martin.inventory.model.User;
import org.martin.inventory.service.UserManager;
import org.martin.inventory.utils.JWTUtil;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

        /* Authorization */
        // Role Authorization - User, Manager, Admin

        /* Get information about the service method which is being called. This information includes the annotated/permitted roles. */
        Method method = resourceInfo.getResourceMethod();
        if (method.isAnnotationPresent(PermitAll.class)) return;
        // if access is denied for all: deny access
        if (method.isAnnotationPresent(DenyAll.class)) {
            Response response = Response.status(Response.Status.FORBIDDEN).build();
            requestContext.abortWith(response);
            return;
        }

        if (method.isAnnotationPresent(RolesAllowed.class)) {
            // get allowed roles for this method
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));
            /* isUserAllowed : implement this method to check if this user has any of the roles in the rolesSet if not isUserAllowed abort the requestContext with FORBIDDEN response*/
            if (!isUserAllowed(token, rolesSet)) {
                Response response = Response.status(Response.Status.FORBIDDEN).build();
                requestContext.abortWith(response);
            }
        }
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

    private boolean isUserAllowed(String token, Set<String> rolesSet) {
        return rolesSet.contains(jwtUtil.extractRole(token).toString());
    }
}
