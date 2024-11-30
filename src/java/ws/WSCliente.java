package ws;

import com.google.gson.Gson;
import dominio.ImpCliente;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
        try {
            Gson gson = new Gson();
            Cliente cliente = gson.fromJson(jsonCliente, Cliente.class);
            if (cliente.getCorreo() != null && !cliente.getCorreo().isEmpty()
                && cliente.getCorreo().matches("^[A-Za-z0-9+_.-]+@(.+)$")
                && cliente.getTelefono() != null && cliente.getTelefono().matches("^\\d{10}$")
                && cliente.getNombre() != null && !cliente.getNombre().isEmpty()
                && cliente.getNombre().length() <= 50) {
                return ImpCliente.registrarCliente(cliente);
            } else {
                return new Mensaje(true, "Correo, teléfono, nombre o contraseña faltantes o inválidos");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException();
        }
    }


    @POST
    @Path("modificarClientes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje modificarCliente(String jsonCliente) {
        Gson gson = new Gson();
        Cliente cliente = gson.fromJson(jsonCliente, Cliente.class);
        if (cliente != null && cliente.getIdCliente() != null) {
            return ImpCliente.editarCliente(cliente);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @Path("eliminarCliente")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje eliminarCliente(Cliente cliente) {
        Mensaje mensaje = null;
        if (cliente.getIdCliente() != null) {
            mensaje = ImpCliente.eliminarCliente(cliente.getIdCliente());
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return mensaje;
    }

    @GET
    @Path("buscarCliente")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> buscarCliente(
        @QueryParam("nombre") String nombre,
        @QueryParam("telefono") String telefono,
        @QueryParam("correo") String correo){

        return ImpCliente.buscarCliente(nombre, telefono, correo);
    }
}
