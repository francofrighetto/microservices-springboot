package com.auth.security.repository;

import java.util.List;
import java.util.Optional;
import com.auth.security.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByCelular(String celular);
    Optional<Usuario> findByCelularOrEmail(String celular, String email);
    Optional<Usuario> findByTokenPassword(String tokenPassword);
    
    @Query(value = "SELECT * FROM usuario where celular ILIKE %:busqueda% or email ILIKE %:busqueda%",nativeQuery = true)
    Page<Usuario> findAll(String busqueda, Pageable pageable);
    
    //List<Usuario> findByCelularContainingIgnoreCaseOrEmailContainingIgnoreCase(String searchString);

    Page<Usuario> findAll(Pageable pageable);

    //Optional<Usuario> findByGrupoempresampresaAndNombreusuarioOrEmail(GrupoEmpresaEmpresa grupoempresaempresa, String nombreUsuario, String email);

    //Optional<Usuario> findByEmpresaEmpresagrupoNumeroAndEmpresaNumeroAndNombreusuarioOrEmail(int grupoEmpresas, int numeroEmpresa, String nombreUsuario, String email);

    boolean existsByCelular(String celular);
    
    boolean existsByEmail(String email);
}


/*
List<Employee> findByEmployeeKeyEmployeeIdAndEmployeeKeyBranchName(
        int employId,
        String branchName);
@Entity
class Employee {

    @EmbeddedId
    private EmployeeKey employeeKey;

    private String firstName;
    private String lastName;

    // Other fields
    // Getter and Setters
}

@Embeddable
class EmployeeKey implements Serializable {

    private int employeeId;
    private String branchName;
    private String departmentName;

    //Getter and Setters
}

*/