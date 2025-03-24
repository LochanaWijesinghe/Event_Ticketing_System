package oop.cw.cli.part1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class JsonConfiguration {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String filePath = "config.json";

    //save configuration to config.json file
    public static void saveConfiguration(SystemConfiguration config) {
        try(FileWriter writer = new FileWriter(filePath)){
            gson.toJson(config, writer);
            System.out.println("Configuration saved to " + filePath);
        }catch(IOException e){
            System.err.println("Error saving configuration to " + filePath);
        }
    }

    //load configurations from config.json file
    public static SystemConfiguration loadConfiguration() {
        File file = new File(filePath);
        if(!file.exists()){
            return null;
        }
        try(FileReader reader = new FileReader(filePath)){
            return gson.fromJson(reader, SystemConfiguration.class);
        }catch(IOException e){
            System.err.println("Error loading configuration from " + filePath);
            return null;
        }
    }
}

