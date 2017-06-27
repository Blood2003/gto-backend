package com.test.service.backend;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.core.MediaType;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.util.log.Logger;

public class ClientSingleton {

    static final Logger LOGGER = org.eclipse.jetty.util.log.Log.getLogger(ClientSingleton.class.getName());
    
    private final HttpClient httpClient = new HttpClient();

    private ClientSingleton() {
    }

    public static ClientSingleton getInstance() {
        return ClientSingletonHolder.INSTANCE;
    }

    public void stop() throws Exception {
        httpClient.stop();
    }

    private static class ClientSingletonHolder {

        private static final ClientSingleton INSTANCE = new ClientSingleton();
    }

    public void start() throws Exception  {
        // Configure HttpClient, for example:
        httpClient.setFollowRedirects(false);

        // Start HttpClient
        httpClient.start();
    }

    
    public ContentResponse post(String path, String json) throws InterruptedException, TimeoutException, ExecutionException {

        String uri = ConfigurationSingleton.getInstance().getServiceUrl();
        
        Request request =  httpClient.POST(uri+path);
        request.header(HttpHeader.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        request.content(new StringContentProvider(json));
        
        LOGGER.info("In POST {}{} body=[{}]",  new Object[]{uri, path, json});
        ContentResponse response = request.send();
        LOGGER.info("Out POST {}{} response=[{}] content=[{}]", new Object[]{uri, path, response, response.getContentAsString()});
        
        return response;

    }
    
    public ContentResponse get(String path) throws InterruptedException, TimeoutException, ExecutionException {

        String uri = ConfigurationSingleton.getInstance().getServiceUrl();
        
   
  
        
        LOGGER.info("In POST {}{}",  new Object[]{uri, path});
        // ContentResponse response = httpClient.GET(uri+path);
        ContentResponse response = httpClient.GET("http://projectx/projectx/x");
        LOGGER.info("Out POST {}{} response=[{}] content=[{}]", new Object[]{uri, path, response, response.getContentAsString()});
        
        return response;

    }
    

}
