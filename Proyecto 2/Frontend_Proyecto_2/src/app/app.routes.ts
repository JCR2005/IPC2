// app.routes.ts
import { Routes } from '@angular/router';
import { PaginaPrincipalAdministradorComponent } from './pagina-principal-administrador/pagina-principal-administrador.component';
import { ConfigPreciosAnunciosComponent } from './configuracion-costa-anuncio/configuracion-costa-anuncio.component';
import { LoginComponent } from './login/login.component';
import { adminGuardia } from './auth.guard'; // Asegúrate de importar el guardia
import { ConfiguracionDuracionAnunciosComponent } from './configuracion-duracion-anuncios/configuracion-duracion-anuncios.component';
import { PaginaPrincipalAnuncianteComponent } from './Anunciante/pagina-principal-anunciante/pagina-principal-anunciante.component';
import { ConfiguracionCompraDeAnunciosComponent } from './Anunciante/configuracion-compra-de-anuncios/configuracion-compra-de-anuncios.component';
import { RegistroComponent } from './registro/registro.component';
import { AnuncianteGuardia } from './auth.guard.Anunciante';
import { RecargarCarteraComponent } from './Anunciante/recargar-cartera/recargar-cartera.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'paginaPrincipalAdministrador', component: PaginaPrincipalAdministradorComponent, canActivate: [adminGuardia] }, // Protegiendo la ruta
  { path: 'configuracionCostoAnuncios', component: ConfigPreciosAnunciosComponent, canActivate: [adminGuardia] }, // Protegiendo la ruta
  { path: 'paginaprincipalanunciante', component: PaginaPrincipalAnuncianteComponent,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta
  { path: 'ConfiguracionCompraDeAnuncios', component: ConfiguracionCompraDeAnunciosComponent ,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta
  { path: 'RecargarSaldo', component: RecargarCarteraComponent ,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta

  { path: 'configuracionVigenciaAnuncios', component: ConfiguracionDuracionAnunciosComponent, canActivate: [adminGuardia] },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirige a login por defecto
];
