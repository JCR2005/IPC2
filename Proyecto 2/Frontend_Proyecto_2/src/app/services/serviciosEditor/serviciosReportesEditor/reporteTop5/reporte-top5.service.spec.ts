import { TestBed } from '@angular/core/testing';

import { ReporteTop5Service } from './reporte-top5.service';

describe('ReporteTop5Service', () => {
  let service: ReporteTop5Service;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReporteTop5Service);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
