package es.personal.catalogo.process;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.personal.catalogo.dto.CategoriaDTO;
import es.personal.catalogo.dto.RespuestaCatalogoDTO;
import es.personal.catalogo.dto.ServicioDTO;
import es.personal.catalogo.entity.CategoriaEntity;
import es.personal.catalogo.entity.ServicioEntity;
import es.personal.catalogo.finder.ServicioFinder;
import es.personal.catalogo.utils.Constantes;

@Service
public class BuscarServiciosProcess {

	private final ServicioFinder servicioFinder;
	
	/**
	 * Constructor
	 * @param servicioFinder
	 */
	public BuscarServiciosProcess (ServicioFinder servicioFinder) {
		this.servicioFinder=servicioFinder;
	}
	

	/**
	 * Buscar servicio activo por categoria
	 * @param categoria
	 * @return
	 */
	public List<ServicioDTO> buscarServiciosActivosPorCategoria (String categoria){
	return 	servicioFinder.buscarServiciosActivosPorCategoria(categoria);
	}

	/**
	 * Buscar servicios inactivos
	 * @return
	 */
	public List<ServicioDTO> buscarServiciosInactivos (){
	return 	servicioFinder.buscarServiciosInactivos();
	}
	
	/**
	 * Buscar servicios por categoria y estado activo o inactivo
	 * @param Categoria
	 * @param estado
	 * @return
	 */
	public List <ServicioDTO> buscarServiciosPorCategoriaEstado (String Categoria, boolean estado){
		return servicioFinder.buscarServiciosPorCategoriaEstado(Categoria, estado);	}
	
	/**
	 * Buscar servicio por categoria
	 * @param Categoria
	 * @return
	 */
	public List <ServicioDTO> buscarServiciosPorCategoria (String Categoria){
		return servicioFinder.buscarServiciosPorCategoria(Categoria);
	}
	
	/**
	 * Buscar servicio por codigo
	 * @param Codigo
	 * @return
	 */
	public Optional <ServicioDTO> buscarServiciosPorCodigo (String codigo){
		return servicioFinder.buscarServicioPorCodigo(codigo);
	}
	
	/**
	 * Crear servicio en base de datos
	 * @param servicio
	 * @return
	 */
	public Optional <ServicioDTO>  guardarServicio(ServicioDTO servicio) {
	    return servicioFinder.guardarServicio(servicio);
	}
	
	public String actualizarServicio(
	        String codigo, ServicioDTO datosActualizados) {

	    return servicioFinder.actualizarServicio(
	            codigo, datosActualizados);
	}
	public boolean existePorCodigo(String codigo) {
		return servicioFinder.existeServicio(codigo);
	}
	
	public boolean desactivarServicio (String codigo, boolean activar) {
		
		return servicioFinder.desactivarServicio(codigo, activar);
	}
	
	public boolean eliminarServicio (String codigo) {
		return servicioFinder.eliminarServicio(codigo);
	}
	
	
	/**
	 * 
	 * 
	 * CATEGORIAS
	 * 
	 * 
	 */
	
	
	public List<CategoriaDTO> buscarTodasCategorias(){
		return servicioFinder.buscarTodasCategorias();
	}
	
	public Optional<CategoriaDTO> buscarCategoriaPorId(Long id){
		return servicioFinder.buscarCategoriaPorId(id);
	}
	
	public String eliminarCategoriaPorNombre (String nombre) {
		return servicioFinder.eliminarCategoriaPorNombre(nombre);
	
	}
	
	public Optional<CategoriaDTO> buscarCategoriaPorNombre(String nombre){
		return servicioFinder.buscarCategoriaPorNombre(nombre);
	}
	
	public boolean crearCategoria (CategoriaDTO categoria) {
		return servicioFinder.crearCategoria(categoria);
	}
	
	public boolean modificarCategoria(CategoriaDTO categoria) {
		return servicioFinder.modificarCategoria(categoria);
	}
	
}
