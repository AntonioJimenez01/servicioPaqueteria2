package dominio;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Mensaje;
import pojo.Rol;

public class ImpColaborador {

    public static List<Colaborador> obtenerTodosLosColaboradores() {
        List<Colaborador> colaboradores = null;
        Mensaje msj = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("colaborador.obtenerColaborador");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
            
        }
        return colaboradores;
    }

    public static Mensaje registrarColaborador(Colaborador colaborador) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                Colaborador colaboradorPorCorreo = conexionBD.selectOne("colaborador.obtenerColaboradorPorCorreo", colaborador.getCorreo());
                if (colaboradorPorCorreo != null) {
                    msj.setError(true);
                    msj.setMensaje("El correo ya está registrado.");
                    return msj;
                }
                Colaborador colaboradorPorCURP = conexionBD.selectOne("colaborador.obtenerColaboradorPorCURP", colaborador.getCurp());
                if (colaboradorPorCURP != null) {
                    msj.setError(true);
                    msj.setMensaje("La CURP ya está registrada.");
                    return msj;
                }
                Colaborador colaboradorPorNumeroPersonal = conexionBD.selectOne("colaborador.obtenerColaboradorPorNumeroPersonal", colaborador.getNoPersonal());
                if (colaboradorPorNumeroPersonal != null) {
                    msj.setError(true);
                    msj.setMensaje("El número de personal ya está registrado.");
                    return msj;
                }
                if (colaborador.getNumeroLicencia() != null && !colaborador.getNumeroLicencia().isEmpty()) {
                    try {
                        Colaborador colaboradorPorNumeroLicencia = conexionBD.selectOne("colaborador.obtenerColaboradorPorNumeroLicencia", colaborador.getNumeroLicencia());
                        if (colaboradorPorNumeroLicencia != null) {
                            msj.setError(true);
                            msj.setMensaje("El número de licencia ya está registrado.");
                            return msj;
                        }
                    } catch (Exception e) {
                        msj.setError(true);
                        msj.setMensaje("Error al validar el número de licencia: " + e.getMessage());
                        return msj;
                    }
                }
                int filasAfectadas = conexionBD.insert("colaborador.registrarColaborador", colaborador);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("El colaborador se registró con éxito.");
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se pudo registrar el colaborador.");
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje(e.getMessage());
            }
        } else {
            msj.setError(true);
            msj.setMensaje("Por el momento el servicio no está disponible.");
        }
        return msj;
    }

    public static Mensaje editarColaborador(Colaborador colaborador) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {

                if (colaborador.getNombre() == null || colaborador.getNombre().isEmpty()) {
                    msj.setError(true);
                    msj.setMensaje("El nombre del colaborador no puede estar vacío.");
                    return msj;
                }
                
                int filasAfectadas = conexionBD.update("colaborador.editarColaborador", colaborador);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("El colaborador con el ID " + colaborador.getIdColaborador() + " fue actualizado con éxito.");
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se encontró el colaborador o no se pudo actualizar.");
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje("Error al actualizar colaborador: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setError(true);
            msj.setMensaje("No se pudo establecer la conexión con la base de datos.");
        }
        return msj;
    }

    public static Mensaje eliminarColaborador(Integer idColaborador) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                Colaborador colaboradorExistente = conexionBD.selectOne("colaborador.idColaborador", idColaborador);
                if (colaboradorExistente != null) {
                    int numeroFilasAfectadas = conexionBD.delete("colaborador.eliminarColaborador", idColaborador);
                    conexionBD.commit();
                    if (numeroFilasAfectadas > 0) {
                        mensaje.setError(false);
                        mensaje.setMensaje("Cuenta eliminada con éxito.");
                    } else {
                        mensaje.setMensaje("Lo sentimos, no se pudo eliminar tu cuenta, inténtalo más tarde.");
                    }
                } else {
                    mensaje.setMensaje("No hay ninguna cuenta registrada en la base de datos con el ID que proporcionaste.");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Lo sentimos, por el momento no hay conexión a la base de datos.");
        }
        return mensaje;
    }

    public static List<Rol> obtenerRoles() {
        List<Rol> roles = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                roles = conexionBD.selectList("colaborador.obtenerRoles");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return roles;
    }
    
    public static Mensaje guardarFoto(Integer idColaborador, byte[] fotografia){
        Mensaje respuesta = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
            try{
                HashMap<String, Object> parametros = new HashMap<>();
                parametros.put("idColaborador",idColaborador);
                parametros.put("fotografia", fotografia);
                int filasAfectadas = conexionBD.update("colaborador.guardarFoto",parametros);
                conexionBD.commit();
                if (filasAfectadas>0){
                    respuesta.setError((false));
                    respuesta.setMensaje("Fotografía del colaborador guardada correctamente. ");
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("Los sentimos, no se pudo guardar la fotografía del colaborador");
                }
                conexionBD.close();
            }catch (Exception e){
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("Los senitmos por el momento no hay conexion al almacenamiento de la base de datos" );
        }
        return respuesta;
    }
    
    public static Colaborador obtenerFoto(Integer idColaborador){
        Colaborador cliente = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null){
            try{
                cliente = conexionBD.selectOne("colaborador.obtenerFoto", idColaborador);
                conexionBD.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return cliente;
    }
    
    public static List<Colaborador> obtenerTodosLosConductores() {
        List<Colaborador> colaboradores = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                colaboradores = conexionBD.selectList("colaborador.obtenerConductores");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
        return colaboradores;
    }
}
