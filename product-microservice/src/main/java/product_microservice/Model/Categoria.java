package product_microservice.Model;

//import jakarta.persistence.*;
import javax.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="categoria")
public class Categoria {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cat_id;
    private String nombre;

    private boolean cat_habilitado;

    public String getNombre() {
        return nombre;
    }

    public int getCat_id() {
        return cat_id;
    }
}