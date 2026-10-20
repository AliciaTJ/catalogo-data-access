
package es.personal.catalogo.dto;

import java.util.List;

public class RespuestaCatalogoDTO {

    private String codigo;
    private String mensaje;
    private List<ServicioDTO> servicios;

    public RespuestaCatalogoDTO() {
    }

    public RespuestaCatalogoDTO(
            String codigo,
            String mensaje,
            List<ServicioDTO> servicios) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.servicios = servicios;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<ServicioDTO> getServicios() {
        return servicios;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setServicios(List<ServicioDTO> servicios) {
        this.servicios = servicios;
    }
}
