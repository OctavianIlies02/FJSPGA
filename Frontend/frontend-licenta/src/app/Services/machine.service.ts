import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Machine } from '../Classes/Machine';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MachineService {

  private machineUrl = 'http://localhost:8080/api/machines';

  constructor(private http: HttpClient) { }

  public getMachines(): Observable<Machine[]>{
    return this.http.get<Machine[]>(`${this.machineUrl}`);
  }
}