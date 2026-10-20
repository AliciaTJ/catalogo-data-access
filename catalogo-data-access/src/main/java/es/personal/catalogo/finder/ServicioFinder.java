package es.personal.catalogo.finder;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.personal.catalogo.dto.ServicioDTO;
import es.personal.catalogo.entity.ServicioEntity;
import es.personal.catalogo.repository.ServicioRepository;

@Service
public class ServicioFinder {

	private final ServicioRepository servicioRepository;

	public ServicioFinder(ServicioRepository servicioRepository) {
		this.servicioRepository = servicioRepository;
	}

	/**
	 * Buscar servicios activos por categoria
	 */
	public List<ServicioDTO> buscarServiciosActivosPorCategoria(String categoria) {
		List<ServicioEntity> servicios = servicioRepository.findByCategoriaAndActivoTrue(categoria);

		return servicios.stream()
				.map(servicio -> new ServicioDTO(servicio.getNombre(), servicio.getCodigo(), servicio.getCategoria()))
				.toList();
	}

	public List<ServicioDTO> buscarServiciosInactivos() {
		List<ServicioEntity> servicios = servicioRepository.findByActivoFalse();

		return servicios.stream()
				.map(servicio -> new ServicioDTO(servicio.getCodigo(), servicio.getNombre(), servicio.getCategoria()))
				.toList();
	}

	public List<ServicioDTO> buscarServiciosPorCategoria(String categoria) {
		List<ServicioEntity> servicios = servicioRepository.findByCategoria(categoria);
		return servicios.stream()
				.map(servicio -> new ServicioDTO(servicio.getCodigo(), servicio.getNombre(), servicio.getCategoria()))
				.toList();

	}

	public List<ServicioDTO> buscarServiciosPorCategoriaEstado(String categoria, boolean estado) {
		List<ServicioEntity> servicios = servicioRepository.findByCategoriaAndActivo(categoria, estado);

		return servicios.stream()
				.map(servicio -> new ServicioDTO(servicio.getNombre(), servicio.getCodigo(), servicio.getCategoria()))
				.toList();
	}

	public Optional<ServicioDTO> buscarServicioPorCodigo(String codigo) {
		Optional<ServicioEntity> servicios = servicioRepository.findByCodigo(codigo);
		return servicios
				.map(servicio -> new ServicioDTO(servicio.getNombre(), servicio.getCodigo(), servicio.getCategoria()));

	}

	public ServicioEntity guardarServicio(ServicioEntity servicio) {
		return servicioRepository.save(servicio);
	}
	public Optional<ServicioDTO> actualizarServicio(
	        String codigo, ServicioDTO datosActualizados) {

	    Optional<ServicioEntity> resultado =
	            servicioRepository.findByCodigo(codigo);

	    if (resultado.isEmpty()) {
	        return Optional.empty();
	    }

	    ServicioEntity servicio = resultado.get();

	    servicio.setNombre(datosActualizados.getNombre());
	    servicio.setCategoria(datosActualizados.getCategoria());

	    ServicioEntity actualizado =
	            servicioRepository.save(servicio);

	    ServicioDTO dto = new ServicioDTO(
	            actualizado.getCodigo(),
	            actualizado.getNombre(),
	            actualizado.getCategoria());

	    return Optional.of(dto);
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
}
