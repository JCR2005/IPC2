import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadoAnunciosComponent } from './estado-anuncios.component';

describe('EstadoAnunciosComponent', () => {
  let component: EstadoAnunciosComponent;
  let fixture: ComponentFixture<EstadoAnunciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadoAnunciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstadoAnunciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
