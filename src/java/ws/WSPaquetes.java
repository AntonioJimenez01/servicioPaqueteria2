
package ws;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dominio.ImpColaborador;
import dominio.ImpPaquete;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import pojo.Mensaje;
import pojo.Paquete;
import pojo.ResultadoPaquete;



@Path("paquetes")
public class WSPaquetes {
    @Context
    private UriInfo context; // cuando necesito info de la peticion, no afecta si se quita
    
    @GET
    @Path("obtenerPaquetes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResultadoPaquete> obtenerPaquetes(){
        return ImpPaquete.obtenerPaquetes();
    }
    
    @GET
    @Path("obtenerPaquete/{idEnvio}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultadoPaquete obtenerPaquetePorEnvio(@PathParam("idEnvio") Integer idEnvio) {
           return ImpPaquete.obtenerPaquetePorEnvio(idEnvio);
    }
    
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarPaquete(String jsonPaquete) {
        try {
            Gson gson = new Gson(); 
            Paquete paquete = gson.fromJson(jsonPaquete, Paquete.class); // Crear objeto a partir del JSON

            // Validaciones
            if (paquete != null 
                && paquete.getDescripcion()!= null && !paquete.getDescripcion().isEmpty()
                && paquete.getPeso()!= null && paquete.getPeso() > 0 // Peso no debe ser nulo y debe ser mayor a 0
                && paquete.getDimensionAlto() != null && paquete.getDimensionAlto() > 0 
                && paquete.getDimensionAncho()!= null && paquete.getDimensionAncho() > 0 
                && paquete.getDimensionProfundidad()!= null && paquete.getDimensionProfundidad() > 0 
                && paquete.getIdEnvio()!= null) { 
                
                return ImpPaquete.registrarPaquete(paquete); // Llama al método de registro en tu DAO/Service
            } else {
                return new Mensaje(true, "Datos del paquete faltantes o incorrectos.");
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return new Mensaje(true, "Formato JSON inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException();
        }
    }
    
    @PUT
    @Path("modificar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarPaquete(String jsonPaquete){
       try{
            Gson gson = new Gson(); //
            Paquete paquete = gson.fromJson(jsonPaquete, Paquete.class); //crea un objeto a aprit de lo que json
            //validaciones
                if(paquete.getIdPaquete()!= null){ //password  no venga nulo y vacio
                return ImpPaquete.modificarPaquete(paquete);
            }else{
                return new Mensaje(true, "Para poder editar se necesita el identificador");
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new BadRequestException();
        }
    }
    
    
    @DELETE
    @Path("eliminar")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarPaquete(@FormParam("idPaquete") Integer idPaquete) {
        try {
        // Validación
            if (idPaquete != null) { // Verifica que el idColaborador no sea nulo
              return ImpPaquete.eliminarPaquete(idPaquete);
            } else {
                return new Mensaje(true, "Para poder eliminar se necesita un identificador.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException();
        }
    }
    
}