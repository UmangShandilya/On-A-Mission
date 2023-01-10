import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DashService } from 'src/app/Service/Dashoard/dash.service';
import { UserService } from 'src/app/Service/User/user.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  constructor( private _snackBar : MatSnackBar, private routes : Router, private service:UserService, private dashboardService : DashService) { }

  ngOnInit(): void {}

  // Custom message to be displayed
  messageToUser : string = "";

  responseData : any ;

  signInForm = new FormGroup(
    {
      email : new FormControl(null, [Validators.required,Validators.email]),
      password : new FormControl(null,[Validators.required])
    }
  );

  // Function calling service
  signIn() : void
  {
    this.service.signInProcess(this.signInForm.value).subscribe({
      next  : (data) => 
      {
        this.responseData = data;
        localStorage.setItem('JWT', this.responseData.token);
        this.dashboardService.authenticatedEmail = this.signInForm.value.email;
        this.service.isAuthenticated = true;
        this.routes.navigateByUrl("dash/task");
      },

      error : (data) => 
      {
        console.log(data); 
        this.messageToUser = "Invalid Credentials"; 
        this.openSnackBar(this.messageToUser);
      }
    }); 
  }

  // Getters
  get emailEntity() { return this.signInForm.get('email');}
  get passwordEntity() { return this.signInForm.get('password');}

  // Snackbar
  openSnackBar(message : string) 
  {
    this._snackBar.open(message, "Close");
  }

  // On Destruction
  ngOnDestroy(): void {}

}
