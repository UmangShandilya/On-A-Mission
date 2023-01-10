import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserTask } from 'src/app/Model/Task';
import { DashService } from 'src/app/Service/Dashoard/dash.service';
import { EditTaskComponent } from '../edit-task/edit-task.component';

@Component({
  selector: 'app-user-task-card',
  templateUrl: './user-task-card.component.html',
  styleUrls: ['./user-task-card.component.css']
})
export class UserTaskCardComponent implements OnInit {

  constructor(private service: DashService, private snackBar: MatSnackBar, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.fetchTaskOfUser();
  }

  fetchedTask: Array<UserTask> = []

  pageSlice = this.fetchedTask.slice(0, 3);

  OnPageChange(event: PageEvent) {
    const startIndex = event.pageIndex * event.pageSize;
    let endIndex = startIndex + event.pageSize;

    if (endIndex > this.fetchedTask.length) {
      endIndex = this.fetchedTask.length;
    }

    this.pageSlice = this.fetchedTask.slice(startIndex, endIndex);
  }
  // Method to get task details
  public fetchTaskOfUser() {
    this.service.fetchTask().subscribe(
      {
        next: (data) => {
          this.fetchedTask = data;
          this.pageSlice = this.fetchedTask.slice(0, 3);
        },
        error: () => { this.openSnackBar("OOPS! Something went wrong") }
      }
    );
  }

  // Function calling service
  removeTask(taskID: any) {
    this.service.deleteTask(taskID).subscribe(
      {
        next: () => {
          this.openSnackBar("Task Removed Successfully");
          this.fetchedTask = [];
          this.fetchTaskOfUser();
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
          this.openSnackBar("Task Completed");
          this.fetchedTask = [];
          this.fetchTaskOfUser();
        },
        error: () => { this.openSnackBar("OOPS! Something went wrong"); }
      }
    );
  }


  // Edit Account Dialog Box
  openEditDialog(task: UserTask): void {
    const dialogRef = this.dialog.open(EditTaskComponent, { data: { task } });
  }

  // Snack Bar
  openSnackBar(message: string) {
    this.snackBar.open(message, "Close");
  }
}
