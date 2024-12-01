package pojo;

public class Paquete {
    
    private int idPaquete;
    private String descripción;
    private float peso;
    private float dimensionAlto;
    private float dimensionAncho;
    private float dimensionProfundidad;
    private int idEnvio;

    public Paquete() {
    }

    public Paquete(int idPaquete, String descripción, float peso, float dimensionAlto, float dimensionAncho, float dimensionProfundidad, int idEnvio) {
        this.idPaquete = idPaquete;
        this.descripción = descripción;
        this.peso = peso;
        this.dimensionAlto = dimensionAlto;
        this.dimensionAncho = dimensionAncho;
        this.dimensionProfundidad = dimensionProfundidad;
        this.idEnvio = idEnvio;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getDimensionAlto() {
        return dimensionAlto;
    }

    public void setDimensionAlto(float dimensionAlto) {
        this.dimensionAlto = dimensionAlto;
    }

    public float getDimensionAncho() {
        return dimensionAncho;
    }

    public void setDimensionAncho(float dimensionAncho) {
        this.dimensionAncho = dimensionAncho;
    }

    public float getDimensionProfundidad() {
        return dimensionProfundidad;
    }

    public void setDimensionProfundidad(float dimensionProfundidad) {
        this.dimensionProfundidad = dimensionProfundidad;
    }

    public int getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }
    
    
}
