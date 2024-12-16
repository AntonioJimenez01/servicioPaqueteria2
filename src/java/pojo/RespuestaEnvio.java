package pojo;

import java.util.List;


public class RespuestaEnvio {
    private Boolean error;
    private String mensaje;
    private Envio envio;
    private List<Envio> envios;

    public RespuestaEnvio() {
    }

    public RespuestaEnvio(Boolean error, String mensaje, Envio envio, List<Envio> envios) {
        this.error = error;
        this.mensaje = mensaje;
        this.envio = envio;
        this.envios = envios;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(List<Envio> envios) {
        this.envios = envios;
    }
    
    

}
