package dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Mensaje;
import pojo.RespuestaEnvio;
import pojo.Unidad;


public class ImpConductor {

    public static Mensaje asignarUnidad(String noPersonal, String numeroInterno) {
    Mensaje msj = new Mensaje();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();

    if (conexionBD != null) {
        try {
            // Verificar si ya existe un conductor asignado a esa unidad
            Unidad unidadAsignada = conexionBD.selectOne("conductor.verificarUnidadUso", noPersonal);
            if (unidadAsignada != null) {
                msj.setError(true);
                msj.setMensaje("El conductor ya esta asignado a una unidad.");
                return msj;
            }

            // Asignar nueva unidad
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("noPersonal", noPersonal);
            parametros.put("numeroInterno", numeroInterno);
            int filasAfectadas = conexionBD.update("conductor.asignarUnidad", parametros);
            conexionBD.commit();

            if (filasAfectadas > 0) {
                msj.setError(false);
                msj.setMensaje("Se ha asignado la unidad: " + numeroInterno + " al conductor: " + noPersonal);
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
    
    
     
    public static Unidad obtenerUnidadColaborador(Integer idColaborador) {
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    Unidad colaborador = null;

    if (conexionBD != null) {
        try {
            // Ejecuta la consulta y obtiene el objeto Colaborador
            colaborador = conexionBD.selectOne("conductor.obtenerUnidadColaborador", idColaborador);
        } catch (Exception e) {
            conexionBD.rollback();
            System.err.println("Error al verificar unidad: " + e.getMessage());
        } finally {
            conexionBD.close();
        }
    } else {
        System.err.println("Por el momento el servicio no está disponible.");
    }

    return colaborador;
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

   public static Mensaje actualizarEstatusEnvio(Map<String, Object> parametros) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                
                int filasAfectadas = conexionBD.insert("conductor.actualizarEstatus", parametros);

                if (filasAfectadas > 0) {
                    // Realiza el commit explícito
                    conexionBD.commit();

                    msj.setError(false);
                    msj.setMensaje("Se actualizó el envío" );
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se pudo actualizar el envío");
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje("Error: " + e);
            } finally {
                // Asegúrate de cerrar la conexión para liberar recursos
                conexionBD.close();
            }
        } else {
            msj.setError(true);
            msj.setMensaje("No se pudo establecer conexión con la base de datos");
        }

        return msj;
    }

    
}
