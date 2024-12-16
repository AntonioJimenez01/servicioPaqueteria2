package ws;
import com.google.gson.Gson;
import dominio.ImpCliente;
import dominio.ImpUnidad;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pojo.Cliente;
import pojo.Mensaje;
import pojo.RespuestaUnidad;
import pojo.Unidad;


@Path("unidades")
public class WSUnidades {
    
    @POST
    @Path("registrarUnidad")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaUnidad registrarUnidad(String json){//marca,modelo,año,vin,numeroInterno,estado,idColaborador,idTipoUnidad
        Gson gson = new Gson();
        Unidad unidad = gson.fromJson(json, Unidad.class);
        if(unidad !=null){
            return ImpUnidad.registrarUnidad(unidad);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
            
        } 
    }
    
    @PUT
    @Path("modificarUnidad")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaUnidad editarUnidad(String json){//""
        Gson gson = new Gson();
        Unidad unidad = gson.fromJson(json, Unidad.class);
        if(unidad !=null){
            return ImpUnidad.modificarUnidad(unidad);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
            
        } 
    }      
    
    @GET
    @Path("buscarUnidad")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaUnidad buscarUnidad(
            @QueryParam("vin") String vin, 
            @QueryParam("marca") String marca, 
            @QueryParam("numeroInterno") String numeroInterno){//vin, marca, numeroInterno
        return ImpUnidad.buscarUnidad(vin, marca, numeroInterno);
    }
    
    @GET
    @Path("obtenerUnidades")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaUnidad obtenerUnidades(){//vin, marca, numeroInterno
        return ImpUnidad.obtenerUnidades();
    }
    
    
    @PUT
    @Path("darBajaUnidad")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje darBajaUnidad(String json) { // Recibes un objeto Unidad con los datos necesarios
        Gson gson = new Gson();
        Unidad unidad = gson.fromJson(json, Unidad.class);
        if(unidad !=null){
            return ImpUnidad.darBajaUnidad(unidad);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
            
        } 
    }

}
