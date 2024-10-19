import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificarCostoOcultacionComponent } from './modificar-costo-ocultacion.component';

describe('ModificarCostoOcultacionComponent', () => {
  let component: ModificarCostoOcultacionComponent;
  let fixture: ComponentFixture<ModificarCostoOcultacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModificarCostoOcultacionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModificarCostoOcultacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
