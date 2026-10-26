
package es.personal.catalogo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import es.personal.catalogo.entity.ServicioEntity;

@SpringBootTest
@Transactional
class ServicioRepositoryTest {

    @Autowired
    private ServicioRepository servicioRepository;

    @Test
    void debeDevolverSoloLosServiciosActivosDeSalud() {

        List<ServicioEntity> resultado =
                servicioRepository.findByCategoriaNombreAndActivoTrue("SALUD");

        assertEquals(3, resultado.size());

        assertTrue(resultado.stream()
                .allMatch(servicio -> servicio.isActivo()));

        assertTrue(resultado.stream()
                .allMatch(servicio ->
                        servicio.getCategoria().getNombre().equals("SALUD")));
    }
    

    @Test
    void debeDevolverSoloLosServiciosInactivos() {

        List<ServicioEntity> resultado =
                servicioRepository.findByActivoFalse();

        assertEquals(2, resultado.size());

        assertTrue(resultado.stream()
                .allMatch(servicio -> !servicio.isActivo()));
    }

    @Test
    void debeDevolverSoloUnServicioActivoDeDatos() {
        List<ServicioEntity> resultado =
                servicioRepository.findByCategoriaNombreAndActivoTrue("Pruebas");
        assertEquals(1, resultado.size());
        assertEquals("SER006", resultado.get(0).getCodigo());
    }
}
