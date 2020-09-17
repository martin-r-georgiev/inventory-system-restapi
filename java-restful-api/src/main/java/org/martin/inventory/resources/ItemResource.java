package org.martin.inventory.resources;

import org.martin.inventory.model.Item;
import org.martin.inventory.repository.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.Path;

import java.net.URI;
import java.util.List;


@Path("/items")
public class ItemResource {

    @Context
    private UriInfo uriInfo;
    private static final ItemRepository  itemRepository = new ItemRepository();

    @GET //GET (./items/)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {
        List<Item> items = itemRepository.getItems();

        GenericEntity<List<Item>> entity = new GenericEntity<>(items) {};
        return Response.ok(entity).build();
    }

    @GET //GET (./items/<id>)
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") int itemId) {
        Item item = itemRepository.getItem(itemId);
        if(item == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("The requested item was not found.").build();
        } else {
            return Response.ok(item).build();
        }
    }

    @POST //POST (./items/)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createItem(Item item) {
        if(!itemRepository.addItem(item)) {
            String msg = String.format("This item (ID: %s) already exists.", item.getId());
            return Response.status(Response.Status.CONFLICT).entity(msg).build();
        } else {
            String url = String.format("%s/%s", uriInfo.getAbsolutePath(), item.getId());
            URI uri = URI.create(url);
            return Response.created(uri).build();
        }
    }

    @PUT //PUT (./items/)
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateItem(Item item) {
        if (itemRepository.updateItem(item)) {
            return Response.noContent().build();
        } else {
            String msg = "Please provide a valid item id.";
            return Response.status(Response.Status.NOT_FOUND).entity(msg).build();
        }
    }

    @DELETE //DELETE (./items/<id>)
    @Path("{id}")
    public Response deleteItem(@PathParam("id") int itemId) {
        itemRepository.deleteItem(itemRepository.getItem(itemId));
        return Response.noContent().build();
    }
}
