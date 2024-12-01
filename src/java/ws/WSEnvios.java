package ws;

import com.google.gson.Gson;
import dominio.ImpEnvio;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.Colaborador;
import pojo.Envio;
import pojo.Mensaje;
import pojo.RespuestaEnvio;

@Path("envios")
public class WSEnvios {
    
    
    @GET
    @Path("saludar")
    @Produces(MediaType.APPLICATION_JSON)
    public int saludar() {
               
       return 0;
        
    }
    
    @POST
    @Path("crearEnvio")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje crearEnvio(String jsonEnvio){
        Mensaje respuesta = new Mensaje();
        try {
            Gson gson = new Gson();
            Envio envio = gson.fromJson(jsonEnvio, Envio.class);
            if (envio.getIdColaborador() == null || envio.getIdCliente() == null ||
                envio.getDireccionOrigen() == null || envio.getDireccionDestino() == null) {
                
                respuesta.setError(true);
                respuesta.setMensaje("Campos obligatorios faltantes o inválidos.");
                return respuesta;
            }else{
                respuesta = ImpEnvio.registrarEnvio(envio);
            }
        } catch (Exception e) {
            e.printStackTrace();
           
            respuesta.setError(true);
            respuesta.setMensaje("Error interno del servidor: " + e.getMessage());
            return respuesta;
        }
        
        return respuesta;
    }
    
    /*
    @PUT
    @Path("actualizarEnvio") //posible cambio de nombre, actualizar -> modificar
    @Produces(MediaType.APPLICATION_JSON)
    public void actualizarEnvio(){
        
    }
    
    @GET
    @Path("consultarEnvio")
    @Produces(MediaType.APPLICATION_JSON)
    public void consultarEnvio(){
        
    }
    
    @PUT
    @Path("asignarConductorEnvio")
    @Produces(MediaType.APPLICATION_JSON)
    public void asignarConductorEnvio(){
        
    }
    
    @PUT
    @Path("asignarSeguimiento")
    @Produces(MediaType.APPLICATION_JSON) 
    public void asignarSeguimiento(){
        
    }*/
}
