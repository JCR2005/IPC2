import { Revista } from './../../models/revista.model';
import { CrearArticuloService } from './../../services/serviciosEditor/servicioCrearArticulo/crear-articulo.service';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { token } from '../../token';
import { DomSanitizer } from '@angular/platform-browser';
import { SuscripcionesService } from '../../services/serviciosEditor/serviciosReportesEditor/reporteSuscripciones/suscripciones.service';


@Component({
  selector: 'app-crearRevista',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './crearArticulo.component.html',
  styleUrls: ['./crearArticulo.component.css']
})
export class CrearArticuloComponent implements OnInit {
  idRevista:string="";
  procesoExitoso=false;
  Revistas:Revista[]=[];
  panelMensaje: boolean = false;
  imagenSeleccionada: File | null = null; // Variable para almacenar la imagen seleccionada
  imagenVisualizacion: string = '';
  selectedFile: File | null = null;
  nombreArticulo: string = "";
  autor: string = "";
  descripcion: string = "";
  categoria: string = "";
  fecha: string = "";
  mensaje:string="";
  etiquetas: string[] = []; // Aquí se almacenarán las etiquetas seleccionadas
  etiquetasSeleccionadas: { [key: string]: boolean } = {}; // Objeto para almacenar el estado de los checkboxes
  cargando: boolean = false;
  constructor( private SuscripcionesService:SuscripcionesService, private sanitizer: DomSanitizer,private crearArticulo: CrearArticuloService, private token: token) {}

  ngOnInit() {
    const formData = new FormData();
    formData.append('idUsuario', this.token.obtenerUsuario());
    this.SuscripcionesService.obtenerRevistas(formData).subscribe(
      (response: any) => {


        this.Revistas=response.revistas;



      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );

  }



  cerrarPanelMensaje(){
    this.panelMensaje=false;
    if(this.procesoExitoso){
      this.reiniciarDatos();
    }
  }
  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.selectedFile = input.files[0]; // Asigna el primer archivo seleccionado
    }
  }
  reiniciarDatos() {
    this.nombreArticulo = "";
    this.categoria = "";
    this.descripcion = "";
    this.etiquetas = [];
    this.fecha = "";
    this.imagenSeleccionada=null;
    // Desmarcar todos los checkboxes
    for (const etiqueta in this.etiquetasSeleccionadas) {
      this.etiquetasSeleccionadas[etiqueta] = false;
    }
  }

  // Método para manejar el envío del formulario
  onSubmit(event: Event) {
    this.cargando = true;
    event.preventDefault(); // Previene el envío por defecto del formulario


    const articulo = {
      idRevista:this.idRevista,
      nombre:this.nombreArticulo,
      descripcion: this.descripcion,
      fechaCreacionTexto: this.fecha
    };

    // Crear un FormData para enviar los datos
    const formData = new FormData();
    formData.append('articulo', JSON.stringify(articulo));

    // Agregar la imagen seleccionada al FormData
    if (this.imagenSeleccionada) {
      formData.append('imagen', this.imagenSeleccionada); // Añadir la imagen seleccionada
    }

    if (this.selectedFile) {
      formData.append('pdf', this.selectedFile); // Añadir la imagen seleccionada
    }

    this.crearArticulo.crearArticulo(formData).subscribe(
      (response: any) => {
         // Iniciar la carga
        // Añadimos un pequeño retraso antes de ocultar el panel de carga
        this.mensaje=response.mensaje;
        this.panelMensaje=true;
        this.procesoExitoso=response.procesoExitoso;
        if(this.procesoExitoso){
          this.reiniciarDatos();
        }
       this.cargando = false;

      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );
  }

  public archivos: any = [];
  onFileChange(event: any) {
    const imagen = event.target.files[0];

    // Almacena la imagen seleccionada en la variable
    this.imagenSeleccionada = imagen;

    this.extraerBase64(imagen).then((imagenb: any) => {
      this.imagenVisualizacion = imagenb.base;
    });
    this.archivos.push(imagen);
  }


  extraerBase64 = ($event: any) => new Promise((resolve, reject) => {
    try {
      const unsafeImg = window.URL.createObjectURL($event);
      const image = this.sanitizer.bypassSecurityTrustUrl(unsafeImg);
      const reader = new FileReader();
      reader.readAsDataURL($event);
      reader.onload = () => {
        resolve({ base: reader.result });
      };
      reader.onerror = (error) => {
        resolve({ base: null });
      };
    } catch (e) {
      reject(null);
    }
  });

}
