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
        Mensaje mensaje = new Mensaje();

        if (conexionBD != null) {
            try {
                listaClientes = conexionBD.selectList("cliente.obtenerClientes");
                for (Cliente cliente : listaClientes) {
                    List<Direccion> direccion = conexionBD.selectList("cliente.obtenerDireccionesPorCliente", cliente.getIdCliente());
                    cliente.setDirecciones(direccion);
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

        return listaClientes;
    }

    public static Mensaje registrarCliente(Cliente cliente, Direccion direccion) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        Mensaje mensaje = new Mensaje();

        if (conexionBD != null) {
            try {
                Cliente clientePorTelefono = conexionBD.selectOne("cliente.obtenerClientePorTelefono", cliente.getTelefono());
                if (clientePorTelefono != null) {
                    mensaje.setError(true);
                    mensaje.setMensaje("El número de teléfono ya fue registrado");
                    return mensaje;
                }

                Cliente clientePorCorreo = conexionBD.selectOne("cliente.obtenerClientePorCorreo", cliente.getCorreo());
                if (clientePorCorreo != null) {
                    mensaje.setError(true);
                    mensaje.setMensaje("El correo ingresado ya fue registrado");
                    return mensaje;
                }

                conexionBD.insert("cliente.insertarCliente", cliente);
                direccion.setIdCliente(cliente.getIdCliente());
                conexionBD.insert("cliente.insertarDireccion", direccion);
                conexionBD.commit();

                mensaje.setError(false);
                mensaje.setMensaje("Cliente y dirección registrados correctamente.");
            } catch (Exception e) {
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
               

                conexionBD.update("cliente.actualizarCliente", cliente);

                for (Direccion direccion : direcciones) {
                    if (direccion.getIdDireccion() != null) {
                        conexionBD.update("cliente.actualizarDireccion", direccion);
                    } else {
                        direccion.setIdCliente(cliente.getIdCliente());
                        conexionBD.insert("cliente.insertarDireccion", direccion);
                    }
                }

                conexionBD.commit();

                mensaje.setError(false);
                mensaje.setMensaje("Cliente y direcciones actualizados correctamente.");
            } catch (Exception e) {
                conexionBD.rollback();
                mensaje.setError(true);
                mensaje.setMensaje("Error al actualizar el cliente: " + e.getMessage());
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

    public static Mensaje eliminarCliente(Integer idCliente) {
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        Mensaje mensaje = new Mensaje();

        if (conexionBD != null) {
            try {
                Cliente clienteExistente = conexionBD.selectOne("cliente.idCliente", idCliente);
                if (clienteExistente != null) {
                    conexionBD.getConnection().setAutoCommit(false);
                    conexionBD.delete("cliente.eliminarDirecciones", idCliente);

                    int filasEliminadas = conexionBD.delete("cliente.eliminarCliente", idCliente);

                    if (filasEliminadas > 0) {
                        mensaje.setError(false);
                        mensaje.setMensaje("Cliente y direcciones eliminados correctamente.");
                    } else {
                        mensaje.setError(true);
                        mensaje.setMensaje("No se encontró un cliente con el ID proporcionado." + idCliente);
                    }
                } else {
                    mensaje.setMensaje("No hay ninguna cuenta registrada en la base de datos con el ID que proporcionaste");
                }

                conexionBD.commit();
            } catch (Exception e) {
                mensaje.setError(true);
                mensaje.setMensaje("Error al eliminar el cliente: " + e.getMessage());
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
}
