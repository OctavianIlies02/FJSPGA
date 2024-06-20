import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlgEvolutivService {

  private apiUrl = 'http://localhost:8080/algEvolutiv';

  constructor(private http: HttpClient) { }

  runAlgorithm(n: number, jobList: any[], populationSize: number, maxGenerations: number, lambda: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/run`, jobList, {
      params: {
        n: n.toString(),
        populationSize: populationSize.toString(),
        maxGenerations: maxGenerations.toString(),
        lambda: lambda.toString()
      }
    });
  }
}
