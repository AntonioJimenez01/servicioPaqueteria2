package ws;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dominio.ImpCliente;
import dominio.ImpColaborador;
import dominio.ImpConductor;
import dominio.ImpEnvio;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pojo.Cliente;
import pojo.Colaborador;
import pojo.Mensaje;
import pojo.RespuestaEnvio;
import pojo.Unidad;



@Path("conductores")
public class WSConductores {
        
    
    @PUT
@Path("asignarUnidad/{noPersonal}/{numeroInterno}")
@Produces(MediaType.APPLICATION_JSON)
public Mensaje asignarUnidad(
        @PathParam("noPersonal") String noPersonal,
        @PathParam("numeroInterno") String numeroInterno) { 
        
    Mensaje respuesta = new Mensaje();
    if (noPersonal != null && numeroInterno != null) {
        return ImpConductor.asignarUnidad(noPersonal, numeroInterno);            
    } else {
        respuesta.setError(true);
        respuesta.setMensaje("Los campos están vacíos");
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
    @Path("unidadAsociada/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Unidad obtenerUnidadColaborador(@PathParam("idColaborador") Integer idColaborador){//noPersonal, numeroInterno
        Unidad colaborador = null;    
        colaborador = ImpConductor.obtenerUnidadColaborador(idColaborador);
         return colaborador;
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
    
    @PUT
    @Path("actualizarSeguimientoEnvio")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje actualizarEstatusEnvio(String json) { // noPersonal, numeroGuia
        Mensaje mensaje = new Mensaje();
        Gson gson = new Gson();

        try {
            // Deserializa el JSON a un Map
            Map<String, Object> parametros = gson.fromJson(json, Map.class);

            // Obtén los parámetros
            String noPersonal = (String) parametros.get("idColaborador");
            String idSeguimiento = (String) parametros.get("idSeguimiento");
            String nombre = (String) parametros.get("nombre");
            String fecha = (String) parametros.get("fecha");
            String hora = (String) parametros.get("hora");

            return ImpConductor.actualizarEstatusEnvio(parametros);
            
        } catch (JsonSyntaxException e) {
            mensaje.setError(true);
            mensaje.setMensaje("Error en el formato del JSON: " + e.getMessage());
        }

        return mensaje;
    }

    
    @PUT
    @Path("actualizarDatosPerfil")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje actualizarDatos(String json){//noPersonal etc...
        Gson gson = new Gson();
        Colaborador colaborador = gson.fromJson(json, Colaborador.class);
        if (colaborador != null && colaborador.getIdColaborador()!= null) {
            return ImpColaborador.editarColaborador(colaborador);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
}