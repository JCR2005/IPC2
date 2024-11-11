import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteDeRevistasMasGustadasComponent } from './reporte-de-revistas-mas-gustadas.component';

describe('ReporteDeComentariosComponent', () => {
  let component: ReporteDeRevistasMasGustadasComponent;
  let fixture: ComponentFixture<ReporteDeRevistasMasGustadasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteDeRevistasMasGustadasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteDeRevistasMasGustadasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
