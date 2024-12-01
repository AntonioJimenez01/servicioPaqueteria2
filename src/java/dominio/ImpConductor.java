package dominio;

import java.util.ArrayList;
import java.util.HashMap;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Mensaje;
import pojo.RespuestaEnvio;


public class ImpConductor {

    public static Mensaje asignarUnidad(Integer noPersonal, String numeroInterno) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {                             
                    
               HashMap<String, Object> parametros = new HashMap<>();
               parametros.put("noPersonal", noPersonal);
               parametros.put("numeroInterno", numeroInterno);
                int filasAfectadas = conexionBD.update("conductor.asignarUnidad", parametros);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("Se ha asignado la unidad: "+numeroInterno+" al conductor: "+noPersonal);
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se encontró el conductor o la unidad.");
                }
            } catch (Exception e) {
                conexionBD.rollback();
                msj.setError(true);
                msj.setMensaje("Error al asignar unidad: " + e.getMessage());
            } finally {
                conexionBD.close(); 
            }
        } else {
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible.");
        }

        return msj;
    }
   
    
    public static Mensaje desasignarUnidad(Integer noPersonal) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {              
               
                int filasAfectadas = conexionBD.update("conductor.desasignarUnidad", noPersonal);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("Se ha desasignado la unidad al conductor: "+noPersonal);
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se encontró el conductor o la unidad.");
                }
            } catch (Exception e) {
                conexionBD.rollback();
                msj.setError(true);
                msj.setMensaje("Error al desasignar unidad: " + e.getMessage());
            } finally {
                conexionBD.close(); 
            }
        } else {
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible.");
        }

        return msj;
    }
    
    
     
    public static Mensaje obtenerUnidadColaborador(Integer idColaborador) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        
        
        if (conexionBD != null) {
            try {           
                // Consultamos el idColaborador actual de la unidad
                String idColaboradorExistente = conexionBD.selectOne("conductor.obtenerUnidadColaborador", idColaborador);

                // Si hay un conductor asignado, avisamos al usuario
                if (idColaboradorExistente != null) {
                    msj.setError(false);
                    msj.setMensaje("El conductor ya tiene una unidad asignada");
                    msj.setBandera(true);
                    return msj;  // Termina aquí si ya hay un conductor asignado
                } else {
                    msj.setBandera(false);
                    msj.setError(true);
                    msj.setMensaje("Conductor sin unidad");
                }
            } catch (Exception e) {
                conexionBD.rollback();
                msj.setError(true);
                msj.setMensaje("Error al verificar unidad " + e.getMessage());
            } finally {
                conexionBD.close(); 
            }
        } else {
         
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible.");
        }

        return msj;
    }

    public static RespuestaEnvio verListaEnvios(String noPersonal) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        RespuestaEnvio msj = new RespuestaEnvio();

        if (conexionBD != null) {
            try {
                // Se obtienen los envíos desde el mapper correspondiente
                msj.setEnvios(conexionBD.selectList("conductor.verListaEnvios", noPersonal));

                // Verificar si la lista de envíos contiene datos
                if (msj.getEnvios() == null || msj.getEnvios().isEmpty()) {
                    msj.setError(true);
                    msj.setMensaje("No se encontraron envíos para el colaborador con número personal: " + noPersonal);
                } else {
                    msj.setError(false);
                    msj.setMensaje("Se encontraron " + msj.getEnvios().size() + " envíos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                msj.setError(true);
                msj.setMensaje("Error al obtener los envíos: " + e.getMessage());
                return msj;
            }
        } else {
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible.");
        }

        return msj;
    }

    public static RespuestaEnvio verDetalleEnvio(String noPersonal, Integer numeroGuia) {
        RespuestaEnvio msj = new RespuestaEnvio();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {                             
                    
                HashMap<String, Object> parametros = new HashMap<>();
                parametros.put("noPersonal", noPersonal);
                parametros.put("numeroGuia", numeroGuia);
                
                msj.setEnvio(conexionBD.selectOne("conductor.verDetalleEnvio", parametros));
                conexionBD.commit();

                if (msj.getEnvio() != null) {
                    msj.setError(false);
                    msj.setMensaje("Se ha encontrado detalles para el numero de guía: "+numeroGuia);
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se encontró informacion para el numero de guía: "+numeroGuia);
                }
            } catch (Exception e) {
                conexionBD.rollback();
                msj.setError(true);
                msj.setMensaje("Error al buscar: " + e.getMessage());
            } finally {
                conexionBD.close(); 
            }
        } else {
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible.");
        }

        return msj;
    }

   
}
