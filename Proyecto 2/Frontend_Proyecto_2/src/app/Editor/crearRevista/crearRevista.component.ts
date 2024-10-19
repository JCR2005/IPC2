import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CrearRevistaService } from '../../services/servicioCrearRevista/crear-revista.service';
import { token } from '../../token';

@Component({
  selector: 'app-crearRevista',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './crearRevista.component.html',
  styleUrls: ['./crearRevista.component.css']
})
export class CrearRevistaComponent implements OnInit {

  nombreRevista: string = "";
  autor: string = "";
  descripcion: string = "";
  categoria: string = "";
  fecha: string = "";
  etiquetas: string[] = []; // Aquí se almacenarán las etiquetas seleccionadas
  etiquetasSeleccionadas: { [key: string]: boolean } = {}; // Objeto para almacenar el estado de los checkboxes
  cargando: boolean = false;
  constructor(private crearRevistaService: CrearRevistaService, private token: token) {}

  ngOnInit() {
    this.inicializarEtiquetasSeleccionadas();
  }

  inicializarEtiquetasSeleccionadas() {
    const etiquetas = ['Tecnología', 'Ciencia', 'Deportes', 'Moda', 'Salud', 'Arte', 'Cultura', 'Entretenimiento', 'Historia',
      'Negocios', 'Viajes', 'Gastronomía', 'Educación', 'Religión', 'Finanzas', 'Ecología', 'Literatura', 'Fotografía',
      'Bienestar', 'Juegos', 'DIY', 'Filosofía', 'Crítica Social', 'Feminismo', 'Tecnología Verde', 'Desarrollo Personal',
      'Sostenibilidad', 'Ciberseguridad', 'Salud Mental', 'Marketing', 'E-commerce', 'Moda Sostenible', 'Eventos',
      'Actualidad', 'Podcasting', 'Crítica Cinematográfica', 'Danza', 'Nostalgia'];

    for (const etiqueta of etiquetas) {
      this.etiquetasSeleccionadas[etiqueta] = false; // Inicializa todos los checkboxes como desmarcados
    }
  }

  reiniciarDatos() {
    this.nombreRevista = "";
    this.categoria = "";
    this.descripcion = "";
    this.etiquetas = [];
    this.fecha = "";

    // Desmarcar todos los checkboxes
    for (const etiqueta in this.etiquetasSeleccionadas) {
      this.etiquetasSeleccionadas[etiqueta] = false;
    }
  }

  // Método para manejar el envío del formulario
  onSubmit(event: Event) {
    this.cargando = true;
    event.preventDefault(); // Previene el envío por defecto del formulario

    this.autor = this.token.obtenerUsuario();
    const nuevaRevista = {
      autor: this.autor,
      titulo: this.nombreRevista,
      descripcion: this.descripcion,
      categoria: this.categoria,
      fechaPublicacionTexto: this.fecha,
      etiquetas: this.etiquetas
    };

    console.log('Nueva Revista:', nuevaRevista);

    this.crearRevistaService.crearRevista(nuevaRevista).subscribe(
      (response: any) => {
         // Iniciar la carga
        // Añadimos un pequeño retraso antes de ocultar el panel de carga
        setTimeout(() => {
          this.cargando = false; // Terminar la carga después de 500ms
        }, 1000);

        if (response.verificacion === "true" || response.verificacion === true) {
          this.reiniciarDatos();
        }
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );
  }

  // Método para actualizar las etiquetas seleccionadas
  toggleEtiqueta(etiqueta: string, event: Event) {
    const checkbox = event.target as HTMLInputElement; // Casting aquí
    const checked = checkbox.checked;

    this.etiquetasSeleccionadas[etiqueta] = checked; // Actualiza el estado del checkbox

    if (checked) {
      this.etiquetas.push(etiqueta); // Añadir la etiqueta si está marcada
    } else {
      this.etiquetas = this.etiquetas.filter(e => e !== etiqueta); // Quitar la etiqueta si está desmarcada
    }
    console.log(`Etiquetas seleccionadas:`, this.etiquetas);
  }
}
