package com.mycompany.backend_proyecto_2;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Configuración principal para los servicios REST en la aplicación.
 * Define el punto de entrada de la API REST con el path "resources" y registra
 * todos los recursos (clases de servicio REST) y filtros que utilizará el sistema.
 */
@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {

    /**
     * Registra los recursos y filtros que estarán disponibles en la API REST.
     *
     * @return un conjunto de clases que contienen los servicios REST y filtros.
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        // Registro de características adicionales (como soporte multipart)
        resources.add(MultiPartFeature.class); // Soporte para el manejo de archivos multipart

        // Registro de recursos REST para el módulo de Servicios Generales
        resources.add(ServicioRest.RestRegistro.class);         // Registro de usuarios
        resources.add(ServicioRest.RestLogin.class);            // Inicio de sesión_
        resources.add(ServicioRest.RestCostoAnuncio.class);     // Gestión de costos de anuncios
        resources.add(ServicioRest.RestCompraAnuncio.class);    // Compra de anuncios
        resources.add(ServicioRest.RestVigenciaAnuncio.class);  // Vigencia de anuncios
        resources.add(ServicioRest.RestCartera.class);          // Cartera de usuario
        resources.add(ServicioRest.Editor.RestRevista.class);          // Gestión de revistas
        resources.add(ServicioRest.RestPerfil.class);           // Perfil de usuario

        // Registro de recursos REST para el módulo del Editor
        resources.add(ServicioRest.Editor.RestArticulo.class);                 
        resources.add(ServicioRest.procesoSistema.RestProcesoAnuncios.class);           // Gestión de artículos
        resources.add(ServicioRest.Editor.RestReporteComentarios.class);              // Reporte de comentarios
        resources.add(ServicioRest.Editor.RestReporteSuscriptores.class);             // Reporte de suscriptores
        resources.add(ServicioRest.Editor.RestReportePagos.class);                    // Reporte de pagos
        resources.add(ServicioRest.Editor.RestReporteRvistasTop5.class);              // Reporte de las top 5 revistas
         resources.add(ServicioRest.Editor.RestCreaciònDeRevista.class);              // Reporte de las top 5 revistas

        // Registro de recursos REST para el módulo de Administración
        resources.add(ServicioRest.Administraciòn.RestReportePagos.class);                      // Reporte de pagos generales
        resources.add(ServicioRest.Administraciòn.RestReporteGananciasPorAnunciantes.class);    // Ganancias por anunciantes
        resources.add(ServicioRest.Administraciòn.RestReporteRvistasTop5Suscripciones.class);   // Revistas con más suscripciones
        resources.add(ServicioRest.Administraciòn.RestReporteRevistasMasComentadas.class);      // Revistas más comentadas
        resources.add(ServicioRest.Administraciòn.RestAnuncios.class);                         // Gestión de anuncios
        resources.add(ServicioRest.Administraciòn.RestRevistas.class);                         // Gestión de revistas para administradores

        // Registro de recursos REST para el módulo de Suscriptores
        resources.add(ServicioRest.Suscritores.RestSuscripcòn.class);   // Gestión de suscripciones
        resources.add(ServicioRest.Suscritores.RestBusqueda.class);     // Búsqueda de revistas
        resources.add(ServicioRest.Suscritores.RestLike.class);         // Gestión de "Me gusta"
        resources.add(ServicioRest.Suscritores.RestCometarios.class);   // Gestión de comentarios
        resources.add(ServicioRest.Suscritores.RestVusaulizaciòn.class);// Visualización de revistas

        // Registro de filtros
        resources.add(filters.CORSFilter.class); // Filtro para habilitar CORS

        return resources;
    }
}
