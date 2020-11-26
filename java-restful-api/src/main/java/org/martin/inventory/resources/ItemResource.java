package org.martin.inventory.resources;

import org.martin.inventory.annotations.Secured;
import org.martin.inventory.model.Item;
import org.martin.inventory.service.ItemManager;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.Path;

import java.net.URI;
import java.util.List;


@Path("/items")
public class ItemResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ItemManager manager;

    @GET //GET (./items/)
    @Secured
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {
        GenericEntity<List<Item>> entity = new GenericEntity<>(manager.getAll()) {};
        return Response.ok(entity).build();
    }

    @GET //GET (./items/<id>)
    @Secured
    @Path("{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") Long itemId) {
        Item item = manager.getById(itemId);
        if(item == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("The requested item was not found.").build();
        } else {
            return Response.ok(item).build();
        }
    }

    @POST //POST (./items/)
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createItem(ItemDTO item) {
        manager.add(item.convertToEntity());
        String url = String.format("%s/%s", uriInfo.getAbsolutePath(), item.getId());
        URI uri = URI.create(url);
        return Response.created(uri).build();
    }

    @PUT //PUT (./items/<id>)
    @Secured
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateItem(@PathParam("id") Long itemId, ItemDTO item) {
        if (manager.update(itemId, item.convertToEntity())) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid item id.").build();
        }
    }

    @DELETE //DELETE (./items/<id>)
    @Secured
    @Path("{id}")
    public Response deleteItem(@PathParam("id") Long itemId) {
        try {
            manager.delete(manager.getById(itemId));
            return Response.noContent().build();
        }
        catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Item not found with provided ID.").build();
        }
    }
}
