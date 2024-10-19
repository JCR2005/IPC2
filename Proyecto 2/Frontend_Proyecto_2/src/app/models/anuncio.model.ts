export interface Anuncio {
  idAnuncio: string; // o string, según tu caso
  tipoAnuncio: string;
  vigencia: string;
  fechaPublicacion: string; // Puedes usar Date si prefieres
  fechaFinalizacion: string;
  estado: boolean;
}
