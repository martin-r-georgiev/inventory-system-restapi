package org.martin.inventory.endpoints;

import org.martin.inventory.DTOs.ItemDTO;
import org.martin.inventory.annotations.Secured;
import org.martin.inventory.model.Item;
import org.martin.inventory.model.ItemHistoryEntry;
import org.martin.inventory.model.Warehouse;
import org.martin.inventory.service.HistoryEntryManager;
import org.martin.inventory.service.ItemManager;
import org.martin.inventory.service.WarehouseManager;
import org.martin.inventory.utils.UUIDUtils;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.Path;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Path("/items")
public class ItemResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ItemManager itemManager;

    @Inject
    private WarehouseManager whManager;

    @Inject
    private HistoryEntryManager entryManager;

    @GET //GET (./items/)
    @Secured
    @RolesAllowed({"Manager", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {
        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(itemManager.getAll()) {};
        return Response.ok(entity).build();
    }

    @GET //GET (./items/warehouse)
    @Secured
    @Path("/warehouse")
    @RolesAllowed({"Manager", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWarehouses() {
        GenericEntity<List<Warehouse>> entity = new GenericEntity<List<Warehouse>>(whManager.getAll()) {};
        return Response.ok(entity).build();
    }

    @GET //GET (./items/<warehouse id>)
    @Secured
    @Path("{wh_id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWarehouseItems(@PathParam("wh_id") String whId) {
        List<Item> items = whManager.getWarehouseItems(UUIDUtils.Dashify(whId));
        if (!items.isEmpty()) {
            GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(items) {};
            return Response.ok(entity).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).entity("The requested warehouse was not found.").build();
        }
    }

    @GET //GET (./items/<warehouse id>/<id>)
    @Secured
    @Path("{wh_id}/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("wh_id") String whId, @PathParam("id") int index) {
        try {
            Item item = whManager.getWarehouseItems(UUIDUtils.Dashify(whId)).get(index);
            return Response.ok(item).build();
        } catch ( IndexOutOfBoundsException e ) {
            return Response.status(Response.Status.NOT_FOUND).entity("The requested item was not found.").build();
        }
    }

    @POST //POST (./items/<warehouse id>)
    @Secured
    @Path("{wh_id}")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createItem(@PathParam("wh_id") String whId, ItemDTO item) {
        UUID warehouseId = UUID.fromString(UUIDUtils.Dashify(whId));
        if (whManager.exists(warehouseId)) {
            item.setWarehouseId(warehouseId);
            Item converted = item.convertToEntity();
            itemManager.add(converted);
            entryManager.add(new ItemHistoryEntry(converted.getId(), warehouseId, converted.getQuantity(), Instant.now()));
            return Response.status(Response.Status.CREATED).entity("New item successfully added.").build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).entity("The target warehouse was not found.").build();
        }
    }

    @PUT //PUT (./items/<id>)
    @Secured
    @Path("{id}")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateItem(@PathParam("id") Long itemId, ItemDTO item) {
        Item updated = itemManager.update(itemId, item.convertToEntity());
        if (updated != null) {
            entryManager.add(new ItemHistoryEntry(itemId, updated.getWarehouseId(), updated.getQuantity(), Instant.now()));
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid item id.").build();
        }
    }

    @DELETE //DELETE (./items/<id>)
    @Secured
    @Path("{id}")
    @PermitAll
    public Response deleteItem(@PathParam("id") Long itemId) {
        try {
            itemManager.delete(itemManager.getById(itemId));
            return Response.noContent().build();
        }
        catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Item not found with provided ID.").build();
        }
    }
}
