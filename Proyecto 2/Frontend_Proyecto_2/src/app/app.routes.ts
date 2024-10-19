// app.routes.ts
import { Routes } from '@angular/router';
import { PaginaPrincipalAdministradorComponent } from './administrador/pagina-principal-administrador/pagina-principal-administrador.component';
import { ConfigPreciosAnunciosComponent } from './administrador/configuracion-costa-anuncio/configuracion-costa-anuncio.component';
import { LoginComponent } from './login/login.component';
import { adminGuardia } from './auth.guard'; // Asegúrate de importar el guardia
import { ConfiguracionDuracionAnunciosComponent } from './administrador/configuracion-duracion-anuncios/configuracion-duracion-anuncios.component';
import { PaginaPrincipalAnuncianteComponent } from './Anunciante/pagina-principal-anunciante/pagina-principal-anunciante.component';
import { ConfiguracionCompraDeAnunciosComponent } from './Anunciante/configuracion-compra-de-anuncios/configuracion-compra-de-anuncios.component';
import { RegistroComponent } from './registro/registro.component';
import { AnuncianteGuardia } from './auth.guard.Anunciante';
import { RecargarCarteraComponent } from './Anunciante/recargar-cartera/recargar-cartera.component';
import { EstadoAnunciosComponent } from './Anunciante/estado-anuncios/estado-anuncios.component';
import { PaginaPrincipalEditorComponent } from './Editor/pagina-principal-editor/pagina-principal-editor.component';
import { editorGuardia } from './auth.guard.Editor';
import { RecargarCarteraEditorComponent } from './Editor/recargar-cartera /recargar-cartera.component';
import { CrearRevistaComponent } from './Editor/crearRevista/crearRevista.component';
import { AprobarRevistaComponent } from './administrador/aprobar-revista/aprobar-revista.component';
import { ModificarCostoOcultacionComponent } from './administrador/modificar-costo-ocultacion/modificar-costo-ocultacion.component';
import { ConfiguracionRevistasComponent } from './Editor/configuracion-revistas/configuracion-revistas.component';
import { BloqueoDeAnunciosComponent } from './Editor/bloqueo-de-anuncios/bloqueo-de-anuncios.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'paginaPrincipalAdministrador', component: PaginaPrincipalAdministradorComponent, canActivate: [adminGuardia] }, // Protegiendo la ruta
  { path: 'configuracionCostoAnuncios', component: ConfigPreciosAnunciosComponent, canActivate: [adminGuardia] }, // Protegiendo la ruta
  { path: 'paginaprincipalanunciante', component: PaginaPrincipalAnuncianteComponent,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta
  { path: 'ConfiguracionCompraDeAnuncios', component: ConfiguracionCompraDeAnunciosComponent ,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta
  { path: 'RecargarSaldo', component: RecargarCarteraComponent ,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta
  { path: 'estadoAnuncios', component: EstadoAnunciosComponent ,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta
  { path: 'paginaPrincipalEditor', component: PaginaPrincipalEditorComponent ,canActivate: [editorGuardia]}, // Protegiendo la ruta
  { path: 'RecargarSaldoEditor', component: RecargarCarteraEditorComponent ,canActivate: [editorGuardia]}, // Protegiendo la ruta
  { path: 'CrearRevista', component: CrearRevistaComponent,canActivate: [editorGuardia] }, // Protegiendo la ruta
  { path: 'aprobarRevista', component: AprobarRevistaComponent }, // Protegiendo la ruta
  { path: 'modificacionCostoOcultacion', component: ModificarCostoOcultacionComponent }, // Protegiendo la ruta
  { path: 'configuracionRevistas', component: ConfiguracionRevistasComponent }, // Protegiendo la ruta
  { path: 'bloqueoAdss', component: BloqueoDeAnunciosComponent }, // Protegiendo la ruta

  { path: 'configuracionVigenciaAnuncios', component: ConfiguracionDuracionAnunciosComponent, canActivate: [adminGuardia] },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirige a login por defecto
];
