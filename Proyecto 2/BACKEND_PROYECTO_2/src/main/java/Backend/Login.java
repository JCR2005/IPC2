/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import JPA.Controladora;
import JPA.Usuario;
import jakarta.faces.annotation.RequestParameterValuesMap;
import respuetas.RespuestaVerificacionIdentidad;
import token.TokenService;

/**
 *
 * @author carlosrodriguez
 */
public class Login {

    RespuestaVerificacionIdentidad repuesta=new RespuestaVerificacionIdentidad();
    private String mensaje = "";

    private Usuario usuario = null;

    private boolean usuarioValidado = false;

    public boolean isUsuarioValidado() {
        return usuarioValidado;
    }

    public void setUsuarioValidado(boolean usuarioValidado) {
        this.usuarioValidado = usuarioValidado;
    }

    private final Controladora controladora = new Controladora();

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public  String validarInicioSesion(Usuario usuario) throws Exception {

        String respueta="";
        if (!buscarUsuario(usuario)) {
            this.mensaje = "El usuario es incorrecto";

        } else if (!validarPassword(usuario.getPassword())) {
            this.mensaje = "El contraceña  incorrecta";
        } else {
            this.mensaje = "Bienbenido "+usuario.getUsuario();
            this.usuarioValidado = true;
        
        }
        
           respueta= generarlogin();
        
        return respueta;
    }
    
        public  RespuestaVerificacionIdentidad verificarUsuario(Usuario usuario) throws Exception {

        if (!buscarUsuario(usuario)) {
             this.repuesta.setMensaje( "Credenciales incorectas");
            
        } else if (!validarPassword(usuario.getPassword())) {
              this.repuesta.setMensaje("Credenciales incorectas");
        } else {
            this.repuesta.setMensaje( "Eres tu!! "+usuario.getUsuario()); 
            this.usuarioValidado = true;
            this.repuesta.setUsuarioVaerificado(true);
        }
        
       
        
        return repuesta;
    }

    private boolean buscarUsuario(Usuario usuario) throws Exception {
        boolean usuarioExiste = false;

        if (controladora.buscarUsuario(usuario)) {
            this.usuario = controladora.obetenerUsuario(usuario);
            usuarioExiste = true;
        }

        return usuarioExiste;

    }

    private boolean validarPassword(String password) throws Exception {
        boolean contraceñaValida = false;
        System.out.println(password+"______________________________________");
        if (this.usuario.getPassword().endsWith(password) && !password.isEmpty()) {
            contraceñaValida = true;
        }
        return contraceñaValida;

    }
    
    public String asignarRutaInicial(){
    
        String ruta="";
        
        
        if (this.usuario.getTipoCuenta().endsWith("Administrador")) {
            ruta="/paginaPrincipalAdministrador";
        }else if (this.usuario.getTipoCuenta().endsWith("Anunciante")) {
            ruta="/paginaprincipalanunciante";
        }else if (this.usuario.getTipoCuenta().endsWith("Editor")) {
            ruta="/paginaPrincipalEditor";
        }else{
              ruta="/soporte";
        }
        
        return ruta;
    }

    
    public String generarlogin(){
    
        String jsonResponse="";
        
        TokenService tokenService = new TokenService(); // Instancia del servicio

        if (isUsuarioValidado()) {
            String token = tokenService.generateToken(this.usuario); // Genera el token
            
            String mensaje=this.mensaje;
            String ruta=asignarRutaInicial();
            jsonResponse = "{\"message\": \"" + mensaje+ "\", \"token\": \"" + token + "\",\"ruta\": \"" + ruta+ "\"}";
        } else {
            jsonResponse = "{\"message\": \" verifica tus credenciales\"}";
        }
        return jsonResponse;
    }
}
