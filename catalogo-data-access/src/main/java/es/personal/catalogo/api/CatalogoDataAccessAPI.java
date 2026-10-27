package es.personal.catalogo.api;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.personal.catalogo.dto.CategoriaDTO;
import es.personal.catalogo.dto.RespuestaCatalogoDTO;
import es.personal.catalogo.dto.RespuestaCategoriaDTO;
import es.personal.catalogo.dto.ServicioDTO;
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

		if (categoria == null || categoria.isBlank()) {
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

		if (categoria == null || categoria.isBlank()) {
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
		if (categoria == null || categoria.isBlank()) {
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
		if (codigo == null || codigo.isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());
		} else {
			Optional<ServicioDTO> listaServicios = buscarServiciosProcess.buscarServiciosPorCodigo(codigo);
			if (listaServicios.isEmpty()) {
				return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.INFO1, List.of());
			} else {
				return new RespuestaCatalogoDTO(Constantes.CODOK, Constantes.INFO2, List.of(listaServicios.get()));
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
		if (servicio == null || servicio.getCodigo() == null || servicio.getCodigo().isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());

		}
		if (servicio.getNombre() == null || servicio.getNombre().isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR6INF, List.of());

		}
		if (servicio.getCategoria() == null || servicio.getCategoria().isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR1INF, List.of());
		}

		if (buscarServiciosProcess.existePorCodigo(servicio.getCodigo())) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR4INF, List.of());
		}

		Optional<ServicioDTO> guardado = buscarServiciosProcess.guardarServicio(servicio);

		if (guardado.isEmpty()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR5INF, List.of());
		}
		return new RespuestaCatalogoDTO(Constantes.CODOK, Constantes.INFO3, List.of(guardado.get()));
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

	String resultado = buscarServiciosProcess.actualizarServicio(servicio.getCodigo(), servicio);

		if (resultado.equalsIgnoreCase(Constantes.INFO1)) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.INFO1, List.of());
		}
		
		if (resultado.equalsIgnoreCase(Constantes.ERROR5INF))  {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR5INF, List.of());
		}


		return new RespuestaCatalogoDTO(Constantes.CODOK, Constantes.INFO3, List.of(servicio));
	}

	/**
	 * Desactivar servicios
	 * 
	 * @param servicio
	 * @return
	 */
	public RespuestaCatalogoDTO desactivarServicio(ServicioDTO servicio, boolean activar) {
		if (servicio == null || servicio.getCodigo() == null || servicio.getCodigo().isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());
		} else {

			if (buscarServiciosProcess.desactivarServicio(servicio.getCodigo(), activar)) {
				return new RespuestaCatalogoDTO(Constantes.CODOK, activar ? Constantes.INFO6 : Constantes.INFO4,
						List.of(servicio));
			} else {
				return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.INFO1, List.of());
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
		if (servicio == null || servicio.getCodigo() == null || servicio.getCodigo().isBlank()) {
			return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.ERROR3INF, List.of());
		} else {
			if (buscarServiciosProcess.eliminarServicio(servicio.getCodigo())) {
				return new RespuestaCatalogoDTO(Constantes.CODOK, Constantes.INFO5, List.of(servicio));
			} else {
				return new RespuestaCatalogoDTO(Constantes.CODKO, Constantes.INFO1, List.of());
			}

		}
	}

	/**
	 * 
	 * CATEGORIAS
	 * 
	 * 
	 */

	/**
	 * Busca categorias por nombre
	 * 
	 * @param nombre
	 * @return
	 */
	public RespuestaCategoriaDTO buscarCategoriaPorNombre(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR1INF, List.of());
		} else {
			Optional<CategoriaDTO> categoria = buscarServiciosProcess.buscarCategoriaPorNombre(nombre);
			if (categoria.isEmpty()) {
				return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR5INF, List.of());
			} else {
				return new RespuestaCategoriaDTO(Constantes.CODOK, Constantes.INFO2, List.of(categoria.get()));
			}
		}

	}

	/**
	 * Byscar categoria por id
	 * 
	 * @param id
	 * @return
	 */
	public RespuestaCategoriaDTO buscarCategoriaPorId(Long id) {
		if (id == null) {
			return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR7INF, List.of());
		} else {
			Optional<CategoriaDTO> categoria = buscarServiciosProcess.buscarCategoriaPorId(id);
			if (categoria.isEmpty()) {
				return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR5INF, List.of());
			} else {
				return new RespuestaCategoriaDTO(Constantes.CODOK, Constantes.INFO2, List.of(categoria.get()));
			}
		}

	}

	/**
	 * Busca todas las categorias
	 * 
	 * @return
	 */
	public RespuestaCategoriaDTO buscarTodasLasCategorias() {
		List<CategoriaDTO> listaCategorias = buscarServiciosProcess.buscarTodasCategorias();

		if (listaCategorias.isEmpty()) {
			return new RespuestaCategoriaDTO(Constantes.CODOK, Constantes.INFO7, listaCategorias);
		} else {
			return new RespuestaCategoriaDTO(Constantes.CODOK, Constantes.INFO2, listaCategorias);
		}
	}

	/**
	 * Eliminar categoria por nombre
	 * 
	 * @param nombre
	 * @return
	 */
	public RespuestaCategoriaDTO eliminarCategoriaPorNombre(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR6INF, List.of());
		} else {
			String resultado = buscarServiciosProcess.eliminarCategoriaPorNombre(nombre);
			if (resultado.equalsIgnoreCase(Constantes.INFO8)) {
				return new RespuestaCategoriaDTO(Constantes.CODOK, Constantes.INFO8, List.of());
			} else if (resultado.equalsIgnoreCase(Constantes.ERROR5INF)) {
				return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR5INF, List.of());
			} else {
				return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.INFO9, List.of());
			}
		}
	}

	/**
	 * Crear categoria
	 * 
	 * @param categoria
	 * @return
	 */
	public RespuestaCategoriaDTO crearCategoria(CategoriaDTO categoria) {
		if (categoria == null || categoria.getNombre() == null || categoria.getNombre().isBlank()) {
			return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR6INF, List.of());
		}
		if (categoria.getDescripcion() == null || categoria.getDescripcion().isBlank()) {
			return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR8INF, List.of());
		}
		if (buscarServiciosProcess.crearCategoria(categoria)) {
			return new RespuestaCategoriaDTO(Constantes.CODOK, Constantes.INF10, List.of(categoria));
		} else {
			return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR9INF, List.of());
		}
	}

	/**
	 * Modificar categoria
	 * 
	 * @param categoria
	 * @return
	 */
	public RespuestaCategoriaDTO modificarCategoria(CategoriaDTO categoria) {
		if (categoria == null || categoria.getNombre() == null || categoria.getNombre().isBlank()) {
			return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR6INF, List.of());

		}
		if (buscarServiciosProcess.modificarCategoria(categoria)) {
			return new RespuestaCategoriaDTO(Constantes.CODOK, Constantes.INF11, List.of(categoria));
		} else {
			return new RespuestaCategoriaDTO(Constantes.CODKO, Constantes.ERROR5INF, List.of());
		}
	}
}
