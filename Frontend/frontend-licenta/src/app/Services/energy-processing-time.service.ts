import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EnergyProcessingTime } from '../Classes/EnergyProcessingTime';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EnergyProcessingTimeService {

  private energyProcessingTimeUrl = 'http://localhost:8080/api/energyprocessingtimes'

  constructor(private http: HttpClient) { }

  public getEnergyProcessingTimes(): Observable<EnergyProcessingTime[]>{
    return this.http.get<EnergyProcessingTime[]>(`${this.energyProcessingTimeUrl}`);
  }
}
