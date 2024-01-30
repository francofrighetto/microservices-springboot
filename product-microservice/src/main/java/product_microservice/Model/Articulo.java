package product_microservice.Model;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
import java.util.List;

//import jakarta.persistence.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Franco
 */
@Getter
@Setter

@Entity
@Table(name="articulo")
public class Articulo {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int art_id;


    private int codigo;


    private Integer subcodigo;


    private boolean artHabilitado;

    private String nombre;

    private int stock;

    private BigDecimal precioCompra;

    private BigDecimal precioVenta;

    private String descripcion;


    private String color;

    private String unidadMedida;

    //@ManyToOne()
    //@JoinColumn(name="promocion",referencedColumnName="promo_id")
    private int promocion;


    @ManyToOne()
    @JoinColumn(name="art_cat",referencedColumnName="cat_id")
    private Categoria art_cat;


    @ManyToMany(mappedBy="articulo")
    //@JsonIgnore
    @JsonManagedReference
    private List<Foto> fotos;


    public Articulo(int art_id) {
        this.art_id = art_id;
    }

    public Articulo(){}

    public int getArt_id(){
        return this.art_id;
    }

    public boolean isArtHabilitado() {
        return artHabilitado;
    }

    public void setArtHabilitado(boolean artHabilitado) {
        this.artHabilitado = artHabilitado;
    }
}
