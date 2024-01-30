package product_microservice.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {
    private String name;
    private String url;
    private String tipo;


    public FileInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public FileInfo() {
    }
}
