import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteDepagosComponent } from './reporte-de-pagos.component';

describe('ReporteDeComentariosComponent', () => {
  let component: ReporteDepagosComponent;
  let fixture: ComponentFixture<ReporteDepagosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteDepagosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteDepagosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
