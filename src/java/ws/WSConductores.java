package ws;

import dominio.ImpConductor;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pojo.Mensaje;
import pojo.RespuestaEnvio;



@Path("conductores")
public class WSConductores {
        
    
    @PUT
    @Path("asignarUnidad/{noPersonal}/{numeroInterno}")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje asignarUnidad(
            @PathParam("noPersonal") Integer noPersonal,
            @PathParam("numeroInterno") String numeroInterno){ //noPersonal, numeroInterno
        
        Mensaje respuesta = new Mensaje();
        if (noPersonal != null && numeroInterno != null) {
            return ImpConductor.asignarUnidad(noPersonal, numeroInterno);            
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("Los campos están vacios");
           
        }
         return respuesta;
    }
    
    @PUT
    @Path("desasignarUnidad/{noPersonal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje desasignarUnidad(
            @PathParam("noPersonal") Integer noPersonal){ //noPersonal, numeroInterno
        
        Mensaje respuesta = new Mensaje();
        if (noPersonal != null) {
            return ImpConductor.desasignarUnidad(noPersonal);            
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("Los campos están vacios");
           
        }
         return respuesta;//noPersonal, numeroInterno        
    }
    
    @GET
    @Path("cambiarUnidad/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje obtenerUnidadColaborador(@PathParam("idColaborador") Integer idColaborador){//noPersonal, numeroInterno
       
        Mensaje respuesta = new Mensaje();
        if (idColaborador != null) {
            respuesta = ImpConductor.obtenerUnidadColaborador(idColaborador);
        }
                
         return respuesta;
    }
    
    
    @GET
    @Path("verListaEnvios/{noPersonal}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEnvio verListaEnvios(@PathParam("noPersonal") String noPersonal){//noPersonal
        RespuestaEnvio respuesta = new RespuestaEnvio();
        
        if(noPersonal !=null){
            respuesta = ImpConductor.verListaEnvios(noPersonal);
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("Se requiere el numero del personal");
        }
        return respuesta;
    }
    
    @GET
    @Path("verDetalleEnvio/{noPersonal}/{numeroGuia}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEnvio verDetalleEnvio(@PathParam("noPersonal") String noPersonal, @PathParam("numeroGuia") Integer numeroGuia){//noPersonal, numeroGuia
        RespuestaEnvio respuesta = new RespuestaEnvio();
        
        if(noPersonal !=null && numeroGuia !=null){
            respuesta = ImpConductor.verDetalleEnvio(noPersonal, numeroGuia);
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("Se requiere el numero del personal y el numero de guía");
        }
        return respuesta;
    }
    /*
    @PUT
    @Path("actualizarSeguimientoEnvio")
    @Produces(MediaType.APPLICATION_JSON)
    public void actualizarEstatusEnvio(){//noPersonal, numeroGuia
        
    }
    
    
    @PUT
    @Path("actualizarDatosPerfil")
    @Produces(MediaType.APPLICATION_JSON)
    public void actualizarDatos(){//noPersonal etc...
        
    }*/
}
