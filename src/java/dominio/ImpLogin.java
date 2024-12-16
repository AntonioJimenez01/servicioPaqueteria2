package dominio;
import java.util.HashMap;
import java.util.LinkedHashMap;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojo.Colaborador;
import pojo.LoginColaborador;


public  class ImpLogin{
    public static LoginColaborador validarSesionColaborador(String noPersonal, String contraseña){ 
      LoginColaborador respuesta = new LoginColaborador();
      //conexion a la BD
      SqlSession conexionBD = MyBatisUtil.obtenerConexion();
      if(conexionBD != null){
          try{
              HashMap<String, String> parametros = new LinkedHashMap<>(); 
              parametros.put("noPersonal",noPersonal);
              parametros.put("contraseña",contraseña);
              Colaborador colaborador = conexionBD.selectOne("sesion.loginColaborador",parametros);
              if (colaborador != null){
                  respuesta.setError(false);
                  respuesta.setMensaje("credenciales correctas del colaborador" + colaborador.getNombre());
                  respuesta.setColaborador(colaborador);
              }else{
                  respuesta.setError(true);
                  respuesta.setMensaje("No. de personal y/o contraseña incorrectos");
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
      return respuesta;
    }
    
    
}