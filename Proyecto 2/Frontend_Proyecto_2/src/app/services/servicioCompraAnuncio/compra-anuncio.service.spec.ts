import { TestBed } from '@angular/core/testing';

import { CompraAnuncioService } from './compra-anuncio.service';

describe('CompraAnuncioService', () => {
  let service: CompraAnuncioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompraAnuncioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
