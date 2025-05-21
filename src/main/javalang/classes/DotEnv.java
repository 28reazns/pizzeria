package main.javalang.classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class DotEnv{
    private final String path;
    public DotEnv(String path){
        this.path = path;
    }

    public String Get(String key){
        var props = new Properties();
        var envFile = Paths.get(this.path);
        try (var inputStream = Files.newInputStream(envFile)) {
            props.load(inputStream);
        }catch(IOException e){
            System.err.println("Failed to read env file: " + e.getMessage());
            return "";
        }
        return (String) props.get(key);
    }
}