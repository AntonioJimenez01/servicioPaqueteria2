package pojo;


public class Estado {
    
    private int idEstado;
    private String nombre; //posible cambio en el nombre de la variable
    private String fecha;
    private String hora;
    private int idColaborador;

    public Estado() {
    }

    public Estado(int idEstado, String nombre, String fecha, String hora, int idColaborador) {
        this.idEstado = idEstado;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.idColaborador = idColaborador;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }
    
    
}
