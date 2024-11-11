import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteDeGananciasAnunciantesComponent } from './reporte-de-ganancias-anunciantes.component';

describe('ReporteDeComentariosComponent', () => {
  let component: ReporteDeGananciasAnunciantesComponent;
  let fixture: ComponentFixture<ReporteDeGananciasAnunciantesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteDeGananciasAnunciantesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteDeGananciasAnunciantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
