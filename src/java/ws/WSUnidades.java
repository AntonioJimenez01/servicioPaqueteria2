package ws;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("unidades")
public class WSUnidades {
    
    @POST
    @Path("registrarUnidad")
    @Produces(MediaType.APPLICATION_JSON)
    public void registrarUnidad(){//marca,modelo,año,vin,numeroInterno,estado,idColaborador,idTipoUnidad
        
    }
    /*
    @PUT
    @Path("editarUnidad")
    @Produces(MediaType.APPLICATION_JSON)
    public void editarUnidad(){//""
        
    }
    
    @PUT
    @Path("darBajaUnidad")
    @Produces(MediaType.APPLICATION_JSON)
    public void darBajaUnidad(){//numeroInterno, motivo
        
    }
    
    @GET
    @Path("buscarUnidad")
    @Produces(MediaType.APPLICATION_JSON)
    public void buscarUnidad(){//vin, marca, numeroInterno
        
    }
    
    */
    
}
