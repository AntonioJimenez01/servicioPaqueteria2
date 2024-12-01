//sajhbsaahsbhhj
package pojo;

public class Mensaje {
    
    private Boolean error;
    private String mensaje;
    private Colaborador colaborador;
    private boolean bandera;

    public Mensaje() {
    }

    public Mensaje(Boolean error, String mensaje, Colaborador colaborador, boolean bandera) {
        this.error = error;
        this.mensaje = mensaje;
        this.colaborador = colaborador;
        this.bandera = bandera;
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

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    

        
    
}
