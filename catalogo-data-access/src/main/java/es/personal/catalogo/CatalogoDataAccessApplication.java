package es.personal.catalogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import es.personal.catalogo.api.CatalogoDataAccessAPI;
import es.personal.catalogo.dto.CategoriaDTO;

@SpringBootApplication
public class CatalogoDataAccessApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(CatalogoDataAccessApplication.class, args);

		// SpringApplication.run(CatalogoDataAccessApplication.class, args);
		/*CatalogoDataAccessAPI api = contexto.getBean(CatalogoDataAccessAPI.class);
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setNombre("TRANSPORTE");
		categoria.setDescripcion("Servicios relacionados con el transporte");
		System.out.println(api.buscarCategoriaPorId(1L));
		System.out.println(api.crearCategoria(categoria).getCodigo() + categoria.getDescripcion());
		*/
		
	}
}
