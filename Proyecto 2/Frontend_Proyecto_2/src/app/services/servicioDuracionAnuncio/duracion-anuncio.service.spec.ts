import { TestBed } from '@angular/core/testing';

import { DuracionAnuncioService } from './duracion-anuncio.service';

describe('DuracionAnuncioService', () => {
  let service: DuracionAnuncioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DuracionAnuncioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
