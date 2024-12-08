package dominio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Cliente;
import pojo.Direccion;
import pojo.Mensaje;

public class ImpCliente {
    
    public static List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> listaClientes = null;
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        Mensaje mensaje = new Mensaje ();
    
        if (conexionBD != null) {
            try {
                // Primero, obtenemos todos los clientes
                listaClientes = conexionBD.selectList("cliente.obtenerClientes");
            
                // Luego, para cada cliente, obtenemos sus direcciones
                for (Cliente cliente : listaClientes) {
                    List<Direccion> direccion = conexionBD.selectList("cliente.obtenerDireccionesPorCliente", cliente.getIdCliente());
                    cliente.setDirecciones(direccion); // Asociamos las direcciones al cliente
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("No se pudo establecer la conexión con la base de datos.");
        }
    
    return listaClientes;    }
    
    public static Mensaje registrarCliente (Cliente cliente, Direccion direccion) {
         SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        Mensaje mensaje = new Mensaje();
        
        if (conexionBD != null) {
            try {
                // Registrar cliente
                conexionBD.insert("cliente.insertarCliente", cliente);
                
                // Usar el ID generado del cliente para registrar la dirección
                direccion.setIdCliente(cliente.getIdCliente());
                conexionBD.insert("cliente.insertarDireccion", direccion);
                
                // Confirmar la transacción
                conexionBD.commit();
                
                mensaje.setError(false);
                mensaje.setMensaje("Cliente y dirección registrados correctamente.");
            } catch (Exception e) {
                // Revertir transacciones en caso de error
                conexionBD.rollback();
                mensaje.setError(true);
                mensaje.setMensaje("Error al registrar el cliente: " + e.getMessage());
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setError(true);
            mensaje.setMensaje("No se pudo establecer conexión con la base de datos.");
        }
        
        return mensaje;
    }    
    
    public static Mensaje actualizarCliente(Cliente cliente, List<Direccion> direcciones) {
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    Mensaje mensaje = new Mensaje();

    if (conexionBD != null) {
        try {
            // Actualizar cliente
            conexionBD.update("cliente.actualizarCliente", cliente);

            // Actualizar direcciones existentes
            for (Direccion direccion : direcciones) {
                if (direccion.getIdDireccion() != null) {
                    // Si la dirección tiene un ID, actualízala
                    conexionBD.update("cliente.actualizarDireccion", direccion);
                } else {
                    // Si la dirección no tiene ID, insertarla como nueva
                    direccion.setIdCliente(cliente.getIdCliente());
                    conexionBD.insert("cliente.insertarDireccion", direccion);
                }
            }

            // Confirmar la transacción
            conexionBD.commit();

            mensaje.setError(false);
            mensaje.setMensaje("Cliente y direcciones actualizados correctamente.");
        } catch (Exception e) {
            // Revertir transacciones en caso de error
            conexionBD.rollback();
            mensaje.setError(true);
            mensaje.setMensaje("Error al actualizar el cliente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    } else {
        mensaje.setError(true);
        mensaje.setMensaje("No se pudo establecer conexión con la base de datos." );
    }

    return mensaje;
}
public static Mensaje eliminarCliente(Integer idCliente) {
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    Mensaje mensaje = new Mensaje();

    if (conexionBD != null) {
        try {
            Cliente clienteExistente = conexionBD.selectOne("cliente.idCliente", idCliente);
            if(clienteExistente!=null){
            conexionBD.getConnection().setAutoCommit(false);
            conexionBD.delete("cliente.eliminarDirecciones", idCliente);

            // Eliminar el cliente
            int filasEliminadas = conexionBD.delete("cliente.eliminarCliente", idCliente);

            if (filasEliminadas > 0) {
                mensaje.setError(false);
                mensaje.setMensaje("Cliente y direcciones eliminados correctamente.");
            } else {
                mensaje.setError(true);
                mensaje.setMensaje("No se encontró un cliente con el ID proporcionado." + idCliente);
            }
            }else{
                    mensaje.setMensaje("No hay ninguna cuenta registrada en la base de datos con el ID que proporcionaste");
                }

            // Confirmar transacción
            conexionBD.commit();
        } catch (Exception e) {

        }
        
    } else {
        mensaje.setError(true);
        mensaje.setMensaje("No se pudo establecer conexión con la base de datos.");
    }

    return mensaje;
}
   public static List<Cliente> buscarCliente(String nombre, String telefono, String correo) {
    List<Cliente> clientes = null;
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();

    if (conexionBD != null) {
        try {
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("nombre", nombre);
            parametros.put("telefono", telefono);
            parametros.put("correo", correo);

            clientes = conexionBD.selectList("cliente.buscarCliente", parametros);
            
            for (Cliente cliente : clientes) {
                List<Direccion> direcciones = conexionBD.selectList("cliente.obtenerDireccionesPorCliente", cliente.getIdCliente());
                cliente.setDirecciones(direcciones);  // Asignar las direcciones al cliente
            }
        } catch (Exception e) {
            System.err.println("Error al buscar colaboradores: " + e.getMessage());
        } finally {
            conexionBD.close();
        }
    } else {
        System.err.println("No se pudo establecer conexión con la base de datos.");
    }

    return clientes;
}



/*            // Obtener las direcciones de cada cliente
            for (Cliente cliente : clientes) {
                List<Direccion> direcciones = conexionBD.selectList("cliente.obtenerDireccionesPorCliente", cliente.getIdCliente());
                cliente.setDirecciones(direcciones);  // Asignar las direcciones al cliente
            }*/

       
}
