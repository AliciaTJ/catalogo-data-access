
package es.personal.catalogo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import es.personal.catalogo.entity.ServicioEntity;

@Repository

public interface ServicioRepository
        extends JpaRepository<ServicioEntity, Long> {

	/**
	 * Busqueda en base de datos servivios activos por categoria
	 * @param categoria
	 * @return List<ServiciosEntitiy)
	 */
    List<ServicioEntity> findByCategoriaNombreAndActivoTrue(String categoria);
    /**
     * Busqueda de servicios inactivos
     */
    List<ServicioEntity> findByActivoFalse();
    
    /**
     * Busqueda de servicio por categoria y estado
     * @param categoria
     * @param activo
     * @return
     */
    List<ServicioEntity> findByCategoriaNombreAndActivo(
            String categoria, boolean activo);
    
    /**
     * Busqueda de servicios activos
     * @return
     */
    List<ServicioEntity> findByActivoTrue();
    /**
     * Busqueda de servicios por categoria
     * @param categoria
     * @return List <ServiceEntity>
     */
    List<ServicioEntity> findByCategoriaNombre (String categoria);
    
    /**
     * Busqueda de servicios por codigo
     * @param codigo
     * @return
     */
    Optional <ServicioEntity> findByCodigo (String codigo);
    
    /**
     * Comprueba si existe el servicio
     * @param codigo
     * @return
     */
    boolean existsByCodigo(String codigo);
    

}
