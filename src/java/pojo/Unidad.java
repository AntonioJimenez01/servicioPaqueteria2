package pojo;

public class Unidad {
    
    private int idUnidad;
    private String marca;
    private String modelo;
    private int año;
    private String vin;
    private String numeroInterno;
    private int estado;
    private String motivo;
    private int idColaborador;
    private int idTipoUnidad;

    public Unidad() {
    }

    public Unidad(int idUnidad, String marca, String modelo, int año, String vin, String numeroInterno, int estado, String motivo, int idColaborador, int idTipoUnidad) {
        this.idUnidad = idUnidad;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.vin = vin;
        this.numeroInterno = numeroInterno;
        this.estado = estado;
        this.motivo = motivo;
        this.idColaborador = idColaborador;
        this.idTipoUnidad = idTipoUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public int getIdTipoUnidad() {
        return idTipoUnidad;
    }

    public void setIdTipoUnidad(int idTipoUnidad) {
        this.idTipoUnidad = idTipoUnidad;
    }
    
    
}
