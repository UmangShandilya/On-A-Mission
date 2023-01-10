import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserTask } from 'src/app/Model/Task';
import { DashService } from 'src/app/Service/Dashoard/dash.service';
import { EditTaskComponent } from '../edit-task/edit-task.component';

@Component({
  selector: 'app-today-task',
  templateUrl: './today-task.component.html',
  styleUrls: ['./today-task.component.css']
})
export class TodayTaskComponent implements OnInit {

  constructor(private service: DashService , private snackBar: MatSnackBar, private dialog : MatDialog) { }

  highPriorityTasks: Array<UserTask> = [];
  mediumPriorityTasks: Array<UserTask> = [];
  lowPriorityTasks: Array<UserTask> = [];

  ngOnInit(): void {
    this.fetchHighPriorityTaskOfUser();
    this.fetchMediumPriorityTaskOfUser();
    this.fetchLowPriorityTaskOfUser();
  }

  // Method to get task details
  fetchHighPriorityTaskOfUser() {
    this.service.fetchPriorityTask("HIGH").subscribe(
      {
        next: (data) => { this.highPriorityTasks = data; },
        error: () => { this.openSnackBar("OOPS! Something went wrong") }
      }
    );
  }

  fetchMediumPriorityTaskOfUser() {
    this.service.fetchPriorityTask("MEDIUM").subscribe(
      {
        next: (data) => { this.mediumPriorityTasks = data; },
        error: () => { this.openSnackBar("OOPS! Something went wrong") }
      }
    );
  }

  fetchLowPriorityTaskOfUser() {
    this.service.fetchPriorityTask("LOW").subscribe(
      {
        next: (data) => { this.lowPriorityTasks = data; },
        error: () => { this.openSnackBar("OOPS! Something went wrong") }
      }
    );
  }

  // Function calling service
  removeTask(taskName: any) {
    this.service.deleteTask(taskName).subscribe(
      {
        next: () => {
          this.openSnackBar("Task Removed Successfully");

          this.highPriorityTasks = [];
          this.mediumPriorityTasks = [];
          this.lowPriorityTasks = [];

          this.fetchHighPriorityTaskOfUser();
          this.fetchMediumPriorityTaskOfUser();
          this.fetchLowPriorityTaskOfUser();
        },
        error: () => { this.openSnackBar("OOPS! Something went wrong"); }
      }
    );

  }

  // Function calling service to update task completion status
  markTaskAsCompleted(task: UserTask) {
    task.isCompleted = true;
    this.service.updateTaskStatusDetails(task).subscribe(
      {
        next: () => {
          this.openSnackBar("Task Completed Successfully");

          this.highPriorityTasks = [];
          this.mediumPriorityTasks = [];
          this.lowPriorityTasks = [];
          
          this.fetchHighPriorityTaskOfUser();
          this.fetchMediumPriorityTaskOfUser();
          this.fetchLowPriorityTaskOfUser();

        },
        error: () => { this.openSnackBar("OOPS! Something went wrong"); }
      }
    );
  }


  // Edit Account Dialog Box
  openEditDialog(task : UserTask): void {
    const dialogRef = this.dialog.open(EditTaskComponent, { data: {task} });
  }

  // Snack Bar
  openSnackBar(message: string) {
    this.snackBar.open(message, "Close");
  }
}
