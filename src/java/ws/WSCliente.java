package ws;

import com.google.gson.Gson;
import dominio.ImpCliente;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import pojo.Cliente;
import pojo.Direccion;
import pojo.Mensaje;

@Path("cliente")
public class WSCliente {

    @Context
    private UriInfo context;

    public WSCliente() {
    }

    @GET
    @Path("obtenerCliente")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> obtenerCliente() {
        return ImpCliente.obtenerTodosLosClientes();
    }

    @Path("registroCliente")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarCliente(String jsonCliente) {
        Gson gson = new Gson();
        Mensaje mensaje = new Mensaje();
        try {
            Cliente cliente = gson.fromJson(jsonCliente, Cliente.class);
            Direccion direccion = cliente.getDireccion();
            mensaje = ImpCliente.registrarCliente(cliente, direccion);
        } catch (Exception e) {
            mensaje.setError(false);
            mensaje.setMensaje("Error al procesar la solicitud: " + e.getMessage());
        }
        return mensaje;
    }

    @Path("modificarCliente")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarCliente(String jsonCliente) {
        Gson gson = new Gson();
        Mensaje mensaje = new Mensaje();
        try {
            Cliente cliente = gson.fromJson(jsonCliente, Cliente.class);
            List<Direccion> direcciones = cliente.getDirecciones();
            mensaje = ImpCliente.actualizarCliente(cliente, direcciones);
        } catch (Exception e) {
            mensaje.setError(true);
            mensaje.setMensaje("Error al procesar la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
        return mensaje;
    }

    @Path("eliminarCliente")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarColaborador(Cliente cliente) {
        Mensaje mensaje = null;
        if (cliente.getIdCliente() != null) {
            mensaje = ImpCliente.eliminarCliente(cliente.getIdCliente());
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return mensaje;
    }
}
