// app.routes.ts
import { Routes } from '@angular/router';
import { PaginaPrincipalAdministradorComponent } from './pagina-principal-administrador/pagina-principal-administrador.component';
import { ConfigPreciosAnunciosComponent } from './configuracion-costa-anuncio/configuracion-costa-anuncio.component';
import { LoginComponent } from './login/login.component';
import { adminGuardia } from './auth.guard'; // Asegúrate de importar el guardia
import { ConfiguracionDuracionAnunciosComponent } from './configuracion-duracion-anuncios/configuracion-duracion-anuncios.component';
import { PaginaPrincipalAnuncianteComponent } from './Anunciante/pagina-principal-anunciante/pagina-principal-anunciante.component';
import { ConfiguracionCompraDeAnunciosComponent } from './configuracion-compra-de-anuncios/configuracion-compra-de-anuncios.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'paginaPrincipalAdministrador', component: PaginaPrincipalAdministradorComponent, canActivate: [adminGuardia] }, // Protegiendo la ruta
  { path: 'configuracionCostoAnuncios', component: ConfigPreciosAnunciosComponent, canActivate: [adminGuardia] }, // Protegiendo la ruta
  { path: 'paginaprincipalanunciante', component: PaginaPrincipalAnuncianteComponent }, // Protegiendo la ruta
  { path: 'ConfiguracionCompraDeAnunciosComponent', component: ConfiguracionCompraDeAnunciosComponent }, // Protegiendo la ruta

  { path: 'configuracionVigenciaAnuncios', component: ConfiguracionDuracionAnunciosComponent, canActivate: [adminGuardia] },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirige a login por defecto
];
