import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfiguracionCompraDeAnunciosComponent } from './configuracion-compra-de-anuncios.component';

describe('ConfiguracionCompraDeAnunciosComponent', () => {
  let component: ConfiguracionCompraDeAnunciosComponent;
  let fixture: ComponentFixture<ConfiguracionCompraDeAnunciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfiguracionCompraDeAnunciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfiguracionCompraDeAnunciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
