package es.personal.catalogo.dto;

public class ServicioDTO {

	private String nombre;
	private String codigo;
	private String categoria;
	
	public ServicioDTO() {};
	
	public ServicioDTO (String codigo,String nombre, String categoria) {
		this.nombre=nombre;
		this.codigo=codigo;
		this.categoria=categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
		
}
