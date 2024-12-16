package pojo;

public class Paquete {
    
    private Integer idPaquete;
    private String descripcion;
    private Float peso;
    private Float dimensionAlto;
    private Float dimensionAncho;
    private Float dimensionProfundidad;
    private Integer idEnvio;

    public Paquete() {
    }

    public Paquete(Integer idPaquete, String descripcion, Float peso, Float dimensionAlto, Float dimensionAncho, Float dimensionProfundidad, Integer idEnvio) {
        this.idPaquete = idPaquete;
        this.descripcion = descripcion;
        this.peso = peso;
        this.dimensionAlto = dimensionAlto;
        this.dimensionAncho = dimensionAncho;
        this.dimensionProfundidad = dimensionProfundidad;
        this.idEnvio = idEnvio;
    }

    public Integer getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getDimensionAlto() {
        return dimensionAlto;
    }

    public void setDimensionAlto(Float dimensionAlto) {
        this.dimensionAlto = dimensionAlto;
    }

    public Float getDimensionAncho() {
        return dimensionAncho;
    }

    public void setDimensionAncho(Float dimensionAncho) {
        this.dimensionAncho = dimensionAncho;
    }

    public Float getDimensionProfundidad() {
        return dimensionProfundidad;
    }

    public void setDimensionProfundidad(Float dimensionProfundidad) {
        this.dimensionProfundidad = dimensionProfundidad;
    }

    public Integer getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Integer idEnvio) {
        this.idEnvio = idEnvio;
    }
    
    
}