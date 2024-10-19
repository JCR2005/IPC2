export interface Revista {
  idRevista: string; // o string, según tu caso
  autor: string;
  titulo: string;
  fechaPublicacion: string; // Puedes usar Date si prefieres
  costoOcultacion: number;
  aprobacion: boolean;
  comentarios: boolean;
  likes: boolean;
  suscripciones: boolean;
}
