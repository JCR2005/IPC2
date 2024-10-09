/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import JPA.Controladora;
import JPA.Usuario;
import token.TokenService;

/**
 *
 * @author carlosrodriguez
 */
public class Login {

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

        if (this.usuario.getPassword().endsWith(password)) {
            contraceñaValida = true;
        }
        return contraceñaValida;

    }
    
    public String asignarRutaInicial(){
    
        String ruta="";
        
        
        if (this.usuario.getTipoCuenta().endsWith("Administrador")) {
            ruta="/paginaPrincipalAdministrador";
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
