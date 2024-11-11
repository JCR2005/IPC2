import { TestBed } from '@angular/core/testing';

import { ReporteComentariosService } from '../reporte-comentarios.service';

describe('ReporteComentariosService', () => {
  let service: ReporteComentariosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReporteComentariosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
