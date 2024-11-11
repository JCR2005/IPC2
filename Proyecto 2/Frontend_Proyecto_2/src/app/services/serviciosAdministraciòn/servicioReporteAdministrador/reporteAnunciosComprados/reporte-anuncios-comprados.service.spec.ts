import { TestBed } from '@angular/core/testing';

import { ReporteAnunciosCompradosService } from './reporte-anuncios-comprados.service';

describe('ReporteAnunciosCompradosService', () => {
  let service: ReporteAnunciosCompradosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReporteAnunciosCompradosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
