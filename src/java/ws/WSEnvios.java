package ws;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dominio.ImpEnvio;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
import pojo.ResultadoObtenerEnvio;

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
    
    
    @PUT
    @Path("actualizarEnvio") //posible cambio de nombre, actualizar -> modificar
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje actualizarEnvio(String json){
         Mensaje respuesta = new Mensaje();
        try {
            Gson gson = new Gson();
            Envio envio = gson.fromJson(json, Envio.class);
            
            if (envio.getIdColaborador() == null || envio.getIdCliente() == null ||
                envio.getDireccionOrigen() == null || envio.getDireccionDestino() == null) {
                
                respuesta.setError(true);
                respuesta.setMensaje("Campos obligatorios faltantes o inválidos.");
                return respuesta;
            }else{
                respuesta = ImpEnvio.actualizarEnvio(envio);
            }
        } catch (Exception e) {
            e.printStackTrace();
           
            respuesta.setError(true);
            respuesta.setMensaje("Error interno del servidor: " + e.getMessage());
            return respuesta;
        }
        
        return respuesta;
    }
    
    @GET
    @Path("consultarEnvio")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResultadoObtenerEnvio consultarEnvio(String json) {
        ResultadoObtenerEnvio respuesta = new ResultadoObtenerEnvio();
        Gson gson = new Gson();

        try {
            // Deserializa el JSON para obtener los datos necesarios
            Map<String, Object> parametros = gson.fromJson(json, Map.class);

            // Extrae el número de guía del JSON
            String numeroGuia = (String) parametros.get("numeroGuia");

            if (numeroGuia != null && !numeroGuia.isEmpty()) {
                // Llama a tu capa de implementación con el número de guía
                respuesta = ImpEnvio.consultarEnvio(numeroGuia);
            } else {
                respuesta.setError(true);
                respuesta.setMensaje("El número de guía no fue proporcionado o está vacío.");
            }
        } catch (JsonSyntaxException e) {
            respuesta.setError(true);
            respuesta.setMensaje("Error al procesar el JSON: " + e.getMessage());
        }

        return respuesta;
    }
    
    @GET
    @Path("obtenerEnvios")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ResultadoObtenerEnvio> obtenerEnvios() {
        List<ResultadoObtenerEnvio> respuesta = new ArrayList<>();
        
            respuesta = ImpEnvio.obtenerEnvios();

        return respuesta;
    }
    

    
    /*
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
