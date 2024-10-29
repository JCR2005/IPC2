import { TestBed } from '@angular/core/testing';

import { ServicioPerfilService } from './servicio-perfil.service';

describe('ServicioPerfilService', () => {
  let service: ServicioPerfilService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicioPerfilService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
