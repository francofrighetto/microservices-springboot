package product_microservice.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
import javax.persistence.*;


@Entity
@Table(name="foto")
public class Foto {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int foto_id;

    @Column
    private String foto_nombre;

    @ManyToOne()
    @JoinColumn(name="foto_art",referencedColumnName="art_id")
    @JsonBackReference
    private Articulo articulo;

    public Foto(String foto_nombre, Articulo articulo) {
        this.foto_nombre = foto_nombre;
        this.articulo = articulo;
    }
    public Foto() {
    }


    public Articulo getArticulo(){
        return this.articulo;
    }

    public void setArticulo(Articulo articulo){
        this.articulo = articulo;
    }

    public String getFoto_nombre() {
        return foto_nombre;
    }

    public void setFoto_nombre(String foto_nombre) {
        this.foto_nombre = foto_nombre;
    }
}

