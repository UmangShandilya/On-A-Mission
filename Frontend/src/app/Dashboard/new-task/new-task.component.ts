import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DashService } from 'src/app/Service/Dashoard/dash.service';
import { UserTaskCardComponent } from '../user-task-card/user-task-card.component';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css']
})
export class NewTaskComponent implements OnInit {

  constructor( 
    private _snackBar : MatSnackBar,  
    private service : DashService,
    private routes : Router
    ) { }

  ngOnInit(): void {}

  // Custom message to be displayed
  messageToUser : string = "";

  responseData : any ;

  newTaskForm = new FormGroup(
    {
      taskName : new FormControl(null, [Validators.required]),
      dueDate : new FormControl(null),
      category : new FormControl(null),
      choosePriority : new FormControl(null, [Validators.required]),
      content : new FormControl(null)
    }
  );

  // Function calling service
  addTask() : void
  {
    this.service.addTask(this.newTaskForm.value).subscribe({
      next  : () => 
      {
        this.openSnackBar("Task Added Successfully");
        this.routes.navigateByUrl("dash/task");
      },
      error : () => {this.openSnackBar("OOPS! Something went wrong");}
    });
  }

  // Getters
  get taskNameEntity() { return this.newTaskForm.get('taskName');}
  get dueDateEntity() { return this.newTaskForm.get('dueDate');}
  get categoryEntity() { return this.newTaskForm.get('category');}
  get priorityEntity() { return this.newTaskForm.get('choosePriority');}
  get contentEntity() { return this.newTaskForm.get('content');}

  // Snackbar
  openSnackBar(message : string) 
  {
    this._snackBar.open(message, "Close");
  }

  // On Destruction
  ngOnDestroy(): void {}
}
