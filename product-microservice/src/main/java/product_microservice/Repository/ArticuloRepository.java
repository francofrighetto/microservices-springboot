package product_microservice.Repository;

import product_microservice.Model.Articulo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

import java.util.List;

@Repository
public interface ArticuloRepository  extends JpaRepository<Articulo,Integer> {
    
    @Query(value = "SELECT * FROM Articulo WHERE art_cat = :idCategoria AND stock <> 0 AND art_habilitado", nativeQuery = true)
    List<Articulo> findByArt_cat_Cat_id(int idCategoria);
    //List<Articulo> findByArtCatIdAndStockIsNotAndArtHabilitadoIsTrue(int artCatId, int stock);

    @Query(value = "SELECT * FROM Articulo WHERE promocion <> 0 AND stock <> 0 AND art_habilitado", nativeQuery = true)
    List<Articulo> findPromocion();
    //List<Articulo> findByPromocionNotAndStockNotAndArtHabilitadoIsTrue(int promocion, int stock);

    @Query(value = "SELECT * FROM Articulo WHERE nombre ILIKE %:busqueda% AND stock <> 0 AND art_habilitado", nativeQuery = true)
    List<Articulo> findByNombre(String busqueda);
    
    List<Articulo> findByArtHabilitadoTrue();

    Page<Articulo> findByArtHabilitadoTrue(Pageable page);


    List<Articulo> findByArtHabilitadoFalse();

    Page<Articulo> findByArtHabilitadoFalse(Pageable page);


    List<Articulo> findByStockNotAndArtHabilitadoTrue(int value);

}
