package dominio;

import java.util.HashMap;
import java.util.LinkedHashMap;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Mensaje;
import pojo.RespuestaUnidad;
import pojo.Unidad;

public class ImpUnidad {
    
    public static RespuestaUnidad registrarUnidad(Unidad unidad) {
        RespuestaUnidad msj = new RespuestaUnidad();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
       
        if (conexionBD != null) {
            try {
                int duplicados = conexionBD.selectOne("unidad.verificarVin", unidad.getVin());
                
                String primerosCuatro = unidad.getVin().substring(0, 4);       
                String numeroInterno = unidad.getAño() + primerosCuatro;
                unidad.setNumeroInterno(numeroInterno);
                
                if(duplicados > 0){
                    msj.setError(true);
                    msj.setMensaje("El vin: "+unidad.getVin()+" ya está registrado por otra unidad");
                    return msj;
                }
                
                int filasAfectadas = conexionBD.insert("unidad.registrarUnidad", unidad);

                if (filasAfectadas > 0) {
                    // Realiza el commit explícito
                    conexionBD.commit();

                    msj.setError(false);
                    msj.setMensaje("Se registró con éxito la unidad con el numero interno: " + unidad.getNumeroInterno());
                    if (msj.getUnidad() == null) {
                        msj.setUnidad(new Unidad());
                    }
                    msj.getUnidad().setNumeroInterno(numeroInterno);
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se pudo registrar la unidad");
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje("Error en la conexión: " + e);
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

    public static RespuestaUnidad modificarUnidad(Unidad unidad) {
        RespuestaUnidad msj = new RespuestaUnidad();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.insert("unidad.modificarUnidad", unidad);
                
                String primerosCuatro = unidad.getVin().substring(0, 4);       
                String numeroInterno = unidad.getAño() + primerosCuatro;
                unidad.setNumeroInterno(numeroInterno);
                
                if (filasAfectadas > 0) {
                    // Realiza el commit explícito
                    conexionBD.commit();

                    msj.setError(false);
                    msj.setMensaje("Se modificó con éxito la unidad, su numero interno es: " + unidad.getNumeroInterno());
                    if (msj.getUnidad() == null) {
                        msj.setUnidad(new Unidad());
                    }
                    msj.getUnidad().setNumeroInterno(numeroInterno);
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se pudo modificar la unidad");
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje("Error en la conexión: " + e);
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

    public static RespuestaUnidad buscarUnidad(String vin, String marca, String numeroInterno) {
        RespuestaUnidad respuesta = new RespuestaUnidad();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
         if (conexionBD != null) {
            try {
                HashMap<String, String> parametros = new LinkedHashMap<>();
                parametros.put("vin", vin);
                parametros.put("marca", marca);
                parametros.put("numeroInterno", numeroInterno);
                
                respuesta.setUnidad(conexionBD.selectOne("unidad.obtenerUnidad", parametros));

                if (respuesta.getUnidad() != null) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Se recuperaron los datos de la unidad: "+respuesta.getUnidad().getNumeroInterno());
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se encontró la unidad: "+numeroInterno);
                }

            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje("Error: "+e);
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Problemas con la conexion");
        }
         
         return respuesta;
    
    }
    
    public static RespuestaUnidad obtenerUnidades() {
        RespuestaUnidad respuesta = new RespuestaUnidad();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
         if (conexionBD != null) {
            try {            
                respuesta.setUnidades(conexionBD.selectList("unidad.obtenerUnidades"));

                if (respuesta.getUnidad() != null) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Se recuperaron los datos de unidades");
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("No se encontraron unidades");
                }

            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje("Error: "+e);
            } finally {
                conexionBD.close();
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Problemas con la conexion");
        }
         
         return respuesta;
    
    }

    public static Mensaje darBajaUnidad(Unidad unidad) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {              
               
                int filasAfectadas = conexionBD.update("unidad.darBajaUnidad", unidad);
                

                if (filasAfectadas > 0) {
                    conexionBD.commit();
                    msj.setError(false);
                    msj.setMensaje("Se ha dado de baja la unidad: "+unidad.getNumeroInterno());
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se encontró la unidad: "+unidad.getNumeroInterno());
                }
            } catch (Exception e) {
                conexionBD.rollback();
                msj.setError(true);
                msj.setMensaje("Error al dar de baja la unidad: " + e.getMessage());
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
