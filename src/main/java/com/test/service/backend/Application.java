
package com.test.service.backend;

import java.io.PrintStream;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.RolloverFileOutputStream;

public class Application {
      
    
    public static void main(String[] args) throws Exception {

        String fileProperties = System.getProperty("backend.config", "./backend-config.properties");
        
        ConfigurationSingleton configurationSingleton = ConfigurationSingleton.getInstance();
        
        configurationSingleton.loadFromFile(fileProperties);
 
        configurationSingleton.printToSystemOutput();

        if(configurationSingleton.isEnableLogggingFileProp()){
             loggingConfiguration();
        }
        
        int portValue = configurationSingleton.getPort();
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/backend");

        Server jettyServer = new Server(portValue);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
             org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.          
        jerseyServlet.setInitParameter(
           "jersey.config.server.provider.classnames", Service.class.getCanonicalName());
        
        // the following is only needed if you want to use the built-in support for mapping pojo objects to json.
        jerseyServlet.setInitParameter(
           "com.sun.jersey.api.json.POJOMappingFeature", "true");
        
        // start Http Client
        ClientSingleton.getInstance().start();
        
        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            ClientSingleton.getInstance().stop();
            jettyServer.destroy();
        }
    }
    
   
    private static void loggingConfiguration() throws Exception {
        // Configurate RolloverFileOutputStream with file name pattern and appending property
	RolloverFileOutputStream os = new RolloverFileOutputStream("yyyy_mm_dd_datagen-proxy.log", true);
        // Create a print stream based on RolloverFileOutputStream
        PrintStream logStream = new PrintStream(os);
        // redirect system out and system error to  print stream.
        System.setOut(logStream);
        System.setErr(logStream);   
    }
}

