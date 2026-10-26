package es.personal.catalogo.finder;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.personal.catalogo.dto.CategoriaDTO;
import es.personal.catalogo.dto.RespuestaCatalogoDTO;
import es.personal.catalogo.dto.ServicioDTO;
import es.personal.catalogo.entity.CategoriaEntity;
import es.personal.catalogo.entity.ServicioEntity;
import es.personal.catalogo.repository.CategoriaRepository;
import es.personal.catalogo.repository.ServicioRepository;
import es.personal.catalogo.utils.Constantes;

@Service
public class ServicioFinder {

	private final ServicioRepository servicioRepository;
	private final CategoriaRepository categoriaRepository;

	public ServicioFinder(ServicioRepository servicioRepository, CategoriaRepository categoriaRepository) {
		this.servicioRepository = servicioRepository;
		this.categoriaRepository = categoriaRepository;
	}

	public List<ServicioDTO> buscarServiciosActivosPorCategoria(String categoria) {
		List<ServicioEntity> servicios = servicioRepository.findByCategoriaNombreAndActivoTrue(categoria);

		return servicios.stream().map(servicio -> new ServicioDTO(servicio.getCodigo(), servicio.getNombre(),
				servicio.getCategoria().getNombre())).toList();
	}

	public List<ServicioDTO> buscarServiciosInactivos() {
		List<ServicioEntity> servicios = servicioRepository.findByActivoFalse();

		return servicios.stream().map(servicio -> new ServicioDTO(servicio.getCodigo(), servicio.getNombre(),
				servicio.getCategoria().getNombre())).toList();
	}

	public List<ServicioDTO> buscarServiciosPorCategoria(String categoria) {
		List<ServicioEntity> servicios = servicioRepository.findByCategoriaNombre(categoria);
		return servicios.stream().map(servicio -> new ServicioDTO(servicio.getCodigo(), servicio.getNombre(),
				servicio.getCategoria().getNombre())).toList();

	}

	public List<ServicioDTO> buscarServiciosPorCategoriaEstado(String categoria, boolean estado) {
		List<ServicioEntity> servicios = servicioRepository.findByCategoriaNombreAndActivo(categoria, estado);

		return servicios.stream().map(servicio -> new ServicioDTO(servicio.getCodigo(), servicio.getNombre(),
				servicio.getCategoria().getNombre())).toList();
	}

	public Optional<ServicioDTO> buscarServicioPorCodigo(String codigo) {
		Optional<ServicioEntity> servicios = servicioRepository.findByCodigo(codigo);
		return servicios.map(servicio -> new ServicioDTO(servicio.getCodigo(), servicio.getNombre(),
				servicio.getCategoria().getNombre()));

	}

	public Optional<ServicioDTO> guardarServicio(ServicioDTO servicio) {
		Optional<CategoriaEntity> categoria = categoriaRepository.findByNombre(servicio.getCategoria());

		if (categoria.isEmpty()) {
			return Optional.empty();
		}

		ServicioEntity servicioNuevo = new ServicioEntity();
		servicioNuevo.setCodigo(servicio.getCodigo());
		servicioNuevo.setNombre(servicio.getNombre());
		servicioNuevo.setCategoria(categoria.get());
		servicioNuevo.setActivo(true);
		servicioRepository.save(servicioNuevo);

		ServicioDTO dto = new ServicioDTO(servicioNuevo.getCodigo(), servicioNuevo.getNombre(),
				servicioNuevo.getCategoria().getNombre());

		return Optional.of(dto);
	}

	/**
	 * Actualizar servicio
	 * 
	 * @param codigo
	 * @param datosActualizados
	 * @return
	 */
	public String actualizarServicio(String codigo, ServicioDTO datosActualizados) {

		Optional<ServicioEntity> resultado = servicioRepository.findByCodigo(codigo);

		if (resultado.isEmpty()) {
			return Constantes.INFO1;
		}

		Optional<CategoriaEntity> categoria = categoriaRepository.findByNombre(datosActualizados.getCategoria());
		if (categoria.isEmpty()) {
			return Constantes.ERROR5INF;
		}

		ServicioEntity servicio = resultado.get();
		servicio.setNombre(datosActualizados.getNombre());
		servicio.setCategoria(categoria.get());

		ServicioEntity actualizado = servicioRepository.save(servicio);

		ServicioDTO dto = new ServicioDTO(actualizado.getCodigo(), actualizado.getNombre(),
				actualizado.getCategoria().getNombre());

		return Constantes.INFO12;
	}

	public boolean existeServicio(String codigo) {
		return servicioRepository.existsByCodigo(codigo);
	}

	public boolean desactivarServicio(String codigo, boolean activar) {
		Optional<ServicioEntity> servicios = servicioRepository.findByCodigo(codigo);
		if (servicios.isEmpty()) {
			return false;
		} else {
			ServicioEntity servicio = servicios.get();
			servicio.setActivo(activar);
			servicioRepository.save(servicio);
			return true;
		}
	}

	public boolean eliminarServicio(String codigo) {
		Optional<ServicioEntity> servicios = servicioRepository.findByCodigo(codigo);
		if (servicios.isEmpty()) {
			return false;
		} else {
			ServicioEntity servicio = servicios.get();
			servicioRepository.delete(servicio);
			return true;
		}
	}

	/**
	 * CATEGORIAS
	 * 
	 */

	public Optional<CategoriaDTO> buscarCategoriaPorNombre(String nombre) {
		Optional<CategoriaEntity> categoriaList = categoriaRepository.findByNombre(nombre);
		return categoriaList.map(
				categoria -> new CategoriaDTO(categoria.getId(), categoria.getNombre(), categoria.getDescripcion()));

	}

	public List<CategoriaDTO> buscarTodasCategorias() {
		List<CategoriaEntity> categoriaList = categoriaRepository.findAll();
		return categoriaList.stream().map(
				categoria -> new CategoriaDTO(categoria.getId(), categoria.getNombre(), categoria.getDescripcion()))
				.toList();

	}

	public Optional<CategoriaDTO> buscarCategoriaPorId(Long id) {
		Optional<CategoriaEntity> categoriaList = categoriaRepository.findById(id);
		return categoriaList.map(
				categoria -> new CategoriaDTO(categoria.getId(), categoria.getNombre(), categoria.getDescripcion()));

	}

	public String eliminarCategoriaPorNombre(String nombre) {
		Optional<CategoriaEntity> categoria = categoriaRepository.findByNombre(nombre);
		if (categoria.isEmpty()) {
			return Constantes.ERROR5INF;
		} else {
			List<ServicioEntity> servicios = servicioRepository.findByCategoriaNombre(nombre);
			if (servicios.isEmpty()) {
				categoriaRepository.delete(categoria.get());
				return Constantes.INFO8;
			} else {
				return Constantes.INFO9;
			}
		}
	}

	public boolean crearCategoria(CategoriaDTO categoria) {
		if (categoriaRepository.findByNombre(categoria.getNombre()).isEmpty()) {
			CategoriaEntity categoriaEntidad = new CategoriaEntity();
			categoriaEntidad.setNombre(categoria.getNombre());
			categoriaEntidad.setDescripcion(categoria.getDescripcion());
			categoriaRepository.save(categoriaEntidad);
			return true;
		} else {
			return false;
		}
	}

	public boolean modificarCategoria(CategoriaDTO categoria) {
		Optional<CategoriaEntity> categoriaEncontrada = categoriaRepository.findByNombre(categoria.getNombre());
		if (categoriaEncontrada.isEmpty()) {
			return false;
		} else {
			CategoriaEntity categoriaEntidad = categoriaEncontrada.get();
			categoriaEntidad.setNombre(categoria.getNombre());
			categoriaEntidad.setDescripcion(categoria.getDescripcion());
			categoriaRepository.save(categoriaEntidad);
			return true;
		}
	}

}
