package Backend.administracion;

import JPA.Controladora;
import JPA.CostoAnuncio;

/**
 *
 * @author carlosrodriguez
 */
public class ConfigurarPrecioAnuncio {

    private String mensaje = "";
    private final Controladora controladora = new Controladora();

    private boolean numerosNegativos = false;

    public String proceso(CostoAnuncio[] costoAnuncios) throws Exception {
        String respuesta = "";

        if (costoAnuncios != null) {
            if (validarNumerosNegativos(costoAnuncios)) {
                this.mensaje = "No puede ingresar numeros negativos";
            }

        }else{
            this.mensaje = "A ocurrido un error, recarga la pagina e intenta de nuevo";
        }

        respuesta = generarRespuesta(costoAnuncios);

        return respuesta;

    }

    private boolean validarNumerosNegativos(CostoAnuncio[] costoAnuncios) {

        this.numerosNegativos = false;
        for (int i = 0; i < costoAnuncios.length; i++) {

            if (costoAnuncios[i].getCosto_Add() < 0) {
                this.numerosNegativos = true;
            }

        }
        return this.numerosNegativos;

    }

    private String generarRespuesta(CostoAnuncio[] costoAnuncios) throws Exception {

        String jsonResponse = "";

        if (!this.numerosNegativos) {
            for (CostoAnuncio costoAnuncio : costoAnuncios) {
                if (costoAnuncio.getCosto_Add() > 0) {

                    if (controladora.actualizarPrecioAnucio(costoAnuncio)) {
                        this.mensaje = "Se han hecho los cambios";

                    } else {
                        this.mensaje = "A ocurrido un error, recarga la pagina e intenta de nuevo";

                    }
                }
            }
        }

       jsonResponse = "{\"message\": \"" + this.mensaje + "\"}";

        return jsonResponse;
    }

}
