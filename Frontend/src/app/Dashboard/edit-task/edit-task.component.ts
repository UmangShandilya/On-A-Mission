import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DashService } from 'src/app/Service/Dashoard/dash.service';
import { TodayTaskComponent } from '../today-task/today-task.component';
import { UserTaskCardComponent } from '../user-task-card/user-task-card.component';

@Component({
  selector: 'app-edit-task',
  templateUrl: './edit-task.component.html',
  styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent implements OnInit {

  constructor(
    private _snackBar: MatSnackBar,
    private service: DashService,
    private routes: Router,
    public dialogRef: MatDialogRef<UserTaskCardComponent, TodayTaskComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { task: any }
  ) {
    this.newTaskForm.setValue({
      taskName: data.task.taskName,
      dueDate: data.task.dueDate,
      category: data.task.category,
      choosePriority: data.task.choosePriority,
      content: data.task.content
    });

    this.responseTaskID = data.task.taskID;
  }

  ngOnInit(): void { }

  // Custom message to be displayed
  messageToUser: string = "";

  responseTaskID: any;

  newTaskForm = new FormGroup(
    {
      taskName: new FormControl(null, [Validators.required]),
      dueDate: new FormControl(null),
      category: new FormControl(null),
      choosePriority: new FormControl(null, [Validators.required]),
      content: new FormControl(null)
    }
  );

  // Function calling service
  saveTask(): void {
    this.service.editTask(this.responseTaskID, this.newTaskForm.value).subscribe({
      next: () => {
        this.openSnackBar("Task Edited Successfully");
        this.routes.navigateByUrl("dash/task");
      },
      error: () => { this.openSnackBar("OOPS! Something went wrong"); console.error();}
    });
  }

  // Getters
  get taskNameEntity() { return this.newTaskForm.get('taskName'); }
  get dueDateEntity() { return this.newTaskForm.get('dueDate'); }
  get categoryEntity() { return this.newTaskForm.get('category'); }
  get priorityEntity() { return this.newTaskForm.get('choosePriority'); }
  get contentEntity() { return this.newTaskForm.get('content'); }

  // Snackbar
  openSnackBar(message: string) {
    this._snackBar.open(message, "Close");
  }

  // On Destruction
  ngOnDestroy(): void { }

}