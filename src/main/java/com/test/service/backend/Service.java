package com.test.service.backend;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.eclipse.jetty.util.log.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.eclipse.jetty.client.api.ContentResponse;

@Path("/api")
public class Service {

    static final Logger LOGGER = org.eclipse.jetty.util.log.Log.getLogger(Service.class.getName());

    Business business = new Business();
    
    @GET
    @Path("ping")
    public Response ping() throws InterruptedException, TimeoutException, ExecutionException {
        LOGGER.info("In GET /backend/api/ping");
        Response response = null;
        try {
            ContentResponse result = business.processRequest();
            response = Response.status(result.getStatus()).entity(result.getContentAsString()).type(MediaType.APPLICATION_JSON).build();
            return response;
        } finally {
            LOGGER.info("Out GET /backend/api/ping response=[{}]", response);
        }
    }


}
