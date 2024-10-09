import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ConfigPreciosAnunciosComponent } from "./configuracion-costa-anuncio/configuracion-costa-anuncio.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, LoginComponent, ConfigPreciosAnunciosComponent], // Importa RouterModule
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Frontend_Proyecto_2';
}
