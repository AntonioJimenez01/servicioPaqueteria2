package pojo;

import java.util.List;


public class ResultadoPaquete {
    private boolean error;
    private String mensaje;
    private Paquete paquete;
    private List<Paquete> paquetes;

    public ResultadoPaquete() {
    }

    public ResultadoPaquete(boolean error, String mensaje, Paquete paquete, List<Paquete> paquetes) {
        this.error = error;
        this.mensaje = mensaje;
        this.paquete = paquete;
        this.paquetes = paquetes;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public List<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }
    
    
}