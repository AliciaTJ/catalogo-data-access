
package es.personal.catalogo.finder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.personal.catalogo.dto.ServicioDTO;
import es.personal.catalogo.entity.ServicioEntity;
import es.personal.catalogo.repository.ServicioRepository;

@ExtendWith(MockitoExtension.class)
class ServicioFinderTest {

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ServicioFinder servicioFinder;

    @Test
    void debeTransformarEntidadesEnDTO() {

        ServicioEntity entidad = new ServicioEntity();
        entidad.setId(1L);
        entidad.setCodigo("SER001");
        entidad.setNombre("Consulta medica");
        entidad.setCategoria("SALUD");
        entidad.setActivo(true);

        when(servicioRepository.findByCategoriaAndActivoTrue("SALUD"))
                .thenReturn(List.of(entidad));

        List<ServicioDTO> resultado =
                servicioFinder.buscarServiciosActivosPorCategoria("SALUD");

        assertEquals(1, resultado.size());
        assertEquals("SER001", resultado.get(0).getCodigo());
        assertEquals("Consulta medica", resultado.get(0).getNombre());
        assertEquals("SALUD", resultado.get(0).getCategoria());

        verify(servicioRepository)
                .findByCategoriaAndActivoTrue("SALUD");
    }

    @Test
    void debeDevolverListaVaciaCuandoNoHayResultados() {

        when(servicioRepository.findByCategoriaAndActivoTrue("CULTURA"))
                .thenReturn(List.of());

        List<ServicioDTO> resultado =
                servicioFinder.buscarServiciosActivosPorCategoria("CULTURA");

        assertEquals(0, resultado.size());
    }
}
