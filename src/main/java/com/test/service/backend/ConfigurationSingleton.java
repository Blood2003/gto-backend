
package com.test.service.backend;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigurationSingleton {
    
    private Properties config = new Properties();
    
    private ConfigurationSingleton() {
    }
    
    public static ConfigurationSingleton getInstance() {
        return ConfigurationSingletonHolder.INSTANCE;
    }
    
    private static class ConfigurationSingletonHolder {

        private static final ConfigurationSingleton INSTANCE = new ConfigurationSingleton();
    }
    
    public void loadFromFile(String filename) throws IOException {
        Path configLocation = Paths.get(filename);
        System.out.println("Config location: " + configLocation.toString());
        config = new Properties();
        if (Files.exists(configLocation)){
            InputStream stream = Files.newInputStream(configLocation);
            config.load(stream);  
        } 
    }
    
    public void printToSystemOutput() throws IOException {
        
        Properties printable = (Properties) config.clone();
        printable.store(System.out, "Loaded properties:");
        
    }
    
    
    public boolean isEnableLogggingFileProp() {
        String value  = getProperty("backend.logging.file.enable", "false");     
        return "true".equalsIgnoreCase(value);
    }
    
    public int  getPort(){
        String value  = getProperty("backend.port", "11080");    
        return Integer.valueOf(value);
    }
    
    public String getServiceUrl() {
        return getProperty("backend.service.url", "http://localhost:8101/projectx");     
    } 
  
    private String getProperty(String key, String defaultValue){
        String value = config.getProperty(key);
        
        if (value==null) {
            value = System.getProperty(key, defaultValue);
        }
        return value;
    }
    
       
}
