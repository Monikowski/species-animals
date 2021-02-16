package studia.isa.monikowski.file.storage;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "isa_lab6_files"; //pliki

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}