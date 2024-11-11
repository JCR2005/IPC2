import { TestBed } from '@angular/core/testing';

import { ReporteDeRevistasMasSuscritasService } from './reporte-de-revistas-mas-suscritas.service';

describe('ReporteDeRevistasMasSuscritasService', () => {
  let service: ReporteDeRevistasMasSuscritasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReporteDeRevistasMasSuscritasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
