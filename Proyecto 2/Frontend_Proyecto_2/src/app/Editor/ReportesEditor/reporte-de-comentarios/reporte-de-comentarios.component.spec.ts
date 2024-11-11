import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteDeComentariosComponent } from './reporte-de-comentarios.component';

describe('ReporteDeComentariosComponent', () => {
  let component: ReporteDeComentariosComponent;
  let fixture: ComponentFixture<ReporteDeComentariosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteDeComentariosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteDeComentariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
