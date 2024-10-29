export interface Usuario {
  nombre: string;
  usuario: string;
  edad: number;
  descripcion: string;
  tipoCuenta: string;
  Intereses: string[];
  foto: string; // Asegúrate de que sea un Blob
}
