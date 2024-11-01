import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideHttpClient } from '@angular/common/http'; // Para solicitudes HTTP
import { provideRouter } from '@angular/router'; // Para el enrutamiento
import { appConfig } from './app/app.config';
import { routes } from './app/app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async'; // Asegúrate de que las rutas están definidas

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    provideHttpClient(), // Cliente HTTP
    provideRouter(routes), provideAnimationsAsync() // Proveedor de rutas
  ],
})
  .catch((err) => console.error(err));
