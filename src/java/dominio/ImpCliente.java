package dominio;

import java.util.HashMap;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import pojo.Mensaje;

public class ImpCliente {
    
    public static List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> cliente = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                cliente = conexionBD.selectList("cliente.obtenerCliente");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
        return cliente;
    }
    
    public static Mensaje registrarCliente (Cliente cliente) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.insert("cliente.registrarCliente", cliente);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("El Cliente " + cliente.getNombre() + " " +
                            cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno() +
                            ", fue registrado con éxito.");
                } else {
                    msj.setError(true);
                    msj.setMensaje("La información del cliente no se pudo ser registrado. :c");
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
    
        public static Mensaje editarCliente(Cliente cliente) {
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("cliente.editarCliente", cliente);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("El cliente con el ID " + cliente.getIdCliente()+ " fue actualizado con éxito.");
                } else {
                    msj.setError(true);
                    msj.setMensaje("No se encontró el cliente o no se pudo actualizar.");
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje("Error al actualizar cliente: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setError(true);
            msj.setMensaje("No se pudo establecer la conexión con la base de datos.");
        }

        return msj;
    }
        
       public static Mensaje eliminarCliente (Integer idCliente){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD!=null){
            try {
                Cliente clienteExistente = conexionBD.selectOne("cliente.idCliente", idCliente);
                if(clienteExistente!=null){
                    int numeroFilasAfectadas = conexionBD.delete("cliente.eliminarCliente", idCliente);
                    conexionBD.commit();
                    if(numeroFilasAfectadas > 0){
                        mensaje.setError(false);
                        mensaje.setMensaje("Cuenta eliminada con exito");
                    }else{
                        mensaje.setMensaje("Lo sentimos, no se pudo eliminar tu cuenta, intentalo mas tarde");
                    }
                }else{
                    mensaje.setMensaje("No hay ninguna cuenta registrada en la base de datos con el ID que proporcionaste");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: " +e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Lo sentimos, por el momento no hay conexion a la base de datos");
        }
        return mensaje;
        
    }
       
   public static List<Cliente> buscarCliente(String nombre, String telefono, String correo) {
    List<Cliente> cliente = null;
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();

    if (conexionBD != null) {
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", nombre);
            parametros.put("telefono", telefono);
            parametros.put("correo", correo);

            cliente = conexionBD.selectList("cliente.buscarCliente", parametros);

        } catch (Exception e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        } finally {
            conexionBD.close();
        }
    } else {
        System.err.println("No se pudo establecer conexión con la base de datos.");
    }

    return cliente;
}
}
