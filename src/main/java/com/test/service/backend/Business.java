package com.test.service.backend;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.eclipse.jetty.client.api.ContentResponse;

public class Business {


    public ContentResponse processRequest() throws InterruptedException, TimeoutException, ExecutionException {

        
        // Post Request 
        return ClientSingleton.getInstance().get("/x");

       

    }

}
