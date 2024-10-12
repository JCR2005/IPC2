package com.mycompany.backend_proyecto_2;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        // Registra tus recursos y filtros aquí
        resources.add(MultiPartFeature.class);
        resources.add(ServicioRest.RestRegistro.class); // Clase de recursos
        resources.add(ServicioRest.RestLogin.class); // Clase de recursos
        resources.add(ServicioRest.RestCostoAnuncio.class); // Clase de recursos
        resources.add(ServicioRest.RestCompraAnuncio.class);
        resources.add(ServicioRest.RestVigenciaAnuncio.class);
        resources.add(ServicioRest.RestCartera.class);
        resources.add(filters.CORSFilter.class);                   // Filtro CORS
        return resources;
    }
}
