
package es.personal.catalogo.finder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.personal.catalogo.dto.ServicioDTO;
import es.personal.catalogo.entity.CategoriaEntity;
import es.personal.catalogo.entity.ServicioEntity;
import es.personal.catalogo.repository.CategoriaRepository;
import es.personal.catalogo.repository.ServicioRepository;
import es.personal.catalogo.utils.Constantes;

@ExtendWith(MockitoExtension.class)
class ServicioFinderTestMock {

	@Mock
	private ServicioRepository servicioRepository;

	@Mock
	private CategoriaRepository categoriaRepository;

	@InjectMocks
	private ServicioFinder servicioFinder;

	private ServicioEntity entidad;
	private CategoriaEntity categoria;
	private ServicioDTO servicio;
	private ServicioDTO servicio2;

	@BeforeEach
	void setUp() {

		categoria = new CategoriaEntity();
		categoria.setId(2L);
		categoria.setNombre("SALUD");
		categoria.setDescripcion("Servicios relacionados con la salud");
		entidad = new ServicioEntity();
		entidad.setId(1L);
		entidad.setCodigo("SER001");
		entidad.setNombre("Consulta medica");
		entidad.setCategoria(categoria);
		entidad.setActivo(true);
		servicio = new ServicioDTO("SER006", "Fisioterapia", "SALUD");
		servicio2 = new ServicioDTO("SER001", "Consulta Medica", "SALUD");
	}

	@Test
	void modificarCuandoServicioInexistente() {
	    when(servicioRepository.findByCodigo("SER001")).thenReturn(Optional.empty());
	
		String resultado= servicioFinder.actualizarServicio("SER001", servicio2);
		assertEquals(resultado, Constantes.INFO1);
		verify(servicioRepository, never()).findByCategoriaNombre("SALUD");
		verify(servicioRepository, never()).save(any(ServicioEntity.class));
	}
		
	@Test
	void modificarCuandoCategoriaInexistente() {
	    when(servicioRepository.findByCodigo("SER001")).thenReturn(Optional.of(entidad));
	    when (categoriaRepository.findByNombre("SALUD")).thenReturn(Optional.empty());
		String  resultado= servicioFinder.actualizarServicio("SER001", servicio2);
		assertEquals(resultado, Constantes.ERROR5INF);
		verify(categoriaRepository).findByNombre("SALUD");
		verify(servicioRepository, never()).save(any(ServicioEntity.class));
		
	}
	@Test
	void modificaCorrectamenteElServicio() {
		when(categoriaRepository.findByNombre(categoria.getNombre())).thenReturn(Optional.of(categoria));
	    when(servicioRepository.findByCodigo("SER001")).thenReturn(Optional.of(entidad));
		when(servicioRepository.save(any(ServicioEntity.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
		
		String  resultado= servicioFinder.actualizarServicio("SER001", servicio2);
		assertEquals(resultado, Constantes.INFO12);
	
	}
	@Test
	void debeDevolverVacioCuandoNoExisteLaCategoria() {
		when(categoriaRepository.findByNombre(categoria.getNombre())).thenReturn(Optional.empty());
		Optional<ServicioDTO> resultado = servicioFinder.guardarServicio(servicio);
		assertTrue(resultado.isEmpty());
		verify(categoriaRepository).findByNombre("SALUD");
		verify(servicioRepository, never()).save(any(ServicioEntity.class));
	}
	
	
	@Test
	void debeGuardarServiciosCuandoExisteLaCategoria() {
		when(categoriaRepository.findByNombre(categoria.getNombre())).thenReturn(Optional.of(categoria));
		when(servicioRepository.save(any(ServicioEntity.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

		Optional<ServicioDTO> resultado = servicioFinder.guardarServicio(servicio);
		assertTrue(resultado.isPresent());
		assertEquals("SER006", resultado.get().getCodigo());
		assertEquals("Fisioterapia", resultado.get().getNombre());
		assertEquals("SALUD", resultado.get().getCategoria());

		verify(categoriaRepository).findByNombre("SALUD");
		verify(servicioRepository).save(any(ServicioEntity.class));
	}

	@Test
	void debeTransformarEntidadesEnDTO() {

		when(servicioRepository.findByCategoriaNombreAndActivoTrue("SALUD")).thenReturn(List.of(entidad));

		List<ServicioDTO> resultado = servicioFinder.buscarServiciosActivosPorCategoria("SALUD");

		assertEquals(1, resultado.size());
		assertEquals("SER001", resultado.get(0).getCodigo());
		assertEquals("Consulta medica", resultado.get(0).getNombre());
		assertEquals("SALUD", resultado.get(0).getCategoria());

		verify(servicioRepository).findByCategoriaNombreAndActivoTrue("SALUD");
	}

	@Test
	void debeDevolverListaVaciaCuandoNoHayResultados() {

		when(servicioRepository.findByCategoriaNombreAndActivoTrue("CULTURA")).thenReturn(List.of());

		List<ServicioDTO> resultado = servicioFinder.buscarServiciosActivosPorCategoria("CULTURA");

		assertEquals(0, resultado.size());
	}
}
