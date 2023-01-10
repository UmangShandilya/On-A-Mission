import { Injectable } from '@angular/core';
import { UserTask } from 'src/app/Model/Task';
import { DashService } from './dash.service';

@Injectable({
  providedIn: 'root'
})
export class FetchDataService {

  constructor(private service : DashService) { }

  fetchedTask: Array<UserTask> = []
  // Method to get task details
  fetchTaskOfUser() {
    this.service.fetchTask().subscribe(
      {
        next: (data) => { this.fetchedTask = data; },
        error: () => {console.error()} 
      }
    );
  }
}
