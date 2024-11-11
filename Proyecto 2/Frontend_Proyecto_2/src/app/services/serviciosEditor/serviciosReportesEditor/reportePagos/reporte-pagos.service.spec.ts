import { TestBed } from '@angular/core/testing';

import { ReportePagosService } from './reporte-pagos.service';

describe('ReportePagosService', () => {
  let service: ReportePagosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReportePagosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
