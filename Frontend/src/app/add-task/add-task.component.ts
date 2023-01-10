import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DashService } from '../Service/Dashoard/dash.service';

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css']
})
export class AddTaskComponent implements OnInit {

  constructor(private dash:DashService,private dialog:MatDialog) { }

  ngOnInit(): void {
  }
  addForm=new FormGroup({

    title:new FormControl('',Validators.required),
    content:new FormControl(''),
    dueDate:new FormControl(''),
    choosePriority:new FormControl('',Validators.required),
    category:new FormControl(''),
    imageUrl:new FormControl(''),
    isCompleted:new FormControl('')
  })
  minDate:any = ""
  addTask(){
    this.dash.addTask(this.addForm.value).subscribe(res=>{alert("added successfully")},err=>{"there is error"})
  }

  ngOnDestroy(): void {}
}
