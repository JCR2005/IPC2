import { TestBed } from '@angular/core/testing';

import { CrearArticuloService } from './crear-articulo.service';

describe('CrearArticuloService', () => {
  let service: CrearArticuloService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrearArticuloService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
