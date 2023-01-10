import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserTask } from 'src/app/Model/Task';
import { DashService } from 'src/app/Service/Dashoard/dash.service';

@Component({
  selector: 'app-archive-task',
  templateUrl: './archive-task.component.html',
  styleUrls: ['./archive-task.component.css']
})
export class ArchiveTaskComponent implements OnInit {

  constructor(private service: DashService, private snackBar: MatSnackBar) { }

  ngOnInit(): void 
  {
    this.fetchTaskOfUser();
  }

  // Arrays to store task fetched
  fetchedCompletedTask  : Array<UserTask> = [];
  fetchedUnfinishedTask : Array<UserTask> = [];
  
  // Method to get task details
  fetchTaskOfUser() 
  {
    this.service.fetchCompletedTask().subscribe(
      {
        next: (data) => { this.fetchedCompletedTask = data; },
        error: () => { this.openSnackBar("OOPS! Something went wrong") }
      }
    );

    this.service.fetchUnfinishedTask().subscribe(
      {
        next: (data) => { this.fetchedUnfinishedTask = data; },
        error: () => { this.openSnackBar("OOPS! Something went wrong") }
      }
    );
    
  }

  // Function to remove a task.
  removeTask(taskID: any) {
    this.service.deleteTask(taskID).subscribe(
      {
        next: () => {
          this.openSnackBar("Task Removed Successfully"); 
          this.fetchedCompletedTask  = [];
          this.fetchedUnfinishedTask = [];
          this.fetchTaskOfUser();
        },
        error: () => { this.openSnackBar("OOPS! Something went wrong"); }
      }
    );

  }

  // Snack Bar
  openSnackBar(message: string) {
    this.snackBar.open(message, "Close");
  }
}
