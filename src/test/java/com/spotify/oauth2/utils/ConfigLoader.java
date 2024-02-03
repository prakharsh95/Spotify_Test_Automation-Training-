package com.spotify.oauth2.utils;

import java.io.File;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;
    public ConfigLoader(){
        properties=PropertyUtils.propertyLoader("src"+ File.separator+"test"+File.separator+"resources"+File.separator+"config.properties");
    }

    public static ConfigLoader getInstance(){
        if(configLoader==null){
            configLoader=new ConfigLoader();
        }
        return configLoader;
    }

    public String getGrantType(){
        if(properties.containsKey("grant_type"))
            return properties.getProperty("grant_type");
        else
            throw new RuntimeException("The key does not exist in the config.properties file");
    }

    public String getClientId(){
        if(properties.containsKey("client_id"))
            return properties.getProperty("client_id");
        else
            throw new RuntimeException("The key does not exist in the config.properties file");
    }

    public String getClientSecret(){
        if(properties.containsKey("client_secret"))
            return properties.getProperty("client_secret");
        else
            throw new RuntimeException("The key does not exist in the config.properties file");
    }

    public String getRefreshToken(){
        if(properties.containsKey("refresh_token"))
            return properties.getProperty("refresh_token");
        else
            throw new RuntimeException("The key does not exist in the config.properties file");
    }

    public String getUserId(){
        if(properties.containsKey("user_id"))
            return properties.getProperty("user_id");
        else
            throw new RuntimeException("The key does not exist in the config.properties file");
    }
}
