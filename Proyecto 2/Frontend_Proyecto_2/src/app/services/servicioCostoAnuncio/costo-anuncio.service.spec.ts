import { TestBed } from '@angular/core/testing';

import { CostoAnuncioService } from './costo-anuncio.service';

describe('CostoAnuncioService', () => {
  let service: CostoAnuncioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CostoAnuncioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
