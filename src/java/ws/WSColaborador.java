package ws;

import com.google.gson.Gson;
import dominio.ImpColaborador;
import java.util.HashMap;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Mensaje;

@Path("colaborador") 
public class WSColaborador {

    @Context
    private UriInfo context;

    public WSColaborador() {
    }

    @GET
    @Path("obtenerColaboradores") 
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> obtenerColaboradores() {
        return ImpColaborador.obtenerTodosLosColaboradores();
    }

    @GET
    @Path("obtenerColaborador/{noPersonal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador obtenerColaboradorPorNoPersonal(@PathParam("noPersonal") String noPersonal) {
        if (noPersonal != null && !noPersonal.isEmpty() && noPersonal.length() <= 10) {
            return ImpColaborador.obtenerColaboradorPorNoPersonal(noPersonal);
        }
        throw new BadRequestException("NoPersonal inválido o vacío.");
    }

    @Path("registroColaboradores")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarColaborador(String jsonColaborador) {
        try {
            Gson gson = new Gson();
            Colaborador colaborador = gson.fromJson(jsonColaborador, Colaborador.class);
            if (colaborador.getNumeroPersonal() != null && !colaborador.getNumeroPersonal().isEmpty()
                && colaborador.getContraseña() != null && !colaborador.getContraseña().isEmpty()) {
                return ImpColaborador.registrarColaborador(colaborador);
            } else {
                return new Mensaje(true, "Número de personal y/o password faltantes o incorrectos");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException();
        }
    }

    @POST
    @Path("modificarColaboradores")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarColaborador(String jsonColaborador){
        Gson gson = new Gson();
        Colaborador colaborador = gson.fromJson(jsonColaborador, Colaborador.class);
        if(colaborador !=null && colaborador.getIdColaborador()!=null){
            return ImpColaborador.editarColaborador(colaborador);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
            
        } 
    }

    @Path("eliminarColaborador")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje eliminarColaborador(Colaborador colaborador) {
        Mensaje mensaje = null;
        if(colaborador.getIdColaborador()!=null){
            mensaje = ImpColaborador.eliminarColaborador(colaborador.getIdColaborador());
        }else{
             throw new WebApplicationException(Response.Status.BAD_REQUEST);           
        }
        
        return mensaje;
    }
    
    @GET
    @Path("buscarColaborador")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> buscarColaborador(
        @QueryParam("nombre") String nombre,
        @QueryParam("numeroPersonal") String numeroPersonal,
        @QueryParam("rol") String rol) {

    return ImpColaborador.buscarColaborador(nombre, numeroPersonal, rol);
}

}

