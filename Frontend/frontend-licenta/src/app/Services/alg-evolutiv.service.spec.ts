import { TestBed } from '@angular/core/testing';

import { AlgEvolutivService } from './alg-evolutiv.service';

describe('AlgEvolutivService', () => {
  let service: AlgEvolutivService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlgEvolutivService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
