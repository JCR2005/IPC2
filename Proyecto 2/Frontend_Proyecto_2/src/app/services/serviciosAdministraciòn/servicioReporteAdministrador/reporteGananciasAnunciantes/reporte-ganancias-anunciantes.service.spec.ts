import { TestBed } from '@angular/core/testing';

import { ReporteGananciasAnunciantesService } from './reporte-ganancias-anunciantes.service';

describe('ReporteGananciasAnunciantesService', () => {
  let service: ReporteGananciasAnunciantesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReporteGananciasAnunciantesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
