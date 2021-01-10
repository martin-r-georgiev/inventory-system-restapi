package org.martin.inventory.resources;

import org.martin.inventory.annotations.Secured;
import org.martin.inventory.model.ItemHistoryEntry;
import org.martin.inventory.service.HistoryEntryManager;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/records")
public class ItemRecordsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private HistoryEntryManager entryManager;

    @GET //GET (./records/<warehouse id>/<id>)
    @Secured
    @Path("{wh_id}/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemRecords(@PathParam("wh_id") String whId, @PathParam("id") Long itemId) {
        try {
            List<ItemHistoryEntry> records = entryManager.getAllByItemAndWarehouseId(itemId, whId);
            GenericEntity<List<ItemHistoryEntry>> entity = new GenericEntity<List<ItemHistoryEntry>>(records) {};
            return Response.ok(entity).build();
        } catch ( IndexOutOfBoundsException e ) {
            return Response.status(Response.Status.NOT_FOUND).entity("The requested item was not found.").build();
        }
    }
}
