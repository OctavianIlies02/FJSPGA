import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Job } from '../Classes/Job';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  private jobUrl = 'http://localhost:8080/api/jobs';

  constructor(private http: HttpClient) { }

  public createJob(job: Job): Observable<Job>{
    return this.http.post<Job>(`${this.jobUrl}`,job);
  }

  public getJobs(): Observable<Job[]>{
    return this.http.get<Job[]>(`${this.jobUrl}`);
  }
}
