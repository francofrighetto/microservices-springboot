package product_microservice.Service;

import product_microservice.Model.Articulo;
import product_microservice.Repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
@Service
public class ArticuloService {
    @Autowired
    private ArticuloRepository repo;

    public List<Articulo> listar() {
        return repo.findAll();
    }

    public List<Articulo> obtenerArticulosVerificados(int value) {
        return repo.findByStockNotAndArtHabilitadoTrue(value);
    }
    
    public List<Articulo> buscarPorNombre(String nombre) {
        return repo.findByNombre(nombre);
    }

    public Articulo add(Articulo a) {
        return repo.save(a);
    }
    public Optional<Articulo> listarId(int id) {
        return repo.findById(id);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public Articulo edit(Articulo cs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Articulo> obtenerArticulosPorIdCategoria(int idCategoria) {
        return repo.findByArt_cat_Cat_id(idCategoria);
    }

    public List<Articulo> obtenerPromociones() {
        return repo.findPromocion();
    }

    public List<Articulo> obtenerHabilitados(){
        return repo.findByArtHabilitadoTrue();
    }

    public List<Articulo> obtenerNoHabilitados(){
        return repo.findByArtHabilitadoFalse();
    }

        // paginación
    public Page<Articulo> findHabilitadoPaginado(Pageable paging) {
        return repo.findByArtHabilitadoTrue(paging);
    }

    public Page<Articulo> findNoHabilitadoPaginado(Pageable paging) {
        return repo.findByArtHabilitadoFalse(paging);
    }
}
