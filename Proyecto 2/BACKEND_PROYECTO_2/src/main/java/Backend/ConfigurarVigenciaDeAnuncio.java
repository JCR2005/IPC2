package Backend;

import JPA.Controladora;
import JPA.vigenciaAnuncio;

/**
 *
 * @author carlosrodriguez
 */
public class ConfigurarVigenciaDeAnuncio {

    private String mensaje = "";
    private final Controladora controladora = new Controladora();

    private boolean numerosNegativos = false;

    public String proceso(vigenciaAnuncio[] vigenciaAnuncios) throws Exception {
        String respuesta = "";

        if (vigenciaAnuncios != null) {
            if (validarNumerosNegativos(vigenciaAnuncios)) {
                this.mensaje = "No puede ingresar numeros negativos";
            }

        } else {
            this.mensaje = "A ocurrido un error, recarga la pagina e intenta de nuevo";
        }

        respuesta = generarRespuesta(vigenciaAnuncios);

        return respuesta;

    }

    private boolean validarNumerosNegativos(vigenciaAnuncio[] vigenciaAnuncios) {

        this.numerosNegativos = false;
        for (int i = 0; i < vigenciaAnuncios.length; i++) {

            if (vigenciaAnuncios[i].getVigencia() < 0) {
                this.numerosNegativos = true;
            }

        }
        return this.numerosNegativos;

    }

    private String generarRespuesta(vigenciaAnuncio[] vigenciaAnuncio) throws Exception {

        String jsonResponse = "";
        boolean ningunCambio=true;

        if (!this.numerosNegativos) {
            for (vigenciaAnuncio i: vigenciaAnuncio) {
                if (i.getVigencia() > 0) {

                    if (controladora.actualizarVigenciaAnucio(i)) {
                        this.mensaje = "Se han hecho los cambios";
                        ningunCambio=false;

                    } else {
                        this.mensaje = "A ocurrido un error, recarga la pagina e intenta de nuevo";

                    }
                }
            }
        }
        
        if (ningunCambio) {
            this.mensaje = "No se realizo ningun cambio";
        }

        jsonResponse = "{\"message\": \"" + this.mensaje + "\"}";

        return jsonResponse;
    }

}
