
package es.personal.catalogo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicio")
public class ServicioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEntity categoria;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    public ServicioEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
