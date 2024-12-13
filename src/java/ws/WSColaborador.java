package ws;

import com.google.gson.Gson;
import dominio.ImpColaborador;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import pojo.Colaborador;
import pojo.Mensaje;
import pojo.Rol;

@Path("colaborador")
public class WSColaborador {

    @Context
    private UriInfo context;

    public WSColaborador() {}

    @GET
    @Path("obtenerColaboradores")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> obtenerColaboradores() {
        return ImpColaborador.obtenerTodosLosColaboradores();
    }

    @Path("registroColaboradores")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarColaborador(String jsonColaborador) {
        try {
            Gson gson = new Gson();
            Colaborador colaborador = gson.fromJson(jsonColaborador, Colaborador.class);

            if (colaborador.getNumeroPersonal() == null || colaborador.getNumeroPersonal().isEmpty() ||
                colaborador.getContraseña() == null || colaborador.getContraseña().isEmpty()) {
                return new Mensaje(true, "Número de personal y/o contraseña faltantes o incorrectos");
            }

            String curpRegex = "^[A-Z]{4}\\d{6}[HM]{1}[A-Z]{5}[0-9A-Z]{1}\\d{1}$";
            if (colaborador.getCurp() == null || !colaborador.getCurp().matches(curpRegex)) {
                return new Mensaje(true, "CURP incorrecta: debe seguir la nomenclatura oficial y contener 18 caracteres");
            }

            String correoRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
            if (colaborador.getCorreo() == null || !colaborador.getCorreo().matches(correoRegex)) {
                return new Mensaje(true, "Correo electrónico inválido: por favor ingresa un correo válido");
            }

            return ImpColaborador.registrarColaborador(colaborador);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException();
        }
    }

    @PUT
    @Path("modificarColaboradores")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarColaborador(String jsonColaborador) {
        Gson gson = new Gson();
        Colaborador colaborador = gson.fromJson(jsonColaborador, Colaborador.class);
        if (colaborador != null && colaborador.getIdColaborador() != null) {
            return ImpColaborador.editarColaborador(colaborador);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @Path("eliminarColaborador")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje eliminarColaborador(Colaborador colaborador) {
        if (colaborador.getIdColaborador() != null) {
            return ImpColaborador.eliminarColaborador(colaborador.getIdColaborador());
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @Path("obtenerRoles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rol> obtenerRoles() {
        return ImpColaborador.obtenerRoles();
    }
}
