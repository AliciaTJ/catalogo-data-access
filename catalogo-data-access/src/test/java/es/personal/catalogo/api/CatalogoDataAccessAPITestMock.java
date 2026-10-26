package es.personal.catalogo.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.personal.catalogo.dto.RespuestaCatalogoDTO;
import es.personal.catalogo.dto.RespuestaCategoriaDTO;
import es.personal.catalogo.dto.ServicioDTO;
import es.personal.catalogo.entity.CategoriaEntity;
import es.personal.catalogo.process.BuscarServiciosProcess;
import es.personal.catalogo.utils.Constantes;

@ExtendWith(MockitoExtension.class)
class CatalogoDataAccessAPITestMock {

	@Mock
	private BuscarServiciosProcess buscarServiciosProcess;

	@InjectMocks
	private CatalogoDataAccessAPI catalogoDataAccessAPI;

	private ServicioDTO servicio;
	private CategoriaEntity categoria;

	@BeforeEach
	void setUp() {
		servicio = new ServicioDTO("SER001", "Consulta actualizada", "SALUD");
		categoria = new CategoriaEntity();
		categoria.setId(1L);
		categoria.setNombre("SALUD");
	}

	@Test
	void debeDevolverErrorCuandoNombreVacio() {
		servicio = new ServicioDTO("SER001", "", "SALUD");

		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.modificarServicio(servicio);

		assertEquals(Constantes.CODKO, respuesta.getCodigo());
		assertEquals(Constantes.ERROR6INF, respuesta.getMensaje());
		assertEquals(0, respuesta.getServicios().size());

		verify(buscarServiciosProcess, never()).actualizarServicio(anyString(), any(ServicioDTO.class));

	}

	@Test
	void debeDevolverErrorCuandoCategoriaVacia() {
		servicio = new ServicioDTO("SER001", "Consulta", null);

		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.modificarServicio(servicio);

		assertEquals(Constantes.CODKO, respuesta.getCodigo());
		assertEquals(Constantes.ERROR1INF, respuesta.getMensaje());
		assertEquals(0, respuesta.getServicios().size());

		verify(buscarServiciosProcess, never()).actualizarServicio(anyString(), any(ServicioDTO.class));

	}
	@Test
	void debeModificarServicioCuandoExiste() {

		when(buscarServiciosProcess.actualizarServicio("SER001", servicio)).thenReturn(Constantes.INFO12);

		// Act: ejecutamos la operación
		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.modificarServicio(servicio);

		// Assert: comprobamos el resultado
		assertEquals(Constantes.CODOK, respuesta.getCodigo());
		assertEquals(1, respuesta.getServicios().size());
		assertEquals("Consulta actualizada", respuesta.getServicios().get(0).getNombre());

		verify(buscarServiciosProcess).actualizarServicio("SER001", servicio);
	}

	@Test
	void debeNoModificarServicioCuandoServicioNoExiste() {

		when(buscarServiciosProcess.actualizarServicio("SER001", servicio)).thenReturn(Constantes.INFO1);

		// Act: ejecutamos la operación
		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.modificarServicio(servicio);

		// Assert: comprobamos el resultado
		assertEquals(Constantes.CODKO, respuesta.getCodigo());
		assertEquals(0, respuesta.getServicios().size());
		assertEquals(Constantes.INFO1, respuesta.getMensaje());

		verify(buscarServiciosProcess).actualizarServicio("SER001", servicio);

	}
	
	//ERROR AQUI CON MENSAJE DE CATEGORIA-SERVICIO
	@Test
	void debeNoModificarServicioCuandoCategoriaNoExiste() {

		when(buscarServiciosProcess.actualizarServicio("SER001", servicio)).thenReturn(Constantes.ERROR5INF);
		// Act: ejecutamos la operación
		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.modificarServicio(servicio);

		// Assert: comprobamos el resultado
		assertEquals(Constantes.CODKO, respuesta.getCodigo());
		assertEquals(0, respuesta.getServicios().size());
		assertEquals(Constantes.ERROR5INF, respuesta.getMensaje());
		verify(buscarServiciosProcess).actualizarServicio("SER001", servicio);

	}

	@Test
	void debeDevolverErrorCuandoServicioEsNulo() {

		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.modificarServicio(null);

		assertEquals(Constantes.CODKO, respuesta.getCodigo());
		assertEquals(Constantes.ERROR3INF, respuesta.getMensaje());
		assertEquals(0, respuesta.getServicios().size());

		verify(buscarServiciosProcess, never()).actualizarServicio(anyString(), any(ServicioDTO.class));
	}

	@Test
	void consultaServicioPorCodigo() {
		when(buscarServiciosProcess.buscarServiciosPorCodigo("SER001")).thenReturn(Optional.of(servicio));

		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.buscarServiciosPorCodigo(servicio.getCodigo());

		assertEquals(Constantes.CODOK, respuesta.getCodigo());
		assertEquals(1, respuesta.getServicios().size());
		assertEquals("Consulta actualizada", respuesta.getServicios().get(0).getNombre());

		verify(buscarServiciosProcess).buscarServiciosPorCodigo(servicio.getCodigo());
	}

	@Test
	void consultaServicioPorCodigoInexistente() {
		when(buscarServiciosProcess.buscarServiciosPorCodigo("SER999")).thenReturn(Optional.empty());

		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.buscarServiciosPorCodigo("SER999");

		assertEquals(Constantes.CODKO, respuesta.getCodigo());
		assertEquals(0, respuesta.getServicios().size());

		verify(buscarServiciosProcess).buscarServiciosPorCodigo("SER999");
	}

	@Test
	void desactivarServicio() {
		when(buscarServiciosProcess.desactivarServicio("SER001", false)).thenReturn(true);
		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.desactivarServicio(servicio, false);
		assertEquals(Constantes.CODOK, respuesta.getCodigo());
		assertEquals(1, respuesta.getServicios().size());
		assertEquals(Constantes.INFO4, respuesta.getMensaje());
		verify(buscarServiciosProcess).desactivarServicio("SER001", false);
	}

	@Test
	void desactivarServicioInexistente() {
		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.desactivarServicio(new ServicioDTO(), false);
		assertEquals(Constantes.CODKO, respuesta.getCodigo());
		assertEquals(0, respuesta.getServicios().size());
		assertEquals(Constantes.ERROR3INF, respuesta.getMensaje());
	}

	@Test
	void buscarServiciosPorCategoria() {
		when(buscarServiciosProcess.buscarServiciosPorCategoria("SALUD")).thenReturn(List.of(servicio));
		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.buscarServiciosPorCategoria(servicio.getCategoria());
		assertEquals(Constantes.CODOK, respuesta.getCodigo());
		assertEquals(1, respuesta.getServicios().size());
	}

	@Test
	void buscarServiciosPorCategoriaInexistente() {
		when(buscarServiciosProcess.buscarServiciosPorCategoria("SALUDO")).thenReturn(List.of());
		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.buscarServiciosPorCategoria("SALUDO");
		assertEquals(Constantes.CODOK, respuesta.getCodigo());
		assertEquals(0, respuesta.getServicios().size());
		assertEquals(Constantes.INFO1, respuesta.getMensaje());
		verify(buscarServiciosProcess).buscarServiciosPorCategoria("SALUDO");
	}

	@Test
	void eliminacionDeServicio() {
		when(buscarServiciosProcess.eliminarServicio("SER001")).thenReturn(true);
		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.eliminarServicio(servicio);
		assertEquals(Constantes.CODOK, respuesta.getCodigo());
		assertEquals(1, respuesta.getServicios().size());
		assertEquals(Constantes.INFO5, respuesta.getMensaje());
		verify(buscarServiciosProcess).eliminarServicio("SER001");
	}

	@Test
	void eliminacionDeServicioInexistente() {
		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.eliminarServicio(new ServicioDTO());
		assertEquals(Constantes.CODKO, respuesta.getCodigo());
		assertEquals(0, respuesta.getServicios().size());
		assertEquals(Constantes.ERROR3INF, respuesta.getMensaje());
	}

	@Test
	void crearServicio() {
		ServicioDTO servicio2 = new ServicioDTO("SER002", "Consulta", "SALUD");
		when(buscarServiciosProcess.existePorCodigo("SER002")).thenReturn(false);
		when(buscarServiciosProcess.guardarServicio(any(ServicioDTO.class))).thenReturn(Optional.of(servicio2));

		RespuestaCatalogoDTO respuesta = catalogoDataAccessAPI.crearServicio(servicio2);

		assertEquals(Constantes.CODOK, respuesta.getCodigo());
		assertEquals(1, respuesta.getServicios().size());
		assertEquals("SER002", respuesta.getServicios().get(0).getCodigo());

		verify(buscarServiciosProcess).existePorCodigo("SER002");
		verify(buscarServiciosProcess).guardarServicio(any());

	}
	
	/**
	 * CATEGORIAS
	 * 
	 */

	@Test
	void eliminarCategoriaQueNoExiste() {
		when (buscarServiciosProcess.eliminarCategoriaPorNombre("CATEGORIA FALSA")).thenReturn(Constantes.ERROR5INF);
		RespuestaCategoriaDTO respuesta = catalogoDataAccessAPI.eliminarCategoriaPorNombre("CATEGORIA FALSA");
		assertEquals(Constantes.CODKO, respuesta.getCodigo());
		assertEquals(0, respuesta.getCategorias().size());
		assertEquals(Constantes.ERROR5INF, respuesta.getMensaje());
		

		verify(buscarServiciosProcess).eliminarCategoriaPorNombre("CATEGORIA FALSA");


	}
	
	@Test
	void eliminarCategoriaConServiciosAsignados() {
		when (buscarServiciosProcess.eliminarCategoriaPorNombre("SALUD")).thenReturn(Constantes.INFO9);
		RespuestaCategoriaDTO respuesta = catalogoDataAccessAPI.eliminarCategoriaPorNombre("SALUD");
		assertEquals(Constantes.CODKO, respuesta.getCodigo());
		assertEquals(0, respuesta.getCategorias().size());
		assertEquals(Constantes.INFO9, respuesta.getMensaje());
		
		verify(buscarServiciosProcess).eliminarCategoriaPorNombre("SALUD");

	}
}