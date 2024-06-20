import { TestBed } from '@angular/core/testing';
import { WritesService } from './writes.service';



describe('WritesService', () => {
  let service: WritesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WritesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
