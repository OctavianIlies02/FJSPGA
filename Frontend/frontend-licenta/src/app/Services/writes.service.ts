import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WritesService {

  private apiUrl = 'http://localhost:8080/api/writes/generate';
  private apiUrlDeleteAll = 'http://localhost:8080/api/writes/deleteAll';

  constructor(private http: HttpClient) { }

  generateData(nbJobs: number, nbMchs: number, ops: Array<any>, modes: Array<any>): Observable<any> {
    const params = new HttpParams()
      .set('nbJobs', nbJobs.toString())
      .set('nbMchs', nbMchs.toString());
    
    const data = {
      ops: ops,
      modes: modes
    };
    
    return this.http.post<any>(this.apiUrl, data, { params }).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error);
      })
    );
  }

  deleteAllData(): Observable<any> {
    return this.http.delete<any>(this.apiUrlDeleteAll).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(error);
      })
    );
  }


}
