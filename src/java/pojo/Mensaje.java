package pojo;


public class Mensaje {
    private boolean error;
    private String mensaje;
    private boolean bandera;

    public Mensaje() {
    }

    public Mensaje(boolean error, String mensaje, boolean bandera) {
        this.error = error;
        this.mensaje = mensaje;
        this.bandera = bandera;
    }

    public Mensaje(boolean b, String datos_del_paquete_faltantes_o_incorrectos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    
    
}
