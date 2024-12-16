/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.Mensaje;
import pojo.Paquete;
import pojo.ResultadoPaquete;


public class ImpPaquete {
     public static List<ResultadoPaquete> obtenerPaquetes(){
        ResultadoPaquete respuesta = new ResultadoPaquete();
        List<ResultadoPaquete> paquetes = new ArrayList<>();
         //conexion a la BD
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if(conexionBD != null){
          try{
            
               // Se manda a llamar al mapper para obtener la lista de paquetes
            paquetes = conexionBD.selectList("paquete.obtenerPaquetes");
            
            if (paquetes == null && paquetes.isEmpty()) {
                respuesta.setError(true);
                respuesta.setMensaje("No se encontraron colaboradores.");
                return Collections.emptyList();
            } 
          }catch(Exception e){
              respuesta.setError(true);
              respuesta.setMensaje(e.getMessage());            
          }
      }else{
          //no hay conexion a la BD
          respuesta.setError(true);
          respuesta.setMensaje("por el momento no hay conexión a la base de datos");
      }
      return paquetes;
    }

    public static ResultadoPaquete obtenerPaquetePorEnvio(Integer idEnvio) {
    ResultadoPaquete respuesta = new ResultadoPaquete();
    SqlSession conexionBD = MyBatisUtil.obtenerConexion();
    if (conexionBD != null) {
        try {
            List<Paquete> paquetes = conexionBD.selectList("paquete.obtenerPaquetePorEnvio", idEnvio);

            if (paquetes != null && !paquetes.isEmpty()) {
                respuesta.setError(false);
                respuesta.setPaquetes(paquetes);
            } else {
                respuesta.setError(true);
                respuesta.setMensaje("No se encontró ningún paquete con el idEnvio proporcionado.");
            }
        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setMensaje("Error al obtener los paquetes.");
        } finally {
            conexionBD.close();
        }
    } else {
        respuesta.setError(true);
        respuesta.setMensaje("Por el momento no hay conexión a la base de datos.");
    }
    return respuesta;
    }
    
    
    public static Mensaje registrarPaquete(Paquete paquete){
        //el pojo ayuda a pasar datos entre servciio a la imp
        Mensaje respuesta = new Mensaje();
         //conexion a la BD
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.insert("paquete.registrar", paquete);
                conexionBD.commit(); //confirmación del insert
                if(filasAfectadas > 0){
                    respuesta.setError(false);
                    respuesta.setMensaje("El paquete fue registrado con exito.");
                }else{
                    respuesta.setError(true);
                    respuesta.setMensaje("El paquete no pudo ser registrado");
                }
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            } 
        }else{
          //no hay conexion a la BD
          respuesta.setError(true);
          respuesta.setMensaje("por el momento no hay conexión a la base de datos");
        }
      return respuesta;
    }
    
    public static Mensaje modificarPaquete(Paquete paquete) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();
        if (conexionBD != null) {
            
            try {
                int filasAfectadas = conexionBD.update("paquete.editar", paquete);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Modificación exitósa");
                } else {
                    mensaje.setMensaje("Modificación fallida");
                }

            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Por el momento no se puede realizar esta operación.");
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Por el momento no hay conexión con la base de datos.");
        }
        return mensaje;
    }
    
    
    
    public static Mensaje eliminarPaquete(Integer idPaquete) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.obtenerConexion();

        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.delete("paquete.eliminarPaquete", idPaquete);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Eliminación exitosa");
                } else {
                    mensaje.setMensaje("No se pudo eliminar el paquete.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Por el momento no se puede realizar esta operación.");
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Por el momento no hay conexión con la base de datos.");
        }
        return mensaje;
    }

}