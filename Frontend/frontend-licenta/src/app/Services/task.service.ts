import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Task } from '../Classes/Task';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private taskUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) { }

  public getTasks(): Observable<Task[]>{
    return this.http.get<Task[]>(`${this.taskUrl}`);
  }
}
