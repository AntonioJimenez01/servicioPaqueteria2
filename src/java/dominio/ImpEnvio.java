package dominio;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Direccion;
import pojo.Envio;
import pojo.Mensaje;
import pojo.RespuestaEnvio;

public class ImpEnvio {

    public static Mensaje registrarEnvio(Envio envio) throws Exception {
        boolean bandera = false;
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try{
                
                conexionBD.getConnection().setAutoCommit(false);
                
                HashMap<String, Object> parametrosEnvio = new HashMap<>();
                //parametrosEnvio.put("costoEnvio", envio.getCostoEnvio());
                parametrosEnvio.put("idColaborador", envio.getIdColaborador());
                parametrosEnvio.put("idCliente", envio.getIdCliente());                
                parametrosEnvio.put("numeroGuia", generarNumeroGuia(conexionBD));
                
                int filasAfectadas = conexionBD.insert("envio.registrarEnvio", parametrosEnvio);                                   
                Object idEnvioObj = parametrosEnvio.get("idEnvio");
                Integer idEnvio = null;
                if (idEnvioObj instanceof BigInteger) {
                    idEnvio = ((BigInteger) idEnvioObj).intValue();
                } else {
                    idEnvio = (Integer) idEnvioObj;
                }
                
                System.out.println("ID del envío registrado: " + idEnvio);

                HashMap<String, Object> parametrosDestino = new HashMap<>();
                parametrosDestino.put("tipo", envio.getDireccionOrigen().getTipo());
                parametrosDestino.put("calle", envio.getDireccionOrigen().getCalle());
                parametrosDestino.put("numero", envio.getDireccionOrigen().getNumero());
                parametrosDestino.put("colonia", envio.getDireccionOrigen().getColonia());
                parametrosDestino.put("codigoPostal", envio.getDireccionOrigen().getCodigoPostal());
                parametrosDestino.put("ciudad", envio.getDireccionOrigen().getCiudad());
                parametrosDestino.put("estado", envio.getDireccionOrigen().getEstado());
                parametrosDestino.put("idEnvio", idEnvio);
                
                HashMap<String, Object> parametrosOrigen = new HashMap<>();
                parametrosOrigen.put("tipo", envio.getDireccionDestino().getTipo());
                parametrosOrigen.put("calle", envio.getDireccionDestino().getCalle());
                parametrosOrigen.put("numero", envio.getDireccionDestino().getNumero());
                parametrosOrigen.put("colonia", envio.getDireccionDestino().getColonia());
                parametrosOrigen.put("codigoPostal", envio.getDireccionDestino().getCodigoPostal());
                parametrosOrigen.put("ciudad", envio.getDireccionDestino().getCiudad());
                parametrosOrigen.put("estado", envio.getDireccionDestino().getEstado());                                
                parametrosDestino.put("idEnvio", idEnvio);
                      
                conexionBD.insert("envio.registrarDireccion", parametrosOrigen);          

                conexionBD.insert("envio.registrarDireccion", parametrosDestino);           
                            
                /*HashMap<String, Object> parametrosSeguimiento = new HashMap<>();
                parametrosSeguimiento.put("nombre", envio.getSeguimiento().getNombre());
                parametrosSeguimiento.put("fecha", envio.getSeguimiento().getFecha());
                parametrosSeguimiento.put("hora", envio.getSeguimiento().getHora());
                parametrosSeguimiento.put("idColaborador", envio.getSeguimiento().getidColaborador());
                
                conexionBD.insert("envio.registrarSeguimiento", parametrosSeguimiento);  */
                
                
                
                conexionBD.commit();
                
                if(filasAfectadas > 0){                   
                    msj.setError(false);
                    msj.setMensaje("El envío fue registrado con éxito.");
                }else{
                    msj.setError(true);
                    msj.setMensaje("El envío no pudo ser registrado");
                }
            } catch (Exception e) {
                conexionBD.rollback();
                throw e; // Manejar o loguear el error adecuadamente.
            } finally {
                if (conexionBD != null) {
                    conexionBD.close();
                }
            }
        }else{
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible");
        }
        return msj;
    }
        
    
    private static String generarNumeroGuia(SqlSession conexionBD) {
        String numeroGuia;
        boolean existe;
        do {
            numeroGuia = UUID.randomUUID().toString().substring(0, 8); // Genera un número único
            existe = conexionBD.selectOne("envio.verificarNumeroGuia", numeroGuia) != null;
        } while (existe);
        return numeroGuia;
    }

    
}
