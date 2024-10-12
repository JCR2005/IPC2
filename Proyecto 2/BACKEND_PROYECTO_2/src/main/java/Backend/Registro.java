/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import JPA.Controladora;
import JPA.Usuario;

/**
 *
 * @author carlosrodriguez
 */
public class Registro {

    private String mensaje = "";

    private Controladora controladora = new Controladora();

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void validacion(Usuario usuario) throws Exception {

        if (usuarioExite(usuario)) {

            mensaje = "UsuarioExiste";
        } else if (!validarContraceña(usuario)) {

            mensaje = "ContraceñaInvalida";
        } else if (!validarNombre(usuario)) {

            mensaje = "nombreInvalido";
        } else if (!validarEdad(usuario)) {

            mensaje = "edadInvalida";
        } else if (!validarDescripcion(usuario)) {

            mensaje = "descripcionValida";
        } else if (verificarCamposVacios(usuario)) {
            mensaje = "Por favor llene todos los cambios";
        } else {
            mensaje = "Felicidades " + usuario.getUsuario() + " su cuenta ha sido creada";

            if (verificarNecesidadCartera(usuario.getTipoCuenta())) {
                usuario.setIdCartera(crearCartera());
            }
            
            controladora.crearUsuario(usuario);

        }
    }

    private boolean verificarNecesidadCartera(String tipoUsuario) {

        boolean necesitaCartera = false;
        if (tipoUsuario.endsWith("Anunciante") || tipoUsuario.endsWith("Editor")) {
            necesitaCartera = true;
        }

        return necesitaCartera;

    }

    private boolean verificarCamposVacios(Usuario usuario) {
        boolean camposVacios = false;

        if ((usuario.getUsuario().isEmpty()) || (usuario.getUsuario() == null)) {
            camposVacios = true;
        }
        if ((usuario.getNombre().isEmpty()) || (usuario.getNombre() == null)) {
            camposVacios = true;
        }
        if ((usuario.getPassword().isEmpty()) || (usuario.getPassword() == null)) {
            camposVacios = true;
        }
        if ((usuario.getTipoCuenta().isEmpty()) || (usuario.getTipoCuenta() == null)) {
            camposVacios = true;
        }
        if ((usuario.getDescripcion().isEmpty()) || (usuario.getDescripcion() == null)) {
            camposVacios = true;
        }
        if ((usuario.getEdad() == 0)) {
            camposVacios = true;
        }
        return camposVacios;
    }

    private boolean usuarioExite(Usuario usuario) throws Exception {

        return controladora.buscarUsuario(usuario);

    }

    private boolean validarContraceña(Usuario usuario) {
        boolean contrecañaValida = false;

        if ((usuario.getPassword().length() >= 8) && (usuario.getPassword().length() <= 32) && (!usuario.getPassword().contains(" "))) {
            contrecañaValida = true;
        }
        return contrecañaValida;
    }

    private boolean validarNombre(Usuario usuario) {
        boolean nombreValido = false;

        if ((usuario.getNombre().length() >= 2) && (usuario.getNombre().length() <= 30)) {
            nombreValido = true;
        }

        return nombreValido;
    }

    private boolean validarEdad(Usuario usuario) {
        boolean edadValida = false;

        if ((usuario.getEdad() >= 18) && (usuario.getEdad() <= 100)) {
            edadValida = true;
        }

        return edadValida;
    }

    private boolean validarDescripcion(Usuario usuario) {
        boolean descripcionValida = false;

        if ((usuario.getDescripcion().length() >= 10) && (usuario.getDescripcion().length() <= 100)) {
            descripcionValida = true;
        }

        return descripcionValida;
    }

    public String crearCartera() throws Exception {
        String idCartera = "";
        int contador = 0;
        boolean carteraGenerada = false;

        while (!carteraGenerada) {

            String id_cartera = String.format("%016d", contador);

            if (!controladora.buscarCartera(id_cartera)) {
                
                controladora.crearCartera(id_cartera );
                carteraGenerada = true;
                idCartera = id_cartera;
            } else {
                contador++;
            }

        }

        return idCartera;

    }

}
