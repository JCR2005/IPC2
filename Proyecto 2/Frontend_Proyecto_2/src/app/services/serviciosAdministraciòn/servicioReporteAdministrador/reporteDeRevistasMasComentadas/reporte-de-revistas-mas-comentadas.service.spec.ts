import { TestBed } from '@angular/core/testing';

import { ReporteDeRevistasMasComentadasService } from './reporte-de-revistas-mas-comentadas.service';

describe('ReporteDeRevistasMasComentadasService', () => {
  let service: ReporteDeRevistasMasComentadasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReporteDeRevistasMasComentadasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
