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
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @GET
    @Secured
    @Path("/daily/quantity/{wh_id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public void getDailyWarehouseQuantity(@PathParam("wh_id") String whId, @Suspended final AsyncResponse asyncResponse) {
        asyncResponse.setTimeoutHandler(new TimeoutHandler() {

            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation timed out.").build());
            }
        });
        asyncResponse.setTimeout(30, TimeUnit.SECONDS);

        new Thread(new Runnable() {

            @Override
            public void run() {
                Response response;
                try {
                    List<QuantityReportDTO> reportList = entryManager.getWeeklyQuantityReport(whId);
                    GenericEntity<List<QuantityReportDTO>> entity = new GenericEntity<List<QuantityReportDTO>>(reportList) {};
                    response = Response.ok(entity).build();
                } catch ( IndexOutOfBoundsException e ) {
                    response = Response.status(Response.Status.NOT_FOUND).entity("The requested warehouse was not found.").build();
                }
                asyncResponse.resume(response);
            }
        }).start();
    }
}
