package es.personal.catalogo.api;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.personal.catalogo.dto.RespuestaCatalogoDTO;
import es.personal.catalogo.dto.ServicioDTO;
import es.personal.catalogo.entity.ServicioEntity;
import es.personal.catalogo.process.BuscarServiciosProcess;
import es.personal.catalogo.utils.Constantes;

@Service
public class CatalogoDataAccessAPI {
	private final BuscarServiciosProcess buscarServiciosProcess;

	public CatalogoDataAccessAPI(BuscarServiciosProcess buscarServiciosProcess) {
		this.buscarServiciosProcess = buscarServiciosProcess;
	}

	/**
	 * Busqueda de servicios activos por categoria
	 * 
	 * @param categoria
	 * @return Lista de servicios activos por categoria
	 */
	public RespuestaCatalogoDTO buscarServiciosActivosPorCategoria(String categoria) {

		if (categoria.isEmpty() || categoria == null || categoria.isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR1INF, List.of());
		} else {
			List<ServicioDTO> listaServicios = buscarServiciosProcess.buscarServiciosActivosPorCategoria(categoria);

			return new RespuestaCatalogoDTO(Constantes.CODOK,
					listaServicios.isEmpty() ? Constantes.INFO1 : Constantes.INFO2, listaServicios);

		}

	}

	/**
	 * Busqueda de servicios inactivos
	 * 
	 * @return Lista de servicios inactivos
	 */
	public RespuestaCatalogoDTO buscarServiciosInactivos() {

		List<ServicioDTO> listaServicios = buscarServiciosProcess.buscarServiciosInactivos();

		return new RespuestaCatalogoDTO(Constantes.CODOK,
				listaServicios.isEmpty() ? Constantes.INFO1 : Constantes.INFO2, listaServicios);
	}

	/**
	 * Busqueda de servicios activos o inactivos por categoria
	 * 
	 * @param categoria
	 * @return Lista de servicios
	 */
	public RespuestaCatalogoDTO buscarServiciosPorCategoria(String categoria) {

		if (categoria.isEmpty() || categoria.isBlank() || categoria == null) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR1INF, List.of());
		} else {
			List<ServicioDTO> listaServicios = buscarServiciosProcess.buscarServiciosPorCategoria(categoria);
			return new RespuestaCatalogoDTO(Constantes.CODOK,
					listaServicios.isEmpty() ? Constantes.INFO1 : Constantes.INFO2, listaServicios);
		}
	}

	/**
	 * Busqueda de servicios por categoria activos e inactivos
	 * 
	 * @param categoria
	 * @param estado
	 * @return
	 */
	public RespuestaCatalogoDTO buscarServiciosPorCategoriaEstado(String categoria, boolean estado) {
		if (categoria == null || categoria.isEmpty() || categoria.isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR1INF, List.of());
		} else {
			List<ServicioDTO> listaServicios = buscarServiciosProcess.buscarServiciosPorCategoriaEstado(categoria,
					estado);
			return new RespuestaCatalogoDTO(Constantes.CODOK,
					listaServicios.isEmpty() ? Constantes.INFO1 : Constantes.INFO2, listaServicios);
		}
	}

	/**
	 * Busqueda de servicios por codigo
	 * 
	 * @param codigo
	 * @return
	 */
	public RespuestaCatalogoDTO buscarServiciosPorCodigo(String codigo) {
		if (codigo.isBlank() || codigo.isEmpty() || codigo == null) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());
		} else {
			Optional<ServicioDTO> listaServicios = buscarServiciosProcess.buscarServiciosPorCodigo(codigo);
			if (listaServicios.isEmpty()) {
				return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.INFO2, List.of());
			} else {
				return new RespuestaCatalogoDTO(Constantes.CODOK, Constantes.INFO1, List.of(listaServicios.get()));
			}
		}
	}

	/**
	 * Creacion de servicio
	 * 
	 * @param servicio
	 * @return
	 */
	public RespuestaCatalogoDTO crearServicio(ServicioDTO servicio) {
		if (servicio == null || servicio.getCodigo().isBlank() || servicio.getCodigo().isEmpty()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());
		} else {
			if (buscarServiciosProcess.existePorCodigo(servicio.getCodigo())) {
				return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());
			} else {
				ServicioEntity servicioNuevo = new ServicioEntity();
				servicioNuevo.setNombre(servicio.getNombre());
				servicioNuevo.setCodigo(servicio.getCodigo());
				servicioNuevo.setCategoria(servicio.getCategoria());
				servicioNuevo.setActivo(true);
				buscarServiciosProcess.guardarServicio(servicioNuevo);
				return new RespuestaCatalogoDTO(Constantes.CODOK, Constantes.INFO3, List.of(servicio));
			}

		}
	}

	/**
	 * Modificar un servicio existente
	 *
	 * @param servicio datos del servicio que se quiere modificar
	 * @return respuesta de la operación
	 */
	public RespuestaCatalogoDTO modificarServicio(ServicioDTO servicio) {

		if (servicio == null) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());
		}

		if (servicio.getCodigo() == null || servicio.getCodigo().isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());
		}

		if (servicio.getNombre() == null || servicio.getNombre().isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR6INF, List.of());
		}

		if (servicio.getCategoria() == null || servicio.getCategoria().isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR1INF, List.of());
		}

		Optional<ServicioDTO> resultado = buscarServiciosProcess.actualizarServicio(servicio.getCodigo(), servicio);

		if (resultado.isEmpty()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.INFO1, List.of());
		}

		return new RespuestaCatalogoDTO(Constantes.CODOK, Constantes.INFO3, List.of(resultado.get()));
	}

	/**
	 * Desactivar servicios
	 * 
	 * @param servicio
	 * @return
	 */
	public RespuestaCatalogoDTO desactivarServicio(ServicioDTO servicio, boolean activar) {
		if (servicio == null || servicio.getCodigo() == null || servicio.getCodigo().isBlank()
				|| servicio.getCodigo().isEmpty()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());
		} else {

			if (buscarServiciosProcess.desactivarServicio(servicio.getCodigo(), activar)) {
				return new RespuestaCatalogoDTO(Constantes.CODOK, activar ? Constantes.INFO6 : Constantes.INFO4,
						List.of(servicio));
			} else {
				return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.INFO1, List.of(servicio));
			}

		}
	}

	/**
	 * Elimina un servicio de la base de datos
	 * 
	 * @param servicio
	 * @return
	 */
	public RespuestaCatalogoDTO eliminarServicio(ServicioDTO servicio) {
		if (servicio == null || servicio.getCodigo() == null || servicio.getCodigo().isBlank()
				|| servicio.getCodigo().isEmpty()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());
		} else {
			if (buscarServiciosProcess.eliminarServicio(servicio.getCodigo())) {
				return new RespuestaCatalogoDTO(Constantes.CODOK, Constantes.INFO5, List.of(servicio));
			} else {
				return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.INFO1, List.of(servicio));
			}

		}
	}

}
