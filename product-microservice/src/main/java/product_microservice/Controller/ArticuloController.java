package product_microservice.Controller;

import product_microservice.Model.Articulo;
import product_microservice.Model.Categoria;
import product_microservice.Service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/articulo")
public class ArticuloController {
    @Autowired
    private ArticuloService artServ;
    @Autowired

    Map<String, Object> response = new HashMap<>();

    @GetMapping(value = "/mostrar")
    public List<Articulo> articulos() {
        return artServ.listar();
    }

    @GetMapping(value = "/mostrar/verificados")
    public List<Articulo> articulosVerificados() {
        return artServ.obtenerArticulosVerificados(0);
    }

    @GetMapping(value = "/busqueda/{nombre}")
    public List<Articulo> articulosPorNombre(@PathVariable String nombre) {
        return artServ.buscarPorNombre(nombre);
    }

    @CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3600)
    @GetMapping(value = "/mostrar/habilitados")
    public List<Articulo> articulosHabilitados() {
        return artServ.obtenerHabilitados();
    }

    @CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3600)
    @GetMapping(value = "/mostrar/habilitados/paginado")
    public ResponseEntity<Object> articulosHabilitadosPaginado(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Articulo> articulos = artServ.findHabilitadoPaginado(paging);
        response.put("contenido", articulos.getContent());
        response.put("paginaActual", articulos.getNumber());
        response.put("totalRegistros", articulos.getTotalElements());
        response.put("totalPaginas", articulos.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3600)
    @GetMapping(value = "/mostrar/noHabilitados")
    public List<Articulo> articulosNoHabilitados() {

        return artServ.obtenerNoHabilitados();
    }

    @CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3600)
    @GetMapping(value = "/mostrar/noHabilitados/paginado")
    public ResponseEntity<Object> articulosNoHabilitadosPaginado(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<Articulo> articulos = artServ.findNoHabilitadoPaginado(paging);
        response.put("contenido", articulos.getContent());
        response.put("paginaActual", articulos.getNumber());
        response.put("totalRegistros", articulos.getTotalElements());
        response.put("totalPaginas", articulos.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3600)
    @GetMapping(value = "/mostrar/{id}")
    public Optional<Articulo> articulos(@PathVariable int id) {
        return artServ.listarId(id);
    }


    @CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3600)
    @PostMapping(value = "/nuevo")
    public ResponseEntity agregarArticulo(@RequestBody Articulo art) {
        try {
            artServ.add(art);
            this.response.put("status", "true");
            return new ResponseEntity<>(this.response, HttpStatus.OK);
        } catch (Exception e) {
            this.response.put("status", "false");
            return new ResponseEntity<>(this.response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3600)
    @DeleteMapping(value = "/eliminar/{idArticulo}")
    public ResponseEntity borrarArticulo(@PathVariable int idArticulo) {
        try {
            artServ.delete(idArticulo);
            this.response.put("status", "true");
            return new ResponseEntity<>(this.response, HttpStatus.OK);
        } catch (Exception e) {
            this.response.put("status", "false");
            return new ResponseEntity<>(this.response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/mostrar/promocion")
    public List<Articulo> promocion() {
        return artServ.obtenerPromociones();
    }

    @CrossOrigin(origins = { "http://localhost:4200" }, maxAge = 3600)
    @PostMapping(value = "/cambioHabilitado/{id}")
    public ResponseEntity cambioHabilitado(@PathVariable int id) {
        try {
            Optional<Articulo> optionalArticulo = artServ.listarId(id);
            if (optionalArticulo.isPresent()) {
                Articulo art = optionalArticulo.get();
                art.setArtHabilitado(!art.isArtHabilitado());
                artServ.add(art);
                this.response.put("status", "true");
            } else {
                this.response.put("status", "false");
            }
            return new ResponseEntity<>(this.response, HttpStatus.OK);
        } catch (Exception e) {
            this.response.put("status", "false");
            return new ResponseEntity<>(this.response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
