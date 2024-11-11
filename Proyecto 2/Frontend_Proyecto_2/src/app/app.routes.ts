  import { PerfilComponent } from './ComponentesComunes/perfil/perfil.component';
  // app.routes.ts
  import { ReporteDeRevistasMasSuscritassComponent } from './administrador/reportesAdministrador/reporte-de-revistas-mas-suscritas/reporte-de-revistas-mas-suscritas.component';
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
  import { EditarPerfilComponent } from './ComponentesComunes/editar-perfil/editar-perfil.component';
  import { EstadoAnunciosAdminComponent } from './administrador/estado-anuncios/estado-anuncios.component';
  import { ModificarCostoAsociadoComponent } from './administrador/modificar-costo-asociado/modificar-costo-asociado.component';
  import { classModificarCosotAsociadoGLOBALComponent } from './administrador/Modificar-cosot-asociado(GLOBAL)/Modificar-cosot-asociado(GLOBAL).component';
  import { PaginaPrincipalSuscriptorComponent } from './Suscriptor/pagina-principal-suscriptor/pagina-principal-suscriptor.component';
  import { RevistasSuscritasComponent } from './Suscriptor/revistas-suscritas/revistas-suscritas.component';
  import { VisualizacionRevistaComponent } from './Suscriptor/visualizacion-revista/visualizacion-revista.component';
  import { CrearArticuloComponent } from './Editor/crearArticulo/crearArticulo.component';
  import { ReportesComponent } from './Editor/ReportesEditor/reportes/reportes.component';
  import { ReporteDeComentariosComponent } from './Editor/ReportesEditor/reporte-de-comentarios/reporte-de-comentarios.component';
  import { ReporteDesuscripcionesComponent } from './Editor/ReportesEditor/reporte-de-suscripciones/reporte-de-suscripciones.component';
  import { ReportesAdministradorComponent } from './administrador/reportesAdministrador/reportes/reportes.component';
  import { CerrarSesionComponent } from './cerrar-sesion/cerrar-sesion.component';
  import { PerfilEditorComponent } from './Suscriptor/perfil Editor/perfilEditor.component';
  import { loginGuardia } from './auth.guardlogin';
  import { guardSuscriptor } from './auth.guardSuscriptor';
  export const routes: Routes = [
    { path: 'login', component: LoginComponent,canActivate: [loginGuardia] },
    { path: 'perfilEditor', component: PerfilEditorComponent },
    { path: 'paginaPrincipalSuscriptor', component: PaginaPrincipalSuscriptorComponent, canActivate: [guardSuscriptor] },
    { path: 'registro', component: RegistroComponent,canActivate: [loginGuardia] },
    { path: 'paginaPrincipalAdministrador', component: PaginaPrincipalAdministradorComponent, canActivate: [adminGuardia] }, // Protegiendo la ruta
    { path: 'configuracionCostoAnuncios', component: ConfigPreciosAnunciosComponent, canActivate: [adminGuardia] }, // Protegiendo la ruta
    { path: 'paginaprincipalanunciante', component: PaginaPrincipalAnuncianteComponent,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta
    { path: 'ConfiguracionCompraDeAnuncios', component: ConfiguracionCompraDeAnunciosComponent ,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta
    { path: 'RecargarSaldo', component: RecargarCarteraComponent ,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta
    { path: 'estadoAnuncios', component: EstadoAnunciosComponent ,canActivate: [AnuncianteGuardia]}, // Protegiendo la ruta
    { path: 'paginaPrincipalEditor', component: PaginaPrincipalEditorComponent ,canActivate: [editorGuardia]}, // Protegiendo la ruta
    { path: 'RecargarSaldoEditor', component: RecargarCarteraEditorComponent ,canActivate: [editorGuardia]}, // Protegiendo la ruta
    { path: 'CrearRevista', component: CrearRevistaComponent,canActivate: [editorGuardia] }, // Protegiendo la ruta
    { path: 'aprobarRevista', component: AprobarRevistaComponent, canActivate: [adminGuardia] }, // Protegiendo la ruta
    { path: 'modificacionCostoOcultacion', component: ModificarCostoOcultacionComponent , canActivate: [adminGuardia]}, // Protegiendo la ruta
    { path: 'modificacionCostoAsociado', component: ModificarCostoAsociadoComponent, canActivate: [adminGuardia] }, // Protegiendo la ruta
    { path: 'configuracionRevistas', component: ConfiguracionRevistasComponent ,canActivate: [editorGuardia]}, // Protegiendo la ruta
    { path: 'bloqueoAdss', component: BloqueoDeAnunciosComponent ,canActivate: [editorGuardia]}, // Protegiendo la ruta
    { path: 'perfil', component: PerfilComponent }, // Protegiendo la ruta
    { path: 'estadoAnunciosAdmin', component: EstadoAnunciosAdminComponent, canActivate: [adminGuardia]},
    { path: 'editarRerfil', component: EditarPerfilComponent }, // Protegiendo la ruta
    { path: 'configuracionVigenciaAnuncios', component: ConfiguracionDuracionAnunciosComponent, canActivate: [adminGuardia] },
    { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirige a login por defecto
    { path: 'modificacionCostoAsociado(Global)', component: classModificarCosotAsociadoGLOBALComponent , canActivate: [adminGuardia]},
    { path: 'misSuscripciones', component: RevistasSuscritasComponent ,canActivate: [guardSuscriptor]},
    { path: 'revista', component: VisualizacionRevistaComponent ,canActivate: [guardSuscriptor]},
    { path: 'CrearArfticulo', component: CrearArticuloComponent,canActivate: [editorGuardia]},
    { path: 'RepostesEditor', component: ReportesComponent,canActivate: [editorGuardia]},
    { path: 'RepostesComentariosGeneral', component: ReporteDeComentariosComponent,canActivate: [editorGuardia]},
    { path: 'RepostesSuscripcionesGeneral', component: ReporteDesuscripcionesComponent,canActivate: [editorGuardia]},
    { path: 'RepostesAdministrado', component: ReportesAdministradorComponent,canActivate: [adminGuardia]},
    { path: 'logout', component: CerrarSesionComponent}
  ];
