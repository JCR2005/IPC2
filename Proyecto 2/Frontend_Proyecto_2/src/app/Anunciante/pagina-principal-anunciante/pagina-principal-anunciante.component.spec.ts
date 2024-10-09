import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaPrincipalAnuncianteComponent } from './pagina-principal-anunciante.component';

describe('PaginaPrincipalAnuncianteComponent', () => {
  let component: PaginaPrincipalAnuncianteComponent;
  let fixture: ComponentFixture<PaginaPrincipalAnuncianteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaPrincipalAnuncianteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaPrincipalAnuncianteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
