import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaPrincipalSuscriptorComponent } from './pagina-principal-suscriptor.component';

describe('PaginaPrincipalAdministradorComponent', () => {
  let component: PaginaPrincipalSuscriptorComponent;
  let fixture: ComponentFixture<PaginaPrincipalSuscriptorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaPrincipalSuscriptorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaPrincipalSuscriptorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
