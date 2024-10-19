import { TestBed } from '@angular/core/testing';

import { CrearRevistaService } from './crear-revista.service';

describe('CrearRevistaService', () => {
  let service: CrearRevistaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrearRevistaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
