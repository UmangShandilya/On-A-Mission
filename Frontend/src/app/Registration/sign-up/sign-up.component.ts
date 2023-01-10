import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, AbstractControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { passwordMatch } from 'src/app/Custom Validators/PasswordValidation';
import { UserService } from 'src/app/Service/User/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})

export class SignUpComponent implements OnInit {

  constructor( private _snackBar : MatSnackBar, private service:UserService, private router:Router) { }

  ngOnInit(): void {}

  // Custom message
  messageToUser : string = "";

  signUpForm = new FormGroup(
    {
      userName : new FormControl(null, [Validators.required]),
      email : new FormControl(null, [Validators.required, Validators.email]),
      password : new FormControl(null,[Validators.required]),
      confirmPassword : new FormControl(null, [Validators.required]),
    },
    [passwordMatch("password","confirmPassword")]
  );

  // Function calling service
  signUp()
  {
    
      this.service.signUpProcess(this.signUpForm.value).subscribe({
        next  : () => {this.messageToUser = "Successfully Registered"; this.openSnackBar(this.messageToUser); this.router.navigateByUrl("");},
        error : (data) => {this.messageToUser = "OOPS! Something went wrong"; this.openSnackBar(this.messageToUser); console.log(data); }
      });
  }  

  // Getters
  get userNameEntity() { return this.signUpForm.get('userName');}
  get emailEntity() { return this.signUpForm.get('email');}
  get passwordEntity() { return this.signUpForm.get('password');}
  get confirmPasswordEntity() { return this.signUpForm.get('confirmPassword');
  }

  // Snack Bar for displaying message
  openSnackBar(message : string) 
  {
    this._snackBar.open(message, "Close");
  }

  // On Destruction
  ngOnDestroy(): void {}
}
